package org.spongycastle.pqc.jcajce.provider.newhope;

import java.io.IOException;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.newhope.NHPublicKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.NHPublicKey;
import org.spongycastle.util.Arrays;

public class BCNHPublicKey implements NHPublicKey {
    private static final long serialVersionUID = 1;
    private final NHPublicKeyParameters params;

    public BCNHPublicKey(NHPublicKeyParameters params2) {
        this.params = params2;
    }

    public BCNHPublicKey(SubjectPublicKeyInfo keyInfo) {
        this.params = new NHPublicKeyParameters(keyInfo.h().q());
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof BCNHPublicKey)) {
            return false;
        }
        return Arrays.b(this.params.b(), ((BCNHPublicKey) o).params.b());
    }

    public int hashCode() {
        return Arrays.J(this.params.b());
    }

    public final String getAlgorithm() {
        return "NH";
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.v), this.params.b()).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getPublicData() {
        return this.params.b();
    }

    /* access modifiers changed from: package-private */
    public CipherParameters getKeyParams() {
        return this.params;
    }
}
