package com.example.calc;

public class DamnBigNumber {
    private String numStr = "0";

    public String getNumStr() {
        return numStr;
    }

    public void setNumStr(String numStr) {
        this.numStr = numStr;
    }

    public DamnBigNumber() {
        setNumStr("0");
    }
    public DamnBigNumber(int x) {
        setNumStr(String.format("%d", x));
    }
    public DamnBigNumber(long x) {
        setNumStr(String.format("%d", x));
    }
    public DamnBigNumber(String xStr) {
        setNumStr(xStr);
    }

    public static final DamnBigNumber DBN_ZERO = new DamnBigNumber(0);
    public static final DamnBigNumber DBN_ONE = new DamnBigNumber(1);

    @Override
    public String toString() {
        return getNumStr();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DamnBigNumber && DamnBigNumbers.areEqual(this, (DamnBigNumber) obj);
    }

    @Override
    public int hashCode() {
        return getNumStr().replaceFirst("^0+(?!$)", "").hashCode();
    }

    public long getLong() {
        return Long.parseLong(getNumStr());
    }

    public DamnBigNumber increment() {
        return DamnBigNumbers.add(this, DBN_ONE);
    }

    public DamnBigNumber decrement() {
        return DamnBigNumbers.subtract(this, DBN_ONE);
    }

    public boolean isPrime() {
        if (getNumStr().startsWith("-") || DamnBigNumbers.compare(this, new DamnBigNumber(2)) < 0) {
            return false;
        }
        DamnBigNumber d = new DamnBigNumber(2);
        while (DamnBigNumbers.compare(DamnBigNumbers.multiply(d, d), this) <= 0) {
            DamnBigNumber q = DamnBigNumbers.divide(this, d);
            if (DamnBigNumbers.areEqual(DamnBigNumbers.multiply(q, d), this)) {
                return false;
            }
            d = d.increment();
        }
        return true;
    }
}
