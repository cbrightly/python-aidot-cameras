package io.ktor.utils.io.core;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.core.internal.MalformedUTF8InputException;
import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.core.internal.e;
import io.ktor.utils.io.core.internal.g;
import io.ktor.utils.io.pool.d;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractInput.kt */
public abstract class a implements w {
    public static final C0281a c = new C0281a((DefaultConstructorMarker) null);
    private io.ktor.utils.io.core.internal.a d;
    @NotNull
    private ByteBuffer f;
    @NotNull
    private final d<io.ktor.utils.io.core.internal.a> p0;
    private int q;
    private int x;
    private long y;
    private boolean z;

    public a() {
        this((io.ktor.utils.io.core.internal.a) null, 0, (d) null, 7, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: protected */
    public abstract int E(@NotNull ByteBuffer byteBuffer, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void l();

    /* compiled from: Require.kt */
    public static final class c extends e {
        final /* synthetic */ int a;

        public c(int i) {
            this.a = i;
        }

        @NotNull
        public Void a() {
            throw new IllegalArgumentException("Negative discard is not allowed: " + this.a);
        }
    }

    /* compiled from: Require.kt */
    public static final class b extends e {
        @NotNull
        public Void a() {
            throw new IllegalStateException("It should be no tail remaining bytes if current tail is EmptyBuffer");
        }
    }

    public a(@NotNull io.ktor.utils.io.core.internal.a head, long remaining, @NotNull d<io.ktor.utils.io.core.internal.a> pool) {
        k.f(head, CacheEntity.HEAD);
        k.f(pool, "pool");
        this.p0 = pool;
        this.d = head;
        this.f = head.n();
        this.q = head.o();
        this.x = head.s();
        this.y = remaining - ((long) (W() - u0()));
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ a(io.ktor.utils.io.core.internal.a r1, long r2, io.ktor.utils.io.pool.d<io.ktor.utils.io.core.internal.a> r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r6 = r5 & 1
            if (r6 == 0) goto L_0x000a
            io.ktor.utils.io.core.internal.a$f r1 = io.ktor.utils.io.core.internal.a.z4
            io.ktor.utils.io.core.internal.a r1 = r1.a()
        L_0x000a:
            r6 = r5 & 2
            if (r6 == 0) goto L_0x0012
            long r2 = io.ktor.utils.io.core.j.g(r1)
        L_0x0012:
            r5 = r5 & 4
            if (r5 == 0) goto L_0x001c
            io.ktor.utils.io.core.internal.a$f r4 = io.ktor.utils.io.core.internal.a.z4
            io.ktor.utils.io.pool.d r4 = r4.c()
        L_0x001c:
            r0.<init>(r1, r2, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.a.<init>(io.ktor.utils.io.core.internal.a, long, io.ktor.utils.io.pool.d, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final d<io.ktor.utils.io.core.internal.a> F0() {
        return this.p0;
    }

    private final void q1(io.ktor.utils.io.core.internal.a newHead) {
        this.d = newHead;
        this.f = newHead.n();
        this.q = newHead.o();
        this.x = newHead.s();
    }

    @NotNull
    public final io.ktor.utils.io.core.internal.a T() {
        io.ktor.utils.io.core.internal.a it = this.d;
        it.i(this.q);
        return it;
    }

    @NotNull
    public final ByteBuffer o0() {
        return this.f;
    }

    public final void o1(int i) {
        this.q = i;
    }

    public final int u0() {
        return this.q;
    }

    public final int W() {
        return this.x;
    }

    private final void p1(long newValue) {
        long tailSize = 0;
        if (newValue >= 0) {
            if (newValue == 0) {
                io.ktor.utils.io.core.internal.a b1 = this.d.b1();
                long tail = b1 != null ? j.g(b1) : 0;
                if (tail != 0) {
                    throw new IllegalStateException(("tailRemaining is set 0 while there is a tail of size " + tail).toString());
                }
            }
            io.ktor.utils.io.core.internal.a b12 = this.d.b1();
            if (b12 != null) {
                tailSize = j.g(b12);
            }
            if (newValue == tailSize) {
                this.y = newValue;
                return;
            }
            throw new IllegalStateException(("tailRemaining is set to a value that is not consistent with the actual tail: " + newValue + " != " + tailSize).toString());
        }
        throw new IllegalStateException(("tailRemaining is negative: " + newValue).toString());
    }

    public final boolean d1(long min) {
        if (min <= 0) {
            return true;
        }
        int headRemaining = W() - u0();
        if (((long) headRemaining) >= min || ((long) headRemaining) + this.y >= min) {
            return true;
        }
        return t(min);
    }

    public final long n0(@NotNull ByteBuffer destination, long destinationOffset, long offset, long min, long max) {
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        d1(min + offset);
        io.ktor.utils.io.core.internal.a current = T();
        long maxCopySize = Math.min(max, ((long) destination.limit()) - destinationOffset);
        long copied = 0;
        long skip = offset;
        long writePosition = destinationOffset;
        while (copied < min && copied < maxCopySize) {
            c this_$iv = current;
            int chunkSize = this_$iv.s() - this_$iv.o();
            if (((long) chunkSize) > skip) {
                long size = Math.min(((long) chunkSize) - skip, maxCopySize - copied);
                int i = chunkSize;
                io.ktor.utils.io.bits.c.d(current.n(), destination, ((long) current.o()) + skip, size, writePosition);
                copied += size;
                writePosition += size;
                skip = 0;
            } else {
                skip -= (long) chunkSize;
            }
            io.ktor.utils.io.core.internal.a b1 = current.b1();
            if (b1 == null) {
                break;
            }
            current = b1;
            ByteBuffer byteBuffer = destination;
        }
        return copied;
    }

    private final boolean t(long min) {
        io.ktor.utils.io.core.internal.a tail = j.c(this.d);
        long available = ((long) (W() - u0())) + this.y;
        do {
            io.ktor.utils.io.core.internal.a next = z();
            if (next == null) {
                this.z = true;
                return false;
            }
            c this_$iv = next;
            int chunkSize = this_$iv.s() - this_$iv.o();
            if (tail == io.ktor.utils.io.core.internal.a.z4.a()) {
                q1(next);
                tail = next;
            } else {
                tail.g1(next);
                p1(this.y + ((long) chunkSize));
            }
            available += (long) chunkSize;
        } while (available < min);
        return true;
    }

    public final long P0() {
        return ((long) (W() - u0())) + this.y;
    }

    public final boolean j() {
        return (this.q == this.x && this.y == 0) ? false : true;
    }

    public final boolean w0() {
        return W() - u0() == 0 && this.y == 0 && (this.z || s() == null);
    }

    public final void release() {
        io.ktor.utils.io.core.internal.a head = T();
        io.ktor.utils.io.core.internal.a empty = io.ktor.utils.io.core.internal.a.z4.a();
        if (head != empty) {
            q1(empty);
            p1(0);
            j.e(head, this.p0);
        }
    }

    public final void close() {
        release();
        if (!this.z) {
            this.z = true;
        }
        l();
    }

    @Nullable
    public final io.ktor.utils.io.core.internal.a s1() {
        io.ktor.utils.io.core.internal.a head = T();
        io.ktor.utils.io.core.internal.a empty = io.ktor.utils.io.core.internal.a.z4.a();
        if (head == empty) {
            return null;
        }
        q1(empty);
        p1(0);
        return head;
    }

    @Nullable
    public final io.ktor.utils.io.core.internal.a r1() {
        io.ktor.utils.io.core.internal.a head = T();
        io.ktor.utils.io.core.internal.a next = head.b1();
        io.ktor.utils.io.core.internal.a empty = io.ktor.utils.io.core.internal.a.z4.a();
        if (head == empty) {
            return null;
        }
        if (next == null) {
            q1(empty);
            p1(0);
        } else {
            q1(next);
            c this_$iv = next;
            p1(this.y - ((long) (this_$iv.s() - this_$iv.o())));
        }
        head.g1((io.ktor.utils.io.core.internal.a) null);
        return head;
    }

    public final void c(@NotNull io.ktor.utils.io.core.internal.a chain) {
        k.f(chain, "chain");
        a.f fVar = io.ktor.utils.io.core.internal.a.z4;
        if (chain != fVar.a()) {
            long size = j.g(chain);
            if (this.d == fVar.a()) {
                q1(chain);
                p1(size - ((long) (W() - u0())));
                return;
            }
            j.c(this.d).g1(chain);
            p1(this.y + size);
        }
    }

    public final boolean t1(@NotNull io.ktor.utils.io.core.internal.a chain) {
        k.f(chain, "chain");
        c tail = j.c(T());
        c this_$iv = chain;
        int size = this_$iv.s() - this_$iv.o();
        if (size == 0) {
            return false;
        }
        c this_$iv2 = tail;
        if (this_$iv2.m() - this_$iv2.s() < size) {
            return false;
        }
        d.a(tail, chain, size);
        if (T() == tail) {
            this.x = tail.s();
            return true;
        }
        p1(this.y + ((long) size));
        return true;
    }

    public final byte readByte() {
        int index = this.q;
        int nextIndex = index + 1;
        if (nextIndex >= this.x) {
            return j1();
        }
        this.q = nextIndex;
        return this.f.get(index);
    }

    private final byte j1() {
        int index = this.q;
        if (index < this.x) {
            byte value = this.f.get(index);
            this.q = index;
            io.ktor.utils.io.core.internal.a head = this.d;
            head.i(index);
            u(head);
            return value;
        }
        io.ktor.utils.io.core.internal.a f1 = f1(1);
        if (f1 != null) {
            io.ktor.utils.io.core.internal.a head2 = f1;
            byte readByte = head2.readByte();
            g.d(this, head2);
            return readByte;
        }
        g0.a(1);
        throw null;
    }

    public final int m(int n) {
        if (n >= 0) {
            return n(n, 0);
        }
        new c(n).a();
        throw null;
    }

    public final void r(int n) {
        if (m(n) != n) {
            throw new EOFException("Unable to discard " + n + " bytes due to end of packet");
        }
    }

    public final long C0(long n) {
        if (n <= 0) {
            return 0;
        }
        return o(n, 0);
    }

    public static /* synthetic */ String l1(a aVar, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 1) != 0) {
                i = 0;
            }
            if ((i3 & 2) != 0) {
                i2 = Integer.MAX_VALUE;
            }
            return aVar.k1(i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: readText");
    }

    @NotNull
    public final String k1(int min, int max) {
        if (min == 0 && (max == 0 || w0())) {
            return "";
        }
        long remaining = P0();
        if (remaining > 0 && ((long) max) >= remaining) {
            return g0.e(this, (int) remaining, (Charset) null, 2, (Object) null);
        }
        StringBuilder $this$buildString = new StringBuilder(n.e(n.b(min, 16), max));
        i1($this$buildString, min, max);
        String sb = $this$buildString.toString();
        k.b(sb, "StringBuilder(capacity).…builderAction).toString()");
        return sb;
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00d9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int i1(java.lang.Appendable r27, int r28, int r29) {
        /*
            r26 = this;
            r1 = r26
            r2 = r27
            r3 = r28
            r4 = r29
            r0 = 0
            if (r4 != 0) goto L_0x000e
            if (r3 != 0) goto L_0x000e
            return r0
        L_0x000e:
            boolean r5 = r26.w0()
            r6 = 0
            if (r5 == 0) goto L_0x001c
            if (r3 != 0) goto L_0x0018
            return r0
        L_0x0018:
            r1.i(r3)
            throw r6
        L_0x001c:
            if (r4 < r3) goto L_0x00f3
            r5 = 0
            r7 = 0
            r8 = r26
            r9 = 0
            r10 = 1
            r11 = 1
            io.ktor.utils.io.core.internal.a r12 = io.ktor.utils.io.core.internal.g.g(r8, r11)
            if (r12 == 0) goto L_0x00dd
        L_0x002d:
            r13 = r12
            r14 = 0
            r15 = r13
            r16 = 0
            r17 = r15
            r18 = 0
            java.nio.ByteBuffer r19 = r17.n()     // Catch:{ all -> 0x00d2 }
            int r20 = r17.o()     // Catch:{ all -> 0x00d2 }
            int r21 = r17.s()     // Catch:{ all -> 0x00d2 }
            r22 = r21
            r21 = 0
            r0 = r20
        L_0x004a:
            r11 = r22
            if (r0 >= r11) goto L_0x0092
            r22 = r19
            r23 = 0
            r24 = r22
            r25 = 0
            r6 = r24
            r24 = r7
            byte r7 = r6.get(r0)     // Catch:{ all -> 0x008c }
            r6 = r7 & 255(0xff, float:3.57E-43)
            r7 = r6 & 128(0x80, float:1.794E-43)
            r22 = r9
            r9 = 128(0x80, float:1.794E-43)
            if (r7 == r9) goto L_0x0085
            char r7 = (char) r6
            r9 = 0
            if (r5 != r4) goto L_0x006f
            r7 = 0
            goto L_0x0075
        L_0x006f:
            r2.append(r7)     // Catch:{ all -> 0x00ce }
            int r5 = r5 + 1
            r7 = 1
        L_0x0075:
            if (r7 != 0) goto L_0x0079
            goto L_0x0085
        L_0x0079:
            int r0 = r0 + 1
            r9 = r22
            r7 = r24
            r6 = 0
            r22 = r11
            r11 = 1
            goto L_0x004a
        L_0x0085:
            int r7 = r0 - r20
            r15.g(r7)     // Catch:{ all -> 0x00ce }
            r0 = 0
            goto L_0x009f
        L_0x008c:
            r0 = move-exception
            r22 = r9
            r7 = r24
            goto L_0x00d7
        L_0x0092:
            r24 = r7
            r22 = r9
            int r0 = r11 - r20
            r6 = r17
            r6.g(r0)     // Catch:{ all -> 0x00ce }
            r0 = 1
        L_0x009f:
            if (r0 == 0) goto L_0x00a7
            r7 = r24
            r0 = 1
            goto L_0x00b0
        L_0x00a7:
            if (r5 != r4) goto L_0x00ad
            r7 = r24
            r0 = 0
            goto L_0x00b0
        L_0x00ad:
            r6 = 1
            r7 = r6
            r0 = 0
        L_0x00b0:
            if (r0 != 0) goto L_0x00b5
            goto L_0x00c5
        L_0x00b5:
            r10 = 0
            io.ktor.utils.io.core.internal.a r0 = io.ktor.utils.io.core.internal.g.i(r8, r12)     // Catch:{ all -> 0x00cc }
            if (r0 == 0) goto L_0x00c5
            r12 = r0
            r10 = 1
            r9 = r22
            r0 = 0
            r6 = 0
            r11 = 1
            goto L_0x002d
        L_0x00c5:
            if (r10 == 0) goto L_0x00ca
            io.ktor.utils.io.core.internal.g.d(r8, r12)
        L_0x00ca:
            goto L_0x00df
        L_0x00cc:
            r0 = move-exception
            goto L_0x00d7
        L_0x00ce:
            r0 = move-exception
            r7 = r24
            goto L_0x00d7
        L_0x00d2:
            r0 = move-exception
            r24 = r7
            r22 = r9
        L_0x00d7:
            if (r10 == 0) goto L_0x00dc
            io.ktor.utils.io.core.internal.g.d(r8, r12)
        L_0x00dc:
            throw r0
        L_0x00dd:
            r22 = r9
        L_0x00df:
            if (r7 == 0) goto L_0x00eb
            int r0 = r3 - r5
            int r6 = r4 - r5
            int r0 = r1.m1(r2, r0, r6)
            int r0 = r0 + r5
            return r0
        L_0x00eb:
            if (r5 < r3) goto L_0x00ee
            return r5
        L_0x00ee:
            r1.e1(r3, r5)
            r0 = 0
            throw r0
        L_0x00f3:
            r0 = r6
            r1.b1(r3, r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.a.i1(java.lang.Appendable, int, int):int");
    }

    private final Void i(int min) {
        throw new EOFException("at least " + min + " characters required but no bytes available");
    }

    private final Void b1(int min, int max) {
        throw new IllegalArgumentException("min should be less or equal to max but min = " + min + ", max = " + max);
    }

    private final Void e1(int min, int copied) {
        throw new MalformedUTF8InputException("Premature end of stream: expected at least " + min + " chars but had only " + copied);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v6, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v7, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v8, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v9, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v10, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v11, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v13, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v13, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v14, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v16, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v17, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v18, resolved type: java.nio.ByteBuffer} */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r15.g(r9 - r23);
        r26 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x013e, code lost:
        r15.g(((r9 - r23) - r19) + 1);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x020a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int m1(java.lang.Appendable r35, int r36, int r37) {
        /*
            r34 = this;
            r1 = r35
            r2 = r36
            r3 = r37
            r0 = 0
            r4 = r34
            r5 = 1
            r6 = 0
            r7 = 1
            io.ktor.utils.io.core.internal.a r8 = io.ktor.utils.io.core.internal.g.g(r4, r5)
            if (r8 == 0) goto L_0x020e
            r10 = r5
            r11 = r10
            r10 = r8
            r8 = r7
            r7 = r0
        L_0x0017:
            r0 = r10
            r12 = 0
            int r13 = r0.s()     // Catch:{ all -> 0x01ff }
            int r14 = r0.o()     // Catch:{ all -> 0x01ff }
            int r13 = r13 - r14
            r12 = r13
            r13 = 0
            if (r12 < r11) goto L_0x01b9
            r0 = r10
            r14 = 0
            r15 = r0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = r15
            r21 = 0
            java.nio.ByteBuffer r22 = r20.n()     // Catch:{ all -> 0x019e }
            int r23 = r20.o()     // Catch:{ all -> 0x019e }
            int r24 = r20.s()     // Catch:{ all -> 0x019e }
            r25 = r24
            r24 = 0
            r9 = r23
        L_0x004a:
            r26 = -1
            r27 = 0
            r28 = 1
            r29 = r0
            r0 = r25
            if (r9 >= r0) goto L_0x0167
            r25 = r22
            r30 = 0
            r31 = r25
            r32 = 0
            r33 = r5
            r5 = r31
            r31 = r6
            byte r6 = r5.get(r9)     // Catch:{ all -> 0x0161 }
            r5 = r6 & 255(0xff, float:3.57E-43)
            r6 = r5 & 128(0x80, float:1.794E-43)
            if (r6 != 0) goto L_0x00a1
            if (r17 != 0) goto L_0x0095
            char r6 = (char) r5
            r25 = 0
            if (r7 != r3) goto L_0x007a
            r6 = r27
            goto L_0x0081
        L_0x007a:
            r1.append(r6)     // Catch:{ all -> 0x009a }
            int r7 = r7 + 1
            r6 = r28
        L_0x0081:
            if (r6 != 0) goto L_0x008f
            int r6 = r9 - r23
            r15.g(r6)     // Catch:{ all -> 0x009a }
            r25 = r8
            r30 = r11
            goto L_0x0179
        L_0x008f:
            r25 = r8
            r30 = r11
            goto L_0x014f
        L_0x0095:
            io.ktor.utils.io.core.internal.f.j(r17)     // Catch:{ all -> 0x009a }
            r6 = 0
            throw r6     // Catch:{ all -> 0x009a }
        L_0x009a:
            r0 = move-exception
            r25 = r8
            r30 = r11
            goto L_0x01a7
        L_0x00a1:
            if (r17 != 0) goto L_0x00d7
            r6 = 128(0x80, float:1.794E-43)
            r18 = r5
            r25 = r8
            r8 = r28
        L_0x00ab:
            r30 = r11
            r11 = 6
            if (r8 > r11) goto L_0x00c2
            r11 = r18 & r6
            if (r11 == 0) goto L_0x00c1
            int r11 = ~r6
            r18 = r18 & r11
            int r6 = r6 >> 1
            int r17 = r17 + 1
            int r8 = r8 + 1
            r11 = r30
            goto L_0x00ab
        L_0x00c1:
        L_0x00c2:
            r8 = r17
            int r17 = r17 + -1
            int r11 = r0 - r9
            if (r8 <= r11) goto L_0x00d3
            int r11 = r9 - r23
            r15.g(r11)     // Catch:{ all -> 0x019c }
            r26 = r8
            goto L_0x0179
        L_0x00d3:
            r19 = r8
            goto L_0x014f
        L_0x00d7:
            r25 = r8
            r30 = r11
            int r6 = r18 << 6
            r8 = r5 & 127(0x7f, float:1.78E-43)
            r6 = r6 | r8
            int r17 = r17 + -1
            if (r17 != 0) goto L_0x014d
            boolean r8 = io.ktor.utils.io.core.internal.f.g(r6)     // Catch:{ all -> 0x019c }
            if (r8 == 0) goto L_0x0106
            char r8 = (char) r6     // Catch:{ all -> 0x019c }
            r11 = 0
            if (r7 != r3) goto L_0x00f1
            r8 = r27
            goto L_0x00f8
        L_0x00f1:
            r1.append(r8)     // Catch:{ all -> 0x019c }
            int r7 = r7 + 1
            r8 = r28
        L_0x00f8:
            if (r8 != 0) goto L_0x0139
            int r8 = r9 - r23
            int r8 = r8 - r19
            int r8 = r8 + 1
            r15.g(r8)     // Catch:{ all -> 0x019c }
            goto L_0x0179
        L_0x0106:
            boolean r8 = io.ktor.utils.io.core.internal.f.h(r6)     // Catch:{ all -> 0x019c }
            if (r8 == 0) goto L_0x0148
            int r8 = io.ktor.utils.io.core.internal.f.f(r6)     // Catch:{ all -> 0x019c }
            char r8 = (char) r8     // Catch:{ all -> 0x019c }
            r11 = 0
            if (r7 != r3) goto L_0x0119
            r8 = r27
            goto L_0x0120
        L_0x0119:
            r1.append(r8)     // Catch:{ all -> 0x019c }
            int r7 = r7 + 1
            r8 = r28
        L_0x0120:
            if (r8 == 0) goto L_0x013e
            int r8 = io.ktor.utils.io.core.internal.f.i(r6)     // Catch:{ all -> 0x019c }
            char r8 = (char) r8     // Catch:{ all -> 0x019c }
            r11 = 0
            if (r7 != r3) goto L_0x012e
            r8 = r27
            goto L_0x0135
        L_0x012e:
            r1.append(r8)     // Catch:{ all -> 0x019c }
            int r7 = r7 + 1
            r8 = r28
        L_0x0135:
            if (r8 != 0) goto L_0x0139
            goto L_0x013e
        L_0x0139:
            r6 = 0
            r18 = r6
            goto L_0x014f
        L_0x013e:
            int r8 = r9 - r23
            int r8 = r8 - r19
            int r8 = r8 + 1
            r15.g(r8)     // Catch:{ all -> 0x019c }
            goto L_0x0179
        L_0x0148:
            io.ktor.utils.io.core.internal.f.k(r6)     // Catch:{ all -> 0x019c }
            r8 = 0
            throw r8     // Catch:{ all -> 0x019c }
        L_0x014d:
            r18 = r6
        L_0x014f:
            int r9 = r9 + 1
            r8 = r25
            r11 = r30
            r6 = r31
            r5 = r33
            r25 = r0
            r0 = r29
            goto L_0x004a
        L_0x0161:
            r0 = move-exception
            r25 = r8
            r30 = r11
            goto L_0x01a7
        L_0x0167:
            r33 = r5
            r31 = r6
            r25 = r8
            r30 = r11
            int r0 = r0 - r23
            r5 = r20
            r5.g(r0)     // Catch:{ all -> 0x019c }
            r26 = r27
        L_0x0179:
            r0 = r26
            if (r0 != 0) goto L_0x0181
            r27 = r28
            goto L_0x0187
        L_0x0181:
            if (r0 <= 0) goto L_0x0186
            r27 = r0
            goto L_0x0187
        L_0x0186:
        L_0x0187:
            r11 = r27
            r0 = r10
            r5 = 0
            int r6 = r0.s()     // Catch:{ all -> 0x0197 }
            int r8 = r0.o()     // Catch:{ all -> 0x0197 }
            int r6 = r6 - r8
            goto L_0x01c2
        L_0x0197:
            r0 = move-exception
            r8 = r25
            goto L_0x0208
        L_0x019c:
            r0 = move-exception
            goto L_0x01a7
        L_0x019e:
            r0 = move-exception
            r33 = r5
            r31 = r6
            r25 = r8
            r30 = r11
        L_0x01a7:
            r5 = r10
            r6 = 0
            int r8 = r5.s()     // Catch:{ all -> 0x01b3 }
            int r9 = r5.o()     // Catch:{ all -> 0x01b3 }
            int r8 = r8 - r9
            throw r0     // Catch:{ all -> 0x01b3 }
        L_0x01b3:
            r0 = move-exception
            r8 = r25
            r11 = r30
            goto L_0x0208
        L_0x01b9:
            r33 = r5
            r31 = r6
            r25 = r8
            r30 = r11
            r6 = r12
        L_0x01c2:
            r8 = 0
            if (r6 != 0) goto L_0x01ce
            io.ktor.utils.io.core.internal.a r0 = io.ktor.utils.io.core.internal.g.i(r4, r10)     // Catch:{ all -> 0x01cc }
            goto L_0x01e9
        L_0x01cc:
            r0 = move-exception
            goto L_0x0208
        L_0x01ce:
            if (r6 < r11) goto L_0x01e2
            r0 = r10
            r5 = 0
            int r9 = r0.l()     // Catch:{ all -> 0x01cc }
            int r13 = r0.m()     // Catch:{ all -> 0x01cc }
            int r9 = r9 - r13
            r0 = 8
            if (r9 >= r0) goto L_0x01e0
            goto L_0x01e2
        L_0x01e0:
            r0 = r10
            goto L_0x01e9
        L_0x01e2:
            io.ktor.utils.io.core.internal.g.d(r4, r10)     // Catch:{ all -> 0x01cc }
            io.ktor.utils.io.core.internal.a r0 = io.ktor.utils.io.core.internal.g.g(r4, r11)     // Catch:{ all -> 0x01cc }
        L_0x01e9:
            if (r0 != 0) goto L_0x01ed
            goto L_0x01f1
        L_0x01ed:
            r10 = r0
            r8 = 1
            if (r11 > 0) goto L_0x01f9
        L_0x01f1:
            if (r8 == 0) goto L_0x01f6
            io.ktor.utils.io.core.internal.g.d(r4, r10)
        L_0x01f6:
            r0 = r7
            goto L_0x0212
        L_0x01f9:
            r6 = r31
            r5 = r33
            goto L_0x0017
        L_0x01ff:
            r0 = move-exception
            r33 = r5
            r31 = r6
            r25 = r8
            r30 = r11
        L_0x0208:
            if (r8 == 0) goto L_0x020d
            io.ktor.utils.io.core.internal.g.d(r4, r10)
        L_0x020d:
            throw r0
        L_0x020e:
            r33 = r5
            r31 = r6
        L_0x0212:
            if (r0 < r2) goto L_0x0215
            return r0
        L_0x0215:
            r4 = r34
            r4.e1(r2, r0)
            r5 = 0
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.a.m1(java.lang.Appendable, int, int):int");
    }

    private final long o(long n, long skipped) {
        io.ktor.utils.io.core.internal.a current;
        while (n != 0 && (current = f1(1)) != null) {
            c this_$iv = current;
            int size = (int) Math.min((long) (this_$iv.s() - this_$iv.o()), n);
            current.g(size);
            this.q += size;
            a(current);
            n -= (long) size;
            skipped += (long) size;
        }
        return skipped;
    }

    private final int n(int n, int skipped) {
        while (n != 0) {
            io.ktor.utils.io.core.internal.a current = f1(1);
            if (current == null) {
                return skipped;
            }
            c this_$iv = current;
            int size = Math.min(this_$iv.s() - this_$iv.o(), n);
            current.g(size);
            this.q += size;
            a(current);
            n -= size;
            skipped += size;
        }
        return skipped;
    }

    @Nullable
    public final io.ktor.utils.io.core.internal.a g1(int minSize) {
        return h1(minSize, T());
    }

    @Nullable
    public final io.ktor.utils.io.core.internal.a x(@NotNull io.ktor.utils.io.core.internal.a current) {
        k.f(current, "current");
        return u(current);
    }

    @Nullable
    public final io.ktor.utils.io.core.internal.a u(@NotNull io.ktor.utils.io.core.internal.a current) {
        k.f(current, "current");
        return v(current, io.ktor.utils.io.core.internal.a.z4.a());
    }

    public final void I(@NotNull io.ktor.utils.io.core.internal.a current) {
        k.f(current, "current");
        io.ktor.utils.io.core.internal.a next = current.b1();
        if (next != null) {
            c this_$iv = current;
            int remaining = this_$iv.s() - this_$iv.o();
            c this_$iv2 = current;
            int overrunSize = Math.min(remaining, 8 - (this_$iv2.l() - this_$iv2.m()));
            if (next.r() < overrunSize) {
                J(current);
                return;
            }
            g.f(next, overrunSize);
            if (remaining > overrunSize) {
                current.t();
                this.x = current.s();
                p1(this.y + ((long) overrunSize));
                return;
            }
            q1(next);
            c this_$iv3 = next;
            p1(this.y - ((long) ((this_$iv3.s() - this_$iv3.o()) - overrunSize)));
            current.P0();
            current.e1(this.p0);
            return;
        }
        J(current);
    }

    private final void J(io.ktor.utils.io.core.internal.a current) {
        if (!this.z || current.b1() != null) {
            c this_$iv = current;
            int size = this_$iv.s() - this_$iv.o();
            c this_$iv2 = current;
            int overrun = Math.min(size, 8 - (this_$iv2.l() - this_$iv2.m()));
            if (size > overrun) {
                P(current, size, overrun);
            } else {
                io.ktor.utils.io.core.internal.a p02 = this.p0.p0();
                p02.x(8);
                p02.g1(current.P0());
                d.a(p02, current, size);
                q1(p02);
            }
            current.e1(this.p0);
            return;
        }
        this.q = current.o();
        this.x = current.s();
        p1(0);
    }

    private final void P(io.ktor.utils.io.core.internal.a current, int size, int overrun) {
        io.ktor.utils.io.core.internal.a chunk1 = this.p0.p0();
        io.ktor.utils.io.core.internal.a chunk2 = this.p0.p0();
        chunk1.x(8);
        chunk2.x(8);
        chunk1.g1(chunk2);
        chunk2.g1(current.P0());
        d.a(chunk1, current, size - overrun);
        d.a(chunk2, current, overrun);
        q1(chunk1);
        p1(j.g(chunk2));
    }

    private final io.ktor.utils.io.core.internal.a v(io.ktor.utils.io.core.internal.a current, io.ktor.utils.io.core.internal.a empty) {
        while (current != empty) {
            io.ktor.utils.io.core.internal.a next = current.P0();
            current.e1(this.p0);
            if (next == null) {
                q1(empty);
                p1(0);
                current = empty;
            } else {
                c $this$canRead$iv = next;
                if ($this$canRead$iv.s() > $this$canRead$iv.o()) {
                    q1(next);
                    c this_$iv = next;
                    p1(this.y - ((long) (this_$iv.s() - this_$iv.o())));
                    return next;
                }
                current = next;
            }
        }
        return s();
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    @Nullable
    public io.ktor.utils.io.core.internal.a z() {
        io.ktor.utils.io.core.internal.a buffer = this.p0.p0();
        try {
            buffer.x(8);
            c this_$iv = buffer;
            int copied = E(buffer.n(), buffer.s(), this_$iv.m() - this_$iv.s());
            if (copied == 0) {
                boolean z2 = true;
                this.z = true;
                c $this$canRead$iv = buffer;
                if ($this$canRead$iv.s() <= $this$canRead$iv.o()) {
                    z2 = false;
                }
                if (!z2) {
                    buffer.e1(this.p0);
                    return null;
                }
            }
            buffer.a(copied);
            return buffer;
        } catch (Throwable t) {
            buffer.e1(this.p0);
            throw t;
        }
    }

    /* access modifiers changed from: protected */
    public final void a1() {
        if (!this.z) {
            this.z = true;
        }
    }

    private final io.ktor.utils.io.core.internal.a s() {
        if (this.z) {
            return null;
        }
        io.ktor.utils.io.core.internal.a chunk = z();
        if (chunk == null) {
            this.z = true;
            return null;
        }
        g(chunk);
        return chunk;
    }

    private final void g(io.ktor.utils.io.core.internal.a chunk) {
        io.ktor.utils.io.core.internal.a tail = j.c(this.d);
        if (tail == io.ktor.utils.io.core.internal.a.z4.a()) {
            q1(chunk);
            long j = 0;
            if (this.y == 0) {
                io.ktor.utils.io.core.internal.a b1 = chunk.b1();
                if (b1 != null) {
                    j = j.g(b1);
                }
                p1(j);
                return;
            }
            new b().a();
            throw null;
        }
        tail.g1(chunk);
        p1(this.y + j.g(chunk));
    }

    @Nullable
    public final io.ktor.utils.io.core.internal.a f1(int minSize) {
        io.ktor.utils.io.core.internal.a head = T();
        if (this.x - this.q >= minSize) {
            return head;
        }
        return h1(minSize, head);
    }

    private final io.ktor.utils.io.core.internal.a h1(int minSize, io.ktor.utils.io.core.internal.a head) {
        while (true) {
            int headSize = W() - u0();
            if (headSize >= minSize) {
                return head;
            }
            io.ktor.utils.io.core.internal.a next = head.b1();
            if (next == null) {
                next = s();
            }
            if (next == null) {
                return null;
            }
            if (headSize == 0) {
                if (head != io.ktor.utils.io.core.internal.a.z4.a()) {
                    n1(head);
                }
                head = next;
            } else {
                int copied = d.a(head, next, minSize - headSize);
                this.x = head.s();
                p1(this.y - ((long) copied));
                c $this$canRead$iv = next;
                if (!($this$canRead$iv.s() > $this$canRead$iv.o())) {
                    head.g1((io.ktor.utils.io.core.internal.a) null);
                    head.g1(next.P0());
                    next.e1(this.p0);
                } else {
                    next.z(copied);
                }
                c this_$iv = head;
                if (this_$iv.s() - this_$iv.o() >= minSize) {
                    return head;
                }
                if (minSize > 8) {
                    c1(minSize);
                    throw null;
                }
            }
        }
    }

    private final Void c1(int minSize) {
        throw new IllegalStateException("minSize of " + minSize + " is too big (should be less than 8)");
    }

    private final void a(io.ktor.utils.io.core.internal.a head) {
        c this_$iv = head;
        if (this_$iv.s() - this_$iv.o() == 0) {
            n1(head);
        }
    }

    @NotNull
    public final io.ktor.utils.io.core.internal.a n1(@NotNull io.ktor.utils.io.core.internal.a head) {
        k.f(head, CacheEntity.HEAD);
        io.ktor.utils.io.core.internal.a next = head.P0();
        if (next == null) {
            next = io.ktor.utils.io.core.internal.a.z4.a();
        }
        q1(next);
        c this_$iv = next;
        p1(this.y - ((long) (this_$iv.s() - this_$iv.o())));
        head.e1(this.p0);
        return next;
    }

    /* renamed from: io.ktor.utils.io.core.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractInput.kt */
    public static final class C0281a {
        private C0281a() {
        }

        public /* synthetic */ C0281a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
