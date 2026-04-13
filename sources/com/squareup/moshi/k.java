package com.squareup.moshi;

import com.google.maps.android.BuildConfig;
import com.squareup.moshi.i;
import com.tencent.bugly.Bugly;
import io.netty.util.internal.StringUtil;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import javax.annotation.Nullable;
import okio.c;
import okio.e;
import okio.f;

/* compiled from: JsonUtf8Reader */
public final class k extends i {
    private static final f a1 = f.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");
    private static final f a2 = f.encodeUtf8("*/");
    private static final f p0 = f.encodeUtf8("\"\\");
    private static final f p1 = f.encodeUtf8("\n\r");
    private static final f z = f.encodeUtf8("'\\");
    private int A4;
    @Nullable
    private String B4;
    @Nullable
    private m C4;
    private final e p2;
    private final c p3;
    private int p4 = 0;
    private long z4;

    k(e source) {
        if (source != null) {
            this.p2 = source;
            this.p3 = source.getBuffer();
            I(6);
            return;
        }
        throw new NullPointerException("source == null");
    }

    k(k copyFrom) {
        super(copyFrom);
        e sourcePeek = copyFrom.p2.peek();
        this.p2 = sourcePeek;
        this.p3 = sourcePeek.getBuffer();
        this.p4 = copyFrom.p4;
        this.z4 = copyFrom.z4;
        this.A4 = copyFrom.A4;
        this.B4 = copyFrom.B4;
        try {
            sourcePeek.k0(copyFrom.p3.e1());
        } catch (IOException e) {
            throw new AssertionError();
        }
    }

    public void a() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 3) {
            I(1);
            this.q[this.c - 1] = 0;
            this.p4 = 0;
            return;
        }
        throw new JsonDataException("Expected BEGIN_ARRAY but was " + x() + " at path " + getPath());
    }

    public void g() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 4) {
            int i = this.c - 1;
            this.c = i;
            int[] iArr = this.q;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
            this.p4 = 0;
            return;
        }
        throw new JsonDataException("Expected END_ARRAY but was " + x() + " at path " + getPath());
    }

    public void c() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 1) {
            I(3);
            this.p4 = 0;
            return;
        }
        throw new JsonDataException("Expected BEGIN_OBJECT but was " + x() + " at path " + getPath());
    }

    public void i() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 2) {
            int i = this.c - 1;
            this.c = i;
            this.f[i] = null;
            int[] iArr = this.q;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
            this.p4 = 0;
            return;
        }
        throw new JsonDataException("Expected END_OBJECT but was " + x() + " at path " + getPath());
    }

    public boolean l() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        return (p == 2 || p == 4 || p == 18) ? false : true;
    }

    public i.b x() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        switch (p) {
            case 1:
                return i.b.BEGIN_OBJECT;
            case 2:
                return i.b.END_OBJECT;
            case 3:
                return i.b.BEGIN_ARRAY;
            case 4:
                return i.b.END_ARRAY;
            case 5:
            case 6:
                return i.b.BOOLEAN;
            case 7:
                return i.b.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return i.b.STRING;
            case 12:
            case 13:
            case 14:
            case 15:
                return i.b.NAME;
            case 16:
            case 17:
                return i.b.NUMBER;
            case 18:
                return i.b.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    private int a1() {
        int[] iArr = this.d;
        int i = this.c;
        int peekStack = iArr[i - 1];
        if (peekStack == 1) {
            iArr[i - 1] = 2;
        } else if (peekStack == 2) {
            int c = f1(true);
            this.p3.readByte();
            switch (c) {
                case 44:
                    break;
                case 59:
                    P0();
                    break;
                case 93:
                    this.p4 = 4;
                    return 4;
                default:
                    throw F0("Unterminated array");
            }
        } else if (peekStack == 3 || peekStack == 5) {
            iArr[i - 1] = 4;
            if (peekStack == 5) {
                int c2 = f1(true);
                this.p3.readByte();
                switch (c2) {
                    case 44:
                        break;
                    case 59:
                        P0();
                        break;
                    case 125:
                        this.p4 = 2;
                        return 2;
                    default:
                        throw F0("Unterminated object");
                }
            }
            int c3 = f1(true);
            switch (c3) {
                case 34:
                    this.p3.readByte();
                    this.p4 = 13;
                    return 13;
                case 39:
                    this.p3.readByte();
                    P0();
                    this.p4 = 12;
                    return 12;
                case 125:
                    if (peekStack != 5) {
                        this.p3.readByte();
                        this.p4 = 2;
                        return 2;
                    }
                    throw F0("Expected name");
                default:
                    P0();
                    if (d1((char) c3)) {
                        this.p4 = 14;
                        return 14;
                    }
                    throw F0("Expected name");
            }
        } else if (peekStack == 4) {
            iArr[i - 1] = 5;
            int c4 = f1(true);
            this.p3.readByte();
            switch (c4) {
                case 58:
                    break;
                case 61:
                    P0();
                    if (this.p2.request(1) && this.p3.n(0) == 62) {
                        this.p3.readByte();
                        break;
                    }
                default:
                    throw F0("Expected ':'");
            }
        } else if (peekStack == 6) {
            iArr[i - 1] = 7;
        } else if (peekStack == 7) {
            if (f1(false) == -1) {
                this.p4 = 18;
                return 18;
            }
            P0();
        } else if (peekStack == 9) {
            this.C4.a();
            throw null;
        } else if (peekStack == 8) {
            throw new IllegalStateException("JsonReader is closed");
        }
        switch (f1(true)) {
            case 34:
                this.p3.readByte();
                this.p4 = 9;
                return 9;
            case 39:
                P0();
                this.p3.readByte();
                this.p4 = 8;
                return 8;
            case 44:
            case 59:
                break;
            case 91:
                this.p3.readByte();
                this.p4 = 3;
                return 3;
            case 93:
                if (peekStack == 1) {
                    this.p3.readByte();
                    this.p4 = 4;
                    return 4;
                }
                break;
            case 123:
                this.p3.readByte();
                this.p4 = 1;
                return 1;
            default:
                int result = i1();
                if (result != 0) {
                    return result;
                }
                int result2 = j1();
                if (result2 != 0) {
                    return result2;
                }
                if (d1(this.p3.n(0))) {
                    P0();
                    this.p4 = 10;
                    return 10;
                }
                throw F0("Expected value");
        }
        if (peekStack == 1 || peekStack == 2) {
            P0();
            this.p4 = 7;
            return 7;
        }
        throw F0("Unexpected value");
    }

    private int i1() {
        int peeking;
        String keywordUpper;
        String keyword;
        byte c = this.p3.n(0);
        if (c == 116 || c == 84) {
            keyword = "true";
            keywordUpper = "TRUE";
            peeking = 5;
        } else if (c == 102 || c == 70) {
            keyword = Bugly.SDK_IS_DEV;
            keywordUpper = "FALSE";
            peeking = 6;
        } else if (c != 110 && c != 78) {
            return 0;
        } else {
            keyword = BuildConfig.TRAVIS;
            keywordUpper = "NULL";
            peeking = 7;
        }
        int length = keyword.length();
        for (int i = 1; i < length; i++) {
            if (!this.p2.request((long) (i + 1))) {
                return 0;
            }
            byte c2 = this.p3.n((long) i);
            if (c2 != keyword.charAt(i) && c2 != keywordUpper.charAt(i)) {
                return 0;
            }
        }
        if (this.p2.request((long) (length + 1)) && d1(this.p3.n((long) length))) {
            return 0;
        }
        this.p3.skip((long) length);
        this.p4 = peeking;
        return peeking;
    }

    private int j1() {
        byte c;
        long value = 0;
        boolean negative = false;
        boolean fitsInLong = true;
        int last = 0;
        int i = 0;
        while (true) {
            boolean z2 = false;
            if (this.p2.request((long) (i + 1))) {
                c = this.p3.n((long) i);
                switch (c) {
                    case 43:
                        if (last != 5) {
                            return 0;
                        }
                        last = 6;
                        continue;
                    case 45:
                        if (last == 0) {
                            negative = true;
                            last = 1;
                            continue;
                        } else if (last == 5) {
                            last = 6;
                            break;
                        } else {
                            return 0;
                        }
                    case 46:
                        if (last != 2) {
                            return 0;
                        }
                        last = 3;
                        continue;
                    case 69:
                    case 101:
                        if (last != 2 && last != 4) {
                            return 0;
                        }
                        last = 5;
                        continue;
                    default:
                        if (c >= 48 && c <= 57) {
                            if (last != 1 && last != 0) {
                                if (last != 2) {
                                    if (last != 3) {
                                        if (last != 5 && last != 6) {
                                            break;
                                        } else {
                                            last = 7;
                                            break;
                                        }
                                    } else {
                                        last = 4;
                                        break;
                                    }
                                } else if (value != 0) {
                                    long newValue = (10 * value) - ((long) (c - 48));
                                    if (value > -922337203685477580L || (value == -922337203685477580L && newValue < value)) {
                                        z2 = true;
                                    }
                                    fitsInLong &= z2;
                                    value = newValue;
                                    break;
                                } else {
                                    return 0;
                                }
                            } else {
                                value = (long) (-(c - 48));
                                last = 2;
                                continue;
                            }
                        } else {
                            break;
                        }
                }
            }
            i++;
        }
        if (d1(c)) {
            return 0;
        }
        if (last == 2 && fitsInLong && ((value != Long.MIN_VALUE || negative) && (value != 0 || !negative))) {
            this.z4 = negative ? value : -value;
            this.p3.skip((long) i);
            this.p4 = 16;
            return 16;
        } else if (last != 2 && last != 4 && last != 7) {
            return 0;
        } else {
            this.A4 = i;
            this.p4 = 17;
            return 17;
        }
    }

    private boolean d1(int c) {
        switch (c) {
            case 9:
            case 10:
            case 12:
            case 13:
            case 32:
            case 44:
            case 58:
            case 91:
            case 93:
            case 123:
            case 125:
                return false;
            case 35:
            case 47:
            case 59:
            case 61:
            case 92:
                P0();
                return false;
            default:
                return true;
        }
    }

    public String e1() {
        String result;
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 14) {
            result = h1();
        } else if (p == 13) {
            result = g1(p0);
        } else if (p == 12) {
            result = g1(z);
        } else if (p == 15) {
            result = this.B4;
            this.B4 = null;
        } else {
            throw new JsonDataException("Expected a name but was " + x() + " at path " + getPath());
        }
        this.p4 = 0;
        this.f[this.c - 1] = result;
        return result;
    }

    public int J(i.a options) {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p < 12 || p > 15) {
            return -1;
        }
        if (p == 15) {
            return b1(this.B4, options);
        }
        int result = this.p2.Z0(options.b);
        if (result != -1) {
            this.p4 = 0;
            this.f[this.c - 1] = options.a[result];
            return result;
        }
        String lastPathName = this.f[this.c - 1];
        String nextName = e1();
        int result2 = b1(nextName, options);
        if (result2 == -1) {
            this.p4 = 15;
            this.B4 = nextName;
            this.f[this.c - 1] = lastPathName;
        }
        return result2;
    }

    public void o0() {
        if (!this.y) {
            int p = this.p4;
            if (p == 0) {
                p = a1();
            }
            if (p == 14) {
                o1();
            } else if (p == 13) {
                l1(p0);
            } else if (p == 12) {
                l1(z);
            } else if (p != 15) {
                throw new JsonDataException("Expected a name but was " + x() + " at path " + getPath());
            }
            this.p4 = 0;
            this.f[this.c - 1] = BuildConfig.TRAVIS;
            return;
        }
        i.b peeked = x();
        e1();
        throw new JsonDataException("Cannot skip unexpected " + peeked + " at " + getPath());
    }

    private int b1(String name, i.a options) {
        int size = options.a.length;
        for (int i = 0; i < size; i++) {
            if (name.equals(options.a[i])) {
                this.p4 = 0;
                this.f[this.c - 1] = name;
                return i;
            }
        }
        return -1;
    }

    public String u() {
        String result;
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 10) {
            result = h1();
        } else if (p == 9) {
            result = g1(p0);
        } else if (p == 8) {
            result = g1(z);
        } else if (p == 11) {
            result = this.B4;
            this.B4 = null;
        } else if (p == 16) {
            result = Long.toString(this.z4);
        } else if (p == 17) {
            result = this.p3.b1((long) this.A4);
        } else {
            throw new JsonDataException("Expected a string but was " + x() + " at path " + getPath());
        }
        this.p4 = 0;
        int[] iArr = this.q;
        int i = this.c - 1;
        iArr[i] = iArr[i] + 1;
        return result;
    }

    public int P(i.a options) {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p < 8 || p > 11) {
            return -1;
        }
        if (p == 11) {
            return c1(this.B4, options);
        }
        int result = this.p2.Z0(options.b);
        if (result != -1) {
            this.p4 = 0;
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return result;
        }
        String nextString = u();
        int result2 = c1(nextString, options);
        if (result2 == -1) {
            this.p4 = 11;
            this.B4 = nextString;
            int[] iArr2 = this.q;
            int i2 = this.c - 1;
            iArr2[i2] = iArr2[i2] - 1;
        }
        return result2;
    }

    private int c1(String string, i.a options) {
        int size = options.a.length;
        for (int i = 0; i < size; i++) {
            if (string.equals(options.a[i])) {
                this.p4 = 0;
                int[] iArr = this.q;
                int i2 = this.c - 1;
                iArr[i2] = iArr[i2] + 1;
                return i;
            }
        }
        return -1;
    }

    public boolean n() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 5) {
            this.p4 = 0;
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        } else if (p == 6) {
            this.p4 = 0;
            int[] iArr2 = this.q;
            int i2 = this.c - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        } else {
            throw new JsonDataException("Expected a boolean but was " + x() + " at path " + getPath());
        }
    }

    @Nullable
    public <T> T t() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 7) {
            this.p4 = 0;
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return null;
        }
        throw new JsonDataException("Expected null but was " + x() + " at path " + getPath());
    }

    public double o() {
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 16) {
            this.p4 = 0;
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return (double) this.z4;
        }
        if (p == 17) {
            this.B4 = this.p3.b1((long) this.A4);
        } else if (p == 9) {
            this.B4 = g1(p0);
        } else if (p == 8) {
            this.B4 = g1(z);
        } else if (p == 10) {
            this.B4 = h1();
        } else if (p != 11) {
            throw new JsonDataException("Expected a double but was " + x() + " at path " + getPath());
        }
        this.p4 = 11;
        try {
            double result = Double.parseDouble(this.B4);
            if (this.x || (!Double.isNaN(result) && !Double.isInfinite(result))) {
                this.B4 = null;
                this.p4 = 0;
                int[] iArr2 = this.q;
                int i2 = this.c - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result;
            }
            throw new JsonEncodingException("JSON forbids NaN and infinities: " + result + " at path " + getPath());
        } catch (NumberFormatException e) {
            throw new JsonDataException("Expected a double but was " + this.B4 + " at path " + getPath());
        }
    }

    public long s() {
        String str;
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 16) {
            this.p4 = 0;
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this.z4;
        }
        if (p == 17) {
            this.B4 = this.p3.b1((long) this.A4);
        } else if (p == 9 || p == 8) {
            if (p == 9) {
                str = g1(p0);
            } else {
                str = g1(z);
            }
            this.B4 = str;
            try {
                long result = Long.parseLong(str);
                this.p4 = 0;
                int[] iArr2 = this.q;
                int i2 = this.c - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result;
            } catch (NumberFormatException e) {
            }
        } else if (p != 11) {
            throw new JsonDataException("Expected a long but was " + x() + " at path " + getPath());
        }
        this.p4 = 11;
        try {
            long result2 = new BigDecimal(this.B4).longValueExact();
            this.B4 = null;
            this.p4 = 0;
            int[] iArr3 = this.q;
            int i3 = this.c - 1;
            iArr3[i3] = iArr3[i3] + 1;
            return result2;
        } catch (ArithmeticException | NumberFormatException e2) {
            throw new JsonDataException("Expected a long but was " + this.B4 + " at path " + getPath());
        }
    }

    private String g1(f runTerminator) {
        StringBuilder builder = null;
        while (true) {
            long index = this.p2.N(runTerminator);
            if (index == -1) {
                throw F0("Unterminated string");
            } else if (this.p3.n(index) == 92) {
                if (builder == null) {
                    builder = new StringBuilder();
                }
                builder.append(this.p3.b1(index));
                this.p3.readByte();
                builder.append(k1());
            } else if (builder == null) {
                String result = this.p3.b1(index);
                this.p3.readByte();
                return result;
            } else {
                builder.append(this.p3.b1(index));
                this.p3.readByte();
                return builder.toString();
            }
        }
    }

    private String h1() {
        long i = this.p2.N(a1);
        return i != -1 ? this.p3.b1(i) : this.p3.a1();
    }

    private void l1(f runTerminator) {
        while (true) {
            long index = this.p2.N(runTerminator);
            if (index == -1) {
                throw F0("Unterminated string");
            } else if (this.p3.n(index) == 92) {
                this.p3.skip(1 + index);
                k1();
            } else {
                this.p3.skip(1 + index);
                return;
            }
        }
    }

    private void o1() {
        long i = this.p2.N(a1);
        c cVar = this.p3;
        cVar.skip(i != -1 ? i : cVar.e1());
    }

    public int r() {
        String str;
        int p = this.p4;
        if (p == 0) {
            p = a1();
        }
        if (p == 16) {
            long j = this.z4;
            int result = (int) j;
            if (j == ((long) result)) {
                this.p4 = 0;
                int[] iArr = this.q;
                int i = this.c - 1;
                iArr[i] = iArr[i] + 1;
                return result;
            }
            throw new JsonDataException("Expected an int but was " + this.z4 + " at path " + getPath());
        }
        if (p == 17) {
            this.B4 = this.p3.b1((long) this.A4);
        } else if (p == 9 || p == 8) {
            if (p == 9) {
                str = g1(p0);
            } else {
                str = g1(z);
            }
            this.B4 = str;
            try {
                int result2 = Integer.parseInt(str);
                this.p4 = 0;
                int[] iArr2 = this.q;
                int i2 = this.c - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result2;
            } catch (NumberFormatException e) {
            }
        } else if (p != 11) {
            throw new JsonDataException("Expected an int but was " + x() + " at path " + getPath());
        }
        this.p4 = 11;
        try {
            double asDouble = Double.parseDouble(this.B4);
            int result3 = (int) asDouble;
            if (((double) result3) == asDouble) {
                this.B4 = null;
                this.p4 = 0;
                int[] iArr3 = this.q;
                int i3 = this.c - 1;
                iArr3[i3] = iArr3[i3] + 1;
                return result3;
            }
            throw new JsonDataException("Expected an int but was " + this.B4 + " at path " + getPath());
        } catch (NumberFormatException e2) {
            throw new JsonDataException("Expected an int but was " + this.B4 + " at path " + getPath());
        }
    }

    public void close() {
        this.p4 = 0;
        this.d[0] = 8;
        this.c = 1;
        this.p3.clear();
        this.p2.close();
    }

    public void u0() {
        if (!this.y) {
            int count = 0;
            do {
                int p = this.p4;
                if (p == 0) {
                    p = a1();
                }
                if (p == 3) {
                    I(1);
                    count++;
                } else if (p == 1) {
                    I(3);
                    count++;
                } else if (p == 4) {
                    count--;
                    if (count >= 0) {
                        this.c--;
                    } else {
                        throw new JsonDataException("Expected a value but was " + x() + " at path " + getPath());
                    }
                } else if (p == 2) {
                    count--;
                    if (count >= 0) {
                        this.c--;
                    } else {
                        throw new JsonDataException("Expected a value but was " + x() + " at path " + getPath());
                    }
                } else if (p == 14 || p == 10) {
                    o1();
                } else if (p == 9 || p == 13) {
                    l1(p0);
                } else if (p == 8 || p == 12) {
                    l1(z);
                } else if (p == 17) {
                    this.p3.skip((long) this.A4);
                } else if (p == 18) {
                    throw new JsonDataException("Expected a value but was " + x() + " at path " + getPath());
                }
                this.p4 = 0;
            } while (count != 0);
            int[] iArr = this.q;
            int i = this.c;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
            this.f[i - 1] = BuildConfig.TRAVIS;
            return;
        }
        throw new JsonDataException("Cannot skip unexpected " + x() + " at " + getPath());
    }

    private int f1(boolean throwOnEof) {
        int c = 0;
        while (this.p2.request((long) (c + 1))) {
            int p = c + 1;
            int c2 = this.p3.n((long) c);
            if (c2 == 10 || c2 == 32 || c2 == 13 || c2 == 9) {
                c = p;
            } else {
                this.p3.skip((long) (p - 1));
                if (c2 == 47) {
                    if (!this.p2.request(2)) {
                        return c2;
                    }
                    P0();
                    switch (this.p3.n(1)) {
                        case 42:
                            this.p3.readByte();
                            this.p3.readByte();
                            if (m1()) {
                                c = 0;
                                break;
                            } else {
                                throw F0("Unterminated comment");
                            }
                        case 47:
                            this.p3.readByte();
                            this.p3.readByte();
                            n1();
                            c = 0;
                            break;
                        default:
                            return c2;
                    }
                } else if (c2 != 35) {
                    return c2;
                } else {
                    P0();
                    n1();
                    c = 0;
                }
            }
        }
        if (!throwOnEof) {
            return -1;
        }
        throw new EOFException("End of input");
    }

    private void P0() {
        if (!this.x) {
            throw F0("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void n1() {
        long index = this.p2.N(p1);
        c cVar = this.p3;
        cVar.skip(index != -1 ? 1 + index : cVar.e1());
    }

    private boolean m1() {
        e eVar = this.p2;
        f fVar = a2;
        long index = eVar.F(fVar);
        boolean found = index != -1;
        c cVar = this.p3;
        cVar.skip(found ? ((long) fVar.size()) + index : cVar.e1());
        return found;
    }

    public i z() {
        return new k(this);
    }

    public String toString() {
        return "JsonReader(" + this.p2 + ")";
    }

    private char k1() {
        int i;
        if (this.p2.request(1)) {
            byte escaped = this.p3.readByte();
            switch (escaped) {
                case 10:
                case 34:
                case 39:
                case 47:
                case 92:
                    return (char) escaped;
                case 98:
                    return 8;
                case 102:
                    return 12;
                case 110:
                    return 10;
                case 114:
                    return StringUtil.CARRIAGE_RETURN;
                case 116:
                    return 9;
                case 117:
                    if (this.p2.request(4)) {
                        char result = 0;
                        int end = 0 + 4;
                        for (int i2 = 0; i2 < end; i2++) {
                            byte c = this.p3.n((long) i2);
                            char result2 = (char) (result << 4);
                            if (c >= 48 && c <= 57) {
                                i = c - 48;
                            } else if (c >= 97 && c <= 102) {
                                i = (c - 97) + 10;
                            } else if (c < 65 || c > 70) {
                                throw F0("\\u" + this.p3.b1(4));
                            } else {
                                i = (c - 65) + 10;
                            }
                            result = (char) (i + result2);
                        }
                        this.p3.skip(4);
                        return result;
                    }
                    throw new EOFException("Unterminated escape sequence at path " + getPath());
                default:
                    if (this.x) {
                        return (char) escaped;
                    }
                    throw F0("Invalid escape sequence: \\" + ((char) escaped));
            }
        } else {
            throw F0("Unterminated escape sequence");
        }
    }

    public void E() {
        if (l()) {
            this.B4 = e1();
            this.p4 = 11;
        }
    }
}
