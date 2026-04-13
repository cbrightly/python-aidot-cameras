package org.spongycastle.pqc.jcajce.provider.xmss;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHAKEDigest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.xmss.XMSSSigner;
import org.spongycastle.pqc.jcajce.interfaces.StateAwareSignature;

public class XMSSSignatureSpi extends Signature implements StateAwareSignature {
    private Digest c;
    private XMSSSigner d;
    private SecureRandom f;
    private ASN1ObjectIdentifier q;

    protected XMSSSignatureSpi(String sigName, Digest digest, XMSSSigner signer) {
        super(sigName);
        this.c = digest;
        this.d = signer;
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        if (publicKey instanceof BCXMSSPublicKey) {
            CipherParameters param = ((BCXMSSPublicKey) publicKey).getKeyParams();
            this.q = null;
            this.c.reset();
            this.d.a(false, param);
            return;
        }
        throw new InvalidKeyException("unknown public key passed to XMSS");
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey, SecureRandom random) {
        this.f = random;
        engineInitSign(privateKey);
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        if (privateKey instanceof BCXMSSPrivateKey) {
            CipherParameters param = ((BCXMSSPrivateKey) privateKey).getKeyParams();
            this.q = ((BCXMSSPrivateKey) privateKey).getTreeDigestOID();
            SecureRandom secureRandom = this.f;
            if (secureRandom != null) {
                param = new ParametersWithRandom(param, secureRandom);
            }
            this.c.reset();
            this.d.a(true, param);
            return;
        }
        throw new InvalidKeyException("unknown private key passed to XMSS");
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b) {
        this.c.d(b);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] b, int off, int len) {
        this.c.update(b, off, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        try {
            return this.d.b(DigestUtil.b(this.c));
        } catch (Exception e) {
            if (e instanceof IllegalStateException) {
                throw new SignatureException(e.getMessage());
            }
            throw new SignatureException(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] sigBytes) {
        return this.d.c(DigestUtil.b(this.c), sigBytes);
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(AlgorithmParameterSpec params) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(String param, Object value) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public Object engineGetParameter(String param) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    public static class withSha256 extends XMSSSignatureSpi {
        public withSha256() {
            super("SHA256withXMSS", new SHA256Digest(), new XMSSSigner());
        }
    }

    public static class withShake128 extends XMSSSignatureSpi {
        public withShake128() {
            super("SHAKE128withXMSSMT", new SHAKEDigest(128), new XMSSSigner());
        }
    }

    public static class withSha512 extends XMSSSignatureSpi {
        public withSha512() {
            super("SHA512withXMSS", new SHA512Digest(), new XMSSSigner());
        }
    }

    public static class withShake256 extends XMSSSignatureSpi {
        public withShake256() {
            super("SHAKE256withXMSS", new SHAKEDigest(256), new XMSSSigner());
        }
    }
}
