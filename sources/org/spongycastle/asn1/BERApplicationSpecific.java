package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BERApplicationSpecific extends ASN1ApplicationSpecific {
    public BERApplicationSpecific(int tagNo, ASN1EncodableVector vec) {
        super(true, tagNo, u(vec));
    }

    private static byte[] u(ASN1EncodableVector vec) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int i = 0;
        while (i != vec.c()) {
            try {
                bOut.write(((ASN1Object) vec.b(i)).getEncoded("BER"));
                i++;
            } catch (IOException e) {
                throw new ASN1ParsingException("malformed object: " + e, e);
            }
        }
        return bOut.toByteArray();
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        int classBits = 64;
        if (this.c) {
            classBits = 64 | 32;
        }
        out.k(classBits, this.d);
        out.c(128);
        out.d(this.f);
        out.c(0);
        out.c(0);
    }
}
