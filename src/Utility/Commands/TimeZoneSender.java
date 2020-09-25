package Utility.Commands;

import Utility.Sockets.SocketUtility;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneSender {

    static int sendTimeZone(Socket socket, String timeZone) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(TimeZoneSender.getTimeZone(timeZone)));

        return SocketUtility.sendMessage(socket, df.format(new Date()));
    }

    private static String getTimeZone(String timeZone) {
        int indexSlash = timeZone.indexOf('/');
        // First char to upper, substring of first word, second word first char to upper, second substring
        return Character.toUpperCase(timeZone.charAt(0)) + timeZone.substring(1, indexSlash + 1) +
                Character.toUpperCase(timeZone.charAt(indexSlash + 1)) + timeZone.substring(indexSlash + 2);
    }

}
