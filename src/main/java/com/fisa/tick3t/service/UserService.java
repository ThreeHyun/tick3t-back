package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public int signUpService(User user){
        return userRepository.insertUser(user);
    }

    public UserDto selectService(int id) {
        return userRepository.selectUser(id);
    }
}
