package com.fisa.tick3t.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@JsonSerialize(using = ResponseDtoSerializer.class)
public class ResponseDto<GenericWrapper> {
    private ResponseCode code;
    @Nullable
    private GenericWrapper data;

    public ResponseDto(ResponseCode responseCode, GenericWrapper data){
        this.code = responseCode;
        this.data = data;
    }

    public ResponseDto(ResponseCode responseCode) {
        this.code = responseCode;
    }
}