package org.spongycastle.asn1;

public class DERNull extends ASN1Null {
    public static final DERNull c = new DERNull();
    private static final byte[] d = new byte[0];

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return 2;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.g(5, d);
    }
}
