package com.fisa.tick3t.repository;

import com.fisa.tick3t.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    // 1-1. 사용자 회원가입
    void insertUser(UserDto userDto);

    // 1-2. 사용자 하나 조회
    UserDto selectUser(int userId);

    int checkEmail(String userEmail);
    //UserDto selectUser(int userId);
}
