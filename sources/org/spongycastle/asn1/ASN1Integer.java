package org.spongycastle.asn1;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Properties;

public class ASN1Integer extends ASN1Primitive {
    private final byte[] c;

    public static ASN1Integer o(Object obj) {
        if (obj == null || (obj instanceof ASN1Integer)) {
            return (ASN1Integer) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Integer) ASN1Primitive.h((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Integer p(ASN1TaggedObject obj, boolean explicit) {
        ASN1Primitive o = obj.q();
        if (explicit || (o instanceof ASN1Integer)) {
            return o(o);
        }
        return new ASN1Integer(ASN1OctetString.o(obj.q()).q());
    }

    public ASN1Integer(long value) {
        this.c = BigInteger.valueOf(value).toByteArray();
    }

    public ASN1Integer(BigInteger value) {
        this.c = value.toByteArray();
    }

    public ASN1Integer(byte[] bytes) {
        this(bytes, true);
    }

    ASN1Integer(byte[] bytes, boolean clone) {
        if (Properties.d("org.spongycastle.asn1.allow_unsafe_integer") || !s(bytes)) {
            this.c = clone ? Arrays.h(bytes) : bytes;
            return;
        }
        throw new IllegalArgumentException("malformed integer");
    }

    static boolean s(byte[] bytes) {
        if (bytes.length > 1) {
            if (bytes[0] == 0 && (bytes[1] & OTACommand.RESP_ID_VERSION) == 0) {
                return true;
            }
            return bytes[0] == -1 && (bytes[1] & OTACommand.RESP_ID_VERSION) != 0;
        }
    }

    public BigInteger r() {
        return new BigInteger(this.c);
    }

    public BigInteger q() {
        return new BigInteger(1, this.c);
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
        out.g(2, this.c);
    }

    public int hashCode() {
        int value = 0;
        int i = 0;
        while (true) {
            byte[] bArr = this.c;
            if (i == bArr.length) {
                return value;
            }
            value ^= (bArr[i] & 255) << (i % 4);
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1Integer)) {
            return false;
        }
        return Arrays.b(this.c, ((ASN1Integer) o).c);
    }

    public String toString() {
        return r().toString();
    }
}
