package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    private ServerSocket socket;
    private final Logger logger = Logger.getLogger(Server.class.getName());

    protected Server(int port) {
        try {
            this.socket = new ServerSocket(port);
            logger.log(Level.INFO, "<System> Server created successfully");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "<System> Unable to create Server");
        }
    }

    @Override
    public void run() {
        this.acceptClient();
    }

    private void acceptClient() {
        ExecutorService clientExecutor = Executors.newCachedThreadPool();
        while (!this.socket.isClosed()) {
            try {
                Socket clientSocket = this.socket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientExecutor.execute(clientHandler);
                logger.log(Level.INFO, "<System> Accepted client");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "<System> Error while accepting a new connection");
            }
        }
    }
}
