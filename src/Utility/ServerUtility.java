package Utility;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ServerUtility {

    public static int executeCommand(Socket socket, String[] args) {
        String command = args[0].toLowerCase();
        switch (command) {
            case "exit": return -1;
            case "help": return ServerUtility.sendHelpMessage(socket);
            case "tz":
                if (args.length >= 2)
                    return ServerUtility.sendTimeZone(socket, args[1].toLowerCase()); // Timezone
                break;
            case "tn":
                if (args.length >= 4)
                    return ServerUtility.sendTranslatedNumber(socket, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])); // Number, Source system, Destination system
                break;
        }
        SocketUtility.sendMessage(socket, "To few arguments! Type 'help' for more information");
        return 0;
    }

    private static int sendHelpMessage(Socket socket) {
        return SocketUtility.sendMessage(socket, "help - Shows this message \n" +
                "tz <zone> - Prints the time in the given timezone \n" +
                "tn <number> <start system> <end system> - Translates the given number form the starting system into the end system \n" +
                "exit - Disconnect");
    }

    private static int sendTimeZone(Socket socket, String timeZone) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(ServerUtility.getTimeZone(timeZone)));

        return SocketUtility.sendMessage(socket, df.format(new Date()));
    }

    private static int sendTranslatedNumber(Socket socket, String num, int from, int to) {
        try {
            return SocketUtility.sendMessage(socket, ServerUtility.getTranslatedNumber(num, from, to));
        } catch (NumberFormatException e) {
            return SocketUtility.sendMessage(socket, "Invalid number system!");
        }
    }

    private static String getTimeZone(String timeZone) {
        int indexSlash = timeZone.indexOf('/');
        // First char to upper, substring of first word, second word first char to upper, second substring
        return Character.toUpperCase(timeZone.charAt(0)) + timeZone.substring(1, indexSlash + 1) +
               Character.toUpperCase(timeZone.charAt(indexSlash + 1)) + timeZone.substring(indexSlash + 2);
    }

    private static String getTranslatedNumber(String num, int from, int to) throws NumberFormatException {
        int decimalNumber = Integer.parseInt(num, from);
        return Integer.toString(decimalNumber, to);
    }
}
