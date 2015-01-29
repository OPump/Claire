package com.github.jasonlvhit.claire;

import java.math.BigInteger;

/**
 * Created by Jason Lyu on 2015/1/28.
 */
public class Utils {
    static BigInteger celsiusToFahrenheit(BigInteger celsius){
        BigInteger fahrenheit = celsius.multiply(BigInteger.valueOf(9));
        fahrenheit = fahrenheit.divide(BigInteger.valueOf(5));
        fahrenheit = fahrenheit.add(BigInteger.valueOf(32));
        return fahrenheit;
    }

    static BigInteger fahrenheitToCelsius(BigInteger fahrenheit){
        BigInteger celsius = fahrenheit.add(BigInteger.valueOf(-32));
        celsius = celsius.multiply(BigInteger.valueOf(5));
        celsius = celsius.divide(BigInteger.valueOf(9));
        return celsius;
    }
}
