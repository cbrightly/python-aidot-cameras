package com.didichuxing.doraemonkit.widget.bravh;

import com.didichuxing.doraemonkit.widget.bravh.module.BaseDraggableModule;
import com.didichuxing.doraemonkit.widget.bravh.module.BaseLoadMoreModule;
import com.didichuxing.doraemonkit.widget.bravh.module.BaseUpFetchModule;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseQuickAdapter.kt */
public interface BaseQuickAdapterModuleImp {
    @NotNull
    BaseDraggableModule addDraggableModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter);

    @NotNull
    BaseLoadMoreModule addLoadMoreModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter);

    @NotNull
    BaseUpFetchModule addUpFetchModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter);

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 4, 0})
    /* compiled from: BaseQuickAdapter.kt */
    public static final class DefaultImpls {
        @NotNull
        public static BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapterModuleImp $this, @NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
            k.f(baseQuickAdapter, "baseQuickAdapter");
            return new BaseLoadMoreModule(baseQuickAdapter);
        }

        @NotNull
        public static BaseUpFetchModule addUpFetchModule(BaseQuickAdapterModuleImp $this, @NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
            k.f(baseQuickAdapter, "baseQuickAdapter");
            return new BaseUpFetchModule(baseQuickAdapter);
        }

        @NotNull
        public static BaseDraggableModule addDraggableModule(BaseQuickAdapterModuleImp $this, @NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
            k.f(baseQuickAdapter, "baseQuickAdapter");
            return new BaseDraggableModule(baseQuickAdapter);
        }
    }
}
