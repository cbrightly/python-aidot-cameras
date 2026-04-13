package org.glassfish.grizzly.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpTrailer;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.HttpUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.utils.Charsets;

public abstract class HttpHeader extends HttpPacket implements MimeHeadersPacket, AttributeStorage {
    private static final byte[] CHUNKED_ENCODING_BYTES = "chunked".getBytes(Charsets.ASCII_CHARSET);
    private AttributeHolder activeAttributes;
    private final AttributeHolder attributes;
    private boolean chunkingAllowed;
    private final List<ContentEncoding> contentEncodings;
    protected long contentLength;
    protected final ContentType.SettableContentType contentType;
    Buffer headerBuffer;
    protected final MimeHeaders headers;
    protected boolean isChunked;
    protected boolean isCommitted;
    protected boolean isContentBroken;
    private boolean isContentEncodingsSelected;
    protected boolean isExpectContent;
    private boolean isIgnoreContentModifiers;
    protected boolean isSkipRemainder;
    protected Protocol parsedProtocol;
    protected final DataChunk protocolC;
    protected boolean secure;
    private final byte[] tmpContentLengthBuffer;
    private final byte[] tmpHeaderEncodingBuffer;
    private TransferEncoding transferEncoding;
    protected final DataChunk upgrade;

    public abstract ProcessingState getProcessingState();

    public abstract boolean isRequest();

    public HttpHeader() {
        this(new MimeHeaders());
    }

    protected HttpHeader(MimeHeaders headers2) {
        this.protocolC = DataChunk.newInstance();
        this.tmpContentLengthBuffer = new byte[20];
        this.tmpHeaderEncodingBuffer = new byte[512];
        this.contentLength = -1;
        this.contentType = ContentType.newSettableContentType();
        this.isExpectContent = true;
        this.upgrade = DataChunk.newInstance();
        this.contentEncodings = new ArrayList(2);
        this.attributes = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createUnsafeAttributeHolder();
        this.headers = headers2;
    }

    /* access modifiers changed from: package-private */
    public void setHeaderBuffer(Buffer headerBuffer2) {
        this.headerBuffer = headerBuffer2;
    }

    public AttributeHolder getAttributes() {
        if (this.activeAttributes == null) {
            this.activeAttributes = this.attributes;
        }
        return this.activeAttributes;
    }

    public final boolean isHeader() {
        return true;
    }

    public HttpHeader getHttpHeader() {
        return this;
    }

    /* access modifiers changed from: protected */
    public HttpPacketParsing getParsingState() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void addContentEncoding(ContentEncoding contentEncoding) {
        this.contentEncodings.add(contentEncoding);
    }

    /* access modifiers changed from: protected */
    public List<ContentEncoding> getContentEncodings(boolean isModifiable) {
        if (isModifiable) {
            return this.contentEncodings;
        }
        return Collections.unmodifiableList(this.contentEncodings);
    }

    public List<ContentEncoding> getContentEncodings() {
        return getContentEncodings(false);
    }

    /* access modifiers changed from: protected */
    public final boolean isContentEncodingsSelected() {
        return this.isContentEncodingsSelected;
    }

    /* access modifiers changed from: protected */
    public final void setContentEncodingsSelected(boolean isContentEncodingsSelected2) {
        this.isContentEncodingsSelected = isContentEncodingsSelected2;
    }

    public TransferEncoding getTransferEncoding() {
        return this.transferEncoding;
    }

    /* access modifiers changed from: protected */
    public void setTransferEncoding(TransferEncoding transferEncoding2) {
        this.transferEncoding = transferEncoding2;
    }

    public boolean isChunked() {
        return this.isChunked;
    }

    public void setChunked(boolean isChunked2) {
        if (getProtocol().compareTo(Protocol.HTTP_1_1) >= 0) {
            this.isChunked = isChunked2;
            if (isChunked2) {
                this.headers.removeHeader(Header.ContentLength);
                return;
            }
            return;
        }
        this.isChunked = false;
    }

    public boolean isExpectContent() {
        return this.isExpectContent;
    }

    public void setExpectContent(boolean isExpectContent2) {
        this.isExpectContent = isExpectContent2;
    }

    public boolean isSkipRemainder() {
        return this.isSkipRemainder;
    }

    public void setSkipRemainder(boolean isSkipRemainder2) {
        this.isSkipRemainder = isSkipRemainder2;
    }

    public boolean isContentBroken() {
        return this.isContentBroken;
    }

    public void setContentBroken(boolean isBroken) {
        this.isContentBroken = isBroken;
    }

    public final String getUpgrade() {
        return this.upgrade.toString();
    }

    public DataChunk getUpgradeDC() {
        return this.upgrade;
    }

    public final void setUpgrade(String upgrade2) {
        this.upgrade.setString(upgrade2);
    }

    public boolean isUpgrade() {
        return !getUpgradeDC().isNull();
    }

    /* access modifiers changed from: protected */
    public void makeUpgradeHeader() {
        if (!this.upgrade.isNull()) {
            this.headers.setValue(Header.Upgrade).set(this.upgrade);
        }
    }

    public boolean isIgnoreContentModifiers() {
        return this.isIgnoreContentModifiers;
    }

    public void setIgnoreContentModifiers(boolean isIgnoreContentModifiers2) {
        this.isIgnoreContentModifiers = isIgnoreContentModifiers2;
    }

    /* access modifiers changed from: protected */
    public void makeContentLengthHeader(long defaultLength) {
        long j = this.contentLength;
        if (j != -1) {
            int start = HttpUtils.longToBuffer(j, this.tmpContentLengthBuffer);
            DataChunk value = this.headers.setValue(Header.ContentLength);
            byte[] bArr = this.tmpContentLengthBuffer;
            value.setBytes(bArr, start, bArr.length);
        } else if (defaultLength != -1) {
            int start2 = HttpUtils.longToBuffer(defaultLength, this.tmpContentLengthBuffer);
            MimeHeaders mimeHeaders = this.headers;
            Header header = Header.ContentLength;
            int idx = mimeHeaders.indexOf(header, 0);
            if (idx == -1) {
                DataChunk addValue = this.headers.addValue(header);
                byte[] bArr2 = this.tmpContentLengthBuffer;
                addValue.setBytes(bArr2, start2, bArr2.length);
            } else if (this.headers.getValue(idx).isNull()) {
                DataChunk value2 = this.headers.getValue(idx);
                byte[] bArr3 = this.tmpContentLengthBuffer;
                value2.setBytes(bArr3, start2, bArr3.length);
            }
        }
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public void setContentLength(int len) {
        setContentLengthLong((long) len);
    }

    public void setContentLengthLong(long contentLength2) {
        this.contentLength = contentLength2;
        if (contentLength2 < 0) {
            this.headers.removeHeader(Header.ContentLength);
        }
    }

    public boolean isCommitted() {
        return this.isCommitted;
    }

    public void setCommitted(boolean isCommitted2) {
        this.isCommitted = isCommitted2;
    }

    /* access modifiers changed from: protected */
    public void makeTransferEncodingHeader(String defaultValue) {
        MimeHeaders mimeHeaders = this.headers;
        Header header = Header.TransferEncoding;
        if (mimeHeaders.indexOf(header, 0) == -1) {
            this.headers.addValue(header).setBytes(CHUNKED_ENCODING_BYTES);
        }
    }

    /* access modifiers changed from: protected */
    public void extractContentEncoding(DataChunk value) {
        int idx = this.headers.indexOf(Header.ContentEncoding, 0);
        if (idx != -1) {
            this.headers.setSerialized(idx, true);
            value.set(this.headers.getValue(idx));
        }
    }

    public String getCharacterEncoding() {
        return this.contentType.getCharacterEncoding();
    }

    public void setCharacterEncoding(String charset) {
        if (!isCommitted()) {
            this.contentType.setCharacterEncoding(charset);
        }
    }

    public boolean isChunkingAllowed() {
        return this.chunkingAllowed;
    }

    public void setChunkingAllowed(boolean chunkingAllowed2) {
        this.chunkingAllowed = chunkingAllowed2;
    }

    public boolean isContentTypeSet() {
        return this.contentType.isMimeTypeSet() || this.headers.getValue(Header.ContentType) != null;
    }

    public String getContentType() {
        return this.contentType.get();
    }

    public void setContentType(String contentType2) {
        this.contentType.set(contentType2);
    }

    public void setContentType(ContentType contentType2) {
        this.contentType.set(contentType2);
    }

    /* access modifiers changed from: protected */
    public ContentType getContentTypeHolder() {
        return this.contentType;
    }

    public MimeHeaders getHeaders() {
        return this.headers;
    }

    public String getHeader(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        String result = handleGetSpecialHeader(name);
        return result != null ? result : this.headers.getHeader(name);
    }

    public String getHeader(Header header) {
        if (header == null) {
            return null;
        }
        String result = handleGetSpecialHeader(header);
        return result != null ? result : this.headers.getHeader(header);
    }

    public void setHeader(String name, String value) {
        if (name != null && value != null && !name.isEmpty() && !handleSetSpecialHeaders(name, value)) {
            this.headers.setValue(name).setString(value);
        }
    }

    public void setHeader(String name, HeaderValue value) {
        if (name != null && value != null && !name.isEmpty() && value.isSet() && !handleSetSpecialHeaders(name, value)) {
            value.serializeToDataChunk(this.headers.setValue(name));
        }
    }

    public void setHeader(Header header, String value) {
        if (header != null && value != null && !handleSetSpecialHeaders(header, value)) {
            this.headers.setValue(header).setString(value);
        }
    }

    public void setHeader(Header header, HeaderValue value) {
        if (header != null && value != null && value.isSet() && !handleSetSpecialHeaders(header, value)) {
            value.serializeToDataChunk(this.headers.setValue(header));
        }
    }

    public void addHeader(String name, String value) {
        if (name != null && value != null && !name.isEmpty() && !handleSetSpecialHeaders(name, value)) {
            this.headers.addValue(name).setString(value);
        }
    }

    public void addHeader(String name, HeaderValue value) {
        if (name != null && value != null && !name.isEmpty() && value.isSet() && !handleSetSpecialHeaders(name, value)) {
            value.serializeToDataChunk(this.headers.setValue(name));
        }
    }

    public void addHeader(Header header, String value) {
        if (header != null && value != null && !handleSetSpecialHeaders(header, value)) {
            this.headers.addValue(header).setString(value);
        }
    }

    public void addHeader(Header header, HeaderValue value) {
        if (header != null && value != null && value.isSet() && !handleSetSpecialHeaders(header, value)) {
            value.serializeToDataChunk(this.headers.setValue(header));
        }
    }

    public boolean containsHeader(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (handleGetSpecialHeader(name) == null && this.headers.getHeader(name) == null) {
            return false;
        }
        return true;
    }

    public boolean containsHeader(Header header) {
        if (header == null) {
            return false;
        }
        if (handleGetSpecialHeader(header) == null && this.headers.getHeader(header) == null) {
            return false;
        }
        return true;
    }

    public DataChunk getProtocolDC() {
        this.parsedProtocol = null;
        return this.protocolC;
    }

    public String getProtocolString() {
        Protocol protocol = this.parsedProtocol;
        if (protocol == null) {
            return getProtocolDC().toString();
        }
        return protocol.getProtocolString();
    }

    public Protocol getProtocol() {
        Protocol protocol = this.parsedProtocol;
        if (protocol != null) {
            return protocol;
        }
        Protocol valueOf = Protocol.valueOf(this.protocolC);
        this.parsedProtocol = valueOf;
        return valueOf;
    }

    public void setProtocol(Protocol protocol) {
        this.parsedProtocol = protocol;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public void setSecure(boolean secure2) {
        this.secure = secure2;
    }

    public final HttpContent.Builder httpContentBuilder() {
        return HttpContent.builder(this);
    }

    public HttpTrailer.Builder httpTrailerBuilder() {
        return HttpTrailer.builder(this);
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.isContentEncodingsSelected = false;
        this.secure = false;
        this.isSkipRemainder = false;
        this.isContentBroken = false;
        AttributeHolder attributeHolder = this.activeAttributes;
        if (attributeHolder != null) {
            attributeHolder.recycle();
            this.activeAttributes = null;
        }
        this.protocolC.recycle();
        this.parsedProtocol = null;
        this.contentEncodings.clear();
        this.headers.clear();
        this.isCommitted = false;
        this.isChunked = false;
        this.contentLength = -1;
        this.contentType.reset();
        this.chunkingAllowed = false;
        this.transferEncoding = null;
        this.isExpectContent = true;
        this.upgrade.recycle();
        this.isIgnoreContentModifiers = false;
        Buffer buffer = this.headerBuffer;
        if (buffer != null) {
            buffer.dispose();
            this.headerBuffer = null;
        }
    }

    public void recycle() {
        reset();
    }

    private final String handleGetSpecialHeader(String name) {
        if (isSpecialHeader(name)) {
            return getValueBasedOnHeader(name);
        }
        return null;
    }

    private final String handleGetSpecialHeader(Header header) {
        if (isSpecialHeader(header.toString())) {
            return getValueBasedOnHeader(header);
        }
        return null;
    }

    private final boolean handleSetSpecialHeaders(String name, String value) {
        return isSpecialHeaderSet(name) && setValueBasedOnHeader(name, value);
    }

    private final boolean handleSetSpecialHeaders(String name, HeaderValue value) {
        return isSpecialHeaderSet(name) && setValueBasedOnHeader(name, value.get());
    }

    private final boolean handleSetSpecialHeaders(Header header, String value) {
        return isSpecialHeaderSet(header.toString()) && setValueBasedOnHeader(header, value);
    }

    private final boolean handleSetSpecialHeaders(Header header, HeaderValue value) {
        return isSpecialHeaderSet(header.toString()) && setValueBasedOnHeader(header, value.get());
    }

    private static boolean isSpecialHeader(String name) {
        return isSpecialHeader(name.charAt(0));
    }

    private static boolean isSpecialHeaderSet(String name) {
        char c = name.charAt(0);
        if (isSpecialHeader(c) || c == 'T' || c == 't') {
            return true;
        }
        return false;
    }

    private static boolean isSpecialHeader(char c) {
        return c == 'C' || c == 'c' || c == 'U' || c == 'u';
    }

    public byte[] getTempHeaderEncodingBuffer() {
        return this.tmpHeaderEncodingBuffer;
    }

    private String getValueBasedOnHeader(Header header) {
        if (Header.ContentType.equals(header)) {
            String value = getContentType();
            if (value != null) {
                return value;
            }
            return null;
        } else if (Header.ContentLength.equals(header)) {
            long value2 = getContentLength();
            if (value2 >= 0) {
                return Long.toString(value2);
            }
            return null;
        } else if (Header.Upgrade.equals(header)) {
            return getUpgrade();
        } else {
            return null;
        }
    }

    private String getValueBasedOnHeader(String name) {
        if (Header.ContentType.toString().equalsIgnoreCase(name)) {
            String value = getContentType();
            if (value != null) {
                return value;
            }
            return null;
        } else if (Header.ContentLength.toString().equalsIgnoreCase(name)) {
            long value2 = getContentLength();
            if (value2 >= 0) {
                return Long.toString(value2);
            }
            return null;
        } else if (Header.Upgrade.toString().equalsIgnoreCase(name)) {
            return getUpgrade();
        } else {
            return null;
        }
    }

    private boolean setValueBasedOnHeader(String name, String value) {
        if (Header.ContentType.toString().equalsIgnoreCase(name)) {
            setContentType(value);
            return true;
        } else if (Header.ContentLength.toString().equalsIgnoreCase(name)) {
            this.headers.removeHeader(Header.TransferEncoding);
            setChunked(false);
            return setContentLenth(value);
        } else {
            if (Header.Upgrade.toString().equalsIgnoreCase(name)) {
                setUpgrade(value);
            } else if (Header.TransferEncoding.toString().equalsIgnoreCase(name)) {
                if ("chunked".equalsIgnoreCase(value)) {
                    setContentLengthLong(-1);
                    setChunked(true);
                }
                return true;
            }
            return false;
        }
    }

    private boolean setValueBasedOnHeader(Header header, String value) {
        if (Header.ContentType.equals(header)) {
            setContentType(value);
            return true;
        } else if (Header.ContentLength.equals(header)) {
            this.headers.removeHeader(Header.TransferEncoding);
            setChunked(false);
            return setContentLenth(value);
        } else {
            if (Header.Upgrade.equals(header)) {
                setUpgrade(value);
            } else if (Header.TransferEncoding.equals(header)) {
                if ("chunked".equalsIgnoreCase(value)) {
                    setContentLengthLong(-1);
                    setChunked(true);
                }
                return true;
            }
            return false;
        }
    }

    private boolean setContentLenth(String value) {
        try {
            setContentLengthLong(Long.parseLong(value));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void flushSpecialHeaders() {
        if (this.contentLength >= 0) {
            this.headers.setValue(Header.ContentLength).setString(String.valueOf(this.contentLength));
        }
        String ct = getContentType();
        if (ct != null) {
            this.headers.setValue(Header.ContentType).setString(String.valueOf(ct));
        }
        if (!this.upgrade.isNull()) {
            this.headers.setValue(Header.Upgrade).setString(this.upgrade.toString());
        }
    }

    public static abstract class Builder<T extends Builder> {
        protected Boolean chunked;
        protected Long contentLength;
        protected String contentType;
        protected MimeHeaders mimeHeaders;
        protected Protocol protocol;
        protected String protocolString;
        protected String upgrade;

        /* access modifiers changed from: protected */
        public abstract HttpHeader create();

        public final T protocol(Protocol protocol2) {
            this.protocol = protocol2;
            this.protocolString = null;
            return this;
        }

        public final T protocol(String protocolString2) {
            this.protocolString = protocolString2;
            this.protocol = null;
            return this;
        }

        public final T chunked(boolean chunked2) {
            this.chunked = Boolean.valueOf(chunked2);
            this.contentLength = null;
            return this;
        }

        public final T contentLength(long contentLength2) {
            this.contentLength = Long.valueOf(contentLength2);
            this.chunked = null;
            return this;
        }

        public final T contentType(String contentType2) {
            this.contentType = contentType2;
            return this;
        }

        public final T upgrade(String upgrade2) {
            this.upgrade = upgrade2;
            return this;
        }

        public final T header(String name, String value) {
            if (this.mimeHeaders == null) {
                this.mimeHeaders = new MimeHeaders();
            }
            if (!handleSpecialHeaderAdd(Header.find(name), value)) {
                this.mimeHeaders.addValue(name).setString(value);
            }
            return this;
        }

        public final T removeHeader(String name) {
            MimeHeaders mimeHeaders2 = this.mimeHeaders;
            if (mimeHeaders2 != null) {
                mimeHeaders2.removeHeader(name);
                handleSpecialHeaderRemove(Header.find(name));
            }
            return this;
        }

        public final T header(Header header, String value) {
            if (this.mimeHeaders == null) {
                this.mimeHeaders = new MimeHeaders();
            }
            if (!handleSpecialHeaderAdd(header, value)) {
                this.mimeHeaders.addValue(header).setString(value);
            }
            return this;
        }

        public final T removeHeader(Header header) {
            MimeHeaders mimeHeaders2 = this.mimeHeaders;
            if (mimeHeaders2 != null) {
                mimeHeaders2.removeHeader(header);
                handleSpecialHeaderRemove(header);
            }
            return this;
        }

        public final T maxNumHeaders(int maxHeaders) {
            if (this.mimeHeaders == null) {
                this.mimeHeaders = new MimeHeaders();
            }
            this.mimeHeaders.setMaxNumHeaders(maxHeaders);
            return this;
        }

        public HttpHeader build() {
            HttpHeader httpHeader = create();
            Protocol protocol2 = this.protocol;
            if (protocol2 != null) {
                httpHeader.setProtocol(protocol2);
            }
            String str = this.protocolString;
            if (str != null) {
                httpHeader.protocolC.setString(str);
            }
            Boolean bool = this.chunked;
            if (bool != null) {
                httpHeader.setChunked(bool.booleanValue());
            }
            Long l = this.contentLength;
            if (l != null) {
                httpHeader.setContentLengthLong(l.longValue());
            }
            String str2 = this.contentType;
            if (str2 != null) {
                httpHeader.setContentType(str2);
            }
            String str3 = this.upgrade;
            if (str3 != null) {
                httpHeader.setUpgrade(str3);
            }
            MimeHeaders mimeHeaders2 = this.mimeHeaders;
            if (mimeHeaders2 != null && mimeHeaders2.size() > 0) {
                httpHeader.getHeaders().copyFrom(this.mimeHeaders);
            }
            return httpHeader;
        }

        public void reset() {
            this.protocol = null;
            this.protocolString = null;
            this.chunked = null;
            this.contentLength = null;
            this.contentType = null;
            this.upgrade = null;
            this.mimeHeaders.recycle();
        }

        private boolean handleSpecialHeaderAdd(Header header, String value) {
            if (Header.ContentLength.equals(header)) {
                this.contentLength = Long.valueOf(Long.parseLong(value));
                return true;
            } else if (!Header.Upgrade.equals(header)) {
                return false;
            } else {
                this.upgrade = value;
                return true;
            }
        }

        private void handleSpecialHeaderRemove(Header header) {
            if (Header.ContentLength.equals(header)) {
                this.contentLength = null;
            } else if (Header.Upgrade.equals(header)) {
                this.upgrade = null;
            }
        }
    }
}
