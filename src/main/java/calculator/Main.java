package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an arithmetic expression: ");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new Exception("Invalid input expression!");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        int num1;
        int num2;

        try {
            num1 = parseOperand(operand1);
            num2 = parseOperand(operand2);
        } catch (Exception e) {
            throw new Exception("Invalid operands!");
        }

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
                    throw new Exception("Division by zero!");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Invalid operator!");
        }

        return formatResult(result, isRoman(operand1) && isRoman(operand2));
    }

    private static int parseOperand(String operand) throws Exception {
        if (isRoman(operand)) {
            return romanToDecimal(operand);
        } else if (isNumeric(operand)) {
            int num = Integer.parseInt(operand);
            if (num < 1 || num > 10) {
                throw new Exception("Numeric operand is out of range!");
            }
            return num;
        } else {
            throw new Exception("Invalid operand format!");
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVXLCDM]+");
    }

    private static boolean isNumeric(String input) {
        return input.matches("\\d+");
    }

    private static int romanToDecimal(String roman) {
        int decimal = 0;
        int prevValue = 0;

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

    private static int getRomanValue(char c) {
        switch (c) {
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
                return 0;
        }
    }

    private static String formatResult(int result, boolean isRoman) {
        if (isRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Roman numerals cannot represent zero or negative numbers!");
            }
            return decimalToRoman(result);
        } else {
            return String.valueOf(result);
        }

    }


    public static String decimalToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999.");
        }

        int[] decimalValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder romanNumeral = new StringBuilder();

        for (int i = 0; i < decimalValues.length; i++) {
            while (number >= decimalValues[i]) {
                romanNumeral.append(romanSymbols[i]);
                number -= decimalValues[i];
            }
        }

        return romanNumeral.toString();
    }

}

