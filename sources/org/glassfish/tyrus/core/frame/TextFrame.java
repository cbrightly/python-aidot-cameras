package org.glassfish.tyrus.core.frame;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import org.glassfish.tyrus.core.StrictUtf8;
import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.Utf8DecodingException;
import org.glassfish.tyrus.core.frame.TyrusFrame;

public class TextFrame extends TyrusFrame {
    private final boolean continuation;
    private final CharsetDecoder currentDecoder;
    private ByteBuffer remainder;
    private final String textPayload;
    private final Charset utf8;

    public TextFrame(Frame frame, ByteBuffer remainder2) {
        super(frame, TyrusFrame.FrameType.TEXT);
        StrictUtf8 strictUtf8 = new StrictUtf8();
        this.utf8 = strictUtf8;
        this.currentDecoder = strictUtf8.newDecoder();
        this.textPayload = utf8Decode(isFin(), getPayloadData(), remainder2);
        this.continuation = false;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TextFrame(Frame frame, ByteBuffer remainder2, boolean continuation2) {
        super(frame, continuation2 ? TyrusFrame.FrameType.TEXT_CONTINUATION : TyrusFrame.FrameType.TEXT);
        StrictUtf8 strictUtf8 = new StrictUtf8();
        this.utf8 = strictUtf8;
        this.currentDecoder = strictUtf8.newDecoder();
        this.textPayload = utf8Decode(isFin(), getPayloadData(), remainder2);
        this.continuation = continuation2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TextFrame(String message, boolean continuation2, boolean fin) {
        super(Frame.builder().payloadData(encode(new StrictUtf8(), message)).opcode(continuation2 ^ true ? (byte) 1 : 0).fin(fin).build(), continuation2 ? TyrusFrame.FrameType.TEXT_CONTINUATION : TyrusFrame.FrameType.TEXT);
        StrictUtf8 strictUtf8 = new StrictUtf8();
        this.utf8 = strictUtf8;
        this.currentDecoder = strictUtf8.newDecoder();
        this.continuation = continuation2;
        this.textPayload = message;
    }

    public String getTextPayload() {
        return this.textPayload;
    }

    public ByteBuffer getRemainder() {
        return this.remainder;
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

    private String utf8Decode(boolean finalFragment, byte[] data, ByteBuffer remainder2) {
        ByteBuffer b = getByteBuffer(data, remainder2);
        int n = (int) (((float) b.remaining()) * this.currentDecoder.averageCharsPerByte());
        CharBuffer cb = CharBuffer.allocate(n);
        while (true) {
            CoderResult result = this.currentDecoder.decode(b, cb, finalFragment);
            if (result.isUnderflow()) {
                if (finalFragment) {
                    this.currentDecoder.flush(cb);
                    if (!b.hasRemaining()) {
                        this.currentDecoder.reset();
                    } else {
                        throw new IllegalStateException("Final UTF-8 fragment received, but not all bytes consumed by decode process");
                    }
                } else if (b.hasRemaining()) {
                    this.remainder = b;
                }
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

    private ByteBuffer getByteBuffer(byte[] data, ByteBuffer remainder2) {
        if (remainder2 == null) {
            return ByteBuffer.wrap(data);
        }
        int rem = remainder2.remaining();
        byte[] orig = remainder2.array();
        byte[] b = new byte[(data.length + rem)];
        System.arraycopy(orig, orig.length - rem, b, 0, rem);
        System.arraycopy(data, 0, b, rem, data.length);
        return ByteBuffer.wrap(b);
    }

    public String toString() {
        return super.toString() + ", textPayload='" + this.textPayload + '\'';
    }

    private static byte[] encode(Charset charset, String string) {
        if (string == null || string.isEmpty()) {
            return new byte[0];
        }
        CharsetEncoder ce = charset.newEncoder();
        byte[] ba = new byte[scale(string.length(), ce.maxBytesPerChar())];
        if (string.length() == 0) {
            return ba;
        }
        ce.reset();
        ByteBuffer bb = ByteBuffer.wrap(ba);
        try {
            CoderResult cr = ce.encode(CharBuffer.wrap(string), bb, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            CoderResult cr2 = ce.flush(bb);
            if (!cr2.isUnderflow()) {
                cr2.throwException();
            }
            return safeTrim(ba, bb.position());
        } catch (CharacterCodingException x) {
            throw new Error(x);
        }
    }

    private static int scale(int len, float expansionFactor) {
        return (int) (((double) len) * ((double) expansionFactor));
    }

    private static byte[] safeTrim(byte[] ba, int len) {
        if (len == ba.length && System.getSecurityManager() == null) {
            return ba;
        }
        return copyOf(ba, len);
    }

    private static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
}
