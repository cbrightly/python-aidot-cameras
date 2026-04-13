package com.leedarson.newui.repos;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.i;
import com.leedarson.newui.door_phone.repos.g;
import com.leedarson.serviceimpl.http.manager.b0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.android.a;
import io.reactivex.disposables.b;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: LiveRepos */
public class r extends g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String b = "LiveRepos";

    public b e(String packageId, int subscribeStatus, i<String> iVar) {
        Object[] objArr = {packageId, new Integer(subscribeStatus), iVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4463, new Class[]{String.class, Integer.TYPE, i.class}, b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        i<String> iVar2 = iVar;
        JSONObject paramsJson = new JSONObject();
        try {
            paramsJson.put("packageId", (Object) packageId);
            paramsJson.put("subscribeStatus", subscribeStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<a>) null, "/api/ipc/recordPlanController/updateSubscribeStatus", "", paramsJson.toString(), iVar2);
    }

    public b d(String deviceId, i<String> iVar) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, iVar}, this, changeQuickRedirect2, false, 4464, new Class[]{String.class, i.class}, b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        i<String> iVar2 = iVar;
        JSONArray devicesAr = new JSONArray();
        try {
            devicesAr.put((Object) deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b0.b().O(BaseApplication.b(), (com.trello.rxlifecycle3.b<a>) null, "/api/ipc/liveStream/liveStreamParam", "", devicesAr.toString(), iVar2);
    }

    public b c(String deviceId, String houseId, i<String> iVar) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, houseId, iVar}, this, changeQuickRedirect2, false, 4465, new Class[]{cls, cls, i.class}, b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        i<String> iVar2 = iVar;
        JSONObject paramsJson = new JSONObject();
        JSONArray devicesAr = new JSONArray();
        try {
            devicesAr.put((Object) deviceId);
            paramsJson.put("deviceIds", (Object) devicesAr);
            paramsJson.put("houseId", (Object) houseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b0.b().O(BaseApplication.b(), (com.trello.rxlifecycle3.b<a>) null, "/api/ipc/recordPlanController/getDeviceBindPackageInfo", "", paramsJson.toString(), iVar2);
    }
}
