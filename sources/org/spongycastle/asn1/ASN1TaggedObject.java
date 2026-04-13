package org.spongycastle.asn1;

import com.meituan.robust.Constants;
import java.io.IOException;

public abstract class ASN1TaggedObject extends ASN1Primitive implements ASN1TaggedObjectParser {
    int c;
    boolean d = false;
    boolean f = true;
    ASN1Encodable q = null;

    public static ASN1TaggedObject p(ASN1TaggedObject obj, boolean explicit) {
        if (explicit) {
            return (ASN1TaggedObject) obj.q();
        }
        throw new IllegalArgumentException("implicitly tagged tagged object");
    }

    public static ASN1TaggedObject o(Object obj) {
        if (obj == null || (obj instanceof ASN1TaggedObject)) {
            return (ASN1TaggedObject) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return o(ASN1Primitive.h((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct tagged object from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public ASN1TaggedObject(boolean explicit, int tagNo, ASN1Encodable obj) {
        if (obj instanceof ASN1Choice) {
            this.f = true;
        } else {
            this.f = explicit;
        }
        this.c = tagNo;
        if (this.f) {
            this.q = obj;
            return;
        }
        if (obj.toASN1Primitive() instanceof ASN1Set) {
        }
        this.q = obj;
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1TaggedObject)) {
            return false;
        }
        ASN1TaggedObject other = (ASN1TaggedObject) o;
        if (this.c != other.c || this.d != other.d || this.f != other.f) {
            return false;
        }
        ASN1Encodable aSN1Encodable = this.q;
        if (aSN1Encodable == null) {
            if (other.q != null) {
                return false;
            }
            return true;
        } else if (!aSN1Encodable.toASN1Primitive().equals(other.q.toASN1Primitive())) {
            return false;
        } else {
            return true;
        }
    }

    public int hashCode() {
        int code = this.c;
        ASN1Encodable aSN1Encodable = this.q;
        if (aSN1Encodable != null) {
            return code ^ aSN1Encodable.hashCode();
        }
        return code;
    }

    public int r() {
        return this.c;
    }

    public boolean s() {
        return this.f;
    }

    public boolean isEmpty() {
        return this.d;
    }

    public ASN1Primitive q() {
        ASN1Encodable aSN1Encodable = this.q;
        if (aSN1Encodable != null) {
            return aSN1Encodable.toASN1Primitive();
        }
        return null;
    }

    public ASN1Primitive d() {
        return toASN1Primitive();
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive k() {
        return new DERTaggedObject(this.f, this.c, this.q);
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive n() {
        return new DLTaggedObject(this.f, this.c, this.q);
    }

    public String toString() {
        return Constants.ARRAY_TYPE + this.c + "]" + this.q;
    }
}
