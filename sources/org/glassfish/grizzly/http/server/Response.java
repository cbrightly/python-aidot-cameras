package org.glassfish.grizzly.http.server;

import com.yanzhenjie.andserver.util.h;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.l;
import org.glassfish.grizzly.CloseListener;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GenericCloseListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Cookies;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.io.NIOWriter;
import org.glassfish.grizzly.http.io.OutputBuffer;
import org.glassfish.grizzly.http.server.io.ServerOutputBuffer;
import org.glassfish.grizzly.http.server.util.Globals;
import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.util.CharChunk;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.CookieSerializerUtils;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.HttpRequestURIDecoder;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.http.util.UEncoder;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.DelayedExecutor;

public class Response {
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();
    private static final String HTTP_RESPONSE_DATE_HEADER = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final Logger LOGGER = Grizzly.logger(Response.class);
    protected static final String info = "org.glassfish.grizzly.http.server.Response/2.0";
    protected boolean appCommitted = false;
    private boolean cacheEnabled = false;
    protected FilterChainContext ctx;
    protected DelayedExecutor.DelayQueue<SuspendTimeout> delayQueue;
    protected boolean error = false;
    private ErrorPageGenerator errorPageGenerator;
    protected SimpleDateFormat format = null;
    protected HttpContext httpContext;
    protected final ServerOutputBuffer outputBuffer = new ServerOutputBuffer();
    private final NIOOutputStreamImpl outputStream = new NIOOutputStreamImpl();
    protected final CharChunk redirectURLCC;
    protected Request request = null;
    protected HttpResponsePacket response;
    private boolean sendFileEnabled;
    SuspendState suspendState;
    /* access modifiers changed from: private */
    public SuspendStatus suspendStatus;
    /* access modifiers changed from: private */
    public final SuspendedContextImpl suspendedContext;
    protected final UEncoder urlEncoder;
    protected boolean usingOutputStream = false;
    protected boolean usingWriter = false;
    private final NIOWriterImpl writer = new NIOWriterImpl();

    public enum SuspendState {
        NONE,
        SUSPENDED,
        RESUMING,
        RESUMED,
        CANCELLING,
        CANCELLED
    }

    static DelayedExecutor.DelayQueue<SuspendTimeout> createDelayQueue(DelayedExecutor delayedExecutor) {
        return delayedExecutor.createDelayQueue(new DelayQueueWorker(), new DelayQueueResolver());
    }

    protected Response() {
        UEncoder uEncoder = new UEncoder();
        this.urlEncoder = uEncoder;
        this.redirectURLCC = new CharChunk();
        this.suspendState = SuspendState.NONE;
        this.suspendedContext = new SuspendedContextImpl();
        uEncoder.addSafeCharacter('/');
    }

    public void initialize(Request request2, HttpResponsePacket response2, FilterChainContext ctx2, DelayedExecutor.DelayQueue<SuspendTimeout> delayQueue2, HttpServerFilter serverFilter) {
        this.request = request2;
        this.response = response2;
        this.sendFileEnabled = serverFilter != null && serverFilter.getConfiguration().isSendFileEnabled();
        this.outputBuffer.initialize(this, ctx2);
        this.ctx = ctx2;
        this.httpContext = HttpContext.get(ctx2);
        this.delayQueue = delayQueue2;
    }

    /* access modifiers changed from: package-private */
    public SuspendStatus initSuspendStatus() {
        SuspendStatus create = SuspendStatus.create();
        this.suspendStatus = create;
        return create;
    }

    public Request getRequest() {
        return this.request;
    }

    public HttpResponsePacket getResponse() {
        return this.response;
    }

    /* access modifiers changed from: protected */
    public void recycle() {
        this.delayQueue = null;
        this.outputBuffer.recycle();
        this.outputStream.recycle();
        this.writer.recycle();
        this.usingOutputStream = false;
        this.usingWriter = false;
        this.appCommitted = false;
        this.error = false;
        this.errorPageGenerator = null;
        this.request = null;
        this.response.recycle();
        this.sendFileEnabled = false;
        this.response = null;
        this.ctx = null;
        this.suspendState = SuspendState.NONE;
        this.cacheEnabled = false;
    }

    public void setTrailers(Supplier<Map<String, String>> trailerSupplier) {
        if (!isCommitted()) {
            Protocol protocol = this.request.getProtocol();
            if (protocol.equals(Protocol.HTTP_0_9) || protocol.equals(Protocol.HTTP_1_0)) {
                throw new IllegalStateException("Trailers not supported by response protocol version " + protocol);
            }
            if (protocol.equals(Protocol.HTTP_1_1)) {
                if (this.response.isChunkingAllowed()) {
                    this.response.setChunked(true);
                } else {
                    throw new IllegalStateException("Chunked transfer-encoding disabled.");
                }
            }
            this.outputBuffer.setTrailers(trailerSupplier);
            return;
        }
        throw new IllegalStateException("Response has already been committed.");
    }

    public Supplier<Map<String, String>> getTrailers() {
        return this.outputBuffer.getTrailers();
    }

    public String encodeURL(String url) {
        String absolute = toAbsolute(url, false);
        if (!isEncodeable(absolute)) {
            return url;
        }
        if (url.equalsIgnoreCase("")) {
            url = absolute;
        }
        return toEncoded(url, this.request.getSession().getIdInternal());
    }

    public String encodeRedirectURL(String url) {
        if (isEncodeable(toAbsolute(url, false))) {
            return toEncoded(url, this.request.getSession().getIdInternal());
        }
        return url;
    }

    /* access modifiers changed from: protected */
    public boolean isEncodeable(String location) {
        Session session;
        if (location != null && !location.startsWith("#") && (session = this.request.getSession(false)) != null && !this.request.isRequestedSessionIdFromCookie() && doIsEncodeable(this.request, session, location)) {
            return true;
        }
        return false;
    }

    private static boolean doIsEncodeable(Request request2, Session session, String location) {
        String file;
        try {
            URL url = new URL(location);
            if (!request2.getScheme().equalsIgnoreCase(url.getProtocol()) || !request2.getServerName().equalsIgnoreCase(url.getHost())) {
                return false;
            }
            int serverPort = request2.getServerPort();
            if (serverPort == -1) {
                if ("https".equals(request2.getScheme())) {
                    serverPort = 443;
                } else {
                    serverPort = 80;
                }
            }
            int urlPort = url.getPort();
            if (urlPort == -1) {
                if ("https".equals(url.getProtocol())) {
                    urlPort = 443;
                } else {
                    urlPort = 80;
                }
            }
            if (serverPort != urlPort || (file = url.getFile()) == null || !file.startsWith("/")) {
                return false;
            }
            if (file.contains(";jsessionid=" + session.getIdInternal())) {
                return false;
            }
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public String getInfo() {
        return info;
    }

    public void setError() {
        this.error = true;
    }

    public boolean isError() {
        return this.error;
    }

    public ErrorPageGenerator getErrorPageGenerator() {
        return this.errorPageGenerator;
    }

    public void setErrorPageGenerator(ErrorPageGenerator errorPageGenerator2) {
        this.errorPageGenerator = errorPageGenerator2;
    }

    public void setDetailMessage(String message) {
        checkResponse();
        this.response.setReasonPhrase(message);
    }

    public String getDetailMessage() {
        checkResponse();
        return this.response.getReasonPhrase();
    }

    public void finish() {
        try {
            this.outputBuffer.endRequest();
        } catch (IOException e) {
            Logger logger = LOGGER;
            Level level = Level.FINEST;
            if (logger.isLoggable(level)) {
                logger.log(level, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_RESPONSE_FINISH_ERROR(), e);
            }
        } catch (Throwable t) {
            Logger logger2 = LOGGER;
            Level level2 = Level.WARNING;
            if (logger2.isLoggable(level2)) {
                logger2.log(level2, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_RESPONSE_FINISH_ERROR(), t);
            }
        }
    }

    public int getContentLength() {
        checkResponse();
        return (int) this.response.getContentLength();
    }

    public long getContentLengthLong() {
        checkResponse();
        return this.response.getContentLength();
    }

    public String getContentType() {
        checkResponse();
        return this.response.getContentType();
    }

    public int getBufferSize() {
        return this.outputBuffer.getBufferSize();
    }

    public String getCharacterEncoding() {
        checkResponse();
        String characterEncoding = this.response.getCharacterEncoding();
        if (characterEncoding == null) {
            return Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
        }
        return characterEncoding;
    }

    public void setCharacterEncoding(String charset) {
        checkResponse();
        if (!isCommitted() && !this.usingWriter) {
            this.response.setCharacterEncoding(charset);
        }
    }

    public NIOOutputStream createOutputStream() {
        this.outputStream.setOutputBuffer(this.outputBuffer);
        return this.outputStream;
    }

    public NIOOutputStream getNIOOutputStream() {
        if (!this.usingWriter) {
            this.usingOutputStream = true;
            this.outputStream.setOutputBuffer(this.outputBuffer);
            return this.outputStream;
        }
        throw new IllegalStateException("Illegal attempt to call getOutputStream() after getWriter() has already been called.");
    }

    public OutputStream getOutputStream() {
        return getNIOOutputStream();
    }

    public Locale getLocale() {
        checkResponse();
        Locale locale = this.response.getLocale();
        if (locale != null) {
            return locale;
        }
        Locale locale2 = DEFAULT_LOCALE;
        this.response.setLocale(locale2);
        return locale2;
    }

    public Writer getWriter() {
        return getNIOWriter();
    }

    public NIOWriter getNIOWriter() {
        if (!this.usingOutputStream) {
            setCharacterEncoding(getCharacterEncoding());
            this.usingWriter = true;
            this.outputBuffer.prepareCharacterEncoder();
            this.writer.setOutputBuffer(this.outputBuffer);
            return this.writer;
        }
        throw new IllegalStateException("Illegal attempt to call getWriter() after getOutputStream() has already been called.");
    }

    public boolean isCommitted() {
        checkResponse();
        return this.response.isCommitted();
    }

    public void flush() {
        this.outputBuffer.flush();
    }

    public OutputBuffer getOutputBuffer() {
        return this.outputBuffer;
    }

    public void reset() {
        checkResponse();
        if (!isCommitted()) {
            this.response.getHeaders().clear();
            this.response.setContentLanguage((String) null);
            if (this.response.getContentLength() > 0) {
                this.response.setContentLengthLong(-1);
            }
            this.response.setCharacterEncoding((String) null);
            this.response.setStatus((HttpStatus) null);
            this.response.setContentType((String) null);
            this.response.setLocale((Locale) null);
            this.outputBuffer.reset();
            this.usingWriter = false;
            this.usingOutputStream = false;
            return;
        }
        throw new IllegalStateException();
    }

    public void resetBuffer() {
        resetBuffer(false);
    }

    public void resetBuffer(boolean resetWriterStreamFlags) {
        if (!isCommitted()) {
            this.outputBuffer.reset();
            if (resetWriterStreamFlags) {
                this.usingOutputStream = false;
                this.usingWriter = false;
                return;
            }
            return;
        }
        throw new IllegalStateException("Cannot reset buffer after response has been committed.");
    }

    public void setBufferSize(int size) {
        if (!isCommitted()) {
            this.outputBuffer.setBufferSize(size);
            return;
        }
        throw new IllegalStateException("Unable to change buffer size as the response has been committed");
    }

    public void setContentLengthLong(long length) {
        checkResponse();
        if (!isCommitted() && !this.usingWriter) {
            this.response.setContentLengthLong(length);
        }
    }

    public void setContentLength(int length) {
        setContentLengthLong((long) length);
    }

    public void setContentType(String type) {
        int index;
        checkResponse();
        if (!isCommitted()) {
            if (!(!this.usingWriter || type == null || (index = type.indexOf(com.meituan.robust.Constants.PACKNAME_END)) == -1)) {
                type = type.substring(0, index);
            }
            this.response.setContentType(type);
        }
    }

    public void setContentType(ContentType type) {
        checkResponse();
        if (!isCommitted()) {
            if (type == null) {
                this.response.setContentType((String) null);
            } else if (!this.usingWriter) {
                this.response.setContentType(type);
            } else {
                this.response.setContentType(type.getMimeType());
            }
        }
    }

    public void setLocale(Locale locale) {
        checkResponse();
        if (!isCommitted()) {
            this.response.setLocale(locale);
        }
    }

    public Cookie[] getCookies() {
        Cookies cookies = new Cookies();
        cookies.setHeaders(this.response.getHeaders(), false);
        return cookies.get();
    }

    public String getHeader(String name) {
        checkResponse();
        return this.response.getHeader(name);
    }

    public String[] getHeaderNames() {
        checkResponse();
        MimeHeaders headers = this.response.getHeaders();
        int n = headers.size();
        String[] result = new String[n];
        for (int i = 0; i < n; i++) {
            result[i] = headers.getName(i).toString();
        }
        return result;
    }

    public String[] getHeaderValues(String name) {
        checkResponse();
        Collection<String> result = new LinkedList<>();
        for (String headerValue : this.response.getHeaders().values(name)) {
            result.add(headerValue);
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    public String getMessage() {
        checkResponse();
        return this.response.getReasonPhrase();
    }

    public int getStatus() {
        checkResponse();
        return this.response.getStatus();
    }

    public void reset(int status, String message) {
        reset();
        setStatus(status, message);
    }

    public void addCookie(final Cookie cookie) {
        if (!isCommitted()) {
            final StringBuilder sb = new StringBuilder();
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        CookieSerializerUtils.serializeServerCookie(sb, cookie);
                        return null;
                    }
                });
            } else {
                CookieSerializerUtils.serializeServerCookie(sb, cookie);
            }
            addHeader(Header.SetCookie, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void addSessionCookieInternal(final Cookie cookie) {
        if (!isCommitted()) {
            String name = cookie.getName();
            String headername = Header.SetCookie.toString();
            String startsWith = name + "=";
            final StringBuilder sb = new StringBuilder();
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    public Object run() {
                        CookieSerializerUtils.serializeServerCookie(sb, cookie);
                        return null;
                    }
                });
            } else {
                CookieSerializerUtils.serializeServerCookie(sb, cookie);
            }
            String cookieString = sb.toString();
            boolean set = false;
            MimeHeaders headers = this.response.getHeaders();
            int n = headers.size();
            for (int i = 0; i < n; i++) {
                if (headers.getName(i).toString().equals(headername) && headers.getValue(i).toString().startsWith(startsWith)) {
                    headers.getValue(i).setString(cookieString);
                    set = true;
                }
            }
            if (!set) {
                addHeader(headername, cookieString);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeSessionCookies() {
        String pattern;
        String sessionCookieName = this.request.getSessionCookieName();
        if (sessionCookieName != null) {
            pattern = '^' + sessionCookieName + "(?:SSO)?=.*";
        } else {
            pattern = Globals.SESSION_COOKIE_PATTERN;
        }
        this.response.getHeaders().removeHeaderMatches(Header.SetCookie, pattern);
    }

    public void addDateHeader(String name, long value) {
        if (!isCommitted()) {
            if (this.format == null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HTTP_RESPONSE_DATE_HEADER, Locale.US);
                this.format = simpleDateFormat;
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            }
            addHeader(name, FastHttpDateFormat.formatDate(value, this.format));
        }
    }

    public void addDateHeader(Header header, long value) {
        if (!isCommitted()) {
            if (this.format == null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HTTP_RESPONSE_DATE_HEADER, Locale.US);
                this.format = simpleDateFormat;
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            }
            addHeader(header, FastHttpDateFormat.formatDate(value, this.format));
        }
    }

    public void addHeader(String name, String value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.addHeader(name, value);
        }
    }

    public void addHeader(String name, HeaderValue value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.addHeader(name, value);
        }
    }

    public void addHeader(Header header, String value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.addHeader(header, value);
        }
    }

    public void addHeader(Header header, HeaderValue value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.addHeader(header, value);
        }
    }

    public void addIntHeader(String name, int value) {
        if (!isCommitted()) {
            addHeader(name, "" + value);
        }
    }

    public void addIntHeader(Header header, int value) {
        if (!isCommitted()) {
            addHeader(header, Integer.toString(value));
        }
    }

    public boolean containsHeader(String name) {
        checkResponse();
        return this.response.containsHeader(name);
    }

    public boolean containsHeader(Header header) {
        checkResponse();
        return this.response.containsHeader(header);
    }

    public void sendAcknowledgement() {
        if (!isCommitted() && this.request.requiresAcknowledgement()) {
            this.response.setAcknowledgement(true);
            this.outputBuffer.acknowledge();
        }
    }

    public void sendError(int status) {
        sendError(status, (String) null);
    }

    public void sendError(int status, String message) {
        checkResponse();
        if (!isCommitted()) {
            setError();
            this.response.getHeaders().removeHeader(Header.TransferEncoding);
            this.response.setContentLanguage((String) null);
            this.response.setContentLengthLong(-1);
            this.response.setChunked(false);
            this.response.setCharacterEncoding((String) null);
            this.response.setContentType((String) null);
            this.response.setLocale((Locale) null);
            this.outputBuffer.reset();
            this.usingWriter = false;
            this.usingOutputStream = false;
            setStatus(status, message);
            String nonNullMsg = message;
            if (nonNullMsg == null) {
                HttpStatus httpStatus = HttpStatus.getHttpStatus(status);
                if (httpStatus == null || httpStatus.getReasonPhrase() == null) {
                    nonNullMsg = "Unknown Error";
                } else {
                    nonNullMsg = httpStatus.getReasonPhrase();
                }
            }
            HtmlHelper.sendErrorPage(this.request, this, getErrorPageGenerator(), status, nonNullMsg, nonNullMsg, (Throwable) null);
            finish();
            return;
        }
        throw new IllegalStateException("Illegal attempt to call sendError() after the response has been committed.");
    }

    public void sendRedirect(String location) {
        if (!isCommitted()) {
            resetBuffer();
            try {
                String absolute = toAbsolute(location, true);
                setStatus(HttpStatus.FOUND_302);
                setHeader(Header.Location, absolute);
                setContentType(h.TEXT_HTML_VALUE);
                setLocale(Locale.getDefault());
                String filteredMsg = filter(absolute);
                StringBuilder sb = new StringBuilder(absolute.length() + 150);
                sb.append("<html>\r\n");
                sb.append("<head><title>Document moved</title></head>\r\n");
                sb.append("<body><h1>Document moved</h1>\r\n");
                sb.append("This document has moved <a href=\"");
                sb.append(filteredMsg);
                sb.append("\">here</a>.<p>\r\n");
                sb.append("</body>\r\n");
                sb.append("</html>\r\n");
                try {
                    getWriter().write(sb.toString());
                    getWriter().flush();
                } catch (IllegalStateException e) {
                    try {
                        getOutputStream().write(sb.toString().getBytes(Constants.DEFAULT_HTTP_CHARSET));
                    } catch (IllegalStateException e2) {
                    }
                }
            } catch (IllegalArgumentException e3) {
                sendError(404);
            }
            finish();
            return;
        }
        throw new IllegalStateException("Illegal attempt to redirect the response as the response has been committed.");
    }

    public void setDateHeader(String name, long value) {
        if (!isCommitted()) {
            if (this.format == null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HTTP_RESPONSE_DATE_HEADER, Locale.US);
                this.format = simpleDateFormat;
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            }
            setHeader(name, FastHttpDateFormat.formatDate(value, this.format));
        }
    }

    public void setDateHeader(Header header, long value) {
        if (!isCommitted()) {
            if (this.format == null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HTTP_RESPONSE_DATE_HEADER, Locale.US);
                this.format = simpleDateFormat;
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            }
            setHeader(header, FastHttpDateFormat.formatDate(value, this.format));
        }
    }

    public void setHeader(String name, String value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.setHeader(name, value);
        }
    }

    public void setHeader(String name, HeaderValue value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.setHeader(name, value);
        }
    }

    public void setHeader(Header header, String value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.setHeader(header, value);
        }
    }

    public void setHeader(Header header, HeaderValue value) {
        checkResponse();
        if (!isCommitted()) {
            this.response.setHeader(header, value);
        }
    }

    public void setIntHeader(String name, int value) {
        if (!isCommitted()) {
            setHeader(name, "" + value);
        }
    }

    public void setIntHeader(Header header, int value) {
        if (!isCommitted()) {
            setHeader(header, Integer.toString(value));
        }
    }

    public void setStatus(int status) {
        setStatus(status, (String) null);
    }

    public void setStatus(int status, String message) {
        checkResponse();
        if (!isCommitted()) {
            this.response.setStatus(status);
            this.response.setReasonPhrase(message);
        }
    }

    public void setStatus(HttpStatus status) {
        checkResponse();
        if (!isCommitted()) {
            status.setValues(this.response);
        }
    }

    /* access modifiers changed from: protected */
    public String toAbsolute(String location, boolean normalize) {
        String encodedURI;
        if (location == null) {
            return null;
        }
        boolean leadingSlash = location.startsWith("/");
        if (!leadingSlash && location.contains("://")) {
            return location;
        }
        String scheme = this.request.getScheme();
        String name = this.request.getServerName();
        int port = this.request.getServerPort();
        this.redirectURLCC.recycle();
        CharChunk cc = this.redirectURLCC;
        try {
            cc.append(scheme, 0, scheme.length());
            cc.append("://", 0, 3);
            cc.append(name, 0, name.length());
            if ((scheme.equals(l.DEFAULT_SCHEME_NAME) && port != 80) || (scheme.equals("https") && port != 443)) {
                cc.append(':');
                String portS = port + "";
                cc.append(portS, 0, portS.length());
            }
            if (!leadingSlash) {
                String relativePath = this.request.getDecodedRequestURI();
                String relativePath2 = relativePath.substring(0, relativePath.lastIndexOf(47));
                if (System.getSecurityManager() != null) {
                    final String frelativePath = relativePath2;
                    encodedURI = (String) AccessController.doPrivileged(new PrivilegedExceptionAction<String>() {
                        public String run() {
                            return Response.this.urlEncoder.encodeURL(frelativePath);
                        }
                    });
                } else {
                    encodedURI = this.urlEncoder.encodeURL(relativePath2);
                }
                cc.append(encodedURI, 0, encodedURI.length());
                cc.append('/');
            }
            cc.append(location, 0, location.length());
            if (normalize) {
                HttpRequestURIDecoder.normalizeChars(cc);
            }
            return cc.toString();
        } catch (PrivilegedActionException pae) {
            throw new IllegalArgumentException(location, pae.getCause());
        } catch (IOException e) {
            throw new IllegalArgumentException(location, e);
        }
    }

    public static String filter(String message) {
        if (message == null) {
            return null;
        }
        char[] content = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '\"':
                    result.append("&quot;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                default:
                    result.append(content[i]);
                    break;
            }
        }
        return result.toString();
    }

    /* access modifiers changed from: protected */
    public String toEncoded(String url, String sessionId) {
        if (url == null || sessionId == null) {
            return url;
        }
        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf(63);
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        int pound = path.indexOf(35);
        if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }
        StringBuilder sb = new StringBuilder(path);
        if (sb.length() > 0) {
            sb.append(";jsessionid=");
            sb.append(sessionId);
        }
        String jrouteId = this.request.getHeader(Constants.PROXY_JROUTE);
        if (jrouteId != null) {
            sb.append(":");
            sb.append(jrouteId);
        }
        sb.append(anchor);
        sb.append(query);
        return sb.toString();
    }

    public boolean isCacheEnabled() {
        return this.cacheEnabled;
    }

    public SuspendContext getSuspendContext() {
        return this.suspendedContext;
    }

    public boolean isSuspended() {
        SuspendState state;
        checkResponse();
        synchronized (this.suspendedContext) {
            state = this.suspendState;
        }
        return state == SuspendState.SUSPENDED || state == SuspendState.RESUMING || state == SuspendState.CANCELLING;
    }

    public void suspend() {
        suspend(-1, TimeUnit.MILLISECONDS);
    }

    @Deprecated
    public void suspend(long timeout, TimeUnit timeunit) {
        suspend(timeout, timeunit, (CompletionHandler<Response>) null);
    }

    public void suspend(long timeout, TimeUnit timeunit, CompletionHandler<Response> completionHandler) {
        suspend(timeout, timeunit, completionHandler, (TimeoutHandler) null);
    }

    public void suspend(long timeout, TimeUnit timeunit, CompletionHandler<Response> completionHandler, TimeoutHandler timeoutHandler) {
        checkResponse();
        if (this.suspendState == SuspendState.NONE) {
            this.suspendState = SuspendState.SUSPENDED;
            this.suspendStatus.suspend();
            this.suspendedContext.init(completionHandler, timeoutHandler);
            HttpServerProbeNotifier.notifyRequestSuspend(this.request.httpServerFilter, this.ctx.getConnection(), this.request);
            this.httpContext.getCloseable().addCloseListener(this.suspendedContext.closeListener);
            if (timeout > 0) {
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                long timeoutMillis = timeUnit.convert(timeout, timeunit);
                this.delayQueue.add(this.suspendedContext.suspendTimeout, timeoutMillis, timeUnit);
                this.suspendedContext.suspendTimeout.delayMillis = timeoutMillis;
                return;
            }
            return;
        }
        throw new IllegalStateException("Already Suspended");
    }

    public void resume() {
        checkResponse();
        this.suspendedContext.markResumed();
        this.ctx.resume();
    }

    @Deprecated
    public void cancel() {
        checkResponse();
        this.suspendedContext.markCancelled();
        this.ctx.resume();
    }

    /* access modifiers changed from: package-private */
    public final void checkResponse() {
        if (this.response == null) {
            throw new IllegalStateException("Internal org.glassfish.grizzly.http.server.Response has not been set");
        }
    }

    public boolean isSendFileEnabled() {
        return this.sendFileEnabled;
    }

    public final class SuspendedContextImpl implements SuspendContext {
        /* access modifiers changed from: private */
        public CloseListener closeListener;
        CompletionHandler<Response> completionHandler;
        private int modCount;
        SuspendTimeout suspendTimeout;

        public SuspendedContextImpl() {
        }

        public synchronized boolean markResumed() {
            this.modCount++;
            Response response = Response.this;
            SuspendState suspendState = response.suspendState;
            if (suspendState != SuspendState.SUSPENDED) {
                if (suspendState != SuspendState.CANCELLED) {
                    if (suspendState != SuspendState.CANCELLING) {
                        throw new IllegalStateException("Not Suspended");
                    }
                }
                return false;
            }
            response.suspendState = SuspendState.RESUMING;
            response.httpContext.getCloseable().removeCloseListener(this.closeListener);
            CompletionHandler<Response> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.completed(Response.this);
            }
            reset();
            Response response2 = Response.this;
            response2.suspendState = SuspendState.RESUMED;
            HttpServerProbeNotifier.notifyRequestResume(response2.request.httpServerFilter, response2.ctx.getConnection(), Response.this.request);
            return true;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0058, code lost:
            return true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized boolean markCancelled(int r5) {
            /*
                r4 = this;
                monitor-enter(r4)
                int r0 = r4.modCount     // Catch:{ all -> 0x0061 }
                if (r0 == r5) goto L_0x0008
                r0 = 0
                monitor-exit(r4)
                return r0
            L_0x0008:
                r1 = 1
                int r0 = r0 + r1
                r4.modCount = r0     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Response r0 = org.glassfish.grizzly.http.server.Response.this     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Response$SuspendState r2 = r0.suspendState     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Response$SuspendState r3 = org.glassfish.grizzly.http.server.Response.SuspendState.SUSPENDED     // Catch:{ all -> 0x0061 }
                if (r2 != r3) goto L_0x0059
                org.glassfish.grizzly.http.server.Response$SuspendState r2 = org.glassfish.grizzly.http.server.Response.SuspendState.CANCELLING     // Catch:{ all -> 0x0061 }
                r0.suspendState = r2     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.HttpContext r0 = r0.httpContext     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.Closeable r0 = r0.getCloseable()     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.CloseListener r2 = r4.closeListener     // Catch:{ all -> 0x0061 }
                r0.removeCloseListener(r2)     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.CompletionHandler<org.glassfish.grizzly.http.server.Response> r0 = r4.completionHandler     // Catch:{ all -> 0x0061 }
                if (r0 == 0) goto L_0x002a
                r0.cancelled()     // Catch:{ all -> 0x0061 }
            L_0x002a:
                org.glassfish.grizzly.http.server.Response r0 = org.glassfish.grizzly.http.server.Response.this     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Response$SuspendState r2 = org.glassfish.grizzly.http.server.Response.SuspendState.CANCELLED     // Catch:{ all -> 0x0061 }
                r0.suspendState = r2     // Catch:{ all -> 0x0061 }
                r4.reset()     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Response r0 = org.glassfish.grizzly.http.server.Response.this     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Request r2 = r0.request     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.HttpServerFilter r2 = r2.httpServerFilter     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.filterchain.FilterChainContext r0 = r0.ctx     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.Connection r0 = r0.getConnection()     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Response r3 = org.glassfish.grizzly.http.server.Response.this     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Request r3 = r3.request     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.HttpServerProbeNotifier.notifyRequestCancel(r2, r0, r3)     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Response r0 = org.glassfish.grizzly.http.server.Response.this     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.server.Request r0 = r0.request     // Catch:{ all -> 0x0061 }
                org.glassfish.grizzly.http.io.InputBuffer r0 = r0.getInputBuffer()     // Catch:{ all -> 0x0061 }
                boolean r2 = r0.isFinished()     // Catch:{ all -> 0x0061 }
                if (r2 != 0) goto L_0x0057
                r0.terminate()     // Catch:{ all -> 0x0061 }
            L_0x0057:
                monitor-exit(r4)
                return r1
            L_0x0059:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0061 }
                java.lang.String r1 = "Not Suspended"
                r0.<init>(r1)     // Catch:{ all -> 0x0061 }
                throw r0     // Catch:{ all -> 0x0061 }
            L_0x0061:
                r5 = move-exception
                monitor-exit(r4)
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.Response.SuspendedContextImpl.markCancelled(int):boolean");
        }

        @Deprecated
        public synchronized void markCancelled() {
            markCancelled(this.modCount);
        }

        /* access modifiers changed from: private */
        public void init(CompletionHandler<Response> completionHandler2, TimeoutHandler timeoutHandler) {
            this.completionHandler = completionHandler2;
            this.suspendTimeout = new SuspendTimeout(this.modCount, timeoutHandler);
            this.closeListener = new SuspendCloseListener(this.modCount);
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.suspendTimeout.reset();
            this.suspendTimeout = null;
            this.completionHandler = null;
            this.closeListener = null;
        }

        public CompletionHandler<Response> getCompletionHandler() {
            return this.completionHandler;
        }

        public TimeoutHandler getTimeoutHandler() {
            return this.suspendTimeout.timeoutHandler;
        }

        public long getTimeout(TimeUnit timeunit) {
            return this.suspendTimeout.getTimeout(timeunit);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setTimeout(long r4, java.util.concurrent.TimeUnit r6) {
            /*
                r3 = this;
                org.glassfish.grizzly.http.server.Response r0 = org.glassfish.grizzly.http.server.Response.this
                org.glassfish.grizzly.http.server.Response$SuspendedContextImpl r0 = r0.suspendedContext
                monitor-enter(r0)
                org.glassfish.grizzly.http.server.Response r1 = org.glassfish.grizzly.http.server.Response.this     // Catch:{ all -> 0x001b }
                org.glassfish.grizzly.http.server.Response$SuspendState r1 = r1.suspendState     // Catch:{ all -> 0x001b }
                org.glassfish.grizzly.http.server.Response$SuspendState r2 = org.glassfish.grizzly.http.server.Response.SuspendState.SUSPENDED     // Catch:{ all -> 0x001b }
                if (r1 != r2) goto L_0x0019
                org.glassfish.grizzly.http.server.Response$SuspendTimeout r1 = r3.suspendTimeout     // Catch:{ all -> 0x001b }
                if (r1 != 0) goto L_0x0014
                goto L_0x0019
            L_0x0014:
                r1.setTimeout(r4, r6)     // Catch:{ all -> 0x001b }
                monitor-exit(r0)     // Catch:{ all -> 0x001b }
                return
            L_0x0019:
                monitor-exit(r0)     // Catch:{ all -> 0x001b }
                return
            L_0x001b:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x001b }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.Response.SuspendedContextImpl.setTimeout(long, java.util.concurrent.TimeUnit):void");
        }

        public boolean isSuspended() {
            return Response.this.isSuspended();
        }

        public SuspendStatus getSuspendStatus() {
            return Response.this.suspendStatus;
        }

        public class SuspendCloseListener implements GenericCloseListener {
            private final int expectedModCount;

            public SuspendCloseListener(int expectedModCount2) {
                this.expectedModCount = expectedModCount2;
            }

            public void onClosed(Closeable connection, CloseType closeType) {
                Response.this.checkResponse();
                if (Response.this.suspendedContext.markCancelled(this.expectedModCount)) {
                    Response.this.ctx.completeAndRelease();
                }
            }
        }
    }

    public class SuspendTimeout {
        long delayMillis;
        private final int expectedModCount;
        TimeoutHandler timeoutHandler;
        volatile long timeoutTimeMillis;

        private SuspendTimeout(int modCount, TimeoutHandler timeoutHandler2) {
            this.expectedModCount = modCount;
            this.timeoutHandler = timeoutHandler2;
        }

        /* access modifiers changed from: package-private */
        public boolean onTimeout() {
            this.timeoutTimeMillis = -1;
            TimeoutHandler localTimeoutHandler = this.timeoutHandler;
            if (localTimeoutHandler != null && !localTimeoutHandler.onTimeout(Response.this)) {
                return false;
            }
            Response response = Response.this;
            HttpServerProbeNotifier.notifyRequestTimeout(response.request.httpServerFilter, response.ctx.getConnection(), Response.this.request);
            try {
                Response.this.checkResponse();
                Response.this.suspendedContext.markCancelled(this.expectedModCount);
                return true;
            } catch (Exception e) {
                return true;
            }
        }

        /* access modifiers changed from: private */
        public long getTimeout(TimeUnit timeunit) {
            long j = this.delayMillis;
            if (j > 0) {
                return timeunit.convert(j, TimeUnit.MILLISECONDS);
            }
            return j;
        }

        /* access modifiers changed from: private */
        public void setTimeout(long timeout, TimeUnit timeunit) {
            if (timeout > 0) {
                this.delayMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
            } else {
                this.delayMillis = -1;
            }
            Response.this.delayQueue.add(this, this.delayMillis, TimeUnit.MILLISECONDS);
        }

        /* access modifiers changed from: private */
        public void reset() {
            this.timeoutTimeMillis = -1;
            this.timeoutHandler = null;
        }
    }

    public static class DelayQueueWorker implements DelayedExecutor.Worker<SuspendTimeout> {
        private DelayQueueWorker() {
        }

        public boolean doWork(SuspendTimeout element) {
            return element.onTimeout();
        }
    }

    public static class DelayQueueResolver implements DelayedExecutor.Resolver<SuspendTimeout> {
        private DelayQueueResolver() {
        }

        public boolean removeTimeout(SuspendTimeout element) {
            if (element.timeoutTimeMillis == -1) {
                return false;
            }
            element.timeoutTimeMillis = -1;
            return true;
        }

        public long getTimeoutMillis(SuspendTimeout element) {
            return element.timeoutTimeMillis;
        }

        public void setTimeoutMillis(SuspendTimeout element, long timeoutMillis) {
            element.timeoutTimeMillis = timeoutMillis;
        }
    }
}
