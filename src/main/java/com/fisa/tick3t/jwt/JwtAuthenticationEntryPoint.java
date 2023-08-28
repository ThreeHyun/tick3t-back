package com.fisa.tick3t.jwt;

import org.springframework.stereotype.Component;

import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        Object invalidJwt = request.getAttribute("INVALID_JWT");

        if (invalidJwt != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(400);

            ResponseDto<Object> responseDto = new ResponseDto<>(ResponseCode.INVALID_TOKEN);

            response.getWriter().write(responseDto.toString());
        }

    }
}