package com.leedarson.bean;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.luck.picture.lib.config.PictureMimeType;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProductRecommend.kt */
public final class Sku {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final String image;
    @NotNull
    private final String name;
    @NotNull
    private final String originalPrice;
    @NotNull
    private final String price;
    @NotNull
    private final String rating;
    @NotNull
    private final String save;
    private final int showPrice;
    @NotNull
    private final String sku;
    private final int skuIndex;
    @NotNull
    private final String url;

    public static /* synthetic */ Sku copy$default(Sku sku2, String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, int i2, String str8, int i3, Object obj) {
        Sku sku3 = sku2;
        int i4 = i3;
        Class<String> cls = String.class;
        int i5 = i;
        int i6 = i2;
        Object[] objArr = {sku3, str, str2, str3, str4, str5, str6, new Integer(i5), str7, new Integer(i6), str8, new Integer(i4), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 1114, new Class[]{Sku.class, cls, cls, cls, cls, cls, cls, cls2, cls, cls2, cls, cls2, Object.class}, Sku.class);
        if (proxy.isSupported) {
            return (Sku) proxy.result;
        }
        return sku2.copy((i4 & 1) != 0 ? sku3.image : str, (i4 & 2) != 0 ? sku3.name : str2, (i4 & 4) != 0 ? sku3.originalPrice : str3, (i4 & 8) != 0 ? sku3.price : str4, (i4 & 16) != 0 ? sku3.rating : str5, (i4 & 32) != 0 ? sku3.save : str6, (i4 & 64) != 0 ? sku3.showPrice : i5, (i4 & 128) != 0 ? sku3.sku : str7, (i4 & 256) != 0 ? sku3.skuIndex : i6, (i4 & 512) != 0 ? sku3.url : str8);
    }

    @NotNull
    public final String component1() {
        return this.image;
    }

    @NotNull
    public final String component10() {
        return this.url;
    }

    @NotNull
    public final String component2() {
        return this.name;
    }

    @NotNull
    public final String component3() {
        return this.originalPrice;
    }

    @NotNull
    public final String component4() {
        return this.price;
    }

    @NotNull
    public final String component5() {
        return this.rating;
    }

    @NotNull
    public final String component6() {
        return this.save;
    }

    public final int component7() {
        return this.showPrice;
    }

    @NotNull
    public final String component8() {
        return this.sku;
    }

    public final int component9() {
        return this.skuIndex;
    }

    @NotNull
    public final Sku copy(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4, @NotNull String str5, @NotNull String str6, int i, @NotNull String str7, int i2, @NotNull String str8) {
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, str3, str4, str5, str6, new Integer(i), str7, new Integer(i2), str8};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls, cls, cls, cls, cls2, cls, cls2, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1113, clsArr, Sku.class);
        if (proxy.isSupported) {
            return (Sku) proxy.result;
        }
        String str9 = str2;
        String str10 = str6;
        String str11 = str4;
        String str12 = str7;
        String str13 = str8;
        String str14 = str;
        String str15 = str3;
        int i3 = i;
        String str16 = str5;
        k.e(str14, PictureMimeType.MIME_TYPE_PREFIX_IMAGE);
        k.e(str9, "name");
        k.e(str15, "originalPrice");
        k.e(str11, FirebaseAnalytics.Param.PRICE);
        k.e(str16, "rating");
        k.e(str10, "save");
        k.e(str12, "sku");
        k.e(str13, "url");
        return new Sku(str14, str9, str15, str11, str16, str10, i3, str12, i2, str13);
    }

    public boolean equals(@Nullable Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1117, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Sku)) {
            return false;
        }
        Sku sku2 = (Sku) obj;
        return k.a(this.image, sku2.image) && k.a(this.name, sku2.name) && k.a(this.originalPrice, sku2.originalPrice) && k.a(this.price, sku2.price) && k.a(this.rating, sku2.rating) && k.a(this.save, sku2.save) && this.showPrice == sku2.showPrice && k.a(this.sku, sku2.sku) && this.skuIndex == sku2.skuIndex && k.a(this.url, sku2.url);
    }

    public int hashCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1116, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (((((((((((((((((this.image.hashCode() * 31) + this.name.hashCode()) * 31) + this.originalPrice.hashCode()) * 31) + this.price.hashCode()) * 31) + this.rating.hashCode()) * 31) + this.save.hashCode()) * 31) + this.showPrice) * 31) + this.sku.hashCode()) * 31) + this.skuIndex) * 31) + this.url.hashCode();
    }

    @NotNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1115, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "Sku(image=" + this.image + ", name=" + this.name + ", originalPrice=" + this.originalPrice + ", price=" + this.price + ", rating=" + this.rating + ", save=" + this.save + ", showPrice=" + this.showPrice + ", sku=" + this.sku + ", skuIndex=" + this.skuIndex + ", url=" + this.url + ')';
    }

    public Sku(@NotNull String image2, @NotNull String name2, @NotNull String originalPrice2, @NotNull String price2, @NotNull String rating2, @NotNull String save2, int showPrice2, @NotNull String sku2, int skuIndex2, @NotNull String url2) {
        k.e(image2, PictureMimeType.MIME_TYPE_PREFIX_IMAGE);
        k.e(name2, "name");
        k.e(originalPrice2, "originalPrice");
        k.e(price2, FirebaseAnalytics.Param.PRICE);
        k.e(rating2, "rating");
        k.e(save2, "save");
        k.e(sku2, "sku");
        k.e(url2, "url");
        this.image = image2;
        this.name = name2;
        this.originalPrice = originalPrice2;
        this.price = price2;
        this.rating = rating2;
        this.save = save2;
        this.showPrice = showPrice2;
        this.sku = sku2;
        this.skuIndex = skuIndex2;
        this.url = url2;
    }

    @NotNull
    public final String getImage() {
        return this.image;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getOriginalPrice() {
        return this.originalPrice;
    }

    @NotNull
    public final String getPrice() {
        return this.price;
    }

    @NotNull
    public final String getRating() {
        return this.rating;
    }

    @NotNull
    public final String getSave() {
        return this.save;
    }

    public final int getShowPrice() {
        return this.showPrice;
    }

    @NotNull
    public final String getSku() {
        return this.sku;
    }

    public final int getSkuIndex() {
        return this.skuIndex;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }
}
