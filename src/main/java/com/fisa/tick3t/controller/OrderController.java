package com.fisa.tick3t.controller;

import com.fisa.tick3t.domain.dto.OrderDto;
import com.fisa.tick3t.domain.dto.PageInfo;
import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.domain.dto.ReservationDto;
import com.fisa.tick3t.global.Constants;
import com.fisa.tick3t.global.CustomException;
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
                                       @RequestParam(name = "page", defaultValue = "1") int page) throws CustomException {
        log.info("selectOrders의 RequestParam 확인");
        log.info("category: " + category + " word: " + word + " page: " + page);
        int userId = getUserIdFromAuthentication(authentication);
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("결제 대기", "결제 완료", "예매 취소", "결제 취소"));
        QueryStringDto queryStringDto = util.checkQuery(category, word, page, categories, 3);
        PageInfo pageInfo = new PageInfo(queryStringDto.getPage(), Constants.concertPageSize);
        return orderService.selectOrders(userId, queryStringDto, pageInfo);
    }


    @GetMapping("/myorder/{ID}")
    public ResponseDto<?> selectOrder(Authentication authentication, @PathVariable int ID) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        return orderService.selectOrder(userId, ID);

    }

    @PostMapping("/myorder/cancel")
    public ResponseDto<?> cancelOrder(Authentication authentication, @RequestBody OrderDto orderDto) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        int ticketId = orderDto.getTicketId();
        return orderService.cancelOrder(userId, ticketId);
    }


    @PostMapping("/myorder/pay")
    public ResponseDto<?> payOrder(Authentication authentication, @RequestBody OrderDto orderDto) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        if (orderDto == null || orderDto.getTicketId() == 0) {
            throw new CustomException(ResponseCode.NON_EXISTENT_RESERVATION);
        }
        int ticketId = orderDto.getTicketId();
        return orderService.payOrder(userId, ticketId);

    }

    @GetMapping("/order/check/{ID}")
    public ResponseDto<?> checkOrder(Authentication authentication, @PathVariable int ID) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        return orderService.checkOrder(userId, ID);
    }

    @PostMapping("/order")
    public ResponseDto<?> selectSeat(Authentication authentication, @RequestBody ReservationDto reservationDto) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        return orderService.selectSeat(userId, reservationDto);
    }

    private int getUserIdFromAuthentication(Authentication authentication) throws CustomException {
        if(authentication == null){
            throw new CustomException(ResponseCode.JWT_ERROR);
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof JwtUserDetails)) {
            throw new CustomException(ResponseCode.INVALID_TOKEN);
        }
        JwtUserDetails userDetails = (JwtUserDetails) principal;
        return userDetails.getUserId();
    } //todo: 반복 로직 처리할 방법 생각하기~~ util은 아닌듯
}
