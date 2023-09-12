package com.fisa.tick3t.global;

import com.fisa.tick3t.domain.dto.QueryStringDto;
import com.fisa.tick3t.domain.dto.RateDto;
import com.fisa.tick3t.domain.dto.UserDto;
import com.fisa.tick3t.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fisa.tick3t.global.StatusCode.codeToStatusDesc;

@Slf4j
@Component
public class UtilFunction {


    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String from;

    public String emailMasking(String email) {
        StringBuilder masking = new StringBuilder();
        char[] chars = email.split("@")[0].toCharArray();
        int count = chars.length;
        if (count <= 2) {
            return "*".repeat(count) + "@" + email.split("@")[1];
        }
        for (char c : chars) {
            if (count == 1 || count == 2) {
                masking.append(c);
            } else {
                masking.append("*");
            }
            count -= 1;
        }
        return masking + "@" + email.split("@")[1];
    }

    public String nameMasking(String name) {
        StringBuilder masking = new StringBuilder();
        char[] chars = name.toCharArray();
        if (chars.length <= 2) {
            return "*".repeat(chars.length);
        }
        int count = 1;
        for (char c : chars) {
            if (count == 1) {
                masking.append(c);
                count += 1;
            } else if (count == chars.length) {
                masking.append(c);
                break;
            } else {
                masking.append("*");
                count += 1;
            }
        }
        return masking.toString();
    }


    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()-_=+\\\\|\\[\\]{};:'\",<.>/?])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return bCryptPasswordEncoder.matches(password, hashedPassword);
    }

    public String generatePassword() {
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digitChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";

        String allChars = lowercaseChars + uppercaseChars + digitChars + specialChars;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        password.append(digitChars.charAt(random.nextInt(digitChars.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        for (int i = 0; i < 4; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the password characters
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordArray[index];
            passwordArray[index] = passwordArray[i];
            passwordArray[i] = temp;
        }

        return new String(passwordArray);
    }

    public QueryStringDto checkQuery(String category, String word, int page, ArrayList<String> categories, int pageSize) {
        if (category != null & categories.contains(category)) {
            category = category.trim();
            category = String.valueOf(categories.indexOf(category));
        } else {
            category = null;
        }
        if (word != null) {
            word = word.trim();
        }
        if (page == 0) {
            page = 1;
        }
        int offset = (page - 1) * pageSize;
        return new QueryStringDto(category, word, page, offset);
    }

    public void calculateSalesRate(RateDto rateDto) {
        double salesRate = (double) rateDto.getSoldSeat() / rateDto.getTotalSeat();
        salesRate = Math.round(salesRate * 100.0);
        rateDto.setSalesRate(salesRate + "%");
        rateDto.setRemainSeat(rateDto.getTotalSeat() - rateDto.getSoldSeat());
    }

    public void maskingUserInfo(UserDto userDto) {
        userDto.setName(nameMasking(userDto.getName()));
        userDto.setEmail(emailMasking(userDto.getEmail()));
        userDto.setUserPwd(null);
        userDto.setRole(null);
        userDto.setStatusCd(codeToStatusDesc(userDto.getStatusCd()));
    }

    public void mailingPassword(String userEmail, String password) throws CustomException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject(Constants.defaultMailTitle);
            String body = "회원님의 임시 비밀번호는 " + password + " 입니다.";
            mimeMessageHelper.setText(body);
            javaMailSender.send(mimeMessage);
        } catch (SendFailedException e) {
            log.error(e.getMessage());
            throw new CustomException(ResponseCode.UNKNOWN_EMAIL);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            log.error("메일링 과정에서 에러가 발생했습니다. 로그인 정보나 SMTP를 점검해주세요.");
            throw new CustomException(ResponseCode.FAIL);
        }
    }

    public void isValidDate(String date) throws CustomException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // 이 설정을 통해 유효하지 않은 날짜에 대해 엄격하게 검사합니다.
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            throw new CustomException(ResponseCode.INVALID_DATA);
        }
    }

    public String isValidParam(String param) throws CustomException {
        if (param == null || param.equals("")) {
            throw new CustomException(ResponseCode.MISSING_OR_INVALID_REQUEST);
        }
        if (param.trim().equals("")) {
            throw new CustomException(ResponseCode.MISSING_OR_INVALID_REQUEST);
        }
        return param.trim();
    }

    public int isValidParam(int param) throws CustomException {
        if (param <= 0) {
            throw new CustomException(ResponseCode.INVALID_DATA);
        } else {
            return param;
        }
    }

    public int discountPrice(String grade, int price) throws CustomException {
        switch (grade) {
            case "R":
                return (int) (price * 0.95);
            case "S":
                return (int) (price * 0.85);
            case "A":
                return (int) (price * 0.75);
            default:
                throw new CustomException(ResponseCode.INVALID_DATA);
        }

    }
}
