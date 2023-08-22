package com.fisa.tick3t.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Setter
@Getter
@ToString
public class UserDto {

    int userId;
    String userName;
    String userBirth;
    String userEmail;
    String userPwd;
    String fanId;
    String fanCd;
    Date createDtm;
    char userStatusCd;

    // [signup]
    public UserDto(String userName, String userBirth, String userEmail, String userPwd, String fanCd){
        this.userName = userName;
        this.userBirth = userBirth;
        this.userEmail = userEmail;
        this.userPwd = userPwd;
        this.fanCd = fanCd;
    }
}
