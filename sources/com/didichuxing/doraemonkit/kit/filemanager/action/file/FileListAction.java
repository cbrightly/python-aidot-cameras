package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import com.blankj.utilcode.util.e0;
import com.blankj.utilcode.util.f;
import com.blankj.utilcode.util.j;
import com.blankj.utilcode.util.r;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.filemanager.FileManagerUtil;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileListAction.kt */
public final class FileListAction {
    public static final FileListAction INSTANCE = new FileListAction();

    private FileListAction() {
    }

    @NotNull
    public final Map<String, Object> fileListRes(@NotNull String dirPath) {
        k.f(dirPath, "dirPath");
        Map $this$apply = new LinkedHashMap();
        $this$apply.put("code", 200);
        if (k.a(dirPath, FileManagerUtil.ROOT_PATH_STR)) {
            Map data = new LinkedHashMap();
            Map $this$apply2 = data;
            $this$apply2.put("dirPath", FileManagerUtil.ROOT_PATH_STR);
            $this$apply2.put("fileList", INSTANCE.createRootInfo());
            $this$apply.put("data", data);
        } else {
            Map data2 = new LinkedHashMap();
            Map $this$apply3 = data2;
            FileManagerUtil fileManagerUtil = FileManagerUtil.INSTANCE;
            $this$apply3.put("dirPath", fileManagerUtil.relativeRootPath(dirPath));
            List fileInfos = INSTANCE.traverseDir(dirPath);
            if (k.a(dirPath, fileManagerUtil.getExternalStorageRootPath()) && fileInfos.isEmpty()) {
                $this$apply3.put("code", 0);
                int i = R.string.dk_file_manager_sd_permission_tip;
                String string = DokitUtil.getString(i);
                k.b(string, "DokitUtil.getString(DoKi…anager_sd_permission_tip)");
                $this$apply3.put("message", string);
                e0.o(DokitUtil.getString(i), new Object[0]);
            }
            $this$apply3.put("fileList", fileInfos);
            $this$apply.put("data", data2);
        }
        return $this$apply;
    }

    private final List<FileInfo> createRootInfo() {
        List fileInfos = new ArrayList();
        String internalAppDataPath = r.c();
        String externalStoragePath = r.b();
        String s = j.s(internalAppDataPath);
        k.b(s, "FileUtils.getFileName(internalAppDataPath)");
        fileInfos.add(new FileInfo(FileManagerUtil.ROOT_PATH_STR, s, "", Progress.FOLDER, "", "" + j.p(internalAppDataPath), true));
        fileInfos.add(new FileInfo(FileManagerUtil.ROOT_PATH_STR, "external", "", Progress.FOLDER, "", "" + j.p(externalStoragePath), true));
        return fileInfos;
    }

    private final List<FileInfo> traverseDir(String dirPath) {
        int i;
        String str;
        File dir;
        String str2;
        String str3 = dirPath;
        List fileInfos = new ArrayList();
        File dir2 = new File(str3);
        if (!j.y(dir2) || !j.u(dir2)) {
        } else {
            File[] listFiles = dir2.listFiles();
            if (listFiles != null) {
                int length = listFiles.length;
                int i2 = 0;
                while (i2 < length) {
                    File file = listFiles[i2];
                    String relativeRootPath = FileManagerUtil.INSTANCE.relativeRootPath(str3);
                    k.b(file, "file");
                    String name = file.getName();
                    k.b(name, "file.name");
                    if (j.u(file)) {
                        i = i2;
                        str = "";
                    } else {
                        i = i2;
                        str = f.a(file.length(), 1);
                    }
                    k.b(str, "if (FileUtils.isDir(file… 1)\n                    }");
                    if (j.u(file)) {
                        dir = dir2;
                        str2 = Progress.FOLDER;
                    } else {
                        String absolutePath = dir2.getAbsolutePath();
                        k.b(absolutePath, "dir.absolutePath");
                        dir = dir2;
                        if (x.S(absolutePath, "/databases", false, 2, (Object) null)) {
                            str2 = FileUtil.DB;
                        } else {
                            String m = j.m(file);
                            k.b(m, "FileUtils.getFileExtension(file)");
                            if (!w.A(m)) {
                                str2 = j.m(file);
                            } else {
                                str2 = FileUtil.TXT;
                            }
                        }
                    }
                    k.b(str2, "if (FileUtils.isDir(file…  }\n                    }");
                    fileInfos.add(new FileInfo(relativeRootPath, name, str, str2, "", "" + j.o(file), false));
                    i2 = i + 1;
                    str3 = dirPath;
                    dir2 = dir;
                }
            }
        }
        return fileInfos;
    }

    /* compiled from: FileListAction.kt */
    public static final class FileInfo {
        @NotNull
        private final String dirPath;
        @NotNull
        private final String fileName;
        @NotNull
        private final String fileSize;
        @NotNull
        private final String fileType;
        @NotNull
        private final String fileUri;
        private final boolean isRootPath;
        @NotNull
        private final String modifyTime;

        public static /* synthetic */ FileInfo copy$default(FileInfo fileInfo, String str, String str2, String str3, String str4, String str5, String str6, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = fileInfo.dirPath;
            }
            if ((i & 2) != 0) {
                str2 = fileInfo.fileName;
            }
            String str7 = str2;
            if ((i & 4) != 0) {
                str3 = fileInfo.fileSize;
            }
            String str8 = str3;
            if ((i & 8) != 0) {
                str4 = fileInfo.fileType;
            }
            String str9 = str4;
            if ((i & 16) != 0) {
                str5 = fileInfo.fileUri;
            }
            String str10 = str5;
            if ((i & 32) != 0) {
                str6 = fileInfo.modifyTime;
            }
            String str11 = str6;
            if ((i & 64) != 0) {
                z = fileInfo.isRootPath;
            }
            return fileInfo.copy(str, str7, str8, str9, str10, str11, z);
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
            return this.fileSize;
        }

        @NotNull
        public final String component4() {
            return this.fileType;
        }

        @NotNull
        public final String component5() {
            return this.fileUri;
        }

        @NotNull
        public final String component6() {
            return this.modifyTime;
        }

        public final boolean component7() {
            return this.isRootPath;
        }

        @NotNull
        public final FileInfo copy(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4, @NotNull String str5, @NotNull String str6, boolean z) {
            k.f(str, "dirPath");
            k.f(str2, Progress.FILE_NAME);
            k.f(str3, "fileSize");
            k.f(str4, "fileType");
            k.f(str5, "fileUri");
            k.f(str6, "modifyTime");
            return new FileInfo(str, str2, str3, str4, str5, str6, z);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FileInfo)) {
                return false;
            }
            FileInfo fileInfo = (FileInfo) obj;
            return k.a(this.dirPath, fileInfo.dirPath) && k.a(this.fileName, fileInfo.fileName) && k.a(this.fileSize, fileInfo.fileSize) && k.a(this.fileType, fileInfo.fileType) && k.a(this.fileUri, fileInfo.fileUri) && k.a(this.modifyTime, fileInfo.modifyTime) && this.isRootPath == fileInfo.isRootPath;
        }

        public int hashCode() {
            String str = this.dirPath;
            int i = 0;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.fileName;
            int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.fileSize;
            int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
            String str4 = this.fileType;
            int hashCode4 = (hashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
            String str5 = this.fileUri;
            int hashCode5 = (hashCode4 + (str5 != null ? str5.hashCode() : 0)) * 31;
            String str6 = this.modifyTime;
            if (str6 != null) {
                i = str6.hashCode();
            }
            int i2 = (hashCode5 + i) * 31;
            boolean z = this.isRootPath;
            if (z) {
                z = true;
            }
            return i2 + (z ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "FileInfo(dirPath=" + this.dirPath + ", fileName=" + this.fileName + ", fileSize=" + this.fileSize + ", fileType=" + this.fileType + ", fileUri=" + this.fileUri + ", modifyTime=" + this.modifyTime + ", isRootPath=" + this.isRootPath + ")";
        }

        public FileInfo(@NotNull String dirPath2, @NotNull String fileName2, @NotNull String fileSize2, @NotNull String fileType2, @NotNull String fileUri2, @NotNull String modifyTime2, boolean isRootPath2) {
            k.f(dirPath2, "dirPath");
            k.f(fileName2, Progress.FILE_NAME);
            k.f(fileSize2, "fileSize");
            k.f(fileType2, "fileType");
            k.f(fileUri2, "fileUri");
            k.f(modifyTime2, "modifyTime");
            this.dirPath = dirPath2;
            this.fileName = fileName2;
            this.fileSize = fileSize2;
            this.fileType = fileType2;
            this.fileUri = fileUri2;
            this.modifyTime = modifyTime2;
            this.isRootPath = isRootPath2;
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
        public final String getFileSize() {
            return this.fileSize;
        }

        @NotNull
        public final String getFileType() {
            return this.fileType;
        }

        @NotNull
        public final String getFileUri() {
            return this.fileUri;
        }

        @NotNull
        public final String getModifyTime() {
            return this.modifyTime;
        }

        public final boolean isRootPath() {
            return this.isRootPath;
        }
    }
}
