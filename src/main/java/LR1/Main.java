package LR1;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
    //Считывание из консоли арифметического выражения
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        sc.close();

        String first = "";
        char operator = 0;
        String second = "";

    //Парсинг входной строки
        boolean flag = false;
        for (int i = 0; i < line.length(); i++) {
            char temp = line.charAt(i);
            if (!flag){
                if (temp == '+' || temp == '-' || temp == '*' || temp == '/'){
                    operator = temp;
                    flag = true;
                } else {
                    first += temp;
                }
            } else {
                second += temp;
            }
        }
        first = first.trim();
        second = second.trim();

        String answer = null;
    //Определение системы счисления
        if (NumberIdentifier.isArabicNumber(first) && NumberIdentifier.isArabicNumber(second)){
            Calculations calculator = new ArabicCalcs();

            if (calculator.isLessThenTen(first) && calculator.isLessThenTen(second)){
                answer = calculator.chooseOperation(operator, calculator, first, second);
            } else {
                answer = "Неверный формат чисел (> 10)";
            }
        } else if (NumberIdentifier.isRomanNumber(first) && NumberIdentifier.isRomanNumber(second)) {
            Calculations calculator = new RomanCalcs();
            if (calculator.isLessThenTen(first) && calculator.isLessThenTen(second)) {
                answer = calculator.chooseOperation(operator, calculator, first, second);
            } else {
                answer = "Неверный формат чисел (> 10)";
            }
        } else {
            answer = "Неверный формат чисел";
        }

    //Вывод в консоль результата расчётов
        System.out.println(answer);
    }



}
