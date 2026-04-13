package zendesk.storage.android;

import android.content.Context;
import java.io.File;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import zendesk.storage.android.e;
import zendesk.storage.android.internal.a;
import zendesk.storage.android.internal.c;

/* compiled from: StorageFactory.kt */
public final class d {
    @NotNull
    public static final d a = new d();

    private d() {
    }

    @NotNull
    public final c a(@NotNull String namespace, @NotNull Context context, @NotNull e type) {
        k.e(namespace, "namespace");
        k.e(context, "context");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (type instanceof e.a) {
            return new a(namespace, context);
        }
        if (type instanceof e.b) {
            return new c(namespace, b(namespace, context), ((e.b) type).a(), new zendesk.storage.android.internal.d());
        }
        throw new NoWhenBranchMatchedException();
    }

    @NotNull
    public final File b(@NotNull String namespace, @NotNull Context context) {
        k.e(namespace, "namespace");
        k.e(context, "context");
        return new File(context.getCacheDir().getPath() + '/' + namespace);
    }
}
