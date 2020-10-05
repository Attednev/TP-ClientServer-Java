package main.java.Utility.Commands;

import main.java.Utility.Sockets.SocketUtility;

import java.net.Socket;

public class CommandHandler {

    public static int executeCommand(Socket socket, String[] args) {
        String command = args[0].toLowerCase();
        switch (command) {
            case "exit":
                return -1;
            case "help":
                return HelpSender.sendHelpMessage(socket);
            case "itr":
                if (args.length >= 2) {
                    return IntToRomanSender.sendIntToRomanResult(socket, args); // int to roman number
                }
            case "tz":
                if (args.length >= 2) {
                    return TimeZoneSender.sendTimeZone(socket, args[1].toLowerCase()); // Timezone
                }
            case "ca":
                if (args.length >= 3) {
                    return Calculator.sendCalculationResult(socket, args);
                }
            case "tn":
                if (args.length >= 4) {
                    return TranslatedNumberSender.sendTranslatedNumber(socket, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])); // Number, Source system, Destination system

                }
        }
        SocketUtility.sendMessage(socket, "Invalid command! Type 'help' for more information");
        return 0;
    }

}
