package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class RSAESOAEPparams extends ASN1Object {
    public static final AlgorithmIdentifier c;
    public static final AlgorithmIdentifier d;
    public static final AlgorithmIdentifier f = new AlgorithmIdentifier(PKCSObjectIdentifiers.S, new DEROctetString(new byte[0]));
    private AlgorithmIdentifier q;
    private AlgorithmIdentifier x;
    private AlgorithmIdentifier y;

    static {
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(OIWObjectIdentifiers.i, DERNull.c);
        c = algorithmIdentifier;
        d = new AlgorithmIdentifier(PKCSObjectIdentifiers.R, algorithmIdentifier);
    }

    public static RSAESOAEPparams f(Object obj) {
        if (obj instanceof RSAESOAEPparams) {
            return (RSAESOAEPparams) obj;
        }
        if (obj != null) {
            return new RSAESOAEPparams(ASN1Sequence.o(obj));
        }
        return null;
    }

    public RSAESOAEPparams() {
        this.q = c;
        this.x = d;
        this.y = f;
    }

    public RSAESOAEPparams(AlgorithmIdentifier hashAlgorithm, AlgorithmIdentifier maskGenAlgorithm, AlgorithmIdentifier pSourceAlgorithm) {
        this.q = hashAlgorithm;
        this.x = maskGenAlgorithm;
        this.y = pSourceAlgorithm;
    }

    public RSAESOAEPparams(ASN1Sequence seq) {
        this.q = c;
        this.x = d;
        this.y = f;
        for (int i = 0; i != seq.size(); i++) {
            ASN1TaggedObject o = (ASN1TaggedObject) seq.r(i);
            switch (o.r()) {
                case 0:
                    this.q = AlgorithmIdentifier.g(o, true);
                    break;
                case 1:
                    this.x = AlgorithmIdentifier.g(o, true);
                    break;
                case 2:
                    this.y = AlgorithmIdentifier.g(o, true);
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag");
            }
        }
    }

    public AlgorithmIdentifier e() {
        return this.q;
    }

    public AlgorithmIdentifier g() {
        return this.x;
    }

    public AlgorithmIdentifier h() {
        return this.y;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (!this.q.equals(c)) {
            v.a(new DERTaggedObject(true, 0, this.q));
        }
        if (!this.x.equals(d)) {
            v.a(new DERTaggedObject(true, 1, this.x));
        }
        if (!this.y.equals(f)) {
            v.a(new DERTaggedObject(true, 2, this.y));
        }
        return new DERSequence(v);
    }
}
