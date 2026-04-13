package com.didichuxing.doraemonkit.constant;

import com.didichuxing.doraemonkit.config.GlobalConfig;
import com.didichuxing.doraemonkit.kit.network.bean.WhiteHostBean;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.didichuxing.doraemonkit.model.ActivityLifecycleInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.l0;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: DokitConstant.kt */
public final class DokitConstant {
    @NotNull
    public static Map<String, ActivityLifecycleInfo> ACTIVITY_LIFECYCLE_INFOS = new LinkedHashMap();
    public static boolean APP_HEALTH_RUNNING = GlobalConfig.getAppHealth();
    public static boolean AWAYS_SHOW_MAIN_ICON = true;
    @NotNull
    private static Map<String, String> DATABASE_PASS = l0.f();
    private static int FILE_MANAGER_HTTP_PORT = 8089;
    @NotNull
    public static final LinkedHashMap<String, List<KitWrapItem>> GLOBAL_KITS = new LinkedHashMap<>();
    @NotNull
    public static final LinkedHashMap<String, List<KitWrapItem>> GLOBAL_SYSTEM_KITS = new LinkedHashMap<>();
    @NotNull
    public static final String GROUP_ID_COMM = "dk_category_comms";
    @NotNull
    public static final String GROUP_ID_PERFORMANCE = "dk_category_performance";
    @NotNull
    public static final String GROUP_ID_PLATFORM = "dk_category_platform";
    @NotNull
    public static final String GROUP_ID_UI = "dk_category_ui";
    @NotNull
    public static final String GROUP_ID_WEEX = "dk_category_weex";
    public static final DokitConstant INSTANCE = new DokitConstant();
    public static boolean IS_NORMAL_FLOAT_MODE = true;
    public static boolean MAIN_ICON_HAS_SHOW;
    @NotNull
    public static String PRODUCT_ID = "";
    @NotNull
    private static final g SYSTEM_KITS_BAK_PATH$delegate = i.b(DokitConstant$SYSTEM_KITS_BAK_PATH$2.INSTANCE);
    private static int TOOL_PANEL_RV_LAST_DY;
    @NotNull
    public static List<WhiteHostBean> WHITE_HOSTS = new ArrayList();

    public static /* synthetic */ void isRpcSDK$annotations() {
    }

    @NotNull
    public final String getSYSTEM_KITS_BAK_PATH() {
        return (String) SYSTEM_KITS_BAK_PATH$delegate.getValue();
    }

    private DokitConstant() {
    }

    public final int getTOOL_PANEL_RV_LAST_DY() {
        return TOOL_PANEL_RV_LAST_DY;
    }

    public final void setTOOL_PANEL_RV_LAST_DY(int i) {
        TOOL_PANEL_RV_LAST_DY = i;
    }

    @NotNull
    public final Map<String, String> getDATABASE_PASS() {
        return DATABASE_PASS;
    }

    public final void setDATABASE_PASS(@NotNull Map<String, String> map) {
        k.f(map, "<set-?>");
        DATABASE_PASS = map;
    }

    public final int getFILE_MANAGER_HTTP_PORT() {
        return FILE_MANAGER_HTTP_PORT;
    }

    public final void setFILE_MANAGER_HTTP_PORT(int i) {
        FILE_MANAGER_HTTP_PORT = i;
    }

    public static final boolean isRpcSDK() {
        boolean isRpcSdk;
        try {
            Class.forName("com.didichuxing.doraemonkit.DoraemonKitRpc");
            isRpcSdk = true;
        } catch (ClassNotFoundException e) {
            isRpcSdk = false;
        }
        return isRpcSdk;
    }

    @NotNull
    public static final String dealDidiPlatformPath(@NotNull String oldPath, int fromSDK) {
        String str = oldPath;
        k.f(oldPath, "oldPath");
        if (fromSDK == DokitDbManager.FROM_SDK_OTHER) {
            return str;
        }
        String newPath = oldPath;
        if (!x.S(oldPath, "/kop", false, 2, (Object) null)) {
            return newPath;
        }
        Object[] array = x.F0(oldPath, new String[]{"\\/"}, false, 0, 6, (Object) null).toArray(new String[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        } else if (array.length <= 1) {
            return newPath;
        } else {
            Object[] array2 = x.F0(oldPath, new String[]{"\\/"}, false, 0, 6, (Object) null).toArray(new String[0]);
            if (array2 != null) {
                String firstPath = ((String[]) array2)[1];
                if (!x.S(firstPath, "kop", false, 2, (Object) null)) {
                    return newPath;
                }
                return w.H(oldPath, '/' + firstPath, "", false, 4, (Object) null);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }
}
