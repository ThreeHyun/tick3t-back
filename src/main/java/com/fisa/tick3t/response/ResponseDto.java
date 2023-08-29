package com.fisa.tick3t.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@JsonSerialize(using = ResponseDtoSerializer.class)
public class ResponseDto<D> {
    private ResponseCode code;
    @Nullable
    private D data;

    public ResponseDto(ResponseCode responseCode, D data){
        this.code = responseCode;
        this.data = data;
    }

    public ResponseDto(ResponseCode responseCode) {
        this.code = responseCode;
    }
}