package com.fisa.tick3t.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPageDto {

    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalElement;
    private List<OrderDto> orders;

    public OrderPageDto(PageInfo pageInfo, List<OrderDto> orders) {
        this.pageNo = pageInfo.getPageNo();
        this.pageSize = pageInfo.getPageSize();
        this.totalElement = pageInfo.getTotalElement();
        this.totalPage = totalElement / pageSize + 1;
        this.orders = orders;
    }
}
