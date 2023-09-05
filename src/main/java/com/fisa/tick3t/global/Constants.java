package com.fisa.tick3t.global;

public final class Constants {

    // page size
    public static final int concertPageSize = 3;
    public static final int userPageSize = 20;
    public static final int logPageSize = 5;

    // token
    public static final String AUTHORITIES_KEY = "auth";
    public static final String BEARER_TYPE = "Bearer";
    //private static `final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 12 * 60 * 60 * 1000L;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    //

}
