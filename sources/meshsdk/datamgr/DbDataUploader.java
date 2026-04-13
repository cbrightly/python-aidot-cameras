package meshsdk.datamgr;

import android.content.Context;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceimpl.reporters.k;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.l;
import io.reactivex.m;
import meshsdk.ConfigUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class DbDataUploader extends DataUploader {
    public DbDataUploader(Context context, String appId) {
        super(context, appId);
    }

    /* access modifiers changed from: protected */
    public l<String> postData(String houseId, String meshJson) {
        String httpServer = SharePreferenceUtils.getPrefString(this.context, "httpServer", "");
        String accessToken = SharePreferenceUtils.getPrefString(this.context, "accessToken", "");
        String url = httpServer + "/BLEMesh/meshJson";
        String appId = this.appId;
        JSONObject headerMap = new JSONObject();
        JSONObject joParams = new JSONObject();
        try {
            headerMap.put("appId", (Object) appId);
            headerMap.put("token", (Object) accessToken);
            headerMap.put("terminal", (Object) "app");
            headerMap.put("Content-Type", (Object) "application/json");
            try {
                joParams.put("houseId", (Object) houseId);
                try {
                    joParams.put("meshJson", (Object) meshJson);
                } catch (JSONException e) {
                    e = e;
                }
            } catch (JSONException e2) {
                e = e2;
                String str = meshJson;
                e.printStackTrace();
                return l.k(new h(this, url, headerMap, joParams, houseId, meshJson));
            }
        } catch (JSONException e3) {
            e = e3;
            String str2 = houseId;
            String str3 = meshJson;
            e.printStackTrace();
            return l.k(new h(this, url, headerMap, joParams, houseId, meshJson));
        }
        return l.k(new h(this, url, headerMap, joParams, houseId, meshJson));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$postData$0 */
    public /* synthetic */ void a(String url, JSONObject headerMap, JSONObject joParams, final String houseId, final String meshJson, final m emitter) {
        b0.b().O(this.context, (b<a>) null, url, headerMap.toString(), joParams.toString(), new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                k.a("上传meshjson【失败】:" + e.getMessage());
                emitter.onError(e);
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                String version = "";
                try {
                    version = new JSONObject(response).optString(ConfigUtil.VERSION_FILE);
                    k.a("上传meshjson成功,houseid:" + houseId + ",返回版本号:" + version + ",meshjson:" + meshJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                    k.a("上传meshjson失敗【异常】" + e.getMessage() + ",houseid:" + houseId);
                }
                emitter.onNext(version);
                emitter.onComplete();
            }
        });
    }

    /* access modifiers changed from: protected */
    public l<String> parseVersionFromResponse(Object o) {
        return null;
    }
}
