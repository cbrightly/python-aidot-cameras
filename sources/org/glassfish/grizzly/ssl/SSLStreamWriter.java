package org.glassfish.grizzly.ssl;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.streams.AbstractStreamWriter;
import org.glassfish.grizzly.streams.StreamReader;
import org.glassfish.grizzly.streams.StreamWriter;
import org.glassfish.grizzly.streams.TransformerStreamWriter;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.conditions.Condition;

public class SSLStreamWriter extends TransformerStreamWriter {
    public SSLStreamWriter(StreamWriter underlyingWriter) {
        super(underlyingWriter, new SSLEncoderTransformer());
    }

    public Future<SSLEngine> handshake(SSLStreamReader sslStreamReader, SSLEngineConfigurator configurator) {
        return handshake(sslStreamReader, configurator, (CompletionHandler<SSLEngine>) null);
    }

    public Future<SSLEngine> handshake(SSLStreamReader sslStreamReader, SSLEngineConfigurator configurator, CompletionHandler<SSLEngine> completionHandler) {
        Connection connection = getConnection();
        SSLEngine sslEngine = SSLUtils.getSSLEngine(getConnection());
        if (sslEngine == null) {
            sslEngine = configurator.createSSLEngine();
            SSLUtils.setSSLEngine(connection, sslEngine);
            checkBuffers(connection, sslEngine);
        }
        Logger logger = AbstractStreamWriter.logger;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "connection={0} engine={1} handshakeStatus={2}", new Object[]{connection, sslEngine, sslEngine.getHandshakeStatus()});
        }
        if (sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            sslEngine.beginHandshake();
        }
        FutureImpl<SSLEngine> future = SafeFutureImpl.create();
        HandshakeCompletionHandler hsCompletionHandler = new HandshakeCompletionHandler(future, completionHandler, sslEngine);
        SSLStreamReader sSLStreamReader = sslStreamReader;
        sslStreamReader.notifyCondition(new SSLHandshakeCondition(sslStreamReader, this, configurator, sslEngine, hsCompletionHandler), hsCompletionHandler);
        return future;
    }

    private static void checkBuffers(Connection connection, SSLEngine sslEngine) {
        int packetBufferSize = sslEngine.getSession().getPacketBufferSize();
        if (connection.getReadBufferSize() < packetBufferSize) {
            connection.setReadBufferSize(packetBufferSize);
        }
        if (connection.getWriteBufferSize() < packetBufferSize) {
            connection.setWriteBufferSize(packetBufferSize);
        }
    }

    public static class SSLHandshakeCondition implements Condition {
        private final HandshakeCompletionHandler completionHandler;
        private final SSLEngineConfigurator configurator;
        private final Connection connection;
        private final SSLEngine sslEngine;
        private final StreamReader streamReader;
        private final StreamWriter streamWriter;

        public SSLHandshakeCondition(StreamReader streamReader2, StreamWriter streamWriter2, SSLEngineConfigurator configurator2, SSLEngine sslEngine2, HandshakeCompletionHandler completionHandler2) {
            this.connection = streamReader2.getConnection();
            this.configurator = configurator2;
            this.sslEngine = sslEngine2;
            this.completionHandler = completionHandler2;
            this.streamReader = streamReader2;
            this.streamWriter = streamWriter2;
        }

        public boolean check() {
            try {
                return doHandshakeStep();
            } catch (IOException e) {
                this.completionHandler.failed(e);
                throw new RuntimeException("Unexpected handshake exception");
            }
        }

        public boolean doHandshakeStep() {
            boolean isLoggingFinest = AbstractStreamWriter.logger.isLoggable(Level.FINEST);
            SSLEngineResult.HandshakeStatus handshakeStatus = this.sslEngine.getHandshakeStatus();
            if (handshakeStatus == SSLEngineResult.HandshakeStatus.FINISHED || handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
                return true;
            }
            do {
                if (isLoggingFinest) {
                    Logger access$100 = AbstractStreamWriter.logger;
                    Level level = Level.FINEST;
                    SSLEngine sSLEngine = this.sslEngine;
                    access$100.log(level, "Loop Engine: {0} handshakeStatus={1}", new Object[]{sSLEngine, sSLEngine.getHandshakeStatus()});
                }
                switch (AnonymousClass1.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[handshakeStatus.ordinal()]) {
                    case 1:
                        if (isLoggingFinest) {
                            AbstractStreamWriter.logger.log(Level.FINEST, "NEED_UNWRAP Engine: {0}", this.sslEngine);
                        }
                        return false;
                    case 2:
                        if (isLoggingFinest) {
                            AbstractStreamWriter.logger.log(Level.FINEST, "NEED_WRAP Engine: {0}", this.sslEngine);
                        }
                        this.streamWriter.writeBuffer(Buffers.EMPTY_BUFFER);
                        this.streamWriter.flush();
                        handshakeStatus = this.sslEngine.getHandshakeStatus();
                        break;
                    case 3:
                        if (isLoggingFinest) {
                            AbstractStreamWriter.logger.log(Level.FINEST, "NEED_TASK Engine: {0}", this.sslEngine);
                        }
                        SSLUtils.executeDelegatedTask(this.sslEngine);
                        handshakeStatus = this.sslEngine.getHandshakeStatus();
                        break;
                    case 4:
                    case 5:
                        return true;
                    default:
                        throw new RuntimeException("Invalid Handshaking State" + handshakeStatus);
                }
            } while (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED);
            return true;
        }
    }

    /* renamed from: org.glassfish.grizzly.ssl.SSLStreamWriter$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;

        static {
            int[] iArr = new int[SSLEngineResult.HandshakeStatus.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = iArr;
            try {
                iArr[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public static final class HandshakeCompletionHandler extends CompletionHandlerAdapter<SSLEngine, Integer> {
        final SSLEngine sslEngine;

        public HandshakeCompletionHandler(FutureImpl<SSLEngine> future, CompletionHandler<SSLEngine> completionHandler, SSLEngine sslEngine2) {
            super(future, completionHandler);
            this.sslEngine = sslEngine2;
        }

        /* access modifiers changed from: protected */
        public SSLEngine adapt(Integer result) {
            return this.sslEngine;
        }
    }
}
