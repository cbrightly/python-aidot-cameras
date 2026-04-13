package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DLSequence;

public class ContentInfo extends ASN1Object implements PKCSObjectIdentifiers {
    private ASN1ObjectIdentifier c;
    private ASN1Encodable d;
    private boolean f = true;

    public static ContentInfo g(Object obj) {
        if (obj instanceof ContentInfo) {
            return (ContentInfo) obj;
        }
        if (obj != null) {
            return new ContentInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    private ContentInfo(ASN1Sequence seq) {
        Enumeration e = seq.s();
        this.c = (ASN1ObjectIdentifier) e.nextElement();
        if (e.hasMoreElements()) {
            this.d = ((ASN1TaggedObject) e.nextElement()).q();
        }
        this.f = seq instanceof BERSequence;
    }

    public ContentInfo(ASN1ObjectIdentifier contentType, ASN1Encodable content) {
        this.c = contentType;
        this.d = content;
    }

    public ASN1ObjectIdentifier f() {
        return this.c;
    }

    public ASN1Encodable e() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1Encodable aSN1Encodable = this.d;
        if (aSN1Encodable != null) {
            v.a(new BERTaggedObject(true, 0, aSN1Encodable));
        }
        if (this.f) {
            return new BERSequence(v);
        }
        return new DLSequence(v);
    }
}
