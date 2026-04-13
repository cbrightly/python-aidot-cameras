package androidx.core.net;

import android.net.Uri;
import java.io.File;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Uri.kt */
public final class UriKt {
    @NotNull
    public static final Uri toUri(@NotNull String $this$toUri) {
        k.e($this$toUri, "<this>");
        Uri parse = Uri.parse($this$toUri);
        k.d(parse, "parse(this)");
        return parse;
    }

    @NotNull
    public static final Uri toUri(@NotNull File $this$toUri) {
        k.e($this$toUri, "<this>");
        Uri fromFile = Uri.fromFile($this$toUri);
        k.d(fromFile, "fromFile(this)");
        return fromFile;
    }

    @NotNull
    public static final File toFile(@NotNull Uri $this$toFile) {
        k.e($this$toFile, "<this>");
        if (k.a($this$toFile.getScheme(), "file")) {
            String path = $this$toFile.getPath();
            if (path != null) {
                return new File(path);
            }
            throw new IllegalArgumentException(k.l("Uri path is null: ", $this$toFile).toString());
        }
        throw new IllegalArgumentException(k.l("Uri lacks 'file' scheme: ", $this$toFile).toString());
    }
}
