package calculator;

/*
  author Tatiana Voitekhovitch
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Выполняет вычисление заданного арифметического выражения.
     *
     * @param input арифметическое выражение
     * @return результат вычисления
     * @throws IllegalArgumentException если введено некорректное выражение
     */
    public static String calc(String input) {
        String[] elements = input.split(" ");

        if (elements.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение. Ожидается два операнда и один оператор (+, -, *, /).");
        }

        String operand1 = elements[0];
        String operator = elements[1];
        String operand2 = elements[2];

        boolean isOperand1Roman = isRoman(operand1);
        boolean isOperand2Roman = isRoman(operand2);

        if (isOperand1Roman && isOperand2Roman) {
            // Оба операнда римские числа
            int decimal1 = romanToDecimal(operand1);
            int decimal2 = romanToDecimal(operand2);

            int result = performCalculation(decimal1, operator, decimal2);

            return formatResult(result);
        } else if (!isOperand1Roman && !isOperand2Roman) {
            // Оба операнда десятичные числа
            int number1 = Integer.parseInt(operand1);
            int number2 = Integer.parseInt(operand2);

            int result = performCalculation(number1, operator, number2);

            return Integer.toString(result);
        } else {
            throw new IllegalArgumentException("Нельзя выполнять операции сразу с римскими и арабскими числами.");
        }
    }

    /**
     * Проверяет, является ли строка римским числом.
     *
     * @param input строка для проверки
     * @return true, если строка римское число, иначе false
     */
    private static boolean isRoman(String input) {
        String romanNumerals = "IVXLCDM";

        for (char symbol : input.toCharArray()) {
            if (romanNumerals.indexOf(symbol) == -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Выполняет преобразование римского числа в десятичное.
     *
     * @param roman римское число
     * @return десятичное число
     * @throws IllegalArgumentException если римское число некорректно
     */
    private static int romanToDecimal(String roman) {
        int decimal = 0;
        int prevValue = 0;

        // Проходимся по символам римского числа и вычисляем десятичное значение
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = getRomanValue(roman.charAt(i));

            if (value < prevValue) {
                decimal -= value;
            } else {
                decimal += value;
                prevValue = value;
            }
        }

        return decimal;
    }

    /**
     * Возвращает числовое значение символа римской цифры.
     *
     * @param symbol символ римской цифры
     * @return числовое значение символа
     * @throws IllegalArgumentException если символ римской цифры некорректен
     */
    private static int getRomanValue(char symbol) {
        return switch (symbol) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> throw new IllegalArgumentException("Некорректный символ римской цифры: " + symbol);
        };
    }

    /**
     * Выполняет арифметическую операцию между двумя числами.
     *
     * @param operand1 первый операнд
     * @param operator арифметический оператор (+, -, *, /)
     * @param operand2 второй операнд
     * @return результат операции
     * @throws IllegalArgumentException если оператор некорректен или деление на ноль
     */
    private static int performCalculation(int operand1, String operator, int operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль невозможно.");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Некорректный оператор: " + operator);
        }
    }

    /**
     * Форматирует результат вычисления. Если результат отрицательный или ноль,
     * возвращает исключение, так как в римской системе счисления отрицательные числа не представлены.
     *
     * @param result результат вычисления
     * @return отформатированный результат
     * @throws IllegalArgumentException если результат отрицательный или ноль
     */
    private static String formatResult(int result) {
        if (result <= 0) {
            throw new IllegalArgumentException("В римской системе счисления нет отрицательных чисел или ноля.");
        }

        return decimalToRoman(result);
    }

    /**
     * Выполняет преобразование десятичного числа в римскую строку.
     *
     * @param decimal десятичное число
     * @return римская строка
     * @throws IllegalArgumentException если число не находится в диапазоне от 1 до 3999
     */
    private static String decimalToRoman(int decimal) {
        if (decimal <= 0 || decimal >= 4000) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999.");
        }

        StringBuilder roman = new StringBuilder();
        int[] decimalValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < decimalValues.length; i++) {
            while (decimal >= decimalValues[i]) {
                roman.append(romanSymbols[i]);
                decimal -= decimalValues[i];
            }
        }

        return roman.toString();
    }
}


