package org.glassfish.grizzly.http.server.filecache;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;

public class LazyFileCacheKey extends FileCacheKey {
    private static final ThreadCache.CachedTypeIndex<LazyFileCacheKey> CACHE_IDX = ThreadCache.obtainIndex(LazyFileCacheKey.class, 16);
    private int hashCode;
    private boolean isInitialized;
    private HttpRequestPacket request;

    private LazyFileCacheKey(HttpRequestPacket request2) {
        this.request = request2;
    }

    /* access modifiers changed from: protected */
    public String getHost() {
        if (!this.isInitialized) {
            initialize();
        }
        return super.getHost();
    }

    /* access modifiers changed from: protected */
    public String getUri() {
        if (!this.isInitialized) {
            initialize();
        }
        return super.getUri();
    }

    public void recycle() {
        this.host = null;
        this.uri = null;
        this.isInitialized = false;
        this.request = null;
        this.hashCode = 0;
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public static LazyFileCacheKey create(HttpRequestPacket request2) {
        LazyFileCacheKey key = (LazyFileCacheKey) ThreadCache.takeFromCache(CACHE_IDX);
        if (key == null) {
            return new LazyFileCacheKey(request2);
        }
        key.request = request2;
        return key;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 0
            if (r8 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Class r1 = r7.getClass()
            java.lang.Class r2 = r8.getClass()
            boolean r1 = r1.isAssignableFrom(r2)
            if (r1 == 0) goto L_0x0013
            return r0
        L_0x0013:
            r1 = r8
            org.glassfish.grizzly.http.server.filecache.FileCacheKey r1 = (org.glassfish.grizzly.http.server.filecache.FileCacheKey) r1
            java.lang.String r2 = r1.host
            org.glassfish.grizzly.http.util.DataChunk r3 = r7.getHostLazy()
            if (r3 == 0) goto L_0x002c
            boolean r4 = r3.isNull()
            if (r4 == 0) goto L_0x0025
            goto L_0x002c
        L_0x0025:
            boolean r4 = r3.equals((java.lang.String) r2)
            if (r4 != 0) goto L_0x002f
            goto L_0x002e
        L_0x002c:
            if (r2 == 0) goto L_0x002f
        L_0x002e:
            return r0
        L_0x002f:
            java.lang.String r4 = r1.uri
            org.glassfish.grizzly.http.util.DataChunk r5 = r7.getUriLazy()
            if (r5 == 0) goto L_0x0045
            boolean r6 = r5.isNull()
            if (r6 == 0) goto L_0x003e
            goto L_0x0045
        L_0x003e:
            boolean r6 = r5.equals((java.lang.String) r4)
            if (r6 != 0) goto L_0x0048
            goto L_0x0047
        L_0x0045:
            if (r4 == 0) goto L_0x0048
        L_0x0047:
            return r0
        L_0x0048:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.filecache.LazyFileCacheKey.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            DataChunk hostDC = getHostLazy();
            DataChunk uriDC = getUriLazy();
            int i = 0;
            int hash = ((3 * 23) + (hostDC != null ? hostDC.hashCode() : 0)) * 23;
            if (uriDC != null) {
                i = uriDC.hashCode();
            }
            this.hashCode = hash + i;
        }
        return this.hashCode;
    }

    private void initialize() {
        this.isInitialized = true;
        this.host = this.request.getHeader(Header.Host);
        this.uri = this.request.getRequestURI();
    }

    private DataChunk getHostLazy() {
        return this.request.getHeaders().getValue(Header.Host);
    }

    private DataChunk getUriLazy() {
        return this.request.getRequestURIRef().getRequestURIBC();
    }
}
