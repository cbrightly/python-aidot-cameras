package org.apache.commons.logging;

import java.net.URL;
import java.security.PrivilegedAction;

/* compiled from: LogFactory */
public final class f implements PrivilegedAction {
    private final /* synthetic */ URL a;

    f(URL url) {
        this.a = url;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0055, code lost:
        r3.append("Unable to close stream for URL ");
        r3.append(r6.a);
        org.apache.commons.logging.h.y(r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object run() {
        /*
            r6 = this;
            java.lang.String r0 = "Unable to close stream for URL "
            r1 = 0
            java.net.URL r2 = r6.a     // Catch:{ IOException -> 0x0067 }
            java.net.URLConnection r2 = r2.openConnection()     // Catch:{ IOException -> 0x0067 }
            r3 = 0
            r2.setUseCaches(r3)     // Catch:{ IOException -> 0x0067 }
            java.io.InputStream r3 = r2.getInputStream()     // Catch:{ IOException -> 0x0067 }
            r1 = r3
            if (r1 == 0) goto L_0x0043
            java.util.Properties r3 = new java.util.Properties     // Catch:{ IOException -> 0x0067 }
            r3.<init>()     // Catch:{ IOException -> 0x0067 }
            r3.load(r1)     // Catch:{ IOException -> 0x0067 }
            r1.close()     // Catch:{ IOException -> 0x0067 }
            r1 = 0
            if (r1 == 0) goto L_0x0042
            r1.close()     // Catch:{ IOException -> 0x0027 }
            goto L_0x0042
        L_0x0027:
            r4 = move-exception
            boolean r5 = org.apache.commons.logging.h.w()
            if (r5 == 0) goto L_0x0042
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            r5.<init>()
            r5.append(r0)
            java.net.URL r0 = r6.a
            r5.append(r0)
            java.lang.String r0 = r5.toString()
            org.apache.commons.logging.h.y(r0)
        L_0x0042:
            return r3
        L_0x0043:
            if (r1 == 0) goto L_0x0097
            r1.close()     // Catch:{ IOException -> 0x0049 }
        L_0x0048:
            goto L_0x0097
        L_0x0049:
            r2 = move-exception
            boolean r3 = org.apache.commons.logging.h.w()
            if (r3 == 0) goto L_0x0048
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
        L_0x0055:
            r3.append(r0)
            java.net.URL r0 = r6.a
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            org.apache.commons.logging.h.y(r0)
            goto L_0x0048
        L_0x0065:
            r2 = move-exception
            goto L_0x0099
        L_0x0067:
            r2 = move-exception
            boolean r3 = org.apache.commons.logging.h.w()     // Catch:{ all -> 0x0065 }
            if (r3 == 0) goto L_0x0084
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ all -> 0x0065 }
            r3.<init>()     // Catch:{ all -> 0x0065 }
            java.lang.String r4 = "Unable to read URL "
            r3.append(r4)     // Catch:{ all -> 0x0065 }
            java.net.URL r4 = r6.a     // Catch:{ all -> 0x0065 }
            r3.append(r4)     // Catch:{ all -> 0x0065 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0065 }
            org.apache.commons.logging.h.y(r3)     // Catch:{ all -> 0x0065 }
        L_0x0084:
            if (r1 == 0) goto L_0x0097
            r1.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x0048
        L_0x008a:
            r2 = move-exception
            boolean r3 = org.apache.commons.logging.h.w()
            if (r3 == 0) goto L_0x0048
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            goto L_0x0055
        L_0x0097:
            r0 = 0
            return r0
        L_0x0099:
            if (r1 == 0) goto L_0x00ba
            r1.close()     // Catch:{ IOException -> 0x009f }
            goto L_0x00ba
        L_0x009f:
            r3 = move-exception
            boolean r4 = org.apache.commons.logging.h.w()
            if (r4 == 0) goto L_0x00ba
            java.lang.StringBuffer r4 = new java.lang.StringBuffer
            r4.<init>()
            r4.append(r0)
            java.net.URL r0 = r6.a
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            org.apache.commons.logging.h.y(r0)
        L_0x00ba:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.logging.f.run():java.lang.Object");
    }
}
