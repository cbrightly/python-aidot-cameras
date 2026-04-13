package org.spongycastle.asn1;

import org.spongycastle.util.Arrays;

public class DERBMPString extends ASN1Primitive implements ASN1String {
    private final char[] c;

    DERBMPString(char[] string) {
        this.c = string;
    }

    public DERBMPString(String string) {
        this.c = string.toCharArray();
    }

    public String a() {
        return new String(this.c);
    }

    public String toString() {
        return a();
    }

    public int hashCode() {
        return Arrays.K(this.c);
    }

    /* access modifiers changed from: protected */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof DERBMPString)) {
            return false;
        }
        return Arrays.c(this.c, ((DERBMPString) o).c);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return StreamUtil.a(this.c.length * 2) + 1 + (this.c.length * 2);
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.c(30);
        out.i(this.c.length * 2);
        int i = 0;
        while (true) {
            char[] cArr = this.c;
            if (i != cArr.length) {
                char c2 = cArr[i];
                out.c((byte) (c2 >> 8));
                out.c((byte) c2);
                i++;
            } else {
                return;
            }
        }
    }
}
