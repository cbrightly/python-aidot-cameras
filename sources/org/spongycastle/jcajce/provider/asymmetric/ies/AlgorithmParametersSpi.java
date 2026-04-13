package org.spongycastle.jcajce.provider.asymmetric.ies;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.jce.spec.IESParameterSpec;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    IESParameterSpec a;

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
            ASN1EncodableVector v = new ASN1EncodableVector();
            if (this.a.b() != null) {
                v.a(new DERTaggedObject(false, 0, new DEROctetString(this.a.b())));
            }
            if (this.a.c() != null) {
                v.a(new DERTaggedObject(false, 1, new DEROctetString(this.a.c())));
            }
            v.a(new ASN1Integer((long) this.a.d()));
            if (this.a.e() != null) {
                ASN1EncodableVector cV = new ASN1EncodableVector();
                cV.a(new ASN1Integer((long) this.a.a()));
                cV.a(new ASN1Integer(this.a.e()));
                v.a(new DERSequence(cV));
            }
            return new DERSequence(v).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException("Error encoding IESParameters");
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
        if (paramSpec == IESParameterSpec.class || paramSpec == AlgorithmParameterSpec.class) {
            return this.a;
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec paramSpec) {
        if (paramSpec instanceof IESParameterSpec) {
            this.a = (IESParameterSpec) paramSpec;
            return;
        }
        throw new InvalidParameterSpecException("IESParameterSpec required to initialise a IES algorithm parameters object");
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] params) {
        try {
            ASN1Sequence s = (ASN1Sequence) ASN1Primitive.h(params);
            if (s.size() == 1) {
                this.a = new IESParameterSpec((byte[]) null, (byte[]) null, ASN1Integer.o(s.r(0)).r().intValue());
            } else if (s.size() == 2) {
                ASN1TaggedObject tagged = ASN1TaggedObject.o(s.r(0));
                if (tagged.r() == 0) {
                    this.a = new IESParameterSpec(ASN1OctetString.p(tagged, false).q(), (byte[]) null, ASN1Integer.o(s.r(1)).r().intValue());
                } else {
                    this.a = new IESParameterSpec((byte[]) null, ASN1OctetString.p(tagged, false).q(), ASN1Integer.o(s.r(1)).r().intValue());
                }
            } else if (s.size() == 3) {
                this.a = new IESParameterSpec(ASN1OctetString.p(ASN1TaggedObject.o(s.r(0)), false).q(), ASN1OctetString.p(ASN1TaggedObject.o(s.r(1)), false).q(), ASN1Integer.o(s.r(2)).r().intValue());
            } else if (s.size() == 4) {
                ASN1TaggedObject tagged1 = ASN1TaggedObject.o(s.r(0));
                ASN1TaggedObject tagged2 = ASN1TaggedObject.o(s.r(1));
                ASN1Sequence cipherDet = ASN1Sequence.o(s.r(3));
                this.a = new IESParameterSpec(ASN1OctetString.p(tagged1, false).q(), ASN1OctetString.p(tagged2, false).q(), ASN1Integer.o(s.r(2)).r().intValue(), ASN1Integer.o(cipherDet.r(0)).r().intValue(), ASN1OctetString.o(cipherDet.r(1)).q());
            }
        } catch (ClassCastException e) {
            throw new IOException("Not a valid IES Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new IOException("Not a valid IES Parameter encoding.");
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
        return "IES Parameters";
    }
}
