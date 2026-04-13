package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Arrays;

public class XMSSMTPrivateKey extends ASN1Object {
    private final int c;
    private final byte[] d;
    private final byte[] f;
    private final byte[] q;
    private final byte[] x;
    private final byte[] y;

    public XMSSMTPrivateKey(int index, byte[] secretKeySeed, byte[] secretKeyPRF, byte[] publicSeed, byte[] root, byte[] bdsState) {
        this.c = index;
        this.d = Arrays.h(secretKeySeed);
        this.f = Arrays.h(secretKeyPRF);
        this.q = Arrays.h(publicSeed);
        this.x = Arrays.h(root);
        this.y = Arrays.h(bdsState);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(0));
        ASN1EncodableVector vK = new ASN1EncodableVector();
        vK.a(new ASN1Integer((long) this.c));
        vK.a(new DEROctetString(this.d));
        vK.a(new DEROctetString(this.f));
        vK.a(new DEROctetString(this.q));
        vK.a(new DEROctetString(this.x));
        v.a(new DERSequence(vK));
        v.a(new DERTaggedObject(true, 0, new DEROctetString(this.y)));
        return new DERSequence(v);
    }
}
