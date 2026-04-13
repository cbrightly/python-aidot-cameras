package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/* compiled from: B2CConverterBlocking */
public final class ReadConverter extends InputStreamReader {
    public ReadConverter(IntermediateInputStream in, Charset charset) {
        super(in, charset);
    }

    public void close() {
    }

    public int read(char[] cbuf, int off, int len) {
        return super.read(cbuf, off, len);
    }

    public void recycle() {
        while (ready()) {
            try {
                read();
            } catch (IOException e) {
                return;
            }
        }
    }
}
