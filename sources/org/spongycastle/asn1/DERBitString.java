package org.spongycastle.asn1;

public class DERBitString extends ASN1BitString {
    public static DERBitString x(Object obj) {
        if (obj == null || (obj instanceof DERBitString)) {
            return (DERBitString) obj;
        }
        if (obj instanceof DLBitString) {
            return new DERBitString(((DLBitString) obj).d, ((DLBitString) obj).f);
        }
        if (obj instanceof byte[]) {
            try {
                return (DERBitString) ASN1Primitive.h((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static DERBitString y(ASN1TaggedObject obj, boolean explicit) {
        ASN1Primitive o = obj.q();
        if (explicit || (o instanceof DERBitString)) {
            return x(o);
        }
        return w(((ASN1OctetString) o).q());
    }

    public DERBitString(byte[] data, int padBits) {
        super(data, padBits);
    }

    public DERBitString(byte[] data) {
        this(data, 0);
    }

    public DERBitString(int value) {
        super(ASN1BitString.r(value), ASN1BitString.u(value));
    }

    public DERBitString(ASN1Encodable obj) {
        super(obj.toASN1Primitive().getEncoded("DER"), 0);
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
        byte[] string = ASN1BitString.o(this.d, this.f);
        byte[] bytes = new byte[(string.length + 1)];
        bytes[0] = (byte) t();
        System.arraycopy(string, 0, bytes, 1, bytes.length - 1);
        out.g(3, bytes);
    }

    static DERBitString w(byte[] bytes) {
        if (bytes.length >= 1) {
            byte padBits = bytes[0];
            byte[] data = new byte[(bytes.length - 1)];
            if (data.length != 0) {
                System.arraycopy(bytes, 1, data, 0, bytes.length - 1);
            }
            return new DERBitString(data, padBits);
        }
        throw new IllegalArgumentException("truncated BIT STRING detected");
    }
}
