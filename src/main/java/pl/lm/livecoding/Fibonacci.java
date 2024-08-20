package pl.lm.livecoding;

import java.math.BigInteger;
import java.util.Iterator;

public class Fibonacci implements Iterator<BigInteger> {

    private BigInteger previous = BigInteger.ZERO;
    private BigInteger current =  BigInteger.ONE;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public BigInteger next() {
        BigInteger nextValue = previous;
        BigInteger newCurrent = previous.add(current);
        previous = current;
        current = newCurrent;
        return nextValue;
    }

}
