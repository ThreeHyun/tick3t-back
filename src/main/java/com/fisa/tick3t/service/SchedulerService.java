package com.fisa.tick3t.service;

import com.fisa.tick3t.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void cancelOrders() {
        try {
            orderRepository.cancelOrders();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
