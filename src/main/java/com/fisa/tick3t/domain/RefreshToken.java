package com.fisa.tick3t.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RefreshToken {
    private String UserId;
    //memberid가 들어가는 곳

    private String value;
    //refresh token string이 들어갑니다.
    //db에 넣을 때는 생성/ 수정시간 column을 넣어야한다!

    @Builder
    public RefreshToken(String UserId, String value) {
        this.UserId = UserId;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
