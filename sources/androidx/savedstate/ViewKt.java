package androidx.savedstate;

import android.view.View;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: View.kt */
public final class ViewKt {
    @Nullable
    public static final SavedStateRegistryOwner findViewTreeSavedStateRegistryOwner(@NotNull View $this$findViewTreeSavedStateRegistryOwner) {
        k.e($this$findViewTreeSavedStateRegistryOwner, "$this$findViewTreeSavedStateRegistryOwner");
        return ViewTreeSavedStateRegistryOwner.get($this$findViewTreeSavedStateRegistryOwner);
    }
}
