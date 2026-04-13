package meshsdk.datamgr;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.callback.OnHttpCallback;
import meshsdk.model.json.CloudDevice;
import meshsdk.util.SharedPreferenceHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class LDSDeviceApi {
    private static final String TAG = "LDSDeviceApi";

    public void editDevice(final String mac, final int bleMeshProtocolVersion) {
        log("ota成功,编辑设备:" + mac + ",修改设备协议版本为:" + bleMeshProtocolVersion);
        getCloudDevices(SharedPreferenceHelper.getHouseId(BaseApplication.b()), new OnHttpCallback<List<CloudDevice>>() {
            public void onResult(List<CloudDevice> cloudDevices) {
                if (cloudDevices != null) {
                    for (CloudDevice cloudDevice : cloudDevices) {
                        if (cloudDevice.mac.equals(mac)) {
                            final String id = cloudDevice.id;
                            String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/v28/devices/" + id;
                            JSONObject headerJson = new JSONObject();
                            try {
                                headerJson.put("token", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", ""));
                            } catch (Exception e) {
                            }
                            JSONObject paramObj = new JSONObject();
                            try {
                                paramObj.put("bleMeshProtocolVersion", bleMeshProtocolVersion);
                            } catch (Exception e2) {
                            }
                            JSONObject jSONObject = paramObj;
                            b0.b().M(BaseApplication.b(), (b<a>) null, url, headerJson.toString(), paramObj.toString(), new i<String>() {
                                /* access modifiers changed from: protected */
                                public void onStart(io.reactivex.disposables.b d) {
                                }

                                /* access modifiers changed from: protected */
                                public void onError(ApiException e) {
                                    LDSDeviceApi lDSDeviceApi = LDSDeviceApi.this;
                                    lDSDeviceApi.logE("编辑device信息失败 ==>" + e.getMsg() + ",mac:" + mac + ",deviceId:" + id);
                                }

                                /* access modifiers changed from: protected */
                                public void onSuccess(String response) {
                                    LDSDeviceApi lDSDeviceApi = LDSDeviceApi.this;
                                    lDSDeviceApi.log("编辑device信息成功,mac:" + mac + ",deviceId:" + id + ",bleMeshProtocolVersion:" + bleMeshProtocolVersion + ",resposne" + response);
                                    try {
                                        if (MeshDataManager.otaSuccessList.contains(mac)) {
                                            MeshDataManager.otaSuccessList.remove(mac);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    public void getCloudDevices(String houseId, final OnHttpCallback<List<CloudDevice>> callback) {
        log("getCloudDevices houseId:" + houseId);
        String httpServer = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
        if (!TextUtils.isEmpty(httpServer)) {
            String url = httpServer + "/v15/devices";
            JSONObject paramJson = new JSONObject();
            try {
                paramJson.put("houseId", (Object) houseId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            b0.b().K(BaseApplication.b(), (b<a>) null, url, (String) null, paramJson.toString(), new i<String>() {
                /* access modifiers changed from: protected */
                public void onStart(io.reactivex.disposables.b d) {
                }

                /* access modifiers changed from: protected */
                public void onError(ApiException e) {
                    LDSDeviceApi lDSDeviceApi = LDSDeviceApi.this;
                    lDSDeviceApi.logE("查询设备列表错误 ==>" + e.getMsg());
                    OnHttpCallback onHttpCallback = callback;
                    if (onHttpCallback != null) {
                        onHttpCallback.onResult(null);
                    }
                }

                /* access modifiers changed from: protected */
                public void onSuccess(String response) {
                    LDSDeviceApi lDSDeviceApi = LDSDeviceApi.this;
                    lDSDeviceApi.log("查询设备列表success==>" + response);
                    try {
                        List<CloudDevice> devices = (List) new Gson().fromJson(response, new TypeToken<List<CloudDevice>>() {
                        }.getType());
                        OnHttpCallback onHttpCallback = callback;
                        if (onHttpCallback != null) {
                            onHttpCallback.onResult(devices);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        OnHttpCallback onHttpCallback2 = callback;
                        if (onHttpCallback2 != null) {
                            onHttpCallback2.onResult(null);
                        }
                    }
                }
            });
        }
    }

    public void log(String msg) {
        MeshLog.i("LDSDeviceApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }

    public void logE(String msg) {
        MeshLog.e("LDSDeviceApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }
}
