package kotlin.internal;

/* compiled from: progressionUtil.kt */
public final class c {
    private static final int e(int a, int b) {
        int mod = a % b;
        return mod >= 0 ? mod : mod + b;
    }

    private static final long f(long a, long b) {
        long mod = a % b;
        return mod >= 0 ? mod : mod + b;
    }

    private static final int a(int a, int b, int c) {
        return e(e(a, c) - e(b, c), c);
    }

    private static final long b(long a, long b, long c) {
        return f(f(a, c) - f(b, c), c);
    }

    public static final int c(int start, int end, int step) {
        if (step > 0) {
            if (start < end) {
                return end - a(end, start, step);
            }
        } else if (step >= 0) {
            throw new IllegalArgumentException("Step is zero.");
        } else if (start > end) {
            return a(start, end, -step) + end;
        }
        return end;
    }

    public static final long d(long start, long end, long step) {
        if (step > 0) {
            if (start < end) {
                return end - b(end, start, step);
            }
        } else if (step >= 0) {
            throw new IllegalArgumentException("Step is zero.");
        } else if (start > end) {
            return b(start, end, -step) + end;
        }
        return end;
    }
}
