package org.glassfish.grizzly.http;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.util.BufferChunk;
import org.glassfish.grizzly.http.util.ByteChunk;
import org.glassfish.grizzly.http.util.CookieParserUtils;
import org.glassfish.grizzly.http.util.CookieUtils;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.MimeHeaders;

public final class Cookies {
    private static final Cookie[] EMPTY_COOKIE_ARRAY = new Cookie[0];
    private static final int INITIAL_SIZE = 4;
    static final char[] SEPARATORS = {9, ' ', StringUtil.DOUBLE_QUOTE, '\'', '(', ')', StringUtil.COMMA, ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '{', '}'};
    private static final Logger logger = Grizzly.logger(Cookies.class);
    static final boolean[] separators = new boolean[128];
    private Cookie[] cookies = new Cookie[4];
    private MimeHeaders headers;
    private boolean isProcessed;
    private boolean isRequest;
    private int nextUnusedCookieIndex = 0;
    private Cookie[] processedCookies;
    private int storedCookieCount;

    static {
        for (int i = 0; i < 128; i++) {
            separators[i] = false;
        }
        int i2 = 0;
        while (true) {
            char[] cArr = SEPARATORS;
            if (i2 < cArr.length) {
                separators[cArr[i2]] = true;
                i2++;
            } else {
                return;
            }
        }
    }

    public boolean initialized() {
        return this.headers != null;
    }

    public Cookie[] get() {
        if (!this.isProcessed) {
            this.isProcessed = true;
            if (this.isRequest) {
                processClientCookies();
            } else {
                processServerCookies();
            }
            int i = this.nextUnusedCookieIndex;
            this.processedCookies = i > 0 ? copyTo(new Cookie[i]) : EMPTY_COOKIE_ARRAY;
        }
        return this.processedCookies;
    }

    public void setHeaders(MimeHeaders headers2) {
        setHeaders(headers2, true);
    }

    public void setHeaders(MimeHeaders headers2, boolean isRequest2) {
        this.headers = headers2;
        this.isRequest = isRequest2;
    }

    public Cookie getNextUnusedCookie() {
        int i = this.nextUnusedCookieIndex;
        if (i < this.storedCookieCount) {
            Cookie[] cookieArr = this.cookies;
            this.nextUnusedCookieIndex = i + 1;
            return cookieArr[i];
        }
        Cookie cookie = new Cookie();
        int i2 = this.nextUnusedCookieIndex;
        Cookie[] cookieArr2 = this.cookies;
        if (i2 == cookieArr2.length) {
            Cookie[] temp = new Cookie[(cookieArr2.length + 4)];
            System.arraycopy(cookieArr2, 0, temp, 0, cookieArr2.length);
            this.cookies = temp;
        }
        this.storedCookieCount++;
        Cookie[] cookieArr3 = this.cookies;
        int i3 = this.nextUnusedCookieIndex;
        this.nextUnusedCookieIndex = i3 + 1;
        cookieArr3[i3] = cookie;
        return cookie;
    }

    public void recycle() {
        for (int i = 0; i < this.nextUnusedCookieIndex; i++) {
            this.cookies[i].recycle();
        }
        this.processedCookies = null;
        this.nextUnusedCookieIndex = 0;
        this.headers = null;
        this.isRequest = false;
        this.isProcessed = false;
    }

    private Cookie[] copyTo(Cookie[] destination) {
        int i = this.nextUnusedCookieIndex;
        if (i > 0) {
            System.arraycopy(this.cookies, 0, destination, 0, i);
        }
        return destination;
    }

    private void processClientCookies() {
        if (this.headers != null) {
            int pos = 0;
            while (pos >= 0) {
                int pos2 = this.headers.indexOf(Header.Cookie, pos);
                if (pos2 >= 0) {
                    DataChunk cookieValue = this.headers.getValue(pos2);
                    if (cookieValue == null || cookieValue.isNull()) {
                        pos = pos2 + 1;
                    } else {
                        if (cookieValue.getType() == DataChunk.Type.Bytes) {
                            if (logger.isLoggable(Level.FINE)) {
                                log("Parsing b[]: " + cookieValue.toString());
                            }
                            ByteChunk byteChunk = cookieValue.getByteChunk();
                            CookieParserUtils.parseClientCookies(this, byteChunk.getBuffer(), byteChunk.getStart(), byteChunk.getLength());
                        } else if (cookieValue.getType() == DataChunk.Type.Buffer) {
                            if (logger.isLoggable(Level.FINE)) {
                                log("Parsing buffer: " + cookieValue.toString());
                            }
                            BufferChunk bufferChunk = cookieValue.getBufferChunk();
                            CookieParserUtils.parseClientCookies(this, bufferChunk.getBuffer(), bufferChunk.getStart(), bufferChunk.getLength());
                        } else {
                            if (logger.isLoggable(Level.FINE)) {
                                log("Parsing string: " + cookieValue.toString());
                            }
                            CookieParserUtils.parseClientCookies(this, cookieValue.toString(), CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
                        }
                        pos = pos2 + 1;
                    }
                } else {
                    return;
                }
            }
        }
    }

    private void processServerCookies() {
        if (this.headers != null) {
            int pos = 0;
            while (pos >= 0) {
                int pos2 = this.headers.indexOf(Header.SetCookie, pos);
                if (pos2 >= 0) {
                    DataChunk cookieValue = this.headers.getValue(pos2);
                    if (cookieValue == null || cookieValue.isNull()) {
                        pos = pos2 + 1;
                    } else {
                        if (cookieValue.getType() == DataChunk.Type.Bytes) {
                            if (logger.isLoggable(Level.FINE)) {
                                log("Parsing b[]: " + cookieValue.toString());
                            }
                            ByteChunk byteChunk = cookieValue.getByteChunk();
                            CookieParserUtils.parseServerCookies(this, byteChunk.getBuffer(), byteChunk.getStart(), byteChunk.getLength(), CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
                        } else if (cookieValue.getType() == DataChunk.Type.Buffer) {
                            if (logger.isLoggable(Level.FINE)) {
                                log("Parsing b[]: " + cookieValue.toString());
                            }
                            BufferChunk bufferChunk = cookieValue.getBufferChunk();
                            CookieParserUtils.parseServerCookies(this, bufferChunk.getBuffer(), bufferChunk.getStart(), bufferChunk.getLength(), CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
                        } else {
                            if (logger.isLoggable(Level.FINE)) {
                                log("Parsing string: " + cookieValue.toString());
                            }
                            CookieParserUtils.parseServerCookies(this, cookieValue.toString(), CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
                        }
                        pos = pos2 + 1;
                    }
                } else {
                    return;
                }
            }
        }
    }

    public String toString() {
        return Arrays.toString(this.cookies);
    }

    private static void log(String s) {
        Logger logger2 = logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            logger2.log(level, "Cookies: {0}", s);
        }
    }

    public Cookie findByName(String cookieName) {
        for (Cookie cookie : get()) {
            if (cookie.lazyNameEquals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }
}
