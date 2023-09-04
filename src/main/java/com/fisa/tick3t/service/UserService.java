package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.PasswordDto;
import com.fisa.tick3t.domain.dto.ProfileDto;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UtilFunction util;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    // 2.1 [signup] 회원가입
    @Transactional
    public ResponseDto<ResponseCode> signUp(User user) {
        ResponseDto<ResponseCode> responseDto = new ResponseDto<>();
        String userPwd = user.getUserPwd();
        String userEmail = user.getEmail();
        // 파라미터 완전성 검사
        if (userPwd == null || userEmail == null || user.getName() == null) {
            responseDto.setCode(ResponseCode.MISSING_OR_INVALID_REQUEST);
            return responseDto;
        }
        // 파라미터 유효성 검사
        if (!util.isValidPassword(userPwd) || !util.isValidEmail(userEmail) || user.getName().length() > 5) {
            responseDto.setCode(ResponseCode.INVALID_DATA);
            return responseDto;
        }

        // 중복된 이메일일 경우
        if (userRepository.checkEmail(userEmail) != null) {
            responseDto.setCode(ResponseCode.EMAIL_ALREADY_IN_USE);
            return responseDto;
        }

        // password를 hashing하고 UserDto에 저장
        UserDto userDto = new UserDto(user, util.hashPassword(userPwd));

        // userDb에 Insert
        try {
            userRepository.insertUser(userDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;

    }


    // 2.4 [reissue] 비밀번호 재발급
    @Transactional
    public ResponseDto<ResponseCode> resetPassword(User user) {
        ResponseDto<ResponseCode> responseDto = new ResponseDto<>();
        String userBirth = user.getBirth();
        String userName = user.getName();
        String userEmail = user.getEmail();

        // 파라미터 완전성 검사
        if (userEmail == null || userName == null || userBirth == null) {
            responseDto.setCode(ResponseCode.MISSING_OR_INVALID_REQUEST);
            return responseDto;
        }
        try {
            //파라미터 유효성 검사
            Integer userId = userRepository.checkUser(user);

            // ID를 받아오지 못했다면 일치하지 않는 유저 정보 반환
            if (userId == null) {
                responseDto.setCode(ResponseCode.MISMATCHED_USER_INFO);
                return responseDto;
            }

            // 새 패스워드 생성
            String password = util.generatePassword();

            // 메일링처리
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("[TICK3T] | 임시비밀번호 안내");
            String body = "회원님의 임시 비밀번호는 " + password + " 입니다.";
            mimeMessageHelper.setText(body);
            javaMailSender.send(mimeMessage);

            // 패스워드 해싱 및 업데이트
            PasswordDto passwordDto = new PasswordDto(userId, util.hashPassword(password));
            userRepository.updatePassword(passwordDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (SendFailedException e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.UNKNOWN_EMAIL);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 4.1 [profile] 회원 내정보 조회
    public ResponseDto<ProfileDto> profile(int userId) {
        ResponseDto<ProfileDto> responseDto = new ResponseDto<>();
        try {
            ProfileDto profileDto = userRepository.selectProfile(userId);
            if (profileDto == null) {
                responseDto.setCode(ResponseCode.NON_EXISTENT_USER);
                return responseDto;
            }
            profileDto.setEmail(util.emailMasking(profileDto.getEmail()));
            profileDto.setName(util.nameMasking(profileDto.getName()));
            responseDto.setCode(ResponseCode.SUCCESS);
            responseDto.setData(profileDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 4.2 [profile/password] 회원 비밀번호 변경
    @Transactional
    public ResponseDto<ResponseCode> changePassword(int userId, PasswordDto passwordDto) {
        ResponseDto<ResponseCode> responseDto = new ResponseDto<>();
        String newPwd = passwordDto.getNewPassword();
        String oldPwd = passwordDto.getOldPassword();
        // // 파라미터 완전성, 유효성 검사
        if (newPwd.equals(oldPwd) || !newPwd.equals(passwordDto.getNewPasswordCheck()) || !util.isValidPassword(newPwd)) {
            responseDto.setCode(ResponseCode.INVALID_DATA);
            return responseDto;
        }
        try {
            //db에서 유저 ID로 UserDto 가져오기
            UserDto userDto = userRepository.selectUser(userId);
            //비밀번호 체킹
            if (!util.checkPassword(oldPwd, userDto.getUserPwd())) {
                responseDto.setCode(ResponseCode.MISMATCHED_USER_INFO);
                return responseDto;
            }

            // 일치한다면 새 비밀번호 해싱
            passwordDto.setNewPassword(util.hashPassword(newPwd));
            passwordDto.setUserId(userId);
            // 해싱한 비밀번호 DB에 저장
            userRepository.updatePassword(passwordDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }


    // 4.3 [profile/fanid] 회원 팬클럽 인증 번호 변경
    @Transactional
    public ResponseDto<ResponseCode> changeFanId(int userId, String fanId) {
        ResponseDto<ResponseCode> responseDto = new ResponseDto<>();

        // 파라미터 완전성, 유효성 검사
        if (fanId.length() != 8) {
            responseDto.setCode(ResponseCode.INVALID_DATA);
            return responseDto;
        }

        try {
            //받아온 fanID가 중복되지 않는지 검증
            if (userRepository.checkFanId(fanId) != null) {
                responseDto.setCode(ResponseCode.INVALID_FAN_ID);
                return responseDto;
            }
            //중복되지 않으면 업데이트
            UserDto userDto = new UserDto(userId, fanId);
            responseDto.setCode(ResponseCode.SUCCESS);
            userRepository.updateFanId(userDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }


    @Transactional
    public ResponseDto<?> withdraw(int userId, String password) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 유저 비밀번호 받아오기
            UserDto userDto = userRepository.selectUser(userId);

            // 비밀번호 체크하기
            if (!util.checkPassword(password, userDto.getUserPwd()) ) {
                responseDto.setCode(ResponseCode.MISMATCHED_USER_INFO);
                return responseDto;
                // todo: throw new customException(코드); + customExceptionHandler 만들기
            }

            //탈퇴 상태 체크하기
            if(userDto.getStatusCd().equals("D")){
                responseDto.setCode(ResponseCode.WITHDRAWN_USER);
                return responseDto;
            }

            //예매 정보 체크하기
            int orderNum = userRepository.checkOrder(userId);
            if(orderNum != 0){
                responseDto.setCode(ResponseCode.CANNOT_WITHDRAW);
                return responseDto;
            }
            // 같으면 삭제시킵니다.
            userRepository.withdraw(userId);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

}
