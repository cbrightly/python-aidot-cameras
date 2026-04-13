package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;

public class X9IntegerConverter {
    public int a(ECCurve c) {
        return (c.t() + 7) / 8;
    }

    public int b(ECFieldElement fe) {
        return (fe.f() + 7) / 8;
    }

    public byte[] c(BigInteger s, int qLength) {
        byte[] bytes = s.toByteArray();
        if (qLength < bytes.length) {
            byte[] tmp = new byte[qLength];
            System.arraycopy(bytes, bytes.length - tmp.length, tmp, 0, tmp.length);
            return tmp;
        } else if (qLength <= bytes.length) {
            return bytes;
        } else {
            byte[] tmp2 = new byte[qLength];
            System.arraycopy(bytes, 0, tmp2, tmp2.length - bytes.length, bytes.length);
            return tmp2;
        }
    }
}
