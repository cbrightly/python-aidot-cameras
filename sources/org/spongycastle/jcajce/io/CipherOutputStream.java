package org.spongycastle.jcajce.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.spongycastle.crypto.io.InvalidCipherTextIOException;

public class CipherOutputStream extends FilterOutputStream {
    private final Cipher c;
    private final byte[] d;

    public void write(int b) {
        byte[] bArr = this.d;
        bArr[0] = (byte) b;
        write(bArr, 0, 1);
    }

    public void write(byte[] b, int off, int len) {
        byte[] outData = this.c.update(b, off, len);
        if (outData != null) {
            this.out.write(outData);
        }
    }

    public void flush() {
        this.out.flush();
    }

    public void close() {
        IOException error = null;
        try {
            byte[] outData = this.c.doFinal();
            if (outData != null) {
                this.out.write(outData);
            }
        } catch (GeneralSecurityException e) {
            error = new InvalidCipherTextIOException("Error during cipher finalisation", e);
        } catch (Exception e2) {
            error = new IOException("Error closing stream: " + e2);
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
