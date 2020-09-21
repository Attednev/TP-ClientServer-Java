package Server;

import Utility.ServerUtility;
import Utility.SocketUtility;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    protected ClientHandler(Socket socket) {
        this.socket = socket;
        this.sendInitialMessage();
    }

    private void sendInitialMessage() {
        ServerUtility.executeCommand(this.socket, new String[]{"help"});
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

            System.out.println("<Client> " + clientMessage);
            String[] commandArguments = clientMessage.split(" ");

            int successValue = ServerUtility.executeCommand(this.socket, commandArguments);
            if (successValue == -1) return;
        }
    }

}
