package com.fisa.tick3t.service;

import com.fisa.tick3t.domain.dto.ConcertDto;
import com.fisa.tick3t.domain.dto.PageInfo;
import com.fisa.tick3t.domain.dto.SeatDto;
import com.fisa.tick3t.global.CustomException;
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
    public ResponseDto<?> selectConcerts(PageInfo pageInfo){
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 콘서트 수 조회
            pageInfo.setTotalElement(concertRepository.selectConcertsNum());
            // 콘서트 정보 조회
            List<ConcertDto> concerts = concertRepository.selectConcerts(pageInfo);
            //ConcertPageDto concertPage = new ConcertPageDto(pageInfo, concerts);
            responseDto.setData(concerts);
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
            // 콘서트 id로 콘서트 조회
            ConcertDto concertDto = concertRepository.selectConcert(ID);

            // 조회된 값이 없다면 존재하지 않는 공연 에러코드 반환
            if(concertDto == null){
                throw new CustomException(ResponseCode.NON_EXISTENT_CONCERT);
            }

            // 콘서트가 존재한다면 좌석정보 조회
            ArrayList<SeatDto> seatDto = concertRepository.selectSeat(ID);

            // 좌석 정보를 담은 후 반환
            concertDto.setSeats(seatDto);
            responseDto.setData(concertDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode());
        } catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }
}
