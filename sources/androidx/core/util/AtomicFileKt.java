package androidx.core.util;

import android.util.AtomicFile;
import androidx.annotation.RequiresApi;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import kotlin.text.c;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: AtomicFile.kt */
public final class AtomicFileKt {
    @RequiresApi(17)
    public static final void tryWrite(@NotNull AtomicFile $this$tryWrite, @NotNull l<? super FileOutputStream, x> block) {
        k.e($this$tryWrite, "<this>");
        k.e(block, "block");
        FileOutputStream stream = $this$tryWrite.startWrite();
        try {
            k.d(stream, "stream");
            block.invoke(stream);
            j.b(1);
            $this$tryWrite.finishWrite(stream);
            j.a(1);
        } catch (Throwable th) {
            j.b(1);
            $this$tryWrite.failWrite(stream);
            j.a(1);
            throw th;
        }
    }

    @RequiresApi(17)
    public static final void writeBytes(@NotNull AtomicFile $this$writeBytes, @NotNull byte[] array) {
        k.e($this$writeBytes, "<this>");
        k.e(array, "array");
        AtomicFile $this$tryWrite$iv = $this$writeBytes;
        FileOutputStream stream$iv = $this$tryWrite$iv.startWrite();
        try {
            k.d(stream$iv, "stream");
            stream$iv.write(array);
            $this$tryWrite$iv.finishWrite(stream$iv);
        } catch (Throwable th) {
            $this$tryWrite$iv.failWrite(stream$iv);
            throw th;
        }
    }

    public static /* synthetic */ void writeText$default(AtomicFile atomicFile, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = c.a;
        }
        writeText(atomicFile, str, charset);
    }

    @RequiresApi(17)
    public static final void writeText(@NotNull AtomicFile $this$writeText, @NotNull String text, @NotNull Charset charset) {
        k.e($this$writeText, "<this>");
        k.e(text, "text");
        k.e(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        k.d(bytes, "(this as java.lang.String).getBytes(charset)");
        writeBytes($this$writeText, bytes);
    }

    @RequiresApi(17)
    @NotNull
    public static final byte[] readBytes(@NotNull AtomicFile $this$readBytes) {
        k.e($this$readBytes, "<this>");
        byte[] readFully = $this$readBytes.readFully();
        k.d(readFully, "readFully()");
        return readFully;
    }

    public static /* synthetic */ String readText$default(AtomicFile atomicFile, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = c.a;
        }
        return readText(atomicFile, charset);
    }

    @RequiresApi(17)
    @NotNull
    public static final String readText(@NotNull AtomicFile $this$readText, @NotNull Charset charset) {
        k.e($this$readText, "<this>");
        k.e(charset, "charset");
        byte[] readFully = $this$readText.readFully();
        k.d(readFully, "readFully()");
        return new String(readFully, charset);
    }
}
