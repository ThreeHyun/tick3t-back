package com.fisa.tick3t.controller;


import com.fisa.tick3t.domain.dto.PasswordDto;
import com.fisa.tick3t.domain.dto.ProfileDto;
import com.fisa.tick3t.jwt.JwtUserDetails;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") // O
    public ResponseDto<?> signup(@RequestBody User user) {
        return userService.signUp(user);
    }

    @PostMapping("/reset-password") // O 메일링 처리하기
    public ResponseDto<?> reissue(@RequestBody User user) {
        return userService.resetPassword(user);
    }

    @GetMapping("/profile") // O
    public ResponseDto<?> profile(Authentication authentication) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            return userService.profile(userId);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }
    }


    @PostMapping("/profile/password") // O
    public ResponseDto<?> changePwd(Authentication authentication, @RequestBody PasswordDto passwordDto) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            return userService.changePassword(userId, passwordDto);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }
    }

    @PostMapping("/profile/fanId") // O
    public ResponseDto<?> changeFanId(Authentication authentication, @RequestBody ProfileDto profileDto) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            String fanId = profileDto.getFanId();
            if (!(fanId.length() == 8)) {
                log.info("fanID : " + fanId);
                return new ResponseDto<>(ResponseCode.INVALID_DATA);
            }
            return userService.changeFanId(userId, fanId);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }

    }

    @PostMapping("/profile/withdraw") // 예매내역 구현 후 예매내역 존재하는지 확인 후에 탈퇴하기
    public ResponseDto<?> withdraw(Authentication authentication, @RequestBody PasswordDto passwordDto) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            return userService.withdraw(userId, passwordDto.getPassword());
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }
    }


}
