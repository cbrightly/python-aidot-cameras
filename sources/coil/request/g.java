package coil.request;

import android.graphics.drawable.Drawable;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageResult.kt */
public final class g extends j {
    @Nullable
    private final Drawable a;
    @NotNull
    private final i b;
    @NotNull
    private final Throwable c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return k.a(a(), gVar.a()) && k.a(b(), gVar.b()) && k.a(this.c, gVar.c);
    }

    public int hashCode() {
        return ((((a() == null ? 0 : a().hashCode()) * 31) + b().hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "ErrorResult(drawable=" + a() + ", request=" + b() + ", throwable=" + this.c + ')';
    }

    @Nullable
    public Drawable a() {
        return this.a;
    }

    @NotNull
    public i b() {
        return this.b;
    }

    @NotNull
    public final Throwable c() {
        return this.c;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public g(@Nullable Drawable drawable, @NotNull i request, @NotNull Throwable throwable) {
        super((DefaultConstructorMarker) null);
        k.e(request, Progress.REQUEST);
        k.e(throwable, "throwable");
        this.a = drawable;
        this.b = request;
        this.c = throwable;
    }
}
