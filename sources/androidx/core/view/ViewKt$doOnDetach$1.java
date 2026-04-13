package androidx.core.view;

import android.view.View;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: View.kt */
public final class ViewKt$doOnDetach$1 implements View.OnAttachStateChangeListener {
    final /* synthetic */ l<View, x> $action;
    final /* synthetic */ View $this_doOnDetach;

    public ViewKt$doOnDetach$1(View $receiver, l<? super View, x> $action2) {
        this.$this_doOnDetach = $receiver;
        this.$action = $action2;
    }

    public void onViewAttachedToWindow(@NotNull View view) {
        k.e(view, "view");
    }

    public void onViewDetachedFromWindow(@NotNull View view) {
        k.e(view, "view");
        this.$this_doOnDetach.removeOnAttachStateChangeListener(this);
        this.$action.invoke(view);
    }
}
