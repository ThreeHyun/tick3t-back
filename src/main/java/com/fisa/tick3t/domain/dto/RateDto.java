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
public class RateDto {
    String salesRate;
    int totalSeat;
    int soldSeat;
    int RemainSeat = 0;
}
