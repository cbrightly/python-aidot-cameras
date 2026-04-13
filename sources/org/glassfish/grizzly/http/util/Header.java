package org.glassfish.grizzly.http.util;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.http.HttpHeader;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public enum Header {
    Accept("Accept"),
    AcceptCharset("Accept-Charset"),
    AcceptEncoding(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING),
    AcceptRanges("Accept-Ranges"),
    Age("Age"),
    Allow(JsonDocumentFields.EFFECT_VALUE_ALLOW),
    Authorization("Authorization"),
    CacheControl(HttpHeaders.HEAD_KEY_CACHE_CONTROL),
    Cookie(HttpHeaders.HEAD_KEY_COOKIE),
    Connection("Connection"),
    ContentDisposition(HttpHeaders.HEAD_KEY_CONTENT_DISPOSITION),
    ContentEncoding(HttpHeaders.HEAD_KEY_CONTENT_ENCODING),
    ContentLanguage("Content-Language"),
    ContentLength("Content-Length"),
    ContentLocation("Content-Location"),
    ContentMD5("Content-MD5"),
    ContentRange(HttpHeaders.HEAD_KEY_CONTENT_RANGE),
    ContentType("Content-Type"),
    Date("Date"),
    ETag(HttpHeaders.HEAD_KEY_E_TAG),
    Expect(HttpHeader.EXPECT),
    Expires(HttpHeaders.HEAD_KEY_EXPIRES),
    From("From"),
    Host("Host"),
    IfMatch("If-Match"),
    IfModifiedSince(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE),
    IfNoneMatch(HttpHeaders.HEAD_KEY_IF_NONE_MATCH),
    IfRange("If-Range"),
    IfUnmodifiedSince("If-Unmodified-Since"),
    KeepAlive("Keep-Alive"),
    LastModified(HttpHeaders.HEAD_KEY_LAST_MODIFIED),
    Location("Location"),
    MaxForwards("Max-Forwards"),
    Pragma(HttpHeaders.HEAD_KEY_PRAGMA),
    ProxyAuthenticate("Proxy-Authenticate"),
    ProxyAuthorization("Proxy-Authorization"),
    ProxyConnection("Proxy-Connection"),
    Range(HttpHeaders.HEAD_KEY_RANGE),
    Referer("Referer"),
    RetryAfter(UpgradeResponse.RETRY_AFTER),
    Server("Server"),
    SetCookie(HttpHeaders.HEAD_KEY_SET_COOKIE),
    TE("TE"),
    Trailer("Trailer"),
    TransferEncoding(Constants.TRANSFERENCODING),
    Upgrade(UpgradeRequest.UPGRADE),
    UserAgent("User-Agent"),
    Vary("Vary"),
    Via("Via"),
    Warnings("Warning"),
    WWWAuthenticate(UpgradeResponse.WWW_AUTHENTICATE),
    XPoweredBy("X-Powered-By"),
    HTTP2Settings("HTTP2-Settings");
    
    private static final Map<String, Header> VALUES = null;
    private final String headerName;
    private final byte[] headerNameBytes;
    private final String headerNameLowerCase;
    private final byte[] headerNameLowerCaseBytes;
    private final int length;

    static {
        int i;
        VALUES = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (Header h : values()) {
            VALUES.put(h.toString(), h);
        }
    }

    private Header(String headerName2) {
        this.headerName = headerName2;
        Charset charset = Charsets.ASCII_CHARSET;
        byte[] bytes = headerName2.getBytes(charset);
        this.headerNameBytes = bytes;
        String lowerCase = headerName2.toLowerCase(Locale.ENGLISH);
        this.headerNameLowerCase = lowerCase;
        this.headerNameLowerCaseBytes = lowerCase.getBytes(charset);
        this.length = bytes.length;
    }

    public final byte[] getBytes() {
        return this.headerNameBytes;
    }

    public final String getLowerCase() {
        return this.headerNameLowerCase;
    }

    public final byte[] getLowerCaseBytes() {
        return this.headerNameLowerCaseBytes;
    }

    public final int getLength() {
        return this.length;
    }

    public final String toString() {
        return this.headerName;
    }

    public final byte[] toByteArray() {
        return this.headerNameBytes;
    }

    public static Header find(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return VALUES.get(name);
    }
}
