package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.x500.X500Name;

public class CertificateRequest {
    protected short[] a;
    protected Vector b;
    protected Vector c;

    public CertificateRequest(short[] certificateTypes, Vector supportedSignatureAlgorithms, Vector certificateAuthorities) {
        this.a = certificateTypes;
        this.b = supportedSignatureAlgorithms;
        this.c = certificateAuthorities;
    }

    public short[] b() {
        return this.a;
    }

    public Vector c() {
        return this.b;
    }

    public void a(OutputStream output) {
        short[] sArr = this.a;
        if (sArr == null || sArr.length == 0) {
            TlsUtils.J0(0, output);
        } else {
            TlsUtils.P0(sArr, output);
        }
        Vector vector = this.b;
        if (vector != null) {
            TlsUtils.s(vector, false, output);
        }
        Vector vector2 = this.c;
        if (vector2 == null || vector2.isEmpty()) {
            TlsUtils.D0(0, output);
            return;
        }
        Vector derEncodings = new Vector(this.c.size());
        int totalLength = 0;
        for (int i = 0; i < this.c.size(); i++) {
            byte[] derEncoding = ((X500Name) this.c.elementAt(i)).getEncoded("DER");
            derEncodings.addElement(derEncoding);
            totalLength += derEncoding.length + 2;
        }
        TlsUtils.h(totalLength);
        TlsUtils.D0(totalLength, output);
        for (int i2 = 0; i2 < derEncodings.size(); i2++) {
            TlsUtils.A0((byte[]) derEncodings.elementAt(i2), output);
        }
    }

    public static CertificateRequest d(TlsContext context, InputStream input) {
        int numTypes = TlsUtils.q0(input);
        short[] certificateTypes = new short[numTypes];
        for (int i = 0; i < numTypes; i++) {
            certificateTypes[i] = TlsUtils.q0(input);
        }
        Vector supportedSignatureAlgorithms = null;
        if (TlsUtils.U(context)) {
            supportedSignatureAlgorithms = TlsUtils.b0(false, input);
        }
        Vector certificateAuthorities = new Vector();
        ByteArrayInputStream bis = new ByteArrayInputStream(TlsUtils.g0(input));
        while (bis.available() > 0) {
            certificateAuthorities.addElement(X500Name.e(TlsUtils.e0(TlsUtils.g0(bis))));
        }
        return new CertificateRequest(certificateTypes, supportedSignatureAlgorithms, certificateAuthorities);
    }
}
