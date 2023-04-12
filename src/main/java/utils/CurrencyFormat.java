package utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
    public static String covertPriceToString(double price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        String formattedAmount = currencyFormatter.format(price);
        return formattedAmount;
    }
}
