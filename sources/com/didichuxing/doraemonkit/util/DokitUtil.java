package com.didichuxing.doraemonkit.util;

import androidx.annotation.StringRes;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.DoraemonKit;
import java.io.IOException;
import okhttp3.c0;
import okio.c;
import org.json.JSONObject;

public class DokitUtil {
    public static String getString(@StringRes int stringId) {
        return DoraemonKit.APPLICATION.getString(stringId);
    }

    @StringRes
    public static int getStringId(String str) {
        try {
            return DoraemonKit.APPLICATION.getResources().getIdentifier(str, TypedValues.Custom.S_STRING, DoraemonKit.APPLICATION.getPackageName());
        } catch (Exception e) {
            LogHelper.e("getStringId", "getStringId===>" + str);
            return -1;
        }
    }

    public static String requestBodyToString(c0 requestBody) {
        try {
            c buffer = new c();
            requestBody.writeTo(buffer);
            return buffer.a1();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String param2Json(String param) {
        String[] params = param.split("&");
        JSONObject jsonObject = new JSONObject();
        for (String p : params) {
            String[] ps = p.split("=");
            jsonObject.put(ps[0], (Object) ps[1]);
        }
        return jsonObject.toString();
    }
}
