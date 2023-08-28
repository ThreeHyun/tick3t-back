package com.fisa.tick3t.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPageDto {
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalElement;
    private List<UserDto> users;

    public UserPageDto(PageInfo pageInfo, List<UserDto> users) {
        this.pageNo = pageInfo.getPageNo();
        this.pageSize = pageInfo.getPageSize();
        this.totalElement = pageInfo.getTotalElement();
        this.totalPage = totalElement / pageSize + 1;
        this.users = users;
    }
}
