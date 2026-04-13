package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.MathContext;

public final class JSONReaderScanner extends JSONLexerBase {
    private static final ThreadLocal<char[]> BUF_LOCAL = new ThreadLocal<>();
    private char[] buf;
    private int bufLength;
    private Reader reader;

    public JSONReaderScanner(String input) {
        this(input, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONReaderScanner(String input, int features) {
        this((Reader) new StringReader(input), features);
    }

    public JSONReaderScanner(char[] input, int inputLength) {
        this(input, inputLength, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONReaderScanner(Reader reader2) {
        this(reader2, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONReaderScanner(Reader reader2, int features) {
        super(features);
        this.reader = reader2;
        ThreadLocal<char[]> threadLocal = BUF_LOCAL;
        char[] cArr = threadLocal.get();
        this.buf = cArr;
        if (cArr != null) {
            threadLocal.set((Object) null);
        }
        if (this.buf == null) {
            this.buf = new char[16384];
        }
        try {
            this.bufLength = reader2.read(this.buf);
            this.bp = -1;
            next();
            if (this.ch == 65279) {
                next();
            }
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public JSONReaderScanner(char[] input, int inputLength, int features) {
        this((Reader) new CharArrayReader(input, 0, inputLength), features);
    }

    public final char charAt(int index) {
        int rest = this.bufLength;
        if (index >= rest) {
            if (rest != -1) {
                int i = this.bp;
                if (i == 0) {
                    char[] cArr = this.buf;
                    char[] buf2 = new char[((cArr.length * 3) / 2)];
                    System.arraycopy(cArr, i, buf2, 0, rest);
                    int length = buf2.length;
                    int i2 = this.bufLength;
                    try {
                        this.bufLength += this.reader.read(buf2, i2, length - i2);
                        this.buf = buf2;
                    } catch (IOException e) {
                        throw new JSONException(e.getMessage(), e);
                    }
                } else {
                    int rest2 = rest - i;
                    if (rest2 > 0) {
                        char[] cArr2 = this.buf;
                        System.arraycopy(cArr2, i, cArr2, 0, rest2);
                    }
                    try {
                        Reader reader2 = this.reader;
                        char[] cArr3 = this.buf;
                        int read = reader2.read(cArr3, rest2, cArr3.length - rest2);
                        this.bufLength = read;
                        if (read == 0) {
                            throw new JSONException("illegal state, textLength is zero");
                        } else if (read == -1) {
                            return JSONLexer.EOI;
                        } else {
                            this.bufLength = read + rest2;
                            int i3 = this.bp;
                            index -= i3;
                            this.np -= i3;
                            this.bp = 0;
                        }
                    } catch (IOException e2) {
                        throw new JSONException(e2.getMessage(), e2);
                    }
                }
            } else if (index < this.sp) {
                return this.buf[index];
            } else {
                return JSONLexer.EOI;
            }
        }
        return this.buf[index];
    }

    public final int indexOf(char ch, int startIndex) {
        int offset = startIndex - this.bp;
        while (true) {
            char chLoal = charAt(this.bp + offset);
            if (ch == chLoal) {
                return this.bp + offset;
            }
            if (chLoal == 26) {
                return -1;
            }
            offset++;
        }
    }

    public final String addSymbol(int offset, int len, int hash, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.buf, offset, len, hash);
    }

    public final char next() {
        int index = this.bp + 1;
        this.bp = index;
        int i = this.bufLength;
        if (index >= i) {
            if (i == -1) {
                return JSONLexer.EOI;
            }
            int i2 = this.sp;
            if (i2 > 0) {
                int offset = i - i2;
                if (this.ch == '\"' && offset > 0) {
                    offset--;
                }
                char[] cArr = this.buf;
                System.arraycopy(cArr, offset, cArr, 0, i2);
            }
            this.np = -1;
            int startPos = this.sp;
            this.bp = startPos;
            index = startPos;
            try {
                char[] cArr2 = this.buf;
                int readLength = cArr2.length - startPos;
                if (readLength == 0) {
                    char[] newBuf = new char[(cArr2.length * 2)];
                    System.arraycopy(cArr2, 0, newBuf, 0, cArr2.length);
                    this.buf = newBuf;
                    readLength = newBuf.length - startPos;
                }
                int read = this.reader.read(this.buf, this.bp, readLength);
                this.bufLength = read;
                if (read == 0) {
                    throw new JSONException("illegal stat, textLength is zero");
                } else if (read == -1) {
                    this.ch = JSONLexer.EOI;
                    return JSONLexer.EOI;
                } else {
                    this.bufLength = read + this.bp;
                }
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        }
        char c = this.buf[index];
        this.ch = c;
        return c;
    }

    /* access modifiers changed from: protected */
    public final void copyTo(int offset, int count, char[] dest) {
        System.arraycopy(this.buf, offset, dest, 0, count);
    }

    public final boolean charArrayCompare(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (charAt(this.bp + i) != chars[i]) {
                return false;
            }
        }
        return true;
    }

    public byte[] bytesValue() {
        if (this.token != 26) {
            return IOUtils.decodeBase64(this.buf, this.np + 1, this.sp);
        }
        throw new JSONException("TODO");
    }

    /* access modifiers changed from: protected */
    public final void arrayCopy(int srcPos, char[] dest, int destPos, int length) {
        System.arraycopy(this.buf, srcPos, dest, destPos, length);
    }

    public final String stringVal() {
        if (this.hasSpecial) {
            return new String(this.sbuf, 0, this.sp);
        }
        int offset = this.np + 1;
        if (offset < 0) {
            throw new IllegalStateException();
        } else if (offset <= this.buf.length - this.sp) {
            return new String(this.buf, offset, this.sp);
        } else {
            throw new IllegalStateException();
        }
    }

    public final String subString(int offset, int count) {
        if (count >= 0) {
            return new String(this.buf, offset, count);
        }
        throw new StringIndexOutOfBoundsException(count);
    }

    public final char[] sub_chars(int offset, int count) {
        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        } else if (offset == 0) {
            return this.buf;
        } else {
            char[] chars = new char[count];
            System.arraycopy(this.buf, offset, chars, 0, count);
            return chars;
        }
    }

    public final String numberString() {
        int offset = this.np;
        if (offset == -1) {
            offset = 0;
        }
        char chLocal = charAt((this.sp + offset) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        return new String(this.buf, offset, sp);
    }

    public final BigDecimal decimalValue() {
        int offset = this.np;
        if (offset == -1) {
            offset = 0;
        }
        char chLocal = charAt((this.sp + offset) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        if (sp <= 65535) {
            return new BigDecimal(this.buf, offset, sp, MathContext.UNLIMITED);
        }
        throw new JSONException("decimal overflow");
    }

    public void close() {
        super.close();
        char[] cArr = this.buf;
        if (cArr.length <= 65536) {
            BUF_LOCAL.set(cArr);
        }
        this.buf = null;
        IOUtils.close(this.reader);
    }

    public boolean isEOF() {
        if (this.bufLength == -1) {
            return true;
        }
        int i = this.bp;
        char[] cArr = this.buf;
        if (i != cArr.length) {
            return this.ch == 26 && i + 1 >= cArr.length;
        }
        return true;
    }

    public final boolean isBlankInput() {
        int i = 0;
        while (true) {
            char chLocal = this.buf[i];
            if (chLocal == 26) {
                this.token = 20;
                return true;
            } else if (!JSONLexerBase.isWhitespace(chLocal)) {
                return false;
            } else {
                i++;
            }
        }
    }
}
