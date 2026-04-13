package org.glassfish.grizzly.http;

import java.util.Locale;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;

public abstract class HttpResponsePacket extends HttpHeader {
    private boolean acknowledgment;
    private boolean allowCustomReasonPhrase = true;
    private String contentLanguage;
    protected HttpStatus httpStatus;
    private boolean isHtmlEncodingCustomReasonPhrase = true;
    private Locale locale;
    private final DataChunk reasonPhraseC = DataChunk.newInstance();
    private HttpRequestPacket request;

    public static Builder builder(HttpRequestPacket request2) {
        return new Builder().requestPacket(request2);
    }

    protected HttpResponsePacket() {
    }

    public int getStatus() {
        return getHttpStatus().getStatusCode();
    }

    public HttpStatus getHttpStatus() {
        if (this.httpStatus == null) {
            this.httpStatus = HttpStatus.OK_200;
        }
        return this.httpStatus;
    }

    public void setStatus(int status) {
        this.httpStatus = HttpStatus.getHttpStatus(status);
    }

    public void setStatus(HttpStatus status) {
        this.httpStatus = status;
        this.reasonPhraseC.recycle();
    }

    public final boolean isAllowCustomReasonPhrase() {
        return this.allowCustomReasonPhrase;
    }

    public final void setAllowCustomReasonPhrase(boolean allowCustomReasonPhrase2) {
        this.allowCustomReasonPhrase = allowCustomReasonPhrase2;
    }

    public boolean isHtmlEncodingCustomReasonPhrase() {
        return this.isHtmlEncodingCustomReasonPhrase;
    }

    public void setHtmlEncodingCustomReasonPhrase(boolean isHtmlEncodingCustomReasonPhrase2) {
        this.isHtmlEncodingCustomReasonPhrase = isHtmlEncodingCustomReasonPhrase2;
    }

    public final DataChunk getReasonPhraseRawDC() {
        return this.reasonPhraseC;
    }

    public final DataChunk getReasonPhraseDC() {
        if (isCustomReasonPhraseSet()) {
            return this.reasonPhraseC;
        }
        this.reasonPhraseC.setBytes(this.httpStatus.getReasonPhraseBytes());
        return this.reasonPhraseC;
    }

    public final String getReasonPhrase() {
        return getReasonPhraseDC().toString();
    }

    public void setReasonPhrase(String message) {
        this.reasonPhraseC.setString(message);
    }

    public void setReasonPhrase(Buffer reason) {
        this.reasonPhraseC.setBuffer(reason, reason.position(), reason.limit());
    }

    public final boolean isCustomReasonPhraseSet() {
        return this.allowCustomReasonPhrase && !this.reasonPhraseC.isNull();
    }

    public HttpRequestPacket getRequest() {
        return this.request;
    }

    public boolean isAcknowledgement() {
        return this.acknowledgment;
    }

    public void setAcknowledgement(boolean acknowledgement) {
        this.acknowledgment = acknowledgement;
    }

    public void acknowledged() {
        this.request.requiresAcknowledgement(false);
        this.acknowledgment = false;
        this.httpStatus = null;
        this.reasonPhraseC.recycle();
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.httpStatus = null;
        this.acknowledgment = false;
        this.allowCustomReasonPhrase = true;
        this.isHtmlEncodingCustomReasonPhrase = true;
        this.reasonPhraseC.recycle();
        this.locale = null;
        this.contentLanguage = null;
        this.request = null;
        super.reset();
    }

    public final boolean isRequest() {
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("HttpResponsePacket (\n  status=");
        sb.append(getStatus());
        sb.append("\n  reason=");
        sb.append(getReasonPhrase());
        sb.append("\n  protocol=");
        sb.append(getProtocol().getProtocolString());
        sb.append("\n  content-length=");
        sb.append(getContentLength());
        sb.append("\n  committed=");
        sb.append(isCommitted());
        sb.append("\n  headers=[");
        MimeHeaders headersLocal = getHeaders();
        for (String name : headersLocal.names()) {
            for (String value : headersLocal.values(name)) {
                sb.append("\n      ");
                sb.append(name);
                sb.append('=');
                sb.append(value);
            }
        }
        sb.append("]\n)");
        return sb.toString();
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale2) {
        String country;
        if (locale2 != null) {
            this.locale = locale2;
            String language = locale2.getLanguage();
            this.contentLanguage = language;
            if (language != null && language.length() > 0 && (country = locale2.getCountry()) != null && country.length() > 0) {
                this.contentLanguage += '-' + country;
            }
        }
    }

    public String getContentLanguage() {
        return this.contentLanguage;
    }

    public void setContentLanguage(String contentLanguage2) {
        this.contentLanguage = contentLanguage2;
    }

    public void setContentLengthLong(long contentLength) {
        setChunked(contentLength < 0);
        super.setContentLengthLong(contentLength);
    }

    public void setRequest(HttpRequestPacket request2) {
        this.request = request2;
    }

    public static class Builder extends HttpHeader.Builder<Builder> {
        protected String reasonPhrase;
        protected HttpRequestPacket requestPacket;
        protected Integer status;

        public Builder status(int status2) {
            this.status = Integer.valueOf(status2);
            return this;
        }

        public Builder reasonPhrase(String reasonPhrase2) {
            this.reasonPhrase = reasonPhrase2;
            return this;
        }

        public Builder requestPacket(HttpRequestPacket requestPacket2) {
            this.requestPacket = requestPacket2;
            return this;
        }

        public final HttpResponsePacket build() {
            HttpResponsePacket responsePacket = (HttpResponsePacket) super.build();
            Integer num = this.status;
            if (num != null) {
                responsePacket.setStatus(num.intValue());
            }
            String str = this.reasonPhrase;
            if (str != null) {
                responsePacket.setReasonPhrase(str);
            }
            return responsePacket;
        }

        public void reset() {
            super.reset();
            this.status = null;
            this.reasonPhrase = null;
        }

        /* access modifiers changed from: protected */
        public HttpHeader create() {
            HttpRequestPacket httpRequestPacket = this.requestPacket;
            if (httpRequestPacket != null) {
                HttpResponsePacket responsePacket = httpRequestPacket.getResponse();
                if (responsePacket != null) {
                    return responsePacket;
                }
                HttpResponsePacket responsePacket2 = HttpResponsePacketImpl.create();
                responsePacket2.setRequest(this.requestPacket);
                responsePacket2.setSecure(this.requestPacket.isSecure());
                return responsePacket2;
            }
            throw new IllegalStateException("Unable to create new HttpResponsePacket.  No HttpRequestPacket available.");
        }
    }
}
