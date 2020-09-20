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

        this.readerService();

        writerExecutor.shutdownNow();
    }

    private void readerService() {
        while (true) {
            String serverMessage = SocketUtility.readMessage(this.clientSocket);
            if (serverMessage == null) break;
            System.out.println(serverMessage);
        }
        SocketUtility.endConnection(this.clientSocket);
    }

    private void writerService() {
        while (true) {
            Scanner commandlineScanner = new Scanner(System.in);
            String userInput = commandlineScanner.nextLine();
            SocketUtility.sendMessage(this.clientSocket, userInput);
        }
    }

}
