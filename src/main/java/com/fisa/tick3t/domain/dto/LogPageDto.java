package com.fisa.tick3t.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LogPageDto {
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalElement;
    private List<LogDto> logs;

    public LogPageDto(PageInfo pageInfo, List<LogDto> logDtos) {
        this.pageNo = pageInfo.getPageNo();
        this.pageSize = pageInfo.getPageSize();
        this.totalElement = pageInfo.getTotalElement();
        if(totalElement % pageSize == 0){
            this.totalPage = totalElement / pageSize;
        } else{
            this.totalPage = totalElement / pageSize + 1;
        }
        this.logs = logDtos;
    }
}
