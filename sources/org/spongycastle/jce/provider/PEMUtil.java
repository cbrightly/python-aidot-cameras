package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.util.encoders.Base64;

public class PEMUtil {
    private final String a;
    private final String b;
    private final String c;
    private final String d;

    PEMUtil(String type) {
        this.a = "-----BEGIN " + type + "-----";
        this.b = "-----BEGIN X509 " + type + "-----";
        this.c = "-----END " + type + "-----";
        this.d = "-----END X509 " + type + "-----";
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0026 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(java.io.InputStream r5) {
        /*
            r4 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            int r1 = r5.read()
            r2 = r1
            r3 = 13
            if (r1 == r3) goto L_0x001c
            r1 = 10
            if (r2 == r1) goto L_0x001c
            if (r2 < 0) goto L_0x001c
            if (r2 != r3) goto L_0x0017
            goto L_0x0005
        L_0x0017:
            char r1 = (char) r2
            r0.append(r1)
            goto L_0x0005
        L_0x001c:
            if (r2 < 0) goto L_0x0024
            int r1 = r0.length()
            if (r1 == 0) goto L_0x0005
        L_0x0024:
            if (r2 >= 0) goto L_0x0028
            r1 = 0
            return r1
        L_0x0028:
            java.lang.String r1 = r0.toString()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jce.provider.PEMUtil.a(java.io.InputStream):java.lang.String");
    }

    /* access modifiers changed from: package-private */
    public ASN1Sequence b(InputStream in) {
        String line;
        StringBuffer pemBuf = new StringBuffer();
        do {
            String a2 = a(in);
            line = a2;
            if (a2 == null || line.startsWith(this.a) || line.startsWith(this.b)) {
            }
            String a22 = a(in);
            line = a22;
            break;
        } while (line.startsWith(this.b));
        while (true) {
            String a3 = a(in);
            String line2 = a3;
            if (a3 != null && !line2.startsWith(this.c) && !line2.startsWith(this.d)) {
                pemBuf.append(line2);
            }
        }
        if (pemBuf.length() == 0) {
            return null;
        }
        ASN1Primitive o = new ASN1InputStream(Base64.a(pemBuf.toString())).r();
        if (o instanceof ASN1Sequence) {
            return (ASN1Sequence) o;
        }
        throw new IOException("malformed PEM data encountered");
    }
}
