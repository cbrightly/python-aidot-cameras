package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;

public class TlsDHKeyExchange extends AbstractTlsKeyExchange {
    protected TlsSigner d;
    protected DHParameters e;
    protected AsymmetricKeyParameter f;
    protected TlsAgreementCredentials g;
    protected DHPrivateKeyParameters h;
    protected DHPublicKeyParameters i;

    public TlsDHKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, DHParameters dhParameters) {
        super(keyExchange, supportedSignatureAlgorithms);
        switch (keyExchange) {
            case 3:
                this.d = new TlsDSSSigner();
                break;
            case 5:
                this.d = new TlsRSASigner();
                break;
            case 7:
            case 9:
            case 11:
                this.d = null;
                break;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
        this.e = dhParameters;
    }

    public void a(TlsContext context) {
        super.a(context);
        TlsSigner tlsSigner = this.d;
        if (tlsSigner != null) {
            tlsSigner.a(context);
        }
    }

    public void n() {
        if (this.a != 11) {
            throw new TlsFatalAlert(10);
        }
    }

    public void m(Certificate serverCertificate) {
        if (this.a == 11) {
            throw new TlsFatalAlert(10);
        } else if (!serverCertificate.c()) {
            Certificate x509Cert = serverCertificate.b(0);
            try {
                AsymmetricKeyParameter a = PublicKeyFactory.a(x509Cert.q());
                this.f = a;
                TlsSigner tlsSigner = this.d;
                if (tlsSigner == null) {
                    try {
                        DHPublicKeyParameters i2 = TlsDHUtils.i((DHPublicKeyParameters) a);
                        this.i = i2;
                        this.e = r(i2.b());
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
            case 3:
            case 5:
            case 11:
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
        this.h = TlsDHUtils.f(this.c.d(), this.e, buf);
        return buf.toByteArray();
    }

    public void c(InputStream input) {
        if (p()) {
            DHPublicKeyParameters i2 = TlsDHUtils.i(ServerDHParams.parse(input).getPublicKey());
            this.i = i2;
            this.e = r(i2.b());
            return;
        }
        throw new TlsFatalAlert(10);
    }

    public void i(CertificateRequest certificateRequest) {
        if (this.a != 11) {
            short[] types = certificateRequest.b();
            int i2 = 0;
            while (i2 < types.length) {
                switch (types[i2]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 64:
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
        if (this.a == 11) {
            throw new TlsFatalAlert(80);
        } else if (clientCredentials instanceof TlsAgreementCredentials) {
            this.g = (TlsAgreementCredentials) clientCredentials;
        } else if (!(clientCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
    }

    public void h(OutputStream output) {
        if (this.g == null) {
            this.h = TlsDHUtils.e(this.c.d(), this.e, output);
        }
    }

    public void d(Certificate clientCertificate) {
        if (this.a == 11) {
            throw new TlsFatalAlert(10);
        }
    }

    public void e(InputStream input) {
        if (this.i == null) {
            this.i = TlsDHUtils.i(new DHPublicKeyParameters(TlsDHUtils.g(input), this.e));
        }
    }

    public byte[] l() {
        TlsAgreementCredentials tlsAgreementCredentials = this.g;
        if (tlsAgreementCredentials != null) {
            return tlsAgreementCredentials.a(this.i);
        }
        DHPrivateKeyParameters dHPrivateKeyParameters = this.h;
        if (dHPrivateKeyParameters != null) {
            return TlsDHUtils.a(this.i, dHPrivateKeyParameters);
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public int q() {
        return 1024;
    }

    /* access modifiers changed from: protected */
    public DHParameters r(DHParameters params) {
        if (params.e().bitLength() >= q()) {
            return TlsDHUtils.h(params);
        }
        throw new TlsFatalAlert(71);
    }
}
