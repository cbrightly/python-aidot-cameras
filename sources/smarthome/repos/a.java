package smarthome.repos;

import android.text.TextUtils;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.trello.rxlifecycle3.b;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: CoreRepos */
public class a extends b {
    private final String b = "LiveRepos";

    private String b(String url) {
        return String.format(Locale.US, "%s/%s", new Object[]{SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", ""), url});
    }

    private JSONObject a() {
        JSONObject headerJson = new JSONObject();
        String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
        String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
        String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
        try {
            headerJson.put("owner", (Object) owner);
            headerJson.put("token", (Object) accessToken);
            headerJson.put("terminal", (Object) "app");
            headerJson.put("appId", (Object) appId);
            headerJson.put("appVersion", (Object) w.E(BaseApplication.b()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headerJson;
    }

    public void c(String eventName, String distinctMessage, i<String> httpRxObserver) {
        JSONObject paramsJson = new JSONObject();
        String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
        String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
        if (!TextUtils.isEmpty("userId")) {
            try {
                paramsJson.put("appId", (Object) appId);
                paramsJson.put("eventName", (Object) eventName);
                JSONObject eventRecordObj = new JSONObject();
                eventRecordObj.put("distinctMessage", (Object) distinctMessage);
                paramsJson.put("eventRecord", (Object) eventRecordObj);
                paramsJson.put("uuid", (Object) userId);
                paramsJson.put("time", System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
            b0.b().O(BaseApplication.b(), (b<com.trello.rxlifecycle3.android.a>) null, b("logs/userEvent"), a().toString(), paramsJson.toString(), httpRxObserver);
        }
    }

    public void d(String eventName, JSONArray eventRecordArray, i<String> httpRxObserver) {
        JSONObject paramsJson = new JSONObject();
        String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
        String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
        if (!TextUtils.isEmpty("userId")) {
            try {
                paramsJson.put("appId", (Object) appId);
                paramsJson.put("eventName", (Object) eventName);
                paramsJson.put("eventRecord", (Object) eventRecordArray);
                paramsJson.put("uuid", (Object) userId);
                paramsJson.put("time", System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
            b0.b().O(BaseApplication.b(), (b<com.trello.rxlifecycle3.android.a>) null, b("logs/userEvent"), a().toString(), paramsJson.toString(), httpRxObserver);
        }
    }
}
