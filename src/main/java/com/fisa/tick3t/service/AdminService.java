package com.fisa.tick3t.service;


import com.fisa.tick3t.domain.dto.*;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.repository.ConcertRepository;
import com.fisa.tick3t.repository.LogRepository;
import com.fisa.tick3t.repository.UserRepository;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final ConcertRepository concertRepository;

    private final UtilFunction util;

    // 5.1 [admin/user] 사용자 조회
    public ResponseDto<?> selectUsers(QueryStringDto queryStringDto) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        PageInfo pageInfo = new PageInfo(queryStringDto.getPage(), 20);
        queryStringDto.setOffset((queryStringDto.getPage()-1) * 20 );
        try {
            pageInfo.setTotalElement(userRepository.selectUserNum());
            ArrayList<UserDto> userList = userRepository.selectUsers(queryStringDto);
            for(UserDto user: userList){
                user.setName(util.nameMasking(user.getName()));
                user.setEmail(util.emailMasking(user.getEmail()));
                if(user.getStatusCd().equals("E")){
                    user.setStatusCd("활성화");
                }else{
                    user.setStatusCd("비활성화");
                }
            }
            UserPageDto userPageDto = new UserPageDto(pageInfo, userList);
            responseDto.setData(userPageDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 5.2 [admin/user/{ID}] 사용자 상세조회
    public ResponseDto<?> selectUserById(int userId){
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            UserDto userDto = userRepository.selectUser(userId);
            if(userDto == null){
                responseDto.setCode(ResponseCode.NON_EXISTENT_USER);
                return responseDto;
            }
            userDto.setName(util.nameMasking(userDto.getName()));
            userDto.setEmail(util.emailMasking(userDto.getEmail()));
            userDto.setUserPwd(null);
            userDto.setRole(null);
            if(userDto.getStatusCd().equals("E")){
                userDto.setStatusCd("활성화");
            }else{
                userDto.setStatusCd("비활성화");
            }
            responseDto.setData(userDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    // 5.3 [admin/log/{ID}] 사용자 로그 조회
    public ResponseDto<?> selectLog(int id, int page) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        PageInfo pageInfo = new PageInfo(page, 5);
        pageInfo.setPageNo(id);
        try {
            pageInfo.setTotalElement(logRepository.selectLogNum(id));
            ArrayList<LogDto> logDtos = logRepository.selectLog(pageInfo);
            for(LogDto logDto : logDtos){
                String statusCd = logDto.getStatusCode();
                switch (statusCd) {
                    case "0":
                        logDto.setStatusCode("성공");
                        break;
                    case "1":
                        logDto.setStatusCode("실패");
                        break;
                    case "2":
                        logDto.setStatusCode("로그아웃");
                        break;
                }
            }
            pageInfo.setPageNo(page);
            LogPageDto logPageDto = new LogPageDto(pageInfo, logDtos);
            responseDto.setData(logPageDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public ResponseDto<?> dashboardFan(String fanCd) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            FanCountDto fanCountDto = userRepository.selectFanSum(fanCd);
            fanCountDto.setNowUser(fanCountDto.getJoinUser() - fanCountDto.getWithdrawUser());
            responseDto.setData(fanCountDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public ResponseDto<?> dashboardConcert() {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            responseDto.setData(concertRepository.selectConcertTitle());
            responseDto.setCode(ResponseCode.SUCCESS);
        }catch (Exception e){
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }


}
