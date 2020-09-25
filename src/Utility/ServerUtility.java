package Utility;

import javax.naming.directory.InvalidAttributesException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class ServerUtility {

    public static int executeCommand(Socket socket, String[] args) {
        String command = args[0].toLowerCase();
        switch (command) {
            case "exit": return -1;
            case "help": return ServerUtility.sendHelpMessage(socket);
            case "itr":
                if (args.length >= 2)
                    return ServerUtility.sendIntToRomanResult(socket, args); // int to roman number
            case "tz":
                if (args.length >= 2)
                    return ServerUtility.sendTimeZone(socket, args[1].toLowerCase()); // Timezone
            case "ca":
                if (args.length >= 3)
                    return ServerUtility.sendCalculationResult(socket, args);
            case "tn":
                if (args.length >= 4)
                    return ServerUtility.sendTranslatedNumber(socket, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])); // Number, Source system, Destination system
        }
        SocketUtility.sendMessage(socket, "Invalid command! Type 'help' for more information");
        return 0;
    }



    private static int sendHelpMessage(Socket socket) {
        return SocketUtility.sendMessage(socket, "help - Shows this message \n" +
                "tz <zone> - Prints the time in the given timezone \n" +
                "tn <number> <start system> <end system> - Translates the given number form the starting system into the end system \n" +
                "ca <number> <operator> <number> <operator> ... - Calculates the given calculation. Valid operators are + - * / % \n" +
                "itr <number> - Prints the roman equivalent of the given number > 0 \n" +
                "exit - Disconnect");
    }



    private static int sendTimeZone(Socket socket, String timeZone) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(ServerUtility.getTimeZone(timeZone)));

        return SocketUtility.sendMessage(socket, df.format(new Date()));
    }

    private static String getTimeZone(String timeZone) {
        int indexSlash = timeZone.indexOf('/');
        // First char to upper, substring of first word, second word first char to upper, second substring
        return Character.toUpperCase(timeZone.charAt(0)) + timeZone.substring(1, indexSlash + 1) +
                Character.toUpperCase(timeZone.charAt(indexSlash + 1)) + timeZone.substring(indexSlash + 2);
    }



    private static int sendTranslatedNumber(Socket socket, String num, int from, int to) {
        try {
            return SocketUtility.sendMessage(socket, ServerUtility.getTranslatedNumber(num, from, to));
        } catch (NumberFormatException e) {
            return SocketUtility.sendMessage(socket, "Invalid number system or number to big!");
        }
    }

    private static String getTranslatedNumber(String num, int from, int to) throws NumberFormatException {
        int decimalNumber = Integer.parseInt(num, from);
        return Integer.toString(decimalNumber, to);
    }



    private static int sendCalculationResult(Socket socket, String[] args) {
        try {
            return SocketUtility.sendMessage(socket, ServerUtility.calculate(args));
        } catch (NumberFormatException | InvalidAttributesException | ClassCastException e) {
            return SocketUtility.sendMessage(socket, "Invalid calculation!");
        }
    }

    private static String calculate(String[] args) throws NumberFormatException, InvalidAttributesException, ClassCastException {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
        ServerUtility.calculateOperators(argList, new ArrayList<>(Arrays.asList("*", "/", "%")));
        ServerUtility.calculateOperators(argList, new ArrayList<>(Arrays.asList("+", "-")));
        return argList.get(0);
    }

    private static void calculateOperators(ArrayList<String> argList, ArrayList<String> operators) throws InvalidAttributesException, NumberFormatException {
        for (int i = 0; i < argList.size(); i++)
            if (operators.contains(argList.get(i)))
                ServerUtility.replaceWithResult(argList, i--);
    }

    private static void replaceWithResult(ArrayList<String> argList, int index) throws InvalidAttributesException, NumberFormatException {
        argList.set(index, ServerUtility.getInterimResult(argList.get(index - 1), argList.get(index + 1), argList.get(index)));
        if (index + 1 < argList.size()) argList.remove(index + 1);
        if (index - 1 >= 0) argList.remove(index - 1);
    }

    private static String getInterimResult(String firstNumberString, String secondNumberString, String operator) throws InvalidAttributesException, ArithmeticException, NumberFormatException {
        double firstNumber = Double.parseDouble(firstNumberString);
        double secondNumber = Double.parseDouble(secondNumberString);
        switch (operator) {
            case "+": return String.valueOf(firstNumber + secondNumber);
            case "-": return String.valueOf(firstNumber - secondNumber);
            case "*": return String.valueOf(firstNumber * secondNumber);
            case "/": return String.valueOf(firstNumber / secondNumber);
            case "%": return String.valueOf(firstNumber % secondNumber);
        }
        throw new InvalidAttributesException();
    }



    private static int sendIntToRomanResult(Socket socket, String[] args) {
        try {
            int number = Integer.parseInt(args[1]);
            if (number < 0) throw new NumberFormatException();
            return SocketUtility.sendMessage(socket, ServerUtility.intToRoman(number));
        } catch (NumberFormatException e) {
            return SocketUtility.sendMessage(socket, "Not a valid integer!");
        }
    }

    private static String intToRoman(int number) {
        int[] numArr = numberToArray(number);
        String romanDigit = "";
        for (int i = numArr.length - 1; i >= 0; i--)
            romanDigit += getRomanDigits((int) (numArr[i] * Math.pow(10, i)));
        return romanDigit;
    }

    private static int[] numberToArray(int number) {
        int[] numArr = new int[getNumberSize(number)];
        for (int i = 0; i < numArr.length; i++, number /= 10)
            numArr[i] = number % 10;
        return numArr;
    }

    static int getNumberSize(int number) {
        int size = 0;
        for (int remainder = number; remainder > 0; remainder /= 10)
            size++;
        return size;
    }

    static String getRomanDigits(int number) {
        int[] numberDelimiters = {1000, 500, 100, 50, 10, 5, 1};
        for (int i = 0; i < numberDelimiters.length; i++)
            if (number >= numberDelimiters[i])
                return getStringOfSequencingChars(number, numberDelimiters[i], i);
        return "";
    }

    static String getStringOfSequencingCharsBelowTen(int number) {
        String returnString = "";
        returnString += number % 9 < 5 ? "I" : "V";
        returnString += number == 9 ? "X" : (number == 4 ? "V" : "I");
        returnString += number == 3 || number == 7 ? "I" : (number == 8 ? "II" : "");
        return returnString;
    }

    static String getStringOfSequencingChars(int number, int increment, int arrayIndex) {
        if (number < 10) return getStringOfSequencingCharsBelowTen(number);
        char[] charDelimiter = {'M', 'D', 'C', 'L', 'X', 'V', 'I'};
        String returnValue = "";
        for (int j = 0; j < number; j += increment)
            returnValue += charDelimiter[arrayIndex];
        return returnValue;
    }



}
