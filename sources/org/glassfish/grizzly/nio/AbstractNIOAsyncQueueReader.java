package org.glassfish.grizzly.nio;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractReader;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.AsyncQueueReader;
import org.glassfish.grizzly.asyncqueue.AsyncReadQueueRecord;
import org.glassfish.grizzly.asyncqueue.TaskQueue;

public abstract class AbstractNIOAsyncQueueReader extends AbstractReader<SocketAddress> implements AsyncQueueReader<SocketAddress> {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final Logger LOGGER = Grizzly.logger(AbstractNIOAsyncQueueReader.class);
    private EOFException cachedEOFException;
    protected int defaultBufferSize = 8192;
    protected final NIOTransport transport;

    /* access modifiers changed from: protected */
    public abstract void onReadyToRead(Connection connection);

    /* access modifiers changed from: protected */
    public abstract int read0(Connection connection, Buffer buffer, ReadResult<Buffer, SocketAddress> readResult);

    public AbstractNIOAsyncQueueReader(NIOTransport transport2) {
        this.transport = transport2;
    }

    public void read(Connection<SocketAddress> connection, Buffer buffer, CompletionHandler<ReadResult<Buffer, SocketAddress>> completionHandler, Interceptor<ReadResult> interceptor) {
        if (connection == null) {
            failure(new IOException("Connection is null"), completionHandler);
        } else if (!connection.isOpen()) {
            failure(new IOException("Connection is closed"), completionHandler);
        } else {
            TaskQueue<AsyncReadQueueRecord> connectionQueue = ((NIOConnection) connection).getAsyncReadQueue();
            AsyncReadQueueRecord queueRecord = AsyncReadQueueRecord.create(connection, buffer, completionHandler, interceptor);
            ReadResult<Buffer, SocketAddress> currentResult = queueRecord.getCurrentResult();
            boolean isQueueEmpty = true;
            if (connectionQueue.reserveSpace(1) == 1) {
                try {
                    doRead(connection, queueRecord);
                    int interceptInstructions = intercept(1, queueRecord, currentResult);
                    if ((interceptInstructions & 1) == 0) {
                        if (interceptor != null || !queueRecord.isFinished()) {
                            if ((interceptInstructions & 4) != 0) {
                                currentResult.setMessage(null);
                                currentResult.setReadSize(0);
                                queueRecord.setMessage((Object) null);
                            }
                            connectionQueue.setCurrentElement(queueRecord);
                            queueRecord.notifyIncomplete();
                            onReadyToRead(connection);
                            intercept(3, queueRecord, (ReadResult) null);
                        }
                    }
                    if (connectionQueue.releaseSpaceAndNotify(1) != 0) {
                        isQueueEmpty = false;
                    }
                    queueRecord.notifyComplete();
                    if (!isQueueEmpty) {
                        onReadyToRead(connection);
                    }
                    intercept(2, queueRecord, (ReadResult) null);
                    queueRecord.recycle();
                } catch (IOException e) {
                    onReadFailure(connection, queueRecord, e);
                }
            } else {
                connectionQueue.offer(queueRecord);
                if (!connection.isOpen() && connectionQueue.remove(queueRecord)) {
                    onReadFailure(connection, queueRecord, new EOFException("Connection is closed"));
                }
            }
        }
    }

    public final boolean isReady(Connection connection) {
        TaskQueue connectionQueue = ((NIOConnection) connection).getAsyncReadQueue();
        return connectionQueue != null && !connectionQueue.isEmpty();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        if ((r7 & 4) == 0) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0041, code lost:
        r4.setMessage(null);
        r4.setReadSize(0);
        r3.setMessage((java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004a, code lost:
        r1.setCurrentElement(r3);
        r3.notifyIncomplete();
        intercept(3, r3, (org.glassfish.grizzly.ReadResult) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0056, code lost:
        return org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.INCOMPLETE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult processAsync(org.glassfish.grizzly.Context r12) {
        /*
            r11 = this;
            org.glassfish.grizzly.Connection r0 = r12.getConnection()
            org.glassfish.grizzly.nio.NIOConnection r0 = (org.glassfish.grizzly.nio.NIOConnection) r0
            boolean r1 = r0.isOpen()
            if (r1 != 0) goto L_0x000f
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r1 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.COMPLETE
            return r1
        L_0x000f:
            org.glassfish.grizzly.asyncqueue.TaskQueue r1 = r0.getAsyncReadQueue()
            r2 = 0
            r3 = 0
        L_0x0015:
            org.glassfish.grizzly.asyncqueue.AsyncQueueRecord r4 = r1.poll()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            org.glassfish.grizzly.asyncqueue.AsyncReadQueueRecord r4 = (org.glassfish.grizzly.asyncqueue.AsyncReadQueueRecord) r4     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r3 = r4
            if (r4 == 0) goto L_0x007e
            org.glassfish.grizzly.ReadResult r4 = r3.getCurrentResult()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r11.doRead(r0, r3)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            org.glassfish.grizzly.Interceptor r5 = r3.getInterceptor()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r6 = 1
            int r7 = r11.intercept(r6, r3, r4)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r8 = r7 & 1
            r9 = 0
            r10 = 0
            if (r8 != 0) goto L_0x0057
            if (r5 != 0) goto L_0x003d
            boolean r8 = r3.isFinished()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            if (r8 == 0) goto L_0x003d
            goto L_0x0057
        L_0x003d:
            r6 = r7 & 4
            if (r6 == 0) goto L_0x004a
            r4.setMessage(r10)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r4.setReadSize(r9)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r3.setMessage(r10)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
        L_0x004a:
            r1.setCurrentElement(r3)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r3.notifyIncomplete()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r6 = 3
            r11.intercept(r6, r3, r10)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r6 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.INCOMPLETE     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            return r6
        L_0x0057:
            boolean r8 = r12.isManualIOEventControl()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            if (r8 != 0) goto L_0x0067
            int r8 = r1.spaceInBytes()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            int r8 = r8 - r6
            if (r8 > 0) goto L_0x0067
            r12.setManualIOEventControl()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
        L_0x0067:
            int r8 = r1.releaseSpaceAndNotify(r6)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            if (r8 != 0) goto L_0x006e
            goto L_0x006f
        L_0x006e:
            r6 = r9
        L_0x006f:
            r2 = r6
            r3.notifyComplete()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r6 = 2
            r11.intercept(r6, r3, r10)     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            r3.recycle()     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            if (r2 == 0) goto L_0x007d
            goto L_0x007e
        L_0x007d:
            goto L_0x0015
        L_0x007e:
            if (r2 != 0) goto L_0x00b2
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r4 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.EXPECTING_MORE     // Catch:{ IOException -> 0x00ae, Exception -> 0x0083 }
            return r4
        L_0x0083:
            r4 = move-exception
            java.lang.String r5 = "Unexpected exception occurred in AsyncQueueReader"
            java.util.logging.Logger r6 = LOGGER
            java.util.logging.Level r7 = java.util.logging.Level.SEVERE
            r6.log(r7, r5, r4)
            java.io.IOException r6 = new java.io.IOException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.Class r8 = r4.getClass()
            r7.append(r8)
            java.lang.String r8 = ": "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            r11.onReadFailure(r0, r3, r6)
            goto L_0x00b3
        L_0x00ae:
            r4 = move-exception
            r11.onReadFailure(r0, r3, r4)
        L_0x00b2:
        L_0x00b3:
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r4 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.COMPLETE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.nio.AbstractNIOAsyncQueueReader.processAsync(org.glassfish.grizzly.Context):org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult");
    }

    public void onClose(Connection connection) {
        TaskQueue<AsyncReadQueueRecord> readQueue = ((NIOConnection) connection).getAsyncReadQueue();
        if (!readQueue.isEmpty()) {
            EOFException error = this.cachedEOFException;
            if (error == null) {
                error = new EOFException("Connection closed");
                this.cachedEOFException = error;
            }
            while (true) {
                AsyncReadQueueRecord poll = readQueue.poll();
                AsyncReadQueueRecord record = poll;
                if (poll != null) {
                    record.notifyFailure(error);
                } else {
                    return;
                }
            }
        }
    }

    public final void close() {
    }

    /* access modifiers changed from: protected */
    public final int doRead(Connection connection, AsyncReadQueueRecord queueRecord) {
        int readBytes = read0(connection, (Buffer) queueRecord.getMessage(), queueRecord.getCurrentResult());
        if (readBytes != -1) {
            return readBytes;
        }
        throw new EOFException();
    }

    /* access modifiers changed from: protected */
    public final void onReadFailure(Connection connection, AsyncReadQueueRecord failedRecord, IOException e) {
        if (failedRecord != null) {
            failedRecord.notifyFailure(e);
        }
        connection.closeSilently();
    }

    private static void failure(Throwable failure, CompletionHandler<ReadResult<Buffer, SocketAddress>> completionHandler) {
        if (completionHandler != null) {
            completionHandler.failed(failure);
        }
    }

    private int intercept(int event, AsyncReadQueueRecord asyncQueueRecord, ReadResult currentResult) {
        Interceptor<ReadResult> interceptor = asyncQueueRecord.getInterceptor();
        if (interceptor != null) {
            return interceptor.intercept(event, asyncQueueRecord, currentResult);
        }
        return 0;
    }
}
