package com.fisa.tick3t.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FanCountDto {
    int joinUser;
    int withdrawUser;
    int nowUser;
    int weekUser;
}
