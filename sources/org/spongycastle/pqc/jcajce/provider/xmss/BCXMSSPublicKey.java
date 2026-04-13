package org.spongycastle.pqc.jcajce.provider.xmss;

import java.io.IOException;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.XMSSKeyParams;
import org.spongycastle.pqc.asn1.XMSSPublicKey;
import org.spongycastle.pqc.crypto.xmss.XMSSParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.XMSSKey;
import org.spongycastle.util.Arrays;

public class BCXMSSPublicKey implements PublicKey, XMSSKey {
    private final XMSSPublicKeyParameters keyParams;
    private final ASN1ObjectIdentifier treeDigest;

    public BCXMSSPublicKey(ASN1ObjectIdentifier treeDigest2, XMSSPublicKeyParameters keyParams2) {
        this.treeDigest = treeDigest2;
        this.keyParams = keyParams2;
    }

    public BCXMSSPublicKey(SubjectPublicKeyInfo keyInfo) {
        XMSSKeyParams keyParams2 = XMSSKeyParams.getInstance(keyInfo.e().h());
        ASN1ObjectIdentifier e = keyParams2.getTreeDigest().e();
        this.treeDigest = e;
        XMSSPublicKey xmssPublicKey = XMSSPublicKey.e(keyInfo.i());
        this.keyParams = new XMSSPublicKeyParameters.Builder(new XMSSParameters(keyParams2.getHeight(), DigestUtil.a(e))).f(xmssPublicKey.f()).g(xmssPublicKey.g()).e();
    }

    public final String getAlgorithm() {
        return "XMSS";
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.w, new XMSSKeyParams(this.keyParams.b().d(), new AlgorithmIdentifier(this.treeDigest))), (ASN1Encodable) new XMSSPublicKey(this.keyParams.c(), this.keyParams.d())).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    public String getFormat() {
        return "X.509";
    }

    /* access modifiers changed from: package-private */
    public CipherParameters getKeyParams() {
        return this.keyParams;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BCXMSSPublicKey)) {
            return false;
        }
        BCXMSSPublicKey otherKey = (BCXMSSPublicKey) o;
        if (!this.treeDigest.equals(otherKey.treeDigest) || !Arrays.b(this.keyParams.e(), otherKey.keyParams.e())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.treeDigest.hashCode() + (Arrays.J(this.keyParams.e()) * 37);
    }

    public int getHeight() {
        return this.keyParams.b().d();
    }

    public String getTreeDigest() {
        return DigestUtil.d(this.treeDigest);
    }
}
