package com.fisa.tick3t.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat
@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("0000", "성공적으로 처리되었습니다."),
    MISSING_OR_INVALID_REQUEST("0022", "필수 파라미터 값 누락 또는 유효하지 않은 파라미터 값입니다."),
    INVALID_DATA("0024", "유효하지 않은 형식의 데이터입니다."),
    INVALID_USER("1111", "유효하지 않은 사용자입니다."),
    LOGGED_OUT_USER("1112", "로그아웃된 사용자입니다."),
    WITHDRAWN_USER("1113", "탈퇴한 사용자입니다."),
    UNAUTHORIZED_USER("1114", "권한이 없는 사용자입니다."),
    MISMATCHED_USER_INFO("1115", "사용자 정보가 일치하지 않습니다."),
    INVALID_TOKEN("1116", "유효하지 않은 토큰입니다."),
    EMAIL_ALREADY_IN_USE("2121", "이미 사용중인 이메일 주소입니다."),
    UNKNOWN_EMAIL("2421", "존재하지 않는 이메일 주소입니다."),
    INVALID_FAN_ID("4323", "유효하지 않은 팬클럽 회원 번호입니다."),
    CANNOT_WITHDRAW("7521", "현재 예매중인 내역이 존재할 경우 탈퇴할 수 없습니다."),
    NON_EXISTENT_USER("5221", "해당하는 사용자가 존재하지 않습니다."),
    NON_EXISTENT_CONCERT("3221", "해당하는 공연이 존재하지 않습니다."),
    NON_EXISTENT_FANDOM("5421", "해당하는 팬덤이 존재하지 않습니다."),
    NOT_CONCERT_RESERVATION_TIME("6001", "공연 예매 시간이 아닙니다."),
    NOT_CONCERT_CANCELLATION_TIME("6002", "공연 취소 가능 시간이 아닙니다."),
    NOT_CONCERT_PAYMENT_TIME("6003", "공연 결제 가능 시간이 아닙니다."),
    UNAUTHORIZED_FAN("6002", "팬클럽 인증을 받지 못한 경우 예매할 수 없습니다."),
    EXCEED_TICKET_LIMIT("6003", "1인 1매만 예매 가능합니다."),
    NON_EXISTENT_SEAT("6421", "잔여석이 존재하지 않습니다."),
    NON_EXISTENT_RESERVATION("6421", "존재하지 않는 예매 정보입니다."),
    JWT_ERROR("8888", "로그인이 필요한 요청입니다." ),
    FAIL("9999", "알 수 없는 에러입니다. 잠시 후 다시 시도해주세요"),
    NO_DATA("9998", "데이터가 존재하지 않습니다");
    // todo: 이 부분을 응답으로 내려줄지 말지.. 고민됩니다.

    private final String resultCode;
    private final String message;
}