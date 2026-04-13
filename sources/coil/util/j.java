package coil.util;

import android.content.Context;
import java.io.File;
import kotlin.jvm.internal.k;
import okhttp3.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: CoilUtils.kt */
public final class j {
    @NotNull
    public static final j a = new j();

    private j() {
    }

    @NotNull
    public static final c a(@NotNull Context context) {
        k.e(context, "context");
        o oVar = o.a;
        File cacheDirectory = oVar.g(context);
        return new c(cacheDirectory, oVar.c(cacheDirectory));
    }
}
