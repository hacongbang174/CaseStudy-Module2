package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private static  SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
    public static Date parseDate(String strDate) {
        try {
            return simpleDateFormat1.parse(strDate);
        } catch (ParseException e) {
            System.out.println("Invalid format");
            return null;
        }
    }
    public static String convertDateToString(Date date) {
        return simpleDateFormat1.format(date);
    }
    public static Date parseDate2(String strDate) {
        try {
            return simpleDateFormat2.parse(strDate);
        } catch (ParseException e) {
            System.out.println("Invalid format");
            return null;
        }
    }
    public static String convertDateToString2(Date date) {
        return simpleDateFormat2.format(date);
    }
}
