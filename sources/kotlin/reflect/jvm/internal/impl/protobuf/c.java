package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.NoSuchElementException;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import org.slf4j.e;

/* compiled from: BoundedByteString */
public class c extends n {
    private final int q;
    private final int x;

    c(byte[] bytes, int offset, int length) {
        super(bytes);
        if (offset < 0) {
            StringBuilder sb = new StringBuilder(29);
            sb.append("Offset too small: ");
            sb.append(offset);
            throw new IllegalArgumentException(sb.toString());
        } else if (length < 0) {
            StringBuilder sb2 = new StringBuilder(29);
            sb2.append("Length too small: ");
            sb2.append(offset);
            throw new IllegalArgumentException(sb2.toString());
        } else if (((long) offset) + ((long) length) <= ((long) bytes.length)) {
            this.q = offset;
            this.x = length;
        } else {
            StringBuilder sb3 = new StringBuilder(48);
            sb3.append("Offset+Length too large: ");
            sb3.append(offset);
            sb3.append(e.ANY_NON_NULL_MARKER);
            sb3.append(length);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public byte A(int index) {
        if (index < 0) {
            StringBuilder sb = new StringBuilder(28);
            sb.append("Index too small: ");
            sb.append(index);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        } else if (index < size()) {
            return this.d[this.q + index];
        } else {
            int size = size();
            StringBuilder sb2 = new StringBuilder(41);
            sb2.append("Index too large: ");
            sb2.append(index);
            sb2.append(", ");
            sb2.append(size);
            throw new ArrayIndexOutOfBoundsException(sb2.toString());
        }
    }

    public int size() {
        return this.x;
    }

    /* access modifiers changed from: protected */
    public int C() {
        return this.q;
    }

    /* access modifiers changed from: protected */
    public void i(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        System.arraycopy(this.d, C() + sourceOffset, target, targetOffset, numberToCopy);
    }

    /* renamed from: p */
    public d.a iterator() {
        return new b();
    }

    /* compiled from: BoundedByteString */
    public class b implements d.a {
        private int c;
        private final int d;

        private b() {
            int C = c.this.C();
            this.c = C;
            this.d = C + c.this.size();
        }

        public boolean hasNext() {
            return this.c < this.d;
        }

        /* renamed from: b */
        public Byte next() {
            return Byte.valueOf(nextByte());
        }

        public byte nextByte() {
            int i = this.c;
            if (i < this.d) {
                byte[] bArr = c.this.d;
                this.c = i + 1;
                return bArr[i];
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
