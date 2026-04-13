package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public class Certificate {
    public static final Certificate a = new Certificate(new org.spongycastle.asn1.x509.Certificate[0]);
    protected org.spongycastle.asn1.x509.Certificate[] b;

    public Certificate(org.spongycastle.asn1.x509.Certificate[] certificateList) {
        if (certificateList != null) {
            this.b = certificateList;
            return;
        }
        throw new IllegalArgumentException("'certificateList' cannot be null");
    }

    public org.spongycastle.asn1.x509.Certificate b(int index) {
        return this.b[index];
    }

    public boolean c() {
        return this.b.length == 0;
    }

    public void a(OutputStream output) {
        Vector derEncodings = new Vector(this.b.length);
        int totalLength = 0;
        int i = 0;
        while (true) {
            org.spongycastle.asn1.x509.Certificate[] certificateArr = this.b;
            if (i >= certificateArr.length) {
                break;
            }
            byte[] derEncoding = certificateArr[i].getEncoded("DER");
            derEncodings.addElement(derEncoding);
            totalLength += derEncoding.length + 3;
            i++;
        }
        TlsUtils.i(totalLength);
        TlsUtils.F0(totalLength, output);
        for (int i2 = 0; i2 < derEncodings.size(); i2++) {
            TlsUtils.B0((byte[]) derEncodings.elementAt(i2), output);
        }
    }

    public static Certificate d(InputStream input) {
        int totalLength = TlsUtils.n0(input);
        if (totalLength == 0) {
            return a;
        }
        ByteArrayInputStream buf = new ByteArrayInputStream(TlsUtils.f0(totalLength, input));
        Vector certificate_list = new Vector();
        while (buf.available() > 0) {
            certificate_list.addElement(org.spongycastle.asn1.x509.Certificate.f(TlsUtils.c0(TlsUtils.h0(buf))));
        }
        org.spongycastle.asn1.x509.Certificate[] certificateList = new org.spongycastle.asn1.x509.Certificate[certificate_list.size()];
        for (int i = 0; i < certificate_list.size(); i++) {
            certificateList[i] = (org.spongycastle.asn1.x509.Certificate) certificate_list.elementAt(i);
        }
        return new Certificate(certificateList);
    }
}
