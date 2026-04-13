package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class XMSSKeyParams extends ASN1Object {
    private final int height;
    private final AlgorithmIdentifier treeDigest;
    private final ASN1Integer version;

    public XMSSKeyParams(int height2, AlgorithmIdentifier treeDigest2) {
        this.version = new ASN1Integer(0);
        this.height = height2;
        this.treeDigest = treeDigest2;
    }

    private XMSSKeyParams(ASN1Sequence sequence) {
        this.version = ASN1Integer.o(sequence.r(0));
        this.height = ASN1Integer.o(sequence.r(1)).r().intValue();
        this.treeDigest = AlgorithmIdentifier.f(sequence.r(2));
    }

    public static XMSSKeyParams getInstance(Object o) {
        if (o instanceof XMSSKeyParams) {
            return (XMSSKeyParams) o;
        }
        if (o != null) {
            return new XMSSKeyParams(ASN1Sequence.o(o));
        }
        return null;
    }

    public int getHeight() {
        return this.height;
    }

    public AlgorithmIdentifier getTreeDigest() {
        return this.treeDigest;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.version);
        v.a(new ASN1Integer((long) this.height));
        v.a(this.treeDigest);
        return new DERSequence(v);
    }
}
