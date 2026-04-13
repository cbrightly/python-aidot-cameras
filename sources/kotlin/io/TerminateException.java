package kotlin.io;

import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Utils.kt */
public final class TerminateException extends FileSystemException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TerminateException(@NotNull File file) {
        super(file, (File) null, (String) null, 6, (DefaultConstructorMarker) null);
        k.e(file, "file");
    }
}
