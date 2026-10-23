package com.example.calc;

public class DamnBigNumbers {

    private static String strip(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return s.substring(i);
    }

    public static int compare(DamnBigNumber a, DamnBigNumber b) {
        String x = strip(a.getNumStr());
        String y = strip(b.getNumStr());
        if (x.length() != y.length()) {
            return x.length() < y.length() ? -1 : 1;
        }
        return Integer.signum(x.compareTo(y));
    }

    private static String addStr(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0 || carry > 0; i--, j--) {
            int s = carry;
            if (i >= 0) s += a.charAt(i) - '0';
            if (j >= 0) s += b.charAt(j) - '0';
            sb.append(s % 10);
            carry = s / 10;
        }
        return strip(sb.reverse().toString());
    }

    // Feltétel: a >= b
    private static String subStr(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0; i--, j--) {
            int d = a.charAt(i) - '0' - borrow;
            if (j >= 0) d -= b.charAt(j) - '0';
            if (d < 0) {
                d += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            sb.append(d);
        }
        return strip(sb.reverse().toString());
    }

    private static String mulStr(String a, String b) {
        int[] r = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--) {
            for (int j = b.length() - 1; j >= 0; j--) {
                r[i + j + 1] += (a.charAt(i) - '0') * (b.charAt(j) - '0');
            }
        }
        for (int k = r.length - 1; k > 0; k--) {
            r[k - 1] += r[k] / 10;
            r[k] %= 10;
        }
        StringBuilder sb = new StringBuilder();
        for (int digit : r) {
            sb.append(digit);
        }
        return strip(sb.toString());
    }

    private static String divStr(String a, String b) {
        if (strip(b).equals("0")) {
            throw new ArithmeticException("Nullával osztás");
        }
        StringBuilder q = new StringBuilder();
        String rem = "0";
        for (int i = 0; i < a.length(); i++) {
            rem = strip(rem + a.charAt(i));
            int d = 0;
            while (compare(new DamnBigNumber(rem), new DamnBigNumber(b)) >= 0) {
                rem = subStr(rem, strip(b));
                d++;
            }
            q.append(d);
        }
        return strip(q.toString());
    }

    public static DamnBigNumber add(DamnBigNumber a, long b) {
        return add(a, new DamnBigNumber(b));
    }

    public static DamnBigNumber add(DamnBigNumber a, DamnBigNumber b) {
        return new DamnBigNumber(addStr(a.getNumStr(), b.getNumStr()));
    }

    public static DamnBigNumber subtract(DamnBigNumber a, long b) {
        return subtract(a, new DamnBigNumber(b));
    }

    // Ha a < b, az eredmény negatív előjelű szöveg lesz (a további műveletek csak természetes számokkal működnek)
    public static DamnBigNumber subtract(DamnBigNumber a, DamnBigNumber b) {
        if (compare(a, b) < 0) {
            return new DamnBigNumber("-" + subStr(strip(b.getNumStr()), strip(a.getNumStr())));
        }
        return new DamnBigNumber(subStr(strip(a.getNumStr()), strip(b.getNumStr())));
    }

    public static DamnBigNumber multiply(DamnBigNumber a, long b) {
        return multiply(a, new DamnBigNumber(b));
    }

    public static DamnBigNumber multiply(DamnBigNumber a, DamnBigNumber b) {
        return new DamnBigNumber(mulStr(a.getNumStr(), b.getNumStr()));
    }

    public static DamnBigNumber divide(DamnBigNumber a, long b) {
        return divide(a, new DamnBigNumber(b));
    }

    public static DamnBigNumber divide(DamnBigNumber a, DamnBigNumber b) {
        return new DamnBigNumber(divStr(strip(a.getNumStr()), strip(b.getNumStr())));
    }

    public static boolean areEqual(DamnBigNumber a, DamnBigNumber b) {
        return compare(a, b) == 0;
    }

    public static boolean areEqual(DamnBigNumber a, long b) {
        return areEqual(a, new DamnBigNumber(b));
    }

    public static boolean areEqual(long a, DamnBigNumber b) {
        return areEqual(new DamnBigNumber(a), b);
    }
}
