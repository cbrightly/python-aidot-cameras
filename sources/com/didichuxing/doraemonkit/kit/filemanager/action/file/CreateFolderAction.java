package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import com.blankj.utilcode.util.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CreateFolderAction.kt */
public final class CreateFolderAction {
    public static final CreateFolderAction INSTANCE = new CreateFolderAction();

    private CreateFolderAction() {
    }

    @NotNull
    public final Map<String, Object> createFolderRes(@NotNull String dirPath, @NotNull String fileName) {
        k.f(dirPath, "dirPath");
        k.f(fileName, Progress.FILE_NAME);
        Map response = new LinkedHashMap();
        if (j.v(dirPath)) {
            if (j.b(dirPath + File.separator + fileName)) {
                response.put("code", 200);
                response.put(FirebaseAnalytics.Param.SUCCESS, true);
                response.put("message", FirebaseAnalytics.Param.SUCCESS);
            } else {
                response.put("code", 0);
                response.put(FirebaseAnalytics.Param.SUCCESS, false);
                response.put("message", "create dir failure");
            }
        } else {
            response.put("code", 0);
            response.put(FirebaseAnalytics.Param.SUCCESS, false);
            response.put("message", "is not dir");
        }
        return response;
    }
}
