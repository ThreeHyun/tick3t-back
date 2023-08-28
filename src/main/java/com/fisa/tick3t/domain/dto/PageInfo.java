package com.fisa.tick3t.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PageInfo {
    private int pageNo;
    private int pageSize;
    private int offset;
    private int totalPage;
    private int totalElement;
    //page 정보 담기

    public PageInfo(int pageNo, int pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.offset = pageSize * (pageNo -1);
    }
}