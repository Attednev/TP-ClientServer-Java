package utility.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketUtility {

    private SocketUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static int sendMessage(Socket socket, String message) {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(message);
            return 0;
        } catch (IOException e) {
            return -1;
        }
    }

    public static String readMessage(Socket socket) {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            return inputStream.readUTF();
        } catch (IOException ignore) {
            return null;
        }
    }

    public static void endConnection(Socket socket) {
        Logger logger = Logger.getLogger(SocketUtility.class.getName());
        try {
            socket.close();
            logger.log(Level.INFO, "<System> Connection ended!");
        } catch (IOException ignore) {
            logger.log(Level.SEVERE, "<System> Error while closing the connection!");
        }
    }

}
