package androidx.lifecycle;

import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.v2;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0015\u0010\u0002\u001a\u00020\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"JOB_KEY", "", "viewModelScope", "Lkotlinx/coroutines/CoroutineScope;", "Landroidx/lifecycle/ViewModel;", "getViewModelScope", "(Landroidx/lifecycle/ViewModel;)Lkotlinx/coroutines/CoroutineScope;", "lifecycle-viewmodel-ktx_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ViewModel.kt */
public final class ViewModelKt {
    @NotNull
    private static final String JOB_KEY = "androidx.lifecycle.ViewModelCoroutineScope.JOB_KEY";

    @NotNull
    public static final o0 getViewModelScope(@NotNull ViewModel $this$viewModelScope) {
        k.e($this$viewModelScope, "<this>");
        o0 scope = (o0) $this$viewModelScope.getTag(JOB_KEY);
        if (scope != null) {
            return scope;
        }
        Object tagIfAbsent = $this$viewModelScope.setTagIfAbsent(JOB_KEY, new CloseableCoroutineScope(v2.b((z1) null, 1, (Object) null).plus(d1.c().W())));
        k.d(tagIfAbsent, "setTagIfAbsent(\n        …Main.immediate)\n        )");
        return (o0) tagIfAbsent;
    }
}
