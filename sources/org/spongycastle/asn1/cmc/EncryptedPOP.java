package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedPOP extends ASN1Object {
    private final TaggedRequest c;
    private final ContentInfo d;
    private final AlgorithmIdentifier f;
    private final AlgorithmIdentifier q;
    private final byte[] x;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        v.a(new DEROctetString(this.x));
        return new DERSequence(v);
    }
}
