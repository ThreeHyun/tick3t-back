package com.fisa.tick3t.repository;

import com.fisa.tick3t.domain.dto.*;
import com.fisa.tick3t.domain.vo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserRepository {

    // 2.1. [signup] 회원가입
    Integer checkEmail(String userEmail); // 2.1.1 -> 중복 여부 체크
    void insertUser(UserDto userDto); // 2.1.2 -> User insert

    // 2.4 [reset-password] 비밀번호 재발급
    Integer checkUser(User user);

    // 4.1 [profile] 회원 정보 조회(User)
    ProfileDto selectProfile(int userId);

    // 4.2 [profile/password] 비밀번호 변경, 2.4 [reset-password] 비밀번호 재발급,
    void updatePassword(PasswordDto passwordDto);

    // 4.3 [profile/fanid] 팬클럽 인증번호 변경
    Integer checkFanId(String fanId); // -> 4.3.1 중복 여부 체크
    void updateFanId(UserDto userDto); // -> 4.3.2 FanId update

    // 4.4 [profile/withdraw] 회원 탈퇴
    void withdraw(int userId);
    int checkOrder(int userId);

    // 5.1 [admin/user] 회원 리스트 조회(Admin)
    ArrayList<UserDto> selectUsers(QueryStringDto queryStringDto);

    // 5.2 [admin/user/{ID}] 회원 상세 정보 조회(Admim)
    UserDto selectUser(int userId);

    // 5.4 [admin/fan/{fancode}] 팬덤별 회원 체크 대시보드
    FanCountDto selectFanSum(String fanCd);

    int selectUserNum();

    //1-5 사용자 팬덤 조회
//    Integer selectFanJoin(String fanCd);
//    Integer selectFanWithdraw(String fanCd);
//    Integer selectFanWeek(String fanCd);
}
