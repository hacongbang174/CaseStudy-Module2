package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
    public static String covertPriceToString(double price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        String formattedAmount = currencyFormatter.format(price);
        return formattedAmount;
    }
    public static double parseDouble(String price) {
        String priceNew = price.replaceAll("\\D+", "");
        return Double.parseDouble(priceNew);
    }

//    public static void main(String[] args) {
//        double price = 10000;
//        String price1 = CurrencyFormat.covertPriceToString(price);
//        System.out.println(price1);
//        double price2 = CurrencyFormat.parseDouble(price1);
//        System.out.println(price2);
//    }
}
