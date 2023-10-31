
package LR1;

public class ArabicCalcs extends Calculations{

    public void printHello(){
        System.out.println("Hello");
    }
    @Override
    public String addition(String a, String b) {
        int aInt = Integer.valueOf(a);
        int bInt = Integer.valueOf(b);
        return String.valueOf(aInt + bInt);
    }

    @Override
    public String subtraction(String a, String b) {
        int aInt = Integer.valueOf(a);
        int bInt = Integer.valueOf(b);
        return String.valueOf(aInt - bInt);
    }

    @Override
    public String multiplication(String a, String b) {
        int aInt = Integer.valueOf(a);
        int bInt = Integer.valueOf(b);
        return String.valueOf(aInt * bInt);
    }

    @Override
    public String division(String a, String b) {
        if (b.equals("0")){
            return "Деление на ноль запрещено";
        }
        int aInt = Integer.valueOf(a);
        int bInt = Integer.valueOf(b);
        return String.valueOf(aInt / bInt);
    }

    @Override
    public boolean isLessThenTen(String x){
        int xInt = Integer.valueOf(x);
        if (xInt > 10){
            return false;
        } else {
            return true;
        }
    }
}

