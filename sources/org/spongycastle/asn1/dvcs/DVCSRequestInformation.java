package org.spongycastle.asn1.dvcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.PolicyInformation;

public class DVCSRequestInformation extends ASN1Object {
    private Extensions a1;
    private int c;
    private ServiceType d;
    private BigInteger f;
    private GeneralNames p0;
    private DVCSTime q;
    private GeneralNames x;
    private PolicyInformation y;
    private GeneralNames z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        int i = this.c;
        if (i != 1) {
            v.a(new ASN1Integer((long) i));
        }
        v.a(this.d);
        BigInteger bigInteger = this.f;
        if (bigInteger != null) {
            v.a(new ASN1Integer(bigInteger));
        }
        DVCSTime dVCSTime = this.q;
        if (dVCSTime != null) {
            v.a(dVCSTime);
        }
        int[] tags = {0, 1, 2, 3, 4};
        ASN1Encodable[] taggedObjects = {this.x, this.y, this.z, this.p0, this.a1};
        for (int i2 = 0; i2 < tags.length; i2++) {
            int tag = tags[i2];
            ASN1Encodable taggedObject = taggedObjects[i2];
            if (taggedObject != null) {
                v.a(new DERTaggedObject(false, tag, taggedObject));
            }
        }
        return new DERSequence(v);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("DVCSRequestInformation {\n");
        if (this.c != 1) {
            s.append("version: " + this.c + "\n");
        }
        s.append("service: " + this.d + "\n");
        if (this.f != null) {
            s.append("nonce: " + this.f + "\n");
        }
        if (this.q != null) {
            s.append("requestTime: " + this.q + "\n");
        }
        if (this.x != null) {
            s.append("requester: " + this.x + "\n");
        }
        if (this.y != null) {
            s.append("requestPolicy: " + this.y + "\n");
        }
        if (this.z != null) {
            s.append("dvcs: " + this.z + "\n");
        }
        if (this.p0 != null) {
            s.append("dataLocations: " + this.p0 + "\n");
        }
        if (this.a1 != null) {
            s.append("extensions: " + this.a1 + "\n");
        }
        s.append("}\n");
        return s.toString();
    }
}
