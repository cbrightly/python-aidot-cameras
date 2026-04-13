package com.leedarson.serviceimpl.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterInfo.kt */
public final class DCLVendorInfoBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private String companyLegalName;
    @Nullable
    private String companyPreferredName;
    @Nullable
    private String creator;
    @Nullable
    private Integer vendorID;
    @Nullable
    private String vendorLandingPageURL;
    @Nullable
    private String vendorName;

    public DCLVendorInfoBean() {
        this((Integer) null, (String) null, (String) null, (String) null, (String) null, (String) null, 63, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ DCLVendorInfoBean copy$default(DCLVendorInfoBean dCLVendorInfoBean, Integer num, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
        DCLVendorInfoBean dCLVendorInfoBean2 = dCLVendorInfoBean;
        int i2 = i;
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dCLVendorInfoBean2, num, str, str2, str3, str4, str5, new Integer(i2), obj}, (Object) null, changeQuickRedirect, true, 6162, new Class[]{DCLVendorInfoBean.class, Integer.class, cls, cls, cls, cls, cls, Integer.TYPE, Object.class}, DCLVendorInfoBean.class);
        if (proxy.isSupported) {
            return (DCLVendorInfoBean) proxy.result;
        }
        return dCLVendorInfoBean.copy((i2 & 1) != 0 ? dCLVendorInfoBean2.vendorID : num, (i2 & 2) != 0 ? dCLVendorInfoBean2.vendorName : str, (i2 & 4) != 0 ? dCLVendorInfoBean2.companyLegalName : str2, (i2 & 8) != 0 ? dCLVendorInfoBean2.companyPreferredName : str3, (i2 & 16) != 0 ? dCLVendorInfoBean2.vendorLandingPageURL : str4, (i2 & 32) != 0 ? dCLVendorInfoBean2.creator : str5);
    }

    @Nullable
    public final Integer component1() {
        return this.vendorID;
    }

    @Nullable
    public final String component2() {
        return this.vendorName;
    }

    @Nullable
    public final String component3() {
        return this.companyLegalName;
    }

    @Nullable
    public final String component4() {
        return this.companyPreferredName;
    }

    @Nullable
    public final String component5() {
        return this.vendorLandingPageURL;
    }

    @Nullable
    public final String component6() {
        return this.creator;
    }

    @NotNull
    public final DCLVendorInfoBean copy(@Nullable Integer num, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{num, str, str2, str3, str4, str5}, this, changeQuickRedirect, false, 6161, new Class[]{Integer.class, cls, cls, cls, cls, cls}, DCLVendorInfoBean.class);
        if (proxy.isSupported) {
            return (DCLVendorInfoBean) proxy.result;
        }
        return new DCLVendorInfoBean(num, str, str2, str3, str4, str5);
    }

    public boolean equals(@Nullable Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6165, new Class[]{Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DCLVendorInfoBean)) {
            return false;
        }
        DCLVendorInfoBean dCLVendorInfoBean = (DCLVendorInfoBean) obj;
        return k.a(this.vendorID, dCLVendorInfoBean.vendorID) && k.a(this.vendorName, dCLVendorInfoBean.vendorName) && k.a(this.companyLegalName, dCLVendorInfoBean.companyLegalName) && k.a(this.companyPreferredName, dCLVendorInfoBean.companyPreferredName) && k.a(this.vendorLandingPageURL, dCLVendorInfoBean.vendorLandingPageURL) && k.a(this.creator, dCLVendorInfoBean.creator);
    }

    public int hashCode() {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6164, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        Integer num = this.vendorID;
        int hashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.vendorName;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.companyLegalName;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.companyPreferredName;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.vendorLandingPageURL;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.creator;
        if (str5 != null) {
            i = str5.hashCode();
        }
        return hashCode5 + i;
    }

    @NotNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6163, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "DCLVendorInfoBean(vendorID=" + this.vendorID + ", vendorName=" + this.vendorName + ", companyLegalName=" + this.companyLegalName + ", companyPreferredName=" + this.companyPreferredName + ", vendorLandingPageURL=" + this.vendorLandingPageURL + ", creator=" + this.creator + ')';
    }

    public DCLVendorInfoBean(@Nullable Integer vendorID2, @Nullable String vendorName2, @Nullable String companyLegalName2, @Nullable String companyPreferredName2, @Nullable String vendorLandingPageURL2, @Nullable String creator2) {
        this.vendorID = vendorID2;
        this.vendorName = vendorName2;
        this.companyLegalName = companyLegalName2;
        this.companyPreferredName = companyPreferredName2;
        this.vendorLandingPageURL = vendorLandingPageURL2;
        this.creator = creator2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ DCLVendorInfoBean(java.lang.Integer r5, java.lang.String r6, java.lang.String r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r4 = this;
            r12 = r11 & 1
            if (r12 == 0) goto L_0x0009
            r5 = 0
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
        L_0x0009:
            r12 = r11 & 2
            java.lang.String r0 = ""
            if (r12 == 0) goto L_0x0011
            r12 = r0
            goto L_0x0012
        L_0x0011:
            r12 = r6
        L_0x0012:
            r6 = r11 & 4
            if (r6 == 0) goto L_0x0018
            r1 = r0
            goto L_0x0019
        L_0x0018:
            r1 = r7
        L_0x0019:
            r6 = r11 & 8
            if (r6 == 0) goto L_0x001f
            r2 = r0
            goto L_0x0020
        L_0x001f:
            r2 = r8
        L_0x0020:
            r6 = r11 & 16
            if (r6 == 0) goto L_0x0026
            r3 = r0
            goto L_0x0027
        L_0x0026:
            r3 = r9
        L_0x0027:
            r6 = r11 & 32
            if (r6 == 0) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            r0 = r10
        L_0x002d:
            r6 = r4
            r7 = r5
            r8 = r12
            r9 = r1
            r10 = r2
            r11 = r3
            r12 = r0
            r6.<init>(r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.bean.DCLVendorInfoBean.<init>(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public final Integer getVendorID() {
        return this.vendorID;
    }

    public final void setVendorID(@Nullable Integer num) {
        this.vendorID = num;
    }

    @Nullable
    public final String getVendorName() {
        return this.vendorName;
    }

    public final void setVendorName(@Nullable String str) {
        this.vendorName = str;
    }

    @Nullable
    public final String getCompanyLegalName() {
        return this.companyLegalName;
    }

    public final void setCompanyLegalName(@Nullable String str) {
        this.companyLegalName = str;
    }

    @Nullable
    public final String getCompanyPreferredName() {
        return this.companyPreferredName;
    }

    public final void setCompanyPreferredName(@Nullable String str) {
        this.companyPreferredName = str;
    }

    @Nullable
    public final String getVendorLandingPageURL() {
        return this.vendorLandingPageURL;
    }

    public final void setVendorLandingPageURL(@Nullable String str) {
        this.vendorLandingPageURL = str;
    }

    @Nullable
    public final String getCreator() {
        return this.creator;
    }

    public final void setCreator(@Nullable String str) {
        this.creator = str;
    }
}
