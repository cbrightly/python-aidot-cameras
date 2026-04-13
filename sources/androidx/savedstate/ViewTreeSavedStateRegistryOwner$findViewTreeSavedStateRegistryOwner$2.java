package androidx.savedstate;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Landroidx/savedstate/SavedStateRegistryOwner;", "view", "Landroid/view/View;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* compiled from: ViewTreeSavedStateRegistryOwner.kt */
public final class ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$2 extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<View, SavedStateRegistryOwner> {
    public static final ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$2 INSTANCE = new ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$2();

    ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$2() {
        super(1);
    }

    @Nullable
    public final SavedStateRegistryOwner invoke(@NotNull View view) {
        k.e(view, "view");
        Object tag = view.getTag(R.id.view_tree_saved_state_registry_owner);
        if (tag instanceof SavedStateRegistryOwner) {
            return (SavedStateRegistryOwner) tag;
        }
        return null;
    }
}
