package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.io.Streams;

public class TlsRSAKeyExchange extends AbstractTlsKeyExchange {
    protected AsymmetricKeyParameter d = null;
    protected RSAKeyParameters e = null;
    protected TlsEncryptionCredentials f = null;
    protected byte[] g;

    public TlsRSAKeyExchange(Vector supportedSignatureAlgorithms) {
        super(1, supportedSignatureAlgorithms);
    }

    public void n() {
        throw new TlsFatalAlert(10);
    }

    public void k(TlsCredentials serverCredentials) {
        if (serverCredentials instanceof TlsEncryptionCredentials) {
            m(serverCredentials.e());
            this.f = (TlsEncryptionCredentials) serverCredentials;
            return;
        }
        throw new TlsFatalAlert(80);
    }

    public void m(Certificate serverCertificate) {
        if (!serverCertificate.c()) {
            Certificate x509Cert = serverCertificate.b(0);
            try {
                AsymmetricKeyParameter a = PublicKeyFactory.a(x509Cert.q());
                this.d = a;
                if (!a.a()) {
                    this.e = q((RSAKeyParameters) this.d);
                    TlsUtils.x0(x509Cert, 32);
                    super.m(serverCertificate);
                    return;
                }
                throw new TlsFatalAlert(80);
            } catch (RuntimeException e2) {
                throw new TlsFatalAlert(43, e2);
            }
        } else {
            throw new TlsFatalAlert(42);
        }
    }

    public void i(CertificateRequest certificateRequest) {
        short[] types = certificateRequest.b();
        int i = 0;
        while (i < types.length) {
            switch (types[i]) {
                case 1:
                case 2:
                case 64:
                    i++;
                default:
                    throw new TlsFatalAlert(47);
            }
        }
    }

    public void f(TlsCredentials clientCredentials) {
        if (!(clientCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
    }

    public void h(OutputStream output) {
        this.g = TlsRSAUtils.a(this.c, this.e, output);
    }

    public void e(InputStream input) {
        byte[] encryptedPreMasterSecret;
        if (TlsUtils.P(this.c)) {
            encryptedPreMasterSecret = Streams.b(input);
        } else {
            encryptedPreMasterSecret = TlsUtils.g0(input);
        }
        this.g = this.f.b(encryptedPreMasterSecret);
    }

    public byte[] l() {
        if (this.g != null) {
            byte[] tmp = this.g;
            this.g = null;
            return tmp;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public RSAKeyParameters q(RSAKeyParameters key) {
        if (key.b().isProbablePrime(2)) {
            return key;
        }
        throw new TlsFatalAlert(47);
    }
}
