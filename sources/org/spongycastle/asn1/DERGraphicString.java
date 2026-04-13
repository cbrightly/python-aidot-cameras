package org.spongycastle.asn1;

import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERGraphicString extends ASN1Primitive implements ASN1String {
    private final byte[] c;

    public DERGraphicString(byte[] string) {
        this.c = Arrays.h(string);
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
        out.g(25, this.c);
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERGraphicString)) {
            return false;
        }
        return Arrays.b(this.c, ((DERGraphicString) o).c);
    }

    public String a() {
        return Strings.b(this.c);
    }
}
