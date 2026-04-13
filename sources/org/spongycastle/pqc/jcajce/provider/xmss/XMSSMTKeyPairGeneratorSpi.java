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
import org.spongycastle.pqc.crypto.xmss.XMSSMTKeyGenerationParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTKeyPairGenerator;
import org.spongycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.XMSSMTParameterSpec;

public class XMSSMTKeyPairGeneratorSpi extends KeyPairGenerator {
    private XMSSMTKeyGenerationParameters a;
    private XMSSMTKeyPairGenerator b = new XMSSMTKeyPairGenerator();
    private ASN1ObjectIdentifier c;
    private SecureRandom d = new SecureRandom();
    private boolean e = false;

    public XMSSMTKeyPairGeneratorSpi() {
        super("XMSSMT");
    }

    public void initialize(int strength, SecureRandom random) {
        throw new IllegalArgumentException("use AlgorithmParameterSpec");
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof XMSSMTParameterSpec) {
            XMSSMTParameterSpec xmssParams = (XMSSMTParameterSpec) params;
            if (xmssParams.c().equals("SHA256")) {
                this.c = NISTObjectIdentifiers.c;
                this.a = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.a(), xmssParams.b(), new SHA256Digest()), random);
            } else if (xmssParams.c().equals("SHA512")) {
                this.c = NISTObjectIdentifiers.e;
                this.a = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.a(), xmssParams.b(), new SHA512Digest()), random);
            } else if (xmssParams.c().equals("SHAKE128")) {
                this.c = NISTObjectIdentifiers.m;
                this.a = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.a(), xmssParams.b(), new SHAKEDigest(128)), random);
            } else if (xmssParams.c().equals("SHAKE256")) {
                this.c = NISTObjectIdentifiers.n;
                this.a = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.a(), xmssParams.b(), new SHAKEDigest(256)), random);
            }
            this.b.c(this.a);
            this.e = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a XMSSMTParameterSpec");
    }

    public KeyPair generateKeyPair() {
        if (!this.e) {
            XMSSMTKeyGenerationParameters xMSSMTKeyGenerationParameters = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(10, 20, new SHA512Digest()), this.d);
            this.a = xMSSMTKeyGenerationParameters;
            this.b.c(xMSSMTKeyGenerationParameters);
            this.e = true;
        }
        AsymmetricCipherKeyPair pair = this.b.a();
        return new KeyPair(new BCXMSSMTPublicKey(this.c, (XMSSMTPublicKeyParameters) pair.b()), new BCXMSSMTPrivateKey(this.c, (XMSSMTPrivateKeyParameters) pair.a()));
    }
}
