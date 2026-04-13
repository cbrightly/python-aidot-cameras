package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.asn1.pkcs.DHParameter;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    DHParameterSpec a;

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
            return new DHParameter(this.a.getP(), this.a.getG(), this.a.getL()).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException("Error encoding DHParameters");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String format) {
        if (a(format)) {
            return engineGetEncoded();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec b(Class paramSpec) {
        if (paramSpec == DHParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) {
            return this.a;
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to DH parameters object.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec paramSpec) {
        if (paramSpec instanceof DHParameterSpec) {
            this.a = (DHParameterSpec) paramSpec;
            return;
        }
        throw new InvalidParameterSpecException("DHParameterSpec required to initialise a Diffie-Hellman algorithm parameters object");
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] params) {
        try {
            DHParameter dhP = DHParameter.f(params);
            if (dhP.g() != null) {
                this.a = new DHParameterSpec(dhP.h(), dhP.e(), dhP.g().intValue());
            } else {
                this.a = new DHParameterSpec(dhP.h(), dhP.e());
            }
        } catch (ClassCastException e) {
            throw new IOException("Not a valid DH Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new IOException("Not a valid DH Parameter encoding.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] params, String format) {
        if (a(format)) {
            engineInit(params);
            return;
        }
        throw new IOException("Unknown parameter format " + format);
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "Diffie-Hellman Parameters";
    }
}
