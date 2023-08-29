package com.fisa.tick3t.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.global.UtilFunction;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    int rowNum;
    int userId;
    String name;
    String birth;
    String email;
    String userPwd;
    String fanId;
    String fanCd;
    String role;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;

    String statusCd;

    public UserDto(User user, String hashPw){
        this.name = user.getName();
        this.birth = user.getBirth();
        this.email = user.getEmail();
        this.userPwd = hashPw;
        this.fanCd = user.getFanCd();
    }

    public UserDto(int userId, String fanId){
        this.userId = userId;
        this.fanId = fanId;
    }

    public UserDto(int userId, String email, String password){
        UtilFunction utilFunction = new UtilFunction();
        this.userId = userId;
        this.email = email;
        this.userPwd = utilFunction.hashPassword(password);
        this.role = "ROLE_USER";
    }

    public UserDto (String email, String name, String birth, String fanId){
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.fanId = fanId;
    }


    public User ToUser(UserDto userDto){
        return new User(userDto.getUserId(),
                        userDto.getEmail(),
                        userDto.getUserPwd(),
                        userDto.getRole());
    }
}
