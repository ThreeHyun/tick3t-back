package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.jwt.TokenProvider;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.service.AuthService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    public final AuthService authService;
    public final TokenProvider tokenProvider;

    public final UtilFunction util;

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody UserDto userDto, HttpServletRequest request) throws CustomException {
        return authService.login(userDto, request.getRemoteAddr());
    }

    @GetMapping("/auth")
    public ResponseDto<?> login(HttpServletRequest request) throws CustomException {
        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();
        String accessToken = request.getHeader("Authorization");
        accessToken = util.isValidParam(accessToken);
        try {
            Map<String, String> map = new HashMap<>();
            // 키-값 쌍을 추가
            map.put("role", tokenProvider.auth(accessToken.substring(7)));
            responseDto.setData(map);
            responseDto.setCode(ResponseCode.SUCCESS);
        }catch (MalformedJwtException e){
            log.error(e.getMessage());
            throw new CustomException(ResponseCode.INVALID_TOKEN);
        }catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

}