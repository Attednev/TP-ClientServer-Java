package Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    // Main Driver function for the server
    public static void main(String[] args) {
        Server server = new Server(5555);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(server);
    }
}
