package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;

public interface WrapperAware {
    Buffer wrap(String str);

    Buffer wrap(String str, Charset charset);

    Buffer wrap(ByteBuffer byteBuffer);

    Buffer wrap(byte[] bArr);

    Buffer wrap(byte[] bArr, int i, int i2);
}
