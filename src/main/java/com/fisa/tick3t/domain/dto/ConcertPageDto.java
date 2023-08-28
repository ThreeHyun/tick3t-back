package com.fisa.tick3t.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConcertPageDto {
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalElement;
    private List<ConcertDto> concerts;

    public ConcertPageDto(PageInfo pageInfo, List<ConcertDto> concertDtos) {
        this.pageNo = pageInfo.getPageNo();
        this.pageSize = pageInfo.getPageSize();
        this.totalElement = pageInfo.getTotalElement();
        this.totalPage = totalElement / pageSize + 1;
        this.concerts = concertDtos;
    }
}
