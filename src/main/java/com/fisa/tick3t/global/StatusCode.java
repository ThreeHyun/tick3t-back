package com.fisa.tick3t.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    LOGIN("0", "성공"),
    LOGIN_FAILURE("1", "실패"),
    LOGOUT("2", "로그아웃"),
    ACTIVE("E","활성화"),
    INACTIVE("D","비활성화");

    private final String code;
    private final String description;

    public static String codeToStatusDesc(String code){
        for (StatusCode status : values()) {
            if (status.getCode().equals(code)) {
                return status.getDescription();
            }
        }
        return "Unknown";
    }
}
