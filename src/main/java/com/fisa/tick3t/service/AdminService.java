package com.fisa.tick3t.service;


import com.fisa.tick3t.domain.dto.*;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.repository.ConcertRepository;
import com.fisa.tick3t.repository.LogRepository;
import com.fisa.tick3t.repository.UserRepository;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fisa.tick3t.global.StatusCode.codeToStatusDesc;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final ConcertRepository concertRepository;

    private final UtilFunction util;

    // 5.1 [admin/user] 사용자 조회
    public ResponseDto<?> selectUsers(QueryStringDto queryStringDto, PageInfo pageInfo) throws CustomException {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 검색된 유저수
            pageInfo.setTotalElement(userRepository.selectUserNum(queryStringDto));
            // 유저 검색
            List<UserDto> userList = userRepository.selectUsers(queryStringDto);
            if (userList.isEmpty()) {
                throw new CustomException(ResponseCode.NO_DATA);
                // todo: 꼭 필요하지 않은 것 같음. 빈 데이터만 내려줘도 될 것 같은데 이걸 실패라고 쳐야하나
            }
            // 마스킹처리
            for (UserDto user : userList) {
                util.maskingUserInfo(user);
            }
            // 반환할 객체 생성, 데이터 담기
            UserPageDto userPageDto = new UserPageDto(pageInfo, userList);
            responseDto.setData(userPageDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            throw new CustomException(ResponseCode.NO_DATA);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 5.2 [admin/user/{ID}] 사용자 상세조회
    public ResponseDto<?> selectUserById(int userId) throws CustomException {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 유저 조회
            UserDto userDto = userRepository.selectUser(userId);
            // 조회된 유저가 없을 경우 존재하지 않는 유저 에러 반환
            if (userDto == null) {
                throw new CustomException(ResponseCode.NON_EXISTENT_USER);
            }
            // 마스킹처리
            util.maskingUserInfo(userDto);
            responseDto.setData(userDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            throw new CustomException(ResponseCode.NON_EXISTENT_USER);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 5.3 [admin/log/{ID}] 사용자 로그 조회
    public ResponseDto<?> selectLog(int userId, PageInfo pageInfo) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 검색된 로그 수
            pageInfo.setTotalElement(logRepository.selectLogNum(userId));
            // 검색된 로그
            List<LogDto> logs = logRepository.selectLog(pageInfo, userId);
            // 0: 로그인 1: 실패 2: 로그아웃 으로 변환
            for (LogDto log : logs) {
                log.setStatusCode(codeToStatusDesc(log.getStatusCode()));
            }
            // 반환할 객체 생성, 데이터 담기
            LogPageDto logPageDto = new LogPageDto(pageInfo, logs);
            responseDto.setData(logPageDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }


    // 5.4 [/admin/fan] 대시보드 팬덤 Select Box
    public ResponseDto<?> dashboardFan() {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            //전체 Fancd를 조회
            responseDto.setData(userRepository.selectFanCd());
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 5.4 [/admin/fan/{fanCd}] 대시보드 팬덤 위젯
    public ResponseDto<?> dashboardFanId(String fanCd) throws CustomException {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // joinUser, WithdrawUser, weekUser 받아오기
            FanCountDto fanCountDto = userRepository.selectFanSum(fanCd);

            // 가입한 유저가 없다면 없는 팬덤 에러 반환
            if (fanCountDto.getJoinUser() == 0) {
                throw new CustomException(ResponseCode.NON_EXISTENT_FANDOM);
            }

            // nowUser 설정 및 반환
            fanCountDto.setNowUser(fanCountDto.getJoinUser() - fanCountDto.getWithdrawUser());
            responseDto.setData(fanCountDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            throw new CustomException(ResponseCode.NON_EXISTENT_FANDOM);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 5-5 [/admin/ticket] 대시보드 콘서트 Select Box
    public ResponseDto<?> dashboardConcert() {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            //1주일 내에 진행하는 콘서트 조회
            responseDto.setData(concertRepository.selectConcertTitle());
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }


    // 5-6 [/admin/ticket/{ID}] 대시보드 판매율 위젯
    public ResponseDto<?> dashboardConcertId(int concertId) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 총 좌석수, 팔린 좌석수, 잔여 좌석 수, 판매율을 받아옵니다.
            RateDto rateDto = concertRepository.selectConcertRate(concertId);

            // 총 좌석수가 0이라면 존재하지 않는 공연입니다.
            if (rateDto.getTotalSeat() == 0) {
                throw new CustomException(ResponseCode.NON_EXISTENT_CONCERT);
            }
            //판매율을 계산하고 데이터를 반환합니다.
            util.calculateSalesRate(rateDto);
            responseDto.setData(rateDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }
}
