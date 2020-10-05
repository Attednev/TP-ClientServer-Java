package main.java.Utility.Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketUtility {

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
        System.out.println("<System> Connection ended!");
        try {
            socket.close();
        } catch (IOException ignore) {}
    }

}
