package org.spongycastle.pqc.jcajce.provider.xmss;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.XMSSKeyParams;
import org.spongycastle.pqc.asn1.XMSSPrivateKey;
import org.spongycastle.pqc.crypto.xmss.BDS;
import org.spongycastle.pqc.crypto.xmss.XMSSParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSUtil;
import org.spongycastle.pqc.jcajce.interfaces.XMSSKey;
import org.spongycastle.util.Arrays;

public class BCXMSSPrivateKey implements PrivateKey, XMSSKey {
    private final XMSSPrivateKeyParameters keyParams;
    private final ASN1ObjectIdentifier treeDigest;

    public BCXMSSPrivateKey(ASN1ObjectIdentifier treeDigest2, XMSSPrivateKeyParameters keyParams2) {
        this.treeDigest = treeDigest2;
        this.keyParams = keyParams2;
    }

    public BCXMSSPrivateKey(PrivateKeyInfo keyInfo) {
        XMSSKeyParams keyParams2 = XMSSKeyParams.getInstance(keyInfo.g().h());
        ASN1ObjectIdentifier e = keyParams2.getTreeDigest().e();
        this.treeDigest = e;
        XMSSPrivateKey xmssPrivateKey = XMSSPrivateKey.g(keyInfo.h());
        try {
            XMSSPrivateKeyParameters.Builder keyBuilder = new XMSSPrivateKeyParameters.Builder(new XMSSParameters(keyParams2.getHeight(), DigestUtil.a(e))).l(xmssPrivateKey.f()).p(xmssPrivateKey.n()).o(xmssPrivateKey.k()).m(xmssPrivateKey.h()).n(xmssPrivateKey.i());
            if (xmssPrivateKey.e() != null) {
                keyBuilder.k((BDS) XMSSUtil.f(xmssPrivateKey.e()));
            }
            this.keyParams = keyBuilder.j();
        } catch (ClassNotFoundException e2) {
            throw new IOException("ClassNotFoundException processing BDS state: " + e2.getMessage());
        }
    }

    public String getAlgorithm() {
        return "XMSS";
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getEncoded() {
        try {
            return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.w, new XMSSKeyParams(this.keyParams.e().d(), new AlgorithmIdentifier(this.treeDigest))), a()).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BCXMSSPrivateKey)) {
            return false;
        }
        BCXMSSPrivateKey otherKey = (BCXMSSPrivateKey) o;
        if (!this.treeDigest.equals(otherKey.treeDigest) || !Arrays.b(this.keyParams.j(), otherKey.keyParams.j())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.treeDigest.hashCode() + (Arrays.J(this.keyParams.j()) * 37);
    }

    /* access modifiers changed from: package-private */
    public CipherParameters getKeyParams() {
        return this.keyParams;
    }

    private XMSSPrivateKey a() {
        byte[] keyData = this.keyParams.j();
        int n = this.keyParams.e().c();
        int totalHeight = this.keyParams.e().d();
        int secretKeySize = n;
        int secretKeyPRFSize = n;
        int publicSeedSize = n;
        int rootSize = n;
        int index = (int) XMSSUtil.a(keyData, 0, 4);
        if (XMSSUtil.l(totalHeight, (long) index)) {
            int position = 0 + 4;
            byte[] secretKeySeed = XMSSUtil.g(keyData, position, secretKeySize);
            int position2 = position + secretKeySize;
            byte[] secretKeyPRF = XMSSUtil.g(keyData, position2, secretKeyPRFSize);
            int position3 = position2 + secretKeyPRFSize;
            byte[] publicSeed = XMSSUtil.g(keyData, position3, publicSeedSize);
            int position4 = position3 + publicSeedSize;
            byte[] root = XMSSUtil.g(keyData, position4, rootSize);
            int position5 = position4 + rootSize;
            return new XMSSPrivateKey(index, secretKeySeed, secretKeyPRF, publicSeed, root, XMSSUtil.g(keyData, position5, keyData.length - position5));
        }
        throw new IllegalArgumentException("index out of bounds");
    }

    /* access modifiers changed from: package-private */
    public ASN1ObjectIdentifier getTreeDigestOID() {
        return this.treeDigest;
    }

    public int getHeight() {
        return this.keyParams.e().d();
    }

    public String getTreeDigest() {
        return DigestUtil.d(this.treeDigest);
    }
}
