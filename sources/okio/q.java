package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmOkio.kt */
public final /* synthetic */ class q {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger("okio.Okio");

    @NotNull
    public static final b0 e(@NotNull OutputStream $this$sink) {
        k.e($this$sink, "$this$sink");
        return new t($this$sink, new f0());
    }

    @NotNull
    public static final e0 i(@NotNull InputStream $this$source) {
        k.e($this$source, "$this$source");
        return new o($this$source, new f0());
    }

    @NotNull
    public static final b0 f(@NotNull Socket $this$sink) {
        k.e($this$sink, "$this$sink");
        c0 timeout = new c0($this$sink);
        OutputStream outputStream = $this$sink.getOutputStream();
        k.d(outputStream, "getOutputStream()");
        return timeout.y(new t(outputStream, timeout));
    }

    @NotNull
    public static final e0 j(@NotNull Socket $this$source) {
        k.e($this$source, "$this$source");
        c0 timeout = new c0($this$source);
        InputStream inputStream = $this$source.getInputStream();
        k.d(inputStream, "getInputStream()");
        return timeout.z(new o(inputStream, timeout));
    }

    @NotNull
    public static final b0 d(@NotNull File $this$sink, boolean append) {
        k.e($this$sink, "$this$sink");
        return p.h(new FileOutputStream($this$sink, append));
    }

    public static /* synthetic */ b0 g(File file, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return p.g(file, z);
    }

    @NotNull
    public static final b0 b(@NotNull File $this$appendingSink) {
        k.e($this$appendingSink, "$this$appendingSink");
        return p.h(new FileOutputStream($this$appendingSink, true));
    }

    @NotNull
    public static final e0 h(@NotNull File $this$source) {
        k.e($this$source, "$this$source");
        return new i(new FileInputStream($this$source));
    }

    public static final boolean c(@NotNull AssertionError $this$isAndroidGetsocknameError) {
        k.e($this$isAndroidGetsocknameError, "$this$isAndroidGetsocknameError");
        if ($this$isAndroidGetsocknameError.getCause() == null) {
            return false;
        }
        String message = $this$isAndroidGetsocknameError.getMessage();
        return message != null ? x.S(message, "getsockname failed", false, 2, (Object) null) : false;
    }
}
