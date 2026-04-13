package io.netty.util.internal;

import java.util.Arrays;

public final class AppendableCharSequence implements CharSequence, Appendable {
    private char[] chars;
    private int pos;

    public AppendableCharSequence(int length) {
        if (length >= 1) {
            this.chars = new char[length];
            return;
        }
        throw new IllegalArgumentException("length: " + length + " (length: >= 1)");
    }

    private AppendableCharSequence(char[] chars2) {
        if (chars2.length >= 1) {
            this.chars = chars2;
            this.pos = chars2.length;
            return;
        }
        throw new IllegalArgumentException("length: " + chars2.length + " (length: >= 1)");
    }

    public int length() {
        return this.pos;
    }

    public char charAt(int index) {
        if (index <= this.pos) {
            return this.chars[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public char charAtUnsafe(int index) {
        return this.chars[index];
    }

    public AppendableCharSequence subSequence(int start, int end) {
        return new AppendableCharSequence(Arrays.copyOfRange(this.chars, start, end));
    }

    public AppendableCharSequence append(char c) {
        if (this.pos == this.chars.length) {
            char[] old = this.chars;
            char[] cArr = new char[(old.length << 1)];
            this.chars = cArr;
            System.arraycopy(old, 0, cArr, 0, old.length);
        }
        char[] old2 = this.chars;
        int i = this.pos;
        this.pos = i + 1;
        old2[i] = c;
        return this;
    }

    public AppendableCharSequence append(CharSequence csq) {
        return append(csq, 0, csq.length());
    }

    public AppendableCharSequence append(CharSequence csq, int start, int end) {
        if (csq.length() >= end) {
            int length = end - start;
            char[] cArr = this.chars;
            int length2 = cArr.length;
            int i = this.pos;
            if (length > length2 - i) {
                this.chars = expand(cArr, i + length, i);
            }
            if (csq instanceof AppendableCharSequence) {
                System.arraycopy(((AppendableCharSequence) csq).chars, start, this.chars, this.pos, length);
                this.pos += length;
                return this;
            }
            for (int i2 = start; i2 < end; i2++) {
                char[] cArr2 = this.chars;
                int i3 = this.pos;
                this.pos = i3 + 1;
                cArr2[i3] = csq.charAt(i2);
            }
            return this;
        }
        throw new IndexOutOfBoundsException();
    }

    public void reset() {
        this.pos = 0;
    }

    public String toString() {
        return new String(this.chars, 0, this.pos);
    }

    public String substring(int start, int end) {
        int length = end - start;
        int i = this.pos;
        if (start <= i && length <= i) {
            return new String(this.chars, start, length);
        }
        throw new IndexOutOfBoundsException();
    }

    public String subStringUnsafe(int start, int end) {
        return new String(this.chars, start, end - start);
    }

    private static char[] expand(char[] array, int neededSpace, int size) {
        int newCapacity = array.length;
        do {
            newCapacity <<= 1;
            if (newCapacity < 0) {
                throw new IllegalStateException();
            }
        } while (neededSpace > newCapacity);
        char[] newArray = new char[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }
}
