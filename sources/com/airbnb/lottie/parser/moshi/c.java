package com.airbnb.lottie.parser.moshi;

import androidx.annotation.Nullable;
import com.airbnb.lottie.parser.moshi.a;
import com.google.maps.android.BuildConfig;
import com.tencent.bugly.Bugly;
import io.netty.util.internal.StringUtil;
import java.io.EOFException;
import okio.e;
import okio.f;

/* compiled from: JsonUtf8Reader */
public final class c extends a {
    private static final f a1 = f.encodeUtf8("\"\\");
    private static final f a2 = f.encodeUtf8("\n\r");
    private static final f p0 = f.encodeUtf8("'\\");
    private static final f p1 = f.encodeUtf8("{}[]:, \n\t\r\f/\\;#=");
    private static final f p2 = f.encodeUtf8("*/");
    private long A4;
    private int B4;
    @Nullable
    private String C4;
    private final e p3;
    private final okio.c p4;
    private int z4 = 0;

    c(e source) {
        if (source != null) {
            this.p3 = source;
            this.p4 = source.buffer();
            v(6);
            return;
        }
        throw new NullPointerException("source == null");
    }

    public void c() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 3) {
            v(1);
            this.x[this.d - 1] = 0;
            this.z4 = 0;
            return;
        }
        throw new JsonDataException("Expected BEGIN_ARRAY but was " + u() + " at path " + getPath());
    }

    public void i() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 4) {
            int i = this.d - 1;
            this.d = i;
            int[] iArr = this.x;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
            this.z4 = 0;
            return;
        }
        throw new JsonDataException("Expected END_ARRAY but was " + u() + " at path " + getPath());
    }

    public void g() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 1) {
            v(3);
            this.z4 = 0;
            return;
        }
        throw new JsonDataException("Expected BEGIN_OBJECT but was " + u() + " at path " + getPath());
    }

    public void j() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 2) {
            int i = this.d - 1;
            this.d = i;
            this.q[i] = null;
            int[] iArr = this.x;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
            this.z4 = 0;
            return;
        }
        throw new JsonDataException("Expected END_OBJECT but was " + u() + " at path " + getPath());
    }

    public boolean l() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        return (p == 2 || p == 4 || p == 18) ? false : true;
    }

    public a.b u() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        switch (p) {
            case 1:
                return a.b.BEGIN_OBJECT;
            case 2:
                return a.b.END_OBJECT;
            case 3:
                return a.b.BEGIN_ARRAY;
            case 4:
                return a.b.END_ARRAY;
            case 5:
            case 6:
                return a.b.BOOLEAN;
            case 7:
                return a.b.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return a.b.STRING;
            case 12:
            case 13:
            case 14:
            case 15:
                return a.b.NAME;
            case 16:
            case 17:
                return a.b.NUMBER;
            case 18:
                return a.b.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    private int T() {
        int[] iArr = this.f;
        int i = this.d;
        int peekStack = iArr[i - 1];
        if (peekStack == 1) {
            iArr[i - 1] = 2;
        } else if (peekStack == 2) {
            int c = u0(true);
            this.p4.readByte();
            switch (c) {
                case 44:
                    break;
                case 59:
                    P();
                    break;
                case 93:
                    this.z4 = 4;
                    return 4;
                default:
                    throw J("Unterminated array");
            }
        } else if (peekStack == 3 || peekStack == 5) {
            iArr[i - 1] = 4;
            if (peekStack == 5) {
                int c2 = u0(true);
                this.p4.readByte();
                switch (c2) {
                    case 44:
                        break;
                    case 59:
                        P();
                        break;
                    case 125:
                        this.z4 = 2;
                        return 2;
                    default:
                        throw J("Unterminated object");
                }
            }
            int c3 = u0(true);
            switch (c3) {
                case 34:
                    this.p4.readByte();
                    this.z4 = 13;
                    return 13;
                case 39:
                    this.p4.readByte();
                    P();
                    this.z4 = 12;
                    return 12;
                case 125:
                    if (peekStack != 5) {
                        this.p4.readByte();
                        this.z4 = 2;
                        return 2;
                    }
                    throw J("Expected name");
                default:
                    P();
                    if (o0((char) c3)) {
                        this.z4 = 14;
                        return 14;
                    }
                    throw J("Expected name");
            }
        } else if (peekStack == 4) {
            iArr[i - 1] = 5;
            int c4 = u0(true);
            this.p4.readByte();
            switch (c4) {
                case 58:
                    break;
                case 61:
                    P();
                    if (this.p3.request(1) && this.p4.n(0) == 62) {
                        this.p4.readByte();
                        break;
                    }
                default:
                    throw J("Expected ':'");
            }
        } else if (peekStack == 6) {
            iArr[i - 1] = 7;
        } else if (peekStack == 7) {
            if (u0(false) == -1) {
                this.z4 = 18;
                return 18;
            }
            P();
        } else if (peekStack == 8) {
            throw new IllegalStateException("JsonReader is closed");
        }
        switch (u0(true)) {
            case 34:
                this.p4.readByte();
                this.z4 = 9;
                return 9;
            case 39:
                P();
                this.p4.readByte();
                this.z4 = 8;
                return 8;
            case 44:
            case 59:
                break;
            case 91:
                this.p4.readByte();
                this.z4 = 3;
                return 3;
            case 93:
                if (peekStack == 1) {
                    this.p4.readByte();
                    this.z4 = 4;
                    return 4;
                }
                break;
            case 123:
                this.p4.readByte();
                this.z4 = 1;
                return 1;
            default:
                int result = a1();
                if (result != 0) {
                    return result;
                }
                int result2 = b1();
                if (result2 != 0) {
                    return result2;
                }
                if (o0(this.p4.n(0))) {
                    P();
                    this.z4 = 10;
                    return 10;
                }
                throw J("Expected value");
        }
        if (peekStack == 1 || peekStack == 2) {
            P();
            this.z4 = 7;
            return 7;
        }
        throw J("Unexpected value");
    }

    private int a1() {
        int peeking;
        String keywordUpper;
        String keyword;
        byte c = this.p4.n(0);
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
            if (!this.p3.request((long) (i + 1))) {
                return 0;
            }
            byte c2 = this.p4.n((long) i);
            if (c2 != keyword.charAt(i) && c2 != keywordUpper.charAt(i)) {
                return 0;
            }
        }
        if (this.p3.request((long) (length + 1)) && o0(this.p4.n((long) length))) {
            return 0;
        }
        this.p4.skip((long) length);
        this.z4 = peeking;
        return peeking;
    }

    private int b1() {
        byte c;
        long value = 0;
        boolean negative = false;
        boolean fitsInLong = true;
        int last = 0;
        int i = 0;
        while (true) {
            boolean z = false;
            if (this.p3.request((long) (i + 1))) {
                c = this.p4.n((long) i);
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
                                        z = true;
                                    }
                                    fitsInLong &= z;
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
        if (o0(c)) {
            return 0;
        }
        if (last == 2 && fitsInLong && ((value != Long.MIN_VALUE || negative) && (value != 0 || !negative))) {
            this.A4 = negative ? value : -value;
            this.p4.skip((long) i);
            this.z4 = 16;
            return 16;
        } else if (last != 2 && last != 4 && last != 7) {
            return 0;
        } else {
            this.B4 = i;
            this.z4 = 17;
            return 17;
        }
    }

    private boolean o0(int c) {
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
                P();
                return false;
            default:
                return true;
        }
    }

    public String r() {
        String result;
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 14) {
            result = P0();
        } else if (p == 13) {
            result = F0(a1);
        } else if (p == 12) {
            result = F0(p0);
        } else if (p == 15) {
            result = this.C4;
        } else {
            throw new JsonDataException("Expected a name but was " + u() + " at path " + getPath());
        }
        this.z4 = 0;
        this.q[this.d - 1] = result;
        return result;
    }

    public int x(a.C0011a options) {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p < 12 || p > 15) {
            return -1;
        }
        if (p == 15) {
            return W(this.C4, options);
        }
        int result = this.p3.Z0(options.b);
        if (result != -1) {
            this.z4 = 0;
            this.q[this.d - 1] = options.a[result];
            return result;
        }
        String lastPathName = this.q[this.d - 1];
        String nextName = r();
        int result2 = W(nextName, options);
        if (result2 == -1) {
            this.z4 = 15;
            this.C4 = nextName;
            this.q[this.d - 1] = lastPathName;
        }
        return result2;
    }

    public void z() {
        if (!this.z) {
            int p = this.z4;
            if (p == 0) {
                p = T();
            }
            if (p == 14) {
                g1();
            } else if (p == 13) {
                d1(a1);
            } else if (p == 12) {
                d1(p0);
            } else if (p != 15) {
                throw new JsonDataException("Expected a name but was " + u() + " at path " + getPath());
            }
            this.z4 = 0;
            this.q[this.d - 1] = BuildConfig.TRAVIS;
            return;
        }
        throw new JsonDataException("Cannot skip unexpected " + u() + " at " + getPath());
    }

    private int W(String name, a.C0011a options) {
        int size = options.a.length;
        for (int i = 0; i < size; i++) {
            if (name.equals(options.a[i])) {
                this.z4 = 0;
                this.q[this.d - 1] = name;
                return i;
            }
        }
        return -1;
    }

    public String s() {
        String result;
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 10) {
            result = P0();
        } else if (p == 9) {
            result = F0(a1);
        } else if (p == 8) {
            result = F0(p0);
        } else if (p == 11) {
            result = this.C4;
            this.C4 = null;
        } else if (p == 16) {
            result = Long.toString(this.A4);
        } else if (p == 17) {
            result = this.p4.b1((long) this.B4);
        } else {
            throw new JsonDataException("Expected a string but was " + u() + " at path " + getPath());
        }
        this.z4 = 0;
        int[] iArr = this.x;
        int i = this.d - 1;
        iArr[i] = iArr[i] + 1;
        return result;
    }

    public boolean m() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 5) {
            this.z4 = 0;
            int[] iArr = this.x;
            int i = this.d - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        } else if (p == 6) {
            this.z4 = 0;
            int[] iArr2 = this.x;
            int i2 = this.d - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        } else {
            throw new JsonDataException("Expected a boolean but was " + u() + " at path " + getPath());
        }
    }

    public double n() {
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 16) {
            this.z4 = 0;
            int[] iArr = this.x;
            int i = this.d - 1;
            iArr[i] = iArr[i] + 1;
            return (double) this.A4;
        }
        if (p == 17) {
            this.C4 = this.p4.b1((long) this.B4);
        } else if (p == 9) {
            this.C4 = F0(a1);
        } else if (p == 8) {
            this.C4 = F0(p0);
        } else if (p == 10) {
            this.C4 = P0();
        } else if (p != 11) {
            throw new JsonDataException("Expected a double but was " + u() + " at path " + getPath());
        }
        this.z4 = 11;
        try {
            double result = Double.parseDouble(this.C4);
            if (this.y || (!Double.isNaN(result) && !Double.isInfinite(result))) {
                this.C4 = null;
                this.z4 = 0;
                int[] iArr2 = this.x;
                int i2 = this.d - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result;
            }
            throw new JsonEncodingException("JSON forbids NaN and infinities: " + result + " at path " + getPath());
        } catch (NumberFormatException e) {
            throw new JsonDataException("Expected a double but was " + this.C4 + " at path " + getPath());
        }
    }

    private String F0(f runTerminator) {
        StringBuilder builder = null;
        while (true) {
            long index = this.p3.N(runTerminator);
            if (index == -1) {
                throw J("Unterminated string");
            } else if (this.p4.n(index) == 92) {
                if (builder == null) {
                    builder = new StringBuilder();
                }
                builder.append(this.p4.b1(index));
                this.p4.readByte();
                builder.append(c1());
            } else if (builder == null) {
                String result = this.p4.b1(index);
                this.p4.readByte();
                return result;
            } else {
                builder.append(this.p4.b1(index));
                this.p4.readByte();
                return builder.toString();
            }
        }
    }

    private String P0() {
        long i = this.p3.N(p1);
        return i != -1 ? this.p4.b1(i) : this.p4.a1();
    }

    private void d1(f runTerminator) {
        while (true) {
            long index = this.p3.N(runTerminator);
            if (index == -1) {
                throw J("Unterminated string");
            } else if (this.p4.n(index) == 92) {
                this.p4.skip(1 + index);
                c1();
            } else {
                this.p4.skip(1 + index);
                return;
            }
        }
    }

    private void g1() {
        long i = this.p3.N(p1);
        okio.c cVar = this.p4;
        cVar.skip(i != -1 ? i : cVar.e1());
    }

    public int o() {
        String str;
        int p = this.z4;
        if (p == 0) {
            p = T();
        }
        if (p == 16) {
            long j = this.A4;
            int result = (int) j;
            if (j == ((long) result)) {
                this.z4 = 0;
                int[] iArr = this.x;
                int i = this.d - 1;
                iArr[i] = iArr[i] + 1;
                return result;
            }
            throw new JsonDataException("Expected an int but was " + this.A4 + " at path " + getPath());
        }
        if (p == 17) {
            this.C4 = this.p4.b1((long) this.B4);
        } else if (p == 9 || p == 8) {
            if (p == 9) {
                str = F0(a1);
            } else {
                str = F0(p0);
            }
            this.C4 = str;
            try {
                int result2 = Integer.parseInt(str);
                this.z4 = 0;
                int[] iArr2 = this.x;
                int i2 = this.d - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result2;
            } catch (NumberFormatException e) {
            }
        } else if (p != 11) {
            throw new JsonDataException("Expected an int but was " + u() + " at path " + getPath());
        }
        this.z4 = 11;
        try {
            double asDouble = Double.parseDouble(this.C4);
            int result3 = (int) asDouble;
            if (((double) result3) == asDouble) {
                this.C4 = null;
                this.z4 = 0;
                int[] iArr3 = this.x;
                int i3 = this.d - 1;
                iArr3[i3] = iArr3[i3] + 1;
                return result3;
            }
            throw new JsonDataException("Expected an int but was " + this.C4 + " at path " + getPath());
        } catch (NumberFormatException e2) {
            throw new JsonDataException("Expected an int but was " + this.C4 + " at path " + getPath());
        }
    }

    public void close() {
        this.z4 = 0;
        this.f[0] = 8;
        this.d = 1;
        this.p4.clear();
        this.p3.close();
    }

    public void E() {
        if (!this.z) {
            int count = 0;
            do {
                int p = this.z4;
                if (p == 0) {
                    p = T();
                }
                if (p == 3) {
                    v(1);
                    count++;
                } else if (p == 1) {
                    v(3);
                    count++;
                } else if (p == 4) {
                    count--;
                    if (count >= 0) {
                        this.d--;
                    } else {
                        throw new JsonDataException("Expected a value but was " + u() + " at path " + getPath());
                    }
                } else if (p == 2) {
                    count--;
                    if (count >= 0) {
                        this.d--;
                    } else {
                        throw new JsonDataException("Expected a value but was " + u() + " at path " + getPath());
                    }
                } else if (p == 14 || p == 10) {
                    g1();
                } else if (p == 9 || p == 13) {
                    d1(a1);
                } else if (p == 8 || p == 12) {
                    d1(p0);
                } else if (p == 17) {
                    this.p4.skip((long) this.B4);
                } else if (p == 18) {
                    throw new JsonDataException("Expected a value but was " + u() + " at path " + getPath());
                }
                this.z4 = 0;
            } while (count != 0);
            int[] iArr = this.x;
            int i = this.d;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
            this.q[i - 1] = BuildConfig.TRAVIS;
            return;
        }
        throw new JsonDataException("Cannot skip unexpected " + u() + " at " + getPath());
    }

    private int u0(boolean throwOnEof) {
        int c = 0;
        while (this.p3.request((long) (c + 1))) {
            int p = c + 1;
            int c2 = this.p4.n((long) c);
            if (c2 == 10 || c2 == 32 || c2 == 13 || c2 == 9) {
                c = p;
            } else {
                this.p4.skip((long) (p - 1));
                if (c2 == 47) {
                    if (!this.p3.request(2)) {
                        return c2;
                    }
                    P();
                    switch (this.p4.n(1)) {
                        case 42:
                            this.p4.readByte();
                            this.p4.readByte();
                            if (e1()) {
                                c = 0;
                                break;
                            } else {
                                throw J("Unterminated comment");
                            }
                        case 47:
                            this.p4.readByte();
                            this.p4.readByte();
                            f1();
                            c = 0;
                            break;
                        default:
                            return c2;
                    }
                } else if (c2 != 35) {
                    return c2;
                } else {
                    P();
                    f1();
                    c = 0;
                }
            }
        }
        if (!throwOnEof) {
            return -1;
        }
        throw new EOFException("End of input");
    }

    private void P() {
        if (!this.y) {
            throw J("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void f1() {
        long index = this.p3.N(a2);
        okio.c cVar = this.p4;
        cVar.skip(index != -1 ? 1 + index : cVar.e1());
    }

    private boolean e1() {
        e eVar = this.p3;
        f fVar = p2;
        long index = eVar.F(fVar);
        boolean found = index != -1;
        okio.c cVar = this.p4;
        cVar.skip(found ? ((long) fVar.size()) + index : cVar.e1());
        return found;
    }

    public String toString() {
        return "JsonReader(" + this.p3 + ")";
    }

    private char c1() {
        int i;
        if (this.p3.request(1)) {
            byte escaped = this.p4.readByte();
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
                    if (this.p3.request(4)) {
                        char result = 0;
                        int end = 0 + 4;
                        for (int i2 = 0; i2 < end; i2++) {
                            byte c = this.p4.n((long) i2);
                            char result2 = (char) (result << 4);
                            if (c >= 48 && c <= 57) {
                                i = c - 48;
                            } else if (c >= 97 && c <= 102) {
                                i = (c - 97) + 10;
                            } else if (c < 65 || c > 70) {
                                throw J("\\u" + this.p4.b1(4));
                            } else {
                                i = (c - 65) + 10;
                            }
                            result = (char) (i + result2);
                        }
                        this.p4.skip(4);
                        return result;
                    }
                    throw new EOFException("Unterminated escape sequence at path " + getPath());
                default:
                    if (this.y) {
                        return (char) escaped;
                    }
                    throw J("Invalid escape sequence: \\" + ((char) escaped));
            }
        } else {
            throw J("Unterminated escape sequence");
        }
    }
}
