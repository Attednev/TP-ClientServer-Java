package Server;

import Utility.ServerUtility;
import Utility.SocketUtility;

import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
            try {
                String userInput = SocketUtility.readMessage(this.socket);
                System.out.println("<Server> Client command was " + userInput);
                String[] args = userInput.split(" "); // Split the command into arguments
                ServerUtility.executeCommand(this.socket, args); // Execute the command
            } catch (IOException e) {
                if (this.socket.isConnected()) {
                    System.out.println("<Server> Error: Failed to read user input"); // Error while reading
                } else {
                    // Client cut connection without "exit"
                    System.out.println("<Server> Error: Client left");
                    try {
                        this.socket.close();
                    } catch (IOException ignore) {
                    }
                    return;
                }
            }
        }
    }

}
