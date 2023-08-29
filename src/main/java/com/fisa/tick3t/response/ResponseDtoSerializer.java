package com.fisa.tick3t.response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

// responseDto를 직렬화해서 보내기 위해서 필요합니다.
public class ResponseDtoSerializer extends JsonSerializer<ResponseDto<?>> {
    @Override
    public void serialize(ResponseDto<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject(); //{
        gen.writeStringField("resultCode", value.getCode().getResultCode()); // "code" : "0000"
        gen.writeStringField("message", value.getCode().getMessage()); // "message" : "success"

        if (value.getData() == null) {
            gen.writeFieldName("data"); //"data" :
            gen.writeStartObject(); // {
            gen.writeEndObject(); // }
        } else {
            gen.writeObjectField("data", value.getData());
        }

        gen.writeEndObject();
    }
}


