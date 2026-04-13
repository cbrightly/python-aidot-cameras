package org.spongycastle.asn1;

import java.util.Enumeration;
import java.util.Vector;

public class BERConstructedOctetString extends BEROctetString {
    private Vector f;

    public byte[] q() {
        return this.c;
    }

    public Enumeration u() {
        Vector vector = this.f;
        if (vector == null) {
            return t().elements();
        }
        return vector.elements();
    }

    private Vector t() {
        int end;
        Vector vec = new Vector();
        int i = 0;
        while (true) {
            byte[] bArr = this.c;
            if (i >= bArr.length) {
                return vec;
            }
            if (i + 1000 > bArr.length) {
                end = bArr.length;
            } else {
                end = i + 1000;
            }
            byte[] nStr = new byte[(end - i)];
            System.arraycopy(bArr, i, nStr, 0, nStr.length);
            vec.addElement(new DEROctetString(nStr));
            i += 1000;
        }
    }
}
