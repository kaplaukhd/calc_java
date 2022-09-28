import java.util.*;

import static java.lang.Integer.parseInt;

class Main {
    static String userInput;
    static String[] actions = {"+", "-", "/", "*"};
    static TreeMap<Character, Integer> romanNumbers = new TreeMap<>();
    static TreeMap<Integer, String> arabianNumbers = new TreeMap<>();
    static int firstNumber, secondNumber;
    static boolean isRomanNumbers;
    static String stringResult;
    static int intResult;

    public static void main(String[] args) throws Exception {
        System.out.print("Введите выражение: ");
        Scanner reader = new Scanner(System.in);
        userInput = reader.nextLine();
        String resultStr = calc(userInput);//
        System.out.println("Результат: " + resultStr);
    }


    public static String calc(String input) throws Exception {
        String scannerInput1 = input.replaceAll("\\s+", "");

        int index = -1;
        for (int i = 0; i < actions.length; i++) {
            if (scannerInput1.contains(actions[i])) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IllegalArgumentException("Строка не является математической операцией");
        }

        String[] strArr = scannerInput1.split("[+-/*]");

        if (strArr.length > 2) {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }


        if (isRoman(strArr[0]) == isRoman(strArr[1])) {
            boolean isArabianNumbers = isRoman(strArr[0]);
            if (!isArabianNumbers) {
                firstNumber = parseInt(strArr[0]);
                secondNumber = parseInt(strArr[1]);
            } else {
                firstNumber = romanToInt(strArr[0]);
                secondNumber = romanToInt(strArr[1]);
                isRomanNumbers = true;
            }
        } else {
            throw new Exception("Ошибка! Используются одновременно разные системы счисления");
        }


        if (firstNumber > 10 || secondNumber > 10) {
            throw new Exception("Числа должны быть в диапазоне от 1 до 10");
        }


        switch (actions[index]) {
            case "+" -> intResult = firstNumber + secondNumber;
            case "-" -> intResult = firstNumber - secondNumber;
            case "*" -> intResult = firstNumber * secondNumber;
            case "/" -> {
                if (secondNumber == 0) {
                    throw new ArithmeticException("на ноль делить нельзя");
                }
                intResult = firstNumber / secondNumber;
            }
        }

        if (isRomanNumbers && intResult <= 0) {
            throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
        }

        if (isRomanNumbers) {
            stringResult = intToRoman(intResult);
        } else {
            stringResult = String.valueOf(intResult);
        }
        return stringResult;
    }


    public static int romanToInt(String s) {
        int end = s.length() - 1;
        char[] arr = s.toCharArray();
        int arabian;
        int result = getArabian(arr[end]);
        for (int i = end - 1; i >= 0; i--) {
            arabian = getArabian(arr[i]);
            if (arabian < getArabian(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;
    }

    public static int getArabian(char roman) {
        if ('I' == roman) return 1;
        else if ('V' == roman) return 5;
        else if ('X' == roman) return 10;
        else if ('L' == roman) return 50;
        else if ('C' == roman) return 100;
        else if ('D' == roman) return 500;
        else if ('M' == roman) return 1000;
        return 0;

    }

    public static boolean isRoman(String number) {
        romanNumbers.put('I', 1);
        romanNumbers.put('V', 5);
        romanNumbers.put('X', 10);
        romanNumbers.put('L', 50);
        romanNumbers.put('C', 100);
        return romanNumbers.containsKey(number.charAt(0));
    }

    public static String intToRoman(int number) {
        arabianNumbers.put(1000, "M");
        arabianNumbers.put(900, "CM");
        arabianNumbers.put(500, "D");
        arabianNumbers.put(400, "CD");
        arabianNumbers.put(100, "C");
        arabianNumbers.put(90, "XC");
        arabianNumbers.put(50, "L");
        arabianNumbers.put(40, "XL");
        arabianNumbers.put(10, "X");
        arabianNumbers.put(9, "IX");
        arabianNumbers.put(5, "V");
        arabianNumbers.put(4, "IV");
        arabianNumbers.put(1, "I");

        StringBuilder roman = new StringBuilder();
        int arabianKey;
        do {
            arabianKey = arabianNumbers.floorKey(number);
            roman.append(arabianNumbers.get(arabianKey));
            number -= arabianKey;
        } while (number != 0);
        return roman.toString();
    }


}


