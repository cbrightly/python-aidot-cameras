package org.glassfish.tyrus.container.grizzly.client;

import jakarta.websocket.CloseReason;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.tyrus.container.grizzly.client.GrizzlyClientSocket;
import org.glassfish.tyrus.container.grizzly.client.TaskProcessor;
import org.glassfish.tyrus.core.CloseReasons;
import org.glassfish.tyrus.core.TyrusUpgradeResponse;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.spi.ClientEngine;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.ReadHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class GrizzlyClientFilter extends BaseFilter {
    private static final Logger LOGGER;
    private static final Attribute<Boolean> PROXY_CONNECTED;
    /* access modifiers changed from: private */
    public static final Attribute<TaskProcessor> TASK_PROCESSOR;
    /* access modifiers changed from: private */
    public static final Attribute<Connection> TYRUS_CONNECTION;
    private static final Attribute<UpgradeRequest> UPGRADE_REQUEST;
    private volatile boolean done = false;
    private final ClientEngine engine;
    private final Callable<Void> grizzlyConnector;
    private final HttpCodecFilter httpCodecFilter;
    private final boolean proxy;
    private final Map<String, String> proxyHeaders;
    /* access modifiers changed from: private */
    public final boolean sharedTransport;
    private final Filter sslFilter;
    private final UpgradeRequest upgradeRequest;

    static {
        Class<GrizzlyClientFilter> cls = GrizzlyClientFilter.class;
        LOGGER = Grizzly.logger(cls);
        AttributeBuilder attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        TYRUS_CONNECTION = attributeBuilder.createAttribute(cls.getName() + ".Connection");
        UPGRADE_REQUEST = attributeBuilder.createAttribute(cls.getName() + ".UpgradeRequest");
        TASK_PROCESSOR = attributeBuilder.createAttribute(TaskProcessor.class.getName() + ".TaskProcessor");
        PROXY_CONNECTED = attributeBuilder.createAttribute(cls.getName() + ".ProxyConnected");
    }

    GrizzlyClientFilter(ClientEngine engine2, boolean proxy2, Filter sslFilter2, HttpCodecFilter httpCodecFilter2, URI uri, boolean sharedTransport2, Map<String, String> proxyHeaders2, Callable<Void> grizzlyConnector2, UpgradeRequest upgradeRequest2) {
        this.engine = engine2;
        this.proxy = proxy2;
        this.sslFilter = sslFilter2;
        this.httpCodecFilter = httpCodecFilter2;
        this.sharedTransport = sharedTransport2;
        this.proxyHeaders = proxyHeaders2;
        this.grizzlyConnector = grizzlyConnector2;
        this.upgradeRequest = upgradeRequest2;
    }

    public NextAction handleConnect(FilterChainContext ctx) {
        LOGGER.log(Level.FINEST, "handleConnect");
        if (this.upgradeRequest == null) {
            new GrizzlyWriter(ctx.getConnection()).close();
            return ctx.getStopAction();
        }
        if (this.proxy) {
            PROXY_CONNECTED.set((AttributeStorage) ctx.getConnection(), false);
        }
        return sendRequest(ctx, this.upgradeRequest);
    }

    private NextAction sendRequest(FilterChainContext ctx, UpgradeRequest upgradeRequest2) {
        HttpRequestPacket.Builder builder = HttpRequestPacket.builder();
        if (!this.proxy || PROXY_CONNECTED.get((AttributeStorage) ctx.getConnection()).booleanValue()) {
            ctx.write(getHttpContent(upgradeRequest2));
        } else {
            UPGRADE_REQUEST.set((AttributeStorage) ctx.getConnection(), upgradeRequest2);
            URI requestURI = upgradeRequest2.getRequestURI();
            HttpRequestPacket.Builder builder2 = ((HttpRequestPacket.Builder) builder.uri(String.format("%s:%d", new Object[]{requestURI.getHost(), Integer.valueOf(Utils.getWsPort(requestURI))})).protocol(Protocol.HTTP_1_1)).method(Method.CONNECT);
            Map<String, String> map = this.proxyHeaders;
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : this.proxyHeaders.entrySet()) {
                    builder2.header(entry.getKey(), entry.getValue());
                }
            }
            ctx.write(HttpContent.builder(((HttpRequestPacket.Builder) ((HttpRequestPacket.Builder) ((HttpRequestPacket.Builder) builder2.header(Header.Host, requestURI.getHost())).header(Header.ProxyConnection, "keep-alive")).header(Header.Connection, "keep-alive")).build()).build());
            ctx.flush((CompletionHandler) null);
        }
        return ctx.getInvokeAction();
    }

    public NextAction handleClose(FilterChainContext ctx) {
        Connection connection = TYRUS_CONNECTION.get((AttributeStorage) ctx.getConnection());
        if (connection != null) {
            this.done = true;
            TASK_PROCESSOR.get((AttributeStorage) ctx.getConnection()).processTask(new CloseTask(connection, CloseReasons.CLOSED_ABNORMALLY.getCloseReason(), ctx.getConnection()));
        }
        return ctx.getStopAction();
    }

    public NextAction handleRead(FilterChainContext ctx) {
        if (this.done) {
            return ctx.getStopAction();
        }
        HttpContent message = (HttpContent) ctx.getMessage();
        org.glassfish.grizzly.Connection grizzlyConnection = ctx.getConnection();
        Connection tyrusConnection = TYRUS_CONNECTION.get((AttributeStorage) grizzlyConnection);
        HttpHeader header = message.getHttpHeader();
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "handleRead websocket: {0} content-size={1} headers=\n{2}", new Object[]{tyrusConnection, Integer.valueOf(message.getContent().remaining()), header});
        }
        if (tyrusConnection != null) {
            if (message.getContent().hasRemaining()) {
                ByteBuffer webSocketBuffer = message.getContent().toByteBuffer();
                message.recycle();
                TASK_PROCESSOR.get((AttributeStorage) ctx.getConnection()).processTask(new ProcessTask(webSocketBuffer, tyrusConnection.getReadHandler()));
            }
            return ctx.getStopAction();
        }
        HttpStatus httpStatus = ((HttpResponsePacket) message.getHttpHeader()).getHttpStatus();
        if (httpStatus.getStatusCode() != 101 && this.proxy) {
            Attribute<Boolean> attribute = PROXY_CONNECTED;
            if (!attribute.get((AttributeStorage) grizzlyConnection).booleanValue()) {
                if (httpStatus.equals(HttpStatus.OK_200)) {
                    attribute.set((AttributeStorage) grizzlyConnection, true);
                    Filter filter = this.sslFilter;
                    if (filter != null) {
                        ((GrizzlyClientSocket.FilterWrapper) filter).enable();
                    }
                    this.httpCodecFilter.resetResponseProcessing(grizzlyConnection);
                    Attribute<UpgradeRequest> attribute2 = UPGRADE_REQUEST;
                    ctx.write(getHttpContent(attribute2.get((AttributeStorage) grizzlyConnection)));
                    attribute2.remove((AttributeStorage) grizzlyConnection);
                } else {
                    this.engine.processError(new IOException(String.format("Proxy error. %s: %s", new Object[]{Integer.valueOf(httpStatus.getStatusCode()), new String(httpStatus.getReasonPhraseBytes(), "UTF-8")})));
                }
                return ctx.getInvokeAction();
            }
        }
        if (UpgradeRequest.WEBSOCKET.equalsIgnoreCase(header.getUpgrade()) || !message.getHttpHeader().isRequest()) {
            return handleHandshake(ctx, message);
        }
        return ctx.getInvokeAction();
    }

    private NextAction handleHandshake(FilterChainContext ctx, HttpContent content) {
        final GrizzlyWriter grizzlyWriter = new GrizzlyWriter(ctx.getConnection()) {
            public void close() {
                super.close();
                try {
                    if (GrizzlyClientFilter.this.sharedTransport) {
                        this.connection.close();
                    } else {
                        this.connection.getTransport().shutdownNow();
                    }
                } catch (IOException e) {
                    Logger.getLogger(GrizzlyClientFilter.class.getName()).log(Level.INFO, "Exception thrown during shutdown.", e);
                }
            }
        };
        ClientEngine.ClientUpgradeInfo clientUpgradeInfo = this.engine.processResponse(getUpgradeResponse((HttpResponsePacket) content.getHttpHeader()), grizzlyWriter, new Connection.CloseListener() {
            public void close(CloseReason reason) {
                grizzlyWriter.close();
            }
        });
        switch (AnonymousClass3.$SwitchMap$org$glassfish$tyrus$spi$ClientEngine$ClientUpgradeStatus[clientUpgradeInfo.getUpgradeStatus().ordinal()]) {
            case 1:
                grizzlyWriter.close();
                return ctx.getStopAction();
            case 2:
                grizzlyWriter.close();
                try {
                    this.grizzlyConnector.call();
                } catch (Exception e) {
                    this.engine.processError(e);
                }
                return ctx.getInvokeAction();
            case 3:
                Connection tyrusConnection = clientUpgradeInfo.createConnection();
                TASK_PROCESSOR.set((AttributeStorage) ctx.getConnection(), new TaskProcessor());
                TYRUS_CONNECTION.set((AttributeStorage) ctx.getConnection(), tyrusConnection);
                AttributeHolder attributeHolder = ctx.getAttributes();
                if (attributeHolder != null) {
                    if (attributeHolder.getAttribute("org.glassfish.tyrus.container.grizzly.WebSocketFilter.HANDSHAKE_PROCESSED") != null) {
                        return ctx.getInvokeAction();
                    }
                    attributeHolder.setAttribute("org.glassfish.tyrus.container.grizzly.WebSocketFilter.HANDSHAKE_PROCESSED", true);
                }
                if (content.getContent().hasRemaining()) {
                    return ctx.getRerunFilterAction();
                }
                content.recycle();
                return ctx.getStopAction();
            default:
                return ctx.getStopAction();
        }
    }

    /* renamed from: org.glassfish.tyrus.container.grizzly.client.GrizzlyClientFilter$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$spi$ClientEngine$ClientUpgradeStatus;

        static {
            int[] iArr = new int[ClientEngine.ClientUpgradeStatus.values().length];
            $SwitchMap$org$glassfish$tyrus$spi$ClientEngine$ClientUpgradeStatus = iArr;
            try {
                iArr[ClientEngine.ClientUpgradeStatus.UPGRADE_REQUEST_FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$spi$ClientEngine$ClientUpgradeStatus[ClientEngine.ClientUpgradeStatus.ANOTHER_UPGRADE_REQUEST_REQUIRED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$spi$ClientEngine$ClientUpgradeStatus[ClientEngine.ClientUpgradeStatus.SUCCESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static UpgradeResponse getUpgradeResponse(HttpResponsePacket httpResponsePacket) {
        TyrusUpgradeResponse tyrusUpgradeResponse = new TyrusUpgradeResponse();
        for (String name : httpResponsePacket.getHeaders().names()) {
            for (String headerValue : httpResponsePacket.getHeaders().values(name)) {
                List<String> values = tyrusUpgradeResponse.getHeaders().get(name);
                if (values == null) {
                    tyrusUpgradeResponse.getHeaders().put(name, Utils.parseHeaderValue(headerValue.trim()));
                } else {
                    values.addAll(Utils.parseHeaderValue(headerValue.trim()));
                }
            }
        }
        tyrusUpgradeResponse.setStatus(httpResponsePacket.getStatus());
        return tyrusUpgradeResponse;
    }

    private HttpContent getHttpContent(UpgradeRequest request) {
        HttpRequestPacket.Builder builder = ((HttpRequestPacket.Builder) HttpRequestPacket.builder().protocol(Protocol.HTTP_1_1)).method(Method.GET);
        StringBuilder sb = new StringBuilder();
        URI uri = URI.create(request.getRequestUri());
        sb.append(uri.getPath());
        String query = uri.getQuery();
        if (query != null) {
            sb.append('?');
            sb.append(query);
        }
        if (sb.length() == 0) {
            sb.append('/');
        }
        HttpRequestPacket.Builder builder2 = builder.uri(sb.toString());
        for (Map.Entry<String, List<String>> headerEntry : request.getHeaders().entrySet()) {
            StringBuilder finalHeaderValue = new StringBuilder();
            for (String headerValue : headerEntry.getValue()) {
                if (finalHeaderValue.length() != 0) {
                    finalHeaderValue.append(", ");
                }
                finalHeaderValue.append(headerValue);
            }
            builder2.header(headerEntry.getKey(), finalHeaderValue.toString());
        }
        return HttpContent.builder(builder2.build()).build();
    }

    public class ProcessTask extends TaskProcessor.Task {
        private final ByteBuffer buffer;
        private final ReadHandler readHandler;

        private ProcessTask(ByteBuffer buffer2, ReadHandler readHandler2) {
            this.buffer = buffer2;
            this.readHandler = readHandler2;
        }

        public void execute() {
            this.readHandler.handle(this.buffer);
        }
    }

    public class CloseTask extends TaskProcessor.Task {
        private final CloseReason closeReason;
        private final Connection connection;
        private final org.glassfish.grizzly.Connection grizzlyConnection;

        private CloseTask(Connection connection2, CloseReason closeReason2, org.glassfish.grizzly.Connection grizzlyConnection2) {
            this.connection = connection2;
            this.closeReason = closeReason2;
            this.grizzlyConnection = grizzlyConnection2;
        }

        public void execute() {
            this.connection.close(this.closeReason);
            GrizzlyClientFilter.TYRUS_CONNECTION.remove((AttributeStorage) this.grizzlyConnection);
            GrizzlyClientFilter.TASK_PROCESSOR.remove((AttributeStorage) this.grizzlyConnection);
        }
    }
}
