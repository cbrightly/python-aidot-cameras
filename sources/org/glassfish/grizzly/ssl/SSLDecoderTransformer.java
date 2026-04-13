package org.glassfish.grizzly.ssl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public final class SSLDecoderTransformer extends AbstractTransformer<Buffer, Buffer> {
    public static final int BUFFER_OVERFLOW_ERROR = 3;
    public static final int BUFFER_UNDERFLOW_ERROR = 2;
    private static final TransformationResult<Buffer, Buffer> HANDSHAKE_NOT_EXECUTED_RESULT = TransformationResult.createErrorResult(1, "Handshake was not executed");
    private static final Logger LOGGER = Grizzly.logger(SSLDecoderTransformer.class);
    public static final int NEED_HANDSHAKE_ERROR = 1;
    private final MemoryManager memoryManager;

    public SSLDecoderTransformer() {
        this(MemoryManager.DEFAULT_MEMORY_MANAGER);
    }

    public SSLDecoderTransformer(MemoryManager memoryManager2) {
        this.memoryManager = memoryManager2;
    }

    public String getName() {
        return SSLDecoderTransformer.class.getName();
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, Buffer> transformImpl(AttributeStorage state, Buffer originalMessage) {
        SSLEngineResult sslEngineResult;
        Buffer buffer = originalMessage;
        SSLEngine sslEngine = SSLUtils.getSSLEngine((Connection) state);
        if (sslEngine == null) {
            return HANDSHAKE_NOT_EXECUTED_RESULT;
        }
        try {
            int expectedLength = SSLUtils.getSSLPacketSize(originalMessage);
            if (expectedLength != -1) {
                if (originalMessage.remaining() >= expectedLength) {
                    Buffer targetBuffer = this.memoryManager.allocate(sslEngine.getSession().getApplicationBufferSize());
                    try {
                        Logger logger = LOGGER;
                        Level level = Level.FINE;
                        if (logger.isLoggable(level)) {
                            logger.log(level, "SSLDecoder engine: {0} input: {1} output: {2}", new Object[]{sslEngine, buffer, targetBuffer});
                        }
                        int pos = originalMessage.position();
                        if (!originalMessage.isComposite()) {
                            sslEngineResult = SSLUtils.sslEngineUnwrap(sslEngine, originalMessage.toByteBuffer(), targetBuffer.toByteBuffer());
                        } else {
                            sslEngineResult = SSLUtils.sslEngineUnwrap(sslEngine, buffer.toByteBuffer(pos, pos + expectedLength), targetBuffer.toByteBuffer());
                        }
                        buffer.position(sslEngineResult.bytesConsumed() + pos);
                        targetBuffer.position(sslEngineResult.bytesProduced());
                        SSLEngineResult.Status status = sslEngineResult.getStatus();
                        if (logger.isLoggable(level)) {
                            logger.log(level, "SSLDecoderr done engine: {0} result: {1} input: {2} output: {3}", new Object[]{sslEngine, sslEngineResult, buffer, targetBuffer});
                        }
                        if (status == SSLEngineResult.Status.OK) {
                            targetBuffer.trim();
                            return TransformationResult.createCompletedResult(targetBuffer, buffer);
                        } else if (status == SSLEngineResult.Status.CLOSED) {
                            targetBuffer.dispose();
                            return TransformationResult.createCompletedResult(Buffers.EMPTY_BUFFER, buffer);
                        } else {
                            targetBuffer.dispose();
                            if (status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                                return TransformationResult.createIncompletedResult(originalMessage);
                            }
                            if (status == SSLEngineResult.Status.BUFFER_OVERFLOW) {
                                return TransformationResult.createErrorResult(3, "Buffer overflow during unwrap operation");
                            }
                            return null;
                        }
                    } catch (SSLException e) {
                        targetBuffer.dispose();
                        throw new TransformationException((Throwable) e);
                    }
                }
            }
            try {
                return TransformationResult.createIncompletedResult(originalMessage);
            } catch (SSLException e2) {
                e = e2;
                throw new TransformationException((Throwable) e);
            }
        } catch (SSLException e3) {
            e = e3;
            throw new TransformationException((Throwable) e);
        }
    }

    public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
        return input != null && input.hasRemaining();
    }
}
