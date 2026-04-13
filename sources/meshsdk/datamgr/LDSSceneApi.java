package meshsdk.datamgr;

import android.text.TextUtils;
import com.google.maps.android.BuildConfig;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.l;
import io.reactivex.m;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.callback.OnHttpCallback;
import meshsdk.model.CustomScene;
import meshsdk.model.Scene;
import meshsdk.model.json.CloudDevice;
import meshsdk.model.json.HSL;
import meshsdk.util.LDSModel;
import meshsdk.util.SharedPreferenceHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LDSSceneApi {
    private static final String TAG = "LDSSceneApi";

    public l<Boolean> getScene(int sceneId) {
        String houseId = SharedPreferenceHelper.getHouseId(BaseApplication.b());
        String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/v28/scenes/" + sceneId;
        JSONObject headerObject = new JSONObject();
        JSONObject paramsObject = new JSONObject();
        try {
            headerObject.put("houseid", (Object) houseId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l.k(new k(this, url, headerObject, paramsObject, sceneId, houseId));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getScene$0 */
    public /* synthetic */ void a(String url, JSONObject headerObject, JSONObject paramsObject, final int sceneId, final String houseId, final m emitter) {
        b0.b().K(BaseApplication.b(), (b<a>) null, url, headerObject.toString(), paramsObject.toString(), new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
                LDSSceneApi lDSSceneApi = LDSSceneApi.this;
                lDSSceneApi.log("onStart:" + Thread.currentThread().getName());
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                emitter.onError(e);
                LDSSceneApi lDSSceneApi = LDSSceneApi.this;
                lDSSceneApi.logE("获取scene信息失败 ==>" + e.getMsg() + ",sceneId:" + sceneId);
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                String str;
                String str2 = response;
                String str3 = "HSLHue";
                LDSSceneApi lDSSceneApi = LDSSceneApi.this;
                lDSSceneApi.log("获取scene信息成功 ==>" + str2);
                try {
                    JSONArray actionArray = new JSONObject(str2).getJSONArray("actions");
                    if (actionArray != null) {
                        int i = 0;
                        while (i < actionArray.length()) {
                            final List ruleList = new ArrayList();
                            JSONObject actionObject = actionArray.getJSONObject(i);
                            JSONObject params = actionObject.getJSONObject("params");
                            if (params.has("OnOff")) {
                                ruleList.add(new CustomScene.SceneRule(4096, Integer.valueOf(params.getInt("OnOff")), 0));
                            }
                            if (params.has("Dimming")) {
                                ruleList.add(new CustomScene.SceneRule(LDSModel.MODEL_BRIGHTNESS_CTRL, Integer.valueOf(params.getInt("Dimming")), 0));
                            }
                            if (params.has("CCT")) {
                                ruleList.add(new CustomScene.SceneRule(LDSModel.MODEL_TEMP_CTRL, Integer.valueOf(params.getInt("CCT")), 0));
                            } else if (params.has(str3)) {
                                ruleList.add(new CustomScene.SceneRule(LDSModel.MODEL_HSL_CTRL, new HSL(params.getInt(str3), params.getInt("HSLSaturation"), params.getInt("HSLLightness")), 1));
                            }
                            String mac = actionObject.getString("mac");
                            if (!TextUtils.isEmpty(mac)) {
                                if (!BuildConfig.TRAVIS.equals(mac)) {
                                    str = str3;
                                    i++;
                                    str3 = str;
                                }
                            }
                            final String deviceId = actionObject.getString("id");
                            str = str3;
                            new LDSDeviceApi().getCloudDevices(houseId, new OnHttpCallback<List<CloudDevice>>() {
                                public void onResult(List<CloudDevice> cloudDevices) {
                                    String mac = "";
                                    if (cloudDevices != null) {
                                        Iterator<CloudDevice> it = cloudDevices.iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                CloudDevice cloudDevice = it.next();
                                                String str = cloudDevice.id;
                                                if (str != null && str.equals(deviceId)) {
                                                    mac = cloudDevice.mac;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                    if (!TextUtils.isEmpty(mac)) {
                                        SIGMesh.getInstance().addSceneAction(sceneId, mac, ruleList, new MeshSceneCallback(SIGMesh.getInstance(), "") {
                                            public void onSuccess(int sceneId1, Scene scene, int meshAddr) {
                                                LDSSceneApi.this.log("addSceneAction success");
                                                emitter.onNext(true);
                                            }

                                            public void onFail(int code, String msg, Scene scene, int meshAddr) {
                                                LDSSceneApi.this.log("addSceneAction fail");
                                                emitter.onNext(false);
                                            }
                                        });
                                    }
                                }
                            });
                            i++;
                            str3 = str;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LDSSceneApi lDSSceneApi2 = LDSSceneApi.this;
                    lDSSceneApi2.log("getScene fail exception:" + e.getMessage());
                    emitter.onError(e);
                }
            }
        });
    }

    public void log(String msg) {
        MeshLog.i("LDSSceneApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }

    public void logE(String msg) {
        MeshLog.e("LDSSceneApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }
}
