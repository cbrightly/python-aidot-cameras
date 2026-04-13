package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import com.blankj.utilcode.util.i;
import com.blankj.utilcode.util.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileDetailAction.kt */
public final class FileDetailAction {
    public static final FileDetailAction INSTANCE = new FileDetailAction();

    private FileDetailAction() {
    }

    @NotNull
    public final Map<String, Object> fileDetailInfoRes(@NotNull String filePath) {
        k.f(filePath, Progress.FILE_PATH);
        Map params = new LinkedHashMap();
        if (!j.z(filePath) || !j.x(filePath)) {
            params.put("code", 0);
            params.put("data", filePath + " is not a file");
        } else {
            params.put("code", 200);
            Map data = new LinkedHashMap();
            String n = j.n(filePath);
            k.b(n, "FileUtils.getFileExtension(filePath)");
            data.put("fileType", n);
            String d = i.d(filePath);
            k.b(d, "FileIOUtils.readFile2String(filePath)");
            data.put("fileContent", d);
            params.put("data", data);
        }
        return params;
    }
}
