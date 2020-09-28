package Tests;

import Utility.Commands.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.directory.InvalidAttributesException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    String[] args = {"calc", "5", "+", "5", "-", "2"};
    ArrayList<String> arguments;

    @BeforeEach
    void setup() {
        arguments = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
    }

    @Test
    void getInterimResult_test() throws InvalidAttributesException {
        assertEquals("50.0", Calculator.getInterimResult("10", "5", "*"));
        assertEquals("2.0", Calculator.getInterimResult("10", "5", "/"));
        assertEquals("15.0", Calculator.getInterimResult("10", "5", "+"));
        assertEquals("5.0", Calculator.getInterimResult("10", "5", "-"));
        assertEquals("0.0", Calculator.getInterimResult("10", "5", "%"));
    }

    @Test
    void replaceWithResult_test() throws InvalidAttributesException {
        Calculator.replaceWithResult(arguments, 1);
        assertEquals(new ArrayList<>(Arrays.asList("10.0", "-", "2")) , arguments);

        Calculator.replaceWithResult(arguments, 1);
        assertEquals(new ArrayList<>(Collections.singletonList("8.0")) , arguments);

    }

    @Test
    void calculateOperators_test() throws InvalidAttributesException {
        Calculator.calculateOperators(arguments, new ArrayList<>(Arrays.asList("+", "-")));
        assertEquals(new ArrayList<>(Collections.singletonList("8.0")), arguments);
    }

    @Test
    void calculate_test() throws InvalidAttributesException {
        assertEquals("10.0", Calculator.calculate(new String[]{"calc", "1", "+", "3", "*", "3"}));
    }
}