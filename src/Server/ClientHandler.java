package Server;

import Utility.ServerUtility;
import Utility.SocketUtility;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    protected ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.sendInitialMessage();
    }

    private void sendInitialMessage() {
        ServerUtility.executeCommand(this.clientSocket, new String[]{"help"});
    }

    @Override
    public void run() {
        while (true) {
            String clientMessage = SocketUtility.readMessage(this.clientSocket);
            if (clientMessage == null) continue; // Error reading TODO: break or continue

            System.out.println("<Client> " + clientMessage);
            String[] commandArguments = clientMessage.split(" ");

            int successValue = ServerUtility.executeCommand(this.clientSocket, commandArguments);
            if (successValue == -1) break;
        }
        try {
            this.clientSocket.close();
        } catch (IOException ignore) {
            System.out.println("Error while closing the connection");
        }
    }
}
