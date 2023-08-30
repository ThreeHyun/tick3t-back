package com.fisa.tick3t.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo {
    private Integer userId;
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