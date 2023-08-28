package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.ConcertDto;
import com.fisa.tick3t.domain.dto.ConcertPageDto;
import com.fisa.tick3t.domain.dto.PageInfo;
import com.fisa.tick3t.domain.dto.SeatDto;
import com.fisa.tick3t.repository.ConcertRepository;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;


    // 3.1 [concert] 공연 목록 조회
    public ResponseDto<?> selectConcerts(int page){
        ResponseDto<Object> responseDto = new ResponseDto<>();
        PageInfo pageInfo = new PageInfo(page, 3);
        try {
            pageInfo.setTotalElement(concertRepository.selectConcertsNum());
            List<ConcertDto> concertDtos = concertRepository.selectConcerts(pageInfo);
            ConcertPageDto concertPage = new ConcertPageDto(pageInfo, concertDtos);
            responseDto.setData(concertPage);
            responseDto.setCode(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;

    }

    // 3.2 [concert/{id}] 공연 상세 조회
    public ResponseDto<ConcertDto> selectConcert(int ID) {
        ResponseDto<ConcertDto> responseDto = new ResponseDto<>();
        try {
            ConcertDto concertDto = concertRepository.selectConcert(ID);
            if(concertDto == null){
                responseDto.setCode(ResponseCode.NON_EXISTENT_CONCERT);
                return responseDto;
            }
            ArrayList<SeatDto> seatDto = concertRepository.selectSeat(ID);
            concertDto.setSeats(seatDto);
            responseDto.setCode(ResponseCode.SUCCESS);
            responseDto.setData(concertDto);
        }catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }
}
