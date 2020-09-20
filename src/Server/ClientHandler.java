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
            // Read user input
            String[] args;
            try {
                if (this.socket.getInputStream().read() == -1) { // No connection
                    break;
                }
                // Read and split
                String userInput = SocketUtility.readMessage(this.socket);
                System.out.println("<Server> Client command was " + userInput);
                args = userInput.split(" "); // Split the command into arguments
            } catch (IOException e) {
                System.out.println("<Server> Error: Failed to read user input"); // Error while reading
                continue;
            }
            // Handle the user input
            try {
                if (ServerUtility.executeCommand(this.socket, args) == -1) { // Execute the command
                    break; // User want to abort connection
                }
            } catch(IOException e) {
                // Something went wrong during command execution
                try {
                    SocketUtility.sendMessage(this.socket, "Der Befehl war ungültig. Bitte stellen Sie sicher, " +
                            "dass der Befehl richtig geschrieben wurde und dass sie die richtige anzahl an argumenten haben!");
                } catch (IOException ignore) {}
            }
        }
        try {
            this.socket.close();
        } catch (IOException ignore) {
            System.out.println("Error while closing the connection");
        }
    }
}
