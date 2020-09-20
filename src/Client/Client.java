package Client;

import Utility.SocketUtility;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class Client implements Runnable {
    private Socket clientSocket = null;

    protected Client(String server, int port) {
        try {
            // Bind to server
            this.clientSocket = new Socket(server, port);
            System.out.println("<System> Successfully connected to the server");
        } catch (IOException e) {
            System.out.println("<System> Error, could not connect to the server");
        }
    }

    @Override
    public void run() {
        // Create writer service and execute reading here
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(this::writerService);
        // Reading
        while (true) {
            try {
                if (this.clientSocket.getInputStream().read() == -1) { // -1 If the server dies or client exits
                    try {
                        this.clientSocket.close();
                    } catch (IOException ignore) {
                    }
                    break;
                }
                // Reading and printing the message
                String text = SocketUtility.readMessage(this.clientSocket);
                System.out.println(text);
            } catch (IOException e) {
                System.out.println("WARNING: Could not read message from server"); // Reading error
            }
        }
        // Close writing thread
        executor.shutdown();
    }

    // Function to write user input to server
    private void writerService() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String text = scanner.nextLine();
            try {
                SocketUtility.sendMessage(this.clientSocket, text);
            } catch (IOException e) {
                System.out.println("ERROR: Could not send command");
            }
        }
    }

}
