package org.glassfish.grizzly.utils;

import java.util.Random;

public class ThreadLocalRandom extends Random {
    private static final long addend = 11;
    private static final ThreadLocal<ThreadLocalRandom> localRandom = new ThreadLocal<ThreadLocalRandom>() {
        /* access modifiers changed from: protected */
        public ThreadLocalRandom initialValue() {
            return new ThreadLocalRandom();
        }
    };
    private static final long mask = 281474976710655L;
    private static final long multiplier = 25214903917L;
    private static final long serialVersionUID = -5851777807851030925L;
    final boolean initialized = true;
    private long pad0;
    private long pad1;
    private long pad2;
    private long pad3;
    private long pad4;
    private long pad5;
    private long pad6;
    private long pad7;
    private long rnd;

    ThreadLocalRandom() {
    }

    public static ThreadLocalRandom current() {
        return localRandom.get();
    }

    public void setSeed(long seed) {
        if (!this.initialized) {
            this.rnd = (multiplier ^ seed) & mask;
            return;
        }
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public int next(int bits) {
        long j = ((this.rnd * multiplier) + 11) & mask;
        this.rnd = j;
        return (int) (j >>> (48 - bits));
    }

    public int nextInt(int least, int bound) {
        if (least < bound) {
            return nextInt(bound - least) + least;
        }
        throw new IllegalArgumentException();
    }

    public long nextLong(long n) {
        if (n > 0) {
            long offset = 0;
            while (n >= 2147483647L) {
                int bits = next(2);
                long half = n >>> 1;
                long nextn = (bits & 2) == 0 ? half : n - half;
                if ((bits & 1) == 0) {
                    offset += n - nextn;
                }
                n = nextn;
            }
            return ((long) nextInt((int) n)) + offset;
        }
        throw new IllegalArgumentException("n must be positive");
    }

    public long nextLong(long least, long bound) {
        if (least < bound) {
            return nextLong(bound - least) + least;
        }
        throw new IllegalArgumentException();
    }

    public double nextDouble(double n) {
        if (n > 0.0d) {
            return nextDouble() * n;
        }
        throw new IllegalArgumentException("n must be positive");
    }

    public double nextDouble(double least, double bound) {
        if (least < bound) {
            return (nextDouble() * (bound - least)) + least;
        }
        throw new IllegalArgumentException();
    }
}
