package com.fisa.tick3t.controller;


import com.fisa.tick3t.domain.dto.PasswordDto;
import com.fisa.tick3t.domain.dto.ProfileDto;
import com.fisa.tick3t.global.CustomException;
import com.fisa.tick3t.global.UtilFunction;
import com.fisa.tick3t.jwt.JwtUserDetails;
import com.fisa.tick3t.response.ResponseCode;
import com.fisa.tick3t.response.ResponseDto;
import com.fisa.tick3t.domain.vo.User;
import com.fisa.tick3t.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UtilFunction util;

    @PostMapping("/signup") // O
    public ResponseDto<?> signup(@RequestBody User user) throws CustomException {
        String userPwd = user.getUserPwd();
        String userEmail = user.getEmail();
        // 파라미터 완전성 검사
        if (userPwd == null || userEmail == null || user.getName() == null) {
            log.info("userPwd : " + userPwd);
            log.info("userEmail : " + userEmail);
            log.info("Name : " + user.getName());
            throw new CustomException(ResponseCode.MISSING_OR_INVALID_REQUEST);
        }

        // 파라미터 유효성 검사
        if (!util.isValidPassword(userPwd) || !util.isValidEmail(userEmail) || user.getName().length() > 5) {
            log.info("패스워드 형식(대소문자 + 숫자 + 기호)이 일치하나요? :" + userPwd);
            log.info("이메일 형식이 일치하나요? :" + userEmail);
            log.info("유저 name이 5자를 초과하나요? :" + user.getName());
            throw new CustomException(ResponseCode.INVALID_DATA);
        }
        return userService.signUp(user);
    }

    @PostMapping("/reset-password") // O 메일링 처리하기
    public ResponseDto<?> reissue(@RequestBody User user) throws CustomException {
        String userBirth = user.getBirth();
        String userName = user.getName();
        String userEmail = user.getEmail();

        // 파라미터 완전성 검사
        if (userEmail == null || userName == null || userBirth == null) {
            log.info("userEmail : " + userEmail);
            log.info("userName : " + userName);
            log.info("userBirth : " + userBirth);
            throw new CustomException(ResponseCode.MISSING_OR_INVALID_REQUEST);
        }
        return userService.resetPassword(user);
    }

    @GetMapping("/profile") // O
    public ResponseDto<?> profile(Authentication authentication) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        return userService.profile(userId);
    }


    @PostMapping("/profile/password") // O
    public ResponseDto<?> changePwd(Authentication authentication, @RequestBody PasswordDto passwordDto) throws CustomException {
        String newPwd = passwordDto.getNewPassword();
        String oldPwd = passwordDto.getOldPassword();
        // // 파라미터 완전성, 유효성 검사
        if (newPwd.equals(oldPwd) || !newPwd.equals(passwordDto.getNewPasswordCheck()) || !util.isValidPassword(newPwd)) {
            log.info(oldPwd + " " + newPwd + " ");
            log.info("패스워드 형식이 일치하나요? : " + util.isValidPassword(newPwd));
            throw new CustomException(ResponseCode.INVALID_DATA);
        }
        int userId = getUserIdFromAuthentication(authentication);
        return userService.changePassword(userId, passwordDto);
    }

    @PostMapping("/profile/fanId") // O
    public ResponseDto<?> changeFanId(Authentication authentication, @RequestBody ProfileDto profileDto) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        String fanId = profileDto.getFanId();
        // 파라미터 완전성, 유효성 검사
        if (fanId.length() != 8) {
            log.info("Invalid fanID length: " + fanId.length());
            throw new CustomException(ResponseCode.INVALID_DATA);
        }
        return userService.changeFanId(userId, fanId);
}

    @PostMapping("/profile/withdraw") // 예매내역 구현 후 예매내역 존재하는지 확인 후에 탈퇴하기
    public ResponseDto<?> withdraw(Authentication authentication, @RequestBody PasswordDto passwordDto) throws CustomException {
        int userId = getUserIdFromAuthentication(authentication);
        return userService.canWithdraw(userId, passwordDto.getPassword());
    }

    private int getUserIdFromAuthentication(Authentication authentication) throws CustomException {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof JwtUserDetails)) {
            throw new CustomException(ResponseCode.INVALID_TOKEN);
        }
        JwtUserDetails userDetails = (JwtUserDetails) principal;
        return userDetails.getUserId();
    } //todo: aspect 사용해서 반복 사용 객체 annotation 만들기


}
