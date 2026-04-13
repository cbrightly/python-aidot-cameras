package org.spongycastle.pqc.jcajce.provider.rainbow;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Arrays;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.RainbowPrivateKey;
import org.spongycastle.pqc.crypto.rainbow.Layer;
import org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.spongycastle.pqc.jcajce.spec.RainbowPrivateKeySpec;

public class BCRainbowPrivateKey implements PrivateKey {
    private static final long serialVersionUID = 1;
    private short[][] A1inv;
    private short[][] A2inv;
    private short[] b1;
    private short[] b2;
    private Layer[] layers;
    private int[] vi;

    public BCRainbowPrivateKey(short[][] A1inv2, short[] b12, short[][] A2inv2, short[] b22, int[] vi2, Layer[] layers2) {
        this.A1inv = A1inv2;
        this.b1 = b12;
        this.A2inv = A2inv2;
        this.b2 = b22;
        this.vi = vi2;
        this.layers = layers2;
    }

    public BCRainbowPrivateKey(RainbowPrivateKeySpec keySpec) {
        this(keySpec.c(), keySpec.a(), keySpec.d(), keySpec.b(), keySpec.f(), keySpec.e());
    }

    public BCRainbowPrivateKey(RainbowPrivateKeyParameters params) {
        this(params.e(), params.c(), params.f(), params.d(), params.h(), params.g());
    }

    public short[][] getInvA1() {
        return this.A1inv;
    }

    public short[] getB1() {
        return this.b1;
    }

    public short[] getB2() {
        return this.b2;
    }

    public short[][] getInvA2() {
        return this.A2inv;
    }

    public Layer[] getLayers() {
        return this.layers;
    }

    public int[] getVi() {
        return this.vi;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof BCRainbowPrivateKey)) {
            return false;
        }
        BCRainbowPrivateKey otherKey = (BCRainbowPrivateKey) other;
        boolean eq = ((((1 != 0 && RainbowUtil.j(this.A1inv, otherKey.getInvA1())) && RainbowUtil.j(this.A2inv, otherKey.getInvA2())) && RainbowUtil.i(this.b1, otherKey.getB1())) && RainbowUtil.i(this.b2, otherKey.getB2())) && Arrays.equals(this.vi, otherKey.getVi());
        if (this.layers.length != otherKey.getLayers().length) {
            return false;
        }
        for (int i = this.layers.length - 1; i >= 0; i--) {
            eq &= this.layers[i].equals(otherKey.getLayers()[i]);
        }
        return eq;
    }

    public int hashCode() {
        int hash = (((((((((this.layers.length * 37) + org.spongycastle.util.Arrays.Q(this.A1inv)) * 37) + org.spongycastle.util.Arrays.P(this.b1)) * 37) + org.spongycastle.util.Arrays.Q(this.A2inv)) * 37) + org.spongycastle.util.Arrays.P(this.b2)) * 37) + org.spongycastle.util.Arrays.L(this.vi);
        for (int i = this.layers.length - 1; i >= 0; i--) {
            hash = (hash * 37) + this.layers[i].hashCode();
        }
        return hash;
    }

    public final String getAlgorithm() {
        return "Rainbow";
    }

    public byte[] getEncoded() {
        try {
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.a, DERNull.c), new RainbowPrivateKey(this.A1inv, this.b1, this.A2inv, this.b2, this.vi, this.layers)).getEncoded();
            } catch (IOException e) {
                return null;
            }
        } catch (IOException e2) {
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }
}
