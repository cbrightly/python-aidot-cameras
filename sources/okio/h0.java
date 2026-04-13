package okio;

import kotlin.jvm.internal.k;
import kotlin.text.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: -JvmPlatform.kt */
public final class h0 {
    @NotNull
    public static final String b(@NotNull byte[] $this$toUtf8String) {
        k.e($this$toUtf8String, "$this$toUtf8String");
        return new String($this$toUtf8String, c.a);
    }

    @NotNull
    public static final byte[] a(@NotNull String $this$asUtf8ToByteArray) {
        k.e($this$asUtf8ToByteArray, "$this$asUtf8ToByteArray");
        byte[] bytes = $this$asUtf8ToByteArray.getBytes(c.a);
        k.d(bytes, "(this as java.lang.String).getBytes(charset)");
        return bytes;
    }
}
