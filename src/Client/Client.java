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
        // Create reader and writer service
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(this::readerService);
        executor.execute(this::writerService);
        executor.shutdown(); // TODO: CHECK IF THIS IS CORRECT
    }

    // Function to write user input to server
    private void writerService() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String text = scanner.next();
            try {
                SocketUtility.sendMessage(this.clientSocket, text);
            } catch (IOException e) {
                if (this.clientSocket.isConnected()) {
                    System.out.println("ERROR: Could not send command");
                } else {
                    System.out.println("ERROR: Server not reachable. Aborting connection");
                    try {
                        this.clientSocket.close();
                    } catch (IOException ignore) {}
                    return;
                }
            }
        }
    }

    // Function to get the text that the server writes
    private void readerService() {
        while (true) {
            try {
                String text = SocketUtility.readMessage(this.clientSocket);
                System.out.println(text);
            } catch (IOException e) {
                if (this.clientSocket.isConnected()) {
                    System.out.println("WARNING: Could not read from server!");
                } else {
                    System.out.println("ERROR: Server not reachable. Aborting connection");
                    try {
                        this.clientSocket.close();
                    } catch (IOException ignore) {}
                    return;
                }
            }
        }
    }

}
