package androidx.activity;

import androidx.annotation.MainThread;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import kotlin.g;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;

/* compiled from: ActivityViewModelLazy.kt */
public final class ActivityViewModelLazyKt {
    public static /* synthetic */ g viewModels$default(ComponentActivity $this$viewModels_u24default, a factoryProducer, int i, Object obj) {
        if ((i & 1) != 0) {
            factoryProducer = null;
        }
        k.e($this$viewModels_u24default, "<this>");
        a factoryPromise = factoryProducer == null ? new ActivityViewModelLazyKt$viewModels$factoryPromise$1($this$viewModels_u24default) : factoryProducer;
        k.i(4, "VM");
        return new ViewModelLazy(a0.b(ViewModel.class), new ActivityViewModelLazyKt$viewModels$1($this$viewModels_u24default), factoryPromise);
    }

    @MainThread
    public static final /* synthetic */ <VM extends ViewModel> g<VM> viewModels(ComponentActivity $this$viewModels, a<? extends ViewModelProvider.Factory> factoryProducer) {
        k.e($this$viewModels, "<this>");
        a factoryPromise = factoryProducer == null ? new ActivityViewModelLazyKt$viewModels$factoryPromise$1($this$viewModels) : factoryProducer;
        k.i(4, "VM");
        return new ViewModelLazy(a0.b(ViewModel.class), new ActivityViewModelLazyKt$viewModels$1($this$viewModels), factoryPromise);
    }
}
