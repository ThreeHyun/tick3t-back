package com.fisa.tick3t.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

@Getter
@ToString //for log
@JsonIgnoreProperties
public class User {

    private final int userId;
    private final String name;
    private final String birth;
    private final String email;
    private final String userPwd;
    private final String fanId;
    private final String fanCd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createDate;
    private final String StatusCd;
    private final String role;

    @JsonCreator
    public User(@JsonProperty("userId") int userId,
                @JsonProperty("name") String name,
                @JsonProperty("birth") String birth,
                @JsonProperty("email") String email,
                @JsonProperty("userPwd") String userPwd,
                @JsonProperty("fanId") String fanId,
                @JsonProperty("fanCd") String fanCd,
                @JsonProperty("createDate") LocalDateTime createDate,
                @JsonProperty("StatusCd") String StatusCd,
                @JsonProperty("role") String role) {
        this.userId = userId;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.userPwd = userPwd;
        this.fanId = fanId;
        this.fanCd = fanCd;
        this.createDate = createDate;
        this.StatusCd = StatusCd;
        this.role = role;
    }

    public User(int userId, String email, String userPwd, String role){
        this.userId = userId;
        this.email = email;
        this.userPwd = userPwd;
        this.role = role;
        this.name = null;
        this.birth = null;
        this.fanId = null;
        this.fanCd = null;
        this.createDate = null;
        this.StatusCd = "E";
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, userPwd);
    }
}

