package org.glassfish.grizzly.http.server.http2;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.MimeHeaders;

public class PushEvent implements FilterChainEvent {
    private static final ThreadCache.CachedTypeIndex<PushEvent> CACHE_IDX;
    public static final Object TYPE;
    private MimeHeaders headers = new MimeHeaders();
    private HttpRequestPacket httpRequest;
    private String method;
    private String path;

    static {
        Class<PushEvent> cls = PushEvent.class;
        CACHE_IDX = ThreadCache.obtainIndex(cls, 8);
        TYPE = cls.getName();
    }

    private PushEvent() {
    }

    public Object type() {
        return TYPE;
    }

    public static PushEvent create(PushBuilder builder) {
        PushEvent pushEvent = (PushEvent) ThreadCache.takeFromCache(CACHE_IDX);
        if (pushEvent == null) {
            pushEvent = new PushEvent();
        }
        return pushEvent.init(builder);
    }

    public String getMethod() {
        return this.method;
    }

    public MimeHeaders getHeaders() {
        return this.headers;
    }

    public String getPath() {
        return this.path;
    }

    public HttpHeader getHttpRequest() {
        return this.httpRequest;
    }

    public void recycle() {
        this.method = null;
        this.headers.recycle();
        this.path = null;
        this.httpRequest = null;
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public static PushEventBuilder builder() {
        return new PushEventBuilder();
    }

    /* access modifiers changed from: private */
    public static PushEvent create(PushEventBuilder builder) {
        PushEvent pushEvent = (PushEvent) ThreadCache.takeFromCache(CACHE_IDX);
        if (pushEvent == null) {
            pushEvent = new PushEvent();
        }
        return pushEvent.init(builder);
    }

    private PushEvent init(PushBuilder builder) {
        this.method = builder.method;
        this.headers.copyFrom(builder.headers);
        this.path = builder.path;
        this.httpRequest = builder.request.getRequest();
        return this;
    }

    private PushEvent init(PushEventBuilder builder) {
        this.method = builder.method;
        this.headers.copyFrom(builder.headers);
        this.path = builder.path;
        this.httpRequest = builder.httpRequest;
        return this;
    }

    public static final class PushEventBuilder {
        /* access modifiers changed from: private */
        public MimeHeaders headers;
        /* access modifiers changed from: private */
        public HttpRequestPacket httpRequest;
        /* access modifiers changed from: private */
        public String method;
        /* access modifiers changed from: private */
        public String path;

        private PushEventBuilder() {
            this.method = Method.GET.getMethodString();
            this.headers = new MimeHeaders();
        }

        public PushEventBuilder method(String val) {
            if (this.method == null) {
                throw new NullPointerException();
            } else if (Method.POST.getMethodString().equals(this.method) || Method.PUT.getMethodString().equals(this.method) || Method.DELETE.getMethodString().equals(this.method) || Method.CONNECT.getMethodString().equals(this.method) || Method.OPTIONS.getMethodString().equals(this.method) || Method.TRACE.getMethodString().equals(this.method)) {
                throw new IllegalArgumentException();
            } else {
                this.method = val;
                return this;
            }
        }

        public PushEventBuilder headers(MimeHeaders val) {
            if (val != null) {
                this.headers.copyFrom(val);
                return this;
            }
            throw new NullPointerException();
        }

        public PushEventBuilder path(String val) {
            this.path = validate(val);
            return this;
        }

        public PushEventBuilder httpRequest(HttpRequestPacket val) {
            if (val != null) {
                this.httpRequest = val;
                return this;
            }
            throw new NullPointerException();
        }

        public PushEvent build() {
            if (this.path != null && this.httpRequest != null && this.headers != null) {
                return PushEvent.create(this);
            }
            throw new IllegalArgumentException();
        }

        private static String validate(String val) {
            if (val == null || val.isEmpty()) {
                return null;
            }
            return val;
        }
    }
}
