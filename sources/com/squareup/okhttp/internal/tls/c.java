package com.squareup.okhttp.internal.tls;

import com.alibaba.fastjson.asm.Opcodes;
import javax.security.auth.x500.X500Principal;

/* compiled from: DistinguishedNameParser */
public final class c {
    private final String a;
    private final int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private char[] g;

    public c(X500Principal principal) {
        String name = principal.getName("RFC2253");
        this.a = name;
        this.b = name.length();
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0015 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String g() {
        /*
            r7 = this;
        L_0x0000:
            int r0 = r7.c
            int r1 = r7.b
            r2 = 32
            if (r0 >= r1) goto L_0x0013
            char[] r3 = r7.g
            char r3 = r3[r0]
            if (r3 != r2) goto L_0x0013
            int r0 = r0 + 1
            r7.c = r0
            goto L_0x0000
        L_0x0013:
            if (r0 != r1) goto L_0x0017
            r0 = 0
            return r0
        L_0x0017:
            r7.d = r0
            int r0 = r0 + 1
            r7.c = r0
        L_0x001d:
            int r0 = r7.c
            int r1 = r7.b
            r3 = 61
            if (r0 >= r1) goto L_0x0034
            char[] r4 = r7.g
            char r5 = r4[r0]
            if (r5 == r3) goto L_0x0034
            char r4 = r4[r0]
            if (r4 == r2) goto L_0x0034
            int r0 = r0 + 1
            r7.c = r0
            goto L_0x001d
        L_0x0034:
            java.lang.String r4 = "Unexpected end of DN: "
            if (r0 >= r1) goto L_0x00da
            r7.e = r0
            char[] r1 = r7.g
            char r0 = r1[r0]
            if (r0 != r2) goto L_0x0075
        L_0x0040:
            int r0 = r7.c
            int r1 = r7.b
            if (r0 >= r1) goto L_0x0055
            char[] r5 = r7.g
            char r6 = r5[r0]
            if (r6 == r3) goto L_0x0055
            char r5 = r5[r0]
            if (r5 != r2) goto L_0x0055
            int r0 = r0 + 1
            r7.c = r0
            goto L_0x0040
        L_0x0055:
            char[] r5 = r7.g
            char r5 = r5[r0]
            if (r5 != r3) goto L_0x005e
            if (r0 == r1) goto L_0x005e
            goto L_0x0075
        L_0x005e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r4)
            java.lang.String r2 = r7.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0075:
            int r0 = r7.c
            int r0 = r0 + 1
            r7.c = r0
        L_0x007b:
            int r0 = r7.c
            int r1 = r7.b
            if (r0 >= r1) goto L_0x008c
            char[] r1 = r7.g
            char r1 = r1[r0]
            if (r1 != r2) goto L_0x008c
            int r0 = r0 + 1
            r7.c = r0
            goto L_0x007b
        L_0x008c:
            int r0 = r7.e
            int r1 = r7.d
            int r0 = r0 - r1
            r2 = 4
            if (r0 <= r2) goto L_0x00cd
            char[] r0 = r7.g
            int r3 = r1 + 3
            char r3 = r0[r3]
            r4 = 46
            if (r3 != r4) goto L_0x00cd
            char r3 = r0[r1]
            r4 = 79
            if (r3 == r4) goto L_0x00aa
            char r3 = r0[r1]
            r4 = 111(0x6f, float:1.56E-43)
            if (r3 != r4) goto L_0x00cd
        L_0x00aa:
            int r3 = r1 + 1
            char r3 = r0[r3]
            r4 = 73
            if (r3 == r4) goto L_0x00ba
            int r3 = r1 + 1
            char r3 = r0[r3]
            r4 = 105(0x69, float:1.47E-43)
            if (r3 != r4) goto L_0x00cd
        L_0x00ba:
            int r3 = r1 + 2
            char r3 = r0[r3]
            r4 = 68
            if (r3 == r4) goto L_0x00ca
            int r3 = r1 + 2
            char r0 = r0[r3]
            r3 = 100
            if (r0 != r3) goto L_0x00cd
        L_0x00ca:
            int r1 = r1 + r2
            r7.d = r1
        L_0x00cd:
            java.lang.String r0 = new java.lang.String
            char[] r1 = r7.g
            int r2 = r7.d
            int r3 = r7.e
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        L_0x00da:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r4)
            java.lang.String r2 = r7.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.tls.c.g():java.lang.String");
    }

    private String h() {
        int i = this.c + 1;
        this.c = i;
        this.d = i;
        this.e = i;
        while (true) {
            int i2 = this.c;
            if (i2 != this.b) {
                char[] cArr = this.g;
                if (cArr[i2] == '\"') {
                    this.c = i2 + 1;
                    while (true) {
                        int i3 = this.c;
                        if (i3 >= this.b || this.g[i3] != ' ') {
                            char[] cArr2 = this.g;
                            int i4 = this.d;
                        } else {
                            this.c = i3 + 1;
                        }
                    }
                    char[] cArr22 = this.g;
                    int i42 = this.d;
                    return new String(cArr22, i42, this.e - i42);
                }
                if (cArr[i2] == '\\') {
                    cArr[this.e] = d();
                } else {
                    cArr[this.e] = cArr[i2];
                }
                this.c++;
                this.e++;
            } else {
                throw new IllegalStateException("Unexpected end of DN: " + this.a);
            }
        }
    }

    private String f() {
        int i;
        int i2 = this.c;
        if (i2 + 4 < this.b) {
            this.d = i2;
            this.c = i2 + 1;
            while (true) {
                i = this.c;
                if (i == this.b) {
                    break;
                }
                char[] cArr = this.g;
                if (cArr[i] == '+' || cArr[i] == ',' || cArr[i] == ';') {
                    break;
                } else if (cArr[i] == ' ') {
                    this.e = i;
                    this.c = i + 1;
                    while (true) {
                        int i3 = this.c;
                        if (i3 >= this.b || this.g[i3] != ' ') {
                            break;
                        }
                        this.c = i3 + 1;
                    }
                } else {
                    if (cArr[i] >= 'A' && cArr[i] <= 'F') {
                        cArr[i] = (char) (cArr[i] + ' ');
                    }
                    this.c = i + 1;
                }
            }
            this.e = i;
            int i4 = this.e;
            int i5 = this.d;
            int hexLen = i4 - i5;
            if (hexLen < 5 || (hexLen & 1) == 0) {
                throw new IllegalStateException("Unexpected end of DN: " + this.a);
            }
            byte[] encoded = new byte[(hexLen / 2)];
            int p = i5 + 1;
            for (int i6 = 0; i6 < encoded.length; i6++) {
                encoded[i6] = (byte) c(p);
                p += 2;
            }
            return new String(this.g, this.d, hexLen);
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.a);
    }

    private String a() {
        int i;
        int i2;
        int i3 = this.c;
        this.d = i3;
        this.e = i3;
        while (true) {
            int i4 = this.c;
            if (i4 < this.b) {
                char[] cArr = this.g;
                switch (cArr[i4]) {
                    case ' ':
                        int i5 = this.e;
                        this.f = i5;
                        this.c = i4 + 1;
                        this.e = i5 + 1;
                        cArr[i5] = ' ';
                        while (true) {
                            i = this.c;
                            i2 = this.b;
                            if (i < i2) {
                                char[] cArr2 = this.g;
                                if (cArr2[i] == ' ') {
                                    int i6 = this.e;
                                    this.e = i6 + 1;
                                    cArr2[i6] = ' ';
                                    this.c = i + 1;
                                }
                            }
                        }
                        if (i == i2) {
                            break;
                        } else {
                            char[] cArr3 = this.g;
                            if (!(cArr3[i] == ',' || cArr3[i] == '+' || cArr3[i] == ';')) {
                                break;
                            }
                        }
                    case '+':
                    case ',':
                    case ';':
                        char[] cArr4 = this.g;
                        int i7 = this.d;
                        return new String(cArr4, i7, this.e - i7);
                    case '\\':
                        int i8 = this.e;
                        this.e = i8 + 1;
                        cArr[i8] = d();
                        this.c++;
                        break;
                    default:
                        int i9 = this.e;
                        this.e = i9 + 1;
                        cArr[i9] = cArr[i4];
                        this.c = i4 + 1;
                        break;
                }
            } else {
                char[] cArr5 = this.g;
                int i10 = this.d;
                return new String(cArr5, i10, this.e - i10);
            }
        }
        char[] cArr6 = this.g;
        int i11 = this.d;
        return new String(cArr6, i11, this.f - i11);
    }

    private char d() {
        int i = this.c + 1;
        this.c = i;
        if (i != this.b) {
            char[] cArr = this.g;
            switch (cArr[i]) {
                case ' ':
                case '\"':
                case '#':
                case '%':
                case '*':
                case '+':
                case ',':
                case ';':
                case '<':
                case '=':
                case '>':
                case '\\':
                case '_':
                    return cArr[i];
                default:
                    return e();
            }
        } else {
            throw new IllegalStateException("Unexpected end of DN: " + this.a);
        }
    }

    private char e() {
        int count;
        int res;
        int res2 = c(this.c);
        this.c++;
        if (res2 < 128) {
            return (char) res2;
        }
        if (res2 < 192 || res2 > 247) {
            return '?';
        }
        if (res2 <= 223) {
            count = 1;
            res = res2 & 31;
        } else if (res2 <= 239) {
            count = 2;
            res = res2 & 15;
        } else {
            count = 3;
            res = res2 & 7;
        }
        for (int i = 0; i < count; i++) {
            int i2 = this.c + 1;
            this.c = i2;
            if (i2 == this.b || this.g[i2] != '\\') {
                return '?';
            }
            int i3 = i2 + 1;
            this.c = i3;
            int b2 = c(i3);
            this.c++;
            if ((b2 & Opcodes.CHECKCAST) != 128) {
                return '?';
            }
            res = (res << 6) + (b2 & 63);
        }
        return (char) res;
    }

    private int c(int position) {
        int b1;
        int b2;
        if (position + 1 < this.b) {
            char[] cArr = this.g;
            char b12 = cArr[position];
            if (b12 >= '0' && b12 <= '9') {
                b1 = b12 - '0';
            } else if (b12 >= 'a' && b12 <= 'f') {
                b1 = b12 - 'W';
            } else if (b12 < 'A' || b12 > 'F') {
                throw new IllegalStateException("Malformed DN: " + this.a);
            } else {
                b1 = b12 - '7';
            }
            char b22 = cArr[position + 1];
            if (b22 >= '0' && b22 <= '9') {
                b2 = b22 - '0';
            } else if (b22 >= 'a' && b22 <= 'f') {
                b2 = b22 - 'W';
            } else if (b22 < 'A' || b22 > 'F') {
                throw new IllegalStateException("Malformed DN: " + this.a);
            } else {
                b2 = b22 - '7';
            }
            return (b1 << 4) + b2;
        }
        throw new IllegalStateException("Malformed DN: " + this.a);
    }

    public String b(String attributeType) {
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = this.a.toCharArray();
        String attType = g();
        if (attType == null) {
            return null;
        }
        do {
            String attValue = "";
            int i = this.c;
            if (i == this.b) {
                return null;
            }
            switch (this.g[i]) {
                case '\"':
                    attValue = h();
                    break;
                case '#':
                    attValue = f();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    attValue = a();
                    break;
            }
            if (attributeType.equalsIgnoreCase(attType)) {
                return attValue;
            }
            int i2 = this.c;
            if (i2 >= this.b) {
                return null;
            }
            char[] cArr = this.g;
            if (cArr[i2] == ',' || cArr[i2] == ';' || cArr[i2] == '+') {
                this.c = i2 + 1;
                attType = g();
            } else {
                throw new IllegalStateException("Malformed DN: " + this.a);
            }
        } while (attType != null);
        throw new IllegalStateException("Malformed DN: " + this.a);
    }
}
