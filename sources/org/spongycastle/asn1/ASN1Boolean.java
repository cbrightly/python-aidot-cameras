package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;

public class ASN1Boolean extends ASN1Primitive {
    private static final byte[] c = {-1};
    private static final byte[] d = {0};
    public static final ASN1Boolean f = new ASN1Boolean(false);
    public static final ASN1Boolean q = new ASN1Boolean(true);
    private final byte[] x;

    public static ASN1Boolean p(Object obj) {
        if (obj == null || (obj instanceof ASN1Boolean)) {
            return (ASN1Boolean) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Boolean) ASN1Primitive.h((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct boolean from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Boolean r(boolean value) {
        return value ? q : f;
    }

    public static ASN1Boolean q(ASN1TaggedObject obj, boolean explicit) {
        ASN1Primitive o = obj.q();
        if (explicit || (o instanceof ASN1Boolean)) {
            return p(o);
        }
        return o(((ASN1OctetString) o).q());
    }

    ASN1Boolean(byte[] value) {
        if (value.length != 1) {
            throw new IllegalArgumentException("byte value should have 1 byte in it");
        } else if (value[0] == 0) {
            this.x = d;
        } else if ((value[0] & 255) == 255) {
            this.x = c;
        } else {
            this.x = Arrays.h(value);
        }
    }

    public ASN1Boolean(boolean value) {
        this.x = value ? c : d;
    }

    public boolean s() {
        return this.x[0] != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return 3;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.g(1, this.x);
    }

    /* access modifiers changed from: protected */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1Boolean) || this.x[0] != ((ASN1Boolean) o).x[0]) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.x[0];
    }

    public String toString() {
        return this.x[0] != 0 ? "TRUE" : "FALSE";
    }

    static ASN1Boolean o(byte[] value) {
        if (value.length != 1) {
            throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
        } else if (value[0] == 0) {
            return f;
        } else {
            if ((value[0] & 255) == 255) {
                return q;
            }
            return new ASN1Boolean(value);
        }
    }
}
