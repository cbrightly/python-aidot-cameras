package kotlin.io;

import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Exceptions.kt */
public final class FileAlreadyExistsException extends FileSystemException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FileAlreadyExistsException(@NotNull File file, @Nullable File other, @Nullable String reason) {
        super(file, other, reason);
        k.e(file, "file");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FileAlreadyExistsException(File file, File file2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? null : file2, (i & 4) != 0 ? null : str);
    }
}
