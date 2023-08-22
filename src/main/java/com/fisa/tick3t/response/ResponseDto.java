package com.fisa.tick3t.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
@JsonSerialize(using = ResponseDtoSerializer.class)
public class ResponseDto<GenericWrapper> {
    private final String code;
    private final String message;
    @Nullable
    private final GenericWrapper data;

    public ResponseDto(ResponseCode responseCode, GenericWrapper data){
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

}