package Tests;

import Utility.Commands.TranslatedNumberSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatedNumberSenderTest {

    @Test
    void getTranslatedNumber_test() {
        assertEquals("101", TranslatedNumberSender.getTranslatedNumber("5", 10, 2));
        assertEquals("a", TranslatedNumberSender.getTranslatedNumber("1010", 2, 16));
    }

}