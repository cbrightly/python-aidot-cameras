package org.glassfish.grizzly.ssl;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.MemoryManager;

public final class SSLConnectionContext {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final float BUFFER_SIZE_COEF;
    private static final Logger LOGGER;
    private volatile int appBufferSize;
    private final Connection connection;
    private final InputBufferWrapper inputBuffer = new InputBufferWrapper();
    final ByteBufferArray inputByteBufferArray = ByteBufferArray.create();
    private boolean isServerMode;
    private InputBufferWrapper lastInputBuffer;
    private Buffer lastOutputBuffer;
    private volatile int netBufferSize;
    private FilterChain newConnectionFilterChain;
    final ByteBufferArray outputByteBufferArray = ByteBufferArray.create();
    private SSLEngine sslEngine;

    public interface Allocator {
        Buffer grow(SSLConnectionContext sSLConnectionContext, Buffer buffer, int i);
    }

    static {
        Class<SSLConnectionContext> cls = SSLConnectionContext.class;
        LOGGER = Grizzly.logger(cls);
        float coeff = 1.5f;
        try {
            coeff = Float.parseFloat(System.getProperty(cls.getName(), "1.5"));
        } catch (NumberFormatException e) {
        }
        BUFFER_SIZE_COEF = coeff;
    }

    public SSLConnectionContext(Connection connection2) {
        this.connection = connection2;
    }

    public SSLEngine getSslEngine() {
        return this.sslEngine;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void attach() {
        SSLUtils.SSL_CTX_ATTR.set((AttributeStorage) this.connection, this);
    }

    public void configure(SSLEngine sslEngine2) {
        this.sslEngine = sslEngine2;
        this.isServerMode = !sslEngine2.getUseClientMode();
        updateBufferSizes();
    }

    public boolean isServerMode() {
        return this.isServerMode;
    }

    /* access modifiers changed from: package-private */
    public void updateBufferSizes() {
        SSLSession session = this.sslEngine.getSession();
        this.appBufferSize = session.getApplicationBufferSize();
        this.netBufferSize = session.getPacketBufferSize();
    }

    public int getAppBufferSize() {
        return this.appBufferSize;
    }

    public int getNetBufferSize() {
        return this.netBufferSize;
    }

    public FilterChain getNewConnectionFilterChain() {
        return this.newConnectionFilterChain;
    }

    public void setNewConnectionFilterChain(FilterChain newConnectionFilterChain2) {
        this.newConnectionFilterChain = newConnectionFilterChain2;
    }

    /* access modifiers changed from: package-private */
    public Buffer resetLastOutputBuffer() {
        Buffer tmp = this.lastOutputBuffer;
        this.lastOutputBuffer = null;
        return tmp;
    }

    /* access modifiers changed from: package-private */
    public void setLastOutputBuffer(Buffer lastOutputBuffer2) {
        this.lastOutputBuffer = lastOutputBuffer2;
    }

    /* access modifiers changed from: package-private */
    public InputBufferWrapper resetLastInputBuffer() {
        InputBufferWrapper tmp = this.lastInputBuffer;
        this.lastInputBuffer = null;
        return tmp;
    }

    /* access modifiers changed from: package-private */
    public InputBufferWrapper useInputBuffer() {
        InputBufferWrapper inputBufferWrapper = this.inputBuffer;
        this.lastInputBuffer = inputBufferWrapper;
        return inputBufferWrapper;
    }

    /* access modifiers changed from: package-private */
    public SslResult unwrap(int len, Buffer input, Buffer output, Allocator allocator) {
        SSLEngineResult sslEngineResult;
        ByteBufferArray bba;
        int i = len;
        Buffer buffer = input;
        Allocator allocator2 = allocator;
        Buffer output2 = ensureBufferSize(output, this.appBufferSize, allocator2);
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "unwrap engine: {0} input: {1} output: {2}", new Object[]{this.sslEngine, buffer, output2});
        }
        int inPos = input.position();
        int outPos = output2.position();
        ByteBuffer inputByteBuffer = buffer.toByteBuffer(input.position(), input.position() + i);
        int initPosition = inputByteBuffer.position();
        try {
            if (!output2.isComposite()) {
                sslEngineResult = SSLUtils.sslEngineUnwrap(this.sslEngine, inputByteBuffer, output2.toByteBuffer());
            } else {
                bba = output2.toByteBufferArray(this.outputByteBufferArray);
                SSLEngineResult sslEngineResult2 = SSLUtils.sslEngineUnwrap(this.sslEngine, inputByteBuffer, (ByteBuffer[]) bba.getArray(), 0, bba.size());
                bba.restore();
                bba.reset();
                sslEngineResult = sslEngineResult2;
            }
            SSLEngineResult.Status status = sslEngineResult.getStatus();
            boolean isOverflow = status == SSLEngineResult.Status.BUFFER_OVERFLOW;
            if (allocator2 != null && isOverflow) {
                updateBufferSizes();
                return unwrap(i, buffer, ensureBufferSize(output2, this.appBufferSize, allocator2), (Allocator) null);
            } else if (isOverflow || status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                return new SslResult(output2, new SSLException("SSL unwrap error: " + status));
            } else {
                buffer.position((inputByteBuffer.position() + inPos) - initPosition);
                output2.position(sslEngineResult.bytesProduced() + outPos);
                if (logger.isLoggable(level)) {
                    logger.log(level, "unwrap done engine: {0} result: {1} input: {2} output: {3}", new Object[]{this.sslEngine, sslEngineResult, buffer, output2});
                }
                return new SslResult(output2, sslEngineResult);
            }
        } catch (SSLException e) {
            return new SslResult(output2, e);
        } catch (Throwable th) {
            bba.restore();
            bba.reset();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public Buffer wrapAll(Buffer input, Allocator allocator) {
        MemoryManager memoryManager = this.connection.getMemoryManager();
        ByteBufferArray bba = input.toByteBufferArray(this.inputByteBufferArray);
        ByteBuffer[] inputArray = (ByteBuffer[]) bba.getArray();
        int inputArraySize = bba.size();
        Buffer output = null;
        SslResult result = null;
        try {
            result = wrap(input, inputArray, inputArraySize, (Buffer) null, allocator);
            if (!result.isError()) {
                Buffer output2 = result.getOutput();
                output2.trim();
                if (input.hasRemaining()) {
                    do {
                        result = wrap(input, inputArray, inputArraySize, (Buffer) null, allocator);
                        if (!result.isError()) {
                            Buffer newOutput = result.getOutput();
                            newOutput.trim();
                            output2 = Buffers.appendBuffers(memoryManager, output2, newOutput);
                        } else {
                            throw result.getError();
                        }
                    } while (input.hasRemaining());
                }
                bba.restore();
                bba.reset();
                if (result.isError()) {
                    if (output2 != null) {
                        output2.dispose();
                    }
                    result.getOutput().dispose();
                }
                return output2;
            }
            throw result.getError();
        } catch (Throwable th) {
            bba.restore();
            bba.reset();
            if (result != null && result.isError()) {
                if (output != null) {
                    output.dispose();
                }
                result.getOutput().dispose();
            }
            throw th;
        }
    }

    private SslResult wrap(Buffer input, ByteBuffer[] inputArray, int inputArraySize, Buffer output, Allocator allocator) {
        SSLEngineResult sslEngineResult;
        Buffer buffer = input;
        Allocator allocator2 = allocator;
        Buffer output2 = ensureBufferSize(output, this.netBufferSize, allocator2);
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "wrap engine: {0} input: {1} output: {2}", new Object[]{this.sslEngine, buffer, output2});
        }
        int inPos = input.position();
        int outPos = output2.position();
        try {
            try {
                SSLEngineResult sslEngineResult2 = SSLUtils.sslEngineWrap(this.sslEngine, inputArray, 0, inputArraySize, output2.toByteBuffer());
                SSLEngineResult.Status status = sslEngineResult2.getStatus();
                if (status == SSLEngineResult.Status.CLOSED) {
                    return new SslResult(output2, new SSLException("SSLEngine is CLOSED"));
                }
                boolean isOverflow = status == SSLEngineResult.Status.BUFFER_OVERFLOW;
                if (allocator2 == null || !isOverflow) {
                    SSLEngineResult sslEngineResult3 = sslEngineResult2;
                    if (isOverflow) {
                    } else if (status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                        SSLEngineResult sSLEngineResult = sslEngineResult3;
                    } else {
                        buffer.position(sslEngineResult3.bytesConsumed() + inPos);
                        output2.position(sslEngineResult3.bytesProduced() + outPos);
                        this.lastOutputBuffer = output2;
                        if (logger.isLoggable(level)) {
                            sslEngineResult = sslEngineResult3;
                            logger.log(level, "wrap done engine: {0} result: {1} input: {2} output: {3}", new Object[]{this.sslEngine, sslEngineResult, buffer, output2});
                        } else {
                            sslEngineResult = sslEngineResult3;
                        }
                        return new SslResult(output2, sslEngineResult);
                    }
                    return new SslResult(output2, new SSLException("SSL wrap error: " + status));
                }
                updateBufferSizes();
                SSLEngineResult sSLEngineResult2 = sslEngineResult2;
                return wrap(input, inputArray, inputArraySize, ensureBufferSize(output2, this.netBufferSize, allocator2), (Allocator) null);
            } catch (SSLException e) {
                e = e;
                return new SslResult(output2, e);
            }
        } catch (SSLException e2) {
            e = e2;
            ByteBuffer[] byteBufferArr = inputArray;
            int i = inputArraySize;
            return new SslResult(output2, e);
        }
    }

    /* access modifiers changed from: package-private */
    public SslResult wrap(Buffer input, Buffer output, Allocator allocator) {
        SSLEngineResult sslEngineResult;
        ByteBufferArray bba;
        Buffer buffer = input;
        Allocator allocator2 = allocator;
        Buffer output2 = ensureBufferSize(output, this.netBufferSize, allocator2);
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "wrap engine: {0} input: {1} output: {2}", new Object[]{this.sslEngine, buffer, output2});
        }
        int inPos = input.position();
        int outPos = output2.position();
        ByteBuffer outputByteBuffer = output2.toByteBuffer();
        try {
            if (!input.isComposite()) {
                sslEngineResult = SSLUtils.sslEngineWrap(this.sslEngine, input.toByteBuffer(), outputByteBuffer);
            } else {
                bba = buffer.toByteBufferArray(this.inputByteBufferArray);
                SSLEngineResult sslEngineResult2 = SSLUtils.sslEngineWrap(this.sslEngine, (ByteBuffer[]) bba.getArray(), 0, bba.size(), outputByteBuffer);
                bba.restore();
                bba.reset();
                sslEngineResult = sslEngineResult2;
            }
            SSLEngineResult.Status status = sslEngineResult.getStatus();
            boolean isOverflow = status == SSLEngineResult.Status.BUFFER_OVERFLOW;
            if (allocator2 != null && isOverflow) {
                updateBufferSizes();
                return wrap(buffer, ensureBufferSize(output2, this.netBufferSize, allocator2), (Allocator) null);
            } else if (isOverflow || status == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                return new SslResult(output2, new SSLException("SSL wrap error: " + status));
            } else {
                buffer.position(sslEngineResult.bytesConsumed() + inPos);
                output2.position(sslEngineResult.bytesProduced() + outPos);
                this.lastOutputBuffer = output2;
                if (logger.isLoggable(level)) {
                    logger.log(level, "wrap done engine: {0} result: {1} input: {2} output: {3}", new Object[]{this.sslEngine, sslEngineResult, buffer, output2});
                }
                return new SslResult(output2, sslEngineResult);
            }
        } catch (SSLException e) {
            return new SslResult(output2, e);
        } catch (Throwable th) {
            bba.restore();
            bba.reset();
            throw th;
        }
    }

    private Buffer ensureBufferSize(Buffer output, int size, Allocator allocator) {
        int sz = (int) (((float) size) * BUFFER_SIZE_COEF);
        if (output == null) {
            if (allocator != null) {
                return allocator.grow(this, (Buffer) null, sz);
            }
            throw new AssertionError();
        } else if (output.remaining() >= sz) {
            return output;
        } else {
            if (allocator != null) {
                return allocator.grow(this, output, (output.capacity() + sz) - output.remaining());
            }
            throw new AssertionError();
        }
    }

    public static final class SslResult {
        private final SSLException error;
        private final Buffer output;
        private final SSLEngineResult sslEngineResult;

        public SslResult(Buffer output2, SSLEngineResult sslEngineResult2) {
            this.output = output2;
            this.sslEngineResult = sslEngineResult2;
            this.error = null;
        }

        public SslResult(Buffer output2, SSLException error2) {
            this.output = output2;
            this.error = error2;
            this.sslEngineResult = null;
        }

        public Buffer getOutput() {
            return this.output;
        }

        public boolean isError() {
            return this.error != null;
        }

        public SSLException getError() {
            return this.error;
        }

        public SSLEngineResult getSslEngineResult() {
            return this.sslEngineResult;
        }
    }
}
