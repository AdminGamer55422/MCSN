package net.admingamer.mcsn.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class MCSNMath {
    private static final double LN_2 = 0.6931471805599453;

    public static double acosh(double x) {
        if (x < 1.0) {
            return Double.NaN;
        }
        if (x > 100000000.0) {
            return Math.log(x) + LN_2;
        }
        return Math.log(x + Math.sqrt(x * x - 1.0));
    }

    public static double asinh(double x) {
        if (Math.abs(x) > 100000000.0) {
            return Math.signum(x) * (Math.log(Math.abs(x)) + LN_2);
        }
        return Math.log(x + Math.sqrt(x * x + 1.0));
    }

    public static double atanh(double x) {
        if (Math.abs(x) >= 1.0) {
            return (x == 1.0) ? Double.POSITIVE_INFINITY : (x == -1.0) ? Double.NEGATIVE_INFINITY : Double.NaN;
        }
        return 0.5 * (Math.log1p(x) - Math.log1p(-x));
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
        double absX = Math.abs(x);
        double val = Math.log(1.0 / absX + Math.sqrt(1.0 / (x * x) + 1.0));
        return Math.copySign(val, x);
    }

    public static double sec(double x) {
        return 1.0 / Math.cos(x);
    }

    public static double sech(double x) {
        return 1.0 / Math.cosh(x);
    }

    public static double asec(double x) {
        if (x > -1.0 && x < 1.0) {
            return Double.NaN;
        }
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

    public static double erf(double x) {
        int sign = 1;
        if (x < 0) {
            sign = -1;
            x = -x;
        }

        double p = 0.3275911;
        double a1 = 0.254829592;
        double a2 = -0.284496736;
        double a3 = 1.421413741;
        double a4 = -1.453152027;
        double a5 = 1.061405429;

        double t = 1.0 / (1.0 + p * x);
        double poly = ((((a5 * t + a4) * t + a3) * t + a2) * t + a1) * t;

        double erfVal = 1.0 - poly * Math.exp(-x * x);
        return sign * erfVal;
    }

    public static double erfc(double x) {
        return 1.0 - erf(x);
    }

    public static double gamma(double x) {
        if (x <= 0.0 && x == Math.floor(x)) {
            return Double.NaN; // Poles at zero and negative integers
        }

        if (x < 0.5) {
            return Math.PI / (Math.sin(Math.PI * x) * gamma(1.0 - x));
        }

        x -= 1.0;
        double[] p = {
                0.99999999999980993, 676.5203681218851, -1259.1392167224028,
                771.32342877765313, -176.61502916214059, 12.507343278686905,
                -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7
        };

        double g = 7.0;
        double base = x + g + 0.5;
        double sum = p[0];
        for (int i = 1; i < p.length; i++) {
            sum += p[i] / (x + i);
        }

        return Math.sqrt(2.0 * Math.PI) * Math.pow(base, x + 0.5) * Math.exp(-base) * sum;
    }

    public static double beta(double x, double y) {
        return (gamma(x) * gamma(y)) / gamma(x + y);
    }

    public static BigDecimal permutation(int n, int r) {
        if (n < 0 || r < 0 || r > n) throw new IllegalArgumentException("Invalid n or r parameters.");
        if (r == 0) return BigDecimal.ONE;

        BigDecimal result = BigDecimal.ONE;
        for (int i = 0; i < r; i++) {
            result = result.multiply(BigDecimal.valueOf(n - i));
        }
        return result;
    }

    public static BigDecimal combination(int n, int r) {
        if (n < 0 || r < 0 || r > n) throw new IllegalArgumentException("Invalid n or r parameters.");
        if (r == 0 || n == r) return BigDecimal.ONE;

        if (r > n - r) {
            r = n - r;
        }

        BigDecimal numerator = BigDecimal.ONE;
        BigDecimal denominator = BigDecimal.ONE;

        for (int i = 1; i <= r; i++) {
            numerator = numerator.multiply(BigDecimal.valueOf(n - i + 1));
            denominator = denominator.multiply(BigDecimal.valueOf(i));
        }

        return numerator.divide(denominator, 14, RoundingMode.HALF_UP);
    }

    public static BigDecimal gcd(BigDecimal a, BigDecimal b) {
        BigInteger biA = a.toBigInteger();
        BigInteger biB = b.toBigInteger();
        return new BigDecimal(biA.gcd(biB));
    }

    public static BigDecimal lcm(BigDecimal a, BigDecimal b) {
        if (a.signum() == 0 || b.signum() == 0) return BigDecimal.ZERO;
        BigInteger biA = a.toBigInteger();
        BigInteger biB = b.toBigInteger();
        BigInteger gcd = biA.gcd(biB);
        BigInteger lcm = biA.multiply(biB).abs().divide(gcd);
        return new BigDecimal(lcm);
    }
}
