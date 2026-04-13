package meshsdk.datamgr;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import meshsdk.MeshLog;
import org.json.JSONObject;

public class LDSRoutinesApi {
    public static final String TAG = "LDSRoutinesApi";

    public void editRoutines(final int routineId, String macAddress, int smartMeshAddress) {
        String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/v28/routines/" + routineId;
        JSONObject headerJson = new JSONObject();
        try {
            headerJson.put("token", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", ""));
        } catch (Exception e) {
        }
        final JSONObject paramObj = new JSONObject();
        try {
            JSONObject smartAddressObj = new JSONObject();
            smartAddressObj.put(macAddress, smartMeshAddress);
            paramObj.put("bleMeshSmartAddress", (Object) smartAddressObj.toString());
        } catch (Exception e2) {
        }
        b0.b().M(BaseApplication.b(), (b<a>) null, url, headerJson.toString(), paramObj.toString(), new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                LDSRoutinesApi lDSRoutinesApi = LDSRoutinesApi.this;
                lDSRoutinesApi.logE("编辑Routine信息失败 ==>" + e.getMsg() + ",routineId:" + routineId);
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                LDSRoutinesApi lDSRoutinesApi = LDSRoutinesApi.this;
                lDSRoutinesApi.log("编辑Routine信息成功,routineId:" + routineId + ",paramObj:" + paramObj + ",resposne" + response);
            }
        });
    }

    public void log(String msg) {
        MeshLog.i("LDSRoutinesApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }

    public void logE(String msg) {
        MeshLog.e("LDSRoutinesApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }
}
