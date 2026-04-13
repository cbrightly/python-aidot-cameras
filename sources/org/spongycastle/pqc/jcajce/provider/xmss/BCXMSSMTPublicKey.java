package org.spongycastle.pqc.jcajce.provider.xmss;

import java.io.IOException;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.XMSSMTKeyParams;
import org.spongycastle.pqc.asn1.XMSSPublicKey;
import org.spongycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPublicKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.XMSSMTKey;
import org.spongycastle.util.Arrays;

public class BCXMSSMTPublicKey implements PublicKey, XMSSMTKey {
    private final XMSSMTPublicKeyParameters keyParams;
    private final ASN1ObjectIdentifier treeDigest;

    public BCXMSSMTPublicKey(ASN1ObjectIdentifier treeDigest2, XMSSMTPublicKeyParameters keyParams2) {
        this.treeDigest = treeDigest2;
        this.keyParams = keyParams2;
    }

    public BCXMSSMTPublicKey(SubjectPublicKeyInfo keyInfo) {
        XMSSMTKeyParams keyParams2 = XMSSMTKeyParams.getInstance(keyInfo.e().h());
        ASN1ObjectIdentifier e = keyParams2.getTreeDigest().e();
        this.treeDigest = e;
        XMSSPublicKey xmssMtPublicKey = XMSSPublicKey.e(keyInfo.i());
        this.keyParams = new XMSSMTPublicKeyParameters.Builder(new XMSSMTParameters(keyParams2.getHeight(), keyParams2.getLayers(), DigestUtil.a(e))).f(xmssMtPublicKey.f()).g(xmssMtPublicKey.g()).e();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BCXMSSMTPublicKey)) {
            return false;
        }
        BCXMSSMTPublicKey otherKey = (BCXMSSMTPublicKey) o;
        if (!this.treeDigest.equals(otherKey.treeDigest) || !Arrays.b(this.keyParams.e(), otherKey.keyParams.e())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.treeDigest.hashCode() + (Arrays.J(this.keyParams.e()) * 37);
    }

    public final String getAlgorithm() {
        return "XMSSMT";
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.B, new XMSSMTKeyParams(this.keyParams.b().c(), this.keyParams.b().d(), new AlgorithmIdentifier(this.treeDigest))), (ASN1Encodable) new XMSSPublicKey(this.keyParams.c(), this.keyParams.d())).getEncoded();
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

    public int getHeight() {
        return this.keyParams.b().c();
    }

    public int getLayers() {
        return this.keyParams.b().d();
    }

    public String getTreeDigest() {
        return DigestUtil.d(this.treeDigest);
    }
}
