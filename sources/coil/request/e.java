package coil.request;

import android.graphics.Bitmap;
import androidx.lifecycle.Lifecycle;
import coil.size.f;
import coil.transition.b;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefinedRequestOptions.kt */
public final class e {
    @Nullable
    private final Lifecycle a;
    @Nullable
    private final f b;
    @Nullable
    private final coil.size.e c;
    @Nullable
    private final i0 d;
    @Nullable
    private final b e;
    @Nullable
    private final coil.size.b f;
    @Nullable
    private final Bitmap.Config g;
    @Nullable
    private final Boolean h;
    @Nullable
    private final Boolean i;
    @Nullable
    private final c j;
    @Nullable
    private final c k;
    @Nullable
    private final c l;

    public e(@Nullable Lifecycle lifecycle, @Nullable f sizeResolver, @Nullable coil.size.e scale, @Nullable i0 dispatcher, @Nullable b transition, @Nullable coil.size.b precision, @Nullable Bitmap.Config bitmapConfig, @Nullable Boolean allowHardware, @Nullable Boolean allowRgb565, @Nullable c memoryCachePolicy, @Nullable c diskCachePolicy, @Nullable c networkCachePolicy) {
        this.a = lifecycle;
        this.b = sizeResolver;
        this.c = scale;
        this.d = dispatcher;
        this.e = transition;
        this.f = precision;
        this.g = bitmapConfig;
        this.h = allowHardware;
        this.i = allowRgb565;
        this.j = memoryCachePolicy;
        this.k = diskCachePolicy;
        this.l = networkCachePolicy;
    }

    @Nullable
    public final Lifecycle f() {
        return this.a;
    }

    @Nullable
    public final f k() {
        return this.b;
    }

    @Nullable
    public final coil.size.e j() {
        return this.c;
    }

    @Nullable
    public final i0 e() {
        return this.d;
    }

    @Nullable
    public final b l() {
        return this.e;
    }

    @Nullable
    public final coil.size.b i() {
        return this.f;
    }

    @Nullable
    public final Bitmap.Config c() {
        return this.g;
    }

    @Nullable
    public final Boolean a() {
        return this.h;
    }

    @Nullable
    public final Boolean b() {
        return this.i;
    }

    @Nullable
    public final c g() {
        return this.j;
    }

    @Nullable
    public final c d() {
        return this.k;
    }

    @Nullable
    public final c h() {
        return this.l;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof e) || !k.a(this.a, ((e) other).a) || !k.a(this.b, ((e) other).b) || this.c != ((e) other).c || !k.a(this.d, ((e) other).d) || !k.a(this.e, ((e) other).e) || this.f != ((e) other).f || this.g != ((e) other).g || !k.a(this.h, ((e) other).h) || !k.a(this.i, ((e) other).i) || this.j != ((e) other).j || this.k != ((e) other).k || this.l != ((e) other).l) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        Lifecycle lifecycle = this.a;
        int i2 = 0;
        int hashCode = (lifecycle == null ? 0 : lifecycle.hashCode()) * 31;
        f fVar = this.b;
        int result = (hashCode + (fVar == null ? 0 : fVar.hashCode())) * 31;
        coil.size.e eVar = this.c;
        int result2 = (result + (eVar == null ? 0 : eVar.hashCode())) * 31;
        i0 i0Var = this.d;
        int result3 = (result2 + (i0Var == null ? 0 : i0Var.hashCode())) * 31;
        b bVar = this.e;
        int result4 = (result3 + (bVar == null ? 0 : bVar.hashCode())) * 31;
        coil.size.b bVar2 = this.f;
        int result5 = (result4 + (bVar2 == null ? 0 : bVar2.hashCode())) * 31;
        Bitmap.Config config = this.g;
        int result6 = (result5 + (config == null ? 0 : config.hashCode())) * 31;
        Boolean bool = this.h;
        int result7 = (result6 + (bool == null ? 0 : bool.hashCode())) * 31;
        Boolean bool2 = this.i;
        int result8 = (result7 + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        c cVar = this.j;
        int result9 = (result8 + (cVar == null ? 0 : cVar.hashCode())) * 31;
        c cVar2 = this.k;
        int result10 = (result9 + (cVar2 == null ? 0 : cVar2.hashCode())) * 31;
        c cVar3 = this.l;
        if (cVar3 != null) {
            i2 = cVar3.hashCode();
        }
        return result10 + i2;
    }

    @NotNull
    public String toString() {
        return "DefinedRequestOptions(lifecycle=" + this.a + ", sizeResolver=" + this.b + ", scale=" + this.c + ", dispatcher=" + this.d + ", transition=" + this.e + ", precision=" + this.f + ", bitmapConfig=" + this.g + ", allowHardware=" + this.h + ", allowRgb565=" + this.i + ", memoryCachePolicy=" + this.j + ", diskCachePolicy=" + this.k + ", networkCachePolicy=" + this.l + ')';
    }
}
