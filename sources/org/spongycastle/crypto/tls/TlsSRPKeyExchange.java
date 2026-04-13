package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.agreement.srp.SRP6Client;
import org.spongycastle.crypto.agreement.srp.SRP6Server;
import org.spongycastle.crypto.agreement.srp.SRP6Util;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.SRP6GroupParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.io.TeeInputStream;

public class TlsSRPKeyExchange extends AbstractTlsKeyExchange {
    protected TlsSigner d;
    protected TlsSRPGroupVerifier e;
    protected byte[] f;
    protected byte[] g;
    protected AsymmetricKeyParameter h = null;
    protected SRP6GroupParameters i = null;
    protected SRP6Client j = null;
    protected SRP6Server k = null;
    protected BigInteger l = null;
    protected BigInteger m = null;
    protected byte[] n = null;
    protected TlsSignerCredentials o = null;

    protected static TlsSigner q(int keyExchange) {
        switch (keyExchange) {
            case 21:
                return null;
            case 22:
                return new TlsDSSSigner();
            case 23:
                return new TlsRSASigner();
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
    }

    public TlsSRPKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, TlsSRPGroupVerifier groupVerifier, byte[] identity, byte[] password) {
        super(keyExchange, supportedSignatureAlgorithms);
        this.d = q(keyExchange);
        this.e = groupVerifier;
        this.f = identity;
        this.g = password;
        this.j = new SRP6Client();
    }

    public TlsSRPKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, byte[] identity, TlsSRPLoginParameters loginParameters) {
        super(keyExchange, supportedSignatureAlgorithms);
        this.d = q(keyExchange);
        this.f = identity;
        this.k = new SRP6Server();
        this.i = loginParameters.a();
        this.m = loginParameters.c();
        this.n = loginParameters.b();
    }

    public void a(TlsContext context) {
        super.a(context);
        TlsSigner tlsSigner = this.d;
        if (tlsSigner != null) {
            tlsSigner.a(context);
        }
    }

    public void n() {
        if (this.d != null) {
            throw new TlsFatalAlert(10);
        }
    }

    public void m(Certificate serverCertificate) {
        if (this.d == null) {
            throw new TlsFatalAlert(10);
        } else if (!serverCertificate.c()) {
            Certificate x509Cert = serverCertificate.b(0);
            try {
                AsymmetricKeyParameter a = PublicKeyFactory.a(x509Cert.q());
                this.h = a;
                if (this.d.e(a)) {
                    TlsUtils.x0(x509Cert, 128);
                    super.m(serverCertificate);
                    return;
                }
                throw new TlsFatalAlert(46);
            } catch (RuntimeException e2) {
                throw new TlsFatalAlert(43, e2);
            }
        } else {
            throw new TlsFatalAlert(42);
        }
    }

    public void k(TlsCredentials serverCredentials) {
        if (this.a == 21 || !(serverCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
        m(serverCredentials.e());
        this.o = (TlsSignerCredentials) serverCredentials;
    }

    public boolean p() {
        return true;
    }

    public byte[] b() {
        this.k.e(this.i, this.m, TlsUtils.o(2), this.c.d());
        ServerSRPParams srpParams = new ServerSRPParams(this.i.b(), this.i.a(), this.n, this.k.c());
        DigestInputBuffer buf = new DigestInputBuffer();
        srpParams.encode(buf);
        TlsSignerCredentials tlsSignerCredentials = this.o;
        if (tlsSignerCredentials != null) {
            SignatureAndHashAlgorithm signatureAndHashAlgorithm = TlsUtils.J(this.c, tlsSignerCredentials);
            Digest d2 = TlsUtils.n(signatureAndHashAlgorithm);
            SecurityParameters securityParameters = this.c.f();
            byte[] bArr = securityParameters.g;
            d2.update(bArr, 0, bArr.length);
            byte[] bArr2 = securityParameters.h;
            d2.update(bArr2, 0, bArr2.length);
            buf.a(d2);
            byte[] hash = new byte[d2.e()];
            d2.c(hash, 0);
            new DigitallySigned(signatureAndHashAlgorithm, this.o.d(hash)).a(buf);
        }
        return buf.toByteArray();
    }

    public void c(InputStream input) {
        SecurityParameters securityParameters = this.c.f();
        SignerInputBuffer buf = null;
        InputStream teeIn = input;
        if (this.d != null) {
            buf = new SignerInputBuffer();
            teeIn = new TeeInputStream(input, buf);
        }
        ServerSRPParams srpParams = ServerSRPParams.parse(teeIn);
        if (buf != null) {
            DigitallySigned signed_params = o(input);
            Signer signer = r(this.d, signed_params.b(), securityParameters);
            buf.a(signer);
            if (!signer.b(signed_params.c())) {
                throw new TlsFatalAlert(51);
            }
        }
        SRP6GroupParameters sRP6GroupParameters = new SRP6GroupParameters(srpParams.getN(), srpParams.getG());
        this.i = sRP6GroupParameters;
        if (this.e.a(sRP6GroupParameters)) {
            this.n = srpParams.getS();
            try {
                this.l = SRP6Util.g(this.i.b(), srpParams.getB());
                this.j.e(this.i, TlsUtils.o(2), this.c.d());
            } catch (CryptoException e2) {
                throw new TlsFatalAlert(47, e2);
            }
        } else {
            throw new TlsFatalAlert(71);
        }
    }

    public void i(CertificateRequest certificateRequest) {
        throw new TlsFatalAlert(10);
    }

    public void f(TlsCredentials clientCredentials) {
        throw new TlsFatalAlert(80);
    }

    public void h(OutputStream output) {
        TlsSRPUtils.e(this.j.c(this.n, this.f, this.g), output);
        this.c.f().k = Arrays.h(this.f);
    }

    public void e(InputStream input) {
        try {
            this.l = SRP6Util.g(this.i.b(), TlsSRPUtils.d(input));
            this.c.f().k = Arrays.h(this.f);
        } catch (CryptoException e2) {
            throw new TlsFatalAlert(47, e2);
        }
    }

    public byte[] l() {
        BigInteger S;
        try {
            SRP6Server sRP6Server = this.k;
            if (sRP6Server != null) {
                S = sRP6Server.b(this.l);
            } else {
                S = this.j.b(this.l);
            }
            return BigIntegers.b(S);
        } catch (CryptoException e2) {
            throw new TlsFatalAlert(47, e2);
        }
    }

    /* access modifiers changed from: protected */
    public Signer r(TlsSigner tlsSigner, SignatureAndHashAlgorithm algorithm, SecurityParameters securityParameters) {
        Signer signer = tlsSigner.d(algorithm, this.h);
        byte[] bArr = securityParameters.g;
        signer.update(bArr, 0, bArr.length);
        byte[] bArr2 = securityParameters.h;
        signer.update(bArr2, 0, bArr2.length);
        return signer;
    }
}
