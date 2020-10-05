package main.java.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket socket;

    protected Server(int port) {
        try {
            this.socket = new ServerSocket(port);
            System.out.println("<System> Server created successfully");
        } catch (IOException e) {
            System.out.println("<System> Unable to create Server");
        }
    }

    @Override
    public void run() {
        this.acceptClient();
    }

    private void acceptClient() {
        ExecutorService clientExecutor = Executors.newCachedThreadPool();
        while (true) {
            try {
                Socket clientSocket = this.socket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientExecutor.execute(clientHandler);
                System.out.println("<System> Accepted client");
            } catch (IOException e) {
                System.out.println("<System> Error while accepting a new connection");
            }
        }
    }

}
