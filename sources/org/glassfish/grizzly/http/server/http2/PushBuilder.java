package org.glassfish.grizzly.http.server.http2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Session;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.MimeHeaders;

public final class PushBuilder {
    private static final Header[] CONDITIONAL_HEADERS;
    private static final Header[] REMOVE_HEADERS;
    List<Cookie> cookies;
    MimeHeaders headers;
    String method = Method.GET.getMethodString();
    String path;
    String queryString;
    Request request;
    boolean sessionFromURL;
    String sessionId;

    static {
        Header header = Header.IfModifiedSince;
        Header header2 = Header.IfNoneMatch;
        Header header3 = Header.IfRange;
        Header header4 = Header.IfUnmodifiedSince;
        Header header5 = Header.IfMatch;
        Header header6 = Header.AcceptRanges;
        REMOVE_HEADERS = new Header[]{Header.Cookie, Header.ETag, header, header2, header3, header4, header5, Header.LastModified, Header.Referer, header6, Header.Range, header6, Header.ContentRange, Header.Authorization, Header.ProxyAuthenticate, Header.ProxyAuthorization, Header.WWWAuthenticate};
        CONDITIONAL_HEADERS = new Header[]{header, header2, header3, header4, header5};
    }

    public PushBuilder(Request request2) {
        this.request = request2;
        MimeHeaders mimeHeaders = new MimeHeaders();
        this.headers = mimeHeaders;
        mimeHeaders.copyFrom(request2.getRequest().getHeaders());
        for (Header removeHeader : REMOVE_HEADERS) {
            this.headers.removeHeader(removeHeader);
        }
        this.headers.setValue(Header.Referer).setString(composeReferrerHeader(request2));
        Session session = request2.getSession(false);
        if (session != null) {
            this.sessionId = session.getIdInternal();
        }
        if (this.sessionId == null) {
            this.sessionId = request2.getRequestedSessionId();
        }
        this.sessionFromURL = request2.isRequestedSessionIdFromURL();
        Cookie[] requestCookies = request2.getCookies();
        if (requestCookies != null) {
            this.cookies = new ArrayList(Arrays.asList(requestCookies));
        }
        Cookie[] responseCookies = request2.getResponse().getCookies();
        if (responseCookies != null) {
            if (this.cookies == null) {
                this.cookies = new ArrayList(responseCookies.length);
            }
            for (Cookie c : responseCookies) {
                if (c.getMaxAge() > 0) {
                    this.cookies.add(new Cookie(c.getName(), c.getValue()));
                } else {
                    int jlen = this.cookies.size();
                    for (int j = 0; j < jlen; j++) {
                        if (this.cookies.get(j).getName().equals(c.getName())) {
                            this.cookies.remove(j);
                        }
                    }
                }
            }
        }
        List<Cookie> list = this.cookies;
        if (list != null && !list.isEmpty()) {
            int len = this.cookies.size();
            for (int i = 0; i < len; i++) {
                this.headers.addValue(Header.Cookie).setString(this.cookies.get(i).asClientCookieString());
            }
        }
    }

    public PushBuilder method(String method2) {
        if (method2 == null) {
            throw new NullPointerException();
        } else if (Method.POST.getMethodString().equals(method2) || Method.PUT.getMethodString().equals(method2) || Method.DELETE.getMethodString().equals(method2) || Method.CONNECT.getMethodString().equals(method2) || Method.OPTIONS.getMethodString().equals(method2) || Method.TRACE.getMethodString().equals(method2)) {
            throw new IllegalArgumentException();
        } else {
            this.method = method2;
            return this;
        }
    }

    public PushBuilder queryString(String queryString2) {
        this.queryString = validate(queryString2);
        return this;
    }

    public PushBuilder sessionId(String sessionId2) {
        this.sessionId = validate(sessionId2);
        return this;
    }

    public PushBuilder setHeader(String name, String value) {
        if (nameAndValueValid(name, value)) {
            this.headers.setValue(name).setString(value);
        }
        return this;
    }

    public PushBuilder setHeader(Header name, String value) {
        if (name != null && validValue(value)) {
            this.headers.setValue(name).setString(value);
        }
        return this;
    }

    public PushBuilder addHeader(String name, String value) {
        if (nameAndValueValid(name, value)) {
            this.headers.addValue(name).setString(value);
        }
        return this;
    }

    public PushBuilder addHeader(Header name, String value) {
        if (name != null && validValue(value)) {
            this.headers.addValue(name).setString(value);
        }
        return this;
    }

    public PushBuilder removeHeader(String name) {
        if (validValue(name) && !Header.Referer.getLowerCase().equals(name.toLowerCase())) {
            this.headers.removeHeader(name);
        }
        return this;
    }

    public PushBuilder removeHeader(Header name) {
        if (!(name == null || Header.Referer == name)) {
            this.headers.removeHeader(name);
        }
        return this;
    }

    public PushBuilder path(String path2) {
        this.path = validate(path2);
        return this;
    }

    public void push() {
        String pathLocal;
        String str;
        if (this.path == null) {
            throw new IllegalStateException();
        } else if (this.request.isPushEnabled()) {
            if (this.path.charAt(0) == '/') {
                pathLocal = this.path;
            } else {
                pathLocal = this.request.getContextPath() + '/' + this.path;
            }
            if (this.queryString != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(pathLocal);
                if (pathLocal.indexOf(63) != -1) {
                    str = '&' + this.queryString;
                } else {
                    str = '?' + this.queryString;
                }
                sb.append(str);
                pathLocal = sb.toString();
            }
            if (this.sessionId != null) {
                if (this.sessionFromURL) {
                    pathLocal = pathLocal + ';' + this.request.getSessionCookieName() + '=' + this.sessionId;
                } else {
                    this.headers.addValue(Header.Cookie).setString(new Cookie(this.request.getSessionCookieName(), this.sessionId).asClientCookieString());
                }
            }
            this.path = pathLocal;
            if (this.request.getContext().getConnection().isOpen()) {
                this.request.getContext().notifyDownstream(PushEvent.create(this));
                this.path = null;
                for (Header removeHeader : CONDITIONAL_HEADERS) {
                    this.headers.removeHeader(removeHeader);
                }
                return;
            }
            throw new UncheckedIOException("Unable to push: connection closed", new IOException());
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public Iterable<String> getHeaderNames() {
        return this.headers.names();
    }

    public String getHeader(String name) {
        return this.headers.getHeader(name);
    }

    public String getPath() {
        return this.path;
    }

    private static boolean nameAndValueValid(String name, String value) {
        return validValue(name) && validValue(value);
    }

    private static boolean validValue(String value) {
        return value != null && !value.isEmpty();
    }

    private static String validate(String value) {
        if (validValue(value)) {
            return value;
        }
        return null;
    }

    private String composeReferrerHeader(Request request2) {
        StringBuilder sb = new StringBuilder(64);
        String queryString2 = request2.getQueryString();
        sb.append(request2.getRequestURL());
        if (queryString2 != null) {
            sb.append('?');
            sb.append(queryString2);
        }
        return sb.toString();
    }
}
