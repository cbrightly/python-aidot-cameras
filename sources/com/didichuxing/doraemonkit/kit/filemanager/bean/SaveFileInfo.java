package com.didichuxing.doraemonkit.kit.filemanager.bean;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SaveFileInfo.kt */
public final class SaveFileInfo {
    @NotNull
    private final String content;
    @NotNull
    private final String dirPath;
    @NotNull
    private final String fileName;

    public static /* synthetic */ SaveFileInfo copy$default(SaveFileInfo saveFileInfo, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = saveFileInfo.dirPath;
        }
        if ((i & 2) != 0) {
            str2 = saveFileInfo.fileName;
        }
        if ((i & 4) != 0) {
            str3 = saveFileInfo.content;
        }
        return saveFileInfo.copy(str, str2, str3);
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
    public final String component3() {
        return this.content;
    }

    @NotNull
    public final SaveFileInfo copy(@NotNull String str, @NotNull String str2, @NotNull String str3) {
        k.f(str, "dirPath");
        k.f(str2, Progress.FILE_NAME);
        k.f(str3, FirebaseAnalytics.Param.CONTENT);
        return new SaveFileInfo(str, str2, str3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SaveFileInfo)) {
            return false;
        }
        SaveFileInfo saveFileInfo = (SaveFileInfo) obj;
        return k.a(this.dirPath, saveFileInfo.dirPath) && k.a(this.fileName, saveFileInfo.fileName) && k.a(this.content, saveFileInfo.content);
    }

    public int hashCode() {
        String str = this.dirPath;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.fileName;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.content;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "SaveFileInfo(dirPath=" + this.dirPath + ", fileName=" + this.fileName + ", content=" + this.content + ")";
    }

    public SaveFileInfo(@NotNull String dirPath2, @NotNull String fileName2, @NotNull String content2) {
        k.f(dirPath2, "dirPath");
        k.f(fileName2, Progress.FILE_NAME);
        k.f(content2, FirebaseAnalytics.Param.CONTENT);
        this.dirPath = dirPath2;
        this.fileName = fileName2;
        this.content = content2;
    }

    @NotNull
    public final String getContent() {
        return this.content;
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
