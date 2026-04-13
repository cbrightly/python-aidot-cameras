package org.spongycastle.pqc.jcajce.provider.sphincs;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.SPHINCS256KeyParams;
import org.spongycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.SPHINCSKey;
import org.spongycastle.util.Arrays;

public class BCSphincs256PrivateKey implements PrivateKey, SPHINCSKey {
    private static final long serialVersionUID = 1;
    private final SPHINCSPrivateKeyParameters params;
    private final ASN1ObjectIdentifier treeDigest;

    public BCSphincs256PrivateKey(ASN1ObjectIdentifier treeDigest2, SPHINCSPrivateKeyParameters params2) {
        this.treeDigest = treeDigest2;
        this.params = params2;
    }

    public BCSphincs256PrivateKey(PrivateKeyInfo keyInfo) {
        this.treeDigest = SPHINCS256KeyParams.getInstance(keyInfo.g().h()).getTreeDigest().e();
        this.params = new SPHINCSPrivateKeyParameters(ASN1OctetString.o(keyInfo.h()).q());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BCSphincs256PrivateKey)) {
            return false;
        }
        BCSphincs256PrivateKey otherKey = (BCSphincs256PrivateKey) o;
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
            return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.r, new SPHINCS256KeyParams(new AlgorithmIdentifier(this.treeDigest))), new DEROctetString(this.params.b())).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public byte[] getKeyData() {
        return this.params.b();
    }

    /* access modifiers changed from: package-private */
    public CipherParameters getKeyParams() {
        return this.params;
    }
}
