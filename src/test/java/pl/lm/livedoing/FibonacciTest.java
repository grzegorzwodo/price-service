package pl.lm.livedoing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lm.livecoding.Fibonacci;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FibonacciTest {

    @Test
    public void checkFirstEightValues() {
        Fibonacci fibonacci = new Fibonacci();
        long[] firstEightValues = {0, 1, 1, 2, 3, 5, 8, 13};
        for (int i = 0; i < 8; i++) {
            assertEquals(fibonacci.next(), BigInteger.valueOf(firstEightValues[i]));
        }
    }

    @Test
    public void checkHasNextValueIsAlwaysTrue() {
        Fibonacci fibonacci = new Fibonacci();
        for (int i = 0; i < 100; i++) {
            assertTrue(fibonacci.hasNext());
            fibonacci.next();
        }
    }


}
