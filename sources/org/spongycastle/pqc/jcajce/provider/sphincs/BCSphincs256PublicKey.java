package org.spongycastle.pqc.jcajce.provider.sphincs;

import java.io.IOException;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.SPHINCS256KeyParams;
import org.spongycastle.pqc.crypto.sphincs.SPHINCSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.SPHINCSKey;
import org.spongycastle.util.Arrays;

public class BCSphincs256PublicKey implements PublicKey, SPHINCSKey {
    private static final long serialVersionUID = 1;
    private final SPHINCSPublicKeyParameters params;
    private final ASN1ObjectIdentifier treeDigest;

    public BCSphincs256PublicKey(ASN1ObjectIdentifier treeDigest2, SPHINCSPublicKeyParameters params2) {
        this.treeDigest = treeDigest2;
        this.params = params2;
    }

    public BCSphincs256PublicKey(SubjectPublicKeyInfo keyInfo) {
        this.treeDigest = SPHINCS256KeyParams.getInstance(keyInfo.e().h()).getTreeDigest().e();
        this.params = new SPHINCSPublicKeyParameters(keyInfo.h().q());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BCSphincs256PublicKey)) {
            return false;
        }
        BCSphincs256PublicKey otherKey = (BCSphincs256PublicKey) o;
        if (!this.treeDigest.equals(otherKey.treeDigest) || !Arrays.b(this.params.b(), otherKey.params.b())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.treeDigest.hashCode() + (Arrays.J(this.params.b()) * 37);
    }

    public final String getAlgorithm() {
        return "SPHINCS-256";
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.r, new SPHINCS256KeyParams(new AlgorithmIdentifier(this.treeDigest))), this.params.b()).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getKeyData() {
        return this.params.b();
    }

    /* access modifiers changed from: package-private */
    public CipherParameters getKeyParams() {
        return this.params;
    }
}
