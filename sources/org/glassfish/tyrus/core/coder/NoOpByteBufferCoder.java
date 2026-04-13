package org.glassfish.tyrus.core.coder;

import jakarta.websocket.Decoder;
import jakarta.websocket.d;
import java.nio.ByteBuffer;

public class NoOpByteBufferCoder extends CoderAdapter implements Decoder.a<ByteBuffer>, d.a<ByteBuffer> {
    public boolean willDecode(ByteBuffer bytes) {
        return true;
    }

    public ByteBuffer decode(ByteBuffer bytes) {
        return bytes;
    }

    public ByteBuffer encode(ByteBuffer object) {
        return object;
    }
}
