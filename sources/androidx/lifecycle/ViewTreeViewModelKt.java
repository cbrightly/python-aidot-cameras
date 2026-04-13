package androidx.lifecycle;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"findViewTreeViewModelStoreOwner", "Landroidx/lifecycle/ViewModelStoreOwner;", "Landroid/view/View;", "lifecycle-viewmodel_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ViewTreeViewModel.kt */
public final class ViewTreeViewModelKt {
    @Nullable
    public static final ViewModelStoreOwner findViewTreeViewModelStoreOwner(@NotNull View $this$findViewTreeViewModelStoreOwner) {
        k.e($this$findViewTreeViewModelStoreOwner, "<this>");
        return ViewTreeViewModelStoreOwner.get($this$findViewTreeViewModelStoreOwner);
    }
}
