package com.didichuxing.doraemonkit;

import android.app.Application;
import com.blankj.utilcode.util.b0;
import java.io.File;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.Nullable;

/* compiled from: DoraemonKitReal.kt */
public final class DoraemonKitReal$startBigFileInspect$1 extends b0.e<Object> {
    DoraemonKitReal$startBigFileInspect$1() {
    }

    @Nullable
    public Object doInBackground() {
        DoraemonKitReal doraemonKitReal = DoraemonKitReal.INSTANCE;
        Application access$getAPPLICATION$p = DoraemonKitReal.APPLICATION;
        if (access$getAPPLICATION$p == null) {
            k.n();
        }
        File externalCacheDir = access$getAPPLICATION$p.getExternalCacheDir();
        if (externalCacheDir != null) {
            doraemonKitReal.traverseFile(externalCacheDir.getParentFile());
        }
        Application access$getAPPLICATION$p2 = DoraemonKitReal.APPLICATION;
        if (access$getAPPLICATION$p2 == null) {
            k.n();
        }
        File innerCacheDir = access$getAPPLICATION$p2.getCacheDir();
        if (innerCacheDir == null) {
            return null;
        }
        doraemonKitReal.traverseFile(innerCacheDir.getParentFile());
        return null;
    }

    public void onSuccess(@Nullable Object result) {
    }
}
