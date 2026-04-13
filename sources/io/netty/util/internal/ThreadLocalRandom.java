package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.Thread;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadLocalRandom extends Random {
    private static final long addend = 11;
    private static volatile long initialSeedUniquifier = 0;
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ThreadLocalRandom.class);
    private static final long mask = 281474976710655L;
    private static final long multiplier = 25214903917L;
    private static final AtomicLong seedUniquifier = new AtomicLong();
    private static final long serialVersionUID = -5851777807851030925L;
    boolean initialized = true;
    private long pad0;
    private long pad1;
    private long pad2;
    private long pad3;
    private long pad4;
    private long pad5;
    private long pad6;
    private long pad7;
    private long rnd;

    public static void setInitialSeedUniquifier(long initialSeedUniquifier2) {
        initialSeedUniquifier = initialSeedUniquifier2;
    }

    public static synchronized long getInitialSeedUniquifier() {
        long initialSeedUniquifier2;
        synchronized (ThreadLocalRandom.class) {
            long initialSeedUniquifier3 = initialSeedUniquifier;
            if (initialSeedUniquifier3 == 0) {
                long j = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0);
                initialSeedUniquifier3 = j;
                initialSeedUniquifier = j;
            }
            if (initialSeedUniquifier2 == 0) {
                if (SystemPropertyUtil.getBoolean("java.util.secureRandomSeed", false)) {
                    final BlockingQueue<Long> queue = new LinkedBlockingQueue<>();
                    Thread generatorThread = new Thread("initialSeedUniquifierGenerator") {
                        public void run() {
                            byte[] seed = new SecureRandom().generateSeed(8);
                            queue.add(Long.valueOf(((((long) seed[0]) & 255) << 56) | ((((long) seed[1]) & 255) << 48) | ((((long) seed[2]) & 255) << 40) | ((((long) seed[3]) & 255) << 32) | ((((long) seed[4]) & 255) << 24) | ((((long) seed[5]) & 255) << 16) | ((((long) seed[6]) & 255) << 8) | (255 & ((long) seed[7]))));
                        }
                    };
                    generatorThread.setDaemon(true);
                    generatorThread.start();
                    generatorThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                        public void uncaughtException(Thread t, Throwable e) {
                            ThreadLocalRandom.logger.debug("An exception has been raised by {}", t.getName(), e);
                        }
                    });
                    long deadLine = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
                    boolean interrupted = false;
                    while (true) {
                        long waitTime = deadLine - System.nanoTime();
                        if (waitTime <= 0) {
                            generatorThread.interrupt();
                            logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entrophy?", (Object) 3L);
                            break;
                        }
                        try {
                            Long seed = queue.poll(waitTime, TimeUnit.NANOSECONDS);
                            if (seed != null) {
                                initialSeedUniquifier2 = seed.longValue();
                                break;
                            }
                        } catch (InterruptedException e) {
                            interrupted = true;
                            logger.warn("Failed to generate a seed from SecureRandom due to an InterruptedException.");
                        }
                    }
                    initialSeedUniquifier2 = (initialSeedUniquifier2 ^ 3627065505421648153L) ^ Long.reverse(System.nanoTime());
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                        generatorThread.interrupt();
                    }
                } else {
                    initialSeedUniquifier2 = mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime());
                }
                initialSeedUniquifier = initialSeedUniquifier2;
            }
        }
        return initialSeedUniquifier2;
    }

    private static long newSeed() {
        AtomicLong atomicLong;
        long current;
        long actualCurrent;
        long next;
        long startTime = System.nanoTime();
        do {
            atomicLong = seedUniquifier;
            current = atomicLong.get();
            actualCurrent = current != 0 ? current : getInitialSeedUniquifier();
            next = 181783497276652981L * actualCurrent;
        } while (!atomicLong.compareAndSet(current, next));
        if (current == 0) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", new Object[]{Long.valueOf(actualCurrent), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime))}));
            }
        }
        return System.nanoTime() ^ next;
    }

    private static long mix64(long z) {
        long z2 = ((z >>> 33) ^ z) * -49064778989728563L;
        long z3 = ((z2 >>> 33) ^ z2) * -4265267296055464877L;
        return (z3 >>> 33) ^ z3;
    }

    ThreadLocalRandom() {
        super(newSeed());
    }

    public static ThreadLocalRandom current() {
        return InternalThreadLocalMap.get().random();
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
