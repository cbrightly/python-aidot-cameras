package org.glassfish.tyrus.core.frame;

import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.frame.TyrusFrame;

public class BinaryFrame extends TyrusFrame {
    private final boolean continuation;

    public BinaryFrame(Frame frame) {
        super(frame, TyrusFrame.FrameType.BINARY);
        this.continuation = false;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BinaryFrame(Frame frame, boolean continuation2) {
        super(frame, continuation2 ? TyrusFrame.FrameType.BINARY_CONTINUATION : TyrusFrame.FrameType.BINARY);
        this.continuation = continuation2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BinaryFrame(byte[] payload, boolean continuation2, boolean fin) {
        super(Frame.builder().payloadData(payload).opcode(continuation2 ? (byte) 0 : 2).fin(fin).build(), continuation2 ? TyrusFrame.FrameType.BINARY_CONTINUATION : TyrusFrame.FrameType.BINARY);
        this.continuation = continuation2;
    }

    public void respond(TyrusWebSocket socket) {
        if (this.continuation) {
            socket.onFragment(this, isFin());
        } else if (isFin()) {
            socket.onMessage(this);
        } else {
            socket.onFragment(this, false);
        }
    }
}
