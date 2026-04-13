package org.spongycastle.asn1.eac;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.util.Integers;

public class CertificateHolderAuthorization extends ASN1Object {
    public static final ASN1ObjectIdentifier c = EACObjectIdentifiers.a.o("3.1.2.1");
    static Hashtable d = new Hashtable();
    static BidirectionalMap f = new BidirectionalMap();
    static Hashtable q = new Hashtable();
    ASN1ObjectIdentifier x;
    DERApplicationSpecific y;

    static {
        d.put(Integers.b(2), "RADG4");
        d.put(Integers.b(1), "RADG3");
        f.put(Integers.b(Opcodes.CHECKCAST), "CVCA");
        f.put(Integers.b(128), "DV_DOMESTIC");
        f.put(Integers.b(64), "DV_FOREIGN");
        f.put(Integers.b(0), "IS");
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.x);
        v.a(this.y);
        return new DERApplicationSpecific(76, v);
    }
}
