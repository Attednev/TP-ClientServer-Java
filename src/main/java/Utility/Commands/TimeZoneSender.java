package main.java.Utility.Commands;

import main.java.Utility.Sockets.SocketUtility;

import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneSender {

    public static int sendTimeZone(Socket socket, String timeZone) {
        return SocketUtility.sendMessage(socket, TimeZoneSender.getMessageToSend(timeZone));
    }

    public static String getMessageToSend(String timeZone) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(TimeZoneSender.refactorTimeZoneString(timeZone)));
        return df.format(new Date());
    }

    public static String refactorTimeZoneString(String timeZone) {
        int indexSlash = timeZone.indexOf('/');
        // First char to upper, substring of first word, second word first char to upper, second substring
        return Character.toUpperCase(timeZone.charAt(0)) + timeZone.substring(1, indexSlash + 1).toLowerCase() +
                Character.toUpperCase(timeZone.charAt(indexSlash + 1)) + timeZone.substring(indexSlash + 2).toLowerCase();
    }

}
