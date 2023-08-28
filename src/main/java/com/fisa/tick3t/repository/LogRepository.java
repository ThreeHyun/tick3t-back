package com.fisa.tick3t.repository;

import com.fisa.tick3t.domain.dto.LogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface LogRepository {

    // 5.3 [admin/log/{ID}] 사용자 로그 조회
    ArrayList<LogDto> selectLog(int id);

    int selectLogNum(int id);
}
