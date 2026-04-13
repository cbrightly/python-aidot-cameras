package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;

public class DigitallySigned {
    protected SignatureAndHashAlgorithm a;
    protected byte[] b;

    public DigitallySigned(SignatureAndHashAlgorithm algorithm, byte[] signature) {
        if (signature != null) {
            this.a = algorithm;
            this.b = signature;
            return;
        }
        throw new IllegalArgumentException("'signature' cannot be null");
    }

    public SignatureAndHashAlgorithm b() {
        return this.a;
    }

    public byte[] c() {
        return this.b;
    }

    public void a(OutputStream output) {
        SignatureAndHashAlgorithm signatureAndHashAlgorithm = this.a;
        if (signatureAndHashAlgorithm != null) {
            signatureAndHashAlgorithm.a(output);
        }
        TlsUtils.A0(this.b, output);
    }

    public static DigitallySigned d(TlsContext context, InputStream input) {
        SignatureAndHashAlgorithm algorithm = null;
        if (TlsUtils.U(context)) {
            algorithm = SignatureAndHashAlgorithm.d(input);
        }
        return new DigitallySigned(algorithm, TlsUtils.g0(input));
    }
}
