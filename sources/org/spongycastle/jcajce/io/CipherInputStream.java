package org.spongycastle.jcajce.io;

import java.io.FilterInputStream;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.spongycastle.crypto.io.InvalidCipherTextIOException;

public class CipherInputStream extends FilterInputStream {
    private final Cipher c;
    private final byte[] d;
    private boolean f;
    private byte[] q;
    private int x;
    private int y;

    private int c() {
        if (this.f) {
            return -1;
        }
        this.y = 0;
        this.x = 0;
        while (true) {
            int i = this.x;
            if (i != 0) {
                return i;
            }
            int read = this.in.read(this.d);
            if (read == -1) {
                byte[] a = a();
                this.q = a;
                if (a == null || a.length == 0) {
                    return -1;
                }
                int length = a.length;
                this.x = length;
                return length;
            }
            byte[] update = this.c.update(this.d, 0, read);
            this.q = update;
            if (update != null) {
                this.x = update.length;
            }
        }
    }

    private byte[] a() {
        try {
            this.f = true;
            return this.c.doFinal();
        } catch (GeneralSecurityException e) {
            throw new InvalidCipherTextIOException("Error finalising cipher", e);
        }
    }

    public int read() {
        if (this.y >= this.x && c() < 0) {
            return -1;
        }
        byte[] bArr = this.q;
        int i = this.y;
        this.y = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] b, int off, int len) {
        if (this.y >= this.x && c() < 0) {
            return -1;
        }
        int toSupply = Math.min(len, available());
        System.arraycopy(this.q, this.y, b, off, toSupply);
        this.y += toSupply;
        return toSupply;
    }

    public long skip(long n) {
        if (n <= 0) {
            return 0;
        }
        int skip = (int) Math.min(n, (long) available());
        this.y += skip;
        return (long) skip;
    }

    public int available() {
        return this.x - this.y;
    }

    public void close() {
        try {
            this.in.close();
            this.y = 0;
            this.x = 0;
        } finally {
            if (!this.f) {
                a();
            }
        }
    }

    public void mark(int readlimit) {
    }

    public void reset() {
    }

    public boolean markSupported() {
        return false;
    }
}
