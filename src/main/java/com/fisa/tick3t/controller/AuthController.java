package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/auth")
public class AuthController {
    public final AuthService authService;

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody UserDto userDto) {
        return authService.login(userDto);
    }

}