package LR1;

public abstract class Calculations {

    //метод, выполняющий сложение
    public abstract String addition(String a, String b);

    //метод, выполняющий вычитание
    public abstract String subtraction(String a, String b);

    //метод, выполняющий умножение
    public abstract String multiplication(String a, String b);

    //метод выполняющий деление
    public abstract String division(String a, String b);

    //Проверка входного условия
    public abstract boolean isLessThenTen(String x);


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
