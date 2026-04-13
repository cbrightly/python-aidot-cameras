package coil.size;

import android.content.Context;
import android.util.DisplayMetrics;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DisplaySizeResolver.kt */
public final class a implements f {
    @NotNull
    private final Context c;

    public a(@NotNull Context context) {
        k.e(context, "context");
        this.c = context;
    }

    @Nullable
    public Object b(@NotNull d<? super Size> $completion) {
        DisplayMetrics $this$size_u24lambda_u2d0 = this.c.getResources().getDisplayMetrics();
        return new PixelSize($this$size_u24lambda_u2d0.widthPixels, $this$size_u24lambda_u2d0.heightPixels);
    }

    public boolean equals(@Nullable Object other) {
        return this == other || ((other instanceof a) && k.a(this.c, ((a) other).c));
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "DisplaySizeResolver(context=" + this.c + ')';
    }
}
