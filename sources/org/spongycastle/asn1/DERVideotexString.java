package org.spongycastle.asn1;

import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERVideotexString extends ASN1Primitive implements ASN1String {
    private final byte[] c;

    public DERVideotexString(byte[] string) {
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
        out.g(21, this.c);
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERVideotexString)) {
            return false;
        }
        return Arrays.b(this.c, ((DERVideotexString) o).c);
    }

    public String a() {
        return Strings.b(this.c);
    }
}
