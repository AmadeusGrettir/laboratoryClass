package LR1;

import java.util.Map;
import java.util.HashMap;

public class RomanCalcs extends Calculations{


    @Override
    public String addition(String a, String b) {
        int x = translateFromRoman(a);
        int y = translateFromRoman(b);
        int sum = x+y;
        return translateToRoman(sum);
    }

    @Override
    public String subtraction(String a, String b) {
        int x = translateFromRoman(a);
        int y = translateFromRoman(b);
        int sub = x-y;
        return translateToRoman(sub);
    }

    @Override
    public String multiplication(String a, String b) {
        int x = translateFromRoman(a);
        int y = translateFromRoman(b);
        int mult = x*y;
        return translateToRoman(mult);
    }

    @Override
    public String division(String a, String b) {
        if (b == "0"){
            return "Деление на ноль запрещено";
        }
        int x = translateFromRoman(a);
        int y = translateFromRoman(b);
        int div = x*y;
        return translateToRoman(div);
    }

    public static int translateFromRoman(String x){
        Map<Character, Integer> map = new HashMap();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);

        int result = map.get(x.charAt(x.length() - 1));
        for(int i = x.length() - 2; i >= 0; i--) {
            if(map.get(x.charAt(i)) < map.get(x.charAt(i+1))) {
                result -= map.get(x.charAt(i));
            } else {
                result += map.get(x.charAt(i));
            }
        }
        return result;
    }
    public static String translateToRoman(int x){
        if (x < 1){
            return "Неположительный результат";
        }
        String[] arrayTens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        String[] arrayOnes = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String result = "";
        int tempX = x / 10;
        int tempN = x % 10;
        for (int i = 0; i < arrayTens.length; i++){
            if(i == tempX){
                result += arrayTens[i];
            }
        }
        for (int i = 0; i < arrayOnes.length; i++){
            if (i == tempN) {
                result += arrayOnes[i];
            }
        }
        return result;
    }

    @Override
    public boolean isLessThenTen(String x){
        int xInt = translateFromRoman(x);
        if (xInt > 10){
            return false;
        } else {
            return true;
        }
    }
}
