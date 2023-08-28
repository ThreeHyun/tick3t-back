package com.fisa.tick3t.domain.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public String authorization(TokenDto tokenDto){
        return tokenDto.grantType + " " + tokenDto.accessToken;
    }
}
