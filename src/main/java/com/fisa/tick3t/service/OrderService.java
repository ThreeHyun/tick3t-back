package com.fisa.tick3t.service;


import com.fisa.tick3t.domain.dto.*;
import com.fisa.tick3t.global.Constants;
import com.fisa.tick3t.repository.OrderRepository;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static com.fisa.tick3t.global.PayCode.codeToPayDesc;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    public ResponseDto<?> selectOrders(int userId, QueryStringDto queryStringDto) {
        ResponseDto<Object> responseDto = new ResponseDto<>();

        // 페이징용 객체 생성
        PageInfo pageInfo = new PageInfo(queryStringDto.getPage(), Constants.concertPageSize);
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
                responseDto.setCode(ResponseCode.NON_EXISTENT_RESERVATION);
                return responseDto;
            }

            // 조회 결과 반환
            responseDto.setData(orderDto);
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
            // 예매내역 결제
            int result = orderRepository.payOrder(userId, ticketId);

            // update된 내역이 없다면 존재하지 않는 예매정보 반환
            if(result == 0){
                responseDto.setCode(ResponseCode.NON_EXISTENT_RESERVATION);
                return responseDto;
            }
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public ResponseDto<?> cancelOrder(int userId, int ticketId) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            // 예매내역 취소
            int result = orderRepository.cancelOrder(userId, ticketId);

            // update된 내역이 없다면 존재하지 않는 예매정보 반환
            if(result == 0){
                responseDto.setCode(ResponseCode.NON_EXISTENT_RESERVATION);
                return responseDto;
            }
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }

    @Transactional
    public ResponseDto<?> selectSeat(int userId, ReservationDto reservationDto) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        reservationDto.setUserId(userId);
        try {
            // 랜덤 좌석 선택 후 저장
            orderRepository.selectSeat(reservationDto);
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (DataIntegrityViolationException e) {
            // 잔여석이 존재하지 않을 경우 에러 반환
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.NON_EXISTENT_SEAT);
        } catch (DataAccessException e) {
            // 팬클럽이 아니라거나 이미 예매 내역이 있을 경우
            if (e.getCause() instanceof SQLException) {
                log.error(e.getMessage());
                responseDto.setCode(ResponseCode.INVALID_FAN_ID);
                return responseDto;
            } else {
                log.error(e.getMessage());
                responseDto.setCode(ResponseCode.FAIL);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    } //

    public ResponseDto<?> checkOrder(int userId, int ticketId) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        try {
            int result = orderRepository.checkReservation(userId, ticketId);
            System.out.println(result);
            // 이미 예매했을 경우
            if (result == 1) {
                responseDto.setCode(ResponseCode.EXCEED_TICKET_LIMIT);
                return responseDto;
            }
            result = orderRepository.checkFanCd(userId, ticketId);

            // 팬클럽 아닐 경우
            if (result != 1) {
                responseDto.setCode(ResponseCode.UNAUTHORIZED_FAN);
                return responseDto;
            }
            responseDto.setCode(ResponseCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDto.setCode(ResponseCode.FAIL);
        }
        return responseDto;
    }
}
