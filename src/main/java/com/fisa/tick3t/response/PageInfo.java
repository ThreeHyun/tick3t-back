package com.fisa.tick3t.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PageInfo {
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalElement;

    //page 정보 담기
}