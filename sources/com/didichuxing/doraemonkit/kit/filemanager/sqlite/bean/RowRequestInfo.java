package com.didichuxing.doraemonkit.kit.filemanager.sqlite.bean;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RowRequestInfo.kt */
public final class RowRequestInfo {
    @NotNull
    private final String dirPath;
    @NotNull
    private final String fileName;
    @NotNull
    private final List<RowFiledInfo> rowDatas;
    @NotNull
    private final String tableName;

    public static /* synthetic */ RowRequestInfo copy$default(RowRequestInfo rowRequestInfo, String str, String str2, String str3, List<RowFiledInfo> list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = rowRequestInfo.dirPath;
        }
        if ((i & 2) != 0) {
            str2 = rowRequestInfo.fileName;
        }
        if ((i & 4) != 0) {
            str3 = rowRequestInfo.tableName;
        }
        if ((i & 8) != 0) {
            list = rowRequestInfo.rowDatas;
        }
        return rowRequestInfo.copy(str, str2, str3, list);
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
        return this.tableName;
    }

    @NotNull
    public final List<RowFiledInfo> component4() {
        return this.rowDatas;
    }

    @NotNull
    public final RowRequestInfo copy(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull List<RowFiledInfo> list) {
        k.f(str, "dirPath");
        k.f(str2, Progress.FILE_NAME);
        k.f(str3, "tableName");
        k.f(list, "rowDatas");
        return new RowRequestInfo(str, str2, str3, list);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RowRequestInfo)) {
            return false;
        }
        RowRequestInfo rowRequestInfo = (RowRequestInfo) obj;
        return k.a(this.dirPath, rowRequestInfo.dirPath) && k.a(this.fileName, rowRequestInfo.fileName) && k.a(this.tableName, rowRequestInfo.tableName) && k.a(this.rowDatas, rowRequestInfo.rowDatas);
    }

    public int hashCode() {
        String str = this.dirPath;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.fileName;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.tableName;
        int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        List<RowFiledInfo> list = this.rowDatas;
        if (list != null) {
            i = list.hashCode();
        }
        return hashCode3 + i;
    }

    @NotNull
    public String toString() {
        return "RowRequestInfo(dirPath=" + this.dirPath + ", fileName=" + this.fileName + ", tableName=" + this.tableName + ", rowDatas=" + this.rowDatas + ")";
    }

    public RowRequestInfo(@NotNull String dirPath2, @NotNull String fileName2, @NotNull String tableName2, @NotNull List<RowFiledInfo> rowDatas2) {
        k.f(dirPath2, "dirPath");
        k.f(fileName2, Progress.FILE_NAME);
        k.f(tableName2, "tableName");
        k.f(rowDatas2, "rowDatas");
        this.dirPath = dirPath2;
        this.fileName = fileName2;
        this.tableName = tableName2;
        this.rowDatas = rowDatas2;
    }

    @NotNull
    public final String getDirPath() {
        return this.dirPath;
    }

    @NotNull
    public final String getFileName() {
        return this.fileName;
    }

    @NotNull
    public final List<RowFiledInfo> getRowDatas() {
        return this.rowDatas;
    }

    @NotNull
    public final String getTableName() {
        return this.tableName;
    }
}
