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
public class ReservationDto {
    int concertId;
    int gradeId;
    int userId;
    int price;
    int totalSeat;
    Integer dept_code;
    Integer canReserve;
}
