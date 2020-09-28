package Tests;

import Utility.Commands.TimeZoneSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeZoneSenderTest {

    @Test
    void getTimeZone_test() {
        assertEquals("Europe/Berlin", TimeZoneSender.refactorTimeZoneString("eUROPE/bERLIN"));
    }
}