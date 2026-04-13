package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import com.tencent.bugly.Bugly;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public abstract class JSONLexerBase implements JSONLexer, Closeable {
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    private static final ThreadLocal<char[]> SBUF_LOCAL = new ThreadLocal<>();
    protected static final int[] digits = new int[103];
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected int bp;
    protected Calendar calendar = null;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected Locale locale = JSON.defaultLocale;
    public int matchStat = 0;
    protected int nanos = 0;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected int token;

    public abstract String addSymbol(int i, int i2, int i3, SymbolTable symbolTable);

    /* access modifiers changed from: protected */
    public abstract void arrayCopy(int i, char[] cArr, int i2, int i3);

    public abstract byte[] bytesValue();

    /* access modifiers changed from: protected */
    public abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i);

    /* access modifiers changed from: protected */
    public abstract void copyTo(int i, int i2, char[] cArr);

    public abstract BigDecimal decimalValue();

    public abstract int indexOf(char c, int i);

    public abstract boolean isEOF();

    public abstract char next();

    public abstract String numberString();

    public abstract String stringVal();

    public abstract String subString(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract char[] sub_chars(int i, int i2);

    /* access modifiers changed from: protected */
    public void lexError(String key, Object... args) {
        this.token = 1;
    }

    static {
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = (i2 - 97) + 10;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = (i3 - 65) + 10;
        }
    }

    public JSONLexerBase(int features2) {
        this.features = features2;
        if ((Feature.InitStringFieldAsEmpty.mask & features2) != 0) {
            this.stringDefaultValue = "";
        }
        char[] cArr = SBUF_LOCAL.get();
        this.sbuf = cArr;
        if (cArr == null) {
            this.sbuf = new char[512];
        }
    }

    public final int matchStat() {
        return this.matchStat;
    }

    public void setToken(int token2) {
        this.token = token2;
    }

    public final void nextToken() {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            char c = this.ch;
            if (c == '/') {
                skipComment();
            } else if (c == '\"') {
                scanString();
                return;
            } else if (c == ',') {
                next();
                this.token = 16;
                return;
            } else if (c >= '0' && c <= '9') {
                scanNumber();
                return;
            } else if (c == '-') {
                scanNumber();
                return;
            } else {
                switch (c) {
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                        next();
                        break;
                    case '\'':
                        if (isEnabled(Feature.AllowSingleQuotes)) {
                            scanStringSingleQuote();
                            return;
                        }
                        throw new JSONException("Feature.AllowSingleQuotes is false");
                    case '(':
                        next();
                        this.token = 10;
                        return;
                    case ')':
                        next();
                        this.token = 11;
                        return;
                    case '+':
                        next();
                        scanNumber();
                        return;
                    case '.':
                        next();
                        this.token = 25;
                        return;
                    case ':':
                        next();
                        this.token = 17;
                        return;
                    case ';':
                        next();
                        this.token = 24;
                        return;
                    case 'N':
                    case 'S':
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        next();
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        scanFalse();
                        return;
                    case 'n':
                        scanNullOrNew();
                        return;
                    case 't':
                        scanTrue();
                        return;
                    case 'x':
                        scanHex();
                        return;
                    case '{':
                        next();
                        this.token = 12;
                        return;
                    case '}':
                        next();
                        this.token = 13;
                        return;
                    default:
                        if (!isEOF()) {
                            char c2 = this.ch;
                            if (c2 <= 31 || c2 == 127) {
                                next();
                                break;
                            } else {
                                lexError("illegal.char", String.valueOf(c2));
                                next();
                                return;
                            }
                        } else if (this.token != 20) {
                            this.token = 20;
                            int i = this.bp;
                            this.pos = i;
                            this.eofPos = i;
                            return;
                        } else {
                            throw new JSONException("EOF error");
                        }
                }
            }
        }
    }

    public final void nextToken(int expect) {
        this.sp = 0;
        while (true) {
            switch (expect) {
                case 2:
                    char c = this.ch;
                    if (c >= '0' && c <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                        return;
                    } else if (c == '\"') {
                        this.pos = this.bp;
                        scanString();
                        return;
                    } else if (c == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (c == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                    break;
                case 4:
                    char c2 = this.ch;
                    if (c2 == '\"') {
                        this.pos = this.bp;
                        scanString();
                        return;
                    } else if (c2 >= '0' && c2 <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                        return;
                    } else if (c2 == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (c2 == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                    break;
                case 12:
                    char c3 = this.ch;
                    if (c3 == '{') {
                        this.token = 12;
                        next();
                        return;
                    } else if (c3 == '[') {
                        this.token = 14;
                        next();
                        return;
                    }
                    break;
                case 14:
                    char c4 = this.ch;
                    if (c4 == '[') {
                        this.token = 14;
                        next();
                        return;
                    } else if (c4 == '{') {
                        this.token = 12;
                        next();
                        return;
                    }
                    break;
                case 15:
                    if (this.ch == ']') {
                        this.token = 15;
                        next();
                        return;
                    }
                    break;
                case 16:
                    char c5 = this.ch;
                    if (c5 == ',') {
                        this.token = 16;
                        next();
                        return;
                    } else if (c5 == '}') {
                        this.token = 13;
                        next();
                        return;
                    } else if (c5 == ']') {
                        this.token = 15;
                        next();
                        return;
                    } else if (c5 == 26) {
                        this.token = 20;
                        return;
                    } else if (c5 == 'n') {
                        scanNullOrNew(false);
                        return;
                    }
                    break;
                case 18:
                    nextIdent();
                    return;
                case 20:
                    break;
            }
            if (this.ch == 26) {
                this.token = 20;
                return;
            }
            char c6 = this.ch;
            if (c6 == ' ' || c6 == 10 || c6 == 13 || c6 == 9 || c6 == 12 || c6 == 8) {
                next();
            } else {
                nextToken();
                return;
            }
        }
    }

    public final void nextIdent() {
        while (isWhitespace(this.ch)) {
            next();
        }
        char c = this.ch;
        if (c == '_' || c == '$' || Character.isLetter(c)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    public final void nextTokenWithColon() {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithChar(char expect) {
        this.sp = 0;
        while (true) {
            char c = this.ch;
            if (c == expect) {
                next();
                nextToken();
                return;
            } else if (c == ' ' || c == 10 || c == 13 || c == 9 || c == 12 || c == 8) {
                next();
            } else {
                throw new JSONException("not match " + expect + " - " + this.ch + ", info : " + info());
            }
        }
    }

    public final int token() {
        return this.token;
    }

    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    public final int pos() {
        return this.pos;
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    public final Number integerValue() {
        long limit;
        long result = 0;
        boolean negative = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i = this.np;
        int max = this.np + this.sp;
        char type = ' ';
        switch (charAt(max - 1)) {
            case 'B':
                max--;
                type = 'B';
                break;
            case 'L':
                max--;
                type = Constants.OBJECT_TYPE;
                break;
            case 'S':
                max--;
                type = 'S';
                break;
        }
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i++;
        } else {
            limit = -9223372036854775807L;
        }
        if (i < max) {
            result = (long) (-(charAt(i) - 48));
            i++;
        }
        while (i < max) {
            int i2 = i + 1;
            int digit = charAt(i) - 48;
            if (result < MULTMIN_RADIX_TEN) {
                return new BigInteger(numberString(), 10);
            }
            long result2 = result * 10;
            if (result2 < ((long) digit) + limit) {
                return new BigInteger(numberString(), 10);
            }
            result = result2 - ((long) digit);
            i = i2;
        }
        if (!negative) {
            long result3 = -result;
            if (result3 > 2147483647L || type == 'L') {
                return Long.valueOf(result3);
            }
            if (type == 'S') {
                return Short.valueOf((short) ((int) result3));
            }
            if (type == 'B') {
                return Byte.valueOf((byte) ((int) result3));
            }
            return Integer.valueOf((int) result3);
        } else if (i <= this.np + 1) {
            throw new NumberFormatException(numberString());
        } else if (result < -2147483648L || type == 'L') {
            return Long.valueOf(result);
        } else {
            if (type == 'S') {
                return Short.valueOf((short) ((int) result));
            }
            if (type == 'B') {
                return Byte.valueOf((byte) ((int) result));
            }
            return Integer.valueOf((int) result);
        }
    }

    public final void nextTokenWithColon(int expect) {
        nextTokenWithChar(':');
    }

    public float floatValue() {
        char c0;
        String strVal = numberString();
        float floatValue = Float.parseFloat(strVal);
        if ((floatValue != 0.0f && floatValue != Float.POSITIVE_INFINITY) || (c0 = strVal.charAt(0)) <= '0' || c0 > '9') {
            return floatValue;
        }
        throw new JSONException("float overflow : " + strVal);
    }

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    public void config(Feature feature, boolean state) {
        int config = Feature.config(this.features, feature, state);
        this.features = config;
        if ((config & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
    }

    public final boolean isEnabled(Feature feature) {
        return isEnabled(feature.mask);
    }

    public final boolean isEnabled(int feature) {
        return (this.features & feature) != 0;
    }

    public final boolean isEnabled(int features2, int feature) {
        return ((this.features & feature) == 0 && (features2 & feature) == 0) ? false : true;
    }

    public final char getCurrent() {
        return this.ch;
    }

    /* access modifiers changed from: protected */
    public void skipComment() {
        char c;
        next();
        char c2 = this.ch;
        if (c2 == '/') {
            do {
                next();
                c = this.ch;
                if (c == 10) {
                    next();
                    return;
                }
            } while (c != 26);
        } else if (c2 == '*') {
            next();
            while (true) {
                char c3 = this.ch;
                if (c3 == 26) {
                    return;
                }
                if (c3 == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
        } else {
            throw new JSONException("invalid comment");
        }
    }

    public final String scanSymbol(SymbolTable symbolTable) {
        skipWhitespace();
        char c = this.ch;
        if (c == '\"') {
            return scanSymbol(symbolTable, StringUtil.DOUBLE_QUOTE);
        }
        if (c == '\'') {
            if (isEnabled(Feature.AllowSingleQuotes)) {
                return scanSymbol(symbolTable, '\'');
            }
            throw new JSONException("syntax error");
        } else if (c == '}') {
            next();
            this.token = 13;
            return null;
        } else if (c == ',') {
            next();
            this.token = 16;
            return null;
        } else if (c == 26) {
            this.token = 20;
            return null;
        } else if (isEnabled(Feature.AllowUnQuotedFieldNames)) {
            return scanSymbolUnQuoted(symbolTable);
        } else {
            throw new JSONException("syntax error");
        }
    }

    public final String scanSymbol(SymbolTable symbolTable, char quote) {
        String value;
        int offset;
        SymbolTable symbolTable2 = symbolTable;
        int hash = 0;
        this.np = this.bp;
        this.sp = 0;
        boolean hasSpecial2 = false;
        while (true) {
            char chLocal = next();
            if (chLocal == quote) {
                this.token = 4;
                if (!hasSpecial2) {
                    int offset2 = this.np;
                    if (offset2 == -1) {
                        offset = 0;
                    } else {
                        offset = offset2 + 1;
                    }
                    value = addSymbol(offset, this.sp, hash, symbolTable2);
                } else {
                    value = symbolTable2.addSymbol(this.sbuf, 0, this.sp, hash);
                }
                this.sp = 0;
                next();
                return value;
            } else if (chLocal == 26) {
                throw new JSONException("unclosed.str");
            } else if (chLocal == '\\') {
                if (!hasSpecial2) {
                    hasSpecial2 = true;
                    int i = this.sp;
                    char[] cArr = this.sbuf;
                    if (i >= cArr.length) {
                        int newCapcity = cArr.length * 2;
                        if (i > newCapcity) {
                            newCapcity = this.sp;
                        }
                        char[] newsbuf = new char[newCapcity];
                        System.arraycopy(cArr, 0, newsbuf, 0, cArr.length);
                        this.sbuf = newsbuf;
                    }
                    arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                }
                char chLocal2 = next();
                switch (chLocal2) {
                    case '\"':
                        hash = (hash * 31) + 34;
                        putChar(StringUtil.DOUBLE_QUOTE);
                        break;
                    case '\'':
                        hash = (hash * 31) + 39;
                        putChar('\'');
                        break;
                    case '/':
                        hash = (hash * 31) + 47;
                        putChar('/');
                        break;
                    case '0':
                        hash = (hash * 31) + chLocal2;
                        putChar(0);
                        break;
                    case '1':
                        hash = (hash * 31) + chLocal2;
                        putChar(1);
                        break;
                    case '2':
                        hash = (hash * 31) + chLocal2;
                        putChar(2);
                        break;
                    case '3':
                        hash = (hash * 31) + chLocal2;
                        putChar(3);
                        break;
                    case '4':
                        hash = (hash * 31) + chLocal2;
                        putChar(4);
                        break;
                    case '5':
                        hash = (hash * 31) + chLocal2;
                        putChar(5);
                        break;
                    case '6':
                        hash = (hash * 31) + chLocal2;
                        putChar(6);
                        break;
                    case '7':
                        hash = (hash * 31) + chLocal2;
                        putChar(7);
                        break;
                    case 'F':
                    case 'f':
                        hash = (hash * 31) + 12;
                        putChar(12);
                        break;
                    case '\\':
                        hash = (hash * 31) + 92;
                        putChar('\\');
                        break;
                    case 'b':
                        hash = (hash * 31) + 8;
                        putChar(8);
                        break;
                    case 'n':
                        hash = (hash * 31) + 10;
                        putChar(10);
                        break;
                    case 'r':
                        hash = (hash * 31) + 13;
                        putChar(StringUtil.CARRIAGE_RETURN);
                        break;
                    case 't':
                        hash = (hash * 31) + 9;
                        putChar(9);
                        break;
                    case 'u':
                        char c1 = next();
                        char chLocal3 = c1;
                        char c2 = next();
                        char chLocal4 = c2;
                        char c3 = next();
                        char chLocal5 = c3;
                        char c4 = next();
                        char chLocal6 = c4;
                        int val = Integer.parseInt(new String(new char[]{c1, c2, c3, c4}), 16);
                        hash = (hash * 31) + val;
                        putChar((char) val);
                        break;
                    case 'v':
                        hash = (hash * 31) + 11;
                        putChar(11);
                        break;
                    case 'x':
                        char x1 = next();
                        this.ch = x1;
                        char x2 = next();
                        this.ch = x2;
                        int[] iArr = digits;
                        char x_char = (char) ((iArr[x1] * 16) + iArr[x2]);
                        hash = (hash * 31) + x_char;
                        putChar(x_char);
                        break;
                    default:
                        this.ch = chLocal2;
                        throw new JSONException("unclosed.str.lit");
                }
            } else {
                hash = (hash * 31) + chLocal;
                if (!hasSpecial2) {
                    this.sp++;
                } else {
                    int i2 = this.sp;
                    char[] cArr2 = this.sbuf;
                    if (i2 == cArr2.length) {
                        putChar(chLocal);
                    } else {
                        this.sp = i2 + 1;
                        cArr2[i2] = chLocal;
                    }
                }
            }
        }
    }

    public final void resetStringPosition() {
        this.sp = 0;
    }

    public String info() {
        return "";
    }

    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        boolean firstFlag = false;
        if (this.token == 1 && this.pos == 0 && this.bp == 1) {
            this.bp = 0;
        }
        boolean[] firstIdentifierFlags = IOUtils.firstIdentifierFlags;
        char first = this.ch;
        if (this.ch >= firstIdentifierFlags.length || firstIdentifierFlags[first]) {
            firstFlag = true;
        }
        if (firstFlag) {
            boolean[] identifierFlags = IOUtils.identifierFlags;
            int hash = first;
            this.np = this.bp;
            this.sp = 1;
            while (true) {
                char chLocal = next();
                if (chLocal < identifierFlags.length && !identifierFlags[chLocal]) {
                    break;
                }
                hash = (hash * 31) + chLocal;
                this.sp++;
            }
            this.ch = charAt(this.bp);
            this.token = 18;
            if (this.sp == 4 && hash == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
                return null;
            }
            if (symbolTable == null) {
                return subString(this.np, this.sp);
            }
            return addSymbol(this.np, this.sp, hash, symbolTable);
        }
        throw new JSONException("illegal identifier : " + this.ch + info());
    }

    public final void scanString() {
        char x1;
        char x2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char ch2 = next();
            if (ch2 == '\"') {
                this.token = 4;
                this.ch = next();
                return;
            } else if (ch2 != 26) {
                boolean z = true;
                if (ch2 == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        int i = this.sp;
                        char[] cArr = this.sbuf;
                        if (i >= cArr.length) {
                            int newCapcity = cArr.length * 2;
                            if (i > newCapcity) {
                                newCapcity = this.sp;
                            }
                            char[] newsbuf = new char[newCapcity];
                            System.arraycopy(cArr, 0, newsbuf, 0, cArr.length);
                            this.sbuf = newsbuf;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char ch3 = next();
                    switch (ch3) {
                        case '\"':
                            putChar(StringUtil.DOUBLE_QUOTE);
                            break;
                        case '\'':
                            putChar('\'');
                            break;
                        case '/':
                            putChar('/');
                            break;
                        case '0':
                            putChar(0);
                            break;
                        case '1':
                            putChar(1);
                            break;
                        case '2':
                            putChar(2);
                            break;
                        case '3':
                            putChar(3);
                            break;
                        case '4':
                            putChar(4);
                            break;
                        case '5':
                            putChar(5);
                            break;
                        case '6':
                            putChar(6);
                            break;
                        case '7':
                            putChar(7);
                            break;
                        case 'F':
                        case 'f':
                            putChar(12);
                            break;
                        case '\\':
                            putChar('\\');
                            break;
                        case 'b':
                            putChar(8);
                            break;
                        case 'n':
                            putChar(10);
                            break;
                        case 'r':
                            putChar(StringUtil.CARRIAGE_RETURN);
                            break;
                        case 't':
                            putChar(9);
                            break;
                        case 'u':
                            putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                            break;
                        case 'v':
                            putChar(11);
                            break;
                        case 'x':
                            x1 = next();
                            x2 = next();
                            boolean hex1 = (x1 >= '0' && x1 <= '9') || (x1 >= 'a' && x1 <= 'f') || (x1 >= 'A' && x1 <= 'F');
                            if ((x2 < '0' || x2 > '9') && ((x2 < 'a' || x2 > 'f') && (x2 < 'A' || x2 > 'F'))) {
                                z = false;
                            }
                            boolean hex2 = z;
                            if (hex1 && hex2) {
                                int[] iArr = digits;
                                putChar((char) ((iArr[x1] * 16) + iArr[x2]));
                                break;
                            } else {
                                break;
                            }
                        default:
                            this.ch = ch3;
                            throw new JSONException("unclosed string : " + ch3);
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else {
                    int i2 = this.sp;
                    char[] cArr2 = this.sbuf;
                    if (i2 == cArr2.length) {
                        putChar(ch2);
                    } else {
                        this.sp = i2 + 1;
                        cArr2[i2] = ch2;
                    }
                }
            } else if (!isEOF()) {
                putChar(JSONLexer.EOI);
            } else {
                throw new JSONException("unclosed string : " + ch2);
            }
        }
        throw new JSONException("invalid escape character \\x" + x1 + x2);
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone2) {
        this.timeZone = timeZone2;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale2) {
        this.locale = locale2;
    }

    public final int intValue() {
        int limit;
        int i;
        if (this.np == -1) {
            this.np = 0;
        }
        int result = 0;
        boolean negative = false;
        int i2 = this.np;
        int i3 = this.np;
        int max = this.sp + i3;
        if (charAt(i3) == '-') {
            negative = true;
            limit = Integer.MIN_VALUE;
            i2++;
        } else {
            limit = -2147483647;
        }
        if (i2 < max) {
            result = -(charAt(i2) - 48);
            i2++;
        }
        while (true) {
            if (i2 >= max) {
                break;
            }
            i = i2 + 1;
            int i4 = charAt(i2);
            if (i4 == 76 || i4 == 83 || i4 == 66) {
                i2 = i;
            } else {
                int digit = i4 - 48;
                if (((long) result) >= -214748364) {
                    int result2 = result * 10;
                    if (result2 >= limit + digit) {
                        result = result2 - digit;
                        i2 = i;
                    } else {
                        throw new NumberFormatException(numberString());
                    }
                } else {
                    throw new NumberFormatException(numberString());
                }
            }
        }
        i2 = i;
        if (!negative) {
            return -result;
        }
        if (i2 > this.np + 1) {
            return result;
        }
        throw new NumberFormatException(numberString());
    }

    public void close() {
        char[] cArr = this.sbuf;
        if (cArr.length <= 8192) {
            SBUF_LOCAL.set(cArr);
        }
        this.sbuf = null;
    }

    public final boolean isRef() {
        if (this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f') {
            return true;
        }
        return false;
    }

    public String scanTypeName(SymbolTable symbolTable) {
        return null;
    }

    public final int scanType(String type) {
        this.matchStat = 0;
        char[] cArr = typeFieldName;
        if (!charArrayCompare(cArr)) {
            return -2;
        }
        int bpLocal = this.bp + cArr.length;
        int typeLength = type.length();
        for (int i = 0; i < typeLength; i++) {
            if (type.charAt(i) != charAt(bpLocal + i)) {
                return -1;
            }
        }
        int bpLocal2 = bpLocal + typeLength;
        if (charAt(bpLocal2) != '\"') {
            return -1;
        }
        int bpLocal3 = bpLocal2 + 1;
        char charAt = charAt(bpLocal3);
        this.ch = charAt;
        if (charAt == ',') {
            int bpLocal4 = bpLocal3 + 1;
            this.ch = charAt(bpLocal4);
            this.bp = bpLocal4;
            this.token = 16;
            return 3;
        }
        if (charAt == '}') {
            bpLocal3++;
            char charAt2 = charAt(bpLocal3);
            this.ch = charAt2;
            if (charAt2 == ',') {
                this.token = 16;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (charAt2 == ']') {
                this.token = 15;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (charAt2 == '}') {
                this.token = 13;
                bpLocal3++;
                this.ch = charAt(bpLocal3);
            } else if (charAt2 != 26) {
                return -1;
            } else {
                this.token = 20;
            }
            this.matchStat = 4;
        }
        this.bp = bpLocal3;
        return this.matchStat;
    }

    public final boolean matchField(char[] fieldName) {
        while (!charArrayCompare(fieldName)) {
            if (!isWhitespace(this.ch)) {
                return false;
            }
            next();
        }
        int length = this.bp + fieldName.length;
        this.bp = length;
        char charAt = charAt(length);
        this.ch = charAt;
        if (charAt == '{') {
            next();
            this.token = 12;
        } else if (charAt == '[') {
            next();
            this.token = 14;
        } else if (charAt == 'S' && charAt(this.bp + 1) == 'e' && charAt(this.bp + 2) == 't' && charAt(this.bp + 3) == '[') {
            int i = this.bp + 3;
            this.bp = i;
            this.ch = charAt(i);
            this.token = 21;
        } else {
            nextToken();
        }
        return true;
    }

    public int matchField(long fieldNameHash) {
        throw new UnsupportedOperationException();
    }

    public boolean seekArrayToItem(int index) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long fieldNameHash, boolean deepScan) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long[] fieldNameHash) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToFieldDeepScan(long fieldNameHash) {
        throw new UnsupportedOperationException();
    }

    public void skipObject() {
        throw new UnsupportedOperationException();
    }

    public void skipObject(boolean valid) {
        throw new UnsupportedOperationException();
    }

    public void skipArray() {
        throw new UnsupportedOperationException();
    }

    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, this.bp + fieldName.length + 1);
        if (endIndex != -1) {
            int startIndex2 = this.bp + fieldName.length + 1;
            String stringVal = subString(startIndex2, endIndex - startIndex2);
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
                int chars_len = endIndex - ((fieldName.length + i2) + 1);
                stringVal = readString(sub_chars(i2 + fieldName.length + 1, chars_len), chars_len);
            }
            int i3 = this.bp;
            int offset3 = offset2 + (endIndex - ((fieldName.length + i3) + 1)) + 1;
            int offset4 = offset3 + 1;
            char chLocal = charAt(i3 + offset3);
            String strVal = stringVal;
            if (chLocal == ',') {
                int i4 = this.bp + offset4;
                this.bp = i4;
                this.ch = charAt(i4);
                this.matchStat = 3;
                return strVal;
            } else if (chLocal == '}') {
                int offset5 = offset4 + 1;
                char chLocal2 = charAt(this.bp + offset4);
                if (chLocal2 == ',') {
                    this.token = 16;
                    int i5 = this.bp + offset5;
                    this.bp = i5;
                    this.ch = charAt(i5);
                } else if (chLocal2 == ']') {
                    this.token = 15;
                    int i6 = this.bp + offset5;
                    this.bp = i6;
                    this.ch = charAt(i6);
                } else if (chLocal2 == '}') {
                    this.token = 13;
                    int i7 = this.bp + offset5;
                    this.bp = i7;
                    this.ch = charAt(i7);
                } else if (chLocal2 == 26) {
                    this.token = 20;
                    this.bp += offset5 - 1;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
                this.matchStat = 4;
                return strVal;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        } else {
            throw new JSONException("unclosed str");
        }
    }

    public String scanString(char expectNextChar) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal != 'n') {
            while (chLocal != '\"') {
                if (isWhitespace(chLocal)) {
                    chLocal = charAt(this.bp + offset);
                    offset++;
                } else {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
            }
            int startIndex = this.bp + offset;
            int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, startIndex);
            if (endIndex != -1) {
                String stringVal = subString(this.bp + offset, endIndex - startIndex);
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
                    int chars_len = endIndex - startIndex;
                    stringVal = readString(sub_chars(this.bp + 1, chars_len), chars_len);
                }
                int offset2 = offset + (endIndex - startIndex) + 1;
                int offset3 = offset2 + 1;
                char chLocal2 = charAt(this.bp + offset2);
                String strVal = stringVal;
                while (chLocal2 != expectNextChar) {
                    if (isWhitespace(chLocal2)) {
                        chLocal2 = charAt(this.bp + offset3);
                        offset3++;
                    } else {
                        if (chLocal2 == ']') {
                            int i2 = this.bp + offset3;
                            this.bp = i2;
                            this.ch = charAt(i2);
                            this.matchStat = -1;
                        }
                        return strVal;
                    }
                }
                int i3 = this.bp + offset3;
                this.bp = i3;
                this.ch = charAt(i3);
                this.matchStat = 3;
                this.token = 16;
                return strVal;
            }
            throw new JSONException("unclosed str");
        } else if (charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            int offset4 = offset + 3;
            int offset5 = offset4 + 1;
            if (charAt(this.bp + offset4) == expectNextChar) {
                int i4 = this.bp + offset5;
                this.bp = i4;
                this.ch = charAt(i4);
                this.matchStat = 3;
                return null;
            }
            this.matchStat = -1;
            return null;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public long scanFieldSymbol(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return 0;
        }
        long hash = -3750763034362895579L;
        while (true) {
            int offset3 = offset2 + 1;
            char chLocal = charAt(this.bp + offset2);
            if (chLocal == '\"') {
                int offset4 = offset3 + 1;
                char chLocal2 = charAt(this.bp + offset3);
                if (chLocal2 == ',') {
                    int i = this.bp + offset4;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    return hash;
                } else if (chLocal2 == '}') {
                    int offset5 = offset4 + 1;
                    char chLocal3 = charAt(this.bp + offset4);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        int i2 = this.bp + offset5;
                        this.bp = i2;
                        this.ch = charAt(i2);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        int i3 = this.bp + offset5;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        int i4 = this.bp + offset5;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0;
                    }
                    this.matchStat = 4;
                    return hash;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            } else {
                hash = (hash ^ ((long) chLocal)) * 1099511628211L;
                if (chLocal == '\\') {
                    this.matchStat = -1;
                    return 0;
                }
                offset2 = offset3;
            }
        }
    }

    public long scanEnumSymbol(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return 0;
        }
        long hash = -3750763034362895579L;
        while (true) {
            int offset3 = offset2 + 1;
            char chLocal = charAt(this.bp + offset2);
            if (chLocal == '\"') {
                int offset4 = offset3 + 1;
                char chLocal2 = charAt(this.bp + offset3);
                if (chLocal2 == ',') {
                    int i = this.bp + offset4;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    return hash;
                } else if (chLocal2 == '}') {
                    int offset5 = offset4 + 1;
                    char chLocal3 = charAt(this.bp + offset4);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        int i2 = this.bp + offset5;
                        this.bp = i2;
                        this.ch = charAt(i2);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        int i3 = this.bp + offset5;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        int i4 = this.bp + offset5;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0;
                    }
                    this.matchStat = 4;
                    return hash;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            } else {
                hash = (hash ^ ((long) ((chLocal < 'A' || chLocal > 'Z') ? chLocal : chLocal + ' '))) * 1099511628211L;
                if (chLocal == '\\') {
                    this.matchStat = -1;
                    return 0;
                }
                offset2 = offset3;
            }
        }
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Enum<?> scanEnum(java.lang.Class<?> r3, com.alibaba.fastjson.parser.SymbolTable r4, char r5) {
        /*
            r2 = this;
            java.lang.String r0 = r2.scanSymbolWithSeperator(r4, r5)
            if (r0 != 0) goto L_0x0008
            r1 = 0
            return r1
        L_0x0008:
            java.lang.Enum r1 = java.lang.Enum.valueOf(r3, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanEnum(java.lang.Class, com.alibaba.fastjson.parser.SymbolTable, char):java.lang.Enum");
    }

    public String scanSymbolWithSeperator(SymbolTable symbolTable, char serperator) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == 'n') {
            if (charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
                int offset2 = offset + 3;
                int offset3 = offset2 + 1;
                if (charAt(this.bp + offset2) == serperator) {
                    int i = this.bp + offset3;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        } else if (chLocal != '\"') {
            this.matchStat = -1;
            return null;
        } else {
            int hash = 0;
            while (true) {
                int offset4 = offset + 1;
                char chLocal2 = charAt(this.bp + offset);
                if (chLocal2 == '\"') {
                    int i2 = this.bp;
                    int start = i2 + 0 + 1;
                    String strVal = addSymbol(start, ((i2 + offset4) - start) - 1, hash, symbolTable);
                    int offset5 = offset4 + 1;
                    char chLocal3 = charAt(this.bp + offset4);
                    while (chLocal3 != serperator) {
                        if (isWhitespace(chLocal3)) {
                            chLocal3 = charAt(this.bp + offset5);
                            offset5++;
                        } else {
                            this.matchStat = -1;
                            return strVal;
                        }
                    }
                    int i3 = this.bp + offset5;
                    this.bp = i3;
                    this.ch = charAt(i3);
                    this.matchStat = 3;
                    return strVal;
                }
                hash = (hash * 31) + chLocal2;
                if (chLocal2 == '\\') {
                    this.matchStat = -1;
                    return null;
                }
                offset = offset4;
            }
        }
    }

    public Collection<String> newCollectionByType(Class<?> type) {
        if (type.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (type.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (type.isAssignableFrom(LinkedList.class)) {
            return new LinkedList();
        }
        try {
            return (Collection) type.newInstance();
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public Collection<String> scanFieldStringArray(char[] fieldName, Class<?> type) {
        int offset;
        char chLocal;
        int offset2;
        char chLocal2;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        Collection<String> list = newCollectionByType(type);
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        int i = -1;
        if (charAt(this.bp + offset3) != '[') {
            this.matchStat = -1;
            return null;
        }
        int offset5 = offset4 + 1;
        char chLocal3 = charAt(this.bp + offset4);
        while (true) {
            if (chLocal3 == '\"') {
                int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, this.bp + offset5);
                if (endIndex != i) {
                    int startIndex2 = this.bp + offset5;
                    String stringVal = subString(startIndex2, endIndex - startIndex2);
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
                        int i3 = this.bp;
                        int chars_len = endIndex - (i3 + offset5);
                        stringVal = readString(sub_chars(i3 + offset5, chars_len), chars_len);
                    }
                    int i4 = this.bp;
                    int offset6 = offset5 + (endIndex - (i4 + offset5)) + 1;
                    offset2 = offset6 + 1;
                    chLocal2 = charAt(i4 + offset6);
                    list.add(stringVal);
                } else {
                    throw new JSONException("unclosed str");
                }
            } else if (chLocal3 == 'n' && charAt(this.bp + offset5) == 'u' && charAt(this.bp + offset5 + 1) == 'l' && charAt(this.bp + offset5 + 2) == 'l') {
                int offset7 = offset5 + 3;
                offset2 = offset7 + 1;
                chLocal2 = charAt(this.bp + offset7);
                list.add((Object) null);
            }
            if (chLocal2 == ',') {
                offset5 = offset2 + 1;
                chLocal3 = charAt(this.bp + offset2);
                i = -1;
            } else if (chLocal2 == ']') {
                offset = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (chLocal3 == ']' && list.size() == 0) {
            offset = offset5 + 1;
            chLocal = charAt(this.bp + offset5);
            if (chLocal == ',') {
                int i5 = this.bp + offset;
                this.bp = i5;
                this.ch = charAt(i5);
                this.matchStat = 3;
                return list;
            } else if (chLocal == '}') {
                int offset8 = offset + 1;
                char chLocal4 = charAt(this.bp + offset);
                if (chLocal4 == ',') {
                    this.token = 16;
                    int i6 = this.bp + offset8;
                    this.bp = i6;
                    this.ch = charAt(i6);
                } else if (chLocal4 == ']') {
                    this.token = 15;
                    int i7 = this.bp + offset8;
                    this.bp = i7;
                    this.ch = charAt(i7);
                } else if (chLocal4 == '}') {
                    this.token = 13;
                    int i8 = this.bp + offset8;
                    this.bp = i8;
                    this.ch = charAt(i8);
                } else if (chLocal4 == 26) {
                    this.bp += offset8 - 1;
                    this.token = 20;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return list;
            } else {
                this.matchStat = -1;
                return null;
            }
        } else {
            throw new JSONException("illega str");
        }
    }

    public void scanStringArray(Collection<String> list, char seperator) {
        int offset;
        char chLocal;
        int startIndex;
        char chLocal2;
        Collection<String> collection = list;
        char c = seperator;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal3 = charAt(this.bp + 0);
        char c2 = 'u';
        char c3 = 'n';
        char c4 = 'l';
        if (chLocal3 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l' && charAt(this.bp + offset2 + 3) == c) {
            int i = this.bp + 5;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 5;
        } else if (chLocal3 != '[') {
            this.matchStat = -1;
        } else {
            int offset3 = offset2 + 1;
            char chLocal4 = charAt(this.bp + offset2);
            while (true) {
                if (chLocal4 != c3 || charAt(this.bp + offset3) != c2 || charAt(this.bp + offset3 + 1) != c4 || charAt(this.bp + offset3 + 2) != c4) {
                    if (chLocal4 == ']' && list.size() == 0) {
                        offset = offset3 + 1;
                        chLocal = charAt(this.bp + offset3);
                        break;
                    } else if (chLocal4 != '\"') {
                        this.matchStat = -1;
                        return;
                    } else {
                        int startIndex2 = this.bp + offset3;
                        int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, startIndex2);
                        if (endIndex != -1) {
                            String stringVal = subString(this.bp + offset3, endIndex - startIndex2);
                            if (stringVal.indexOf(92) != -1) {
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
                                int chars_len = endIndex - startIndex2;
                                stringVal = readString(sub_chars(this.bp + offset3, chars_len), chars_len);
                            }
                            int i3 = this.bp;
                            int offset4 = offset3 + (endIndex - (i3 + offset3)) + 1;
                            chLocal2 = charAt(i3 + offset4);
                            collection.add(stringVal);
                            startIndex = offset4 + 1;
                        } else {
                            throw new JSONException("unclosed str");
                        }
                    }
                } else {
                    int offset5 = offset3 + 3;
                    startIndex = offset5 + 1;
                    chLocal2 = charAt(this.bp + offset5);
                    collection.add((Object) null);
                }
                if (chLocal2 == ',') {
                    offset3 = startIndex + 1;
                    chLocal4 = charAt(this.bp + startIndex);
                    c2 = 'u';
                    c3 = 'n';
                    c4 = 'l';
                } else if (chLocal2 == ']') {
                    offset = startIndex + 1;
                    chLocal = charAt(this.bp + startIndex);
                } else {
                    this.matchStat = -1;
                    return;
                }
            }
            if (chLocal == c) {
                int i4 = this.bp + offset;
                this.bp = i4;
                this.ch = charAt(i4);
                this.matchStat = 3;
                return;
            }
            this.matchStat = -1;
        }
    }

    public int scanFieldInt(char[] fieldName) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal2 < '0' || chLocal2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int value = chLocal2 - '0';
        while (true) {
            offset = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal >= '0' && chLocal <= '9') {
                value = (value * 10) + (chLocal - '0');
                offset3 = offset;
            }
        }
        if (chLocal == '.') {
            this.matchStat = -1;
            return 0;
        } else if ((value < 0 || offset > fieldName.length + 14) && !(value == Integer.MIN_VALUE && offset == 17 && negative)) {
            this.matchStat = -1;
            return 0;
        } else if (chLocal == ',') {
            int i = this.bp + offset;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 3;
            this.token = 16;
            return negative ? -value : value;
        } else if (chLocal == '}') {
            int offset4 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 == ',') {
                this.token = 16;
                int i2 = this.bp + offset4;
                this.bp = i2;
                this.ch = charAt(i2);
            } else if (chLocal3 == ']') {
                this.token = 15;
                int i3 = this.bp + offset4;
                this.bp = i3;
                this.ch = charAt(i3);
            } else if (chLocal3 == '}') {
                this.token = 13;
                int i4 = this.bp + offset4;
                this.bp = i4;
                this.ch = charAt(i4);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset4 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return 0;
            }
            this.matchStat = 4;
            return negative ? -value : value;
        } else {
            this.matchStat = -1;
            return 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x012a, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int[] scanFieldIntArray(char[] r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = 0
            r0.matchStat = r1
            boolean r2 = r18.charArrayCompare(r19)
            r3 = -2
            r4 = 0
            if (r2 != 0) goto L_0x0010
            r0.matchStat = r3
            return r4
        L_0x0010:
            r2 = r19
            int r5 = r2.length
            int r6 = r0.bp
            int r7 = r5 + 1
            int r6 = r6 + r5
            char r5 = r0.charAt(r6)
            r6 = 91
            if (r5 == r6) goto L_0x0023
            r0.matchStat = r3
            return r4
        L_0x0023:
            int r3 = r0.bp
            int r6 = r7 + 1
            int r3 = r3 + r7
            char r3 = r0.charAt(r3)
            r5 = 16
            int[] r7 = new int[r5]
            r8 = 0
            r9 = 3
            r10 = 44
            r12 = 93
            if (r3 != r12) goto L_0x0043
            int r13 = r0.bp
            int r14 = r6 + 1
            int r13 = r13 + r6
            char r3 = r0.charAt(r13)
            goto L_0x00a8
        L_0x0043:
            r13 = 0
            r14 = 45
            if (r3 != r14) goto L_0x0053
            int r14 = r0.bp
            int r15 = r6 + 1
            int r14 = r14 + r6
            char r3 = r0.charAt(r14)
            r13 = 1
            r6 = r15
        L_0x0053:
            r14 = 48
            if (r3 < r14) goto L_0x0126
            r15 = 57
            if (r3 > r15) goto L_0x0126
            int r16 = r3 + -48
            r4 = r16
        L_0x005f:
            int r11 = r0.bp
            int r17 = r6 + 1
            int r11 = r11 + r6
            char r3 = r0.charAt(r11)
            if (r3 < r14) goto L_0x0075
            if (r3 > r15) goto L_0x0075
            int r6 = r4 * 10
            int r11 = r3 + -48
            int r4 = r6 + r11
            r6 = r17
            goto L_0x005f
        L_0x0075:
            int r6 = r7.length
            if (r8 < r6) goto L_0x0082
            int r6 = r7.length
            int r6 = r6 * r9
            int r6 = r6 / 2
            int[] r6 = new int[r6]
            java.lang.System.arraycopy(r7, r1, r6, r1, r8)
            r7 = r6
        L_0x0082:
            int r6 = r8 + 1
            if (r13 == 0) goto L_0x0088
            int r11 = -r4
            goto L_0x0089
        L_0x0088:
            r11 = r4
        L_0x0089:
            r7[r8] = r11
            if (r3 != r10) goto L_0x009b
            int r8 = r0.bp
            int r11 = r17 + 1
            int r8 = r8 + r17
            char r3 = r0.charAt(r8)
            r17 = r11
            goto L_0x011f
        L_0x009b:
            if (r3 != r12) goto L_0x011f
            int r8 = r0.bp
            int r14 = r17 + 1
            int r8 = r8 + r17
            char r3 = r0.charAt(r8)
            r8 = r6
        L_0x00a8:
            int r4 = r7.length
            if (r8 == r4) goto L_0x00b1
            int[] r4 = new int[r8]
            java.lang.System.arraycopy(r7, r1, r4, r1, r8)
            r7 = r4
        L_0x00b1:
            if (r3 != r10) goto L_0x00c2
            int r1 = r0.bp
            int r4 = r14 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r18.next()
            r0.matchStat = r9
            r0.token = r5
            return r7
        L_0x00c2:
            r1 = 125(0x7d, float:1.75E-43)
            if (r3 != r1) goto L_0x011a
            int r4 = r0.bp
            int r6 = r14 + 1
            int r4 = r4 + r14
            char r3 = r0.charAt(r4)
            if (r3 != r10) goto L_0x00de
            r0.token = r5
            int r1 = r0.bp
            int r4 = r6 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r18.next()
            goto L_0x0111
        L_0x00de:
            if (r3 != r12) goto L_0x00ef
            r1 = 15
            r0.token = r1
            int r1 = r0.bp
            int r4 = r6 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r18.next()
            goto L_0x0111
        L_0x00ef:
            if (r3 != r1) goto L_0x0100
            r1 = 13
            r0.token = r1
            int r1 = r0.bp
            int r4 = r6 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r18.next()
            goto L_0x0111
        L_0x0100:
            r1 = 26
            if (r3 != r1) goto L_0x0115
            int r4 = r0.bp
            int r5 = r6 + -1
            int r4 = r4 + r5
            r0.bp = r4
            r4 = 20
            r0.token = r4
            r0.ch = r1
        L_0x0111:
            r1 = 4
            r0.matchStat = r1
            return r7
        L_0x0115:
            r1 = -1
            r0.matchStat = r1
            r4 = 0
            return r4
        L_0x011a:
            r1 = -1
            r4 = 0
            r0.matchStat = r1
            return r4
        L_0x011f:
            r8 = r6
            r6 = r17
            r4 = 0
            goto L_0x0043
        L_0x0126:
            r1 = -1
            r0.matchStat = r1
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldIntArray(char[]):int[]");
    }

    public boolean scanBoolean(char expectNext) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        boolean value = false;
        if (chLocal == 't') {
            if (charAt(this.bp + offset) == 'r' && charAt(this.bp + offset + 1) == 'u' && charAt(this.bp + offset + 2) == 'e') {
                int offset2 = offset + 3;
                chLocal = charAt(this.bp + offset2);
                value = true;
                offset = offset2 + 1;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (chLocal == 'f') {
            if (charAt(this.bp + offset) == 'a' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 's' && charAt(this.bp + offset + 3) == 'e') {
                int offset3 = offset + 4;
                chLocal = charAt(this.bp + offset3);
                value = false;
                offset = offset3 + 1;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (chLocal == '1') {
            chLocal = charAt(this.bp + offset);
            value = true;
            offset++;
        } else if (chLocal == '0') {
            chLocal = charAt(this.bp + offset);
            value = false;
            offset++;
        }
        while (chLocal != expectNext) {
            if (isWhitespace(chLocal)) {
                chLocal = charAt(this.bp + offset);
                offset++;
            } else {
                this.matchStat = -1;
                return value;
            }
        }
        int i = this.bp + offset;
        this.bp = i;
        this.ch = charAt(i);
        this.matchStat = 3;
        return value;
    }

    public int scanInt(char expectNext) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            int value = chLocal2 - '0';
            while (true) {
                offset = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal >= '0' && chLocal <= '9') {
                    value = (value * 10) + (chLocal - '0');
                    offset2 = offset;
                }
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0;
            } else if (value < 0) {
                this.matchStat = -1;
                return 0;
            } else {
                while (chLocal != expectNext) {
                    if (isWhitespace(chLocal)) {
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    } else {
                        this.matchStat = -1;
                        return negative ? -value : value;
                    }
                }
                int i = this.bp + offset;
                this.bp = i;
                this.ch = charAt(i);
                this.matchStat = 3;
                this.token = 16;
                return negative ? -value : value;
            }
        } else if (chLocal2 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
            this.matchStat = 5;
            int offset3 = offset2 + 3;
            int offset4 = offset3 + 1;
            char chLocal3 = charAt(this.bp + offset3);
            if (quote && chLocal3 == '\"') {
                chLocal3 = charAt(this.bp + offset4);
                offset4++;
            }
            while (chLocal3 != ',') {
                if (chLocal3 == ']') {
                    int i2 = this.bp + offset4;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0;
                } else if (isWhitespace(chLocal3)) {
                    chLocal3 = charAt(this.bp + offset4);
                    offset4++;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            int i3 = this.bp + offset4;
            this.bp = i3;
            this.ch = charAt(i3);
            this.matchStat = 5;
            this.token = 16;
            return 0;
        } else {
            this.matchStat = -1;
            return 0;
        }
    }

    public boolean scanFieldBoolean(char[] fieldName) {
        int offset;
        boolean value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        if (chLocal == 't') {
            int offset4 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int offset5 = offset4 + 1;
            if (charAt(this.bp + offset4) != 'u') {
                this.matchStat = -1;
                return false;
            }
            offset = offset5 + 1;
            if (charAt(this.bp + offset5) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = true;
        } else if (chLocal == 'f') {
            int offset6 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int offset7 = offset6 + 1;
            if (charAt(this.bp + offset6) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int offset8 = offset7 + 1;
            if (charAt(this.bp + offset7) != 's') {
                this.matchStat = -1;
                return false;
            }
            int offset9 = offset8 + 1;
            if (charAt(this.bp + offset8) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = false;
            offset = offset9;
        } else {
            this.matchStat = -1;
            return false;
        }
        int offset10 = offset + 1;
        char chLocal2 = charAt(this.bp + offset);
        if (chLocal2 == ',') {
            int i = this.bp + offset10;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 3;
            this.token = 16;
            return value;
        } else if (chLocal2 == '}') {
            int offset11 = offset10 + 1;
            char chLocal3 = charAt(this.bp + offset10);
            if (chLocal3 == ',') {
                this.token = 16;
                int i2 = this.bp + offset11;
                this.bp = i2;
                this.ch = charAt(i2);
            } else if (chLocal3 == ']') {
                this.token = 15;
                int i3 = this.bp + offset11;
                this.bp = i3;
                this.ch = charAt(i3);
            } else if (chLocal3 == '}') {
                this.token = 13;
                int i4 = this.bp + offset11;
                this.bp = i4;
                this.ch = charAt(i4);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset11 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return false;
            }
            this.matchStat = 4;
            return value;
        } else {
            this.matchStat = -1;
            return false;
        }
    }

    public long scanFieldLong(char[] fieldName) {
        int offset;
        char chLocal;
        char[] cArr = fieldName;
        boolean valid = false;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset2 = cArr.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        boolean negative = false;
        if (chLocal2 == '-') {
            chLocal2 = charAt(this.bp + offset3);
            negative = true;
            offset3++;
        }
        if (chLocal2 >= '0') {
            char c = '9';
            if (chLocal2 <= '9') {
                long value = (long) (chLocal2 - '0');
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > c) {
                        long value2 = value;
                    } else {
                        long j = value;
                        value = (10 * value) + ((long) (chLocal - '0'));
                        offset3 = offset;
                        c = '9';
                    }
                }
                long value22 = value;
                if (chLocal == '.') {
                    this.matchStat = -1;
                    return 0;
                }
                if (offset - cArr.length < 21 && (value22 >= 0 || (value22 == Long.MIN_VALUE && negative))) {
                    valid = true;
                }
                if (!valid) {
                    this.matchStat = -1;
                    return 0;
                } else if (chLocal == ',') {
                    int i = this.bp + offset;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    this.token = 16;
                    return negative ? -value22 : value22;
                } else {
                    long value3 = value22;
                    if (chLocal == '}') {
                        int offset4 = offset + 1;
                        char chLocal3 = charAt(this.bp + offset);
                        if (chLocal3 == ',') {
                            this.token = 16;
                            int i2 = this.bp + offset4;
                            this.bp = i2;
                            this.ch = charAt(i2);
                        } else if (chLocal3 == ']') {
                            this.token = 15;
                            int i3 = this.bp + offset4;
                            this.bp = i3;
                            this.ch = charAt(i3);
                        } else if (chLocal3 == '}') {
                            this.token = 13;
                            int i4 = this.bp + offset4;
                            this.bp = i4;
                            this.ch = charAt(i4);
                        } else if (chLocal3 == 26) {
                            this.token = 20;
                            this.bp += offset4 - 1;
                            this.ch = JSONLexer.EOI;
                        } else {
                            this.matchStat = -1;
                            return 0;
                        }
                        this.matchStat = 4;
                        return negative ? -value3 : value3;
                    }
                    this.matchStat = -1;
                    return 0;
                }
            }
        }
        this.matchStat = -1;
        return 0;
    }

    public long scanLong(char expectNextChar) {
        int offset;
        char chLocal;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(this.bp + offset2);
            offset2++;
        }
        if (chLocal2 >= '0') {
            char c = '9';
            if (chLocal2 <= '9') {
                char c2 = chLocal2;
                long value = (long) (chLocal2 - '0');
                while (true) {
                    offset = offset2 + 1;
                    chLocal = charAt(this.bp + offset2);
                    if (chLocal >= '0' && chLocal <= c) {
                        value = (10 * value) + ((long) (chLocal - '0'));
                        char c3 = chLocal;
                        offset2 = offset;
                        c = '9';
                    }
                }
                if (chLocal == '.') {
                    this.matchStat = -1;
                    return 0;
                }
                if (value >= 0 || (value == Long.MIN_VALUE && negative)) {
                    if (quote) {
                        if (chLocal != '\"') {
                            this.matchStat = -1;
                            return 0;
                        }
                        chLocal = charAt(this.bp + offset);
                        offset++;
                    }
                    while (chLocal != expectNextChar) {
                        if (isWhitespace(chLocal)) {
                            chLocal = charAt(this.bp + offset);
                            offset++;
                        } else {
                            this.matchStat = -1;
                            return value;
                        }
                    }
                    int i = this.bp + offset;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    this.token = 16;
                    return negative ? -value : value;
                }
                char c4 = expectNextChar;
                throw new NumberFormatException(subString(this.bp, offset - 1));
            }
        }
        char c5 = expectNextChar;
        if (chLocal2 == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
            this.matchStat = 5;
            int offset3 = offset2 + 3;
            int offset4 = offset3 + 1;
            char chLocal3 = charAt(this.bp + offset3);
            if (quote && chLocal3 == '\"') {
                chLocal3 = charAt(this.bp + offset4);
                offset4++;
            }
            while (chLocal3 != ',') {
                if (chLocal3 == ']') {
                    int i2 = this.bp + offset4;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0;
                } else if (isWhitespace(chLocal3)) {
                    chLocal3 = charAt(this.bp + offset4);
                    offset4++;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            int i3 = this.bp + offset4;
            this.bp = i3;
            this.ch = charAt(i3);
            this.matchStat = 5;
            this.token = 16;
            return 0;
        }
        this.matchStat = -1;
        return 0;
    }

    public final float scanFieldFloat(char[] fieldName) {
        long intVal;
        int offset;
        char chLocal;
        boolean negative;
        int offset2;
        char chLocal2;
        int start;
        int count;
        char chLocal3;
        float value;
        char[] cArr = fieldName;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int offset3 = cArr.length;
        int offset4 = offset3 + 1;
        char chLocal4 = charAt(this.bp + offset3);
        boolean quote = chLocal4 == '\"';
        if (quote) {
            chLocal4 = charAt(this.bp + offset4);
            offset4++;
        }
        boolean negative2 = chLocal4 == '-';
        if (negative2) {
            chLocal4 = charAt(this.bp + offset4);
            offset4++;
        }
        if (chLocal4 >= '0') {
            char c = '9';
            if (chLocal4 <= '9') {
                long intVal2 = (long) (chLocal4 - '0');
                while (true) {
                    offset = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        long power = 1;
                    } else {
                        intVal2 = (10 * intVal) + ((long) (chLocal - '0'));
                        offset4 = offset;
                    }
                }
                long power2 = 1;
                boolean small = chLocal == '.';
                if (small) {
                    int offset5 = offset + 1;
                    char chLocal5 = charAt(this.bp + offset);
                    if (chLocal5 < '0' || chLocal5 > '9') {
                        boolean z = small;
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    negative = negative2;
                    power2 = 10;
                    intVal = (intVal * 10) + ((long) (chLocal5 - '0'));
                    while (true) {
                        offset = offset5 + 1;
                        chLocal = charAt(this.bp + offset5);
                        if (chLocal < '0' || chLocal > c) {
                        } else {
                            intVal = (intVal * 10) + ((long) (chLocal - '0'));
                            power2 *= 10;
                            offset5 = offset;
                            small = small;
                            c = '9';
                        }
                    }
                } else {
                    negative = negative2;
                    boolean z2 = small;
                }
                boolean exp = chLocal2 == 'e' || chLocal2 == 'E';
                if (exp) {
                    int offset6 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    if (chLocal2 == '+' || chLocal2 == '-') {
                        chLocal2 = charAt(this.bp + offset6);
                        offset2 = offset6 + 1;
                    } else {
                        offset2 = offset6;
                    }
                    while (chLocal2 >= '0' && chLocal2 <= '9') {
                        chLocal2 = charAt(this.bp + offset2);
                        offset2++;
                    }
                }
                if (!quote) {
                    int count2 = this.bp;
                    start = count2 + cArr.length;
                    count = ((count2 + offset2) - start) - 1;
                } else if (chLocal2 != '\"') {
                    this.matchStat = -1;
                    return 0.0f;
                } else {
                    int offset7 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    int i = this.bp;
                    start = cArr.length + i + 1;
                    count = ((i + offset7) - start) - 2;
                    offset2 = offset7;
                }
                if (exp || count >= 17) {
                    chLocal3 = chLocal2;
                    value = Float.parseFloat(subString(start, count));
                } else {
                    chLocal3 = chLocal2;
                    value = (float) (((double) intVal) / ((double) power2));
                    if (negative) {
                        value = -value;
                    }
                }
                char chLocal6 = chLocal3;
                if (chLocal6 == ',') {
                    int i2 = this.bp + offset2;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                } else if (chLocal6 == '}') {
                    int offset8 = offset2 + 1;
                    char chLocal7 = charAt(this.bp + offset2);
                    if (chLocal7 == ',') {
                        this.token = 16;
                        int i3 = this.bp + offset8;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (chLocal7 == ']') {
                        this.token = 15;
                        int i4 = this.bp + offset8;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (chLocal7 == '}') {
                        this.token = 13;
                        int i5 = this.bp + offset8;
                        this.bp = i5;
                        this.ch = charAt(i5);
                    } else if (chLocal7 == 26) {
                        this.bp += offset8 - 1;
                        this.token = 20;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    this.matchStat = 4;
                    return value;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
        }
        if (chLocal4 == 'n' && charAt(this.bp + offset4) == 'u' && charAt(this.bp + offset4 + 1) == 'l' && charAt(this.bp + offset4 + 2) == 'l') {
            this.matchStat = 5;
            int offset9 = offset4 + 3;
            int offset10 = offset9 + 1;
            char chLocal8 = charAt(this.bp + offset9);
            if (quote && chLocal8 == '\"') {
                chLocal8 = charAt(this.bp + offset10);
                offset10++;
            }
            while (chLocal8 != ',') {
                if (chLocal8 == '}') {
                    int i6 = this.bp + offset10;
                    this.bp = i6;
                    this.ch = charAt(i6);
                    this.matchStat = 5;
                    this.token = 13;
                    return 0.0f;
                } else if (isWhitespace(chLocal8)) {
                    chLocal8 = charAt(this.bp + offset10);
                    offset10++;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            int i7 = this.bp + offset10;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        this.matchStat = -1;
        return 0.0f;
    }

    public final float scanFloat(char seperator) {
        long intVal;
        int offset;
        char chLocal;
        int offset2;
        char chLocal2;
        int start;
        int count;
        float value;
        this.matchStat = 0;
        int offset3 = 0 + 1;
        char chLocal3 = charAt(this.bp + 0);
        boolean quote = chLocal3 == '\"';
        if (quote) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        boolean negative = chLocal3 == '-';
        if (negative) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal3 >= '0') {
            char c = '9';
            if (chLocal3 <= '9') {
                char c2 = chLocal3;
                long intVal2 = (long) (chLocal3 - '0');
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > '9') {
                        long power = 1;
                    } else {
                        intVal2 = (10 * intVal) + ((long) (chLocal - '0'));
                        char c3 = chLocal;
                        offset3 = offset;
                    }
                }
                long power2 = 1;
                boolean small = chLocal == '.';
                if (small) {
                    int offset4 = offset + 1;
                    int chLocal4 = charAt(this.bp + offset);
                    if (chLocal4 < 48 || chLocal4 > '9') {
                        int offset5 = chLocal4;
                        boolean z = small;
                        this.matchStat = -1;
                        return 0.0f;
                    }
                    power2 = 10;
                    char c4 = chLocal4;
                    intVal = (intVal * 10) + ((long) (chLocal4 - '0'));
                    while (true) {
                        offset = offset4 + 1;
                        chLocal = charAt(this.bp + offset4);
                        if (chLocal < '0' || chLocal > c) {
                        } else {
                            intVal = (intVal * 10) + ((long) (chLocal - '0'));
                            power2 *= 10;
                            offset4 = offset;
                            small = small;
                            c = '9';
                        }
                    }
                }
                boolean exp = chLocal2 == 'e' || chLocal2 == 'E';
                if (exp) {
                    int offset6 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    if (chLocal2 == '+' || chLocal2 == '-') {
                        chLocal2 = charAt(this.bp + offset6);
                        offset2 = offset6 + 1;
                    } else {
                        offset2 = offset6;
                    }
                    while (chLocal2 >= '0' && chLocal2 <= '9') {
                        chLocal2 = charAt(this.bp + offset2);
                        offset2++;
                    }
                }
                if (!quote) {
                    start = this.bp;
                    count = ((this.bp + offset2) - start) - 1;
                } else if (chLocal2 != '\"') {
                    this.matchStat = -1;
                    return 0.0f;
                } else {
                    int offset7 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    int i = this.bp;
                    start = i + 1;
                    count = ((i + offset7) - start) - 2;
                    offset2 = offset7;
                }
                if (exp || count >= 17) {
                    value = Float.parseFloat(subString(start, count));
                } else {
                    long j = intVal;
                    value = (float) (((double) intVal) / ((double) power2));
                    if (negative) {
                        value = -value;
                    }
                }
                if (chLocal2 == seperator) {
                    int i2 = this.bp + offset2;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                this.matchStat = -1;
                return value;
            }
        }
        char chLocal5 = chLocal3;
        char chLocal6 = seperator;
        if (chLocal5 == 'n' && charAt(this.bp + offset3) == 'u' && charAt(this.bp + offset3 + 1) == 'l' && charAt(this.bp + offset3 + 2) == 'l') {
            this.matchStat = 5;
            int offset8 = offset3 + 3;
            int offset9 = offset8 + 1;
            char chLocal7 = charAt(this.bp + offset8);
            if (quote && chLocal7 == '\"') {
                chLocal7 = charAt(this.bp + offset9);
                offset9++;
            }
            while (chLocal7 != ',') {
                if (chLocal7 == ']') {
                    int i3 = this.bp + offset9;
                    this.bp = i3;
                    this.ch = charAt(i3);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0f;
                } else if (isWhitespace(chLocal7)) {
                    chLocal7 = charAt(this.bp + offset9);
                    offset9++;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            int i4 = this.bp + offset9;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        this.matchStat = -1;
        return 0.0f;
    }

    public double scanDouble(char seperator) {
        long intVal;
        int offset;
        char chLocal;
        boolean negative;
        int offset2;
        char chLocal2;
        int start;
        int count;
        double value;
        this.matchStat = 0;
        int offset3 = 0 + 1;
        char chLocal3 = charAt(this.bp + 0);
        boolean quote = chLocal3 == '\"';
        if (quote) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        boolean negative2 = chLocal3 == '-';
        if (negative2) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        char c = '0';
        if (chLocal3 >= '0') {
            char c2 = '9';
            if (chLocal3 <= '9') {
                long intVal2 = (long) (chLocal3 - '0');
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > '9') {
                        long power = 1;
                    } else {
                        intVal2 = (10 * intVal) + ((long) (chLocal - '0'));
                        offset3 = offset;
                    }
                }
                long power2 = 1;
                if (chLocal == '.') {
                    int offset4 = offset + 1;
                    char chLocal4 = charAt(this.bp + offset);
                    if (chLocal4 >= '0' && chLocal4 <= '9') {
                        negative = negative2;
                        power2 = 10;
                        intVal = (intVal * 10) + ((long) (chLocal4 - '0'));
                        while (true) {
                            offset = offset4 + 1;
                            chLocal = charAt(this.bp + offset4);
                            if (chLocal < c || chLocal > c2) {
                                break;
                            }
                            intVal = (intVal * 10) + ((long) (chLocal - '0'));
                            power2 *= 10;
                            offset4 = offset;
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
                    int offset5 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    if (chLocal2 == '+' || chLocal2 == '-') {
                        chLocal2 = charAt(this.bp + offset5);
                        offset2 = offset5 + 1;
                    } else {
                        offset2 = offset5;
                    }
                    while (chLocal2 >= '0' && chLocal2 <= '9') {
                        chLocal2 = charAt(this.bp + offset2);
                        offset2++;
                    }
                }
                if (!quote) {
                    start = this.bp;
                    count = ((this.bp + offset2) - start) - 1;
                } else if (chLocal2 != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                } else {
                    int offset6 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    int i = this.bp;
                    start = i + 1;
                    count = ((i + offset6) - start) - 2;
                    offset2 = offset6;
                }
                if (exp || count >= 17) {
                    value = Double.parseDouble(subString(start, count));
                } else {
                    long j = intVal;
                    value = ((double) intVal) / ((double) power2);
                    if (negative) {
                        value = -value;
                    }
                }
                if (chLocal2 == seperator) {
                    int i2 = this.bp + offset2;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                }
                this.matchStat = -1;
                return value;
            }
        }
        char c3 = seperator;
        boolean z = negative2;
        if (chLocal3 == 'n' && charAt(this.bp + offset3) == 'u' && charAt(this.bp + offset3 + 1) == 'l' && charAt(this.bp + offset3 + 2) == 'l') {
            this.matchStat = 5;
            int offset7 = offset3 + 3;
            int offset8 = offset7 + 1;
            char chLocal5 = charAt(this.bp + offset7);
            if (quote && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset8);
                offset8++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == ']') {
                    int i3 = this.bp + offset8;
                    this.bp = i3;
                    this.ch = charAt(i3);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0d;
                } else if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset8);
                    offset8++;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            int i4 = this.bp + offset8;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        this.matchStat = -1;
        return 0.0d;
    }

    public BigDecimal scanDecimal(char seperator) {
        int offset;
        char chLocal;
        int offset2;
        char chLocal2;
        int start;
        int count;
        this.matchStat = 0;
        int offset3 = 0 + 1;
        char chLocal3 = charAt(this.bp + 0);
        boolean quote = chLocal3 == '\"';
        if (quote) {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal3 == '-') {
            chLocal3 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal3 >= '0' && chLocal3 <= '9') {
            while (true) {
                offset = offset3 + 1;
                chLocal = charAt(this.bp + offset3);
                if (chLocal >= '0' && chLocal <= '9') {
                    offset3 = offset;
                }
            }
            if (chLocal == '.') {
                int offset4 = offset + 1;
                char chLocal4 = charAt(this.bp + offset);
                if (chLocal4 >= '0' && chLocal4 <= '9') {
                    while (true) {
                        offset = offset4 + 1;
                        chLocal = charAt(this.bp + offset4);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        offset4 = offset;
                    }
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            if (chLocal2 == 'e' || chLocal2 == 'E') {
                int offset5 = offset2 + 1;
                chLocal2 = charAt(this.bp + offset2);
                if (chLocal2 == '+' || chLocal2 == '-') {
                    chLocal2 = charAt(this.bp + offset5);
                    offset2 = offset5 + 1;
                } else {
                    offset2 = offset5;
                }
                while (chLocal2 >= '0' && chLocal2 <= '9') {
                    chLocal2 = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (!quote) {
                start = this.bp;
                count = ((this.bp + offset2) - start) - 1;
            } else if (chLocal2 != '\"') {
                this.matchStat = -1;
                return null;
            } else {
                int offset6 = offset2 + 1;
                chLocal2 = charAt(this.bp + offset2);
                int i = this.bp;
                start = i + 1;
                count = ((i + offset6) - start) - 2;
                offset2 = offset6;
            }
            if (count <= 65535) {
                char[] chars = sub_chars(start, count);
                BigDecimal value = new BigDecimal(chars, 0, chars.length, MathContext.UNLIMITED);
                if (chLocal2 == ',') {
                    int i2 = this.bp + offset2;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                } else if (chLocal2 == ']') {
                    int offset7 = offset2 + 1;
                    char chLocal5 = charAt(this.bp + offset2);
                    if (chLocal5 == ',') {
                        this.token = 16;
                        int i3 = this.bp + offset7;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (chLocal5 == ']') {
                        this.token = 15;
                        int i4 = this.bp + offset7;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (chLocal5 == '}') {
                        this.token = 13;
                        int i5 = this.bp + offset7;
                        this.bp = i5;
                        this.ch = charAt(i5);
                    } else if (chLocal5 == 26) {
                        this.token = 20;
                        this.bp += offset7 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                    this.matchStat = 4;
                    return value;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("decimal overflow");
            }
        } else if (chLocal3 == 'n' && charAt(this.bp + offset3) == 'u' && charAt(this.bp + offset3 + 1) == 'l' && charAt(this.bp + offset3 + 2) == 'l') {
            this.matchStat = 5;
            int offset8 = offset3 + 3;
            int offset9 = offset8 + 1;
            char chLocal6 = charAt(this.bp + offset8);
            if (quote && chLocal6 == '\"') {
                chLocal6 = charAt(this.bp + offset9);
                offset9++;
            }
            while (chLocal6 != ',') {
                if (chLocal6 == '}') {
                    int i6 = this.bp + offset9;
                    this.bp = i6;
                    this.ch = charAt(i6);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                } else if (isWhitespace(chLocal6)) {
                    chLocal6 = charAt(this.bp + offset9);
                    offset9++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            int i7 = this.bp + offset9;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 5;
            this.token = 16;
            return null;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ad, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float[] scanFieldFloatArray(char[] r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = 0
            r0.matchStat = r1
            boolean r2 = r21.charArrayCompare(r22)
            r3 = -2
            r4 = 0
            if (r2 != 0) goto L_0x0010
            r0.matchStat = r3
            return r4
        L_0x0010:
            r2 = r22
            int r5 = r2.length
            int r6 = r0.bp
            int r7 = r5 + 1
            int r6 = r6 + r5
            char r5 = r0.charAt(r6)
            r6 = 91
            if (r5 == r6) goto L_0x0023
            r0.matchStat = r3
            return r4
        L_0x0023:
            int r3 = r0.bp
            int r6 = r7 + 1
            int r3 = r3 + r7
            char r3 = r0.charAt(r3)
            r5 = 16
            float[] r7 = new float[r5]
            r8 = 0
        L_0x0031:
            int r9 = r0.bp
            int r10 = r9 + r6
            r11 = 1
            int r10 = r10 - r11
            r12 = 45
            if (r3 != r12) goto L_0x003d
            r13 = r11
            goto L_0x003e
        L_0x003d:
            r13 = r1
        L_0x003e:
            if (r13 == 0) goto L_0x0048
            int r14 = r6 + 1
            int r9 = r9 + r6
            char r3 = r0.charAt(r9)
            r6 = r14
        L_0x0048:
            r9 = -1
            r14 = 48
            if (r3 < r14) goto L_0x01cc
            r15 = 57
            if (r3 > r15) goto L_0x01cc
            int r16 = r3 + -48
        L_0x0053:
            int r5 = r0.bp
            int r17 = r6 + 1
            int r5 = r5 + r6
            char r3 = r0.charAt(r5)
            if (r3 < r14) goto L_0x006b
            if (r3 > r15) goto L_0x006b
            int r5 = r16 * 10
            int r6 = r3 + -48
            int r16 = r5 + r6
            r6 = r17
            r5 = 16
            goto L_0x0053
        L_0x006b:
            r5 = 1
            r6 = 46
            if (r3 != r6) goto L_0x0072
            r6 = r11
            goto L_0x0073
        L_0x0072:
            r6 = r1
        L_0x0073:
            if (r6 == 0) goto L_0x00ae
            int r1 = r0.bp
            int r18 = r17 + 1
            int r1 = r1 + r17
            char r1 = r0.charAt(r1)
            r3 = 10
            if (r1 < r14) goto L_0x00ab
            if (r1 > r15) goto L_0x00ab
            int r5 = r16 * 10
            int r17 = r1 + -48
            int r5 = r5 + r17
            r16 = r5
            r5 = r3
        L_0x008e:
            int r3 = r0.bp
            int r17 = r18 + 1
            int r3 = r3 + r18
            char r1 = r0.charAt(r3)
            if (r1 < r14) goto L_0x00a7
            if (r1 > r15) goto L_0x00a7
            int r3 = r16 * 10
            int r18 = r1 + -48
            int r16 = r3 + r18
            int r5 = r5 * 10
            r18 = r17
            goto L_0x008e
        L_0x00a7:
            r3 = r1
            r1 = r16
            goto L_0x00b0
        L_0x00ab:
            r0.matchStat = r9
            return r4
        L_0x00ae:
            r1 = r16
        L_0x00b0:
            r4 = 101(0x65, float:1.42E-43)
            if (r3 == r4) goto L_0x00bb
            r4 = 69
            if (r3 != r4) goto L_0x00b9
            goto L_0x00bb
        L_0x00b9:
            r4 = 0
            goto L_0x00bc
        L_0x00bb:
            r4 = r11
        L_0x00bc:
            if (r4 == 0) goto L_0x00ef
            int r9 = r0.bp
            int r19 = r17 + 1
            int r9 = r9 + r17
            char r3 = r0.charAt(r9)
            r9 = 43
            if (r3 == r9) goto L_0x00d2
            if (r3 != r12) goto L_0x00cf
            goto L_0x00d2
        L_0x00cf:
            r17 = r19
            goto L_0x00de
        L_0x00d2:
            int r9 = r0.bp
            int r12 = r19 + 1
            int r9 = r9 + r19
            char r3 = r0.charAt(r9)
            r17 = r12
        L_0x00de:
            if (r3 < r14) goto L_0x00ef
            if (r3 > r15) goto L_0x00ef
            int r9 = r0.bp
            int r12 = r17 + 1
            int r9 = r9 + r17
            char r3 = r0.charAt(r9)
            r17 = r12
            goto L_0x00de
        L_0x00ef:
            int r9 = r0.bp
            int r9 = r9 + r17
            int r9 = r9 - r10
            int r9 = r9 - r11
            if (r4 != 0) goto L_0x0102
            r11 = 10
            if (r9 >= r11) goto L_0x0102
            float r11 = (float) r1
            float r12 = (float) r5
            float r11 = r11 / r12
            if (r13 == 0) goto L_0x010b
            float r11 = -r11
            goto L_0x010b
        L_0x0102:
            java.lang.String r11 = r0.subString(r10, r9)
            float r12 = java.lang.Float.parseFloat(r11)
            r11 = r12
        L_0x010b:
            int r12 = r7.length
            r14 = 3
            if (r8 < r12) goto L_0x011a
            int r12 = r7.length
            int r12 = r12 * r14
            int r12 = r12 / 2
            float[] r12 = new float[r12]
            r15 = 0
            java.lang.System.arraycopy(r7, r15, r12, r15, r8)
            r7 = r12
        L_0x011a:
            int r12 = r8 + 1
            r7[r8] = r11
            r8 = 44
            if (r3 != r8) goto L_0x0132
            int r8 = r0.bp
            int r14 = r17 + 1
            int r8 = r8 + r17
            char r3 = r0.charAt(r8)
            r6 = r14
            r14 = 16
            r15 = 0
            goto L_0x01c5
        L_0x0132:
            r15 = 93
            if (r3 != r15) goto L_0x01c0
            int r15 = r0.bp
            int r20 = r17 + 1
            int r15 = r15 + r17
            char r3 = r0.charAt(r15)
            int r1 = r7.length
            if (r12 == r1) goto L_0x014b
            float[] r1 = new float[r12]
            r15 = 0
            java.lang.System.arraycopy(r7, r15, r1, r15, r12)
            r7 = r1
        L_0x014b:
            if (r3 != r8) goto L_0x015e
            int r1 = r0.bp
            int r4 = r20 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r21.next()
            r0.matchStat = r14
            r1 = 16
            r0.token = r1
            return r7
        L_0x015e:
            r1 = 125(0x7d, float:1.75E-43)
            if (r3 != r1) goto L_0x01bb
            int r4 = r0.bp
            int r5 = r20 + 1
            int r4 = r4 + r20
            char r3 = r0.charAt(r4)
            if (r3 != r8) goto L_0x017d
            r14 = 16
            r0.token = r14
            int r1 = r0.bp
            int r4 = r5 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r21.next()
            goto L_0x01b2
        L_0x017d:
            r4 = 93
            if (r3 != r4) goto L_0x0190
            r1 = 15
            r0.token = r1
            int r1 = r0.bp
            int r4 = r5 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r21.next()
            goto L_0x01b2
        L_0x0190:
            if (r3 != r1) goto L_0x01a1
            r1 = 13
            r0.token = r1
            int r1 = r0.bp
            int r4 = r5 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r21.next()
            goto L_0x01b2
        L_0x01a1:
            r1 = 26
            if (r3 != r1) goto L_0x01b6
            int r4 = r0.bp
            int r6 = r5 + -1
            int r4 = r4 + r6
            r0.bp = r4
            r4 = 20
            r0.token = r4
            r0.ch = r1
        L_0x01b2:
            r1 = 4
            r0.matchStat = r1
            return r7
        L_0x01b6:
            r1 = -1
            r0.matchStat = r1
            r4 = 0
            return r4
        L_0x01bb:
            r1 = -1
            r4 = 0
            r0.matchStat = r1
            return r4
        L_0x01c0:
            r14 = 16
            r15 = 0
            r6 = r17
        L_0x01c5:
            r8 = r12
            r5 = r14
            r1 = r15
            r4 = 0
            goto L_0x0031
        L_0x01cc:
            r1 = -1
            r0.matchStat = r1
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray(char[]):float[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b9, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x013f, code lost:
        r14 = r21 + 1;
        r3 = charAt(r0.bp + r21);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x014b, code lost:
        if (r12 == r8.length) goto L_0x0154;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x014d, code lost:
        r1 = new float[r12];
        java.lang.System.arraycopy(r8, 0, r1, 0, r12);
        r8 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0155, code lost:
        if (r9 < r7.length) goto L_0x0163;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0157, code lost:
        r1 = new float[((r7.length * 3) / 2)][];
        java.lang.System.arraycopy(r8, 0, r1, 0, r12);
        r7 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0163, code lost:
        r1 = r9 + 1;
        r7[r9] = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0169, code lost:
        if (r3 != ',') goto L_0x0176;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x016b, code lost:
        r3 = charAt(r0.bp + r14);
        r8 = r14 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0178, code lost:
        if (r3 != ']') goto L_0x0186;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x017a, code lost:
        r3 = charAt(r0.bp + r14);
        r9 = r1;
        r8 = r14 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0186, code lost:
        r8 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01a1, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final float[][] scanFieldFloatArray2(char[] r25) {
        /*
            r24 = this;
            r0 = r24
            r1 = 0
            r0.matchStat = r1
            boolean r2 = r24.charArrayCompare(r25)
            r3 = -2
            r4 = 0
            if (r2 != 0) goto L_0x0010
            r0.matchStat = r3
            return r4
        L_0x0010:
            r2 = r25
            int r5 = r2.length
            int r6 = r0.bp
            int r7 = r5 + 1
            int r6 = r6 + r5
            char r5 = r0.charAt(r6)
            r6 = 91
            if (r5 == r6) goto L_0x0023
            r0.matchStat = r3
            return r4
        L_0x0023:
            int r3 = r0.bp
            int r8 = r7 + 1
            int r3 = r3 + r7
            char r3 = r0.charAt(r3)
            r5 = 16
            float[][] r7 = new float[r5][]
            r9 = 0
        L_0x0031:
            r11 = 3
            r12 = -1
            if (r3 != r6) goto L_0x01a2
            int r14 = r0.bp
            int r15 = r8 + 1
            int r14 = r14 + r8
            char r3 = r0.charAt(r14)
            float[] r8 = new float[r5]
            r14 = 0
        L_0x0041:
            int r6 = r0.bp
            int r16 = r6 + r15
            r17 = 1
            int r5 = r16 + -1
            r10 = 45
            if (r3 != r10) goto L_0x0050
            r18 = r17
            goto L_0x0052
        L_0x0050:
            r18 = r1
        L_0x0052:
            if (r18 == 0) goto L_0x005d
            int r19 = r15 + 1
            int r6 = r6 + r15
            char r3 = r0.charAt(r6)
            r15 = r19
        L_0x005d:
            r6 = 48
            if (r3 < r6) goto L_0x019d
            r13 = 57
            if (r3 > r13) goto L_0x019d
            int r20 = r3 + -48
        L_0x0067:
            int r1 = r0.bp
            int r21 = r15 + 1
            int r1 = r1 + r15
            char r3 = r0.charAt(r1)
            if (r3 < r6) goto L_0x007e
            if (r3 > r13) goto L_0x007e
            int r1 = r20 * 10
            int r15 = r3 + -48
            int r20 = r1 + r15
            r15 = r21
            r1 = 0
            goto L_0x0067
        L_0x007e:
            r1 = 1
            r15 = 46
            if (r3 != r15) goto L_0x00ba
            int r15 = r0.bp
            int r22 = r21 + 1
            int r15 = r15 + r21
            char r3 = r0.charAt(r15)
            if (r3 < r6) goto L_0x00b7
            if (r3 > r13) goto L_0x00b7
            int r15 = r20 * 10
            int r21 = r3 + -48
            int r15 = r15 + r21
            r1 = 10
            r20 = r15
        L_0x009b:
            int r15 = r0.bp
            int r21 = r22 + 1
            int r15 = r15 + r22
            char r3 = r0.charAt(r15)
            if (r3 < r6) goto L_0x00b4
            if (r3 > r13) goto L_0x00b4
            int r15 = r20 * 10
            int r22 = r3 + -48
            int r20 = r15 + r22
            int r1 = r1 * 10
            r22 = r21
            goto L_0x009b
        L_0x00b4:
            r15 = r20
            goto L_0x00bc
        L_0x00b7:
            r0.matchStat = r12
            return r4
        L_0x00ba:
            r15 = r20
        L_0x00bc:
            r4 = 101(0x65, float:1.42E-43)
            if (r3 == r4) goto L_0x00c7
            r4 = 69
            if (r3 != r4) goto L_0x00c5
            goto L_0x00c7
        L_0x00c5:
            r4 = 0
            goto L_0x00c9
        L_0x00c7:
            r4 = r17
        L_0x00c9:
            if (r4 == 0) goto L_0x00fc
            int r12 = r0.bp
            int r23 = r21 + 1
            int r12 = r12 + r21
            char r3 = r0.charAt(r12)
            r12 = 43
            if (r3 == r12) goto L_0x00df
            if (r3 != r10) goto L_0x00dc
            goto L_0x00df
        L_0x00dc:
            r21 = r23
            goto L_0x00eb
        L_0x00df:
            int r10 = r0.bp
            int r12 = r23 + 1
            int r10 = r10 + r23
            char r3 = r0.charAt(r10)
            r21 = r12
        L_0x00eb:
            if (r3 < r6) goto L_0x00fc
            if (r3 > r13) goto L_0x00fc
            int r10 = r0.bp
            int r12 = r21 + 1
            int r10 = r10 + r21
            char r3 = r0.charAt(r10)
            r21 = r12
            goto L_0x00eb
        L_0x00fc:
            int r6 = r0.bp
            int r6 = r6 + r21
            int r6 = r6 - r5
            int r6 = r6 + -1
            if (r4 != 0) goto L_0x0110
            r10 = 10
            if (r6 >= r10) goto L_0x0110
            float r10 = (float) r15
            float r12 = (float) r1
            float r10 = r10 / r12
            if (r18 == 0) goto L_0x0119
            float r10 = -r10
            goto L_0x0119
        L_0x0110:
            java.lang.String r10 = r0.subString(r5, r6)
            float r12 = java.lang.Float.parseFloat(r10)
            r10 = r12
        L_0x0119:
            int r12 = r8.length
            if (r14 < r12) goto L_0x0127
            int r12 = r8.length
            int r12 = r12 * r11
            int r12 = r12 / 2
            float[] r12 = new float[r12]
            r13 = 0
            java.lang.System.arraycopy(r8, r13, r12, r13, r14)
            r8 = r12
        L_0x0127:
            int r12 = r14 + 1
            r8[r14] = r10
            r13 = 44
            if (r3 != r13) goto L_0x013b
            int r13 = r0.bp
            int r14 = r21 + 1
            int r13 = r13 + r21
            char r3 = r0.charAt(r13)
            r15 = r14
            goto L_0x0192
        L_0x013b:
            r13 = 93
            if (r3 != r13) goto L_0x0190
            int r13 = r0.bp
            int r14 = r21 + 1
            int r13 = r13 + r21
            char r3 = r0.charAt(r13)
            int r1 = r8.length
            if (r12 == r1) goto L_0x0154
            float[] r1 = new float[r12]
            r4 = 0
            java.lang.System.arraycopy(r8, r4, r1, r4, r12)
            r8 = r1
        L_0x0154:
            int r1 = r7.length
            if (r9 < r1) goto L_0x0163
            int r1 = r7.length
            int r1 = r1 * r11
            int r1 = r1 / 2
            float[][] r1 = new float[r1][]
            r4 = 0
            java.lang.System.arraycopy(r8, r4, r1, r4, r12)
            r4 = r1
            r7 = r4
        L_0x0163:
            int r1 = r9 + 1
            r7[r9] = r8
            r4 = 44
            if (r3 != r4) goto L_0x0176
            int r4 = r0.bp
            int r5 = r14 + 1
            int r4 = r4 + r14
            char r3 = r0.charAt(r4)
            r8 = r5
            goto L_0x0187
        L_0x0176:
            r4 = 93
            if (r3 != r4) goto L_0x0186
            int r4 = r0.bp
            int r5 = r14 + 1
            int r4 = r4 + r14
            char r3 = r0.charAt(r4)
            r9 = r1
            r8 = r5
            goto L_0x01a2
        L_0x0186:
            r8 = r14
        L_0x0187:
            r9 = r1
            r1 = 0
            r4 = 0
            r5 = 16
            r6 = 91
            goto L_0x0031
        L_0x0190:
            r15 = r21
        L_0x0192:
            r14 = r12
            r1 = 0
            r4 = 0
            r5 = 16
            r6 = 91
            r12 = -1
            goto L_0x0041
        L_0x019d:
            r1 = -1
            r0.matchStat = r1
            r1 = 0
            return r1
        L_0x01a2:
            int r1 = r7.length
            if (r9 == r1) goto L_0x01ac
            float[][] r1 = new float[r9][]
            r4 = 0
            java.lang.System.arraycopy(r7, r4, r1, r4, r9)
            r7 = r1
        L_0x01ac:
            r1 = 44
            if (r3 != r1) goto L_0x01c1
            int r1 = r0.bp
            int r4 = r8 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r24.next()
            r0.matchStat = r11
            r1 = 16
            r0.token = r1
            return r7
        L_0x01c1:
            r1 = 125(0x7d, float:1.75E-43)
            if (r3 != r1) goto L_0x021f
            int r4 = r0.bp
            int r5 = r8 + 1
            int r4 = r4 + r8
            char r3 = r0.charAt(r4)
            r4 = 44
            if (r3 != r4) goto L_0x01e1
            r1 = 16
            r0.token = r1
            int r1 = r0.bp
            int r4 = r5 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r24.next()
            goto L_0x0216
        L_0x01e1:
            r4 = 93
            if (r3 != r4) goto L_0x01f4
            r1 = 15
            r0.token = r1
            int r1 = r0.bp
            int r4 = r5 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r24.next()
            goto L_0x0216
        L_0x01f4:
            if (r3 != r1) goto L_0x0205
            r1 = 13
            r0.token = r1
            int r1 = r0.bp
            int r4 = r5 + -1
            int r1 = r1 + r4
            r0.bp = r1
            r24.next()
            goto L_0x0216
        L_0x0205:
            r1 = 26
            if (r3 != r1) goto L_0x021a
            int r4 = r0.bp
            int r6 = r5 + -1
            int r4 = r4 + r6
            r0.bp = r4
            r4 = 20
            r0.token = r4
            r0.ch = r1
        L_0x0216:
            r1 = 4
            r0.matchStat = r1
            return r7
        L_0x021a:
            r1 = -1
            r0.matchStat = r1
            r4 = 0
            return r4
        L_0x021f:
            r1 = -1
            r4 = 0
            r0.matchStat = r1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray2(char[]):float[][]");
    }

    public final double scanFieldDouble(char[] fieldName) {
        long intVal;
        int offset;
        char chLocal;
        boolean negative;
        int offset2;
        char chLocal2;
        int start;
        int count;
        double value;
        char chLocal3;
        char[] cArr = fieldName;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0d;
        }
        int offset3 = cArr.length;
        int offset4 = offset3 + 1;
        char chLocal4 = charAt(this.bp + offset3);
        boolean quote = chLocal4 == '\"';
        if (quote) {
            chLocal4 = charAt(this.bp + offset4);
            offset4++;
        }
        boolean negative2 = chLocal4 == '-';
        if (negative2) {
            chLocal4 = charAt(this.bp + offset4);
            offset4++;
        }
        char c = '0';
        if (chLocal4 >= '0') {
            char c2 = '9';
            if (chLocal4 <= '9') {
                char c3 = chLocal4;
                long intVal2 = (long) (chLocal4 - '0');
                while (true) {
                    offset = offset4 + 1;
                    chLocal = charAt(this.bp + offset4);
                    if (chLocal < '0' || chLocal > '9') {
                        boolean quote2 = quote;
                        long power = 1;
                    } else {
                        intVal2 = (10 * intVal) + ((long) (chLocal - '0'));
                        char c4 = chLocal;
                        offset4 = offset;
                        quote = quote;
                    }
                }
                boolean quote22 = quote;
                long power2 = 1;
                boolean small = chLocal == '.';
                if (small) {
                    int offset5 = offset + 1;
                    char chLocal5 = charAt(this.bp + offset);
                    if (chLocal5 < '0' || chLocal5 > '9') {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    negative = negative2;
                    power2 = 10;
                    intVal = (intVal * 10) + ((long) (chLocal5 - '0'));
                    while (true) {
                        offset = offset5 + 1;
                        chLocal3 = charAt(this.bp + offset5);
                        if (chLocal3 < c || chLocal3 > c2) {
                            chLocal = chLocal3;
                        } else {
                            intVal = (intVal * 10) + ((long) (chLocal3 - '0'));
                            power2 *= 10;
                            offset5 = offset;
                            c2 = '9';
                            c = '0';
                        }
                    }
                    chLocal = chLocal3;
                } else {
                    negative = negative2;
                }
                boolean exp = chLocal2 == 'e' || chLocal2 == 'E';
                if (exp) {
                    int offset6 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    if (chLocal2 == '+' || chLocal2 == '-') {
                        chLocal2 = charAt(this.bp + offset6);
                        offset2 = offset6 + 1;
                    } else {
                        offset2 = offset6;
                    }
                    while (chLocal2 >= '0' && chLocal2 <= '9') {
                        chLocal2 = charAt(this.bp + offset2);
                        offset2++;
                    }
                }
                if (!quote22) {
                    int count2 = this.bp;
                    start = count2 + cArr.length;
                    count = ((count2 + offset2) - start) - 1;
                } else if (chLocal2 != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                } else {
                    int offset7 = offset2 + 1;
                    chLocal2 = charAt(this.bp + offset2);
                    int i = this.bp;
                    start = cArr.length + i + 1;
                    count = ((i + offset7) - start) - 2;
                    offset2 = offset7;
                }
                if (exp || count >= 17) {
                    value = Double.parseDouble(subString(start, count));
                } else {
                    boolean z = small;
                    value = ((double) intVal) / ((double) power2);
                    if (negative) {
                        value = -value;
                    }
                }
                if (chLocal2 == ',') {
                    int i2 = this.bp + offset2;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                } else if (chLocal2 == '}') {
                    int offset8 = offset2 + 1;
                    char chLocal6 = charAt(this.bp + offset2);
                    if (chLocal6 == ',') {
                        this.token = 16;
                        int i3 = this.bp + offset8;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (chLocal6 == ']') {
                        this.token = 15;
                        int i4 = this.bp + offset8;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (chLocal6 == '}') {
                        this.token = 13;
                        int i5 = this.bp + offset8;
                        this.bp = i5;
                        this.ch = charAt(i5);
                    } else if (chLocal6 == 26) {
                        this.token = 20;
                        this.bp += offset8 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0.0d;
                    }
                    this.matchStat = 4;
                    return value;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
        }
        boolean quote3 = quote;
        boolean z2 = negative2;
        if (chLocal4 == 'n' && charAt(this.bp + offset4) == 'u' && charAt(this.bp + offset4 + 1) == 'l' && charAt(this.bp + offset4 + 2) == 'l') {
            this.matchStat = 5;
            int offset9 = offset4 + 3;
            int offset10 = offset9 + 1;
            char chLocal7 = charAt(this.bp + offset9);
            if (quote3 && chLocal7 == '\"') {
                chLocal7 = charAt(this.bp + offset10);
                offset10++;
            }
            while (chLocal7 != ',') {
                if (chLocal7 == '}') {
                    int i6 = this.bp + offset10;
                    this.bp = i6;
                    this.ch = charAt(i6);
                    this.matchStat = 5;
                    this.token = 13;
                    return 0.0d;
                } else if (isWhitespace(chLocal7)) {
                    chLocal7 = charAt(this.bp + offset10);
                    offset10++;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            int i7 = this.bp + offset10;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        this.matchStat = -1;
        return 0.0d;
    }

    public BigDecimal scanFieldDecimal(char[] fieldName) {
        int offset;
        char chLocal;
        int offset2;
        char chLocal2;
        int start;
        int count;
        char[] cArr = fieldName;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset3 = cArr.length;
        int offset4 = offset3 + 1;
        char chLocal3 = charAt(this.bp + offset3);
        boolean quote = chLocal3 == '\"';
        if (quote) {
            chLocal3 = charAt(this.bp + offset4);
            offset4++;
        }
        if (chLocal3 == '-') {
            chLocal3 = charAt(this.bp + offset4);
            offset4++;
        }
        if (chLocal3 >= '0' && chLocal3 <= '9') {
            while (true) {
                offset = offset4 + 1;
                chLocal = charAt(this.bp + offset4);
                if (chLocal >= '0' && chLocal <= '9') {
                    offset4 = offset;
                }
            }
            if (chLocal == '.') {
                int offset5 = offset + 1;
                char chLocal4 = charAt(this.bp + offset);
                if (chLocal4 >= '0' && chLocal4 <= '9') {
                    while (true) {
                        offset = offset5 + 1;
                        chLocal = charAt(this.bp + offset5);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        offset5 = offset;
                    }
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            if (chLocal2 == 'e' || chLocal2 == 'E') {
                int offset6 = offset2 + 1;
                chLocal2 = charAt(this.bp + offset2);
                if (chLocal2 == '+' || chLocal2 == '-') {
                    chLocal2 = charAt(this.bp + offset6);
                    offset2 = offset6 + 1;
                } else {
                    offset2 = offset6;
                }
                while (chLocal2 >= '0' && chLocal2 <= '9') {
                    chLocal2 = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (!quote) {
                int count2 = this.bp;
                start = count2 + cArr.length;
                count = ((count2 + offset2) - start) - 1;
            } else if (chLocal2 != '\"') {
                this.matchStat = -1;
                return null;
            } else {
                int offset7 = offset2 + 1;
                chLocal2 = charAt(this.bp + offset2);
                int i = this.bp;
                start = cArr.length + i + 1;
                count = ((i + offset7) - start) - 2;
                offset2 = offset7;
            }
            if (count <= 65535) {
                char[] chars = sub_chars(start, count);
                BigDecimal value = new BigDecimal(chars, 0, chars.length, MathContext.UNLIMITED);
                if (chLocal2 == ',') {
                    int i2 = this.bp + offset2;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                } else if (chLocal2 == '}') {
                    int offset8 = offset2 + 1;
                    char chLocal5 = charAt(this.bp + offset2);
                    if (chLocal5 == ',') {
                        this.token = 16;
                        int i3 = this.bp + offset8;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (chLocal5 == ']') {
                        this.token = 15;
                        int i4 = this.bp + offset8;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (chLocal5 == '}') {
                        this.token = 13;
                        int i5 = this.bp + offset8;
                        this.bp = i5;
                        this.ch = charAt(i5);
                    } else if (chLocal5 == 26) {
                        this.token = 20;
                        this.bp += offset8 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                    this.matchStat = 4;
                    return value;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } else {
                throw new JSONException("scan decimal overflow");
            }
        } else if (chLocal3 == 'n' && charAt(this.bp + offset4) == 'u' && charAt(this.bp + offset4 + 1) == 'l' && charAt(this.bp + offset4 + 2) == 'l') {
            this.matchStat = 5;
            int offset9 = offset4 + 3;
            int offset10 = offset9 + 1;
            char chLocal6 = charAt(this.bp + offset9);
            if (quote && chLocal6 == '\"') {
                chLocal6 = charAt(this.bp + offset10);
                offset10++;
            }
            while (chLocal6 != ',') {
                if (chLocal6 == '}') {
                    int i6 = this.bp + offset10;
                    this.bp = i6;
                    this.ch = charAt(i6);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                } else if (isWhitespace(chLocal6)) {
                    chLocal6 = charAt(this.bp + offset10);
                    offset10++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            int i7 = this.bp + offset10;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 5;
            this.token = 16;
            return null;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public BigInteger scanFieldBigInteger(char[] fieldName) {
        int offset;
        char chLocal;
        int start;
        int count;
        BigInteger value;
        char[] cArr = fieldName;
        boolean negative = false;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = cArr.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal2 == '-') {
            negative = true;
        }
        if (negative) {
            chLocal2 = charAt(this.bp + offset3);
            offset3++;
        }
        if (chLocal2 >= '0') {
            char c = '9';
            if (chLocal2 <= '9') {
                long intVal = (long) (chLocal2 - '0');
                boolean overflow = false;
                while (true) {
                    offset = offset3 + 1;
                    chLocal = charAt(this.bp + offset3);
                    if (chLocal < '0' || chLocal > c) {
                        break;
                    }
                    long temp = (10 * intVal) + ((long) (chLocal - '0'));
                    if (temp < intVal) {
                        overflow = true;
                        break;
                    }
                    intVal = temp;
                    offset3 = offset;
                    c = '9';
                }
                if (!quote) {
                    int count2 = this.bp;
                    start = count2 + cArr.length;
                    count = ((count2 + offset) - start) - 1;
                } else if (chLocal != '\"') {
                    this.matchStat = -1;
                    return null;
                } else {
                    int offset4 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    int i = this.bp;
                    start = cArr.length + i + 1;
                    count = ((i + offset4) - start) - 2;
                    offset = offset4;
                }
                if (!overflow && (count < 20 || (negative && count < 21))) {
                    value = BigInteger.valueOf(negative ? -intVal : intVal);
                } else if (count <= 65535) {
                    value = new BigInteger(subString(start, count), 10);
                } else {
                    throw new JSONException("scanInteger overflow");
                }
                if (chLocal == ',') {
                    int i2 = this.bp + offset;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    this.token = 16;
                    return value;
                } else if (chLocal == '}') {
                    int offset5 = offset + 1;
                    char chLocal3 = charAt(this.bp + offset);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        int i3 = this.bp + offset5;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        int i4 = this.bp + offset5;
                        this.bp = i4;
                        this.ch = charAt(i4);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        int i5 = this.bp + offset5;
                        this.bp = i5;
                        this.ch = charAt(i5);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                    this.matchStat = 4;
                    return value;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset3) == 'u' && charAt(this.bp + offset3 + 1) == 'l' && charAt(this.bp + offset3 + 2) == 'l') {
            this.matchStat = 5;
            int offset6 = offset3 + 3;
            int offset7 = offset6 + 1;
            char chLocal4 = charAt(this.bp + offset6);
            if (quote && chLocal4 == '\"') {
                chLocal4 = charAt(this.bp + offset7);
                offset7++;
            }
            while (chLocal4 != ',') {
                if (chLocal4 == '}') {
                    int i6 = this.bp + offset7;
                    this.bp = i6;
                    this.ch = charAt(i6);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                } else if (isWhitespace(chLocal4)) {
                    chLocal4 = charAt(this.bp + offset7);
                    offset7++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            int i7 = this.bp + offset7;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    public Date scanFieldDate(char[] fieldName) {
        int offset;
        Date dateVal;
        char[] cArr = fieldName;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = cArr.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        if (chLocal == '\"') {
            int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, this.bp + cArr.length + 1);
            if (endIndex != -1) {
                int startIndex2 = this.bp + cArr.length + 1;
                String stringVal = subString(startIndex2, endIndex - startIndex2);
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
                    int chars_len = endIndex - ((cArr.length + i2) + 1);
                    stringVal = readString(sub_chars(i2 + cArr.length + 1, chars_len), chars_len);
                }
                int i3 = this.bp;
                int offset4 = offset3 + (endIndex - ((cArr.length + i3) + 1)) + 1;
                offset = offset4 + 1;
                chLocal = charAt(i3 + offset4);
                JSONScanner dateLexer = new JSONScanner(stringVal);
                try {
                    if (dateLexer.scanISO8601DateIfMatch(false)) {
                        dateVal = dateLexer.getCalendar().getTime();
                    } else {
                        this.matchStat = -1;
                        dateLexer.close();
                        return null;
                    }
                } finally {
                    dateLexer.close();
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            char c = '9';
            if (chLocal == '-' || (chLocal >= '0' && chLocal <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (chLocal == '-') {
                    chLocal = charAt(this.bp + offset3);
                    negative = true;
                    offset3++;
                }
                if (chLocal >= '0' && chLocal <= '9') {
                    millis = (long) (chLocal - '0');
                    while (true) {
                        offset = offset3 + 1;
                        chLocal = charAt(this.bp + offset3);
                        if (chLocal < '0' || chLocal > c) {
                            break;
                        }
                        millis = (10 * millis) + ((long) (chLocal - '0'));
                        offset3 = offset;
                        c = '9';
                    }
                } else {
                    offset = offset3;
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
        if (chLocal == ',') {
            int i4 = this.bp + offset;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 3;
            return dateVal;
        } else if (chLocal == '}') {
            int offset5 = offset + 1;
            char chLocal2 = charAt(this.bp + offset);
            if (chLocal2 == ',') {
                this.token = 16;
                int i5 = this.bp + offset5;
                this.bp = i5;
                this.ch = charAt(i5);
            } else if (chLocal2 == ']') {
                this.token = 15;
                int i6 = this.bp + offset5;
                this.bp = i6;
                this.ch = charAt(i6);
            } else if (chLocal2 == '}') {
                this.token = 13;
                int i7 = this.bp + offset5;
                this.bp = i7;
                this.ch = charAt(i7);
            } else if (chLocal2 == 26) {
                this.token = 20;
                this.bp += offset5 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return dateVal;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public Date scanDate(char seperator) {
        int offset;
        Date dateVal;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == '\"') {
            int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, this.bp + 1);
            if (endIndex != -1) {
                int startIndex2 = this.bp + 1;
                String stringVal = subString(startIndex2, endIndex - startIndex2);
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
                    int chars_len = endIndex - (i2 + 1);
                    stringVal = readString(sub_chars(i2 + 1, chars_len), chars_len);
                }
                int i3 = this.bp;
                int offset3 = offset2 + (endIndex - (i3 + 1)) + 1;
                offset = offset3 + 1;
                chLocal = charAt(i3 + offset3);
                JSONScanner dateLexer = new JSONScanner(stringVal);
                try {
                    if (dateLexer.scanISO8601DateIfMatch(false)) {
                        dateVal = dateLexer.getCalendar().getTime();
                    } else {
                        this.matchStat = -1;
                        dateLexer.close();
                        return null;
                    }
                } finally {
                    dateLexer.close();
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            char c = '0';
            if (chLocal == '-' || (chLocal >= '0' && chLocal <= '9')) {
                long millis = 0;
                boolean negative = false;
                if (chLocal == '-') {
                    chLocal = charAt(this.bp + offset2);
                    negative = true;
                    offset2++;
                }
                if (chLocal < '0' || chLocal > '9') {
                    offset = offset2;
                } else {
                    long millis2 = (long) (chLocal - '0');
                    while (true) {
                        offset = offset2 + 1;
                        chLocal = charAt(this.bp + offset2);
                        if (chLocal < c || chLocal > '9') {
                            millis = millis2;
                        } else {
                            long j = millis2;
                            millis2 = (10 * millis2) + ((long) (chLocal - '0'));
                            offset2 = offset;
                            c = '0';
                        }
                    }
                    millis = millis2;
                }
                if (millis < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (negative) {
                    millis = -millis;
                }
                dateVal = new Date(millis);
            } else if (chLocal == 'n' && charAt(this.bp + offset2) == 'u' && charAt(this.bp + offset2 + 1) == 'l' && charAt(this.bp + offset2 + 2) == 'l') {
                this.matchStat = 5;
                dateVal = null;
                int offset4 = offset2 + 3;
                offset = offset4 + 1;
                chLocal = charAt(this.bp + offset4);
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (chLocal == ',') {
            int i4 = this.bp + offset;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 3;
            this.token = 16;
            return dateVal;
        } else if (chLocal == ']') {
            int offset5 = offset + 1;
            char chLocal2 = charAt(this.bp + offset);
            if (chLocal2 == ',') {
                this.token = 16;
                int i5 = this.bp + offset5;
                this.bp = i5;
                this.ch = charAt(i5);
            } else if (chLocal2 == ']') {
                this.token = 15;
                int i6 = this.bp + offset5;
                this.bp = i6;
                this.ch = charAt(i6);
            } else if (chLocal2 == '}') {
                this.token = 13;
                int i7 = this.bp + offset5;
                this.bp = i7;
                this.ch = charAt(i7);
            } else if (chLocal2 == 26) {
                this.token = 20;
                this.bp += offset5 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return dateVal;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public UUID scanFieldUUID(char[] fieldName) {
        int offset;
        UUID uuid;
        char chLocal;
        int num;
        int num2;
        int num3;
        int num4;
        int num5;
        int num6;
        int num7;
        char[] cArr = fieldName;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = cArr.length;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        if (chLocal2 == '\"') {
            int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, this.bp + cArr.length + 1);
            if (endIndex != -1) {
                int startIndex2 = this.bp + cArr.length + 1;
                int len = endIndex - startIndex2;
                char c = 'F';
                char c2 = 'f';
                char c3 = '9';
                char c4 = 'A';
                char c5 = 'a';
                char c6 = '0';
                if (len == 36) {
                    long mostSigBits = 0;
                    long leastSigBits = 0;
                    int i = 0;
                    while (i < 8) {
                        char ch2 = charAt(startIndex2 + i);
                        if (ch2 >= '0' && ch2 <= '9') {
                            num7 = ch2 - '0';
                        } else if (ch2 >= 'a' && ch2 <= 'f') {
                            num7 = (ch2 - 'a') + 10;
                        } else if (ch2 < c4 || ch2 > c) {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num7 = (ch2 - 'A') + 10;
                        }
                        mostSigBits = (mostSigBits << 4) | ((long) num7);
                        i++;
                        offset3 = offset3;
                        c4 = 'A';
                        c = 'F';
                    }
                    int offset4 = offset3;
                    for (int i2 = 9; i2 < 13; i2++) {
                        char ch3 = charAt(startIndex2 + i2);
                        if (ch3 >= '0' && ch3 <= '9') {
                            num6 = ch3 - '0';
                        } else if (ch3 >= 'a' && ch3 <= 'f') {
                            num6 = (ch3 - 'a') + 10;
                        } else if (ch3 < 'A' || ch3 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num6 = (ch3 - 'A') + 10;
                        }
                        mostSigBits = (mostSigBits << 4) | ((long) num6);
                    }
                    int i3 = 14;
                    long mostSigBits2 = mostSigBits;
                    while (i3 < 18) {
                        char ch4 = charAt(startIndex2 + i3);
                        if (ch4 >= '0' && ch4 <= c3) {
                            num5 = ch4 - '0';
                        } else if (ch4 >= 'a' && ch4 <= 'f') {
                            num5 = (ch4 - 'a') + 10;
                        } else if (ch4 < 'A' || ch4 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num5 = (ch4 - 'A') + 10;
                        }
                        mostSigBits2 = (mostSigBits2 << 4) | ((long) num5);
                        i3++;
                        endIndex = endIndex;
                        c3 = '9';
                    }
                    int endIndex2 = endIndex;
                    int i4 = 19;
                    while (i4 < 23) {
                        char ch5 = charAt(startIndex2 + i4);
                        if (ch5 >= '0' && ch5 <= '9') {
                            num4 = ch5 - '0';
                        } else if (ch5 >= 'a' && ch5 <= c2) {
                            num4 = (ch5 - 'a') + 10;
                        } else if (ch5 < 'A' || ch5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num4 = (ch5 - 'A') + 10;
                        }
                        leastSigBits = (leastSigBits << 4) | ((long) num4);
                        i4++;
                        c2 = 'f';
                    }
                    long leastSigBits2 = leastSigBits;
                    for (int i5 = 24; i5 < 36; i5++) {
                        char ch6 = charAt(startIndex2 + i5);
                        if (ch6 >= '0' && ch6 <= '9') {
                            num3 = ch6 - '0';
                        } else if (ch6 >= 'a' && ch6 <= 'f') {
                            num3 = (ch6 - 'a') + 10;
                        } else if (ch6 < 'A' || ch6 > 'F') {
                            char c7 = ch6;
                            this.matchStat = -2;
                            return null;
                        } else {
                            num3 = (ch6 - 'A') + 10;
                        }
                        char c8 = ch6;
                        leastSigBits2 = (leastSigBits2 << 4) | ((long) num3);
                    }
                    uuid = new UUID(mostSigBits2, leastSigBits2);
                    int i6 = this.bp;
                    int offset5 = offset4 + (endIndex2 - ((cArr.length + i6) + 1)) + 1;
                    offset = offset5 + 1;
                    chLocal = charAt(i6 + offset5);
                } else {
                    int offset6 = offset3;
                    int endIndex3 = endIndex;
                    if (len == 32) {
                        long mostSigBits3 = 0;
                        long leastSigBits3 = 0;
                        int i7 = 0;
                        while (i7 < 16) {
                            char ch7 = charAt(startIndex2 + i7);
                            if (ch7 >= c6 && ch7 <= '9') {
                                num2 = ch7 - '0';
                            } else if (ch7 >= c5 && ch7 <= 'f') {
                                num2 = (ch7 - 'a') + 10;
                            } else if (ch7 < 'A' || ch7 > 'F') {
                                this.matchStat = -2;
                                return null;
                            } else {
                                num2 = (ch7 - 'A') + 10;
                            }
                            mostSigBits3 = (mostSigBits3 << 4) | ((long) num2);
                            i7++;
                            c6 = '0';
                            c5 = 'a';
                        }
                        int i8 = 16;
                        for (int i9 = 32; i8 < i9; i9 = 32) {
                            char ch8 = charAt(startIndex2 + i8);
                            if (ch8 >= '0') {
                                if (ch8 <= '9') {
                                    num = ch8 - '0';
                                    leastSigBits3 = (leastSigBits3 << 4) | ((long) num);
                                    i8++;
                                }
                            }
                            if (ch8 >= 'a') {
                                if (ch8 <= 'f') {
                                    num = (ch8 - 'a') + 10;
                                    leastSigBits3 = (leastSigBits3 << 4) | ((long) num);
                                    i8++;
                                }
                            }
                            if (ch8 < 'A' || ch8 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            num = (ch8 - 'A') + 10;
                            leastSigBits3 = (leastSigBits3 << 4) | ((long) num);
                            i8++;
                        }
                        uuid = new UUID(mostSigBits3, leastSigBits3);
                        int i10 = this.bp;
                        int offset7 = offset6 + (endIndex3 - ((cArr.length + i10) + 1)) + 1;
                        offset = offset7 + 1;
                        chLocal = charAt(i10 + offset7);
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            int offset8 = offset3;
            if (chLocal2 == 'n') {
                int offset9 = offset8 + 1;
                if (charAt(this.bp + offset8) == 'u') {
                    int offset10 = offset9 + 1;
                    if (charAt(this.bp + offset9) == 'l') {
                        int offset11 = offset10 + 1;
                        if (charAt(this.bp + offset10) == 'l') {
                            uuid = null;
                            offset = offset11 + 1;
                            chLocal = charAt(this.bp + offset11);
                        }
                    } else {
                        int i11 = offset10;
                    }
                }
            } else {
                int i12 = offset8;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal == ',') {
            int i13 = this.bp + offset;
            this.bp = i13;
            this.ch = charAt(i13);
            this.matchStat = 3;
            return uuid;
        } else if (chLocal == '}') {
            int offset12 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 == ',') {
                this.token = 16;
                int i14 = this.bp + offset12;
                this.bp = i14;
                this.ch = charAt(i14);
            } else if (chLocal3 == ']') {
                this.token = 15;
                int i15 = this.bp + offset12;
                this.bp = i15;
                this.ch = charAt(i15);
            } else if (chLocal3 == '}') {
                this.token = 13;
                int i16 = this.bp + offset12;
                this.bp = i16;
                this.ch = charAt(i16);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset12 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return uuid;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public UUID scanUUID(char seperator) {
        int offset;
        UUID uuid;
        char chLocal;
        int num;
        int num2;
        int num3;
        int num4;
        int num5;
        int num6;
        int num7;
        this.matchStat = 0;
        int offset2 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        char c = 4;
        if (chLocal2 == '\"') {
            int endIndex = indexOf(StringUtil.DOUBLE_QUOTE, this.bp + 1);
            if (endIndex != -1) {
                int startIndex2 = this.bp + 1;
                int len = endIndex - startIndex2;
                char c2 = 'F';
                char c3 = 'f';
                char c4 = '9';
                char c5 = 'A';
                char c6 = 'a';
                char c7 = '0';
                if (len == 36) {
                    long mostSigBits = 0;
                    long leastSigBits = 0;
                    int i = 0;
                    while (i < 8) {
                        char ch2 = charAt(startIndex2 + i);
                        if (ch2 >= '0' && ch2 <= '9') {
                            num7 = ch2 - '0';
                        } else if (ch2 >= 'a' && ch2 <= 'f') {
                            num7 = (ch2 - 'a') + 10;
                        } else if (ch2 < c5 || ch2 > c2) {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num7 = (ch2 - 'A') + 10;
                        }
                        mostSigBits = (mostSigBits << 4) | ((long) num7);
                        i++;
                        endIndex = endIndex;
                        c5 = 'A';
                        c2 = 'F';
                    }
                    int endIndex2 = endIndex;
                    int i2 = 9;
                    while (i2 < 13) {
                        char ch3 = charAt(startIndex2 + i2);
                        if (ch3 >= '0' && ch3 <= '9') {
                            num6 = ch3 - '0';
                        } else if (ch3 >= c6 && ch3 <= 'f') {
                            num6 = (ch3 - 'a') + 10;
                        } else if (ch3 < 'A' || ch3 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num6 = (ch3 - 'A') + 10;
                        }
                        mostSigBits = (mostSigBits << 4) | ((long) num6);
                        i2++;
                        offset2 = offset2;
                        c6 = 'a';
                    }
                    int offset3 = offset2;
                    long mostSigBits2 = mostSigBits;
                    for (int i3 = 14; i3 < 18; i3++) {
                        char ch4 = charAt(startIndex2 + i3);
                        if (ch4 >= '0' && ch4 <= '9') {
                            num5 = ch4 - '0';
                        } else if (ch4 >= 'a' && ch4 <= 'f') {
                            num5 = (ch4 - 'a') + 10;
                        } else if (ch4 < 'A' || ch4 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num5 = (ch4 - 'A') + 10;
                        }
                        mostSigBits2 = (mostSigBits2 << 4) | ((long) num5);
                    }
                    int i4 = 19;
                    while (i4 < 23) {
                        char ch5 = charAt(startIndex2 + i4);
                        if (ch5 >= '0' && ch5 <= c4) {
                            num4 = ch5 - '0';
                        } else if (ch5 >= 'a' && ch5 <= 'f') {
                            num4 = (ch5 - 'a') + 10;
                        } else if (ch5 < 'A' || ch5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num4 = (ch5 - 'A') + 10;
                        }
                        leastSigBits = (leastSigBits << c) | ((long) num4);
                        i4++;
                        c4 = '9';
                        c = 4;
                    }
                    int i5 = 24;
                    long leastSigBits2 = leastSigBits;
                    while (i5 < 36) {
                        char ch6 = charAt(startIndex2 + i5);
                        if (ch6 >= c7 && ch6 <= '9') {
                            num3 = ch6 - '0';
                        } else if (ch6 >= 'a' && ch6 <= 'f') {
                            num3 = (ch6 - 'a') + 10;
                        } else if (ch6 < 'A' || ch6 > 'F') {
                            this.matchStat = -2;
                            return null;
                        } else {
                            num3 = (ch6 - 'A') + 10;
                        }
                        leastSigBits2 = (leastSigBits2 << 4) | ((long) num3);
                        i5++;
                        c7 = '0';
                    }
                    uuid = new UUID(mostSigBits2, leastSigBits2);
                    int i6 = this.bp;
                    int offset4 = offset3 + (endIndex2 - (i6 + 1)) + 1;
                    offset = offset4 + 1;
                    chLocal = charAt(i6 + offset4);
                } else {
                    int offset5 = offset2;
                    int endIndex3 = endIndex;
                    if (len == 32) {
                        long mostSigBits3 = 0;
                        long leastSigBits3 = 0;
                        int i7 = 0;
                        while (i7 < 16) {
                            char ch7 = charAt(startIndex2 + i7);
                            if (ch7 >= '0' && ch7 <= '9') {
                                num2 = ch7 - '0';
                            } else if (ch7 >= 'a' && ch7 <= c3) {
                                num2 = (ch7 - 'a') + 10;
                            } else if (ch7 < 'A' || ch7 > 'F') {
                                this.matchStat = -2;
                                return null;
                            } else {
                                num2 = (ch7 - 'A') + 10;
                            }
                            mostSigBits3 = (mostSigBits3 << 4) | ((long) num2);
                            i7++;
                            c3 = 'f';
                        }
                        int i8 = 16;
                        for (int i9 = 32; i8 < i9; i9 = 32) {
                            char ch8 = charAt(startIndex2 + i8);
                            if (ch8 >= '0') {
                                if (ch8 <= '9') {
                                    num = ch8 - '0';
                                    leastSigBits3 = (leastSigBits3 << 4) | ((long) num);
                                    i8++;
                                }
                            }
                            if (ch8 >= 'a') {
                                if (ch8 <= 'f') {
                                    num = (ch8 - 'a') + 10;
                                    leastSigBits3 = (leastSigBits3 << 4) | ((long) num);
                                    i8++;
                                }
                            }
                            if (ch8 < 'A' || ch8 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            num = (ch8 - 'A') + 10;
                            leastSigBits3 = (leastSigBits3 << 4) | ((long) num);
                            i8++;
                        }
                        uuid = new UUID(mostSigBits3, leastSigBits3);
                        int i10 = this.bp;
                        int offset6 = offset5 + (endIndex3 - (i10 + 1)) + 1;
                        offset = offset6 + 1;
                        chLocal = charAt(i10 + offset6);
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                }
            } else {
                throw new JSONException("unclosed str");
            }
        } else {
            int offset7 = offset2;
            if (chLocal2 == 'n') {
                int offset8 = offset7 + 1;
                if (charAt(this.bp + offset7) == 'u') {
                    int offset9 = offset8 + 1;
                    if (charAt(this.bp + offset8) == 'l') {
                        int offset10 = offset9 + 1;
                        if (charAt(this.bp + offset9) == 'l') {
                            uuid = null;
                            offset = offset10 + 1;
                            chLocal = charAt(this.bp + offset10);
                        } else {
                            int i11 = offset10;
                        }
                    } else {
                        int i12 = offset9;
                    }
                }
            } else {
                int i13 = offset7;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal == ',') {
            int i14 = this.bp + offset;
            this.bp = i14;
            this.ch = charAt(i14);
            this.matchStat = 3;
            return uuid;
        } else if (chLocal == ']') {
            int offset11 = offset + 1;
            char chLocal3 = charAt(this.bp + offset);
            if (chLocal3 == ',') {
                this.token = 16;
                int i15 = this.bp + offset11;
                this.bp = i15;
                this.ch = charAt(i15);
            } else if (chLocal3 == ']') {
                this.token = 15;
                int i16 = this.bp + offset11;
                this.bp = i16;
                this.ch = charAt(i16);
            } else if (chLocal3 == '}') {
                this.token = 13;
                int i17 = this.bp + offset11;
                this.bp = i17;
                this.ch = charAt(i17);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset11 - 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return uuid;
        } else {
            this.matchStat = -1;
            return null;
        }
    }

    public final void scanTrue() {
        if (this.ch == 't') {
            next();
            if (this.ch == 'r') {
                next();
                if (this.ch == 'u') {
                    next();
                    if (this.ch == 'e') {
                        next();
                        char c = this.ch;
                        if (c == ' ' || c == ',' || c == '}' || c == ']' || c == 10 || c == 13 || c == 9 || c == 26 || c == 12 || c == 8 || c == ':' || c == '/') {
                            this.token = 6;
                            return;
                        }
                        throw new JSONException("scan true error");
                    }
                    throw new JSONException("error parse true");
                }
                throw new JSONException("error parse true");
            }
            throw new JSONException("error parse true");
        }
        throw new JSONException("error parse true");
    }

    public final void scanNullOrNew() {
        scanNullOrNew(true);
    }

    public final void scanNullOrNew(boolean acceptColon) {
        if (this.ch == 'n') {
            next();
            char c = this.ch;
            if (c == 'u') {
                next();
                if (this.ch == 'l') {
                    next();
                    if (this.ch == 'l') {
                        next();
                        char c2 = this.ch;
                        if (c2 == ' ' || c2 == ',' || c2 == '}' || c2 == ']' || c2 == 10 || c2 == 13 || c2 == 9 || c2 == 26 || ((c2 == ':' && acceptColon) || c2 == 12 || c2 == 8)) {
                            this.token = 8;
                            return;
                        }
                        throw new JSONException("scan null error");
                    }
                    throw new JSONException("error parse null");
                }
                throw new JSONException("error parse null");
            } else if (c == 'e') {
                next();
                if (this.ch == 'w') {
                    next();
                    char c3 = this.ch;
                    if (c3 == ' ' || c3 == ',' || c3 == '}' || c3 == ']' || c3 == 10 || c3 == 13 || c3 == 9 || c3 == 26 || c3 == 12 || c3 == 8) {
                        this.token = 9;
                        return;
                    }
                    throw new JSONException("scan new error");
                }
                throw new JSONException("error parse new");
            } else {
                throw new JSONException("error parse new");
            }
        } else {
            throw new JSONException("error parse null or new");
        }
    }

    public final void scanFalse() {
        if (this.ch == 'f') {
            next();
            if (this.ch == 'a') {
                next();
                if (this.ch == 'l') {
                    next();
                    if (this.ch == 's') {
                        next();
                        if (this.ch == 'e') {
                            next();
                            char c = this.ch;
                            if (c == ' ' || c == ',' || c == '}' || c == ']' || c == 10 || c == 13 || c == 9 || c == 26 || c == 12 || c == 8 || c == ':' || c == '/') {
                                this.token = 7;
                                return;
                            }
                            throw new JSONException("scan false error");
                        }
                        throw new JSONException("error parse false");
                    }
                    throw new JSONException("error parse false");
                }
                throw new JSONException("error parse false");
            }
            throw new JSONException("error parse false");
        }
        throw new JSONException("error parse false");
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String ident = stringVal();
        if (BuildConfig.TRAVIS.equalsIgnoreCase(ident)) {
            this.token = 8;
        } else if ("new".equals(ident)) {
            this.token = 9;
        } else if ("true".equals(ident)) {
            this.token = 6;
        } else if (Bugly.SDK_IS_DEV.equals(ident)) {
            this.token = 7;
        } else if ("undefined".equals(ident)) {
            this.token = 23;
        } else if ("Set".equals(ident)) {
            this.token = 21;
        } else if ("TreeSet".equals(ident)) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    public static String readString(char[] chars, int chars_len) {
        char[] sbuf2 = new char[chars_len];
        int len = 0;
        int i = 0;
        while (i < chars_len) {
            char ch2 = chars[i];
            if (ch2 != '\\') {
                sbuf2[len] = ch2;
                len++;
            } else {
                i++;
                switch (chars[i]) {
                    case '\"':
                        sbuf2[len] = StringUtil.DOUBLE_QUOTE;
                        len++;
                        break;
                    case '\'':
                        sbuf2[len] = '\'';
                        len++;
                        break;
                    case '/':
                        sbuf2[len] = '/';
                        len++;
                        break;
                    case '0':
                        sbuf2[len] = 0;
                        len++;
                        break;
                    case '1':
                        sbuf2[len] = 1;
                        len++;
                        break;
                    case '2':
                        sbuf2[len] = 2;
                        len++;
                        break;
                    case '3':
                        sbuf2[len] = 3;
                        len++;
                        break;
                    case '4':
                        sbuf2[len] = 4;
                        len++;
                        break;
                    case '5':
                        sbuf2[len] = 5;
                        len++;
                        break;
                    case '6':
                        sbuf2[len] = 6;
                        len++;
                        break;
                    case '7':
                        sbuf2[len] = 7;
                        len++;
                        break;
                    case 'F':
                    case 'f':
                        sbuf2[len] = 12;
                        len++;
                        break;
                    case '\\':
                        sbuf2[len] = '\\';
                        len++;
                        break;
                    case 'b':
                        sbuf2[len] = 8;
                        len++;
                        break;
                    case 'n':
                        sbuf2[len] = 10;
                        len++;
                        break;
                    case 'r':
                        sbuf2[len] = StringUtil.CARRIAGE_RETURN;
                        len++;
                        break;
                    case 't':
                        sbuf2[len] = 9;
                        len++;
                        break;
                    case 'u':
                        int i2 = i + 1;
                        int i3 = i2 + 1;
                        int i4 = i3 + 1;
                        i = i4 + 1;
                        sbuf2[len] = (char) Integer.parseInt(new String(new char[]{chars[i2], chars[i3], chars[i4], chars[i]}), 16);
                        len++;
                        break;
                    case 'v':
                        sbuf2[len] = 11;
                        len++;
                        break;
                    case 'x':
                        int[] iArr = digits;
                        int i5 = i + 1;
                        i = i5 + 1;
                        sbuf2[len] = (char) ((iArr[chars[i5]] * 16) + iArr[chars[i]]);
                        len++;
                        break;
                    default:
                        throw new JSONException("unclosed.str.lit");
                }
            }
            i++;
        }
        return new String(sbuf2, 0, len);
    }

    public boolean isBlankInput() {
        int i = 0;
        while (true) {
            char chLocal = charAt(i);
            if (chLocal == 26) {
                this.token = 20;
                return true;
            } else if (!isWhitespace(chLocal)) {
                return false;
            } else {
                i++;
            }
        }
    }

    public final void skipWhitespace() {
        while (true) {
            char c = this.ch;
            if (c > '/') {
                return;
            }
            if (c == ' ' || c == 13 || c == 10 || c == 9 || c == 12 || c == 8) {
                next();
            } else if (c == '/') {
                skipComment();
            } else {
                return;
            }
        }
    }

    private void scanStringSingleQuote() {
        char x1;
        char x2;
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char chLocal = next();
            if (chLocal == '\'') {
                this.token = 4;
                next();
                return;
            } else if (chLocal != 26) {
                boolean z = true;
                if (chLocal == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        int i = this.sp;
                        char[] cArr = this.sbuf;
                        if (i > cArr.length) {
                            char[] newsbuf = new char[(i * 2)];
                            System.arraycopy(cArr, 0, newsbuf, 0, cArr.length);
                            this.sbuf = newsbuf;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char chLocal2 = next();
                    switch (chLocal2) {
                        case '\"':
                            putChar(StringUtil.DOUBLE_QUOTE);
                            break;
                        case '\'':
                            putChar('\'');
                            break;
                        case '/':
                            putChar('/');
                            break;
                        case '0':
                            putChar(0);
                            break;
                        case '1':
                            putChar(1);
                            break;
                        case '2':
                            putChar(2);
                            break;
                        case '3':
                            putChar(3);
                            break;
                        case '4':
                            putChar(4);
                            break;
                        case '5':
                            putChar(5);
                            break;
                        case '6':
                            putChar(6);
                            break;
                        case '7':
                            putChar(7);
                            break;
                        case 'F':
                        case 'f':
                            putChar(12);
                            break;
                        case '\\':
                            putChar('\\');
                            break;
                        case 'b':
                            putChar(8);
                            break;
                        case 'n':
                            putChar(10);
                            break;
                        case 'r':
                            putChar(StringUtil.CARRIAGE_RETURN);
                            break;
                        case 't':
                            putChar(9);
                            break;
                        case 'u':
                            putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                            break;
                        case 'v':
                            putChar(11);
                            break;
                        case 'x':
                            x1 = next();
                            x2 = next();
                            boolean hex1 = (x1 >= '0' && x1 <= '9') || (x1 >= 'a' && x1 <= 'f') || (x1 >= 'A' && x1 <= 'F');
                            if ((x2 < '0' || x2 > '9') && ((x2 < 'a' || x2 > 'f') && (x2 < 'A' || x2 > 'F'))) {
                                z = false;
                            }
                            boolean hex2 = z;
                            if (hex1 && hex2) {
                                int[] iArr = digits;
                                putChar((char) ((iArr[x1] * 16) + iArr[x2]));
                                break;
                            } else {
                                break;
                            }
                        default:
                            this.ch = chLocal2;
                            throw new JSONException("unclosed single-quote string");
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else {
                    int i2 = this.sp;
                    char[] cArr2 = this.sbuf;
                    if (i2 == cArr2.length) {
                        putChar(chLocal);
                    } else {
                        this.sp = i2 + 1;
                        cArr2[i2] = chLocal;
                    }
                }
            } else if (!isEOF()) {
                putChar(JSONLexer.EOI);
            } else {
                throw new JSONException("unclosed single-quote string");
            }
        }
        throw new JSONException("invalid escape character \\x" + x1 + x2);
    }

    /* access modifiers changed from: protected */
    public final void putChar(char ch2) {
        int i = this.sp;
        char[] cArr = this.sbuf;
        if (i == cArr.length) {
            char[] newsbuf = new char[(cArr.length * 2)];
            System.arraycopy(cArr, 0, newsbuf, 0, cArr.length);
            this.sbuf = newsbuf;
        }
        char[] newsbuf2 = this.sbuf;
        int i2 = this.sp;
        this.sp = i2 + 1;
        newsbuf2[i2] = ch2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0052  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void scanHex() {
        /*
            r6 = this;
            char r0 = r6.ch
            java.lang.String r1 = "illegal state. "
            r2 = 120(0x78, float:1.68E-43)
            if (r0 != r2) goto L_0x007e
            r6.next()
            char r0 = r6.ch
            r2 = 39
            if (r0 != r2) goto L_0x0067
            int r0 = r6.bp
            r6.np = r0
            r6.next()
            char r0 = r6.ch
            r3 = 26
            if (r0 != r2) goto L_0x0024
            r6.next()
            r6.token = r3
            return
        L_0x0024:
            r0 = 0
        L_0x0025:
            char r4 = r6.next()
            r5 = 48
            if (r4 < r5) goto L_0x0031
            r5 = 57
            if (r4 <= r5) goto L_0x0039
        L_0x0031:
            r5 = 65
            if (r4 < r5) goto L_0x0043
            r5 = 70
            if (r4 > r5) goto L_0x0043
        L_0x0039:
            int r5 = r6.sp
            int r5 = r5 + 1
            r6.sp = r5
            int r0 = r0 + 1
            goto L_0x0025
        L_0x0043:
            if (r4 != r2) goto L_0x0052
            int r1 = r6.sp
            int r1 = r1 + 1
            r6.sp = r1
            r6.next()
            r6.token = r3
            return
        L_0x0052:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r1)
            r3.append(r4)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x0067:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            char r1 = r6.ch
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x007e:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            char r1 = r6.ch
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanHex():void");
    }

    public final void scanNumber() {
        char c;
        this.np = this.bp;
        if (this.ch == '-') {
            this.sp++;
            next();
        }
        while (true) {
            c = this.ch;
            if (c < '0' || c > '9') {
                boolean isDouble = false;
            } else {
                this.sp++;
                next();
            }
        }
        boolean isDouble2 = false;
        if (c == '.') {
            this.sp++;
            next();
            isDouble2 = true;
            while (true) {
                char c2 = this.ch;
                if (c2 < '0' || c2 > '9') {
                    break;
                }
                this.sp++;
                next();
            }
        }
        int i = this.sp;
        if (i <= 65535) {
            char c3 = this.ch;
            if (c3 == 'L') {
                this.sp = i + 1;
                next();
            } else if (c3 == 'S') {
                this.sp = i + 1;
                next();
            } else if (c3 == 'B') {
                this.sp = i + 1;
                next();
            } else if (c3 == 'F') {
                this.sp = i + 1;
                next();
                isDouble2 = true;
            } else if (c3 == 'D') {
                this.sp = i + 1;
                next();
                isDouble2 = true;
            } else if (c3 == 'e' || c3 == 'E') {
                this.sp = i + 1;
                next();
                char c4 = this.ch;
                if (c4 == '+' || c4 == '-') {
                    this.sp++;
                    next();
                }
                while (true) {
                    char c5 = this.ch;
                    if (c5 >= '0' && c5 <= '9') {
                        this.sp++;
                        next();
                    } else if (c5 == 'D' || c5 == 'F') {
                        this.sp++;
                        next();
                    }
                }
                this.sp++;
                next();
                isDouble2 = true;
            }
            if (isDouble2) {
                this.token = 3;
            } else {
                this.token = 2;
            }
        } else {
            throw new JSONException("scanNumber overflow");
        }
    }

    public final long longValue() {
        long limit;
        int i;
        long result = 0;
        boolean negative = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i2 = this.np;
        int i3 = this.np;
        int max = this.sp + i3;
        if (charAt(i3) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i2++;
        } else {
            limit = -9223372036854775807L;
        }
        if (i2 < max) {
            result = (long) (-(charAt(i2) - 48));
            i2++;
        }
        while (true) {
            if (i2 >= max) {
                break;
            }
            i = i2 + 1;
            int i4 = charAt(i2);
            if (i4 == 76 || i4 == 83 || i4 == 66) {
                i2 = i;
            } else {
                int digit = i4 - 48;
                if (result >= MULTMIN_RADIX_TEN) {
                    long result2 = result * 10;
                    if (result2 >= ((long) digit) + limit) {
                        result = result2 - ((long) digit);
                        i2 = i;
                    } else {
                        throw new NumberFormatException(numberString());
                    }
                } else {
                    throw new NumberFormatException(numberString());
                }
            }
        }
        i2 = i;
        if (!negative) {
            return -result;
        }
        if (i2 > this.np + 1) {
            return result;
        }
        throw new NumberFormatException(numberString());
    }

    public final Number decimalValue(boolean decimal) {
        char chLocal = charAt((this.np + this.sp) - 1);
        if (chLocal == 'F') {
            try {
                return Float.valueOf(Float.parseFloat(numberString()));
            } catch (NumberFormatException ex) {
                throw new JSONException(ex.getMessage() + ", " + info());
            }
        } else if (chLocal == 'D') {
            return Double.valueOf(Double.parseDouble(numberString()));
        } else {
            if (decimal) {
                return decimalValue();
            }
            return Double.valueOf(doubleValue());
        }
    }

    public static boolean isWhitespace(char ch2) {
        return ch2 <= ' ' && (ch2 == ' ' || ch2 == 10 || ch2 == 13 || ch2 == 9 || ch2 == 12 || ch2 == 8);
    }

    public String[] scanFieldStringArray(char[] fieldName, int argTypesCount, SymbolTable typeSymbolTable) {
        throw new UnsupportedOperationException();
    }

    public boolean matchField2(char[] fieldName) {
        throw new UnsupportedOperationException();
    }

    public int getFeatures() {
        return this.features;
    }

    public void setFeatures(int features2) {
        this.features = features2;
    }
}
