package com.fisa.tick3t.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogDto {
    int rowNum;
    int logId;
    String accessIp;
    String createDate;
    String statusCode; //0: 성공 1: 실패 2:로그아웃
}
