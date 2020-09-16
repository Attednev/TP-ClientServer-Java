package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket serverSocket = null;

    protected Server(int port) {
        try {
            // Bind the socket to the port
            this.serverSocket = new ServerSocket(port);
            System.out.println("<System> Server created successfully");
        } catch (IOException e) {
            System.out.println("<System> Unable to create Server");
        }
    }

    @Override
    public void run() {
        // Socket is not bound so we cannot accept connections
        if (serverSocket == null) {
            System.out.println("<System> Socket not bound");
            return;
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        while (true) {
            // Accept a new client
            try {
                // Create a new client socket and handle that client
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.execute(clientHandler);
                System.out.println("<Server> Accepted client");
            } catch (IOException e) {
                System.out.println("<Server> Error while accepting a new connection");
            }
        }
    }

}
