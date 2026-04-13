package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.OutputStream;
import java.util.NoSuchElementException;
import kotlin.reflect.jvm.internal.impl.protobuf.d;

/* compiled from: LiteralByteString */
public class n extends d {
    protected final byte[] d;
    private int f = 0;

    n(byte[] bytes) {
        this.d = bytes;
    }

    public byte A(int index) {
        return this.d[index];
    }

    public int size() {
        return this.d.length;
    }

    /* access modifiers changed from: protected */
    public void i(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        System.arraycopy(this.d, sourceOffset, target, targetOffset, numberToCopy);
    }

    /* access modifiers changed from: package-private */
    public void z(OutputStream outputStream, int sourceOffset, int numberToWrite) {
        outputStream.write(this.d, C() + sourceOffset, numberToWrite);
    }

    public String w(String charsetName) {
        return new String(this.d, C(), size(), charsetName);
    }

    public boolean o() {
        int offset = C();
        return v.f(this.d, offset, size() + offset);
    }

    /* access modifiers changed from: protected */
    public int t(int state, int offset, int length) {
        int index = C() + offset;
        return v.g(state, this.d, index, index + length);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof d) || size() != ((d) other).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (other instanceof n) {
            return B((n) other, 0, size());
        }
        if (other instanceof s) {
            return other.equals(this);
        }
        String valueOf = String.valueOf(String.valueOf(other.getClass()));
        StringBuilder sb = new StringBuilder(valueOf.length() + 49);
        sb.append("Has a new type of ByteString been created? Found ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: package-private */
    public boolean B(n other, int offset, int length) {
        if (length > other.size()) {
            int size = size();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(length);
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        } else if (offset + length <= other.size()) {
            byte[] thisBytes = this.d;
            byte[] otherBytes = other.d;
            int thisLimit = C() + length;
            int thisIndex = C();
            int otherIndex = other.C() + offset;
            while (thisIndex < thisLimit) {
                if (thisBytes[thisIndex] != otherBytes[otherIndex]) {
                    return false;
                }
                thisIndex++;
                otherIndex++;
            }
            return true;
        } else {
            int size2 = other.size();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: ");
            sb2.append(offset);
            sb2.append(", ");
            sb2.append(length);
            sb2.append(", ");
            sb2.append(size2);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public int hashCode() {
        int h = this.f;
        if (h == 0) {
            int size = size();
            h = s(size, 0, size);
            if (h == 0) {
                h = 1;
            }
            this.f = h;
        }
        return h;
    }

    /* access modifiers changed from: protected */
    public int u() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public int s(int h, int offset, int length) {
        return D(h, this.d, C() + offset, length);
    }

    static int D(int h, byte[] bytes, int offset, int length) {
        for (int i = offset; i < offset + length; i++) {
            h = (h * 31) + bytes[i];
        }
        return h;
    }

    public e q() {
        return e.h(this);
    }

    /* renamed from: p */
    public d.a iterator() {
        return new b();
    }

    /* compiled from: LiteralByteString */
    public class b implements d.a {
        private int c;
        private final int d;

        private b() {
            this.c = 0;
            this.d = n.this.size();
        }

        public boolean hasNext() {
            return this.c < this.d;
        }

        /* renamed from: b */
        public Byte next() {
            return Byte.valueOf(nextByte());
        }

        public byte nextByte() {
            try {
                byte[] bArr = n.this.d;
                int i = this.c;
                this.c = i + 1;
                return bArr[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException(e.getMessage());
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: protected */
    public int k() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean n() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int C() {
        return 0;
    }
}
