package main.java.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    // Server main
    public static void main(String[] args) {
        Server server = new Server(5555);
        ExecutorService serverExecutor = Executors.newCachedThreadPool();
        serverExecutor.execute(server);
        serverExecutor.shutdown();
    }

}
