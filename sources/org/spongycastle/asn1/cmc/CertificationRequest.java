package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CertificationRequest extends ASN1Object {
    private static final ASN1Integer c = new ASN1Integer(0);
    private final CertificationRequestInfo d;
    private final AlgorithmIdentifier f;
    private final DERBitString q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        return new DERSequence(v);
    }

    public class CertificationRequestInfo extends ASN1Object {
        private final ASN1Integer c;
        private final X500Name d;
        private final ASN1Sequence f;
        private final ASN1Set q;

        public ASN1Primitive toASN1Primitive() {
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.a(this.c);
            v.a(this.d);
            v.a(this.f);
            v.a(new DERTaggedObject(false, 0, this.q));
            return new DERSequence(v);
        }
    }
}
