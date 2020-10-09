import utility.commands.IntToRomanSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntToRomanSenderTest {

    @Test
    void intToRoman_test() {
        assertEquals("XII", IntToRomanSender.intToRoman(12));
    }

    @Test
    void numberToArray_test() {
        assertArrayEquals(new int[]{1, 5, 5, 5}, IntToRomanSender.numberToArray(5551));
    }

    @Test
    void getNumberSize_test() {
        assertEquals(4, IntToRomanSender.getNumberSize(5551));
    }

    @Test
    void getRomanDigits_test() {
        assertEquals("XX", IntToRomanSender.getRomanDigits(20));
    }

    @Test
    void getStringOfSequencingCharsBelowTen_test() {
        assertEquals("IV", IntToRomanSender.getRomanDigits(4));
    }

    @Test
    void getStringOfSequencingChars_test() {
        assertEquals("XX", IntToRomanSender.getRomanDigits(14));
    }

}