package org.spongycastle.asn1.bc;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class ObjectStoreIntegrityCheck extends ASN1Object implements ASN1Choice {
    private final int c;
    private final ASN1Object d;

    public ObjectStoreIntegrityCheck(PbkdMacIntegrityCheck macIntegrityCheck) {
        this((ASN1Encodable) macIntegrityCheck);
    }

    private ObjectStoreIntegrityCheck(ASN1Encodable obj) {
        if ((obj instanceof ASN1Sequence) || (obj instanceof PbkdMacIntegrityCheck)) {
            this.c = 0;
            this.d = PbkdMacIntegrityCheck.e(obj);
            return;
        }
        throw new IllegalArgumentException("Unknown check object in integrity check.");
    }

    public static ObjectStoreIntegrityCheck e(Object o) {
        if (o instanceof ObjectStoreIntegrityCheck) {
            return (ObjectStoreIntegrityCheck) o;
        }
        if (o instanceof byte[]) {
            try {
                return new ObjectStoreIntegrityCheck((ASN1Encodable) ASN1Primitive.h((byte[]) o));
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to parse integrity check details.");
            }
        } else if (o != null) {
            return new ObjectStoreIntegrityCheck((ASN1Encodable) o);
        } else {
            return null;
        }
    }

    public int g() {
        return this.c;
    }

    public ASN1Object f() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.d.toASN1Primitive();
    }
}
