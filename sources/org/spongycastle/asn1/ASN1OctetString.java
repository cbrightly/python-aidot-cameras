package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public abstract class ASN1OctetString extends ASN1Primitive implements ASN1OctetStringParser {
    byte[] c;

    public static ASN1OctetString p(ASN1TaggedObject obj, boolean explicit) {
        ASN1Primitive o = obj.q();
        if (explicit || (o instanceof ASN1OctetString)) {
            return o(o);
        }
        return BEROctetString.s(ASN1Sequence.o(o));
    }

    public static ASN1OctetString o(Object obj) {
        if (obj == null || (obj instanceof ASN1OctetString)) {
            return (ASN1OctetString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return o(ASN1Primitive.h((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e.getMessage());
            }
        } else {
            if (obj instanceof ASN1Encodable) {
                ASN1Primitive primitive = ((ASN1Encodable) obj).toASN1Primitive();
                if (primitive instanceof ASN1OctetString) {
                    return (ASN1OctetString) primitive;
                }
            }
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public ASN1OctetString(byte[] string) {
        if (string != null) {
            this.c = string;
            return;
        }
        throw new NullPointerException("string cannot be null");
    }

    public InputStream b() {
        return new ByteArrayInputStream(this.c);
    }

    public byte[] q() {
        return this.c;
    }

    public int hashCode() {
        return Arrays.J(q());
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1OctetString)) {
            return false;
        }
        return Arrays.b(this.c, ((ASN1OctetString) o).c);
    }

    public ASN1Primitive d() {
        return toASN1Primitive();
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive k() {
        return new DEROctetString(this.c);
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive n() {
        return new DEROctetString(this.c);
    }

    public String toString() {
        return "#" + Strings.b(Hex.b(this.c));
    }
}
