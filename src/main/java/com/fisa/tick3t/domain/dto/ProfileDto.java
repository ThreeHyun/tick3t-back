package com.fisa.tick3t.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileDto {

    String email;
    String name;
    String birth;
    String fanId;

    public ProfileDto(String email, String name, String birth, String fanId){
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.fanId = fanId;
    }
}
