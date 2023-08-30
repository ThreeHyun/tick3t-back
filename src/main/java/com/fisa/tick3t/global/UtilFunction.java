package com.fisa.tick3t.global;

import com.fisa.tick3t.domain.dto.QueryStringDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@Component
public class UtilFunction {


    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String emailMasking(String email) {
        String masking = "";
        char[] chars = email.split("@")[0].toCharArray();
        int count = chars.length;
        for (char c : chars){
            if (count == 1 || count == 2) {
                masking += String.valueOf(c);
            }else{
                masking +=  "*";
            }
            count -= 1;
        }
        return masking +"@"+ email.split("@")[1];

    }

    public String nameMasking(String name) {
        String masking = "";
        char[] chars = name.toCharArray();
        int count = 1;
        for (char c : chars) {
            if (count == 1) {
                masking += String.valueOf(c);
                count += 1;
            } else if (count == chars.length) {
                masking += String.valueOf(c);
                break;
            } else {
                masking += "*";
                count += 1;
            }
        }
        return masking;
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
        }
        if(word != null){
            word = word.trim();
        }
        if(page == 0){
            page = 1;
        }
        int offset = (page -1) * pageSize;
        return new QueryStringDto(category, word, page, offset);
    }

    public boolean check(String param){
        boolean result = true;
        if(param == null){
            result= false;
        } else if (param.trim().equals("")) {
            result = false;
        }
        return result;
    }
}
