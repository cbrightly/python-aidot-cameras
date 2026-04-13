package com.didichuxing.doraemonkit.widget.bravh;

import android.widget.FrameLayout;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.m;
import kotlin.l;
import kotlin.reflect.e;
import org.jetbrains.annotations.Nullable;

@l(d1 = {}, d2 = {}, k = 3, mv = {1, 4, 0})
/* compiled from: BaseQuickAdapter.kt */
public final /* synthetic */ class BaseQuickAdapter$setEmptyView$1 extends m {
    BaseQuickAdapter$setEmptyView$1(BaseQuickAdapter baseQuickAdapter) {
        super(baseQuickAdapter);
    }

    public String getName() {
        return "mEmptyLayout";
    }

    public e getOwner() {
        return a0.b(BaseQuickAdapter.class);
    }

    public String getSignature() {
        return "getMEmptyLayout()Landroid/widget/FrameLayout;";
    }

    @Nullable
    public Object get() {
        return BaseQuickAdapter.access$getMEmptyLayout$p((BaseQuickAdapter) this.receiver);
    }

    public void set(@Nullable Object value) {
        ((BaseQuickAdapter) this.receiver).mEmptyLayout = (FrameLayout) value;
    }
}
