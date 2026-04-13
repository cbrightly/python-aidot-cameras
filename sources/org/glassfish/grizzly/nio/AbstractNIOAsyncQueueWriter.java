package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractWriter;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.RecordWriteResult;
import org.glassfish.grizzly.asyncqueue.TaskQueue;
import org.glassfish.grizzly.asyncqueue.WritableMessage;

public abstract class AbstractNIOAsyncQueueWriter extends AbstractWriter<SocketAddress> implements AsyncQueueWriter<SocketAddress> {
    private static final Logger LOGGER = Grizzly.logger(AbstractNIOAsyncQueueWriter.class);
    private volatile boolean isAllowDirectWrite = true;
    protected volatile int maxPendingBytes = -2;
    protected volatile int maxWriteReentrants = 10;
    protected final NIOTransport transport;

    /* access modifiers changed from: protected */
    public abstract void onReadyToWrite(NIOConnection nIOConnection);

    /* access modifiers changed from: protected */
    public abstract RecordWriteResult write0(NIOConnection nIOConnection, AsyncWriteQueueRecord asyncWriteQueueRecord);

    public AbstractNIOAsyncQueueWriter(NIOTransport transport2) {
        this.transport = transport2;
    }

    @Deprecated
    public boolean canWrite(Connection<SocketAddress> connection, int size) {
        return canWrite(connection);
    }

    public boolean canWrite(Connection<SocketAddress> connection) {
        int size;
        NIOConnection nioConnection = (NIOConnection) connection;
        int connectionMaxPendingBytes = nioConnection.getMaxAsyncWriteQueueSize();
        if (connectionMaxPendingBytes >= 0 && (size = nioConnection.getAsyncWriteQueue().spaceInBytes()) != 0 && size >= connectionMaxPendingBytes) {
            return false;
        }
        return true;
    }

    @Deprecated
    public void notifyWritePossible(Connection<SocketAddress> connection, WriteHandler writeHandler, int size) {
        notifyWritePossible(connection, writeHandler);
    }

    public void notifyWritePossible(Connection<SocketAddress> connection, WriteHandler writeHandler) {
        ((NIOConnection) connection).getAsyncWriteQueue().notifyWritePossible(writeHandler);
    }

    public void setMaxPendingBytesPerConnection(int maxPendingBytes2) {
        int i = -2;
        if (maxPendingBytes2 >= -2) {
            i = maxPendingBytes2;
        }
        this.maxPendingBytes = i;
    }

    public int getMaxPendingBytesPerConnection() {
        return this.maxPendingBytes;
    }

    public boolean isAllowDirectWrite() {
        return this.isAllowDirectWrite;
    }

    public void setAllowDirectWrite(boolean isAllowDirectWrite2) {
        this.isAllowDirectWrite = isAllowDirectWrite2;
    }

    public void write(Connection<SocketAddress> connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, MessageCloner<WritableMessage> cloner) {
        write(connection, dstAddress, message, completionHandler, (PushBackHandler) null, cloner);
    }

    @Deprecated
    public void write(Connection<SocketAddress> connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, PushBackHandler pushBackHandler) {
        write(connection, dstAddress, message, completionHandler, pushBackHandler, (MessageCloner<WritableMessage>) null);
    }

    @Deprecated
    public void write(Connection<SocketAddress> connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, PushBackHandler pushBackHandler, MessageCloner<WritableMessage> cloner) {
        WritableMessage writableMessage = message;
        MessageCloner<WritableMessage> messageCloner = cloner;
        NIOConnection nioConnection = (NIOConnection) connection;
        AsyncWriteQueueRecord queueRecord = createRecord(nioConnection, message, completionHandler, dstAddress, pushBackHandler, !message.hasRemaining() || message.isExternal());
        if (nioConnection == null) {
            queueRecord.notifyFailure(new IOException("Connection is null"));
        } else if (!nioConnection.isOpen()) {
            onWriteFailure(nioConnection, queueRecord, nioConnection.getCloseReason().getCause());
        } else {
            TaskQueue<AsyncWriteQueueRecord> writeTaskQueue = nioConnection.getAsyncWriteQueue();
            int bytesToReserve = (int) queueRecord.getBytesToReserve();
            int pendingBytes = writeTaskQueue.reserveSpace(bytesToReserve);
            boolean isCurrent = pendingBytes == bytesToReserve;
            boolean isLogFine = LOGGER.isLoggable(Level.FINEST);
            if (isLogFine) {
                doFineLog("AsyncQueueWriter.write connection={0}, record={1}, directWrite={2}, size={3}, isUncountable={4}, bytesToReserve={5}, pendingBytes={6}", nioConnection, queueRecord, Boolean.valueOf(isCurrent), Long.valueOf(queueRecord.remaining()), Boolean.valueOf(queueRecord.isUncountable()), Integer.valueOf(bytesToReserve), Integer.valueOf(pendingBytes));
            }
            Writer.Reentrant reentrants = Writer.Reentrant.getWriteReentrant();
            try {
                if (!reentrants.inc()) {
                    queueRecord.setMessage(cloneRecordIfNeeded(nioConnection, messageCloner, writableMessage));
                    if (isCurrent) {
                        writeTaskQueue.setCurrentElement(queueRecord);
                        nioConnection.simulateIOEvent(IOEvent.WRITE);
                    } else {
                        writeTaskQueue.offer(queueRecord);
                    }
                    reentrants.dec();
                    return;
                }
                if (isCurrent) {
                    if (this.isAllowDirectWrite) {
                        int bytesToRelease = (int) write0(nioConnection, queueRecord).bytesToReleaseAfterLastWrite();
                        boolean isFinished = queueRecord.isFinished();
                        int pendingBytesAfterRelease = writeTaskQueue.releaseSpaceAndNotify(bytesToRelease);
                        boolean isQueueEmpty = pendingBytesAfterRelease == 0;
                        if (isLogFine) {
                            doFineLog("AsyncQueueWriter.write directWrite connection={0}, record={1}, isFinished={2}, remaining={3}, isUncountable={4}, bytesToRelease={5}, pendingBytesAfterRelease={6}", nioConnection, queueRecord, Boolean.valueOf(isFinished), Long.valueOf(queueRecord.remaining()), Boolean.valueOf(queueRecord.isUncountable()), Integer.valueOf(bytesToRelease), Integer.valueOf(pendingBytesAfterRelease));
                        }
                        if (isFinished) {
                            queueRecord.notifyCompleteAndRecycle();
                            if (!isQueueEmpty) {
                                nioConnection.simulateIOEvent(IOEvent.WRITE);
                            }
                            reentrants.dec();
                            return;
                        }
                    }
                }
                queueRecord.setMessage(cloneRecordIfNeeded(nioConnection, messageCloner, writableMessage));
                if (isLogFine) {
                    doFineLog("AsyncQueueWriter.write queuing connection={0}, record={1}, size={2}, isUncountable={3}", nioConnection, queueRecord, Long.valueOf(queueRecord.remaining()), Boolean.valueOf(queueRecord.isUncountable()));
                }
                if (isCurrent) {
                    writeTaskQueue.setCurrentElement(queueRecord);
                    onReadyToWrite(nioConnection);
                } else {
                    writeTaskQueue.offer(queueRecord);
                }
                reentrants.dec();
            } catch (IOException e) {
                if (isLogFine) {
                    Logger logger = LOGGER;
                    Level level = Level.FINEST;
                    logger.log(level, "AsyncQueueWriter.write exception. connection=" + nioConnection + " record=" + queueRecord, e);
                }
                onWriteFailure(nioConnection, queueRecord, e);
            } catch (Throwable th) {
                reentrants.dec();
                throw th;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x0107  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult processAsync(org.glassfish.grizzly.Context r20) {
        /*
            r19 = this;
            r1 = r19
            java.util.logging.Logger r0 = LOGGER
            java.util.logging.Level r2 = java.util.logging.Level.FINEST
            boolean r2 = r0.isLoggable(r2)
            org.glassfish.grizzly.Connection r0 = r20.getConnection()
            r3 = r0
            org.glassfish.grizzly.nio.NIOConnection r3 = (org.glassfish.grizzly.nio.NIOConnection) r3
            boolean r0 = r3.isOpen()
            if (r0 != 0) goto L_0x001a
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r0 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.COMPLETE
            return r0
        L_0x001a:
            org.glassfish.grizzly.asyncqueue.TaskQueue r4 = r3.getAsyncWriteQueue()
            r0 = 0
            r5 = 1
            r6 = 0
            r7 = r6
            r6 = r5
            r5 = r0
        L_0x0024:
            org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord r0 = r1.aggregate(r4)     // Catch:{ IOException -> 0x0102 }
            r7 = r0
            r9 = 3
            r10 = 5
            r11 = 2
            r12 = 1
            r13 = 0
            if (r0 == 0) goto L_0x008e
            if (r2 == 0) goto L_0x003d
            java.lang.String r0 = "AsyncQueueWriter.processAsync beforeWrite connection={0} record={1}"
            java.lang.Object[] r14 = new java.lang.Object[r11]     // Catch:{ IOException -> 0x0102 }
            r14[r13] = r3     // Catch:{ IOException -> 0x0102 }
            r14[r12] = r7     // Catch:{ IOException -> 0x0102 }
            doFineLog(r0, r14)     // Catch:{ IOException -> 0x0102 }
        L_0x003d:
            org.glassfish.grizzly.asyncqueue.RecordWriteResult r0 = r1.write0(r3, r7)     // Catch:{ IOException -> 0x0102 }
            long r14 = r0.bytesToReleaseAfterLastWrite()     // Catch:{ IOException -> 0x0102 }
            int r14 = (int) r14     // Catch:{ IOException -> 0x0102 }
            boolean r15 = r7.isFinished()     // Catch:{ IOException -> 0x0102 }
            r6 = r15
            int r5 = r5 + r14
            if (r2 == 0) goto L_0x0075
            java.lang.String r15 = "AsyncQueueWriter.processAsync written connection={0}, written={1}, done={2}, bytesToRelease={3}, bytesReleased={4}"
            java.lang.Object[] r8 = new java.lang.Object[r10]     // Catch:{ IOException -> 0x0102 }
            r8[r13] = r3     // Catch:{ IOException -> 0x0102 }
            long r17 = r0.lastWrittenBytes()     // Catch:{ IOException -> 0x0102 }
            java.lang.Long r17 = java.lang.Long.valueOf(r17)     // Catch:{ IOException -> 0x0102 }
            r8[r12] = r17     // Catch:{ IOException -> 0x0102 }
            java.lang.Boolean r17 = java.lang.Boolean.valueOf(r6)     // Catch:{ IOException -> 0x0102 }
            r8[r11] = r17     // Catch:{ IOException -> 0x0102 }
            java.lang.Integer r17 = java.lang.Integer.valueOf(r14)     // Catch:{ IOException -> 0x0102 }
            r8[r9] = r17     // Catch:{ IOException -> 0x0102 }
            java.lang.Integer r17 = java.lang.Integer.valueOf(r5)     // Catch:{ IOException -> 0x0102 }
            r16 = 4
            r8[r16] = r17     // Catch:{ IOException -> 0x0102 }
            doFineLog(r15, r8)     // Catch:{ IOException -> 0x0102 }
        L_0x0075:
            if (r6 == 0) goto L_0x007b
            finishQueueRecord(r3, r7)     // Catch:{ IOException -> 0x0102 }
            goto L_0x0024
        L_0x007b:
            r7.notifyIncomplete()     // Catch:{ IOException -> 0x0102 }
            r4.setCurrentElement(r7)     // Catch:{ IOException -> 0x0102 }
            if (r2 == 0) goto L_0x008e
            java.lang.String r8 = "AsyncQueueWriter.processAsync onReadyToWrite connection={0} peekRecord={1}"
            java.lang.Object[] r15 = new java.lang.Object[r11]     // Catch:{ IOException -> 0x0102 }
            r15[r13] = r3     // Catch:{ IOException -> 0x0102 }
            r15[r12] = r7     // Catch:{ IOException -> 0x0102 }
            doFineLog(r8, r15)     // Catch:{ IOException -> 0x0102 }
        L_0x008e:
            r0 = 0
            if (r5 <= 0) goto L_0x00b8
            if (r6 == 0) goto L_0x00ae
            boolean r8 = r20.isManualIOEventControl()     // Catch:{ IOException -> 0x0102 }
            if (r8 != 0) goto L_0x00ae
            int r8 = r4.spaceInBytes()     // Catch:{ IOException -> 0x0102 }
            int r8 = r8 - r5
            if (r8 > 0) goto L_0x00ae
            if (r2 == 0) goto L_0x00ab
            java.lang.String r8 = "AsyncQueueWriter.processAsync setManualIOEventControl connection={0}"
            java.lang.Object[] r14 = new java.lang.Object[r12]     // Catch:{ IOException -> 0x0102 }
            r14[r13] = r3     // Catch:{ IOException -> 0x0102 }
            doFineLog(r8, r14)     // Catch:{ IOException -> 0x0102 }
        L_0x00ab:
            r20.setManualIOEventControl()     // Catch:{ IOException -> 0x0102 }
        L_0x00ae:
            int r8 = r4.releaseSpace(r5)     // Catch:{ IOException -> 0x0102 }
            if (r8 != 0) goto L_0x00b6
            r8 = r12
            goto L_0x00b7
        L_0x00b6:
            r8 = r13
        L_0x00b7:
            r0 = r8
        L_0x00b8:
            if (r2 == 0) goto L_0x00e0
            java.lang.String r8 = "AsyncQueueWriter.processAsync exit connection={0}, done={1}, isComplete={2}, bytesReleased={3}, queueSize={4}"
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ IOException -> 0x0102 }
            r10[r13] = r3     // Catch:{ IOException -> 0x0102 }
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r6)     // Catch:{ IOException -> 0x0102 }
            r10[r12] = r13     // Catch:{ IOException -> 0x0102 }
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r0)     // Catch:{ IOException -> 0x0102 }
            r10[r11] = r12     // Catch:{ IOException -> 0x0102 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)     // Catch:{ IOException -> 0x0102 }
            r10[r9] = r11     // Catch:{ IOException -> 0x0102 }
            int r9 = r4.size()     // Catch:{ IOException -> 0x0102 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ IOException -> 0x0102 }
            r11 = 4
            r10[r11] = r9     // Catch:{ IOException -> 0x0102 }
            doFineLog(r8, r10)     // Catch:{ IOException -> 0x0102 }
        L_0x00e0:
            if (r6 != 0) goto L_0x00e5
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r8 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.INCOMPLETE     // Catch:{ IOException -> 0x0102 }
            goto L_0x00ec
        L_0x00e5:
            if (r0 != 0) goto L_0x00ea
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r8 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.EXPECTING_MORE     // Catch:{ IOException -> 0x0102 }
            goto L_0x00ec
        L_0x00ea:
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r8 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.COMPLETE     // Catch:{ IOException -> 0x0102 }
        L_0x00ec:
            if (r5 <= 0) goto L_0x00ff
            org.glassfish.grizzly.ProcessorResult r9 = r8.toProcessorResult()     // Catch:{ IOException -> 0x0102 }
            r10 = r20
            r10.complete(r9)     // Catch:{ IOException -> 0x00fd }
            r4.doNotify()     // Catch:{ IOException -> 0x00fd }
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r9 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.TERMINATE     // Catch:{ IOException -> 0x00fd }
            return r9
        L_0x00fd:
            r0 = move-exception
            goto L_0x0105
        L_0x00ff:
            r10 = r20
            return r8
        L_0x0102:
            r0 = move-exception
            r10 = r20
        L_0x0105:
            if (r2 == 0) goto L_0x0127
            java.util.logging.Logger r8 = LOGGER
            java.util.logging.Level r9 = java.util.logging.Level.FINEST
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "AsyncQueueWriter.processAsync exception connection="
            r11.append(r12)
            r11.append(r3)
            java.lang.String r12 = " peekRecord="
            r11.append(r12)
            r11.append(r7)
            java.lang.String r11 = r11.toString()
            r8.log(r9, r11, r0)
        L_0x0127:
            onWriteFailure(r3, r7, r0)
            org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult r0 = org.glassfish.grizzly.asyncqueue.AsyncQueue.AsyncResult.COMPLETE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.nio.AbstractNIOAsyncQueueWriter.processAsync(org.glassfish.grizzly.Context):org.glassfish.grizzly.asyncqueue.AsyncQueue$AsyncResult");
    }

    private static void finishQueueRecord(NIOConnection nioConnection, AsyncWriteQueueRecord queueRecord) {
        boolean isLogFine = LOGGER.isLoggable(Level.FINEST);
        if (isLogFine) {
            doFineLog("AsyncQueueWriter.processAsync finished connection={0} record={1}", nioConnection, queueRecord);
        }
        if (queueRecord != null) {
            queueRecord.notifyCompleteAndRecycle();
        }
        if (isLogFine) {
            doFineLog("AsyncQueueWriter.processAsync finishQueueRecord connection={0} queueRecord={1}", nioConnection, queueRecord);
        }
    }

    private static WritableMessage cloneRecordIfNeeded(Connection connection, MessageCloner<WritableMessage> cloner, WritableMessage message) {
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "AsyncQueueWriter.write clone. connection={0} cloner={1} size={2}", new Object[]{connection, cloner, Integer.valueOf(message.remaining())});
        }
        return cloner == null ? message : cloner.clone(connection, message);
    }

    /* access modifiers changed from: protected */
    public AsyncWriteQueueRecord createRecord(Connection connection, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, SocketAddress dstAddress, PushBackHandler pushBackHandler, boolean isUncountable) {
        return AsyncWriteQueueRecord.create(connection, message, completionHandler, dstAddress, pushBackHandler, isUncountable);
    }

    public final boolean isReady(Connection connection) {
        TaskQueue connectionQueue = ((NIOConnection) connection).getAsyncWriteQueue();
        return connectionQueue != null && !connectionQueue.isEmpty();
    }

    private static void doFineLog(String msg, Object... params) {
        LOGGER.log(Level.FINEST, msg, params);
    }

    public void onClose(Connection connection) {
        NIOConnection nioConnection = (NIOConnection) connection;
        nioConnection.getAsyncWriteQueue().onClose(nioConnection.getCloseReason().getCause());
    }

    public final void close() {
    }

    protected static void onWriteFailure(Connection connection, AsyncWriteQueueRecord failedRecord, Throwable e) {
        failedRecord.notifyFailure(e);
        connection.closeSilently();
    }

    /* access modifiers changed from: protected */
    public AsyncWriteQueueRecord aggregate(TaskQueue<AsyncWriteQueueRecord> connectionQueue) {
        return connectionQueue.poll();
    }
}
