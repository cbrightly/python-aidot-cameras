package meshsdk.util;

import android.content.Context;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.serviceimpl.reporters.k;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.telink.ble.mesh.util.FileSystem;
import io.reactivex.l;
import io.reactivex.m;
import java.io.File;
import meshsdk.ConfigUtil;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.datamgr.DBDataFetcher;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.MeshNetKey;
import meshsdk.model.json.MeshJSON;
import meshsdk.model.json.MeshStorageService;
import meshsdk.model.json.NetworkEncryptInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;

public class MeshEncryptInfoUtil {
    private Context context;
    private MeshDataManager meshDataManager;
    private String traceId = "getNetworkEncryptInfo";

    public MeshEncryptInfoUtil(Context context2, MeshDataManager meshDataManager2) {
        this.context = context2;
        this.meshDataManager = meshDataManager2;
    }

    public l<JSONObject> getNetworkEncryptInfo(String houseId) {
        if (TextUtils.isEmpty(houseId)) {
            k.a("houseId为空，提交当前构建的meshinfo中的network信息");
            return getCurrentNetworkInfo();
        }
        File file = new File(ConfigUtil.getConfigPath(this.context, houseId), MeshStorageService.JSON_FILE);
        if (file.exists()) {
            return getLocalExistNetWorkInfo(file, houseId);
        }
        return getLocalUnExistNetworkInfo(houseId);
    }

    private l<JSONObject> getCurrentNetworkInfo() {
        return l.k(g.a);
    }

    static /* synthetic */ void lambda$getCurrentNetworkInfo$0(m emitter) {
        NetworkEncryptInfo networkEncryptInfo = new NetworkEncryptInfo();
        MeshNetKey defaultNetKey = SIGMesh.getInstance().getMeshInfo().getDefaultNetKey();
        networkEncryptInfo.netKeyIndex = defaultNetKey.index;
        networkEncryptInfo.netKey = ByteUtils.e(defaultNetKey.key).toUpperCase();
        networkEncryptInfo.appKey = ByteUtils.e(SIGMesh.getInstance().getMeshInfo().appKeyList.get(0).key).toUpperCase();
        networkEncryptInfo.appKeyIndex = 0;
        networkEncryptInfo.IVIndex = SIGMesh.getInstance().getMeshInfo().ivIndex;
        networkEncryptInfo.meshUUID = SIGMesh.getInstance().getMeshInfo().meshUUID;
        emitter.onNext(networkEncryptInfo.toJSON());
        MeshLog.i("获取当前家 mesh network info,house id 为空,提交当前mesh信息");
        emitter.onComplete();
    }

    private l<JSONObject> getLocalExistNetWorkInfo(File file, String houseId) {
        k.a("获取本地指定 house mesh network info houseId=" + houseId);
        return l.k(new e(file, houseId));
    }

    static /* synthetic */ void lambda$getLocalExistNetWorkInfo$1(File file, String houseId, m emitter) {
        NetworkEncryptInfo networkEncryptInfo = new NetworkEncryptInfo();
        try {
            JSONObject jsonObject = new JSONObject((String) FileSystem.b(file)[1]);
            networkEncryptInfo.meshUUID = jsonObject.getString("meshUUID");
            JSONArray appKeys = jsonObject.getJSONArray("appKeys");
            int i = 0;
            while (true) {
                if (i >= appKeys.length()) {
                    break;
                }
                JSONObject appKey = appKeys.getJSONObject(i);
                if (appKey.optInt(FirebaseAnalytics.Param.INDEX) == 0) {
                    networkEncryptInfo.appKeyIndex = 0;
                    networkEncryptInfo.appKey = appKey.optString(CacheEntity.KEY);
                    break;
                }
                i++;
            }
            JSONArray netKeys = jsonObject.getJSONArray("netKeys");
            int i2 = 0;
            while (true) {
                if (i2 >= netKeys.length()) {
                    break;
                }
                JSONObject netKey = netKeys.getJSONObject(i2);
                if (netKey.optInt(FirebaseAnalytics.Param.INDEX) == 0) {
                    networkEncryptInfo.netKeyIndex = 0;
                    networkEncryptInfo.netKey = netKey.optString(CacheEntity.KEY);
                    break;
                }
                i2++;
            }
            k.a("本地存在house:" + houseId + "的meshjson，提交该meshjson中的mesh network信息");
            emitter.onNext(networkEncryptInfo.toJSON());
            emitter.onComplete();
        } catch (Exception e) {
            networkEncryptInfo.exception = e.getMessage();
            k.a("getLocalExistNetWorkInfo exception:" + e.getMessage());
            e.printStackTrace();
            emitter.onError(e);
        }
    }

    private l<JSONObject> getLocalUnExistNetworkInfo(String houseId) {
        k.a("本地 mesh json不存在，从云端 获取,hourseId:" + houseId);
        return DBDataFetcher.getJsonRequest(this.context, houseId, SharePreferenceUtils.getPrefString(this.context, "APP_ID", "")).P(1).b0(com.leedarson.base.http.observer.l.g).J(com.leedarson.base.http.observer.l.g).p(h.c).u(new f(this, houseId));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$getLocalUnExistNetworkInfo$3 */
    public /* synthetic */ Object a(String houseId, Object o) {
        String resp = o.toString();
        if (TextUtils.isEmpty(resp)) {
            return getTempMeshNetworkInfo(houseId);
        }
        MeshJSON meshJSON = (MeshJSON) new Gson().fromJson(resp, new TypeToken<MeshJSON>() {
        }.getType());
        if (TextUtils.isEmpty(meshJSON.meshJson)) {
            return getTempMeshNetworkInfo(houseId);
        }
        k.a("本地meshjson不存在，首次去云端获取meshjson");
        this.meshDataManager.queryMeshConfig(true);
        File file = FileSystem.e(new File(ConfigUtil.getConfigPath(this.context, houseId)), MeshStorageService.JSON_FILE, meshJSON.meshJson);
        MeshLog.e("文件是否存在:" + file.getAbsolutePath() + "," + file.exists());
        k.a("从云端获取 mesh json 成功，保存到本地,解析返回network相关信息");
        return getLocalExistNetWorkInfo(file, houseId);
    }

    private l<JSONObject> getTempMeshNetworkInfo(String houseId) {
        SIGMesh.getInstance().getMeshInfo().saveOrUpdate(this.context, "getNetworkEncryptInfo 首次创建引起的保存");
        MeshLogNew.meshJsonLog("云端不存在meshjson信息，提交当前构建的meshinfo给云端");
        this.meshDataManager.upload(houseId, (MeshDataManager.OnUploadCallback) null);
        return getCurrentNetworkInfo();
    }
}
