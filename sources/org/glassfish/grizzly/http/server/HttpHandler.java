package org.glassfish.grizzly.http.server;

import java.io.CharConversionException;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.server.RequestExecutorProvider;
import org.glassfish.grizzly.http.server.util.DispatcherHelper;
import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.RequestURIRef;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.Charsets;

public abstract class HttpHandler {
    private static final RequestExecutorProvider DEFAULT_REQUEST_EXECUTOR_PROVIDER = new RequestExecutorProvider.WorkerThreadProvider();
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Grizzly.logger(HttpHandler.class);
    private boolean allowCustomStatusMessage;
    private boolean allowEncodedSlash;
    private boolean decodeURL;
    private final String name;
    private Charset requestURIEncoding;

    public abstract void service(Request request, Response response);

    public HttpHandler() {
        this((String) null);
    }

    public HttpHandler(String name2) {
        this.allowEncodedSlash = false;
        this.decodeURL = false;
        this.allowCustomStatusMessage = true;
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }

    /* access modifiers changed from: package-private */
    public boolean doHandle(Request request, Response response) {
        request.setRequestExecutorProvider(getRequestExecutorProvider());
        request.setSessionCookieName(getSessionCookieName());
        request.setSessionManager(getSessionManager(request));
        response.setErrorPageGenerator(getErrorPageGenerator(request));
        if (request.requiresAcknowledgement() && !sendAcknowledgment(request, response)) {
            return true;
        }
        try {
            RequestURIRef requestURIRef = request.getRequest().getRequestURIRef();
            requestURIRef.setDefaultURIEncoding(this.requestURIEncoding);
            if (this.decodeURL) {
                try {
                    requestURIRef.getDecodedRequestURIBC(this.allowEncodedSlash);
                } catch (CharConversionException e) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
                    response.setDetailMessage("Invalid URI: " + e.getMessage());
                    return true;
                }
            }
            response.getResponse().setAllowCustomReasonPhrase(this.allowCustomStatusMessage);
            request.parseSessionId();
            return runService(request, response);
        } catch (Exception t) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLER_SERVICE_ERROR(), t);
            ErrorPageGenerator errorPageGenerator = response.getErrorPageGenerator();
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR_500;
            HtmlHelper.setErrorAndSendErrorPage(request, response, errorPageGenerator, 500, httpStatus.getReasonPhrase(), httpStatus.getReasonPhrase(), t);
            return true;
        }
    }

    private boolean runService(Request request, Response response) {
        Executor threadPool = getRequestExecutorProvider().getExecutor(request);
        HttpServerFilter httpServerFilter = request.getServerFilter();
        Connection connection = request.getContext().getConnection();
        if (threadPool == null) {
            SuspendStatus suspendStatus = response.initSuspendStatus();
            HttpServerProbeNotifier.notifyBeforeService(httpServerFilter, connection, request, this);
            service(request, response);
            return !suspendStatus.getAndInvalidate();
        }
        FilterChainContext ctx = request.getContext();
        ctx.suspend();
        final Response response2 = response;
        final HttpServerFilter httpServerFilter2 = httpServerFilter;
        final Connection connection2 = connection;
        final Request request2 = request;
        final FilterChainContext filterChainContext = ctx;
        threadPool.execute(new Runnable() {
            public void run() {
                SuspendStatus suspendStatus = response2.initSuspendStatus();
                try {
                    HttpServerProbeNotifier.notifyBeforeService(httpServerFilter2, connection2, request2, HttpHandler.this);
                    HttpHandler.this.service(request2, response2);
                    if (suspendStatus.getAndInvalidate()) {
                        return;
                    }
                } catch (Throwable th) {
                    if (0 == 0) {
                        filterChainContext.resume();
                    }
                    throw th;
                }
                filterChainContext.resume();
            }
        });
        return false;
    }

    public void start() {
    }

    public void destroy() {
    }

    public boolean isAllowCustomStatusMessage() {
        return this.allowCustomStatusMessage;
    }

    public void setAllowCustomStatusMessage(boolean allowCustomStatusMessage2) {
        this.allowCustomStatusMessage = allowCustomStatusMessage2;
    }

    public boolean isAllowEncodedSlash() {
        return this.allowEncodedSlash;
    }

    public void setAllowEncodedSlash(boolean allowEncodedSlash2) {
        this.allowEncodedSlash = allowEncodedSlash2;
    }

    public Charset getRequestURIEncoding() {
        return this.requestURIEncoding;
    }

    public void setRequestURIEncoding(Charset requestURIEncoding2) {
        this.requestURIEncoding = requestURIEncoding2;
    }

    public void setRequestURIEncoding(String requestURIEncoding2) {
        this.requestURIEncoding = Charsets.lookupCharset(requestURIEncoding2);
    }

    public RequestExecutorProvider getRequestExecutorProvider() {
        return DEFAULT_REQUEST_EXECUTOR_PROVIDER;
    }

    /* access modifiers changed from: protected */
    public ErrorPageGenerator getErrorPageGenerator(Request request) {
        return request.getHttpFilter().getConfiguration().getDefaultErrorPageGenerator();
    }

    /* access modifiers changed from: protected */
    public String getSessionCookieName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public SessionManager getSessionManager(Request request) {
        return request.getHttpFilter().getConfiguration().getSessionManager();
    }

    /* access modifiers changed from: protected */
    public boolean sendAcknowledgment(Request request, Response response) {
        if ("100-continue".equalsIgnoreCase(request.getHeader(Header.Expect))) {
            response.setStatus(HttpStatus.CONINTUE_100);
            response.sendAcknowledgement();
            return true;
        }
        response.setStatus(HttpStatus.EXPECTATION_FAILED_417);
        return false;
    }

    /* access modifiers changed from: protected */
    public void setDecodeUrl(boolean decodeURL2) {
        this.decodeURL = decodeURL2;
    }

    protected static void updatePaths(Request request, MappingData mappingData) {
        request.setContextPath(mappingData.contextPath.toString());
        request.setPathInfo(mappingData.pathInfo.toString());
        request.setHttpHandlerPath(mappingData.wrapperPath.toString());
    }

    /* access modifiers changed from: protected */
    public void setDispatcherHelper(DispatcherHelper dispatcherHelper) {
    }
}
