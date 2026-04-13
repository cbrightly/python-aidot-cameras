package org.spongycastle.pqc.jcajce.provider.gmss;

import java.security.PublicKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.GMSSPublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.ParSet;
import org.spongycastle.pqc.crypto.gmss.GMSSParameters;
import org.spongycastle.pqc.crypto.gmss.GMSSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.provider.util.KeyUtil;
import org.spongycastle.util.encoders.Hex;

public class BCGMSSPublicKey implements CipherParameters, PublicKey {
    private static final long serialVersionUID = 1;
    private GMSSParameters gmssParameterSet;
    private GMSSParameters gmssParams;
    private byte[] publicKeyBytes;

    public BCGMSSPublicKey(byte[] pub2, GMSSParameters gmssParameterSet2) {
        this.gmssParameterSet = gmssParameterSet2;
        this.publicKeyBytes = pub2;
    }

    public BCGMSSPublicKey(GMSSPublicKeyParameters params) {
        this(params.c(), params.b());
    }

    public String getAlgorithm() {
        return "GMSS";
    }

    public byte[] getPublicKeyBytes() {
        return this.publicKeyBytes;
    }

    public GMSSParameters getParameterSet() {
        return this.gmssParameterSet;
    }

    public String toString() {
        String out = "GMSS public key : " + new String(Hex.b(this.publicKeyBytes)) + "\nHeight of Trees: \n";
        for (int i = 0; i < this.gmssParameterSet.a().length; i++) {
            out = out + "Layer " + i + " : " + this.gmssParameterSet.a()[i] + " WinternitzParameter: " + this.gmssParameterSet.d()[i] + " K: " + this.gmssParameterSet.b()[i] + "\n";
        }
        return out;
    }

    public byte[] getEncoded() {
        return KeyUtil.a(new AlgorithmIdentifier(PQCObjectIdentifiers.g, new ParSet(this.gmssParameterSet.c(), this.gmssParameterSet.a(), this.gmssParameterSet.d(), this.gmssParameterSet.b()).toASN1Primitive()), new GMSSPublicKey(this.publicKeyBytes));
    }

    public String getFormat() {
        return "X.509";
    }
}
