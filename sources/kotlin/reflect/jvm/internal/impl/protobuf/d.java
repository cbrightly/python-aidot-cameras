package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.slf4j.e;

/* compiled from: ByteString */
public abstract class d implements Iterable<Byte> {
    public static final d c = new n(new byte[0]);

    /* compiled from: ByteString */
    public interface a extends Iterator<Byte> {
        byte nextByte();
    }

    /* access modifiers changed from: protected */
    public abstract void i(byte[] bArr, int i, int i2, int i3);

    /* access modifiers changed from: protected */
    public abstract int k();

    /* access modifiers changed from: protected */
    public abstract boolean n();

    public abstract boolean o();

    /* renamed from: p */
    public abstract a iterator();

    public abstract e q();

    /* access modifiers changed from: protected */
    public abstract int s(int i, int i2, int i3);

    public abstract int size();

    /* access modifiers changed from: protected */
    public abstract int t(int i, int i2, int i3);

    /* access modifiers changed from: protected */
    public abstract int u();

    public abstract String w(String str);

    /* access modifiers changed from: package-private */
    public abstract void z(OutputStream outputStream, int i, int i2);

    static {
        Class<d> cls = d.class;
    }

    d() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public static d f(byte[] bytes, int offset, int size) {
        byte[] copy = new byte[size];
        System.arraycopy(bytes, offset, copy, 0, size);
        return new n(copy);
    }

    public static d e(byte[] bytes) {
        return f(bytes, 0, bytes.length);
    }

    public static d g(String text) {
        try {
            return new n(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public d b(d other) {
        int thisSize = size();
        int otherSize = other.size();
        if (((long) thisSize) + ((long) otherSize) < 2147483647L) {
            return s.D(this, other);
        }
        StringBuilder sb = new StringBuilder(53);
        sb.append("ByteString would be too long: ");
        sb.append(thisSize);
        sb.append(e.ANY_NON_NULL_MARKER);
        sb.append(otherSize);
        throw new IllegalArgumentException(sb.toString());
    }

    public static d d(Iterable<d> byteStrings) {
        Collection<ByteString> collection;
        if (!(byteStrings instanceof Collection)) {
            collection = new ArrayList<>();
            for (d byteString : byteStrings) {
                collection.add(byteString);
            }
        } else {
            collection = (Collection) byteStrings;
        }
        if (collection.isEmpty()) {
            return c;
        }
        return a(collection.iterator(), collection.size());
    }

    private static d a(Iterator<d> iterator, int length) {
        if (length < 1) {
            throw new AssertionError();
        } else if (length == 1) {
            return iterator.next();
        } else {
            int halfLength = length >>> 1;
            return a(iterator, halfLength).b(a(iterator, length - halfLength));
        }
    }

    public void h(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        if (sourceOffset < 0) {
            StringBuilder sb = new StringBuilder(30);
            sb.append("Source offset < 0: ");
            sb.append(sourceOffset);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (targetOffset < 0) {
            StringBuilder sb2 = new StringBuilder(30);
            sb2.append("Target offset < 0: ");
            sb2.append(targetOffset);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else if (numberToCopy < 0) {
            StringBuilder sb3 = new StringBuilder(23);
            sb3.append("Length < 0: ");
            sb3.append(numberToCopy);
            throw new IndexOutOfBoundsException(sb3.toString());
        } else if (sourceOffset + numberToCopy > size()) {
            StringBuilder sb4 = new StringBuilder(34);
            sb4.append("Source end offset < 0: ");
            sb4.append(sourceOffset + numberToCopy);
            throw new IndexOutOfBoundsException(sb4.toString());
        } else if (targetOffset + numberToCopy > target.length) {
            StringBuilder sb5 = new StringBuilder(34);
            sb5.append("Target end offset < 0: ");
            sb5.append(targetOffset + numberToCopy);
            throw new IndexOutOfBoundsException(sb5.toString());
        } else if (numberToCopy > 0) {
            i(target, sourceOffset, targetOffset, numberToCopy);
        }
    }

    public byte[] v() {
        int size = size();
        if (size == 0) {
            return i.a;
        }
        byte[] result = new byte[size];
        i(result, 0, 0, size);
        return result;
    }

    /* access modifiers changed from: package-private */
    public void y(OutputStream out, int sourceOffset, int numberToWrite) {
        if (sourceOffset < 0) {
            StringBuilder sb = new StringBuilder(30);
            sb.append("Source offset < 0: ");
            sb.append(sourceOffset);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (numberToWrite < 0) {
            StringBuilder sb2 = new StringBuilder(23);
            sb2.append("Length < 0: ");
            sb2.append(numberToWrite);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else if (sourceOffset + numberToWrite > size()) {
            StringBuilder sb3 = new StringBuilder(39);
            sb3.append("Source end offset exceeded: ");
            sb3.append(sourceOffset + numberToWrite);
            throw new IndexOutOfBoundsException(sb3.toString());
        } else if (numberToWrite > 0) {
            z(out, sourceOffset, numberToWrite);
        }
    }

    public String x() {
        try {
            return w("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public static b r() {
        return new b(128);
    }

    /* compiled from: ByteString */
    public static final class b extends OutputStream {
        private static final byte[] c = new byte[0];
        private final int d;
        private final ArrayList<d> f;
        private int q;
        private byte[] x;
        private int y;

        b(int initialCapacity) {
            if (initialCapacity >= 0) {
                this.d = initialCapacity;
                this.f = new ArrayList<>();
                this.x = new byte[initialCapacity];
                return;
            }
            throw new IllegalArgumentException("Buffer size < 0");
        }

        public synchronized void write(int b) {
            if (this.y == this.x.length) {
                c(1);
            }
            byte[] bArr = this.x;
            int i = this.y;
            this.y = i + 1;
            bArr[i] = (byte) b;
        }

        public synchronized void write(byte[] b, int offset, int length) {
            byte[] bArr = this.x;
            int length2 = bArr.length;
            int i = this.y;
            if (length <= length2 - i) {
                System.arraycopy(b, offset, bArr, i, length);
                this.y += length;
            } else {
                int copySize = bArr.length - i;
                System.arraycopy(b, offset, bArr, i, copySize);
                int length3 = length - copySize;
                c(length3);
                System.arraycopy(b, offset + copySize, this.x, 0, length3);
                this.y = length3;
            }
        }

        public synchronized d j() {
            g();
            return d.d(this.f);
        }

        private byte[] a(byte[] buffer, int length) {
            byte[] result = new byte[length];
            System.arraycopy(buffer, 0, result, 0, Math.min(buffer.length, length));
            return result;
        }

        public synchronized int i() {
            return this.q + this.y;
        }

        public String toString() {
            return String.format("<ByteString.Output@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(i())});
        }

        private void c(int minSize) {
            this.f.add(new n(this.x));
            int length = this.q + this.x.length;
            this.q = length;
            this.x = new byte[Math.max(this.d, Math.max(minSize, length >>> 1))];
            this.y = 0;
        }

        private void g() {
            int i = this.y;
            byte[] bArr = this.x;
            if (i >= bArr.length) {
                this.f.add(new n(this.x));
                this.x = c;
            } else if (i > 0) {
                this.f.add(new n(a(bArr, i)));
            }
            this.q += this.y;
            this.y = 0;
        }
    }

    public String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }
}
