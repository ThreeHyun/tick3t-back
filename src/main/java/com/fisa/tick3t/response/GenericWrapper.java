package com.fisa.tick3t.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class GenericWrapper<D> {
    private Map<String, D> dataMap = new HashMap<>();

    public GenericWrapper(String key, D value) {
        dataMap.put(key, value);
    }

    public Map<String, D> getDataMap() {
        return dataMap;
    }

    // ex ) return new ResponseDto<>(ResponseCode.INVALID_DATA, new GenericWrapper<>("userList",users));
}