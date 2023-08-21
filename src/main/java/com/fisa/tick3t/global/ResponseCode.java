package com.fisa.tick3t.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat
@AllAgrgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS("0000", "성공적으로 처리되었습니다."),
    MISSING_OR_INVALID_PARAM("0022", "필수 파라미터 값 누락 또는 유효하지 않은 파라미터 값입니다."),
    INVALID_USER("1111", "유효하지 않은 사용자입니다."),
    LOGGED_OUT_USER("1112", "로그아웃된 사용자입니다."),
    WITHDRAWN_USER("1113", "탈퇴한 사용자입니다."),
    UNAUTHORIZED_USER("1114", "권한이 없는 사용자입니다."),
    MISMATCHED_USER_INFO("1115", "사용자 정보가 일치하지 않습니다."),
    EMAIL_ALREADY_IN_USE("2121", "이미 사용중인 이메일 주소입니다."),
    UNKNOWN_EMAIL("2421", "가입되지 않은 이메일 주소입니다."),
    INVALID_FAN_ID("4323", "유효하지 않은 팬클럽 회원 번호입니다."),
    CANNOT_WITHDRAW("7521", "현재 예매중인 내역이 존재할 경우 탈퇴할 수 없습니다."),
    NON_EXISTENT_USER("5221", "해당하는 사용자가 존재하지 않습니다."),
    NON_EXISTENT_CONCERT("3221", "해당하는 공연이 존재하지 않습니다."),
    NON_EXISTENT_FANDOM("5421", "해당하는 팬덤이 존재하지 않습니다."),
    NON_EXISTENT_TICKETING("5621", "해당하는 티켓팅이 존재하지 않습니다."),
    NON_EXISTENT_RESERVATION("6421", "해당하는 예매 정보가 존재하지 않습니다."),
    FAIL("9999", "알 수 없는 에러입니다. 잠시 후 다시 시도해주세요");

    private final String code;
    private final String message;
}