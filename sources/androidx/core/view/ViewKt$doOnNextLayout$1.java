package androidx.core.view;

import android.view.View;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: View.kt */
public final class ViewKt$doOnNextLayout$1 implements View.OnLayoutChangeListener {
    final /* synthetic */ l<View, x> $action;

    public ViewKt$doOnNextLayout$1(l<? super View, x> $action2) {
        this.$action = $action2;
    }

    public void onLayoutChange(@NotNull View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        k.e(view, "view");
        view.removeOnLayoutChangeListener(this);
        this.$action.invoke(view);
    }
}
