package utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String PHONENUMBER_REGEX = "((84|0)[3|5|7|8|9])+([0-9]{8})\\b";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String CCCD_REGEX = "^0\\d{11}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    public static final String PASSWORD_REGGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}$";

    public static boolean isPhoneNumber(String number) {
        return Pattern.matches(PHONENUMBER_REGEX, number);
    }
    public static boolean isEmail(String email){
        return Pattern.matches(EMAIL_REGEX,email);
    }
    public static boolean isCCCD(String cccd){
        return Pattern.matches(CCCD_REGEX,cccd);
    }
    public static boolean isUserName(String username){
        return Pattern.matches(USERNAME_REGEX,username);
    }
    public static boolean isPassWord(String password) {
        return Pattern.matches(PHONENUMBER_REGEX, password);
    }
    public static boolean isValidPrice(double price) {
        if (price > 0 && price < 200000) {
            return true;
        }
        return false;
    }
    public static boolean isQuantity(int quantity) {
        if(quantity >= 0 && quantity <= 1000) {
            return true;
        }
        return false;
    }
}
