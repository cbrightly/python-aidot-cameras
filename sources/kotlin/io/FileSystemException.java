package kotlin.io;

import java.io.File;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Exceptions.kt */
public class FileSystemException extends IOException {
    @NotNull
    private final File file;
    @Nullable
    private final File other;
    @Nullable
    private final String reason;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FileSystemException(@NotNull File file2, @Nullable File other2, @Nullable String reason2) {
        super(c.a(file2, other2, reason2));
        k.e(file2, "file");
        this.file = file2;
        this.other = other2;
        this.reason = reason2;
    }

    @NotNull
    public final File getFile() {
        return this.file;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FileSystemException(File file2, File file3, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file2, (i & 2) != 0 ? null : file3, (i & 4) != 0 ? null : str);
    }

    @Nullable
    public final File getOther() {
        return this.other;
    }

    @Nullable
    public final String getReason() {
        return this.reason;
    }
}
