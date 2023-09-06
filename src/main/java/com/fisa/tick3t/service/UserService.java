package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.PasswordDto;
import com.fisa.tick3t.domain.dto.ProfileDto;
import com.fisa.tick3t.domain.dto.TokenDto;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.jwt.TokenProvider;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UtilFunction util;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    // 2.1 [signup] 회원가입 --- 0
    @Transactional
    public ResponseDto<ResponseCode> signUp(User user) throws CustomException {
        ResponseDto<ResponseCode> responseDto = new ResponseDto<>();
        String userPwd = user.getUserPwd();
        String userEmail = user.getEmail();

        // 중복된 이메일일 경우
        if (userRepository.checkEmail(userEmail) != null) {
            throw new CustomException(ResponseCode.EMAIL_ALREADY_IN_USE);
        }

        // password를 hashing하고 UserDto에 저장
        UserDto userDto = new UserDto(user, util.hashPassword(userPwd));

        // userDb에 Insert
        try {
            userRepository.insertUser(userDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ResponseCode.FAIL);
        }
        return responseDto;

    }


    // 2.4 [reissue] 비밀번호 재발급 --- 0
    @Transactional
    public ResponseDto<ResponseCode> resetPassword(User user) {
        ResponseDto<ResponseCode> responseDto = new ResponseDto<>();
        String userEmail = user.getEmail();
        try {
            //파라미터 유효성 검사 (존재하는 유저인지 판단)
            Integer userId = userRepository.checkUser(user);

            // ID를 받아오지 못했다면 일치하지 않는 유저 정보 에러 반환
            if (userId == null) {
                throw new CustomException(ResponseCode.MISMATCHED_USER_INFO);
            }

            // 새 패스워드 생성
            String password = util.generatePassword();

            // 메일링처리
            util.mailingPassword(userEmail, password);

            // 패스워드 해싱 및 업데이트
            PasswordDto passwordDto = new PasswordDto(userId, util.hashPassword(password));
            userRepository.updatePassword(passwordDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 4.1 [profile] 회원 내정보 조회 --- 0
    public ResponseDto<ProfileDto> profile(int userId) {
        ResponseDto<ProfileDto> responseDto = new ResponseDto<>();
        try {
            // 회원 정보 조회
            ProfileDto profileDto = userRepository.selectProfile(userId);

            // null이라면 존재하지 않는 회원 반환
            if (profileDto == null) {
                throw new CustomException(ResponseCode.NON_EXISTENT_USER);
            }

            // 마스킹처리
            profileDto.setEmail(util.emailMasking(profileDto.getEmail()));
            profileDto.setName(util.nameMasking(profileDto.getName()));

            responseDto.setCode(ResponseCode.SUCCESS);
            responseDto.setData(profileDto);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 4.2 [profile/password] 회원 비밀번호 변경 --- 0
    @Transactional
    public ResponseDto<Object> changePassword(int userId, PasswordDto passwordDto) {
        ResponseDto<Object> responseDto = new ResponseDto<>();

        try {
            //db에서 유저 ID로 UserDto 가져오기
            UserDto userDto = userRepository.selectUser(userId);
            //비밀번호 체킹
            if (!util.checkPassword(passwordDto.getOldPassword(), userDto.getUserPwd())) {
                throw new CustomException(ResponseCode.MISMATCHED_USER_INFO);
            }
            // 일치한다면 새 비밀번호 해싱
            passwordDto.setNewPassword(util.hashPassword(passwordDto.getNewPassword()));
            passwordDto.setUserId(userId);
            // 해싱한 비밀번호 DB에 저장
            userRepository.updatePassword(passwordDto);

            // 새 accessToken 발급 후 반환
            UsernamePasswordAuthenticationToken authenticationToken = userDto.ToUser(userDto).toAuthentication();
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            responseDto.setData(tokenDto.getAccessToken());
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.SUCCESS);
        }
        return responseDto;
    }


    // 4.3 [profile/fanid] 회원 팬클럽 인증 번호 변경 --- 0
    @Transactional
    public ResponseDto<ResponseCode> changeFanId(int userId, String fanId) {
        ResponseDto<ResponseCode> responseDto = new ResponseDto<>();
        try {
            //받아온 fanID가 중복되지 않는지 검증
            if (userRepository.checkFanId(fanId) != null) {
                throw new CustomException(ResponseCode.INVALID_FAN_ID);
            }
            //중복되지 않으면 업데이트
            UserDto userDto = new UserDto(userId, fanId);
            responseDto.setCode(ResponseCode.SUCCESS);
            userRepository.updateFanId(userDto);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 4.4 [profile/withdraw] 회원 탈퇴
    public ResponseDto<?> canWithdraw(int userId, String password) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 유저 비밀번호 받아오기
            UserDto userDto = userRepository.selectUser(userId);

            // 비밀번호 체크하기
            if (!util.checkPassword(password, userDto.getUserPwd())) {
                throw new CustomException(ResponseCode.MISMATCHED_USER_INFO);
            }

            //탈퇴 상태 체크하기
            if (userDto.getStatusCd().equals("D")) {
                throw new CustomException(ResponseCode.WITHDRAWN_USER);
            }

            //예매 정보 체크하기
            int orderNum = userRepository.checkOrder(userId);
            if (orderNum != 0) {
                throw new CustomException(ResponseCode.CANNOT_WITHDRAW);
            }
            // 같으면 삭제시킵니다.
            withdraw(userId);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public void withdraw(int userId) {
        userRepository.withdraw(userId);
    }

}
