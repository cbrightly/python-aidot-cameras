package coil.util;

import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import kotlin.jvm.functions.a;
import kotlin.x;
import org.jetbrains.annotations.Nullable;

/* renamed from: coil.util.-GifExtensions$animatable2CompatCallbackOf$1  reason: invalid class name */
/* compiled from: Extensions.kt */
public final class GifExtensions$animatable2CompatCallbackOf$1 extends Animatable2Compat.AnimationCallback {
    final /* synthetic */ a<x> a;
    final /* synthetic */ a<x> b;

    GifExtensions$animatable2CompatCallbackOf$1(a<x> $onStart, a<x> $onEnd) {
        this.a = $onStart;
        this.b = $onEnd;
    }

    public void onAnimationStart(@Nullable Drawable drawable) {
        a<x> aVar = this.a;
        if (aVar != null) {
            aVar.invoke();
        }
    }

    public void onAnimationEnd(@Nullable Drawable drawable) {
        a<x> aVar = this.b;
        if (aVar != null) {
            aVar.invoke();
        }
    }
}
