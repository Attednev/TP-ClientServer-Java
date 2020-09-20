package Utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketUtility {

    // Function to send a message to the socket
    public static int sendMessage(Socket socket, String message) {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(message);
            return 0;
        } catch (IOException e) {
            return -1;
        }
    }

    // Function to read the text that a client sends
    public static String readMessage(Socket socket) {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            return inputStream.readUTF();
        } catch (IOException ignore) {
            return null;
        }
    }


}
