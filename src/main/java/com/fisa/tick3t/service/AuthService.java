package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.LogDto;
import com.fisa.tick3t.domain.dto.TokenDto;
import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.global.StatusCode;
import com.fisa.tick3t.jwt.JwtUserDetails;
import com.fisa.tick3t.jwt.TokenProvider;
import com.fisa.tick3t.repository.LogRepository;
import com.fisa.tick3t.repository.UserRepository;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final LogRepository logRepository;

    private final UserRepository userRepository;


    public ResponseDto<?> login(UserDto userDto, String ip) throws CustomException {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        LogDto logDto = new LogDto(ip);
        try{
            // 유저 email과 password 받아서 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = userDto.ToUser(userDto).toAuthentication();

            // UsernamePasswordAuthenticationToken 토큰으로 인증 객체 생성
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // authentication을 사용해서 jwtToken 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // 로그인한 유저 id와 ip, 상태 정보를 로그 DB에 저장
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            logDto.setRowNum(userId);
            logDto.setStatusCode(StatusCode.LOGIN.getCode());
            logRepository.insertLog(logDto);

            // jwt 토큰을 반환
            responseDto.setData(tokenDto);
            responseDto.setCode(ResponseCode.SUCCESS);

            //userEmail과 password가 일치하지 않으면 발생하는 에러
        }
        catch(AuthenticationException e) {
            Integer userId = userRepository.checkEmail(userDto.getEmail());
            if(userId == null) {
                throw new CustomException(ResponseCode.UNKNOWN_EMAIL);
            }
            String statusCd = userRepository.checkStatusCode(userId);
            if(statusCd.equals("D")){
                throw new CustomException(ResponseCode.WITHDRAWN_USER);
            }
            logDto.setRowNum(userId);
            logDto.setStatusCode(StatusCode.LOGIN_FAILURE.getCode());
            logRepository.insertLog(logDto);
            throw new CustomException(ResponseCode.MISMATCHED_USER_INFO);
        } catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

}

