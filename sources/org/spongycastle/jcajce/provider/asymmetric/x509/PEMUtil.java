package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.util.encoders.Base64;

public class PEMUtil {
    private final String a;
    private final String b;
    private final String c = "-----BEGIN PKCS7-----";
    private final String d;
    private final String e;
    private final String f;

    PEMUtil(String type) {
        this.a = "-----BEGIN " + type + "-----";
        this.b = "-----BEGIN X509 " + type + "-----";
        this.d = "-----END " + type + "-----";
        this.e = "-----END X509 " + type + "-----";
        this.f = "-----END PKCS7-----";
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0023 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(java.io.InputStream r6) {
        /*
            r5 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            int r1 = r6.read()
            r2 = r1
            r3 = 10
            r4 = 13
            if (r1 == r4) goto L_0x0019
            if (r2 == r3) goto L_0x0019
            if (r2 < 0) goto L_0x0019
            char r1 = (char) r2
            r0.append(r1)
            goto L_0x0005
        L_0x0019:
            if (r2 < 0) goto L_0x0021
            int r1 = r0.length()
            if (r1 == 0) goto L_0x0005
        L_0x0021:
            if (r2 >= 0) goto L_0x0025
            r1 = 0
            return r1
        L_0x0025:
            if (r2 != r4) goto L_0x003a
            r1 = 1
            r6.mark(r1)
            int r4 = r6.read()
            r2 = r4
            if (r4 != r3) goto L_0x0035
            r6.mark(r1)
        L_0x0035:
            if (r2 <= 0) goto L_0x003a
            r6.reset()
        L_0x003a:
            java.lang.String r1 = r0.toString()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.asymmetric.x509.PEMUtil.a(java.io.InputStream):java.lang.String");
    }

    /* access modifiers changed from: package-private */
    public ASN1Sequence b(InputStream in) {
        String line;
        StringBuffer pemBuf = new StringBuffer();
        do {
            String a2 = a(in);
            line = a2;
            if (a2 == null || line.startsWith(this.a) || line.startsWith(this.b) || line.startsWith(this.c)) {
            }
            String a22 = a(in);
            line = a22;
            break;
        } while (line.startsWith(this.c));
        while (true) {
            String a3 = a(in);
            String line2 = a3;
            if (a3 != null && !line2.startsWith(this.d) && !line2.startsWith(this.e) && !line2.startsWith(this.f)) {
                pemBuf.append(line2);
            }
        }
        if (pemBuf.length() == 0) {
            return null;
        }
        try {
            return ASN1Sequence.o(Base64.a(pemBuf.toString()));
        } catch (Exception e2) {
            throw new IOException("malformed PEM data encountered");
        }
    }
}
