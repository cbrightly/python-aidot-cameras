package kotlin.reflect.jvm.internal.impl.protobuf;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class CodedOutputStream {
    private final byte[] a;
    private final int b;
    private int c;
    private int d = 0;
    private final OutputStream e;

    static int u(int dataLength) {
        if (dataLength > 4096) {
            return 4096;
        }
        return dataLength;
    }

    private CodedOutputStream(OutputStream output, byte[] buffer) {
        this.e = output;
        this.a = buffer;
        this.c = 0;
        this.b = buffer.length;
    }

    public static CodedOutputStream J(OutputStream output, int bufferSize) {
        return new CodedOutputStream(output, new byte[bufferSize]);
    }

    public void Q(int fieldNumber, double value) {
        w0(fieldNumber, 1);
        R(value);
    }

    public void W(int fieldNumber, float value) {
        w0(fieldNumber, 5);
        X(value);
    }

    public void a0(int fieldNumber, int value) {
        w0(fieldNumber, 0);
        b0(value);
    }

    public void L(int fieldNumber, boolean value) {
        w0(fieldNumber, 0);
        M(value);
    }

    public void Y(int fieldNumber, o value) {
        w0(fieldNumber, 3);
        Z(value);
        w0(fieldNumber, 4);
    }

    public void d0(int fieldNumber, o value) {
        w0(fieldNumber, 2);
        e0(value);
    }

    public void O(int fieldNumber, d value) {
        w0(fieldNumber, 2);
        P(value);
    }

    public void x0(int fieldNumber, int value) {
        w0(fieldNumber, 0);
        y0(value);
    }

    public void S(int fieldNumber, int value) {
        w0(fieldNumber, 0);
        T(value);
    }

    public void t0(int fieldNumber, long value) {
        w0(fieldNumber, 0);
        u0(value);
    }

    public void f0(int fieldNumber, o value) {
        w0(1, 3);
        x0(2, fieldNumber);
        d0(3, value);
        w0(1, 4);
    }

    public void R(double value) {
        n0(Double.doubleToRawLongBits(value));
    }

    public void X(float value) {
        m0(Float.floatToRawIntBits(value));
    }

    public void z0(long value) {
        p0(value);
    }

    public void c0(long value) {
        p0(value);
    }

    public void b0(int value) {
        if (value >= 0) {
            o0(value);
        } else {
            p0((long) value);
        }
    }

    public void V(long value) {
        n0(value);
    }

    public void U(int value) {
        m0(value);
    }

    public void M(boolean value) {
        h0(value);
    }

    public void v0(String value) {
        byte[] bytes = value.getBytes("UTF-8");
        o0(bytes.length);
        k0(bytes);
    }

    public void Z(o value) {
        value.writeTo(this);
    }

    public void e0(o value) {
        o0(value.getSerializedSize());
        value.writeTo(this);
    }

    public void P(d value) {
        o0(value.size());
        i0(value);
    }

    public void N(byte[] value) {
        o0(value.length);
        k0(value);
    }

    public void y0(int value) {
        o0(value);
    }

    public void T(int value) {
        b0(value);
    }

    public void q0(int value) {
        m0(value);
    }

    public void r0(long value) {
        n0(value);
    }

    public void s0(int value) {
        o0(G(value));
    }

    public void u0(long value) {
        p0(H(value));
    }

    public static int f(int fieldNumber, double value) {
        return D(fieldNumber) + g(value);
    }

    public static int l(int fieldNumber, float value) {
        return D(fieldNumber) + m(value);
    }

    public static int o(int fieldNumber, int value) {
        return D(fieldNumber) + p(value);
    }

    public static int a(int fieldNumber, boolean value) {
        return D(fieldNumber) + b(value);
    }

    public static int s(int fieldNumber, o value) {
        return D(fieldNumber) + t(value);
    }

    public static int d(int fieldNumber, d value) {
        return D(fieldNumber) + e(value);
    }

    public static int h(int fieldNumber, int value) {
        return D(fieldNumber) + i(value);
    }

    public static int A(int fieldNumber, long value) {
        return D(fieldNumber) + B(value);
    }

    public static int g(double value) {
        return 8;
    }

    public static int m(float value) {
        return 4;
    }

    public static int F(long value) {
        return w(value);
    }

    public static int q(long value) {
        return w(value);
    }

    public static int p(int value) {
        if (value >= 0) {
            return v(value);
        }
        return 10;
    }

    public static int k(long value) {
        return 8;
    }

    public static int j(int value) {
        return 4;
    }

    public static int b(boolean value) {
        return 1;
    }

    public static int C(String value) {
        try {
            byte[] bytes = value.getBytes("UTF-8");
            return v(bytes.length) + bytes.length;
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UTF-8 not supported.", e2);
        }
    }

    public static int n(o value) {
        return value.getSerializedSize();
    }

    public static int t(o value) {
        int size = value.getSerializedSize();
        return v(size) + size;
    }

    public static int r(k value) {
        int size = value.b();
        return v(size) + size;
    }

    public static int e(d value) {
        return v(value.size()) + value.size();
    }

    public static int c(byte[] value) {
        return v(value.length) + value.length;
    }

    public static int E(int value) {
        return v(value);
    }

    public static int i(int value) {
        return p(value);
    }

    public static int x(int value) {
        return 4;
    }

    public static int y(long value) {
        return 8;
    }

    public static int z(int value) {
        return v(G(value));
    }

    public static int B(long value) {
        return w(H(value));
    }

    private void K() {
        OutputStream outputStream = this.e;
        if (outputStream != null) {
            outputStream.write(this.a, 0, this.c);
            this.c = 0;
            return;
        }
        throw new OutOfSpaceException();
    }

    public void I() {
        if (this.e != null) {
            K();
        }
    }

    public static class OutOfSpaceException extends IOException {
        OutOfSpaceException() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    public void g0(byte value) {
        if (this.c == this.b) {
            K();
        }
        byte[] bArr = this.a;
        int i = this.c;
        this.c = i + 1;
        bArr[i] = value;
        this.d++;
    }

    public void h0(int value) {
        g0((byte) value);
    }

    public void i0(d value) {
        j0(value, 0, value.size());
    }

    public void k0(byte[] value) {
        l0(value, 0, value.length);
    }

    public void l0(byte[] value, int offset, int length) {
        int i = this.b;
        int i2 = this.c;
        if (i - i2 >= length) {
            System.arraycopy(value, offset, this.a, i2, length);
            this.c += length;
            this.d += length;
            return;
        }
        int bytesWritten = i - i2;
        System.arraycopy(value, offset, this.a, i2, bytesWritten);
        int offset2 = offset + bytesWritten;
        int length2 = length - bytesWritten;
        this.c = this.b;
        this.d += bytesWritten;
        K();
        if (length2 <= this.b) {
            System.arraycopy(value, offset2, this.a, 0, length2);
            this.c = length2;
        } else {
            this.e.write(value, offset2, length2);
        }
        this.d += length2;
    }

    public void j0(d value, int offset, int length) {
        int i = this.b;
        int i2 = this.c;
        if (i - i2 >= length) {
            value.h(this.a, offset, i2, length);
            this.c += length;
            this.d += length;
            return;
        }
        int bytesWritten = i - i2;
        value.h(this.a, offset, i2, bytesWritten);
        int offset2 = offset + bytesWritten;
        int length2 = length - bytesWritten;
        this.c = this.b;
        this.d += bytesWritten;
        K();
        if (length2 <= this.b) {
            value.h(this.a, offset2, 0, length2);
            this.c = length2;
        } else {
            value.y(this.e, offset2, length2);
        }
        this.d += length2;
    }

    public void w0(int fieldNumber, int wireType) {
        o0(w.c(fieldNumber, wireType));
    }

    public static int D(int fieldNumber) {
        return v(w.c(fieldNumber, 0));
    }

    public void o0(int value) {
        while ((value & -128) != 0) {
            h0((value & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            value >>>= 7;
        }
        h0(value);
    }

    public static int v(int value) {
        if ((value & -128) == 0) {
            return 1;
        }
        if ((value & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & value) == 0) {
            return 3;
        }
        if ((-268435456 & value) == 0) {
            return 4;
        }
        return 5;
    }

    public void p0(long value) {
        while ((-128 & value) != 0) {
            h0((((int) value) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            value >>>= 7;
        }
        h0((int) value);
    }

    public static int w(long value) {
        if ((-128 & value) == 0) {
            return 1;
        }
        if ((-16384 & value) == 0) {
            return 2;
        }
        if ((-2097152 & value) == 0) {
            return 3;
        }
        if ((-268435456 & value) == 0) {
            return 4;
        }
        if ((-34359738368L & value) == 0) {
            return 5;
        }
        if ((-4398046511104L & value) == 0) {
            return 6;
        }
        if ((-562949953421312L & value) == 0) {
            return 7;
        }
        if ((-72057594037927936L & value) == 0) {
            return 8;
        }
        if ((Long.MIN_VALUE & value) == 0) {
            return 9;
        }
        return 10;
    }

    public void m0(int value) {
        h0(value & 255);
        h0((value >> 8) & 255);
        h0((value >> 16) & 255);
        h0((value >> 24) & 255);
    }

    public void n0(long value) {
        h0(((int) value) & 255);
        h0(((int) (value >> 8)) & 255);
        h0(((int) (value >> 16)) & 255);
        h0(((int) (value >> 24)) & 255);
        h0(((int) (value >> 32)) & 255);
        h0(((int) (value >> 40)) & 255);
        h0(((int) (value >> 48)) & 255);
        h0(((int) (value >> 56)) & 255);
    }

    public static int G(int n) {
        return (n << 1) ^ (n >> 31);
    }

    public static long H(long n) {
        return (n << 1) ^ (n >> 63);
    }
}
