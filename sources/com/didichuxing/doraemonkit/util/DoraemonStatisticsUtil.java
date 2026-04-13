package com.didichuxing.doraemonkit.util;

import android.content.Context;
import com.blankj.utilcode.util.d;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import java.io.IOException;
import java.util.Locale;
import meshsdk.ConfigUtil;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.f;
import okhttp3.x;
import okhttp3.z;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class DoraemonStatisticsUtil {
    private static final String TAG = "DoraemonStatisticsUtil";

    private DoraemonStatisticsUtil() {
    }

    public static void uploadUserInfo(Context context) {
        String appId = SystemUtil.getPackageName(context);
        String appName = SystemUtil.getAppName(context);
        x mediaType = x.h("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appId", (Object) appId);
            jsonObject.put("appName", (Object) appName);
            jsonObject.put("appVersion", (Object) d.n());
            jsonObject.put(ConfigUtil.VERSION_FILE, (Object) "3.2.0");
            jsonObject.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "Android");
            jsonObject.put("from", (Object) "1");
            jsonObject.put(IjkMediaMeta.IJKM_KEY_LANGUAGE, (Object) Locale.getDefault().getDisplayLanguage());
        } catch (JSONException e) {
            LogHelper.e(TAG, e.toString());
        }
        new z().a(new b0.a().p(NetworkManager.APP_START_DATA_PICK_URL).k(c0.create(mediaType, jsonObject.toString())).b()).o0(new f() {
            public void onFailure(e call, IOException e) {
                e.printStackTrace();
            }

            public void onResponse(e call, d0 response) {
                response.close();
            }
        });
    }
}
