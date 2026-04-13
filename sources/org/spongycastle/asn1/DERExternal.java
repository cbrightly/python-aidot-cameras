package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;

public class DERExternal extends ASN1Primitive {
    private ASN1ObjectIdentifier c;
    private ASN1Integer d;
    private ASN1Primitive f;
    private int q;
    private ASN1Primitive x;

    public DERExternal(ASN1EncodableVector vector) {
        int offset = 0;
        ASN1Primitive enc = t(vector, 0);
        if (enc instanceof ASN1ObjectIdentifier) {
            this.c = (ASN1ObjectIdentifier) enc;
            offset = 0 + 1;
            enc = t(vector, offset);
        }
        if (enc instanceof ASN1Integer) {
            this.d = (ASN1Integer) enc;
            offset++;
            enc = t(vector, offset);
        }
        if (!(enc instanceof ASN1TaggedObject)) {
            this.f = enc;
            offset++;
            enc = t(vector, offset);
        }
        if (vector.c() != offset + 1) {
            throw new IllegalArgumentException("input vector too large");
        } else if (enc instanceof ASN1TaggedObject) {
            ASN1TaggedObject obj = (ASN1TaggedObject) enc;
            u(obj.r());
            this.x = obj.q();
        } else {
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        }
    }

    private ASN1Primitive t(ASN1EncodableVector v, int index) {
        if (v.c() > index) {
            return v.b(index).toASN1Primitive();
        }
        throw new IllegalArgumentException("too few objects in input vector");
    }

    public int hashCode() {
        int ret = 0;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.c;
        if (aSN1ObjectIdentifier != null) {
            ret = aSN1ObjectIdentifier.hashCode();
        }
        ASN1Integer aSN1Integer = this.d;
        if (aSN1Integer != null) {
            ret ^= aSN1Integer.hashCode();
        }
        ASN1Primitive aSN1Primitive = this.f;
        if (aSN1Primitive != null) {
            ret ^= aSN1Primitive.hashCode();
        }
        return ret ^ this.x.hashCode();
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return getEncoded().length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.c;
        if (aSN1ObjectIdentifier != null) {
            baos.write(aSN1ObjectIdentifier.getEncoded("DER"));
        }
        ASN1Integer aSN1Integer = this.d;
        if (aSN1Integer != null) {
            baos.write(aSN1Integer.getEncoded("DER"));
        }
        ASN1Primitive aSN1Primitive = this.f;
        if (aSN1Primitive != null) {
            baos.write(aSN1Primitive.getEncoded("DER"));
        }
        baos.write(new DERTaggedObject(true, this.q, this.x).getEncoded("DER"));
        out.f(32, 8, baos.toByteArray());
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        ASN1Primitive aSN1Primitive;
        ASN1Integer aSN1Integer;
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        if (!(o instanceof DERExternal)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        DERExternal other = (DERExternal) o;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = this.c;
        if (aSN1ObjectIdentifier2 != null && ((aSN1ObjectIdentifier = other.c) == null || !aSN1ObjectIdentifier.equals(aSN1ObjectIdentifier2))) {
            return false;
        }
        ASN1Integer aSN1Integer2 = this.d;
        if (aSN1Integer2 != null && ((aSN1Integer = other.d) == null || !aSN1Integer.equals(aSN1Integer2))) {
            return false;
        }
        ASN1Primitive aSN1Primitive2 = this.f;
        if (aSN1Primitive2 == null || ((aSN1Primitive = other.f) != null && aSN1Primitive.equals(aSN1Primitive2))) {
            return this.x.equals(other.x);
        }
        return false;
    }

    public ASN1Primitive o() {
        return this.f;
    }

    public ASN1ObjectIdentifier p() {
        return this.c;
    }

    public int q() {
        return this.q;
    }

    public ASN1Primitive r() {
        return this.x;
    }

    public ASN1Integer s() {
        return this.d;
    }

    private void u(int encoding) {
        if (encoding < 0 || encoding > 2) {
            throw new IllegalArgumentException("invalid encoding value: " + encoding);
        }
        this.q = encoding;
    }
}
