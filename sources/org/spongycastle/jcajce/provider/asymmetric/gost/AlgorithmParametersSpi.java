package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    GOST3410ParameterSpec a;

    /* access modifiers changed from: protected */
    public boolean a(String format) {
        return format == null || format.equals("ASN.1");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec engineGetParameterSpec(Class paramSpec) {
        if (paramSpec != null) {
            return b(paramSpec);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        try {
            return new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.a.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.a.getDigestParamSetOID()), new ASN1ObjectIdentifier(this.a.getEncryptionParamSetOID())).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException("Error encoding GOST3410Parameters");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String format) {
        if (a(format) || format.equalsIgnoreCase("X.509")) {
            return engineGetEncoded();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec b(Class paramSpec) {
        if (paramSpec == GOST3410PublicKeyParameterSetSpec.class || paramSpec == AlgorithmParameterSpec.class) {
            return this.a;
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to GOST3410 parameters object.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec paramSpec) {
        if (paramSpec instanceof GOST3410ParameterSpec) {
            this.a = (GOST3410ParameterSpec) paramSpec;
            return;
        }
        throw new InvalidParameterSpecException("GOST3410ParameterSpec required to initialise a GOST3410 algorithm parameters object");
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] params) {
        try {
            this.a = GOST3410ParameterSpec.a(new GOST3410PublicKeyAlgParameters((ASN1Sequence) ASN1Primitive.h(params)));
        } catch (ClassCastException e) {
            throw new IOException("Not a valid GOST3410 Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new IOException("Not a valid GOST3410 Parameter encoding.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] params, String format) {
        if (a(format) || format.equalsIgnoreCase("X.509")) {
            engineInit(params);
            return;
        }
        throw new IOException("Unknown parameter format " + format);
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "GOST3410 Parameters";
    }
}
