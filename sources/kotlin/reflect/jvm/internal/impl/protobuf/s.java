package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import kotlin.reflect.jvm.internal.impl.protobuf.d;

/* compiled from: RopeByteString */
public class s extends d {
    /* access modifiers changed from: private */
    public static final int[] d;
    private final int f;
    private int p0;
    /* access modifiers changed from: private */
    public final d q;
    /* access modifiers changed from: private */
    public final d x;
    private final int y;
    private final int z;

    static {
        List<Integer> numbers = new ArrayList<>();
        int f1 = 1;
        int f2 = 1;
        while (f2 > 0) {
            numbers.add(Integer.valueOf(f2));
            int temp = f1 + f2;
            f1 = f2;
            f2 = temp;
        }
        numbers.add(Integer.MAX_VALUE);
        d = new int[numbers.size()];
        int i = 0;
        while (true) {
            int[] iArr = d;
            if (i < iArr.length) {
                iArr[i] = numbers.get(i).intValue();
                i++;
            } else {
                return;
            }
        }
    }

    private s(d left, d right) {
        this.p0 = 0;
        this.q = left;
        this.x = right;
        int size = left.size();
        this.y = size;
        this.f = size + right.size();
        this.z = Math.max(left.k(), right.k()) + 1;
    }

    static d D(d left, d right) {
        s leftRope = left instanceof s ? (s) left : null;
        if (right.size() == 0) {
            return left;
        }
        if (left.size() == 0) {
            return right;
        }
        int newLength = left.size() + right.size();
        if (newLength < 128) {
            return E(left, right);
        }
        if (leftRope != null && leftRope.x.size() + right.size() < 128) {
            return new s(leftRope.q, E(leftRope.x, right));
        } else if (leftRope == null || leftRope.q.k() <= leftRope.x.k() || leftRope.k() <= right.k()) {
            if (newLength >= d[Math.max(left.k(), right.k()) + 1]) {
                return new s(left, right);
            }
            return new b().b(left, right);
        } else {
            return new s(leftRope.q, new s(leftRope.x, right));
        }
    }

    private static n E(d left, d right) {
        int leftSize = left.size();
        int rightSize = right.size();
        byte[] bytes = new byte[(leftSize + rightSize)];
        left.h(bytes, 0, 0, leftSize);
        right.h(bytes, 0, leftSize, rightSize);
        return new n(bytes);
    }

    public int size() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public int k() {
        return this.z;
    }

    /* access modifiers changed from: protected */
    public boolean n() {
        return this.f >= d[this.z];
    }

    /* access modifiers changed from: protected */
    public void i(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        int i = sourceOffset + numberToCopy;
        int i2 = this.y;
        if (i <= i2) {
            this.q.i(target, sourceOffset, targetOffset, numberToCopy);
        } else if (sourceOffset >= i2) {
            this.x.i(target, sourceOffset - i2, targetOffset, numberToCopy);
        } else {
            int leftLength = i2 - sourceOffset;
            this.q.i(target, sourceOffset, targetOffset, leftLength);
            this.x.i(target, 0, targetOffset + leftLength, numberToCopy - leftLength);
        }
    }

    /* access modifiers changed from: package-private */
    public void z(OutputStream out, int sourceOffset, int numberToWrite) {
        int i = sourceOffset + numberToWrite;
        int i2 = this.y;
        if (i <= i2) {
            this.q.z(out, sourceOffset, numberToWrite);
        } else if (sourceOffset >= i2) {
            this.x.z(out, sourceOffset - i2, numberToWrite);
        } else {
            int numberToWriteInLeft = i2 - sourceOffset;
            this.q.z(out, sourceOffset, numberToWriteInLeft);
            this.x.z(out, 0, numberToWrite - numberToWriteInLeft);
        }
    }

    public String w(String charsetName) {
        return new String(v(), charsetName);
    }

    public boolean o() {
        int leftPartial = this.q.t(0, 0, this.y);
        d dVar = this.x;
        if (dVar.t(leftPartial, 0, dVar.size()) == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int t(int state, int offset, int length) {
        int toIndex = offset + length;
        int i = this.y;
        if (toIndex <= i) {
            return this.q.t(state, offset, length);
        }
        if (offset >= i) {
            return this.x.t(state, offset - i, length);
        }
        int leftLength = i - offset;
        return this.x.t(this.q.t(state, offset, leftLength), 0, length - leftLength);
    }

    public boolean equals(Object other) {
        int cachedOtherHash;
        if (other == this) {
            return true;
        }
        if (!(other instanceof d)) {
            return false;
        }
        d otherByteString = (d) other;
        if (this.f != otherByteString.size()) {
            return false;
        }
        if (this.f == 0) {
            return true;
        }
        if (this.p0 == 0 || (cachedOtherHash = otherByteString.u()) == 0 || this.p0 == cachedOtherHash) {
            return F(otherByteString);
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: kotlin.reflect.jvm.internal.impl.protobuf.n} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: kotlin.reflect.jvm.internal.impl.protobuf.n} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean F(kotlin.reflect.jvm.internal.impl.protobuf.d r13) {
        /*
            r12 = this;
            r0 = 0
            kotlin.reflect.jvm.internal.impl.protobuf.s$c r1 = new kotlin.reflect.jvm.internal.impl.protobuf.s$c
            r2 = 0
            r1.<init>(r12)
            java.lang.Object r3 = r1.next()
            kotlin.reflect.jvm.internal.impl.protobuf.n r3 = (kotlin.reflect.jvm.internal.impl.protobuf.n) r3
            r4 = 0
            kotlin.reflect.jvm.internal.impl.protobuf.s$c r5 = new kotlin.reflect.jvm.internal.impl.protobuf.s$c
            r5.<init>(r13)
            r2 = r5
            java.lang.Object r5 = r2.next()
            kotlin.reflect.jvm.internal.impl.protobuf.n r5 = (kotlin.reflect.jvm.internal.impl.protobuf.n) r5
            r6 = 0
        L_0x001b:
            int r7 = r3.size()
            int r7 = r7 - r0
            int r8 = r5.size()
            int r8 = r8 - r4
            int r9 = java.lang.Math.min(r7, r8)
            if (r0 != 0) goto L_0x0030
            boolean r10 = r3.B(r5, r4, r9)
            goto L_0x0034
        L_0x0030:
            boolean r10 = r5.B(r3, r0, r9)
        L_0x0034:
            if (r10 != 0) goto L_0x0038
            r11 = 0
            return r11
        L_0x0038:
            int r6 = r6 + r9
            int r11 = r12.f
            if (r6 < r11) goto L_0x0047
            if (r6 != r11) goto L_0x0041
            r11 = 1
            return r11
        L_0x0041:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            r11.<init>()
            throw r11
        L_0x0047:
            if (r9 != r7) goto L_0x0052
            r0 = 0
            java.lang.Object r11 = r1.next()
            r3 = r11
            kotlin.reflect.jvm.internal.impl.protobuf.n r3 = (kotlin.reflect.jvm.internal.impl.protobuf.n) r3
            goto L_0x0053
        L_0x0052:
            int r0 = r0 + r9
        L_0x0053:
            if (r9 != r8) goto L_0x005e
            r4 = 0
            java.lang.Object r11 = r2.next()
            r5 = r11
            kotlin.reflect.jvm.internal.impl.protobuf.n r5 = (kotlin.reflect.jvm.internal.impl.protobuf.n) r5
            goto L_0x005f
        L_0x005e:
            int r4 = r4 + r9
        L_0x005f:
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.protobuf.s.F(kotlin.reflect.jvm.internal.impl.protobuf.d):boolean");
    }

    public int hashCode() {
        int h = this.p0;
        if (h == 0) {
            h = s(this.f, 0, this.f);
            if (h == 0) {
                h = 1;
            }
            this.p0 = h;
        }
        return h;
    }

    /* access modifiers changed from: protected */
    public int u() {
        return this.p0;
    }

    /* access modifiers changed from: protected */
    public int s(int h, int offset, int length) {
        int toIndex = offset + length;
        int i = this.y;
        if (toIndex <= i) {
            return this.q.s(h, offset, length);
        }
        if (offset >= i) {
            return this.x.s(h, offset - i, length);
        }
        int leftLength = i - offset;
        return this.x.s(this.q.s(h, offset, leftLength), 0, length - leftLength);
    }

    public e q() {
        return e.g(new e());
    }

    /* compiled from: RopeByteString */
    public static class b {
        private final Stack<d> a;

        private b() {
            this.a = new Stack<>();
        }

        /* access modifiers changed from: private */
        public d b(d left, d right) {
            c(left);
            c(right);
            d partialString = this.a.pop();
            while (!this.a.isEmpty()) {
                partialString = new s(this.a.pop(), partialString);
            }
            return partialString;
        }

        private void c(d root) {
            if (root.n()) {
                e(root);
            } else if (root instanceof s) {
                s rbs = (s) root;
                c(rbs.q);
                c(rbs.x);
            } else {
                String valueOf = String.valueOf(String.valueOf(root.getClass()));
                StringBuilder sb = new StringBuilder(valueOf.length() + 49);
                sb.append("Has a new type of ByteString been created? Found ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        private void e(d byteString) {
            int depthBin = d(byteString.size());
            int binEnd = s.d[depthBin + 1];
            if (this.a.isEmpty() || this.a.peek().size() >= binEnd) {
                this.a.push(byteString);
                return;
            }
            int binStart = s.d[depthBin];
            d newTree = this.a.pop();
            while (!this.a.isEmpty() && this.a.peek().size() < binStart) {
                newTree = new s(this.a.pop(), newTree);
            }
            d newTree2 = new s(newTree, byteString);
            while (!this.a.isEmpty()) {
                if (this.a.peek().size() >= s.d[d(newTree2.size()) + 1]) {
                    break;
                }
                newTree2 = new s(this.a.pop(), newTree2);
            }
            this.a.push(newTree2);
        }

        private int d(int length) {
            int depth = Arrays.binarySearch(s.d, length);
            if (depth < 0) {
                return (-(depth + 1)) - 1;
            }
            return depth;
        }
    }

    /* compiled from: RopeByteString */
    public static class c implements Iterator<n> {
        private final Stack<s> c;
        private n d;

        private c(d root) {
            this.c = new Stack<>();
            this.d = b(root);
        }

        private n b(d root) {
            d pos = root;
            while (pos instanceof s) {
                s rbs = (s) pos;
                this.c.push(rbs);
                pos = rbs.q;
            }
            return (n) pos;
        }

        private n c() {
            while (!this.c.isEmpty()) {
                n result = b(this.c.pop().x);
                if (!result.isEmpty()) {
                    return result;
                }
            }
            return null;
        }

        public boolean hasNext() {
            return this.d != null;
        }

        /* renamed from: d */
        public n next() {
            if (this.d != null) {
                n result = this.d;
                this.d = c();
                return result;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* renamed from: p */
    public d.a iterator() {
        return new d();
    }

    /* compiled from: RopeByteString */
    public class d implements d.a {
        private final c c;
        private d.a d;
        int f;

        private d() {
            c cVar = new c(s.this);
            this.c = cVar;
            this.d = cVar.next().iterator();
            this.f = s.this.size();
        }

        public boolean hasNext() {
            return this.f > 0;
        }

        /* renamed from: b */
        public Byte next() {
            return Byte.valueOf(nextByte());
        }

        public byte nextByte() {
            if (!this.d.hasNext()) {
                this.d = this.c.next().iterator();
            }
            this.f--;
            return this.d.nextByte();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: RopeByteString */
    public class e extends InputStream {
        private c c;
        private n d;
        private int f;
        private int q;
        private int x;
        private int y;

        public e() {
            c();
        }

        public int read(byte[] b, int offset, int length) {
            if (b == null) {
                throw new NullPointerException();
            } else if (offset >= 0 && length >= 0 && length <= b.length - offset) {
                return g(b, offset, length);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public long skip(long length) {
            if (length >= 0) {
                if (length > 2147483647L) {
                    length = 2147483647L;
                }
                return (long) g((byte[]) null, 0, (int) length);
            }
            throw new IndexOutOfBoundsException();
        }

        private int g(byte[] b, int offset, int length) {
            int bytesRemaining = length;
            while (true) {
                if (bytesRemaining <= 0) {
                    break;
                }
                a();
                if (this.d != null) {
                    int count = Math.min(this.f - this.q, bytesRemaining);
                    if (b != null) {
                        this.d.h(b, this.q, offset, count);
                        offset += count;
                    }
                    this.q += count;
                    bytesRemaining -= count;
                } else if (bytesRemaining == length) {
                    return -1;
                }
            }
            return length - bytesRemaining;
        }

        public int read() {
            a();
            n nVar = this.d;
            if (nVar == null) {
                return -1;
            }
            int i = this.q;
            this.q = i + 1;
            return nVar.A(i) & 255;
        }

        public int available() {
            return s.this.size() - (this.x + this.q);
        }

        public boolean markSupported() {
            return true;
        }

        public void mark(int readAheadLimit) {
            this.y = this.x + this.q;
        }

        public synchronized void reset() {
            c();
            g((byte[]) null, 0, this.y);
        }

        private void c() {
            c cVar = new c(s.this);
            this.c = cVar;
            n d2 = cVar.next();
            this.d = d2;
            this.f = d2.size();
            this.q = 0;
            this.x = 0;
        }

        private void a() {
            int i;
            if (this.d != null && this.q == (i = this.f)) {
                this.x += i;
                this.q = 0;
                if (this.c.hasNext()) {
                    n d2 = this.c.next();
                    this.d = d2;
                    this.f = d2.size();
                    return;
                }
                this.d = null;
                this.f = 0;
            }
        }
    }
}
