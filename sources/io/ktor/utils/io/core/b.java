package io.ktor.utils.io.core;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.maps.android.BuildConfig;
import io.ktor.utils.io.bits.c;
import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.core.internal.f;
import io.ktor.utils.io.pool.d;
import java.io.EOFException;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractOutput.kt */
public abstract class b implements Appendable, c0 {
    private final int a1;
    private a c;
    private a d;
    @NotNull
    private ByteBuffer f;
    @NotNull
    private l p0;
    @NotNull
    private final d<a> p1;
    private int q;
    private int x;
    private int y;
    private int z;

    /* access modifiers changed from: protected */
    public abstract void s();

    /* access modifiers changed from: protected */
    public abstract void t(@NotNull ByteBuffer byteBuffer, int i, int i2);

    public b(int headerSizeHint, @NotNull d<a> pool) {
        k.f(pool, "pool");
        this.a1 = headerSizeHint;
        this.p1 = pool;
        this.f = c.b.a();
        this.p0 = l.BIG_ENDIAN;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final d<a> x() {
        return this.p1;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public b(@NotNull d<a> pool) {
        this(0, pool);
        k.f(pool, "pool");
    }

    public b() {
        this(a.z4.c());
    }

    @NotNull
    public final a v() {
        a aVar = this.c;
        return aVar != null ? aVar : a.z4.a();
    }

    public final int E() {
        return this.q;
    }

    public final int z() {
        return this.x;
    }

    /* access modifiers changed from: protected */
    public final int I() {
        return this.z + (this.q - this.y);
    }

    public final void flush() {
        u();
    }

    private final void u() {
        a oldTail = P();
        if (oldTail != null) {
            a current$iv = oldTail;
            while (true) {
                c chunk = current$iv;
                try {
                    c this_$iv = chunk;
                    t(chunk.n(), chunk.o(), this_$iv.s() - this_$iv.o());
                    a b1 = current$iv.b1();
                    if (b1 != null) {
                        current$iv = b1;
                    } else {
                        return;
                    }
                } finally {
                    j.e(oldTail, this.p1);
                }
            }
        }
    }

    @Nullable
    public final a P() {
        a head = this.c;
        if (head == null) {
            return null;
        }
        a aVar = this.d;
        if (aVar != null) {
            aVar.c(this.q);
        }
        this.c = null;
        this.d = null;
        this.q = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.f = c.b.a();
        return head;
    }

    public final void a() {
        a head = v();
        if (head != a.z4.a()) {
            if (head.b1() == null) {
                head.J();
                head.z(this.a1);
                head.x(8);
                int s = head.s();
                this.q = s;
                this.y = s;
                this.x = head.m();
                return;
            }
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    public final void r(@NotNull a buffer) {
        k.f(buffer, "buffer");
        if (buffer.b1() == null) {
            m(buffer, buffer, 0);
            return;
        }
        throw new IllegalStateException("It should be a single buffer chunk.".toString());
    }

    public final void l(@NotNull a head) {
        k.f(head, CacheEntity.HEAD);
        a tail = j.c(head);
        c this_$iv = tail;
        long $this$toIntOrFail$iv = j.g(head) - ((long) (this_$iv.s() - this_$iv.o()));
        if ($this$toIntOrFail$iv < ((long) Integer.MAX_VALUE)) {
            m(head, tail, (int) $this$toIntOrFail$iv);
        } else {
            io.ktor.utils.io.core.internal.d.a($this$toIntOrFail$iv, "total size increase");
            throw null;
        }
    }

    private final a o() {
        a p02 = this.p1.p0();
        p02.x(8);
        r(p02);
        return p02;
    }

    private final void m(a head, a newTail, int chainedSizeDelta) {
        a _tail = this.d;
        if (_tail == null) {
            this.c = head;
            this.z = 0;
        } else {
            _tail.g1(head);
            int tailPosition = this.q;
            _tail.c(tailPosition);
            this.z += tailPosition - this.y;
        }
        this.d = newTail;
        this.z += chainedSizeDelta;
        this.f = newTail.n();
        this.q = newTail.s();
        this.y = newTail.o();
        this.x = newTail.m();
    }

    public final void writeByte(byte v) {
        int index = this.q;
        if (index < this.x) {
            this.q = index + 1;
            this.f.put(index, v);
            return;
        }
        T(v);
    }

    private final void T(byte v) {
        o().writeByte(v);
        this.q++;
    }

    public final void close() {
        try {
            flush();
        } finally {
            s();
        }
    }

    @NotNull
    /* renamed from: g */
    public b append(char c2) {
        int tailPosition = this.q;
        byte value$iv$iv = 3;
        if (this.x - tailPosition >= 3) {
            ByteBuffer $this$putUtf8Char$iv = this.f;
            int v$iv = c2;
            if (v$iv >= 0 && 127 >= v$iv) {
                $this$putUtf8Char$iv.put(tailPosition, (byte) v$iv);
                value$iv$iv = 1;
            } else if (128 <= v$iv && 2047 >= v$iv) {
                $this$putUtf8Char$iv.put(tailPosition, (byte) (((v$iv >> 6) & 31) | Opcodes.CHECKCAST));
                $this$putUtf8Char$iv.put(tailPosition + 1, (byte) ((v$iv & 63) | 128));
                value$iv$iv = 2;
            } else if (2048 <= v$iv && 65535 >= v$iv) {
                $this$putUtf8Char$iv.put(tailPosition, (byte) (((v$iv >> 12) & 15) | 224));
                $this$putUtf8Char$iv.put(tailPosition + 1, (byte) (((v$iv >> 6) & 63) | 128));
                $this$putUtf8Char$iv.put(tailPosition + 2, (byte) (128 | (v$iv & 63)));
            } else if (65536 > v$iv || 1114111 < v$iv) {
                f.k(v$iv);
                throw null;
            } else {
                $this$putUtf8Char$iv.put(tailPosition, (byte) (((v$iv >> 18) & 7) | 240));
                ByteBuffer $this$iv$iv$iv = $this$putUtf8Char$iv;
                $this$iv$iv$iv.put(tailPosition + 1, (byte) (((v$iv >> 12) & 63) | 128));
                $this$iv$iv$iv.put(tailPosition + 2, (byte) (((v$iv >> 6) & 63) | 128));
                $this$putUtf8Char$iv.put(tailPosition + 3, (byte) ((v$iv & 63) | 128));
                value$iv$iv = 4;
            }
            this.q = tailPosition + value$iv$iv;
            return this;
        }
        n(c2);
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00f1 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00f7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void n(char r19) {
        /*
            r18 = this;
            r0 = 3
            r1 = r18
            r2 = r0
            r3 = 0
            io.ktor.utils.io.core.internal.a r4 = r1.J(r2)
            r0 = r4
            r5 = 0
            java.nio.ByteBuffer r6 = r0.n()     // Catch:{ all -> 0x0109 }
            int r7 = r0.s()     // Catch:{ all -> 0x0109 }
            r8 = r19
            r9 = 0
            r10 = 127(0x7f, float:1.78E-43)
            if (r8 >= 0) goto L_0x001d
            goto L_0x0029
        L_0x001d:
            if (r10 < r8) goto L_0x0029
            byte r10 = (byte) r8     // Catch:{ all -> 0x0109 }
            r12 = r6
            r13 = 0
            r12.put(r7, r10)     // Catch:{ all -> 0x0109 }
            r10 = 1
            goto L_0x00e4
        L_0x0029:
            r10 = 2047(0x7ff, float:2.868E-42)
            r12 = 128(0x80, float:1.794E-43)
            if (r12 <= r8) goto L_0x0030
            goto L_0x0056
        L_0x0030:
            if (r10 < r8) goto L_0x0056
            int r10 = r8 >> 6
            r10 = r10 & 31
            r10 = r10 | 192(0xc0, float:2.69E-43)
            byte r10 = (byte) r10     // Catch:{ all -> 0x0109 }
            r13 = r6
            r14 = 0
            r15 = r13
            r16 = 0
            r15.put(r7, r10)     // Catch:{ all -> 0x0109 }
            int r10 = r7 + 1
            r13 = r8 & 63
            r12 = r12 | r13
            byte r12 = (byte) r12     // Catch:{ all -> 0x0109 }
            r13 = r6
            r14 = 0
            r15 = r13
            r16 = 0
            r15.put(r10, r12)     // Catch:{ all -> 0x0109 }
            r10 = 2
            goto L_0x00e4
        L_0x0056:
            r10 = 65535(0xffff, float:9.1834E-41)
            r13 = 2048(0x800, float:2.87E-42)
            if (r13 <= r8) goto L_0x005e
            goto L_0x0097
        L_0x005e:
            if (r10 < r8) goto L_0x0097
            int r10 = r8 >> 12
            r10 = r10 & 15
            r10 = r10 | 224(0xe0, float:3.14E-43)
            byte r10 = (byte) r10     // Catch:{ all -> 0x0109 }
            r13 = r6
            r14 = 0
            r15 = r13
            r16 = 0
            r15.put(r7, r10)     // Catch:{ all -> 0x0109 }
            int r10 = r7 + 1
            int r13 = r8 >> 6
            r13 = r13 & 63
            r13 = r13 | r12
            byte r13 = (byte) r13     // Catch:{ all -> 0x0109 }
            r14 = r6
            r15 = 0
            r16 = r14
            r17 = 0
            r11 = r16
            r11.put(r10, r13)     // Catch:{ all -> 0x0109 }
            int r10 = r7 + 2
            r11 = r8 & 63
            r11 = r11 | r12
            byte r11 = (byte) r11     // Catch:{ all -> 0x0109 }
            r12 = r6
            r13 = 0
            r14 = r12
            r15 = 0
            r14.put(r10, r11)     // Catch:{ all -> 0x0109 }
            r10 = 3
            goto L_0x00e4
        L_0x0097:
            r10 = 1114111(0x10ffff, float:1.561202E-39)
            r11 = 65536(0x10000, float:9.18355E-41)
            if (r11 > r8) goto L_0x0104
            if (r10 < r8) goto L_0x0104
            int r10 = r8 >> 18
            r10 = r10 & 7
            r10 = r10 | 240(0xf0, float:3.36E-43)
            byte r10 = (byte) r10     // Catch:{ all -> 0x0109 }
            r11 = r6
            r13 = 0
            r14 = r11
            r15 = 0
            r14.put(r7, r10)     // Catch:{ all -> 0x0109 }
            int r10 = r7 + 1
            int r11 = r8 >> 12
            r11 = r11 & 63
            r11 = r11 | r12
            byte r11 = (byte) r11     // Catch:{ all -> 0x0109 }
            r13 = r6
            r14 = 0
            r15 = r13
            r16 = 0
            r15.put(r10, r11)     // Catch:{ all -> 0x0109 }
            int r10 = r7 + 2
            int r11 = r8 >> 6
            r11 = r11 & 63
            r11 = r11 | r12
            byte r11 = (byte) r11     // Catch:{ all -> 0x0109 }
            r13 = r6
            r14 = 0
            r15 = r13
            r16 = 0
            r15.put(r10, r11)     // Catch:{ all -> 0x0109 }
            int r10 = r7 + 3
            r11 = r8 & 63
            r11 = r11 | r12
            byte r11 = (byte) r11     // Catch:{ all -> 0x0109 }
            r12 = r6
            r13 = 0
            r14 = r12
            r15 = 0
            r14.put(r10, r11)     // Catch:{ all -> 0x0109 }
            r10 = 4
        L_0x00e4:
            r6 = r10
            r0.a(r6)     // Catch:{ all -> 0x0109 }
            if (r6 < 0) goto L_0x00ee
            r11 = 1
            goto L_0x00ef
        L_0x00ee:
            r11 = 0
        L_0x00ef:
            if (r11 == 0) goto L_0x00f7
            r1.c()
            return
        L_0x00f7:
            r0 = 0
            java.lang.String r5 = "The returned value shouldn't be negative"
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0109 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0109 }
            r0.<init>(r5)     // Catch:{ all -> 0x0109 }
            throw r0     // Catch:{ all -> 0x0109 }
        L_0x0104:
            io.ktor.utils.io.core.internal.f.k(r8)     // Catch:{ all -> 0x0109 }
            r10 = 0
            throw r10     // Catch:{ all -> 0x0109 }
        L_0x0109:
            r0 = move-exception
            r1.c()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.b.n(char):void");
    }

    @NotNull
    /* renamed from: i */
    public b append(@Nullable CharSequence csq) {
        if (csq == null) {
            append(BuildConfig.TRAVIS, 0, 4);
        } else {
            append(csq, 0, csq.length());
        }
        return this;
    }

    @NotNull
    /* renamed from: j */
    public b append(@Nullable CharSequence csq, int start, int end) {
        if (csq == null) {
            return append(BuildConfig.TRAVIS, start, end);
        }
        g0.f(this, csq, start, end, kotlin.text.c.a);
        return this;
    }

    public final void W(@NotNull q p) {
        k.f(p, "p");
        a foreignStolen = p.s1();
        if (foreignStolen == null) {
            p.release();
            return;
        }
        a _tail = this.d;
        if (_tail == null) {
            l(foreignStolen);
        } else {
            F0(_tail, foreignStolen, p);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00ae  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void F0(io.ktor.utils.io.core.internal.a r11, io.ktor.utils.io.core.internal.a r12, io.ktor.utils.io.core.q r13) {
        /*
            r10 = this;
            int r0 = r10.q
            r11.c(r0)
            r0 = r11
            r1 = 0
            int r2 = r0.s()
            int r3 = r0.o()
            int r2 = r2 - r3
            r0 = r2
            r1 = r12
            r2 = 0
            int r3 = r1.s()
            int r4 = r1.o()
            int r3 = r3 - r4
            r1 = r3
            int r2 = io.ktor.utils.io.core.f0.c()
            r3 = -1
            if (r1 >= r2) goto L_0x003f
            r4 = r11
            r5 = 0
            int r6 = r4.l()
            int r7 = r4.m()
            int r6 = r6 - r7
            r5 = 0
            int r7 = r4.m()
            int r8 = r4.s()
            int r7 = r7 - r8
            int r6 = r6 + r7
            if (r1 > r6) goto L_0x003f
            r4 = r1
            goto L_0x0040
        L_0x003f:
            r4 = r3
        L_0x0040:
            if (r0 >= r2) goto L_0x0052
            int r5 = r12.r()
            if (r0 > r5) goto L_0x0052
            boolean r5 = io.ktor.utils.io.core.internal.b.a(r12)
            if (r5 == 0) goto L_0x0052
            r5 = r0
            goto L_0x0053
        L_0x0052:
            r5 = r3
        L_0x0053:
            if (r4 != r3) goto L_0x005d
            if (r5 != r3) goto L_0x005d
            r10.l(r12)
            goto L_0x00bb
        L_0x005d:
            if (r5 == r3) goto L_0x008b
            if (r4 > r5) goto L_0x0062
            goto L_0x008b
        L_0x0062:
            if (r4 == r3) goto L_0x0087
            if (r5 >= r4) goto L_0x0067
            goto L_0x0087
        L_0x0067:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "prep = "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r7 = ", app = "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            r3.<init>(r6)
            throw r3
        L_0x0087:
            r10.P0(r12, r11)
            goto L_0x00bb
        L_0x008b:
            r3 = r11
            r6 = 0
            int r7 = r3.m()
            int r8 = r3.s()
            int r7 = r7 - r8
            r6 = 0
            int r8 = r3.l()
            int r9 = r3.m()
            int r8 = r8 - r9
            int r7 = r7 + r8
            io.ktor.utils.io.core.d.a(r11, r12, r7)
            r10.c()
            io.ktor.utils.io.core.internal.a r3 = r12.P0()
            if (r3 == 0) goto L_0x00b4
            r6 = 0
            r10.l(r3)
        L_0x00b4:
            io.ktor.utils.io.pool.d r3 = r13.F0()
            r12.e1(r3)
        L_0x00bb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.b.F0(io.ktor.utils.io.core.internal.a, io.ktor.utils.io.core.internal.a, io.ktor.utils.io.core.q):void");
    }

    private final void P0(a foreignStolen, a tail) {
        d.c(foreignStolen, tail);
        a _head = this.c;
        if (_head != null) {
            if (_head == tail) {
                this.c = foreignStolen;
            } else {
                a pre = _head;
                while (true) {
                    a next = pre.b1();
                    if (next == null) {
                        k.n();
                    }
                    if (next == tail) {
                        break;
                    }
                    pre = next;
                }
                pre.g1(foreignStolen);
            }
            tail.e1(this.p1);
            this.d = j.c(foreignStolen);
            return;
        }
        throw new IllegalStateException("head should't be null since it is already handled in the fast-path".toString());
    }

    public final void o0(@NotNull q p, int n) {
        int positionAfter$iv;
        int s;
        k.f(p, "p");
        int remaining = n;
        while (remaining > 0) {
            a this_$iv = p;
            int headRemaining = this_$iv.W() - this_$iv.u0();
            if (headRemaining <= remaining) {
                remaining -= headRemaining;
                a r1 = p.r1();
                if (r1 != null) {
                    r(r1);
                } else {
                    throw new EOFException("Unexpected end of packet");
                }
            } else {
                a $this$read$iv = p;
                a buffer$iv = $this$read$iv.f1(1);
                if (buffer$iv != null) {
                    int positionBefore$iv = buffer$iv.o();
                    try {
                        e0.a(this, buffer$iv, remaining);
                        if (positionAfter$iv < positionBefore$iv) {
                            throw new IllegalStateException("Buffer's position shouldn't be rewinded");
                        } else if (positionAfter$iv != s) {
                            $this$read$iv.o1(positionAfter$iv);
                            return;
                        } else {
                            return;
                        }
                    } finally {
                        positionAfter$iv = buffer$iv.o();
                        if (positionAfter$iv >= positionBefore$iv) {
                            if (positionAfter$iv == buffer$iv.s()) {
                                $this$read$iv.u(buffer$iv);
                            } else {
                                $this$read$iv.o1(positionAfter$iv);
                            }
                            throw th;
                        }
                        IllegalStateException illegalStateException = new IllegalStateException("Buffer's position shouldn't be rewinded");
                    }
                } else {
                    g0.a(1);
                    throw null;
                }
            }
        }
    }

    public final void u0(@NotNull q p, long n) {
        k.f(p, "p");
        long remaining = n;
        while (remaining > 0) {
            a this_$iv = p;
            long headRemaining = (long) (this_$iv.W() - this_$iv.u0());
            if (headRemaining <= remaining) {
                remaining -= headRemaining;
                a r1 = p.r1();
                if (r1 != null) {
                    r(r1);
                } else {
                    throw new EOFException("Unexpected end of packet");
                }
            } else {
                a $this$read$iv = p;
                a f1 = $this$read$iv.f1(1);
                if (f1 != null) {
                    a buffer$iv = f1;
                    int positionBefore$iv = buffer$iv.o();
                    try {
                        e0.a(this, buffer$iv, (int) remaining);
                        int positionAfter$iv = buffer$iv.o();
                        if (positionAfter$iv < positionBefore$iv) {
                            throw new IllegalStateException("Buffer's position shouldn't be rewinded");
                        } else if (positionAfter$iv == buffer$iv.s()) {
                            $this$read$iv.u(buffer$iv);
                            return;
                        } else {
                            $this$read$iv.o1(positionAfter$iv);
                            return;
                        }
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        int positionAfter$iv2 = buffer$iv.o();
                        if (positionAfter$iv2 >= positionBefore$iv) {
                            if (positionAfter$iv2 == buffer$iv.s()) {
                                $this$read$iv.u(buffer$iv);
                            } else {
                                $this$read$iv.o1(positionAfter$iv2);
                            }
                            throw th2;
                        }
                        throw new IllegalStateException("Buffer's position shouldn't be rewinded");
                    }
                } else {
                    g0.a(1);
                    throw null;
                }
            }
        }
    }

    public final void release() {
        close();
    }

    @NotNull
    public final a J(int n) {
        a it;
        if (z() - E() < n || (it = this.d) == null) {
            return o();
        }
        it.c(this.q);
        return it;
    }

    public final void c() {
        a it = this.d;
        if (it != null) {
            this.q = it.s();
        }
    }
}
