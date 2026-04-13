package org.apache.http.conn;

import java.io.InterruptedIOException;
import org.apache.http.l;

public class ConnectTimeoutException extends InterruptedIOException {
    private static final long serialVersionUID = -4816682903149535989L;
    private final l host;

    public ConnectTimeoutException() {
        this.host = null;
    }

    public ConnectTimeoutException(String message) {
        super(message);
        this.host = null;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ConnectTimeoutException(java.io.IOException r4, org.apache.http.l r5, java.net.InetAddress... r6) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Connect to "
            r0.append(r1)
            if (r5 == 0) goto L_0x0011
            java.lang.String r1 = r5.toHostString()
            goto L_0x0013
        L_0x0011:
            java.lang.String r1 = "remote host"
        L_0x0013:
            r0.append(r1)
            if (r6 == 0) goto L_0x0031
            int r1 = r6.length
            if (r1 <= 0) goto L_0x0031
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = " "
            r1.append(r2)
            java.util.List r2 = java.util.Arrays.asList(r6)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L_0x0033
        L_0x0031:
            java.lang.String r1 = ""
        L_0x0033:
            r0.append(r1)
            if (r4 == 0) goto L_0x0054
            java.lang.String r1 = r4.getMessage()
            if (r1 == 0) goto L_0x0054
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = " failed: "
            r1.append(r2)
            java.lang.String r2 = r4.getMessage()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L_0x0056
        L_0x0054:
            java.lang.String r1 = " timed out"
        L_0x0056:
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r3.<init>(r0)
            r3.host = r5
            r3.initCause(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.conn.ConnectTimeoutException.<init>(java.io.IOException, org.apache.http.l, java.net.InetAddress[]):void");
    }

    public l getHost() {
        return this.host;
    }
}
