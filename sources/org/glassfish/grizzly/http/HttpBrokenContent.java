package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.http.HttpContent;

public class HttpBrokenContent extends HttpContent {
    private static final ThreadCache.CachedTypeIndex<HttpBrokenContent> CACHE_IDX = ThreadCache.obtainIndex(HttpBrokenContent.class, 1);
    /* access modifiers changed from: private */
    public Throwable exception;

    public static HttpBrokenContent create() {
        return create((HttpHeader) null);
    }

    public static HttpBrokenContent create(HttpHeader httpHeader) {
        HttpBrokenContent httpBrokenContent = (HttpBrokenContent) ThreadCache.takeFromCache(CACHE_IDX);
        if (httpBrokenContent == null) {
            return new HttpBrokenContent(httpHeader);
        }
        httpBrokenContent.httpHeader = httpHeader;
        return httpBrokenContent;
    }

    public static Builder builder(HttpHeader httpHeader) {
        return (Builder) new Builder().httpHeader(httpHeader);
    }

    protected HttpBrokenContent(HttpHeader httpHeader) {
        super(httpHeader);
    }

    public Throwable getException() {
        return this.exception;
    }

    public Buffer getContent() {
        Throwable th = this.exception;
        throw (th instanceof HttpBrokenContentException ? (HttpBrokenContentException) th : new HttpBrokenContentException(this.exception));
    }

    public final boolean isLast() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.exception = null;
        super.reset();
    }

    public void recycle() {
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public static final class Builder extends HttpContent.Builder<Builder> {
        private Throwable cause;

        protected Builder() {
        }

        public Builder error(Throwable cause2) {
            this.cause = cause2;
            return this;
        }

        public HttpBrokenContent build() {
            HttpBrokenContent httpBrokenContent = (HttpBrokenContent) super.build();
            Throwable th = this.cause;
            if (th != null) {
                Throwable unused = httpBrokenContent.exception = th;
                return httpBrokenContent;
            }
            throw new IllegalStateException("No cause specified");
        }

        /* access modifiers changed from: protected */
        public HttpContent create() {
            return HttpBrokenContent.create();
        }
    }
}
