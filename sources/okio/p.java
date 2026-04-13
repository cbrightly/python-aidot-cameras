package okio;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.jetbrains.annotations.NotNull;

public final class p {
    @NotNull
    public static final b0 a(@NotNull File $this$appendingSink) {
        return q.b($this$appendingSink);
    }

    @NotNull
    public static final b0 b() {
        return r.a();
    }

    @NotNull
    public static final d c(@NotNull b0 $this$buffer) {
        return r.b($this$buffer);
    }

    @NotNull
    public static final e d(@NotNull e0 $this$buffer) {
        return r.c($this$buffer);
    }

    public static final boolean e(@NotNull AssertionError $this$isAndroidGetsocknameError) {
        return q.c($this$isAndroidGetsocknameError);
    }

    @NotNull
    public static final b0 f(@NotNull File file) {
        return q.g(file, false, 1, (Object) null);
    }

    @NotNull
    public static final b0 g(@NotNull File $this$sink, boolean append) {
        return q.d($this$sink, append);
    }

    @NotNull
    public static final b0 h(@NotNull OutputStream $this$sink) {
        return q.e($this$sink);
    }

    @NotNull
    public static final b0 i(@NotNull Socket $this$sink) {
        return q.f($this$sink);
    }

    @NotNull
    public static final e0 k(@NotNull File $this$source) {
        return q.h($this$source);
    }

    @NotNull
    public static final e0 l(@NotNull InputStream $this$source) {
        return q.i($this$source);
    }

    @NotNull
    public static final e0 m(@NotNull Socket $this$source) {
        return q.j($this$source);
    }
}
