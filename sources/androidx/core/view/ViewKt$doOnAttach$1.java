package androidx.core.view;

import android.view.View;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: View.kt */
public final class ViewKt$doOnAttach$1 implements View.OnAttachStateChangeListener {
    final /* synthetic */ l<View, x> $action;
    final /* synthetic */ View $this_doOnAttach;

    public ViewKt$doOnAttach$1(View $receiver, l<? super View, x> $action2) {
        this.$this_doOnAttach = $receiver;
        this.$action = $action2;
    }

    public void onViewAttachedToWindow(@NotNull View view) {
        k.e(view, "view");
        this.$this_doOnAttach.removeOnAttachStateChangeListener(this);
        this.$action.invoke(view);
    }

    public void onViewDetachedFromWindow(@NotNull View view) {
        k.e(view, "view");
    }
}
