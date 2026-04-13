package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import io.netty.util.internal.StringUtil;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.SimpleTimeZone;

public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    public JSONScanner(String input) {
        this(input, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String input, int features) {
        super(features);
        this.text = input;
        this.len = input.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    public final char charAt(int index) {
        if (index >= this.len) {
            return JSONLexer.EOI;
        }
        return this.text.charAt(index);
    }

    public final char next() {
        char c;
        int index = this.bp + 1;
        this.bp = index;
        if (index >= this.len) {
            c = JSONLexer.EOI;
        } else {
            c = this.text.charAt(index);
        }
        this.ch = c;
        return c;
    }

    public JSONScanner(char[] input, int inputLength) {
        this(input, inputLength, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] input, int inputLength, int features) {
        this(new String(input, 0, inputLength), features);
    }

    /* access modifiers changed from: protected */
    public final void copyTo(int offset, int count, char[] dest) {
        this.text.getChars(offset, offset + count, dest, 0);
    }

    static boolean charArrayCompare(String src, int offset, char[] dest) {
        int destLen = dest.length;
        if (destLen + offset > src.length()) {
            return false;
        }
        for (int i = 0; i < destLen; i++) {
            if (dest[i] != src.charAt(offset + i)) {
                return false;
            }
        }
        return true;
    }

    public final boolean charArrayCompare(char[] chars) {
        return charArrayCompare(this.text, this.bp, chars);
    }

    public final int indexOf(char ch, int startIndex) {
        return this.text.indexOf(ch, startIndex);
    }

    public final String addSymbol(int offset, int len2, int hash, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, offset, len2, hash);
    }

    public byte[] bytesValue() {
        if (this.token == 26) {
            int start = this.np + 1;
            int len2 = this.sp;
            if (len2 % 2 == 0) {
                byte[] bytes = new byte[(len2 / 2)];
                for (int i = 0; i < bytes.length; i++) {
                    char c0 = this.text.charAt((i * 2) + start);
                    char c1 = this.text.charAt((i * 2) + start + 1);
                    char c = '0';
                    int b0 = c0 - (c0 <= '9' ? '0' : '7');
                    if (c1 > '9') {
                        c = '7';
                    }
                    bytes[i] = (byte) ((b0 << 4) | (c1 - c));
                }
                return bytes;
            }
            throw new JSONException("illegal state. " + len2);
        } else if (this.hasSpecial == 0) {
            return IOUtils.decodeBase64(this.text, this.np + 1, this.sp);
        } else {
            return IOUtils.decodeBase64(new String(this.sbuf, 0, this.sp));
        }
    }

    public final String stringVal() {
        if (!this.hasSpecial) {
            return subString(this.np + 1, this.sp);
        }
        return new String(this.sbuf, 0, this.sp);
    }

    public final String subString(int offset, int count) {
        if (!ASMUtils.IS_ANDROID) {
            return this.text.substring(offset, offset + count);
        }
        char[] cArr = this.sbuf;
        if (count < cArr.length) {
            this.text.getChars(offset, offset + count, cArr, 0);
            return new String(this.sbuf, 0, count);
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return new String(chars);
    }

    public final char[] sub_chars(int offset, int count) {
        if (ASMUtils.IS_ANDROID) {
            char[] cArr = this.sbuf;
            if (count < cArr.length) {
                this.text.getChars(offset, offset + count, cArr, 0);
                return this.sbuf;
            }
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return chars;
    }

    public final String numberString() {
        char chLocal = charAt((this.np + this.sp) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        return subString(this.np, sp);
    }

    public final BigDecimal decimalValue() {
        char chLocal = charAt((this.np + this.sp) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        if (sp <= 65535) {
            int offset = this.np;
            int count = sp;
            char[] cArr = this.sbuf;
            if (count < cArr.length) {
                this.text.getChars(offset, offset + count, cArr, 0);
                return new BigDecimal(this.sbuf, 0, count, MathContext.UNLIMITED);
            }
            char[] chars = new char[count];
            this.text.getChars(offset, offset + count, chars, 0);
            return new BigDecimal(chars, 0, chars.length, MathContext.UNLIMITED);
        }
        throw new JSONException("decimal overflow");
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    public boolean scanISO8601DateIfMatch(boolean strict) {
        return scanISO8601DateIfMatch(strict, this.len - this.bp);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ef, code lost:
        if (r25 != ' ') goto L_0x0109;
     */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x070e  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0710  */
    /* JADX WARNING: Removed duplicated region for block: B:313:0x0719  */
    /* JADX WARNING: Removed duplicated region for block: B:314:0x071b  */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x0720  */
    /* JADX WARNING: Removed duplicated region for block: B:326:0x076c  */
    /* JADX WARNING: Removed duplicated region for block: B:330:0x07a7 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x07a9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean scanISO8601DateIfMatch(boolean r51, int r52) {
        /*
            r50 = this;
            r9 = r50
            r10 = r52
            r11 = 8
            r12 = 0
            if (r10 >= r11) goto L_0x000a
            return r12
        L_0x000a:
            int r0 = r9.bp
            char r13 = r9.charAt(r0)
            int r0 = r9.bp
            r14 = 1
            int r0 = r0 + r14
            char r15 = r9.charAt(r0)
            int r0 = r9.bp
            r8 = 2
            int r0 = r0 + r8
            char r7 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 3
            char r6 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 4
            char r5 = r9.charAt(r0)
            int r0 = r9.bp
            r4 = 5
            int r0 = r0 + r4
            char r3 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 6
            char r2 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 7
            char r1 = r9.charAt(r0)
            r0 = 13
            if (r51 != 0) goto L_0x00ce
            if (r10 <= r0) goto L_0x00ce
            int r0 = r9.bp
            int r0 = r0 + r10
            int r0 = r0 - r14
            char r0 = r9.charAt(r0)
            int r14 = r9.bp
            int r14 = r14 + r10
            int r14 = r14 - r8
            char r14 = r9.charAt(r14)
            r8 = 47
            if (r13 != r8) goto L_0x00ca
            r8 = 68
            if (r15 != r8) goto L_0x00ca
            r8 = 97
            if (r7 != r8) goto L_0x00ca
            r8 = 116(0x74, float:1.63E-43)
            if (r6 != r8) goto L_0x00ca
            r8 = 101(0x65, float:1.42E-43)
            if (r5 != r8) goto L_0x00ca
            r8 = 40
            if (r3 != r8) goto L_0x00ca
            r8 = 47
            if (r0 != r8) goto L_0x00ca
            r8 = 41
            if (r14 != r8) goto L_0x00ca
            r8 = -1
            r16 = 6
            r12 = r16
        L_0x0083:
            if (r12 >= r10) goto L_0x009f
            int r11 = r9.bp
            int r11 = r11 + r12
            char r11 = r9.charAt(r11)
            r4 = 43
            if (r11 != r4) goto L_0x0093
            r4 = r12
            r8 = r4
            goto L_0x009c
        L_0x0093:
            r4 = 48
            if (r11 < r4) goto L_0x009f
            r4 = 57
            if (r11 <= r4) goto L_0x009c
            goto L_0x009f
        L_0x009c:
            int r12 = r12 + 1
            goto L_0x0083
        L_0x009f:
            r4 = -1
            if (r8 != r4) goto L_0x00a4
            r4 = 0
            return r4
        L_0x00a4:
            int r4 = r9.bp
            int r11 = r4 + 6
            int r4 = r4 + r8
            int r4 = r4 - r11
            java.lang.String r4 = r9.subString(r11, r4)
            r16 = r11
            long r11 = java.lang.Long.parseLong(r4)
            r22 = r0
            java.util.TimeZone r0 = r9.timeZone
            r18 = r4
            java.util.Locale r4 = r9.locale
            java.util.Calendar r0 = java.util.Calendar.getInstance(r0, r4)
            r9.calendar = r0
            r0.setTimeInMillis(r11)
            r4 = 5
            r9.token = r4
            r0 = 1
            return r0
        L_0x00ca:
            r22 = r0
            r4 = 5
            goto L_0x00cf
        L_0x00ce:
            r4 = 5
        L_0x00cf:
            r11 = 16
            r8 = 32
            r14 = 84
            r12 = 14
            r0 = 45
            r4 = 8
            if (r10 == r4) goto L_0x06d8
            if (r10 == r12) goto L_0x06d8
            if (r10 != r11) goto L_0x0109
            int r4 = r9.bp
            int r4 = r4 + 10
            char r4 = r9.charAt(r4)
            r25 = r4
            if (r4 == r14) goto L_0x00f2
            r4 = r25
            if (r4 == r8) goto L_0x00f4
            goto L_0x0109
        L_0x00f2:
            r4 = r25
        L_0x00f4:
            r47 = r1
            r48 = r2
            r33 = r3
            r49 = r5
            r24 = r6
            r37 = r7
            r0 = 0
            r8 = 48
            r12 = 13
            r18 = 9
            goto L_0x06eb
        L_0x0109:
            r4 = 17
            if (r10 != r4) goto L_0x012c
            int r4 = r9.bp
            int r4 = r4 + 6
            char r4 = r9.charAt(r4)
            if (r4 == r0) goto L_0x012c
            r47 = r1
            r48 = r2
            r33 = r3
            r49 = r5
            r24 = r6
            r37 = r7
            r0 = 0
            r8 = 48
            r12 = 13
            r18 = 9
            goto L_0x06eb
        L_0x012c:
            r4 = 9
            if (r10 >= r4) goto L_0x0132
            r0 = 0
            return r0
        L_0x0132:
            int r12 = r9.bp
            r16 = 8
            int r12 = r12 + 8
            char r12 = r9.charAt(r12)
            int r11 = r9.bp
            int r11 = r11 + r4
            char r11 = r9.charAt(r11)
            r26 = 10
            r14 = 26085(0x65e5, float:3.6553E-41)
            if (r5 != r0) goto L_0x014b
            if (r1 == r0) goto L_0x0153
        L_0x014b:
            r4 = 47
            if (r5 != r4) goto L_0x0180
            r4 = 47
            if (r1 != r4) goto L_0x0180
        L_0x0153:
            r4 = r13
            r30 = r15
            r31 = r7
            r32 = r6
            r33 = r3
            r34 = r2
            if (r11 != r8) goto L_0x0171
            r35 = 48
            r36 = r12
            r26 = 9
            r14 = r4
            r8 = r26
            r26 = r33
            r45 = r35
            r46 = r36
            goto L_0x028f
        L_0x0171:
            r35 = r12
            r36 = r11
            r14 = r4
            r8 = r26
            r26 = r33
            r45 = r35
            r46 = r36
            goto L_0x028f
        L_0x0180:
            if (r5 != r0) goto L_0x01b3
            if (r2 != r0) goto L_0x01b3
            r4 = r13
            r30 = r15
            r31 = r7
            r32 = r6
            r33 = 48
            r34 = r3
            if (r12 != r8) goto L_0x01a2
            r35 = 48
            r36 = r1
            r26 = 8
            r14 = r4
            r8 = r26
            r26 = r33
            r45 = r35
            r46 = r36
            goto L_0x028f
        L_0x01a2:
            r35 = r1
            r36 = r12
            r26 = 9
            r14 = r4
            r8 = r26
            r26 = r33
            r45 = r35
            r46 = r36
            goto L_0x028f
        L_0x01b3:
            r4 = 46
            if (r7 != r4) goto L_0x01bb
            r4 = 46
            if (r3 == r4) goto L_0x01bf
        L_0x01bb:
            if (r7 != r0) goto L_0x01d9
            if (r3 != r0) goto L_0x01d9
        L_0x01bf:
            r35 = r13
            r36 = r15
            r33 = r6
            r34 = r5
            r4 = r2
            r30 = r1
            r31 = r12
            r32 = r11
            r14 = r4
            r8 = r26
            r26 = r33
            r45 = r35
            r46 = r36
            goto L_0x028f
        L_0x01d9:
            r4 = 84
            if (r12 != r4) goto L_0x01f9
            r4 = r13
            r30 = r15
            r31 = r7
            r32 = r6
            r33 = r5
            r34 = r3
            r35 = r2
            r36 = r1
            r26 = 8
            r14 = r4
            r8 = r26
            r26 = r33
            r45 = r35
            r46 = r36
            goto L_0x028f
        L_0x01f9:
            r4 = 24180(0x5e74, float:3.3883E-41)
            if (r5 == r4) goto L_0x0205
            r4 = 45380(0xb144, float:6.3591E-41)
            if (r5 != r4) goto L_0x0203
            goto L_0x0205
        L_0x0203:
            r0 = 0
            return r0
        L_0x0205:
            r4 = r13
            r30 = r15
            r31 = r7
            r32 = r6
            r0 = 26376(0x6708, float:3.696E-41)
            if (r1 == r0) goto L_0x0250
            r0 = 50900(0xc6d4, float:7.1326E-41)
            if (r1 != r0) goto L_0x0216
            goto L_0x0250
        L_0x0216:
            r0 = 26376(0x6708, float:3.696E-41)
            if (r2 == r0) goto L_0x0222
            r0 = 50900(0xc6d4, float:7.1326E-41)
            if (r2 != r0) goto L_0x0220
            goto L_0x0222
        L_0x0220:
            r0 = 0
            return r0
        L_0x0222:
            r0 = 48
            r34 = r3
            if (r12 == r14) goto L_0x0242
            r8 = 51068(0xc77c, float:7.1562E-41)
            if (r12 != r8) goto L_0x022e
            goto L_0x0242
        L_0x022e:
            if (r11 == r14) goto L_0x0235
            if (r11 != r8) goto L_0x0233
            goto L_0x0235
        L_0x0233:
            r8 = 0
            return r8
        L_0x0235:
            r8 = r1
            r36 = r12
            r14 = r4
            r45 = r8
            r8 = r26
            r46 = r36
            r26 = r0
            goto L_0x028f
        L_0x0242:
            r8 = 48
            r36 = r1
            r14 = r4
            r45 = r8
            r8 = r26
            r46 = r36
            r26 = r0
            goto L_0x028f
        L_0x0250:
            r0 = r3
            r34 = r2
            if (r11 == r14) goto L_0x0283
            r8 = 51068(0xc77c, float:7.1562E-41)
            if (r11 != r8) goto L_0x025b
            goto L_0x0283
        L_0x025b:
            int r8 = r9.bp
            int r8 = r8 + 10
            char r8 = r9.charAt(r8)
            if (r8 == r14) goto L_0x0275
            int r8 = r9.bp
            int r8 = r8 + 10
            char r8 = r9.charAt(r8)
            r14 = 51068(0xc77c, float:7.1562E-41)
            if (r8 != r14) goto L_0x0273
            goto L_0x0275
        L_0x0273:
            r8 = 0
            return r8
        L_0x0275:
            r8 = r12
            r14 = r11
            r26 = 11
            r45 = r8
            r46 = r14
            r8 = r26
            r26 = r0
            r14 = r4
            goto L_0x028f
        L_0x0283:
            r8 = 48
            r14 = r12
            r45 = r8
            r46 = r14
            r8 = r26
            r26 = r0
            r14 = r4
        L_0x028f:
            r37 = r14
            r38 = r30
            r39 = r31
            r40 = r32
            r41 = r26
            r42 = r34
            r43 = r45
            r44 = r46
            boolean r0 = checkDate(r37, r38, r39, r40, r41, r42, r43, r44)
            if (r0 != 0) goto L_0x02a7
            r0 = 0
            return r0
        L_0x02a7:
            r29 = r11
            r4 = 13
            r11 = 45
            r18 = 9
            r0 = r50
            r47 = r1
            r1 = r14
            r48 = r2
            r2 = r30
            r33 = r3
            r3 = r31
            r11 = 5
            r4 = r32
            r49 = r5
            r5 = r26
            r24 = r6
            r6 = r34
            r37 = r7
            r7 = r45
            r11 = r8
            r19 = r12
            r12 = 2
            r8 = r46
            r0.setCalendar(r1, r2, r3, r4, r5, r6, r7, r8)
            int r0 = r9.bp
            int r0 = r0 + r11
            char r7 = r9.charAt(r0)
            r0 = 84
            if (r7 != r0) goto L_0x0376
            r0 = 16
            if (r10 != r0) goto L_0x0376
            r0 = 8
            if (r11 != r0) goto L_0x0376
            int r0 = r9.bp
            int r0 = r0 + 15
            char r0 = r9.charAt(r0)
            r1 = 90
            if (r0 != r1) goto L_0x0376
            int r0 = r9.bp
            int r0 = r0 + r11
            r1 = 1
            int r0 = r0 + r1
            char r8 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + r12
            char r12 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 3
            char r16 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 4
            char r17 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            r1 = 5
            int r0 = r0 + r1
            char r18 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 6
            char r20 = r9.charAt(r0)
            r0 = r50
            r1 = r8
            r2 = r12
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r20
            boolean r0 = r0.checkTime(r1, r2, r3, r4, r5, r6)
            if (r0 != 0) goto L_0x033c
            r0 = 0
            return r0
        L_0x033c:
            r0 = r50
            r1 = r8
            r2 = r12
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r20
            r0.setTime(r1, r2, r3, r4, r5, r6)
            java.util.Calendar r0 = r9.calendar
            r1 = 14
            r2 = 0
            r0.set(r1, r2)
            java.util.Calendar r0 = r9.calendar
            java.util.TimeZone r0 = r0.getTimeZone()
            int r0 = r0.getRawOffset()
            if (r0 == 0) goto L_0x0371
            java.lang.String[] r0 = java.util.TimeZone.getAvailableIDs(r2)
            int r1 = r0.length
            if (r1 <= 0) goto L_0x0371
            r1 = r0[r2]
            java.util.TimeZone r1 = java.util.TimeZone.getTimeZone(r1)
            java.util.Calendar r2 = r9.calendar
            r2.setTimeZone(r1)
        L_0x0371:
            r0 = 5
            r9.token = r0
            r0 = 1
            return r0
        L_0x0376:
            r0 = 84
            if (r7 == r0) goto L_0x042d
            r0 = 32
            if (r7 != r0) goto L_0x0384
            if (r51 != 0) goto L_0x0384
            r8 = 48
            goto L_0x042f
        L_0x0384:
            r0 = 34
            if (r7 == r0) goto L_0x0400
            r0 = 26
            if (r7 == r0) goto L_0x0400
            r0 = 26085(0x65e5, float:3.6553E-41)
            if (r7 == r0) goto L_0x0400
            r0 = 51068(0xc77c, float:7.1562E-41)
            if (r7 != r0) goto L_0x0397
            r0 = 0
            goto L_0x0401
        L_0x0397:
            r0 = 43
            if (r7 == r0) goto L_0x03a2
            r0 = 45
            if (r7 != r0) goto L_0x03a0
            goto L_0x03a2
        L_0x03a0:
            r0 = 0
            return r0
        L_0x03a2:
            int r0 = r9.len
            int r8 = r11 + 6
            if (r0 != r8) goto L_0x03fe
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 3
            char r0 = r9.charAt(r0)
            r1 = 58
            if (r0 != r1) goto L_0x03fc
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 4
            char r0 = r9.charAt(r0)
            r8 = 48
            if (r0 != r8) goto L_0x03fc
            int r0 = r9.bp
            int r0 = r0 + r11
            r1 = 5
            int r0 = r0 + r1
            char r0 = r9.charAt(r0)
            if (r0 == r8) goto L_0x03ce
            goto L_0x03fc
        L_0x03ce:
            r1 = 48
            r2 = 48
            r3 = 48
            r4 = 48
            r5 = 48
            r6 = 48
            r0 = r50
            r0.setTime(r1, r2, r3, r4, r5, r6)
            java.util.Calendar r0 = r9.calendar
            r1 = 14
            r2 = 0
            r0.set(r1, r2)
            int r0 = r9.bp
            int r0 = r0 + r11
            r1 = 1
            int r0 = r0 + r1
            char r0 = r9.charAt(r0)
            int r2 = r9.bp
            int r2 = r2 + r11
            int r2 = r2 + r12
            char r2 = r9.charAt(r2)
            r9.setTimeZone(r7, r0, r2)
            return r1
        L_0x03fc:
            r0 = 0
            return r0
        L_0x03fe:
            r0 = 0
            return r0
        L_0x0400:
            r0 = 0
        L_0x0401:
            java.util.Calendar r1 = r9.calendar
            r2 = 11
            r1.set(r2, r0)
            java.util.Calendar r1 = r9.calendar
            r2 = 12
            r1.set(r2, r0)
            java.util.Calendar r1 = r9.calendar
            r12 = 13
            r1.set(r12, r0)
            java.util.Calendar r1 = r9.calendar
            r2 = 14
            r1.set(r2, r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            r9.bp = r0
            char r0 = r9.charAt(r0)
            r9.ch = r0
            r0 = 5
            r9.token = r0
            r0 = 1
            return r0
        L_0x042d:
            r8 = 48
        L_0x042f:
            int r0 = r11 + 9
            if (r10 >= r0) goto L_0x0435
            r0 = 0
            return r0
        L_0x0435:
            r0 = 0
            int r1 = r9.bp
            int r1 = r1 + r11
            int r1 = r1 + 3
            char r1 = r9.charAt(r1)
            r2 = 58
            if (r1 == r2) goto L_0x0444
            return r0
        L_0x0444:
            int r1 = r9.bp
            int r1 = r1 + r11
            int r1 = r1 + 6
            char r1 = r9.charAt(r1)
            if (r1 == r2) goto L_0x0450
            return r0
        L_0x0450:
            int r0 = r9.bp
            int r0 = r0 + r11
            r1 = 1
            int r0 = r0 + r1
            char r25 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + r12
            char r27 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 4
            char r28 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            r1 = 5
            int r0 = r0 + r1
            char r36 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 7
            char r39 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r11
            r1 = 8
            int r0 = r0 + r1
            char r16 = r9.charAt(r0)
            r0 = r50
            r1 = r25
            r2 = r27
            r3 = r28
            r4 = r36
            r5 = r39
            r6 = r16
            boolean r0 = r0.checkTime(r1, r2, r3, r4, r5, r6)
            if (r0 != 0) goto L_0x049c
            r0 = 0
            return r0
        L_0x049c:
            r0 = r50
            r1 = r25
            r2 = r27
            r3 = r28
            r4 = r36
            r5 = r39
            r6 = r16
            r0.setTime(r1, r2, r3, r4, r5, r6)
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 9
            char r6 = r9.charAt(r0)
            r0 = -1
            r1 = 0
            r2 = 46
            if (r6 != r2) goto L_0x050e
            int r2 = r11 + 11
            if (r10 >= r2) goto L_0x04c2
            r2 = 0
            return r2
        L_0x04c2:
            int r2 = r9.bp
            int r2 = r2 + r11
            int r2 = r2 + 10
            char r2 = r9.charAt(r2)
            if (r2 < r8) goto L_0x050c
            r3 = 57
            if (r2 <= r3) goto L_0x04d2
            goto L_0x050c
        L_0x04d2:
            int r1 = r2 + -48
            r0 = 1
            int r3 = r11 + 11
            if (r10 <= r3) goto L_0x04f0
            int r3 = r9.bp
            int r3 = r3 + r11
            r4 = 11
            int r3 = r3 + r4
            char r3 = r9.charAt(r3)
            if (r3 < r8) goto L_0x04f0
            r4 = 57
            if (r3 > r4) goto L_0x04f0
            int r4 = r1 * 10
            int r5 = r3 + -48
            int r4 = r4 + r5
            r0 = 2
            r1 = r4
        L_0x04f0:
            if (r0 != r12) goto L_0x050a
            int r3 = r9.bp
            int r3 = r3 + r11
            int r3 = r3 + 12
            char r3 = r9.charAt(r3)
            if (r3 < r8) goto L_0x050a
            r4 = 57
            if (r3 > r4) goto L_0x050a
            int r4 = r1 * 10
            int r5 = r3 + -48
            int r1 = r4 + r5
            r0 = 3
            r5 = r1
            goto L_0x050f
        L_0x050a:
            r5 = r1
            goto L_0x050f
        L_0x050c:
            r3 = 0
            return r3
        L_0x050e:
            r5 = r1
        L_0x050f:
            java.util.Calendar r1 = r9.calendar
            r2 = 14
            r1.set(r2, r5)
            r1 = 0
            int r2 = r9.bp
            int r2 = r2 + r11
            int r2 = r2 + 10
            int r2 = r2 + r0
            char r2 = r9.charAt(r2)
            r3 = 32
            if (r2 != r3) goto L_0x0535
            int r0 = r0 + 1
            int r3 = r9.bp
            int r3 = r3 + r11
            int r3 = r3 + 10
            int r3 = r3 + r0
            char r2 = r9.charAt(r3)
            r17 = r0
            r4 = r2
            goto L_0x0538
        L_0x0535:
            r17 = r0
            r4 = r2
        L_0x0538:
            r0 = 43
            if (r4 == r0) goto L_0x0571
            r0 = 45
            if (r4 != r0) goto L_0x0541
            goto L_0x0571
        L_0x0541:
            r0 = 90
            if (r4 != r0) goto L_0x056b
            r1 = 1
            java.util.Calendar r0 = r9.calendar
            java.util.TimeZone r0 = r0.getTimeZone()
            int r0 = r0.getRawOffset()
            if (r0 == 0) goto L_0x0565
            r0 = 0
            java.lang.String[] r2 = java.util.TimeZone.getAvailableIDs(r0)
            int r3 = r2.length
            if (r3 <= 0) goto L_0x0565
            r3 = r2[r0]
            java.util.TimeZone r0 = java.util.TimeZone.getTimeZone(r3)
            java.util.Calendar r3 = r9.calendar
            r3.setTimeZone(r0)
        L_0x0565:
            r23 = r4
            r35 = r5
            goto L_0x0699
        L_0x056b:
            r23 = r4
            r35 = r5
            goto L_0x0699
        L_0x0571:
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 10
            int r0 = r0 + r17
            r2 = 1
            int r0 = r0 + r2
            char r3 = r9.charAt(r0)
            if (r3 < r8) goto L_0x06ce
            r0 = 49
            if (r3 <= r0) goto L_0x058f
            r20 = r1
            r22 = r3
            r23 = r4
            r35 = r5
            r0 = 0
            goto L_0x06d7
        L_0x058f:
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 10
            int r0 = r0 + r17
            int r0 = r0 + r12
            char r12 = r9.charAt(r0)
            if (r12 < r8) goto L_0x06c4
            r0 = 57
            if (r12 <= r0) goto L_0x05ab
            r20 = r1
            r22 = r3
            r23 = r4
            r35 = r5
            goto L_0x06cc
        L_0x05ab:
            int r0 = r9.bp
            int r0 = r0 + r11
            int r0 = r0 + 10
            int r0 = r0 + r17
            int r0 = r0 + 3
            char r2 = r9.charAt(r0)
            r0 = 48
            r18 = 48
            r8 = 58
            if (r2 != r8) goto L_0x061c
            int r8 = r9.bp
            int r8 = r8 + r11
            int r8 = r8 + 10
            int r8 = r8 + r17
            int r8 = r8 + 4
            char r0 = r9.charAt(r8)
            int r8 = r9.bp
            int r8 = r8 + r11
            int r8 = r8 + 10
            int r8 = r8 + r17
            r20 = 5
            int r8 = r8 + 5
            char r8 = r9.charAt(r8)
            r20 = r1
            r1 = 52
            if (r0 != r1) goto L_0x0602
            r1 = 53
            if (r8 != r1) goto L_0x0602
            r1 = 49
            if (r3 != r1) goto L_0x05f3
            r1 = 50
            if (r12 == r1) goto L_0x05f2
            r1 = 51
            if (r12 != r1) goto L_0x05f3
        L_0x05f2:
            goto L_0x0614
        L_0x05f3:
            r1 = 48
            if (r3 != r1) goto L_0x0600
            r1 = 53
            if (r12 == r1) goto L_0x05ff
            r1 = 56
            if (r12 != r1) goto L_0x0600
        L_0x05ff:
            goto L_0x0614
        L_0x0600:
            r1 = 0
            return r1
        L_0x0602:
            r1 = 0
            r1 = 48
            if (r0 == r1) goto L_0x060d
            r1 = 51
            if (r0 == r1) goto L_0x060d
            r1 = 0
            return r1
        L_0x060d:
            r1 = 0
            r1 = 48
            if (r8 == r1) goto L_0x0614
            r1 = 0
            return r1
        L_0x0614:
            r1 = 6
            r18 = r1
            r20 = r8
            r8 = r0
            goto L_0x0684
        L_0x061c:
            r20 = r1
            r1 = 48
            if (r2 != r1) goto L_0x063e
            int r8 = r9.bp
            int r8 = r8 + r11
            int r8 = r8 + 10
            int r8 = r8 + r17
            int r8 = r8 + 4
            char r0 = r9.charAt(r8)
            if (r0 == r1) goto L_0x0637
            r1 = 51
            if (r0 == r1) goto L_0x0637
            r1 = 0
            return r1
        L_0x0637:
            r1 = 5
            r8 = r0
            r20 = r18
            r18 = r1
            goto L_0x0684
        L_0x063e:
            r1 = 51
            if (r2 != r1) goto L_0x065e
            int r1 = r9.bp
            int r1 = r1 + r11
            int r1 = r1 + 10
            int r1 = r1 + r17
            int r1 = r1 + 4
            char r1 = r9.charAt(r1)
            r8 = 48
            if (r1 != r8) goto L_0x065e
            r0 = 51
            r18 = 48
            r1 = 5
            r8 = r0
            r20 = r18
            r18 = r1
            goto L_0x0684
        L_0x065e:
            r1 = 52
            if (r2 != r1) goto L_0x067e
            int r1 = r9.bp
            int r1 = r1 + r11
            int r1 = r1 + 10
            int r1 = r1 + r17
            int r1 = r1 + 4
            char r1 = r9.charAt(r1)
            r8 = 53
            if (r1 != r8) goto L_0x067e
            r0 = 52
            r18 = 53
            r1 = 5
            r8 = r0
            r20 = r18
            r18 = r1
            goto L_0x0684
        L_0x067e:
            r1 = 3
            r8 = r0
            r20 = r18
            r18 = r1
        L_0x0684:
            r0 = r50
            r1 = r4
            r21 = r2
            r2 = r3
            r22 = r3
            r3 = r12
            r23 = r4
            r4 = r8
            r35 = r5
            r5 = r20
            r0.setTimeZone(r1, r2, r3, r4, r5)
            r1 = r18
        L_0x0699:
            int r0 = r9.bp
            int r8 = r11 + 10
            int r8 = r8 + r17
            int r8 = r8 + r1
            int r0 = r0 + r8
            char r0 = r9.charAt(r0)
            r2 = 26
            if (r0 == r2) goto L_0x06af
            r2 = 34
            if (r0 == r2) goto L_0x06af
            r2 = 0
            return r2
        L_0x06af:
            int r2 = r9.bp
            int r8 = r11 + 10
            int r8 = r8 + r17
            int r8 = r8 + r1
            int r2 = r2 + r8
            r9.bp = r2
            char r2 = r9.charAt(r2)
            r9.ch = r2
            r2 = 5
            r9.token = r2
            r2 = 1
            return r2
        L_0x06c4:
            r20 = r1
            r22 = r3
            r23 = r4
            r35 = r5
        L_0x06cc:
            r0 = 0
            return r0
        L_0x06ce:
            r20 = r1
            r22 = r3
            r23 = r4
            r35 = r5
            r0 = 0
        L_0x06d7:
            return r0
        L_0x06d8:
            r47 = r1
            r48 = r2
            r33 = r3
            r49 = r5
            r24 = r6
            r37 = r7
            r0 = 0
            r8 = 48
            r12 = 13
            r18 = 9
        L_0x06eb:
            if (r51 == 0) goto L_0x06ee
            return r0
        L_0x06ee:
            int r0 = r9.bp
            r1 = 8
            int r0 = r0 + r1
            char r11 = r9.charAt(r0)
            r14 = r49
            r0 = 45
            if (r14 != r0) goto L_0x0703
            r7 = r47
            if (r7 != r0) goto L_0x0705
            r0 = 1
            goto L_0x0706
        L_0x0703:
            r7 = r47
        L_0x0705:
            r0 = 0
        L_0x0706:
            r19 = r0
            if (r19 == 0) goto L_0x0710
            r0 = 16
            if (r10 != r0) goto L_0x0710
            r0 = 1
            goto L_0x0711
        L_0x0710:
            r0 = 0
        L_0x0711:
            r26 = r0
            if (r19 == 0) goto L_0x071b
            r0 = 17
            if (r10 != r0) goto L_0x071b
            r0 = 1
            goto L_0x071c
        L_0x071b:
            r0 = 0
        L_0x071c:
            r28 = r0
            if (r28 != 0) goto L_0x076c
            if (r26 == 0) goto L_0x0725
            r6 = r48
            goto L_0x076e
        L_0x0725:
            r0 = 45
            if (r14 != r0) goto L_0x074c
            r6 = r48
            if (r6 != r0) goto L_0x074e
            r0 = r13
            r1 = r15
            r2 = r37
            r3 = r24
            r4 = 48
            r5 = r33
            r21 = 48
            r29 = r7
            r30 = r2
            r31 = r3
            r32 = r4
            r34 = r5
            r36 = r21
            r47 = r29
            r21 = r0
            r29 = r1
            goto L_0x0791
        L_0x074c:
            r6 = r48
        L_0x074e:
            r0 = r13
            r1 = r15
            r2 = r37
            r3 = r24
            r4 = r14
            r5 = r33
            r21 = r6
            r29 = r7
            r30 = r2
            r31 = r3
            r32 = r4
            r34 = r5
            r36 = r21
            r47 = r29
            r21 = r0
            r29 = r1
            goto L_0x0791
        L_0x076c:
            r6 = r48
        L_0x076e:
            r0 = r13
            r1 = r15
            r2 = r37
            r3 = r24
            r4 = r33
            r5 = r6
            r21 = r11
            int r8 = r9.bp
            int r8 = r8 + 9
            char r29 = r9.charAt(r8)
            r30 = r2
            r31 = r3
            r32 = r4
            r34 = r5
            r36 = r21
            r47 = r29
            r21 = r0
            r29 = r1
        L_0x0791:
            r39 = r21
            r40 = r29
            r41 = r30
            r42 = r31
            r43 = r32
            r44 = r34
            r45 = r36
            r46 = r47
            boolean r0 = checkDate(r39, r40, r41, r42, r43, r44, r45, r46)
            if (r0 != 0) goto L_0x07a9
            r0 = 0
            return r0
        L_0x07a9:
            r0 = r50
            r1 = r21
            r2 = r29
            r3 = r30
            r4 = r31
            r5 = r32
            r39 = r6
            r6 = r34
            r40 = r7
            r7 = r36
            r8 = r47
            r0.setCalendar(r1, r2, r3, r4, r5, r6, r7, r8)
            r0 = 8
            if (r10 == r0) goto L_0x08c4
            int r0 = r9.bp
            int r0 = r0 + 9
            char r7 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 10
            char r8 = r9.charAt(r0)
            int r0 = r9.bp
            r1 = 11
            int r0 = r0 + r1
            char r16 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + 12
            char r18 = r9.charAt(r0)
            int r0 = r9.bp
            int r0 = r0 + r12
            char r6 = r9.charAt(r0)
            if (r28 == 0) goto L_0x0805
            r0 = 84
            if (r8 != r0) goto L_0x0805
            r0 = 58
            if (r6 != r0) goto L_0x0805
            int r0 = r9.bp
            r1 = 16
            int r0 = r0 + r1
            char r0 = r9.charAt(r0)
            r1 = 90
            if (r0 == r1) goto L_0x0813
        L_0x0805:
            if (r26 == 0) goto L_0x0839
            r0 = 32
            if (r8 == r0) goto L_0x080f
            r0 = 84
            if (r8 != r0) goto L_0x0839
        L_0x080f:
            r0 = 58
            if (r6 != r0) goto L_0x0839
        L_0x0813:
            r0 = r16
            r1 = r18
            int r2 = r9.bp
            r3 = 14
            int r2 = r2 + r3
            char r2 = r9.charAt(r2)
            int r3 = r9.bp
            int r3 = r3 + 15
            char r3 = r9.charAt(r3)
            r4 = 48
            r5 = 48
            r22 = r0
            r27 = r1
            r35 = r2
            r41 = r3
            r42 = r4
            r43 = r5
            goto L_0x084d
        L_0x0839:
            r0 = r11
            r1 = r7
            r2 = r8
            r3 = r16
            r4 = r18
            r5 = r6
            r22 = r0
            r27 = r1
            r35 = r2
            r41 = r3
            r42 = r4
            r43 = r5
        L_0x084d:
            r0 = r50
            r1 = r22
            r2 = r27
            r3 = r35
            r4 = r41
            r5 = r42
            r44 = r6
            r6 = r43
            boolean r0 = r0.checkTime(r1, r2, r3, r4, r5, r6)
            if (r0 != 0) goto L_0x0865
            r0 = 0
            return r0
        L_0x0865:
            r0 = 17
            if (r10 != r0) goto L_0x08ad
            if (r28 != 0) goto L_0x08ad
            int r0 = r9.bp
            r1 = 14
            int r0 = r0 + r1
            char r0 = r9.charAt(r0)
            int r1 = r9.bp
            int r1 = r1 + 15
            char r1 = r9.charAt(r1)
            int r2 = r9.bp
            r3 = 16
            int r2 = r2 + r3
            char r2 = r9.charAt(r2)
            r3 = 48
            if (r0 < r3) goto L_0x08ab
            r4 = 57
            if (r0 <= r4) goto L_0x088f
            r3 = 0
            goto L_0x08ac
        L_0x088f:
            if (r1 < r3) goto L_0x08a9
            if (r1 <= r4) goto L_0x0895
            r3 = 0
            goto L_0x08aa
        L_0x0895:
            if (r2 < r3) goto L_0x08a7
            if (r2 <= r4) goto L_0x089a
            goto L_0x08a7
        L_0x089a:
            int r3 = r0 + -48
            int r3 = r3 * 100
            int r4 = r1 + -48
            int r4 = r4 * 10
            int r3 = r3 + r4
            int r4 = r2 + -48
            int r3 = r3 + r4
            goto L_0x08ae
        L_0x08a7:
            r3 = 0
            return r3
        L_0x08a9:
            r3 = 0
        L_0x08aa:
            return r3
        L_0x08ab:
            r3 = 0
        L_0x08ac:
            return r3
        L_0x08ad:
            r3 = 0
        L_0x08ae:
            int r0 = r22 + -48
            int r0 = r0 * 10
            int r1 = r27 + -48
            int r0 = r0 + r1
            int r1 = r35 + -48
            int r1 = r1 * 10
            int r2 = r41 + -48
            int r1 = r1 + r2
            int r2 = r42 + -48
            int r2 = r2 * 10
            int r4 = r43 + -48
            int r2 = r2 + r4
            goto L_0x08c8
        L_0x08c4:
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
        L_0x08c8:
            java.util.Calendar r4 = r9.calendar
            r5 = 11
            r4.set(r5, r0)
            java.util.Calendar r4 = r9.calendar
            r5 = 12
            r4.set(r5, r1)
            java.util.Calendar r4 = r9.calendar
            r4.set(r12, r2)
            java.util.Calendar r4 = r9.calendar
            r5 = 14
            r4.set(r5, r3)
            r4 = 5
            r9.token = r4
            r4 = 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanISO8601DateIfMatch(boolean, int):boolean");
    }

    /* access modifiers changed from: protected */
    public void setTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        this.calendar.set(11, ((h0 - '0') * 10) + (h1 - '0'));
        this.calendar.set(12, ((m0 - '0') * 10) + (m1 - '0'));
        this.calendar.set(13, ((s0 - '0') * 10) + (s1 - '0'));
    }

    /* access modifiers changed from: protected */
    public void setTimeZone(char timeZoneFlag, char t0, char t1) {
        setTimeZone(timeZoneFlag, t0, t1, '0', '0');
    }

    /* access modifiers changed from: protected */
    public void setTimeZone(char timeZoneFlag, char t0, char t1, char t3, char t4) {
        int timeZoneOffset = ((((t0 - '0') * 10) + (t1 - '0')) * 3600 * 1000) + ((((t3 - '0') * 10) + (t4 - '0')) * 60 * 1000);
        if (timeZoneFlag == '-') {
            timeZoneOffset = -timeZoneOffset;
        }
        if (this.calendar.getTimeZone().getRawOffset() != timeZoneOffset) {
            this.calendar.setTimeZone(new SimpleTimeZone(timeZoneOffset, Integer.toString(timeZoneOffset)));
        }
    }

    private boolean checkTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        if (h0 == '0') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 == '1') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 != '2' || h1 < '0' || h1 > '4') {
            return false;
        }
        if (m0 < '0' || m0 > '5') {
            if (!(m0 == '6' && m1 == '0')) {
                return false;
            }
        } else if (m1 < '0' || m1 > '9') {
            return false;
        }
        if (s0 < '0' || s0 > '5') {
            if (s0 == '6' && s1 == '0') {
                return true;
            }
            return false;
        } else if (s1 < '0' || s1 > '9') {
            return false;
        } else {
            return true;
        }
    }

    private void setCalendar(char y0, char y1, char y2, char y3, char M0, char M1, char d0, char d1) {
        Calendar instance = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar = instance;
        instance.set(1, ((y0 - '0') * 1000) + ((y1 - '0') * 100) + ((y2 - '0') * 10) + (y3 - '0'));
        this.calendar.set(2, (((M0 - '0') * 10) + (M1 - '0')) - 1);
        this.calendar.set(5, ((d0 - '0') * 10) + (d1 - '0'));
    }

    static boolean checkDate(char y0, char y1, char y2, char y3, char M0, char M1, int d0, int d1) {
        if (y0 < '0' || y0 > '9' || y1 < '0' || y1 > '9' || y2 < '0' || y2 > '9' || y3 < '0' || y3 > '9') {
            return false;
        }
        if (M0 == '0') {
            if (M1 < '1' || M1 > '9') {
                return false;
            }
        } else if (M0 != '1') {
            return false;
        } else {
            if (!(M1 == '0' || M1 == '1' || M1 == '2')) {
                return false;
            }
        }
        if (d0 == 48) {
            if (d1 < 49 || d1 > 57) {
                return false;
            }
            return true;
        } else if (d0 == 49 || d0 == 50) {
            if (d1 < 48 || d1 > 57) {
                return false;
            }
            return true;
        } else if (d0 != 51) {
            return false;
        } else {
            if (d1 == 48 || d1 == 49) {
                return true;
            }
            return false;
        }
    }

    public boolean isEOF() {
        int i = this.bp;
        int i2 = this.len;
        if (i != i2) {
            return this.ch == 26 && i + 1 >= i2;
        }
        return true;
    }

    public int scanFieldInt(char[] fieldName) {
        int index;
        char ch;
        char[] cArr = fieldName;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int index2 = this.bp + cArr.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index3);
            index3++;
        }
        boolean negative = ch2 == '-';
        if (negative) {
            ch2 = charAt(index3);
            index3++;
        }
        if (ch2 < '0' || ch2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int value = ch2 - '0';
        while (true) {
            index = index3 + 1;
            ch = charAt(index3);
            if (ch >= '0' && ch <= '9') {
                int value_10 = value * 10;
                if (value_10 < value) {
                    this.matchStat = -1;
                    return 0;
                }
                value = value_10 + (ch - '0');
                index3 = index;
            }
        }
        if (ch == '.') {
            this.matchStat = -1;
            return 0;
        } else if (value < 0) {
            this.matchStat = -1;
            return 0;
        } else {
            if (quote) {
                if (ch != '\"') {
                    this.matchStat = -1;
                    return 0;
                }
                ch = charAt(index);
                index++;
            }
            while (ch != ',' && ch != '}') {
                if (JSONLexerBase.isWhitespace(ch)) {
                    ch = charAt(index);
                    index++;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            int i = index - 1;
            this.bp = i;
            if (ch == ',') {
                int i2 = i + 1;
                this.bp = i2;
                this.ch = charAt(i2);
                this.matchStat = 3;
                this.token = 16;
                return negative ? -value : value;
            }
            if (ch == '}') {
                int i3 = index - 1;
                this.bp = i3;
                int i4 = i3 + 1;
                this.bp = i4;
                char ch3 = charAt(i4);
                while (true) {
                    if (ch3 == ',') {
                        this.token = 16;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        this.ch = charAt(i5);
                        break;
                    } else if (ch3 == ']') {
                        this.token = 15;
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        this.ch = charAt(i6);
                        break;
                    } else if (ch3 == '}') {
                        this.token = 13;
                        int i7 = this.bp + 1;
                        this.bp = i7;
                        this.ch = charAt(i7);
                        break;
                    } else if (ch3 == 26) {
                        this.token = 20;
                        break;
                    } else if (JSONLexerBase.isWhitespace(ch3)) {
                        int i8 = this.bp + 1;
                        this.bp = i8;
                        ch3 = charAt(i8);
                    } else {
                        this.bp = startPos;
                        this.ch = startChar;
                        this.matchStat = -1;
                        return 0;
                    }
                }
                this.matchStat = 4;
            }
            return negative ? -value : value;
        }
    }

    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        while (!charArrayCompare(this.text, this.bp, fieldName)) {
            if (JSONLexerBase.isWhitespace(this.ch)) {
                next();
                while (JSONLexerBase.isWhitespace(this.ch)) {
                    next();
                }
            } else {
                this.matchStat = -2;
                return stringDefaultValue();
            }
        }
        int index = this.bp + fieldName.length;
        int spaceCount = 0;
        int index2 = index + 1;
        char ch = charAt(index);
        if (ch != '\"') {
            while (JSONLexerBase.isWhitespace(ch)) {
                spaceCount++;
                ch = charAt(index2);
                index2++;
            }
            if (ch != '\"') {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        int startIndex = index2;
        int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, startIndex);
        if (endIndex != -1) {
            String stringVal = subString(startIndex, endIndex - startIndex);
            if (stringVal.indexOf(92) != -1) {
                while (true) {
                    int slashCount = 0;
                    int i = endIndex - 1;
                    while (i >= 0 && charAt(i) == '\\') {
                        slashCount++;
                        i--;
                    }
                    if (slashCount % 2 == 0) {
                        break;
                    }
                    endIndex = indexOf(StringUtil.DOUBLE_QUOTE, endIndex + 1);
                }
                int i2 = this.bp;
                int chars_len = endIndex - (((fieldName.length + i2) + 1) + spaceCount);
                stringVal = JSONLexerBase.readString(sub_chars(i2 + fieldName.length + 1 + spaceCount, chars_len), chars_len);
            }
            if ((this.features & Feature.TrimStringFieldValue.mask) != 0) {
                stringVal = stringVal.trim();
            }
            char ch2 = charAt(endIndex + 1);
            while (ch2 != ',' && ch2 != '}') {
                if (JSONLexerBase.isWhitespace(ch2)) {
                    endIndex++;
                    ch2 = charAt(endIndex + 1);
                } else {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
            }
            int i3 = endIndex + 1;
            this.bp = i3;
            this.ch = ch2;
            String strVal = stringVal;
            if (ch2 == ',') {
                int i4 = i3 + 1;
                this.bp = i4;
                this.ch = charAt(i4);
                this.matchStat = 3;
                return strVal;
            }
            int i5 = i3 + 1;
            this.bp = i5;
            char ch3 = charAt(i5);
            if (ch3 == ',') {
                this.token = 16;
                int i6 = this.bp + 1;
                this.bp = i6;
                this.ch = charAt(i6);
            } else if (ch3 == ']') {
                this.token = 15;
                int i7 = this.bp + 1;
                this.bp = i7;
                this.ch = charAt(i7);
            } else if (ch3 == '}') {
                this.token = 13;
                int i8 = this.bp + 1;
                this.bp = i8;
                this.ch = charAt(i8);
            } else if (ch3 == 26) {
                this.token = 20;
            } else {
                this.bp = startPos;
                this.ch = startChar;
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.matchStat = 4;
            return strVal;
        }
        throw new JSONException("unclosed str");
    }

    public Date scanFieldDate(char[] fieldName) {
        char ch;
        Date dateVal;
        int index;
        char[] cArr = fieldName;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return null;
        }
        int index2 = this.bp + cArr.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        if (ch2 == '\"') {
            int startIndex = index3;
            int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, startIndex);
            if (endIndex != -1) {
                this.bp = index3;
                if (scanISO8601DateIfMatch(false, endIndex - startIndex)) {
                    dateVal = this.calendar.getTime();
                    ch = charAt(endIndex + 1);
                    this.bp = startPos;
                    while (ch != ',' && ch != '}') {
                        if (JSONLexerBase.isWhitespace(ch)) {
                            endIndex++;
                            ch = charAt(endIndex + 1);
                        } else {
                            this.matchStat = -1;
                            return null;
                        }
                    }
                    this.bp = endIndex + 1;
                    this.ch = ch;
                } else {
                    this.bp = startPos;
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            char c = '0';
            if (ch2 == '-' || (ch2 >= '0' && ch2 <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (ch2 == '-') {
                    ch2 = charAt(index3);
                    negative = true;
                    index3++;
                }
                if (ch2 >= '0' && ch2 <= '9') {
                    long millis2 = (long) (ch2 - '0');
                    while (true) {
                        index = index3 + 1;
                        ch2 = charAt(index3);
                        if (ch2 < c || ch2 > '9') {
                            long millis3 = millis2;
                        } else {
                            long j = millis2;
                            millis2 = (10 * millis2) + ((long) (ch2 - '0'));
                            index3 = index;
                            c = '0';
                        }
                    }
                    long millis32 = millis2;
                    if (ch2 == ',' || ch2 == '}') {
                        this.bp = index - 1;
                    }
                    int i = index;
                    millis = millis32;
                }
                if (millis < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (negative) {
                    millis = -millis;
                }
                dateVal = new Date(millis);
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (ch == ',') {
            int i2 = this.bp + 1;
            this.bp = i2;
            this.ch = charAt(i2);
            this.matchStat = 3;
            this.token = 16;
            return dateVal;
        }
        int i3 = this.bp + 1;
        this.bp = i3;
        char ch3 = charAt(i3);
        if (ch3 == ',') {
            this.token = 16;
            int i4 = this.bp + 1;
            this.bp = i4;
            this.ch = charAt(i4);
        } else if (ch3 == ']') {
            this.token = 15;
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
        } else if (ch3 == '}') {
            this.token = 13;
            int i6 = this.bp + 1;
            this.bp = i6;
            this.ch = charAt(i6);
        } else if (ch3 == 26) {
            this.token = 20;
        } else {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return null;
        }
        this.matchStat = 4;
        return dateVal;
    }

    public long scanFieldSymbol(char[] fieldName) {
        this.matchStat = 0;
        while (!charArrayCompare(this.text, this.bp, fieldName)) {
            if (JSONLexerBase.isWhitespace(this.ch)) {
                next();
                while (JSONLexerBase.isWhitespace(this.ch)) {
                    next();
                }
            } else {
                this.matchStat = -2;
                return 0;
            }
        }
        int index = this.bp + fieldName.length;
        int spaceCount = 0;
        int index2 = index + 1;
        char ch = charAt(index);
        if (ch != '\"') {
            while (JSONLexerBase.isWhitespace(ch)) {
                ch = charAt(index2);
                spaceCount++;
                index2++;
            }
            if (ch != '\"') {
                this.matchStat = -1;
                return 0;
            }
        }
        long hash = -3750763034362895579L;
        while (true) {
            int index3 = index2 + 1;
            char ch2 = charAt(index2);
            if (ch2 == '\"') {
                this.bp = index3;
                char charAt = charAt(index3);
                char ch3 = charAt;
                this.ch = charAt;
                while (ch3 != ',') {
                    if (ch3 == '}') {
                        next();
                        skipWhitespace();
                        char ch4 = getCurrent();
                        if (ch4 == ',') {
                            this.token = 16;
                            int i = this.bp + 1;
                            this.bp = i;
                            this.ch = charAt(i);
                        } else if (ch4 == ']') {
                            this.token = 15;
                            int i2 = this.bp + 1;
                            this.bp = i2;
                            this.ch = charAt(i2);
                        } else if (ch4 == '}') {
                            this.token = 13;
                            int i3 = this.bp + 1;
                            this.bp = i3;
                            this.ch = charAt(i3);
                        } else if (ch4 == 26) {
                            this.token = 20;
                        } else {
                            this.matchStat = -1;
                            return 0;
                        }
                        this.matchStat = 4;
                        return hash;
                    } else if (JSONLexerBase.isWhitespace(ch3)) {
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        ch3 = charAt(i4);
                    } else {
                        this.matchStat = -1;
                        return 0;
                    }
                }
                int i5 = this.bp + 1;
                this.bp = i5;
                this.ch = charAt(i5);
                this.matchStat = 3;
                return hash;
            } else if (index3 > this.len) {
                this.matchStat = -1;
                return 0;
            } else {
                hash = (hash ^ ((long) ch2)) * 1099511628211L;
                index2 = index3;
            }
        }
    }

    public Collection<String> scanFieldStringArray(char[] fieldName, Class<?> type) {
        char c;
        char c2;
        int index;
        char ch;
        boolean space;
        int index2;
        int index3;
        char[] cArr = fieldName;
        this.matchStat = 0;
        while (true) {
            char c3 = this.ch;
            if (c3 != 10) {
                if (c3 != ' ') {
                    break;
                }
                Class<?> cls = type;
                c = JSONLexer.EOI;
            } else {
                Class<?> cls2 = type;
                c = JSONLexer.EOI;
            }
            int index4 = this.bp + 1;
            this.bp = index4;
            if (index4 >= this.len) {
                c2 = c;
            } else {
                c2 = this.text.charAt(index4);
            }
            this.ch = c2;
        }
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return null;
        }
        Collection<String> list = newCollectionByType(type);
        int startPos = this.bp;
        char startChar = this.ch;
        int index5 = this.bp + cArr.length;
        int index6 = index5 + 1;
        int i = -1;
        if (charAt(index5) == '[') {
            int index7 = index6 + 1;
            char ch2 = charAt(index6);
            while (true) {
                if (ch2 == '\"') {
                    int startIndex = index7;
                    int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, startIndex);
                    if (endIndex != i) {
                        String stringVal = subString(startIndex, endIndex - startIndex);
                        if (stringVal.indexOf(92) != i) {
                            while (true) {
                                int slashCount = 0;
                                int i2 = endIndex - 1;
                                while (i2 >= 0 && charAt(i2) == '\\') {
                                    slashCount++;
                                    i2--;
                                }
                                if (slashCount % 2 == 0) {
                                    break;
                                }
                                endIndex = indexOf(StringUtil.DOUBLE_QUOTE, endIndex + 1);
                            }
                            int chars_len = endIndex - startIndex;
                            stringVal = JSONLexerBase.readString(sub_chars(startIndex, chars_len), chars_len);
                        }
                        int index8 = endIndex + 1;
                        index2 = index8 + 1;
                        index3 = charAt(index8);
                        list.add(stringVal);
                    } else {
                        throw new JSONException("unclosed str");
                    }
                } else if (ch2 == 'n' && this.text.startsWith("ull", index7)) {
                    int index9 = index7 + 3;
                    index2 = index9 + 1;
                    index3 = charAt(index9);
                    list.add((Object) null);
                }
                if (index3 == 44) {
                    index7 = index2 + 1;
                    ch2 = charAt(index2);
                    i = -1;
                } else if (index3 == 93) {
                    index = index2 + 1;
                    ch = charAt(index2);
                    while (JSONLexerBase.isWhitespace(ch)) {
                        ch = charAt(index);
                        index++;
                    }
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            if (ch2 == ']' && list.size() == 0) {
                index = index7 + 1;
                ch = charAt(index7);
            } else {
                this.matchStat = -1;
                return null;
            }
        } else if (this.text.startsWith("ull", index6)) {
            int index10 = index6 + 3;
            index = index10 + 1;
            ch = charAt(index10);
            list = null;
        } else {
            this.matchStat = -1;
            return null;
        }
        this.bp = index;
        if (ch == ',') {
            this.ch = charAt(index);
            this.matchStat = 3;
            return list;
        } else if (ch == '}') {
            char ch3 = charAt(index);
            do {
                if (ch3 == ',') {
                    this.token = 16;
                    int i3 = this.bp + 1;
                    this.bp = i3;
                    this.ch = charAt(i3);
                } else if (ch3 == ']') {
                    this.token = 15;
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    this.ch = charAt(i4);
                } else if (ch3 == '}') {
                    this.token = 13;
                    int i5 = this.bp + 1;
                    this.bp = i5;
                    this.ch = charAt(i5);
                } else if (ch3 == 26) {
                    this.token = 20;
                    this.ch = ch3;
                } else {
                    space = false;
                    while (JSONLexerBase.isWhitespace(ch3)) {
                        int index11 = index + 1;
                        ch3 = charAt(index);
                        this.bp = index11;
                        space = true;
                        index = index11;
                    }
                }
                this.matchStat = 4;
                return list;
            } while (space);
            this.matchStat = -1;
            return null;
        } else {
            this.ch = startChar;
            this.bp = startPos;
            this.matchStat = -1;
            return null;
        }
    }

    public long scanFieldLong(char[] fieldName) {
        int index;
        char ch;
        char[] cArr = fieldName;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int index2 = this.bp + cArr.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index3);
            index3++;
        }
        boolean negative = false;
        if (ch2 == '-') {
            ch2 = charAt(index3);
            negative = true;
            index3++;
        }
        if (ch2 < '0' || ch2 > '9') {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return 0;
        }
        long value = (long) (ch2 - '0');
        while (true) {
            index = index3 + 1;
            ch = charAt(index3);
            if (ch >= '0' && ch <= '9') {
                value = (10 * value) + ((long) (ch - '0'));
                index3 = index;
            }
        }
        if (ch == '.') {
            this.matchStat = -1;
            return 0;
        }
        if (quote) {
            if (ch != '\"') {
                this.matchStat = -1;
                return 0;
            }
            ch = charAt(index);
            index++;
        }
        if (ch == ',' || ch == '}') {
            this.bp = index - 1;
        }
        if (!(value >= 0 || (value == Long.MIN_VALUE && negative))) {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return 0;
        }
        while (ch != ',') {
            if (ch == '}') {
                int i = 1;
                int i2 = this.bp + 1;
                this.bp = i2;
                char ch3 = charAt(i2);
                while (true) {
                    if (ch3 == ',') {
                        this.token = 16;
                        int i3 = this.bp + i;
                        this.bp = i3;
                        this.ch = charAt(i3);
                        break;
                    } else if (ch3 == ']') {
                        this.token = 15;
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        this.ch = charAt(i4);
                        break;
                    } else if (ch3 == '}') {
                        this.token = 13;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        this.ch = charAt(i5);
                        break;
                    } else if (ch3 == 26) {
                        this.token = 20;
                        break;
                    } else if (JSONLexerBase.isWhitespace(ch3)) {
                        i = 1;
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        ch3 = charAt(i6);
                    } else {
                        this.bp = startPos;
                        this.ch = startChar;
                        this.matchStat = -1;
                        return 0;
                    }
                }
                this.matchStat = 4;
                return negative ? -value : value;
            } else if (JSONLexerBase.isWhitespace(ch)) {
                this.bp = index;
                ch = charAt(index);
                index++;
            } else {
                this.matchStat = -1;
                return 0;
            }
        }
        int i7 = this.bp + 1;
        this.bp = i7;
        this.ch = charAt(i7);
        this.matchStat = 3;
        this.token = 16;
        return negative ? -value : value;
    }

    public boolean scanFieldBoolean(char[] fieldName) {
        boolean value;
        char ch;
        int index;
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int startPos = this.bp;
        int index2 = this.bp + fieldName.length;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index3);
            index3++;
        }
        if (ch2 == 't') {
            int index4 = index3 + 1;
            if (charAt(index3) != 114) {
                this.matchStat = -1;
                return false;
            }
            int index5 = index4 + 1;
            if (charAt(index4) != 117) {
                this.matchStat = -1;
                return false;
            }
            int index6 = index5 + 1;
            if (charAt(index5) != 101) {
                this.matchStat = -1;
                return false;
            }
            if (quote) {
                int index7 = index6 + 1;
                if (charAt(index6) != 34) {
                    this.matchStat = -1;
                    return false;
                }
                index6 = index7;
            }
            this.bp = index6;
            ch = charAt(index6);
            value = true;
        } else if (ch2 == 'f') {
            int index8 = index3 + 1;
            if (charAt(index3) != 97) {
                this.matchStat = -1;
                return false;
            }
            int index9 = index8 + 1;
            if (charAt(index8) != 108) {
                this.matchStat = -1;
                return false;
            }
            int index10 = index9 + 1;
            if (charAt(index9) != 115) {
                this.matchStat = -1;
                return false;
            }
            int index11 = index10 + 1;
            if (charAt(index10) != 101) {
                this.matchStat = -1;
                return false;
            }
            if (quote) {
                index = index11 + 1;
                if (charAt(index11) != 34) {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                index = index11;
            }
            this.bp = index;
            ch = charAt(index);
            value = false;
        } else if (ch2 == '1') {
            if (quote) {
                int index12 = index3 + 1;
                if (charAt(index3) != 34) {
                    this.matchStat = -1;
                    return false;
                }
                index3 = index12;
            }
            this.bp = index3;
            ch = charAt(index3);
            int i = index3;
            value = true;
        } else if (ch2 == '0') {
            if (quote) {
                int index13 = index3 + 1;
                if (charAt(index3) != 34) {
                    this.matchStat = -1;
                    return false;
                }
                index3 = index13;
            }
            this.bp = index3;
            ch = charAt(index3);
            int i2 = index3;
            value = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        while (true) {
            if (ch == ',') {
                int i3 = this.bp + 1;
                this.bp = i3;
                this.ch = charAt(i3);
                this.matchStat = 3;
                this.token = 16;
                break;
            } else if (ch == '}') {
                int i4 = this.bp + 1;
                this.bp = i4;
                char ch3 = charAt(i4);
                while (true) {
                    if (ch3 == ',') {
                        this.token = 16;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        this.ch = charAt(i5);
                        break;
                    } else if (ch3 == ']') {
                        this.token = 15;
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        this.ch = charAt(i6);
                        break;
                    } else if (ch3 == '}') {
                        this.token = 13;
                        int i7 = this.bp + 1;
                        this.bp = i7;
                        this.ch = charAt(i7);
                        break;
                    } else if (ch3 == 26) {
                        this.token = 20;
                        break;
                    } else if (JSONLexerBase.isWhitespace(ch3)) {
                        int i8 = this.bp + 1;
                        this.bp = i8;
                        ch3 = charAt(i8);
                    } else {
                        this.matchStat = -1;
                        return false;
                    }
                }
                this.matchStat = 4;
            } else if (JSONLexerBase.isWhitespace(ch)) {
                int i9 = this.bp + 1;
                this.bp = i9;
                ch = charAt(i9);
            } else {
                this.bp = startPos;
                char ch4 = charAt(startPos);
                this.matchStat = -1;
                return false;
            }
        }
        return value;
    }

    public final int scanInt(char expectNext) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int mark = this.bp;
        int offset2 = this.bp;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(offset2);
        while (JSONLexerBase.isWhitespace(chLocal2)) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        boolean negative = true;
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        if (chLocal2 != '-') {
            negative = false;
        }
        if (negative) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        if (chLocal2 < '0' || chLocal2 > '9') {
            if (chLocal2 == 'n') {
                int offset4 = offset3 + 1;
                if (charAt(offset3) == 117) {
                    int offset5 = offset4 + 1;
                    if (charAt(offset4) == 108) {
                        int offset6 = offset5 + 1;
                        if (charAt(offset5) == 108) {
                            this.matchStat = 5;
                            int offset7 = offset6 + 1;
                            char chLocal3 = charAt(offset6);
                            if (quote && chLocal3 == '\"') {
                                chLocal3 = charAt(offset7);
                                offset7++;
                            }
                            while (chLocal3 != ',') {
                                if (chLocal3 == ']') {
                                    this.bp = offset7;
                                    this.ch = charAt(offset7);
                                    this.matchStat = 5;
                                    this.token = 15;
                                    return 0;
                                } else if (JSONLexerBase.isWhitespace(chLocal3)) {
                                    chLocal3 = charAt(offset7);
                                    offset7++;
                                } else {
                                    this.matchStat = -1;
                                    return 0;
                                }
                            }
                            this.bp = offset7;
                            this.ch = charAt(offset7);
                            this.matchStat = 5;
                            this.token = 16;
                            return 0;
                        }
                        int i = offset6;
                    }
                } else {
                    int i2 = offset4;
                }
            }
            this.matchStat = -1;
            return 0;
        }
        int value = chLocal2 - '0';
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(offset3);
            if (chLocal >= '0' && chLocal <= '9') {
                int value_10 = value * 10;
                if (value_10 >= value) {
                    value = value_10 + (chLocal - '0');
                    offset3 = offset;
                } else {
                    throw new JSONException("parseInt error : " + subString(mark, offset - 1));
                }
            }
        }
        if (chLocal == '.') {
            this.matchStat = -1;
            return 0;
        }
        if (quote) {
            if (chLocal != '\"') {
                this.matchStat = -1;
                return 0;
            }
            chLocal = charAt(offset);
            offset++;
        }
        if (value < 0) {
            this.matchStat = -1;
            return 0;
        }
        while (chLocal != expectNext) {
            if (JSONLexerBase.isWhitespace(chLocal)) {
                chLocal = charAt(offset);
                offset++;
            } else {
                this.matchStat = -1;
                return negative ? -value : value;
            }
        }
        this.bp = offset;
        this.ch = charAt(offset);
        this.matchStat = 3;
        this.token = 16;
        return negative ? -value : value;
    }

    public double scanDouble(char seperator) {
        long intVal;
        int offset;
        char chLocal;
        boolean negative;
        int offset2;
        char chLocal2;
        int count;
        int start;
        double value;
        this.matchStat = 0;
        int offset3 = this.bp;
        int offset4 = offset3 + 1;
        char chLocal3 = charAt(offset3);
        boolean quote = chLocal3 == '\"';
        if (quote) {
            chLocal3 = charAt(offset4);
            offset4++;
        }
        boolean negative2 = chLocal3 == '-';
        if (negative2) {
            chLocal3 = charAt(offset4);
            offset4++;
        }
        char c = '0';
        if (chLocal3 >= '0') {
            char c2 = '9';
            if (chLocal3 <= '9') {
                char c3 = chLocal3;
                long intVal2 = (long) (chLocal3 - '0');
                while (true) {
                    offset = offset4 + 1;
                    chLocal = charAt(offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        long power = 1;
                    } else {
                        intVal2 = (10 * intVal) + ((long) (chLocal - '0'));
                        char c4 = chLocal;
                        offset4 = offset;
                    }
                }
                long power2 = 1;
                if (chLocal == '.') {
                    int offset5 = offset + 1;
                    char chLocal4 = charAt(offset);
                    if (chLocal4 >= '0' && chLocal4 <= '9') {
                        negative = negative2;
                        power2 = 10;
                        int offset6 = offset5;
                        intVal = (intVal * 10) + ((long) (chLocal4 - '0'));
                        while (true) {
                            offset = offset6 + 1;
                            chLocal = charAt(offset6);
                            if (chLocal < c || chLocal > c2) {
                                break;
                            }
                            intVal = (intVal * 10) + ((long) (chLocal - '0'));
                            power2 *= 10;
                            offset6 = offset;
                            c = '0';
                            c2 = '9';
                        }
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                } else {
                    negative = negative2;
                }
                boolean exp = chLocal2 == 'e' || chLocal2 == 'E';
                if (exp) {
                    int offset7 = offset2 + 1;
                    chLocal2 = charAt(offset2);
                    if (chLocal2 == '+' || chLocal2 == '-') {
                        chLocal2 = charAt(offset7);
                        offset2 = offset7 + 1;
                    } else {
                        offset2 = offset7;
                    }
                    while (chLocal2 >= '0' && chLocal2 <= '9') {
                        chLocal2 = charAt(offset2);
                        offset2++;
                    }
                }
                if (!quote) {
                    start = this.bp;
                    count = (offset2 - start) - 1;
                } else if (chLocal2 != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                } else {
                    int offset8 = offset2 + 1;
                    chLocal2 = charAt(offset2);
                    start = this.bp + 1;
                    count = (offset8 - start) - 2;
                    offset2 = offset8;
                }
                if (exp || count >= 18) {
                    value = Double.parseDouble(subString(start, count));
                } else {
                    long j = intVal;
                    value = ((double) intVal) / ((double) power2);
                    if (negative) {
                        value = -value;
                    }
                }
                if (chLocal2 == seperator) {
                    this.bp = offset2;
                    this.ch = charAt(offset2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                this.matchStat = -1;
                return value;
            }
        }
        char c5 = seperator;
        boolean z = negative2;
        if (chLocal3 == 'n') {
            int offset9 = offset4 + 1;
            if (charAt(offset4) == 117) {
                int offset10 = offset9 + 1;
                if (charAt(offset9) == 108) {
                    int offset11 = offset10 + 1;
                    if (charAt(offset10) == 108) {
                        this.matchStat = 5;
                        int offset12 = offset11 + 1;
                        char chLocal5 = charAt(offset11);
                        if (quote && chLocal5 == '\"') {
                            chLocal5 = charAt(offset12);
                            offset12++;
                        }
                        while (chLocal5 != ',') {
                            if (chLocal5 == ']') {
                                this.bp = offset12;
                                this.ch = charAt(offset12);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0.0d;
                            } else if (JSONLexerBase.isWhitespace(chLocal5)) {
                                chLocal5 = charAt(offset12);
                                offset12++;
                            } else {
                                this.matchStat = -1;
                                return 0.0d;
                            }
                        }
                        this.bp = offset12;
                        this.ch = charAt(offset12);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0.0d;
                    }
                    int i = offset11;
                }
            } else {
                int i2 = offset9;
            }
        }
        this.matchStat = -1;
        return 0.0d;
    }

    public long scanLong(char seperator) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int offset2 = this.bp;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        char c = '0';
        if (chLocal2 < '0' || chLocal2 > '9') {
            char c2 = seperator;
            if (chLocal2 == 'n') {
                int offset4 = offset3 + 1;
                if (charAt(offset3) == 117) {
                    int offset5 = offset4 + 1;
                    if (charAt(offset4) == 108) {
                        int offset6 = offset5 + 1;
                        if (charAt(offset5) == 108) {
                            this.matchStat = 5;
                            int offset7 = offset6 + 1;
                            char chLocal3 = charAt(offset6);
                            if (quote && chLocal3 == '\"') {
                                chLocal3 = charAt(offset7);
                                offset7++;
                            }
                            while (chLocal3 != ',') {
                                if (chLocal3 == ']') {
                                    this.bp = offset7;
                                    this.ch = charAt(offset7);
                                    this.matchStat = 5;
                                    this.token = 15;
                                    return 0;
                                } else if (JSONLexerBase.isWhitespace(chLocal3)) {
                                    chLocal3 = charAt(offset7);
                                    offset7++;
                                } else {
                                    this.matchStat = -1;
                                    return 0;
                                }
                            }
                            this.bp = offset7;
                            this.ch = charAt(offset7);
                            this.matchStat = 5;
                            this.token = 16;
                            return 0;
                        }
                        int i = offset6;
                    }
                } else {
                    int i2 = offset4;
                }
            }
            this.matchStat = -1;
            return 0;
        }
        long value = (long) (chLocal2 - '0');
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(offset3);
            if (chLocal >= c && chLocal <= '9') {
                value = (10 * value) + ((long) (chLocal - '0'));
                offset3 = offset;
                c = '0';
            }
        }
        if (chLocal == '.') {
            this.matchStat = -1;
            return 0;
        }
        if (quote) {
            if (chLocal != '\"') {
                this.matchStat = -1;
                return 0;
            }
            chLocal = charAt(offset);
            offset++;
        }
        if (!(value >= 0 || (value == Long.MIN_VALUE && negative))) {
            this.matchStat = -1;
            return 0;
        }
        while (chLocal != seperator) {
            if (JSONLexerBase.isWhitespace(chLocal)) {
                chLocal = charAt(offset);
                offset++;
            } else {
                this.matchStat = -1;
                return value;
            }
        }
        this.bp = offset;
        this.ch = charAt(offset);
        this.matchStat = 3;
        this.token = 16;
        return negative ? -value : value;
    }

    public Date scanDate(char seperator) {
        char ch;
        Date dateVal;
        int index;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        int index2 = this.bp;
        int index3 = index2 + 1;
        char ch2 = charAt(index2);
        if (ch2 == '\"') {
            int startIndex = index3;
            int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, startIndex);
            if (endIndex != -1) {
                this.bp = index3;
                if (scanISO8601DateIfMatch(false, endIndex - startIndex)) {
                    dateVal = this.calendar.getTime();
                    ch = charAt(endIndex + 1);
                    this.bp = startPos;
                    while (ch != ',' && ch != ']') {
                        if (JSONLexerBase.isWhitespace(ch)) {
                            endIndex++;
                            ch = charAt(endIndex + 1);
                        } else {
                            this.bp = startPos;
                            this.ch = startChar;
                            this.matchStat = -1;
                            return null;
                        }
                    }
                    this.bp = endIndex + 1;
                    this.ch = ch;
                } else {
                    this.bp = startPos;
                    this.ch = startChar;
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            char c = '0';
            if (ch2 == '-' || (ch2 >= '0' && ch2 <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (ch2 == '-') {
                    ch2 = charAt(index3);
                    negative = true;
                    index3++;
                }
                if (ch2 >= '0' && ch2 <= '9') {
                    long millis2 = (long) (ch2 - '0');
                    while (true) {
                        index = index3 + 1;
                        ch2 = charAt(index3);
                        if (ch2 < c || ch2 > '9') {
                            long millis3 = millis2;
                        } else {
                            long j = millis2;
                            millis2 = (10 * millis2) + ((long) (ch2 - '0'));
                            index3 = index;
                            c = '0';
                        }
                    }
                    long millis32 = millis2;
                    if (ch2 == ',' || ch2 == ']') {
                        this.bp = index - 1;
                    }
                    int i = index;
                    millis = millis32;
                }
                if (millis < 0) {
                    this.bp = startPos;
                    this.ch = startChar;
                    this.matchStat = -1;
                    return null;
                }
                if (negative) {
                    millis = -millis;
                }
                dateVal = new Date(millis);
            } else {
                if (ch2 == 'n') {
                    int index4 = index3 + 1;
                    if (charAt(index3) == 117) {
                        int index5 = index4 + 1;
                        if (charAt(index4) == 108) {
                            int index6 = index5 + 1;
                            if (charAt(index5) == 108) {
                                ch = charAt(index6);
                                this.bp = index6;
                                int i2 = index6;
                                dateVal = null;
                            } else {
                                int i3 = index6;
                            }
                        }
                    } else {
                        int i4 = index4;
                    }
                }
                this.bp = startPos;
                this.ch = startChar;
                this.matchStat = -1;
                return null;
            }
        }
        if (ch == ',') {
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
            this.matchStat = 3;
            return dateVal;
        }
        int i6 = this.bp + 1;
        this.bp = i6;
        char ch3 = charAt(i6);
        if (ch3 == ',') {
            this.token = 16;
            int i7 = this.bp + 1;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (ch3 == ']') {
            this.token = 15;
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = charAt(i8);
        } else if (ch3 == '}') {
            this.token = 13;
            int i9 = this.bp + 1;
            this.bp = i9;
            this.ch = charAt(i9);
        } else if (ch3 == 26) {
            this.ch = JSONLexer.EOI;
            this.token = 20;
        } else {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return null;
        }
        this.matchStat = 4;
        return dateVal;
    }

    /* access modifiers changed from: protected */
    public final void arrayCopy(int srcPos, char[] dest, int destPos, int length) {
        this.text.getChars(srcPos, srcPos + length, dest, destPos);
    }

    public String info() {
        StringBuilder buf = new StringBuilder();
        int line = 1;
        int column = 1;
        int i = 0;
        while (i < this.bp) {
            if (this.text.charAt(i) == 10) {
                column = 1;
                line++;
            }
            i++;
            column++;
        }
        buf.append("pos ");
        buf.append(this.bp);
        buf.append(", line ");
        buf.append(line);
        buf.append(", column ");
        buf.append(column);
        if (this.text.length() < 65535) {
            buf.append(this.text);
        } else {
            buf.append(this.text.substring(0, 65535));
        }
        return buf.toString();
    }

    public String[] scanFieldStringArray(char[] fieldName, int argTypesCount, SymbolTable typeSymbolTable) {
        int offset;
        char ch;
        char[] cArr = fieldName;
        int i = argTypesCount;
        int startPos = this.bp;
        char starChar = this.ch;
        while (JSONLexerBase.isWhitespace(this.ch)) {
            next();
        }
        String[] strArr = null;
        if (cArr != null) {
            this.matchStat = 0;
            if (!charArrayCompare(fieldName)) {
                this.matchStat = -2;
                return null;
            }
            int offset2 = this.bp + cArr.length;
            int offset3 = offset2 + 1;
            char ch2 = this.text.charAt(offset2);
            while (JSONLexerBase.isWhitespace(ch2)) {
                ch2 = this.text.charAt(offset3);
                offset3++;
            }
            if (ch2 == ':') {
                offset = offset3 + 1;
                ch = this.text.charAt(offset3);
                while (JSONLexerBase.isWhitespace(ch)) {
                    ch = this.text.charAt(offset);
                    offset++;
                }
            } else {
                this.matchStat = -1;
                return null;
            }
        } else {
            offset = this.bp + 1;
            ch = this.ch;
        }
        if (ch == '[') {
            this.bp = offset;
            this.ch = this.text.charAt(offset);
            String[] types = i >= 0 ? new String[i] : new String[4];
            int typeIndex = 0;
            while (true) {
                if (JSONLexerBase.isWhitespace(this.ch)) {
                    next();
                } else if (this.ch != '\"') {
                    this.bp = startPos;
                    this.ch = starChar;
                    this.matchStat = -1;
                    return strArr;
                } else {
                    String type = scanSymbol(typeSymbolTable, StringUtil.DOUBLE_QUOTE);
                    if (typeIndex == types.length) {
                        String[] array = new String[(types.length + (types.length >> 1) + 1)];
                        System.arraycopy(types, 0, array, 0, types.length);
                        types = array;
                    }
                    int typeIndex2 = typeIndex + 1;
                    types[typeIndex] = type;
                    while (JSONLexerBase.isWhitespace(this.ch)) {
                        next();
                    }
                    if (this.ch == ',') {
                        next();
                        typeIndex = typeIndex2;
                        strArr = null;
                    } else {
                        if (types.length != typeIndex2) {
                            String[] array2 = new String[typeIndex2];
                            System.arraycopy(types, 0, array2, 0, typeIndex2);
                            types = array2;
                        }
                        while (JSONLexerBase.isWhitespace(this.ch)) {
                            next();
                        }
                        if (this.ch == ']') {
                            next();
                            return types;
                        }
                        this.bp = startPos;
                        this.ch = starChar;
                        this.matchStat = -1;
                        return null;
                    }
                }
            }
        } else {
            SymbolTable symbolTable = typeSymbolTable;
            if (ch != 'n' || !this.text.startsWith("ull", this.bp + 1)) {
                this.matchStat = -1;
                return null;
            }
            int i2 = this.bp + 4;
            this.bp = i2;
            this.ch = this.text.charAt(i2);
            return null;
        }
    }

    public boolean matchField2(char[] fieldName) {
        while (JSONLexerBase.isWhitespace(this.ch)) {
            next();
        }
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int offset = this.bp + fieldName.length;
        int offset2 = offset + 1;
        char ch = this.text.charAt(offset);
        while (JSONLexerBase.isWhitespace(ch)) {
            ch = this.text.charAt(offset2);
            offset2++;
        }
        if (ch == ':') {
            this.bp = offset2;
            this.ch = charAt(offset2);
            return true;
        }
        this.matchStat = -2;
        return false;
    }

    public final void skipObject() {
        skipObject(false);
    }

    public final void skipObject(boolean valid) {
        boolean quote = false;
        int braceCnt = 0;
        int i = this.bp;
        while (i < this.text.length()) {
            char ch = this.text.charAt(i);
            if (ch == '\\') {
                if (i < this.len - 1) {
                    i++;
                } else {
                    this.ch = ch;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
            } else if (ch == '\"') {
                quote = !quote;
            } else if (ch == '{') {
                if (!quote) {
                    braceCnt++;
                }
            } else if (ch == '}' && !quote && braceCnt - 1 == -1) {
                int i2 = i + 1;
                this.bp = i2;
                int length = this.text.length();
                char c = JSONLexer.EOI;
                if (i2 == length) {
                    this.ch = JSONLexer.EOI;
                    this.token = 20;
                    return;
                }
                char charAt = this.text.charAt(this.bp);
                this.ch = charAt;
                if (charAt == ',') {
                    this.token = 16;
                    int index = this.bp + 1;
                    this.bp = index;
                    if (index < this.text.length()) {
                        c = this.text.charAt(index);
                    }
                    this.ch = c;
                    return;
                } else if (charAt == '}') {
                    this.token = 13;
                    next();
                    return;
                } else if (charAt == ']') {
                    this.token = 15;
                    next();
                    return;
                } else {
                    nextToken(16);
                    return;
                }
            }
            i++;
        }
        for (int j = 0; j < this.bp; j++) {
            if (j < this.text.length() && this.text.charAt(j) == ' ') {
                i++;
            }
        }
        if (i == this.text.length()) {
            throw new JSONException("illegal str, " + info());
        }
    }

    public final void skipArray() {
        skipArray(false);
    }

    public final void skipArray(boolean valid) {
        boolean quote = false;
        int bracketCnt = 0;
        int i = this.bp;
        while (i < this.text.length()) {
            char ch = this.text.charAt(i);
            if (ch == '\\') {
                if (i < this.len - 1) {
                    i++;
                } else {
                    this.ch = ch;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
            } else if (ch == '\"') {
                quote = !quote;
            } else if (ch != '[') {
                char c = JSONLexer.EOI;
                if (ch == '{' && valid) {
                    int index = this.bp + 1;
                    this.bp = index;
                    if (index < this.text.length()) {
                        c = this.text.charAt(index);
                    }
                    this.ch = c;
                    skipObject(valid);
                } else if (ch == ']' && !quote && bracketCnt - 1 == -1) {
                    int i2 = i + 1;
                    this.bp = i2;
                    if (i2 == this.text.length()) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    }
                    this.ch = this.text.charAt(this.bp);
                    nextToken(16);
                    return;
                }
            } else if (!quote) {
                bracketCnt++;
            }
            i++;
        }
        if (i == this.text.length()) {
            throw new JSONException("illegal str, " + info());
        }
    }

    public final void skipString() {
        if (this.ch == '\"') {
            int i = this.bp;
            while (true) {
                i++;
                if (i < this.text.length()) {
                    char c = this.text.charAt(i);
                    if (c == '\\') {
                        if (i < this.len - 1) {
                            i++;
                        }
                    } else if (c == '\"') {
                        String str = this.text;
                        int i2 = i + 1;
                        this.bp = i2;
                        this.ch = str.charAt(i2);
                        return;
                    }
                } else {
                    throw new JSONException("unclosed str");
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean seekArrayToItem(int index) {
        if (index >= 0) {
            int i = this.token;
            if (i == 20) {
                return false;
            }
            if (i == 14) {
                for (int i2 = 0; i2 < index; i2++) {
                    skipWhitespace();
                    char c = this.ch;
                    if (c == '\"' || c == '\'') {
                        skipString();
                        char c2 = this.ch;
                        if (c2 == ',') {
                            next();
                        } else if (c2 == ']') {
                            next();
                            nextToken(16);
                            return false;
                        } else {
                            throw new JSONException("illegal json.");
                        }
                    } else {
                        if (c == '{') {
                            next();
                            this.token = 12;
                            skipObject(false);
                        } else if (c == '[') {
                            next();
                            this.token = 14;
                            skipArray(false);
                        } else {
                            boolean match = false;
                            int j = this.bp + 1;
                            while (true) {
                                if (j >= this.text.length()) {
                                    break;
                                }
                                char c3 = this.text.charAt(j);
                                if (c3 == ',') {
                                    match = true;
                                    int i3 = j + 1;
                                    this.bp = i3;
                                    this.ch = charAt(i3);
                                    break;
                                } else if (c3 == ']') {
                                    int i4 = j + 1;
                                    this.bp = i4;
                                    this.ch = charAt(i4);
                                    nextToken();
                                    return false;
                                } else {
                                    j++;
                                }
                            }
                            if (!match) {
                                throw new JSONException("illegal json.");
                            }
                        }
                        int i5 = this.token;
                        if (i5 != 16) {
                            if (i5 == 15) {
                                return false;
                            }
                            throw new UnsupportedOperationException();
                        }
                    }
                }
                nextToken();
                return true;
            }
            throw new UnsupportedOperationException();
        }
        throw new IllegalArgumentException("index must > 0, but " + index);
    }

    public int seekObjectToField(long fieldNameHash, boolean deepScan) {
        char c;
        char c2;
        char c3;
        char c4;
        char c5;
        char c6;
        char c7;
        char c8;
        char c9;
        int i = this.token;
        int i2 = -1;
        if (i == 20) {
            return -1;
        }
        int i3 = 13;
        if (i != 13) {
            int i4 = 15;
            if (i != 15) {
                int i5 = 16;
                if (i == 12 || i == 16) {
                    while (true) {
                        char c10 = this.ch;
                        if (c10 == '}') {
                            next();
                            nextToken();
                            return i2;
                        } else if (c10 == 26) {
                            return i2;
                        } else {
                            if (c10 != '\"') {
                                skipWhitespace();
                            }
                            if (this.ch == '\"') {
                                long hash = -3750763034362895579L;
                                int i6 = this.bp + 1;
                                while (true) {
                                    if (i6 >= this.text.length()) {
                                        break;
                                    }
                                    char c11 = this.text.charAt(i6);
                                    if (c11 == '\\') {
                                        i6++;
                                        if (i6 != this.text.length()) {
                                            c11 = this.text.charAt(i6);
                                        } else {
                                            throw new JSONException("unclosed str, " + info());
                                        }
                                    }
                                    if (c11 == '\"') {
                                        int i7 = i6 + 1;
                                        this.bp = i7;
                                        if (i7 >= this.text.length()) {
                                            c9 = 26;
                                        } else {
                                            c9 = this.text.charAt(this.bp);
                                        }
                                        this.ch = c9;
                                    } else {
                                        hash = (hash ^ ((long) c11)) * 1099511628211L;
                                        i6++;
                                    }
                                }
                                if (hash == fieldNameHash) {
                                    if (this.ch != ':') {
                                        skipWhitespace();
                                    }
                                    if (this.ch != ':') {
                                        return 3;
                                    }
                                    int index = this.bp + 1;
                                    this.bp = index;
                                    if (index >= this.text.length()) {
                                        c = JSONLexer.EOI;
                                    } else {
                                        c = this.text.charAt(index);
                                    }
                                    this.ch = c;
                                    if (c == ',') {
                                        int index2 = this.bp + 1;
                                        this.bp = index2;
                                        if (index2 >= this.text.length()) {
                                            c4 = JSONLexer.EOI;
                                        } else {
                                            c4 = this.text.charAt(index2);
                                        }
                                        this.ch = c4;
                                        this.token = i5;
                                        return 3;
                                    } else if (c == ']') {
                                        int index3 = this.bp + 1;
                                        this.bp = index3;
                                        if (index3 >= this.text.length()) {
                                            c3 = JSONLexer.EOI;
                                        } else {
                                            c3 = this.text.charAt(index3);
                                        }
                                        this.ch = c3;
                                        this.token = i4;
                                        return 3;
                                    } else if (c == '}') {
                                        int index4 = this.bp + 1;
                                        this.bp = index4;
                                        if (index4 >= this.text.length()) {
                                            c2 = JSONLexer.EOI;
                                        } else {
                                            c2 = this.text.charAt(index4);
                                        }
                                        this.ch = c2;
                                        this.token = i3;
                                        return 3;
                                    } else if (c < '0' || c > '9') {
                                        nextToken(2);
                                        return 3;
                                    } else {
                                        this.sp = 0;
                                        this.pos = this.bp;
                                        scanNumber();
                                        return 3;
                                    }
                                } else {
                                    if (this.ch != ':') {
                                        skipWhitespace();
                                    }
                                    if (this.ch == ':') {
                                        int index5 = this.bp + 1;
                                        this.bp = index5;
                                        if (index5 >= this.text.length()) {
                                            c5 = JSONLexer.EOI;
                                        } else {
                                            c5 = this.text.charAt(index5);
                                        }
                                        this.ch = c5;
                                        if (!(c5 == '\"' || c5 == '\'' || c5 == '{' || c5 == '[' || c5 == '0' || c5 == '1' || c5 == '2' || c5 == '3' || c5 == '4' || c5 == '5' || c5 == '6' || c5 == '7' || c5 == '8' || c5 == '9' || c5 == '+' || c5 == '-')) {
                                            skipWhitespace();
                                        }
                                        char c12 = this.ch;
                                        if (c12 == '-' || c12 == '+') {
                                            i3 = 13;
                                            c6 = 12;
                                        } else if (c12 < '0' || c12 > '9') {
                                            if (c12 == '\"') {
                                                skipString();
                                                char c13 = this.ch;
                                                if (!(c13 == ',' || c13 == '}')) {
                                                    skipWhitespace();
                                                }
                                                if (this.ch == ',') {
                                                    next();
                                                    i3 = 13;
                                                    c6 = 12;
                                                } else {
                                                    i3 = 13;
                                                    c6 = 12;
                                                }
                                            } else if (c12 == 't') {
                                                next();
                                                if (this.ch == 'r') {
                                                    next();
                                                    if (this.ch == 'u') {
                                                        next();
                                                        if (this.ch == 'e') {
                                                            next();
                                                        }
                                                    }
                                                }
                                                char c14 = this.ch;
                                                if (!(c14 == ',' || c14 == '}')) {
                                                    skipWhitespace();
                                                }
                                                if (this.ch == ',') {
                                                    next();
                                                    i3 = 13;
                                                    c6 = 12;
                                                } else {
                                                    i3 = 13;
                                                    c6 = 12;
                                                }
                                            } else if (c12 == 'n') {
                                                next();
                                                if (this.ch == 'u') {
                                                    next();
                                                    if (this.ch == 'l') {
                                                        next();
                                                        if (this.ch == 'l') {
                                                            next();
                                                        }
                                                    }
                                                }
                                                char c15 = this.ch;
                                                if (!(c15 == ',' || c15 == '}')) {
                                                    skipWhitespace();
                                                }
                                                if (this.ch == ',') {
                                                    next();
                                                    i3 = 13;
                                                    c6 = 12;
                                                } else {
                                                    i3 = 13;
                                                    c6 = 12;
                                                }
                                            } else if (c12 == 'f') {
                                                next();
                                                if (this.ch == 'a') {
                                                    next();
                                                    if (this.ch == 'l') {
                                                        next();
                                                        if (this.ch == 's') {
                                                            next();
                                                            if (this.ch == 'e') {
                                                                next();
                                                            }
                                                        }
                                                    }
                                                }
                                                char c16 = this.ch;
                                                if (!(c16 == ',' || c16 == '}')) {
                                                    skipWhitespace();
                                                }
                                                if (this.ch == ',') {
                                                    next();
                                                    i3 = 13;
                                                    c6 = 12;
                                                } else {
                                                    i3 = 13;
                                                    c6 = 12;
                                                }
                                            } else if (c12 == '{') {
                                                int index6 = this.bp + 1;
                                                this.bp = index6;
                                                if (index6 >= this.text.length()) {
                                                    c8 = JSONLexer.EOI;
                                                } else {
                                                    c8 = this.text.charAt(index6);
                                                }
                                                this.ch = c8;
                                                if (deepScan) {
                                                    this.token = 12;
                                                    return 1;
                                                }
                                                c6 = 12;
                                                skipObject(false);
                                                if (this.token == 13) {
                                                    return -1;
                                                }
                                                i3 = 13;
                                            } else {
                                                c6 = 12;
                                                if (c12 == '[') {
                                                    next();
                                                    if (deepScan) {
                                                        this.token = 14;
                                                        return 2;
                                                    }
                                                    skipArray(false);
                                                    i3 = 13;
                                                    if (this.token == 13) {
                                                        return -1;
                                                    }
                                                } else {
                                                    throw new UnsupportedOperationException();
                                                }
                                            }
                                            char c17 = c6;
                                            i2 = -1;
                                            i4 = 15;
                                            i5 = 16;
                                        } else {
                                            i3 = 13;
                                            c6 = 12;
                                        }
                                        next();
                                        while (true) {
                                            c7 = this.ch;
                                            if (c7 >= '0' && c7 <= '9') {
                                                next();
                                            }
                                        }
                                        if (c7 == '.') {
                                            next();
                                            while (true) {
                                                char c18 = this.ch;
                                                if (c18 < '0' || c18 > '9') {
                                                    break;
                                                }
                                                next();
                                            }
                                        }
                                        char c19 = this.ch;
                                        if (c19 == 'E' || c19 == 'e') {
                                            next();
                                            char c20 = this.ch;
                                            if (c20 == '-' || c20 == '+') {
                                                next();
                                            }
                                            while (true) {
                                                char c21 = this.ch;
                                                if (c21 < '0' || c21 > '9') {
                                                    break;
                                                }
                                                next();
                                            }
                                        }
                                        if (this.ch != ',') {
                                            skipWhitespace();
                                        }
                                        if (this.ch == ',') {
                                            next();
                                        }
                                        char c172 = c6;
                                        i2 = -1;
                                        i4 = 15;
                                        i5 = 16;
                                    } else {
                                        throw new JSONException("illegal json, " + info());
                                    }
                                }
                            } else {
                                throw new UnsupportedOperationException();
                            }
                        }
                    }
                } else {
                    throw new UnsupportedOperationException(JSONToken.name(this.token));
                }
            }
        }
        nextToken();
        return -1;
    }

    public int seekObjectToField(long[] fieldNameHash) {
        char c;
        char c2;
        char c3;
        char c4;
        char c5;
        long[] jArr = fieldNameHash;
        int i = this.token;
        int i2 = 16;
        if (i == 12 || i == 16) {
            while (true) {
                char c6 = this.ch;
                if (c6 == '}') {
                    next();
                    nextToken();
                    this.matchStat = -1;
                    return -1;
                }
                char c7 = JSONLexer.EOI;
                if (c6 == 26) {
                    this.matchStat = -1;
                    return -1;
                }
                if (c6 != '\"') {
                    skipWhitespace();
                }
                if (this.ch == '\"') {
                    long hash = -3750763034362895579L;
                    int i3 = this.bp;
                    while (true) {
                        i3++;
                        if (i3 >= this.text.length()) {
                            break;
                        }
                        char c8 = this.text.charAt(i3);
                        if (c8 == '\\') {
                            i3++;
                            if (i3 != this.text.length()) {
                                c8 = this.text.charAt(i3);
                            } else {
                                throw new JSONException("unclosed str, " + info());
                            }
                        }
                        if (c8 == '\"') {
                            int i4 = i3 + 1;
                            this.bp = i4;
                            if (i4 >= this.text.length()) {
                                c5 = 26;
                            } else {
                                c5 = this.text.charAt(this.bp);
                            }
                            this.ch = c5;
                        } else {
                            hash = (hash ^ ((long) c8)) * 1099511628211L;
                        }
                    }
                    int matchIndex = -1;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= jArr.length) {
                            break;
                        } else if (hash == jArr[i5]) {
                            matchIndex = i5;
                            break;
                        } else {
                            i5++;
                        }
                    }
                    if (matchIndex != -1) {
                        if (this.ch != ':') {
                            skipWhitespace();
                        }
                        if (this.ch == ':') {
                            int index = this.bp + 1;
                            this.bp = index;
                            if (index >= this.text.length()) {
                                c = 26;
                            } else {
                                c = this.text.charAt(index);
                            }
                            this.ch = c;
                            if (c == ',') {
                                int index2 = this.bp + 1;
                                this.bp = index2;
                                if (index2 < this.text.length()) {
                                    c7 = this.text.charAt(index2);
                                }
                                this.ch = c7;
                                this.token = i2;
                            } else if (c == ']') {
                                int index3 = this.bp + 1;
                                this.bp = index3;
                                if (index3 < this.text.length()) {
                                    c7 = this.text.charAt(index3);
                                }
                                this.ch = c7;
                                this.token = 15;
                            } else if (c == '}') {
                                int index4 = this.bp + 1;
                                this.bp = index4;
                                if (index4 < this.text.length()) {
                                    c7 = this.text.charAt(index4);
                                }
                                this.ch = c7;
                                this.token = 13;
                            } else if (c < '0' || c > '9') {
                                nextToken(2);
                            } else {
                                this.sp = 0;
                                this.pos = this.bp;
                                scanNumber();
                            }
                        }
                        this.matchStat = 3;
                        return matchIndex;
                    }
                    if (this.ch != ':') {
                        skipWhitespace();
                    }
                    if (this.ch == ':') {
                        int index5 = this.bp + 1;
                        this.bp = index5;
                        if (index5 >= this.text.length()) {
                            c2 = 26;
                        } else {
                            c2 = this.text.charAt(index5);
                        }
                        this.ch = c2;
                        if (!(c2 == '\"' || c2 == '\'' || c2 == '{' || c2 == '[' || c2 == '0' || c2 == '1' || c2 == '2' || c2 == '3' || c2 == '4' || c2 == '5' || c2 == '6' || c2 == '7' || c2 == '8' || c2 == '9' || c2 == '+' || c2 == '-')) {
                            skipWhitespace();
                        }
                        char c9 = this.ch;
                        if (c9 == '-' || c9 == '+' || (c9 >= '0' && c9 <= '9')) {
                            next();
                            while (true) {
                                c3 = this.ch;
                                if (c3 >= '0' && c3 <= '9') {
                                    next();
                                }
                            }
                            if (c3 == '.') {
                                next();
                                while (true) {
                                    char c10 = this.ch;
                                    if (c10 < '0' || c10 > '9') {
                                        break;
                                    }
                                    next();
                                }
                            }
                            char c11 = this.ch;
                            if (c11 == 'E' || c11 == 'e') {
                                next();
                                char c12 = this.ch;
                                if (c12 == '-' || c12 == '+') {
                                    next();
                                }
                                while (true) {
                                    char c13 = this.ch;
                                    if (c13 < '0' || c13 > '9') {
                                        break;
                                    }
                                    next();
                                }
                            }
                            if (this.ch != ',') {
                                skipWhitespace();
                            }
                            if (this.ch == ',') {
                                next();
                            }
                        } else if (c9 == '\"') {
                            skipString();
                            char c14 = this.ch;
                            if (!(c14 == ',' || c14 == '}')) {
                                skipWhitespace();
                            }
                            if (this.ch == ',') {
                                next();
                            }
                        } else if (c9 == '{') {
                            int index6 = this.bp + 1;
                            this.bp = index6;
                            if (index6 >= this.text.length()) {
                                c4 = JSONLexer.EOI;
                            } else {
                                c4 = this.text.charAt(index6);
                            }
                            this.ch = c4;
                            skipObject(false);
                        } else if (c9 == '[') {
                            next();
                            skipArray(false);
                        } else {
                            throw new UnsupportedOperationException();
                        }
                        i2 = 16;
                    } else {
                        throw new JSONException("illegal json, " + info());
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public String scanTypeName(SymbolTable symbolTable) {
        int p;
        if (!this.text.startsWith("\"@type\":\"", this.bp) || (p = this.text.indexOf(34, this.bp + 9)) == -1) {
            return null;
        }
        this.bp += 9;
        int h = 0;
        for (int i = this.bp; i < p; i++) {
            h = (h * 31) + this.text.charAt(i);
        }
        int i2 = this.bp;
        String typeName = addSymbol(i2, p - i2, h, symbolTable);
        char separator = this.text.charAt(p + 1);
        if (separator != ',' && separator != ']') {
            return null;
        }
        int i3 = p + 2;
        this.bp = i3;
        this.ch = this.text.charAt(i3);
        return typeName;
    }
}
