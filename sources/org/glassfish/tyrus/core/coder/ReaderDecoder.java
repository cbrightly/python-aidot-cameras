package org.glassfish.tyrus.core.coder;

import jakarta.websocket.Decoder;
import java.io.Reader;
import java.io.StringReader;

public class ReaderDecoder extends CoderAdapter implements Decoder.Text<Reader> {
    public boolean willDecode(String s) {
        return true;
    }

    public Reader decode(String s) {
        return new StringReader(s);
    }
}
