package com.didichuxing.doraemonkit.kit.filemanager.sqlite.bean;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RowFiledInfo.kt */
public final class RowFiledInfo {
    private final boolean isPrimary;
    @NotNull
    private final String title;
    @Nullable
    private final String value;

    public static /* synthetic */ RowFiledInfo copy$default(RowFiledInfo rowFiledInfo, String str, boolean z, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = rowFiledInfo.title;
        }
        if ((i & 2) != 0) {
            z = rowFiledInfo.isPrimary;
        }
        if ((i & 4) != 0) {
            str2 = rowFiledInfo.value;
        }
        return rowFiledInfo.copy(str, z, str2);
    }

    @NotNull
    public final String component1() {
        return this.title;
    }

    public final boolean component2() {
        return this.isPrimary;
    }

    @Nullable
    public final String component3() {
        return this.value;
    }

    @NotNull
    public final RowFiledInfo copy(@NotNull String str, boolean z, @Nullable String str2) {
        k.f(str, "title");
        return new RowFiledInfo(str, z, str2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RowFiledInfo)) {
            return false;
        }
        RowFiledInfo rowFiledInfo = (RowFiledInfo) obj;
        return k.a(this.title, rowFiledInfo.title) && this.isPrimary == rowFiledInfo.isPrimary && k.a(this.value, rowFiledInfo.value);
    }

    public int hashCode() {
        String str = this.title;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        boolean z = this.isPrimary;
        if (z) {
            z = true;
        }
        int i2 = (hashCode + (z ? 1 : 0)) * 31;
        String str2 = this.value;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return i2 + i;
    }

    @NotNull
    public String toString() {
        return "RowFiledInfo(title=" + this.title + ", isPrimary=" + this.isPrimary + ", value=" + this.value + ")";
    }

    public RowFiledInfo(@NotNull String title2, boolean isPrimary2, @Nullable String value2) {
        k.f(title2, "title");
        this.title = title2;
        this.isPrimary = isPrimary2;
        this.value = value2;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getValue() {
        return this.value;
    }

    public final boolean isPrimary() {
        return this.isPrimary;
    }
}
