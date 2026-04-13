package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Extension;
import jakarta.websocket.SendHandler;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.glassfish.tyrus.core.frame.BinaryFrame;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.frame.PingFrame;
import org.glassfish.tyrus.core.frame.PongFrame;
import org.glassfish.tyrus.core.frame.TextFrame;
import org.glassfish.tyrus.core.frame.TyrusFrame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.MessageEventListener;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.WriterInfo;

public class TyrusWebSocket {
    private static final WriterInfo PING_INFO;
    private static final WriterInfo PONG_INFO;
    private final EnumSet<State> connected = EnumSet.range(State.CONNECTED, State.CLOSING);
    private final TyrusEndpointWrapper endpointWrapper;
    private final Lock lock = new ReentrantLock();
    private volatile MessageEventListener messageEventListener = MessageEventListener.NO_OP;
    private final CountDownLatch onConnectLatch = new CountDownLatch(1);
    private final ProtocolHandler protocolHandler;
    private final AtomicReference<State> state = new AtomicReference<>(State.NEW);

    public enum State {
        NEW,
        CONNECTED,
        CLOSING,
        CLOSED
    }

    static {
        WriterInfo.MessageType messageType = WriterInfo.MessageType.PING;
        WriterInfo.RemoteEndpointType remoteEndpointType = WriterInfo.RemoteEndpointType.SUPER;
        PING_INFO = new WriterInfo(messageType, remoteEndpointType);
        PONG_INFO = new WriterInfo(WriterInfo.MessageType.PONG, remoteEndpointType);
    }

    public TyrusWebSocket(ProtocolHandler protocolHandler2, TyrusEndpointWrapper endpointWrapper2) {
        this.protocolHandler = protocolHandler2;
        this.endpointWrapper = endpointWrapper2;
        protocolHandler2.setWebSocket(this);
    }

    public void setWriteTimeout(long timeoutMs) {
    }

    public boolean isConnected() {
        return this.connected.contains(this.state.get());
    }

    public void onClose(CloseFrame frame) {
        if (this.lock.tryLock()) {
            try {
                CloseReason closeReason = frame.getCloseReason();
                TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
                if (tyrusEndpointWrapper != null) {
                    tyrusEndpointWrapper.onClose(this, closeReason);
                }
                if (this.state.compareAndSet(State.CONNECTED, State.CLOSING)) {
                    this.protocolHandler.close(closeReason.a().getCode(), closeReason.b());
                } else {
                    this.state.set(State.CLOSED);
                    this.protocolHandler.doClose();
                }
            } finally {
                this.lock.unlock();
            }
        }
    }

    public void onConnect(UpgradeRequest upgradeRequest, String subProtocol, List<Extension> extensions, String connectionId, DebugContext debugContext) {
        this.state.set(State.CONNECTED);
        TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
        if (tyrusEndpointWrapper != null) {
            tyrusEndpointWrapper.onConnect(this, upgradeRequest, subProtocol, extensions, connectionId, debugContext);
        }
        this.onConnectLatch.countDown();
    }

    public void onFragment(BinaryFrame frame, boolean last) {
        awaitOnConnect();
        TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
        if (tyrusEndpointWrapper != null) {
            tyrusEndpointWrapper.onPartialMessage(this, ByteBuffer.wrap(frame.getPayloadData()), last);
            this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
        }
    }

    public void onFragment(TextFrame frame, boolean last) {
        awaitOnConnect();
        TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
        if (tyrusEndpointWrapper != null) {
            tyrusEndpointWrapper.onPartialMessage(this, frame.getTextPayload(), last);
            this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
        }
    }

    public void onMessage(BinaryFrame frame) {
        awaitOnConnect();
        TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
        if (tyrusEndpointWrapper != null) {
            tyrusEndpointWrapper.onMessage(this, ByteBuffer.wrap(frame.getPayloadData()));
            this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
        }
    }

    public void onMessage(TextFrame frame) {
        awaitOnConnect();
        TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
        if (tyrusEndpointWrapper != null) {
            tyrusEndpointWrapper.onMessage(this, frame.getTextPayload());
            this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
        }
    }

    public void onPing(PingFrame frame) {
        awaitOnConnect();
        TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
        if (tyrusEndpointWrapper != null) {
            tyrusEndpointWrapper.onPing(this, ByteBuffer.wrap(frame.getPayloadData()));
            this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
        }
    }

    public void onPong(PongFrame frame) {
        awaitOnConnect();
        TyrusEndpointWrapper tyrusEndpointWrapper = this.endpointWrapper;
        if (tyrusEndpointWrapper != null) {
            tyrusEndpointWrapper.onPong(this, ByteBuffer.wrap(frame.getPayloadData()));
            this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
        }
    }

    public void close() {
        close(CloseReason.a.NORMAL_CLOSURE.getCode(), (String) null);
    }

    public void close(int code, String reason) {
        if (this.state.compareAndSet(State.CONNECTED, State.CLOSING)) {
            this.protocolHandler.close(code, reason);
        }
    }

    public void close(CloseReason closeReason) {
        close(closeReason.a().getCode(), closeReason.b());
    }

    @Deprecated
    public Future<Frame> sendBinary(byte[] data) {
        checkConnectedState();
        return this.protocolHandler.send(data);
    }

    public Future<Frame> sendBinary(byte[] data, WriterInfo writerInfo) {
        checkConnectedState();
        return this.protocolHandler.send(data, writerInfo);
    }

    @Deprecated
    public void sendBinary(byte[] data, SendHandler handler) {
        checkConnectedState();
        this.protocolHandler.send(data, handler);
    }

    public void sendBinary(byte[] data, SendHandler handler, WriterInfo writerInfo) {
        checkConnectedState();
        this.protocolHandler.send(data, handler, writerInfo);
    }

    @Deprecated
    public Future<Frame> sendText(String data) {
        checkConnectedState();
        return this.protocolHandler.send(data);
    }

    public Future<Frame> sendText(String data, WriterInfo writerInfo) {
        checkConnectedState();
        return this.protocolHandler.send(data, writerInfo);
    }

    @Deprecated
    public void sendText(String data, SendHandler handler) {
        checkConnectedState();
        this.protocolHandler.send(data, handler);
    }

    public void sendText(String data, SendHandler handler, WriterInfo writerInfo) {
        checkConnectedState();
        this.protocolHandler.send(data, handler, writerInfo);
    }

    public Future<Frame> sendRawFrame(ByteBuffer data) {
        checkConnectedState();
        return this.protocolHandler.sendRawFrame(data);
    }

    public Future<Frame> sendPing(byte[] data) {
        return send(new PingFrame(data), PING_INFO);
    }

    public Future<Frame> sendPong(byte[] data) {
        return send(new PongFrame(data), PONG_INFO);
    }

    private void awaitOnConnect() {
        try {
            this.onConnectLatch.await();
        } catch (InterruptedException e) {
        }
    }

    private Future<Frame> send(TyrusFrame frame, WriterInfo writerInfo) {
        checkConnectedState();
        return this.protocolHandler.send(frame, writerInfo);
    }

    @Deprecated
    public Future<Frame> sendText(String fragment, boolean last) {
        checkConnectedState();
        return this.protocolHandler.stream(last, fragment);
    }

    public Future<Frame> sendText(String fragment, boolean last, WriterInfo writerInfo) {
        checkConnectedState();
        return this.protocolHandler.stream(last, fragment, writerInfo);
    }

    @Deprecated
    public Future<Frame> sendBinary(byte[] bytes, boolean last) {
        return sendBinary(bytes, 0, bytes.length, last);
    }

    public Future<Frame> sendBinary(byte[] bytes, boolean last, WriterInfo writerInfo) {
        return sendBinary(bytes, 0, bytes.length, last, writerInfo);
    }

    @Deprecated
    public Future<Frame> sendBinary(byte[] bytes, int off, int len, boolean last) {
        checkConnectedState();
        return this.protocolHandler.stream(last, bytes, off, len);
    }

    public Future<Frame> sendBinary(byte[] bytes, int off, int len, boolean last, WriterInfo writerInfo) {
        checkConnectedState();
        return this.protocolHandler.stream(last, bytes, off, len, writerInfo);
    }

    /* access modifiers changed from: package-private */
    public ProtocolHandler getProtocolHandler() {
        return this.protocolHandler;
    }

    /* access modifiers changed from: package-private */
    public void setMessageEventListener(MessageEventListener messageEventListener2) {
        this.messageEventListener = messageEventListener2;
        this.protocolHandler.setMessageEventListener(messageEventListener2);
    }

    /* access modifiers changed from: package-private */
    public MessageEventListener getMessageEventListener() {
        return this.messageEventListener;
    }

    private void checkConnectedState() {
        if (!isConnected()) {
            throw new RuntimeException(LocalizationMessages.SOCKET_NOT_CONNECTED());
        }
    }
}
