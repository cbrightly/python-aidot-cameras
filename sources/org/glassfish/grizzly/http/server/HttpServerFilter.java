package org.glassfish.grizzly.http.server;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.ShutdownEvent;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.Futures;

public class HttpServerFilter extends BaseFilter implements MonitoringAware<HttpServerProbe> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOGGER = Grizzly.logger(HttpHandler.class);
    private final AtomicInteger activeRequestsCounter = new AtomicInteger();
    private final ServerFilterConfiguration config;
    private final FlushResponseHandler flushResponseHandler = new FlushResponseHandler();
    private volatile HttpHandler httpHandler;
    private final Attribute<Request> httpRequestInProgress;
    protected final DefaultMonitoringConfig<HttpServerProbe> monitoringConfig = new DefaultMonitoringConfig<HttpServerProbe>(HttpServerProbe.class) {
        public Object createManagementObject() {
            return HttpServerFilter.this.createJmxManagementObject();
        }
    };
    private AtomicReference<FutureImpl<HttpServerFilter>> shutdownCompletionFuture;
    private AtomicBoolean shuttingDown = new AtomicBoolean();
    private final DelayedExecutor.DelayQueue<Response.SuspendTimeout> suspendedResponseQueue;

    public HttpServerFilter(ServerFilterConfiguration config2, DelayedExecutor delayedExecutor) {
        this.config = config2;
        this.suspendedResponseQueue = Response.createDelayQueue(delayedExecutor);
        this.httpRequestInProgress = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("HttpServerFilter.Request");
    }

    public HttpHandler getHttpHandler() {
        return this.httpHandler;
    }

    public void setHttpHandler(HttpHandler httpHandler2) {
        this.httpHandler = httpHandler2;
    }

    public ServerFilterConfiguration getConfiguration() {
        return this.config;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0150  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.glassfish.grizzly.filterchain.NextAction handleRead(org.glassfish.grizzly.filterchain.FilterChainContext r24) {
        /*
            r23 = this;
            r7 = r23
            r8 = r24
            org.glassfish.grizzly.http.HttpContext r0 = org.glassfish.grizzly.http.HttpContext.get(r24)
            if (r0 == 0) goto L_0x0189
            java.lang.Object r9 = r24.getMessage()
            org.glassfish.grizzly.Connection r10 = r24.getConnection()
            boolean r0 = org.glassfish.grizzly.http.HttpPacket.isHttp(r9)
            if (r0 == 0) goto L_0x017d
            r11 = r9
            org.glassfish.grizzly.http.HttpContent r11 = (org.glassfish.grizzly.http.HttpContent) r11
            org.glassfish.grizzly.http.HttpHeader r0 = r11.getHttpHeader()
            org.glassfish.grizzly.http.ProcessingState r0 = r0.getProcessingState()
            org.glassfish.grizzly.http.HttpContext r12 = r0.getHttpContext()
            org.glassfish.grizzly.attributes.Attribute<org.glassfish.grizzly.http.server.Request> r0 = r7.httpRequestInProgress
            java.lang.Object r0 = r0.get((org.glassfish.grizzly.attributes.AttributeStorage) r12)
            r1 = r0
            org.glassfish.grizzly.http.server.Request r1 = (org.glassfish.grizzly.http.server.Request) r1
            if (r1 != 0) goto L_0x0155
            org.glassfish.grizzly.http.HttpHeader r0 = r11.getHttpHeader()
            r13 = r0
            org.glassfish.grizzly.http.HttpRequestPacket r13 = (org.glassfish.grizzly.http.HttpRequestPacket) r13
            org.glassfish.grizzly.http.HttpResponsePacket r14 = r13.getResponse()
            org.glassfish.grizzly.http.server.Request r15 = org.glassfish.grizzly.http.server.Request.create()
            org.glassfish.grizzly.http.util.Parameters r0 = r15.parameters
            org.glassfish.grizzly.http.server.ServerFilterConfiguration r1 = r7.config
            int r1 = r1.getMaxRequestParameters()
            r0.setLimit(r1)
            org.glassfish.grizzly.attributes.Attribute<org.glassfish.grizzly.http.server.Request> r0 = r7.httpRequestInProgress
            r0.set((org.glassfish.grizzly.attributes.AttributeStorage) r12, r15)
            org.glassfish.grizzly.http.server.Response r6 = r15.getResponse()
            r15.initialize(r13, r8, r7)
            org.glassfish.grizzly.utils.DelayedExecutor$DelayQueue<org.glassfish.grizzly.http.server.Response$SuspendTimeout> r5 = r7.suspendedResponseQueue
            r1 = r6
            r2 = r15
            r3 = r14
            r4 = r24
            r22 = r12
            r12 = r6
            r6 = r23
            r1.initialize(r2, r3, r4, r5, r6)
            org.glassfish.grizzly.http.server.ServerFilterConfiguration r0 = r7.config
            boolean r0 = r0.isGracefulShutdownSupported()
            if (r0 == 0) goto L_0x0079
            java.util.concurrent.atomic.AtomicInteger r0 = r7.activeRequestsCounter
            r0.incrementAndGet()
            org.glassfish.grizzly.http.server.HttpServerFilter$FlushResponseHandler r0 = r7.flushResponseHandler
            r15.addAfterServiceListener(r0)
        L_0x0079:
            org.glassfish.grizzly.http.server.HttpServerProbeNotifier.notifyRequestReceive(r7, r10, r15)
            r1 = 0
            r2 = 1
            r8.setMessage(r12)     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            java.util.concurrent.atomic.AtomicBoolean r0 = r7.shuttingDown     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            boolean r0 = r0.get()     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            if (r0 == 0) goto L_0x00ae
            org.glassfish.grizzly.http.HttpResponsePacket r0 = r12.getResponse()     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            org.glassfish.grizzly.http.ProcessingState r0 = r0.getProcessingState()     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            r0.setError(r2)     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            org.glassfish.grizzly.http.server.ServerFilterConfiguration r0 = r7.config     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            org.glassfish.grizzly.http.server.ErrorPageGenerator r17 = r0.getDefaultErrorPageGenerator()     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            r18 = 503(0x1f7, float:7.05E-43)
            org.glassfish.grizzly.http.util.HttpStatus r0 = org.glassfish.grizzly.http.util.HttpStatus.SERVICE_UNAVAILABLE_503     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            java.lang.String r19 = r0.getReasonPhrase()     // Catch:{ Exception -> 0x0114, all -> 0x0101 }
            java.lang.String r20 = "The server is being shutting down..."
            r21 = 0
            r3 = r15
            r16 = r12
            org.glassfish.grizzly.http.server.util.HtmlHelper.setErrorAndSendErrorPage(r15, r16, r17, r18, r19, r20, r21)     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            goto L_0x0148
        L_0x00ae:
            r3 = r15
            org.glassfish.grizzly.http.server.ServerFilterConfiguration r0 = r7.config     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            boolean r0 = r0.isPassTraceRequest()     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            if (r0 != 0) goto L_0x00c4
            org.glassfish.grizzly.http.Method r0 = r13.getMethod()     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            org.glassfish.grizzly.http.Method r4 = org.glassfish.grizzly.http.Method.TRACE     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            if (r0 != r4) goto L_0x00c4
            r7.onTraceRequest(r3, r12)     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            goto L_0x0148
        L_0x00c4:
            long r4 = r13.getContentLength()     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            boolean r0 = r7.checkMaxPostSize(r4)     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            if (r0 != 0) goto L_0x00f2
            org.glassfish.grizzly.http.HttpResponsePacket r0 = r12.getResponse()     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            org.glassfish.grizzly.http.ProcessingState r0 = r0.getProcessingState()     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            r0.setError(r2)     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            org.glassfish.grizzly.http.server.ServerFilterConfiguration r0 = r7.config     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            org.glassfish.grizzly.http.server.ErrorPageGenerator r17 = r0.getDefaultErrorPageGenerator()     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            r18 = 413(0x19d, float:5.79E-43)
            org.glassfish.grizzly.http.util.HttpStatus r0 = org.glassfish.grizzly.http.util.HttpStatus.REQUEST_ENTITY_TOO_LARGE_413     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            java.lang.String r19 = r0.getReasonPhrase()     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            java.lang.String r20 = "The request payload size exceeds the max post size limitation"
            r21 = 0
            r15 = r3
            r16 = r12
            org.glassfish.grizzly.http.server.util.HtmlHelper.setErrorAndSendErrorPage(r15, r16, r17, r18, r19, r20, r21)     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            goto L_0x0148
        L_0x00f2:
            org.glassfish.grizzly.http.server.HttpHandler r0 = r7.httpHandler     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            if (r0 == 0) goto L_0x0148
            boolean r4 = r0.doHandle(r3, r12)     // Catch:{ Exception -> 0x00ff, all -> 0x00fd }
            r2 = r2 ^ r4
            r1 = r2
            goto L_0x0148
        L_0x00fd:
            r0 = move-exception
            goto L_0x0103
        L_0x00ff:
            r0 = move-exception
            goto L_0x0116
        L_0x0101:
            r0 = move-exception
            r3 = r15
        L_0x0103:
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r4 = java.util.logging.Level.WARNING
            java.lang.String r5 = org.glassfish.grizzly.localization.LogMessages.WARNING_GRIZZLY_HTTP_SERVER_FILTER_UNEXPECTED()
            r2.log(r4, r5, r0)
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            r2.<init>(r0)
            throw r2
        L_0x0114:
            r0 = move-exception
            r3 = r15
        L_0x0116:
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.WARNING
            java.lang.String r6 = org.glassfish.grizzly.localization.LogMessages.WARNING_GRIZZLY_HTTP_SERVER_FILTER_HTTPHANDLER_INVOCATION_ERROR()
            r4.log(r5, r6, r0)
            org.glassfish.grizzly.http.ProcessingState r4 = r13.getProcessingState()
            r4.setError(r2)
            boolean r2 = r14.isCommitted()
            if (r2 != 0) goto L_0x0148
            org.glassfish.grizzly.http.server.ServerFilterConfiguration r2 = r7.config
            org.glassfish.grizzly.http.server.ErrorPageGenerator r17 = r2.getDefaultErrorPageGenerator()
            r18 = 500(0x1f4, float:7.0E-43)
            org.glassfish.grizzly.http.util.HttpStatus r2 = org.glassfish.grizzly.http.util.HttpStatus.INTERNAL_SERVER_ERROR_500
            java.lang.String r19 = r2.getReasonPhrase()
            java.lang.String r20 = r2.getReasonPhrase()
            r15 = r3
            r16 = r12
            r21 = r0
            org.glassfish.grizzly.http.server.util.HtmlHelper.setErrorAndSendErrorPage(r15, r16, r17, r18, r19, r20, r21)
        L_0x0148:
            if (r1 != 0) goto L_0x0150
            org.glassfish.grizzly.filterchain.NextAction r0 = r7.afterService(r8, r10, r3, r12)
            return r0
        L_0x0150:
            org.glassfish.grizzly.filterchain.NextAction r0 = r24.getSuspendAction()
            return r0
        L_0x0155:
            r22 = r12
            r24.suspend()     // Catch:{ all -> 0x0178 }
            org.glassfish.grizzly.filterchain.NextAction r0 = r24.getSuspendAction()     // Catch:{ all -> 0x0178 }
            org.glassfish.grizzly.http.io.InputBuffer r2 = r1.getInputBuffer()     // Catch:{ all -> 0x0178 }
            boolean r2 = r2.append(r11)     // Catch:{ all -> 0x0178 }
            if (r2 != 0) goto L_0x016c
            r24.completeAndRecycle()     // Catch:{ all -> 0x0178 }
            goto L_0x0173
        L_0x016c:
            org.glassfish.grizzly.filterchain.NextAction r2 = r24.getStopAction()     // Catch:{ all -> 0x0178 }
            r8.resume(r2)     // Catch:{ all -> 0x0178 }
        L_0x0173:
            r11.recycle()
            return r0
        L_0x0178:
            r0 = move-exception
            r11.recycle()
            throw r0
        L_0x017d:
            r0 = r9
            org.glassfish.grizzly.http.server.Response r0 = (org.glassfish.grizzly.http.server.Response) r0
            org.glassfish.grizzly.http.server.Request r1 = r0.getRequest()
            org.glassfish.grizzly.filterchain.NextAction r2 = r7.afterService(r8, r10, r1, r0)
            return r2
        L_0x0189:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.HttpServerFilter.handleRead(org.glassfish.grizzly.filterchain.FilterChainContext):org.glassfish.grizzly.filterchain.NextAction");
    }

    public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
        Request request;
        ReadHandler handler;
        HttpContext context = HttpContext.get(ctx);
        if (context != null && (request = this.httpRequestInProgress.get((AttributeStorage) context)) != null && (handler = request.getInputBuffer().getReadHandler()) != null) {
            handler.onError(error);
        }
    }

    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
        if (event.type() == ShutdownEvent.TYPE && this.shuttingDown.compareAndSet(false, true)) {
            final FutureImpl<HttpServerFilter> future = Futures.createSafeFuture();
            ((ShutdownEvent) event).addShutdownTask(new Callable<Filter>() {
                public Filter call() {
                    return (Filter) future.get();
                }
            });
            this.shutdownCompletionFuture = new AtomicReference<>(future);
            if (this.activeRequestsCounter.get() == 0) {
                future.result(this);
            }
        }
        return ctx.getInvokeAction();
    }

    public MonitoringConfig<HttpServerProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.jmx.HttpServerFilter", this, HttpServerFilter.class);
    }

    /* access modifiers changed from: protected */
    public void onTraceRequest(Request request, Response response) {
        if (this.config.isTraceEnabled()) {
            HtmlHelper.writeTraceMessage(request, response);
            return;
        }
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
        response.setHeader(Header.Allow, "POST, GET, DELETE, OPTIONS, PUT, HEAD");
    }

    /* access modifiers changed from: protected */
    public String getFullServerName() {
        return this.config.getHttpServerName() + " " + this.config.getHttpServerVersion();
    }

    private NextAction afterService(FilterChainContext ctx, Connection connection, Request request, Response response) {
        this.httpRequestInProgress.remove((AttributeStorage) request.getRequest().getProcessingState().getHttpContext());
        response.finish();
        request.onAfterService();
        HttpServerProbeNotifier.notifyRequestComplete(this, connection, response);
        boolean isBroken = request.getRequest().isContentBroken();
        if (response.suspendState != Response.SuspendState.CANCELLED) {
            response.recycle();
            request.recycle();
        }
        if (!isBroken) {
            return ctx.getStopAction();
        }
        NextAction suspendNextAction = ctx.getSuspendAction();
        ctx.completeAndRecycle();
        return suspendNextAction;
    }

    /* access modifiers changed from: private */
    public void onRequestCompleteAndResponseFlushed() {
        if (this.activeRequestsCounter.decrementAndGet() == 0 && this.shuttingDown.get()) {
            AtomicReference<FutureImpl<HttpServerFilter>> atomicReference = this.shutdownCompletionFuture;
            FutureImpl<HttpServerFilter> futureImpl = null;
            if (atomicReference != null) {
                futureImpl = atomicReference.getAndSet((Object) null);
            }
            FutureImpl<HttpServerFilter> shutdownFuture = futureImpl;
            if (shutdownFuture != null) {
                shutdownFuture.result(this);
            }
        }
    }

    private boolean checkMaxPostSize(long requestContentLength) {
        long maxPostSize = this.config.getMaxPostSize();
        return requestContentLength <= 0 || maxPostSize < 0 || maxPostSize >= requestContentLength;
    }

    public final class FlushResponseHandler extends EmptyCompletionHandler<Object> implements AfterServiceListener {
        private final FilterChainEvent event;

        private FlushResponseHandler() {
            this.event = TransportFilter.createFlushEvent(this);
        }

        public void cancelled() {
            HttpServerFilter.this.onRequestCompleteAndResponseFlushed();
        }

        public void failed(Throwable throwable) {
            HttpServerFilter.this.onRequestCompleteAndResponseFlushed();
        }

        public void completed(Object result) {
            HttpServerFilter.this.onRequestCompleteAndResponseFlushed();
        }

        public void onAfterService(Request request) {
            request.getContext().notifyDownstream(this.event);
        }
    }
}
