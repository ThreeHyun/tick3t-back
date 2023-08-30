package com.fisa.tick3t.service;


import com.fisa.tick3t.domain.dto.OrderDto;
import com.fisa.tick3t.domain.dto.OrderPageDto;
import com.fisa.tick3t.domain.dto.PageInfo;
import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.repository.OrderRepository;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    public ResponseDto<?> selectOrders(int userId, QueryStringDto queryStringDto) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        PageInfo pageInfo = new PageInfo(queryStringDto.getPage(), 3);
        try {
            pageInfo.setTotalElement(orderRepository.selectOrderNum());
            // todo: 검색할 경우 필터에 따라서 totalElement도 달라지는데 생각 못했음
            List<OrderDto> orders = orderRepository.selectOrders(userId, queryStringDto);
            for (OrderDto order : orders) {
                switch (order.getPayState()) {
                    case "0":
                        order.setPayState("결제 대기");
                        break;
                    case "1":
                        order.setPayState("결제 완료");
                        break;
                    case "2":
                        order.setPayState("예매 취소");
                        break;
                    case "3":
                        order.setPayState("결제 취소");
                        break;
                } // todo: map이나 enum으로 바꿔보기 pageSize들도 마찬가지 상수로 둘 수 있게!
            }
            OrderPageDto orderPageDto = new OrderPageDto(pageInfo, orders);
            responseDto.setData(orderPageDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    public ResponseDto<?> selectOrder(int userId, int ticketId) {
        ResponseDto<OrderDto> responseDto = new ResponseDto<>();
        try {
            OrderDto orderDto = orderRepository.selectOrder(userId, ticketId);
            if(orderDto == null){
                responseDto.setCode(ResponseCode.NON_EXISTENT_CONCERT);
                return responseDto;
            }
            responseDto.setData(orderRepository.selectOrder(userId, ticketId));
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public ResponseDto<?> payOrder(int userId, int ticketId) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            orderRepository.payOrder(userId, ticketId);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

}
