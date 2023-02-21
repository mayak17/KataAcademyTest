import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        String inputString = console.nextLine();
        System.out.println(calc(inputString));
    }

    static char[] allSign = new char[]{'/', '+', '-', '*'};
    static String[] allRomanNumber = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    enum Numeral {
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);
        final int weight;
        Numeral(int weight) {
            this.weight = weight;
        }
    }

    static String roman(long n) {

        if( n <= 0) {
            throw new IllegalArgumentException("В римской системе нет отрицательных чисел и нуля!");
        }

        StringBuilder buf = new StringBuilder();

        final Numeral[] values = Numeral.values();
        for (int i = values.length - 1; i >= 0; i--) {
            while (n >= values[i].weight) {
                buf.append(values[i]);
                n -= values[i].weight;
            }
        }
        return buf.toString();
    }

    static int getRomanIntNumber(String Number){
        int romanNumber = 0;
        boolean isFound = false;
        for (int i = 0; i < 10; i++) {
            romanNumber++;
            if (Number.equals(allRomanNumber[i])) {
                isFound = true;
                break;
            }
        }
        if(!isFound)
            throw new ArithmeticException("Недопустимое вводимое число! Допустимые римские числа: [I-X]");

        return romanNumber;
    }
    static int getIndexSign(String inputStr) {
        int index = -1;
        for (char c : allSign) {
            index = inputStr.indexOf(c);
            if (index >= 0)
                break;
        }
        return index;
    }

    static boolean isRomanNumber(String inputStr){
        return (inputStr.charAt(0) == 'I' || inputStr.charAt(0) == 'V' || inputStr.charAt(0) == 'X');
    }

    static int getInt(String numberString){
        return (isRomanNumber(numberString)) ? getRomanIntNumber(numberString): Integer.parseInt(numberString);
    }
    public static String calc(String input) {

        String firstNumber;
        String secondNumber;
        char sign;
        int indexSign;
        int result = -1;
        int firstNumberInt;
        int secondNumberInt;

        indexSign = getIndexSign(input);

        if(indexSign == -1)
            throw new ArithmeticException("Cтрока не является математической операцией");

        sign = input.charAt(indexSign);

        firstNumber = input.substring(0, indexSign).trim();
        secondNumber = input.substring(indexSign + 1).trim();

        if(getIndexSign(secondNumber)>=0)
            throw new ArithmeticException("формат математической операции не удовлетворяет заданию " +
                    "- два операнда и один оператор (+, -, /, *)");

        firstNumberInt = getInt(firstNumber);
        secondNumberInt = getInt(secondNumber);

        if(firstNumberInt < 1 || firstNumberInt > 10 || secondNumberInt < 1 || secondNumberInt > 10)
            throw new ArithmeticException("Недопустимое вводимое число! Допустимые арабские числа: [1-10]");


        if(isRomanNumber(firstNumber)!= isRomanNumber(secondNumber))
            throw new ArithmeticException("используются одновременно разные системы счисления!");

        if(isRomanNumber(firstNumber) && firstNumberInt < secondNumberInt)
            throw new ArithmeticException("В римской системе нет отрицательных чисел!");

        switch (sign) {
            case ('/') -> result = firstNumberInt / secondNumberInt;
            case ('*') -> result = firstNumberInt * secondNumberInt;
            case ('+') -> result = firstNumberInt + secondNumberInt;
            case ('-') -> result = firstNumberInt - secondNumberInt;
        }
        if(isRomanNumber(firstNumber) && isRomanNumber(secondNumber))
            return roman(result);
        else
            return String.valueOf(result);
    }
}