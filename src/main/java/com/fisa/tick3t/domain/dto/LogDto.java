package com.fisa.tick3t.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogDto {
    int rowNum;
    int logId;
    String accessIp;
    String statusCode; //0: 성공 1: 실패 2:로그아웃

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime logCreateDate;

    public LogDto(int userId, String ip, String statusCode) {
        this.accessIp = ip;
        this.statusCode = statusCode;
        this.rowNum = userId;
    }

}
