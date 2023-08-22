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
        gen.writeStringField("code", value.getCode()); // "code" : "0000"
        gen.writeStringField("message", value.getMessage()); // "message" : "success"

        if (value.getData() == null) {
//            null일 경우에도 안전히 {}로 내려가도록..
            gen.writeFieldName("data"); //"data" :
            gen.writeStartObject(); // {
            gen.writeEndObject(); // }
        } else {
            Object dataContent = value.getData();
            if (dataContent instanceof GenericWrapper) {
                // data에 GenericWrapper의 dataMap 내용을 넣습니다.
                gen.writeObjectField("data", ((GenericWrapper<?>) dataContent).getDataMap());
            } else {
                // data에 dataContent를 직접 추가합니다.
                gen.writeObjectField("data", dataContent);
            }
        }

        gen.writeEndObject();
    }
}


