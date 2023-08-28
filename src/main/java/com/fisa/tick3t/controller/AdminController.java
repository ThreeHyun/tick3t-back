package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.response .ResponseDto;
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
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("name", "email", "fanCd"));
        QueryStringDto queryStringDto = util.checkQuery(category, word, page, categories,20);
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

//    @GetMapping("/fan/{fanCd}") // O 근데 쿼리 개선해야할듯 db를 짱마니 갔다옴 ㅠㅠ
//    public ResponseDto<?> selectFan(@PathVariable(required = false) String fanCd) {
//        if(fanCd == null){
//            fanCd = "default"; // 디폴트값인거 하나 정해놓기..?
//        }
//        return adminService.dashboardFan(fanCd);
//    }

    @GetMapping("/fan/{fanCd}")
    public ResponseDto<?> selectFan(@PathVariable(required = false) String fanCd) {
        if(fanCd == null){
            fanCd = "12341";
        }
        return adminService.dashboardFan(fanCd);
    }


    @GetMapping("/ticket") //date를 받아서 그 이후의 것만 보여주고 싶음..
    public ResponseDto<?> selectConcert() {
        return adminService.dashboardConcert();
    }

    @GetMapping("/ticket/{ID}")
    public ResponseDto<?> selectConcertById(@PathVariable String ID) {
        return adminService.dashboardConcert();
    }


}
