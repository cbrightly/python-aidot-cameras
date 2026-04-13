package org.glassfish.grizzly.http.io;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpBrokenContent;
import org.glassfish.grizzly.http.HttpBrokenContentException;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpTrailer;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.threadpool.Threads;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.grizzly.utils.Exceptions;

public class InputBuffer {
    private static final Logger LOGGER = Grizzly.logger(InputBuffer.class);
    private static final Level LOGGER_LEVEL = Level.FINER;
    private float averageCharsPerByte = 1.0f;
    private boolean closed;
    private Connection connection;
    private boolean contentRead;
    private FilterChainContext ctx;
    private CharsetDecoder decoder;
    private final Map<String, CharsetDecoder> decoders = new HashMap();
    private String encoding = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
    private ReadHandler handler;
    private HttpHeader httpHeader;
    private Buffer inputContentBuffer;
    private boolean isWaitingDataAsynchronously;
    private int markPos = -1;
    private boolean processingChars;
    private int readAheadLimit = -1;
    private int readCount = 0;
    private int requestedSize;
    private final CharBuffer singleCharBuf = ((CharBuffer) CharBuffer.allocate(1).position(1));
    protected Map<String, String> trailers;

    public void initialize(HttpHeader httpHeader2, FilterChainContext ctx2) {
        if (ctx2 != null) {
            this.httpHeader = httpHeader2;
            this.ctx = ctx2;
            this.connection = ctx2.getConnection();
            Object message = ctx2.getMessage();
            if (message instanceof HttpContent) {
                HttpContent content = (HttpContent) message;
                checkHttpTrailer(content);
                updateInputContentBuffer(content.getContent());
                boolean isLast = content.isLast();
                this.contentRead = isLast;
                if (isLast) {
                    processTrailers();
                }
                content.recycle();
                if (LOGGER.isLoggable(LOGGER_LEVEL)) {
                    log("InputBuffer %s initialize with ready content: %s", this, this.inputContentBuffer);
                    return;
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("ctx cannot be null.");
    }

    public void setDefaultEncoding(String encoding2) {
        this.encoding = encoding2;
    }

    public void recycle() {
        this.inputContentBuffer.tryDispose();
        this.inputContentBuffer = null;
        CharBuffer charBuffer = this.singleCharBuf;
        charBuffer.position(charBuffer.limit());
        this.connection = null;
        this.decoder = null;
        this.ctx = null;
        this.handler = null;
        this.trailers = null;
        this.processingChars = false;
        this.closed = false;
        this.contentRead = false;
        this.markPos = -1;
        this.readAheadLimit = -1;
        this.requestedSize = -1;
        this.readCount = 0;
        this.averageCharsPerByte = 1.0f;
        this.isWaitingDataAsynchronously = false;
        this.encoding = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
    }

    public void processingChars() {
        if (!this.processingChars) {
            this.processingChars = true;
            String enc = this.httpHeader.getCharacterEncoding();
            if (enc != null) {
                this.encoding = enc;
                this.averageCharsPerByte = getDecoder().averageCharsPerByte();
            }
        }
    }

    public int readByte() {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s readByte. Ready content: %s", this, this.inputContentBuffer);
        }
        if (this.closed) {
            throw new IOException("Already closed");
        } else if (!this.inputContentBuffer.hasRemaining() && fill(1) == -1) {
            return -1;
        } else {
            checkMarkAfterRead(1);
            return this.inputContentBuffer.get() & 255;
        }
    }

    public int read(byte[] b, int off, int len) {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s read byte array of len: %s. Ready content: %s", this, Integer.valueOf(len), this.inputContentBuffer);
        }
        if (this.closed) {
            throw new IOException("Already closed");
        } else if (len == 0) {
            return 0;
        } else {
            if (!this.inputContentBuffer.hasRemaining() && fill(1) == -1) {
                return -1;
            }
            int nlen = Math.min(this.inputContentBuffer.remaining(), len);
            this.inputContentBuffer.get(b, off, nlen);
            if (!checkMarkAfterRead((long) nlen)) {
                this.inputContentBuffer.shrink();
            }
            return nlen;
        }
    }

    public int readyData() {
        if (this.closed) {
            return 0;
        }
        return this.processingChars ? availableChar() : available();
    }

    public int available() {
        if (this.closed) {
            return 0;
        }
        return this.inputContentBuffer.remaining();
    }

    public Buffer getBuffer() {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s getBuffer. Ready content: %s", this, this.inputContentBuffer);
        }
        return this.inputContentBuffer.duplicate();
    }

    public Buffer readBuffer() {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s readBuffer. Ready content: %s", this, this.inputContentBuffer);
        }
        return readBuffer(this.inputContentBuffer.remaining());
    }

    public Buffer readBuffer(int size) {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s readBuffer(size), size: %s. Ready content: %s", this, Integer.valueOf(size), this.inputContentBuffer);
        }
        int remaining = this.inputContentBuffer.remaining();
        if (size > remaining) {
            throw new IllegalStateException("Can not read more bytes than available");
        } else if (size == remaining) {
            Buffer buffer = this.inputContentBuffer;
            this.inputContentBuffer = Buffers.EMPTY_BUFFER;
            return buffer;
        } else {
            Buffer buffer2 = this.inputContentBuffer;
            Buffer tmpBuffer = buffer2.split(buffer2.position() + size);
            Buffer buffer3 = this.inputContentBuffer;
            this.inputContentBuffer = tmpBuffer;
            return buffer3;
        }
    }

    public ReadHandler getReadHandler() {
        return this.handler;
    }

    public int read(CharBuffer target) {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s read(CharBuffer). Ready content: %s", this, this.inputContentBuffer);
        }
        if (this.closed) {
            throw new IOException("Already closed");
        } else if (!this.processingChars) {
            throw new IllegalStateException();
        } else if (target != null) {
            int read = fillChars(1, target);
            checkMarkAfterRead((long) read);
            return read;
        } else {
            throw new IllegalArgumentException("target cannot be null.");
        }
    }

    public int readChar() {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s readChar. Ready content: %s", this, this.inputContentBuffer);
        }
        if (this.closed) {
            throw new IOException("Already closed");
        } else if (this.processingChars) {
            if (!this.singleCharBuf.hasRemaining()) {
                this.singleCharBuf.clear();
                if (read(this.singleCharBuf) == -1) {
                    return -1;
                }
            }
            return this.singleCharBuf.get();
        } else {
            throw new IllegalStateException();
        }
    }

    public int read(char[] cbuf, int off, int len) {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s read char array, len: %s. Ready content: %s", this, Integer.valueOf(len), this.inputContentBuffer);
        }
        if (this.closed) {
            throw new IOException("Already closed");
        } else if (!this.processingChars) {
            throw new IllegalStateException();
        } else if (len == 0) {
            return 0;
        } else {
            return read(CharBuffer.wrap(cbuf, off, len));
        }
    }

    public boolean ready() {
        if (this.closed) {
            return false;
        }
        if (!this.processingChars) {
            throw new IllegalStateException();
        } else if (this.inputContentBuffer.hasRemaining() || this.httpHeader.isExpectContent()) {
            return true;
        } else {
            return false;
        }
    }

    public void fillFully(int length) {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s fillFully, len: %s. Ready content: %s", this, Integer.valueOf(length), this.inputContentBuffer);
        }
        if (length != 0) {
            if (length > 0) {
                int remaining = length - this.inputContentBuffer.remaining();
                if (remaining > 0) {
                    fill(remaining);
                    return;
                }
                return;
            }
            fill(-1);
        }
    }

    public int availableChar() {
        if (!this.singleCharBuf.hasRemaining()) {
            this.singleCharBuf.clear();
            if (fillAvailableChars(1, this.singleCharBuf) == 0) {
                CharBuffer charBuffer = this.singleCharBuf;
                charBuffer.position(charBuffer.limit());
                return 0;
            }
            this.singleCharBuf.flip();
        }
        return ((int) (((float) this.inputContentBuffer.remaining()) * this.averageCharsPerByte)) + 1;
    }

    public void mark(int readAheadLimit2) {
        if (readAheadLimit2 > 0) {
            this.markPos = this.inputContentBuffer.position();
            this.readCount = 0;
            this.readAheadLimit = readAheadLimit2;
        }
    }

    public boolean markSupported() {
        if (!this.processingChars) {
            return true;
        }
        throw new IllegalStateException();
    }

    public void reset() {
        if (this.closed) {
            throw new IOException("Already closed");
        } else if (this.readAheadLimit != -1) {
            this.readCount = 0;
            this.inputContentBuffer.position(this.markPos);
        } else {
            throw new IOException("Mark not set");
        }
    }

    public void close() {
        this.closed = true;
    }

    @Deprecated
    public long skip(long n, boolean block) {
        return skip(n);
    }

    public long skip(long n) {
        if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s skip %s bytes. Ready content: %s", this, Long.valueOf(n), this.inputContentBuffer);
        }
        if (!this.processingChars) {
            if (n <= 0) {
                return 0;
            }
            if (!this.inputContentBuffer.hasRemaining() && fill((int) n) == -1) {
                return -1;
            }
            if (((long) this.inputContentBuffer.remaining()) < n) {
                fill((int) n);
            }
            long nlen = Math.min((long) this.inputContentBuffer.remaining(), n);
            Buffer buffer = this.inputContentBuffer;
            buffer.position(buffer.position() + ((int) nlen));
            if (!checkMarkAfterRead(n)) {
                this.inputContentBuffer.shrink();
            }
            return nlen;
        } else if (n < 0) {
            throw new IllegalArgumentException();
        } else if (n == 0) {
            return 0;
        } else {
            CharBuffer skipBuffer = CharBuffer.allocate((int) n);
            if (fillChars((int) n, skipBuffer) == -1) {
                return 0;
            }
            return Math.min((long) skipBuffer.remaining(), n);
        }
    }

    public Map<String, String> getTrailers() {
        return this.trailers;
    }

    public boolean areTrailersAvailable() {
        return this.trailers != null;
    }

    /* access modifiers changed from: protected */
    public void finished() {
        if (!this.contentRead) {
            this.contentRead = true;
            ReadHandler localHandler = this.handler;
            processTrailers();
            if (localHandler != null) {
                this.handler = null;
                invokeHandlerAllRead(localHandler, getThreadPool());
            }
        }
    }

    private void finishedInTheCurrentThread(ReadHandler readHandler) {
        if (!this.contentRead) {
            this.contentRead = true;
            processTrailers();
            if (readHandler != null) {
                invokeHandlerAllRead(readHandler, (Executor) null);
            }
        }
    }

    private void invokeHandlerAllRead(final ReadHandler readHandler, Executor executor) {
        if (executor != null) {
            executor.execute(new Runnable() {
                public void run() {
                    try {
                        readHandler.onAllDataRead();
                    } catch (Throwable t) {
                        readHandler.onError(t);
                    }
                }
            });
            return;
        }
        try {
            readHandler.onAllDataRead();
        } catch (Throwable t) {
            readHandler.onError(t);
        }
    }

    private void processTrailers() {
        if (this.trailers == null) {
            MimeHeaders headers = this.httpHeader.getHeaders();
            int trailerSize = headers.trailerSize();
            if (trailerSize > 0) {
                this.trailers = new HashMap(trailerSize);
                for (String name : headers.trailerNames()) {
                    this.trailers.put(name.toLowerCase(), headers.getHeader(name));
                }
                return;
            }
            this.trailers = Collections.emptyMap();
        }
    }

    public void replayPayload(Buffer buffer) {
        if (isFinished()) {
            if (LOGGER.isLoggable(LOGGER_LEVEL)) {
                log("InputBuffer %s replayPayload to %s", this, buffer);
            }
            this.closed = false;
            this.readCount = 0;
            this.readAheadLimit = -1;
            this.markPos = -1;
            this.inputContentBuffer = buffer;
            return;
        }
        throw new IllegalStateException("Can't replay when InputBuffer is not closed");
    }

    public boolean isFinished() {
        return this.contentRead;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void notifyAvailable(ReadHandler handler2) {
        notifyAvailable(handler2, 1);
    }

    public void notifyAvailable(ReadHandler handler2, int size) {
        if (handler2 == null) {
            throw new IllegalArgumentException("handler cannot be null.");
        } else if (size <= 0) {
            throw new IllegalArgumentException("size should be positive integer");
        } else if (this.handler != null) {
            throw new IllegalStateException("Illegal attempt to register a new handler before the existing handler has been notified");
        } else if (this.closed || isFinished()) {
            try {
                handler2.onAllDataRead();
            } catch (Throwable ioe) {
                handler2.onError(ioe);
            }
        } else if (shouldNotifyNow(size, readyData())) {
            try {
                handler2.onDataAvailable();
            } catch (Throwable ioe2) {
                handler2.onError(ioe2);
            }
        } else {
            this.requestedSize = size;
            this.handler = handler2;
            if (!this.isWaitingDataAsynchronously) {
                this.isWaitingDataAsynchronously = true;
                initiateAsyncronousDataReceiving();
            }
        }
    }

    public boolean append(HttpContent httpContent) {
        this.isWaitingDataAsynchronously = false;
        if (!HttpContent.isBroken(httpContent)) {
            Buffer buffer = httpContent.getContent();
            if (this.closed) {
                buffer.dispose();
                return false;
            }
            ReadHandler localHandler = this.handler;
            boolean isLast = httpContent.isLast();
            boolean askForMoreDataInThisThread = !isLast && localHandler != null;
            boolean invokeDataAvailable = false;
            if (buffer.hasRemaining()) {
                updateInputContentBuffer(buffer);
                if (localHandler != null && readyData() >= this.requestedSize) {
                    invokeDataAvailable = true;
                    askForMoreDataInThisThread = false;
                }
            }
            if (askForMoreDataInThisThread) {
                this.isWaitingDataAsynchronously = true;
                return true;
            }
            this.handler = null;
            if (isLast) {
                checkHttpTrailer(httpContent);
            }
            invokeHandlerOnProperThread(localHandler, invokeDataAvailable, isLast);
        } else {
            ReadHandler localHandler2 = this.handler;
            this.handler = null;
            invokeErrorHandlerOnProperThread(localHandler2, ((HttpBrokenContent) httpContent).getException());
        }
        return false;
    }

    @Deprecated
    public boolean isAsyncEnabled() {
        return true;
    }

    @Deprecated
    public void setAsyncEnabled(boolean asyncEnabled) {
    }

    public void terminate() {
        ReadHandler localHandler = this.handler;
        if (localHandler != null) {
            this.handler = null;
            localHandler.onError(this.connection.isOpen() ? new CancellationException() : new EOFException());
        }
    }

    public void initiateAsyncronousDataReceiving() {
        FilterChainContext filterChainContext = this.ctx;
        filterChainContext.fork(filterChainContext.getStopAction());
    }

    /* access modifiers changed from: protected */
    public Executor getThreadPool() {
        ExecutorService es;
        if (Threads.isService() && (es = this.connection.getTransport().getWorkerThreadPool()) != null && !es.isShutdown()) {
            return es;
        }
        return null;
    }

    private void invokeErrorHandlerOnProperThread(final ReadHandler localHandler, final Throwable error) {
        if (!this.closed && localHandler != null) {
            Executor executor = getThreadPool();
            if (executor != null) {
                executor.execute(new Runnable() {
                    public void run() {
                        localHandler.onError(error);
                    }
                });
            } else {
                localHandler.onError(error);
            }
        }
    }

    private void invokeHandlerOnProperThread(final ReadHandler localHandler, final boolean invokeDataAvailable, final boolean isLast) {
        Executor executor = getThreadPool();
        if (executor != null) {
            executor.execute(new Runnable() {
                public void run() {
                    InputBuffer.this.invokeHandler(localHandler, invokeDataAvailable, isLast);
                }
            });
        } else {
            invokeHandler(localHandler, invokeDataAvailable, isLast);
        }
    }

    /* access modifiers changed from: private */
    public void invokeHandler(ReadHandler localHandler, boolean invokeDataAvailable, boolean isLast) {
        if (invokeDataAvailable) {
            try {
                localHandler.onDataAvailable();
            } catch (Throwable t) {
                localHandler.onError(t);
                return;
            }
        }
        if (isLast) {
            finishedInTheCurrentThread(localHandler);
        }
    }

    /* access modifiers changed from: protected */
    public HttpContent blockingRead() {
        ReadResult rr = this.ctx.read();
        HttpContent c = (HttpContent) rr.getMessage();
        rr.recycle();
        return c;
    }

    private int fill(int requestedLen) {
        int read = 0;
        while (true) {
            if ((requestedLen != -1 && read >= requestedLen) || !this.httpHeader.isExpectContent()) {
                break;
            }
            HttpContent c = blockingRead();
            boolean isLast = c.isLast();
            checkHttpTrailer(c);
            try {
                Buffer b = c.getContent();
                read += b.remaining();
                updateInputContentBuffer(b);
                c.recycle();
                if (isLast) {
                    finished();
                    break;
                }
            } catch (HttpBrokenContentException e) {
                Throwable cause = e.getCause();
                throw Exceptions.makeIOException(cause != null ? cause : e);
            }
        }
        if (read > 0 || requestedLen == 0) {
            return read;
        }
        return -1;
    }

    private int fillChars(int requestedLen, CharBuffer dst) {
        int read = 0;
        CharBuffer charBuffer = this.singleCharBuf;
        if (dst != charBuffer && charBuffer.hasRemaining()) {
            dst.put(this.singleCharBuf.get());
            read = 1;
        }
        if (this.inputContentBuffer.hasRemaining()) {
            read += fillAvailableChars(requestedLen - read, dst);
        }
        if (read >= requestedLen) {
            dst.flip();
            return read;
        } else if (!this.httpHeader.isExpectContent()) {
            dst.flip();
            if (read > 0) {
                return read;
            }
            return -1;
        } else {
            CharsetDecoder decoderLocal = getDecoder();
            boolean isNeedMoreInput = false;
            boolean last = false;
            while (read < requestedLen && this.httpHeader.isExpectContent()) {
                if (isNeedMoreInput || !this.inputContentBuffer.hasRemaining()) {
                    HttpContent c = blockingRead();
                    updateInputContentBuffer(c.getContent());
                    last = c.isLast();
                    c.recycle();
                    isNeedMoreInput = false;
                }
                ByteBuffer bytes = this.inputContentBuffer.toByteBuffer();
                int bytesPos = bytes.position();
                int dstPos = dst.position();
                CoderResult result = decoderLocal.decode(bytes, dst, false);
                int consumedBytes = bytes.position() - bytesPos;
                read += dst.position() - dstPos;
                if (consumedBytes > 0) {
                    bytes.position(bytesPos);
                    Buffer buffer = this.inputContentBuffer;
                    buffer.position(buffer.position() + consumedBytes);
                    if (this.readAheadLimit == -1) {
                        this.inputContentBuffer.shrink();
                    }
                } else {
                    isNeedMoreInput = true;
                }
                if (!last) {
                    if (result == CoderResult.OVERFLOW) {
                        break;
                    }
                } else {
                    break;
                }
            }
            dst.flip();
            if (!last || read != 0) {
                return read;
            }
            return -1;
        }
    }

    private int fillAvailableChars(int requestedLen, CharBuffer dst) {
        CoderResult result;
        CharsetDecoder decoderLocal = getDecoder();
        ByteBuffer bb = this.inputContentBuffer.toByteBuffer();
        int oldBBPos = bb.position();
        int producedChars = 0;
        int consumedBytes = 0;
        int remaining = requestedLen;
        do {
            int charPos = dst.position();
            int bbPos = bb.position();
            result = decoderLocal.decode(bb, dst, false);
            int producedCharsNow = dst.position() - charPos;
            int consumedBytesNow = bb.position() - bbPos;
            producedChars += producedCharsNow;
            consumedBytes += consumedBytesNow;
            remaining -= producedCharsNow;
            if (remaining <= 0 || ((producedCharsNow <= 0 && consumedBytesNow <= 0) || !bb.hasRemaining() || result != CoderResult.UNDERFLOW)) {
                bb.position(oldBBPos);
                Buffer buffer = this.inputContentBuffer;
                buffer.position(buffer.position() + consumedBytes);
            }
            int charPos2 = dst.position();
            int bbPos2 = bb.position();
            result = decoderLocal.decode(bb, dst, false);
            int producedCharsNow2 = dst.position() - charPos2;
            int consumedBytesNow2 = bb.position() - bbPos2;
            producedChars += producedCharsNow2;
            consumedBytes += consumedBytesNow2;
            remaining -= producedCharsNow2;
            break;
        } while (result != CoderResult.UNDERFLOW);
        bb.position(oldBBPos);
        Buffer buffer2 = this.inputContentBuffer;
        buffer2.position(buffer2.position() + consumedBytes);
        if (this.readAheadLimit == -1) {
            this.inputContentBuffer.shrink();
        }
        return producedChars;
    }

    /* access modifiers changed from: protected */
    public void updateInputContentBuffer(Buffer buffer) {
        buffer.allowBufferDispose(true);
        Buffer buffer2 = this.inputContentBuffer;
        if (buffer2 == null) {
            this.inputContentBuffer = buffer;
        } else if (buffer2.hasRemaining() || this.readAheadLimit > 0) {
            toCompositeInputContentBuffer().append(buffer);
        } else {
            this.inputContentBuffer.tryDispose();
            this.inputContentBuffer = buffer;
        }
    }

    private static boolean shouldNotifyNow(int size, int available) {
        return available != 0 && available >= size;
    }

    private CharsetDecoder getDecoder() {
        if (this.decoder == null) {
            CharsetDecoder charsetDecoder = this.decoders.get(this.encoding);
            this.decoder = charsetDecoder;
            if (charsetDecoder == null) {
                CharsetDecoder newDecoder = Charsets.lookupCharset(this.encoding).newDecoder();
                this.decoder = newDecoder;
                newDecoder.onMalformedInput(CodingErrorAction.REPLACE);
                this.decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
                this.decoders.put(this.encoding, this.decoder);
            } else {
                charsetDecoder.reset();
            }
        }
        return this.decoder;
    }

    private CompositeBuffer toCompositeInputContentBuffer() {
        if (!this.inputContentBuffer.isComposite()) {
            CompositeBuffer compositeBuffer = CompositeBuffer.newBuffer(this.connection.getMemoryManager());
            compositeBuffer.allowBufferDispose(true);
            compositeBuffer.allowInternalBuffersDispose(true);
            int posAlign = 0;
            if (this.readAheadLimit > 0) {
                Buffer buffer = this.inputContentBuffer;
                buffer.position(buffer.position() - this.readCount);
                posAlign = this.readCount;
                this.markPos = 0;
            }
            compositeBuffer.append(this.inputContentBuffer);
            compositeBuffer.position(posAlign);
            this.inputContentBuffer = compositeBuffer;
        }
        return (CompositeBuffer) this.inputContentBuffer;
    }

    private boolean checkMarkAfterRead(long n) {
        int i;
        if (n > 0 && (i = this.readAheadLimit) != -1) {
            int i2 = this.readCount;
            if (((long) i2) + n <= ((long) i)) {
                this.readCount = (int) (((long) i2) + n);
                return true;
            }
            this.readAheadLimit = -1;
            this.markPos = -1;
            this.readCount = 0;
        }
        return false;
    }

    private static void checkHttpTrailer(HttpContent httpContent) {
        if (HttpTrailer.isTrailer(httpContent)) {
            HttpHeader httpHeader2 = httpContent.getHttpHeader();
            httpHeader2.getHeaders().mark();
            MimeHeaders trailerHeaders = ((HttpTrailer) httpContent).getHeaders();
            int size = trailerHeaders.size();
            for (int i = 0; i < size; i++) {
                httpHeader2.addHeader(trailerHeaders.getName(i).toString(), trailerHeaders.getValue(i).toString());
            }
        }
    }

    private static void log(String message, Object... params) {
        String preparedMsg = String.format(message, params);
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, preparedMsg, new Exception("Logged at"));
        } else {
            logger.log(LOGGER_LEVEL, preparedMsg);
        }
    }
}
