package com.leedarson.log.reporter;

import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.log.sensorsdata.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SensorsReporter */
public class f implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    JSONObject a;
    private String b = "unknown";

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1340, new Class[0], Void.TYPE).isSupported) {
            a.b().m(this.b, this.a);
        }
    }

    public void a(String content) {
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 1341, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jSONObject = new JSONObject(content);
                this.a = jSONObject;
                String optString = jSONObject.optString("eventName");
                this.b = optString;
                if (TextUtils.isEmpty(optString)) {
                    this.b = this.a.optString("traceId");
                }
                if (TextUtils.isEmpty(this.b)) {
                    this.b = this.a.optString(FirebaseAnalytics.Param.METHOD);
                }
                JSONArray arr = this.a.getJSONArray("message");
                JSONArray newArr = new JSONArray();
                for (int i = 0; i < arr.length(); i++) {
                    Object o = arr.get(i);
                    if (o instanceof JSONObject) {
                        JSONObject obj = (JSONObject) o;
                        Iterator<String> keys = obj.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            Object value = obj.opt(key);
                            newArr.put((Object) key + ":" + value.toString());
                        }
                    } else {
                        newArr.put(arr.get(i));
                    }
                }
                this.a.put("message", (Object) newArr);
            } catch (JSONException e) {
                e.printStackTrace();
                this.a = new JSONObject();
            }
        }
    }
}
