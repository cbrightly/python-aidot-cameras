package kotlin.io;

import java.io.InputStream;
import java.io.OutputStream;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: IOStreams.kt */
public final class a {
    public static /* synthetic */ long b(InputStream inputStream, OutputStream outputStream, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return a(inputStream, outputStream, i);
    }

    public static final long a(@NotNull InputStream $this$copyTo, @NotNull OutputStream out, int bufferSize) {
        k.e($this$copyTo, "$this$copyTo");
        k.e(out, "out");
        long bytesCopied = 0;
        byte[] buffer = new byte[bufferSize];
        int bytes = $this$copyTo.read(buffer);
        while (bytes >= 0) {
            out.write(buffer, 0, bytes);
            bytesCopied += (long) bytes;
            bytes = $this$copyTo.read(buffer);
        }
        return bytesCopied;
    }
}
