package okhttp3.internal.io;

import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.b0;
import okio.e0;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileSystem.kt */
public interface b {
    @NotNull
    public static final b a = new a$a();
    public static final a b = new a((DefaultConstructorMarker) null);

    void a(@NotNull File file);

    boolean b(@NotNull File file);

    @NotNull
    b0 c(@NotNull File file);

    long d(@NotNull File file);

    @NotNull
    e0 e(@NotNull File file);

    @NotNull
    b0 f(@NotNull File file);

    void g(@NotNull File file, @NotNull File file2);

    void h(@NotNull File file);

    /* compiled from: FileSystem.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
