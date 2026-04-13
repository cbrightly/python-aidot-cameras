package org.glassfish.grizzly.http;

import org.glassfish.grizzly.ThreadCache;

public class HttpRequestPacketImpl extends HttpRequestPacket {
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<HttpRequestPacketImpl> CACHE_IDX = ThreadCache.obtainIndex(HttpRequestPacketImpl.class, 16);
    private final ProcessingState processingState = new ProcessingState();

    public static HttpRequestPacketImpl create() {
        HttpRequestPacketImpl httpRequestImpl = (HttpRequestPacketImpl) ThreadCache.takeFromCache(CACHE_IDX);
        if (httpRequestImpl != null) {
            return httpRequestImpl;
        }
        return new HttpRequestPacketImpl() {
            public void recycle() {
                HttpRequestPacketImpl.super.recycle();
                ThreadCache.putToCache(HttpRequestPacketImpl.CACHE_IDX, this);
            }
        };
    }

    protected HttpRequestPacketImpl() {
        this.isExpectContent = true;
    }

    public ProcessingState getProcessingState() {
        return this.processingState;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.processingState.recycle();
        this.isExpectContent = true;
        super.reset();
    }

    public void recycle() {
        if (!isExpectContent()) {
            reset();
        }
    }
}
