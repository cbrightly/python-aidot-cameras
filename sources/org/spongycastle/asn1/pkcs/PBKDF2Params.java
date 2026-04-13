package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;

public class PBKDF2Params extends ASN1Object {
    private static final AlgorithmIdentifier algid_hmacWithSHA1 = new AlgorithmIdentifier(PKCSObjectIdentifiers.u0, DERNull.c);
    private final ASN1Integer iterationCount;
    private final ASN1Integer keyLength;
    private final ASN1OctetString octStr;
    private final AlgorithmIdentifier prf;

    public static PBKDF2Params getInstance(Object obj) {
        if (obj instanceof PBKDF2Params) {
            return (PBKDF2Params) obj;
        }
        if (obj != null) {
            return new PBKDF2Params(ASN1Sequence.o(obj));
        }
        return null;
    }

    public PBKDF2Params(byte[] salt, int iterationCount2) {
        this(salt, iterationCount2, 0);
    }

    public PBKDF2Params(byte[] salt, int iterationCount2, int keyLength2) {
        this(salt, iterationCount2, keyLength2, (AlgorithmIdentifier) null);
    }

    public PBKDF2Params(byte[] salt, int iterationCount2, int keyLength2, AlgorithmIdentifier prf2) {
        this.octStr = new DEROctetString(Arrays.h(salt));
        this.iterationCount = new ASN1Integer((long) iterationCount2);
        if (keyLength2 > 0) {
            this.keyLength = new ASN1Integer((long) keyLength2);
        } else {
            this.keyLength = null;
        }
        this.prf = prf2;
    }

    public PBKDF2Params(byte[] salt, int iterationCount2, AlgorithmIdentifier prf2) {
        this(salt, iterationCount2, 0, prf2);
    }

    private PBKDF2Params(ASN1Sequence seq) {
        Enumeration e = seq.s();
        this.octStr = (ASN1OctetString) e.nextElement();
        this.iterationCount = (ASN1Integer) e.nextElement();
        if (e.hasMoreElements()) {
            Object o = e.nextElement();
            if (o instanceof ASN1Integer) {
                this.keyLength = ASN1Integer.o(o);
                if (e.hasMoreElements()) {
                    o = e.nextElement();
                } else {
                    o = null;
                }
            } else {
                this.keyLength = null;
            }
            if (o != null) {
                this.prf = AlgorithmIdentifier.f(o);
            } else {
                this.prf = null;
            }
        } else {
            this.keyLength = null;
            this.prf = null;
        }
    }

    public byte[] getSalt() {
        return this.octStr.q();
    }

    public BigInteger getIterationCount() {
        return this.iterationCount.r();
    }

    public BigInteger getKeyLength() {
        ASN1Integer aSN1Integer = this.keyLength;
        if (aSN1Integer != null) {
            return aSN1Integer.r();
        }
        return null;
    }

    public boolean isDefaultPrf() {
        AlgorithmIdentifier algorithmIdentifier = this.prf;
        return algorithmIdentifier == null || algorithmIdentifier.equals(algid_hmacWithSHA1);
    }

    public AlgorithmIdentifier getPrf() {
        AlgorithmIdentifier algorithmIdentifier = this.prf;
        if (algorithmIdentifier != null) {
            return algorithmIdentifier;
        }
        return algid_hmacWithSHA1;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.octStr);
        v.a(this.iterationCount);
        ASN1Integer aSN1Integer = this.keyLength;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        AlgorithmIdentifier algorithmIdentifier = this.prf;
        if (algorithmIdentifier != null && !algorithmIdentifier.equals(algid_hmacWithSHA1)) {
            v.a(this.prf);
        }
        return new DERSequence(v);
    }
}
