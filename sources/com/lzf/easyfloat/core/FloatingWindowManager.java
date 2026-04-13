package com.lzf.easyfloat.core;

import android.content.Context;
import android.view.View;
import com.lzf.easyfloat.EasyFloatMessageKt;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.interfaces.FloatCallbacks;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.utils.Logger;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FloatingWindowManager.kt */
public final class FloatingWindowManager {
    @NotNull
    private static final String DEFAULT_TAG = "default";
    @NotNull
    public static final FloatingWindowManager INSTANCE = new FloatingWindowManager();
    @NotNull
    private static final ConcurrentHashMap<String, FloatingWindowHelper> windowMap = new ConcurrentHashMap<>();

    private FloatingWindowManager() {
    }

    @NotNull
    public final ConcurrentHashMap<String, FloatingWindowHelper> getWindowMap() {
        return windowMap;
    }

    public final void create(@NotNull Context context, @NotNull FloatConfig config) {
        FloatCallbacks.Builder builder;
        q<Boolean, String, View, x> createdResult$easyfloat_release;
        k.e(context, "context");
        k.e(config, "config");
        if (!checkTag(config)) {
            FloatingWindowHelper helper = new FloatingWindowHelper(context, config);
            helper.createWindow(new FloatingWindowManager$create$1(config, helper));
            return;
        }
        OnFloatCallbacks callbacks = config.getCallbacks();
        if (callbacks != null) {
            callbacks.createdResult(false, EasyFloatMessageKt.WARN_REPEATED_TAG, (View) null);
        }
        FloatCallbacks floatCallbacks = config.getFloatCallbacks();
        if (!(floatCallbacks == null || (builder = floatCallbacks.getBuilder()) == null || (createdResult$easyfloat_release = builder.getCreatedResult$easyfloat_release()) == null)) {
            createdResult$easyfloat_release.invoke(false, EasyFloatMessageKt.WARN_REPEATED_TAG, null);
        }
        Logger.INSTANCE.w(EasyFloatMessageKt.WARN_REPEATED_TAG);
    }

    public static /* synthetic */ x dismiss$default(FloatingWindowManager floatingWindowManager, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return floatingWindowManager.dismiss(str, z);
    }

    @Nullable
    public final x dismiss(@Nullable String tag, boolean force) {
        FloatingWindowHelper $this$dismiss_u24lambda_u2d0 = getHelper(tag);
        if ($this$dismiss_u24lambda_u2d0 == null) {
            return null;
        }
        if (force) {
            $this$dismiss_u24lambda_u2d0.remove(force);
        } else {
            $this$dismiss_u24lambda_u2d0.exitAnim();
        }
        return x.a;
    }

    @Nullable
    public final FloatingWindowHelper remove(@Nullable String floatTag) {
        return windowMap.remove(getTag(floatTag));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0018, code lost:
        r3 = r3.getConfig();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ kotlin.x visible$default(com.lzf.easyfloat.core.FloatingWindowManager r0, boolean r1, java.lang.String r2, boolean r3, int r4, java.lang.Object r5) {
        /*
            r5 = r4 & 2
            if (r5 == 0) goto L_0x0005
            r2 = 0
        L_0x0005:
            r4 = r4 & 4
            if (r4 == 0) goto L_0x0023
            java.util.concurrent.ConcurrentHashMap<java.lang.String, com.lzf.easyfloat.core.FloatingWindowHelper> r3 = windowMap
            java.lang.Object r3 = r3.get(r2)
            com.lzf.easyfloat.core.FloatingWindowHelper r3 = (com.lzf.easyfloat.core.FloatingWindowHelper) r3
            r4 = 1
            if (r3 != 0) goto L_0x0018
        L_0x0016:
            r3 = r4
            goto L_0x0023
        L_0x0018:
            com.lzf.easyfloat.data.FloatConfig r3 = r3.getConfig()
            if (r3 != 0) goto L_0x001f
            goto L_0x0016
        L_0x001f:
            boolean r3 = r3.getNeedShow$easyfloat_release()
        L_0x0023:
            kotlin.x r0 = r0.visible(r1, r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lzf.easyfloat.core.FloatingWindowManager.visible$default(com.lzf.easyfloat.core.FloatingWindowManager, boolean, java.lang.String, boolean, int, java.lang.Object):kotlin.x");
    }

    @Nullable
    public final x visible(boolean isShow, @Nullable String tag, boolean needShow) {
        FloatingWindowHelper helper = getHelper(tag);
        if (helper == null) {
            return null;
        }
        helper.setVisible(isShow ? 0 : 8, needShow);
        return x.a;
    }

    private final boolean checkTag(FloatConfig config) {
        config.setFloatTag(getTag(config.getFloatTag()));
        ConcurrentHashMap<String, FloatingWindowHelper> concurrentHashMap = windowMap;
        String floatTag = config.getFloatTag();
        k.c(floatTag);
        return concurrentHashMap.containsKey(floatTag);
    }

    private final String getTag(String tag) {
        return tag == null ? DEFAULT_TAG : tag;
    }

    @Nullable
    public final FloatingWindowHelper getHelper(@Nullable String tag) {
        return windowMap.get(getTag(tag));
    }
}
