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
        try {
            ServerUtility.executeCommand(this.clientSocket, new String[]{"help"});
        } catch (IOException e) {
            System.out.println("<Server> Client not reachable");
        }
    }

    @Override
    public void run() {
        while (true) {
            String[] commandArguments;
            try {
                if (this.clientSocket.getInputStream().read() == -1) break;
                String clientMessage = SocketUtility.readMessage(this.clientSocket);
                System.out.println("<Client> " + clientMessage);
                commandArguments = clientMessage.split(" ");
            } catch (IOException e) {
                System.out.println("<Server> Error: Failed to read user input");
                continue;
            }

            try {
                if (ServerUtility.executeCommand(this.clientSocket, commandArguments) == -1) break;
            } catch(IOException e) {
                try {
                    SocketUtility.sendMessage(this.clientSocket, "Der Befehl war ungültig. Bitte stellen Sie sicher, " +
                            "dass der Befehl richtig geschrieben wurde und dass sie die richtige anzahl an argumenten haben!");
                } catch (IOException ignore) {}
            }
        }
        try {
            this.clientSocket.close();
        } catch (IOException ignore) {
            System.out.println("Error while closing the connection");
        }
    }
}
