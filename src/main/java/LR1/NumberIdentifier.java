package LR1;

import java.util.List;
public class NumberIdentifier {
    public static boolean isArabicNumber(String number){
        String[] arrayDigits = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < number.length(); i++) {
            String firstDigit = String.valueOf(number.charAt(i));
            boolean found = List.of(arrayDigits).contains(firstDigit);
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRomanNumber(String number){
        String[] arrayDigits = {"I", "V", "X"};
        for (int i = 0; i < number.length(); i++) {
            String firstDigit = String.valueOf(number.charAt(i));
            boolean found = List.of(arrayDigits).contains(firstDigit);
            if (!found) {
                return false;
            }
        }
        return true;
    }

}
