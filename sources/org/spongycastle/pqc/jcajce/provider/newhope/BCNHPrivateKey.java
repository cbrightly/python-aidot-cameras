package org.spongycastle.pqc.jcajce.provider.newhope;

import java.io.IOException;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.newhope.NHPrivateKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.NHPrivateKey;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class BCNHPrivateKey implements NHPrivateKey {
    private static final long serialVersionUID = 1;
    private final NHPrivateKeyParameters params;

    public BCNHPrivateKey(NHPrivateKeyParameters params2) {
        this.params = params2;
    }

    public BCNHPrivateKey(PrivateKeyInfo keyInfo) {
        this.params = new NHPrivateKeyParameters(a(ASN1OctetString.o(keyInfo.h()).q()));
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof BCNHPrivateKey)) {
            return false;
        }
        return Arrays.g(this.params.b(), ((BCNHPrivateKey) o).params.b());
    }

    public int hashCode() {
        return Arrays.P(this.params.b());
    }

    public final String getAlgorithm() {
        return "NH";
    }

    public byte[] getEncoded() {
        try {
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.v);
            short[] privateKeyData = this.params.b();
            byte[] octets = new byte[(privateKeyData.length * 2)];
            for (int i = 0; i != privateKeyData.length; i++) {
                Pack.v(privateKeyData[i], octets, i * 2);
            }
            return new PrivateKeyInfo(algorithmIdentifier, new DEROctetString(octets)).getEncoded();
        } catch (IOException e) {
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public short[] getSecretData() {
        return this.params.b();
    }

    /* access modifiers changed from: package-private */
    public CipherParameters getKeyParams() {
        return this.params;
    }

    private static short[] a(byte[] octets) {
        short[] rv = new short[(octets.length / 2)];
        for (int i = 0; i != rv.length; i++) {
            rv[i] = Pack.o(octets, i * 2);
        }
        return rv;
    }
}
