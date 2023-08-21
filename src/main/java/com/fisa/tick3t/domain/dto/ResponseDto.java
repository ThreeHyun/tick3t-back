package com.fisa.tick3t.domain.dto;

import com.fisa.tick3t.global.ResponseCode;
import lombok.*;

@Getter
@AllArgsConstructor
public class ResponseDto<D> {
    private final String code;
    private final String message;
    private final D data;

    public ResponseDto(ResponseCode responseCode, D data){
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

}