package androidx.lifecycle;

import kotlin.jvm.functions.a;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Landroidx/lifecycle/SavedStateHandlesVM;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* compiled from: SavedStateHandleSupport.kt */
public final class SavedStateHandlesProvider$viewModel$2 extends kotlin.jvm.internal.l implements a<SavedStateHandlesVM> {
    final /* synthetic */ ViewModelStoreOwner $viewModelStoreOwner;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SavedStateHandlesProvider$viewModel$2(ViewModelStoreOwner viewModelStoreOwner) {
        super(0);
        this.$viewModelStoreOwner = viewModelStoreOwner;
    }

    @NotNull
    public final SavedStateHandlesVM invoke() {
        return SavedStateHandleSupport.getSavedStateHandlesVM(this.$viewModelStoreOwner);
    }
}
