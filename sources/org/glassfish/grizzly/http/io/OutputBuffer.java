package org.glassfish.grizzly.http.io;

import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpServerFilter;
import org.glassfish.grizzly.http.HttpTrailer;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.MimeType;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.threadpool.Threads;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Futures;

public class OutputBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_BUFFER_SIZE;
    private static final boolean IS_BLOCKING;
    protected static final Logger LOGGER;
    private static final int MAX_CHAR_BUFFER_SIZE;
    private static final int MIN_BUFFER_SIZE = 512;
    private InternalWriteHandler asyncWriteHandler;
    private int bufferSize = DEFAULT_BUFFER_SIZE;
    private HttpContent.Builder builder;
    private char[] charsArray;
    private int charsArrayLength;
    private CharBuffer charsBuffer;
    private final ByteArrayCloner cloner;
    private boolean closed;
    private boolean committed;
    private CompositeBuffer compositeBuffer;
    private Connection connection;
    private FilterChainContext ctx;
    private Buffer currentBuffer;
    private CharsetEncoder encoder;
    private final Map<String, CharsetEncoder> encoders = new HashMap();
    private boolean fileTransferRequested;
    private boolean finished;
    /* access modifiers changed from: private */
    public WriteHandler handler;
    private boolean headersWritten;
    /* access modifiers changed from: private */
    public HttpContext httpContext;
    private boolean isLastWriteNonBlocking;
    private boolean isNonBlockingWriteGuaranteed;
    private final List<LifeCycleListener> lifeCycleListeners = new ArrayList(2);
    private MemoryManager memoryManager;
    private HttpHeader outputHeader;
    protected boolean sendfileEnabled;
    private final TemporaryHeapBuffer temporaryWriteBuffer;
    private Supplier<Map<String, String>> trailersSupplier;
    private Runnable writePossibleRunnable;

    public interface LifeCycleListener {
        void onCommit();
    }

    static {
        Class<OutputBuffer> cls = OutputBuffer.class;
        LOGGER = Grizzly.logger(cls);
        DEFAULT_BUFFER_SIZE = Integer.getInteger(cls.getName() + ".default-buffer-size", 8192).intValue();
        MAX_CHAR_BUFFER_SIZE = Integer.getInteger(cls.getName() + ".max-char-buffer-size", AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_RESP).intValue();
        IS_BLOCKING = Boolean.getBoolean(cls.getName() + ".isBlocking");
    }

    public OutputBuffer() {
        TemporaryHeapBuffer temporaryHeapBuffer = new TemporaryHeapBuffer();
        this.temporaryWriteBuffer = temporaryHeapBuffer;
        this.cloner = new ByteArrayCloner(temporaryHeapBuffer);
    }

    public void initialize(HttpHeader outputHeader2, boolean sendfileEnabled2, FilterChainContext ctx2) {
        this.outputHeader = outputHeader2;
        HttpContent.Builder builder2 = this.builder;
        if (builder2 == null) {
            this.builder = outputHeader2.httpContentBuilder();
        } else {
            builder2.httpHeader(outputHeader2);
        }
        this.sendfileEnabled = sendfileEnabled2;
        this.ctx = ctx2;
        this.httpContext = outputHeader2.getProcessingState().getHttpContext();
        this.connection = ctx2.getConnection();
        this.memoryManager = ctx2.getMemoryManager();
    }

    @Deprecated
    public boolean isAsyncEnabled() {
        return true;
    }

    @Deprecated
    public void setAsyncEnabled(boolean asyncEnabled) {
    }

    public void prepareCharacterEncoder() {
        getEncoder();
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void registerLifeCycleListener(LifeCycleListener listener) {
        this.lifeCycleListeners.add(listener);
    }

    public boolean removeLifeCycleListener(LifeCycleListener listener) {
        return this.lifeCycleListeners.remove(listener);
    }

    public void setBufferSize(int bufferSize2) {
        if (!this.committed) {
            int bufferSizeLocal = Math.max(bufferSize2, 512);
            if (this.currentBuffer == null) {
                this.bufferSize = bufferSizeLocal;
            }
            char[] cArr = this.charsArray;
            if (cArr != null && cArr.length < bufferSizeLocal) {
                char[] newCharsArray = new char[bufferSizeLocal];
                System.arraycopy(cArr, 0, newCharsArray, 0, this.charsArrayLength);
                this.charsBuffer = CharBuffer.wrap(newCharsArray);
                this.charsArray = newCharsArray;
            }
        }
    }

    public void reset() {
        if (!this.committed) {
            this.compositeBuffer = null;
            Buffer buffer = this.currentBuffer;
            if (buffer != null) {
                buffer.clear();
            }
            this.charsArrayLength = 0;
            this.encoder = null;
            return;
        }
        throw new IllegalStateException("Cannot reset the response as it has already been committed.");
    }

    public boolean isClosed() {
        return this.closed;
    }

    public int getBufferedDataSize() {
        int size = 0;
        CompositeBuffer compositeBuffer2 = this.compositeBuffer;
        if (compositeBuffer2 != null) {
            size = 0 + compositeBuffer2.remaining();
        }
        Buffer buffer = this.currentBuffer;
        if (buffer != null) {
            size += buffer.position();
        }
        return size + (this.charsArrayLength << 1);
    }

    public void recycle() {
        this.outputHeader = null;
        this.builder.reset();
        CompositeBuffer compositeBuffer2 = this.compositeBuffer;
        if (compositeBuffer2 != null) {
            compositeBuffer2.dispose();
            this.compositeBuffer = null;
        }
        Buffer buffer = this.currentBuffer;
        if (buffer != null) {
            buffer.dispose();
            this.currentBuffer = null;
        }
        this.temporaryWriteBuffer.recycle();
        char[] cArr = this.charsArray;
        if (cArr != null) {
            this.charsArrayLength = 0;
            if (cArr.length < MAX_CHAR_BUFFER_SIZE) {
                this.charsBuffer.clear();
            } else {
                this.charsBuffer = null;
                this.charsArray = null;
            }
        }
        this.bufferSize = DEFAULT_BUFFER_SIZE;
        this.fileTransferRequested = false;
        this.encoder = null;
        this.ctx = null;
        this.httpContext = null;
        this.connection = null;
        this.memoryManager = null;
        this.handler = null;
        this.isNonBlockingWriteGuaranteed = false;
        this.isLastWriteNonBlocking = false;
        this.asyncWriteHandler = null;
        this.trailersSupplier = null;
        this.committed = false;
        this.finished = false;
        this.closed = false;
        this.headersWritten = false;
        this.lifeCycleListeners.clear();
    }

    public void endRequest() {
        if (!this.finished) {
            InternalWriteHandler asyncWriteQueueHandlerLocal = this.asyncWriteHandler;
            if (asyncWriteQueueHandlerLocal != null) {
                this.asyncWriteHandler = null;
                asyncWriteQueueHandlerLocal.detach();
            }
            if (!this.closed) {
                try {
                    close();
                } catch (IOException e) {
                }
            }
            FilterChainContext filterChainContext = this.ctx;
            if (filterChainContext != null) {
                filterChainContext.notifyDownstream(HttpServerFilter.RESPONSE_COMPLETE_EVENT);
            }
            this.finished = true;
        }
    }

    public void acknowledge() {
        this.ctx.write((Object) this.outputHeader, IS_BLOCKING);
    }

    public void writeChar(int c) {
        this.connection.assertOpen();
        if (!this.closed) {
            updateNonBlockingStatus();
            checkCharBuffer();
            if (this.charsArrayLength == this.charsArray.length) {
                flushCharsToBuf(true);
            }
            char[] cArr = this.charsArray;
            int i = this.charsArrayLength;
            this.charsArrayLength = i + 1;
            cArr[i] = (char) c;
        }
    }

    public void write(char[] cbuf, int off, int len) {
        this.connection.assertOpen();
        if (!this.closed && len != 0) {
            updateNonBlockingStatus();
            if (writingBytes()) {
                flushBinaryBuffers(false);
            }
            checkCharBuffer();
            char[] cArr = this.charsArray;
            int length = cArr.length;
            int i = this.charsArrayLength;
            int remaining = length - i;
            if (len <= remaining) {
                System.arraycopy(cbuf, off, cArr, i, len);
                this.charsArrayLength += len;
            } else if (len - remaining < remaining) {
                System.arraycopy(cbuf, off, cArr, i, remaining);
                this.charsArrayLength += remaining;
                flushCharsToBuf(true);
                System.arraycopy(cbuf, off + remaining, this.charsArray, 0, len - remaining);
                this.charsArrayLength = len - remaining;
            } else {
                flushCharsToBuf(false);
                flushCharsToBuf(CharBuffer.wrap(cbuf, off, len), true);
            }
        }
    }

    public void write(char[] cbuf) {
        write(cbuf, 0, cbuf.length);
    }

    public void write(String str) {
        write(str, 0, str.length());
    }

    public void write(String str, int off, int len) {
        this.connection.assertOpen();
        if (!this.closed && len != 0) {
            updateNonBlockingStatus();
            if (writingBytes()) {
                flushBinaryBuffers(false);
            }
            checkCharBuffer();
            char[] cArr = this.charsArray;
            int length = cArr.length;
            int i = this.charsArrayLength;
            if (length - i >= len) {
                str.getChars(off, off + len, cArr, i);
                this.charsArrayLength += len;
                return;
            }
            int offLocal = off;
            int lenLocal = len;
            do {
                int workingLen = Math.min(lenLocal, this.charsArray.length - this.charsArrayLength);
                str.getChars(offLocal, offLocal + workingLen, this.charsArray, this.charsArrayLength);
                this.charsArrayLength += workingLen;
                offLocal += workingLen;
                lenLocal -= workingLen;
                if (lenLocal > 0) {
                    flushCharsToBuf(false);
                    continue;
                }
            } while (lenLocal > 0);
            flushBinaryBuffersIfNeeded();
        }
    }

    public void writeByte(int b) {
        this.connection.assertOpen();
        if (!this.closed) {
            updateNonBlockingStatus();
            if (writingChars()) {
                flushCharsToBuf(false);
            }
            checkCurrentBuffer();
            if (!this.currentBuffer.hasRemaining()) {
                if (canWritePayloadChunk()) {
                    doCommit();
                    flushBinaryBuffers(false);
                    checkCurrentBuffer();
                    blockAfterWriteIfNeeded();
                } else {
                    finishCurrentBuffer();
                    checkCurrentBuffer();
                }
            }
            this.currentBuffer.put((byte) b);
        }
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void sendfile(File file, CompletionHandler<WriteResult> handler2) {
        if (file != null) {
            sendfile(file, 0, file.length(), handler2);
            return;
        }
        throw new IllegalArgumentException("Argument 'file' cannot be null");
    }

    public void sendfile(File file, long offset, long length, CompletionHandler<WriteResult> handler2) {
        if (!this.sendfileEnabled) {
            throw new IllegalStateException("sendfile support isn't available.");
        } else if (!this.fileTransferRequested) {
            reset();
            FileTransfer fileTransfer = new FileTransfer(file, offset, length);
            this.fileTransferRequested = true;
            this.outputHeader.setContentLengthLong((long) fileTransfer.remaining());
            if (this.outputHeader.getContentType() == null) {
                this.outputHeader.setContentType(MimeType.getByFilename(file.getName()));
            }
            this.outputHeader.setHeader(Header.ContentEncoding, HeaderValue.IDENTITY);
            try {
                flush();
                this.ctx.write((Object) fileTransfer, handler2);
            } catch (IOException e) {
                if (handler2 != null) {
                    handler2.failed(e);
                    return;
                }
                Logger logger = LOGGER;
                Level level = Level.SEVERE;
                if (logger.isLoggable(level)) {
                    logger.log(level, String.format("Failed to transfer file %s.  Cause: %s.", new Object[]{file.getAbsolutePath(), e.getMessage()}), e);
                }
            }
        } else {
            throw new IllegalStateException("Only one file transfer allowed per request");
        }
    }

    public void write(byte[] b, int off, int len) {
        Buffer buffer;
        this.connection.assertOpen();
        if (!this.closed && len != 0) {
            updateNonBlockingStatus();
            if (writingChars()) {
                flushCharsToBuf(false);
            }
            if (this.bufferSize >= len && ((buffer = this.currentBuffer) == null || buffer.remaining() >= len)) {
                checkCurrentBuffer();
                Buffer buffer2 = this.currentBuffer;
                if (buffer2 != null) {
                    buffer2.put(b, off, len);
                    return;
                }
                throw new AssertionError();
            } else if (canWritePayloadChunk()) {
                this.temporaryWriteBuffer.reset(b, off, len);
                finishCurrentBuffer();
                doCommit();
                CompositeBuffer compositeBuffer2 = this.compositeBuffer;
                if (compositeBuffer2 != null) {
                    compositeBuffer2.append(this.temporaryWriteBuffer);
                    flushBuffer(this.compositeBuffer, false, this.cloner);
                    this.compositeBuffer = null;
                } else {
                    flushBuffer(this.temporaryWriteBuffer, false, this.cloner);
                }
                blockAfterWriteIfNeeded();
            } else {
                finishCurrentBuffer();
                Buffer cloneBuffer = this.memoryManager.allocate(len);
                cloneBuffer.put(b, off, len);
                cloneBuffer.flip();
                checkCompositeBuffer();
                this.compositeBuffer.append(cloneBuffer);
            }
        }
    }

    public void close() {
        if (!this.closed) {
            this.closed = true;
            this.connection.assertOpen();
            boolean isJustCommitted = doCommit();
            if (!flushAllBuffers(true) && (isJustCommitted || this.outputHeader.isChunked())) {
                forceCommitHeaders(true);
            }
            blockAfterWriteIfNeeded();
        }
    }

    public void flush() {
        this.connection.assertOpen();
        boolean isJustCommitted = doCommit();
        if (!flushAllBuffers(false) && isJustCommitted) {
            forceCommitHeaders(false);
        }
        blockAfterWriteIfNeeded();
    }

    public void writeByteBuffer(ByteBuffer byteBuffer) {
        Buffer w = Buffers.wrap(this.memoryManager, byteBuffer);
        w.allowBufferDispose(false);
        writeBuffer(w);
    }

    public void writeBuffer(Buffer buffer) {
        this.connection.assertOpen();
        updateNonBlockingStatus();
        finishCurrentBuffer();
        checkCompositeBuffer();
        this.compositeBuffer.append(buffer);
        if (canWritePayloadChunk() && this.compositeBuffer.remaining() > this.bufferSize) {
            flush();
        }
    }

    @Deprecated
    public boolean canWriteChar(int length) {
        return canWrite();
    }

    @Deprecated
    public boolean canWrite(int length) {
        return canWrite();
    }

    public boolean canWrite() {
        if (IS_BLOCKING || this.isNonBlockingWriteGuaranteed) {
            return true;
        }
        if (!this.httpContext.getOutputSink().canWrite()) {
            return false;
        }
        this.isNonBlockingWriteGuaranteed = true;
        return true;
    }

    @Deprecated
    public void notifyCanWrite(WriteHandler handler2, int length) {
        notifyCanWrite(handler2);
    }

    public void notifyCanWrite(WriteHandler handler2) {
        if (this.handler != null) {
            throw new IllegalStateException("Illegal attempt to set a new handler before the existing handler has been notified.");
        } else if (handler2 == null || this.httpContext.getCloseable().isOpen()) {
            this.handler = handler2;
            if (this.isNonBlockingWriteGuaranteed || canWrite()) {
                if (Writer.Reentrant.getWriteReentrant().isMaxReentrantsReached()) {
                    notifyWritePossibleAsync();
                } else {
                    notifyWritePossible();
                }
            } else if (!IS_BLOCKING) {
                if (this.asyncWriteHandler == null) {
                    this.asyncWriteHandler = new InternalWriteHandler(this);
                }
                try {
                    this.httpContext.getOutputSink().notifyCanWrite(this.asyncWriteHandler);
                } catch (Exception ignored) {
                    LOGGER.log(Level.FINE, "Ignoring exception.", ignored);
                }
            } else {
                throw new AssertionError();
            }
        } else {
            CloseReason closeReason = this.connection.getCloseReason();
            if (closeReason == null) {
                Logger logger = LOGGER;
                Level level = Level.WARNING;
                CloseReason closeReason2 = CloseReason.LOCALLY_CLOSED_REASON;
                logger.log(level, "No close reason set, using default: {0}", closeReason2);
                handler2.onError(closeReason2.getCause());
                return;
            }
            handler2.onError(closeReason.getCause());
        }
    }

    public void setTrailers(Supplier<Map<String, String>> trailersSupplier2) {
        this.trailersSupplier = trailersSupplier2;
    }

    public Supplier<Map<String, String>> getTrailers() {
        return this.trailersSupplier;
    }

    /* access modifiers changed from: protected */
    public Executor getThreadPool() {
        ExecutorService es;
        if (Threads.isService() && (es = this.connection.getTransport().getWorkerThreadPool()) != null && !es.isShutdown()) {
            return es;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void notifyWritePossibleAsync() {
        if (this.writePossibleRunnable == null) {
            this.writePossibleRunnable = new Runnable() {
                public void run() {
                    OutputBuffer.this.notifyWritePossible();
                }
            };
        }
        this.connection.executeInEventThread(IOEvent.WRITE, this.writePossibleRunnable);
    }

    /* access modifiers changed from: private */
    public void notifyWritePossible() {
        WriteHandler localHandler = this.handler;
        if (localHandler != null) {
            Writer.Reentrant reentrant = Writer.Reentrant.getWriteReentrant();
            try {
                this.handler = null;
                reentrant.inc();
                this.isNonBlockingWriteGuaranteed = true;
                localHandler.onWritePossible();
            } catch (Throwable th) {
                reentrant.dec();
                throw th;
            }
            reentrant.dec();
        }
    }

    private boolean canWritePayloadChunk() {
        return this.outputHeader.isChunkingAllowed() || this.outputHeader.getContentLength() != -1;
    }

    private void blockAfterWriteIfNeeded() {
        if (!IS_BLOCKING && !this.isNonBlockingWriteGuaranteed && !this.isLastWriteNonBlocking && !this.httpContext.getOutputSink().canWrite()) {
            final FutureImpl<Boolean> future = Futures.createSafeFuture();
            this.httpContext.getOutputSink().notifyCanWrite(new WriteHandler() {
                public void onWritePossible() {
                    future.result(Boolean.TRUE);
                }

                public void onError(Throwable t) {
                    future.failure(Exceptions.makeIOException(t));
                }
            });
            try {
                Connection connection2 = this.connection;
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                long writeTimeout = connection2.getWriteTimeout(timeUnit);
                if (writeTimeout >= 0) {
                    future.get(writeTimeout, timeUnit);
                } else {
                    future.get();
                }
            } catch (ExecutionException e) {
                this.httpContext.close();
                throw Exceptions.makeIOException(e.getCause());
            } catch (TimeoutException e2) {
                this.httpContext.close();
                throw new IOException("Write timeout exceeded when trying to flush the data");
            } catch (Exception e3) {
                this.httpContext.close();
                throw Exceptions.makeIOException(e3);
            }
        }
    }

    private boolean flushAllBuffers(boolean isLast) {
        if (this.charsArrayLength > 0) {
            flushCharsToBuf(false);
        }
        return flushBinaryBuffers(isLast);
    }

    private boolean flushBinaryBuffers(boolean isLast) {
        Buffer bufferToFlush;
        if (!this.outputHeader.isChunkingAllowed() && this.outputHeader.getContentLength() == -1) {
            if (!isLast) {
                return false;
            }
            this.outputHeader.setContentLength(getBufferedDataSize());
        }
        CompositeBuffer compositeBuffer2 = this.compositeBuffer;
        if (compositeBuffer2 != null && compositeBuffer2.hasRemaining()) {
            finishCurrentBuffer();
            bufferToFlush = this.compositeBuffer;
            this.compositeBuffer = null;
        } else {
            Buffer bufferToFlush2 = this.currentBuffer;
            if (bufferToFlush2 == null || bufferToFlush2.position() <= 0) {
                bufferToFlush = null;
            } else {
                this.currentBuffer.trim();
                bufferToFlush = this.currentBuffer;
                this.currentBuffer = null;
            }
        }
        if (bufferToFlush == null && !isLast) {
            return false;
        }
        doCommit();
        flushBuffer(bufferToFlush, isLast, (MessageCloner<Buffer>) null);
        return true;
    }

    private void flushBuffer(Buffer bufferToFlush, boolean isLast, MessageCloner<Buffer> messageCloner) {
        HttpContent content;
        if (!isLast || this.trailersSupplier == null || (!this.outputHeader.isChunked() && !this.outputHeader.getProtocol().equals(Protocol.HTTP_2_0))) {
            content = this.builder.content(bufferToFlush).last(isLast).build();
        } else {
            forceCommitHeaders(false);
            HttpTrailer.Builder tBuilder = (HttpTrailer.Builder) ((HttpTrailer.Builder) this.outputHeader.httpTrailerBuilder().content(bufferToFlush)).last(true);
            Map<String, String> trailers = this.trailersSupplier.get();
            if (trailers != null && !trailers.isEmpty()) {
                for (Map.Entry<String, String> entry : trailers.entrySet()) {
                    tBuilder.header(entry.getKey(), entry.getValue());
                }
            }
            content = tBuilder.build();
        }
        this.ctx.write((Object) null, (Object) content, (CompletionHandler<WriteResult>) null, (MessageCloner) messageCloner, IS_BLOCKING);
    }

    private void checkCharBuffer() {
        if (this.charsArray == null) {
            char[] cArr = new char[this.bufferSize];
            this.charsArray = cArr;
            this.charsBuffer = CharBuffer.wrap(cArr);
        }
    }

    private boolean writingChars() {
        return this.charsArray != null && this.charsArrayLength > 0;
    }

    private boolean writingBytes() {
        Buffer buffer = this.currentBuffer;
        return (buffer == null || buffer.position() == 0) ? false : true;
    }

    private void checkCurrentBuffer() {
        if (this.currentBuffer == null) {
            Buffer allocate = this.memoryManager.allocate(this.bufferSize);
            this.currentBuffer = allocate;
            allocate.allowBufferDispose(true);
        }
    }

    private void finishCurrentBuffer() {
        Buffer buffer = this.currentBuffer;
        if (buffer != null && buffer.position() > 0) {
            this.currentBuffer.trim();
            checkCompositeBuffer();
            this.compositeBuffer.append(this.currentBuffer);
            this.currentBuffer = null;
        }
    }

    private CharsetEncoder getEncoder() {
        if (this.encoder == null) {
            String encoding = this.outputHeader.getCharacterEncoding();
            if (encoding == null) {
                encoding = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
            }
            CharsetEncoder charsetEncoder = this.encoders.get(encoding);
            this.encoder = charsetEncoder;
            if (charsetEncoder == null) {
                CharsetEncoder newEncoder = Charsets.lookupCharset(encoding).newEncoder();
                this.encoder = newEncoder;
                newEncoder.onMalformedInput(CodingErrorAction.REPLACE);
                this.encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
                this.encoders.put(encoding, this.encoder);
            } else {
                charsetEncoder.reset();
            }
        }
        return this.encoder;
    }

    private boolean doCommit() {
        if (this.committed) {
            return false;
        }
        notifyCommit();
        this.committed = true;
        this.outputHeader.getHeaders().mark();
        return true;
    }

    private void forceCommitHeaders(boolean isLast) {
        if (!this.headersWritten) {
            if (!isLast) {
                this.ctx.write((Object) this.outputHeader, IS_BLOCKING);
            } else if (this.outputHeader != null) {
                this.builder.last(true).content((Buffer) null);
                this.ctx.write((Object) this.builder.build(), IS_BLOCKING);
            }
        }
        this.headersWritten = true;
    }

    private void checkCompositeBuffer() {
        if (this.compositeBuffer == null) {
            CompositeBuffer buffer = CompositeBuffer.newBuffer(this.memoryManager);
            buffer.allowBufferDispose(true);
            buffer.allowInternalBuffersDispose(true);
            this.compositeBuffer = buffer;
        }
    }

    private void flushCharsToBuf(boolean canFlushToNet) {
        this.charsBuffer.limit(this.charsArrayLength);
        try {
            flushCharsToBuf(this.charsBuffer, canFlushToNet);
        } finally {
            this.charsArrayLength = 0;
            this.charsBuffer.clear();
        }
    }

    private void flushCharsToBuf(CharBuffer charBuf, boolean canFlushToNet) {
        if (charBuf.hasRemaining()) {
            CharsetEncoder enc = getEncoder();
            checkCurrentBuffer();
            if (!this.currentBuffer.hasRemaining()) {
                finishCurrentBuffer();
                checkCurrentBuffer();
            }
            ByteBuffer currentByteBuffer = this.currentBuffer.toByteBuffer();
            int bufferPos = this.currentBuffer.position();
            int byteBufferPos = currentByteBuffer.position();
            CoderResult res = enc.encode(charBuf, currentByteBuffer, true);
            this.currentBuffer.position((currentByteBuffer.position() + bufferPos) - byteBufferPos);
            while (res == CoderResult.OVERFLOW) {
                checkCurrentBuffer();
                ByteBuffer currentByteBuffer2 = this.currentBuffer.toByteBuffer();
                int bufferPos2 = this.currentBuffer.position();
                int byteBufferPos2 = currentByteBuffer2.position();
                res = enc.encode(charBuf, currentByteBuffer2, true);
                this.currentBuffer.position((currentByteBuffer2.position() + bufferPos2) - byteBufferPos2);
                if (res == CoderResult.OVERFLOW) {
                    finishCurrentBuffer();
                }
            }
            if (res != CoderResult.UNDERFLOW) {
                throw new IOException("Encoding error");
            } else if (canFlushToNet) {
                flushBinaryBuffersIfNeeded();
            }
        }
    }

    private void flushBinaryBuffersIfNeeded() {
        if (this.compositeBuffer != null) {
            flushBinaryBuffers(false);
            blockAfterWriteIfNeeded();
        }
    }

    private void notifyCommit() {
        List<LifeCycleListener> list = this.lifeCycleListeners;
        for (LifeCycleListener lifeCycleListener : (LifeCycleListener[]) list.toArray(new LifeCycleListener[list.size()])) {
            lifeCycleListener.onCommit();
        }
    }

    private void updateNonBlockingStatus() {
        this.isLastWriteNonBlocking = this.isNonBlockingWriteGuaranteed;
        this.isNonBlockingWriteGuaranteed = false;
    }

    public static final class ByteArrayCloner implements MessageCloner<Buffer> {
        private final TemporaryHeapBuffer temporaryWriteBuffer;

        public ByteArrayCloner(TemporaryHeapBuffer temporaryWriteBuffer2) {
            this.temporaryWriteBuffer = temporaryWriteBuffer2;
        }

        public Buffer clone(Connection connection, Buffer originalMessage) {
            if (this.temporaryWriteBuffer.isDisposed()) {
                return originalMessage;
            }
            return clone0(connection.getMemoryManager(), originalMessage);
        }

        /* access modifiers changed from: package-private */
        public Buffer clone0(MemoryManager memoryManager, Buffer originalMessage) {
            if (!originalMessage.isComposite()) {
                return this.temporaryWriteBuffer.cloneContent(memoryManager);
            }
            CompositeBuffer compositeBuffer = (CompositeBuffer) originalMessage;
            compositeBuffer.shrink();
            if (!this.temporaryWriteBuffer.isDisposed()) {
                if (compositeBuffer.remaining() == this.temporaryWriteBuffer.remaining()) {
                    compositeBuffer.allowInternalBuffersDispose(false);
                    compositeBuffer.tryDispose();
                    return this.temporaryWriteBuffer.cloneContent(memoryManager);
                }
                TemporaryHeapBuffer temporaryHeapBuffer = this.temporaryWriteBuffer;
                compositeBuffer.replace(temporaryHeapBuffer, temporaryHeapBuffer.cloneContent(memoryManager));
            }
            return originalMessage;
        }
    }

    public static class InternalWriteHandler implements WriteHandler {
        private volatile OutputBuffer outputBuffer;

        public InternalWriteHandler(OutputBuffer outputBuffer2) {
            this.outputBuffer = outputBuffer2;
        }

        public void detach() {
            OutputBuffer obLocal = this.outputBuffer;
            if (obLocal != null) {
                this.outputBuffer = null;
                onError0(obLocal, obLocal.httpContext.getCloseable().isOpen() ? new CancellationException() : new EOFException());
            }
        }

        public void onWritePossible() {
            final OutputBuffer localOB = this.outputBuffer;
            if (localOB != null) {
                Executor executor = localOB.getThreadPool();
                if (executor != null) {
                    executor.execute(new Runnable() {
                        public void run() {
                            try {
                                InternalWriteHandler.onWritePossible0(localOB);
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    onWritePossible0(localOB);
                }
            }
        }

        public void onError(final Throwable t) {
            final OutputBuffer localOB = this.outputBuffer;
            if (localOB != null) {
                Executor executor = localOB.getThreadPool();
                if (executor != null) {
                    executor.execute(new Runnable() {
                        public void run() {
                            InternalWriteHandler.onError0(localOB, t);
                        }
                    });
                } else {
                    onError0(localOB, t);
                }
            }
        }

        /* access modifiers changed from: private */
        public static void onWritePossible0(OutputBuffer ob) {
            try {
                if (!Writer.Reentrant.getWriteReentrant().isMaxReentrantsReached()) {
                    ob.notifyWritePossible();
                } else {
                    ob.notifyWritePossibleAsync();
                }
            } catch (Exception e) {
            }
        }

        /* access modifiers changed from: private */
        public static void onError0(OutputBuffer ob, Throwable t) {
            WriteHandler localHandler = ob.handler;
            if (localHandler != null) {
                try {
                    WriteHandler unused = ob.handler = null;
                    localHandler.onError(t);
                } catch (Exception e) {
                }
            }
        }
    }
}
