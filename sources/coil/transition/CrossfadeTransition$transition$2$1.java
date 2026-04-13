package coil.transition;

import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import coil.drawable.CrossfadeDrawable;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.Nullable;

/* compiled from: CrossfadeTransition.kt */
public final class CrossfadeTransition$transition$2$1 extends Animatable2Compat.AnimationCallback {
    final /* synthetic */ CrossfadeDrawable a;
    final /* synthetic */ n<x> b;

    CrossfadeTransition$transition$2$1(CrossfadeDrawable $crossfade, n<? super x> $continuation) {
        this.a = $crossfade;
        this.b = $continuation;
    }

    public void onAnimationEnd(@Nullable Drawable drawable) {
        this.a.unregisterAnimationCallback(this);
        n<x> nVar = this.b;
        x xVar = x.a;
        o.a aVar = o.Companion;
        nVar.resumeWith(o.m17constructorimpl(xVar));
    }
}
