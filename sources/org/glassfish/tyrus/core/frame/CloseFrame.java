package org.glassfish.tyrus.core.frame;

import jakarta.websocket.CloseReason;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import org.glassfish.tyrus.core.CloseReasons;
import org.glassfish.tyrus.core.ProtocolException;
import org.glassfish.tyrus.core.StrictUtf8;
import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.Utf8DecodingException;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.frame.TyrusFrame;

public class CloseFrame extends TyrusFrame {
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final CloseReason closeReason;

    public CloseFrame(Frame frame) {
        super(frame, TyrusFrame.FrameType.CLOSE);
        String closeReasonString;
        final byte[] data = frame.getPayloadData();
        if (data.length >= 2) {
            int closeCode = (int) Utils.toLong(data, 0, 2);
            if (closeCode < 1000 || closeCode == 1004 || closeCode == 1005 || closeCode == 1006 || ((closeCode > 1013 && closeCode < 3000) || closeCode > 4999)) {
                throw new ProtocolException("Illegal status code: " + closeCode);
            }
            if (data.length > 2) {
                closeReasonString = utf8Decode(data);
            } else {
                closeReasonString = null;
            }
            this.closeReason = new CloseReason(CloseReason.a.getCloseCode(closeCode), closeReasonString);
            return;
        }
        throw new ProtocolException("Closing wrappedFrame payload, if present, must be a minimum of 2 bytes in length") {
            private static final long serialVersionUID = -5720682492584668231L;

            public CloseReason getCloseReason() {
                if (data.length == 0) {
                    return CloseReasons.NORMAL_CLOSURE.getCloseReason();
                }
                return super.getCloseReason();
            }
        };
    }

    public CloseFrame(CloseReason closeReason2) {
        super(Frame.builder().fin(true).opcode((byte) 8).payloadData(getPayload(closeReason2.a().getCode(), closeReason2.b())).build(), TyrusFrame.FrameType.CLOSE);
        this.closeReason = closeReason2;
    }

    public CloseReason getCloseReason() {
        return this.closeReason;
    }

    public void respond(TyrusWebSocket socket) {
        socket.onClose(this);
        socket.close();
    }

    private String utf8Decode(byte[] data) {
        ByteBuffer b = ByteBuffer.wrap(data, 2, data.length - 2);
        CharsetDecoder decoder = new StrictUtf8().newDecoder();
        int n = (int) (((float) b.remaining()) * decoder.averageCharsPerByte());
        CharBuffer cb = CharBuffer.allocate(n);
        while (true) {
            CoderResult result = decoder.decode(b, cb, true);
            if (result.isUnderflow()) {
                decoder.flush(cb);
                cb.flip();
                return cb.toString();
            } else if (result.isOverflow()) {
                CharBuffer tmp = CharBuffer.allocate((n * 2) + 1);
                cb.flip();
                tmp.put(cb);
                cb = tmp;
            } else if (result.isError() || result.isMalformed()) {
            }
        }
        throw new Utf8DecodingException();
    }

    private static byte[] getPayload(int closeCode, String closeReason2) {
        if (closeCode == -1) {
            return EMPTY_BYTES;
        }
        byte[] bytes = Utils.toArray((long) closeCode);
        byte[] reasonBytes = closeReason2 == null ? EMPTY_BYTES : closeReason2.getBytes(new StrictUtf8());
        byte[] frameBytes = new byte[(reasonBytes.length + 2)];
        System.arraycopy(bytes, bytes.length - 2, frameBytes, 0, 2);
        System.arraycopy(reasonBytes, 0, frameBytes, 2, reasonBytes.length);
        return frameBytes;
    }
}
