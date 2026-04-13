package coil.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import coil.bitmap.e;
import coil.request.m;
import kotlin.coroutines.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TargetDelegate.kt */
public final class j extends s {
    @NotNull
    private final e a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(@NotNull e referenceCounter) {
        super((DefaultConstructorMarker) null);
        k.e(referenceCounter, "referenceCounter");
        this.a = referenceCounter;
    }

    @Nullable
    public Object f(@NotNull m result, @NotNull d<? super x> $completion) {
        e $this$setValid$iv = this.a;
        Drawable a2 = result.a();
        Bitmap bitmap = null;
        BitmapDrawable bitmapDrawable = a2 instanceof BitmapDrawable ? (BitmapDrawable) a2 : null;
        if (bitmapDrawable != null) {
            bitmap = bitmapDrawable.getBitmap();
        }
        Bitmap bitmap$iv = bitmap;
        if (bitmap$iv != null) {
            $this$setValid$iv.a(bitmap$iv, false);
        }
        return x.a;
    }
}
