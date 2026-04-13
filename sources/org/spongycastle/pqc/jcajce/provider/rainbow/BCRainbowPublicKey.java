package org.spongycastle.pqc.jcajce.provider.rainbow;

import java.security.PublicKey;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.RainbowPublicKey;
import org.spongycastle.pqc.crypto.rainbow.RainbowParameters;
import org.spongycastle.pqc.crypto.rainbow.RainbowPublicKeyParameters;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.spongycastle.pqc.jcajce.provider.util.KeyUtil;
import org.spongycastle.pqc.jcajce.spec.RainbowPublicKeySpec;
import org.spongycastle.util.Arrays;

public class BCRainbowPublicKey implements PublicKey {
    private static final long serialVersionUID = 1;
    private short[][] coeffquadratic;
    private short[] coeffscalar;
    private short[][] coeffsingular;
    private int docLength;
    private RainbowParameters rainbowParams;

    public BCRainbowPublicKey(int docLength2, short[][] coeffQuadratic, short[][] coeffSingular, short[] coeffScalar) {
        this.docLength = docLength2;
        this.coeffquadratic = coeffQuadratic;
        this.coeffsingular = coeffSingular;
        this.coeffscalar = coeffScalar;
    }

    public BCRainbowPublicKey(RainbowPublicKeySpec keySpec) {
        this(keySpec.d(), keySpec.a(), keySpec.c(), keySpec.b());
    }

    public BCRainbowPublicKey(RainbowPublicKeyParameters params) {
        this(params.b(), params.c(), params.e(), params.d());
    }

    public int getDocLength() {
        return this.docLength;
    }

    public short[][] getCoeffQuadratic() {
        return this.coeffquadratic;
    }

    public short[][] getCoeffSingular() {
        short[][] copy = new short[this.coeffsingular.length][];
        int i = 0;
        while (true) {
            short[][] sArr = this.coeffsingular;
            if (i == sArr.length) {
                return copy;
            }
            copy[i] = Arrays.o(sArr[i]);
            i++;
        }
    }

    public short[] getCoeffScalar() {
        return Arrays.o(this.coeffscalar);
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof BCRainbowPublicKey)) {
            return false;
        }
        BCRainbowPublicKey otherKey = (BCRainbowPublicKey) other;
        if (this.docLength != otherKey.getDocLength() || !RainbowUtil.j(this.coeffquadratic, otherKey.getCoeffQuadratic()) || !RainbowUtil.j(this.coeffsingular, otherKey.getCoeffSingular()) || !RainbowUtil.i(this.coeffscalar, otherKey.getCoeffScalar())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.docLength * 37) + Arrays.Q(this.coeffquadratic)) * 37) + Arrays.Q(this.coeffsingular)) * 37) + Arrays.P(this.coeffscalar);
    }

    public final String getAlgorithm() {
        return "Rainbow";
    }

    public String getFormat() {
        return "X.509";
    }

    public byte[] getEncoded() {
        return KeyUtil.a(new AlgorithmIdentifier(PQCObjectIdentifiers.a, DERNull.c), new RainbowPublicKey(this.docLength, this.coeffquadratic, this.coeffsingular, this.coeffscalar));
    }
}
