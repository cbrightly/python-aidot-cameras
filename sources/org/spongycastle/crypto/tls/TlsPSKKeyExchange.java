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
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;

public class TlsPSKKeyExchange extends AbstractTlsKeyExchange {
    protected TlsPSKIdentity d;
    protected TlsPSKIdentityManager e;
    protected DHParameters f;
    protected int[] g;
    protected short[] h;
    protected short[] i;
    protected byte[] j = null;
    protected byte[] k = null;
    protected DHPrivateKeyParameters l = null;
    protected DHPublicKeyParameters m = null;
    protected ECPrivateKeyParameters n = null;
    protected ECPublicKeyParameters o = null;
    protected AsymmetricKeyParameter p = null;
    protected RSAKeyParameters q = null;
    protected TlsEncryptionCredentials r = null;
    protected byte[] s;

    public TlsPSKKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, TlsPSKIdentity pskIdentity, TlsPSKIdentityManager pskIdentityManager, DHParameters dhParameters, int[] namedCurves, short[] clientECPointFormats, short[] serverECPointFormats) {
        super(keyExchange, supportedSignatureAlgorithms);
        switch (keyExchange) {
            case 13:
            case 14:
            case 15:
            case 24:
                this.d = pskIdentity;
                this.e = pskIdentityManager;
                this.f = dhParameters;
                this.g = namedCurves;
                this.h = clientECPointFormats;
                this.i = serverECPointFormats;
                return;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
    }

    public void n() {
        if (this.a == 15) {
            throw new TlsFatalAlert(10);
        }
    }

    public void k(TlsCredentials serverCredentials) {
        if (serverCredentials instanceof TlsEncryptionCredentials) {
            m(serverCredentials.e());
            this.r = (TlsEncryptionCredentials) serverCredentials;
            return;
        }
        throw new TlsFatalAlert(80);
    }

    public byte[] b() {
        byte[] hint = this.e.getHint();
        this.j = hint;
        if (hint == null && !p()) {
            return null;
        }
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] bArr = this.j;
        if (bArr == null) {
            TlsUtils.A0(TlsUtils.a, buf);
        } else {
            TlsUtils.A0(bArr, buf);
        }
        int i2 = this.a;
        if (i2 == 14) {
            if (this.f != null) {
                this.l = TlsDHUtils.f(this.c.d(), this.f, buf);
            } else {
                throw new TlsFatalAlert(80);
            }
        } else if (i2 == 24) {
            this.n = TlsECCUtils.k(this.c.d(), this.g, this.h, buf);
        }
        return buf.toByteArray();
    }

    public void m(Certificate serverCertificate) {
        if (this.a != 15) {
            throw new TlsFatalAlert(10);
        } else if (!serverCertificate.c()) {
            Certificate x509Cert = serverCertificate.b(0);
            try {
                AsymmetricKeyParameter a = PublicKeyFactory.a(x509Cert.q());
                this.p = a;
                if (!a.a()) {
                    this.q = r((RSAKeyParameters) this.p);
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

    public boolean p() {
        switch (this.a) {
            case 14:
            case 24:
                return true;
            default:
                return false;
        }
    }

    public void c(InputStream input) {
        this.j = TlsUtils.g0(input);
        int i2 = this.a;
        if (i2 == 14) {
            DHPublicKeyParameters i3 = TlsDHUtils.i(ServerDHParams.parse(input).getPublicKey());
            this.m = i3;
            this.f = i3.b();
        } else if (i2 == 24) {
            this.o = TlsECCUtils.A(TlsECCUtils.h(this.h, TlsECCUtils.w(this.g, this.h, input), TlsUtils.i0(input)));
        }
    }

    public void i(CertificateRequest certificateRequest) {
        throw new TlsFatalAlert(10);
    }

    public void f(TlsCredentials clientCredentials) {
        throw new TlsFatalAlert(80);
    }

    public void h(OutputStream output) {
        byte[] bArr = this.j;
        if (bArr == null) {
            this.d.b();
        } else {
            this.d.c(bArr);
        }
        byte[] psk_identity = this.d.a();
        if (psk_identity != null) {
            byte[] d2 = this.d.d();
            this.k = d2;
            if (d2 != null) {
                TlsUtils.A0(psk_identity, output);
                this.c.f().j = Arrays.h(psk_identity);
                int i2 = this.a;
                if (i2 == 14) {
                    this.l = TlsDHUtils.e(this.c.d(), this.f, output);
                } else if (i2 == 24) {
                    this.n = TlsECCUtils.j(this.c.d(), this.i, this.o.b(), output);
                } else if (i2 == 15) {
                    this.s = TlsRSAUtils.a(this.c, this.q, output);
                }
            } else {
                throw new TlsFatalAlert(80);
            }
        } else {
            throw new TlsFatalAlert(80);
        }
    }

    public void e(InputStream input) {
        byte[] encryptedPreMasterSecret;
        byte[] psk_identity = TlsUtils.g0(input);
        byte[] a = this.e.a(psk_identity);
        this.k = a;
        if (a != null) {
            this.c.f().j = psk_identity;
            int i2 = this.a;
            if (i2 == 14) {
                this.m = TlsDHUtils.i(new DHPublicKeyParameters(TlsDHUtils.g(input), this.f));
            } else if (i2 == 24) {
                byte[] point = TlsUtils.i0(input);
                this.o = TlsECCUtils.A(TlsECCUtils.h(this.i, this.n.b(), point));
            } else if (i2 == 15) {
                if (TlsUtils.P(this.c)) {
                    encryptedPreMasterSecret = Streams.b(input);
                } else {
                    encryptedPreMasterSecret = TlsUtils.g0(input);
                }
                this.s = this.r.b(encryptedPreMasterSecret);
            }
        } else {
            throw new TlsFatalAlert(115);
        }
    }

    public byte[] l() {
        byte[] other_secret = q(this.k.length);
        ByteArrayOutputStream buf = new ByteArrayOutputStream(other_secret.length + 4 + this.k.length);
        TlsUtils.A0(other_secret, buf);
        TlsUtils.A0(this.k, buf);
        Arrays.F(this.k, (byte) 0);
        this.k = null;
        return buf.toByteArray();
    }

    /* access modifiers changed from: protected */
    public byte[] q(int pskLength) {
        int i2 = this.a;
        if (i2 == 14) {
            DHPrivateKeyParameters dHPrivateKeyParameters = this.l;
            if (dHPrivateKeyParameters != null) {
                return TlsDHUtils.a(this.m, dHPrivateKeyParameters);
            }
            throw new TlsFatalAlert(80);
        } else if (i2 == 24) {
            ECPrivateKeyParameters eCPrivateKeyParameters = this.n;
            if (eCPrivateKeyParameters != null) {
                return TlsECCUtils.b(this.o, eCPrivateKeyParameters);
            }
            throw new TlsFatalAlert(80);
        } else if (i2 == 15) {
            return this.s;
        } else {
            return new byte[pskLength];
        }
    }

    /* access modifiers changed from: protected */
    public RSAKeyParameters r(RSAKeyParameters key) {
        if (key.b().isProbablePrime(2)) {
            return key;
        }
        throw new TlsFatalAlert(47);
    }
}
