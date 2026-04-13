package org.glassfish.tyrus.core.frame;

import java.nio.ByteBuffer;
import java.util.Locale;
import org.glassfish.tyrus.core.ProtocolException;
import org.glassfish.tyrus.core.TyrusWebSocket;

public abstract class TyrusFrame extends Frame {
    private FrameType frameType;

    public enum FrameType {
        TEXT,
        TEXT_CONTINUATION,
        BINARY,
        BINARY_CONTINUATION,
        PING,
        PONG,
        CLOSE
    }

    public abstract void respond(TyrusWebSocket tyrusWebSocket);

    protected TyrusFrame(Frame frame, FrameType frameType2) {
        super(frame);
        this.frameType = frameType2;
    }

    public FrameType getFrameType() {
        return this.frameType;
    }

    public static TyrusFrame wrap(Frame frame, byte inFragmentedType, ByteBuffer remainder) {
        switch (frame.getOpcode()) {
            case 0:
                if ((inFragmentedType & 1) == 1) {
                    return new TextFrame(frame, remainder, true);
                }
                return new BinaryFrame(frame, true);
            case 1:
                return new TextFrame(frame, remainder);
            case 2:
                return new BinaryFrame(frame);
            case 8:
                return new CloseFrame(frame);
            case 9:
                return new PingFrame(frame);
            case 10:
                return new PongFrame(frame);
            default:
                throw new ProtocolException(String.format("Unknown wrappedFrame type: %s", new Object[]{Integer.toHexString(frame.getOpcode()).toUpperCase(Locale.US)}));
        }
    }
}
