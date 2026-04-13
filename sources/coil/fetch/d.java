package coil.fetch;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import coil.bitmap.c;
import coil.decode.b;
import coil.decode.f;
import coil.decode.m;
import coil.fetch.g;
import coil.size.Size;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DrawableFetcher.kt */
public final class d implements g<Drawable> {
    @NotNull
    private final f a;

    public d(@NotNull f drawableDecoder) {
        k.e(drawableDecoder, "drawableDecoder");
        this.a = drawableDecoder;
    }

    /* renamed from: e */
    public boolean a(@NotNull Drawable data) {
        return g.a.a(this, data);
    }

    @Nullable
    /* renamed from: f */
    public String c(@NotNull Drawable data) {
        k.e(data, "data");
        return null;
    }

    @Nullable
    /* renamed from: d */
    public Object b(@NotNull c pool, @NotNull Drawable data, @NotNull Size size, @NotNull m options, @NotNull kotlin.coroutines.d<? super f> $completion) {
        Drawable drawable;
        boolean isVector = coil.util.f.k(data);
        if (isVector) {
            Bitmap $this$toDrawable$iv = this.a.a(data, options.d(), size, options.k(), options.a());
            Resources resources$iv$iv = options.e().getResources();
            k.d(resources$iv$iv, "context.resources");
            drawable = new BitmapDrawable(resources$iv$iv, $this$toDrawable$iv);
        } else {
            drawable = data;
        }
        return new e(drawable, isVector, b.MEMORY);
    }
}
