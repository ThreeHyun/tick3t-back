package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.PageInfo;
import com.fisa.tick3t.global.Constants;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.service.ConcertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    private final UtilFunction util;

    @GetMapping("/concert")
    public ResponseDto<?> selectConcerts(@RequestParam(name = "page", defaultValue = "1") int page) {
        PageInfo pageInfo = new PageInfo(page, Constants.concertPageSize);
        return concertService.selectConcerts(pageInfo);
    }

    @GetMapping("/concert/{ID}")
    public ResponseDto<?> selectConcert(@PathVariable int ID) throws CustomException {
        ID = util.isValidParam(ID);
        return concertService.selectConcert(ID);
    }
}
