package com.fisa.tick3t.repository;

import com.fisa.tick3t.domain.dto.ConcertDto;
import com.fisa.tick3t.domain.dto.PageInfo;
import com.fisa.tick3t.domain.dto.SeatDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ConcertRepository {
    // 3.1 [concert] 공연 목록 조회
    ArrayList<ConcertDto> selectConcerts(PageInfo pageInfo);

    int selectConcertsNum();

    // 3.2 [concert/{ID}] 공연 상세 조회
    ConcertDto selectConcert(int concertId);

    // 5.5 [admin/ticket] 공연 title 조회
    ArrayList<ConcertDto> selectConcertTitle();

    ArrayList<SeatDto> selectSeat(int id);
}
