package org.spongycastle.asn1;

public class DLBitString extends ASN1BitString {
    public DLBitString(byte[] data, int padBits) {
        super(data, padBits);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return StreamUtil.a(this.d.length + 1) + 1 + this.d.length + 1;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        byte[] string = this.d;
        byte[] bytes = new byte[(string.length + 1)];
        bytes[0] = (byte) t();
        System.arraycopy(string, 0, bytes, 1, bytes.length - 1);
        out.g(3, bytes);
    }
}
