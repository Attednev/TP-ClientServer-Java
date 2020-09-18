package Server;

import Utility.ServerUtility;
import Utility.SocketUtility;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    protected ClientHandler(Socket socket) {
        this.socket = socket;
        // Send initial message
        try {
            ServerUtility.executeCommand(this.socket, new String[]{"help"});
        } catch (IOException e) {
            System.out.println("<Server> Client not reachable");
        }
    }

    @Override
    public void run() {
        // Start reading process
        while (true) {
            String userInput;
            try {
                userInput = SocketUtility.readMessage(this.socket);
                System.out.println("<Server> Client command was " + userInput);
            } catch (IOException e) {
                if (this.socket.isConnected()) {
                    System.out.println("<Server> Error: Failed to read user input"); // Error while reading
                } else {
                    // Client cut connection without "exit"
                    System.out.println("<Server> Error: Client left");
                    try {
                        this.socket.close();
                    } catch (IOException ignore) {}
                    return; // Connection aborted so we can end the Thread
                }
                continue;
            }
            String[] args = userInput.split(" "); // Split the command into arguments
            try {
                if (ServerUtility.executeCommand(this.socket, args) == -1) { // Execute the command
                    // User exited
                    try {
                        this.socket.close();
                    } catch (IOException ignore) {
                        System.out.println("Error closing the connection");
                    }
                    return;
                }
            } catch(IOException e) {
                // Error while executing the command
                try {
                    SocketUtility.sendMessage(this.socket, "Der Befehl war ungültig. Bitte stellen Sie sicher, dass der Befehl richtig geschrieben wurde und dass sie die richtige anzahl an argumenten haben!");
                } catch (IOException ee) {
                    if (this.socket.isConnected()) {
                        System.out.println("<Server> Error: Failed to read user input"); // Error while reading
                    } else {
                        // Client cut connection without "exit"
                        System.out.println("<Server> Error: Client left");
                        try {
                            this.socket.close();
                        } catch (IOException ignore) {}
                        return; // Connection aborted so we can end the Thread
                    }
                }
            }
        }
    }

}
