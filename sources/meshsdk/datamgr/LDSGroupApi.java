package meshsdk.datamgr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.l;
import io.reactivex.m;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.model.json.CloudGroup;
import meshsdk.util.SharedPreferenceHelper;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class LDSGroupApi {
    private static final String TAG = "LDSGroupApi";

    public l getVisibleGroups() {
        String houseId = SharedPreferenceHelper.getHouseId(BaseApplication.b());
        String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/groups";
        JSONObject paramJson = new JSONObject();
        try {
            paramJson.put("houseId", (Object) houseId);
            paramJson.put("filter", 1);
            paramJson.put("visible", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l.k(new i(this, url, paramJson));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getVisibleGroups$0 */
    public /* synthetic */ void b(String url, JSONObject paramJson, final m emitter) {
        b0.b().K(BaseApplication.b(), (b<a>) null, url, (String) null, paramJson.toString(), new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                LDSGroupApi lDSGroupApi = LDSGroupApi.this;
                lDSGroupApi.logE("查询首页group列表错误 ==>" + e.getMsg());
                emitter.onError(e);
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                LDSGroupApi lDSGroupApi = LDSGroupApi.this;
                lDSGroupApi.log("query visibleGroupApi success==>" + response);
                try {
                    emitter.onNext((List) new Gson().fromJson(response, new TypeToken<List<CloudGroup>>() {
                    }.getType()));
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    public l getMusicGroup() {
        String houseId = SharedPreferenceHelper.getHouseId(BaseApplication.b());
        String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/groups";
        JSONObject paramJson = new JSONObject();
        try {
            paramJson.put("houseId", (Object) houseId);
            paramJson.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "MUSIC_RHYTHM_GROUP");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l.k(new j(this, url, paramJson));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getMusicGroup$1 */
    public /* synthetic */ void a(String url, JSONObject paramJson, final m emitter) {
        b0.b().K(BaseApplication.b(), (b<a>) null, url, (String) null, paramJson.toString(), new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                LDSGroupApi lDSGroupApi = LDSGroupApi.this;
                lDSGroupApi.logE("查询音乐律动组错误 ==>" + e.getMsg());
                emitter.onError(e);
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                LDSGroupApi lDSGroupApi = LDSGroupApi.this;
                lDSGroupApi.log("query musicGroupApi success==>" + response);
                try {
                    emitter.onNext((List) new Gson().fromJson(response, new TypeToken<List<CloudGroup>>() {
                    }.getType()));
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });
    }

    public void editGroup(int groupId, String bz) {
    }

    public void log(String msg) {
        MeshLog.i("LDSGroupApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }

    public void logE(String msg) {
        MeshLog.e("LDSGroupApi,thread:" + Thread.currentThread().getName() + ":" + msg);
    }
}
