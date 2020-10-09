package client;

import utility.sockets.SocketUtility;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {
    private Socket socket = null;
    private final Logger logger = Logger.getLogger(Client.class.getName());

    protected Client(String serverAddress, int port) {
        try {
            this.socket = new Socket(serverAddress, port);
            this.logger.log(Level.INFO, "<System> Successfully connected to the server");
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "<System> Error, could not connect to the server");
        }
    }

    @Override
    public void run() {
        if (this.socket == null) return;

        ExecutorService writerExecutor = Executors.newCachedThreadPool();
        writerExecutor.execute(this::writerService);

        this.readerService();

        writerExecutor.shutdownNow();
    }

    private void readerService() {
        while (true) {
            String serverMessage = SocketUtility.readMessage(this.socket);
            if (serverMessage == null) break;
            this.logger.log(Level.FINEST, serverMessage);
        }
        SocketUtility.endConnection(this.socket);
    }

    private void writerService() {
        while (true) {
            Scanner commandlineScanner = new Scanner(System.in);
            String userInput = commandlineScanner.nextLine();
           if (SocketUtility.sendMessage(this.socket, userInput) == -1) break;
        }
    }

}
