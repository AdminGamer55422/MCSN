package net.admingamer.mcsn.util;

import java.math.BigDecimal;

public class MCSNMath {
    public static double acosh(double x) {
        if (x < 1.0) {
            return Double.NaN;
        }
        if (x > 100000000.0) {
            return Math.log(x) + Math.log(2.0);
        }
        return Math.log(x + Math.sqrt(x * x - 1.0));
    }

    public static double asinh(double x) {
        if (Math.abs(x) > 100000000.0) {
            return Math.signum(x) * (Math.log(Math.abs(x)) + Math.log(2.0));
        }
        return Math.log(x + Math.sqrt(x * x + 1.0));
    }

    public static double atanh(double x) {
        if (Math.abs(x) >= 1.0) {
            return (x == 1.0) ? Double.POSITIVE_INFINITY : (x == -1.0) ? Double.NEGATIVE_INFINITY : Double.NaN;
        }
        return 0.5 * Math.log1p(2.0 * x / (1.0 - x));
    }

    public static double csc(double x) {
        return 1.0 / Math.sin(x);
    }

    public static double csch(double x) {
        return 1.0 / Math.sinh(x);
    }

    public static double acsc(double x) {
        if (x > -1.0 && x < 1.0) {
            return Double.NaN;
        }
        return Math.asin(1.0 / x);
    }

    public static double acsch(double x) {
        if (Double.compare(x, 0.0) == 0) {
            return (Double.doubleToRawLongBits(x) < 0) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        return Math.log(1.0 / x + Math.sqrt(1.0 / (x * x) + 1.0));
    }

    public static double sec(double x) {
        return 1.0 / Math.cos(x);
    }

    public static double sech(double x) {
        return 1.0 / Math.cosh(x);
    }

    public static double asec(double x) {
        return Math.acos(1.0 / x);
    }

    public static double asech(double x) {
        if (x <= 0 || x > 1) {
            return Double.NaN;
        }
        return Math.log((1.0 + Math.sqrt(1.0 - x * x)) / x);
    }

    public static double cot(double angleInRadians) {
        return 1.0 / Math.tan(angleInRadians);
    }

    public static double coth(double x) {
        if (x > 20.0) return 1.0;
        if (x < -20.0) return -1.0;
        return 1.0 / Math.tanh(x);
    }

    public static double acot(double x) {
        if (x == 0.0) {
            return Math.PI / 2;
        }
        return Math.atan(1.0 / x);
    }

    public static double acoth(double x) {
        if (Math.abs(x) <= 1.0) {
            return Double.NaN;
        }
        return 0.5 * Math.log((x + 1.0) / (x - 1.0));
    }
}
