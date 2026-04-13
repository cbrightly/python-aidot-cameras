package androidx.savedstate;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.sequences.m;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a\u0013\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u001b\u0010\u0004\u001a\u00020\u0005*\u00020\u00022\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\b\u0007¨\u0006\b"}, d2 = {"findViewTreeSavedStateRegistryOwner", "Landroidx/savedstate/SavedStateRegistryOwner;", "Landroid/view/View;", "get", "setViewTreeSavedStateRegistryOwner", "", "owner", "set", "savedstate_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ViewTreeSavedStateRegistryOwner.kt */
public final class ViewTreeSavedStateRegistryOwner {
    public static final void set(@NotNull View $this$setViewTreeSavedStateRegistryOwner, @Nullable SavedStateRegistryOwner owner) {
        k.e($this$setViewTreeSavedStateRegistryOwner, "<this>");
        $this$setViewTreeSavedStateRegistryOwner.setTag(R.id.view_tree_saved_state_registry_owner, owner);
    }

    @Nullable
    public static final SavedStateRegistryOwner get(@NotNull View $this$findViewTreeSavedStateRegistryOwner) {
        k.e($this$findViewTreeSavedStateRegistryOwner, "<this>");
        return (SavedStateRegistryOwner) o.r(o.x(m.h($this$findViewTreeSavedStateRegistryOwner, ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1.INSTANCE), ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$2.INSTANCE));
    }
}
