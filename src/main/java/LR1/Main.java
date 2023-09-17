package LR1;
import java.util.Scanner;
public class Main {

    //Считывание из консоли арифметического выражения
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String line = sc.nextLine();

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

        String answer = null;
    //Определение системы счисления
        if (NumberIdentifier.isArabicNumber(first) && NumberIdentifier.isArabicNumber(second)){
            Calculations calculator = new ArabicCalcs();
            answer = Main.chooseOperation(operator, calculator, first, second);

        } else if (NumberIdentifier.isRomanNumber(first) && NumberIdentifier.isRomanNumber(second)) {
            Calculations calculator = new RomanCalcs();
            answer = Main.chooseOperation(operator, calculator, first, second);
        } else {
            answer = "Неверный формат чисел";
        }

    //Вывод в консоль результата расчётов
        System.out.println(answer);
    }


    public static String chooseOperation(char operator, Calculations calculator, String first, String second){
        // функция для определения арифметического действия
        String answer = null;
        switch (operator) {
            case '+' -> answer = calculator.addition(first, second);
            case '-' -> answer = calculator.subtraction(first, second);
            case '*' -> answer = calculator.multiplication(first, second);
            case '/' -> answer = calculator.division(first, second);
        }
        return answer;
    }
}
