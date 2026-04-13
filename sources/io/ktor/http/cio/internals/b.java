package io.ktor.http.cio.internals;

import io.ktor.utils.io.pool.d;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CharArrayBuilder.kt */
public final class b implements CharSequence, Appendable {
    private List<char[]> c;
    private char[] d;
    private String f;
    private boolean q;
    private int x;
    private int y;
    @NotNull
    private final d<char[]> z;

    public b() {
        this((d) null, 1, (DefaultConstructorMarker) null);
    }

    public b(@NotNull d<char[]> pool) {
        k.f(pool, "pool");
        this.z = pool;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(d<char[]> dVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? c.a() : dVar);
    }

    public final /* bridge */ char charAt(int i) {
        return i(i);
    }

    public final /* bridge */ int length() {
        return k();
    }

    public int k() {
        return this.y;
    }

    public char i(int index) {
        boolean z2 = true;
        if (index >= 0) {
            if (index >= length()) {
                z2 = false;
            }
            if (z2) {
                return j(index);
            }
            throw new IllegalArgumentException(("index " + index + " is not in range [0, " + length() + ')').toString());
        }
        throw new IllegalArgumentException(("index is negative: " + index).toString());
    }

    /* access modifiers changed from: private */
    public final char j(int index) {
        char[] f2 = f(index);
        char[] cArr = this.d;
        if (cArr == null) {
            k.n();
        }
        return f2[index % cArr.length];
    }

    @NotNull
    public CharSequence subSequence(int startIndex, int endIndex) {
        boolean z2 = true;
        if (startIndex <= endIndex) {
            if (startIndex >= 0) {
                if (endIndex > length()) {
                    z2 = false;
                }
                if (z2) {
                    return new a(startIndex, endIndex);
                }
                throw new IllegalArgumentException(("endIndex (" + endIndex + ") is greater than length (" + length() + ')').toString());
            }
            throw new IllegalArgumentException(("startIndex is negative: " + startIndex).toString());
        }
        throw new IllegalArgumentException(("startIndex (" + startIndex + ") should be less or equal to endIndex (" + endIndex + ')').toString());
    }

    @NotNull
    public String toString() {
        String str = this.f;
        if (str != null) {
            return str;
        }
        String it = g(0, length()).toString();
        this.f = it;
        return it;
    }

    public boolean equals(@Nullable Object other) {
        if ((other instanceof CharSequence) && length() == ((CharSequence) other).length()) {
            return n(0, (CharSequence) other, 0, length());
        }
        return false;
    }

    public int hashCode() {
        String str = this.f;
        return str != null ? str.hashCode() : l(0, length());
    }

    @NotNull
    public Appendable append(char c2) {
        char[] m = m();
        char[] cArr = this.d;
        if (cArr == null) {
            k.n();
        }
        int length = cArr.length;
        int i = this.x;
        m[length - i] = c2;
        this.f = null;
        this.x = i - 1;
        this.y = length() + 1;
        return this;
    }

    @NotNull
    public Appendable append(@Nullable CharSequence csq, int start, int end) {
        if (csq == null) {
            return this;
        }
        int current = start;
        while (current < end) {
            char[] buffer = m();
            int length = buffer.length;
            int i = this.x;
            int offset = length - i;
            int bytesToCopy = Math.min(end - current, i);
            for (int i2 = 0; i2 < bytesToCopy; i2++) {
                buffer[offset + i2] = csq.charAt(current + i2);
            }
            current += bytesToCopy;
            this.x -= bytesToCopy;
        }
        this.f = null;
        this.y = length() + (end - start);
        return this;
    }

    @NotNull
    public Appendable append(@Nullable CharSequence csq) {
        if (csq != null) {
            return append(csq, 0, csq.length());
        }
        return this;
    }

    public final void o() {
        List list = this.c;
        if (list != null) {
            this.d = null;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                this.z.N0(list.get(i));
            }
        } else {
            char[] it = this.d;
            if (it != null) {
                this.z.N0(it);
            }
            this.d = null;
        }
        this.q = true;
        this.c = null;
        this.f = null;
        this.y = 0;
        this.x = 0;
    }

    /* access modifiers changed from: private */
    public final CharSequence g(int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return "";
        }
        StringBuilder builder = new StringBuilder(endIndex - startIndex);
        for (int base = startIndex - (startIndex % 2048); base < endIndex; base += 2048) {
            char[] buffer = f(base);
            int innerStartIndex = Math.max(0, startIndex - base);
            int innerEndIndex = Math.min(endIndex - base, 2048);
            for (int innerIndex = innerStartIndex; innerIndex < innerEndIndex; innerIndex++) {
                builder.append(buffer[innerIndex]);
            }
        }
        return builder;
    }

    /* compiled from: CharArrayBuilder.kt */
    public final class a implements CharSequence {
        private String c;
        private final int d;
        private final int f;

        public a(int start, int end) {
            this.d = start;
            this.f = end;
        }

        public final /* bridge */ char charAt(int i) {
            return a(i);
        }

        public final /* bridge */ int length() {
            return b();
        }

        public int b() {
            return this.f - this.d;
        }

        public char a(int index) {
            int withOffset = this.d + index;
            boolean z = true;
            if (index >= 0) {
                if (withOffset >= this.f) {
                    z = false;
                }
                if (z) {
                    return b.this.j(withOffset);
                }
                throw new IllegalArgumentException(("index (" + index + ") should be less than length (" + length() + ')').toString());
            }
            throw new IllegalArgumentException(("index is negative: " + index).toString());
        }

        @NotNull
        public CharSequence subSequence(int startIndex, int endIndex) {
            boolean z = true;
            if (startIndex >= 0) {
                if (startIndex <= endIndex) {
                    int i = this.f;
                    int i2 = this.d;
                    if (endIndex > i - i2) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalArgumentException(("end should be less than length (" + length() + ')').toString());
                    } else if (startIndex == endIndex) {
                        return "";
                    } else {
                        return new a(i2 + startIndex, i2 + endIndex);
                    }
                } else {
                    throw new IllegalArgumentException(("start (" + startIndex + ") should be less or equal to end (" + endIndex + ')').toString());
                }
            } else {
                throw new IllegalArgumentException(("start is negative: " + startIndex).toString());
            }
        }

        @NotNull
        public String toString() {
            String str = this.c;
            if (str != null) {
                return str;
            }
            String it = b.this.g(this.d, this.f).toString();
            this.c = it;
            return it;
        }

        public boolean equals(@Nullable Object other) {
            if ((other instanceof CharSequence) && ((CharSequence) other).length() == length()) {
                return b.this.n(this.d, (CharSequence) other, 0, length());
            }
            return false;
        }

        public int hashCode() {
            String str = this.c;
            return str != null ? str.hashCode() : b.this.l(this.d, this.f);
        }
    }

    private final char[] f(int index) {
        List list = this.c;
        if (list != null) {
            char[] cArr = this.d;
            if (cArr == null) {
                k.n();
            }
            return list.get(index / cArr.length);
        } else if (index < 2048) {
            char[] cArr2 = this.d;
            if (cArr2 != null) {
                return cArr2;
            }
            p(index);
            throw null;
        } else {
            p(index);
            throw null;
        }
    }

    private final Void p(int index) {
        if (this.q) {
            throw new IllegalStateException("Buffer is already released");
        }
        throw new IndexOutOfBoundsException(index + " is not in range [0; " + h() + ')');
    }

    private final char[] m() {
        if (this.x == 0) {
            return e();
        }
        char[] cArr = this.d;
        if (cArr != null) {
            return cArr;
        }
        k.n();
        return cArr;
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.util.List<char[]>, java.util.ArrayList] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final char[] e() {
        /*
            r5 = this;
            io.ktor.utils.io.pool.d<char[]> r0 = r5.z
            java.lang.Object r0 = r0.p0()
            char[] r0 = (char[]) r0
            char[] r1 = r5.d
            r5.d = r0
            int r2 = r0.length
            r5.x = r2
            r2 = 0
            r5.q = r2
            if (r1 == 0) goto L_0x002a
            java.util.List<char[]> r2 = r5.c
            if (r2 == 0) goto L_0x0019
            goto L_0x0027
        L_0x0019:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = r2
            r4 = 0
            r5.c = r3
            r3.add(r1)
        L_0x0027:
            r2.add(r0)
        L_0x002a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.internals.b.e():char[]");
    }

    /* access modifiers changed from: private */
    public final boolean n(int start, CharSequence other, int otherStart, int length) {
        for (int i = 0; i < length; i++) {
            if (j(start + i) != other.charAt(otherStart + i)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final int l(int start, int end) {
        int hc = 0;
        for (int i = start; i < end; i++) {
            hc = (hc * 31) + j(i);
        }
        return hc;
    }

    private final int h() {
        char[] cArr = this.d;
        if (cArr == null) {
            k.n();
        }
        return cArr.length - this.x;
    }
}
