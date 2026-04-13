package com.didichuxing.doraemonkit.kit.filemanager;

import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileManagerUtil.kt */
public final class FileManagerUtil {
    public static final FileManagerUtil INSTANCE = new FileManagerUtil();
    @NotNull
    public static final String ROOT_PATH_STR = "/root/";
    private static final g externalStorageRootPath$delegate = i.b(FileManagerUtil$externalStorageRootPath$2.INSTANCE);
    private static final g externalStorageRootReplacePath$delegate = i.b(FileManagerUtil$externalStorageRootReplacePath$2.INSTANCE);
    private static final g internalAppRootPath$delegate = i.b(FileManagerUtil$internalAppRootPath$2.INSTANCE);
    private static final g internalAppRootReplacePath$delegate = i.b(FileManagerUtil$internalAppRootReplacePath$2.INSTANCE);

    private final String getExternalStorageRootReplacePath() {
        return (String) externalStorageRootReplacePath$delegate.getValue();
    }

    private final String getInternalAppRootPath() {
        return (String) internalAppRootPath$delegate.getValue();
    }

    private final String getInternalAppRootReplacePath() {
        return (String) internalAppRootReplacePath$delegate.getValue();
    }

    public final String getExternalStorageRootPath() {
        return (String) externalStorageRootPath$delegate.getValue();
    }

    private FileManagerUtil() {
    }

    @NotNull
    public final String relativeRootPath(@Nullable String path) {
        if (path == null) {
            return "";
        }
        String it = path;
        FileManagerUtil fileManagerUtil = INSTANCE;
        String internalAppRootPath = fileManagerUtil.getInternalAppRootPath();
        k.b(internalAppRootPath, "internalAppRootPath");
        if (x.S(it, internalAppRootPath, false, 2, (Object) null)) {
            String internalAppRootPath2 = fileManagerUtil.getInternalAppRootPath();
            k.b(internalAppRootPath2, "internalAppRootPath");
            return w.H(it, internalAppRootPath2, ROOT_PATH_STR + fileManagerUtil.getInternalAppRootReplacePath(), false, 4, (Object) null);
        }
        String externalStorageRootPath = fileManagerUtil.getExternalStorageRootPath();
        k.b(externalStorageRootPath, "externalStorageRootPath");
        if (!x.S(it, externalStorageRootPath, false, 2, (Object) null)) {
            return it;
        }
        String externalStorageRootPath2 = fileManagerUtil.getExternalStorageRootPath();
        k.b(externalStorageRootPath2, "externalStorageRootPath");
        return w.H(it, externalStorageRootPath2, ROOT_PATH_STR + fileManagerUtil.getExternalStorageRootReplacePath(), false, 4, (Object) null);
    }

    @NotNull
    public final String absoluteRootPath(@Nullable String path) {
        if (path == null) {
            return "";
        }
        String it = path;
        StringBuilder sb = new StringBuilder();
        sb.append(ROOT_PATH_STR);
        FileManagerUtil fileManagerUtil = INSTANCE;
        sb.append(fileManagerUtil.getInternalAppRootReplacePath());
        if (x.S(it, sb.toString(), false, 2, (Object) null)) {
            String str = ROOT_PATH_STR + fileManagerUtil.getInternalAppRootReplacePath();
            String internalAppRootPath = fileManagerUtil.getInternalAppRootPath();
            k.b(internalAppRootPath, "internalAppRootPath");
            return w.H(it, str, internalAppRootPath, false, 4, (Object) null);
        }
        if (!x.S(it, ROOT_PATH_STR + fileManagerUtil.getExternalStorageRootReplacePath(), false, 2, (Object) null)) {
            return it;
        }
        String str2 = ROOT_PATH_STR + fileManagerUtil.getExternalStorageRootReplacePath();
        String externalStorageRootPath = fileManagerUtil.getExternalStorageRootPath();
        k.b(externalStorageRootPath, "externalStorageRootPath");
        return w.H(it, str2, externalStorageRootPath, false, 4, (Object) null);
    }
}
