package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1String;

public class DirectoryString extends ASN1Object implements ASN1Choice, ASN1String {
    private ASN1String c;

    public String a() {
        return this.c.a();
    }

    public String toString() {
        return this.c.a();
    }

    public ASN1Primitive toASN1Primitive() {
        return ((ASN1Encodable) this.c).toASN1Primitive();
    }
}
