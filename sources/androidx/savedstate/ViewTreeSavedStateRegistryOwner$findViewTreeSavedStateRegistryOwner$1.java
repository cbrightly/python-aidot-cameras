package androidx.savedstate;

import android.view.View;
import android.view.ViewParent;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Landroid/view/View;", "view", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* compiled from: ViewTreeSavedStateRegistryOwner.kt */
public final class ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1 extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<View, View> {
    public static final ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1 INSTANCE = new ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1();

    ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1() {
        super(1);
    }

    @Nullable
    public final View invoke(@NotNull View view) {
        k.e(view, "view");
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }
}
