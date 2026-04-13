package org.glassfish.grizzly.nio.tmpselectors;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.AbstractWriter;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.nio.NIOConnection;

public abstract class TemporarySelectorWriter extends AbstractWriter<SocketAddress> {
    protected final TemporarySelectorsEnabledTransport transport;

    /* access modifiers changed from: protected */
    public abstract long writeNow0(NIOConnection nIOConnection, SocketAddress socketAddress, WritableMessage writableMessage, WriteResult<WritableMessage, SocketAddress> writeResult);

    public TemporarySelectorWriter(TemporarySelectorsEnabledTransport transport2) {
        this.transport = transport2;
    }

    public void write(Connection<SocketAddress> connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, MessageCloner<WritableMessage> messageCloner) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        write(connection, dstAddress, message, completionHandler, (PushBackHandler) null, connection.getWriteTimeout(timeUnit), timeUnit);
    }

    @Deprecated
    public void write(Connection<SocketAddress> connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, PushBackHandler pushBackHandler) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        write(connection, dstAddress, message, completionHandler, pushBackHandler, connection.getWriteTimeout(timeUnit), timeUnit);
    }

    public void write(Connection<SocketAddress> connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, long timeout, TimeUnit timeunit) {
        write(connection, dstAddress, message, completionHandler, (PushBackHandler) null, timeout, timeunit);
    }

    public void write(Connection<SocketAddress> connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler, PushBackHandler pushBackHandler, long timeout, TimeUnit timeunit) {
        Connection<SocketAddress> connection2 = connection;
        WritableMessage writableMessage = message;
        CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler2 = completionHandler;
        PushBackHandler pushBackHandler2 = pushBackHandler;
        if (writableMessage == null) {
            failure(new IllegalStateException("Message cannot be null"), completionHandler2);
            return;
        }
        if (connection2 == null) {
            SocketAddress socketAddress = dstAddress;
        } else if (!(connection2 instanceof NIOConnection)) {
            SocketAddress socketAddress2 = dstAddress;
        } else {
            WriteResult<WritableMessage, SocketAddress> writeResult = WriteResult.create(connection2, writableMessage, dstAddress, 0);
            try {
                write0((NIOConnection) connection2, dstAddress, message, writeResult, timeout, timeunit);
                if (pushBackHandler2 != null) {
                    pushBackHandler2.onAccept(connection2, writableMessage);
                }
                if (completionHandler2 != null) {
                    completionHandler2.completed(writeResult);
                }
                message.release();
                return;
            } catch (IOException e) {
                failure(e, completionHandler2);
                return;
            }
        }
        failure(new IllegalStateException("Connection should be NIOConnection and cannot be null"), completionHandler2);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0074, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0075, code lost:
        r11 = r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long write0(org.glassfish.grizzly.nio.NIOConnection r18, java.net.SocketAddress r19, org.glassfish.grizzly.asyncqueue.WritableMessage r20, org.glassfish.grizzly.WriteResult<org.glassfish.grizzly.asyncqueue.WritableMessage, java.net.SocketAddress> r21, long r22, java.util.concurrent.TimeUnit r24) {
        /*
            r17 = this;
            r1 = r17
            java.nio.channels.SelectableChannel r2 = r18.getChannel()
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS
            r3 = r22
            r5 = r24
            long r6 = r0.convert(r3, r5)
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            monitor-enter(r18)     // Catch:{ all -> 0x0077 }
            r12 = r11
        L_0x0016:
            boolean r0 = r20.hasRemaining()     // Catch:{ all -> 0x0071 }
            if (r0 == 0) goto L_0x0064
            long r13 = r17.writeNow0(r18, r19, r20, r21)     // Catch:{ all -> 0x0071 }
            r15 = 0
            int r0 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r0 <= 0) goto L_0x002d
            r0 = 0
            long r10 = (long) r12     // Catch:{ all -> 0x0071 }
            long r10 = r10 + r13
            int r10 = (int) r10     // Catch:{ all -> 0x0071 }
            r12 = r10
            r10 = r0
            goto L_0x0063
        L_0x002d:
            int r10 = r10 + 1
            if (r9 != 0) goto L_0x004a
            org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorsEnabledTransport r0 = r1.transport     // Catch:{ all -> 0x0071 }
            org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO r0 = r0.getTemporarySelectorIO()     // Catch:{ all -> 0x0071 }
            org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorPool r0 = r0.getSelectorPool()     // Catch:{ all -> 0x0071 }
            java.nio.channels.Selector r0 = r0.poll()     // Catch:{ all -> 0x0071 }
            r9 = r0
            if (r9 != 0) goto L_0x0043
            goto L_0x0016
        L_0x0043:
            r0 = 4
            java.nio.channels.SelectionKey r0 = r2.register(r9, r0)     // Catch:{ all -> 0x0071 }
            r8 = r0
            goto L_0x0051
        L_0x004a:
            java.util.Set r0 = r9.selectedKeys()     // Catch:{ all -> 0x0071 }
            r0.clear()     // Catch:{ all -> 0x0071 }
        L_0x0051:
            int r0 = r9.select(r6)     // Catch:{ all -> 0x0071 }
            if (r0 != 0) goto L_0x0063
            r0 = 2
            if (r10 > r0) goto L_0x005b
            goto L_0x0063
        L_0x005b:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0071 }
            java.lang.String r11 = "Client disconnected"
            r0.<init>(r11)     // Catch:{ all -> 0x0071 }
            throw r0     // Catch:{ all -> 0x0071 }
        L_0x0063:
            goto L_0x0016
        L_0x0064:
            monitor-exit(r18)     // Catch:{ all -> 0x0071 }
            org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorsEnabledTransport r0 = r1.transport
            org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO r0 = r0.getTemporarySelectorIO()
            r0.recycleTemporaryArtifacts(r9, r8)
            long r13 = (long) r12
            return r13
        L_0x0071:
            r0 = move-exception
            monitor-exit(r18)     // Catch:{ all -> 0x0071 }
            throw r0     // Catch:{ all -> 0x0074 }
        L_0x0074:
            r0 = move-exception
            r11 = r12
            goto L_0x0078
        L_0x0077:
            r0 = move-exception
        L_0x0078:
            org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorsEnabledTransport r12 = r1.transport
            org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO r12 = r12.getTemporarySelectorIO()
            r12.recycleTemporaryArtifacts(r9, r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorWriter.write0(org.glassfish.grizzly.nio.NIOConnection, java.net.SocketAddress, org.glassfish.grizzly.asyncqueue.WritableMessage, org.glassfish.grizzly.WriteResult, long, java.util.concurrent.TimeUnit):long");
    }

    public TemporarySelectorsEnabledTransport getTransport() {
        return this.transport;
    }

    private static void failure(Throwable failure, CompletionHandler<WriteResult<WritableMessage, SocketAddress>> completionHandler) {
        if (completionHandler != null) {
            completionHandler.failed(failure);
        }
    }

    public boolean canWrite(Connection connection) {
        return true;
    }

    public void notifyWritePossible(Connection connection, WriteHandler writeHandler) {
        try {
            writeHandler.onWritePossible();
        } catch (Throwable t) {
            writeHandler.onError(t);
        }
    }
}
