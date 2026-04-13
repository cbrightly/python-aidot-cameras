package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;

public class SignatureAndHashAlgorithm {
    protected short a;
    protected short b;

    public SignatureAndHashAlgorithm(short hash, short signature) {
        if (!TlsUtils.a0(hash)) {
            throw new IllegalArgumentException("'hash' should be a uint8");
        } else if (!TlsUtils.a0(signature)) {
            throw new IllegalArgumentException("'signature' should be a uint8");
        } else if (signature != 0) {
            this.a = hash;
            this.b = signature;
        } else {
            throw new IllegalArgumentException("'signature' MUST NOT be \"anonymous\"");
        }
    }

    public short b() {
        return this.a;
    }

    public short c() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SignatureAndHashAlgorithm)) {
            return false;
        }
        SignatureAndHashAlgorithm other = (SignatureAndHashAlgorithm) obj;
        if (other.b() == b() && other.c() == c()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (b() << 16) | c();
    }

    public void a(OutputStream output) {
        TlsUtils.L0(b(), output);
        TlsUtils.L0(c(), output);
    }

    public static SignatureAndHashAlgorithm d(InputStream input) {
        return new SignatureAndHashAlgorithm(TlsUtils.q0(input), TlsUtils.q0(input));
    }
}
