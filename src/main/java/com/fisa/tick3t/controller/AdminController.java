package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.global.Constants;
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
                                      @RequestParam(name = "page", defaultValue = "1") int page) {
        log.info("category: " + category);
        log.info("word: " + word);
        log.info("page: " + page);
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("name", "email", "fanCd"));
        QueryStringDto queryStringDto = util.checkQuery(category, word, page, categories, Constants.userPageSize);
        return adminService.selectUsers(queryStringDto);
    }

    @GetMapping("/user/{ID}") // o
    public ResponseDto<?> selectUserById(@PathVariable int ID) {
        return adminService.selectUserById(ID);
    }


    @GetMapping("/log/{ID}") // O
    public ResponseDto<?> log(@PathVariable int ID, @RequestParam(name = "page", defaultValue = "1") int page) {
        return adminService.selectLog(ID, page);
    }

    @GetMapping("/fan")
    public ResponseDto<?> dashboardFan() {
        return adminService.dashboardFan();
    }

    @GetMapping("/fan/{fanCd}")
    public ResponseDto<?> selectFanId(@PathVariable(required = false) String fanCd) {
        if (fanCd == null) {
            fanCd = "IU";
        }
        return adminService.dashboardFanId(fanCd);
    }


    @GetMapping("/ticket")
    public ResponseDto<?> selectConcert() {
        return adminService.dashboardConcert();
    }

    @GetMapping("/ticket/{ID}")
    public ResponseDto<?> selectConcertById(@PathVariable String ID) {
        log.info("ID : "  + ID);
        try{
            int concertId = Integer.parseInt(ID);
            return adminService.dashboardConcertId(concertId);
        }catch (NumberFormatException e){
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.MISSING_OR_INVALID_REQUEST);
        }
    }
}
