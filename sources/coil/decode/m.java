package coil.decode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.os.Build;
import coil.request.c;
import coil.request.l;
import coil.size.e;
import kotlin.jvm.internal.k;
import okhttp3.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Options.kt */
public final class m {
    @NotNull
    private final Context a;
    @NotNull
    private final Bitmap.Config b;
    @Nullable
    private final ColorSpace c;
    @NotNull
    private final e d;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    @NotNull
    private final u h;
    @NotNull
    private final l i;
    @NotNull
    private final c j;
    @NotNull
    private final c k;
    @NotNull
    private final c l;

    public m(@NotNull Context context, @NotNull Bitmap.Config config, @Nullable ColorSpace colorSpace, @NotNull e scale, boolean allowInexactSize, boolean allowRgb565, boolean premultipliedAlpha, @NotNull u headers, @NotNull l parameters, @NotNull c memoryCachePolicy, @NotNull c diskCachePolicy, @NotNull c networkCachePolicy) {
        k.e(context, "context");
        k.e(config, "config");
        k.e(scale, "scale");
        k.e(headers, "headers");
        k.e(parameters, "parameters");
        k.e(memoryCachePolicy, "memoryCachePolicy");
        k.e(diskCachePolicy, "diskCachePolicy");
        k.e(networkCachePolicy, "networkCachePolicy");
        this.a = context;
        this.b = config;
        this.c = colorSpace;
        this.d = scale;
        this.e = allowInexactSize;
        this.f = allowRgb565;
        this.g = premultipliedAlpha;
        this.h = headers;
        this.i = parameters;
        this.j = memoryCachePolicy;
        this.k = diskCachePolicy;
        this.l = networkCachePolicy;
    }

    @NotNull
    public final Context e() {
        return this.a;
    }

    @NotNull
    public final Bitmap.Config d() {
        return this.b;
    }

    @Nullable
    public final ColorSpace c() {
        return this.c;
    }

    @NotNull
    public final e k() {
        return this.d;
    }

    public final boolean a() {
        return this.e;
    }

    public final boolean b() {
        return this.f;
    }

    public final boolean j() {
        return this.g;
    }

    @NotNull
    public final u g() {
        return this.h;
    }

    @NotNull
    public final l i() {
        return this.i;
    }

    @NotNull
    public final c f() {
        return this.k;
    }

    @NotNull
    public final c h() {
        return this.l;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof m) || !k.a(this.a, ((m) other).a) || this.b != ((m) other).b || ((Build.VERSION.SDK_INT >= 26 && !k.a(this.c, ((m) other).c)) || this.d != ((m) other).d || this.e != ((m) other).e || this.f != ((m) other).f || this.g != ((m) other).g || !k.a(this.h, ((m) other).h) || !k.a(this.i, ((m) other).i) || this.j != ((m) other).j || this.k != ((m) other).k || this.l != ((m) other).l)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        ColorSpace colorSpace = this.c;
        return ((((((((((((((((((result + (colorSpace == null ? 0 : colorSpace.hashCode())) * 31) + this.d.hashCode()) * 31) + l.a(this.e)) * 31) + l.a(this.f)) * 31) + l.a(this.g)) * 31) + this.h.hashCode()) * 31) + this.i.hashCode()) * 31) + this.j.hashCode()) * 31) + this.k.hashCode()) * 31) + this.l.hashCode();
    }

    @NotNull
    public String toString() {
        return "Options(context=" + this.a + ", config=" + this.b + ", colorSpace=" + this.c + ", scale=" + this.d + ", allowInexactSize=" + this.e + ", allowRgb565=" + this.f + ", premultipliedAlpha=" + this.g + ", headers=" + this.h + ", parameters=" + this.i + ", memoryCachePolicy=" + this.j + ", diskCachePolicy=" + this.k + ", networkCachePolicy=" + this.l + ')';
    }
}
