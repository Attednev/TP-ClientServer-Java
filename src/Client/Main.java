package Client;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    // Main Driver function for the client
    public static void main(String[] args) {
        Client client = new Client("localhost", 5555);
        ExecutorService clientExecutor = Executors.newCachedThreadPool();
        clientExecutor.execute(client);
        clientExecutor.shutdown();
    }
}
