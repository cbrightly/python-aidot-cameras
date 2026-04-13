package com.didichuxing.doraemonkit.widget.bravh;

import android.util.SparseArray;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseItemProvider;
import kotlin.jvm.functions.a;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001\"\u0004\b\u0000\u0010\u0000H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"T", "Landroid/util/SparseArray;", "Lcom/didichuxing/doraemonkit/widget/bravh/provider/BaseItemProvider;", "invoke", "()Landroid/util/SparseArray;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: BaseProviderMultiAdapter.kt */
public final class BaseProviderMultiAdapter$mItemProviders$2 extends kotlin.jvm.internal.l implements a<SparseArray<BaseItemProvider<T>>> {
    public static final BaseProviderMultiAdapter$mItemProviders$2 INSTANCE = new BaseProviderMultiAdapter$mItemProviders$2();

    BaseProviderMultiAdapter$mItemProviders$2() {
        super(0);
    }

    @NotNull
    public final SparseArray<BaseItemProvider<T>> invoke() {
        return new SparseArray<>();
    }
}
