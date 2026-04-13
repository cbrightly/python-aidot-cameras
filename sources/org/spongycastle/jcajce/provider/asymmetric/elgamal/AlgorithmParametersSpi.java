package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public class AlgorithmParametersSpi extends BaseAlgorithmParameters {
    ElGamalParameterSpec a;

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        try {
            return new ElGamalParameter(this.a.b(), this.a.a()).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException("Error encoding ElGamalParameters");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String format) {
        if (isASN1FormatString(format) || format.equalsIgnoreCase("X.509")) {
            return engineGetEncoded();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec) {
        if (paramSpec == ElGamalParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) {
            return this.a;
        }
        if (paramSpec == DHParameterSpec.class) {
            return new DHParameterSpec(this.a.b(), this.a.a());
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec paramSpec) {
        if (!(paramSpec instanceof ElGamalParameterSpec) && !(paramSpec instanceof DHParameterSpec)) {
            throw new InvalidParameterSpecException("DHParameterSpec required to initialise a ElGamal algorithm parameters object");
        } else if (paramSpec instanceof ElGamalParameterSpec) {
            this.a = (ElGamalParameterSpec) paramSpec;
        } else {
            DHParameterSpec s = (DHParameterSpec) paramSpec;
            this.a = new ElGamalParameterSpec(s.getP(), s.getG());
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] params) {
        try {
            ElGamalParameter elP = ElGamalParameter.f(ASN1Primitive.h(params));
            this.a = new ElGamalParameterSpec(elP.g(), elP.e());
        } catch (ClassCastException e) {
            throw new IOException("Not a valid ElGamal Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new IOException("Not a valid ElGamal Parameter encoding.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] params, String format) {
        if (isASN1FormatString(format) || format.equalsIgnoreCase("X.509")) {
            engineInit(params);
            return;
        }
        throw new IOException("Unknown parameter format " + format);
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "ElGamal Parameters";
    }
}
