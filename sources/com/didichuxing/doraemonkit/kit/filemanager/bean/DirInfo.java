package com.didichuxing.doraemonkit.kit.filemanager.bean;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DirInfo.kt */
public final class DirInfo {
    @NotNull
    private final String dirPath;
    @NotNull
    private final String fileName;

    public static /* synthetic */ DirInfo copy$default(DirInfo dirInfo, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = dirInfo.dirPath;
        }
        if ((i & 2) != 0) {
            str2 = dirInfo.fileName;
        }
        return dirInfo.copy(str, str2);
    }

    @NotNull
    public final String component1() {
        return this.dirPath;
    }

    @NotNull
    public final String component2() {
        return this.fileName;
    }

    @NotNull
    public final DirInfo copy(@NotNull String str, @NotNull String str2) {
        k.f(str, "dirPath");
        k.f(str2, Progress.FILE_NAME);
        return new DirInfo(str, str2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DirInfo)) {
            return false;
        }
        DirInfo dirInfo = (DirInfo) obj;
        return k.a(this.dirPath, dirInfo.dirPath) && k.a(this.fileName, dirInfo.fileName);
    }

    public int hashCode() {
        String str = this.dirPath;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.fileName;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "DirInfo(dirPath=" + this.dirPath + ", fileName=" + this.fileName + ")";
    }

    public DirInfo(@NotNull String dirPath2, @NotNull String fileName2) {
        k.f(dirPath2, "dirPath");
        k.f(fileName2, Progress.FILE_NAME);
        this.dirPath = dirPath2;
        this.fileName = fileName2;
    }

    @NotNull
    public final String getDirPath() {
        return this.dirPath;
    }

    @NotNull
    public final String getFileName() {
        return this.fileName;
    }
}
