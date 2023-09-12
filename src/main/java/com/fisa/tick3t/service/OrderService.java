package com.fisa.tick3t.service;


import com.fisa.tick3t.domain.dto.*;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.repository.OrderRepository;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fisa.tick3t.global.PayCode.codeToPayDesc;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final UtilFunction util;


    public ResponseDto<?> selectOrders(int userId, QueryStringDto queryStringDto, PageInfo pageInfo) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 검색 예매내역 수 조회
            pageInfo.setTotalElement(orderRepository.selectOrderNum(queryStringDto, userId));

            // 예매내역 조회
            List<OrderDto> orders = orderRepository.selectOrders(userId, queryStringDto);

            // 0 : 결제 대기 1: 결제 완료 2: 예매 취소 3: 결제 취소
            for (OrderDto order : orders) {
                order.setPayState(codeToPayDesc(order.getPayState()));
            }

            // 반환용 객체 생성
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
            // id로 예매내역 조회
            OrderDto orderDto = orderRepository.selectOrder(userId, ticketId);

            // 반환값이 null이라면 없는 예매내역 결과 반환
            if (orderDto == null) {
                throw new CustomException(ResponseCode.NON_EXISTENT_RESERVATION); //400
            }

            // 가격 차등
            orderDto.setPrice(util.discountPrice(orderDto.getGrade(), orderDto.getPrice()));

            // 조회 결과 반환
            responseDto.setData(orderDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            responseDto.setCode(e.getResponseCode()); //200
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public ResponseDto<?> payOrder(int userId, int ticketId) throws CustomException {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            Integer timeCheck = orderRepository.payOrderTime(userId, ticketId);
            if(timeCheck == null || timeCheck == 0){
                throw new CustomException(ResponseCode.NOT_CONCERT_PAYMENT_TIME);
            }
            // 예매내역 결제
            Integer result = orderRepository.payOrder(userId, ticketId);

            // update된 내역이 없다면 존재하지 않는 예매정보 반환
            if(result == null || result == 0){
                throw new CustomException(ResponseCode.NON_EXISTENT_RESERVATION);
            }
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public ResponseDto<?> cancelOrder(int userId, int ticketId) throws CustomException {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 예매내역 취소
            Integer timeCheck = orderRepository.cancelOrderTime(ticketId);
            if(timeCheck == null){
                throw new CustomException(ResponseCode.NON_EXISTENT_RESERVATION);
            }
            if(timeCheck == 0){
                throw new CustomException(ResponseCode.NOT_CONCERT_CANCELLATION_TIME);
            }
            Integer result = orderRepository.cancelOrder(userId, ticketId);

            // update된 내역이 없다면 존재하지 않는 예매정보 반환
            if(result == null || result == 0){
                throw new CustomException(ResponseCode.NON_EXISTENT_RESERVATION);
            }
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (CustomException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }


    @Transactional
    public ResponseDto<?> selectSeat(int userId, ReservationDto reservationDto) throws CustomException {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        reservationDto.setUserId(userId);
        try {
            // 랜덤 좌석 선택 후 저장
            orderRepository.selectSeat(reservationDto);
            int result = orderRepository.selectTicketId(userId, reservationDto.getConcertId());
            Map<String, Integer> map = new HashMap<>();
            // 키-값 쌍을 추가
            map.put("ticketId", result);
            responseDto.setData(map);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (DataIntegrityViolationException e) {
            // 잔여석이 존재하지 않을 경우 에러 반환
            log.error(e.getMessage());
            throw new CustomException(ResponseCode.NON_EXISTENT_SEAT);
        } catch (DataAccessException e) {
            int result = reservationDto.getCanReserve();
            setOrderStatus(responseDto, result);
        }  catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    private void setOrderStatus(ResponseDto<Object> responseDto, int result) {
        if(result == 0){
            responseDto.setCode(ResponseCode.SUCCESS);
        } else if (result == 1) {
            responseDto.setCode(ResponseCode.UNAUTHORIZED_FAN);
        } else if (result == 2){
            responseDto.setCode(ResponseCode.EXCEED_TICKET_LIMIT);
        } else if (result == 3){
            responseDto.setCode(ResponseCode.NOT_CONCERT_RESERVATION_TIME);
        }
    }


    public ResponseDto<?> checkOrder(int userId, int concertId) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            int result = orderRepository.checkOrder(userId, concertId);
            setOrderStatus(responseDto, result);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }
}
