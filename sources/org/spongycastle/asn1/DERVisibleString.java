package org.spongycastle.asn1;

import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERVisibleString extends ASN1Primitive implements ASN1String {
    private final byte[] c;

    DERVisibleString(byte[] string) {
        this.c = string;
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
        out.g(26, this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERVisibleString)) {
            return false;
        }
        return Arrays.b(this.c, ((DERVisibleString) o).c);
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }
}
