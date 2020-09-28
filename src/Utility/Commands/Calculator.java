package Utility.Commands;

import Utility.Sockets.SocketUtility;

import javax.naming.directory.InvalidAttributesException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {

    public static int sendCalculationResult(Socket socket, String[] args) {
        try {
            return SocketUtility.sendMessage(socket, Calculator.calculate(args));
        } catch (NumberFormatException | InvalidAttributesException | ClassCastException e) {
            return SocketUtility.sendMessage(socket, "Invalid calculation!");
        }
    }

    public static String calculate(String[] args) throws NumberFormatException, InvalidAttributesException, ClassCastException {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
        Calculator.calculateOperators(argList, new ArrayList<>(Arrays.asList("*", "/", "%")));
        Calculator.calculateOperators(argList, new ArrayList<>(Arrays.asList("+", "-")));
        return argList.get(0);
    }

    public static void calculateOperators(ArrayList<String> argList, ArrayList<String> operators) throws InvalidAttributesException, NumberFormatException {
        for (int i = 0; i < argList.size(); i++) {
            if (operators.contains(argList.get(i))) {
                Calculator.replaceWithResult(argList, i--);
            }
        }
    }

    public static void replaceWithResult(ArrayList<String> argList, int index) throws InvalidAttributesException, NumberFormatException {
        argList.set(index, Calculator.getInterimResult(argList.get(index - 1), argList.get(index + 1), argList.get(index)));
        if (index + 1 < argList.size()) {
            argList.remove(index + 1);
        }
        if (index - 1 >= 0) {
            argList.remove(index - 1);
        }
    }

    public static String getInterimResult(String firstNumberString, String secondNumberString, String operator) throws InvalidAttributesException, ArithmeticException, NumberFormatException {
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

}
