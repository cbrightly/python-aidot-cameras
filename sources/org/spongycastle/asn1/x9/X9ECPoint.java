package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Arrays;

public class X9ECPoint extends ASN1Object {
    private final ASN1OctetString c;
    private ECCurve d;
    private ECPoint f;

    public X9ECPoint(ECPoint p) {
        this(p, false);
    }

    public X9ECPoint(ECPoint p, boolean compressed) {
        this.f = p.y();
        this.c = new DEROctetString(p.l(compressed));
    }

    public X9ECPoint(ECCurve c2, byte[] encoding) {
        this.d = c2;
        this.c = new DEROctetString(Arrays.h(encoding));
    }

    public X9ECPoint(ECCurve c2, ASN1OctetString s) {
        this(c2, s.q());
    }

    public synchronized ECPoint e() {
        if (this.f == null) {
            this.f = this.d.j(this.c.q()).y();
        }
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
