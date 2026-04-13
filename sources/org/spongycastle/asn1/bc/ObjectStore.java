package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class ObjectStore extends ASN1Object {
    private final ASN1Encodable c;
    private final ObjectStoreIntegrityCheck d;

    public ObjectStore(EncryptedObjectStoreData encryptedObjectStoreData, ObjectStoreIntegrityCheck integrityCheck) {
        this.c = encryptedObjectStoreData;
        this.d = integrityCheck;
    }

    private ObjectStore(ASN1Sequence seq) {
        ASN1Encodable sData = seq.r(0);
        if (sData instanceof EncryptedObjectStoreData) {
            this.c = sData;
        } else if (sData instanceof ObjectStoreData) {
            this.c = sData;
        } else {
            ASN1Sequence seqData = ASN1Sequence.o(sData);
            if (seqData.size() == 2) {
                this.c = EncryptedObjectStoreData.g(seqData);
            } else {
                this.c = ObjectStoreData.f(seqData);
            }
        }
        this.d = ObjectStoreIntegrityCheck.e(seq.r(1));
    }

    public static ObjectStore e(Object o) {
        if (o instanceof ObjectStore) {
            return (ObjectStore) o;
        }
        if (o != null) {
            return new ObjectStore(ASN1Sequence.o(o));
        }
        return null;
    }

    public ObjectStoreIntegrityCheck f() {
        return this.d;
    }

    public ASN1Encodable g() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
