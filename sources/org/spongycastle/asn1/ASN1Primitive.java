package org.spongycastle.asn1;

import java.io.IOException;

public abstract class ASN1Primitive extends ASN1Object {
    /* access modifiers changed from: package-private */
    public abstract boolean e(ASN1Primitive aSN1Primitive);

    /* access modifiers changed from: package-private */
    public abstract void f(ASN1OutputStream aSN1OutputStream);

    /* access modifiers changed from: package-private */
    public abstract int g();

    /* access modifiers changed from: package-private */
    public abstract boolean i();

    ASN1Primitive() {
    }

    public static ASN1Primitive h(byte[] data) {
        ASN1InputStream aIn = new ASN1InputStream(data);
        try {
            ASN1Primitive o = aIn.r();
            if (aIn.available() == 0) {
                return o;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException e) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ASN1Encodable) || !e(((ASN1Encodable) o).toASN1Primitive())) {
            return false;
        }
        return true;
    }

    public ASN1Primitive toASN1Primitive() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive k() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive n() {
        return this;
    }
}
