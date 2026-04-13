package coil.map;

import android.net.Uri;
import androidx.core.net.UriKt;
import coil.util.f;
import java.io.File;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileUriMapper.kt */
public final class a implements b<Uri, File> {
    /* renamed from: b */
    public boolean a(@NotNull Uri data) {
        k.e(data, "data");
        if (k.a(data.getScheme(), "file")) {
            String it = f.c(data);
            if (((it == null || k.a(it, "android_asset")) ? null : 1) != null) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    /* renamed from: c */
    public File map(@NotNull Uri data) {
        k.e(data, "data");
        return UriKt.toFile(data);
    }
}
