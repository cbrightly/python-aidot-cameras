package org.spongycastle.asn1;

import java.math.BigInteger;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Properties;

public class ASN1Enumerated extends ASN1Primitive {
    private static ASN1Enumerated[] c = new ASN1Enumerated[12];
    private final byte[] d;

    public static ASN1Enumerated p(Object obj) {
        if (obj == null || (obj instanceof ASN1Enumerated)) {
            return (ASN1Enumerated) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Enumerated) ASN1Primitive.h((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public ASN1Enumerated(int value) {
        this.d = BigInteger.valueOf((long) value).toByteArray();
    }

    public ASN1Enumerated(byte[] bytes) {
        if (Properties.d("org.spongycastle.asn1.allow_unsafe_integer") || !ASN1Integer.s(bytes)) {
            this.d = Arrays.h(bytes);
            return;
        }
        throw new IllegalArgumentException("malformed enumerated");
    }

    public BigInteger q() {
        return new BigInteger(this.d);
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return StreamUtil.a(this.d.length) + 1 + this.d.length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.g(10, this.d);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1Enumerated)) {
            return false;
        }
        return Arrays.b(this.d, ((ASN1Enumerated) o).d);
    }

    public int hashCode() {
        return Arrays.J(this.d);
    }

    static ASN1Enumerated o(byte[] enc) {
        if (enc.length > 1) {
            return new ASN1Enumerated(enc);
        }
        if (enc.length != 0) {
            int value = enc[0] & 255;
            ASN1Enumerated[] aSN1EnumeratedArr = c;
            if (value >= aSN1EnumeratedArr.length) {
                return new ASN1Enumerated(Arrays.h(enc));
            }
            ASN1Enumerated possibleMatch = aSN1EnumeratedArr[value];
            if (possibleMatch != null) {
                return possibleMatch;
            }
            ASN1Enumerated possibleMatch2 = new ASN1Enumerated(Arrays.h(enc));
            aSN1EnumeratedArr[value] = possibleMatch2;
            return possibleMatch2;
        }
        throw new IllegalArgumentException("ENUMERATED has zero length");
    }
}
