package Utility;

import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ServerUtility {

    // Function that executes a given command with its arguments
    public static int executeCommand(Socket socket, String[] args) throws IOException {
        String command = args[0].toLowerCase();
        switch (command) {
            case "help":
                ServerUtility.sendHelp(socket); // Help
                break;
            case "tz":
                ServerUtility.sendTimeZone(socket, args[1]); // TimeZone
                break;
            case "tn":
                if (args.length >= 4) {
                    int from = Integer.parseInt(args[2]);
                    int to = Integer.parseInt(args[3]);
                    ServerUtility.translateNumber(args[1], from, to); // Change number system
                }
                break;
            case "exit":
                return -1;
            default:
                SocketUtility.sendMessage(socket, "Unknown command");
        }
        return 0;
    }

    // Function that sends a predefined text to the client that shows him the commands
    private static void sendHelp(Socket socket) throws IOException {
        SocketUtility.sendMessage(socket, "help - Zeige diese Anzeige an \n" +
                "tz <zone> - Zeit in der Zeitzone \n" +
                "tn <Zahl> <StartBasis> <EndBasis> - Umwandlung der Zahl <Zahl> aus dem System <StartBasis> ins System <EndBasis> \n" +
                "exit - Unterbrich die Verbindung");
    }

    // Function that sends the time and date of a specific time zone to the client
    private static void sendTimeZone(Socket socket, String timeZone) throws IOException {
        String zone = timeZone.toLowerCase();
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(zone));
        SocketUtility.sendMessage(socket, df.format(date));
    }


    private static void translateNumber(String num, int from, int to) {
        if (from != 10) {
            // Get number in decimal value
            num = String.valueOf(Integer.parseInt(num, from));
        }
        // Translate into "to"

    }



}
