package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public final class ByteChunk implements Chunk, Cloneable, Serializable {
    private static final Charset DEFAULT_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
    private static final long serialVersionUID = -1;
    private byte[] buff;
    private String cachedString;
    private Charset cachedStringCharset;
    private Charset charset;
    private int end;
    private transient ByteInputChannel in = null;
    private boolean isSet = false;
    private int limit = -1;
    private boolean optimizedWrite = true;
    private transient ByteOutputChannel out = null;
    private int start = 0;

    public interface ByteInputChannel {
        int realReadBytes(byte[] bArr, int i, int i2);
    }

    public interface ByteOutputChannel {
        void realWriteBytes(byte[] bArr, int i, int i2);
    }

    public ByteChunk() {
    }

    public ByteChunk(int initial) {
        allocate(initial, -1);
    }

    public ByteChunk getClone() {
        try {
            return (ByteChunk) clone();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isNull() {
        return !this.isSet;
    }

    public void recycle() {
        this.charset = null;
        this.start = 0;
        this.end = 0;
        this.isSet = false;
    }

    public void recycleAndReset() {
        this.buff = null;
        this.charset = null;
        this.start = 0;
        this.end = 0;
        this.isSet = false;
        resetStringCache();
    }

    public void reset() {
        this.buff = null;
        resetStringCache();
    }

    /* access modifiers changed from: protected */
    public void resetStringCache() {
        this.cachedString = null;
        this.cachedStringCharset = null;
    }

    public void allocate(int initial, int limit2) {
        byte[] bArr = this.buff;
        if (bArr == null || bArr.length < initial) {
            this.buff = new byte[initial];
        }
        this.limit = limit2;
        this.start = 0;
        this.end = 0;
        this.isSet = true;
        resetStringCache();
    }

    public void setBytes(byte[] b, int off, int len) {
        this.buff = b;
        this.start = off;
        this.end = off + len;
        this.isSet = true;
        resetStringCache();
    }

    public void setOptimizedWrite(boolean optimizedWrite2) {
        this.optimizedWrite = optimizedWrite2;
    }

    public Charset getCharset() {
        Charset charset2 = this.charset;
        return charset2 != null ? charset2 : DEFAULT_CHARSET;
    }

    public void setCharset(Charset charset2) {
        this.charset = charset2;
        resetStringCache();
    }

    public byte[] getBytes() {
        return getBuffer();
    }

    public byte[] getBuffer() {
        return this.buff;
    }

    public int getStart() {
        return this.start;
    }

    public int getOffset() {
        return getStart();
    }

    public void setStart(int start2) {
        if (this.end < start2) {
            this.end = start2;
        }
        this.start = start2;
        resetStringCache();
    }

    public void setOffset(int off) {
        setStart(off);
    }

    public int getLength() {
        return this.end - this.start;
    }

    public void setLimit(int limit2) {
        this.limit = limit2;
        resetStringCache();
    }

    public int getLimit() {
        return this.limit;
    }

    public void setByteInputChannel(ByteInputChannel in2) {
        this.in = in2;
    }

    public void setByteOutputChannel(ByteOutputChannel out2) {
        this.out = out2;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int i) {
        this.end = i;
        resetStringCache();
    }

    /* access modifiers changed from: protected */
    public void notifyDirectUpdate() {
    }

    public int indexOf(String s, int fromIdx) {
        int strLen = s.length();
        if (strLen == 0) {
            return fromIdx;
        }
        int absFromIdx = this.start + fromIdx;
        int i = this.end;
        if (strLen > i - absFromIdx) {
            return -1;
        }
        int strOffs = 0;
        int lastOffs = i - strLen;
        while (absFromIdx <= lastOffs + strOffs) {
            if (this.buff[absFromIdx] == s.charAt(strOffs)) {
                strOffs++;
                if (strOffs == strLen) {
                    return ((absFromIdx - strLen) - this.start) + 1;
                }
            } else {
                strOffs = 0;
            }
            absFromIdx++;
        }
        return -1;
    }

    public void delete(int start2, int end2) {
        resetStringCache();
        int i = this.start;
        int absDeleteStart = i + start2;
        int absDeleteEnd = i + end2;
        int diff = this.end - absDeleteEnd;
        if (diff == 0) {
            this.end = absDeleteStart;
            return;
        }
        byte[] bArr = this.buff;
        System.arraycopy(bArr, absDeleteEnd, bArr, absDeleteStart, diff);
        this.end = absDeleteStart + diff;
    }

    public void append(char c) {
        append((byte) c);
    }

    public void append(byte b) {
        resetStringCache();
        makeSpace(1);
        int i = this.limit;
        if (i > 0 && this.end >= i) {
            flushBuffer();
        }
        byte[] bArr = this.buff;
        int i2 = this.end;
        this.end = i2 + 1;
        bArr[i2] = b;
    }

    public void append(ByteChunk src) {
        append(src.getBytes(), src.getStart(), src.getLength());
    }

    public void append(byte[] src, int off, int len) {
        resetStringCache();
        makeSpace(len);
        int i = this.limit;
        if (i < 0) {
            System.arraycopy(src, off, this.buff, this.end, len);
            this.end += len;
        } else if (this.optimizedWrite && len == i && this.end == this.start) {
            this.out.realWriteBytes(src, off, len);
        } else {
            int i2 = this.end;
            if (len <= i - i2) {
                System.arraycopy(src, off, this.buff, i2, len);
                this.end += len;
                return;
            }
            int avail = i - i2;
            System.arraycopy(src, off, this.buff, i2, avail);
            this.end += avail;
            flushBuffer();
            int remain = len - avail;
            while (true) {
                int i3 = this.limit;
                int i4 = this.end;
                if (remain > i3 - i4) {
                    this.out.realWriteBytes(src, (off + len) - remain, i3 - i4);
                    remain -= this.limit - this.end;
                } else {
                    System.arraycopy(src, (off + len) - remain, this.buff, i4, remain);
                    this.end += remain;
                    return;
                }
            }
        }
    }

    public int substract() {
        resetStringCache();
        if (this.end - this.start == 0) {
            ByteInputChannel byteInputChannel = this.in;
            if (byteInputChannel == null) {
                return -1;
            }
            byte[] bArr = this.buff;
            if (byteInputChannel.realReadBytes(bArr, 0, bArr.length) < 0) {
                return -1;
            }
        }
        byte[] bArr2 = this.buff;
        int i = this.start;
        this.start = i + 1;
        return bArr2[i] & 255;
    }

    public int substract(ByteChunk src) {
        resetStringCache();
        if (this.end - this.start == 0) {
            ByteInputChannel byteInputChannel = this.in;
            if (byteInputChannel == null) {
                return -1;
            }
            byte[] bArr = this.buff;
            if (byteInputChannel.realReadBytes(bArr, 0, bArr.length) < 0) {
                return -1;
            }
        }
        int len = getLength();
        src.append(this.buff, this.start, len);
        this.start = this.end;
        return len;
    }

    public int substract(byte[] src, int off, int len) {
        resetStringCache();
        if (this.end - this.start == 0) {
            ByteInputChannel byteInputChannel = this.in;
            if (byteInputChannel == null) {
                return -1;
            }
            byte[] bArr = this.buff;
            if (byteInputChannel.realReadBytes(bArr, 0, bArr.length) < 0) {
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
        ByteOutputChannel byteOutputChannel = this.out;
        if (byteOutputChannel != null) {
            byte[] bArr = this.buff;
            int i = this.start;
            byteOutputChannel.realWriteBytes(bArr, i, this.end - i);
            this.end = this.start;
            return;
        }
        throw new IOException("Buffer overflow, no sink " + this.limit + ' ' + this.buff.length);
    }

    /* access modifiers changed from: package-private */
    public boolean canGrow() {
        byte[] bArr = this.buff;
        int length = bArr.length;
        int i = this.limit;
        if (length == i) {
            return false;
        }
        int desiredSize = bArr.length * 2;
        if (i > 0 && desiredSize > i && i > this.end - this.start) {
            desiredSize = this.limit;
        }
        byte[] tmp = new byte[desiredSize];
        int i2 = this.start;
        System.arraycopy(bArr, i2, tmp, 0, this.end - i2);
        this.buff = tmp;
        this.end -= this.start;
        this.start = 0;
        return true;
    }

    private void makeSpace(int count) {
        byte[] tmp;
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
            this.buff = new byte[desiredSize];
        }
        byte[] bArr = this.buff;
        if (desiredSize > bArr.length) {
            if (desiredSize < bArr.length * 2) {
                int newSize = bArr.length * 2;
                if (i2 > 0 && newSize > i2) {
                    newSize = this.limit;
                }
                tmp = new byte[newSize];
            } else {
                int newSize2 = (bArr.length * 2) + count;
                if (i2 > 0 && newSize2 > i2) {
                    newSize2 = this.limit;
                }
                tmp = new byte[newSize2];
            }
            int i3 = this.start;
            System.arraycopy(bArr, i3, tmp, 0, i - i3);
            this.buff = tmp;
            this.end -= this.start;
            this.start = 0;
        }
    }

    public void trimLeft() {
        boolean modified = false;
        while (true) {
            byte[] bArr = this.buff;
            int i = this.start;
            if (bArr[i] > 32) {
                break;
            }
            modified = true;
            this.start = i + 1;
        }
        if (modified) {
            resetStringCache();
        }
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
        if (this.charset == null) {
            this.charset = DEFAULT_CHARSET;
        }
        try {
            return new String(this.buff, this.start + start2, end2 - start2, this.charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unexpected error", e);
        }
    }

    public String toString(Charset charset2) {
        if (charset2 == null) {
            Charset charset3 = this.charset;
            if (charset3 == null) {
                charset3 = DEFAULT_CHARSET;
            }
            charset2 = charset3;
        }
        if (this.cachedString != null && charset2.equals(this.cachedStringCharset)) {
            return this.cachedString;
        }
        byte[] bArr = this.buff;
        int i = this.start;
        String charBuffer = charset2.decode(ByteBuffer.wrap(bArr, i, this.end - i)).toString();
        this.cachedString = charBuffer;
        this.cachedStringCharset = charset2;
        return charBuffer;
    }

    public String toStringInternal() {
        if (this.charset == null) {
            this.charset = DEFAULT_CHARSET;
        }
        return toString(this.charset);
    }

    public int getInt() {
        byte[] bArr = this.buff;
        int i = this.start;
        return Ascii.parseInt(bArr, i, this.end - i);
    }

    public long getLong() {
        byte[] bArr = this.buff;
        int i = this.start;
        return Ascii.parseLong(bArr, i, this.end - i);
    }

    public int hashCode() {
        return (((((((((((((((Arrays.hashCode(this.buff) * 31) + this.start) * 31) + this.end) * 31) + this.charset.hashCode()) * 31) + (this.isSet ? 1 : 0)) * 31) + this.limit) * 31) + this.in.hashCode()) * 31) + this.out.hashCode()) * 31) + (this.optimizedWrite ? 1 : 0);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ByteChunk byteChunk = (ByteChunk) o;
        if (this.end != byteChunk.end || this.isSet != byteChunk.isSet || this.limit != byteChunk.limit || this.optimizedWrite != byteChunk.optimizedWrite || this.start != byteChunk.start || !Arrays.equals(this.buff, byteChunk.buff)) {
            return false;
        }
        Charset charset2 = this.charset;
        if (charset2 == null ? byteChunk.charset != null : !charset2.equals(byteChunk.charset)) {
            return false;
        }
        ByteInputChannel byteInputChannel = this.in;
        if (byteInputChannel == null ? byteChunk.in != null : !byteInputChannel.equals(byteChunk.in)) {
            return false;
        }
        ByteOutputChannel byteOutputChannel = this.out;
        if (byteOutputChannel == null ? byteChunk.out == null : byteOutputChannel.equals(byteChunk.out)) {
            return true;
        }
        return false;
    }

    public boolean equals(String s) {
        byte[] bArr = this.buff;
        int i = this.start;
        return equals(bArr, i, this.end - i, s);
    }

    public boolean equals(byte[] bytes) {
        byte[] bArr = this.buff;
        int i = this.start;
        return equals(bArr, i, this.end - i, bytes, 0, bytes.length);
    }

    public boolean equalsIgnoreCase(String s) {
        return equalsIgnoreCase(this.buff, this.start, getLength(), s);
    }

    public boolean equalsIgnoreCase(byte[] b) {
        return equalsIgnoreCase(b, 0, b.length);
    }

    public boolean equalsIgnoreCase(byte[] b, int offset, int len) {
        return equalsIgnoreCase(this.buff, this.start, getLength(), b, offset, len);
    }

    public boolean equalsIgnoreCaseLowerCase(byte[] cmpTo) {
        return equalsIgnoreCaseLowerCase(this.buff, this.start, this.end, cmpTo);
    }

    public boolean equals(ByteChunk bb) {
        return equals(bb.getBytes(), bb.getStart(), bb.getLength());
    }

    public boolean equals(byte[] b2, int off2, int len2) {
        byte[] b1 = this.buff;
        if (b1 == null && b2 == null) {
            return true;
        }
        int off1 = this.end - this.start;
        if (len2 != off1 || b1 == null || b2 == null) {
            return false;
        }
        int off12 = this.start;
        while (true) {
            int len = off1 - 1;
            if (off1 <= 0) {
                return true;
            }
            int off13 = off12 + 1;
            int off22 = off2 + 1;
            if (b1[off12] != b2[off2]) {
                return false;
            }
            off12 = off13;
            off1 = len;
            off2 = off22;
        }
    }

    public boolean equals(CharChunk cc) {
        return equals(cc.getChars(), cc.getStart(), cc.getLength());
    }

    public boolean equals(char[] c2, int off2, int len2) {
        byte[] b1 = this.buff;
        if (c2 == null && b1 == null) {
            return true;
        }
        if (!(b1 == null || c2 == null)) {
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
                    if (((char) b1[off1]) != c2[off2]) {
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

    public boolean startsWith(String s, int offset) {
        byte[] b = this.buff;
        int len = s.length();
        if (b != null) {
            int i = len + offset;
            int i2 = this.end;
            int i3 = this.start;
            if (i <= i2 - i3) {
                int off = i3 + offset;
                int i4 = 0;
                while (i4 < len) {
                    int off2 = off + 1;
                    if (b[off] != s.charAt(i4)) {
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

    public boolean startsWith(byte[] b2) {
        byte[] b1 = this.buff;
        if (b1 == null && b2 == null) {
            return true;
        }
        int len = this.end - this.start;
        if (b1 == null || b2 == null || b2.length > len) {
            return false;
        }
        int i = this.start;
        int j = 0;
        while (i < this.end && j < b2.length) {
            int i2 = i + 1;
            int j2 = j + 1;
            if (b1[i] != b2[j]) {
                return false;
            }
            i = i2;
            j = j2;
        }
        return true;
    }

    public boolean startsWithIgnoreCase(String s, int pos) {
        byte[] b = this.buff;
        int len = s.length();
        if (b != null) {
            int i = len + pos;
            int i2 = this.end;
            int i3 = this.start;
            if (i <= i2 - i3) {
                int off = i3 + pos;
                int i4 = 0;
                while (i4 < len) {
                    int off2 = off + 1;
                    if (Ascii.toLower((int) b[off]) != Ascii.toLower((int) s.charAt(i4))) {
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

    public int hash() {
        byte[] bArr = this.buff;
        int i = this.start;
        return hashBytes(bArr, i, this.end - i);
    }

    public int hashIgnoreCase() {
        byte[] bArr = this.buff;
        int i = this.start;
        return hashBytesIC(bArr, i, this.end - i);
    }

    public static boolean equals(byte[] b1, int b1Offs, int b1Len, byte[] b2, int b2Offs, int b2Len) {
        if (b1Len != b2Len) {
            return false;
        }
        if (b1 == b2) {
            return true;
        }
        if (b1 == null || b2 == null) {
            return false;
        }
        for (int i = 0; i < b1Len; i++) {
            if (b1[i + b1Offs] != b2[i + b2Offs]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(byte[] b, int offs, int len, String s) {
        if (b == null || len != s.length()) {
            return false;
        }
        int i = 0;
        while (i < len) {
            int offs2 = offs + 1;
            if (b[offs] != s.charAt(i)) {
                return false;
            }
            i++;
            offs = offs2;
        }
        return true;
    }

    public static boolean equalsIgnoreCase(byte[] b1, int b1Offs, int b1Len, byte[] b2, int b2Offs, int b2Len) {
        if (b1Len != b2Len) {
            return false;
        }
        if (b1 == b2) {
            return true;
        }
        if (b1 == null || b2 == null) {
            return false;
        }
        for (int i = 0; i < b1Len; i++) {
            if (Ascii.toLower((int) b1[i + b1Offs]) != Ascii.toLower((int) b2[i + b2Offs])) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsIgnoreCase(byte[] b, int offset, int len, String s) {
        if (len != s.length()) {
            return false;
        }
        int boff = offset;
        int i = 0;
        while (i < len) {
            int boff2 = boff + 1;
            if (Ascii.toLower((int) b[boff]) != Ascii.toLower((int) s.charAt(i))) {
                return false;
            }
            i++;
            boff = boff2;
        }
        return true;
    }

    public static boolean equalsIgnoreCaseLowerCase(byte[] buffer, int start2, int end2, byte[] cmpTo) {
        int len = end2 - start2;
        if (len != cmpTo.length) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (Ascii.toLower((int) buffer[i + start2]) != cmpTo[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean startsWith(byte[] buffer, int start2, int end2, byte[] cmpTo) {
        if (end2 - start2 < cmpTo.length) {
            return false;
        }
        for (int i = 0; i < cmpTo.length; i++) {
            if (buffer[start2 + i] != cmpTo[i]) {
                return false;
            }
        }
        return true;
    }

    private static int hashBytes(byte[] buff2, int start2, int bytesLen) {
        int max = start2 + bytesLen;
        int code = 0;
        for (int i = start2; i < max; i++) {
            code = (code * 31) + buff2[i];
        }
        return code;
    }

    private static int hashBytesIC(byte[] bytes, int start2, int bytesLen) {
        int max = start2 + bytesLen;
        int code = 0;
        for (int i = start2; i < max; i++) {
            code = (code * 31) + Ascii.toLower((int) bytes[i]);
        }
        return code;
    }

    public int indexOf(char c, int starting) {
        int ret = indexOf(this.buff, this.start + starting, this.end, c);
        int i = this.start;
        if (ret >= i) {
            return ret - i;
        }
        return -1;
    }

    public static int indexOf(byte[] bytes, int off, int end2, char qq) {
        while (off < end2) {
            if (bytes[off] == qq) {
                return off;
            }
            off++;
        }
        return -1;
    }

    public static int findChar(byte[] buf, int start2, int end2, char c) {
        byte b = (byte) c;
        for (int offset = start2; offset < end2; offset++) {
            if (buf[offset] == b) {
                return offset;
            }
        }
        return -1;
    }

    public static int findChars(byte[] buf, int start2, int end2, byte[] c) {
        for (int offset = start2; offset < end2; offset++) {
            for (byte b : c) {
                if (buf[offset] == b) {
                    return offset;
                }
            }
        }
        return -1;
    }

    public static int findNotChars(byte[] buf, int start2, int end2, byte[] c) {
        int clen = c.length;
        for (int offset = start2; offset < end2; offset++) {
            boolean found = true;
            int i = 0;
            while (true) {
                if (i >= clen) {
                    break;
                } else if (buf[offset] == c[i]) {
                    found = false;
                    break;
                } else {
                    i++;
                }
            }
            if (found) {
                return offset;
            }
        }
        return -1;
    }

    public static byte[] convertToBytes(String value) {
        byte[] result = new byte[value.length()];
        for (int i = 0; i < value.length(); i++) {
            result[i] = (byte) value.charAt(i);
        }
        return result;
    }
}
