package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterInfo.kt */
public final class DCLVendorListBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private PaginationBean pagination;
    @Nullable
    private ArrayList<DCLVendorInfoBean> vendorInfo;

    public DCLVendorListBean() {
        this((ArrayList) null, (PaginationBean) null, 3, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ DCLVendorListBean copy$default(DCLVendorListBean dCLVendorListBean, ArrayList<DCLVendorInfoBean> arrayList, PaginationBean paginationBean, int i, Object obj) {
        Object[] objArr = {dCLVendorListBean, arrayList, paginationBean, new Integer(i), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 6167, new Class[]{DCLVendorListBean.class, ArrayList.class, PaginationBean.class, Integer.TYPE, Object.class}, DCLVendorListBean.class);
        if (proxy.isSupported) {
            return (DCLVendorListBean) proxy.result;
        }
        if ((i & 1) != 0) {
            arrayList = dCLVendorListBean.vendorInfo;
        }
        if ((i & 2) != 0) {
            paginationBean = dCLVendorListBean.pagination;
        }
        return dCLVendorListBean.copy(arrayList, paginationBean);
    }

    @Nullable
    public final ArrayList<DCLVendorInfoBean> component1() {
        return this.vendorInfo;
    }

    @Nullable
    public final PaginationBean component2() {
        return this.pagination;
    }

    @NotNull
    public final DCLVendorListBean copy(@Nullable ArrayList<DCLVendorInfoBean> arrayList, @Nullable PaginationBean paginationBean) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{arrayList, paginationBean}, this, changeQuickRedirect2, false, 6166, new Class[]{ArrayList.class, PaginationBean.class}, DCLVendorListBean.class);
        if (proxy.isSupported) {
            return (DCLVendorListBean) proxy.result;
        }
        return new DCLVendorListBean(arrayList, paginationBean);
    }

    public boolean equals(@Nullable Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6170, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DCLVendorListBean)) {
            return false;
        }
        DCLVendorListBean dCLVendorListBean = (DCLVendorListBean) obj;
        return k.a(this.vendorInfo, dCLVendorListBean.vendorInfo) && k.a(this.pagination, dCLVendorListBean.pagination);
    }

    public int hashCode() {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6169, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        ArrayList<DCLVendorInfoBean> arrayList = this.vendorInfo;
        int hashCode = (arrayList == null ? 0 : arrayList.hashCode()) * 31;
        PaginationBean paginationBean = this.pagination;
        if (paginationBean != null) {
            i = paginationBean.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6168, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "DCLVendorListBean(vendorInfo=" + this.vendorInfo + ", pagination=" + this.pagination + ')';
    }

    public DCLVendorListBean(@Nullable ArrayList<DCLVendorInfoBean> vendorInfo2, @Nullable PaginationBean pagination2) {
        this.vendorInfo = vendorInfo2;
        this.pagination = pagination2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DCLVendorListBean(ArrayList arrayList, PaginationBean paginationBean, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : arrayList, (i & 2) != 0 ? null : paginationBean);
    }

    @Nullable
    public final ArrayList<DCLVendorInfoBean> getVendorInfo() {
        return this.vendorInfo;
    }

    public final void setVendorInfo(@Nullable ArrayList<DCLVendorInfoBean> arrayList) {
        this.vendorInfo = arrayList;
    }

    @Nullable
    public final PaginationBean getPagination() {
        return this.pagination;
    }

    public final void setPagination(@Nullable PaginationBean paginationBean) {
        this.pagination = paginationBean;
    }
}
