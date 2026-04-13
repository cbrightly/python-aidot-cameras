package org.glassfish.grizzly.http.util;

import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public class BufferChunk implements Chunk {
    private static final Charset DEFAULT_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
    private Buffer buffer;
    String cachedString;
    Charset cachedStringCharset;
    private int end;
    private int limit;
    private int start;

    public void setBufferChunk(Buffer buffer2, int start2, int end2) {
        setBufferChunk(buffer2, start2, end2, end2);
    }

    public void setBufferChunk(Buffer buffer2, int start2, int end2, int limit2) {
        this.buffer = buffer2;
        this.start = start2;
        this.end = end2;
        this.limit = limit2;
        resetStringCache();
    }

    public Buffer getBuffer() {
        return this.buffer;
    }

    public void setBuffer(Buffer buffer2) {
        this.buffer = buffer2;
        resetStringCache();
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start2) {
        this.start = start2;
        resetStringCache();
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end2) {
        this.end = end2;
        resetStringCache();
    }

    public final int getLength() {
        return this.end - this.start;
    }

    public final boolean isNull() {
        return this.buffer == null;
    }

    public void allocate(int size) {
        if (isNull() || this.limit - this.start < size) {
            setBufferChunk(Buffers.wrap((MemoryManager) null, new byte[size]), 0, 0, size);
        }
        this.end = this.start;
    }

    public void delete(int start2, int end2) {
        int i = this.start;
        int absDeleteStart = i + start2;
        int absDeleteEnd = i + end2;
        int diff = this.end - absDeleteEnd;
        if (diff == 0) {
            this.end = absDeleteStart;
        } else {
            int oldPos = this.buffer.position();
            int oldLim = this.buffer.limit();
            try {
                Buffers.setPositionLimit(this.buffer, absDeleteStart, absDeleteStart + diff);
                this.buffer.put(this.buffer.duplicate(), absDeleteEnd, diff);
                this.end = absDeleteStart + diff;
            } finally {
                Buffers.setPositionLimit(this.buffer, oldPos, oldLim);
            }
        }
        resetStringCache();
    }

    public void append(BufferChunk bc) {
        int oldPos = this.buffer.position();
        int oldLim = this.buffer.limit();
        int srcLen = bc.getLength();
        Buffer buffer2 = this.buffer;
        int i = this.end;
        Buffers.setPositionLimit(buffer2, i, i + srcLen);
        this.buffer.put(bc.getBuffer(), bc.getStart(), srcLen);
        Buffers.setPositionLimit(this.buffer, oldPos, oldLim);
        this.end += srcLen;
    }

    public final int indexOf(char c, int fromIndex) {
        int idx = indexOf(this.buffer, this.start + fromIndex, this.end, c);
        int i = this.start;
        if (idx >= i) {
            return idx - i;
        }
        return -1;
    }

    public final int indexOf(String s, int fromIndex) {
        int idx = indexOf(this.buffer, this.start + fromIndex, this.end, (CharSequence) s);
        int i = this.start;
        if (idx >= i) {
            return idx - i;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public boolean startsWith(String s, int pos) {
        int len = s.length();
        if (len > getLength() - pos) {
            return false;
        }
        int off = this.start + pos;
        int i = 0;
        while (i < len) {
            int off2 = off + 1;
            if (this.buffer.get(off) != s.charAt(i)) {
                return false;
            }
            i++;
            off = off2;
        }
        return true;
    }

    public boolean startsWithIgnoreCase(String s, int pos) {
        int len = s.length();
        if (len > getLength() - pos) {
            return false;
        }
        int off = this.start + pos;
        int i = 0;
        while (i < len) {
            int off2 = off + 1;
            if (Ascii.toLower(this.buffer.get(off)) != Ascii.toLower((int) s.charAt(i))) {
                return false;
            }
            i++;
            off = off2;
        }
        return true;
    }

    public int findBytesAscii(byte[] b) {
        byte first = b[0];
        int from = getStart();
        int to = getEnd();
        int srcEnd = b.length;
        for (int i = from; i <= to - srcEnd; i++) {
            if (Ascii.toLower((int) this.buffer.get(i)) == first) {
                int myPos = i + 1;
                int srcPos = 1;
                while (srcPos < srcEnd) {
                    int myPos2 = myPos + 1;
                    int srcPos2 = srcPos + 1;
                    if (Ascii.toLower(this.buffer.get(myPos)) != b[srcPos]) {
                        continue;
                        break;
                    } else if (srcPos2 == srcEnd) {
                        return i - from;
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

    public int hashCode() {
        return hash();
    }

    public int hash() {
        int code = 0;
        for (int i = this.start; i < this.end; i++) {
            code = (code * 31) + this.buffer.get(i);
        }
        return code;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BufferChunk)) {
            return false;
        }
        BufferChunk anotherBC = (BufferChunk) o;
        int len = getLength();
        if (len != anotherBC.getLength()) {
            return false;
        }
        int offs1 = this.start;
        int offs2 = anotherBC.start;
        int i = 0;
        while (i < len) {
            int offs12 = offs1 + 1;
            int offs22 = offs2 + 1;
            if (this.buffer.get(offs1) != anotherBC.buffer.get(offs2)) {
                return false;
            }
            i++;
            offs1 = offs12;
            offs2 = offs22;
        }
        return true;
    }

    public boolean equals(CharSequence s) {
        if (getLength() != s.length()) {
            return false;
        }
        for (int i = this.start; i < this.end; i++) {
            if (this.buffer.get(i) != s.charAt(i - this.start)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(byte[] b) {
        return equals(b, 0, b.length);
    }

    public boolean equals(byte[] b, int offset, int len) {
        if (getLength() != len) {
            return false;
        }
        int i = this.start;
        while (i < this.end) {
            int offset2 = offset + 1;
            if (this.buffer.get(i) != b[offset]) {
                return false;
            }
            i++;
            offset = offset2;
        }
        return true;
    }

    public static boolean equals(byte[] c, int cOff, int cLen, Buffer t, int tOff, int tLen) {
        if (cLen != tLen || c == null || t == null) {
            return false;
        }
        for (int i = 0; i < cLen; i++) {
            if (c[i + cOff] != t.get(i + tOff)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(char[] b, int offset, int len) {
        if (getLength() != len) {
            return false;
        }
        int i = this.start;
        while (i < this.end) {
            int offset2 = offset + 1;
            if (this.buffer.get(i) != b[offset]) {
                return false;
            }
            i++;
            offset = offset2;
        }
        return true;
    }

    public boolean equalsIgnoreCase(Object o) {
        if (!(o instanceof BufferChunk)) {
            return false;
        }
        BufferChunk anotherBC = (BufferChunk) o;
        int len = getLength();
        if (len != anotherBC.getLength()) {
            return false;
        }
        int offs1 = this.start;
        int offs2 = anotherBC.start;
        int i = 0;
        while (i < len) {
            int offs12 = offs1 + 1;
            int offs22 = offs2 + 1;
            if (Ascii.toLower(this.buffer.get(offs1)) != Ascii.toLower(anotherBC.buffer.get(offs2))) {
                return false;
            }
            i++;
            offs1 = offs12;
            offs2 = offs22;
        }
        return true;
    }

    public boolean equalsIgnoreCase(CharSequence s) {
        if (getLength() != s.length()) {
            return false;
        }
        for (int i = this.start; i < this.end; i++) {
            if (Ascii.toLower((int) this.buffer.get(i)) != Ascii.toLower((int) s.charAt(i - this.start))) {
                return false;
            }
        }
        return true;
    }

    public boolean equalsIgnoreCase(byte[] b) {
        return equalsIgnoreCase(b, 0, b.length);
    }

    public boolean equalsIgnoreCase(byte[] b, int offset, int len) {
        if (getLength() != len) {
            return false;
        }
        int offs1 = this.start;
        int offs2 = offset;
        int i = 0;
        while (i < len) {
            int offs12 = offs1 + 1;
            int offs22 = offs2 + 1;
            if (Ascii.toLower(this.buffer.get(offs1)) != Ascii.toLower((int) b[offs2])) {
                return false;
            }
            i++;
            offs2 = offs22;
            offs1 = offs12;
        }
        return true;
    }

    public boolean equalsIgnoreCase(char[] b, int offset, int len) {
        if (getLength() != len) {
            return false;
        }
        int i = this.start;
        while (i < this.end) {
            int offset2 = offset + 1;
            if (Ascii.toLower((int) this.buffer.get(i)) != Ascii.toLower((int) b[offset])) {
                return false;
            }
            i++;
            offset = offset2;
        }
        return true;
    }

    public boolean equalsIgnoreCaseLowerCase(byte[] b) {
        return equalsIgnoreCaseLowerCase(this.buffer, this.start, this.end, b);
    }

    public String toString() {
        return toString((Charset) null);
    }

    public String toString(Charset charset) {
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        if (this.cachedString != null && charset.equals(this.cachedStringCharset)) {
            return this.cachedString;
        }
        String stringContent = this.buffer.toStringContent(charset, this.start, this.end);
        this.cachedString = stringContent;
        this.cachedStringCharset = charset;
        return stringContent;
    }

    public String toString(int start2, int end2) {
        Buffer buffer2 = this.buffer;
        Charset charset = DEFAULT_CHARSET;
        int i = this.start;
        return buffer2.toStringContent(charset, i + start2, i + end2);
    }

    /* access modifiers changed from: protected */
    public final void resetStringCache() {
        this.cachedString = null;
        this.cachedStringCharset = null;
    }

    /* access modifiers changed from: protected */
    public final void reset() {
        this.buffer = null;
        this.start = -1;
        this.end = -1;
        this.limit = -1;
        resetStringCache();
    }

    public final void recycle() {
        reset();
    }

    /* access modifiers changed from: protected */
    public void notifyDirectUpdate() {
    }

    public static int indexOf(Buffer buffer2, int off, int end2, char qq) {
        while (off < end2) {
            if (buffer2.get(off) == qq) {
                return off;
            }
            off++;
        }
        return -1;
    }

    public static int indexOf(Buffer buffer2, int off, int end2, CharSequence s) {
        int strLen = s.length();
        if (strLen == 0) {
            return off;
        }
        if (strLen > end2 - off) {
            return -1;
        }
        int strOffs = 0;
        int lastOffs = end2 - strLen;
        while (off <= lastOffs + strOffs) {
            if (buffer2.get(off) == s.charAt(strOffs)) {
                strOffs++;
                if (strOffs == strLen) {
                    return (off - strLen) + 1;
                }
            } else {
                strOffs = 0;
            }
            off++;
        }
        return -1;
    }

    public int compareIgnoreCase(int start2, int end2, String compareTo) {
        int result = 0;
        int len = compareTo.length();
        if (end2 - start2 < len) {
            len = end2 - start2;
        }
        for (int i = 0; i < len && result == 0; i++) {
            if (Ascii.toLower((int) this.buffer.get(i + start2)) > Ascii.toLower((int) compareTo.charAt(i))) {
                result = 1;
            } else if (Ascii.toLower((int) this.buffer.get(i + start2)) < Ascii.toLower((int) compareTo.charAt(i))) {
                result = -1;
            }
        }
        if (result != 0) {
            return result;
        }
        if (compareTo.length() > end2 - start2) {
            return -1;
        }
        if (compareTo.length() < end2 - start2) {
            return 1;
        }
        return result;
    }

    public int compare(int start2, int end2, String compareTo) {
        int result = 0;
        int len = compareTo.length();
        if (end2 - start2 < len) {
            len = end2 - start2;
        }
        for (int i = 0; i < len && result == 0; i++) {
            if (this.buffer.get(i + start2) > compareTo.charAt(i)) {
                result = 1;
            } else if (this.buffer.get(i + start2) < compareTo.charAt(i)) {
                result = -1;
            }
        }
        if (result != 0) {
            return result;
        }
        if (compareTo.length() > end2 - start2) {
            return -1;
        }
        if (compareTo.length() < end2 - start2) {
            return 1;
        }
        return result;
    }

    public static boolean equalsIgnoreCaseLowerCase(Buffer buffer2, int start2, int end2, byte[] cmpTo) {
        int len = end2 - start2;
        if (len != cmpTo.length) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (Ascii.toLower((int) buffer2.get(i + start2)) != cmpTo[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean startsWith(Buffer buffer2, int start2, int end2, byte[] cmpTo) {
        if (end2 - start2 < cmpTo.length) {
            return false;
        }
        for (int i = 0; i < cmpTo.length; i++) {
            if (buffer2.get(start2 + i) != cmpTo[i]) {
                return false;
            }
        }
        return true;
    }

    public void trimLeft() {
        boolean modified = false;
        while (this.buffer.get(this.start) <= 32) {
            modified = true;
            this.start++;
        }
        if (modified) {
            resetStringCache();
        }
    }
}
