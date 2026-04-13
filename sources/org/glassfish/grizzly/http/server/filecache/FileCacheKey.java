package org.glassfish.grizzly.http.server.filecache;

import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;

public class FileCacheKey implements Cacheable {
    private static final ThreadCache.CachedTypeIndex<FileCacheKey> CACHE_IDX = ThreadCache.obtainIndex(FileCacheKey.class, 16);
    protected String host;
    protected String uri;

    protected FileCacheKey() {
    }

    protected FileCacheKey(String host2, String uri2) {
        this.host = host2;
        this.uri = uri2;
    }

    public void recycle() {
        this.host = null;
        this.uri = null;
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public static FileCacheKey create(String host2, String uri2) {
        FileCacheKey key = (FileCacheKey) ThreadCache.takeFromCache(CACHE_IDX);
        if (key == null) {
            return new FileCacheKey(host2, uri2);
        }
        key.host = host2;
        key.uri = uri2;
        return key;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0031 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r6) {
        /*
            r5 = this;
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Class r1 = r5.getClass()
            java.lang.Class r2 = r6.getClass()
            if (r1 == r2) goto L_0x000f
            return r0
        L_0x000f:
            r1 = r6
            org.glassfish.grizzly.http.server.filecache.FileCacheKey r1 = (org.glassfish.grizzly.http.server.filecache.FileCacheKey) r1
            java.lang.String r2 = r1.host
            java.lang.String r3 = r5.host
            if (r3 != 0) goto L_0x001b
            if (r2 == 0) goto L_0x0022
            goto L_0x0021
        L_0x001b:
            boolean r3 = r3.equals(r2)
            if (r3 != 0) goto L_0x0022
        L_0x0021:
            return r0
        L_0x0022:
            java.lang.String r3 = r1.uri
            java.lang.String r4 = r5.uri
            if (r4 != 0) goto L_0x002b
            if (r3 == 0) goto L_0x0032
            goto L_0x0031
        L_0x002b:
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L_0x0032
        L_0x0031:
            return r0
        L_0x0032:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.filecache.FileCacheKey.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int i = 3 * 23;
        String str = this.host;
        int i2 = 0;
        int hash = (i + (str != null ? str.hashCode() : 0)) * 23;
        String str2 = this.uri;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return hash + i2;
    }

    /* access modifiers changed from: protected */
    public String getHost() {
        return this.host;
    }

    /* access modifiers changed from: protected */
    public String getUri() {
        return this.uri;
    }
}
