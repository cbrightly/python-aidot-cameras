package org.glassfish.tyrus.core;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Extension;
import jakarta.websocket.SendHandler;
import jakarta.websocket.i;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import meshsdk.ctrl.GroupCtrlAdapter;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.BinaryFrame;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.frame.TextFrame;
import org.glassfish.tyrus.core.frame.TyrusFrame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.MessageEventListener;
import org.glassfish.tyrus.spi.CompletionHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.glassfish.tyrus.spi.Writer;
import org.glassfish.tyrus.spi.WriterInfo;

public final class ProtocolHandler {
    private static final WriterInfo CLOSE = new WriterInfo(WriterInfo.MessageType.CLOSE, WriterInfo.RemoteEndpointType.SUPER);
    private static final Logger LOGGER = Logger.getLogger(ProtocolHandler.class.getName());
    public static final int MASK_SIZE = 4;
    private static final WriterInfo NULL_INFO = new WriterInfo((WriterInfo.MessageType) null, (WriterInfo.RemoteEndpointType) null);
    private static final int SEND_TIMEOUT = 3000;
    private final boolean client;
    private volatile ExtendedExtension.ExtensionContext extensionContext;
    private volatile List<Extension> extensions;
    private volatile boolean hasExtensions = false;
    private final Condition idleCondition;
    private volatile byte inFragmentedType;
    private final Lock lock;
    private final MaskingKeyGenerator maskingKeyGenerator;
    private volatile MessageEventListener messageEventListener = MessageEventListener.NO_OP;
    private volatile byte outFragmentedType;
    private final ParsingState parsingState = new ParsingState();
    private volatile boolean processingFragment;
    private volatile ByteBuffer remainder = null;
    private volatile SendingFragmentState sendingFragment = SendingFragmentState.IDLE;
    private volatile String subProtocol = null;
    private volatile TyrusWebSocket webSocket;
    private volatile Writer writer;

    public enum SendingFragmentState {
        IDLE,
        SENDING_TEXT,
        SENDING_BINARY
    }

    ProtocolHandler(boolean client2, MaskingKeyGenerator maskingKeyGenerator2) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.idleCondition = reentrantLock.newCondition();
        this.client = client2;
        if (!client2) {
            this.maskingKeyGenerator = null;
        } else if (maskingKeyGenerator2 != null) {
            this.maskingKeyGenerator = maskingKeyGenerator2;
        } else {
            this.maskingKeyGenerator = new MaskingKeyGenerator() {
                private final SecureRandom secureRandom = new SecureRandom();

                public int nextInt() {
                    return this.secureRandom.nextInt();
                }
            };
        }
    }

    public void setWriter(Writer writer2) {
        this.writer = writer2;
    }

    public boolean hasExtensions() {
        return this.hasExtensions;
    }

    public Handshake handshake(TyrusEndpointWrapper endpointWrapper, UpgradeRequest request, UpgradeResponse response, ExtendedExtension.ExtensionContext extensionContext2) {
        Handshake handshake = Handshake.createServerHandshake(request, extensionContext2);
        this.extensions = handshake.respond(request, response, endpointWrapper);
        this.subProtocol = response.getFirstHeaderValue("Sec-WebSocket-Protocol");
        this.extensionContext = extensionContext2;
        this.hasExtensions = this.extensions != null && this.extensions.size() > 0;
        return handshake;
    }

    /* access modifiers changed from: package-private */
    public List<Extension> getExtensions() {
        return this.extensions;
    }

    public void setExtensions(List<Extension> extensions2) {
        this.extensions = extensions2;
        this.hasExtensions = extensions2 != null && extensions2.size() > 0;
    }

    /* access modifiers changed from: package-private */
    public String getSubProtocol() {
        return this.subProtocol;
    }

    public void setWebSocket(TyrusWebSocket webSocket2) {
        this.webSocket = webSocket2;
    }

    public void setExtensionContext(ExtendedExtension.ExtensionContext extensionContext2) {
        this.extensionContext = extensionContext2;
    }

    public void setMessageEventListener(MessageEventListener messageEventListener2) {
        this.messageEventListener = messageEventListener2;
    }

    /* access modifiers changed from: package-private */
    public final Future<Frame> send(TyrusFrame frame, WriterInfo writerInfo) {
        return send(frame, (CompletionHandler<Frame>) null, writerInfo, (Boolean) true);
    }

    private Future<Frame> send(TyrusFrame frame, CompletionHandler<Frame> completionHandler, WriterInfo writerInfo, Boolean useTimeout) {
        return write(frame, completionHandler, writerInfo, useTimeout.booleanValue());
    }

    private Future<Frame> send(ByteBuffer frame, CompletionHandler<Frame> completionHandler, WriterInfo writerInfo, Boolean useTimeout) {
        return write(frame, completionHandler, writerInfo, useTimeout.booleanValue());
    }

    @Deprecated
    public Future<Frame> send(byte[] data) {
        return send(data, NULL_INFO);
    }

    public Future<Frame> send(byte[] data, WriterInfo writerInfo) {
        this.lock.lock();
        try {
            checkSendingFragment();
            return send((TyrusFrame) new BinaryFrame(data, false, true), (CompletionHandler<Frame>) null, writerInfo, (Boolean) true);
        } finally {
            this.lock.unlock();
        }
    }

    @Deprecated
    public void send(byte[] data, SendHandler handler) {
        send(data, handler, NULL_INFO);
    }

    public void send(byte[] data, final SendHandler handler, WriterInfo writerInfo) {
        this.lock.lock();
        try {
            checkSendingFragment();
            send((TyrusFrame) new BinaryFrame(data, false, true), (CompletionHandler<Frame>) new CompletionHandler<Frame>() {
                public void failed(Throwable throwable) {
                    handler.a(new i(throwable));
                }

                public void completed(Frame result) {
                    handler.a(new i());
                }
            }, writerInfo, (Boolean) true);
        } finally {
            this.lock.unlock();
        }
    }

    @Deprecated
    public Future<Frame> send(String data) {
        return send(data, NULL_INFO);
    }

    public Future<Frame> send(String data, WriterInfo writerInfo) {
        this.lock.lock();
        try {
            checkSendingFragment();
            return send((TyrusFrame) new TextFrame(data, false, true), writerInfo);
        } finally {
            this.lock.unlock();
        }
    }

    @Deprecated
    public void send(String data, SendHandler handler) {
        send(data, handler, NULL_INFO);
    }

    public void send(String data, final SendHandler handler, WriterInfo writerInfo) {
        this.lock.lock();
        try {
            checkSendingFragment();
            send((TyrusFrame) new TextFrame(data, false, true), (CompletionHandler<Frame>) new CompletionHandler<Frame>() {
                public void failed(Throwable throwable) {
                    handler.a(new i(throwable));
                }

                public void completed(Frame result) {
                    handler.a(new i());
                }
            }, writerInfo, (Boolean) true);
        } finally {
            this.lock.unlock();
        }
    }

    public Future<Frame> sendRawFrame(ByteBuffer data) {
        this.lock.lock();
        try {
            checkSendingFragment();
            return send(data, (CompletionHandler<Frame>) null, new WriterInfo(WriterInfo.MessageType.BINARY, WriterInfo.RemoteEndpointType.BROADCAST), (Boolean) true);
        } finally {
            this.lock.unlock();
        }
    }

    private void checkSendingFragment() {
        long timeout = System.currentTimeMillis() + GroupCtrlAdapter.RETRY_TIMEOUT;
        while (this.sendingFragment != SendingFragmentState.IDLE) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < timeout) {
                try {
                    if (!this.idleCondition.await(timeout - currentTimeMillis, TimeUnit.MILLISECONDS)) {
                        throw new IllegalStateException();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException(e);
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Deprecated
    public Future<Frame> stream(boolean last, byte[] bytes, int off, int len) {
        return stream(last, bytes, off, len, NULL_INFO);
    }

    public Future<Frame> stream(boolean last, byte[] bytes, int off, int len, WriterInfo writerInfo) {
        SendingFragmentState sendingFragmentState;
        this.lock.lock();
        try {
            switch (AnonymousClass4.$SwitchMap$org$glassfish$tyrus$core$ProtocolHandler$SendingFragmentState[this.sendingFragment.ordinal()]) {
                case 1:
                    Future<Frame> frameFuture = send((TyrusFrame) new BinaryFrame(Arrays.copyOfRange(bytes, off, off + len), true, last), writerInfo);
                    if (last) {
                        this.sendingFragment = SendingFragmentState.IDLE;
                        this.idleCondition.signalAll();
                    }
                    this.lock.unlock();
                    return frameFuture;
                case 2:
                    checkSendingFragment();
                    this.sendingFragment = last ? SendingFragmentState.IDLE : SendingFragmentState.SENDING_BINARY;
                    Future<Frame> send = send((TyrusFrame) new BinaryFrame(Arrays.copyOfRange(bytes, off, off + len), false, last), writerInfo);
                    this.lock.unlock();
                    return send;
                default:
                    if (last) {
                        sendingFragmentState = SendingFragmentState.IDLE;
                    } else {
                        sendingFragmentState = SendingFragmentState.SENDING_BINARY;
                    }
                    this.sendingFragment = sendingFragmentState;
                    return send((TyrusFrame) new BinaryFrame(Arrays.copyOfRange(bytes, off, off + len), false, last), writerInfo);
            }
        } finally {
            this.lock.unlock();
        }
        this.lock.unlock();
    }

    /* renamed from: org.glassfish.tyrus.core.ProtocolHandler$4  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$core$ProtocolHandler$SendingFragmentState;

        static {
            int[] iArr = new int[SendingFragmentState.values().length];
            $SwitchMap$org$glassfish$tyrus$core$ProtocolHandler$SendingFragmentState = iArr;
            try {
                iArr[SendingFragmentState.SENDING_BINARY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$ProtocolHandler$SendingFragmentState[SendingFragmentState.SENDING_TEXT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @Deprecated
    public Future<Frame> stream(boolean last, String fragment) {
        return stream(last, fragment, NULL_INFO);
    }

    public Future<Frame> stream(boolean last, String fragment, WriterInfo writerInfo) {
        SendingFragmentState sendingFragmentState;
        this.lock.lock();
        try {
            switch (AnonymousClass4.$SwitchMap$org$glassfish$tyrus$core$ProtocolHandler$SendingFragmentState[this.sendingFragment.ordinal()]) {
                case 1:
                    checkSendingFragment();
                    this.sendingFragment = last ? SendingFragmentState.IDLE : SendingFragmentState.SENDING_TEXT;
                    Future<Frame> send = send((TyrusFrame) new TextFrame(fragment, false, last), writerInfo);
                    this.lock.unlock();
                    return send;
                case 2:
                    Future<Frame> frameFuture = send((TyrusFrame) new TextFrame(fragment, true, last), writerInfo);
                    if (last) {
                        this.sendingFragment = SendingFragmentState.IDLE;
                        this.idleCondition.signalAll();
                    }
                    this.lock.unlock();
                    return frameFuture;
                default:
                    if (last) {
                        sendingFragmentState = SendingFragmentState.IDLE;
                    } else {
                        sendingFragmentState = SendingFragmentState.SENDING_TEXT;
                    }
                    this.sendingFragment = sendingFragmentState;
                    return send((TyrusFrame) new TextFrame(fragment, false, last), writerInfo);
            }
        } finally {
            this.lock.unlock();
        }
        this.lock.unlock();
    }

    public synchronized Future<Frame> close(int code, String reason) {
        CloseFrame outgoingCloseFrame;
        Future<Frame> send;
        CloseReason closeReason = new CloseReason(CloseReason.a.getCloseCode(code), reason);
        if (!(code == CloseReason.a.NO_STATUS_CODE.getCode() || code == CloseReason.a.CLOSED_ABNORMALLY.getCode() || code == CloseReason.a.TLS_HANDSHAKE_FAILURE.getCode())) {
            if (this.client) {
                if (code != CloseReason.a.SERVICE_RESTART.getCode()) {
                    if (code == CloseReason.a.TRY_AGAIN_LATER.getCode()) {
                    }
                }
            }
            outgoingCloseFrame = new CloseFrame(closeReason);
            send = send((TyrusFrame) outgoingCloseFrame, (CompletionHandler<Frame>) null, CLOSE, (Boolean) false);
            this.webSocket.onClose(new CloseFrame(closeReason));
        }
        outgoingCloseFrame = new CloseFrame(new CloseReason(CloseReason.a.NORMAL_CLOSURE, reason));
        send = send((TyrusFrame) outgoingCloseFrame, (CompletionHandler<Frame>) null, CLOSE, (Boolean) false);
        this.webSocket.onClose(new CloseFrame(closeReason));
        return send;
    }

    private Future<Frame> write(TyrusFrame frame, CompletionHandler<Frame> completionHandler, WriterInfo data, boolean useTimeout) {
        Writer localWriter = this.writer;
        TyrusFuture<Frame> future = new TyrusFuture<>();
        if (localWriter != null) {
            localWriter.write(frame(frame), new CompletionHandlerWrapper(completionHandler, future, frame), data);
            this.messageEventListener.onFrameSent(frame.getFrameType(), frame.getPayloadLength());
            return future;
        }
        throw new IllegalStateException(LocalizationMessages.CONNECTION_NULL());
    }

    private Future<Frame> write(ByteBuffer frame, CompletionHandler<Frame> completionHandler, WriterInfo data, boolean useTimeout) {
        Writer localWriter = this.writer;
        TyrusFuture<Frame> future = new TyrusFuture<>();
        if (localWriter != null) {
            localWriter.write(frame, new CompletionHandlerWrapper(completionHandler, future, (Frame) null), data);
            return future;
        }
        throw new IllegalStateException(LocalizationMessages.CONNECTION_NULL());
    }

    private long decodeLength(byte[] bytes) {
        return Utils.toLong(bytes, 0, bytes.length);
    }

    private byte[] encodeLength(long length) {
        if (length <= 125) {
            return new byte[]{(byte) ((int) length)};
        }
        byte[] lengthBytes = Utils.toArray(length);
        if (length <= 65535) {
            byte[] lengthBytes2 = new byte[3];
            lengthBytes2[0] = 126;
            System.arraycopy(lengthBytes, 6, lengthBytes2, 1, 2);
            return lengthBytes2;
        }
        byte[] lengthBytes3 = new byte[9];
        lengthBytes3[0] = Byte.MAX_VALUE;
        System.arraycopy(lengthBytes, 0, lengthBytes3, 1, 8);
        return lengthBytes3;
    }

    private void validate(byte fragmentType, byte opcode) {
        if (opcode != 0 && opcode != fragmentType && !isControlFrame(opcode)) {
            throw new ProtocolException(LocalizationMessages.SEND_MESSAGE_INFRAGMENT());
        }
    }

    private byte checkForLastFrame(Frame frame) {
        byte local;
        byte local2 = frame.getOpcode();
        if (frame.isControlFrame()) {
            return (byte) (local2 | OTACommand.RESP_ID_VERSION);
        }
        if (!frame.isFin()) {
            if (this.outFragmentedType != 0) {
                local = 0;
            } else {
                this.outFragmentedType = local2;
                local = (byte) (local2 & Byte.MAX_VALUE);
            }
            validate(this.outFragmentedType, local);
            return local;
        } else if (this.outFragmentedType == 0) {
            return (byte) (local2 | OTACommand.RESP_ID_VERSION);
        } else {
            this.outFragmentedType = 0;
            return OTACommand.RESP_ID_VERSION;
        }
    }

    /* access modifiers changed from: package-private */
    public void doClose() {
        Writer localWriter = this.writer;
        if (localWriter != null) {
            try {
                localWriter.close();
            } catch (IOException e) {
                throw new IllegalStateException(LocalizationMessages.IOEXCEPTION_CLOSE(), e);
            }
        } else {
            throw new IllegalStateException(LocalizationMessages.CONNECTION_NULL());
        }
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer frame(Frame frame) {
        if (this.client) {
            frame = Frame.builder(frame).maskingKey(Integer.valueOf(this.maskingKeyGenerator.nextInt())).mask(true).build();
        }
        if (this.extensions != null && this.extensions.size() > 0) {
            for (Extension extension : this.extensions) {
                if (extension instanceof ExtendedExtension) {
                    try {
                        frame = ((ExtendedExtension) extension).processOutgoing(this.extensionContext, frame);
                    } catch (Throwable t) {
                        LOGGER.log(Level.FINE, LocalizationMessages.EXTENSION_EXCEPTION(extension.getName(), t.getMessage()), t);
                    }
                }
            }
        }
        byte opcode = checkForLastFrame(frame);
        if (frame.isRsv1()) {
            opcode = (byte) (opcode | 64);
        }
        if (frame.isRsv2()) {
            opcode = (byte) (opcode | 32);
        }
        if (frame.isRsv3()) {
            opcode = (byte) (opcode | MappingData.PATH);
        }
        byte[] bytes = frame.getPayloadData();
        byte[] lengthBytes = encodeLength(frame.getPayloadLength());
        int payloadLength = (int) frame.getPayloadLength();
        int length = lengthBytes.length + 1 + payloadLength;
        boolean z = this.client;
        int length2 = length + (z ? 4 : 0);
        int payloadStart = lengthBytes.length + 1 + (z ? 4 : 0);
        byte[] packet = new byte[length2];
        packet[0] = opcode;
        System.arraycopy(lengthBytes, 0, packet, 1, lengthBytes.length);
        if (this.client) {
            Integer maskingKey = frame.getMaskingKey();
            if (maskingKey != null) {
                Masker masker = new Masker(maskingKey.intValue());
                packet[1] = (byte) (packet[1] | OTACommand.RESP_ID_VERSION);
                masker.mask(packet, payloadStart, bytes, payloadLength);
                System.arraycopy(masker.getMask(), 0, packet, payloadStart - 4, 4);
            } else {
                throw new ProtocolException("Masking key cannot be null when sending message from client to server.");
            }
        } else {
            System.arraycopy(bytes, 0, packet, payloadStart, payloadLength);
        }
        return ByteBuffer.wrap(packet);
    }

    public Frame unframe(ByteBuffer buffer) {
        while (true) {
            try {
                int i = 2;
                switch (this.parsingState.state.get()) {
                    case 0:
                        if (buffer.remaining() >= 2) {
                            byte opcode = buffer.get();
                            this.parsingState.finalFragment = isBitSet(opcode, 7);
                            this.parsingState.controlFrame = isControlFrame(opcode);
                            this.parsingState.opcode = (byte) (opcode & Byte.MAX_VALUE);
                            if (!this.parsingState.finalFragment) {
                                if (this.parsingState.controlFrame) {
                                    throw new ProtocolException(LocalizationMessages.CONTROL_FRAME_FRAGMENTED());
                                }
                            }
                            byte lengthCode = buffer.get();
                            this.parsingState.masked = (lengthCode & OTACommand.RESP_ID_VERSION) == 128;
                            this.parsingState.masker = new Masker(buffer);
                            if (this.parsingState.masked) {
                                lengthCode = (byte) (lengthCode ^ OTACommand.RESP_ID_VERSION);
                            }
                            byte unused = this.parsingState.lengthCode = lengthCode;
                            this.parsingState.state.incrementAndGet();
                            break;
                        } else {
                            return null;
                        }
                    case 1:
                        if (this.parsingState.lengthCode <= 125) {
                            ParsingState parsingState2 = this.parsingState;
                            parsingState2.length = (long) parsingState2.lengthCode;
                        } else if (!this.parsingState.controlFrame) {
                            if (this.parsingState.lengthCode != 126) {
                                i = 8;
                            }
                            int lengthBytes = i;
                            if (buffer.remaining() < lengthBytes) {
                                return null;
                            }
                            this.parsingState.masker.setBuffer(buffer);
                            ParsingState parsingState3 = this.parsingState;
                            parsingState3.length = decodeLength(parsingState3.masker.unmask(lengthBytes));
                        } else {
                            throw new ProtocolException(LocalizationMessages.CONTROL_FRAME_LENGTH());
                        }
                        this.parsingState.state.incrementAndGet();
                        break;
                    case 2:
                        if (this.parsingState.masked) {
                            if (buffer.remaining() < 4) {
                                return null;
                            }
                            this.parsingState.masker.setBuffer(buffer);
                            this.parsingState.masker.readMask();
                        }
                        this.parsingState.state.incrementAndGet();
                        break;
                    case 3:
                        if (((long) buffer.remaining()) < this.parsingState.length) {
                            return null;
                        }
                        this.parsingState.masker.setBuffer(buffer);
                        byte[] data = this.parsingState.masker.unmask((int) this.parsingState.length);
                        if (((long) data.length) == this.parsingState.length) {
                            Frame frame = Frame.builder().fin(this.parsingState.finalFragment).rsv1(isBitSet(this.parsingState.opcode, 6)).rsv2(isBitSet(this.parsingState.opcode, 5)).rsv3(isBitSet(this.parsingState.opcode, 4)).opcode((byte) (this.parsingState.opcode & 15)).payloadLength(this.parsingState.length).payloadData(data).build();
                            this.parsingState.recycle();
                            return frame;
                        }
                        throw new ProtocolException(LocalizationMessages.DATA_UNEXPECTED_LENGTH(Integer.valueOf(data.length), Long.valueOf(this.parsingState.length)));
                    default:
                        throw new IllegalStateException(LocalizationMessages.UNEXPECTED_STATE(this.parsingState.state));
                }
            } catch (Exception e) {
                this.parsingState.recycle();
                throw ((RuntimeException) e);
            }
        }
    }

    public void process(Frame frame, TyrusWebSocket socket) {
        if (frame.isRsv1() || frame.isRsv2() || frame.isRsv3()) {
            throw new ProtocolException(LocalizationMessages.RSV_INCORRECTLY_SET());
        }
        byte opcode = frame.getOpcode();
        boolean fin = frame.isFin();
        if (!frame.isControlFrame()) {
            boolean continuationFrame = opcode == 0;
            if (continuationFrame && !this.processingFragment) {
                throw new ProtocolException(LocalizationMessages.UNEXPECTED_END_FRAGMENT());
            } else if (!this.processingFragment || continuationFrame) {
                if (!fin && !continuationFrame) {
                    this.processingFragment = true;
                }
                if (!fin && this.inFragmentedType == 0) {
                    this.inFragmentedType = opcode;
                }
            } else {
                throw new ProtocolException(LocalizationMessages.FRAGMENT_INVALID_OPCODE());
            }
        }
        TyrusFrame tyrusFrame = TyrusFrame.wrap(frame, this.inFragmentedType, this.remainder);
        if (tyrusFrame instanceof TextFrame) {
            this.remainder = ((TextFrame) tyrusFrame).getRemainder();
        }
        if (!this.client && tyrusFrame.isControlFrame() && (tyrusFrame instanceof CloseFrame)) {
            CloseReason.CloseCode closeCode = ((CloseFrame) tyrusFrame).getCloseReason().a();
            if (closeCode.equals(CloseReason.a.SERVICE_RESTART) || closeCode.equals(CloseReason.a.TRY_AGAIN_LATER)) {
                throw new ProtocolException("Illegal close code: " + closeCode);
            }
        }
        tyrusFrame.respond(socket);
        if (!tyrusFrame.isControlFrame() && fin) {
            this.inFragmentedType = 0;
            this.processingFragment = false;
        }
    }

    private boolean isControlFrame(byte opcode) {
        return (opcode & 8) == 8;
    }

    private boolean isBitSet(byte b, int bit) {
        return ((b >> bit) & 1) != 0;
    }

    public static class CompletionHandlerWrapper extends CompletionHandler<ByteBuffer> {
        private final Frame frame;
        private final CompletionHandler<Frame> frameCompletionHandler;
        private final TyrusFuture<Frame> future;

        private CompletionHandlerWrapper(CompletionHandler<Frame> frameCompletionHandler2, TyrusFuture<Frame> future2, Frame frame2) {
            this.frameCompletionHandler = frameCompletionHandler2;
            this.future = future2;
            this.frame = frame2;
        }

        public void cancelled() {
            CompletionHandler<Frame> completionHandler = this.frameCompletionHandler;
            if (completionHandler != null) {
                completionHandler.cancelled();
            }
            TyrusFuture<Frame> tyrusFuture = this.future;
            if (tyrusFuture != null) {
                tyrusFuture.setFailure(new RuntimeException(LocalizationMessages.FRAME_WRITE_CANCELLED()));
            }
        }

        public void failed(Throwable throwable) {
            CompletionHandler<Frame> completionHandler = this.frameCompletionHandler;
            if (completionHandler != null) {
                completionHandler.failed(throwable);
            }
            TyrusFuture<Frame> tyrusFuture = this.future;
            if (tyrusFuture != null) {
                tyrusFuture.setFailure(throwable);
            }
        }

        public void completed(ByteBuffer result) {
            CompletionHandler<Frame> completionHandler = this.frameCompletionHandler;
            if (completionHandler != null) {
                completionHandler.completed(this.frame);
            }
            TyrusFuture<Frame> tyrusFuture = this.future;
            if (tyrusFuture != null) {
                tyrusFuture.setResult(this.frame);
            }
        }

        public void updated(ByteBuffer result) {
            CompletionHandler<Frame> completionHandler = this.frameCompletionHandler;
            if (completionHandler != null) {
                completionHandler.updated(this.frame);
            }
        }
    }

    public static class ParsingState {
        volatile boolean controlFrame;
        volatile boolean finalFragment;
        volatile long length;
        /* access modifiers changed from: private */
        public volatile byte lengthCode;
        volatile boolean masked;
        volatile Masker masker;
        volatile byte opcode;
        final AtomicInteger state;

        private ParsingState() {
            this.state = new AtomicInteger(0);
            this.opcode = -1;
            this.length = -1;
            this.lengthCode = -1;
        }

        /* access modifiers changed from: package-private */
        public void recycle() {
            this.state.set(0);
            this.opcode = -1;
            this.length = -1;
            this.lengthCode = -1;
            this.masked = false;
            this.masker = null;
            this.finalFragment = false;
            this.controlFrame = false;
        }
    }
}
