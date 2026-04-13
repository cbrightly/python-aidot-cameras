package androidx.core.view;

import android.view.View;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: View.kt */
public final class ViewKt$doOnLayout$$inlined$doOnNextLayout$1 implements View.OnLayoutChangeListener {
    final /* synthetic */ l $action$inlined;

    public ViewKt$doOnLayout$$inlined$doOnNextLayout$1(l lVar) {
        this.$action$inlined = lVar;
    }

    public void onLayoutChange(@NotNull View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        k.e(view, "view");
        view.removeOnLayoutChangeListener(this);
        this.$action$inlined.invoke(view);
    }
}
