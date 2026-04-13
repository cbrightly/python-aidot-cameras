package com.leedarson.bean;

import com.google.chip.chiptool.setuppayloadscanner.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProductRecommend.kt */
public final class ProductRecommend {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final long createBy;
    private final long createTime;
    @NotNull
    private final String description;
    private final long id;
    private final int isEnabled;
    @NotNull
    private final List<String> modelList;
    @NotNull
    private final String moreAddress;
    @NotNull
    private final String moreTitle;
    @NotNull
    private final List<Sku> skuList;
    @NotNull
    private final String title;
    private final long updateBy;
    private final long updateTime;

    public static /* synthetic */ ProductRecommend copy$default(ProductRecommend productRecommend, long j, long j2, String str, long j3, int i, List list, String str2, String str3, List list2, String str4, long j4, long j5, int i2, Object obj) {
        ProductRecommend productRecommend2 = productRecommend;
        int i3 = i2;
        Class<String> cls = String.class;
        long j6 = j;
        long j7 = j2;
        int i4 = i;
        Object[] objArr = {productRecommend2, new Long(j6), new Long(j7), str, new Long(j3), new Integer(i4), list, str2, str3, list2, str4, new Long(j4), new Long(j5), new Integer(i3), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Long.TYPE;
        Class cls3 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 1053, new Class[]{ProductRecommend.class, cls2, cls2, cls, cls2, cls3, List.class, cls, cls, List.class, cls, cls2, cls2, cls3, Object.class}, ProductRecommend.class);
        if (proxy.isSupported) {
            return (ProductRecommend) proxy.result;
        }
        long j8 = (i3 & 1) != 0 ? productRecommend2.createBy : j6;
        if ((i3 & 2) != 0) {
            j7 = productRecommend2.createTime;
        }
        String str5 = (i3 & 4) != 0 ? productRecommend2.description : str;
        long j9 = (i3 & 8) != 0 ? productRecommend2.id : j3;
        if ((i3 & 16) != 0) {
            i4 = productRecommend2.isEnabled;
        }
        return productRecommend.copy(j8, j7, str5, j9, i4, (i3 & 32) != 0 ? productRecommend2.modelList : list, (i3 & 64) != 0 ? productRecommend2.moreAddress : str2, (i3 & 128) != 0 ? productRecommend2.moreTitle : str3, (i3 & 256) != 0 ? productRecommend2.skuList : list2, (i3 & 512) != 0 ? productRecommend2.title : str4, (i3 & 1024) != 0 ? productRecommend2.updateBy : j4, (i3 & 2048) != 0 ? productRecommend2.updateTime : j5);
    }

    public final long component1() {
        return this.createBy;
    }

    @NotNull
    public final String component10() {
        return this.title;
    }

    public final long component11() {
        return this.updateBy;
    }

    public final long component12() {
        return this.updateTime;
    }

    public final long component2() {
        return this.createTime;
    }

    @NotNull
    public final String component3() {
        return this.description;
    }

    public final long component4() {
        return this.id;
    }

    public final int component5() {
        return this.isEnabled;
    }

    @NotNull
    public final List<String> component6() {
        return this.modelList;
    }

    @NotNull
    public final String component7() {
        return this.moreAddress;
    }

    @NotNull
    public final String component8() {
        return this.moreTitle;
    }

    @NotNull
    public final List<Sku> component9() {
        return this.skuList;
    }

    @NotNull
    public final ProductRecommend copy(long j, long j2, @NotNull String str, long j3, int i, @NotNull List<String> list, @NotNull String str2, @NotNull String str3, @NotNull List<Sku> list2, @NotNull String str4, long j4, long j5) {
        Class<String> cls = String.class;
        Object[] objArr = {new Long(j), new Long(j2), str, new Long(j3), new Integer(i), list, str2, str3, list2, str4, new Long(j4), new Long(j5)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Long.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1052, new Class[]{cls2, cls2, cls, cls2, Integer.TYPE, List.class, cls, cls, List.class, cls, cls2, cls2}, ProductRecommend.class);
        if (proxy.isSupported) {
            return (ProductRecommend) proxy.result;
        }
        List<Sku> list3 = list2;
        List<Sku> list4 = list3;
        String str5 = str4;
        String str6 = str5;
        int i2 = i;
        List<String> list5 = list;
        List<String> list6 = list5;
        String str7 = str3;
        String str8 = str7;
        String str9 = str2;
        String str10 = str9;
        long j6 = j;
        long j7 = j2;
        String str11 = str;
        String str12 = str9;
        String str13 = str11;
        String str14 = str11;
        k.e(str14, "description");
        k.e(list5, "modelList");
        k.e(str12, "moreAddress");
        k.e(str7, "moreTitle");
        k.e(list3, "skuList");
        k.e(str5, "title");
        String str15 = str7;
        String str16 = str14;
        return new ProductRecommend(j6, j7, str13, j3, i2, list6, str10, str8, list4, str6, j4, j5);
    }

    public boolean equals(@Nullable Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETGUARD_REQ, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProductRecommend)) {
            return false;
        }
        ProductRecommend productRecommend = (ProductRecommend) obj;
        return this.createBy == productRecommend.createBy && this.createTime == productRecommend.createTime && k.a(this.description, productRecommend.description) && this.id == productRecommend.id && this.isEnabled == productRecommend.isEnabled && k.a(this.modelList, productRecommend.modelList) && k.a(this.moreAddress, productRecommend.moreAddress) && k.a(this.moreTitle, productRecommend.moreTitle) && k.a(this.skuList, productRecommend.skuList) && k.a(this.title, productRecommend.title) && this.updateBy == productRecommend.updateBy && this.updateTime == productRecommend.updateTime;
    }

    public int hashCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1055, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (((((((((((((((((((((a.a(this.createBy) * 31) + a.a(this.createTime)) * 31) + this.description.hashCode()) * 31) + a.a(this.id)) * 31) + this.isEnabled) * 31) + this.modelList.hashCode()) * 31) + this.moreAddress.hashCode()) * 31) + this.moreTitle.hashCode()) * 31) + this.skuList.hashCode()) * 31) + this.title.hashCode()) * 31) + a.a(this.updateBy)) * 31) + a.a(this.updateTime);
    }

    @NotNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1054, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "ProductRecommend(createBy=" + this.createBy + ", createTime=" + this.createTime + ", description=" + this.description + ", id=" + this.id + ", isEnabled=" + this.isEnabled + ", modelList=" + this.modelList + ", moreAddress=" + this.moreAddress + ", moreTitle=" + this.moreTitle + ", skuList=" + this.skuList + ", title=" + this.title + ", updateBy=" + this.updateBy + ", updateTime=" + this.updateTime + ')';
    }

    public ProductRecommend(long createBy2, long createTime2, @NotNull String description2, long id2, int isEnabled2, @NotNull List<String> modelList2, @NotNull String moreAddress2, @NotNull String moreTitle2, @NotNull List<Sku> skuList2, @NotNull String title2, long updateBy2, long updateTime2) {
        String str = description2;
        List<String> list = modelList2;
        String str2 = moreAddress2;
        String str3 = moreTitle2;
        List<Sku> list2 = skuList2;
        String str4 = title2;
        k.e(str, "description");
        k.e(list, "modelList");
        k.e(str2, "moreAddress");
        k.e(str3, "moreTitle");
        k.e(list2, "skuList");
        k.e(str4, "title");
        this.createBy = createBy2;
        this.createTime = createTime2;
        this.description = str;
        this.id = id2;
        this.isEnabled = isEnabled2;
        this.modelList = list;
        this.moreAddress = str2;
        this.moreTitle = str3;
        this.skuList = list2;
        this.title = str4;
        this.updateBy = updateBy2;
        this.updateTime = updateTime2;
    }

    public final long getCreateBy() {
        return this.createBy;
    }

    public final long getCreateTime() {
        return this.createTime;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    public final long getId() {
        return this.id;
    }

    public final int isEnabled() {
        return this.isEnabled;
    }

    @NotNull
    public final List<String> getModelList() {
        return this.modelList;
    }

    @NotNull
    public final String getMoreAddress() {
        return this.moreAddress;
    }

    @NotNull
    public final String getMoreTitle() {
        return this.moreTitle;
    }

    @NotNull
    public final List<Sku> getSkuList() {
        return this.skuList;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final long getUpdateBy() {
        return this.updateBy;
    }

    public final long getUpdateTime() {
        return this.updateTime;
    }
}
