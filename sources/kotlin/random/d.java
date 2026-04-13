package kotlin.random;

import java.io.Serializable;
import kotlin.internal.b;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Random.kt */
public abstract class d {
    @NotNull
    public static final a Default = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final d c = b.a.b();

    public abstract int nextBits(int i);

    public int nextInt() {
        return nextBits(32);
    }

    public int nextInt(int until) {
        return nextInt(0, until);
    }

    public int nextInt(int from, int until) {
        int bitCount;
        int bits;
        e.c(from, until);
        int n = until - from;
        if (n > 0 || n == Integer.MIN_VALUE) {
            if (((-n) & n) == n) {
                bitCount = nextBits(e.e(n));
            } else {
                do {
                    bits = nextInt() >>> 1;
                    bitCount = bits % n;
                } while ((bits - bitCount) + (n - 1) < 0);
            }
            return from + bitCount;
        }
        while (true) {
            int rnd = nextInt();
            if (from <= rnd && until > rnd) {
                return rnd;
            }
        }
    }

    public long nextLong() {
        return (((long) nextInt()) << 32) + ((long) nextInt());
    }

    public long nextLong(long until) {
        return nextLong(0, until);
    }

    public long nextLong(long from, long until) {
        long rnd;
        long bits;
        long v;
        long j;
        e.d(from, until);
        long n = until - from;
        if (n > 0) {
            if (((-n) & n) == n) {
                int nLow = (int) n;
                int nHigh = (int) (n >>> 32);
                if (nLow != 0) {
                    j = ((long) nextBits(e.e(nLow))) & 4294967295L;
                } else if (nHigh == 1) {
                    j = ((long) nextInt()) & 4294967295L;
                } else {
                    j = (((long) nextBits(e.e(nHigh))) << 32) + ((long) nextInt());
                }
                rnd = j;
            } else {
                do {
                    bits = nextLong() >>> 1;
                    v = bits % n;
                } while ((bits - v) + (n - 1) < 0);
                rnd = v;
            }
            return from + rnd;
        }
        while (true) {
            long rnd2 = nextLong();
            if (from <= rnd2 && until > rnd2) {
                return rnd2;
            }
        }
    }

    public boolean nextBoolean() {
        return nextBits(1) != 0;
    }

    public double nextDouble() {
        return c.a(nextBits(26), nextBits(27));
    }

    public double nextDouble(double until) {
        return nextDouble(0.0d, until);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public double nextDouble(double r9, double r11) {
        /*
            r8 = this;
            kotlin.random.e.b(r9, r11)
            double r0 = r11 - r9
            boolean r2 = java.lang.Double.isInfinite(r0)
            if (r2 == 0) goto L_0x003e
            boolean r2 = java.lang.Double.isInfinite(r9)
            r3 = 1
            r4 = 0
            if (r2 != 0) goto L_0x001b
            boolean r2 = java.lang.Double.isNaN(r9)
            if (r2 != 0) goto L_0x001b
            r2 = r3
            goto L_0x001c
        L_0x001b:
            r2 = r4
        L_0x001c:
            if (r2 == 0) goto L_0x003e
            boolean r2 = java.lang.Double.isInfinite(r11)
            if (r2 != 0) goto L_0x002b
            boolean r2 = java.lang.Double.isNaN(r11)
            if (r2 != 0) goto L_0x002b
            goto L_0x002c
        L_0x002b:
            r3 = r4
        L_0x002c:
            if (r3 == 0) goto L_0x003e
            double r2 = r8.nextDouble()
            r4 = 2
            double r4 = (double) r4
            double r6 = r11 / r4
            double r4 = r9 / r4
            double r6 = r6 - r4
            double r2 = r2 * r6
            double r4 = r9 + r2
            double r4 = r4 + r2
            goto L_0x0045
        L_0x003e:
            double r2 = r8.nextDouble()
            double r2 = r2 * r0
            double r4 = r9 + r2
        L_0x0045:
            r2 = r4
            int r4 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r4 < 0) goto L_0x0051
            r4 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            double r4 = java.lang.Math.nextAfter(r11, r4)
            goto L_0x0052
        L_0x0051:
            r4 = r2
        L_0x0052:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.random.d.nextDouble(double, double):double");
    }

    public float nextFloat() {
        return ((float) nextBits(24)) / ((float) 16777216);
    }

    public static /* synthetic */ byte[] nextBytes$default(d dVar, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = bArr.length;
            }
            return dVar.nextBytes(bArr, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001a  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x008c  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] nextBytes(@org.jetbrains.annotations.NotNull byte[] r11, int r12, int r13) {
        /*
            r10 = this;
            java.lang.String r0 = "array"
            kotlin.jvm.internal.k.e(r11, r0)
            int r0 = r11.length
            r1 = 0
            r2 = 1
            if (r12 >= 0) goto L_0x000b
            goto L_0x0015
        L_0x000b:
            if (r0 < r12) goto L_0x0015
            int r0 = r11.length
            if (r13 >= 0) goto L_0x0011
            goto L_0x0015
        L_0x0011:
            if (r0 < r13) goto L_0x0015
            r0 = r2
            goto L_0x0016
        L_0x0015:
            r0 = r1
        L_0x0016:
            java.lang.String r3 = "fromIndex ("
            if (r0 == 0) goto L_0x008c
            if (r12 > r13) goto L_0x001e
            r0 = r2
            goto L_0x001f
        L_0x001e:
            r0 = r1
        L_0x001f:
            if (r0 == 0) goto L_0x0065
            int r0 = r13 - r12
            int r0 = r0 / 4
            r3 = r12
            r4 = r1
        L_0x0027:
            if (r4 >= r0) goto L_0x004d
            r5 = r4
            r6 = 0
            int r7 = r10.nextInt()
            byte r8 = (byte) r7
            r11[r3] = r8
            int r8 = r3 + 1
            int r9 = r7 >>> 8
            byte r9 = (byte) r9
            r11[r8] = r9
            int r8 = r3 + 2
            int r9 = r7 >>> 16
            byte r9 = (byte) r9
            r11[r8] = r9
            int r8 = r3 + 3
            int r9 = r7 >>> 24
            byte r9 = (byte) r9
            r11[r8] = r9
            int r3 = r3 + 4
            int r4 = r4 + 1
            goto L_0x0027
        L_0x004d:
            int r4 = r13 - r3
            int r5 = r4 * 8
            int r5 = r10.nextBits(r5)
        L_0x0056:
            if (r1 >= r4) goto L_0x0064
            int r6 = r3 + r1
            int r7 = r1 * 8
            int r7 = r5 >>> r7
            byte r7 = (byte) r7
            r11[r6] = r7
            int r1 = r1 + r2
            goto L_0x0056
        L_0x0064:
            return r11
        L_0x0065:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r12)
            java.lang.String r2 = ") must be not greater than toIndex ("
            r1.append(r2)
            r1.append(r13)
            java.lang.String r2 = ")."
            r1.append(r2)
            java.lang.String r0 = r1.toString()
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x008c:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r12)
            java.lang.String r2 = ") or toIndex ("
            r1.append(r2)
            r1.append(r13)
            java.lang.String r2 = ") are out of range: 0.."
            r1.append(r2)
            int r2 = r11.length
            r1.append(r2)
            r2 = 46
            r1.append(r2)
            java.lang.String r0 = r1.toString()
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.random.d.nextBytes(byte[], int, int):byte[]");
    }

    @NotNull
    public byte[] nextBytes(@NotNull byte[] array) {
        k.e(array, "array");
        return nextBytes(array, 0, array.length);
    }

    @NotNull
    public byte[] nextBytes(int size) {
        return nextBytes(new byte[size]);
    }

    /* compiled from: Random.kt */
    public static final class a extends d implements Serializable {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* renamed from: kotlin.random.d$a$a  reason: collision with other inner class name */
        /* compiled from: Random.kt */
        public static final class C0326a implements Serializable {
            @NotNull
            public static final C0326a INSTANCE = new C0326a();
            private static final long serialVersionUID = 0;

            private C0326a() {
            }

            private final Object readResolve() {
                return d.Default;
            }
        }

        private final Object writeReplace() {
            return C0326a.INSTANCE;
        }

        public int nextBits(int bitCount) {
            return d.c.nextBits(bitCount);
        }

        public int nextInt() {
            return d.c.nextInt();
        }

        public int nextInt(int until) {
            return d.c.nextInt(until);
        }

        public int nextInt(int from, int until) {
            return d.c.nextInt(from, until);
        }

        public long nextLong() {
            return d.c.nextLong();
        }

        public long nextLong(long until) {
            return d.c.nextLong(until);
        }

        public long nextLong(long from, long until) {
            return d.c.nextLong(from, until);
        }

        public boolean nextBoolean() {
            return d.c.nextBoolean();
        }

        public double nextDouble() {
            return d.c.nextDouble();
        }

        public double nextDouble(double until) {
            return d.c.nextDouble(until);
        }

        public double nextDouble(double from, double until) {
            return d.c.nextDouble(from, until);
        }

        public float nextFloat() {
            return d.c.nextFloat();
        }

        @NotNull
        public byte[] nextBytes(@NotNull byte[] array) {
            k.e(array, "array");
            return d.c.nextBytes(array);
        }

        @NotNull
        public byte[] nextBytes(int size) {
            return d.c.nextBytes(size);
        }

        @NotNull
        public byte[] nextBytes(@NotNull byte[] array, int fromIndex, int toIndex) {
            k.e(array, "array");
            return d.c.nextBytes(array, fromIndex, toIndex);
        }
    }
}
