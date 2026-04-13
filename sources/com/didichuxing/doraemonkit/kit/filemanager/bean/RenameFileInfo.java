package com.didichuxing.doraemonkit.kit.filemanager.bean;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RenameFileInfo.kt */
public final class RenameFileInfo {
    @NotNull
    private final String dirPath;
    @NotNull
    private final String newName;
    @NotNull
    private final String oldName;

    public static /* synthetic */ RenameFileInfo copy$default(RenameFileInfo renameFileInfo, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = renameFileInfo.dirPath;
        }
        if ((i & 2) != 0) {
            str2 = renameFileInfo.oldName;
        }
        if ((i & 4) != 0) {
            str3 = renameFileInfo.newName;
        }
        return renameFileInfo.copy(str, str2, str3);
    }

    @NotNull
    public final String component1() {
        return this.dirPath;
    }

    @NotNull
    public final String component2() {
        return this.oldName;
    }

    @NotNull
    public final String component3() {
        return this.newName;
    }

    @NotNull
    public final RenameFileInfo copy(@NotNull String str, @NotNull String str2, @NotNull String str3) {
        k.f(str, "dirPath");
        k.f(str2, "oldName");
        k.f(str3, "newName");
        return new RenameFileInfo(str, str2, str3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RenameFileInfo)) {
            return false;
        }
        RenameFileInfo renameFileInfo = (RenameFileInfo) obj;
        return k.a(this.dirPath, renameFileInfo.dirPath) && k.a(this.oldName, renameFileInfo.oldName) && k.a(this.newName, renameFileInfo.newName);
    }

    public int hashCode() {
        String str = this.dirPath;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.oldName;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.newName;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "RenameFileInfo(dirPath=" + this.dirPath + ", oldName=" + this.oldName + ", newName=" + this.newName + ")";
    }

    public RenameFileInfo(@NotNull String dirPath2, @NotNull String oldName2, @NotNull String newName2) {
        k.f(dirPath2, "dirPath");
        k.f(oldName2, "oldName");
        k.f(newName2, "newName");
        this.dirPath = dirPath2;
        this.oldName = oldName2;
        this.newName = newName2;
    }

    @NotNull
    public final String getDirPath() {
        return this.dirPath;
    }

    @NotNull
    public final String getNewName() {
        return this.newName;
    }

    @NotNull
    public final String getOldName() {
        return this.oldName;
    }
}
