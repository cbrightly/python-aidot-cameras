package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import com.blankj.utilcode.util.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DeleteFileAction.kt */
public final class DeleteFileAction {
    public static final DeleteFileAction INSTANCE = new DeleteFileAction();

    private DeleteFileAction() {
    }

    @NotNull
    public final Map<String, Object> deleteFileRes(@NotNull String filePath, @NotNull String dirPath, @NotNull String fileName) {
        String str = filePath;
        String str2 = dirPath;
        String str3 = fileName;
        k.f(str, Progress.FILE_PATH);
        k.f(str2, "dirPath");
        k.f(str3, Progress.FILE_NAME);
        Map response = new LinkedHashMap();
        if (!j.z(filePath)) {
            response.put("code", 0);
            response.put(FirebaseAnalytics.Param.SUCCESS, false);
            response.put("message", "delete " + str + " failure");
        } else if (j.v(filePath)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            String str4 = File.separator;
            sb.append(str4);
            sb.append(str3);
            boolean deleteFilesSuccess = j.g(sb.toString());
            boolean deleteDirSuccess = j.e(str2 + str4 + str3);
            if (!deleteFilesSuccess || !deleteDirSuccess) {
                response.put("code", 0);
                response.put(FirebaseAnalytics.Param.SUCCESS, false);
                response.put("message", "delete " + str + " failure");
            } else {
                response.put("code", 200);
                response.put(FirebaseAnalytics.Param.SUCCESS, true);
                response.put("message", FirebaseAnalytics.Param.SUCCESS);
            }
        } else {
            if (j.e(str2 + File.separator + str3)) {
                response.put("code", 200);
                response.put(FirebaseAnalytics.Param.SUCCESS, true);
                response.put("message", FirebaseAnalytics.Param.SUCCESS);
            } else {
                response.put("code", 0);
                response.put(FirebaseAnalytics.Param.SUCCESS, false);
                response.put("message", "delete " + str + " failure");
            }
        }
        return response;
    }
}
