package com.didichuxing.doraemonkit.kit.toolpanel;

import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.widget.bravh.entity.MultiItemEntity;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KitWrapItem.kt */
public final class KitWrapItem implements MultiItemEntity, Cloneable {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final int TYPE_EXIT = 203;
    public static final int TYPE_KIT = 201;
    public static final int TYPE_MODE = 202;
    public static final int TYPE_TITLE = 999;
    public static final int TYPE_VERSION = 204;
    private boolean checked;
    @NotNull
    private String groupName;
    private final int itemType;
    @Nullable
    private final AbstractKit kit;
    @NotNull
    private final String name;

    public static /* synthetic */ KitWrapItem copy$default(KitWrapItem kitWrapItem, int i, String str, boolean z, String str2, AbstractKit abstractKit, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = kitWrapItem.getItemType();
        }
        if ((i2 & 2) != 0) {
            str = kitWrapItem.name;
        }
        String str3 = str;
        if ((i2 & 4) != 0) {
            z = kitWrapItem.checked;
        }
        boolean z2 = z;
        if ((i2 & 8) != 0) {
            str2 = kitWrapItem.groupName;
        }
        String str4 = str2;
        if ((i2 & 16) != 0) {
            abstractKit = kitWrapItem.kit;
        }
        return kitWrapItem.copy(i, str3, z2, str4, abstractKit);
    }

    public final int component1() {
        return getItemType();
    }

    @NotNull
    public final String component2() {
        return this.name;
    }

    public final boolean component3() {
        return this.checked;
    }

    @NotNull
    public final String component4() {
        return this.groupName;
    }

    @Nullable
    public final AbstractKit component5() {
        return this.kit;
    }

    @NotNull
    public final KitWrapItem copy(int i, @NotNull String str, boolean z, @NotNull String str2, @Nullable AbstractKit abstractKit) {
        k.f(str, "name");
        k.f(str2, "groupName");
        return new KitWrapItem(i, str, z, str2, abstractKit);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KitWrapItem)) {
            return false;
        }
        KitWrapItem kitWrapItem = (KitWrapItem) obj;
        return getItemType() == kitWrapItem.getItemType() && k.a(this.name, kitWrapItem.name) && this.checked == kitWrapItem.checked && k.a(this.groupName, kitWrapItem.groupName) && k.a(this.kit, kitWrapItem.kit);
    }

    public int hashCode() {
        int itemType2 = getItemType() * 31;
        String str = this.name;
        int i = 0;
        int hashCode = (itemType2 + (str != null ? str.hashCode() : 0)) * 31;
        boolean z = this.checked;
        if (z) {
            z = true;
        }
        int i2 = (hashCode + (z ? 1 : 0)) * 31;
        String str2 = this.groupName;
        int hashCode2 = (i2 + (str2 != null ? str2.hashCode() : 0)) * 31;
        AbstractKit abstractKit = this.kit;
        if (abstractKit != null) {
            i = abstractKit.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "KitWrapItem(itemType=" + getItemType() + ", name=" + this.name + ", checked=" + this.checked + ", groupName=" + this.groupName + ", kit=" + this.kit + ")";
    }

    /* compiled from: KitWrapItem.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public KitWrapItem(int itemType2, @NotNull String name2, boolean checked2, @NotNull String groupName2, @Nullable AbstractKit kit2) {
        k.f(name2, "name");
        k.f(groupName2, "groupName");
        this.itemType = itemType2;
        this.name = name2;
        this.checked = checked2;
        this.groupName = groupName2;
        this.kit = kit2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ KitWrapItem(int i, String str, boolean z, String str2, AbstractKit abstractKit, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? "" : str2, abstractKit);
    }

    public final boolean getChecked() {
        return this.checked;
    }

    @NotNull
    public final String getGroupName() {
        return this.groupName;
    }

    public int getItemType() {
        return this.itemType;
    }

    @Nullable
    public final AbstractKit getKit() {
        return this.kit;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setChecked(boolean z) {
        this.checked = z;
    }

    public final void setGroupName(@NotNull String str) {
        k.f(str, "<set-?>");
        this.groupName = str;
    }

    @NotNull
    public KitWrapItem clone() {
        Object clone = super.clone();
        if (clone != null) {
            KitWrapItem item = (KitWrapItem) clone;
            item.checked = this.checked;
            item.groupName = this.groupName;
            return item;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem");
    }
}
