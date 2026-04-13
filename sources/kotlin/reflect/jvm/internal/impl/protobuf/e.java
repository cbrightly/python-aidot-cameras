package kotlin.reflect.jvm.internal.impl.protobuf;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: CodedInputStream */
public final class e {
    private final byte[] a;
    private final boolean b;
    private int c;
    private int d;
    private int e;
    private final InputStream f;
    private int g;
    private boolean h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private a n;

    /* compiled from: CodedInputStream */
    public interface a {
        void a();
    }

    public static e g(InputStream input) {
        return new e(input);
    }

    static e h(n byteString) {
        e result = new e(byteString);
        try {
            result.j(byteString.size());
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public int K() {
        if (f()) {
            this.g = 0;
            return 0;
        }
        int A = A();
        this.g = A;
        if (w.a(A) != 0) {
            return this.g;
        }
        throw InvalidProtocolBufferException.invalidTag();
    }

    public void a(int value) {
        if (this.g != value) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    public boolean P(int tag, CodedOutputStream output) {
        switch (w.b(tag)) {
            case 0:
                long value = t();
                output.o0(tag);
                output.z0(value);
                return true;
            case 1:
                long value2 = z();
                output.o0(tag);
                output.V(value2);
                return true;
            case 2:
                d value3 = l();
                output.o0(tag);
                output.P(value3);
                return true;
            case 3:
                output.o0(tag);
                Q(output);
                int endtag = w.c(w.a(tag), 4);
                a(endtag);
                output.o0(endtag);
                return true;
            case 4:
                return false;
            case 5:
                int value4 = y();
                output.o0(tag);
                output.U(value4);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void Q(kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream r3) {
        /*
            r2 = this;
        L_0x0000:
            int r0 = r2.K()
            if (r0 == 0) goto L_0x000e
            boolean r1 = r2.P(r0, r3)
            if (r1 != 0) goto L_0x000d
            goto L_0x000e
        L_0x000d:
            goto L_0x0000
        L_0x000e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.protobuf.e.Q(kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream):void");
    }

    public double m() {
        return Double.longBitsToDouble(z());
    }

    public float q() {
        return Float.intBitsToFloat(y());
    }

    public long M() {
        return C();
    }

    public long t() {
        return C();
    }

    public int s() {
        return A();
    }

    public long p() {
        return z();
    }

    public int o() {
        return y();
    }

    public boolean k() {
        return C() != 0;
    }

    public String I() {
        int size = A();
        if (size <= this.c - this.e && size > 0) {
            String result = new String(this.a, this.e, size, "UTF-8");
            this.e += size;
            return result;
        } else if (size == 0) {
            return "";
        } else {
            return new String(x(size), "UTF-8");
        }
    }

    public String J() {
        byte[] bytes;
        int size = A();
        int pos = this.e;
        if (size <= this.c - pos && size > 0) {
            bytes = this.a;
            this.e = pos + size;
        } else if (size == 0) {
            return "";
        } else {
            bytes = x(size);
            pos = 0;
        }
        if (v.f(bytes, pos, pos + size)) {
            return new String(bytes, pos, size, "UTF-8");
        }
        throw InvalidProtocolBufferException.invalidUtf8();
    }

    public void r(int fieldNumber, o.a builder, f extensionRegistry) {
        int i2 = this.k;
        if (i2 < this.l) {
            this.k = i2 + 1;
            builder.J(this, extensionRegistry);
            a(w.c(fieldNumber, 4));
            this.k--;
            return;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public void v(o.a builder, f extensionRegistry) {
        int length = A();
        if (this.k < this.l) {
            int oldLimit = j(length);
            this.k++;
            builder.J(this, extensionRegistry);
            a(0);
            this.k--;
            i(oldLimit);
            return;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public <T extends o> T u(q<T> parser, f extensionRegistry) {
        int length = A();
        if (this.k < this.l) {
            int oldLimit = j(length);
            this.k++;
            T result = (o) parser.c(this, extensionRegistry);
            a(0);
            this.k--;
            i(oldLimit);
            return result;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    public d l() {
        int size = A();
        int i2 = this.c;
        int i3 = this.e;
        if (size <= i2 - i3 && size > 0) {
            d result = (!this.b || !this.h) ? d.f(this.a, i3, size) : new c(this.a, this.e, size);
            this.e += size;
            return result;
        } else if (size == 0) {
            return d.c;
        } else {
            return new n(x(size));
        }
    }

    public int L() {
        return A();
    }

    public int n() {
        return A();
    }

    public int E() {
        return y();
    }

    public long F() {
        return z();
    }

    public int G() {
        return b(A());
    }

    public long H() {
        return c(C());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0086, code lost:
        if (r2[r1] < 0) goto L_0x0089;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int A() {
        /*
            r9 = this;
            int r0 = r9.e
            int r1 = r9.c
            if (r1 != r0) goto L_0x0008
            goto L_0x0089
        L_0x0008:
            byte[] r2 = r9.a
            int r3 = r0 + 1
            byte r0 = r2[r0]
            r4 = r0
            if (r0 < 0) goto L_0x0014
            r9.e = r3
            return r4
        L_0x0014:
            int r1 = r1 - r3
            r0 = 9
            if (r1 >= r0) goto L_0x001b
            goto L_0x0089
        L_0x001b:
            int r0 = r3 + 1
            byte r1 = r2[r3]
            int r1 = r1 << 7
            r1 = r1 ^ r4
            r3 = r1
            long r4 = (long) r1
            r6 = 0
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 >= 0) goto L_0x0031
            long r4 = (long) r3
            r6 = -128(0xffffffffffffff80, double:NaN)
            long r4 = r4 ^ r6
            int r1 = (int) r4
            goto L_0x0094
        L_0x0031:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            int r0 = r0 << 14
            r0 = r0 ^ r3
            r3 = r0
            long r4 = (long) r0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 < 0) goto L_0x0047
            long r4 = (long) r3
            r6 = 16256(0x3f80, double:8.0315E-320)
            long r4 = r4 ^ r6
            int r0 = (int) r4
            r8 = r1
            r1 = r0
            r0 = r8
            goto L_0x0094
        L_0x0047:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 21
            r1 = r1 ^ r3
            r3 = r1
            long r4 = (long) r1
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 >= 0) goto L_0x005b
            long r4 = (long) r3
            r6 = -2080896(0xffffffffffe03f80, double:NaN)
            long r4 = r4 ^ r6
            int r1 = (int) r4
            goto L_0x0094
        L_0x005b:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            int r4 = r0 << 28
            r3 = r3 ^ r4
            long r4 = (long) r3
            r6 = 266354560(0xfe03f80, double:1.315966377E-315)
            long r4 = r4 ^ r6
            int r3 = (int) r4
            if (r0 >= 0) goto L_0x0092
            int r4 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x008f
            int r1 = r4 + 1
            byte r4 = r2[r4]
            if (r4 >= 0) goto L_0x0092
            int r4 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x008f
            int r1 = r4 + 1
            byte r4 = r2[r4]
            if (r4 >= 0) goto L_0x0092
            int r4 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x008f
        L_0x0089:
            long r0 = r9.D()
            int r0 = (int) r0
            return r0
        L_0x008f:
            r1 = r3
            r0 = r4
            goto L_0x0094
        L_0x0092:
            r0 = r1
            r1 = r3
        L_0x0094:
            r9.e = r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.protobuf.e.A():int");
    }

    public static int B(int firstByte, InputStream input) {
        if ((firstByte & 128) == 0) {
            return firstByte;
        }
        int result = firstByte & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        int offset = 7;
        while (offset < 32) {
            int b2 = input.read();
            if (b2 != -1) {
                result |= (b2 & NeedPermissionEvent.PER_IPC_SPEAK_PERM) << offset;
                if ((b2 & 128) == 0) {
                    return result;
                }
                offset += 7;
            } else {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }
        while (offset < 64) {
            int b3 = input.read();
            if (b3 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if ((b3 & 128) == 0) {
                return result;
            } else {
                offset += 7;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c4, code lost:
        if (((long) r2[r1]) < 0) goto L_0x00c7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long C() {
        /*
            r11 = this;
            int r0 = r11.e
            int r1 = r11.c
            if (r1 != r0) goto L_0x0008
            goto L_0x00c7
        L_0x0008:
            byte[] r2 = r11.a
            int r3 = r0 + 1
            byte r0 = r2[r0]
            r4 = r0
            if (r0 < 0) goto L_0x0015
            r11.e = r3
            long r0 = (long) r4
            return r0
        L_0x0015:
            int r1 = r1 - r3
            r0 = 9
            if (r1 >= r0) goto L_0x001c
            goto L_0x00c7
        L_0x001c:
            int r0 = r3 + 1
            byte r1 = r2[r3]
            int r1 = r1 << 7
            r1 = r1 ^ r4
            long r5 = (long) r1
            r7 = r5
            r9 = 0
            int r1 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r1 >= 0) goto L_0x0030
            r5 = -128(0xffffffffffffff80, double:NaN)
            long r5 = r5 ^ r7
            goto L_0x00cd
        L_0x0030:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            int r0 = r0 << 14
            long r5 = (long) r0
            long r5 = r5 ^ r7
            r7 = r5
            int r0 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r0 < 0) goto L_0x0043
            r5 = 16256(0x3f80, double:8.0315E-320)
            long r5 = r5 ^ r7
            r0 = r1
            goto L_0x00cd
        L_0x0043:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 21
            long r5 = (long) r1
            long r5 = r5 ^ r7
            r7 = r5
            int r1 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r1 >= 0) goto L_0x0056
            r5 = -2080896(0xffffffffffe03f80, double:NaN)
            long r5 = r5 ^ r7
            goto L_0x00cd
        L_0x0056:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r5 = (long) r0
            r0 = 28
            long r5 = r5 << r0
            long r5 = r5 ^ r7
            r7 = r5
            int r0 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r0 < 0) goto L_0x006a
            r5 = 266354560(0xfe03f80, double:1.315966377E-315)
            long r5 = r5 ^ r7
            r0 = r1
            goto L_0x00cd
        L_0x006a:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r5 = (long) r1
            r1 = 35
            long r5 = r5 << r1
            long r5 = r5 ^ r7
            r7 = r5
            int r1 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r1 >= 0) goto L_0x007f
            r5 = -34093383808(0xfffffff80fe03f80, double:NaN)
            long r5 = r5 ^ r7
            goto L_0x00cd
        L_0x007f:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r5 = (long) r0
            r0 = 42
            long r5 = r5 << r0
            long r5 = r5 ^ r7
            r7 = r5
            int r0 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r0 < 0) goto L_0x0095
            r5 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
            long r5 = r5 ^ r7
            r0 = r1
            goto L_0x00cd
        L_0x0095:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r5 = (long) r1
            r1 = 49
            long r5 = r5 << r1
            long r5 = r5 ^ r7
            r7 = r5
            int r1 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r1 >= 0) goto L_0x00aa
            r5 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
            long r5 = r5 ^ r7
            goto L_0x00cd
        L_0x00aa:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r5 = (long) r0
            r0 = 56
            long r5 = r5 << r0
            long r5 = r5 ^ r7
            r7 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
            long r5 = r5 ^ r7
            int r0 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r0 >= 0) goto L_0x00cc
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r7 = (long) r1
            int r1 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r1 >= 0) goto L_0x00cd
        L_0x00c7:
            long r0 = r11.D()
            return r0
        L_0x00cc:
            r0 = r1
        L_0x00cd:
            r11.e = r0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.protobuf.e.C():long");
    }

    /* access modifiers changed from: package-private */
    public long D() {
        long result = 0;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b2 = w();
            result |= ((long) (b2 & Byte.MAX_VALUE)) << shift;
            if ((b2 & OTACommand.RESP_ID_VERSION) == 0) {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int y() {
        int pos = this.e;
        if (this.c - pos < 4) {
            O(4);
            pos = this.e;
        }
        byte[] buffer = this.a;
        this.e = pos + 4;
        return (buffer[pos] & 255) | ((buffer[pos + 1] & 255) << 8) | ((buffer[pos + 2] & 255) << MappingData.PATH) | ((buffer[pos + 3] & 255) << 24);
    }

    public long z() {
        int pos = this.e;
        if (this.c - pos < 8) {
            O(8);
            pos = this.e;
        }
        byte[] buffer = this.a;
        this.e = pos + 8;
        return (((long) buffer[pos]) & 255) | ((((long) buffer[pos + 1]) & 255) << 8) | ((((long) buffer[pos + 2]) & 255) << 16) | ((((long) buffer[pos + 3]) & 255) << 24) | ((((long) buffer[pos + 4]) & 255) << 32) | ((((long) buffer[pos + 5]) & 255) << 40) | ((((long) buffer[pos + 6]) & 255) << 48) | ((((long) buffer[pos + 7]) & 255) << 56);
    }

    public static int b(int n2) {
        return (n2 >>> 1) ^ (-(n2 & 1));
    }

    public static long c(long n2) {
        return (n2 >>> 1) ^ (-(1 & n2));
    }

    private e(InputStream input) {
        this.h = false;
        this.j = Integer.MAX_VALUE;
        this.l = 64;
        this.m = 67108864;
        this.n = null;
        this.a = new byte[4096];
        this.c = 0;
        this.e = 0;
        this.i = 0;
        this.f = input;
        this.b = false;
    }

    private e(n byteString) {
        this.h = false;
        this.j = Integer.MAX_VALUE;
        this.l = 64;
        this.m = 67108864;
        this.n = null;
        this.a = byteString.d;
        int C = byteString.C();
        this.e = C;
        this.c = C + byteString.size();
        this.i = -this.e;
        this.f = null;
        this.b = true;
    }

    public int j(int byteLimit) {
        if (byteLimit >= 0) {
            int byteLimit2 = byteLimit + this.i + this.e;
            int oldLimit = this.j;
            if (byteLimit2 <= oldLimit) {
                this.j = byteLimit2;
                N();
                return oldLimit;
            }
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    private void N() {
        int i2 = this.c + this.d;
        this.c = i2;
        int bufferEnd = this.i + i2;
        int i3 = this.j;
        if (bufferEnd > i3) {
            int i4 = bufferEnd - i3;
            this.d = i4;
            this.c = i2 - i4;
            return;
        }
        this.d = 0;
    }

    public void i(int oldLimit) {
        this.j = oldLimit;
        N();
    }

    public int e() {
        int i2 = this.j;
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        return i2 - (this.i + this.e);
    }

    public boolean f() {
        return this.e == this.c && !T(1);
    }

    private void d(int n2) {
        if (this.c - this.e < n2) {
            O(n2);
        }
    }

    private void O(int n2) {
        if (!T(n2)) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    private boolean T(int n2) {
        int i2 = this.e;
        if (i2 + n2 <= this.c) {
            StringBuilder sb = new StringBuilder(77);
            sb.append("refillBuffer() called when ");
            sb.append(n2);
            sb.append(" bytes were already available in buffer");
            throw new IllegalStateException(sb.toString());
        } else if (this.i + i2 + n2 > this.j) {
            return false;
        } else {
            a aVar = this.n;
            if (aVar != null) {
                aVar.a();
            }
            if (this.f != null) {
                int pos = this.e;
                if (pos > 0) {
                    int i3 = this.c;
                    if (i3 > pos) {
                        byte[] bArr = this.a;
                        System.arraycopy(bArr, pos, bArr, 0, i3 - pos);
                    }
                    this.i += pos;
                    this.c -= pos;
                    this.e = 0;
                }
                InputStream inputStream = this.f;
                byte[] bArr2 = this.a;
                int i4 = this.c;
                int bytesRead = inputStream.read(bArr2, i4, bArr2.length - i4);
                if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.a.length) {
                    StringBuilder sb2 = new StringBuilder(102);
                    sb2.append("InputStream#read(byte[]) returned invalid result: ");
                    sb2.append(bytesRead);
                    sb2.append("\nThe InputStream implementation is buggy.");
                    throw new IllegalStateException(sb2.toString());
                } else if (bytesRead > 0) {
                    this.c += bytesRead;
                    if ((this.i + n2) - this.m <= 0) {
                        N();
                        if (this.c >= n2) {
                            return true;
                        }
                        return T(n2);
                    }
                    throw InvalidProtocolBufferException.sizeLimitExceeded();
                }
            }
            return false;
        }
    }

    public byte w() {
        if (this.e == this.c) {
            O(1);
        }
        byte[] bArr = this.a;
        int i2 = this.e;
        this.e = i2 + 1;
        return bArr[i2];
    }

    private byte[] x(int size) {
        if (size > 0) {
            int sizeLeft = this.i;
            int i2 = this.e;
            int i3 = sizeLeft + i2 + size;
            int i4 = this.j;
            if (i3 > i4) {
                R((i4 - sizeLeft) - i2);
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if (size < 4096) {
                byte[] bytes = new byte[size];
                int pos = this.c - i2;
                System.arraycopy(this.a, i2, bytes, 0, pos);
                this.e = this.c;
                d(size - pos);
                System.arraycopy(this.a, 0, bytes, pos, size - pos);
                this.e = size - pos;
                return bytes;
            } else {
                int originalBufferPos = this.e;
                int originalBufferSize = this.c;
                this.i = sizeLeft + this.c;
                this.e = 0;
                this.c = 0;
                int sizeLeft2 = size - (originalBufferSize - originalBufferPos);
                List<byte[]> chunks = new ArrayList<>();
                while (sizeLeft2 > 0) {
                    byte[] chunk = new byte[Math.min(sizeLeft2, 4096)];
                    int pos2 = 0;
                    while (pos2 < chunk.length) {
                        InputStream inputStream = this.f;
                        int n2 = inputStream == null ? -1 : inputStream.read(chunk, pos2, chunk.length - pos2);
                        if (n2 != -1) {
                            this.i += n2;
                            pos2 += n2;
                        } else {
                            throw InvalidProtocolBufferException.truncatedMessage();
                        }
                    }
                    sizeLeft2 -= chunk.length;
                    chunks.add(chunk);
                }
                byte[] bytes2 = new byte[size];
                int pos3 = originalBufferSize - originalBufferPos;
                System.arraycopy(this.a, originalBufferPos, bytes2, 0, pos3);
                for (byte[] chunk2 : chunks) {
                    System.arraycopy(chunk2, 0, bytes2, pos3, chunk2.length);
                    pos3 += chunk2.length;
                }
                return bytes2;
            }
        } else if (size == 0) {
            return i.a;
        } else {
            throw InvalidProtocolBufferException.negativeSize();
        }
    }

    public void R(int size) {
        int i2 = this.c;
        int i3 = this.e;
        if (size > i2 - i3 || size < 0) {
            S(size);
        } else {
            this.e = i3 + size;
        }
    }

    private void S(int size) {
        if (size >= 0) {
            int i2 = this.i;
            int i3 = this.e;
            int i4 = i2 + i3 + size;
            int i5 = this.j;
            if (i4 <= i5) {
                int i6 = this.c;
                int pos = i6 - i3;
                this.e = i6;
                O(1);
                while (true) {
                    int i7 = size - pos;
                    int i8 = this.c;
                    if (i7 > i8) {
                        pos += i8;
                        this.e = i8;
                        O(1);
                    } else {
                        this.e = size - pos;
                        return;
                    }
                }
            } else {
                R((i5 - i2) - i3);
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        } else {
            throw InvalidProtocolBufferException.negativeSize();
        }
    }
}
