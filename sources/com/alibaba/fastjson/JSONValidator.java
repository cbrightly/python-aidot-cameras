package com.alibaba.fastjson;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public abstract class JSONValidator implements Cloneable, Closeable {
    protected char ch;
    protected int count = 0;
    protected boolean eof;
    protected int pos = -1;
    protected boolean supportMultiValue = false;
    protected Type type;
    private Boolean valiateResult;

    public enum Type {
        Object,
        Array,
        Value
    }

    /* access modifiers changed from: package-private */
    public abstract void next();

    public static JSONValidator fromUtf8(byte[] jsonBytes) {
        return new UTF8Validator(jsonBytes);
    }

    public static JSONValidator fromUtf8(InputStream is) {
        return new UTF8InputStreamValidator(is);
    }

    public static JSONValidator from(String jsonStr) {
        return new UTF16Validator(jsonStr);
    }

    public static JSONValidator from(Reader r) {
        return new ReaderValidator(r);
    }

    public boolean isSupportMultiValue() {
        return this.supportMultiValue;
    }

    public JSONValidator setSupportMultiValue(boolean supportMultiValue2) {
        this.supportMultiValue = supportMultiValue2;
        return this;
    }

    public Type getType() {
        if (this.type == null) {
            validate();
        }
        return this.type;
    }

    public boolean validate() {
        Boolean bool = this.valiateResult;
        if (bool != null) {
            return bool.booleanValue();
        }
        while (any()) {
            this.count++;
            if (this.eof) {
                this.valiateResult = true;
                return true;
            } else if (this.supportMultiValue) {
                skipWhiteSpace();
                if (this.eof) {
                    this.valiateResult = true;
                    return true;
                }
            } else {
                this.valiateResult = false;
                return false;
            }
        }
        this.valiateResult = false;
        return false;
    }

    public void close() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:111:0x016a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean any() {
        /*
            r10 = this;
            char r0 = r10.ch
            r1 = 34
            r2 = 101(0x65, float:1.42E-43)
            r3 = 108(0x6c, float:1.51E-43)
            r4 = 117(0x75, float:1.64E-43)
            r5 = 125(0x7d, float:1.75E-43)
            r6 = 44
            r7 = 93
            r8 = 1
            r9 = 0
            switch(r0) {
                case 34: goto L_0x01ae;
                case 43: goto L_0x0144;
                case 45: goto L_0x0144;
                case 48: goto L_0x0144;
                case 49: goto L_0x0144;
                case 50: goto L_0x0144;
                case 51: goto L_0x0144;
                case 52: goto L_0x0144;
                case 53: goto L_0x0144;
                case 54: goto L_0x0144;
                case 55: goto L_0x0144;
                case 56: goto L_0x0144;
                case 57: goto L_0x0144;
                case 91: goto L_0x0112;
                case 102: goto L_0x00d2;
                case 110: goto L_0x009e;
                case 116: goto L_0x0068;
                case 123: goto L_0x0016;
                default: goto L_0x0015;
            }
        L_0x0015:
            return r9
        L_0x0016:
            r10.next()
        L_0x0019:
            char r0 = r10.ch
            boolean r0 = isWhiteSpace(r0)
            if (r0 == 0) goto L_0x0025
            r10.next()
            goto L_0x0019
        L_0x0025:
            char r0 = r10.ch
            if (r0 != r5) goto L_0x0031
            r10.next()
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Object
            r10.type = r0
            return r8
        L_0x0031:
            char r0 = r10.ch
            if (r0 != r1) goto L_0x0067
            r10.fieldName()
            r10.skipWhiteSpace()
            char r0 = r10.ch
            r2 = 58
            if (r0 != r2) goto L_0x0066
            r10.next()
            r10.skipWhiteSpace()
            boolean r0 = r10.any()
            if (r0 != 0) goto L_0x004e
            return r9
        L_0x004e:
            r10.skipWhiteSpace()
            char r0 = r10.ch
            if (r0 != r6) goto L_0x005c
            r10.next()
            r10.skipWhiteSpace()
            goto L_0x0031
        L_0x005c:
            if (r0 != r5) goto L_0x0031
            r10.next()
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Object
            r10.type = r0
            return r8
        L_0x0066:
            return r9
        L_0x0067:
            return r9
        L_0x0068:
            r10.next()
            char r0 = r10.ch
            r1 = 114(0x72, float:1.6E-43)
            if (r0 == r1) goto L_0x0072
            return r9
        L_0x0072:
            r10.next()
            char r0 = r10.ch
            if (r0 == r4) goto L_0x007a
            return r9
        L_0x007a:
            r10.next()
            char r0 = r10.ch
            if (r0 == r2) goto L_0x0082
            return r9
        L_0x0082:
            r10.next()
            char r0 = r10.ch
            boolean r0 = isWhiteSpace(r0)
            if (r0 != 0) goto L_0x0099
            char r0 = r10.ch
            if (r0 == r6) goto L_0x0099
            if (r0 == r7) goto L_0x0099
            if (r0 == r5) goto L_0x0099
            if (r0 != 0) goto L_0x0098
            goto L_0x0099
        L_0x0098:
            return r9
        L_0x0099:
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Value
            r10.type = r0
            return r8
        L_0x009e:
            r10.next()
            char r0 = r10.ch
            if (r0 == r4) goto L_0x00a6
            return r9
        L_0x00a6:
            r10.next()
            char r0 = r10.ch
            if (r0 == r3) goto L_0x00ae
            return r9
        L_0x00ae:
            r10.next()
            char r0 = r10.ch
            if (r0 == r3) goto L_0x00b6
            return r9
        L_0x00b6:
            r10.next()
            char r0 = r10.ch
            boolean r0 = isWhiteSpace(r0)
            if (r0 != 0) goto L_0x00cd
            char r0 = r10.ch
            if (r0 == r6) goto L_0x00cd
            if (r0 == r7) goto L_0x00cd
            if (r0 == r5) goto L_0x00cd
            if (r0 != 0) goto L_0x00cc
            goto L_0x00cd
        L_0x00cc:
            return r9
        L_0x00cd:
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Value
            r10.type = r0
            return r8
        L_0x00d2:
            r10.next()
            char r0 = r10.ch
            r1 = 97
            if (r0 == r1) goto L_0x00dc
            return r9
        L_0x00dc:
            r10.next()
            char r0 = r10.ch
            if (r0 == r3) goto L_0x00e4
            return r9
        L_0x00e4:
            r10.next()
            char r0 = r10.ch
            r1 = 115(0x73, float:1.61E-43)
            if (r0 == r1) goto L_0x00ee
            return r9
        L_0x00ee:
            r10.next()
            char r0 = r10.ch
            if (r0 == r2) goto L_0x00f6
            return r9
        L_0x00f6:
            r10.next()
            char r0 = r10.ch
            boolean r0 = isWhiteSpace(r0)
            if (r0 != 0) goto L_0x010d
            char r0 = r10.ch
            if (r0 == r6) goto L_0x010d
            if (r0 == r7) goto L_0x010d
            if (r0 == r5) goto L_0x010d
            if (r0 != 0) goto L_0x010c
            goto L_0x010d
        L_0x010c:
            return r9
        L_0x010d:
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Value
            r10.type = r0
            return r8
        L_0x0112:
            r10.next()
            r10.skipWhiteSpace()
            char r0 = r10.ch
            if (r0 != r7) goto L_0x0124
            r10.next()
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Array
            r10.type = r0
            return r8
        L_0x0124:
            boolean r0 = r10.any()
            if (r0 != 0) goto L_0x012b
            return r9
        L_0x012b:
            r10.skipWhiteSpace()
            char r0 = r10.ch
            if (r0 != r6) goto L_0x0139
            r10.next()
            r10.skipWhiteSpace()
            goto L_0x0124
        L_0x0139:
            if (r0 != r7) goto L_0x0143
            r10.next()
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Array
            r10.type = r0
            return r8
        L_0x0143:
            return r9
        L_0x0144:
            r1 = 43
            r3 = 45
            r4 = 57
            r5 = 48
            if (r0 == r3) goto L_0x0150
            if (r0 != r1) goto L_0x015d
        L_0x0150:
            r10.next()
            r10.skipWhiteSpace()
            char r0 = r10.ch
            if (r0 < r5) goto L_0x01ad
            if (r0 <= r4) goto L_0x015d
            goto L_0x01ad
        L_0x015d:
            r10.next()
            char r0 = r10.ch
            if (r0 < r5) goto L_0x0166
            if (r0 <= r4) goto L_0x015d
        L_0x0166:
            r6 = 46
            if (r0 != r6) goto L_0x017f
            r10.next()
            char r0 = r10.ch
            if (r0 < r5) goto L_0x017e
            if (r0 <= r4) goto L_0x0174
            goto L_0x017e
        L_0x0174:
            char r0 = r10.ch
            if (r0 < r5) goto L_0x017f
            if (r0 > r4) goto L_0x017f
            r10.next()
            goto L_0x0174
        L_0x017e:
            return r9
        L_0x017f:
            char r0 = r10.ch
            if (r0 == r2) goto L_0x0187
            r2 = 69
            if (r0 != r2) goto L_0x01a6
        L_0x0187:
            r10.next()
            char r0 = r10.ch
            if (r0 == r3) goto L_0x0190
            if (r0 != r1) goto L_0x0193
        L_0x0190:
            r10.next()
        L_0x0193:
            char r0 = r10.ch
            if (r0 < r5) goto L_0x01ac
            if (r0 > r4) goto L_0x01ac
            r10.next()
        L_0x019c:
            char r0 = r10.ch
            if (r0 < r5) goto L_0x01a6
            if (r0 > r4) goto L_0x01a6
            r10.next()
            goto L_0x019c
        L_0x01a6:
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Value
            r10.type = r0
            return r8
        L_0x01ac:
            return r9
        L_0x01ad:
            return r9
        L_0x01ae:
            r10.next()
        L_0x01b1:
            boolean r0 = r10.eof
            if (r0 == 0) goto L_0x01b6
            return r9
        L_0x01b6:
            char r0 = r10.ch
            r2 = 92
            if (r0 != r2) goto L_0x01d7
            r10.next()
            char r0 = r10.ch
            if (r0 != r4) goto L_0x01d3
            r10.next()
            r10.next()
            r10.next()
            r10.next()
            r10.next()
            goto L_0x01b1
        L_0x01d3:
            r10.next()
            goto L_0x01b1
        L_0x01d7:
            if (r0 != r1) goto L_0x01e1
            r10.next()
            com.alibaba.fastjson.JSONValidator$Type r0 = com.alibaba.fastjson.JSONValidator.Type.Value
            r10.type = r0
            return r8
        L_0x01e1:
            r10.next()
            goto L_0x01b1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONValidator.any():boolean");
    }

    /* access modifiers changed from: protected */
    public void fieldName() {
        next();
        while (true) {
            char c = this.ch;
            if (c == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else if (c == '\"') {
                next();
                return;
            } else {
                next();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean string() {
        next();
        while (!this.eof) {
            char c = this.ch;
            if (c == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else if (c == '\"') {
                next();
                return true;
            } else {
                next();
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void skipWhiteSpace() {
        while (isWhiteSpace(this.ch)) {
            next();
        }
    }

    static final boolean isWhiteSpace(char ch2) {
        return ch2 == ' ' || ch2 == 9 || ch2 == 13 || ch2 == 10 || ch2 == 12 || ch2 == 8;
    }

    public static class UTF8Validator extends JSONValidator {
        private final byte[] bytes;

        public UTF8Validator(byte[] bytes2) {
            this.bytes = bytes2;
            next();
            skipWhiteSpace();
        }

        /* access modifiers changed from: package-private */
        public void next() {
            int i = this.pos + 1;
            this.pos = i;
            byte[] bArr = this.bytes;
            if (i >= bArr.length) {
                this.ch = 0;
                this.eof = true;
                return;
            }
            this.ch = (char) bArr[i];
        }
    }

    public static class UTF8InputStreamValidator extends JSONValidator {
        private static final ThreadLocal<byte[]> bufLocal = new ThreadLocal<>();
        private byte[] buf;
        private int end = -1;
        private final InputStream is;
        private int readCount = 0;

        public UTF8InputStreamValidator(InputStream is2) {
            this.is = is2;
            ThreadLocal<byte[]> threadLocal = bufLocal;
            byte[] bArr = threadLocal.get();
            this.buf = bArr;
            if (bArr != null) {
                threadLocal.set((Object) null);
            } else {
                this.buf = new byte[8192];
            }
            next();
            skipWhiteSpace();
        }

        /* access modifiers changed from: package-private */
        public void next() {
            int i = this.pos;
            if (i < this.end) {
                byte[] bArr = this.buf;
                int i2 = i + 1;
                this.pos = i2;
                this.ch = (char) bArr[i2];
            } else if (!this.eof) {
                try {
                    InputStream inputStream = this.is;
                    byte[] bArr2 = this.buf;
                    int len = inputStream.read(bArr2, 0, bArr2.length);
                    this.readCount++;
                    if (len > 0) {
                        this.ch = (char) this.buf[0];
                        this.pos = 0;
                        this.end = len - 1;
                    } else if (len == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = 0;
                        this.eof = true;
                    } else {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = 0;
                        this.eof = true;
                        throw new JSONException("read error");
                    }
                } catch (IOException e) {
                    throw new JSONException("read error");
                }
            }
        }

        public void close() {
            bufLocal.set(this.buf);
            this.is.close();
        }
    }

    public static class UTF16Validator extends JSONValidator {
        private final String str;

        public UTF16Validator(String str2) {
            this.str = str2;
            next();
            skipWhiteSpace();
        }

        /* access modifiers changed from: package-private */
        public void next() {
            int i = this.pos + 1;
            this.pos = i;
            if (i >= this.str.length()) {
                this.ch = 0;
                this.eof = true;
                return;
            }
            this.ch = this.str.charAt(this.pos);
        }

        /* access modifiers changed from: protected */
        public final void fieldName() {
            char ch;
            int i = this.pos;
            while (true) {
                i++;
                if (i >= this.str.length() || (ch = this.str.charAt(i)) == '\\') {
                    next();
                } else if (ch == '\"') {
                    this.ch = this.str.charAt(i + 1);
                    this.pos = i + 1;
                    return;
                }
            }
            next();
            while (true) {
                char c = this.ch;
                if (c == '\\') {
                    next();
                    if (this.ch == 'u') {
                        next();
                        next();
                        next();
                        next();
                        next();
                    } else {
                        next();
                    }
                } else if (c == '\"') {
                    next();
                    return;
                } else {
                    next();
                }
            }
        }
    }

    public static class ReaderValidator extends JSONValidator {
        private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
        private char[] buf;
        private int end = -1;
        final Reader r;
        private int readCount = 0;

        ReaderValidator(Reader r2) {
            this.r = r2;
            ThreadLocal<char[]> threadLocal = bufLocal;
            char[] cArr = threadLocal.get();
            this.buf = cArr;
            if (cArr != null) {
                threadLocal.set((Object) null);
            } else {
                this.buf = new char[8192];
            }
            next();
            skipWhiteSpace();
        }

        /* access modifiers changed from: package-private */
        public void next() {
            int i = this.pos;
            if (i < this.end) {
                char[] cArr = this.buf;
                int i2 = i + 1;
                this.pos = i2;
                this.ch = cArr[i2];
            } else if (!this.eof) {
                try {
                    Reader reader = this.r;
                    char[] cArr2 = this.buf;
                    int len = reader.read(cArr2, 0, cArr2.length);
                    this.readCount++;
                    if (len > 0) {
                        this.ch = this.buf[0];
                        this.pos = 0;
                        this.end = len - 1;
                    } else if (len == -1) {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = 0;
                        this.eof = true;
                    } else {
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = 0;
                        this.eof = true;
                        throw new JSONException("read error");
                    }
                } catch (IOException e) {
                    throw new JSONException("read error");
                }
            }
        }

        public void close() {
            bufLocal.set(this.buf);
            this.r.close();
        }
    }
}
