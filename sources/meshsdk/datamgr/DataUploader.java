package meshsdk.datamgr;

import android.content.Context;
import com.leedarson.serviceimpl.reporters.k;
import com.telink.ble.mesh.core.MeshUtils;
import io.reactivex.functions.f;
import io.reactivex.l;
import io.reactivex.o;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import meshsdk.ConfigUtil;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.model.MeshInfo;
import meshsdk.model.json.MeshStorage;
import meshsdk.model.json.MeshStorageService;
import meshsdk.util.SharedPreferenceHelper;

public abstract class DataUploader {
    protected String appId;
    protected Context context;

    /* access modifiers changed from: protected */
    public abstract l<String> parseVersionFromResponse(Object obj);

    /* access modifiers changed from: protected */
    public abstract l<String> postData(String str, String str2);

    public DataUploader(Context context2, String appId2) {
        this.context = context2;
        this.appId = appId2;
    }

    public l<String> exportAndUpload(final String houseId) {
        final String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return l.F(1).b0(com.leedarson.base.http.observer.l.g).J(com.leedarson.base.http.observer.l.g).u(new f<Integer, o<String>>() {
            public o<String> apply(Integer integer) {
                if (DataUploader.this.exportMeshJsonFile(houseId, timeStamp) != null) {
                    k.a("上传meshjson前，写入meshinfo信息到 本地meshjson文件成功");
                    return DataUploader.this.postData(houseId, MeshStorageService.getInstance().meshToJsonString(SIGMesh.getInstance().getMeshInfo(), houseId));
                }
                k.a("上传meshjson前，写入meshinfo信息到 本地meshjson文件【失败】");
                return l.F("");
            }
        });
    }

    public l<String> exportAndUpload(final String houseId, final MeshStorage meshStorage) {
        return l.F(1).b0(com.leedarson.base.http.observer.l.g).J(com.leedarson.base.http.observer.l.g).u(new f<Integer, o<String>>() {
            public o<String> apply(Integer integer) {
                if (DataUploader.this.exportMeshJsonFile(houseId, meshStorage) != null) {
                    k.a("2写入meshinfo信息到 本地meshjson文件成功");
                    return DataUploader.this.postData(houseId, MeshStorageService.getInstance().meshToJsonString(meshStorage));
                }
                k.a("2写入meshinfo信息到 本地meshjson文件【失败】");
                return l.F("");
            }
        });
    }

    public File exportMeshJsonFile(String houseId, String timeStamp) {
        if (!MeshUtils.m(this.context, houseId)) {
            MeshLog.e("exportMeshJsonFile failed, houseid不一致");
            return null;
        }
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        MeshLog.d("Mesh info to json");
        MeshDataManager.exportTraceByELK("beforeUpload:" + MeshStorageService.getInstance().meshToJsonString(meshInfo, SharedPreferenceHelper.getHouseId(this.context)), "debug", "exportMeshJsonFile");
        return MeshStorageService.getInstance().exportMeshToJsonFile(new File(ConfigUtil.getConfigPath(this.context, houseId)), MeshStorageService.JSON_FILE, meshInfo, meshInfo.meshNetKeyList, timeStamp, houseId);
    }

    public File exportMeshJsonFile(String houseId, MeshStorage meshStorage) {
        return MeshStorageService.getInstance().exportMeshToJsonFile(new File(ConfigUtil.getConfigPath(this.context, houseId)), MeshStorageService.JSON_FILE, meshStorage);
    }
}
