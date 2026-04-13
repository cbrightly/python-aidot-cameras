package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.asm.Opcodes;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.amazonaws.kinesisvideo.producer.Time;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.Closeable;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Buffer.kt */
public final class c implements e, d, Cloneable, ByteChannel {
    @Nullable
    public y c;
    private long d;

    public static /* synthetic */ a v(c cVar, a aVar, int i, Object obj) {
        if ((i & 1) != 0) {
            aVar = new a();
        }
        return cVar.u(aVar);
    }

    public /* synthetic */ g cursor() {
        return d0.a(this);
    }

    public final void d1(long j) {
        this.d = j;
    }

    public final long e1() {
        return this.d;
    }

    @NotNull
    public c buffer() {
        return this;
    }

    @NotNull
    public c getBuffer() {
        return this;
    }

    /* renamed from: okio.c$c  reason: collision with other inner class name */
    /* compiled from: Buffer.kt */
    public static final class C0480c extends OutputStream {
        final /* synthetic */ c c;

        C0480c(c this$0) {
            this.c = this$0;
        }

        public void write(int b) {
            this.c.writeByte(b);
        }

        public void write(@NotNull byte[] data, int offset, int byteCount) {
            k.e(data, "data");
            this.c.write(data, offset, byteCount);
        }

        public void flush() {
        }

        public void close() {
        }

        @NotNull
        public String toString() {
            return this.c + ".outputStream()";
        }
    }

    @NotNull
    public OutputStream outputStream() {
        return new C0480c(this);
    }

    @NotNull
    /* renamed from: m */
    public c emitCompleteSegments() {
        return this;
    }

    @NotNull
    /* renamed from: l */
    public c emit() {
        return this;
    }

    public boolean r0() {
        return this.d == 0;
    }

    public void k0(long byteCount) {
        if (this.d < byteCount) {
            throw new EOFException();
        }
    }

    public boolean request(long byteCount) {
        return this.d >= byteCount;
    }

    @NotNull
    public e peek() {
        return p.d(new u(this));
    }

    /* compiled from: Buffer.kt */
    public static final class b extends InputStream {
        final /* synthetic */ c c;

        b(c this$0) {
            this.c = this$0;
        }

        public int read() {
            if (this.c.e1() > 0) {
                return this.c.readByte() & 255;
            }
            return -1;
        }

        public int read(@NotNull byte[] sink, int offset, int byteCount) {
            k.e(sink, "sink");
            return this.c.read(sink, offset, byteCount);
        }

        public int available() {
            return (int) Math.min(this.c.e1(), (long) Integer.MAX_VALUE);
        }

        public void close() {
        }

        @NotNull
        public String toString() {
            return this.c + ".inputStream()";
        }
    }

    @NotNull
    public InputStream Y0() {
        return new b(this);
    }

    @NotNull
    public final c j(@NotNull c out, long offset, long byteCount) {
        c cVar = out;
        k.e(out, "out");
        long offset$iv = offset;
        long byteCount$iv = byteCount;
        i0.b(e1(), offset$iv, byteCount$iv);
        if (byteCount$iv != 0) {
            out.d1(out.e1() + byteCount$iv);
            y s$iv = this.c;
            while (true) {
                k.c(s$iv);
                int i = s$iv.d;
                int i2 = s$iv.c;
                if (offset$iv < ((long) (i - i2))) {
                    break;
                }
                offset$iv -= (long) (i - i2);
                s$iv = s$iv.g;
            }
            while (byteCount$iv > 0) {
                k.c(s$iv);
                y copy$iv = s$iv.d();
                int i3 = copy$iv.c + ((int) offset$iv);
                copy$iv.c = i3;
                copy$iv.d = Math.min(i3 + ((int) byteCount$iv), copy$iv.d);
                y yVar = cVar.c;
                if (yVar == null) {
                    copy$iv.h = copy$iv;
                    copy$iv.g = copy$iv;
                    cVar.c = copy$iv;
                } else {
                    k.c(yVar);
                    y yVar2 = yVar.h;
                    k.c(yVar2);
                    yVar2.c(copy$iv);
                }
                byteCount$iv -= (long) (copy$iv.d - copy$iv.c);
                offset$iv = 0;
                s$iv = s$iv.g;
            }
        }
        return this;
    }

    public final long c() {
        long result$iv = e1();
        if (result$iv == 0) {
            return 0;
        }
        y yVar = this.c;
        k.c(yVar);
        y tail$iv = yVar.h;
        k.c(tail$iv);
        int i = tail$iv.d;
        if (i < 8192 && tail$iv.f) {
            result$iv -= (long) (i - tail$iv.c);
        }
        return result$iv;
    }

    public byte readByte() {
        if (e1() != 0) {
            y segment$iv = this.c;
            k.c(segment$iv);
            int pos$iv = segment$iv.c;
            int limit$iv = segment$iv.d;
            int pos$iv2 = pos$iv + 1;
            byte pos$iv3 = segment$iv.b[pos$iv];
            d1(e1() - 1);
            if (pos$iv2 == limit$iv) {
                this.c = segment$iv.b();
                z.b(segment$iv);
            } else {
                segment$iv.c = pos$iv2;
            }
            return pos$iv3;
        }
        throw new EOFException();
    }

    public final byte n(long pos) {
        i0.b(e1(), pos, 1);
        y s$iv$iv = this.c;
        if (s$iv$iv == null) {
            k.c((Object) null);
            return null.b[(int) ((((long) null.c) + pos) - -1)];
        } else if (e1() - pos < pos) {
            long offset$iv$iv = e1();
            while (offset$iv$iv > pos) {
                y yVar = s$iv$iv.h;
                k.c(yVar);
                s$iv$iv = yVar;
                offset$iv$iv -= (long) (s$iv$iv.d - s$iv$iv.c);
            }
            y s$iv = s$iv$iv;
            k.c(s$iv);
            return s$iv.b[(int) ((((long) s$iv.c) + pos) - offset$iv$iv)];
        } else {
            long offset$iv$iv2 = 0;
            while (true) {
                long nextOffset$iv$iv = ((long) (s$iv$iv.d - s$iv$iv.c)) + offset$iv$iv2;
                if (nextOffset$iv$iv > pos) {
                    y s$iv2 = s$iv$iv;
                    k.c(s$iv2);
                    return s$iv2.b[(int) ((((long) s$iv2.c) + pos) - offset$iv$iv2)];
                }
                y s$iv3 = s$iv$iv.g;
                k.c(s$iv3);
                s$iv$iv = s$iv3;
                offset$iv$iv2 = nextOffset$iv$iv;
            }
        }
    }

    public short readShort() {
        if (e1() >= 2) {
            y segment$iv = this.c;
            k.c(segment$iv);
            int pos$iv = segment$iv.c;
            int limit$iv = segment$iv.d;
            if (limit$iv - pos$iv < 2) {
                return (short) (((readByte() & 255) << 8) | (readByte() & 255));
            }
            byte[] data$iv = segment$iv.b;
            int pos$iv2 = pos$iv + 1;
            int pos$iv3 = pos$iv2 + 1;
            int s$iv = ((data$iv[pos$iv] & 255) << 8) | (data$iv[pos$iv2] & 255);
            d1(e1() - 2);
            if (pos$iv3 == limit$iv) {
                this.c = segment$iv.b();
                z.b(segment$iv);
            } else {
                segment$iv.c = pos$iv3;
            }
            return (short) s$iv;
        }
        throw new EOFException();
    }

    public int readInt() {
        if (e1() >= 4) {
            y segment$iv = this.c;
            k.c(segment$iv);
            int pos$iv = segment$iv.c;
            int limit$iv = segment$iv.d;
            if (((long) (limit$iv - pos$iv)) < 4) {
                return ((readByte() & 255) << 24) | ((readByte() & 255) << MappingData.PATH) | ((readByte() & 255) << 8) | (readByte() & 255);
            }
            byte[] data$iv = segment$iv.b;
            int pos$iv2 = pos$iv + 1;
            int pos$iv3 = pos$iv2 + 1;
            int i = ((data$iv[pos$iv] & 255) << 24) | ((data$iv[pos$iv2] & 255) << MappingData.PATH);
            int pos$iv4 = pos$iv3 + 1;
            int i2 = i | ((data$iv[pos$iv3] & 255) << 8);
            int pos$iv5 = pos$iv4 + 1;
            int i$iv = i2 | (data$iv[pos$iv4] & 255);
            d1(e1() - 4);
            if (pos$iv5 == limit$iv) {
                this.c = segment$iv.b();
                z.b(segment$iv);
            } else {
                segment$iv.c = pos$iv5;
            }
            return i$iv;
        }
        throw new EOFException();
    }

    public long readLong() {
        if (e1() >= 8) {
            y segment$iv = this.c;
            k.c(segment$iv);
            int pos$iv = segment$iv.c;
            int limit$iv = segment$iv.d;
            if (((long) (limit$iv - pos$iv)) < 8) {
                return ((((long) readInt()) & 4294967295L) << 32) | (((long) readInt()) & 4294967295L);
            }
            byte[] data$iv = segment$iv.b;
            int pos$iv2 = pos$iv + 1;
            int pos$iv3 = pos$iv2 + 1;
            long j = (((long) data$iv[pos$iv2]) & 255) << 48;
            int pos$iv4 = pos$iv3 + 1;
            int pos$iv5 = pos$iv4 + 1;
            int pos$iv6 = pos$iv5 + 1;
            int pos$iv7 = pos$iv6 + 1;
            long j2 = j | ((255 & ((long) data$iv[pos$iv])) << 56) | ((255 & ((long) data$iv[pos$iv3])) << 40) | ((((long) data$iv[pos$iv4]) & 255) << 32) | ((255 & ((long) data$iv[pos$iv5])) << 24) | ((((long) data$iv[pos$iv6]) & 255) << 16);
            int pos$iv8 = pos$iv7 + 1;
            int pos$iv9 = pos$iv8 + 1;
            long v$iv = j2 | ((255 & ((long) data$iv[pos$iv7])) << 8) | (((long) data$iv[pos$iv8]) & 255);
            d1(e1() - 8);
            if (pos$iv9 == limit$iv) {
                this.c = segment$iv.b();
                z.b(segment$iv);
            } else {
                segment$iv.c = pos$iv9;
            }
            return v$iv;
        }
        throw new EOFException();
    }

    public short I() {
        return i0.e(readShort());
    }

    public int z() {
        return i0.c(readInt());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00cd, code lost:
        if (r11 != r12) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00cf, code lost:
        r1 = r15;
        r1.c = r9.b();
        okio.z.b(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00da, code lost:
        r1 = r15;
        r9.c = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00de, code lost:
        if (r6 != false) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00e2, code lost:
        if (r1.c != null) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ea, code lost:
        r1.d1(r1.e1() - ((long) r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f3, code lost:
        if (r5 == false) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return -r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long s0() {
        /*
            r19 = this;
            r0 = r19
            r1 = 0
            long r2 = r0.e1()
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x00f9
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = -7
        L_0x0014:
            okio.y r9 = r0.c
            kotlin.jvm.internal.k.c(r9)
            byte[] r10 = r9.b
            int r11 = r9.c
            int r12 = r9.d
        L_0x0020:
            if (r11 >= r12) goto L_0x00c6
            byte r13 = r10[r11]
            r14 = 48
            byte r14 = (byte) r14
            if (r13 < r14) goto L_0x0086
            r15 = 57
            byte r15 = (byte) r15
            if (r13 > r15) goto L_0x0086
            int r14 = r14 - r13
            r15 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r17 = (r2 > r15 ? 1 : (r2 == r15 ? 0 : -1))
            if (r17 < 0) goto L_0x0052
            int r15 = (r2 > r15 ? 1 : (r2 == r15 ? 0 : -1))
            if (r15 != 0) goto L_0x0045
            r15 = r0
            r16 = r1
            long r0 = (long) r14
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x0048
            goto L_0x0055
        L_0x0045:
            r15 = r0
            r16 = r1
        L_0x0048:
            r0 = 10
            long r2 = r2 * r0
            long r0 = (long) r14
            long r2 = r2 + r0
            r17 = r6
            r18 = r10
            goto L_0x0098
        L_0x0052:
            r15 = r0
            r16 = r1
        L_0x0055:
            okio.c r0 = new okio.c
            r0.<init>()
            okio.c r0 = r0.writeDecimalLong(r2)
            okio.c r0 = r0.writeByte(r13)
            if (r5 != 0) goto L_0x0067
            r0.readByte()
        L_0x0067:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            r17 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r18 = r10
            java.lang.String r10 = "Number too large: "
            r6.append(r10)
            java.lang.String r10 = r0.a1()
            r6.append(r10)
            java.lang.String r6 = r6.toString()
            r1.<init>(r6)
            throw r1
        L_0x0086:
            r15 = r0
            r16 = r1
            r17 = r6
            r18 = r10
            r0 = 45
            byte r0 = (byte) r0
            if (r13 != r0) goto L_0x00a6
            if (r4 != 0) goto L_0x00a6
            r5 = 1
            r0 = 1
            long r7 = r7 - r0
        L_0x0098:
            int r11 = r11 + 1
            int r4 = r4 + 1
            r0 = r15
            r1 = r16
            r6 = r17
            r10 = r18
            goto L_0x0020
        L_0x00a6:
            if (r4 == 0) goto L_0x00ab
            r0 = 1
            r6 = r0
            goto L_0x00cd
        L_0x00ab:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r6 = "Expected leading [0-9] or '-' character but was 0x"
            r1.append(r6)
            java.lang.String r6 = okio.i0.f(r13)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00c6:
            r15 = r0
            r16 = r1
            r17 = r6
            r18 = r10
        L_0x00cd:
            if (r11 != r12) goto L_0x00da
            okio.y r0 = r9.b()
            r1 = r15
            r1.c = r0
            okio.z.b(r9)
            goto L_0x00dd
        L_0x00da:
            r1 = r15
            r9.c = r11
        L_0x00dd:
            if (r6 != 0) goto L_0x00ea
            okio.y r0 = r1.c
            if (r0 != 0) goto L_0x00e5
            goto L_0x00ea
        L_0x00e5:
            r0 = r1
            r1 = r16
            goto L_0x0014
        L_0x00ea:
            long r9 = r1.e1()
            long r11 = (long) r4
            long r9 = r9 - r11
            r1.d1(r9)
            if (r5 == 0) goto L_0x00f6
            goto L_0x00f8
        L_0x00f6:
            long r9 = -r2
            r2 = r9
        L_0x00f8:
            return r2
        L_0x00f9:
            r16 = r1
            r1 = r0
            java.io.EOFException r0 = new java.io.EOFException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.c.s0():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a4 A[EDGE_INSN: B:45:0x00a4->B:29:0x00a4 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x001f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long X0() {
        /*
            r16 = this;
            r0 = r16
            r1 = 0
            long r2 = r0.e1()
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x00c4
            r2 = 0
            r6 = 0
            r7 = 0
        L_0x0011:
            okio.y r8 = r0.c
            kotlin.jvm.internal.k.c(r8)
            byte[] r9 = r8.b
            int r10 = r8.c
            int r11 = r8.d
        L_0x001d:
            if (r10 >= r11) goto L_0x00a4
            r12 = 0
            byte r13 = r9[r10]
            r14 = 48
            byte r14 = (byte) r14
            if (r13 < r14) goto L_0x002f
            r15 = 57
            byte r15 = (byte) r15
            if (r13 > r15) goto L_0x002f
            int r12 = r13 - r14
            goto L_0x004c
        L_0x002f:
            r14 = 97
            byte r14 = (byte) r14
            if (r13 < r14) goto L_0x003e
            r15 = 102(0x66, float:1.43E-43)
            byte r15 = (byte) r15
            if (r13 > r15) goto L_0x003e
            int r14 = r13 - r14
            int r12 = r14 + 10
            goto L_0x004c
        L_0x003e:
            r14 = 65
            byte r14 = (byte) r14
            if (r13 < r14) goto L_0x0085
            r15 = 70
            byte r15 = (byte) r15
            if (r13 > r15) goto L_0x0085
            int r14 = r13 - r14
            int r12 = r14 + 10
        L_0x004c:
            r14 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r14 = r14 & r2
            int r14 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r14 != 0) goto L_0x005d
            r14 = 4
            long r2 = r2 << r14
            long r14 = (long) r12
            long r2 = r2 | r14
            int r10 = r10 + 1
            int r6 = r6 + 1
            goto L_0x001d
        L_0x005d:
            okio.c r4 = new okio.c
            r4.<init>()
            okio.c r4 = r4.writeHexadecimalUnsignedLong(r2)
            okio.c r4 = r4.writeByte(r13)
            java.lang.NumberFormatException r5 = new java.lang.NumberFormatException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "Number too large: "
            r14.append(r15)
            java.lang.String r15 = r4.a1()
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            r5.<init>(r14)
            throw r5
        L_0x0085:
            if (r6 == 0) goto L_0x0089
            r7 = 1
            goto L_0x00a4
        L_0x0089:
            java.lang.NumberFormatException r4 = new java.lang.NumberFormatException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r14 = "Expected leading [0-9a-fA-F] character but was 0x"
            r5.append(r14)
            java.lang.String r14 = okio.i0.f(r13)
            r5.append(r14)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x00a4:
            if (r10 != r11) goto L_0x00b0
            okio.y r12 = r8.b()
            r0.c = r12
            okio.z.b(r8)
            goto L_0x00b2
        L_0x00b0:
            r8.c = r10
        L_0x00b2:
            if (r7 != 0) goto L_0x00b9
            okio.y r12 = r0.c
            if (r12 != 0) goto L_0x0011
        L_0x00b9:
            long r4 = r0.e1()
            long r8 = (long) r6
            long r4 = r4 - r8
            r0.d1(r4)
            return r2
        L_0x00c4:
            java.io.EOFException r2 = new java.io.EOFException
            r2.<init>()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.c.X0():long");
    }

    @NotNull
    public f D0() {
        return m0(e1());
    }

    @NotNull
    public f m0(long byteCount) {
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        } else if (e1() < byteCount) {
            throw new EOFException();
        } else if (byteCount < ((long) 4096)) {
            return new f(g0(byteCount));
        } else {
            f g1 = g1((int) byteCount);
            f fVar = g1;
            skip(byteCount);
            return g1;
        }
    }

    public int Z0(@NotNull s options) {
        k.e(options, "options");
        int index$iv = okio.internal.b.f(this, options, false, 2, (Object) null);
        if (index$iv == -1) {
            return -1;
        }
        skip((long) options.f()[index$iv].size());
        return index$iv;
    }

    public void L(@NotNull c sink, long byteCount) {
        k.e(sink, "sink");
        if (e1() >= byteCount) {
            sink.write(this, byteCount);
        } else {
            sink.write(this, e1());
            throw new EOFException();
        }
    }

    public long M0(@NotNull b0 sink) {
        k.e(sink, "sink");
        long byteCount$iv = e1();
        if (byteCount$iv > 0) {
            sink.write(this, byteCount$iv);
        }
        return byteCount$iv;
    }

    @NotNull
    public String a1() {
        return u0(this.d, kotlin.text.c.a);
    }

    @NotNull
    public String b1(long byteCount) {
        return u0(byteCount, kotlin.text.c.a);
    }

    @NotNull
    public String x0(@NotNull Charset charset) {
        k.e(charset, "charset");
        return u0(this.d, charset);
    }

    @NotNull
    public String u0(long byteCount, @NotNull Charset charset) {
        k.e(charset, "charset");
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        } else if (this.d < byteCount) {
            throw new EOFException();
        } else if (byteCount == 0) {
            return "";
        } else {
            y s = this.c;
            k.c(s);
            int i = s.c;
            if (((long) i) + byteCount > ((long) s.d)) {
                return new String(g0(byteCount), charset);
            }
            String result = new String(s.b, i, (int) byteCount, charset);
            int i2 = s.c + ((int) byteCount);
            s.c = i2;
            this.d -= byteCount;
            if (i2 == s.d) {
                this.c = s.b();
                z.b(s);
            }
            return result;
        }
    }

    @NotNull
    public String d0() {
        return Q(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    @NotNull
    public String Q(long limit) {
        long j = limit;
        if (j >= 0) {
            long j2 = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            if (j != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                j2 = j + 1;
            }
            long scanLength$iv = j2;
            byte b2 = (byte) 10;
            long newline$iv = M(b2, 0, scanLength$iv);
            if (newline$iv != -1) {
                return okio.internal.b.d(this, newline$iv);
            }
            if (scanLength$iv < e1() && n(scanLength$iv - 1) == ((byte) 13) && n(scanLength$iv) == b2) {
                return okio.internal.b.d(this, scanLength$iv);
            }
            c data$iv = new c();
            long j3 = newline$iv;
            j(data$iv, 0, Math.min((long) 32, e1()));
            throw new EOFException("\\n not found: limit=" + Math.min(e1(), j) + " content=" + data$iv.D0().hex() + 8230);
        }
        throw new IllegalArgumentException(("limit < 0: " + j).toString());
    }

    public int c1() {
        int min$iv;
        int byteCount$iv;
        int codePoint$iv;
        if (e1() != 0) {
            byte b0$iv = n(0);
            if ((128 & b0$iv) == 0) {
                codePoint$iv = b0$iv & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
                byteCount$iv = 1;
                min$iv = 0;
            } else if ((224 & b0$iv) == 192) {
                codePoint$iv = b0$iv & 31;
                byteCount$iv = 2;
                min$iv = 128;
            } else if ((240 & b0$iv) == 224) {
                codePoint$iv = b0$iv & 15;
                byteCount$iv = 3;
                min$iv = 2048;
            } else if ((248 & b0$iv) == 240) {
                codePoint$iv = b0$iv & 7;
                byteCount$iv = 4;
                min$iv = 65536;
            } else {
                skip(1);
                return 65533;
            }
            if (e1() >= ((long) byteCount$iv)) {
                int i$iv = 1;
                while (i$iv < byteCount$iv) {
                    int b$iv = n((long) i$iv);
                    if ((192 & b$iv) == 128) {
                        codePoint$iv = (codePoint$iv << 6) | (63 & b$iv);
                        i$iv++;
                    } else {
                        skip((long) i$iv);
                        return 65533;
                    }
                }
                skip((long) byteCount$iv);
                if (codePoint$iv > 1114111) {
                    return 65533;
                }
                if ((55296 <= codePoint$iv && 57343 >= codePoint$iv) || codePoint$iv < min$iv) {
                    return 65533;
                }
                return codePoint$iv;
            }
            throw new EOFException("size < " + byteCount$iv + ": " + e1() + " (to read code point prefixed 0x" + i0.f(b0$iv) + ')');
        }
        throw new EOFException();
    }

    @NotNull
    public byte[] q0() {
        return g0(e1());
    }

    @NotNull
    public byte[] g0(long byteCount) {
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        } else if (e1() >= byteCount) {
            byte[] result$iv = new byte[((int) byteCount)];
            readFully(result$iv);
            return result$iv;
        } else {
            throw new EOFException();
        }
    }

    public void readFully(@NotNull byte[] sink) {
        k.e(sink, "sink");
        int offset$iv = 0;
        while (offset$iv < sink.length) {
            int read$iv = read(sink, offset$iv, sink.length - offset$iv);
            if (read$iv != -1) {
                offset$iv += read$iv;
            } else {
                throw new EOFException();
            }
        }
    }

    public int read(@NotNull byte[] sink, int offset, int byteCount) {
        k.e(sink, "sink");
        i0.b((long) sink.length, (long) offset, (long) byteCount);
        y s$iv = this.c;
        if (s$iv == null) {
            return -1;
        }
        int toCopy$iv = Math.min(byteCount, s$iv.d - s$iv.c);
        byte[] bArr = s$iv.b;
        int i = s$iv.c;
        kotlin.collections.k.d(bArr, sink, offset, i, i + toCopy$iv);
        s$iv.c += toCopy$iv;
        d1(e1() - ((long) toCopy$iv));
        if (s$iv.c != s$iv.d) {
            return toCopy$iv;
        }
        this.c = s$iv.b();
        z.b(s$iv);
        return toCopy$iv;
    }

    public int read(@NotNull ByteBuffer sink) {
        k.e(sink, "sink");
        y s = this.c;
        if (s == null) {
            return -1;
        }
        int toCopy = Math.min(sink.remaining(), s.d - s.c);
        sink.put(s.b, s.c, toCopy);
        int i = s.c + toCopy;
        s.c = i;
        this.d -= (long) toCopy;
        if (i == s.d) {
            this.c = s.b();
            z.b(s);
        }
        return toCopy;
    }

    public final void clear() {
        skip(e1());
    }

    public void skip(long byteCount) {
        long byteCount$iv = byteCount;
        while (byteCount$iv > 0) {
            y head$iv = this.c;
            if (head$iv != null) {
                int toSkip$iv = (int) Math.min(byteCount$iv, (long) (head$iv.d - head$iv.c));
                d1(e1() - ((long) toSkip$iv));
                byteCount$iv -= (long) toSkip$iv;
                int i = head$iv.c + toSkip$iv;
                head$iv.c = i;
                if (i == head$iv.d) {
                    this.c = head$iv.b();
                    z.b(head$iv);
                }
            } else {
                throw new EOFException();
            }
        }
    }

    @NotNull
    /* renamed from: i1 */
    public c write(@NotNull f byteString) {
        k.e(byteString, "byteString");
        byteString.write$okio(this, 0, byteString.size());
        return this;
    }

    @NotNull
    /* renamed from: x1 */
    public c writeUtf8(@NotNull String string) {
        k.e(string, TypedValues.Custom.S_STRING);
        return writeUtf8(string, 0, string.length());
    }

    @NotNull
    /* renamed from: y1 */
    public c writeUtf8(@NotNull String string, int beginIndex, int endIndex) {
        String str = string;
        int i = beginIndex;
        int i2 = endIndex;
        k.e(str, TypedValues.Custom.S_STRING);
        int i3 = 1;
        if (i >= 0) {
            if (i2 >= i) {
                if (i2 <= string.length()) {
                    int runSize$iv = beginIndex;
                    while (runSize$iv < i2) {
                        int c$iv = str.charAt(runSize$iv);
                        if (c$iv < 128) {
                            y tail$iv = h1(i3);
                            byte[] data$iv = tail$iv.b;
                            int segmentOffset$iv = tail$iv.d - runSize$iv;
                            int runLimit$iv = Math.min(i2, 8192 - segmentOffset$iv);
                            int i$iv = runSize$iv + 1;
                            data$iv[runSize$iv + segmentOffset$iv] = (byte) c$iv;
                            while (i$iv < runLimit$iv) {
                                int c$iv2 = str.charAt(i$iv);
                                if (c$iv2 >= 128) {
                                    break;
                                }
                                data$iv[i$iv + segmentOffset$iv] = (byte) c$iv2;
                                i$iv++;
                            }
                            int i4 = tail$iv.d;
                            int runSize$iv2 = (i$iv + segmentOffset$iv) - i4;
                            tail$iv.d = i4 + runSize$iv2;
                            d1(((long) runSize$iv2) + e1());
                            runSize$iv = i$iv;
                        } else if (c$iv < 2048) {
                            y tail$iv2 = h1(2);
                            byte[] bArr = tail$iv2.b;
                            int i5 = tail$iv2.d;
                            bArr[i5] = (byte) ((c$iv >> 6) | Opcodes.CHECKCAST);
                            bArr[i5 + 1] = (byte) (128 | (c$iv & 63));
                            tail$iv2.d = i5 + 2;
                            d1(e1() + 2);
                            runSize$iv++;
                        } else if (c$iv < 55296 || c$iv > 57343) {
                            y tail$iv3 = h1(3);
                            byte[] bArr2 = tail$iv3.b;
                            int i6 = tail$iv3.d;
                            bArr2[i6] = (byte) ((c$iv >> 12) | 224);
                            bArr2[i6 + 1] = (byte) ((63 & (c$iv >> 6)) | 128);
                            bArr2[i6 + 2] = (byte) (128 | (c$iv & 63));
                            tail$iv3.d = i6 + 3;
                            d1(e1() + 3);
                            runSize$iv++;
                        } else {
                            int low$iv = runSize$iv + 1 < i2 ? str.charAt(runSize$iv + 1) : 0;
                            if (c$iv > 56319 || 56320 > low$iv || 57343 < low$iv) {
                                writeByte(63);
                                runSize$iv++;
                            } else {
                                int codePoint$iv = (((c$iv & 1023) << 10) | (low$iv & 1023)) + 65536;
                                y tail$iv4 = h1(4);
                                byte[] bArr3 = tail$iv4.b;
                                int i7 = tail$iv4.d;
                                bArr3[i7] = (byte) ((codePoint$iv >> 18) | 240);
                                bArr3[i7 + 1] = (byte) (((codePoint$iv >> 12) & 63) | 128);
                                bArr3[i7 + 2] = (byte) ((63 & (codePoint$iv >> 6)) | 128);
                                bArr3[i7 + 3] = (byte) ((codePoint$iv & 63) | 128);
                                tail$iv4.d = i7 + 4;
                                d1(e1() + 4);
                                runSize$iv += 2;
                            }
                        }
                        i3 = 1;
                    }
                    return this;
                }
                throw new IllegalArgumentException(("endIndex > string.length: " + i2 + " > " + string.length()).toString());
            }
            throw new IllegalArgumentException(("endIndex < beginIndex: " + i2 + " < " + i).toString());
        }
        throw new IllegalArgumentException(("beginIndex < 0: " + i).toString());
    }

    @NotNull
    /* renamed from: z1 */
    public c writeUtf8CodePoint(int codePoint) {
        if (codePoint < 128) {
            writeByte(codePoint);
        } else if (codePoint < 2048) {
            y tail$iv = h1(2);
            byte[] bArr = tail$iv.b;
            int i = tail$iv.d;
            bArr[i] = (byte) ((codePoint >> 6) | Opcodes.CHECKCAST);
            bArr[i + 1] = (byte) (128 | (codePoint & 63));
            tail$iv.d = i + 2;
            d1(e1() + 2);
        } else if (55296 <= codePoint && 57343 >= codePoint) {
            writeByte(63);
        } else if (codePoint < 65536) {
            y tail$iv2 = h1(3);
            byte[] bArr2 = tail$iv2.b;
            int i2 = tail$iv2.d;
            bArr2[i2] = (byte) ((codePoint >> 12) | 224);
            bArr2[i2 + 1] = (byte) ((63 & (codePoint >> 6)) | 128);
            bArr2[i2 + 2] = (byte) (128 | (codePoint & 63));
            tail$iv2.d = i2 + 3;
            d1(e1() + 3);
        } else if (codePoint <= 1114111) {
            y tail$iv3 = h1(4);
            byte[] bArr3 = tail$iv3.b;
            int i3 = tail$iv3.d;
            bArr3[i3] = (byte) ((codePoint >> 18) | 240);
            bArr3[i3 + 1] = (byte) (((codePoint >> 12) & 63) | 128);
            bArr3[i3 + 2] = (byte) ((63 & (codePoint >> 6)) | 128);
            bArr3[i3 + 3] = (byte) (128 | (codePoint & 63));
            tail$iv3.d = i3 + 4;
            d1(e1() + 4);
        } else {
            throw new IllegalArgumentException("Unexpected code point: 0x" + i0.g(codePoint));
        }
        return this;
    }

    @NotNull
    /* renamed from: w1 */
    public c writeString(@NotNull String string, @NotNull Charset charset) {
        k.e(string, TypedValues.Custom.S_STRING);
        k.e(charset, "charset");
        return writeString(string, 0, string.length(), charset);
    }

    @NotNull
    /* renamed from: v1 */
    public c writeString(@NotNull String string, int beginIndex, int endIndex, @NotNull Charset charset) {
        k.e(string, TypedValues.Custom.S_STRING);
        k.e(charset, "charset");
        boolean z = true;
        if (beginIndex >= 0) {
            if (endIndex >= beginIndex) {
                if (endIndex > string.length()) {
                    z = false;
                }
                if (!z) {
                    throw new IllegalArgumentException(("endIndex > string.length: " + endIndex + " > " + string.length()).toString());
                } else if (k.a(charset, kotlin.text.c.a)) {
                    return writeUtf8(string, beginIndex, endIndex);
                } else {
                    String substring = string.substring(beginIndex, endIndex);
                    k.d(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    if (substring != null) {
                        byte[] data = substring.getBytes(charset);
                        k.d(data, "(this as java.lang.String).getBytes(charset)");
                        return write(data, 0, data.length);
                    }
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                }
            } else {
                throw new IllegalArgumentException(("endIndex < beginIndex: " + endIndex + " < " + beginIndex).toString());
            }
        } else {
            throw new IllegalArgumentException(("beginIndex < 0: " + beginIndex).toString());
        }
    }

    @NotNull
    /* renamed from: k1 */
    public c write(@NotNull byte[] source) {
        k.e(source, "source");
        return write(source, 0, source.length);
    }

    @NotNull
    /* renamed from: l1 */
    public c write(@NotNull byte[] source, int offset, int byteCount) {
        k.e(source, "source");
        int offset$iv = offset;
        i0.b((long) source.length, (long) offset$iv, (long) byteCount);
        int limit$iv = offset$iv + byteCount;
        while (offset$iv < limit$iv) {
            y tail$iv = h1(1);
            int toCopy$iv = Math.min(limit$iv - offset$iv, 8192 - tail$iv.d);
            kotlin.collections.k.d(source, tail$iv.b, tail$iv.d, offset$iv, offset$iv + toCopy$iv);
            offset$iv += toCopy$iv;
            tail$iv.d += toCopy$iv;
        }
        d1(e1() + ((long) byteCount));
        return this;
    }

    public int write(@NotNull ByteBuffer source) {
        k.e(source, "source");
        int byteCount = source.remaining();
        int remaining = byteCount;
        while (remaining > 0) {
            y tail = h1(1);
            int toCopy = Math.min(remaining, 8192 - tail.d);
            source.get(tail.b, tail.d, toCopy);
            remaining -= toCopy;
            tail.d += toCopy;
        }
        this.d += (long) byteCount;
        return byteCount;
    }

    public long writeAll(@NotNull e0 source) {
        k.e(source, "source");
        long totalBytesRead$iv = 0;
        while (true) {
            long readCount$iv = source.read(this, (long) 8192);
            if (readCount$iv == -1) {
                return totalBytesRead$iv;
            }
            totalBytesRead$iv += readCount$iv;
        }
    }

    @NotNull
    /* renamed from: j1 */
    public c write(@NotNull e0 source, long byteCount) {
        k.e(source, "source");
        long byteCount$iv = byteCount;
        while (byteCount$iv > 0) {
            long read$iv = source.read(this, byteCount$iv);
            if (read$iv != -1) {
                byteCount$iv -= read$iv;
            } else {
                throw new EOFException();
            }
        }
        return this;
    }

    @NotNull
    /* renamed from: m1 */
    public c writeByte(int b2) {
        y tail$iv = h1(1);
        byte[] bArr = tail$iv.b;
        int i = tail$iv.d;
        tail$iv.d = i + 1;
        bArr[i] = (byte) b2;
        d1(e1() + 1);
        return this;
    }

    @NotNull
    /* renamed from: t1 */
    public c writeShort(int s) {
        y tail$iv = h1(2);
        byte[] data$iv = tail$iv.b;
        int limit$iv = tail$iv.d;
        int limit$iv2 = limit$iv + 1;
        data$iv[limit$iv] = (byte) ((s >>> 8) & 255);
        data$iv[limit$iv2] = (byte) (s & 255);
        tail$iv.d = limit$iv2 + 1;
        d1(e1() + 2);
        return this;
    }

    @NotNull
    /* renamed from: u1 */
    public c writeShortLe(int s) {
        return writeShort(i0.e((short) s));
    }

    @NotNull
    /* renamed from: p1 */
    public c writeInt(int i) {
        y tail$iv = h1(4);
        byte[] data$iv = tail$iv.b;
        int limit$iv = tail$iv.d;
        int limit$iv2 = limit$iv + 1;
        data$iv[limit$iv] = (byte) ((i >>> 24) & 255);
        int limit$iv3 = limit$iv2 + 1;
        data$iv[limit$iv2] = (byte) ((i >>> 16) & 255);
        int limit$iv4 = limit$iv3 + 1;
        data$iv[limit$iv3] = (byte) ((i >>> 8) & 255);
        data$iv[limit$iv4] = (byte) (i & 255);
        tail$iv.d = limit$iv4 + 1;
        d1(e1() + 4);
        return this;
    }

    @NotNull
    /* renamed from: q1 */
    public c writeIntLe(int i) {
        return writeInt(i0.c(i));
    }

    @NotNull
    /* renamed from: r1 */
    public c writeLong(long v) {
        y tail$iv = h1(8);
        byte[] data$iv = tail$iv.b;
        int limit$iv = tail$iv.d;
        int limit$iv2 = limit$iv + 1;
        data$iv[limit$iv] = (byte) ((int) ((v >>> 56) & 255));
        int limit$iv3 = limit$iv2 + 1;
        data$iv[limit$iv2] = (byte) ((int) ((v >>> 48) & 255));
        int limit$iv4 = limit$iv3 + 1;
        data$iv[limit$iv3] = (byte) ((int) ((v >>> 40) & 255));
        int limit$iv5 = limit$iv4 + 1;
        data$iv[limit$iv4] = (byte) ((int) ((v >>> 32) & 255));
        int limit$iv6 = limit$iv5 + 1;
        data$iv[limit$iv5] = (byte) ((int) ((v >>> 24) & 255));
        int limit$iv7 = limit$iv6 + 1;
        data$iv[limit$iv6] = (byte) ((int) ((v >>> 16) & 255));
        int limit$iv8 = limit$iv7 + 1;
        data$iv[limit$iv7] = (byte) ((int) ((v >>> 8) & 255));
        data$iv[limit$iv8] = (byte) ((int) (v & 255));
        tail$iv.d = limit$iv8 + 1;
        d1(e1() + 8);
        return this;
    }

    @NotNull
    /* renamed from: s1 */
    public c writeLongLe(long v) {
        return writeLong(i0.d(v));
    }

    @NotNull
    /* renamed from: n1 */
    public c writeDecimalLong(long v) {
        int width$iv;
        long v$iv = v;
        if (v$iv == 0) {
            return writeByte(48);
        }
        boolean negative$iv = false;
        if (v$iv < 0) {
            v$iv = -v$iv;
            if (v$iv < 0) {
                return writeUtf8("-9223372036854775808");
            }
            negative$iv = true;
        }
        if (v$iv < 100000000) {
            width$iv = v$iv < 10000 ? v$iv < 100 ? v$iv < 10 ? 1 : 2 : v$iv < 1000 ? 3 : 4 : v$iv < Time.NANOS_IN_A_MILLISECOND ? v$iv < 100000 ? 5 : 6 : v$iv < Time.HUNDREDS_OF_NANOS_IN_A_SECOND ? 7 : 8;
        } else if (v$iv < 1000000000000L) {
            if (v$iv < 10000000000L) {
                width$iv = v$iv < 1000000000 ? 9 : 10;
            } else if (v$iv < 100000000000L) {
                width$iv = 11;
            } else {
                width$iv = 12;
            }
        } else if (v$iv < 1000000000000000L) {
            if (v$iv < 10000000000000L) {
                width$iv = 13;
            } else if (v$iv < 100000000000000L) {
                width$iv = 14;
            } else {
                width$iv = 15;
            }
        } else if (v$iv < 100000000000000000L) {
            if (v$iv < 10000000000000000L) {
                width$iv = 16;
            } else {
                width$iv = 17;
            }
        } else if (v$iv < 1000000000000000000L) {
            width$iv = 18;
        } else {
            width$iv = 19;
        }
        if (negative$iv) {
            width$iv++;
        }
        y tail$iv = h1(width$iv);
        byte[] data$iv = tail$iv.b;
        int pos$iv = tail$iv.d + width$iv;
        while (v$iv != 0) {
            long j = (long) 10;
            pos$iv--;
            data$iv[pos$iv] = okio.internal.b.b()[(int) (v$iv % j)];
            v$iv /= j;
        }
        if (negative$iv) {
            data$iv[pos$iv - 1] = (byte) 45;
        }
        tail$iv.d += width$iv;
        d1(e1() + ((long) width$iv));
        return this;
    }

    @NotNull
    /* renamed from: o1 */
    public c writeHexadecimalUnsignedLong(long v) {
        long v$iv = v;
        if (v$iv == 0) {
            return writeByte(48);
        }
        long x$iv = v$iv;
        long x$iv2 = x$iv | (x$iv >>> 1);
        long x$iv3 = x$iv2 | (x$iv2 >>> 2);
        long x$iv4 = x$iv3 | (x$iv3 >>> 4);
        long x$iv5 = x$iv4 | (x$iv4 >>> 8);
        long x$iv6 = x$iv5 | (x$iv5 >>> 16);
        long x$iv7 = x$iv6 | (x$iv6 >>> 32);
        long x$iv8 = x$iv7 - ((x$iv7 >>> 1) & 6148914691236517205L);
        long x$iv9 = ((x$iv8 >>> 2) & 3689348814741910323L) + (3689348814741910323L & x$iv8);
        long x$iv10 = ((x$iv9 >>> 4) + x$iv9) & 1085102592571150095L;
        long x$iv11 = x$iv10 + (x$iv10 >>> 8);
        long x$iv12 = x$iv11 + (x$iv11 >>> 16);
        int width$iv = (int) ((((long) 3) + ((x$iv12 & 63) + (63 & (x$iv12 >>> 32)))) / ((long) 4));
        y tail$iv = h1(width$iv);
        byte[] data$iv = tail$iv.b;
        int start$iv = tail$iv.d;
        for (int pos$iv = (tail$iv.d + width$iv) - 1; pos$iv >= start$iv; pos$iv--) {
            data$iv[pos$iv] = okio.internal.b.b()[(int) (15 & v$iv)];
            v$iv >>>= 4;
        }
        tail$iv.d += width$iv;
        d1(e1() + ((long) width$iv));
        return this;
    }

    @NotNull
    public final y h1(int minimumCapacity) {
        boolean z = true;
        if (minimumCapacity < 1 || minimumCapacity > 8192) {
            z = false;
        }
        if (z) {
            y yVar = this.c;
            if (yVar == null) {
                y tail$iv = z.c();
                this.c = tail$iv;
                tail$iv.h = tail$iv;
                tail$iv.g = tail$iv;
                return tail$iv;
            }
            k.c(yVar);
            y tail$iv2 = yVar.h;
            k.c(tail$iv2);
            if (tail$iv2.d + minimumCapacity > 8192 || !tail$iv2.f) {
                return tail$iv2.c(z.c());
            }
            return tail$iv2;
        }
        throw new IllegalArgumentException("unexpected capacity".toString());
    }

    public void write(@NotNull c source, long byteCount) {
        y tail$iv;
        k.e(source, "source");
        long byteCount$iv = byteCount;
        if (source != this) {
            i0.b(source.e1(), 0, byteCount$iv);
            while (byteCount$iv > 0) {
                y yVar = source.c;
                k.c(yVar);
                int i = yVar.d;
                y yVar2 = source.c;
                k.c(yVar2);
                if (byteCount$iv < ((long) (i - yVar2.c))) {
                    y yVar3 = this.c;
                    if (yVar3 != null) {
                        k.c(yVar3);
                        tail$iv = yVar3.h;
                    } else {
                        tail$iv = null;
                    }
                    if (tail$iv != null && tail$iv.f) {
                        if ((((long) tail$iv.d) + byteCount$iv) - ((long) (tail$iv.e ? 0 : tail$iv.c)) <= ((long) 8192)) {
                            y yVar4 = source.c;
                            k.c(yVar4);
                            yVar4.g(tail$iv, (int) byteCount$iv);
                            source.d1(source.e1() - byteCount$iv);
                            d1(e1() + byteCount$iv);
                            return;
                        }
                    }
                    y yVar5 = source.c;
                    k.c(yVar5);
                    source.c = yVar5.e((int) byteCount$iv);
                }
                y segmentToMove$iv = source.c;
                k.c(segmentToMove$iv);
                long movedByteCount$iv = (long) (segmentToMove$iv.d - segmentToMove$iv.c);
                source.c = segmentToMove$iv.b();
                y yVar6 = this.c;
                if (yVar6 == null) {
                    this.c = segmentToMove$iv;
                    segmentToMove$iv.h = segmentToMove$iv;
                    segmentToMove$iv.g = segmentToMove$iv;
                } else {
                    k.c(yVar6);
                    y tail$iv2 = yVar6.h;
                    k.c(tail$iv2);
                    tail$iv2.c(segmentToMove$iv).a();
                }
                source.d1(source.e1() - movedByteCount$iv);
                d1(e1() + movedByteCount$iv);
                byteCount$iv -= movedByteCount$iv;
            }
            return;
        }
        throw new IllegalArgumentException("source == this".toString());
    }

    public long read(@NotNull c sink, long byteCount) {
        k.e(sink, "sink");
        long byteCount$iv = byteCount;
        if (!(byteCount$iv >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount$iv).toString());
        } else if (e1() == 0) {
            return -1;
        } else {
            if (byteCount$iv > e1()) {
                byteCount$iv = e1();
            }
            sink.write(this, byteCount$iv);
            return byteCount$iv;
        }
    }

    public long o(byte b2, long fromIndex) {
        return M(b2, fromIndex, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
    }

    public long M(byte b2, long fromIndex, long toIndex) {
        byte b3 = b2;
        long fromIndex$iv = fromIndex;
        long toIndex$iv = toIndex;
        if (0 <= fromIndex$iv && toIndex$iv >= fromIndex$iv) {
            if (toIndex$iv > e1()) {
                toIndex$iv = e1();
            }
            if (fromIndex$iv == toIndex$iv) {
                return -1;
            }
            long fromIndex$iv$iv = fromIndex$iv;
            c $this$seek$iv$iv = this;
            int $i$f$seek = false;
            y s$iv$iv = $this$seek$iv$iv.c;
            if (s$iv$iv == null) {
                c cVar = $this$seek$iv$iv;
                long j = fromIndex$iv$iv;
                return -1;
            } else if ($this$seek$iv$iv.e1() - fromIndex$iv$iv < fromIndex$iv$iv) {
                long offset$iv$iv = $this$seek$iv$iv.e1();
                while (offset$iv$iv > fromIndex$iv$iv) {
                    y yVar = s$iv$iv.h;
                    k.c(yVar);
                    s$iv$iv = yVar;
                    offset$iv$iv -= (long) (s$iv$iv.d - s$iv$iv.c);
                }
                y s$iv = s$iv$iv;
                int i = false;
                long offset$iv = offset$iv$iv;
                y s$iv2 = s$iv;
                while (offset$iv < toIndex$iv) {
                    c $this$seek$iv$iv2 = $this$seek$iv$iv;
                    byte[] data$iv = s$iv2.b;
                    y s$iv3 = s$iv;
                    int i2 = i;
                    int $i$f$seek2 = $i$f$seek;
                    y s$iv$iv2 = s$iv$iv;
                    int limit$iv = (int) Math.min((long) s$iv2.d, (((long) s$iv2.c) + toIndex$iv) - offset$iv);
                    for (int pos$iv = (int) ((((long) s$iv2.c) + fromIndex$iv) - offset$iv); pos$iv < limit$iv; pos$iv++) {
                        if (data$iv[pos$iv] == b3) {
                            return ((long) (pos$iv - s$iv2.c)) + offset$iv;
                        }
                    }
                    offset$iv += (long) (s$iv2.d - s$iv2.c);
                    fromIndex$iv = offset$iv;
                    y yVar2 = s$iv2.g;
                    k.c(yVar2);
                    s$iv2 = yVar2;
                    $this$seek$iv$iv = $this$seek$iv$iv2;
                    s$iv = s$iv3;
                    i = i2;
                    $i$f$seek = $i$f$seek2;
                    s$iv$iv = s$iv$iv2;
                }
                y yVar3 = s$iv;
                int i3 = i;
                int i4 = $i$f$seek;
                y yVar4 = s$iv$iv;
                return -1;
            } else {
                c cVar2 = $this$seek$iv$iv;
                long offset$iv$iv2 = 0;
                while (true) {
                    long nextOffset$iv$iv = ((long) (s$iv$iv.d - s$iv$iv.c)) + offset$iv$iv2;
                    if (nextOffset$iv$iv > fromIndex$iv$iv) {
                        break;
                    }
                    long j2 = fromIndex$iv$iv;
                    y yVar5 = s$iv$iv.g;
                    k.c(yVar5);
                    s$iv$iv = yVar5;
                    offset$iv$iv2 = nextOffset$iv$iv;
                }
                y s$iv4 = s$iv$iv;
                int i5 = false;
                y s$iv5 = s$iv4;
                long offset$iv2 = offset$iv$iv2;
                while (offset$iv2 < toIndex$iv) {
                    y s$iv6 = s$iv4;
                    byte[] data$iv2 = s$iv5.b;
                    long offset$iv$iv3 = offset$iv$iv2;
                    int i6 = i5;
                    long fromIndex$iv$iv2 = fromIndex$iv$iv;
                    int limit$iv2 = (int) Math.min((long) s$iv5.d, (((long) s$iv5.c) + toIndex$iv) - offset$iv2);
                    for (int pos$iv2 = (int) ((((long) s$iv5.c) + fromIndex$iv) - offset$iv2); pos$iv2 < limit$iv2; pos$iv2++) {
                        if (data$iv2[pos$iv2] == b3) {
                            return ((long) (pos$iv2 - s$iv5.c)) + offset$iv2;
                        }
                    }
                    offset$iv2 += (long) (s$iv5.d - s$iv5.c);
                    fromIndex$iv = offset$iv2;
                    y yVar6 = s$iv5.g;
                    k.c(yVar6);
                    s$iv5 = yVar6;
                    s$iv4 = s$iv6;
                    i5 = i6;
                    offset$iv$iv2 = offset$iv$iv3;
                    fromIndex$iv$iv = fromIndex$iv$iv2;
                }
                long j3 = offset$iv$iv2;
                int i7 = i5;
                long j4 = fromIndex$iv$iv;
                return -1;
            }
        } else {
            throw new IllegalArgumentException(("size=" + e1() + " fromIndex=" + fromIndex$iv + " toIndex=" + toIndex$iv).toString());
        }
    }

    public long F(@NotNull f bytes) {
        k.e(bytes, "bytes");
        return r(bytes, 0);
    }

    public long r(@NotNull f bytes, long fromIndex) {
        byte b0$iv;
        byte[] targetByteArray$iv;
        k.e(bytes, "bytes");
        c $this$commonIndexOf$iv = this;
        long fromIndex$iv = fromIndex;
        if (bytes.size() > 0) {
            if (fromIndex$iv >= 0) {
                long fromIndex$iv$iv = fromIndex$iv;
                c $this$seek$iv$iv = $this$commonIndexOf$iv;
                int $i$f$seek = false;
                y s$iv$iv = $this$seek$iv$iv.c;
                if (s$iv$iv == null) {
                    c cVar = $this$seek$iv$iv;
                    long j = fromIndex$iv$iv;
                    return -1;
                } else if ($this$seek$iv$iv.e1() - fromIndex$iv$iv < fromIndex$iv$iv) {
                    long offset$iv$iv = $this$seek$iv$iv.e1();
                    while (offset$iv$iv > fromIndex$iv$iv) {
                        y yVar = s$iv$iv.h;
                        k.c(yVar);
                        s$iv$iv = yVar;
                        offset$iv$iv -= (long) (s$iv$iv.d - s$iv$iv.c);
                    }
                    y s$iv = s$iv$iv;
                    long offset$iv = offset$iv$iv;
                    long offset$iv2 = offset$iv;
                    byte[] targetByteArray$iv2 = bytes.internalArray$okio();
                    byte b0$iv2 = targetByteArray$iv2[0];
                    int bytesSize$iv = bytes.size();
                    long resultLimit$iv = ($this$commonIndexOf$iv.e1() - ((long) bytesSize$iv)) + 1;
                    y s$iv2 = s$iv;
                    while (offset$iv2 < resultLimit$iv) {
                        byte[] data$iv = s$iv2.b;
                        c $this$seek$iv$iv2 = $this$seek$iv$iv;
                        int $i$f$seek2 = $i$f$seek;
                        y s$iv$iv2 = s$iv$iv;
                        long offset$iv3 = offset$iv;
                        y s$iv3 = s$iv;
                        int a$iv$iv = (int) Math.min((long) s$iv2.d, (((long) s$iv2.c) + resultLimit$iv) - offset$iv2);
                        for (int pos$iv = (int) ((((long) s$iv2.c) + fromIndex$iv) - offset$iv2); pos$iv < a$iv$iv; pos$iv++) {
                            if (data$iv[pos$iv] == b0$iv2 && okio.internal.b.c(s$iv2, pos$iv + 1, targetByteArray$iv2, 1, bytesSize$iv)) {
                                return ((long) (pos$iv - s$iv2.c)) + offset$iv2;
                            }
                        }
                        offset$iv2 += (long) (s$iv2.d - s$iv2.c);
                        fromIndex$iv = offset$iv2;
                        y yVar2 = s$iv2.g;
                        k.c(yVar2);
                        s$iv2 = yVar2;
                        s$iv = s$iv3;
                        $this$seek$iv$iv = $this$seek$iv$iv2;
                        s$iv$iv = s$iv$iv2;
                        $i$f$seek = $i$f$seek2;
                        offset$iv = offset$iv3;
                    }
                    int i = $i$f$seek;
                    y yVar3 = s$iv$iv;
                    long j2 = offset$iv;
                    y yVar4 = s$iv;
                    return -1;
                } else {
                    c cVar2 = $this$seek$iv$iv;
                    long offset$iv$iv2 = 0;
                    while (true) {
                        long nextOffset$iv$iv = ((long) (s$iv$iv.d - s$iv$iv.c)) + offset$iv$iv2;
                        if (nextOffset$iv$iv > fromIndex$iv$iv) {
                            break;
                        }
                        long j3 = offset$iv$iv2;
                        y yVar5 = s$iv$iv.g;
                        k.c(yVar5);
                        s$iv$iv = yVar5;
                        offset$iv$iv2 = nextOffset$iv$iv;
                        $this$commonIndexOf$iv = $this$commonIndexOf$iv;
                        fromIndex$iv$iv = fromIndex$iv$iv;
                    }
                    long offset$iv4 = offset$iv$iv2;
                    y s$iv4 = s$iv$iv;
                    long offset$iv5 = offset$iv4;
                    long j4 = offset$iv$iv2;
                    byte[] targetByteArray$iv3 = bytes.internalArray$okio();
                    byte b0$iv3 = targetByteArray$iv3[0];
                    int bytesSize$iv2 = bytes.size();
                    long j5 = fromIndex$iv$iv;
                    long resultLimit$iv2 = ($this$commonIndexOf$iv.e1() - ((long) bytesSize$iv2)) + 1;
                    while (offset$iv5 < resultLimit$iv2) {
                        byte[] data$iv2 = s$iv4.b;
                        c $this$commonIndexOf$iv2 = $this$commonIndexOf$iv;
                        long offset$iv6 = offset$iv4;
                        byte[] targetByteArray$iv4 = targetByteArray$iv3;
                        int segmentLimit$iv = (int) Math.min((long) s$iv4.d, (((long) s$iv4.c) + resultLimit$iv2) - offset$iv5);
                        int pos$iv2 = (int) ((((long) s$iv4.c) + fromIndex$iv) - offset$iv5);
                        while (pos$iv2 < segmentLimit$iv) {
                            if (data$iv2[pos$iv2] == b0$iv3) {
                                targetByteArray$iv = targetByteArray$iv4;
                                if (okio.internal.b.c(s$iv4, pos$iv2 + 1, targetByteArray$iv, 1, bytesSize$iv2)) {
                                    byte b2 = b0$iv3;
                                    long j6 = fromIndex$iv;
                                    return ((long) (pos$iv2 - s$iv4.c)) + offset$iv5;
                                }
                                b0$iv = b0$iv3;
                            } else {
                                b0$iv = b0$iv3;
                                targetByteArray$iv = targetByteArray$iv4;
                            }
                            pos$iv2++;
                            b0$iv3 = b0$iv;
                            fromIndex$iv = fromIndex$iv;
                            targetByteArray$iv4 = targetByteArray$iv;
                        }
                        byte[] targetByteArray$iv5 = targetByteArray$iv4;
                        long j7 = fromIndex$iv;
                        offset$iv5 += (long) (s$iv4.d - s$iv4.c);
                        fromIndex$iv = offset$iv5;
                        y yVar6 = s$iv4.g;
                        k.c(yVar6);
                        s$iv4 = yVar6;
                        targetByteArray$iv3 = targetByteArray$iv5;
                        $this$commonIndexOf$iv = $this$commonIndexOf$iv2;
                        b0$iv3 = b0$iv3;
                        offset$iv4 = offset$iv6;
                    }
                    byte b3 = b0$iv3;
                    long j8 = fromIndex$iv;
                    long j9 = offset$iv4;
                    byte[] bArr = targetByteArray$iv3;
                    return -1;
                }
            } else {
                c cVar3 = $this$commonIndexOf$iv;
                throw new IllegalArgumentException(("fromIndex < 0: " + fromIndex$iv).toString());
            }
        } else {
            throw new IllegalArgumentException("bytes is empty".toString());
        }
    }

    public long N(@NotNull f targetBytes) {
        k.e(targetBytes, "targetBytes");
        return s(targetBytes, 0);
    }

    public long s(@NotNull f targetBytes, long fromIndex) {
        f fVar = targetBytes;
        k.e(fVar, "targetBytes");
        c $this$commonIndexOfElement$iv = this;
        int $i$f$commonIndexOfElement = 0;
        long fromIndex$iv = fromIndex;
        if (fromIndex$iv >= 0) {
            long fromIndex$iv$iv = fromIndex$iv;
            c $this$seek$iv$iv = $this$commonIndexOfElement$iv;
            int $i$f$seek = false;
            y s$iv$iv = $this$seek$iv$iv.c;
            if (s$iv$iv == null) {
                c cVar = $this$seek$iv$iv;
                return -1;
            } else if ($this$seek$iv$iv.e1() - fromIndex$iv$iv < fromIndex$iv$iv) {
                long offset$iv$iv = $this$seek$iv$iv.e1();
                while (offset$iv$iv > fromIndex$iv$iv) {
                    y yVar = s$iv$iv.h;
                    k.c(yVar);
                    s$iv$iv = yVar;
                    offset$iv$iv -= (long) (s$iv$iv.d - s$iv$iv.c);
                }
                y s$iv = s$iv$iv;
                long offset$iv = offset$iv$iv;
                if (targetBytes.size() == 2) {
                    byte b0$iv = fVar.getByte(0);
                    byte b1$iv = fVar.getByte(1);
                    y s$iv2 = s$iv;
                    while (offset$iv < $this$commonIndexOfElement$iv.e1()) {
                        int $i$f$commonIndexOfElement2 = $i$f$commonIndexOfElement;
                        byte[] data$iv = s$iv2.b;
                        c $this$seek$iv$iv2 = $this$seek$iv$iv;
                        int $i$f$seek2 = $i$f$seek;
                        y s$iv$iv2 = s$iv$iv;
                        int limit$iv = s$iv2.d;
                        for (int pos$iv = (int) ((((long) s$iv2.c) + fromIndex$iv) - offset$iv); pos$iv < limit$iv; pos$iv++) {
                            byte b$iv = data$iv[pos$iv];
                            if (b$iv == b0$iv || b$iv == b1$iv) {
                                byte[] bArr = data$iv;
                                long j = fromIndex$iv;
                                return ((long) (pos$iv - s$iv2.c)) + offset$iv;
                            }
                        }
                        long j2 = fromIndex$iv;
                        offset$iv += (long) (s$iv2.d - s$iv2.c);
                        fromIndex$iv = offset$iv;
                        y yVar2 = s$iv2.g;
                        k.c(yVar2);
                        s$iv2 = yVar2;
                        s$iv$iv = s$iv$iv2;
                        $i$f$commonIndexOfElement = $i$f$commonIndexOfElement2;
                        $this$seek$iv$iv = $this$seek$iv$iv2;
                        $i$f$seek = $i$f$seek2;
                    }
                    long j3 = fromIndex$iv;
                    c cVar2 = $this$seek$iv$iv;
                    int i = $i$f$seek;
                    y yVar3 = s$iv$iv;
                } else {
                    c cVar3 = $this$seek$iv$iv;
                    y yVar4 = s$iv$iv;
                    byte[] targetByteArray$iv = targetBytes.internalArray$okio();
                    y s$iv3 = s$iv;
                    while (offset$iv < $this$commonIndexOfElement$iv.e1()) {
                        byte[] data$iv2 = s$iv3.b;
                        int pos$iv2 = (int) ((((long) s$iv3.c) + fromIndex$iv) - offset$iv);
                        int limit$iv2 = s$iv3.d;
                        while (pos$iv2 < limit$iv2) {
                            byte b$iv2 = data$iv2[pos$iv2];
                            long fromIndex$iv2 = fromIndex$iv;
                            for (byte t$iv : targetByteArray$iv) {
                                if (b$iv2 == t$iv) {
                                    byte[] bArr2 = targetByteArray$iv;
                                    return ((long) (pos$iv2 - s$iv3.c)) + offset$iv;
                                }
                            }
                            pos$iv2++;
                            fromIndex$iv = fromIndex$iv2;
                        }
                        byte[] targetByteArray$iv2 = targetByteArray$iv;
                        long j4 = fromIndex$iv;
                        offset$iv += (long) (s$iv3.d - s$iv3.c);
                        fromIndex$iv = offset$iv;
                        y yVar5 = s$iv3.g;
                        k.c(yVar5);
                        s$iv3 = yVar5;
                        targetByteArray$iv = targetByteArray$iv2;
                    }
                    long j5 = fromIndex$iv;
                    y yVar6 = s$iv3;
                }
                return -1;
            } else {
                c cVar4 = $this$seek$iv$iv;
                long offset$iv$iv2 = 0;
                while (true) {
                    long nextOffset$iv$iv = ((long) (s$iv$iv.d - s$iv$iv.c)) + offset$iv$iv2;
                    if (nextOffset$iv$iv > fromIndex$iv$iv) {
                        break;
                    }
                    c $this$commonIndexOfElement$iv2 = $this$commonIndexOfElement$iv;
                    long j6 = offset$iv$iv2;
                    y yVar7 = s$iv$iv.g;
                    k.c(yVar7);
                    s$iv$iv = yVar7;
                    offset$iv$iv2 = nextOffset$iv$iv;
                    fVar = targetBytes;
                    $this$commonIndexOfElement$iv = $this$commonIndexOfElement$iv2;
                }
                y s$iv4 = s$iv$iv;
                y s$iv5 = s$iv4;
                long offset$iv2 = offset$iv$iv2;
                if (targetBytes.size() == 2) {
                    byte b0$iv2 = fVar.getByte(0);
                    byte b1$iv2 = fVar.getByte(1);
                    while (offset$iv2 < $this$commonIndexOfElement$iv.e1()) {
                        byte[] data$iv3 = s$iv5.b;
                        y s$iv6 = s$iv4;
                        long offset$iv$iv3 = offset$iv$iv2;
                        int pos$iv3 = (int) ((((long) s$iv5.c) + fromIndex$iv) - offset$iv2);
                        int limit$iv3 = s$iv5.d;
                        while (pos$iv3 < limit$iv3) {
                            byte b$iv3 = data$iv3[pos$iv3];
                            if (b$iv3 == b0$iv2 || b$iv3 == b1$iv2) {
                                byte[] bArr3 = data$iv3;
                                long j7 = fromIndex$iv;
                                int i2 = pos$iv3;
                                return ((long) (pos$iv3 - s$iv5.c)) + offset$iv2;
                            }
                            pos$iv3++;
                        }
                        long j8 = fromIndex$iv;
                        int i3 = pos$iv3;
                        offset$iv2 += (long) (s$iv5.d - s$iv5.c);
                        y yVar8 = s$iv5.g;
                        k.c(yVar8);
                        s$iv5 = yVar8;
                        f fVar2 = targetBytes;
                        fromIndex$iv = offset$iv2;
                        s$iv4 = s$iv6;
                        offset$iv$iv2 = offset$iv$iv3;
                    }
                    long j9 = fromIndex$iv;
                    long j10 = offset$iv$iv2;
                    c cVar5 = $this$commonIndexOfElement$iv;
                } else {
                    long j11 = offset$iv$iv2;
                    byte[] targetByteArray$iv3 = targetBytes.internalArray$okio();
                    while (offset$iv2 < $this$commonIndexOfElement$iv.e1()) {
                        byte[] data$iv4 = s$iv5.b;
                        int pos$iv4 = (int) ((((long) s$iv5.c) + fromIndex$iv) - offset$iv2);
                        int limit$iv4 = s$iv5.d;
                        while (pos$iv4 < limit$iv4) {
                            byte b$iv4 = data$iv4[pos$iv4];
                            int length = targetByteArray$iv3.length;
                            c $this$commonIndexOfElement$iv3 = $this$commonIndexOfElement$iv;
                            int i4 = 0;
                            while (i4 < length) {
                                byte[] data$iv5 = data$iv4;
                                if (b$iv4 == targetByteArray$iv3[i4]) {
                                    byte[] bArr4 = targetByteArray$iv3;
                                    return ((long) (pos$iv4 - s$iv5.c)) + offset$iv2;
                                }
                                i4++;
                                data$iv4 = data$iv5;
                            }
                            byte[] bArr5 = data$iv4;
                            pos$iv4++;
                            $this$commonIndexOfElement$iv = $this$commonIndexOfElement$iv3;
                        }
                        byte[] targetByteArray$iv4 = targetByteArray$iv3;
                        byte[] bArr6 = data$iv4;
                        offset$iv2 += (long) (s$iv5.d - s$iv5.c);
                        fromIndex$iv = offset$iv2;
                        y yVar9 = s$iv5.g;
                        k.c(yVar9);
                        s$iv5 = yVar9;
                        $this$commonIndexOfElement$iv = $this$commonIndexOfElement$iv;
                        targetByteArray$iv3 = targetByteArray$iv4;
                    }
                    c cVar6 = $this$commonIndexOfElement$iv;
                }
                return -1;
            }
        } else {
            c cVar7 = $this$commonIndexOfElement$iv;
            throw new IllegalArgumentException(("fromIndex < 0: " + fromIndex$iv).toString());
        }
    }

    public boolean V(long offset, @NotNull f bytes) {
        k.e(bytes, "bytes");
        return t(offset, bytes, 0, bytes.size());
    }

    public boolean t(long offset, @NotNull f bytes, int bytesOffset, int byteCount) {
        k.e(bytes, "bytes");
        if (offset < 0 || bytesOffset < 0 || byteCount < 0 || e1() - offset < ((long) byteCount) || bytes.size() - bytesOffset < byteCount) {
            return false;
        }
        for (int i$iv = 0; i$iv < byteCount; i$iv++) {
            if (n(((long) i$iv) + offset) != bytes.getByte(bytesOffset + i$iv)) {
                return false;
            }
        }
        return true;
    }

    public void flush() {
    }

    public boolean isOpen() {
        return true;
    }

    public void close() {
    }

    @NotNull
    public f0 timeout() {
        return f0.a;
    }

    /* JADX WARNING: type inference failed for: r21v0, types: [java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object r21) {
        /*
            r20 = this;
            r0 = r21
            r1 = r20
            r2 = 0
            r4 = 1
            if (r1 != r0) goto L_0x000b
            r3 = r4
            goto L_0x0096
        L_0x000b:
            boolean r5 = r0 instanceof okio.c
            if (r5 != 0) goto L_0x0012
            r3 = 0
            goto L_0x0096
        L_0x0012:
            long r5 = r1.e1()
            r7 = r0
            okio.c r7 = (okio.c) r7
            long r7 = r7.e1()
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L_0x0024
            r3 = 0
            goto L_0x0096
        L_0x0024:
            long r5 = r1.e1()
            r7 = 0
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 != 0) goto L_0x0031
            r3 = r4
            goto L_0x0096
        L_0x0031:
            okio.y r5 = r1.c
            kotlin.jvm.internal.k.c(r5)
            r6 = r0
            okio.c r6 = (okio.c) r6
            okio.y r6 = r6.c
            kotlin.jvm.internal.k.c(r6)
            int r9 = r5.c
            int r10 = r6.c
            r11 = 0
            r13 = 0
        L_0x0046:
            long r15 = r1.e1()
            int r15 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r15 >= 0) goto L_0x0095
            int r15 = r5.d
            int r15 = r15 - r9
            int r3 = r6.d
            int r3 = r3 - r10
            int r3 = java.lang.Math.min(r15, r3)
            long r13 = (long) r3
            r17 = r7
        L_0x005b:
            int r3 = (r17 > r13 ? 1 : (r17 == r13 ? 0 : -1))
            if (r3 >= 0) goto L_0x0079
            byte[] r3 = r5.b
            int r15 = r9 + 1
            byte r3 = r3[r9]
            byte[] r9 = r6.b
            int r19 = r10 + 1
            byte r9 = r9[r10]
            if (r3 == r9) goto L_0x006f
            r3 = 0
            goto L_0x0096
        L_0x006f:
            r9 = 1
            long r9 = r17 + r9
            r17 = r9
            r9 = r15
            r10 = r19
            goto L_0x005b
        L_0x0079:
            int r3 = r5.d
            if (r9 != r3) goto L_0x0086
            okio.y r3 = r5.g
            kotlin.jvm.internal.k.c(r3)
            int r5 = r3.c
            r9 = r5
            r5 = r3
        L_0x0086:
            int r3 = r6.d
            if (r10 != r3) goto L_0x0093
            okio.y r3 = r6.g
            kotlin.jvm.internal.k.c(r3)
            int r6 = r3.c
            r10 = r6
            r6 = r3
        L_0x0093:
            long r11 = r11 + r13
            goto L_0x0046
        L_0x0095:
            r3 = r4
        L_0x0096:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.c.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        y s$iv = this.c;
        if (s$iv == null) {
            return 0;
        }
        int result$iv = 1;
        do {
            int limit$iv = s$iv.d;
            for (int pos$iv = s$iv.c; pos$iv < limit$iv; pos$iv++) {
                result$iv = (result$iv * 31) + s$iv.b[pos$iv];
            }
            y yVar = s$iv.g;
            k.c(yVar);
            s$iv = yVar;
        } while (s$iv != this.c);
        return result$iv;
    }

    @NotNull
    public String toString() {
        return f1().toString();
    }

    @NotNull
    public final c i() {
        c result$iv = new c();
        if (e1() != 0) {
            y head$iv = this.c;
            k.c(head$iv);
            y headCopy$iv = head$iv.d();
            result$iv.c = headCopy$iv;
            headCopy$iv.h = headCopy$iv;
            headCopy$iv.g = headCopy$iv;
            for (y s$iv = head$iv.g; s$iv != head$iv; s$iv = s$iv.g) {
                y yVar = headCopy$iv.h;
                k.c(yVar);
                k.c(s$iv);
                yVar.c(s$iv.d());
            }
            result$iv.d1(e1());
        }
        return result$iv;
    }

    @NotNull
    /* renamed from: a */
    public c clone() {
        return i();
    }

    @NotNull
    public final f f1() {
        if (e1() <= ((long) Integer.MAX_VALUE)) {
            return g1((int) e1());
        }
        throw new IllegalStateException(("size > Int.MAX_VALUE: " + e1()).toString());
    }

    @NotNull
    public final f g1(int byteCount) {
        if (byteCount == 0) {
            return f.EMPTY;
        }
        i0.b(e1(), 0, (long) byteCount);
        int offset$iv = 0;
        int segmentCount$iv = 0;
        y s$iv = this.c;
        while (offset$iv < byteCount) {
            k.c(s$iv);
            int i = s$iv.d;
            int i2 = s$iv.c;
            if (i != i2) {
                offset$iv += i - i2;
                segmentCount$iv++;
                s$iv = s$iv.g;
            } else {
                throw new AssertionError("s.limit == s.pos");
            }
        }
        byte[][] segments$iv = new byte[segmentCount$iv][];
        int[] directory$iv = new int[(segmentCount$iv * 2)];
        int offset$iv2 = 0;
        int segmentCount$iv2 = 0;
        y s$iv2 = this.c;
        while (offset$iv2 < byteCount) {
            k.c(s$iv2);
            segments$iv[segmentCount$iv2] = s$iv2.b;
            offset$iv2 += s$iv2.d - s$iv2.c;
            directory$iv[segmentCount$iv2] = Math.min(offset$iv2, byteCount);
            directory$iv[segments$iv.length + segmentCount$iv2] = s$iv2.c;
            s$iv2.e = true;
            segmentCount$iv2++;
            s$iv2 = s$iv2.g;
        }
        return new a0(segments$iv, directory$iv);
    }

    @NotNull
    public final a u(@NotNull a unsafeCursor) {
        k.e(unsafeCursor, "unsafeCursor");
        return okio.internal.b.a(this, unsafeCursor);
    }

    /* compiled from: Buffer.kt */
    public static final class a implements Closeable {
        @Nullable
        public c c;
        public boolean d;
        @Nullable
        private y f;
        public long q = -1;
        @Nullable
        public byte[] x;
        public int y = -1;
        public int z = -1;

        @Nullable
        public final y a() {
            return this.f;
        }

        public final void j(@Nullable y yVar) {
            this.f = yVar;
        }

        public final int c() {
            long j = this.q;
            c cVar = this.c;
            k.c(cVar);
            if (j != cVar.e1()) {
                long j2 = this.q;
                return i(j2 == -1 ? 0 : j2 + ((long) (this.z - this.y)));
            }
            throw new IllegalStateException("no more bytes".toString());
        }

        public final int i(long offset) {
            long nextOffset$iv;
            y next$iv;
            long j = offset;
            int $i$f$commonSeek = 0;
            c buffer$iv = this.c;
            if (buffer$iv == null) {
                throw new IllegalStateException("not attached to a buffer".toString());
            } else if (j < ((long) -1) || j > buffer$iv.e1()) {
                throw new ArrayIndexOutOfBoundsException("offset=" + j + " > size=" + buffer$iv.e1());
            } else {
                if (j != -1) {
                    if (j != buffer$iv.e1()) {
                        long min$iv = 0;
                        long max$iv = buffer$iv.e1();
                        y head$iv = buffer$iv.c;
                        y tail$iv = buffer$iv.c;
                        if (a() != null) {
                            long j2 = this.q;
                            int i = this.y;
                            y a = a();
                            k.c(a);
                            long segmentOffset$iv = j2 - ((long) (i - a.c));
                            if (segmentOffset$iv > j) {
                                max$iv = segmentOffset$iv;
                                tail$iv = a();
                            } else {
                                min$iv = segmentOffset$iv;
                                head$iv = a();
                            }
                        }
                        if (max$iv - j > j - min$iv) {
                            next$iv = head$iv;
                            nextOffset$iv = min$iv;
                            while (true) {
                                k.c(next$iv);
                                int i2 = next$iv.d;
                                int i3 = next$iv.c;
                                int $i$f$commonSeek2 = $i$f$commonSeek;
                                long min$iv2 = min$iv;
                                if (j < ((long) (i2 - i3)) + nextOffset$iv) {
                                    break;
                                }
                                nextOffset$iv += (long) (i2 - i3);
                                next$iv = next$iv.g;
                                $i$f$commonSeek = $i$f$commonSeek2;
                                min$iv = min$iv2;
                            }
                        } else {
                            long j3 = min$iv;
                            y next$iv2 = tail$iv;
                            long nextOffset$iv2 = max$iv;
                            while (nextOffset$iv > j) {
                                k.c(next$iv);
                                next$iv2 = next$iv.h;
                                k.c(next$iv2);
                                nextOffset$iv2 = nextOffset$iv - ((long) (next$iv2.d - next$iv2.c));
                            }
                        }
                        if (this.d) {
                            k.c(next$iv);
                            if (next$iv.e) {
                                y unsharedNext$iv = next$iv.f();
                                if (buffer$iv.c == next$iv) {
                                    buffer$iv.c = unsharedNext$iv;
                                }
                                next$iv = next$iv.c(unsharedNext$iv);
                                y yVar = next$iv.h;
                                k.c(yVar);
                                yVar.b();
                            }
                        }
                        j(next$iv);
                        this.q = j;
                        k.c(next$iv);
                        this.x = next$iv.b;
                        int i4 = next$iv.c + ((int) (j - nextOffset$iv));
                        this.y = i4;
                        int i5 = next$iv.d;
                        this.z = i5;
                        return i5 - i4;
                    }
                }
                j((y) null);
                this.q = j;
                this.x = null;
                this.y = -1;
                this.z = -1;
                return -1;
            }
        }

        public final long g(long newSize) {
            long j = newSize;
            c buffer$iv = this.c;
            if (buffer$iv == null) {
                throw new IllegalStateException("not attached to a buffer".toString());
            } else if (this.d) {
                long oldSize$iv = buffer$iv.e1();
                int segmentBytesToAdd$iv = 1;
                if (j <= oldSize$iv) {
                    if (j < 0) {
                        segmentBytesToAdd$iv = 0;
                    }
                    if (segmentBytesToAdd$iv != 0) {
                        long bytesToSubtract$iv = oldSize$iv - j;
                        while (true) {
                            if (bytesToSubtract$iv <= 0) {
                                break;
                            }
                            y yVar = buffer$iv.c;
                            k.c(yVar);
                            y tail$iv = yVar.h;
                            k.c(tail$iv);
                            int i = tail$iv.d;
                            int tailSize$iv = i - tail$iv.c;
                            if (((long) tailSize$iv) > bytesToSubtract$iv) {
                                tail$iv.d = i - ((int) bytesToSubtract$iv);
                                break;
                            }
                            buffer$iv.c = tail$iv.b();
                            z.b(tail$iv);
                            bytesToSubtract$iv -= (long) tailSize$iv;
                        }
                        j((y) null);
                        this.q = j;
                        this.x = null;
                        this.y = -1;
                        this.z = -1;
                    } else {
                        throw new IllegalArgumentException(("newSize < 0: " + j).toString());
                    }
                } else if (j > oldSize$iv) {
                    boolean needsToSeek$iv = true;
                    long bytesToAdd$iv = j - oldSize$iv;
                    for (long j2 = 0; bytesToAdd$iv > j2; j2 = 0) {
                        y tail$iv2 = buffer$iv.h1(segmentBytesToAdd$iv);
                        int segmentBytesToAdd$iv2 = (int) Math.min(bytesToAdd$iv, (long) (8192 - tail$iv2.d));
                        tail$iv2.d += segmentBytesToAdd$iv2;
                        bytesToAdd$iv -= (long) segmentBytesToAdd$iv2;
                        if (needsToSeek$iv) {
                            j(tail$iv2);
                            this.q = oldSize$iv;
                            this.x = tail$iv2.b;
                            int i2 = tail$iv2.d;
                            this.y = i2 - segmentBytesToAdd$iv2;
                            this.z = i2;
                            needsToSeek$iv = false;
                        }
                        segmentBytesToAdd$iv = 1;
                    }
                }
                buffer$iv.d1(j);
                return oldSize$iv;
            } else {
                throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers".toString());
            }
        }

        public void close() {
            if (this.c != null) {
                this.c = null;
                j((y) null);
                this.q = -1;
                this.x = null;
                this.y = -1;
                this.z = -1;
                return;
            }
            throw new IllegalStateException("not attached to a buffer".toString());
        }
    }
}
