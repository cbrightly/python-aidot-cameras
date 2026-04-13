package org.spongycastle.asn1;

public class DEROctetString extends ASN1OctetString {
    public DEROctetString(byte[] string) {
        super(string);
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
        out.g(4, this.c);
    }

    static void r(DEROutputStream derOut, byte[] bytes) {
        derOut.g(4, bytes);
    }
}
