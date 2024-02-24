package org.dft;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Statistic {


    private volatile long countFloat;
    private volatile long countInteger;
    private volatile long countString;
    private volatile BigDecimal sumFloat;
    private volatile BigInteger sumInteger;
    private volatile String theLongestString;
    private volatile boolean extendedForm;

    public Statistic(boolean extendedForm) {
        this.extendedForm = extendedForm;
        this.countFloat = 0;
        this.countInteger = 0;
        this.countString = 0;
        this.sumFloat = new BigDecimal("0");
        this.sumInteger = new BigInteger("0");
        this.theLongestString = "";
    }

    synchronized void addInteger(String str) {
        ++countInteger;
        sumInteger.add(new BigInteger(str));
    }

    synchronized void addFloat(String str) {
        ++countFloat;
        sumFloat.add(new BigDecimal(str));
    }

    synchronized void setTheLongestString(String str) {
        ++countString;
        theLongestString = (str.length() > theLongestString.length()) ? str : theLongestString;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Statistic for files:\n");
        if (extendedForm) {
            if (countInteger != 0) {
                stringBuilder.append(String.format("Integers were written: %d\n", countInteger));
                stringBuilder.append("Sum of integers is: \n").append(sumInteger.toString());
            }
            if (countFloat != 0) {
                stringBuilder.append(String.format("Floats were written: %d\n", countFloat));
                stringBuilder.append("Sum of floats is: \n").append(sumFloat.toString());
            }
            if (countString != 0) {
                stringBuilder.append(String.format("Strings were written: %d\n", countString));
                stringBuilder.append("The longest string is: \n").append(theLongestString);
            }
        } else {
            if (countInteger != 0) {
                stringBuilder.append(String.format("Integers were written: %d\n", countInteger));
            }
            if (countFloat != 0) {
                stringBuilder.append(String.format("Floats were written: %d\n", countFloat));
            }
            if (countString != 0) {
                stringBuilder.append(String.format("Strings were written: %d\n", countString));
            }
        }
        return stringBuilder.toString();
    }


}
