package Utility.Commands;

import Utility.Sockets.SocketUtility;

import java.net.Socket;

public class IntToRomanSender {

    static int sendIntToRomanResult(Socket socket, String[] args) {
        try {
            int number = Integer.parseInt(args[1]);
            if (number < 0) throw new NumberFormatException();
            return SocketUtility.sendMessage(socket, IntToRomanSender.intToRoman(number));
        } catch (NumberFormatException e) {
            return SocketUtility.sendMessage(socket, "Not a valid integer!");
        }
    }

    private static String intToRoman(int number) {
        int[] numArr = numberToArray(number);
        String romanDigit = "";
        for (int i = numArr.length - 1; i >= 0; i--) {
            romanDigit += getRomanDigits((int) (numArr[i] * Math.pow(10, i)));
        }
        return romanDigit;
    }

    private static int[] numberToArray(int number) {
        int[] numArr = new int[getNumberSize(number)];
        for (int i = 0; i < numArr.length; i++, number /= 10) {
            numArr[i] = number % 10;
        }
        return numArr;
    }

    private static int getNumberSize(int number) {
        int size = 0;
        for (int remainder = number; remainder > 0; remainder /= 10, size++);
        return size;
    }

    private static String getRomanDigits(int number) {
        int[] numberDelimiters = {1000, 500, 100, 50, 10, 5, 1};
        for (int i = 0; i < numberDelimiters.length; i++) {
            if (number >= numberDelimiters[i]) {
                return getStringOfSequencingChars(number, numberDelimiters[i], i);
            }
        }
        return "";
    }

    private static String getStringOfSequencingCharsBelowTen(int number) {
        String returnString = "";
        returnString += number % 9 < 5 ? "I" : "V";
        returnString += number == 9 ? "X" : (number == 4 ? "V" : "I");
        returnString += number == 3 || number == 7 ? "I" : (number == 8 ? "II" : "");
        return returnString;
    }

    private static String getStringOfSequencingChars(int number, int increment, int arrayIndex) {
        if (number < 10) return getStringOfSequencingCharsBelowTen(number);
        char[] charDelimiter = {'M', 'D', 'C', 'L', 'X', 'V', 'I'};
        String returnValue = "";
        for (int j = 0; j < number; j += increment) {
            returnValue += charDelimiter[arrayIndex];
        }
        return returnValue;
    }

}
