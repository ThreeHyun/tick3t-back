package com.fisa.tick3t.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayCode {
    PENDING_PAYMENT("0", "결제 대기"),
    PAYMENT_COMPLETE("1", "결제 완료"),
    BOOKING_CANCELLED("2", "예매 취소"),
    PAYMENT_CANCELLED("3", "결제 취소");

    private final String code;
    private final String description;

    public static String codeToPayDesc(String code){
        for (PayCode status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDescription();
            }
        }
        return "Unknown";

    }
}
