package com.didichuxing.doraemonkit;

import android.app.Application;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DoraemonKit.kt */
public final class DoraemonKit {
    @Nullable
    public static Application APPLICATION = null;
    public static final DoraemonKit INSTANCE = new DoraemonKit();
    private static final String TAG = "DoraemonKit";

    public static /* synthetic */ void isShow$annotations() {
    }

    private DoraemonKit() {
    }

    public static final void install(@NotNull Application app) {
        k.f(app, "app");
        install(app, new LinkedHashMap(), new ArrayList(), "");
    }

    public static final void install(@NotNull Application app, @NotNull String productId) {
        k.f(app, "app");
        k.f(productId, "productId");
        install(app, new LinkedHashMap(), new ArrayList(), productId);
    }

    public static final void install(@NotNull Application app, @NotNull LinkedHashMap<String, List<AbstractKit>> mapKits) {
        k.f(app, "app");
        k.f(mapKits, "mapKits");
        install(app, mapKits, new ArrayList(), "");
    }

    public static final void install(@NotNull Application app, @NotNull LinkedHashMap<String, List<AbstractKit>> mapKits, @NotNull String productId) {
        k.f(app, "app");
        k.f(mapKits, "mapKits");
        k.f(productId, "productId");
        install(app, mapKits, new ArrayList(), productId);
    }

    public static final void install(@NotNull Application app, @NotNull List<AbstractKit> listKits) {
        k.f(app, "app");
        k.f(listKits, "listKits");
        install(app, new LinkedHashMap(), listKits, "");
    }

    public static final void install(@NotNull Application app, @NotNull List<AbstractKit> listKits, @NotNull String productId) {
        k.f(app, "app");
        k.f(listKits, "listKits");
        k.f(productId, "productId");
        install(app, new LinkedHashMap(), listKits, productId);
    }

    static /* synthetic */ void install$default(Application application, LinkedHashMap linkedHashMap, List list, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            linkedHashMap = new LinkedHashMap();
        }
        if ((i & 4) != 0) {
            list = new ArrayList();
        }
        if ((i & 8) != 0) {
            str = "";
        }
        install(application, linkedHashMap, list, str);
    }

    private static final void install(Application app, LinkedHashMap<String, List<AbstractKit>> mapKits, List<AbstractKit> listKits, String productId) {
        List<AbstractKit> list;
        APPLICATION = app;
        try {
            DoraemonKitReal doraemonKitReal = DoraemonKitReal.INSTANCE;
            LinkedHashMap<String, List<AbstractKit>> linkedHashMap = mapKits != null ? mapKits : new LinkedHashMap<>();
            if (listKits != null) {
                list = listKits;
            } else {
                list = new ArrayList<>();
            }
            doraemonKitReal.install(app, linkedHashMap, list, productId != null ? productId : "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void setWebDoorCallback(@Nullable WebDoorManager.WebDoorCallback callback) {
        DoraemonKitReal.INSTANCE.setWebDoorCallback(callback);
    }

    public static final void show() {
        DoraemonKitReal.INSTANCE.show();
    }

    public static final void showToolPanel() {
        DoraemonKitReal.INSTANCE.showToolPanel();
    }

    public static final void hideToolPanel() {
        DoraemonKitReal.INSTANCE.hideToolPanel();
    }

    public static final void hide() {
        DoraemonKitReal.INSTANCE.hide();
    }

    public static final void disableUpload() {
        DoraemonKitReal.INSTANCE.disableUpload();
    }

    public static final boolean isShow() {
        return DoraemonKitReal.INSTANCE.isShow();
    }

    public static final void setDebug(boolean debug) {
        DoraemonKitReal.INSTANCE.setDebug(debug);
    }

    public static final void setAwaysShowMainIcon(boolean awaysShow) {
        DokitConstant.AWAYS_SHOW_MAIN_ICON = awaysShow;
    }

    public static final void setDatabasePass(@NotNull Map<String, String> map) {
        k.f(map, "map");
        DoraemonKitReal.INSTANCE.setDatabasePass(map);
    }

    public static final void setFileManagerHttpPort(int port) {
        DoraemonKitReal.INSTANCE.setFileManagerHttpPort(port);
    }
}
