package org.spongycastle.asn1.eac;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;

public class CertificateBody extends ASN1Object {
    private DERApplicationSpecific c;
    private DERApplicationSpecific d;
    private PublicKeyDataObject f;
    private int p0;
    private DERApplicationSpecific q;
    private CertificateHolderAuthorization x;
    private DERApplicationSpecific y;
    private DERApplicationSpecific z;

    private ASN1Primitive e() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(new DERApplicationSpecific(false, 73, (ASN1Encodable) this.f));
        v.a(this.q);
        v.a(this.x);
        v.a(this.y);
        v.a(this.z);
        return new DERApplicationSpecific(78, v);
    }

    private ASN1Primitive f() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(new DERApplicationSpecific(false, 73, (ASN1Encodable) this.f));
        v.a(this.q);
        return new DERApplicationSpecific(78, v);
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            int i = this.p0;
            if (i == 127) {
                return e();
            }
            if (i == 13) {
                return f();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
