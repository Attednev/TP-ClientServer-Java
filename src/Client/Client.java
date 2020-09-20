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
            this.clientSocket = new Socket(serverAddress, port);
            System.out.println("<System> Successfully connected to the server");
        } catch (IOException e) {
            System.out.println("<System> Error, could not connect to the server");
        }
    }

    @Override
    public void run() {
        if (this.clientSocket == null) return;
        ExecutorService writerExecutor = Executors.newCachedThreadPool();
        writerExecutor.execute(this::writerService);
        while (true) {
            String serverMessage = SocketUtility.readMessage(this.clientSocket);
            if (serverMessage == null) break;
            System.out.println(serverMessage);
        }
        writerExecutor.shutdown();
        System.out.println("<System> Connection to the server ended!");
        try {
            this.clientSocket.close();
        } catch (IOException ignore) {}
    }

    // Function to write user input to server
    private void writerService() {
        while (true) {
            Scanner commandlineScanner = new Scanner(System.in);
            String userInput = commandlineScanner.nextLine();
            int successValue = SocketUtility.sendMessage(this.clientSocket, userInput);
            if (successValue == -1) break;
        }
        try {
            this.clientSocket.close();
        } catch (IOException ignore) {
            System.out.println("Error while closing the connection");
        }
    }

}
