package com.didichuxing.doraemonkit.kit.toolpanel;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KitBeans.kt */
public final class KitBean {
    @NotNull
    private String allClassName;
    private boolean checked;
    @NotNull
    private String innerKitId;

    public static /* synthetic */ KitBean copy$default(KitBean kitBean, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = kitBean.allClassName;
        }
        if ((i & 2) != 0) {
            z = kitBean.checked;
        }
        if ((i & 4) != 0) {
            str2 = kitBean.innerKitId;
        }
        return kitBean.copy(str, z, str2);
    }

    @NotNull
    public final String component1() {
        return this.allClassName;
    }

    public final boolean component2() {
        return this.checked;
    }

    @NotNull
    public final String component3() {
        return this.innerKitId;
    }

    @NotNull
    public final KitBean copy(@NotNull String str, boolean z, @NotNull String str2) {
        k.f(str, "allClassName");
        k.f(str2, "innerKitId");
        return new KitBean(str, z, str2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KitBean)) {
            return false;
        }
        KitBean kitBean = (KitBean) obj;
        return k.a(this.allClassName, kitBean.allClassName) && this.checked == kitBean.checked && k.a(this.innerKitId, kitBean.innerKitId);
    }

    public int hashCode() {
        String str = this.allClassName;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        boolean z = this.checked;
        if (z) {
            z = true;
        }
        int i2 = (hashCode + (z ? 1 : 0)) * 31;
        String str2 = this.innerKitId;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return i2 + i;
    }

    @NotNull
    public String toString() {
        return "KitBean(allClassName=" + this.allClassName + ", checked=" + this.checked + ", innerKitId=" + this.innerKitId + ")";
    }

    public KitBean(@NotNull String allClassName2, boolean checked2, @NotNull String innerKitId2) {
        k.f(allClassName2, "allClassName");
        k.f(innerKitId2, "innerKitId");
        this.allClassName = allClassName2;
        this.checked = checked2;
        this.innerKitId = innerKitId2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ KitBean(String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, (i & 4) != 0 ? "" : str2);
    }

    @NotNull
    public final String getAllClassName() {
        return this.allClassName;
    }

    public final boolean getChecked() {
        return this.checked;
    }

    @NotNull
    public final String getInnerKitId() {
        return this.innerKitId;
    }

    public final void setAllClassName(@NotNull String str) {
        k.f(str, "<set-?>");
        this.allClassName = str;
    }

    public final void setChecked(boolean z) {
        this.checked = z;
    }

    public final void setInnerKitId(@NotNull String str) {
        k.f(str, "<set-?>");
        this.innerKitId = str;
    }
}
