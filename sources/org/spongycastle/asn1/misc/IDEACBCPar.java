package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class IDEACBCPar extends ASN1Object {
    ASN1OctetString c;

    public IDEACBCPar(byte[] iv) {
        this.c = new DEROctetString(iv);
    }

    public IDEACBCPar(ASN1Sequence seq) {
        if (seq.size() == 1) {
            this.c = (ASN1OctetString) seq.r(0);
        } else {
            this.c = null;
        }
    }

    public byte[] getIV() {
        ASN1OctetString aSN1OctetString = this.c;
        if (aSN1OctetString != null) {
            return Arrays.h(aSN1OctetString.q());
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1OctetString aSN1OctetString = this.c;
        if (aSN1OctetString != null) {
            v.a(aSN1OctetString);
        }
        return new DERSequence(v);
    }
}
