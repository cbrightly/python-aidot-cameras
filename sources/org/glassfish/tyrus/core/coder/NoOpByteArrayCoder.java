package org.glassfish.tyrus.core.coder;

import jakarta.websocket.Decoder;
import jakarta.websocket.d;
import java.nio.ByteBuffer;

public class NoOpByteArrayCoder extends CoderAdapter implements Decoder.a<byte[]>, d.a<byte[]> {
    public ByteBuffer encode(byte[] object) {
        return ByteBuffer.wrap(object);
    }

    public boolean willDecode(ByteBuffer bytes) {
        return true;
    }

    public byte[] decode(ByteBuffer bytes) {
        return bytes.array();
    }
}
