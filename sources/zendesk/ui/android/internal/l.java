package zendesk.ui.android.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.ColorInt;
import androidx.annotation.Px;
import com.google.android.material.shape.MaterialShapeDrawable;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$dimen;

/* compiled from: View.kt */
public final class l {
    public static /* synthetic */ void g(View view, int i, float f, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            Context context = view.getContext();
            k.d(context, "fun View.outlinedBoxBack…ize(borderRadius)\n    }\n}");
            i = d.a(d.b(context, R$attr.colorOnSurface), 0.12f);
        }
        if ((i2 & 2) != 0) {
            f = view.getResources().getDimension(R$dimen.zuia_message_cell_radius);
        }
        f(view, i, f);
    }

    public static final void f(@NotNull View $this$outlinedBoxBackground, @ColorInt int borderColor, @Px float borderRadius) {
        k.e($this$outlinedBoxBackground, "<this>");
        MaterialShapeDrawable createWithElevationOverlay = MaterialShapeDrawable.createWithElevationOverlay($this$outlinedBoxBackground.getContext());
        MaterialShapeDrawable $this$outlinedBoxBackground_u24lambda_u2d0 = createWithElevationOverlay;
        $this$outlinedBoxBackground_u24lambda_u2d0.setStrokeWidth($this$outlinedBoxBackground.getResources().getDimension(R$dimen.zuia_divider_size));
        $this$outlinedBoxBackground_u24lambda_u2d0.setStrokeColor(ColorStateList.valueOf(borderColor));
        $this$outlinedBoxBackground_u24lambda_u2d0.setCornerSize(borderRadius);
        x xVar = x.a;
        $this$outlinedBoxBackground.setBackground(createWithElevationOverlay);
    }

    public static final void b(@NotNull View $this$focusAndShowKeyboard) {
        k.e($this$focusAndShowKeyboard, "<this>");
        $this$focusAndShowKeyboard.requestFocus();
        if ($this$focusAndShowKeyboard.hasWindowFocus()) {
            h($this$focusAndShowKeyboard);
        } else {
            $this$focusAndShowKeyboard.getViewTreeObserver().addOnWindowFocusChangeListener(new a($this$focusAndShowKeyboard));
        }
    }

    /* compiled from: View.kt */
    public static final class a implements ViewTreeObserver.OnWindowFocusChangeListener {
        final /* synthetic */ View a;

        a(View $receiver) {
            this.a = $receiver;
        }

        public void onWindowFocusChanged(boolean hasFocus) {
            if (hasFocus) {
                this.a.getViewTreeObserver().removeOnWindowFocusChangeListener(this);
                l.h(this.a);
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void h(View $this$showKeyboardNow) {
        if ($this$showKeyboardNow.isFocused()) {
            $this$showKeyboardNow.post(new c($this$showKeyboardNow));
        }
    }

    /* access modifiers changed from: private */
    public static final void i(View $this_showKeyboardNow) {
        k.e($this_showKeyboardNow, "$this_showKeyboardNow");
        Object systemService = $this_showKeyboardNow.getContext().getSystemService("input_method");
        if (systemService != null) {
            ((InputMethodManager) systemService).showSoftInput($this_showKeyboardNow, 1);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
    }

    public static /* synthetic */ void d(View view, int i, float f, float f2, float f3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            Context context = view.getContext();
            k.d(context, "fun View.glowingBoxBackg…rInset,\n        )\n    }\n}");
            i = d.b(context, R$attr.colorAccent);
        }
        if ((i2 & 2) != 0) {
            f = view.getResources().getDimension(R$dimen.zuia_message_cell_radius);
        }
        if ((i2 & 4) != 0) {
            f2 = view.getResources().getDimension(R$dimen.zuia_outer_stroke_width);
        }
        if ((i2 & 8) != 0) {
            f3 = view.getResources().getDimension(R$dimen.zuia_inner_stroke_width);
        }
        c(view, i, f, f2, f3);
    }

    public static final void c(@NotNull View $this$glowingBoxBackground, @ColorInt int borderColor, @Px float borderRadius, @Px float outerStrokeWidth, @Px float innerStrokeWidth) {
        View view = $this$glowingBoxBackground;
        int i = borderColor;
        float f = borderRadius;
        float f2 = outerStrokeWidth;
        float f3 = innerStrokeWidth;
        k.e(view, "<this>");
        MaterialShapeDrawable createWithElevationOverlay = MaterialShapeDrawable.createWithElevationOverlay($this$glowingBoxBackground.getContext());
        MaterialShapeDrawable $this$glowingBoxBackground_u24lambda_u2d2 = createWithElevationOverlay;
        $this$glowingBoxBackground_u24lambda_u2d2.setStrokeWidth(f2 + f3);
        $this$glowingBoxBackground_u24lambda_u2d2.setStrokeColor(new ColorStateList(new int[][]{new int[0]}, new int[]{d.a(i, 0.35f)}));
        $this$glowingBoxBackground_u24lambda_u2d2.setCornerSize(f);
        x xVar = x.a;
        MaterialShapeDrawable createWithElevationOverlay2 = MaterialShapeDrawable.createWithElevationOverlay($this$glowingBoxBackground.getContext());
        MaterialShapeDrawable $this$glowingBoxBackground_u24lambda_u2d3 = createWithElevationOverlay2;
        $this$glowingBoxBackground_u24lambda_u2d3.setStrokeWidth(f3);
        $this$glowingBoxBackground_u24lambda_u2d3.setStrokeColor(new ColorStateList(new int[][]{new int[0]}, new int[]{i}));
        $this$glowingBoxBackground_u24lambda_u2d3.setCornerSize(f);
        LayerDrawable $this$glowingBoxBackground_u24lambda_u2d4 = new LayerDrawable(new MaterialShapeDrawable[]{createWithElevationOverlay, createWithElevationOverlay2});
        int layerInset = ((int) f2) * -1;
        $this$glowingBoxBackground_u24lambda_u2d4.setLayerInset(0, layerInset, layerInset, layerInset, layerInset);
        view.setBackground($this$glowingBoxBackground_u24lambda_u2d4);
    }
}
