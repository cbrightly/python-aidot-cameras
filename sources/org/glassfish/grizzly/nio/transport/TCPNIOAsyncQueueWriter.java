package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.RecordWriteResult;
import org.glassfish.grizzly.asyncqueue.TaskQueue;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.nio.AbstractNIOAsyncQueueWriter;
import org.glassfish.grizzly.nio.DirectByteBufferRecord;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;

public final class TCPNIOAsyncQueueWriter extends AbstractNIOAsyncQueueWriter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Attribute<CompositeQueueRecord> COMPOSITE_BUFFER_ATTR;
    /* access modifiers changed from: private */
    public static final Logger LOGGER;

    static {
        Class<TCPNIOAsyncQueueWriter> cls = TCPNIOAsyncQueueWriter.class;
        LOGGER = Grizzly.logger(cls);
        AttributeBuilder attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        COMPOSITE_BUFFER_ATTR = attributeBuilder.createAttribute(cls.getName() + ".compositeBuffer");
    }

    public TCPNIOAsyncQueueWriter(NIOTransport transport) {
        super(transport);
    }

    /* access modifiers changed from: protected */
    public RecordWriteResult write0(NIOConnection connection, AsyncWriteQueueRecord queueRecord) {
        if (queueRecord instanceof CompositeQueueRecord) {
            return writeCompositeRecord(connection, (CompositeQueueRecord) queueRecord);
        }
        RecordWriteResult writeResult = queueRecord.getCurrentResult();
        if (queueRecord.remaining() == 0) {
            return writeResult.lastWriteResult(0, queueRecord.isUncountable() ? 1 : 0);
        }
        long written = write0(connection, queueRecord.getWritableMessage(), writeResult);
        return writeResult.lastWriteResult(written, written);
    }

    /* access modifiers changed from: protected */
    public long write0(NIOConnection connection, WritableMessage message, WriteResult<WritableMessage, SocketAddress> currentResult) {
        long written;
        if (message instanceof Buffer) {
            Buffer buffer = (Buffer) message;
            try {
                if (!buffer.hasRemaining()) {
                    written = 0;
                } else if (!buffer.isComposite()) {
                    written = (long) TCPNIOUtils.writeSimpleBuffer((TCPNIOConnection) connection, buffer);
                } else {
                    written = (long) TCPNIOUtils.writeCompositeBuffer((TCPNIOConnection) connection, (CompositeBuffer) buffer);
                }
                ((TCPNIOConnection) connection).onWrite(buffer, written);
            } catch (IOException e) {
                ((TCPNIOConnection) connection).terminate0((CompletionHandler<Closeable>) null, new CloseReason(CloseType.REMOTELY, e));
                throw e;
            }
        } else if (message instanceof FileTransfer) {
            written = ((FileTransfer) message).writeTo((SocketChannel) connection.getChannel());
            ((TCPNIOConnection) connection).onWrite((Buffer) null, written);
        } else {
            throw new IllegalStateException("Unhandled message type");
        }
        if (currentResult != null) {
            currentResult.setMessage(message);
            currentResult.setWrittenSize(currentResult.getWrittenSize() + written);
            currentResult.setDstAddressHolder(((TCPNIOConnection) connection).peerSocketAddressHolder);
        }
        return written;
    }

    private RecordWriteResult writeCompositeRecord(NIOConnection connection, CompositeQueueRecord queueRecord) {
        int i;
        int written = 0;
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "writeCompositeRecord connection={0}, queueRecord={1}, queueRecord.remaining={2}, queueRecord.queue.size()={3}", new Object[]{connection, queueRecord, Long.valueOf(queueRecord.remaining()), Integer.valueOf(queueRecord.queue.size())});
        }
        if (queueRecord.size > 0) {
            int bufferSize = Math.min(queueRecord.size, (connection.getWriteBufferSize() * 3) / 2);
            DirectByteBufferRecord directByteBufferRecord = DirectByteBufferRecord.get();
            try {
                SocketChannel socketChannel = (SocketChannel) connection.getChannel();
                fill(queueRecord, bufferSize, directByteBufferRecord);
                directByteBufferRecord.finishBufferSlice();
                int arraySize = directByteBufferRecord.getArraySize();
                if (arraySize == 1) {
                    i = TCPNIOUtils.flushByteBuffer(socketChannel, directByteBufferRecord.getArray()[0]);
                } else {
                    i = TCPNIOUtils.flushByteBuffers(socketChannel, directByteBufferRecord.getArray(), 0, arraySize);
                }
                written = i;
                directByteBufferRecord.release();
            } catch (IOException e) {
                ((TCPNIOConnection) connection).terminate0((CompletionHandler<Closeable>) null, new CloseReason(CloseType.REMOTELY, e));
                throw e;
            } catch (Throwable th) {
                directByteBufferRecord.release();
                throw th;
            }
        }
        return update(queueRecord, written);
    }

    private static void fill(CompositeQueueRecord queueRecord, int totalBufferSize, DirectByteBufferRecord ioRecord) {
        int totalRemaining = totalBufferSize;
        Deque<AsyncWriteQueueRecord> queue = queueRecord.queue;
        ArrayList<BufferArray> savedBufferStates = queueRecord.savedBufferStates;
        Iterator<AsyncWriteQueueRecord> it = queue.iterator();
        while (it.hasNext() && totalRemaining > 0) {
            AsyncWriteQueueRecord record = it.next();
            if (!record.isUncountable()) {
                Buffer message = (Buffer) record.getMessage();
                int pos = message.position();
                int messageRemaining = message.remaining();
                BufferArray bufferArray = totalRemaining >= messageRemaining ? message.toBufferArray() : message.toBufferArray(pos, pos + totalRemaining);
                savedBufferStates.add(bufferArray);
                TCPNIOUtils.fill(bufferArray, totalRemaining, ioRecord);
                totalRemaining -= messageRemaining;
            }
        }
    }

    private RecordWriteResult update(CompositeQueueRecord queueRecord, int written) {
        int i = written;
        for (int i2 = 0; i2 < queueRecord.savedBufferStates.size(); i2++) {
            BufferArray savedState = (BufferArray) queueRecord.savedBufferStates.get(i2);
            if (savedState != null) {
                savedState.restore();
                savedState.recycle();
            }
        }
        int extraBytesToRelease = 0;
        queueRecord.savedBufferStates.clear();
        int remainder = written;
        int unused = queueRecord.size = queueRecord.size - i;
        Connection connection = queueRecord.getConnection();
        Deque<AsyncWriteQueueRecord> queue = queueRecord.queue;
        while (remainder > 0) {
            AsyncWriteQueueRecord record = queue.peekFirst();
            if (record == null) {
                throw new AssertionError();
            } else if (record.isUncountable()) {
                queue.removeFirst();
                record.notifyCompleteAndRecycle();
                extraBytesToRelease++;
            } else {
                WriteResult firstResult = record.getCurrentResult();
                Buffer firstMessage = (Buffer) record.getMessage();
                long firstMessageRemaining = record.getInitialMessageSize() - firstResult.getWrittenSize();
                if (((long) remainder) >= firstMessageRemaining) {
                    remainder = (int) (((long) remainder) - firstMessageRemaining);
                    queue.removeFirst();
                    firstResult.setWrittenSize(record.getInitialMessageSize());
                    firstMessage.position(firstMessage.limit());
                    ((TCPNIOConnection) connection).onWrite(firstMessage, firstMessageRemaining);
                    record.notifyCompleteAndRecycle();
                } else {
                    firstMessage.position(firstMessage.position() + remainder);
                    firstResult.setWrittenSize(firstResult.getWrittenSize() + ((long) remainder));
                    ((TCPNIOConnection) connection).onWrite(firstMessage, (long) remainder);
                    return queueRecord.getCurrentResult().lastWriteResult((long) i, (long) (i + extraBytesToRelease));
                }
            }
        }
        while (true) {
            AsyncWriteQueueRecord peekFirst = queue.peekFirst();
            AsyncWriteQueueRecord record2 = peekFirst;
            if (peekFirst != null && record2.isUncountable()) {
                queue.removeFirst();
                record2.notifyCompleteAndRecycle();
                extraBytesToRelease++;
            }
        }
        return queueRecord.getCurrentResult().lastWriteResult((long) i, (long) (i + extraBytesToRelease));
    }

    /* access modifiers changed from: protected */
    public void onReadyToWrite(NIOConnection connection) {
        connection.enableIOEvent(IOEvent.WRITE);
    }

    /* access modifiers changed from: protected */
    public AsyncWriteQueueRecord aggregate(TaskQueue<AsyncWriteQueueRecord> writeTaskQueue) {
        AsyncWriteQueueRecord nextRecord;
        AsyncWriteQueueRecord checkAndGetNextRecord;
        int queueSize = writeTaskQueue.size();
        if (queueSize == 0) {
            return null;
        }
        AsyncWriteQueueRecord currentRecord = writeTaskQueue.poll();
        if (currentRecord == null || !canBeAggregated(currentRecord) || ((long) queueSize) == currentRecord.remaining() || (nextRecord = checkAndGetNextRecord(writeTaskQueue)) == null) {
            return currentRecord;
        }
        CompositeQueueRecord compositeQueueRecord = createCompositeQueueRecord(currentRecord);
        do {
            compositeQueueRecord.append(nextRecord);
            if (compositeQueueRecord.remaining() >= ((long) queueSize)) {
                break;
            }
            checkAndGetNextRecord = checkAndGetNextRecord(writeTaskQueue);
            nextRecord = checkAndGetNextRecord;
        } while (checkAndGetNextRecord != null);
        return compositeQueueRecord;
    }

    private static AsyncWriteQueueRecord checkAndGetNextRecord(TaskQueue<AsyncWriteQueueRecord> writeTaskQueue) {
        AsyncWriteQueueRecord nextRecord = writeTaskQueue.getQueue().poll();
        if (nextRecord == null) {
            return null;
        }
        if (canBeAggregated(nextRecord)) {
            return nextRecord;
        }
        writeTaskQueue.setCurrentElement(nextRecord);
        return null;
    }

    private static boolean canBeAggregated(AsyncWriteQueueRecord record) {
        return record.canBeAggregated();
    }

    private CompositeQueueRecord createCompositeQueueRecord(AsyncWriteQueueRecord currentRecord) {
        if (currentRecord instanceof CompositeQueueRecord) {
            return (CompositeQueueRecord) currentRecord;
        }
        Connection connection = currentRecord.getConnection();
        Attribute<CompositeQueueRecord> attribute = COMPOSITE_BUFFER_ATTR;
        CompositeQueueRecord compositeQueueRecord = attribute.get((AttributeStorage) connection);
        if (compositeQueueRecord == null) {
            compositeQueueRecord = CompositeQueueRecord.create(connection);
            attribute.set((AttributeStorage) connection, compositeQueueRecord);
        }
        compositeQueueRecord.append(currentRecord);
        return compositeQueueRecord;
    }

    public static final class CompositeQueueRecord extends AsyncWriteQueueRecord {
        /* access modifiers changed from: private */
        public final Deque<AsyncWriteQueueRecord> queue = new ArrayDeque(2);
        /* access modifiers changed from: private */
        public final ArrayList<BufferArray> savedBufferStates = new ArrayList<>(2);
        /* access modifiers changed from: private */
        public int size;

        public static CompositeQueueRecord create(Connection connection) {
            return new CompositeQueueRecord(connection);
        }

        public CompositeQueueRecord(Connection connection) {
            super(connection, (WritableMessage) null, (CompletionHandler) null, (Object) null, (PushBackHandler) null, false);
        }

        public void append(AsyncWriteQueueRecord queueRecord) {
            Logger access$300 = TCPNIOAsyncQueueWriter.LOGGER;
            Level level = Level.FINEST;
            if (access$300.isLoggable(level)) {
                TCPNIOAsyncQueueWriter.LOGGER.log(level, "CompositeQueueRecord.append. connection={0}, this={1}, comp-size={2}, elem-count={3}, queueRecord={4}, newrec-size={5}, isEmpty={6}", new Object[]{this.connection, this, Integer.valueOf(this.size), Integer.valueOf(this.queue.size()), queueRecord, Long.valueOf(queueRecord.remaining()), Boolean.valueOf(queueRecord.isUncountable())});
            }
            this.size = (int) (((long) this.size) + queueRecord.remaining());
            this.queue.add(queueRecord);
        }

        public boolean isUncountable() {
            return false;
        }

        public boolean isFinished() {
            return this.size == 0;
        }

        public boolean canBeAggregated() {
            return true;
        }

        public long remaining() {
            return (long) this.size;
        }

        public void notifyCompleteAndRecycle() {
        }

        public void notifyFailure(Throwable e) {
            while (true) {
                AsyncWriteQueueRecord poll = this.queue.poll();
                AsyncWriteQueueRecord record = poll;
                if (poll != null) {
                    record.notifyFailure(e);
                } else {
                    return;
                }
            }
        }

        public void recycle() {
        }
    }
}
