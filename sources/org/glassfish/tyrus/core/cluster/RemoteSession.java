package org.glassfish.tyrus.core.cluster;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Extension;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.SendHandler;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.i;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.Utils;

public class RemoteSession implements Session, DistributedSession {
    /* access modifiers changed from: private */
    public static final Integer SYNC_SEND_TIMEOUT = 30;
    private final RemoteEndpoint.Async asyncRemote;
    private final RemoteEndpoint.Basic basicRemote;
    private final ClusterContext clusterContext;
    private final String connectionId;
    private final Map<DistributedMapKey, Object> distributedPropertyMap;
    private final TyrusEndpointWrapper endpointWrapper;
    private final String sessionId;

    public enum DistributedMapKey implements Serializable {
        NEGOTIATED_SUBPROTOCOL("negotiatedSubprotocol"),
        NEGOTIATED_EXTENSIONS("negotiatedExtensions"),
        SECURE("secure"),
        MAX_IDLE_TIMEOUT("maxIdleTimeout"),
        MAX_BINARY_MESSAGE_BUFFER_SIZE("maxBinaryBufferSize"),
        MAX_TEXT_MESSAGE_BUFFER_SIZE("maxTextBufferSize"),
        REQUEST_URI("requestURI"),
        REQUEST_PARAMETER_MAP("requestParameterMap"),
        QUERY_STRING("queryString"),
        PATH_PARAMETERS("pathParameters"),
        USER_PRINCIPAL("userPrincipal"),
        CONNECTION_ID("connectionId");
        
        private final String key;

        private DistributedMapKey(String key2) {
            this.key = key2;
        }

        public String toString() {
            return this.key;
        }
    }

    public RemoteSession(String sessionId2, ClusterContext clusterContext2, Map<DistributedMapKey, Object> distributedPropertyMap2, TyrusEndpointWrapper endpointWrapper2, Session session) {
        this.sessionId = sessionId2;
        this.clusterContext = clusterContext2;
        this.distributedPropertyMap = distributedPropertyMap2;
        this.endpointWrapper = endpointWrapper2;
        this.connectionId = distributedPropertyMap2.get(DistributedMapKey.CONNECTION_ID).toString();
        final ClusterContext clusterContext3 = clusterContext2;
        final String str = sessionId2;
        final TyrusEndpointWrapper tyrusEndpointWrapper = endpointWrapper2;
        final Session session2 = session;
        this.basicRemote = new RemoteEndpoint.Basic() {
            public void sendText(String text) {
                Utils.checkNotNull(text, "text");
                processFuture(clusterContext3.sendText(str, text));
            }

            public void sendBinary(ByteBuffer data) {
                Utils.checkNotNull(data, "data");
                processFuture(clusterContext3.sendBinary(str, Utils.getRemainingArray(data)));
            }

            public void sendText(String partialMessage, boolean isLast) {
                Utils.checkNotNull(partialMessage, "partialMessage");
                processFuture(clusterContext3.sendText(str, partialMessage, isLast));
            }

            public void sendBinary(ByteBuffer partialByte, boolean isLast) {
                Utils.checkNotNull(partialByte, "partialByte");
                processFuture(clusterContext3.sendBinary(str, Utils.getRemainingArray(partialByte), isLast));
            }

            private void processFuture(Future<?> future) {
                try {
                    future.get((long) RemoteSession.SYNC_SEND_TIMEOUT.intValue(), TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e2) {
                    if (e2.getCause() instanceof IOException) {
                        throw ((IOException) e2.getCause());
                    }
                    throw new IOException(e2.getCause());
                } catch (TimeoutException e3) {
                    throw new IOException(e3.getCause());
                }
            }

            public void sendPing(ByteBuffer applicationData) {
                if (applicationData == null || applicationData.remaining() <= 125) {
                    clusterContext3.sendPing(str, Utils.getRemainingArray(applicationData));
                    return;
                }
                throw new IllegalArgumentException("Ping applicationData exceeded the maximum allowed payload of 125 bytes.");
            }

            public void sendPong(ByteBuffer applicationData) {
                if (applicationData == null || applicationData.remaining() <= 125) {
                    clusterContext3.sendPong(str, Utils.getRemainingArray(applicationData));
                    return;
                }
                throw new IllegalArgumentException("Pong applicationData exceeded the maximum allowed payload of 125 bytes.");
            }

            public void sendObject(Object data) {
                Future<Void> future;
                Utils.checkNotNull(data, "data");
                Object toSend = tyrusEndpointWrapper.doEncode(session2, data);
                if (toSend instanceof String) {
                    future = clusterContext3.sendText(str, (String) toSend);
                } else if (toSend instanceof ByteBuffer) {
                    future = clusterContext3.sendBinary(str, Utils.getRemainingArray((ByteBuffer) toSend));
                } else if (toSend instanceof StringWriter) {
                    future = clusterContext3.sendText(str, ((StringWriter) toSend).getBuffer().toString());
                } else if (toSend instanceof ByteArrayOutputStream) {
                    future = clusterContext3.sendBinary(str, ((ByteArrayOutputStream) toSend).toByteArray());
                } else {
                    return;
                }
                processFuture(future);
            }

            public OutputStream getSendStream() {
                return new OutputStream() {
                    public void write(byte[] b, int off, int len) {
                        if (b == null) {
                            throw new NullPointerException();
                        } else if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
                            throw new IndexOutOfBoundsException();
                        } else if (len != 0) {
                            byte[] toSend = new byte[len];
                            System.arraycopy(b, off, toSend, 0, len);
                            AnonymousClass1 r2 = AnonymousClass1.this;
                            try {
                                clusterContext3.sendBinary(str, toSend, false).get();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            } catch (ExecutionException e2) {
                                if (e2.getCause() instanceof IOException) {
                                    throw ((IOException) e2.getCause());
                                }
                                throw new IOException(e2.getCause());
                            }
                        }
                    }

                    public void write(int i) {
                        byte[] byteArray = {(byte) i};
                        write(byteArray, 0, byteArray.length);
                    }

                    public void flush() {
                    }

                    public void close() {
                        AnonymousClass1 r0 = AnonymousClass1.this;
                        clusterContext3.sendBinary(str, new byte[0], true);
                    }
                };
            }

            public Writer getSendWriter() {
                return new Writer() {
                    private String buffer = null;

                    private void sendBuffer(boolean last) {
                        AnonymousClass1 r0 = AnonymousClass1.this;
                        clusterContext3.sendText(str, this.buffer, last);
                    }

                    public void write(char[] chars, int index, int len) {
                        if (this.buffer != null) {
                            sendBuffer(false);
                        }
                        this.buffer = new String(chars).substring(index, index + len);
                    }

                    public void flush() {
                        sendBuffer(false);
                        this.buffer = null;
                    }

                    public void close() {
                        sendBuffer(true);
                    }
                };
            }

            public void setBatchingAllowed(boolean allowed) {
            }

            public boolean getBatchingAllowed() {
                return false;
            }

            public void flushBatch() {
            }
        };
        this.asyncRemote = new RemoteEndpoint.Async() {
            public long getSendTimeout() {
                return 0;
            }

            public void setSendTimeout(long timeoutmillis) {
            }

            public void sendText(String text, SendHandler handler) {
                Utils.checkNotNull(text, "text");
                Utils.checkNotNull(handler, "handler");
                clusterContext3.sendText(str, text, handler);
            }

            public Future<Void> sendText(String text) {
                Utils.checkNotNull(text, "text");
                return clusterContext3.sendText(str, text);
            }

            public Future<Void> sendBinary(ByteBuffer data) {
                Utils.checkNotNull(data, "data");
                return clusterContext3.sendBinary(str, Utils.getRemainingArray(data));
            }

            public void sendBinary(ByteBuffer data, SendHandler handler) {
                Utils.checkNotNull(data, "data");
                Utils.checkNotNull(handler, "handler");
                clusterContext3.sendBinary(str, Utils.getRemainingArray(data), handler);
            }

            public Future<Void> sendObject(Object data) {
                Utils.checkNotNull(data, "data");
                try {
                    Object toSend = tyrusEndpointWrapper.doEncode(session2, data);
                    if (toSend instanceof String) {
                        return clusterContext3.sendText(str, (String) toSend);
                    }
                    if (toSend instanceof ByteBuffer) {
                        return clusterContext3.sendBinary(str, Utils.getRemainingArray((ByteBuffer) toSend));
                    }
                    if (toSend instanceof StringWriter) {
                        return clusterContext3.sendText(str, ((StringWriter) toSend).getBuffer().toString());
                    } else if (toSend instanceof ByteArrayOutputStream) {
                        return clusterContext3.sendBinary(str, ((ByteArrayOutputStream) toSend).toByteArray());
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    return new Future<Void>() {
                        public boolean cancel(boolean mayInterruptIfRunning) {
                            return false;
                        }

                        public boolean isCancelled() {
                            return false;
                        }

                        public boolean isDone() {
                            return true;
                        }

                        public Void get() {
                            throw new ExecutionException(e);
                        }

                        public Void get(long timeout, TimeUnit unit) {
                            throw new ExecutionException(e);
                        }
                    };
                }
            }

            public void sendObject(Object data, SendHandler handler) {
                Utils.checkNotNull(data, "data");
                if (data instanceof String) {
                    clusterContext3.sendText(str, (String) data, handler);
                    return;
                }
                try {
                    Object toSend = tyrusEndpointWrapper.doEncode(session2, data);
                    if (toSend instanceof String) {
                        clusterContext3.sendText(str, (String) toSend, handler);
                    } else if (toSend instanceof ByteBuffer) {
                        clusterContext3.sendBinary(str, Utils.getRemainingArray((ByteBuffer) toSend), handler);
                    } else if (toSend instanceof StringWriter) {
                        clusterContext3.sendText(str, ((StringWriter) toSend).getBuffer().toString(), handler);
                    } else if (toSend instanceof ByteArrayOutputStream) {
                        clusterContext3.sendBinary(str, ((ByteArrayOutputStream) toSend).toByteArray(), handler);
                    }
                } catch (Throwable t) {
                    handler.a(new i(t));
                }
            }

            public void sendPing(ByteBuffer applicationData) {
                if (applicationData == null || applicationData.remaining() <= 125) {
                    clusterContext3.sendPing(str, Utils.getRemainingArray(applicationData));
                    return;
                }
                throw new IllegalArgumentException("Ping applicationData exceeded the maximum allowed payload of 125 bytes.");
            }

            public void sendPong(ByteBuffer applicationData) {
                if (applicationData == null || applicationData.remaining() <= 125) {
                    clusterContext3.sendPong(str, Utils.getRemainingArray(applicationData));
                    return;
                }
                throw new IllegalArgumentException("Pong applicationData exceeded the maximum allowed payload of 125 bytes.");
            }

            public void setBatchingAllowed(boolean allowed) {
            }

            public boolean getBatchingAllowed() {
                return false;
            }

            public void flushBatch() {
            }
        };
    }

    public String getProtocolVersion() {
        return "13";
    }

    public String getNegotiatedSubprotocol() {
        return (String) this.distributedPropertyMap.get(DistributedMapKey.NEGOTIATED_SUBPROTOCOL);
    }

    public List<Extension> getNegotiatedExtensions() {
        return (List) this.distributedPropertyMap.get(DistributedMapKey.NEGOTIATED_EXTENSIONS);
    }

    public boolean isSecure() {
        return ((Boolean) this.distributedPropertyMap.get(DistributedMapKey.SECURE)).booleanValue();
    }

    public boolean isOpen() {
        return this.clusterContext.isSessionOpen(this.sessionId, this.endpointWrapper.getEndpointPath());
    }

    public long getMaxIdleTimeout() {
        return ((Long) this.distributedPropertyMap.get(DistributedMapKey.MAX_IDLE_TIMEOUT)).longValue();
    }

    public int getMaxBinaryMessageBufferSize() {
        return ((Integer) this.distributedPropertyMap.get(DistributedMapKey.MAX_BINARY_MESSAGE_BUFFER_SIZE)).intValue();
    }

    public int getMaxTextMessageBufferSize() {
        return ((Integer) this.distributedPropertyMap.get(DistributedMapKey.MAX_TEXT_MESSAGE_BUFFER_SIZE)).intValue();
    }

    public RemoteEndpoint.Async getAsyncRemote() {
        return this.asyncRemote;
    }

    public RemoteEndpoint.Basic getBasicRemote() {
        return this.basicRemote;
    }

    public String getId() {
        return this.sessionId;
    }

    public void close() {
        this.clusterContext.close(this.sessionId);
    }

    public void close(CloseReason closeReason) {
        this.clusterContext.close(this.sessionId, closeReason);
    }

    public URI getRequestURI() {
        return (URI) this.distributedPropertyMap.get(DistributedMapKey.REQUEST_URI);
    }

    public Map<String, List<String>> getRequestParameterMap() {
        return (Map) this.distributedPropertyMap.get(DistributedMapKey.REQUEST_PARAMETER_MAP);
    }

    public String getQueryString() {
        return (String) this.distributedPropertyMap.get(DistributedMapKey.QUERY_STRING);
    }

    public Map<String, String> getPathParameters() {
        return (Map) this.distributedPropertyMap.get(DistributedMapKey.PATH_PARAMETERS);
    }

    public Map<String, Object> getUserProperties() {
        throw new UnsupportedOperationException();
    }

    public Map<String, Object> getDistributedProperties() {
        return this.clusterContext.getDistributedUserProperties(this.connectionId);
    }

    public Principal getUserPrincipal() {
        return (Principal) this.distributedPropertyMap.get(DistributedMapKey.USER_PRINCIPAL);
    }

    public String toString() {
        return "RemoteSession{sessionId='" + this.sessionId + '\'' + ", clusterContext=" + this.clusterContext + '}';
    }

    public WebSocketContainer getContainer() {
        throw new UnsupportedOperationException();
    }

    public void addMessageHandler(MessageHandler handler) {
        throw new UnsupportedOperationException();
    }

    public <T> void addMessageHandler(Class<T> cls, MessageHandler.Whole<T> whole) {
        throw new UnsupportedOperationException();
    }

    public <T> void addMessageHandler(Class<T> cls, MessageHandler.Partial<T> partial) {
        throw new UnsupportedOperationException();
    }

    public Set<MessageHandler> getMessageHandlers() {
        throw new UnsupportedOperationException();
    }

    public void removeMessageHandler(MessageHandler handler) {
        throw new UnsupportedOperationException();
    }

    public void setMaxIdleTimeout(long milliseconds) {
        throw new UnsupportedOperationException();
    }

    public void setMaxBinaryMessageBufferSize(int length) {
        throw new UnsupportedOperationException();
    }

    public void setMaxTextMessageBufferSize(int length) {
        throw new UnsupportedOperationException();
    }

    public Set<Session> getOpenSessions() {
        throw new UnsupportedOperationException();
    }
}
