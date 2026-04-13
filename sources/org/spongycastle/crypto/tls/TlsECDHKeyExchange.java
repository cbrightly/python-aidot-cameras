package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;

public class TlsECDHKeyExchange extends AbstractTlsKeyExchange {
    protected TlsSigner d;
    protected int[] e;
    protected short[] f;
    protected short[] g;
    protected AsymmetricKeyParameter h;
    protected TlsAgreementCredentials i;
    protected ECPrivateKeyParameters j;
    protected ECPublicKeyParameters k;

    public TlsECDHKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, int[] namedCurves, short[] clientECPointFormats, short[] serverECPointFormats) {
        super(keyExchange, supportedSignatureAlgorithms);
        switch (keyExchange) {
            case 16:
            case 18:
            case 20:
                this.d = null;
                break;
            case 17:
                this.d = new TlsECDSASigner();
                break;
            case 19:
                this.d = new TlsRSASigner();
                break;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
        this.e = namedCurves;
        this.f = clientECPointFormats;
        this.g = serverECPointFormats;
    }

    public void a(TlsContext context) {
        super.a(context);
        TlsSigner tlsSigner = this.d;
        if (tlsSigner != null) {
            tlsSigner.a(context);
        }
    }

    public void n() {
        if (this.a != 20) {
            throw new TlsFatalAlert(10);
        }
    }

    public void m(Certificate serverCertificate) {
        if (this.a == 20) {
            throw new TlsFatalAlert(10);
        } else if (!serverCertificate.c()) {
            Certificate x509Cert = serverCertificate.b(0);
            try {
                AsymmetricKeyParameter a = PublicKeyFactory.a(x509Cert.q());
                this.h = a;
                TlsSigner tlsSigner = this.d;
                if (tlsSigner == null) {
                    try {
                        this.k = TlsECCUtils.A((ECPublicKeyParameters) a);
                        TlsUtils.x0(x509Cert, 8);
                    } catch (ClassCastException e2) {
                        throw new TlsFatalAlert(46, e2);
                    }
                } else if (tlsSigner.e(a)) {
                    TlsUtils.x0(x509Cert, 128);
                } else {
                    throw new TlsFatalAlert(46);
                }
                super.m(serverCertificate);
            } catch (RuntimeException e3) {
                throw new TlsFatalAlert(43, e3);
            }
        } else {
            throw new TlsFatalAlert(42);
        }
    }

    public boolean p() {
        switch (this.a) {
            case 17:
            case 19:
            case 20:
                return true;
            default:
                return false;
        }
    }

    public byte[] b() {
        if (!p()) {
            return null;
        }
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        this.j = TlsECCUtils.k(this.c.d(), this.e, this.f, buf);
        return buf.toByteArray();
    }

    public void c(InputStream input) {
        if (p()) {
            this.k = TlsECCUtils.A(TlsECCUtils.h(this.f, TlsECCUtils.w(this.e, this.f, input), TlsUtils.i0(input)));
            return;
        }
        throw new TlsFatalAlert(10);
    }

    public void i(CertificateRequest certificateRequest) {
        if (this.a != 20) {
            short[] types = certificateRequest.b();
            int i2 = 0;
            while (i2 < types.length) {
                switch (types[i2]) {
                    case 1:
                    case 2:
                    case 64:
                    case 65:
                    case 66:
                        i2++;
                    default:
                        throw new TlsFatalAlert(47);
                }
            }
            return;
        }
        throw new TlsFatalAlert(40);
    }

    public void f(TlsCredentials clientCredentials) {
        if (this.a == 20) {
            throw new TlsFatalAlert(80);
        } else if (clientCredentials instanceof TlsAgreementCredentials) {
            this.i = (TlsAgreementCredentials) clientCredentials;
        } else if (!(clientCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
    }

    public void h(OutputStream output) {
        if (this.i == null) {
            this.j = TlsECCUtils.j(this.c.d(), this.g, this.k.b(), output);
        }
    }

    public void d(Certificate clientCertificate) {
        if (this.a == 20) {
            throw new TlsFatalAlert(10);
        }
    }

    public void e(InputStream input) {
        if (this.k == null) {
            byte[] point = TlsUtils.i0(input);
            this.k = TlsECCUtils.A(TlsECCUtils.h(this.g, this.j.b(), point));
        }
    }

    public byte[] l() {
        TlsAgreementCredentials tlsAgreementCredentials = this.i;
        if (tlsAgreementCredentials != null) {
            return tlsAgreementCredentials.a(this.k);
        }
        ECPrivateKeyParameters eCPrivateKeyParameters = this.j;
        if (eCPrivateKeyParameters != null) {
            return TlsECCUtils.b(this.k, eCPrivateKeyParameters);
        }
        throw new TlsFatalAlert(80);
    }
}
