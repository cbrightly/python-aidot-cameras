package com.didichuxing.doraemonkit.aop.method_stack;

import java.util.List;
import org.jetbrains.annotations.Nullable;

/* compiled from: MethodStackBean.kt */
public final class MethodStackBean {
    @Nullable
    private List<MethodStackBean> children;
    @Nullable
    private String costTime;
    @Nullable
    private String function;

    @Nullable
    public final String getFunction() {
        return this.function;
    }

    public final void setFunction(@Nullable String str) {
        this.function = str;
    }

    @Nullable
    public final String getCostTime() {
        return this.costTime;
    }

    public final void setCostTime(@Nullable String str) {
        this.costTime = str;
    }

    @Nullable
    public final List<MethodStackBean> getChildren() {
        return this.children;
    }

    public final void setChildren(@Nullable List<MethodStackBean> list) {
        this.children = list;
    }

    public final void setCostTime(int costTime2) {
        this.costTime = String.valueOf(costTime2) + "ms";
    }
}
