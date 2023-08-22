package com.fisa.tick3t.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDto {
    String userName;
    String userEmail;
    String userPwd;
    // Userdto.setName(StringUtil.masking(vo.getName()))
}
