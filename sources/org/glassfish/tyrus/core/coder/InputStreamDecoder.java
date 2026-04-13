package org.glassfish.tyrus.core.coder;

import jakarta.websocket.Decoder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class InputStreamDecoder extends CoderAdapter implements Decoder.a<InputStream> {
    public boolean willDecode(ByteBuffer bytes) {
        return true;
    }

    public InputStream decode(ByteBuffer bytes) {
        return new ByteArrayInputStream(bytes.array());
    }
}
