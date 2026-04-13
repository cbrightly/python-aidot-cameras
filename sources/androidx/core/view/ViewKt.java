package androidx.core.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.exifinterface.media.ExifInterface;
import kotlin.coroutines.d;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.sequences.h;
import kotlin.sequences.m;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: View.kt */
public final class ViewKt {
    public static final void doOnNextLayout(@NotNull View $this$doOnNextLayout, @NotNull l<? super View, x> action) {
        k.e($this$doOnNextLayout, "<this>");
        k.e(action, "action");
        $this$doOnNextLayout.addOnLayoutChangeListener(new ViewKt$doOnNextLayout$1(action));
    }

    public static final void doOnLayout(@NotNull View $this$doOnLayout, @NotNull l<? super View, x> action) {
        k.e($this$doOnLayout, "<this>");
        k.e(action, "action");
        if (!ViewCompat.isLaidOut($this$doOnLayout) || $this$doOnLayout.isLayoutRequested()) {
            $this$doOnLayout.addOnLayoutChangeListener(new ViewKt$doOnLayout$$inlined$doOnNextLayout$1(action));
        } else {
            action.invoke($this$doOnLayout);
        }
    }

    @NotNull
    public static final OneShotPreDrawListener doOnPreDraw(@NotNull View $this$doOnPreDraw, @NotNull l<? super View, x> action) {
        k.e($this$doOnPreDraw, "<this>");
        k.e(action, "action");
        OneShotPreDrawListener add = OneShotPreDrawListener.add($this$doOnPreDraw, new ViewKt$doOnPreDraw$1(action, $this$doOnPreDraw));
        k.d(add, "View.doOnPreDraw(\n    crossinline action: (view: View) -> Unit\n): OneShotPreDrawListener = OneShotPreDrawListener.add(this) { action(this) }");
        return add;
    }

    public static final void doOnAttach(@NotNull View $this$doOnAttach, @NotNull l<? super View, x> action) {
        k.e($this$doOnAttach, "<this>");
        k.e(action, "action");
        if (ViewCompat.isAttachedToWindow($this$doOnAttach)) {
            action.invoke($this$doOnAttach);
        } else {
            $this$doOnAttach.addOnAttachStateChangeListener(new ViewKt$doOnAttach$1($this$doOnAttach, action));
        }
    }

    public static final void doOnDetach(@NotNull View $this$doOnDetach, @NotNull l<? super View, x> action) {
        k.e($this$doOnDetach, "<this>");
        k.e(action, "action");
        if (!ViewCompat.isAttachedToWindow($this$doOnDetach)) {
            action.invoke($this$doOnDetach);
        } else {
            $this$doOnDetach.addOnAttachStateChangeListener(new ViewKt$doOnDetach$1($this$doOnDetach, action));
        }
    }

    public static /* synthetic */ void updatePaddingRelative$default(View $this$updatePaddingRelative_u24default, int start, int top, int end, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            start = $this$updatePaddingRelative_u24default.getPaddingStart();
        }
        if ((i & 2) != 0) {
            top = $this$updatePaddingRelative_u24default.getPaddingTop();
        }
        if ((i & 4) != 0) {
            end = $this$updatePaddingRelative_u24default.getPaddingEnd();
        }
        if ((i & 8) != 0) {
            bottom = $this$updatePaddingRelative_u24default.getPaddingBottom();
        }
        k.e($this$updatePaddingRelative_u24default, "<this>");
        $this$updatePaddingRelative_u24default.setPaddingRelative(start, top, end, bottom);
    }

    @RequiresApi(17)
    public static final void updatePaddingRelative(@NotNull View $this$updatePaddingRelative, @Px int start, @Px int top, @Px int end, @Px int bottom) {
        k.e($this$updatePaddingRelative, "<this>");
        $this$updatePaddingRelative.setPaddingRelative(start, top, end, bottom);
    }

    public static /* synthetic */ void updatePadding$default(View $this$updatePadding_u24default, int left, int top, int right, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            left = $this$updatePadding_u24default.getPaddingLeft();
        }
        if ((i & 2) != 0) {
            top = $this$updatePadding_u24default.getPaddingTop();
        }
        if ((i & 4) != 0) {
            right = $this$updatePadding_u24default.getPaddingRight();
        }
        if ((i & 8) != 0) {
            bottom = $this$updatePadding_u24default.getPaddingBottom();
        }
        k.e($this$updatePadding_u24default, "<this>");
        $this$updatePadding_u24default.setPadding(left, top, right, bottom);
    }

    public static final void updatePadding(@NotNull View $this$updatePadding, @Px int left, @Px int top, @Px int right, @Px int bottom) {
        k.e($this$updatePadding, "<this>");
        $this$updatePadding.setPadding(left, top, right, bottom);
    }

    public static final void setPadding(@NotNull View $this$setPadding, @Px int size) {
        k.e($this$setPadding, "<this>");
        $this$setPadding.setPadding(size, size, size, size);
    }

    @NotNull
    public static final Runnable postDelayed(@NotNull View $this$postDelayed, long delayInMillis, @NotNull a<x> action) {
        k.e($this$postDelayed, "<this>");
        k.e(action, "action");
        Runnable runnable = new ViewKt$postDelayed$runnable$1(action);
        $this$postDelayed.postDelayed(runnable, delayInMillis);
        return runnable;
    }

    @RequiresApi(16)
    @NotNull
    public static final Runnable postOnAnimationDelayed(@NotNull View $this$postOnAnimationDelayed, long delayInMillis, @NotNull a<x> action) {
        k.e($this$postOnAnimationDelayed, "<this>");
        k.e(action, "action");
        Runnable runnable = new ViewKt$postOnAnimationDelayed$runnable$1(action);
        $this$postOnAnimationDelayed.postOnAnimationDelayed(runnable, delayInMillis);
        return runnable;
    }

    public static /* synthetic */ Bitmap drawToBitmap$default(View view, Bitmap.Config config, int i, Object obj) {
        if ((i & 1) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        return drawToBitmap(view, config);
    }

    @NotNull
    public static final Bitmap drawToBitmap(@NotNull View $this$drawToBitmap, @NotNull Bitmap.Config config) {
        k.e($this$drawToBitmap, "<this>");
        k.e(config, "config");
        if (ViewCompat.isLaidOut($this$drawToBitmap)) {
            Bitmap $this$applyCanvas$iv = Bitmap.createBitmap($this$drawToBitmap.getWidth(), $this$drawToBitmap.getHeight(), config);
            k.d($this$applyCanvas$iv, "createBitmap(width, height, config)");
            Canvas $this$drawToBitmap_u24lambda_u2d1 = new Canvas($this$applyCanvas$iv);
            $this$drawToBitmap_u24lambda_u2d1.translate(-((float) $this$drawToBitmap.getScrollX()), -((float) $this$drawToBitmap.getScrollY()));
            $this$drawToBitmap.draw($this$drawToBitmap_u24lambda_u2d1);
            return $this$applyCanvas$iv;
        }
        throw new IllegalStateException("View needs to be laid out before calling drawToBitmap()");
    }

    public static final boolean isVisible(@NotNull View $this$isVisible) {
        k.e($this$isVisible, "<this>");
        return $this$isVisible.getVisibility() == 0;
    }

    public static final void setVisible(@NotNull View $this$isVisible, boolean value) {
        k.e($this$isVisible, "<this>");
        $this$isVisible.setVisibility(value ? 0 : 8);
    }

    public static final boolean isInvisible(@NotNull View $this$isInvisible) {
        k.e($this$isInvisible, "<this>");
        return $this$isInvisible.getVisibility() == 4;
    }

    public static final void setInvisible(@NotNull View $this$isInvisible, boolean value) {
        k.e($this$isInvisible, "<this>");
        $this$isInvisible.setVisibility(value ? 4 : 0);
    }

    public static final boolean isGone(@NotNull View $this$isGone) {
        k.e($this$isGone, "<this>");
        return $this$isGone.getVisibility() == 8;
    }

    public static final void setGone(@NotNull View $this$isGone, boolean value) {
        k.e($this$isGone, "<this>");
        $this$isGone.setVisibility(value ? 8 : 0);
    }

    public static final void updateLayoutParams(@NotNull View $this$updateLayoutParams, @NotNull l<? super ViewGroup.LayoutParams, x> block) {
        k.e($this$updateLayoutParams, "<this>");
        k.e(block, "block");
        View $this$updateLayoutParams$iv = $this$updateLayoutParams;
        ViewGroup.LayoutParams params$iv = $this$updateLayoutParams$iv.getLayoutParams();
        if (params$iv != null) {
            block.invoke(params$iv);
            $this$updateLayoutParams$iv.setLayoutParams(params$iv);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
    }

    public static final /* synthetic */ <T extends ViewGroup.LayoutParams> void updateLayoutParamsTyped(View $this$updateLayoutParams, l<? super T, x> block) {
        k.e($this$updateLayoutParams, "<this>");
        k.e(block, "block");
        ViewGroup.LayoutParams params = $this$updateLayoutParams.getLayoutParams();
        k.i(1, ExifInterface.GPS_DIRECTION_TRUE);
        block.invoke(params);
        $this$updateLayoutParams.setLayoutParams(params);
    }

    public static final int getMarginLeft(@NotNull View $this$marginLeft) {
        k.e($this$marginLeft, "<this>");
        ViewGroup.LayoutParams layoutParams = $this$marginLeft.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.leftMargin;
    }

    public static final int getMarginTop(@NotNull View $this$marginTop) {
        k.e($this$marginTop, "<this>");
        ViewGroup.LayoutParams layoutParams = $this$marginTop.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.topMargin;
    }

    public static final int getMarginRight(@NotNull View $this$marginRight) {
        k.e($this$marginRight, "<this>");
        ViewGroup.LayoutParams layoutParams = $this$marginRight.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.rightMargin;
    }

    public static final int getMarginBottom(@NotNull View $this$marginBottom) {
        k.e($this$marginBottom, "<this>");
        ViewGroup.LayoutParams layoutParams = $this$marginBottom.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.bottomMargin;
    }

    public static final int getMarginStart(@NotNull View $this$marginStart) {
        k.e($this$marginStart, "<this>");
        ViewGroup.LayoutParams lp = $this$marginStart.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) lp);
        }
        return 0;
    }

    public static final int getMarginEnd(@NotNull View $this$marginEnd) {
        k.e($this$marginEnd, "<this>");
        ViewGroup.LayoutParams lp = $this$marginEnd.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams) lp);
        }
        return 0;
    }

    @NotNull
    public static final h<ViewParent> getAncestors(@NotNull View $this$ancestors) {
        k.e($this$ancestors, "<this>");
        return m.h($this$ancestors.getParent(), ViewKt$ancestors$1.INSTANCE);
    }

    @NotNull
    public static final h<View> getAllViews(@NotNull View $this$allViews) {
        k.e($this$allViews, "<this>");
        return kotlin.sequences.k.b(new ViewKt$allViews$1($this$allViews, (d<? super ViewKt$allViews$1>) null));
    }
}
