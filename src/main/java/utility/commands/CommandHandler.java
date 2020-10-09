package utility.commands;

import utility.sockets.SocketUtility;

import java.net.Socket;

public class CommandHandler {

    private CommandHandler() {
        throw new IllegalStateException("Utility class");
    }

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
                break;
            case "tz":
                if (args.length >= 2) {
                    return TimeZoneSender.sendTimeZone(socket, args[1].toLowerCase()); // Timezone
                }
                break;
            case "ca":
                if (args.length >= 3) {
                    return Calculator.sendCalculationResult(socket, args);
                }
                break;
            case "tn":
                if (args.length >= 4) {
                    return TranslatedNumberSender.sendTranslatedNumber(socket, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3])); // Number, Source system, Destination system

                }
                break;
            default: break;
        }
        SocketUtility.sendMessage(socket, "Invalid command! Type 'help' for more information");
        return 0;
    }

}
