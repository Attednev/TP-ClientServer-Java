package server;

import utility.commands.CommandHandler;
import utility.sockets.SocketUtility;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    protected ClientHandler(Socket socket) {
        this.socket = socket;
        this.sendInitialMessage();
    }

    private void sendInitialMessage() {
        CommandHandler.executeCommand(this.socket, new String[]{"help"});
    }

    @Override
    public void run() {
        this.handleClient();
        SocketUtility.endConnection(this.socket);
    }

    private void handleClient() {
        while (true) {
            String clientMessage = SocketUtility.readMessage(this.socket);
            if (clientMessage == null) return;

            logger.log(Level.INFO, "<Client> {0}", clientMessage);
            String[] commandArguments = clientMessage.split(" ");

            int successValue = CommandHandler.executeCommand(this.socket, commandArguments);
            if (successValue == -1) return;
        }
    }

}
