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
            case "exit":
                return -1;
            case "help":
                return ServerUtility.sendHelpMessage(socket);
            case "tz":
                if (args.length >= 2) {
                    String timeZone = args[1];
                    return ServerUtility.sendTimeZone(socket, timeZone);
                }
                break;
            case "tn":
                if (args.length >= 4) {
                    int from = Integer.parseInt(args[2]);
                    int to = Integer.parseInt(args[3]);
                    String number = args[1];
                    return ServerUtility.sendTranslatedNumber(socket, number, from, to);
                }
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
        int indexCountry = timeZone.indexOf('/') + 1;
        char continentFirstChar = Character.toUpperCase(timeZone.charAt(0));
        char countryFirstChar = Character.toUpperCase(timeZone.charAt(0));
        String continentSubString = timeZone.substring(1, indexCountry).toLowerCase();
        String countrySubString = timeZone.substring(indexCountry + 1).toLowerCase();
        String timeZoneCamelCase = continentFirstChar + continentSubString + countryFirstChar + countrySubString;

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(timeZoneCamelCase));

        return SocketUtility.sendMessage(socket, df.format(new Date()));
    }

    private static int sendTranslatedNumber(Socket socket, String num, int from, int to) {
        try {
            int dec = Integer.parseInt(num, from);
            String number = Integer.toString(dec, to);
            return SocketUtility.sendMessage(socket, number);
        } catch (NumberFormatException e) {
            return SocketUtility.sendMessage(socket, "Invalid number system!");
        }
    }

}
