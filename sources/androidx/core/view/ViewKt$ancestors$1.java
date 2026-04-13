package androidx.core.view;

import android.view.ViewParent;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {}, d2 = {}, k = 3, mv = {1, 5, 1})
/* compiled from: View.kt */
public final /* synthetic */ class ViewKt$ancestors$1 extends i implements kotlin.jvm.functions.l<ViewParent, ViewParent> {
    public static final ViewKt$ancestors$1 INSTANCE = new ViewKt$ancestors$1();

    ViewKt$ancestors$1() {
        super(1, ViewParent.class, "getParent", "getParent()Landroid/view/ViewParent;", 0);
    }

    public final ViewParent invoke(@NotNull ViewParent p0) {
        k.e(p0, "p0");
        return p0.getParent();
    }
}
