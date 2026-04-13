package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class ProfessionInfo extends ASN1Object {
    public static final ASN1ObjectIdentifier A4;
    public static final ASN1ObjectIdentifier B4;
    public static final ASN1ObjectIdentifier C4;
    public static final ASN1ObjectIdentifier D4;
    public static final ASN1ObjectIdentifier a1;
    public static final ASN1ObjectIdentifier a2;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier d;
    public static final ASN1ObjectIdentifier f;
    public static final ASN1ObjectIdentifier p0;
    public static final ASN1ObjectIdentifier p1;
    public static final ASN1ObjectIdentifier p2;
    public static final ASN1ObjectIdentifier p3;
    public static final ASN1ObjectIdentifier p4;
    public static final ASN1ObjectIdentifier q;
    public static final ASN1ObjectIdentifier x;
    public static final ASN1ObjectIdentifier y;
    public static final ASN1ObjectIdentifier z;
    public static final ASN1ObjectIdentifier z4;
    private NamingAuthority E4;
    private ASN1Sequence F4;
    private ASN1Sequence G4;
    private String H4;
    private ASN1OctetString I4;

    static {
        StringBuilder sb = new StringBuilder();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NamingAuthority.c;
        sb.append(aSN1ObjectIdentifier);
        sb.append(".1");
        c = new ASN1ObjectIdentifier(sb.toString());
        d = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".2");
        f = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".3");
        q = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".4");
        x = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".5");
        y = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".6");
        z = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".7");
        p0 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".8");
        a1 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".9");
        p1 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".10");
        a2 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".11");
        p2 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".12");
        p3 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".13");
        p4 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".14");
        z4 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".15");
        A4 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".16");
        B4 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".17");
        C4 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".18");
        D4 = new ASN1ObjectIdentifier(aSN1ObjectIdentifier + ".19");
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        if (this.E4 != null) {
            vec.a(new DERTaggedObject(true, 0, this.E4));
        }
        vec.a(this.F4);
        ASN1Sequence aSN1Sequence = this.G4;
        if (aSN1Sequence != null) {
            vec.a(aSN1Sequence);
        }
        String str = this.H4;
        if (str != null) {
            vec.a(new DERPrintableString(str, true));
        }
        ASN1OctetString aSN1OctetString = this.I4;
        if (aSN1OctetString != null) {
            vec.a(aSN1OctetString);
        }
        return new DERSequence(vec);
    }
}
