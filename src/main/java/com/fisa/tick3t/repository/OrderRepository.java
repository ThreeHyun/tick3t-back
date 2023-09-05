package com.fisa.tick3t.repository;

import com.fisa.tick3t.domain.dto.OrderDto;
import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.domain.dto.ReservationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface OrderRepository {
    ArrayList<OrderDto> selectOrders(@Param("userId") int userId, @Param("queryStringDto") QueryStringDto queryStringDto);

    //ArrayList<OrderDto> selectOrders(Map<String, Object> params);
    //ArrayList<OrderDto> selectOrders(Map<String, Object> params);
    //ArrayList<OrderDto> selectOrders(QueryStringDto queryStringDto);
    OrderDto selectOrder(@Param("userId") int userId, @Param("concertId") int concertId);

    int payOrder(@Param("userId") int userId, @Param("ticketId") int ticketId);

    int selectOrderNum(@Param("queryStringDto") QueryStringDto queryStringDto, @Param("userId") int userId);

    void selectSeat(ReservationDto ReservationDto);

    int cancelOrder(@Param("userId") int userId, @Param("ticketId") int ticketId);
    void cancelOrders();

    int checkReservation(int userId, int ticketId);

    int checkFanCd(int userId, int ticketId);
}
