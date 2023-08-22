package com.fisa.tick3t.repository;

import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.domain.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    // 1-1. 사용자 회원가입
    int insertUser(User user);

    // 1-2. 사용자 하나 조회
    UserDto selectUser(int userId);
    //UserDto selectUser(int userId);
}
