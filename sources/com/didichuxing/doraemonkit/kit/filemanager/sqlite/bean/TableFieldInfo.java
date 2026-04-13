package com.didichuxing.doraemonkit.kit.filemanager.sqlite.bean;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TableFieldInfo.kt */
public final class TableFieldInfo {
    private boolean isPrimary;
    @NotNull
    private String title;

    public static /* synthetic */ TableFieldInfo copy$default(TableFieldInfo tableFieldInfo, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = tableFieldInfo.title;
        }
        if ((i & 2) != 0) {
            z = tableFieldInfo.isPrimary;
        }
        return tableFieldInfo.copy(str, z);
    }

    @NotNull
    public final String component1() {
        return this.title;
    }

    public final boolean component2() {
        return this.isPrimary;
    }

    @NotNull
    public final TableFieldInfo copy(@NotNull String str, boolean z) {
        k.f(str, "title");
        return new TableFieldInfo(str, z);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TableFieldInfo)) {
            return false;
        }
        TableFieldInfo tableFieldInfo = (TableFieldInfo) obj;
        return k.a(this.title, tableFieldInfo.title) && this.isPrimary == tableFieldInfo.isPrimary;
    }

    public int hashCode() {
        String str = this.title;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        boolean z = this.isPrimary;
        if (z) {
            z = true;
        }
        return hashCode + (z ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "TableFieldInfo(title=" + this.title + ", isPrimary=" + this.isPrimary + ")";
    }

    public TableFieldInfo(@NotNull String title2, boolean isPrimary2) {
        k.f(title2, "title");
        this.title = title2;
        this.isPrimary = isPrimary2;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final boolean isPrimary() {
        return this.isPrimary;
    }

    public final void setPrimary(boolean z) {
        this.isPrimary = z;
    }

    public final void setTitle(@NotNull String str) {
        k.f(str, "<set-?>");
        this.title = str;
    }
}
