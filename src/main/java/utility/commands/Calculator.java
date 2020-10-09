package utility.commands;

import utility.sockets.SocketUtility;

import javax.naming.directory.InvalidAttributesException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

    private Calculator() {
        throw new IllegalStateException("Utility class");
    }

    public static int sendCalculationResult(Socket socket, String[] args) {
        try {
            return SocketUtility.sendMessage(socket, Calculator.calculate(args));
        } catch (NumberFormatException | InvalidAttributesException | ClassCastException e) {
            return SocketUtility.sendMessage(socket, "Invalid calculation!");
        }
    }

    public static String calculate(String[] args) throws InvalidAttributesException {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
        Calculator.calculateOperators(argList, new ArrayList<>(Arrays.asList("*", "/", "%")));
        Calculator.calculateOperators(argList, new ArrayList<>(Arrays.asList("+", "-")));
        return argList.get(0);
    }

    public static void calculateOperators(List<String> argList, List<String> operators) throws InvalidAttributesException {
        int i = 0;
        while (i < argList.size()) {
            if (!operators.contains(argList.get(i))) {
                i++;
                continue;
            }
            Calculator.replaceWithResult(argList, i);
        }
    }

    public static void replaceWithResult(List<String> argList, int index) throws InvalidAttributesException {
        argList.set(index, Calculator.getInterimResult(argList.get(index - 1), argList.get(index + 1), argList.get(index)));
        if (index + 1 < argList.size()) {
            argList.remove(index + 1);
        }
        argList.remove(index - 1);
    }

    public static String getInterimResult(String firstNumberString, String secondNumberString, String operator) throws InvalidAttributesException {
        double firstNumber = Double.parseDouble(firstNumberString);
        double secondNumber = Double.parseDouble(secondNumberString);
        switch (operator) {
            case "+": return String.valueOf(firstNumber + secondNumber);
            case "-": return String.valueOf(firstNumber - secondNumber);
            case "*": return String.valueOf(firstNumber * secondNumber);
            case "/": return String.valueOf(firstNumber / secondNumber);
            case "%": return String.valueOf(firstNumber % secondNumber);
            default: throw new InvalidAttributesException();
        }
    }

}
