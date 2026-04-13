package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import com.blankj.utilcode.util.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: RenameFileAction.kt */
public final class RenameFileAction {
    public static final RenameFileAction INSTANCE = new RenameFileAction();

    private RenameFileAction() {
    }

    @NotNull
    public final Map<String, Object> renameFileRes(@NotNull String newName, @NotNull String filePath) {
        k.f(newName, "newName");
        k.f(filePath, Progress.FILE_PATH);
        Map response = new LinkedHashMap();
        if (j.z(filePath)) {
            j.C(filePath, newName);
            response.put("code", 200);
            response.put(FirebaseAnalytics.Param.SUCCESS, true);
            response.put("message", FirebaseAnalytics.Param.SUCCESS);
        } else {
            response.put("code", 0);
            response.put(FirebaseAnalytics.Param.SUCCESS, false);
            response.put("message", filePath + " is not exists");
        }
        return response;
    }
}
