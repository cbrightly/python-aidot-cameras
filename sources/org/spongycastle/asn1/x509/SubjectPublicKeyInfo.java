package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class SubjectPublicKeyInfo extends ASN1Object {
    private AlgorithmIdentifier c;
    private DERBitString d;

    public static SubjectPublicKeyInfo g(Object obj) {
        if (obj instanceof SubjectPublicKeyInfo) {
            return (SubjectPublicKeyInfo) obj;
        }
        if (obj != null) {
            return new SubjectPublicKeyInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier algId, ASN1Encodable publicKey) {
        this.d = new DERBitString(publicKey);
        this.c = algId;
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier algId, byte[] publicKey) {
        this.d = new DERBitString(publicKey);
        this.c = algId;
    }

    public SubjectPublicKeyInfo(ASN1Sequence seq) {
        if (seq.size() == 2) {
            Enumeration e = seq.s();
            this.c = AlgorithmIdentifier.f(e.nextElement());
            this.d = DERBitString.x(e.nextElement());
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public AlgorithmIdentifier e() {
        return this.c;
    }

    public AlgorithmIdentifier f() {
        return this.c;
    }

    public ASN1Primitive i() {
        return new ASN1InputStream(this.d.s()).r();
    }

    public DERBitString h() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
