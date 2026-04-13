package org.spongycastle.asn1.bc;

import java.util.Iterator;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Iterable;

public class ObjectDataSequence extends ASN1Object implements Iterable<ASN1Encodable> {
    private final ASN1Encodable[] c;

    public ObjectDataSequence(ObjectData[] dataSequence) {
        ASN1Encodable[] aSN1EncodableArr = new ASN1Encodable[dataSequence.length];
        this.c = aSN1EncodableArr;
        System.arraycopy(dataSequence, 0, aSN1EncodableArr, 0, dataSequence.length);
    }

    private ObjectDataSequence(ASN1Sequence seq) {
        this.c = new ASN1Encodable[seq.size()];
        int i = 0;
        while (true) {
            ASN1Encodable[] aSN1EncodableArr = this.c;
            if (i != aSN1EncodableArr.length) {
                aSN1EncodableArr[i] = ObjectData.h(seq.r(i));
                i++;
            } else {
                return;
            }
        }
    }

    public static ObjectDataSequence e(Object obj) {
        if (obj instanceof ObjectDataSequence) {
            return (ObjectDataSequence) obj;
        }
        if (obj != null) {
            return new ObjectDataSequence(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.c);
    }

    public Iterator<ASN1Encodable> iterator() {
        return new Arrays.Iterator(this.c);
    }
}
