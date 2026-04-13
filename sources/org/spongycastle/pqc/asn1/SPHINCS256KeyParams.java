package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class SPHINCS256KeyParams extends ASN1Object {
    private final AlgorithmIdentifier treeDigest;
    private final ASN1Integer version;

    public SPHINCS256KeyParams(AlgorithmIdentifier treeDigest2) {
        this.version = new ASN1Integer(0);
        this.treeDigest = treeDigest2;
    }

    private SPHINCS256KeyParams(ASN1Sequence sequence) {
        this.version = ASN1Integer.o(sequence.r(0));
        this.treeDigest = AlgorithmIdentifier.f(sequence.r(1));
    }

    public static final SPHINCS256KeyParams getInstance(Object o) {
        if (o instanceof SPHINCS256KeyParams) {
            return (SPHINCS256KeyParams) o;
        }
        if (o != null) {
            return new SPHINCS256KeyParams(ASN1Sequence.o(o));
        }
        return null;
    }

    public AlgorithmIdentifier getTreeDigest() {
        return this.treeDigest;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.version);
        v.a(this.treeDigest);
        return new DERSequence(v);
    }
}
