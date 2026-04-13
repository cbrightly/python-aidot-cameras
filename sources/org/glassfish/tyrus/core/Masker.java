package org.glassfish.tyrus.core;

import java.nio.ByteBuffer;

public class Masker {
    private volatile ByteBuffer buffer;
    private volatile int index;
    private volatile byte[] mask;

    public Masker(ByteBuffer buffer2) {
        this.index = 0;
        this.buffer = buffer2;
    }

    public Masker(int mask2) {
        this.index = 0;
        this.mask = new byte[4];
        this.mask[0] = (byte) (mask2 >> 24);
        this.mask[1] = (byte) (mask2 >> 16);
        this.mask[2] = (byte) (mask2 >> 8);
        this.mask[3] = (byte) mask2;
    }

    /* access modifiers changed from: package-private */
    public byte get() {
        return this.buffer.get();
    }

    /* access modifiers changed from: package-private */
    public byte[] get(int size) {
        byte[] bytes = new byte[size];
        this.buffer.get(bytes);
        return bytes;
    }

    public byte[] unmask(int count) {
        byte[] bytes = get(count);
        if (this.mask != null) {
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                byte[] bArr = this.mask;
                int i2 = this.index;
                this.index = i2 + 1;
                bytes[i] = (byte) (b ^ bArr[i2 % 4]);
            }
        }
        return bytes;
    }

    public void mask(byte[] target, int location, byte[] bytes, int length) {
        byte b;
        if (bytes != null && target != null) {
            for (int i = 0; i < length; i++) {
                int i2 = location + i;
                if (this.mask == null) {
                    b = bytes[i];
                } else {
                    byte b2 = bytes[i];
                    byte[] bArr = this.mask;
                    int i3 = this.index;
                    this.index = i3 + 1;
                    b = (byte) (b2 ^ bArr[i3 % 4]);
                }
                target[i2] = b;
            }
        }
    }

    public void setBuffer(ByteBuffer buffer2) {
        this.buffer = buffer2;
    }

    public byte[] getMask() {
        return this.mask;
    }

    public void readMask() {
        this.mask = get(4);
    }
}
