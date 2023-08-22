package com.fisa.tick3t.global;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilFunction {

    public String emailMasking(String email){
        String masking;
        masking = email.split("@")[0];
        if(masking.length() > 3){
            masking = "";
        } else if (masking.length() ==3) {
            masking = "";
        } else {
            masking = "";
        }
        return masking + email.split("@")[1];

    }

    public String nameMasking(String name){
        String masking;
        if(name.length() >= 3){
            masking = name.charAt(0) + name.substring(name.length()-1);
        }
        return "";
    }

    public static boolean isValidPassword(String password){
        String regex = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()-_=+\\\\|\\[\\]{};:'\",<.>/?])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
