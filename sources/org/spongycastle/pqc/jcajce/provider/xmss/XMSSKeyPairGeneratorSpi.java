package org.spongycastle.pqc.jcajce.provider.xmss;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHAKEDigest;
import org.spongycastle.pqc.crypto.xmss.XMSSKeyGenerationParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSKeyPairGenerator;
import org.spongycastle.pqc.crypto.xmss.XMSSParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.XMSSParameterSpec;

public class XMSSKeyPairGeneratorSpi extends KeyPairGenerator {
    private XMSSKeyGenerationParameters a;
    private ASN1ObjectIdentifier b;
    private XMSSKeyPairGenerator c = new XMSSKeyPairGenerator();
    private SecureRandom d = new SecureRandom();
    private boolean e = false;

    public XMSSKeyPairGeneratorSpi() {
        super("XMSS");
    }

    public void initialize(int strength, SecureRandom random) {
        throw new IllegalArgumentException("use AlgorithmParameterSpec");
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof XMSSParameterSpec) {
            XMSSParameterSpec xmssParams = (XMSSParameterSpec) params;
            if (xmssParams.b().equals("SHA256")) {
                this.b = NISTObjectIdentifiers.c;
                this.a = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.a(), new SHA256Digest()), random);
            } else if (xmssParams.b().equals("SHA512")) {
                this.b = NISTObjectIdentifiers.e;
                this.a = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.a(), new SHA512Digest()), random);
            } else if (xmssParams.b().equals("SHAKE128")) {
                this.b = NISTObjectIdentifiers.m;
                this.a = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.a(), new SHAKEDigest(128)), random);
            } else if (xmssParams.b().equals("SHAKE256")) {
                this.b = NISTObjectIdentifiers.n;
                this.a = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.a(), new SHAKEDigest(256)), random);
            }
            this.c.c(this.a);
            this.e = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a XMSSParameterSpec");
    }

    public KeyPair generateKeyPair() {
        if (!this.e) {
            XMSSKeyGenerationParameters xMSSKeyGenerationParameters = new XMSSKeyGenerationParameters(new XMSSParameters(10, new SHA512Digest()), this.d);
            this.a = xMSSKeyGenerationParameters;
            this.c.c(xMSSKeyGenerationParameters);
            this.e = true;
        }
        AsymmetricCipherKeyPair pair = this.c.a();
        return new KeyPair(new BCXMSSPublicKey(this.b, (XMSSPublicKeyParameters) pair.b()), new BCXMSSPrivateKey(this.b, (XMSSPrivateKeyParameters) pair.a()));
    }
}
