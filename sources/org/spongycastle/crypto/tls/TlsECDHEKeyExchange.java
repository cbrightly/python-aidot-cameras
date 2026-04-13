package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.util.io.TeeInputStream;

public class TlsECDHEKeyExchange extends TlsECDHKeyExchange {
    protected TlsSignerCredentials l = null;

    public TlsECDHEKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, int[] namedCurves, short[] clientECPointFormats, short[] serverECPointFormats) {
        super(keyExchange, supportedSignatureAlgorithms, namedCurves, clientECPointFormats, serverECPointFormats);
    }

    public void k(TlsCredentials serverCredentials) {
        if (serverCredentials instanceof TlsSignerCredentials) {
            m(serverCredentials.e());
            this.l = (TlsSignerCredentials) serverCredentials;
            return;
        }
        throw new TlsFatalAlert(80);
    }

    public byte[] b() {
        DigestInputBuffer buf = new DigestInputBuffer();
        this.j = TlsECCUtils.k(this.c.d(), this.e, this.f, buf);
        SignatureAndHashAlgorithm signatureAndHashAlgorithm = TlsUtils.J(this.c, this.l);
        Digest d = TlsUtils.n(signatureAndHashAlgorithm);
        SecurityParameters securityParameters = this.c.f();
        byte[] bArr = securityParameters.g;
        d.update(bArr, 0, bArr.length);
        byte[] bArr2 = securityParameters.h;
        d.update(bArr2, 0, bArr2.length);
        buf.a(d);
        byte[] hash = new byte[d.e()];
        d.c(hash, 0);
        new DigitallySigned(signatureAndHashAlgorithm, this.l.d(hash)).a(buf);
        return buf.toByteArray();
    }

    public void c(InputStream input) {
        SecurityParameters securityParameters = this.c.f();
        SignerInputBuffer buf = new SignerInputBuffer();
        InputStream teeIn = new TeeInputStream(input, buf);
        ECDomainParameters curve_params = TlsECCUtils.w(this.e, this.f, teeIn);
        byte[] point = TlsUtils.i0(teeIn);
        DigitallySigned signed_params = o(input);
        Signer signer = q(this.d, signed_params.b(), securityParameters);
        buf.a(signer);
        if (signer.b(signed_params.c())) {
            this.k = TlsECCUtils.A(TlsECCUtils.h(this.f, curve_params, point));
            return;
        }
        throw new TlsFatalAlert(51);
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

    /* access modifiers changed from: protected */
    public Signer q(TlsSigner tlsSigner, SignatureAndHashAlgorithm algorithm, SecurityParameters securityParameters) {
        Signer signer = tlsSigner.d(algorithm, this.h);
        byte[] bArr = securityParameters.g;
        signer.update(bArr, 0, bArr.length);
        byte[] bArr2 = securityParameters.h;
        signer.update(bArr2, 0, bArr2.length);
        return signer;
    }
}
