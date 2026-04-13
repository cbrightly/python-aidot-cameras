package org.spongycastle.asn1;

import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERIA5String extends ASN1Primitive implements ASN1String {
    private final byte[] c;

    public static DERIA5String o(Object obj) {
        if (obj == null || (obj instanceof DERIA5String)) {
            return (DERIA5String) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERIA5String) ASN1Primitive.h((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static DERIA5String p(ASN1TaggedObject obj, boolean explicit) {
        ASN1Primitive o = obj.q();
        if (explicit || (o instanceof DERIA5String)) {
            return o(o);
        }
        return new DERIA5String(((ASN1OctetString) o).q());
    }

    DERIA5String(byte[] string) {
        this.c = string;
    }

    public DERIA5String(String string) {
        this(string, false);
    }

    public DERIA5String(String string, boolean validate) {
        if (string == null) {
            throw new NullPointerException("string cannot be null");
        } else if (!validate || q(string)) {
            this.c = Strings.f(string);
        } else {
            throw new IllegalArgumentException("string contains illegal characters");
        }
    }

    public String a() {
        return Strings.b(this.c);
    }

    public String toString() {
        return a();
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
        out.g(22, this.c);
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERIA5String)) {
            return false;
        }
        return Arrays.b(this.c, ((DERIA5String) o).c);
    }

    public static boolean q(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) > 127) {
                return false;
            }
        }
        return true;
    }
}
