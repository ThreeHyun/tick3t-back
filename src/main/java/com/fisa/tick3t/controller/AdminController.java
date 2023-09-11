package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.PageInfo;
import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.global.Constants;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UtilFunction util;


    @GetMapping("/user") // o
    public ResponseDto<?> selectUsers(@RequestParam(name = "category", required = false) String category,
                                      @RequestParam(name = "word", required = false) String word,
                                      @RequestParam(name = "page", defaultValue = "1") int page) throws CustomException {
        log.info("selectUsers의 RequestParam 확인");
        log.info("category: " + category + " word: " + word + " page: " + page);
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("name", "email", "fanCd"));
        QueryStringDto queryStringDto = util.checkQuery(category, word, page, categories, Constants.userPageSize);
        PageInfo pageInfo = new PageInfo(queryStringDto.getPage(), Constants.userPageSize);
        return adminService.selectUsers(queryStringDto, pageInfo);
    }

    @GetMapping("/user/{ID}") // o
    public ResponseDto<?> selectUserById(@PathVariable int ID) throws CustomException {
        ID = util.isValidParam(ID);
        return adminService.selectUserById(ID);
    }


    @GetMapping("/log/{ID}") // O
    public ResponseDto<?> log(@PathVariable int ID, @RequestParam(name = "page", defaultValue = "1") int page) throws CustomException {
        ID = util.isValidParam(ID);
        PageInfo pageInfo = new PageInfo(page, Constants.logPageSize);
        return adminService.selectLog(ID, pageInfo);
    }

    @GetMapping("/fan")
    public ResponseDto<?> dashboardFan() {
        return adminService.dashboardFan();
    }

    @GetMapping("/fan/{fanCd}")
    public ResponseDto<?> selectFanId(@PathVariable(required = false) String fanCd) throws CustomException {
        if (fanCd == null || fanCd.equals("") || fanCd.trim().equals("")) {
            fanCd = Constants.defaultFanCd;
        }
        return adminService.dashboardFanId(fanCd);
    }


    @GetMapping("/ticket")
    public ResponseDto<?> selectConcert() {
        return adminService.dashboardConcert();
    }

    @GetMapping("/ticket/{ID}")
    public ResponseDto<?> selectConcertById(@PathVariable String ID) throws CustomException{
        try{
            int concertId = Integer.parseInt(ID);
            if(concertId <= 0){
                throw new CustomException(ResponseCode.MISSING_OR_INVALID_REQUEST);
                //return new ResponseDto<>(ResponseCode.MISSING_OR_INVALID_REQUEST);
            }
            return adminService.dashboardConcertId(concertId);
        }catch (NumberFormatException e){
            log.info("ID : "  + ID);
            log.error(e.getMessage());
            throw new CustomException(ResponseCode.MISSING_OR_INVALID_REQUEST);
            //return new ResponseDto<>(ResponseCode.MISSING_OR_INVALID_REQUEST);
        }
    }
}
