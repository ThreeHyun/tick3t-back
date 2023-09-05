package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.OrderDto;
import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.domain.dto.ReservationDto;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.jwt.JwtUserDetails;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UtilFunction util;

    @GetMapping("/myorder")
    public ResponseDto<?> selectOrders(Authentication authentication,
                                       @RequestParam(name = "category", required = false) String category,
                                       @RequestParam(name = "word", required = false) String word,
                                       @RequestParam(name = "page", defaultValue = "1") int page) {
        log.info("category: " + category);
        log.info("word: " + word);
        log.info("page: " + page);
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            ArrayList<String> categories = new ArrayList<>(Arrays.asList("결제 대기", "결제 완료", "예매 취소", "결제 취소"));
            QueryStringDto queryStringDto = util.checkQuery(category, word, page, categories, 3);
            return orderService.selectOrders(userId, queryStringDto);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }
    }


    @GetMapping("/myorder/{ID}")
    public ResponseDto<?> selectOrder(Authentication authentication, @PathVariable int ID) {
        try {
            // 토큰이 없어 id를 받지 못할 경우 nullpointException 발생
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            return orderService.selectOrder(userId, ID);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        } //todo: exceptionhandler를 사용해서 에러 처리 구현하기
    }

    @PostMapping("/myorder/cancel")
    public ResponseDto<?> cancelOrder(Authentication authentication, @RequestBody OrderDto orderDto) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            int ticketId = orderDto.getTicketId();
            return orderService.cancelOrder(userId, ticketId);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }
    }

    @PostMapping("/myorder/pay")
    public ResponseDto<?> payOrder(Authentication authentication, @RequestBody OrderDto orderDto) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            int ticketId = orderDto.getTicketId();
            return orderService.payOrder(userId, ticketId);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }

    }

    @GetMapping("/order/check/{ID}")
    public ResponseDto<?> checkOrder(Authentication authentication, @PathVariable int ID) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            return orderService.checkOrder(userId, ID);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }
    }

    @PostMapping("/order")
    public ResponseDto<?> selectSeat(Authentication authentication, @RequestBody ReservationDto reservationDto) {
        try {
            int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
            return orderService.selectSeat(userId, reservationDto);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            return new ResponseDto<>(ResponseCode.INVALID_TOKEN);
        }
    }
}
