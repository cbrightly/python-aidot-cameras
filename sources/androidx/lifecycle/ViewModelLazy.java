package androidx.lifecycle;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.g;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.reflect.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003BA\b\u0007\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\u0002\u0010\rJ\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0012\u0010\u000e\u001a\u0004\u0018\u00018\u0000X\u000e¢\u0006\u0004\n\u0002\u0010\u000fR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00028\u00008VX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Landroidx/lifecycle/ViewModelLazy;", "VM", "Landroidx/lifecycle/ViewModel;", "Lkotlin/Lazy;", "viewModelClass", "Lkotlin/reflect/KClass;", "storeProducer", "Lkotlin/Function0;", "Landroidx/lifecycle/ViewModelStore;", "factoryProducer", "Landroidx/lifecycle/ViewModelProvider$Factory;", "extrasProducer", "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "cached", "Landroidx/lifecycle/ViewModel;", "value", "getValue", "()Landroidx/lifecycle/ViewModel;", "isInitialized", "", "lifecycle-viewmodel_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ViewModelLazy.kt */
public final class ViewModelLazy<VM extends ViewModel> implements g<VM> {
    @Nullable
    private VM cached;
    @NotNull
    private final a<CreationExtras> extrasProducer;
    @NotNull
    private final a<ViewModelProvider.Factory> factoryProducer;
    @NotNull
    private final a<ViewModelStore> storeProducer;
    @NotNull
    private final c<VM> viewModelClass;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ViewModelLazy(@NotNull c<VM> cVar, @NotNull a<? extends ViewModelStore> aVar, @NotNull a<? extends ViewModelProvider.Factory> aVar2) {
        this(cVar, aVar, aVar2, (a) null, 8, (DefaultConstructorMarker) null);
        k.e(cVar, "viewModelClass");
        k.e(aVar, "storeProducer");
        k.e(aVar2, "factoryProducer");
    }

    public ViewModelLazy(@NotNull c<VM> viewModelClass2, @NotNull a<? extends ViewModelStore> storeProducer2, @NotNull a<? extends ViewModelProvider.Factory> factoryProducer2, @NotNull a<? extends CreationExtras> extrasProducer2) {
        k.e(viewModelClass2, "viewModelClass");
        k.e(storeProducer2, "storeProducer");
        k.e(factoryProducer2, "factoryProducer");
        k.e(extrasProducer2, "extrasProducer");
        this.viewModelClass = viewModelClass2;
        this.storeProducer = storeProducer2;
        this.factoryProducer = factoryProducer2;
        this.extrasProducer = extrasProducer2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ViewModelLazy(c cVar, a aVar, a aVar2, a aVar3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(cVar, aVar, aVar2, (i & 8) != 0 ? AnonymousClass1.INSTANCE : aVar3);
    }

    @NotNull
    public VM getValue() {
        ViewModel viewModel = this.cached;
        if (viewModel != null) {
            return viewModel;
        }
        ViewModel it = new ViewModelProvider(this.storeProducer.invoke(), this.factoryProducer.invoke(), this.extrasProducer.invoke()).get(kotlin.jvm.a.b(this.viewModelClass));
        this.cached = it;
        return it;
    }

    public boolean isInitialized() {
        return this.cached != null;
    }
}
