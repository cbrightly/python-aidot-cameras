package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;

public class KeyUsage extends ASN1Object {
    private DERBitString c;

    public static KeyUsage g(Object obj) {
        if (obj instanceof KeyUsage) {
            return (KeyUsage) obj;
        }
        if (obj != null) {
            return new KeyUsage(DERBitString.x(obj));
        }
        return null;
    }

    public static KeyUsage e(Extensions extensions) {
        return g(extensions.f(Extension.f));
    }

    public KeyUsage(int usage) {
        this.c = new DERBitString(usage);
    }

    private KeyUsage(DERBitString bitString) {
        this.c = bitString;
    }

    public byte[] f() {
        return this.c.q();
    }

    public String toString() {
        byte[] data = this.c.q();
        if (data.length == 1) {
            return "KeyUsage: 0x" + Integer.toHexString(data[0] & 255);
        }
        return "KeyUsage: 0x" + Integer.toHexString((data[0] & 255) | ((data[1] & 255) << 8));
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
