package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket serverSocket;

    protected Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("<System> Server created successfully");
        } catch (IOException e) {
            System.out.println("<System> Unable to create Server");
        }
    }

    @Override
    public void run() {
        ExecutorService clientExecutor = Executors.newCachedThreadPool();
        while (true) {
            try {
                Socket clientSocket = this.serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientExecutor.execute(clientHandler);
                System.out.println("<Server> Accepted client");
            } catch (IOException e) {
                System.out.println("<Server> Error while accepting a new connection");
            }
        }
    }

}
