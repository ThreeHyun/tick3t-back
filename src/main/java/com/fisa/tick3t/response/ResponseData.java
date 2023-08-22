package com.fisa.tick3t.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseData<T> {
    private PageInfo pageInfo;
    private T data;

    // list로 내려줄 때 한번 더 감싸는 용도로 사용합니다.
    // pageInfo와 list 형태의 data를 ResponseData에 담고 -> GenericWrapper로 감싸서 반환합니다.
    /*
         PageInfo pageInfo = new PageInfo(1, 3, 5, 15);
         ArrayList<User> users = new ArrayList<>(); 안에 데이터 넣기
         ResponseData responseData = new ResponseData<>(pageInfo, T data);
         return new ResponseDto<>(ResponseCode.SUCCESS, new GenericWrapper<>(responseData));
     */

}