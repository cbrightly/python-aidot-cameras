package meshsdk.util;

import com.google.gson.JsonArray;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.l;
import io.reactivex.m;
import meshsdk.datamgr.MeshDataManager;
import okhttp3.e0;
import org.json.JSONArray;
import org.json.JSONObject;

@Deprecated
public class CheckMeshConfigUtils {
    @Deprecated
    public static l<Boolean> toCheckRemoteFileUploadSuccess(String fileId) {
        return queryConfigFileUrlByFileId(fileId).J(com.leedarson.base.http.observer.l.g).b0(com.leedarson.base.http.observer.l.g).u(new b(fileId)).u(a.c);
    }

    static /* synthetic */ void lambda$toCheckRemoteFileUploadSuccess$1(e0 responseBody, m observableEmitter) {
        long totalLength = responseBody.contentLength();
        MeshDataManager.exportTraceByELK("尝试验证远程地址下载的文件长度 length=" + totalLength, "info", "toCheckRemoteFileUploadSuccess");
        observableEmitter.onNext(Boolean.valueOf(totalLength > 0));
        observableEmitter.onComplete();
    }

    public static l<String> queryConfigFileUrlByFileId(String fileId) {
        return l.k(new d(fileId));
    }

    static /* synthetic */ void lambda$queryConfigFileUrlByFileId$3(final String fileId, final m observableEmitter) {
        Exception ex;
        try {
            JsonArray bodyDatas = new JsonArray();
            bodyDatas.add(fileId);
            try {
                b0.b().O(BaseApplication.b(), (b<a>) null, "/files", "", bodyDatas.toString(), new i<String>() {
                    /* access modifiers changed from: protected */
                    public void onStart(io.reactivex.disposables.b d) {
                    }

                    /* access modifiers changed from: protected */
                    public void onError(ApiException e) {
                        m.this.onError(e);
                        MeshDataManager.exportTraceByELK("二次确认上传是否成功失败 fileId=" + fileId + "  e:=" + e.toString(), "info", "queryConfigFileUrlByFileId");
                    }

                    /* access modifiers changed from: protected */
                    public void onSuccess(String response) {
                        if (!"".equals(response)) {
                            try {
                                JSONArray array = new JSONArray(response);
                                if (array.length() != 0) {
                                    m.this.onNext(((JSONObject) array.get(0)).getString("fileUrl"));
                                }
                            } catch (Exception ex) {
                                m mVar = m.this;
                                mVar.onError(new Throwable("根据fileId获取下载地址失败--->" + ex.toString()));
                                MeshDataManager.exportTraceByELK("二次确认上传配置失败 查询到匹配的文件为0=" + fileId + ",exception=" + ex.toString() + ",response=" + response, "info", "queryConfigFileUrlByFileId");
                            }
                            m.this.onComplete();
                        }
                    }
                });
            } catch (Exception e) {
                ex = e;
            }
        } catch (Exception e2) {
            Object obj = "";
            ex = e2;
            observableEmitter.onError(new Throwable("根据fileId获取下载地址失败--->" + ex.toString()));
            MeshDataManager.exportTraceByELK("二次确认上传配置失败异常 exception=" + ex.toString(), "info", "queryConfigFileUrlByFileId");
        }
    }
}
