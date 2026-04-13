package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.utils.Charsets;

public final class CharChunk implements Chunk, Cloneable, Serializable {
    public static final Charset DEFAULT_HTTP_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
    private static final long serialVersionUID = -1;
    private char[] buff;
    private String cachedString;
    private int end;
    private transient CharInputChannel in = null;
    private boolean isSet = false;
    private int limit = -1;
    private boolean optimizedWrite = true;
    private transient CharOutputChannel out = null;
    private int start;

    public interface CharInputChannel {
        int realReadChars(char[] cArr, int i, int i2);
    }

    public interface CharOutputChannel {
        void realWriteChars(char[] cArr, int i, int i2);
    }

    public CharChunk() {
    }

    public CharChunk(int size) {
        allocate(size, -1);
    }

    public CharChunk getClone() {
        try {
            return (CharChunk) clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isNull() {
        return this.end <= 0 && !this.isSet;
    }

    public void recycle() {
        this.isSet = false;
        this.start = 0;
        this.end = 0;
    }

    public void reset() {
        this.buff = null;
        this.cachedString = null;
    }

    public void allocate(int initial, int limit2) {
        char[] cArr = this.buff;
        if (cArr == null || cArr.length < initial) {
            this.buff = new char[initial];
        }
        this.limit = limit2;
        this.start = 0;
        this.end = 0;
        this.isSet = true;
        resetStringCache();
    }

    public void ensureCapacity(int size) {
        resetStringCache();
        char[] cArr = this.buff;
        if (cArr == null || cArr.length < size) {
            this.buff = new char[size];
            this.limit = -1;
        }
        this.start = 0;
        this.end = 0;
    }

    public void setOptimizedWrite(boolean optimizedWrite2) {
        this.optimizedWrite = optimizedWrite2;
    }

    public void setChars(char[] c, int off, int len) {
        this.buff = c;
        this.start = off;
        this.end = off + len;
        this.isSet = true;
        resetStringCache();
    }

    public void setLimit(int limit2) {
        this.limit = limit2;
        resetStringCache();
    }

    public int getLimit() {
        return this.limit;
    }

    public void setCharInputChannel(CharInputChannel in2) {
        this.in = in2;
    }

    public void setCharOutputChannel(CharOutputChannel out2) {
        this.out = out2;
    }

    public char[] getChars() {
        return getBuffer();
    }

    public char[] getBuffer() {
        return this.buff;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start2) {
        this.start = start2;
        resetStringCache();
    }

    public int getLength() {
        return this.end - this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int i) {
        this.end = i;
        resetStringCache();
    }

    public void append(char b) {
        makeSpace(1);
        int i = this.limit;
        if (i > 0 && this.end >= i) {
            flushBuffer();
        }
        char[] cArr = this.buff;
        int i2 = this.end;
        this.end = i2 + 1;
        cArr[i2] = b;
        resetStringCache();
    }

    public void append(CharChunk src) {
        append(src.getBuffer(), src.getStart(), src.getLength());
    }

    public void append(char[] src, int off, int len) {
        resetStringCache();
        makeSpace(len);
        int i = this.limit;
        if (i < 0) {
            System.arraycopy(src, off, this.buff, this.end, len);
            this.end += len;
        } else if (this.optimizedWrite && len == i && this.end == this.start) {
            this.out.realWriteChars(src, off, len);
        } else {
            int i2 = this.end;
            if (len <= i - i2) {
                System.arraycopy(src, off, this.buff, i2, len);
                this.end += len;
            } else if (len + i2 < i * 2) {
                int avail = i - i2;
                System.arraycopy(src, off, this.buff, i2, avail);
                this.end += avail;
                flushBuffer();
                System.arraycopy(src, off + avail, this.buff, this.end, len - avail);
                this.end += len - avail;
            } else {
                flushBuffer();
                this.out.realWriteChars(src, off, len);
            }
        }
    }

    public void append(StringBuffer sb) {
        resetStringCache();
        int len = sb.length();
        makeSpace(len);
        if (this.limit < 0) {
            sb.getChars(0, len, this.buff, this.end);
            this.end += len;
            return;
        }
        int sbOff = 0;
        int sbEnd = 0 + len;
        while (sbOff < sbEnd) {
            int d = min(this.limit - this.end, sbEnd - sbOff);
            sb.getChars(sbOff, sbOff + d, this.buff, this.end);
            sbOff += d;
            int i = this.end + d;
            this.end = i;
            if (i >= this.limit) {
                flushBuffer();
            }
        }
    }

    public void append(String s) {
        if (s != null) {
            append(s, 0, s.length());
        }
    }

    public void append(String s, int off, int len) {
        if (s != null) {
            resetStringCache();
            makeSpace(len);
            if (this.limit < 0) {
                s.getChars(off, off + len, this.buff, this.end);
                this.end += len;
                return;
            }
            int sOff = off;
            int sEnd = off + len;
            while (sOff < sEnd) {
                int d = min(this.limit - this.end, sEnd - sOff);
                s.getChars(sOff, sOff + d, this.buff, this.end);
                sOff += d;
                int i = this.end + d;
                this.end = i;
                if (i >= this.limit) {
                    flushBuffer();
                }
            }
        }
    }

    public void delete(int start2, int end2) {
        resetStringCache();
        int diff = this.end - end2;
        if (diff == 0) {
            this.end = start2;
            return;
        }
        char[] cArr = this.buff;
        System.arraycopy(cArr, end2, cArr, start2, diff);
        this.end = start2 + diff;
    }

    public int substract() {
        resetStringCache();
        int i = this.end;
        if (i - this.start == 0) {
            CharInputChannel charInputChannel = this.in;
            if (charInputChannel == null) {
                return -1;
            }
            char[] cArr = this.buff;
            if (charInputChannel.realReadChars(cArr, i, cArr.length - i) < 0) {
                return -1;
            }
        }
        char[] cArr2 = this.buff;
        int i2 = this.start;
        this.start = i2 + 1;
        return cArr2[i2];
    }

    public int substract(CharChunk src) {
        resetStringCache();
        int i = this.end;
        if (i - this.start == 0) {
            CharInputChannel charInputChannel = this.in;
            if (charInputChannel == null) {
                return -1;
            }
            char[] cArr = this.buff;
            if (charInputChannel.realReadChars(cArr, i, cArr.length - i) < 0) {
                return -1;
            }
        }
        int len = getLength();
        src.append(this.buff, this.start, len);
        this.start = this.end;
        return len;
    }

    public int substract(char[] src, int off, int len) {
        resetStringCache();
        int i = this.end;
        if (i - this.start == 0) {
            CharInputChannel charInputChannel = this.in;
            if (charInputChannel == null) {
                return -1;
            }
            char[] cArr = this.buff;
            if (charInputChannel.realReadChars(cArr, i, cArr.length - i) < 0) {
                return -1;
            }
        }
        int n = len;
        if (len > getLength()) {
            n = getLength();
        }
        System.arraycopy(this.buff, this.start, src, off, n);
        this.start += n;
        return n;
    }

    public void flushBuffer() {
        CharOutputChannel charOutputChannel = this.out;
        if (charOutputChannel != null) {
            char[] cArr = this.buff;
            int i = this.start;
            charOutputChannel.realWriteChars(cArr, i, this.end - i);
            this.end = this.start;
            resetStringCache();
            return;
        }
        throw new IOException("Buffer overflow, no sink " + this.limit + ' ' + this.buff.length);
    }

    /* access modifiers changed from: package-private */
    public void makeSpace(int count) {
        char[] tmp;
        int i = this.end;
        int desiredSize = i + count;
        int i2 = this.limit;
        if (i2 > 0 && desiredSize > i2) {
            desiredSize = this.limit;
        }
        if (this.buff == null) {
            if (desiredSize < 256) {
                desiredSize = 256;
            }
            this.buff = new char[desiredSize];
        }
        char[] cArr = this.buff;
        if (desiredSize > cArr.length) {
            if (desiredSize < cArr.length * 2) {
                int newSize = cArr.length * 2;
                if (i2 > 0 && newSize > i2) {
                    newSize = this.limit;
                }
                tmp = new char[newSize];
            } else {
                int newSize2 = (cArr.length * 2) + count;
                if (i2 > 0 && newSize2 > i2) {
                    newSize2 = this.limit;
                }
                tmp = new char[newSize2];
            }
            int i3 = this.start;
            System.arraycopy(cArr, i3, tmp, i3, i - i3);
            this.buff = tmp;
        }
    }

    /* access modifiers changed from: protected */
    public void notifyDirectUpdate() {
    }

    /* access modifiers changed from: protected */
    public void resetStringCache() {
        this.cachedString = null;
    }

    public String toString() {
        if (this.buff == null || this.end - this.start == 0) {
            return "";
        }
        String str = this.cachedString;
        if (str != null) {
            return str;
        }
        String stringInternal = toStringInternal();
        this.cachedString = stringInternal;
        return stringInternal;
    }

    public String toString(int start2, int end2) {
        if (start2 == this.start && end2 == this.end) {
            return toString();
        }
        if (this.buff == null) {
            return null;
        }
        if (end2 - start2 == 0) {
            return "";
        }
        return new String(this.buff, this.start + start2, end2 - start2);
    }

    public String toStringInternal() {
        char[] cArr = this.buff;
        int i = this.start;
        return new String(cArr, i, this.end - i);
    }

    public int getInt() {
        char[] cArr = this.buff;
        int i = this.start;
        return Ascii.parseInt(cArr, i, this.end - i);
    }

    public void set(ByteChunk byteChunk, Charset encoding) {
        int bufferStart = byteChunk.getStart();
        int bufferLength = byteChunk.getLength();
        allocate(bufferLength, -1);
        byte[] buffer = byteChunk.getBuffer();
        if (!DEFAULT_HTTP_CHARSET.equals(encoding)) {
            ByteBuffer bb = ByteBuffer.wrap(buffer, bufferStart, bufferLength);
            char[] cArr = this.buff;
            int i = this.start;
            CharBuffer cb = CharBuffer.wrap(cArr, i, cArr.length - i);
            if (Charsets.getCharsetDecoder(encoding).decode(bb, cb, true) == CoderResult.UNDERFLOW) {
                this.end = this.start + cb.position();
                return;
            }
            throw new CharConversionException("Decoding error");
        }
        for (int i2 = 0; i2 < bufferLength; i2++) {
            this.buff[i2] = (char) (buffer[i2 + bufferStart] & 255);
        }
        this.end = bufferLength;
    }

    public void set(BufferChunk bufferChunk, Charset encoding) {
        int bufferStart = bufferChunk.getStart();
        int bufferLength = bufferChunk.getLength();
        allocate(bufferLength, -1);
        Buffer buffer = bufferChunk.getBuffer();
        if (!DEFAULT_HTTP_CHARSET.equals(encoding)) {
            ByteBuffer bb = buffer.toByteBuffer(bufferStart, bufferStart + bufferLength);
            char[] cArr = this.buff;
            int i = this.start;
            CharBuffer cb = CharBuffer.wrap(cArr, i, cArr.length - i);
            if (Charsets.getCharsetDecoder(encoding).decode(bb, cb, true) == CoderResult.UNDERFLOW) {
                this.end = this.start + cb.position();
                return;
            }
            throw new CharConversionException("Decoding error");
        }
        for (int i2 = 0; i2 < bufferLength; i2++) {
            this.buff[i2] = (char) (buffer.get(i2 + bufferStart) & 255);
        }
        this.end = bufferLength;
    }

    public int hashCode() {
        return (((((((((((((Arrays.hashCode(this.buff) * 31) + this.start) * 31) + this.end) * 31) + (this.isSet ? 1 : 0)) * 31) + this.limit) * 31) + this.in.hashCode()) * 31) + this.out.hashCode()) * 31) + (this.optimizedWrite ? 1 : 0);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CharChunk charChunk = (CharChunk) o;
        if (this.end != charChunk.end || this.isSet != charChunk.isSet || this.limit != charChunk.limit || this.optimizedWrite != charChunk.optimizedWrite || this.start != charChunk.start || !Arrays.equals(this.buff, charChunk.buff)) {
            return false;
        }
        CharInputChannel charInputChannel = this.in;
        if (charInputChannel == null ? charChunk.in != null : !charInputChannel.equals(charChunk.in)) {
            return false;
        }
        CharOutputChannel charOutputChannel = this.out;
        if (charOutputChannel == null ? charChunk.out == null : charOutputChannel.equals(charChunk.out)) {
            return true;
        }
        return false;
    }

    public boolean equals(CharSequence s) {
        char[] c = this.buff;
        int len = this.end - this.start;
        if (c == null || len != s.length()) {
            return false;
        }
        int off = this.start;
        int i = 0;
        while (i < len) {
            int off2 = off + 1;
            if (c[off] != s.charAt(i)) {
                return false;
            }
            i++;
            off = off2;
        }
        return true;
    }

    public boolean equals(byte[] b) {
        char[] c = this.buff;
        int len = this.end - this.start;
        if (c == null || len != b.length) {
            return false;
        }
        int off = this.start;
        int i = 0;
        while (i < len) {
            int off2 = off + 1;
            if (c[off] != b[i]) {
                return false;
            }
            i++;
            off = off2;
        }
        return true;
    }

    public boolean equalsIgnoreCase(CharSequence s) {
        char[] c = this.buff;
        int len = this.end - this.start;
        if (c == null || len != s.length()) {
            return false;
        }
        int off = this.start;
        int i = 0;
        while (i < len) {
            int off2 = off + 1;
            if (Ascii.toLower((int) c[off]) != Ascii.toLower((int) s.charAt(i))) {
                return false;
            }
            i++;
            off = off2;
        }
        return true;
    }

    public boolean equalsIgnoreCase(byte[] b) {
        return equalsIgnoreCase(b, 0, b.length);
    }

    public boolean equalsIgnoreCase(byte[] b, int offset, int len) {
        char[] c = this.buff;
        if (c == null || getLength() != len) {
            return false;
        }
        int offs1 = this.start;
        int offs2 = offset;
        int i = 0;
        while (i < len) {
            int offs12 = offs1 + 1;
            int offs22 = offs2 + 1;
            if (Ascii.toLower((int) c[offs1]) != Ascii.toLower((int) b[offs2])) {
                return false;
            }
            i++;
            offs1 = offs12;
            offs2 = offs22;
        }
        return true;
    }

    public boolean equalsIgnoreCase(char[] b, int offset, int len) {
        char[] c = this.buff;
        if (c == null || getLength() != len) {
            return false;
        }
        int offs1 = this.start;
        int offs2 = offset;
        int i = 0;
        while (i < len) {
            int offs12 = offs1 + 1;
            int offs22 = offs2 + 1;
            if (Ascii.toLower((int) c[offs1]) != Ascii.toLower((int) b[offs2])) {
                return false;
            }
            i++;
            offs1 = offs12;
            offs2 = offs22;
        }
        return true;
    }

    public boolean equalsIgnoreCaseLowerCase(byte[] b) {
        char[] c = this.buff;
        int len = this.end - this.start;
        if (c == null || len != b.length) {
            return false;
        }
        int off = this.start;
        int i = 0;
        while (i < len) {
            int off2 = off + 1;
            if (Ascii.toLower((int) c[off]) != b[i]) {
                return false;
            }
            i++;
            off = off2;
        }
        return true;
    }

    public boolean equals(CharChunk cc) {
        return equals(cc.getChars(), cc.getStart(), cc.getLength());
    }

    public boolean equals(char[] b2, int off2, int len2) {
        char[] b1 = this.buff;
        if (b1 == null && b2 == null) {
            return true;
        }
        if (!(b1 == null || b2 == null)) {
            int i = this.end;
            int i2 = this.start;
            if (i - i2 == len2) {
                int off1 = this.start;
                int off12 = i - i2;
                while (true) {
                    int len = off12 - 1;
                    if (off12 <= 0) {
                        return true;
                    }
                    int off13 = off1 + 1;
                    int off22 = off2 + 1;
                    if (b1[off1] != b2[off2]) {
                        return false;
                    }
                    off1 = off13;
                    off12 = len;
                    off2 = off22;
                }
            }
        }
        return false;
    }

    public boolean equals(byte[] b2, int off2, int len2) {
        char[] b1 = this.buff;
        if (b2 == null && b1 == null) {
            return true;
        }
        if (!(b1 == null || b2 == null)) {
            int i = this.end;
            int i2 = this.start;
            if (i - i2 == len2) {
                int off1 = this.start;
                int off12 = i - i2;
                while (true) {
                    int len = off12 - 1;
                    if (off12 <= 0) {
                        return true;
                    }
                    int off13 = off1 + 1;
                    int off22 = off2 + 1;
                    if (b1[off1] != ((char) b2[off2])) {
                        return false;
                    }
                    off1 = off13;
                    off12 = len;
                    off2 = off22;
                }
            }
        }
        return false;
    }

    public boolean startsWith(String s) {
        return startsWith(s, 0);
    }

    /* access modifiers changed from: package-private */
    public boolean startsWith(String s, int pos) {
        char[] c = this.buff;
        int len = s.length();
        if (c != null) {
            int i = len + pos;
            int i2 = this.end;
            int i3 = this.start;
            if (i <= i2 - i3) {
                int off = i3 + pos;
                int i4 = 0;
                while (i4 < len) {
                    int off2 = off + 1;
                    if (c[off] != s.charAt(i4)) {
                        return false;
                    }
                    i4++;
                    off = off2;
                }
                return true;
            }
        }
        return false;
    }

    public boolean startsWithIgnoreCase(String s, int pos) {
        char[] c = this.buff;
        int len = s.length();
        if (c != null) {
            int i = len + pos;
            int i2 = this.end;
            int i3 = this.start;
            if (i <= i2 - i3) {
                int off = i3 + pos;
                int i4 = 0;
                while (i4 < len) {
                    int off2 = off + 1;
                    if (Ascii.toLower((int) c[off]) != Ascii.toLower((int) s.charAt(i4))) {
                        return false;
                    }
                    i4++;
                    off = off2;
                }
                return true;
            }
        }
        return false;
    }

    public boolean endsWith(String s) {
        char[] c = this.buff;
        int len = s.length();
        if (c != null) {
            int i = this.end;
            if (len <= i - this.start) {
                int off = i - len;
                int i2 = 0;
                while (i2 < len) {
                    int off2 = off + 1;
                    if (c[off] != s.charAt(i2)) {
                        return false;
                    }
                    i2++;
                    off = off2;
                }
                return true;
            }
        }
        return false;
    }

    public int hash() {
        int code = 0;
        for (int i = this.start; i < this.end; i++) {
            code = (code * 31) + this.buff[i];
        }
        return code;
    }

    public int hashIgnoreCase() {
        int code = 0;
        for (int i = this.start; i < this.end; i++) {
            code = (code * 31) + Ascii.toLower((int) this.buff[i]);
        }
        return code;
    }

    public int indexOf(char c) {
        return indexOf(c, this.start);
    }

    public int indexOf(char c, int starting) {
        int ret = indexOf(this.buff, this.start + starting, this.end, c);
        int i = this.start;
        if (ret >= i) {
            return ret - i;
        }
        return -1;
    }

    public static int indexOf(char[] chars, int off, int cend, char qq) {
        while (off < cend) {
            if (chars[off] == qq) {
                return off;
            }
            off++;
        }
        return -1;
    }

    public int indexOf(String s, int fromIndex) {
        return indexOf(s, 0, s.length(), fromIndex);
    }

    public int indexOf(String src, int srcOff, int srcLen, int myOff) {
        char first = src.charAt(srcOff);
        int srcEnd = srcOff + srcLen;
        for (int i = this.start + myOff; i <= this.end - srcLen; i++) {
            if (this.buff[i] == first) {
                int myPos = i + 1;
                int srcPos = srcOff + 1;
                while (srcPos < srcEnd) {
                    int myPos2 = myPos + 1;
                    int srcPos2 = srcPos + 1;
                    if (this.buff[myPos] != src.charAt(srcPos)) {
                        continue;
                        break;
                    } else if (srcPos2 == srcEnd) {
                        return i - this.start;
                    } else {
                        srcPos = srcPos2;
                        myPos = myPos2;
                    }
                }
                continue;
            }
        }
        return -1;
    }

    public void trimLeft() {
        boolean modified = false;
        while (true) {
            char[] cArr = this.buff;
            int i = this.start;
            if (cArr[i] > ' ') {
                break;
            }
            modified = true;
            this.start = i + 1;
        }
        if (modified) {
            resetStringCache();
        }
    }

    private int min(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }
}
