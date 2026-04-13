package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.spongycastle.asn1.x500.DirectoryString;

public class NamingAuthority extends ASN1Object {
    public static final ASN1ObjectIdentifier c = new ASN1ObjectIdentifier(ISISMTTObjectIdentifiers.o + ".1");
    private ASN1ObjectIdentifier d;
    private String f;
    private DirectoryString q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.d;
        if (aSN1ObjectIdentifier != null) {
            vec.a(aSN1ObjectIdentifier);
        }
        String str = this.f;
        if (str != null) {
            vec.a(new DERIA5String(str, true));
        }
        DirectoryString directoryString = this.q;
        if (directoryString != null) {
            vec.a(directoryString);
        }
        return new DERSequence(vec);
    }
}
