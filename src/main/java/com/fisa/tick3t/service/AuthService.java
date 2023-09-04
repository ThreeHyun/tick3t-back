package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.LogDto;
import com.fisa.tick3t.domain.dto.TokenDto;
import com.fisa.tick3t.domain.dto.UserDto;
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


    public ResponseDto<?> login(UserDto userDto, String ip) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try{
            // 유저 email과 password 받아서 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = userDto.ToUser(userDto).toAuthentication();

            // UsernamePasswordAuthenticationToken 토큰으로 인증 객체 생성
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // authentication을 사용해서 jwtToken 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // 로그인한 유저 id와 ip, 상태 정보를 로그 DB에 저장
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            LogDto logDto = new LogDto(userId, ip,"0");
            logRepository.insertLog(logDto);

            // jwt 토큰을 반환
            responseDto.setData(tokenDto);
            responseDto.setCode(ResponseCode.SUCCESS);

            //userEmail과 password가 일치하지 않으면 발생하는 에러
        } catch(AuthenticationException e) {
            log.error(e.getMessage());
            try {
                // 로그인 실패한  유저 id와 ip, 상태 정보를 로그 DB에 저장
                int userId = userRepository.checkEmail(userDto.getEmail());
                LogDto logDto = new LogDto(userId, ip,"1");
                logRepository.insertLog(logDto);
                responseDto.setCode(ResponseCode.MISMATCHED_USER_INFO);

                // 로그인 시도한 Email이 DB에 존재하지 않을 경우 발생하는 에러
            }catch (Exception e2){
                log.error(e2.getMessage());
                responseDto.setCode(ResponseCode.UNKNOWN_EMAIL);
            }

            // 알 수 없는 에러
        } catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

}

