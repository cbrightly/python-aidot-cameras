package org.glassfish.grizzly.nio.transport;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.nio.DirectByteBufferRecord;

public class TCPNIOUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Logger LOGGER = TCPNIOTransport.LOGGER;

    /* JADX INFO: finally extract failed */
    public static int writeCompositeBuffer(TCPNIOConnection connection, CompositeBuffer buffer) {
        int i;
        int bufferSize = calcWriteBufferSize(connection, buffer.remaining());
        int oldPos = buffer.position();
        int oldLim = buffer.limit();
        buffer.limit(oldPos + bufferSize);
        SocketChannel socketChannel = (SocketChannel) connection.getChannel();
        BufferArray bufferArray = buffer.toBufferArray();
        DirectByteBufferRecord ioRecord = DirectByteBufferRecord.get();
        try {
            fill(bufferArray, bufferSize, ioRecord);
            ioRecord.finishBufferSlice();
            int arraySize = ioRecord.getArraySize();
            if (arraySize != 1) {
                i = flushByteBuffers(socketChannel, ioRecord.getArray(), 0, arraySize);
            } else {
                i = flushByteBuffer(socketChannel, ioRecord.getArray()[0]);
            }
            int written = i;
            Logger logger = LOGGER;
            Level level = Level.FINE;
            if (logger.isLoggable(level)) {
                logger.log(level, "TCPNIOConnection ({0}) (composite) write {1} bytes", new Object[]{connection, Integer.valueOf(written)});
            }
            ioRecord.release();
            bufferArray.restore();
            bufferArray.recycle();
            Buffers.setPositionLimit((Buffer) buffer, oldPos + written, oldLim);
            return written;
        } catch (Throwable th) {
            ioRecord.release();
            bufferArray.restore();
            bufferArray.recycle();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public static int writeSimpleBuffer(TCPNIOConnection connection, Buffer buffer) {
        int written;
        SocketChannel socketChannel = (SocketChannel) connection.getChannel();
        int oldPos = buffer.position();
        int oldLim = buffer.limit();
        if (buffer.isDirect()) {
            ByteBuffer directByteBuffer = buffer.toByteBuffer();
            int pos = directByteBuffer.position();
            try {
                written = flushByteBuffer(socketChannel, directByteBuffer);
            } finally {
                directByteBuffer.position(pos);
            }
        } else {
            int bufferSize = calcWriteBufferSize(connection, buffer.remaining());
            buffer.limit(oldPos + bufferSize);
            DirectByteBufferRecord ioRecord = DirectByteBufferRecord.get();
            ByteBuffer directByteBuffer2 = ioRecord.allocate(bufferSize);
            fill(buffer, bufferSize, directByteBuffer2);
            try {
                int written2 = flushByteBuffer(socketChannel, directByteBuffer2);
                ioRecord.release();
                written = written2;
            } catch (Throwable th) {
                ioRecord.release();
                throw th;
            }
        }
        Buffers.setPositionLimit(buffer, oldPos + written, oldLim);
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "TCPNIOConnection ({0}) (plain) write {1} bytes", new Object[]{connection, Integer.valueOf(written)});
        }
        return written;
    }

    public static int flushByteBuffer(SocketChannel channel, ByteBuffer byteBuffer) {
        return channel.write(byteBuffer);
    }

    public static int flushByteBuffers(SocketChannel channel, ByteBuffer[] byteBuffer, int firstBufferOffest, int numberOfBuffers) {
        return (int) channel.write(byteBuffer, firstBufferOffest, numberOfBuffers);
    }

    private static void fill(Buffer src, int size, ByteBuffer dstByteBuffer) {
        dstByteBuffer.limit(size);
        int oldPos = src.position();
        src.get(dstByteBuffer);
        dstByteBuffer.position(0);
        src.position(oldPos);
    }

    static void fill(BufferArray bufferArray, int totalBufferSize, DirectByteBufferRecord ioRecord) {
        Buffer[] buffers = (Buffer[]) bufferArray.getArray();
        int size = bufferArray.size();
        int totalRemaining = totalBufferSize;
        int i = 0;
        while (i < size) {
            Buffer buffer = buffers[i];
            if (!buffer.isComposite()) {
                int bufferSize = buffer.remaining();
                if (bufferSize != 0) {
                    if (buffer.isDirect()) {
                        ioRecord.finishBufferSlice();
                        ioRecord.putToArray(buffer.toByteBuffer());
                    } else {
                        ByteBuffer currentDirectBufferSlice = ioRecord.getDirectBufferSlice();
                        if (currentDirectBufferSlice == null) {
                            if (ioRecord.getDirectBuffer() == null) {
                                ioRecord.allocate(totalRemaining);
                            }
                            currentDirectBufferSlice = ioRecord.sliceBuffer();
                        }
                        int oldLim = currentDirectBufferSlice.limit();
                        currentDirectBufferSlice.limit(currentDirectBufferSlice.position() + bufferSize);
                        buffer.get(currentDirectBufferSlice);
                        currentDirectBufferSlice.limit(oldLim);
                    }
                    totalRemaining -= bufferSize;
                }
                i++;
            } else {
                throw new AssertionError();
            }
        }
    }

    private static int calcWriteBufferSize(TCPNIOConnection connection, int bufferSize) {
        return Math.min(TCPNIOTransport.MAX_SEND_BUFFER_SIZE, Math.min(bufferSize, (connection.getWriteBufferSize() * 3) / 2));
    }

    /* JADX WARNING: type inference failed for: r2v5, types: [org.glassfish.grizzly.Buffer] */
    /* JADX WARNING: type inference failed for: r2v7, types: [org.glassfish.grizzly.Buffer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.glassfish.grizzly.Buffer allocateAndReadBuffer(org.glassfish.grizzly.nio.transport.TCPNIOConnection r9) {
        /*
            org.glassfish.grizzly.memory.MemoryManager r0 = r9.getMemoryManager()
            r1 = 0
            r2 = 0
            int r3 = org.glassfish.grizzly.nio.transport.TCPNIOTransport.MAX_RECEIVE_BUFFER_SIZE     // Catch:{ all -> 0x0045 }
            int r4 = r9.getReadBufferSize()     // Catch:{ all -> 0x0045 }
            int r3 = java.lang.Math.min(r3, r4)     // Catch:{ all -> 0x0045 }
            boolean r4 = r0.willAllocateDirect(r3)     // Catch:{ all -> 0x0045 }
            if (r4 != 0) goto L_0x003a
            org.glassfish.grizzly.nio.DirectByteBufferRecord r4 = org.glassfish.grizzly.nio.DirectByteBufferRecord.get()     // Catch:{ all -> 0x0045 }
            java.nio.ByteBuffer r5 = r4.allocate(r3)     // Catch:{ all -> 0x0045 }
            int r6 = readSimpleByteBuffer(r9, r5)     // Catch:{ all -> 0x0034 }
            if (r6 <= 0) goto L_0x002f
            r5.flip()     // Catch:{ all -> 0x0034 }
            org.glassfish.grizzly.Buffer r7 = r0.allocate(r6)     // Catch:{ all -> 0x0034 }
            r2 = r7
            r2.put((java.nio.ByteBuffer) r5)     // Catch:{ all -> 0x0034 }
        L_0x002f:
            r4.release()     // Catch:{ all -> 0x0045 }
            goto L_0x0044
        L_0x0034:
            r6 = move-exception
            r4.release()     // Catch:{ all -> 0x0045 }
            throw r6     // Catch:{ all -> 0x0045 }
        L_0x003a:
            org.glassfish.grizzly.Buffer r4 = r0.allocateAtLeast(r3)     // Catch:{ all -> 0x0045 }
            r2 = r4
            int r4 = readBuffer(r9, r2)     // Catch:{ all -> 0x0045 }
            r6 = r4
        L_0x0044:
            goto L_0x0048
        L_0x0045:
            r3 = move-exception
            r1 = r3
            r6 = -1
        L_0x0048:
            r3 = 1
            if (r6 <= 0) goto L_0x0052
            r2.position(r6)
            r2.allowBufferDispose(r3)
            goto L_0x0068
        L_0x0052:
            if (r2 == 0) goto L_0x0057
            r2.dispose()
        L_0x0057:
            if (r6 >= 0) goto L_0x0066
            if (r1 == 0) goto L_0x0060
            java.io.IOException r3 = org.glassfish.grizzly.utils.Exceptions.makeIOException(r1)
            goto L_0x0065
        L_0x0060:
            java.io.EOFException r3 = new java.io.EOFException
            r3.<init>()
        L_0x0065:
            throw r3
        L_0x0066:
            org.glassfish.grizzly.Buffer r2 = org.glassfish.grizzly.memory.Buffers.EMPTY_BUFFER
        L_0x0068:
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            boolean r7 = r4.isLoggable(r5)
            if (r7 == 0) goto L_0x0083
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r8 = 0
            r7[r8] = r9
            java.lang.Integer r8 = java.lang.Integer.valueOf(r6)
            r7[r3] = r8
            java.lang.String r3 = "TCPNIOConnection ({0}) (allocated) read {1} bytes"
            r4.log(r5, r3, r7)
        L_0x0083:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.nio.transport.TCPNIOUtils.allocateAndReadBuffer(org.glassfish.grizzly.nio.transport.TCPNIOConnection):org.glassfish.grizzly.Buffer");
    }

    public static int readBuffer(TCPNIOConnection connection, Buffer buffer) {
        return buffer.isComposite() ? readCompositeBuffer(connection, (CompositeBuffer) buffer) : readSimpleBuffer(connection, buffer);
    }

    public static int readCompositeBuffer(TCPNIOConnection connection, CompositeBuffer buffer) {
        int oldPos = buffer.position();
        ByteBufferArray array = buffer.toByteBufferArray();
        int read = (int) ((SocketChannel) connection.getChannel()).read((ByteBuffer[]) array.getArray(), 0, array.size());
        array.restore();
        array.recycle();
        if (read > 0) {
            buffer.position(oldPos + read);
        }
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "TCPNIOConnection ({0}) (nonallocated, composite) read {1} bytes", new Object[]{connection, Integer.valueOf(read)});
        }
        return read;
    }

    public static int readSimpleBuffer(TCPNIOConnection connection, Buffer buffer) {
        int oldPos = buffer.position();
        ByteBuffer byteBuffer = buffer.toByteBuffer();
        int bbOldPos = byteBuffer.position();
        int read = ((SocketChannel) connection.getChannel()).read(byteBuffer);
        if (read > 0) {
            byteBuffer.position(bbOldPos);
            buffer.position(oldPos + read);
        }
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "TCPNIOConnection ({0}) (nonallocated, simple) read {1} bytes", new Object[]{connection, Integer.valueOf(read)});
        }
        return read;
    }

    private static int readSimpleByteBuffer(TCPNIOConnection tcpConnection, ByteBuffer byteBuffer) {
        return ((SocketChannel) tcpConnection.getChannel()).read(byteBuffer);
    }
}
