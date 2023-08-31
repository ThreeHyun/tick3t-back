package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.OrderDto;
import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.domain.dto.ReservationDto;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.jwt.JwtUserDetails;
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
        int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("1", "2", "3"));
        QueryStringDto queryStringDto = util.checkQuery(category, word, page, categories, 3);
        return orderService.selectOrders(userId, queryStringDto);
    }


    @GetMapping("/myorder/{ID}")
    public ResponseDto<?> selectOrder(Authentication authentication, @PathVariable int ID) {
        int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
        return orderService.selectOrder(userId, ID);
    }

    @PostMapping("/myorder/pay")
    public ResponseDto<?> payOrder(Authentication authentication, @RequestBody OrderDto orderDto) {
        int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
        int ticketId = orderDto.getTicketId();
        return orderService.payOrder(userId, ticketId);
    }

    @PostMapping("/myorder/cancel")
    public ResponseDto<?> cancleOrder(Authentication authentication, @RequestBody OrderDto orderDto){
        int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
        int ticketId = orderDto.getTicketId();
        return orderService.cancelOrder(userId, ticketId);
    }

    @GetMapping("/order/check/{ID}")
    public ResponseDto<?> checkOrder(Authentication authentication, @PathVariable int ID){
        int userId = ((JwtUserDetails) authentication.getPrincipal()).getUserId();
        return orderService.checkOrder(userId, ID);
    }

    @PostMapping("/order")
    public ResponseDto<?> test(@RequestBody ReservationDto reservationDto){
        return orderService.selectSeat(reservationDto);
    }


}
