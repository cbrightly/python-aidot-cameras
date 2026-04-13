package org.spongycastle.crypto.tls;

import java.io.InputStream;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.util.io.TeeInputStream;

public class TlsDHEKeyExchange extends TlsDHKeyExchange {
    protected TlsSignerCredentials j = null;

    public TlsDHEKeyExchange(int keyExchange, Vector supportedSignatureAlgorithms, DHParameters dhParameters) {
        super(keyExchange, supportedSignatureAlgorithms, dhParameters);
    }

    public void k(TlsCredentials serverCredentials) {
        if (serverCredentials instanceof TlsSignerCredentials) {
            m(serverCredentials.e());
            this.j = (TlsSignerCredentials) serverCredentials;
            return;
        }
        throw new TlsFatalAlert(80);
    }

    public byte[] b() {
        if (this.e != null) {
            DigestInputBuffer buf = new DigestInputBuffer();
            this.h = TlsDHUtils.f(this.c.d(), this.e, buf);
            SignatureAndHashAlgorithm signatureAndHashAlgorithm = TlsUtils.J(this.c, this.j);
            Digest d = TlsUtils.n(signatureAndHashAlgorithm);
            SecurityParameters securityParameters = this.c.f();
            byte[] bArr = securityParameters.g;
            d.update(bArr, 0, bArr.length);
            byte[] bArr2 = securityParameters.h;
            d.update(bArr2, 0, bArr2.length);
            buf.a(d);
            byte[] hash = new byte[d.e()];
            d.c(hash, 0);
            new DigitallySigned(signatureAndHashAlgorithm, this.j.d(hash)).a(buf);
            return buf.toByteArray();
        }
        throw new TlsFatalAlert(80);
    }

    public void c(InputStream input) {
        SecurityParameters securityParameters = this.c.f();
        SignerInputBuffer buf = new SignerInputBuffer();
        ServerDHParams dhParams = ServerDHParams.parse(new TeeInputStream(input, buf));
        DigitallySigned signed_params = o(input);
        Signer signer = s(this.d, signed_params.b(), securityParameters);
        buf.a(signer);
        if (signer.b(signed_params.c())) {
            DHPublicKeyParameters i = TlsDHUtils.i(dhParams.getPublicKey());
            this.i = i;
            this.e = r(i.b());
            return;
        }
        throw new TlsFatalAlert(51);
    }

    /* access modifiers changed from: protected */
    public Signer s(TlsSigner tlsSigner, SignatureAndHashAlgorithm algorithm, SecurityParameters securityParameters) {
        Signer signer = tlsSigner.d(algorithm, this.f);
        byte[] bArr = securityParameters.g;
        signer.update(bArr, 0, bArr.length);
        byte[] bArr2 = securityParameters.h;
        signer.update(bArr2, 0, bArr2.length);
        return signer;
    }
}
