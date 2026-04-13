package org.spongycastle.asn1;

import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERUTF8String extends ASN1Primitive implements ASN1String {
    private final byte[] c;

    public static DERUTF8String o(Object obj) {
        if (obj == null || (obj instanceof DERUTF8String)) {
            return (DERUTF8String) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERUTF8String) ASN1Primitive.h((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    DERUTF8String(byte[] string) {
        this.c = string;
    }

    public DERUTF8String(String string) {
        this.c = Strings.j(string);
    }

    public String a() {
        return Strings.c(this.c);
    }

    public String toString() {
        return a();
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERUTF8String)) {
            return false;
        }
        return Arrays.b(this.c, ((DERUTF8String) o).c);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return StreamUtil.a(this.c.length) + 1 + this.c.length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.g(12, this.c);
    }
}
