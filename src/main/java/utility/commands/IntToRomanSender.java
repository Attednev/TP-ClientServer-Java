package utility.commands;

import utility.sockets.SocketUtility;

import java.net.Socket;

public class IntToRomanSender {

    private IntToRomanSender() {
        throw new IllegalStateException("Utility class");
    }

    static int sendIntToRomanResult(Socket socket, String[] args) {
        try {
            int number = Integer.parseInt(args[1]);
            if (number < 0) throw new NumberFormatException();
            return SocketUtility.sendMessage(socket, IntToRomanSender.intToRoman(number));
        } catch (NumberFormatException e) {
            return SocketUtility.sendMessage(socket, "Not a valid integer!");
        }
    }

    public static String intToRoman(int number) {
        int[] numArr = numberToArray(number);
        StringBuilder romanDigit = new StringBuilder();
        for (int i = numArr.length - 1; i >= 0; i--) {
            romanDigit.append(getRomanDigits((int) (numArr[i] * Math.pow(10, i))));
        }
        return romanDigit.toString();
    }

    public static int[] numberToArray(int number) {
        int[] numArr = new int[getNumberSize(number)];
        for (int i = 0; i < numArr.length; i++, number /= 10) {
            numArr[i] = number % 10;
        }
        return numArr;
    }

    public static int getNumberSize(int number) {
        int size = 0;
        for (int remainder = number; remainder > 0; remainder /= 10, size++);
        return size;
    }

    public static String getRomanDigits(int number) {
        int[] numberDelimiters = {1000, 500, 100, 50, 10, 5, 1};
        for (int i = 0; i < numberDelimiters.length; i++) {
            if (number >= numberDelimiters[i]) {
                return getStringOfSequencingChars(number, numberDelimiters[i], i);
            }
        }
        return "";
    }

    public static String getStringOfSequencingCharsBelowTen(int number) {
        String returnString = "";
        if (number % 9 < 5) {
            returnString += "I";
        } else {
            returnString += "V";
        }
        if (number == 9) {
            returnString += "X";
        } else if (number == 4) {
            returnString += "V";
        } else {
            returnString += "I";
        }
        if (number == 3) {
            returnString += "I";
        } else if (number == 8) {
            returnString += "II";
        }
        return returnString;
    }

    public static String getStringOfSequencingChars(int number, int increment, int arrayIndex) {
        if (number < 10) return getStringOfSequencingCharsBelowTen(number);
        char[] charDelimiter = {'M', 'D', 'C', 'L', 'X', 'V', 'I'};
        StringBuilder returnValue = new StringBuilder();
        for (int j = 0; j < number; j += increment) {
            returnValue.append(charDelimiter[arrayIndex]);
        }
        return returnValue.toString();
    }

}
