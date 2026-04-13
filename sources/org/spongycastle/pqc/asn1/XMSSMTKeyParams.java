package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class XMSSMTKeyParams extends ASN1Object {
    private final int height;
    private final int layers;
    private final AlgorithmIdentifier treeDigest;
    private final ASN1Integer version;

    public XMSSMTKeyParams(int height2, int layers2, AlgorithmIdentifier treeDigest2) {
        this.version = new ASN1Integer(0);
        this.height = height2;
        this.layers = layers2;
        this.treeDigest = treeDigest2;
    }

    private XMSSMTKeyParams(ASN1Sequence sequence) {
        this.version = ASN1Integer.o(sequence.r(0));
        this.height = ASN1Integer.o(sequence.r(1)).r().intValue();
        this.layers = ASN1Integer.o(sequence.r(2)).r().intValue();
        this.treeDigest = AlgorithmIdentifier.f(sequence.r(3));
    }

    public static XMSSMTKeyParams getInstance(Object o) {
        if (o instanceof XMSSMTKeyParams) {
            return (XMSSMTKeyParams) o;
        }
        if (o != null) {
            return new XMSSMTKeyParams(ASN1Sequence.o(o));
        }
        return null;
    }

    public int getHeight() {
        return this.height;
    }

    public int getLayers() {
        return this.layers;
    }

    public AlgorithmIdentifier getTreeDigest() {
        return this.treeDigest;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.version);
        v.a(new ASN1Integer((long) this.height));
        v.a(new ASN1Integer((long) this.layers));
        v.a(this.treeDigest);
        return new DERSequence(v);
    }
}
