package org.spongycastle.pqc.jcajce.provider.xmss;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.XMSSMTKeyParams;
import org.spongycastle.pqc.asn1.XMSSMTPrivateKey;
import org.spongycastle.pqc.asn1.XMSSPrivateKey;
import org.spongycastle.pqc.crypto.xmss.BDSStateMap;
import org.spongycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSUtil;
import org.spongycastle.pqc.jcajce.interfaces.XMSSMTKey;
import org.spongycastle.util.Arrays;

public class BCXMSSMTPrivateKey implements PrivateKey, XMSSMTKey {
    private final XMSSMTPrivateKeyParameters keyParams;
    private final ASN1ObjectIdentifier treeDigest;

    public BCXMSSMTPrivateKey(ASN1ObjectIdentifier treeDigest2, XMSSMTPrivateKeyParameters keyParams2) {
        this.treeDigest = treeDigest2;
        this.keyParams = keyParams2;
    }

    public BCXMSSMTPrivateKey(PrivateKeyInfo keyInfo) {
        XMSSMTKeyParams keyParams2 = XMSSMTKeyParams.getInstance(keyInfo.g().h());
        ASN1ObjectIdentifier e = keyParams2.getTreeDigest().e();
        this.treeDigest = e;
        XMSSPrivateKey xmssMtPrivateKey = XMSSPrivateKey.g(keyInfo.h());
        try {
            XMSSMTPrivateKeyParameters.Builder keyBuilder = new XMSSMTPrivateKeyParameters.Builder(new XMSSMTParameters(keyParams2.getHeight(), keyParams2.getLayers(), DigestUtil.a(e))).l((long) xmssMtPrivateKey.f()).p(xmssMtPrivateKey.n()).o(xmssMtPrivateKey.k()).m(xmssMtPrivateKey.h()).n(xmssMtPrivateKey.i());
            if (xmssMtPrivateKey.e() != null) {
                keyBuilder.k((BDSStateMap) XMSSUtil.f(xmssMtPrivateKey.e()));
            }
            this.keyParams = keyBuilder.j();
        } catch (ClassNotFoundException e2) {
            throw new IOException("ClassNotFoundException processing BDS state: " + e2.getMessage());
        }
    }

    public String getAlgorithm() {
        return "XMSSMT";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        try {
            return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.B, new XMSSMTKeyParams(this.keyParams.e().c(), this.keyParams.e().d(), new AlgorithmIdentifier(this.treeDigest))), a()).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public CipherParameters getKeyParams() {
        return this.keyParams;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BCXMSSMTPrivateKey)) {
            return false;
        }
        BCXMSSMTPrivateKey otherKey = (BCXMSSMTPrivateKey) o;
        if (!this.treeDigest.equals(otherKey.treeDigest) || !Arrays.b(this.keyParams.j(), otherKey.keyParams.j())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.treeDigest.hashCode() + (Arrays.J(this.keyParams.j()) * 37);
    }

    private XMSSMTPrivateKey a() {
        byte[] keyData = this.keyParams.j();
        int n = this.keyParams.e().b();
        int totalHeight = this.keyParams.e().c();
        int indexSize = (totalHeight + 7) / 8;
        int secretKeySize = n;
        int secretKeyPRFSize = n;
        int publicSeedSize = n;
        int rootSize = n;
        int index = (int) XMSSUtil.a(keyData, 0, indexSize);
        if (XMSSUtil.l(totalHeight, (long) index)) {
            int position = 0 + indexSize;
            byte[] secretKeySeed = XMSSUtil.g(keyData, position, secretKeySize);
            int position2 = position + secretKeySize;
            byte[] secretKeyPRF = XMSSUtil.g(keyData, position2, secretKeyPRFSize);
            int position3 = position2 + secretKeyPRFSize;
            byte[] publicSeed = XMSSUtil.g(keyData, position3, publicSeedSize);
            int position4 = position3 + publicSeedSize;
            byte[] root = XMSSUtil.g(keyData, position4, rootSize);
            int position5 = position4 + rootSize;
            return new XMSSMTPrivateKey(index, secretKeySeed, secretKeyPRF, publicSeed, root, XMSSUtil.g(keyData, position5, keyData.length - position5));
        }
        throw new IllegalArgumentException("index out of bounds");
    }

    /* access modifiers changed from: package-private */
    public ASN1ObjectIdentifier getTreeDigestOID() {
        return this.treeDigest;
    }

    public int getHeight() {
        return this.keyParams.e().c();
    }

    public int getLayers() {
        return this.keyParams.e().d();
    }

    public String getTreeDigest() {
        return DigestUtil.d(this.treeDigest);
    }
}
