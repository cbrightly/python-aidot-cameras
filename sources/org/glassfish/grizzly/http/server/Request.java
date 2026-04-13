package org.glassfish.grizzly.http.server;

import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import org.apache.http.l;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Cookies;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.Note;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.io.NIOInputStream;
import org.glassfish.grizzly.http.io.NIOReader;
import org.glassfish.grizzly.http.server.http2.PushBuilder;
import org.glassfish.grizzly.http.server.io.ServerInputBuffer;
import org.glassfish.grizzly.http.server.util.Globals;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.server.util.ParameterMap;
import org.glassfish.grizzly.http.server.util.RequestUtils;
import org.glassfish.grizzly.http.server.util.SimpleDateFormats;
import org.glassfish.grizzly.http.server.util.StringParser;
import org.glassfish.grizzly.http.util.Chunk;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.Parameters;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.grizzly.utils.JdkVersion;
import org.slf4j.e;

public class Request {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ThreadCache.CachedTypeIndex<Request> CACHE_IDX;
    private static final Boolean FORCE_CLIENT_AUTH_ON_GET_USER_PRINCIPAL;
    private static final String HTTP2_PUSH_ENABLED = "http2-push-enabled";
    private static final Logger LOGGER;
    public static final String SEND_FILE_ATTR = "org.glassfish.grizzly.http.SEND_FILE";
    public static final String SEND_FILE_ENABLED_ATTR = "org.glassfish.grizzly.http.SEND_FILE_ENABLED";
    public static final String SEND_FILE_START_OFFSET_ATTR = "org.glassfish.grizzly.http.FILE_START_OFFSET";
    public static final String SEND_FILE_WRITE_LEN_ATTR = "org.glassfish.grizzly.http.FILE_WRITE_LEN";
    protected static final Locale defaultLocale = Locale.getDefault();
    private static final LocaleParser localeParser;
    private static final String match = ";jsessionid=";
    private static int maxDispatchDepth = 20;
    protected final List<AfterServiceListener> afterServicesList;
    private MappingData cachedMappingData;
    private final PathData contextPath;
    protected Cookie[] cookies;
    protected boolean cookiesParsed;
    protected FilterChainContext ctx;
    private int dispatchDepth;
    protected Object dispatcherType;
    private final PathData httpHandlerPath;
    protected HttpServerFilter httpServerFilter;
    protected final ServerInputBuffer inputBuffer;
    private final NIOInputStreamImpl inputStream;
    private String jrouteId;
    protected final ArrayList<Locale> locales;
    protected boolean localesParsed;
    protected final ParameterMap parameterMap;
    protected final Parameters parameters;
    private StringParser parser;
    private final PathData pathInfo;
    protected Cookies rawCookies;
    private final NIOReaderImpl reader;
    protected HttpRequestPacket request;
    protected Object requestDispatcherPath;
    private RequestExecutorProvider requestExecutorProvider;
    protected boolean requestParametersParsed;
    protected boolean requestedSessionCookie;
    protected String requestedSessionId;
    protected boolean requestedSessionURL;
    protected final Response response;
    private String scheme;
    protected boolean secure;
    private Session session;
    protected String sessionCookieName;
    protected SessionManager sessionManager;
    protected boolean sessionParsed;
    protected Subject subject;
    protected Map<String, String> trailers;
    protected Principal userPrincipal;
    protected boolean usingInputStream;
    protected boolean usingReader;

    public interface PathResolver {
        String resolve(Request request);
    }

    static {
        LocaleParser lp;
        Class<Request> cls = Request.class;
        FORCE_CLIENT_AUTH_ON_GET_USER_PRINCIPAL = Boolean.valueOf(Boolean.getBoolean(cls.getName() + ".force-client-auth-on-get-user-principal"));
        LOGGER = Grizzly.logger(cls);
        CACHE_IDX = ThreadCache.obtainIndex(cls, 16);
        if (JdkVersion.getJdkVersion().compareTo("1.7.0") >= 0) {
            try {
                lp = (LocaleParser) Class.forName("org.glassfish.grizzly.http.server.TagLocaleParser").newInstance();
            } catch (Throwable e) {
                Logger logger = LOGGER;
                Level level = Level.FINE;
                if (logger.isLoggable(level)) {
                    logger.log(level, "Can't load JDK7 TagLocaleParser", e);
                }
                lp = new LegacyLocaleParser();
            }
        } else {
            lp = new LegacyLocaleParser();
        }
        localeParser = lp;
        if (lp != null) {
            return;
        }
        throw new AssertionError();
    }

    public static Request create() {
        Request request2 = (Request) ThreadCache.takeFromCache(CACHE_IDX);
        if (request2 != null) {
            return request2;
        }
        return new Request(new Response());
    }

    public final MappingData obtainMappingData() {
        if (this.cachedMappingData == null) {
            this.cachedMappingData = new MappingData();
        }
        return this.cachedMappingData;
    }

    @Deprecated
    public Request() {
        this.afterServicesList = new ArrayList(4);
        this.contextPath = new PathData(this, "", (PathResolver) null);
        this.httpHandlerPath = new PathData(this);
        this.pathInfo = new PathData(this);
        this.cookies = null;
        this.locales = new ArrayList<>();
        this.dispatcherType = null;
        this.inputBuffer = new ServerInputBuffer();
        this.inputStream = new NIOInputStreamImpl();
        this.reader = new NIOReaderImpl();
        this.usingInputStream = false;
        this.usingReader = false;
        this.userPrincipal = null;
        this.sessionParsed = false;
        this.requestParametersParsed = false;
        this.cookiesParsed = false;
        this.secure = false;
        this.subject = null;
        this.parameterMap = new ParameterMap();
        this.parameters = new Parameters();
        this.requestDispatcherPath = null;
        this.requestedSessionCookie = false;
        this.requestedSessionId = null;
        this.requestedSessionURL = false;
        this.localesParsed = false;
        this.dispatchDepth = 0;
        this.response = null;
    }

    protected Request(Response response2) {
        this.afterServicesList = new ArrayList(4);
        this.contextPath = new PathData(this, "", (PathResolver) null);
        this.httpHandlerPath = new PathData(this);
        this.pathInfo = new PathData(this);
        this.cookies = null;
        this.locales = new ArrayList<>();
        this.dispatcherType = null;
        this.inputBuffer = new ServerInputBuffer();
        this.inputStream = new NIOInputStreamImpl();
        this.reader = new NIOReaderImpl();
        this.usingInputStream = false;
        this.usingReader = false;
        this.userPrincipal = null;
        this.sessionParsed = false;
        this.requestParametersParsed = false;
        this.cookiesParsed = false;
        this.secure = false;
        this.subject = null;
        this.parameterMap = new ParameterMap();
        this.parameters = new Parameters();
        this.requestDispatcherPath = null;
        this.requestedSessionCookie = false;
        this.requestedSessionId = null;
        this.requestedSessionURL = false;
        this.localesParsed = false;
        this.dispatchDepth = 0;
        this.response = response2;
    }

    public void initialize(HttpRequestPacket request2, FilterChainContext ctx2, HttpServerFilter httpServerFilter2) {
        this.request = request2;
        this.ctx = ctx2;
        this.httpServerFilter = httpServerFilter2;
        this.inputBuffer.initialize(this, ctx2);
        this.parameters.setHeaders(request2.getHeaders());
        this.parameters.setQuery(request2.getQueryStringDC());
        DataChunk remoteUser = request2.remoteUser();
        String str = "https";
        if (httpServerFilter2 != null) {
            ServerFilterConfiguration configuration = httpServerFilter2.getConfiguration();
            this.parameters.setQueryStringEncoding(configuration.getDefaultQueryEncoding());
            BackendConfiguration backendConfiguration = configuration.getBackendConfiguration();
            if (backendConfiguration != null) {
                if (backendConfiguration.getScheme() != null) {
                    this.scheme = backendConfiguration.getScheme();
                } else if (backendConfiguration.getSchemeMapping() != null) {
                    this.scheme = request2.getHeader(backendConfiguration.getSchemeMapping());
                }
                if (str.equalsIgnoreCase(this.scheme)) {
                    request2.setSecure(true);
                }
                if (remoteUser.isNull() && backendConfiguration.getRemoteUserMapping() != null) {
                    remoteUser.setString(request2.getHeader(backendConfiguration.getRemoteUserMapping()));
                }
            }
        }
        if (this.scheme == null) {
            if (!request2.isSecure()) {
                str = l.DEFAULT_SCHEME_NAME;
            }
            this.scheme = str;
        }
        if (!remoteUser.isNull()) {
            setUserPrincipal(new GrizzlyPrincipal(remoteUser.toString()));
        }
    }

    /* access modifiers changed from: package-private */
    public final HttpServerFilter getServerFilter() {
        return this.httpServerFilter;
    }

    public HttpRequestPacket getRequest() {
        return this.request;
    }

    public Response getResponse() {
        return this.response;
    }

    public String getSessionCookieName() {
        return obtainSessionCookieName();
    }

    public void setSessionCookieName(String sessionCookieName2) {
        this.sessionCookieName = sessionCookieName2;
    }

    public boolean isPushEnabled() {
        Boolean result = (Boolean) getContext().getConnection().getAttributes().getAttribute(HTTP2_PUSH_ENABLED);
        if (result != null) {
            return result.booleanValue();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public String obtainSessionCookieName() {
        String str = this.sessionCookieName;
        return str != null ? str : getSessionManager().getSessionCookieName();
    }

    /* access modifiers changed from: protected */
    public SessionManager getSessionManager() {
        SessionManager sessionManager2 = this.sessionManager;
        return sessionManager2 != null ? sessionManager2 : DefaultSessionManager.instance();
    }

    /* access modifiers changed from: protected */
    public void setSessionManager(SessionManager sessionManager2) {
        this.sessionManager = sessionManager2;
    }

    public Executor getRequestExecutor() {
        return this.requestExecutorProvider.getExecutor(this);
    }

    /* access modifiers changed from: protected */
    public void setRequestExecutorProvider(RequestExecutorProvider requestExecutorProvider2) {
        this.requestExecutorProvider = requestExecutorProvider2;
    }

    public void addAfterServiceListener(AfterServiceListener listener) {
        this.afterServicesList.add(listener);
    }

    public void removeAfterServiceListener(AfterServiceListener listener) {
        this.afterServicesList.remove(listener);
    }

    /* access modifiers changed from: protected */
    public void onAfterService() {
        if (!this.inputBuffer.isFinished()) {
            this.inputBuffer.terminate();
        }
        if (!this.afterServicesList.isEmpty()) {
            int size = this.afterServicesList.size();
            for (int i = 0; i < size; i++) {
                try {
                    this.afterServicesList.get(i).onAfterService(this);
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_AFTERSERVICE_NOTIFICATION_ERROR(), e);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void recycle() {
        this.scheme = null;
        this.contextPath.setPath("");
        this.httpHandlerPath.reset();
        this.pathInfo.reset();
        this.dispatcherType = null;
        this.requestDispatcherPath = null;
        this.inputBuffer.recycle();
        this.inputStream.recycle();
        this.reader.recycle();
        this.usingInputStream = false;
        this.usingReader = false;
        this.userPrincipal = null;
        this.subject = null;
        this.sessionParsed = false;
        this.requestParametersParsed = false;
        this.cookiesParsed = false;
        Cookies cookies2 = this.rawCookies;
        if (cookies2 != null) {
            cookies2.recycle();
        }
        this.locales.clear();
        this.localesParsed = false;
        this.secure = false;
        this.request.recycle();
        this.request = null;
        this.ctx = null;
        this.httpServerFilter = null;
        this.cookies = null;
        this.requestedSessionId = null;
        this.sessionCookieName = null;
        this.sessionManager = null;
        this.session = null;
        this.dispatchDepth = 0;
        this.parameterMap.setLocked(false);
        this.parameterMap.clear();
        this.parameters.recycle();
        this.requestExecutorProvider = null;
        this.trailers = null;
        this.afterServicesList.clear();
        MappingData mappingData = this.cachedMappingData;
        if (mappingData != null) {
            mappingData.recycle();
        }
        ThreadCache.putToCache(CACHE_IDX, this);
    }

    public String getAuthorization() {
        return this.request.getHeader(Constants.AUTHORIZATION_HEADER);
    }

    public PushBuilder newPushBuilder() {
        if (isPushEnabled()) {
            return new PushBuilder(this);
        }
        return null;
    }

    public void replayPayload(Buffer buffer) {
        this.inputBuffer.replayPayload(buffer);
        this.usingReader = false;
        this.usingInputStream = false;
        if (Method.POST.equals(getMethod()) && this.requestParametersParsed) {
            this.requestParametersParsed = false;
            this.parameterMap.setLocked(false);
            this.parameterMap.clear();
            this.parameters.recycle();
        }
    }

    public NIOInputStream createInputStream() {
        this.inputStream.setInputBuffer(this.inputBuffer);
        return this.inputStream;
    }

    public static <E> Note<E> createNote(String name) {
        return HttpRequestPacket.createNote(name);
    }

    public <E> E getNote(Note<E> note) {
        return this.request.getNote(note);
    }

    public Set<String> getNoteNames() {
        return this.request.getNoteNames();
    }

    public <E> E removeNote(Note<E> note) {
        return this.request.removeNote(note);
    }

    public <E> void setNote(Note<E> note, E value) {
        this.request.setNote(note, value);
    }

    public void setServerName(String name) {
        this.request.serverName().setString(name);
    }

    public void setServerPort(int port) {
        this.request.setServerPort(port);
    }

    public HttpServerFilter getHttpFilter() {
        return this.httpServerFilter;
    }

    public String getContextPath() {
        return this.contextPath.get();
    }

    /* access modifiers changed from: protected */
    public void setContextPath(String contextPath2) {
        this.contextPath.setPath(contextPath2);
    }

    /* access modifiers changed from: protected */
    public void setContextPath(PathResolver contextPath2) {
        this.contextPath.setResolver(contextPath2);
    }

    public String getHttpHandlerPath() {
        return this.httpHandlerPath.get();
    }

    /* access modifiers changed from: protected */
    public void setHttpHandlerPath(String httpHandlerPath2) {
        this.httpHandlerPath.setPath(httpHandlerPath2);
    }

    /* access modifiers changed from: protected */
    public void setHttpHandlerPath(PathResolver httpHandlerPath2) {
        this.httpHandlerPath.setResolver(httpHandlerPath2);
    }

    public String getPathInfo() {
        return this.pathInfo.get();
    }

    /* access modifiers changed from: protected */
    public void setPathInfo(String pathInfo2) {
        this.pathInfo.setPath(pathInfo2);
    }

    /* access modifiers changed from: protected */
    public void setPathInfo(PathResolver pathInfo2) {
        this.pathInfo.setResolver(pathInfo2);
    }

    public Object getAttribute(String name) {
        if (SEND_FILE_ENABLED_ATTR.equals(name)) {
            Response response2 = this.response;
            if (response2 != null) {
                return Boolean.valueOf(response2.isSendFileEnabled());
            }
            throw new AssertionError();
        }
        Object attribute = this.request.getAttribute(name);
        if (attribute != null) {
            return attribute;
        }
        if (Globals.SSL_CERTIFICATE_ATTR.equals(name)) {
            Object attribute2 = RequestUtils.populateCertificateAttribute(this);
            if (attribute2 == null) {
                return attribute2;
            }
            this.request.setAttribute(name, attribute2);
            return attribute2;
        } else if (isSSLAttribute(name)) {
            RequestUtils.populateSSLAttributes(this);
            return this.request.getAttribute(name);
        } else if (Globals.DISPATCHER_REQUEST_PATH_ATTR.equals(name)) {
            return this.requestDispatcherPath;
        } else {
            return attribute;
        }
    }

    static boolean isSSLAttribute(String name) {
        return "jakarta.servlet.request.X509Certificate".equals(name) || "jakarta.servlet.request.cipher_suite".equals(name) || "jakarta.servlet.request.key_size".equals(name);
    }

    public Set<String> getAttributeNames() {
        return this.request.getAttributeNames();
    }

    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    public int getContentLength() {
        return (int) this.request.getContentLength();
    }

    public long getContentLengthLong() {
        return this.request.getContentLength();
    }

    public String getContentType() {
        return this.request.getContentType();
    }

    public InputStream getInputStream() {
        return getNIOInputStream();
    }

    public NIOInputStream getNIOInputStream() {
        if (!this.usingReader) {
            this.usingInputStream = true;
            this.inputStream.setInputBuffer(this.inputBuffer);
            return this.inputStream;
        }
        throw new IllegalStateException("Illegal attempt to call getInputStream() after getReader() has already been called.");
    }

    @Deprecated
    public boolean asyncInput() {
        return true;
    }

    public boolean requiresAcknowledgement() {
        return this.request.requiresAcknowledgement();
    }

    public Locale getLocale() {
        if (!this.localesParsed) {
            parseLocales();
        }
        if (!this.locales.isEmpty()) {
            return this.locales.get(0);
        }
        return defaultLocale;
    }

    public List<Locale> getLocales() {
        if (!this.localesParsed) {
            parseLocales();
        }
        if (!this.locales.isEmpty()) {
            return this.locales;
        }
        ArrayList<Locale> results = new ArrayList<>();
        results.add(defaultLocale);
        return results;
    }

    public Parameters getParameters() {
        return this.parameters;
    }

    public String getParameter(String name) {
        if (!this.requestParametersParsed) {
            parseRequestParameters();
        }
        return this.parameters.getParameter(name);
    }

    public Map<String, String[]> getParameterMap() {
        if (this.parameterMap.isLocked()) {
            return this.parameterMap;
        }
        for (String name : getParameterNames()) {
            this.parameterMap.put(name, getParameterValues(name));
        }
        this.parameterMap.setLocked(true);
        return this.parameterMap;
    }

    public Set<String> getParameterNames() {
        if (!this.requestParametersParsed) {
            parseRequestParameters();
        }
        return this.parameters.getParameterNames();
    }

    public String[] getParameterValues(String name) {
        if (!this.requestParametersParsed) {
            parseRequestParameters();
        }
        return this.parameters.getParameterValues(name);
    }

    public Protocol getProtocol() {
        return this.request.getProtocol();
    }

    public Reader getReader() {
        return getNIOReader();
    }

    public NIOReader getNIOReader() {
        if (!this.usingInputStream) {
            this.usingReader = true;
            this.inputBuffer.processingChars();
            this.reader.setInputBuffer(this.inputBuffer);
            return this.reader;
        }
        throw new IllegalStateException("Illegal attempt to call getReader() after getInputStream() has alread been called.");
    }

    public String getRemoteAddr() {
        return this.request.getRemoteAddress();
    }

    public String getRemoteHost() {
        return this.request.getRemoteHost();
    }

    public int getRemotePort() {
        return this.request.getRemotePort();
    }

    public String getLocalName() {
        return this.request.getLocalName();
    }

    public String getLocalAddr() {
        return this.request.getLocalAddress();
    }

    public int getLocalPort() {
        return this.request.getLocalPort();
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getServerName() {
        return this.request.serverName().toString();
    }

    public int getServerPort() {
        return this.request.getServerPort();
    }

    public boolean isSecure() {
        return this.request.isSecure();
    }

    public void removeAttribute(String name) {
        this.request.removeAttribute(name);
    }

    public void setAttribute(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("Argument 'name' cannot be null");
        } else if (value == null) {
            removeAttribute(name);
        } else if (name.equals(Globals.DISPATCHER_TYPE_ATTR)) {
            this.dispatcherType = value;
        } else if (name.equals(Globals.DISPATCHER_REQUEST_PATH_ATTR)) {
            this.requestDispatcherPath = value;
        } else {
            this.request.setAttribute(name, value);
            Response response2 = this.response;
            if (response2 == null) {
                throw new AssertionError();
            } else if (response2.isSendFileEnabled() && SEND_FILE_ATTR.equals(name)) {
                RequestUtils.handleSendFile(this);
            }
        }
    }

    public void setCharacterEncoding(String encoding) {
        if (!this.requestParametersParsed && !this.usingReader) {
            Charsets.lookupCharset(encoding);
            this.request.setCharacterEncoding(encoding);
        }
    }

    public static void setMaxDispatchDepth(int depth) {
        maxDispatchDepth = depth;
    }

    public static int getMaxDispatchDepth() {
        return maxDispatchDepth;
    }

    public int incrementDispatchDepth() {
        int i = this.dispatchDepth + 1;
        this.dispatchDepth = i;
        return i;
    }

    public int decrementDispatchDepth() {
        int i = this.dispatchDepth - 1;
        this.dispatchDepth = i;
        return i;
    }

    public boolean isMaxDispatchDepthReached() {
        return this.dispatchDepth > maxDispatchDepth;
    }

    public void addCookie(Cookie cookie) {
        if (!this.cookiesParsed) {
            parseCookies();
        }
        int size = 0;
        if (cookie != null) {
            size = this.cookies.length;
        }
        Cookie[] newCookies = new Cookie[(size + 1)];
        System.arraycopy(this.cookies, 0, newCookies, 0, size);
        newCookies[size] = cookie;
        this.cookies = newCookies;
    }

    public void addLocale(Locale locale) {
        this.locales.add(locale);
    }

    public void addParameter(String name, String[] values) {
        this.parameters.addParameterValues(name, values);
    }

    public void clearCookies() {
        this.cookiesParsed = true;
        this.cookies = null;
    }

    public void clearHeaders() {
    }

    public void clearLocales() {
        this.locales.clear();
    }

    public void clearParameters() {
    }

    public String getDecodedRequestURI() {
        return this.request.getRequestURIRef().getDecodedURI();
    }

    public void setUserPrincipal(Principal principal) {
        this.userPrincipal = principal;
    }

    public String getAuthType() {
        return this.request.authType().toString();
    }

    public Cookie[] getCookies() {
        if (!this.cookiesParsed) {
            parseCookies();
        }
        return this.cookies;
    }

    public void setCookies(Cookie[] cookies2) {
        this.cookies = cookies2;
    }

    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value == null) {
            return -1;
        }
        SimpleDateFormats formats = SimpleDateFormats.create();
        try {
            long result = FastHttpDateFormat.parseDate(value, formats.getFormats());
            if (result != -1) {
                return result;
            }
            throw new IllegalArgumentException(value);
        } finally {
            formats.recycle();
        }
    }

    public long getDateHeader(Header header) {
        String value = getHeader(header);
        if (value == null) {
            return -1;
        }
        SimpleDateFormats formats = SimpleDateFormats.create();
        try {
            long result = FastHttpDateFormat.parseDate(value, formats.getFormats());
            if (result != -1) {
                return result;
            }
            throw new IllegalArgumentException(value);
        } finally {
            formats.recycle();
        }
    }

    public String getHeader(String name) {
        return this.request.getHeader(name);
    }

    public String getHeader(Header header) {
        return this.request.getHeader(header);
    }

    public Iterable<String> getHeaders(String name) {
        return this.request.getHeaders().values(name);
    }

    public Iterable<String> getHeaders(Header header) {
        return this.request.getHeaders().values(header);
    }

    public Map<String, String> getTrailers() {
        if (this.inputBuffer.isFinished()) {
            return this.inputBuffer.getTrailers();
        }
        throw new IllegalStateException();
    }

    public boolean areTrailersAvailable() {
        return this.inputBuffer.areTrailersAvailable();
    }

    public Iterable<String> getHeaderNames() {
        return this.request.getHeaders().names();
    }

    public int getIntHeader(String name) {
        String value = getHeader(name);
        if (value == null) {
            return -1;
        }
        return Integer.parseInt(value);
    }

    public int getIntHeader(Header header) {
        String value = getHeader(header);
        if (value == null) {
            return -1;
        }
        return Integer.parseInt(value);
    }

    public Method getMethod() {
        return this.request.getMethod();
    }

    public void setMethod(String method) {
        this.request.setMethod(method);
    }

    public String getQueryString() {
        String queryString = this.request.getQueryStringDC().toString(this.parameters.getQueryStringEncoding());
        if (queryString == null || queryString.isEmpty()) {
            return null;
        }
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.request.setQueryString(queryString);
    }

    public String getRemoteUser() {
        Principal principal = this.userPrincipal;
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }

    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }

    public String getRequestURI() {
        return this.request.getRequestURI();
    }

    public void setRequestURI(String uri) {
        this.request.setRequestURI(uri);
    }

    public StringBuilder getRequestURL() {
        return appendRequestURL(this, new StringBuilder());
    }

    public static StringBuilder appendRequestURL(Request request2, StringBuilder buffer) {
        String scheme2 = request2.getScheme();
        int port = request2.getServerPort();
        if (port < 0) {
            port = 80;
        }
        buffer.append(scheme2);
        buffer.append("://");
        buffer.append(request2.getServerName());
        if ((scheme2.equals(l.DEFAULT_SCHEME_NAME) && port != 80) || (scheme2.equals("https") && port != 443)) {
            buffer.append(':');
            buffer.append(port);
        }
        buffer.append(request2.getRequestURI());
        return buffer;
    }

    public static StringBuffer appendRequestURL(Request request2, StringBuffer buffer) {
        String scheme2 = request2.getScheme();
        int port = request2.getServerPort();
        if (port < 0) {
            port = 80;
        }
        buffer.append(scheme2);
        buffer.append("://");
        buffer.append(request2.getServerName());
        if ((scheme2.equals(l.DEFAULT_SCHEME_NAME) && port != 80) || (scheme2.equals("https") && port != 443)) {
            buffer.append(':');
            buffer.append(port);
        }
        buffer.append(request2.getRequestURI());
        return buffer;
    }

    public Principal getUserPrincipal() {
        if (this.userPrincipal == null && getRequest().isSecure()) {
            X509Certificate[] certs = (X509Certificate[]) getAttribute("jakarta.servlet.request.X509Certificate");
            if (FORCE_CLIENT_AUTH_ON_GET_USER_PRINCIPAL.booleanValue() && (certs == null || certs.length < 1)) {
                certs = (X509Certificate[]) getAttribute(Globals.SSL_CERTIFICATE_ATTR);
            }
            if (certs != null && certs.length > 0) {
                this.userPrincipal = certs[0].getSubjectX500Principal();
            }
        }
        return this.userPrincipal;
    }

    public FilterChainContext getContext() {
        return this.ctx;
    }

    /* access modifiers changed from: protected */
    public String unescape(String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(92) == -1) {
            return s;
        }
        StringBuilder buf = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c != '\\') {
                buf.append(c);
            } else {
                i++;
                if (i < s.length()) {
                    buf.append(s.charAt(i));
                } else {
                    throw new IllegalArgumentException();
                }
            }
            i++;
        }
        return buf.toString();
    }

    /* access modifiers changed from: protected */
    public void parseCookies() {
        this.cookiesParsed = true;
        this.cookies = getRawCookies().get();
    }

    public InputBuffer getInputBuffer() {
        return this.inputBuffer;
    }

    public void setRequestParameters(Parameters parameters2) {
        this.requestParametersParsed = true;
        for (String name : parameters2.getParameterNames()) {
            this.parameters.addParameterValues(name, parameters2.getParameterValues(name));
        }
    }

    /* access modifiers changed from: protected */
    public Cookies getRawCookies() {
        if (this.rawCookies == null) {
            this.rawCookies = new Cookies();
        }
        if (!this.rawCookies.initialized()) {
            this.rawCookies.setHeaders(this.request.getHeaders());
        }
        return this.rawCookies;
    }

    /* access modifiers changed from: protected */
    public void parseRequestParameters() {
        this.requestParametersParsed = true;
        Charset charset = null;
        if (this.parameters.getEncoding() == null) {
            charset = lookupCharset(getCharacterEncoding());
            this.parameters.setEncoding(charset);
        }
        if (this.parameters.getQueryStringEncoding() == null) {
            if (charset == null) {
                charset = lookupCharset(getCharacterEncoding());
            }
            this.parameters.setQueryStringEncoding(charset);
        }
        this.parameters.handleQueryParameters();
        if (!this.usingInputStream && !this.usingReader && Method.POST.equals(getMethod()) && checkPostContentType(getContentType())) {
            int maxFormPostSize = this.httpServerFilter.getConfiguration().getMaxFormPostSize();
            int len = getContentLength();
            if (len < 0) {
                if (this.request.isChunked()) {
                    len = maxFormPostSize;
                } else {
                    return;
                }
            }
            if (maxFormPostSize <= 0 || len <= maxFormPostSize) {
                int read = 0;
                try {
                    Buffer formData = getPostBody(len);
                    read = formData.remaining();
                    this.parameters.processParameters(formData, formData.position(), read);
                    try {
                        skipPostBody(read);
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_BODY_SKIP(), e);
                    }
                } catch (Exception e2) {
                    skipPostBody(read);
                } catch (Throwable th) {
                    try {
                        skipPostBody(read);
                    } catch (Exception e3) {
                        LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_BODY_SKIP(), e3);
                    }
                    throw th;
                }
            } else {
                Logger logger = LOGGER;
                if (logger.isLoggable(Level.WARNING)) {
                    logger.warning(LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_POST_TOO_LARGE());
                }
                throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_POST_TOO_LARGE());
            }
        }
    }

    private Charset lookupCharset(String enc) {
        if (enc == null) {
            return Constants.DEFAULT_HTTP_CHARSET;
        }
        try {
            return Charsets.lookupCharset(enc);
        } catch (Exception e) {
            return Constants.DEFAULT_HTTP_CHARSET;
        }
    }

    private boolean checkPostContentType(String contentType) {
        return contentType != null && contentType.trim().startsWith("application/x-www-form-urlencoded");
    }

    public Buffer getPostBody(int len) {
        this.inputBuffer.fillFully(len);
        return this.inputBuffer.getBuffer();
    }

    /* access modifiers changed from: protected */
    public void skipPostBody(int len) {
        this.inputBuffer.skip((long) len);
    }

    /* access modifiers changed from: protected */
    public void parseLocales() {
        this.localesParsed = true;
        for (String value : getHeaders("accept-language")) {
            parseLocalesHeader(value);
        }
    }

    /* access modifiers changed from: protected */
    public void parseLocalesHeader(String value) {
        Locale locale;
        TreeMap<Double, List<Locale>> localLocalesMap = new TreeMap<>();
        int white = value.indexOf(32);
        if (white < 0) {
            white = value.indexOf(9);
        }
        if (white >= 0) {
            int len = value.length();
            StringBuilder sb = new StringBuilder(len - 1);
            for (int i = 0; i < len; i++) {
                char ch = value.charAt(i);
                if (!(ch == ' ' || ch == 9)) {
                    sb.append(ch);
                }
            }
            value = sb.toString();
        }
        if (this.parser == null) {
            this.parser = new StringParser();
        }
        this.parser.setString(value);
        int length = this.parser.getLength();
        while (true) {
            int start = this.parser.getIndex();
            if (start >= length) {
                break;
            }
            String entry = this.parser.extract(start, this.parser.findChar(StringUtil.COMMA)).trim();
            this.parser.advance();
            double quality = 1.0d;
            int semi = entry.indexOf(";q=");
            if (semi >= 0) {
                String qvalue = entry.substring(semi + 3);
                if (qvalue.length() <= 5) {
                    try {
                        quality = Double.parseDouble(qvalue);
                    } catch (NumberFormatException e) {
                        quality = 0.0d;
                    }
                } else {
                    quality = 0.0d;
                }
                entry = entry.substring(0, semi);
            }
            if (quality >= 5.0E-5d && !e.ANY_MARKER.equals(entry) && (locale = localeParser.parseLocale(entry)) != null) {
                Double key = Double.valueOf(-quality);
                List<Locale> values = localLocalesMap.get(key);
                if (values == null) {
                    values = new ArrayList<>();
                    localLocalesMap.put(key, values);
                }
                values.add(locale);
            }
        }
        for (List<Locale> localLocales : localLocalesMap.values()) {
            for (Locale locale2 : localLocales) {
                addLocale(locale2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void parseJrouteCookie() {
        if (!this.cookiesParsed) {
            parseCookies();
        }
        Cookie cookie = getRawCookies().findByName(Constants.JROUTE_COOKIE);
        if (cookie != null) {
            setJrouteId(cookie.getValue());
        }
    }

    static boolean isAlpha(String value) {
        if (value == null) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void setJrouteId(String jrouteId2) {
        this.jrouteId = jrouteId2;
    }

    public String getJrouteId() {
        return this.jrouteId;
    }

    public Session getSession() {
        return doGetSession(true);
    }

    public Session getSession(boolean create) {
        return doGetSession(create);
    }

    public String changeSessionId() {
        Session sessionLocal = doGetSession(false);
        if (sessionLocal != null) {
            String oldSessionId = getSessionManager().changeSessionId(this, sessionLocal);
            String newSessionId = sessionLocal.getIdInternal();
            this.requestedSessionId = newSessionId;
            if (!isRequestedSessionIdFromURL() && this.response != null) {
                Cookie cookie = new Cookie(obtainSessionCookieName(), newSessionId);
                configureSessionCookie(cookie);
                this.response.addSessionCookieInternal(cookie);
            }
            return oldSessionId;
        }
        throw new IllegalStateException("changeSessionId has been called without a session");
    }

    /* access modifiers changed from: protected */
    public Session doGetSession(boolean create) {
        Session session2 = this.session;
        if (session2 != null && session2.isValid()) {
            return this.session;
        }
        this.session = null;
        if (this.requestedSessionId == null) {
            Cookie[] cookiesLocale = getCookies();
            if (cookiesLocale != null) {
                String sessionCookieNameLocal = obtainSessionCookieName();
                int i = 0;
                while (true) {
                    if (i >= cookiesLocale.length) {
                        break;
                    }
                    Cookie c = cookiesLocale[i];
                    if (sessionCookieNameLocal.equals(c.getName())) {
                        setRequestedSessionId(c.getValue());
                        setRequestedSessionCookie(true);
                        break;
                    }
                    i++;
                }
            } else {
                throw new AssertionError();
            }
        }
        Session session3 = getSessionManager().getSession(this, this.requestedSessionId);
        this.session = session3;
        if (session3 != null && !session3.isValid()) {
            this.session = null;
        }
        Session session4 = this.session;
        if (session4 != null) {
            session4.access();
            return this.session;
        } else if (!create) {
            return null;
        } else {
            Session createSession = getSessionManager().createSession(this);
            this.session = createSession;
            createSession.setSessionTimeout((long) (this.httpServerFilter.getConfiguration().getSessionTimeoutSeconds() * 1000));
            this.requestedSessionId = this.session.getIdInternal();
            Cookie cookie = new Cookie(obtainSessionCookieName(), this.session.getIdInternal());
            configureSessionCookie(cookie);
            Response response2 = this.response;
            if (response2 != null) {
                response2.addCookie(cookie);
                return this.session;
            }
            throw new AssertionError();
        }
    }

    public boolean isRequestedSessionIdFromCookie() {
        return this.requestedSessionId != null && this.requestedSessionCookie;
    }

    public boolean isRequestedSessionIdFromURL() {
        return this.requestedSessionId != null && this.requestedSessionURL;
    }

    public boolean isRequestedSessionIdValid() {
        String str = this.requestedSessionId;
        if (str == null) {
            return false;
        }
        Session session2 = this.session;
        if (session2 != null && str.equals(session2.getIdInternal())) {
            return this.session.isValid();
        }
        Session localSession = getSessionManager().getSession(this, this.requestedSessionId);
        if (localSession == null || !localSession.isValid()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void configureSessionCookie(Cookie cookie) {
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        if (isSecure()) {
            cookie.setSecure(true);
        }
        getSessionManager().configureSessionCookie(this, cookie);
    }

    /* access modifiers changed from: protected */
    public void parseSessionId() {
        boolean isUpdated;
        if (!this.sessionParsed) {
            this.sessionParsed = true;
            DataChunk uriDC = this.request.getRequestURIRef().getRequestURIBC();
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[uriDC.getType().ordinal()]) {
                case 1:
                    isUpdated = parseSessionId((Chunk) uriDC.getByteChunk());
                    break;
                case 2:
                    isUpdated = parseSessionId((Chunk) uriDC.getBufferChunk());
                    break;
                case 3:
                    isUpdated = parseSessionId((Chunk) uriDC.getCharChunk());
                    break;
                case 4:
                    isUpdated = parseSessionId(uriDC);
                    break;
                default:
                    throw new IllegalStateException("Unexpected DataChunk type: " + uriDC.getType());
            }
            if (isUpdated) {
                uriDC.notifyDirectUpdate();
            }
        }
    }

    /* renamed from: org.glassfish.grizzly.http.server.Request$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type;

        static {
            int[] iArr = new int[DataChunk.Type.values().length];
            $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type = iArr;
            try {
                iArr[DataChunk.Type.Bytes.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Buffer.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.Chars.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$util$DataChunk$Type[DataChunk.Type.String.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private boolean parseSessionId(Chunk uriChunk) {
        String sessionParamNameMatch;
        if (this.sessionCookieName != null) {
            sessionParamNameMatch = ';' + this.sessionCookieName + '=';
        } else {
            sessionParamNameMatch = match;
        }
        boolean isUpdated = false;
        int semicolon = uriChunk.indexOf(sessionParamNameMatch, 0);
        if (semicolon > 0) {
            int sessionIdStart = sessionParamNameMatch.length() + semicolon;
            int semicolon2 = uriChunk.indexOf(';', sessionIdStart);
            isUpdated = semicolon2 >= 0;
            int end = isUpdated ? semicolon2 : uriChunk.getLength();
            String sessionId = uriChunk.toString(sessionIdStart, end);
            int jrouteIndex = sessionId.lastIndexOf(58);
            if (jrouteIndex > 0) {
                setRequestedSessionId(sessionId.substring(0, jrouteIndex));
                if (jrouteIndex < sessionId.length() - 1) {
                    setJrouteId(sessionId.substring(jrouteIndex + 1));
                }
            } else {
                setRequestedSessionId(sessionId);
            }
            setRequestedSessionURL(true);
            uriChunk.delete(semicolon, end);
        } else {
            setRequestedSessionId((String) null);
            setRequestedSessionURL(false);
        }
        return isUpdated;
    }

    private boolean parseSessionId(DataChunk dataChunkStr) {
        String sessionParamNameMatch;
        if (dataChunkStr.getType() == DataChunk.Type.String) {
            String uri = dataChunkStr.toString();
            if (this.sessionCookieName != null) {
                sessionParamNameMatch = ';' + this.sessionCookieName + '=';
            } else {
                sessionParamNameMatch = match;
            }
            boolean isUpdated = false;
            int semicolon = uri.indexOf(sessionParamNameMatch);
            if (semicolon > 0) {
                int sessionIdStart = sessionParamNameMatch.length() + semicolon;
                int semicolon2 = uri.indexOf(59, sessionIdStart);
                isUpdated = semicolon2 >= 0;
                String sessionId = uri.substring(sessionIdStart, isUpdated ? semicolon2 : uri.length());
                int jrouteIndex = sessionId.lastIndexOf(58);
                if (jrouteIndex > 0) {
                    setRequestedSessionId(sessionId.substring(0, jrouteIndex));
                    if (jrouteIndex < sessionId.length() - 1) {
                        setJrouteId(sessionId.substring(jrouteIndex + 1));
                    }
                } else {
                    setRequestedSessionId(sessionId);
                }
                setRequestedSessionURL(true);
                dataChunkStr.setString(uri.substring(0, semicolon));
            } else {
                setRequestedSessionId((String) null);
                setRequestedSessionURL(false);
            }
            return isUpdated;
        }
        throw new AssertionError();
    }

    public void setRequestedSessionCookie(boolean flag) {
        this.requestedSessionCookie = flag;
    }

    public void setRequestedSessionId(String id) {
        this.requestedSessionId = id;
    }

    public void setRequestedSessionURL(boolean flag) {
        this.requestedSessionURL = flag;
    }

    public static class PathData {
        private String path;
        private final Request request;
        private PathResolver resolver;

        public PathData(Request request2) {
            this.request = request2;
        }

        public PathData(Request request2, String path2, PathResolver resolver2) {
            this.request = request2;
            this.path = path2;
            this.resolver = resolver2;
        }

        public void setPath(String path2) {
            this.path = path2;
            this.resolver = null;
        }

        public void setResolver(PathResolver resolver2) {
            this.resolver = resolver2;
            this.path = null;
        }

        public String get() {
            String str = this.path;
            if (str != null) {
                return str;
            }
            PathResolver pathResolver = this.resolver;
            if (pathResolver == null) {
                return null;
            }
            String resolve = pathResolver.resolve(this.request);
            this.path = resolve;
            return resolve;
        }

        public void reset() {
            this.path = null;
            this.resolver = null;
        }
    }
}
