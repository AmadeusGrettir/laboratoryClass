package LR1;

public abstract class Calculations {

    public String addition(String a, String b) {
        //метод, выполняющий сложение
        return null;
    }

    public String subtraction(String a, String b) {
        //метод, выполняющий вычитание
        return null;
    }

    public String multiplication(String a, String b){
        //метод, выполняющий умножение
        return null;
    }

    public String division(String a, String b){
        //метод выполняющий деление
        return null;
    }

    public boolean isLessThenTen(String x){
        //Проверка входного условия
        return false;
    }

    public String chooseOperation(char operator, Calculations calculator, String first, String second){
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
