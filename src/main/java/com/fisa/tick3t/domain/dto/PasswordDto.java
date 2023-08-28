package com.fisa.tick3t.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordDto {
    String password;
    String oldPassword;
    String newPassword;
    String newPasswordCheck;
    int userId;

    public PasswordDto(int userId, String newPassword){
        this.userId = userId;
        this.newPassword = newPassword;
    }
}
