package com.fisa.tick3t.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString //for log
@JsonIgnoreProperties
public class User {

    private final int userId;
    private final String userName;
    private final String userBirth;
    private final String userEmail;
    private final String userPwd;
    private final int fanId;
    private final int fanCd;
    private final Date createDtm;
    private final char userStatusCd;

}

