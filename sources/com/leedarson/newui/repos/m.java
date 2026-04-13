package com.leedarson.newui.repos;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.utils.w;
import com.leedarson.newui.door_phone.repos.g;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: CloudRepos */
public class m extends g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String b = "CloudRepos";

    private String f(String url) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 4418, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return String.format(Locale.US, "%s/%s", new Object[]{SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", ""), url});
    }

    private JSONObject e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4419, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
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

    public void c(i<String> iVar) {
        if (!PatchProxy.proxy(new Object[]{iVar}, this, changeQuickRedirect, false, 4420, new Class[]{i.class}, Void.TYPE).isSupported) {
            b0.b().K(BaseApplication.b(), (b<a>) null, f("feedback/aiEventFeedBackUrl"), e().toString(), new JSONObject().toString(), iVar);
        }
    }

    public io.reactivex.disposables.b d(String houseId, i<String> iVar) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{houseId, iVar}, this, changeQuickRedirect2, false, 4421, new Class[]{String.class, i.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        i<String> iVar2 = iVar;
        JSONObject paramsJson = new JSONObject();
        try {
            paramsJson.put("deviceIds", (Object) new JSONArray());
            paramsJson.put("houseId", (Object) houseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b0.b().O(BaseApplication.b(), (b<a>) null, f("/api/ipc/recordPlanController/getDeviceBindPackageInfo"), e().toString(), paramsJson.toString(), iVar2);
    }
}
