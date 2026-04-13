package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a7\u0010\u0007\u001a\u00020\u0005\"\n\b\u0000\u0010\b\u0018\u0001*\u00020\t*\u00020\u00042\u0019\b\b\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\b0\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u000b"}, d2 = {"viewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "builder", "Lkotlin/Function1;", "Landroidx/lifecycle/viewmodel/InitializerViewModelFactoryBuilder;", "", "Lkotlin/ExtensionFunctionType;", "initializer", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "lifecycle-viewmodel_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: InitializerViewModelFactory.kt */
public final class InitializerViewModelFactoryKt {
    @NotNull
    public static final ViewModelProvider.Factory viewModelFactory(@NotNull kotlin.jvm.functions.l<? super InitializerViewModelFactoryBuilder, x> builder) {
        k.e(builder, "builder");
        InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder = new InitializerViewModelFactoryBuilder();
        builder.invoke(initializerViewModelFactoryBuilder);
        return initializerViewModelFactoryBuilder.build();
    }

    public static final /* synthetic */ <VM extends ViewModel> void initializer(InitializerViewModelFactoryBuilder $this$initializer, kotlin.jvm.functions.l<? super CreationExtras, ? extends VM> initializer) {
        k.e($this$initializer, "<this>");
        k.e(initializer, "initializer");
        k.i(4, "VM");
        $this$initializer.addInitializer(a0.b(ViewModel.class), initializer);
    }
}
