package org.spongycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.modes.AEADBlockCipher;

public class CipherOutputStream extends FilterOutputStream {
    private BufferedBlockCipher c;
    private StreamCipher d;
    private AEADBlockCipher f;
    private final byte[] q;
    private byte[] x;

    public void write(int b) {
        byte[] bArr = this.q;
        bArr[0] = (byte) b;
        StreamCipher streamCipher = this.d;
        if (streamCipher != null) {
            this.out.write(streamCipher.e((byte) b));
        } else {
            write(bArr, 0, 1);
        }
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        a(len, false);
        BufferedBlockCipher bufferedBlockCipher = this.c;
        if (bufferedBlockCipher != null) {
            int outLen = bufferedBlockCipher.g(b, off, len, this.x, 0);
            if (outLen != 0) {
                this.out.write(this.x, 0, outLen);
                return;
            }
            return;
        }
        AEADBlockCipher aEADBlockCipher = this.f;
        if (aEADBlockCipher != null) {
            int outLen2 = aEADBlockCipher.d(b, off, len, this.x, 0);
            if (outLen2 != 0) {
                this.out.write(this.x, 0, outLen2);
                return;
            }
            return;
        }
        this.d.d(b, off, len, this.x, 0);
        this.out.write(this.x, 0, len);
    }

    private void a(int updateSize, boolean finalOutput) {
        int bufLen = updateSize;
        if (finalOutput) {
            BufferedBlockCipher bufferedBlockCipher = this.c;
            if (bufferedBlockCipher != null) {
                bufLen = bufferedBlockCipher.c(updateSize);
            } else {
                AEADBlockCipher aEADBlockCipher = this.f;
                if (aEADBlockCipher != null) {
                    bufLen = aEADBlockCipher.f(updateSize);
                }
            }
        } else {
            BufferedBlockCipher bufferedBlockCipher2 = this.c;
            if (bufferedBlockCipher2 != null) {
                bufLen = bufferedBlockCipher2.e(updateSize);
            } else {
                AEADBlockCipher aEADBlockCipher2 = this.f;
                if (aEADBlockCipher2 != null) {
                    bufLen = aEADBlockCipher2.e(updateSize);
                }
            }
        }
        byte[] bArr = this.x;
        if (bArr == null || bArr.length < bufLen) {
            this.x = new byte[bufLen];
        }
    }

    public void flush() {
        this.out.flush();
    }

    public void close() {
        a(0, true);
        IOException error = null;
        try {
            BufferedBlockCipher bufferedBlockCipher = this.c;
            if (bufferedBlockCipher != null) {
                int outLen = bufferedBlockCipher.a(this.x, 0);
                if (outLen != 0) {
                    this.out.write(this.x, 0, outLen);
                }
            } else {
                AEADBlockCipher aEADBlockCipher = this.f;
                if (aEADBlockCipher != null) {
                    int outLen2 = aEADBlockCipher.c(this.x, 0);
                    if (outLen2 != 0) {
                        this.out.write(this.x, 0, outLen2);
                    }
                } else {
                    StreamCipher streamCipher = this.d;
                    if (streamCipher != null) {
                        streamCipher.reset();
                    }
                }
            }
        } catch (InvalidCipherTextException e) {
            error = new InvalidCipherTextIOException("Error finalising cipher data", e);
        } catch (Exception e2) {
            error = new CipherIOException("Error closing stream: ", e2);
        }
        try {
            flush();
            this.out.close();
        } catch (IOException e3) {
            if (error == null) {
                error = e3;
            }
        }
        if (error != null) {
            throw error;
        }
    }
}
