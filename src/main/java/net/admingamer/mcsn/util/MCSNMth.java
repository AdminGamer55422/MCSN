package net.admingamer.mcsn.util;

public class MCSNMth {
    private static final float LN_2 = 0.6931472f;

    public static float acosh(float x) {
        if (x < 1.0f) {
            return Float.NaN;
        }
        if (x > 10000000.0f) {
            return (float) Math.log(x) + LN_2;
        }
        return (float) Math.log(x + Math.sqrt(x * x - 1.0f));
    }

    public static float asinh(float x) {
        float absX = Math.abs(x);
        if (absX > 10000000.0f) {
            return Math.signum(x) * ((float) Math.log(absX) + LN_2);
        }
        return (float) Math.log(x + Math.sqrt(x * x + 1.0f));
    }

    public static float atanh(float x) {
        float absX = Math.abs(x);
        if (absX >= 1.0f) {
            return (x == 1.0f) ? Float.POSITIVE_INFINITY : (x == -1.0f) ? Float.NEGATIVE_INFINITY : Float.NaN;
        }
        return 0.5f * ((float) Math.log1p(x) - (float) Math.log1p(-x));
    }

    public static float csc(float x) {
        return 1.0f / (float) Math.sin(x);
    }

    public static float csch(float x) {
        return 1.0f / (float) Math.sinh(x);
    }

    public static float acsc(float x) {
        if (x > -1.0f && x < 1.0f) {
            return Float.NaN;
        }
        return (float) Math.asin(1.0f / x);
    }

    public static float acsch(float x) {
        if (Float.compare(x, 0.0f) == 0) {
            return (Float.floatToRawIntBits(x) < 0) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
        }
        float absX = Math.abs(x);
        float val = (float) Math.log(1.0f / absX + Math.sqrt(1.0f / (x * x) + 1.0f));
        return Math.copySign(val, x);
    }

    public static float sec(float x) {
        return 1.0f / (float) Math.cos(x);
    }

    public static float sech(float x) {
        return 1.0f / (float) Math.cosh(x);
    }

    public static float asec(float x) {
        if (x > -1.0f && x < 1.0f) {
            return Float.NaN;
        }
        return (float) Math.acos(1.0f / x);
    }

    public static float asech(float x) {
        if (x <= 0.0f || x > 1.0f) {
            return Float.NaN;
        }
        return (float) Math.log((1.0f + Math.sqrt(1.0f - x * x)) / x);
    }

    public static float cot(float angleInRadians) {
        return 1.0f / (float) Math.tan(angleInRadians);
    }

    public static float coth(float x) {
        if (x > 10.0f) return 1.0f;
        if (x < -10.0f) return -1.0f;
        return 1.0f / (float) Math.tanh(x);
    }

    public static float acot(float x) {
        if (x == 0.0f) {
            return (float) (Math.PI / 2.0);
        }
        return (float) Math.atan(1.0f / x);
    }

    public static float acoth(float x) {
        if (Math.abs(x) <= 1.0f) {
            return Float.NaN;
        }
        return 0.5f * (float) Math.log((x + 1.0f) / (x - 1.0f));
    }
}
