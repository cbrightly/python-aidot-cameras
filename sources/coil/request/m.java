package coil.request;

import android.graphics.drawable.Drawable;
import coil.request.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageResult.kt */
public final class m extends j {
    @NotNull
    private final Drawable a;
    @NotNull
    private final i b;
    @NotNull
    private final j.a c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof m)) {
            return false;
        }
        m mVar = (m) obj;
        return k.a(a(), mVar.a()) && k.a(b(), mVar.b()) && k.a(this.c, mVar.c);
    }

    public int hashCode() {
        return (((a().hashCode() * 31) + b().hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "SuccessResult(drawable=" + a() + ", request=" + b() + ", metadata=" + this.c + ')';
    }

    @NotNull
    public Drawable a() {
        return this.a;
    }

    @NotNull
    public i b() {
        return this.b;
    }

    @NotNull
    public final j.a c() {
        return this.c;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull Drawable drawable, @NotNull i request, @NotNull j.a metadata) {
        super((DefaultConstructorMarker) null);
        k.e(drawable, "drawable");
        k.e(request, Progress.REQUEST);
        k.e(metadata, "metadata");
        this.a = drawable;
        this.b = request;
        this.c = metadata;
    }
}
