package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Appendable;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public class HttpContent extends HttpPacket implements Appendable<HttpContent> {
    private static final ThreadCache.CachedTypeIndex<HttpContent> CACHE_IDX = ThreadCache.obtainIndex(HttpContent.class, 16);
    protected Buffer content;
    protected HttpHeader httpHeader;
    protected boolean isLast;

    public static boolean isContent(HttpPacket httpPacket) {
        return httpPacket instanceof HttpContent;
    }

    public static boolean isBroken(HttpContent httpContent) {
        return httpContent instanceof HttpBrokenContent;
    }

    public static HttpContent create() {
        return create((HttpHeader) null);
    }

    public static HttpContent create(HttpHeader httpHeader2) {
        return create(httpHeader2, false);
    }

    public static HttpContent create(HttpHeader httpHeader2, boolean isLast2) {
        return create(httpHeader2, isLast2, Buffers.EMPTY_BUFFER);
    }

    public static HttpContent create(HttpHeader httpHeader2, boolean isLast2, Buffer content2) {
        Buffer content3 = content2 != null ? content2 : Buffers.EMPTY_BUFFER;
        HttpContent httpContent = (HttpContent) ThreadCache.takeFromCache(CACHE_IDX);
        if (httpContent == null) {
            return new HttpContent(httpHeader2, isLast2, content3);
        }
        httpContent.httpHeader = httpHeader2;
        httpContent.isLast = isLast2;
        httpContent.content = content3;
        return httpContent;
    }

    public static Builder builder(HttpHeader httpHeader2) {
        return new Builder().httpHeader(httpHeader2);
    }

    protected HttpContent() {
        this((HttpHeader) null);
    }

    protected HttpContent(HttpHeader httpHeader2) {
        this.content = Buffers.EMPTY_BUFFER;
        this.httpHeader = httpHeader2;
    }

    protected HttpContent(HttpHeader httpHeader2, boolean isLast2, Buffer content2) {
        this.content = Buffers.EMPTY_BUFFER;
        this.httpHeader = httpHeader2;
        this.isLast = isLast2;
        this.content = content2;
    }

    public Buffer getContent() {
        return this.content;
    }

    /* access modifiers changed from: protected */
    public final void setContent(Buffer content2) {
        this.content = content2;
    }

    public final HttpHeader getHttpHeader() {
        return this.httpHeader;
    }

    public boolean isLast() {
        return this.isLast;
    }

    public void setLast(boolean isLast2) {
        this.isLast = isLast2;
    }

    public final boolean isHeader() {
        return false;
    }

    public HttpContent append(HttpContent element) {
        if (this.isLast) {
            throw new IllegalStateException("Can not append to a last chunk");
        } else if (isBroken(element)) {
            return element;
        } else {
            Buffer content2 = element.getContent();
            if (content2 != null && content2.hasRemaining()) {
                this.content = Buffers.appendBuffers((MemoryManager) null, this.content, content2);
            }
            if (!element.isLast()) {
                return this;
            }
            element.setContent(this.content);
            return element;
        }
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.isLast = false;
        this.content = Buffers.EMPTY_BUFFER;
        this.httpHeader = null;
    }

    public void recycle() {
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public static class Builder<T extends Builder> {
        protected Buffer content;
        protected HttpHeader httpHeader;
        protected boolean last;

        protected Builder() {
        }

        public final T httpHeader(HttpHeader httpHeader2) {
            this.httpHeader = httpHeader2;
            return this;
        }

        public final T last(boolean last2) {
            this.last = last2;
            return this;
        }

        public final T content(Buffer content2) {
            this.content = content2;
            return this;
        }

        public HttpContent build() {
            if (this.httpHeader != null) {
                HttpContent httpContent = create();
                httpContent.httpHeader = this.httpHeader;
                httpContent.setLast(this.last);
                Buffer buffer = this.content;
                if (buffer != null) {
                    httpContent.setContent(buffer);
                }
                return httpContent;
            }
            throw new IllegalStateException("No HttpHeader specified to associate with this HttpContent.");
        }

        public void reset() {
            this.last = false;
            this.content = null;
            this.httpHeader = null;
        }

        /* access modifiers changed from: protected */
        public HttpContent create() {
            return HttpContent.create();
        }
    }
}
