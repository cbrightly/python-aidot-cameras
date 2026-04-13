package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterInfo.kt */
public final class PaginationBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private String next_key;
    private int total;

    public PaginationBean() {
        this((String) null, 0, 3, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ PaginationBean copy$default(PaginationBean paginationBean, String str, int i, int i2, Object obj) {
        Object[] objArr = {paginationBean, str, new Integer(i), new Integer(i2), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 6197, new Class[]{PaginationBean.class, String.class, cls, cls, Object.class}, PaginationBean.class);
        if (proxy.isSupported) {
            return (PaginationBean) proxy.result;
        }
        if ((i2 & 1) != 0) {
            str = paginationBean.next_key;
        }
        if ((i2 & 2) != 0) {
            i = paginationBean.total;
        }
        return paginationBean.copy(str, i);
    }

    @Nullable
    public final String component1() {
        return this.next_key;
    }

    public final int component2() {
        return this.total;
    }

    @NotNull
    public final PaginationBean copy(@Nullable String str, int i) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str, new Integer(i)}, this, changeQuickRedirect, false, 6196, new Class[]{String.class, Integer.TYPE}, PaginationBean.class);
        if (proxy.isSupported) {
            return (PaginationBean) proxy.result;
        }
        return new PaginationBean(str, i);
    }

    public boolean equals(@Nullable Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6200, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PaginationBean)) {
            return false;
        }
        PaginationBean paginationBean = (PaginationBean) obj;
        return k.a(this.next_key, paginationBean.next_key) && this.total == paginationBean.total;
    }

    public int hashCode() {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6199, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String str = this.next_key;
        if (str != null) {
            i = str.hashCode();
        }
        return (i * 31) + this.total;
    }

    @NotNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6198, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "PaginationBean(next_key=" + this.next_key + ", total=" + this.total + ')';
    }

    public PaginationBean(@Nullable String next_key2, int total2) {
        this.next_key = next_key2;
        this.total = total2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ PaginationBean(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : str, (i2 & 2) != 0 ? 0 : i);
    }

    @Nullable
    public final String getNext_key() {
        return this.next_key;
    }

    public final void setNext_key(@Nullable String str) {
        this.next_key = str;
    }

    public final int getTotal() {
        return this.total;
    }

    public final void setTotal(int i) {
        this.total = i;
    }
}
