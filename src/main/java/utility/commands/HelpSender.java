package utility.commands;

import utility.sockets.SocketUtility;

import java.net.Socket;

public class HelpSender {

    private HelpSender() {
        throw new IllegalStateException("Utility class");
    }

    static int sendHelpMessage(Socket socket) {
        return SocketUtility.sendMessage(socket,
                "help - Shows this message \n" +
                         "tz <zone> - Prints the time in the given timezone \n" +
                         "tn <number> <start system> <end system> - Translates the given number form the starting system into the end system \n" +
                         "ca <number> <operator> <number> <operator> ... - Calculates the given calculation. Valid operators are + - * / % \n" +
                         "itr <number> - Prints the roman equivalent of the given number > 0 \n" +
                         "exit - Disconnect");
    }


}
