package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class RoleSyntax extends ASN1Object {
    private GeneralNames c;
    private GeneralName d;

    public String f() {
        return ((ASN1String) this.d.h()).a();
    }

    public String[] e() {
        GeneralNames generalNames = this.c;
        if (generalNames == null) {
            return new String[0];
        }
        GeneralName[] names = generalNames.g();
        String[] namesString = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            ASN1Encodable value = names[i].h();
            if (value instanceof ASN1String) {
                namesString[i] = ((ASN1String) value).a();
            } else {
                namesString[i] = value.toString();
            }
        }
        return namesString;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(false, 0, this.c));
        }
        v.a(new DERTaggedObject(true, 1, this.d));
        return new DERSequence(v);
    }

    public String toString() {
        StringBuffer buff = new StringBuffer("Name: " + f() + " - Auth: ");
        GeneralNames generalNames = this.c;
        if (generalNames == null || generalNames.g().length == 0) {
            buff.append("N/A");
        } else {
            String[] names = e();
            buff.append('[');
            buff.append(names[0]);
            for (int i = 1; i < names.length; i++) {
                buff.append(", ");
                buff.append(names[i]);
            }
            buff.append(']');
        }
        return buff.toString();
    }
}
