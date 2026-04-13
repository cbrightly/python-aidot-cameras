package org.apache.http.util;

import java.io.Serializable;

/* compiled from: ByteArrayBuffer */
public final class c implements Serializable {
    private static final long serialVersionUID = 4359112959524048036L;
    private byte[] buffer;
    private int len;

    public c(int capacity) {
        a.g(capacity, "Buffer capacity");
        this.buffer = new byte[capacity];
    }

    private void a(int newlen) {
        byte[] newbuffer = new byte[Math.max(this.buffer.length << 1, newlen)];
        System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
        this.buffer = newbuffer;
    }

    public void append(byte[] b, int off, int len2) {
        if (b != null) {
            if (off < 0 || off > b.length || len2 < 0 || off + len2 < 0 || off + len2 > b.length) {
                throw new IndexOutOfBoundsException("off: " + off + " len: " + len2 + " b.length: " + b.length);
            } else if (len2 != 0) {
                int newlen = this.len + len2;
                if (newlen > this.buffer.length) {
                    a(newlen);
                }
                System.arraycopy(b, off, this.buffer, this.len, len2);
                this.len = newlen;
            }
        }
    }

    public void append(int b) {
        int newlen = this.len + 1;
        if (newlen > this.buffer.length) {
            a(newlen);
        }
        this.buffer[this.len] = (byte) b;
        this.len = newlen;
    }

    public void append(char[] b, int off, int len2) {
        if (b != null) {
            if (off < 0 || off > b.length || len2 < 0 || off + len2 < 0 || off + len2 > b.length) {
                throw new IndexOutOfBoundsException("off: " + off + " len: " + len2 + " b.length: " + b.length);
            } else if (len2 != 0) {
                int oldlen = this.len;
                int newlen = oldlen + len2;
                if (newlen > this.buffer.length) {
                    a(newlen);
                }
                int i1 = off;
                for (int i2 = oldlen; i2 < newlen; i2++) {
                    this.buffer[i2] = (byte) b[i1];
                    i1++;
                }
                this.len = newlen;
            }
        }
    }

    public void append(d b, int off, int len2) {
        if (b != null) {
            append(b.buffer(), off, len2);
        }
    }

    public void clear() {
        this.len = 0;
    }

    public byte[] toByteArray() {
        int i = this.len;
        byte[] b = new byte[i];
        if (i > 0) {
            System.arraycopy(this.buffer, 0, b, 0, i);
        }
        return b;
    }

    public int byteAt(int i) {
        return this.buffer[i];
    }

    public int capacity() {
        return this.buffer.length;
    }

    public int length() {
        return this.len;
    }

    public void ensureCapacity(int required) {
        if (required > 0) {
            int length = this.buffer.length;
            int i = this.len;
            if (required > length - i) {
                a(i + required);
            }
        }
    }

    public byte[] buffer() {
        return this.buffer;
    }

    public void setLength(int len2) {
        if (len2 < 0 || len2 > this.buffer.length) {
            throw new IndexOutOfBoundsException("len: " + len2 + " < 0 or > buffer len: " + this.buffer.length);
        }
        this.len = len2;
    }

    public boolean isEmpty() {
        return this.len == 0;
    }

    public boolean isFull() {
        return this.len == this.buffer.length;
    }

    public int indexOf(byte b, int from, int to) {
        int beginIndex = from;
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        int endIndex = to;
        if (endIndex > this.len) {
            endIndex = this.len;
        }
        if (beginIndex > endIndex) {
            return -1;
        }
        for (int i = beginIndex; i < endIndex; i++) {
            if (this.buffer[i] == b) {
                return i;
            }
        }
        return -1;
    }

    public int indexOf(byte b) {
        return indexOf(b, 0, this.len);
    }
}
