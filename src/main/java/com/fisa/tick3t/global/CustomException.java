package com.fisa.tick3t.global;

import com.fisa.tick3t.response.ResponseCode;


public class CustomException extends Exception{
    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode){
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode(){
        return responseCode;
    }
}
