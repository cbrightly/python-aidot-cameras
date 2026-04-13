package org.glassfish.grizzly.streams;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.CompositeBuffer;

public abstract class BufferedOutput implements Output {
    protected static final Integer ZERO = 0;
    protected static final GrizzlyFuture<Integer> ZERO_READY_FUTURE = ReadyFutureImpl.create(0);
    private Buffer buffer;
    protected final int bufferSize;
    protected final AtomicBoolean isClosed;
    private int lastSlicedPosition;
    protected CompositeBuffer multiBufferWindow;

    /* access modifiers changed from: protected */
    public abstract GrizzlyFuture<Integer> flush0(Buffer buffer2, CompletionHandler<Integer> completionHandler);

    /* access modifiers changed from: protected */
    public abstract Buffer newBuffer(int i);

    /* access modifiers changed from: protected */
    public abstract void onClosed();

    /* access modifiers changed from: protected */
    public abstract Buffer reallocateBuffer(Buffer buffer2, int i);

    public BufferedOutput() {
        this(8192);
    }

    public BufferedOutput(int bufferSize2) {
        this.isClosed = new AtomicBoolean();
        this.bufferSize = bufferSize2;
    }

    public void write(byte data) {
        ensureBufferCapacity(1);
        this.buffer.put(data);
    }

    public void write(Buffer bufferToWrite) {
        if (this.multiBufferWindow == null) {
            this.multiBufferWindow = CompositeBuffer.newBuffer();
        }
        Buffer buffer2 = this.buffer;
        if (!(buffer2 == null || buffer2.position() - this.lastSlicedPosition == 0)) {
            Buffer buffer3 = this.buffer;
            this.multiBufferWindow.append(buffer3.slice(this.lastSlicedPosition, buffer3.position()));
            this.lastSlicedPosition = this.buffer.position();
        }
        this.multiBufferWindow.append(bufferToWrite);
        ensureBufferCapacity(0);
    }

    public boolean isBuffered() {
        return true;
    }

    public Buffer getBuffer() {
        return this.buffer;
    }

    public void ensureBufferCapacity(int size) {
        if (size <= this.bufferSize) {
            if (getBufferedSize() >= this.bufferSize) {
                overflow((CompletionHandler<Integer>) null);
            }
            if (size != 0) {
                Buffer buffer2 = this.buffer;
                if (buffer2 == null) {
                    this.buffer = newBuffer(this.bufferSize);
                } else if (buffer2.remaining() < size) {
                    overflow((CompletionHandler<Integer>) null);
                    ensureBufferCapacity(size);
                }
            }
        } else {
            throw new IllegalArgumentException("Size exceeds max size limit: " + this.bufferSize);
        }
    }

    private GrizzlyFuture<Integer> overflow(CompletionHandler<Integer> completionHandler) {
        int i;
        if (this.multiBufferWindow != null) {
            Buffer buffer2 = this.buffer;
            if (buffer2 != null && buffer2.position() > (i = this.lastSlicedPosition)) {
                Buffer buffer3 = this.buffer;
                Buffer slice = buffer3.slice(i, buffer3.position());
                this.lastSlicedPosition = this.buffer.position();
                this.multiBufferWindow.append(slice);
            }
            GrizzlyFuture<Integer> future = flush0(this.multiBufferWindow, completionHandler);
            if (future.isDone()) {
                this.multiBufferWindow.removeAll();
                this.multiBufferWindow.clear();
                Buffer buffer4 = this.buffer;
                if (buffer4 != null) {
                    if (!buffer4.isComposite()) {
                        this.buffer.clear();
                    } else {
                        this.buffer = null;
                    }
                    this.lastSlicedPosition = 0;
                }
            } else {
                this.multiBufferWindow = null;
                this.buffer = null;
                this.lastSlicedPosition = 0;
            }
            return future;
        }
        Buffer buffer5 = this.buffer;
        if (buffer5 == null || buffer5.position() <= 0) {
            return flush0((Buffer) null, completionHandler);
        }
        this.buffer.flip();
        GrizzlyFuture<Integer> future2 = flush0(this.buffer, completionHandler);
        if (!future2.isDone() || this.buffer.isComposite()) {
            this.buffer = null;
        } else {
            this.buffer.clear();
        }
        return future2;
    }

    public GrizzlyFuture<Integer> flush(CompletionHandler<Integer> completionHandler) {
        return overflow(completionHandler);
    }

    public GrizzlyFuture<Integer> close(final CompletionHandler<Integer> completionHandler) {
        Buffer buffer2;
        if (this.isClosed.getAndSet(true) || (buffer2 = this.buffer) == null || buffer2.position() <= 0) {
            if (completionHandler != null) {
                completionHandler.completed(ZERO);
            }
            return ZERO_READY_FUTURE;
        }
        final FutureImpl<Integer> future = SafeFutureImpl.create();
        try {
            overflow(new CompletionHandler<Integer>() {
                public void cancelled() {
                    close(BufferedOutput.ZERO);
                }

                public void failed(Throwable throwable) {
                    close(BufferedOutput.ZERO);
                }

                public void completed(Integer result) {
                    close(result);
                }

                public void updated(Integer result) {
                }

                /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
                    if (r0 == null) goto L_0x0020;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
                    r0.completed(r3);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
                    r0.result(r3);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:3:0x0007, code lost:
                    if (r0 != null) goto L_0x001d;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void close(java.lang.Integer r3) {
                    /*
                        r2 = this;
                        org.glassfish.grizzly.streams.BufferedOutput r0 = org.glassfish.grizzly.streams.BufferedOutput.this     // Catch:{ IOException -> 0x0018, all -> 0x000a }
                        r0.onClosed()     // Catch:{ IOException -> 0x0018, all -> 0x000a }
                        org.glassfish.grizzly.CompletionHandler r0 = r3
                        if (r0 == 0) goto L_0x0020
                        goto L_0x001d
                    L_0x000a:
                        r0 = move-exception
                        org.glassfish.grizzly.CompletionHandler r1 = r3
                        if (r1 == 0) goto L_0x0012
                        r1.completed(r3)
                    L_0x0012:
                        org.glassfish.grizzly.impl.FutureImpl r1 = r0
                        r1.result(r3)
                        throw r0
                    L_0x0018:
                        r0 = move-exception
                        org.glassfish.grizzly.CompletionHandler r0 = r3
                        if (r0 == 0) goto L_0x0020
                    L_0x001d:
                        r0.completed(r3)
                    L_0x0020:
                        org.glassfish.grizzly.impl.FutureImpl r0 = r0
                        r0.result(r3)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.streams.BufferedOutput.AnonymousClass1.close(java.lang.Integer):void");
                }
            });
        } catch (IOException e) {
        }
        return future;
    }

    /* access modifiers changed from: protected */
    public int getBufferedSize() {
        int size = 0;
        CompositeBuffer compositeBuffer = this.multiBufferWindow;
        if (compositeBuffer != null) {
            size = compositeBuffer.remaining();
        }
        Buffer buffer2 = this.buffer;
        if (buffer2 != null) {
            return size + (buffer2.position() - this.lastSlicedPosition);
        }
        return size;
    }
}
