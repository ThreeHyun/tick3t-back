package com.fisa.tick3t.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    int ticketId;
    String title;
    Date datetime;
    String location;
    String payState;
    String seat;
    int price;
    Date cancelDate;
}
