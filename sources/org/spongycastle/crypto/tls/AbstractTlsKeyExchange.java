package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.util.Vector;

public abstract class AbstractTlsKeyExchange implements TlsKeyExchange {
    protected int a;
    protected Vector b;
    protected TlsContext c;

    protected AbstractTlsKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms) {
        this.a = keyExchange;
        this.b = supportedSignatureAlgorithms;
    }

    /* access modifiers changed from: protected */
    public DigitallySigned o(InputStream input) {
        DigitallySigned signature = DigitallySigned.d(this.c, input);
        SignatureAndHashAlgorithm signatureAlgorithm = signature.b();
        if (signatureAlgorithm != null) {
            TlsUtils.z0(this.b, signatureAlgorithm);
        }
        return signature;
    }

    public void a(TlsContext context) {
        this.c = context;
        ProtocolVersion clientVersion = context.c();
        if (TlsUtils.Q(clientVersion)) {
            if (this.b == null) {
                switch (this.a) {
                    case 1:
                    case 5:
                    case 9:
                    case 15:
                    case 18:
                    case 19:
                    case 23:
                        this.b = TlsUtils.A();
                        return;
                    case 3:
                    case 7:
                    case 22:
                        this.b = TlsUtils.y();
                        return;
                    case 13:
                    case 14:
                    case 21:
                    case 24:
                        return;
                    case 16:
                    case 17:
                        this.b = TlsUtils.z();
                        return;
                    default:
                        throw new IllegalStateException("unsupported key exchange algorithm");
                }
            }
        } else if (this.b != null) {
            throw new IllegalStateException("supported_signature_algorithms not allowed for " + clientVersion);
        }
    }

    public void m(Certificate serverCertificate) {
    }

    public void k(TlsCredentials serverCredentials) {
        m(serverCredentials.e());
    }

    public boolean p() {
        return false;
    }

    public byte[] b() {
        if (!p()) {
            return null;
        }
        throw new TlsFatalAlert(80);
    }

    public void j() {
        if (p()) {
            throw new TlsFatalAlert(10);
        }
    }

    public void c(InputStream input) {
        if (!p()) {
            throw new TlsFatalAlert(10);
        }
    }

    public void g() {
    }

    public void d(Certificate clientCertificate) {
    }

    public void e(InputStream input) {
        throw new TlsFatalAlert(80);
    }
}
