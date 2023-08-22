USE tick3t;
SHOW tables;

DESCRIBE TCK_USER_M;
DESCRIBE TCK_CONCERT_M;
DESCRIBE TCK_HALL_M;
DESCRIBE CNCR_TICKET_M;
DESCRIBE TCK_ADMIN_M;
DESCRIBE TCK_HALL_SEAT_M;
DESCRIBE TCK_LOG_M;

select * from TCK_USER_M;

DROP TABLE IF EXISTS TCK_USER_M;
DROP TABLE IF EXISTS TCK_CONCERT_M;
DROP TABLE IF EXISTS TCK_HALL_M;
DROP TABLE IF EXISTS CNCR_TICKET_M;
DROP TABLE IF EXISTS TCK_ADMIN_M;
DROP TABLE IF EXISTS TCK_HALL_SEAT_M;
DROP TABLE IF EXISTS TCK_LOG_M;

CREATE TABLE TCK_USER_M
(
    user_id        int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name      varchar(5)   NULL,
    user_birth     date         NULL,
    user_email     varchar(100) NULL,
    user_pwd       varchar(100) NULL,
    fan_id         int          NULL DEFAULT 0 COMMENT '8글자',
    fan_cd         int          NULL DEFAULT 0,
    create_dtm     datetime     NULL DEFAULT NOW(),
    user_status_cd char(1)      NULL DEFAULT 'E' COMMENT '사용자 상태(E: 활성, D: 탈퇴)'
);

CREATE TABLE TCK_CONCERT_M
(
    concert_id       int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hall_id          int          NOT NULL,
    cncr_title       varchar(100) NULL,
    cncr_img_url     varchar(100) NULL,
    cncr_dtm         datetime     NULL,
    cncr_price       int          NULL COMMENT '콘서트 기준 가격',
    cncr_cancel_date datetime     NULL COMMENT '취소 가능일자',
    fan_cd           int          NULL COMMENT '대상 팬덤',
    cncr_start_dtm   datetime     NULL
);

CREATE TABLE TCK_HALL_M
(
    hall_id   int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hall_name varchar(10)  NULL,
    hall_loc  varchar(256) NULL COMMENT '주소'
);

CREATE TABLE CNCR_TICKET_M
(
    reservation_id   int      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id      int      NOT NULL COMMENT '예매자',
    concert_id       int      NOT NULL,
    grade_id         int      NOT NULL COMMENT 'auto_increment',
    hall_id          int      NOT NULL,
    resv_dtm         datetime NOT NULL DEFAULT now(),
    pay_dtm          datetime NULL,
    ticket_status_cd char(1)  NULL     DEFAULT 0 COMMENT '0: 결제대기, 1: 결제 완료(예매완료), 2: 예매취소, 3: 결제취소',
    ticket_price     int      NOT NULL COMMENT '티켓 실제 가격',
    seat_no          int      NOT NULL COMMENT '좌석번호'
);

CREATE TABLE TCK_ADMIN_M
(
    admin_id        int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    admin_email     varchar(128) NOT NULL COMMENT '메일 주소',
    admin_name      varchar(128) NOT NULL,
    admin_pwd       varchar(100) NULL,
    admin_status_cd char(1)      NULL COMMENT '관리자 상태(E: 활성, D: 비활성화)',
    create_dtm      datetime     NOT NULL DEFAULT now() COMMENT '관리자 정보 생성일'
);

CREATE TABLE TCK_HALL_SEAT_M
(
    grade_id   int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    hall_id    int         NOT NULL,
    grade_name varchar(100) NOT NULL,
    seat_no    int          NOT NULL
);

CREATE TABLE TCK_LOG_M
(
    log_id           int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id          int          NOT NULL,
    access_ip        varchar(100) NULL,
    access_status_cd char(1)      NULL COMMENT '0: 성공 1: 실패 2:로그아웃',
    create_dtm       datetime     NOT NULL DEFAULT now()
);

ALTER TABLE TCK_CONCERT_M
    ADD CONSTRAINT FK_TCK_HALL_M_TO_TCK_CONCERT_M_1 FOREIGN KEY (
                                                                   hall_id
        )
        REFERENCES TCK_HALL_M (
                                 hall_id
            );

ALTER TABLE CNCR_TICKET_M
    ADD CONSTRAINT FK_TCK_USER_M_TO_CNCR_TICKET_M_1 FOREIGN KEY (
                                                                   customer_id
        )
        REFERENCES TCK_USER_M (
                                 user_id
            );

ALTER TABLE CNCR_TICKET_M
    ADD CONSTRAINT FK_TCK_CONCERT_M_TO_CNCR_TICKET_M_1 FOREIGN KEY (
                                                                      concert_id
        )
        REFERENCES TCK_CONCERT_M (
                                    concert_id
            );

ALTER TABLE CNCR_TICKET_M
    ADD CONSTRAINT FK_TCK_HALL_SEAT_M_TO_CNCR_TICKET_M_1 FOREIGN KEY (
                                                                        grade_id
        )
        REFERENCES TCK_HALL_SEAT_M (
                                      grade_id
            );

ALTER TABLE TCK_HALL_SEAT_M
    ADD CONSTRAINT FK_TCK_HALL_M_TO_TCK_HALL_SEAT_M_1 FOREIGN KEY (
                                                                   hall_id
        )
        REFERENCES TCK_HALL_M (
                               hall_id
            );

ALTER TABLE CNCR_TICKET_M
    ADD CONSTRAINT FK_TCK_HALL_SEAT_M_TO_CNCR_TICKET_M_2 FOREIGN KEY (
                                                                        hall_id
        )
        REFERENCES TCK_HALL_SEAT_M (
                                      hall_id
            );

ALTER TABLE TCK_LOG_M
    ADD CONSTRAINT FK_TCK_USER_M_TO_TCK_LOG_M_1 FOREIGN KEY (
                                                               user_id
        )
        REFERENCES TCK_USER_M (
                                 user_id
            );

