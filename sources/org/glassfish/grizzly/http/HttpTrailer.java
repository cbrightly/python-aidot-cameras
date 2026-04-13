package org.glassfish.grizzly.http;

import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.MimeHeaders;

public class HttpTrailer extends HttpContent implements MimeHeadersPacket {
    private static final ThreadCache.CachedTypeIndex<HttpTrailer> CACHE_IDX = ThreadCache.obtainIndex(HttpTrailer.class, 16);
    /* access modifiers changed from: private */
    public MimeHeaders trailers;

    public static boolean isTrailer(HttpContent httpContent) {
        return httpContent != null && HttpTrailer.class.isAssignableFrom(httpContent.getClass());
    }

    public static HttpTrailer create() {
        return create((HttpHeader) null);
    }

    public static HttpTrailer create(HttpHeader httpHeader) {
        HttpTrailer httpTrailer = (HttpTrailer) ThreadCache.takeFromCache(CACHE_IDX);
        if (httpTrailer == null) {
            return new HttpTrailer(httpHeader);
        }
        httpTrailer.httpHeader = httpHeader;
        return httpTrailer;
    }

    public static Builder builder(HttpHeader httpHeader) {
        return (Builder) new Builder().httpHeader(httpHeader);
    }

    protected HttpTrailer(HttpHeader httpHeader) {
        super(httpHeader);
        MimeHeaders mimeHeaders = new MimeHeaders();
        this.trailers = mimeHeaders;
        mimeHeaders.mark();
    }

    public final boolean isLast() {
        return true;
    }

    public MimeHeaders getHeaders() {
        return this.trailers;
    }

    public String getHeader(String name) {
        return this.trailers.getHeader(name);
    }

    public String getHeader(Header header) {
        return this.trailers.getHeader(header);
    }

    public void setHeader(String name, String value) {
        if (name != null && value != null) {
            this.trailers.setValue(name).setString(value);
        }
    }

    public void setHeader(String name, HeaderValue value) {
        if (name != null && value != null && value.isSet()) {
            value.serializeToDataChunk(this.trailers.setValue(name));
        }
    }

    public void setHeader(Header header, String value) {
        if (header != null && value != null) {
            this.trailers.setValue(header).setString(value);
        }
    }

    public void setHeader(Header header, HeaderValue value) {
        if (header != null && value != null && value.isSet()) {
            value.serializeToDataChunk(this.trailers.setValue(header));
        }
    }

    public void addHeader(String name, String value) {
        if (name != null && value != null) {
            this.trailers.addValue(name).setString(value);
        }
    }

    public void addHeader(String name, HeaderValue value) {
        if (name != null && value != null && value.isSet()) {
            value.serializeToDataChunk(this.trailers.setValue(name));
        }
    }

    public void addHeader(Header header, String value) {
        DataChunk c;
        if (header != null && value != null && (c = this.trailers.addValue(header)) != null) {
            c.setString(value);
        }
    }

    public void addHeader(Header header, HeaderValue value) {
        if (header != null && value != null && value.isSet()) {
            value.serializeToDataChunk(this.trailers.setValue(header));
        }
    }

    public boolean containsHeader(String name) {
        return this.trailers.contains(name);
    }

    public boolean containsHeader(Header header) {
        return this.trailers.contains(header);
    }

    /* access modifiers changed from: protected */
    public void setTrailers(MimeHeaders trailers2) {
        this.trailers = trailers2;
        trailers2.mark();
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.trailers.recycle();
        this.trailers.mark();
        super.reset();
    }

    public void recycle() {
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public static final class Builder extends HttpContent.Builder<Builder> {
        private MimeHeaders mimeTrailers;

        protected Builder() {
        }

        public Builder headers(MimeHeaders mimeTrailers2) {
            this.mimeTrailers = mimeTrailers2;
            mimeTrailers2.mark();
            return this;
        }

        public Builder header(String name, String value) {
            if (this.mimeTrailers == null) {
                MimeHeaders mimeHeaders = new MimeHeaders();
                this.mimeTrailers = mimeHeaders;
                mimeHeaders.mark();
            }
            DataChunk c = this.mimeTrailers.addValue(name);
            if (c != null) {
                c.setString(value);
            }
            return this;
        }

        public HttpTrailer build() {
            HttpTrailer trailer = (HttpTrailer) super.build();
            MimeHeaders mimeHeaders = this.mimeTrailers;
            if (mimeHeaders != null) {
                MimeHeaders unused = trailer.trailers = mimeHeaders;
            }
            return trailer;
        }

        /* access modifiers changed from: protected */
        public HttpContent create() {
            return HttpTrailer.create();
        }
    }
}
