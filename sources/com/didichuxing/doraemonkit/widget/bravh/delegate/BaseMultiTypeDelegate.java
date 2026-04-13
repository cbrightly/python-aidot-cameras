package com.didichuxing.doraemonkit.widget.bravh.delegate;

import android.util.SparseIntArray;
import androidx.annotation.LayoutRes;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseMultiTypeDelegate.kt */
public abstract class BaseMultiTypeDelegate<T> {
    private boolean autoMode;
    private SparseIntArray layouts;
    private boolean selfMode;

    public BaseMultiTypeDelegate() {
        this((SparseIntArray) null, 1, (DefaultConstructorMarker) null);
    }

    public abstract int getItemType(@NotNull List<? extends T> list, int i);

    public BaseMultiTypeDelegate(@NotNull SparseIntArray layouts2) {
        k.f(layouts2, "layouts");
        this.layouts = layouts2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseMultiTypeDelegate(SparseIntArray sparseIntArray, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new SparseIntArray() : sparseIntArray);
    }

    public final int getLayoutId(int viewType) {
        int layoutResId = this.layouts.get(viewType);
        if (layoutResId != 0) {
            return layoutResId;
        }
        throw new IllegalArgumentException(("ViewType: " + viewType + " found layoutResId，please use registerItemType() first!").toString());
    }

    private final void registerItemType(int type, @LayoutRes int layoutResId) {
        this.layouts.put(type, layoutResId);
    }

    @NotNull
    public final BaseMultiTypeDelegate<T> addItemTypeAutoIncrease(@NotNull @LayoutRes int... layoutResIds) {
        k.f(layoutResIds, "layoutResIds");
        this.autoMode = true;
        checkMode(this.selfMode);
        int length = layoutResIds.length;
        for (int i = 0; i < length; i++) {
            registerItemType(i, layoutResIds[i]);
        }
        return this;
    }

    @NotNull
    public final BaseMultiTypeDelegate<T> addItemType(int type, @LayoutRes int layoutResId) {
        this.selfMode = true;
        checkMode(this.autoMode);
        registerItemType(type, layoutResId);
        return this;
    }

    private final void checkMode(boolean mode) {
        if (!(!mode)) {
            throw new IllegalArgumentException("Don't mess two register mode".toString());
        }
    }
}
