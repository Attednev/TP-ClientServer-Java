package Client;

import Utility.SocketUtility;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class Client implements Runnable {
    private Socket clientSocket = null;

    protected Client(String serverAddress, int port) {
        try {
            // Bind to server
            this.clientSocket = new Socket(serverAddress, port);
            System.out.println("<System> Successfully connected to the server");
        } catch (IOException e) {
            System.out.println("<System> Error, could not connect to the server");
        }
    }

    @Override
    public void run() {
        ExecutorService writerExecutor = Executors.newCachedThreadPool();
        writerExecutor.execute(this::writerService);
        while (true) {
            try {
                if (this.clientSocket.getInputStream().read() == -1) { // -1 If the server dies or client exits
                    try {
                        this.clientSocket.close();
                    } catch (IOException ignore) {
                    }
                    break;
                }
                String serverMessage = SocketUtility.readMessage(this.clientSocket);
                System.out.println(serverMessage);
            } catch (IOException e) {
                System.out.println("WARNING: Could not read message from server");
            }
        }
        writerExecutor.shutdown();
    }

    // Function to write user input to server
    private void writerService() {
        while (true) {
            Scanner commandlineScanner = new Scanner(System.in);
            String userInput = commandlineScanner.nextLine();
            try {
                SocketUtility.sendMessage(this.clientSocket, userInput);
            } catch (IOException e) {
                System.out.println("ERROR: Could not send command");
            }
        }
    }

}
