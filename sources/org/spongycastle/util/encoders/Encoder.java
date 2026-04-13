package org.spongycastle.util.encoders;

import java.io.OutputStream;

public interface Encoder {
    int a(byte[] bArr, int i, int i2, OutputStream outputStream);

    int b(String str, OutputStream outputStream);
}
