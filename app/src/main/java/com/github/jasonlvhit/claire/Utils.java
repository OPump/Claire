/*
 * Copyright (C) 2015 南瓜工作室.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
