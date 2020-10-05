package main.java.Utility.Commands;

import main.java.Utility.Sockets.SocketUtility;

import java.net.Socket;

public class TranslatedNumberSender {

    static int sendTranslatedNumber(Socket socket, String num, int from, int to) {
        try {
            return SocketUtility.sendMessage(socket, TranslatedNumberSender.getTranslatedNumber(num, from, to));
        } catch (NumberFormatException e) {
            return SocketUtility.sendMessage(socket, "Invalid number system or number to big!");
        }
    }

    public static String getTranslatedNumber(String num, int from, int to) throws NumberFormatException {
        int decimalNumber = Integer.parseInt(num, from);
        return Integer.toString(decimalNumber, to);
    }

}
