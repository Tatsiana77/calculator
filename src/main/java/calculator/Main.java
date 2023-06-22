package calculator;

/**
 * author Tatiana Voitekhovitch
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение: : ");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат : " + result);
        } catch (Exception e) {
            System.out.println("Неверный ввод : " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Выполняет арифметическое выражение между двумя операндами и возвращает результат в виде строки.
     * Поддерживаемые операции: +, -, *, /
     *
     * @param input арифметическое выражение
     * @return результат вычисления
     * @throws IllegalArgumentException при неверном формате ввода или неподдерживаемой операции
     */

    public static String calc(String input) throws Exception {
// Разделяем ввод на операнды и оператор
        String[] parts = input.split("\\ s + ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат ввода. Формат: операнд оператор операнд !");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        int num1;
        int num2;

        // Парсим операнды
        try {
            num1 = parseOperand(operand1);
            num2 = parseOperand(operand2);
        } catch (Exception e) {
            throw new Exception("Invalid operands!");
        }

        // Выполняем арифметическую операцию в зависимости от оператора
        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемый оператор. Поддерживаемые операторы: +, -, *, /");
        }

        return formatResult(result, isRoman(operand1) && isRoman(operand2));
    }

    /**
     * Преобразует операнд в целое число.
     *
     * @param operand операнд
     * @return преобразованное число
     * @throws IllegalArgumentException при неверном формате операнда
     */
    private static int parseOperand(String operand) throws Exception {
        if (isRoman(operand)) {
            return romanToDecimal(operand);
        } else if (isNumeric(operand)) {
            int num = Integer.parseInt(operand);
            if (num < 1 || num > 10) {
                throw new IllegalArgumentException("Неверный операнд. ");
            }
            return num;
        } else {
            throw new IllegalArgumentException("Неверный операнд!");
        }
    }

    /**
     * Проверяет, является ли переданная строка римским числом.
     *
     * @param input проверяемая строка
     * @return true, если строка является римским числом, иначе false
     */

    private static boolean isRoman(String input) {
        // Римские числа состоят только из символов I, V, X, L, C, D, M
        return input.matches("[IVXLCDM]+");
    }

    /**
     * Проверяет, является ли переданная строка числом.
     *
     * @param input проверяемая строка
     * @return true, если строка является числом, иначе false
     */
    private static boolean isNumeric(String input) {
        // Числа состоят только из цифр
        return input.matches("\\d+");
    }

    /**
     * Преобразует римское число в десятичное.
     *
     * @param roman римское число
     * @return десятичное число
     */
    private static int romanToDecimal(String roman) {
        int decimal = 0;
        int prevValue = 0;
// Проходимся по символам римского числа и вычисляем десятичное значение
        for (int i = roman.length() - 1; i >= 0; i--) {
            for (int i = roman.length() - 1; i >= 0; i--) {
                int currValue = getRomanValue(roman.charAt(i));

                if (currValue < prevValue) {
                    decimal -= currValue;
                } else {
                    decimal += currValue;
                    prevValue = currValue;
                }
            }

            return decimal;
        }
        /**
         * Возвращает десятичное значение символа римского числа.
         *
         * @param symbol символ римского числа
         * @return десятичное значение символа
         * @throws IllegalArgumentException при некорректном символе
         */
        private static int getRomanValue ( char symbol){
            switch (symbol) {
                case 'I':
                    return 1;
                case 'V':
                    return 5;
                case 'X':
                    return 10;
                case 'L':
                    return 50;
                case 'C':
                    return 100;
                case 'D':
                    return 500;
                case 'M':
                    return 1000;
                default:
                    throw new IllegalArgumentException("Некорректный символ римского числа: " + symbol);
            }
        }


        /**
         * Форматирует результат вычисления в соответствии с типом операндов.
         * Если операнды римские числа, то результат будет возвращен в виде римского числа.
         *
         * @param result результат вычисления
         * @return отформатированный результат
         */

        private static String formatResult ( int result, boolean isRoman){
            if (isRoman) {
                if (result <= 0) {
                    throw new IllegalArgumentException("Результат меньше или равен нулю. Для римских чисел должны быть только положительные числа.");
                }
                return decimalToRoman(result);
            } else {
                return String.valueOf(result);
            }
        }
        /**
         * Преобразует десятичное число в римское.
         *
         * @param number десятичное число
         * @return римское число
         */
        public static String decimalToRoman ( int number){
            if (number < 1 || number > 3999) {
                throw new IllegalArgumentException("Number must be between 1 and 3999.");
            }
            // Массив значений римских чисел
            int[] decimalValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
            // Массив символов римских чисел
            String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
            StringBuilder romanNumeral = new StringBuilder();

// Проходимся по значениям римских чисел и добавляем нужное количество символов в римскую строку
            for (int i = 0; i < decimalValues.length; i++) {
                while (number >= decimalValues[i]) {
                    romanNumeral.append(romanSymbols[i]);
                    number -= decimalValues[i];
                }
            }

            return romanNumeral.toString();
        }

    }

