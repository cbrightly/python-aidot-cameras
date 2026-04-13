package coil.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PostProcessor;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import coil.transform.c;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: coil.util.-GifExtensions  reason: invalid class name */
/* compiled from: Extensions.kt */
public final class GifExtensions {

    /* renamed from: coil.util.-GifExtensions$a */
    /* compiled from: Extensions.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.values().length];
            iArr[c.UNCHANGED.ordinal()] = 1;
            iArr[c.TRANSLUCENT.ordinal()] = 2;
            iArr[c.OPAQUE.ordinal()] = 3;
            a = iArr;
        }
    }

    @RequiresApi(28)
    @NotNull
    public static final PostProcessor c(@NotNull coil.transform.a $this$asPostProcessor) {
        k.e($this$asPostProcessor, "<this>");
        return new b($this$asPostProcessor);
    }

    /* access modifiers changed from: private */
    public static final int d(coil.transform.a $this_asPostProcessor, Canvas canvas) {
        k.e($this_asPostProcessor, "$this_asPostProcessor");
        k.e(canvas, "canvas");
        return e($this_asPostProcessor.a(canvas));
    }

    public static final int e(@NotNull c $this$flag) {
        k.e($this$flag, "<this>");
        switch (a.a[$this$flag.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return -3;
            case 3:
                return -1;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* renamed from: coil.util.-GifExtensions$b */
    /* compiled from: Extensions.kt */
    public static final class b extends Animatable2.AnimationCallback {
        final /* synthetic */ kotlin.jvm.functions.a<x> a;
        final /* synthetic */ kotlin.jvm.functions.a<x> b;

        b(kotlin.jvm.functions.a<x> $onStart, kotlin.jvm.functions.a<x> $onEnd) {
            this.a = $onStart;
            this.b = $onEnd;
        }

        public void onAnimationStart(@Nullable Drawable drawable) {
            kotlin.jvm.functions.a<x> aVar = this.a;
            if (aVar != null) {
                aVar.invoke();
            }
        }

        public void onAnimationEnd(@Nullable Drawable drawable) {
            kotlin.jvm.functions.a<x> aVar = this.b;
            if (aVar != null) {
                aVar.invoke();
            }
        }
    }

    @RequiresApi(23)
    @NotNull
    public static final Animatable2.AnimationCallback a(@Nullable kotlin.jvm.functions.a<x> onStart, @Nullable kotlin.jvm.functions.a<x> onEnd) {
        return new b(onStart, onEnd);
    }

    @NotNull
    public static final Animatable2Compat.AnimationCallback b(@Nullable kotlin.jvm.functions.a<x> onStart, @Nullable kotlin.jvm.functions.a<x> onEnd) {
        return new GifExtensions$animatable2CompatCallbackOf$1(onStart, onEnd);
    }

    public static final boolean f(@NotNull Bitmap.Config $this$isHardware) {
        k.e($this$isHardware, "<this>");
        return Build.VERSION.SDK_INT >= 26 && $this$isHardware == Bitmap.Config.HARDWARE;
    }
}
