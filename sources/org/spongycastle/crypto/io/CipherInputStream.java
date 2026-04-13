package org.spongycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.IOException;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SkippingCipher;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.modes.AEADBlockCipher;
import org.spongycastle.util.Arrays;

public class CipherInputStream extends FilterInputStream {
    private int a1;
    private long a2;
    private SkippingCipher c;
    private byte[] d;
    private BufferedBlockCipher f;
    private int p0;
    private boolean p1;
    private int p2;
    private StreamCipher q;
    private AEADBlockCipher x;
    private byte[] y;
    private byte[] z;

    private int g() {
        if (this.p1) {
            return -1;
        }
        this.p0 = 0;
        this.a1 = 0;
        while (true) {
            int read = this.a1;
            if (read != 0) {
                return read;
            }
            int read2 = this.in.read(this.d);
            if (read2 == -1) {
                c();
                int i = this.a1;
                if (i == 0) {
                    return -1;
                }
                return i;
            }
            try {
                a(read2, false);
                BufferedBlockCipher bufferedBlockCipher = this.f;
                if (bufferedBlockCipher != null) {
                    this.a1 = bufferedBlockCipher.g(this.d, 0, read2, this.y, 0);
                } else {
                    AEADBlockCipher aEADBlockCipher = this.x;
                    if (aEADBlockCipher != null) {
                        this.a1 = aEADBlockCipher.d(this.d, 0, read2, this.y, 0);
                    } else {
                        this.q.d(this.d, 0, read2, this.y, 0);
                        this.a1 = read2;
                    }
                }
            } catch (Exception e) {
                throw new CipherIOException("Error processing stream ", e);
            }
        }
    }

    private void c() {
        try {
            this.p1 = true;
            a(0, true);
            BufferedBlockCipher bufferedBlockCipher = this.f;
            if (bufferedBlockCipher != null) {
                this.a1 = bufferedBlockCipher.a(this.y, 0);
                return;
            }
            AEADBlockCipher aEADBlockCipher = this.x;
            if (aEADBlockCipher != null) {
                this.a1 = aEADBlockCipher.c(this.y, 0);
            } else {
                this.a1 = 0;
            }
        } catch (InvalidCipherTextException e) {
            throw new InvalidCipherTextIOException("Error finalising cipher", e);
        } catch (Exception e2) {
            throw new IOException("Error finalising cipher " + e2);
        }
    }

    public int read() {
        if (this.p0 >= this.a1 && g() < 0) {
            return -1;
        }
        byte[] bArr = this.y;
        int i = this.p0;
        this.p0 = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) {
        if (this.p0 >= this.a1 && g() < 0) {
            return -1;
        }
        int toSupply = Math.min(len, available());
        System.arraycopy(this.y, this.p0, b, off, toSupply);
        this.p0 += toSupply;
        return toSupply;
    }

    public long skip(long n) {
        if (n <= 0) {
            return 0;
        }
        if (this.c != null) {
            int avail = available();
            if (n <= ((long) avail)) {
                this.p0 = (int) (((long) this.p0) + n);
                return n;
            }
            this.p0 = this.a1;
            long skip = this.in.skip(n - ((long) avail));
            if (skip == this.c.skip(skip)) {
                return ((long) avail) + skip;
            }
            throw new IOException("Unable to skip cipher " + skip + " bytes.");
        }
        int skip2 = (int) Math.min(n, (long) available());
        this.p0 += skip2;
        return (long) skip2;
    }

    public int available() {
        return this.a1 - this.p0;
    }

    private void a(int updateSize, boolean finalOutput) {
        int bufLen = updateSize;
        if (finalOutput) {
            BufferedBlockCipher bufferedBlockCipher = this.f;
            if (bufferedBlockCipher != null) {
                bufLen = bufferedBlockCipher.c(updateSize);
            } else {
                AEADBlockCipher aEADBlockCipher = this.x;
                if (aEADBlockCipher != null) {
                    bufLen = aEADBlockCipher.f(updateSize);
                }
            }
        } else {
            BufferedBlockCipher bufferedBlockCipher2 = this.f;
            if (bufferedBlockCipher2 != null) {
                bufLen = bufferedBlockCipher2.e(updateSize);
            } else {
                AEADBlockCipher aEADBlockCipher2 = this.x;
                if (aEADBlockCipher2 != null) {
                    bufLen = aEADBlockCipher2.e(updateSize);
                }
            }
        }
        byte[] bArr = this.y;
        if (bArr == null || bArr.length < bufLen) {
            this.y = new byte[bufLen];
        }
    }

    public void close() {
        try {
            this.in.close();
            this.p0 = 0;
            this.a1 = 0;
            this.p2 = 0;
            this.a2 = 0;
            byte[] bArr = this.z;
            if (bArr != null) {
                Arrays.F(bArr, (byte) 0);
                this.z = null;
            }
            byte[] bArr2 = this.y;
            if (bArr2 != null) {
                Arrays.F(bArr2, (byte) 0);
                this.y = null;
            }
            Arrays.F(this.d, (byte) 0);
        } finally {
            if (!this.p1) {
                c();
            }
        }
    }

    public void mark(int readlimit) {
        this.in.mark(readlimit);
        SkippingCipher skippingCipher = this.c;
        if (skippingCipher != null) {
            this.a2 = skippingCipher.getPosition();
        }
        byte[] bArr = this.y;
        if (bArr != null) {
            byte[] bArr2 = new byte[bArr.length];
            this.z = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        }
        this.p2 = this.p0;
    }

    public void reset() {
        if (this.c != null) {
            this.in.reset();
            this.c.seekTo(this.a2);
            byte[] bArr = this.z;
            if (bArr != null) {
                this.y = bArr;
            }
            this.p0 = this.p2;
            return;
        }
        throw new IOException("cipher must implement SkippingCipher to be used with reset()");
    }

    public boolean markSupported() {
        if (this.c != null) {
            return this.in.markSupported();
        }
        return false;
    }
}
