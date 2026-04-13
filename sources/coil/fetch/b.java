package coil.fetch;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import coil.bitmap.c;
import coil.decode.m;
import coil.fetch.g;
import coil.size.Size;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BitmapFetcher.kt */
public final class b implements g<Bitmap> {
    /* renamed from: e */
    public boolean a(@NotNull Bitmap data) {
        return g.a.a(this, data);
    }

    @Nullable
    /* renamed from: f */
    public String c(@NotNull Bitmap data) {
        k.e(data, "data");
        return null;
    }

    @Nullable
    /* renamed from: d */
    public Object b(@NotNull c pool, @NotNull Bitmap data, @NotNull Size size, @NotNull m options, @NotNull d<? super f> $completion) {
        Resources resources$iv$iv = options.e().getResources();
        k.d(resources$iv$iv, "context.resources");
        return new e(new BitmapDrawable(resources$iv$iv, data), false, coil.decode.b.MEMORY);
    }
}
