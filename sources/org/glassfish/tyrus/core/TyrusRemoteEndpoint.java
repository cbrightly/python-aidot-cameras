package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;
import jakarta.websocket.EncodeException;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.SendHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.WriterInfo;

public abstract class TyrusRemoteEndpoint implements RemoteEndpoint {
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Logger.getLogger(TyrusRemoteEndpoint.class.getName());
    private final TyrusEndpointWrapper endpointWrapper;
    final TyrusSession session;
    final TyrusWebSocket webSocket;

    private TyrusRemoteEndpoint(TyrusSession session2, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper2) {
        this.webSocket = socket;
        this.endpointWrapper = endpointWrapper2;
        this.session = session2;
    }

    public static class Basic extends TyrusRemoteEndpoint implements RemoteEndpoint.Basic {
        Basic(TyrusSession session, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper) {
            super(session, socket, endpointWrapper);
        }

        public void sendText(String text) {
            Utils.checkNotNull(text, "text");
            this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending text message: ", text);
            try {
                processFuture(this.webSocket.sendText(text, new WriterInfo(WriterInfo.MessageType.TEXT, WriterInfo.RemoteEndpointType.BASIC)));
            } finally {
                this.session.restartIdleTimeoutExecutor();
            }
        }

        public void sendBinary(ByteBuffer data) {
            Utils.checkNotNull(data, "data");
            this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending binary message");
            try {
                processFuture(this.webSocket.sendBinary(Utils.getRemainingArray(data), new WriterInfo(WriterInfo.MessageType.BINARY, WriterInfo.RemoteEndpointType.BASIC)));
            } finally {
                this.session.restartIdleTimeoutExecutor();
            }
        }

        public void sendText(String partialMessage, boolean isLast) {
            Utils.checkNotNull(partialMessage, "partialMessage");
            this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending partial text message: ", partialMessage);
            try {
                processFuture(this.webSocket.sendText(partialMessage, isLast, new WriterInfo(isLast ? WriterInfo.MessageType.TEXT : WriterInfo.MessageType.TEXT_CONTINUATION, WriterInfo.RemoteEndpointType.BASIC)));
            } finally {
                this.session.restartIdleTimeoutExecutor();
            }
        }

        public void sendBinary(ByteBuffer partialByte, boolean isLast) {
            Utils.checkNotNull(partialByte, "partialByte");
            this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending partial binary message");
            try {
                processFuture(this.webSocket.sendBinary(Utils.getRemainingArray(partialByte), isLast, new WriterInfo(isLast ? WriterInfo.MessageType.BINARY : WriterInfo.MessageType.BINARY_CONTINUATION, WriterInfo.RemoteEndpointType.BASIC)));
            } finally {
                this.session.restartIdleTimeoutExecutor();
            }
        }

        private void processFuture(Future<?> future) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e2) {
                if (e2.getCause() instanceof IOException) {
                    throw ((IOException) e2.getCause());
                }
                throw new IOException(e2.getCause());
            }
        }

        public void sendObject(Object data) {
            Utils.checkNotNull(data, "data");
            try {
                sendSyncObject(data, new WriterInfo(WriterInfo.MessageType.OBJECT, WriterInfo.RemoteEndpointType.BASIC)).get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e2) {
                if (e2.getCause() instanceof IOException) {
                    throw ((IOException) e2.getCause());
                } else if (e2.getCause() instanceof EncodeException) {
                    throw ((EncodeException) e2.getCause());
                } else {
                    throw new IOException(e2.getCause());
                }
            }
            this.session.restartIdleTimeoutExecutor();
        }

        public OutputStream getSendStream() {
            return new OutputStreamToAsyncBinaryAdapter(this.webSocket);
        }

        public Writer getSendWriter() {
            return new WriterToAsyncTextAdapter(this.webSocket);
        }
    }

    public static class Async extends TyrusRemoteEndpoint implements RemoteEndpoint.Async {
        private long sendTimeout;

        public enum AsyncMessageType {
            TEXT,
            BINARY,
            OBJECT
        }

        Async(TyrusSession session, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper) {
            super(session, socket, endpointWrapper);
            if (session.getContainer() != null) {
                setSendTimeout(session.getContainer().getDefaultAsyncSendTimeout());
            }
        }

        public void sendText(String text, SendHandler handler) {
            Utils.checkNotNull(text, "text");
            Utils.checkNotNull(handler, "handler");
            this.session.restartIdleTimeoutExecutor();
            sendAsync(text, handler, AsyncMessageType.TEXT);
        }

        public Future<Void> sendText(String text) {
            Utils.checkNotNull(text, "text");
            this.session.restartIdleTimeoutExecutor();
            return sendAsync(text, AsyncMessageType.TEXT);
        }

        public Future<Void> sendBinary(ByteBuffer data) {
            Utils.checkNotNull(data, "data");
            this.session.restartIdleTimeoutExecutor();
            return sendAsync(data, AsyncMessageType.BINARY);
        }

        public void sendBinary(ByteBuffer data, SendHandler handler) {
            Utils.checkNotNull(data, "data");
            Utils.checkNotNull(handler, "handler");
            this.session.restartIdleTimeoutExecutor();
            sendAsync(data, handler, AsyncMessageType.BINARY);
        }

        public void sendObject(Object data, SendHandler handler) {
            Utils.checkNotNull(data, "data");
            Utils.checkNotNull(handler, "handler");
            this.session.restartIdleTimeoutExecutor();
            sendAsync(data, handler, AsyncMessageType.OBJECT);
        }

        public Future<Void> sendObject(Object data) {
            Utils.checkNotNull(data, "data");
            this.session.restartIdleTimeoutExecutor();
            return sendAsync(data, AsyncMessageType.OBJECT);
        }

        public long getSendTimeout() {
            return this.sendTimeout;
        }

        public void setSendTimeout(long timeoutmillis) {
            this.sendTimeout = timeoutmillis;
            this.webSocket.setWriteTimeout(timeoutmillis);
        }

        private Future<Void> sendAsync(Object message, AsyncMessageType type) {
            Future future = null;
            switch (AnonymousClass2.$SwitchMap$org$glassfish$tyrus$core$TyrusRemoteEndpoint$Async$AsyncMessageType[type.ordinal()]) {
                case 1:
                    this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending text message: ", message);
                    future = this.webSocket.sendText((String) message, new WriterInfo(WriterInfo.MessageType.TEXT, WriterInfo.RemoteEndpointType.ASYNC));
                    break;
                case 2:
                    this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending binary message");
                    future = this.webSocket.sendBinary(Utils.getRemainingArray((ByteBuffer) message), new WriterInfo(WriterInfo.MessageType.BINARY, WriterInfo.RemoteEndpointType.ASYNC));
                    break;
                case 3:
                    future = sendSyncObject(message, new WriterInfo(WriterInfo.MessageType.OBJECT, WriterInfo.RemoteEndpointType.ASYNC));
                    break;
            }
            final Future future2 = future;
            return new Future<Void>() {
                public boolean cancel(boolean mayInterruptIfRunning) {
                    return future2.cancel(mayInterruptIfRunning);
                }

                public boolean isCancelled() {
                    return future2.isCancelled();
                }

                public boolean isDone() {
                    return future2.isDone();
                }

                public Void get() {
                    future2.get();
                    return null;
                }

                public Void get(long timeout, TimeUnit unit) {
                    future2.get(timeout, unit);
                    return null;
                }
            };
        }

        private void sendAsync(Object message, SendHandler handler, AsyncMessageType type) {
            switch (AnonymousClass2.$SwitchMap$org$glassfish$tyrus$core$TyrusRemoteEndpoint$Async$AsyncMessageType[type.ordinal()]) {
                case 1:
                    this.webSocket.sendText((String) message, handler, new WriterInfo(WriterInfo.MessageType.TEXT, WriterInfo.RemoteEndpointType.ASYNC));
                    return;
                case 2:
                    this.webSocket.sendBinary(Utils.getRemainingArray((ByteBuffer) message), handler, new WriterInfo(WriterInfo.MessageType.BINARY, WriterInfo.RemoteEndpointType.ASYNC));
                    return;
                case 3:
                    sendSyncObject(message, handler, new WriterInfo(WriterInfo.MessageType.OBJECT, WriterInfo.RemoteEndpointType.ASYNC));
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.glassfish.tyrus.core.TyrusRemoteEndpoint$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$core$TyrusRemoteEndpoint$Async$AsyncMessageType;

        static {
            int[] iArr = new int[Async.AsyncMessageType.values().length];
            $SwitchMap$org$glassfish$tyrus$core$TyrusRemoteEndpoint$Async$AsyncMessageType = iArr;
            try {
                iArr[Async.AsyncMessageType.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusRemoteEndpoint$Async$AsyncMessageType[Async.AsyncMessageType.BINARY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusRemoteEndpoint$Async$AsyncMessageType[Async.AsyncMessageType.OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Future<?> sendSyncObject(Object o, WriterInfo writerInfo) {
        try {
            this.session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending object: ", o);
            Object toSend = this.endpointWrapper.doEncode(this.session, o);
            if (toSend instanceof String) {
                return this.webSocket.sendText((String) toSend, writerInfo);
            }
            if (toSend instanceof ByteBuffer) {
                return this.webSocket.sendBinary(Utils.getRemainingArray((ByteBuffer) toSend), writerInfo);
            }
            if (toSend instanceof StringWriter) {
                return this.webSocket.sendText(((StringWriter) toSend).getBuffer().toString(), writerInfo);
            } else if (toSend instanceof ByteArrayOutputStream) {
                return this.webSocket.sendBinary(((ByteArrayOutputStream) toSend).toByteArray(), writerInfo);
            } else {
                return null;
            }
        } catch (Exception e) {
            return new Future<Object>() {
                public boolean cancel(boolean mayInterruptIfRunning) {
                    return false;
                }

                public boolean isCancelled() {
                    return false;
                }

                public boolean isDone() {
                    return true;
                }

                public Object get() {
                    throw new ExecutionException(e);
                }

                public Object get(long timeout, TimeUnit unit) {
                    throw new ExecutionException(e);
                }
            };
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.io.ByteArrayOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.io.ByteArrayOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.io.StringWriter} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.io.ByteArrayOutputStream} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendSyncObject(java.lang.Object r6, jakarta.websocket.SendHandler r7, org.glassfish.tyrus.spi.WriterInfo r8) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof java.lang.String
            if (r0 == 0) goto L_0x000d
            org.glassfish.tyrus.core.TyrusWebSocket r0 = r5.webSocket
            r1 = r6
            java.lang.String r1 = (java.lang.String) r1
            r0.sendText((java.lang.String) r1, (jakarta.websocket.SendHandler) r7, (org.glassfish.tyrus.spi.WriterInfo) r8)
            goto L_0x0066
        L_0x000d:
            r0 = 0
            org.glassfish.tyrus.core.TyrusEndpointWrapper r1 = r5.endpointWrapper     // Catch:{ Exception -> 0x0018 }
            org.glassfish.tyrus.core.TyrusSession r2 = r5.session     // Catch:{ Exception -> 0x0018 }
            java.lang.Object r1 = r1.doEncode(r2, r6)     // Catch:{ Exception -> 0x0018 }
            r0 = r1
            goto L_0x0021
        L_0x0018:
            r1 = move-exception
            jakarta.websocket.i r2 = new jakarta.websocket.i
            r2.<init>(r1)
            r7.a(r2)
        L_0x0021:
            boolean r1 = r0 instanceof java.lang.String
            if (r1 == 0) goto L_0x002e
            org.glassfish.tyrus.core.TyrusWebSocket r1 = r5.webSocket
            r2 = r0
            java.lang.String r2 = (java.lang.String) r2
            r1.sendText((java.lang.String) r2, (jakarta.websocket.SendHandler) r7, (org.glassfish.tyrus.spi.WriterInfo) r8)
            goto L_0x0066
        L_0x002e:
            boolean r1 = r0 instanceof java.nio.ByteBuffer
            if (r1 == 0) goto L_0x003f
            org.glassfish.tyrus.core.TyrusWebSocket r1 = r5.webSocket
            r2 = r0
            java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
            byte[] r2 = org.glassfish.tyrus.core.Utils.getRemainingArray(r2)
            r1.sendBinary((byte[]) r2, (jakarta.websocket.SendHandler) r7, (org.glassfish.tyrus.spi.WriterInfo) r8)
            goto L_0x0066
        L_0x003f:
            boolean r1 = r0 instanceof java.io.StringWriter
            if (r1 == 0) goto L_0x0054
            r1 = r0
            java.io.StringWriter r1 = (java.io.StringWriter) r1
            java.lang.StringBuffer r2 = r1.getBuffer()
            org.glassfish.tyrus.core.TyrusWebSocket r3 = r5.webSocket
            java.lang.String r4 = r2.toString()
            r3.sendText((java.lang.String) r4, (jakarta.websocket.SendHandler) r7, (org.glassfish.tyrus.spi.WriterInfo) r8)
            goto L_0x0065
        L_0x0054:
            boolean r1 = r0 instanceof java.io.ByteArrayOutputStream
            if (r1 == 0) goto L_0x0065
            r1 = r0
            java.io.ByteArrayOutputStream r1 = (java.io.ByteArrayOutputStream) r1
            org.glassfish.tyrus.core.TyrusWebSocket r2 = r5.webSocket
            byte[] r3 = r1.toByteArray()
            r2.sendBinary((byte[]) r3, (jakarta.websocket.SendHandler) r7, (org.glassfish.tyrus.spi.WriterInfo) r8)
            goto L_0x0066
        L_0x0065:
        L_0x0066:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.TyrusRemoteEndpoint.sendSyncObject(java.lang.Object, jakarta.websocket.SendHandler, org.glassfish.tyrus.spi.WriterInfo):void");
    }

    public void sendPing(ByteBuffer applicationData) {
        if (applicationData == null || applicationData.remaining() <= 125) {
            this.session.restartIdleTimeoutExecutor();
            this.webSocket.sendPing(Utils.getRemainingArray(applicationData));
            return;
        }
        throw new IllegalArgumentException(LocalizationMessages.APPLICATION_DATA_TOO_LONG("Ping"));
    }

    public void sendPong(ByteBuffer applicationData) {
        if (applicationData == null || applicationData.remaining() <= 125) {
            this.session.restartIdleTimeoutExecutor();
            this.webSocket.sendPong(Utils.getRemainingArray(applicationData));
            return;
        }
        throw new IllegalArgumentException(LocalizationMessages.APPLICATION_DATA_TOO_LONG("Pong"));
    }

    public String toString() {
        return "Wrapped: " + getClass().getSimpleName();
    }

    public void setBatchingAllowed(boolean allowed) {
    }

    public boolean getBatchingAllowed() {
        return false;
    }

    public void flushBatch() {
    }

    public void close(CloseReason cr) {
        Logger logger = LOGGER;
        logger.fine("Close public void close(CloseReason cr): " + cr);
        this.webSocket.close(cr);
    }
}
