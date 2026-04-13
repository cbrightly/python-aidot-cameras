package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.DERBitString;

public class NetscapeCertType extends DERBitString {
    public NetscapeCertType(DERBitString usage) {
        super(usage.q(), usage.t());
    }

    public String toString() {
        return "NetscapeCertType: 0x" + Integer.toHexString(this.d[0] & 255);
    }
}
