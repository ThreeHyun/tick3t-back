package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.TokenDto;
import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.jwt.TokenProvider;
import com.fisa.tick3t.response.GenericWrapper;
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


    public ResponseDto<?> login(UserDto userDto) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try{
            UsernamePasswordAuthenticationToken authenticationToken = userDto.ToUser(userDto).toAuthentication();
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            GenericWrapper<String> genericWrapper = new GenericWrapper<>("Authorization", tokenDto.authorization(tokenDto));
            responseDto.setData(genericWrapper);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch(AuthenticationException e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.MISMATCHED_USER_INFO);
        } catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

}

