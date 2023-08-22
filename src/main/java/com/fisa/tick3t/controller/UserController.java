package com.fisa.tick3t.controller;


import com.fisa.tick3t.domain.dto.ResponseDto;
import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.global.ResponseCode;
import com.fisa.tick3t.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody User user) {
        System.out.println(user.toString());
        try {
            int result = userService.signUpService(user);
            if (result == 1) {
                return new ResponseDto<>(ResponseCode.SUCCESS, user.getUserEmail());
            } else {
                return new ResponseDto<>(ResponseCode.FAIL, null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseDto<>(ResponseCode.MISSING_OR_INVALID_BODY, null);
        }
    }

    @GetMapping("/select/{id}")
    public ResponseDto<?> selectUser(@PathVariable String id) {
        System.out.println(id);
        try {
            UserDto userDto = userService.selectService(Integer.parseInt(id));
            System.out.println(userDto.getClass());
            if (userDto.getUserName() != null) {
                return new ResponseDto<>(ResponseCode.SUCCESS, userDto );
            } else {
                return new ResponseDto<>(ResponseCode.FAIL, null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseDto<>(ResponseCode.MISSING_OR_INVALID_BODY, null);
        }
    }


}
