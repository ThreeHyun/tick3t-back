package com.fisa.tick3t.service;

import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<?> signUp(User user) {
        if (UtilFunction.isValidPassword(user.getUserPwd()) && UtilFunction.isValidEmail(user.getUserEmail())) {
            if (userRepository.checkEmail(user.getUserEmail()) == 0) {
                String password = UtilFunction.hashPassword(user.getUserPwd());
                UserDto userDto = new UserDto(user.getUserName(), user.getUserBirth(), user.getUserEmail(), password, user.getFanCd());
                try {
                    userRepository.insertUser(userDto);
                    return new ResponseDto<>(ResponseCode.SUCCESS, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseDto<>(ResponseCode.FAIL, null);
                }
            } else {
                return new ResponseDto<>(ResponseCode.EMAIL_ALREADY_IN_USE,null);
            }
        } else {
            return new ResponseDto<>(ResponseCode.INVALID_DATA, null);
        }
    }

    public UserDto selectService(int id) {
        return userRepository.selectUser(id);
    }
}
