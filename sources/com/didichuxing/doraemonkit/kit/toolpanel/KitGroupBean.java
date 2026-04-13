package com.didichuxing.doraemonkit.kit.toolpanel;

import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KitBeans.kt */
public final class KitGroupBean {
    @NotNull
    private String groupId;
    @NotNull
    private List<KitBean> kits;

    public static /* synthetic */ KitGroupBean copy$default(KitGroupBean kitGroupBean, String str, List<KitBean> list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = kitGroupBean.groupId;
        }
        if ((i & 2) != 0) {
            list = kitGroupBean.kits;
        }
        return kitGroupBean.copy(str, list);
    }

    @NotNull
    public final String component1() {
        return this.groupId;
    }

    @NotNull
    public final List<KitBean> component2() {
        return this.kits;
    }

    @NotNull
    public final KitGroupBean copy(@NotNull String str, @NotNull List<KitBean> list) {
        k.f(str, "groupId");
        k.f(list, "kits");
        return new KitGroupBean(str, list);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KitGroupBean)) {
            return false;
        }
        KitGroupBean kitGroupBean = (KitGroupBean) obj;
        return k.a(this.groupId, kitGroupBean.groupId) && k.a(this.kits, kitGroupBean.kits);
    }

    public int hashCode() {
        String str = this.groupId;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        List<KitBean> list = this.kits;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "KitGroupBean(groupId=" + this.groupId + ", kits=" + this.kits + ")";
    }

    public KitGroupBean(@NotNull String groupId2, @NotNull List<KitBean> kits2) {
        k.f(groupId2, "groupId");
        k.f(kits2, "kits");
        this.groupId = groupId2;
        this.kits = kits2;
    }

    @NotNull
    public final String getGroupId() {
        return this.groupId;
    }

    @NotNull
    public final List<KitBean> getKits() {
        return this.kits;
    }

    public final void setGroupId(@NotNull String str) {
        k.f(str, "<set-?>");
        this.groupId = str;
    }

    public final void setKits(@NotNull List<KitBean> list) {
        k.f(list, "<set-?>");
        this.kits = list;
    }
}
