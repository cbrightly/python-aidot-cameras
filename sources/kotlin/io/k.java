package kotlin.io;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReadWrite.kt */
public final class k {
    @NotNull
    public static final String c(@NotNull Reader $this$readText) {
        kotlin.jvm.internal.k.e($this$readText, "$this$readText");
        StringWriter buffer = new StringWriter();
        b($this$readText, buffer, 0, 2, (Object) null);
        String stringWriter = buffer.toString();
        kotlin.jvm.internal.k.d(stringWriter, "buffer.toString()");
        return stringWriter;
    }

    public static /* synthetic */ long b(Reader reader, Writer writer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return a(reader, writer, i);
    }

    public static final long a(@NotNull Reader $this$copyTo, @NotNull Writer out, int bufferSize) {
        kotlin.jvm.internal.k.e($this$copyTo, "$this$copyTo");
        kotlin.jvm.internal.k.e(out, "out");
        long charsCopied = 0;
        char[] buffer = new char[bufferSize];
        int chars = $this$copyTo.read(buffer);
        while (chars >= 0) {
            out.write(buffer, 0, chars);
            charsCopied += (long) chars;
            chars = $this$copyTo.read(buffer);
        }
        return charsCopied;
    }
}
