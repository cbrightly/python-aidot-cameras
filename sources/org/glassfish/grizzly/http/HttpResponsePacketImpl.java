package org.glassfish.grizzly.http;

import org.glassfish.grizzly.ThreadCache;

public class HttpResponsePacketImpl extends HttpResponsePacket {
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<HttpResponsePacketImpl> CACHE_IDX = ThreadCache.obtainIndex(HttpResponsePacketImpl.class, 16);

    public static HttpResponsePacketImpl create() {
        HttpResponsePacketImpl httpResponseImpl = (HttpResponsePacketImpl) ThreadCache.takeFromCache(CACHE_IDX);
        if (httpResponseImpl != null) {
            return httpResponseImpl;
        }
        return new HttpResponsePacketImpl() {
            public void recycle() {
                HttpResponsePacketImpl.super.recycle();
                ThreadCache.putToCache(HttpResponsePacketImpl.CACHE_IDX, this);
            }
        };
    }

    protected HttpResponsePacketImpl() {
    }

    public ProcessingState getProcessingState() {
        return getRequest().getProcessingState();
    }

    /* access modifiers changed from: protected */
    public void reset() {
        super.reset();
    }

    public void recycle() {
        if (!getRequest().isExpectContent()) {
            reset();
        }
    }
}
