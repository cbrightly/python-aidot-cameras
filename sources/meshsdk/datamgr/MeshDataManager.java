package meshsdk.datamgr;

import android.content.Context;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.google.gson.Gson;
import com.google.maps.android.BuildConfig;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.elkstrays.a;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceimpl.reporters.k;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.util.FileSystem;
import com.trello.rxlifecycle3.b;
import io.reactivex.functions.e;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.ConfigUtil;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.callback.OnHttpCallback;
import meshsdk.datamgr.DataFetcher;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.AddDevicesBean;
import meshsdk.model.json.CloudDevice;
import meshsdk.model.json.MeshStorage;
import meshsdk.model.json.MeshStorageService;
import meshsdk.sql.SqlManager;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.MeshConstants;
import meshsdk.util.MeshEncryptInfoUtil;
import meshsdk.util.SharedPreferenceHelper;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.HttpException;

public class MeshDataManager implements EventListener<String> {
    public static NodeInfo deviceFocus = null;
    public static volatile boolean flagAddGroup = false;
    public static volatile boolean flagNetConfingAdddevices = false;
    public static volatile boolean flagOTA = false;
    public static volatile boolean isFlagAddGroupByHand = false;
    public static Set otaSuccessList = new HashSet();
    public static volatile long startOTATimespan;
    public static boolean updateError = false;
    private final int FUNC_QUERY_CONFIG = 1;
    private final int FUNC_UPLOAD = 0;
    private final int UP_DELAY = 15000;
    private String appId;
    /* access modifiers changed from: private */
    public Context context;
    public DataUploader dataUploader;
    /* access modifiers changed from: private */
    public DBDataFetcher dbDataFetcher;
    public volatile boolean isUpdateing = false;
    /* access modifiers changed from: private */
    public boolean isUploading = false;
    int retryCount = 0;
    private boolean taskRunning = false;
    private Timer timer;

    public interface OnUploadCallback {
        void onComplete();

        void onFail(String str);

        void onSuccess(String str);
    }

    public MeshDataManager(Context context2) {
        this.context = context2;
        this.appId = SharePreferenceUtils.getPrefString(context2, "APP_ID", "");
        MeshLog.d("MeshDataManager app id is:" + this.appId);
        this.timer = new Timer();
        this.dataUploader = new DbDataUploader(context2, this.appId);
        this.dbDataFetcher = new DBDataFetcher(context2, this);
    }

    public static void setCurrentDeviceFocusMeshAddress(String currentPageMac) {
        if (TextUtils.isEmpty(currentPageMac)) {
            deviceFocus = null;
            MeshLog.i("取消设备焦点");
            return;
        }
        NodeInfo findMeshNode = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, currentPageMac);
        deviceFocus = findMeshNode;
        if (findMeshNode != null) {
            MeshLog.i(String.format("设置设备焦点为: 0x%04X mac:%s", new Object[]{Integer.valueOf(findMeshNode.meshAddress), deviceFocus.macAddress}));
        }
    }

    public void clear(String callbackKey) {
        MeshLog.d("clear mesh data");
        File filesDir = this.context.getFilesDir();
        MeshService.k().n(true, "clear mesh data");
        MeshInfo meshInfo = MeshInfo.createNewMesh(this.context);
        MeshEventHandler.getInstance().setupMesh(meshInfo);
        MeshService.k().w(meshInfo.convertToConfiguration());
        c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorSuccessResp().toString()));
    }

    @Deprecated
    public JSONObject getVersion() {
        String houseId = SharedPreferenceHelper.getHouseId(this.context);
        String version = String.valueOf(getMeshVersion(this.context, houseId));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dataVersion", (Object) version);
            jsonObject.put("currentHouseId", (Object) houseId);
            jsonObject.put("meshData", (Object) new JSONObject(MeshStorageService.getInstance().meshToJsonString(SIGMesh.getInstance().getMeshInfo(), houseId)));
            JSONArray dictJson = new JSONArray(new Gson().toJson((Object) SqlManager.getAllDictBean()));
            jsonObject.put("dictList", (Object) dictJson);
            JSONArray arr = SqlManager.getDelNodes();
            jsonObject.put("dictList", (Object) dictJson);
            jsonObject.put("delCacheNode", (Object) arr);
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, MeshService.k().i());
            jsonObject.put("directMac", (Object) nodeInfo == null ? BuildConfig.TRAVIS : nodeInfo.macAddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getConnectState() {
        boolean conn = SIGMesh.getInstance().hasConnected();
        JSONObject jsonObject = new JSONObject();
        List<NodeInfo> nodes = SIGMesh.getInstance().getMeshInfo().nodes;
        JSONArray arr = new JSONArray();
        if (nodes != null) {
            try {
                if (nodes.size() > 0) {
                    for (NodeInfo n : nodes) {
                        JSONObject obj = new JSONObject();
                        obj.put("mac", (Object) n.macAddress);
                        obj.put(Constants.ACTION_STATE, n.getOnOff());
                        obj.put("addr", n.meshAddress);
                        arr.put((Object) obj);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        jsonObject.put("hasConnected", conn);
        jsonObject.put("nodes", (Object) arr);
        return jsonObject;
    }

    public void upload(String houseId, OnUploadCallback onUploadCallback) {
        if (!TextUtils.isEmpty(houseId) && !updateError) {
            MeshLogNew.meshJsonLog("");
            new UploadTask(houseId, onUploadCallback).run();
        }
    }

    public class UploadTask implements Runnable {
        private String houseId;
        private OnUploadCallback onUploadCallback;

        public UploadTask(String houseId2, OnUploadCallback onUploadCallback2) {
            this.houseId = houseId2;
            this.onUploadCallback = onUploadCallback2;
        }

        public void run() {
            boolean unused = MeshDataManager.this.isUploading = true;
            MeshDataManager.this.doUpload(this.onUploadCallback, this.houseId);
        }
    }

    public boolean update(String callbackKey, String targetHouseId, boolean forceUpdate) {
        if (flagNetConfingAdddevices) {
            k.a("当前正在配网，不下载meshjson");
            MeshLog.i("#######  当前正在添加设备ing、不要去下载mesh配置文件，容易造成已配置的节点无法重新Mesh情况 flagNetConfingAdddevices=" + flagNetConfingAdddevices);
            c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(KitWrapItem.TYPE_MODE, "we are config matching new now ,be deny to update remote meshconfig").toString()));
            return false;
        }
        if (forceUpdate) {
            k.a("强制更新meshjson, 清除mesh所有信息文件（mesh.json,version,meshinfo持久化文件) targetHouseId:" + targetHouseId);
            l.a.b(new r(this, targetHouseId));
        }
        String curHouseId = SharedPreferenceHelper.getHouseId(this.context);
        HashMap<String, Object> receiveUpdateByMqtt = new HashMap<>();
        if (!curHouseId.equals(targetHouseId)) {
            k.a("当前hourse不一致，不更新meshjson");
            String desc = "houseId not equals!!!current houseId is " + curHouseId + ",but target houseId is " + targetHouseId;
            c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(-2, desc).toString()));
            receiveUpdateByMqtt.put("cause", "ACTION_UPDATE_MESH_DATA_BY_MQTT");
            receiveUpdateByMqtt.put("messageContent", desc);
            a.a(receiveUpdateByMqtt);
            return true;
        } else if (this.isUpdateing) {
            k.a("当前正在更新meshjson, 跳过");
            return true;
        } else {
            SIGMesh.getInstance().setLastDirectMac((String) null);
            String finalCurHouseId = curHouseId;
            this.isUpdateing = true;
            this.dbDataFetcher.fetchData(finalCurHouseId, this.appId).e0(15, TimeUnit.SECONDS).b0(l.g).J(l.g).Y(new l(this, callbackKey, targetHouseId, receiveUpdateByMqtt, finalCurHouseId), new t(this, receiveUpdateByMqtt, finalCurHouseId, callbackKey));
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$update$0 */
    public /* synthetic */ void e(String targetHouseId) {
        deleteMeshJSON(this.context, targetHouseId);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$update$1 */
    public /* synthetic */ void f(String callbackKey, String targetHouseId, HashMap receiveUpdateByMqtt, String finalCurHouseId, DataFetcher.FetcherResp resp) {
        updateError = false;
        this.isUpdateing = false;
        try {
            if (resp.isSuccess()) {
                processDownloaded(resp.getLocalPath(), callbackKey, targetHouseId);
                return;
            }
            importTraceByELK(resp.getMsg(), "debug", "update");
            c c = c.c();
            c.l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(-1, "update mesh data fail:" + resp.getMsg()).toString()));
        } catch (Exception e) {
            receiveUpdateByMqtt.put("cause", "ACTION_UPDATE_MESH_DATA_BY_MQTT");
            receiveUpdateByMqtt.put("messageContent", "更新mesh info config 失败" + e.toString());
            a.a(receiveUpdateByMqtt);
            k.a("导入meshjson 异常:" + e.getMessage());
            this.isUpdateing = false;
            SharedPreferenceHelper.setHouseId(this.context, finalCurHouseId);
            MeshLog.e("importMeshJson error:" + e.toString());
            c c2 = c.c();
            c2.l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(-1, "importMeshJson error:" + e.toString()).toString()));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$update$2 */
    public /* synthetic */ void g(HashMap receiveUpdateByMqtt, String finalCurHouseId, String callbackKey, Throwable throwable) {
        updateError = true;
        MeshLog.e("update mesh data error:" + throwable.toString() + Thread.currentThread().getName());
        if (!(throwable instanceof ApiException) || ((ApiException) throwable).getCode() != -1008) {
            importTraceByELK("update mesh data error:" + throwable.toString(), "info", "update");
        } else {
            importTraceByELK("update mesh data error:" + throwable.toString() + ",network:" + w.x(this.context), "info", "update");
        }
        receiveUpdateByMqtt.put("cause", "ACTION_UPDATE_MESH_DATA_BY_MQTT");
        receiveUpdateByMqtt.put("messageContent", "更新mesh info config 失败,finalCurHouseId:" + finalCurHouseId + ",ex:" + throwable.toString());
        a.b(receiveUpdateByMqtt, MeshConstants.TRACE_ID_IMPORT);
        StringBuilder sb = new StringBuilder();
        sb.append("update meshjson 异常:");
        sb.append(throwable.getMessage());
        k.a(sb.toString());
        c c = c.c();
        c.l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(-1, "update mesh data error:" + throwable.toString()).toString()));
        this.isUpdateing = false;
    }

    private void processDownloaded(String localPath, String callbackKey, String targetHouseId) {
        k.a("准备导入meshjson");
        if (TextUtils.isEmpty(localPath)) {
            importTraceByELK("meshJson 配置为空", "info", "processDownloaded");
            return;
        }
        HashMap<String, Object> receiveUpdateByMqtt = new HashMap<>();
        MeshLog.i("json 配置下载成功,flagNetConfingAdddevices" + flagNetConfingAdddevices + ",flagOTA:" + flagOTA + ",flagAddGroup:" + flagAddGroup);
        receiveUpdateByMqtt.put("cause", "ACTION_UPDATE_MESH_DATA_BY_MQTT");
        receiveUpdateByMqtt.put("messageContent", "Write To File Success,And We Start To Import MeshJson");
        a.a(receiveUpdateByMqtt);
        if (flagNetConfingAdddevices) {
            k.a("当前正在配网，不导入meshjson");
            c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(KitWrapItem.TYPE_MODE, "we are config matching new now ,be deny to update remote meshconfig").toString()));
        } else if (flagOTA) {
            k.a("当前正在OTA，不导入meshjson");
            c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(KitWrapItem.TYPE_MODE, "we are executing ota now").toString()));
        } else if (flagAddGroup) {
            k.a("当前正在添加或删除组成员，不导入meshjson");
            c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(KitWrapItem.TYPE_MODE, "we are editing group member now").toString()));
        } else {
            boolean isSave = importMeshJson(localPath);
            this.isUpdateing = false;
            if (!isSave) {
                c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorFailResp(KitWrapItem.TYPE_MODE, "the same time stamp.ignore update").toString()));
                return;
            }
            cleanInvalidNode();
            c.c().l(new JsBridgeCallbackEvent(callbackKey, BaseResp.generatorSuccessResp().toString()));
        }
    }

    public boolean importMeshJson(String jsonPath) {
        k.a("开始importMeshJson");
        MeshInfo localCloneMesh = null;
        try {
            localCloneMesh = (MeshInfo) SIGMesh.getInstance().getMeshInfo().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        MeshInfo newMesh = Json2Mesh(jsonPath);
        reportImportMeshJsonBeforeUpdateLog(newMesh);
        boolean hasNodeChange = LDSMeshUtil.hasChangedNode(localCloneMesh, newMesh);
        if (newMesh != null) {
            k.a("导入json成功");
        } else {
            MeshLog.e("importMeshJson newMesh=null,may be occur exception");
        }
        boolean isSave = newMesh != null && newMesh.saveOrUpdate(this.context, "MQTT_MESSAGE_TO_RELOAD_CONFIG");
        if (isSave) {
            if (hasNodeChange) {
                k.a("节点有变化，断开mesh网络，重新连接");
                importTraceByELK("检测到节点信息变化，断开重新连接mesh网络", "info", "importMeshJson");
                MeshService.k().n(true, "导入json配置");
            } else {
                k.a("节点无变化，不需要重新连接mesh网络");
                importTraceByELK("未检测到节点信息变化，不重新连接mesh网络", "info", "importMeshJson");
            }
            MeshEventHandler.getInstance().setupMesh(newMesh);
            MeshService.k().w(newMesh.convertToConfiguration());
            reportImportMeshJsonAfaterUpdateLog();
        }
        return isSave;
    }

    private void reportImportMeshJsonBeforeUpdateLog(MeshInfo newMesh) {
        HashMap<String, Object> receiveUpdateByMqtt = new HashMap<>();
        Gson gson = new Gson();
        receiveUpdateByMqtt.put("cause", "ACTION_UPDATE_MESH_DATA_BY_MQTT");
        receiveUpdateByMqtt.put("messageContent", "Before Update Mesh newMesh Is \n" + gson.toJson((Object) newMesh) + " \n will be Replace MeshInfo=" + gson.toJson((Object) SIGMesh.getInstance().getMeshInfo()));
        a.b(receiveUpdateByMqtt, MeshConstants.TRACE_ID_IMPORT);
    }

    private void reportImportMeshJsonAfaterUpdateLog() {
        HashMap<String, Object> receiveUpdateByMqtt = new HashMap<>();
        Gson gson = new Gson();
        receiveUpdateByMqtt.put("cause", "ACTION_UPDATE_MESH_DATA_BY_MQTT");
        receiveUpdateByMqtt.put("messageContent", "Update Config Success And the New is \n" + gson.toJson((Object) SIGMesh.getInstance().getMeshInfo()));
        a.b(receiveUpdateByMqtt, MeshConstants.TRACE_ID_IMPORT);
    }

    public MeshInfo Json2Mesh(String jsonFilePath) {
        MeshLog.d("jsonFile  to Mesh info");
        File file = new File(jsonFilePath);
        if (!file.exists()) {
            k.a("Json2Mesh失败，json文件不存在");
            return null;
        }
        String jsonData = (String) FileSystem.b(file)[1];
        MeshInfo localMesh = SIGMesh.getInstance().getMeshInfo();
        MeshInfo newMesh = null;
        try {
            importTraceByELK("准备导入json配置 jsonData=" + jsonData + " ,localMesh=" + new Gson().toJson((Object) localMesh), "info", "Json2Mesh");
            newMesh = MeshStorageService.getInstance().importExternal(jsonData, localMesh);
        } catch (Exception e) {
            e.printStackTrace();
            k.a("导入远程meshjson，更新内存meshinfo信息异常:" + e.getMessage());
            importTraceByELK("导入json配置出异常:" + e.toString(), "warn", "Json2Mesh");
        }
        if (newMesh != null) {
            return newMesh;
        }
        k.a("导入远程meshjson，更新内存meshinfo信息失败");
        return null;
    }

    public void doUpload(OnUploadCallback onUploadCallback, String houseId) {
        if (!TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this.context, "accessToken", ""))) {
            setNeedUpload(true);
            exportTraceByELK("开始上传mesh配置 houseId:" + houseId, "info", "UploadTask");
            this.dataUploader.exportAndUpload(houseId).b0(l.g).J(l.g).Y(new s(this, houseId, onUploadCallback), new p(this, onUploadCallback, houseId));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$doUpload$3 */
    public /* synthetic */ void a(String houseId, OnUploadCallback onUploadCallback, String version) {
        this.isUploading = false;
        setNeedUpload(false);
        this.retryCount = 0;
        setMeshVersion(houseId, version);
        k.a("写入version版本到文件成功");
        exportTraceByELK("mesh配置文件上传成功,ver:" + version, "info", "UploadTask");
        if (onUploadCallback != null) {
            onUploadCallback.onSuccess("");
            onUploadCallback.onComplete();
        }
        MeshLogNew.meshJsonLog("保存saveLocalUUIDAddress到数据库");
        SIGMesh.getInstance().getMeshInfo().saveLocalUUIDAddress(SIGMesh.getInstance().getContext(), 1);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$doUpload$4 */
    public /* synthetic */ void b(OnUploadCallback onUploadCallback, String houseId, Throwable throwable) {
        MeshLog.e("upload error:" + throwable.toString());
        exportTraceByELK("mesh配置文件上传失败:" + throwable.toString(), "info", "UploadTask");
        this.isUploading = false;
        try {
            if ((throwable instanceof HttpException) && new JSONObject(((HttpException) throwable).response().d().string()).getInt("code") == 21026) {
                k.a("上传meshjson【失败】,token过期，刷新token");
                refreshToken(onUploadCallback, houseId, 0);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int i = this.retryCount + 1;
        this.retryCount = i;
        if (i > 3) {
            this.retryCount = 0;
            k.a("达到最大重试次数3,【不再重试】");
            setNeedUpload(true);
            if (onUploadCallback != null) {
                onUploadCallback.onFail(throwable.toString());
                onUploadCallback.onComplete();
            }
        } else if (MeshUtils.m(this.context, houseId)) {
            k.a("第:" + this.retryCount + "次重试上传meshjson");
            doUpload(onUploadCallback, houseId);
        } else if (onUploadCallback != null) {
            onUploadCallback.onFail(throwable.toString() + ",houseid发生了变化，不上传，避免mesh信息串家");
            onUploadCallback.onComplete();
        }
    }

    public void doUpload(OnUploadCallback onUploadCallback, String houseId, MeshStorage meshStorage) {
        if (!TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this.context, "accessToken", ""))) {
            setNeedUpload(true);
            exportTraceByELK("开始上传mesh配置 houseId:" + houseId, "info", "UploadTask");
            this.dataUploader.exportAndUpload(houseId, meshStorage).b0(l.g).J(l.g).Y(new q(this, houseId, onUploadCallback), new o(this, onUploadCallback, houseId, meshStorage));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$doUpload$5 */
    public /* synthetic */ void c(String houseId, OnUploadCallback onUploadCallback, String version) {
        this.isUploading = false;
        setNeedUpload(false);
        this.retryCount = 0;
        setMeshVersion(houseId, version);
        k.a("写入version版本到文件成功");
        exportTraceByELK("mesh配置文件上传成功,ver:" + version, "info", "UploadTask");
        if (onUploadCallback != null) {
            onUploadCallback.onSuccess("");
            onUploadCallback.onComplete();
        }
        MeshLogNew.meshJsonLog("保存saveLocalUUIDAddress到数据库");
        SIGMesh.getInstance().getMeshInfo().saveLocalUUIDAddress(SIGMesh.getInstance().getContext(), 1);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$doUpload$6 */
    public /* synthetic */ void d(OnUploadCallback onUploadCallback, String houseId, MeshStorage meshStorage, Throwable throwable) {
        exportTraceByELK("mesh配置文件上传失败:" + throwable.toString(), "info", "UploadTask");
        this.isUploading = false;
        try {
            if ((throwable instanceof HttpException) && new JSONObject(((HttpException) throwable).response().d().string()).getInt("code") == 21026) {
                k.a("上传meshjson【失败】,token过期，刷新token");
                refreshToken(onUploadCallback, houseId, 0);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int i = this.retryCount + 1;
        this.retryCount = i;
        if (i > 3) {
            this.retryCount = 0;
            k.a("达到最大重试次数3,【不再重试】");
            setNeedUpload(true);
            if (onUploadCallback != null) {
                onUploadCallback.onFail(throwable.toString());
                onUploadCallback.onComplete();
                return;
            }
            return;
        }
        k.a("第:" + this.retryCount + "次重试上传meshjson");
        doUpload(onUploadCallback, houseId, meshStorage);
    }

    public void performed(Event<String> event) {
    }

    public void setNeedUpload(boolean flag) {
        MeshLog.d("setNeedUpload flag=" + flag);
        SharedPreferenceHelper.setNeedUpload(this.context, flag);
    }

    public void startUpTimer() {
        if (this.taskRunning) {
            MeshLog.d("upload taskRunning is true");
            return;
        }
        stopUpTimer();
        Timer timer2 = new Timer();
        this.timer = timer2;
        timer2.schedule(new TimerTask() {
            public void run() {
                if (SharedPreferenceHelper.isNeedUpload(MeshDataManager.this.context) && !MeshDataManager.this.isUploading) {
                    String houseId = SharedPreferenceHelper.getHouseId(MeshDataManager.this.context);
                    MeshLogNew.d("MeshDataManager 执行定时上传本地的mesh配置文件,houseId:" + houseId);
                    MeshDataManager.this.upload(houseId, (OnUploadCallback) null);
                }
            }
        }, 15000, 15000);
        this.taskRunning = true;
    }

    public void stopUpTimer() {
        Timer timer2 = this.timer;
        if (timer2 != null) {
            timer2.cancel();
            this.timer = null;
        }
        this.taskRunning = false;
    }

    private void refreshToken(OnUploadCallback onUploadCallback, String houseId, int function) {
        JSONObject headerJson = new JSONObject();
        String accessToken = SharePreferenceUtils.getPrefString(this.context, "accessToken", "");
        try {
            headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(this.context, "APP_ID", ""));
            if (!TextUtils.isEmpty(accessToken)) {
                headerJson.put("token", (Object) accessToken);
            }
            headerJson.put("terminal", (Object) "app");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String base_url = SharePreferenceUtils.getPrefString(this.context, "httpServer", "");
        JSONObject msgObj = new JSONObject();
        try {
            msgObj.put("refreshToken", (Object) SharePreferenceUtils.getPrefString(this.context, "refreshToken", ""));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        b0 b = b0.b();
        Context applicationContext = this.context.getApplicationContext();
        final OnUploadCallback onUploadCallback2 = onUploadCallback;
        final String str = houseId;
        final int i = function;
        b.K(applicationContext, (b<com.trello.rxlifecycle3.android.a>) null, base_url + "/user/refreshUserToken", headerJson.toString(), msgObj.toString(), new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                k.a("refreshToken【失败】:" + e.getMessage());
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                MeshLog.d("refreshToken onSuccess: " + response);
                try {
                    JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                    if (dataObj.has("accessToken")) {
                        SharePreferenceUtils.setPrefString(MeshDataManager.this.context, "accessToken", dataObj.getString("accessToken"));
                    }
                    if (dataObj.has("refreshToken")) {
                        SharePreferenceUtils.setPrefString(MeshDataManager.this.context, "refreshToken", dataObj.getString("refreshToken"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i = i;
                if (i == 0) {
                    k.a("refreshToken成功，重新上传meshjson信息");
                    MeshDataManager.this.doUpload(onUploadCallback2, str);
                } else if (i == 1) {
                    k.a("refreshToken成功，查询meshjson信息");
                    MeshDataManager.this.queryMeshConfig(false);
                }
            }
        });
    }

    public void queryMeshConfig(boolean forceUpdate) {
        if (!TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this.context, "accessToken", ""))) {
            String houseId = SharedPreferenceHelper.getHouseId(this.context);
            MeshLogNew.meshJsonLog("准备调用update 下载meshjson");
            update("", houseId, forceUpdate);
        }
    }

    public static int deleteMeshJSON(Context context2, String houseId) {
        FutureTask<Integer> task = new FutureTask<>(new ConfigUtil.DeleteVersionTask(context2, houseId));
        SIGMesh.getInstance().executorTask(task);
        Integer ret = 0;
        try {
            ret = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        return ret.intValue();
    }

    public static int getMeshVersion(Context context2, String houseId) {
        FutureTask<Integer> task = new FutureTask<>(new ConfigUtil.ReadVersionTask(context2, houseId));
        SIGMesh.getInstance().executorTask(task);
        Integer version = 0;
        try {
            version = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        return version.intValue();
    }

    public void setMeshVersion(String houseId, String newVersion) {
        SIGMesh.getInstance().executorTask(new FutureTask<>(new ConfigUtil.WriteVersionTask(this.context, houseId, newVersion)));
    }

    public void cleanInvalidNode() {
        final String hid = SharedPreferenceHelper.getHouseId(this.context);
        new LDSDeviceApi().getCloudDevices(hid, new OnHttpCallback<List<CloudDevice>>() {
            public void onResult(List<CloudDevice> devices) {
                if (!SharedPreferenceHelper.getHouseId(MeshDataManager.this.context).equals(hid)) {
                    MeshLog.e("云端设备列表回来了，但是houseid已经不一致了，数据不处理");
                    return;
                }
                MeshLog.e("云端设备列表回来了，校验删除无效本地mesh节点数据...");
                int num = 0;
                if (devices != null) {
                    List<NodeInfo> nodes = SIGMesh.getInstance().getMeshInfo().nodes;
                    if (nodes != null && nodes.size() > 0) {
                        Iterator<NodeInfo> iterator = nodes.iterator();
                        while (iterator.hasNext()) {
                            NodeInfo info = iterator.next();
                            if (!MeshDataManager.this.cloudContains(info.macAddress, devices) && !info.isOnline()) {
                                num++;
                                k.a("云端不存在设备节点:" + info.macAddress + ",从mesh中移除");
                                iterator.remove();
                            }
                        }
                    }
                    if (num > 0) {
                        MeshDataManager.this.setNeedUpload(true);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean cloudContains(String mac, List<CloudDevice> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (CloudDevice device : list) {
            if (mac.equals(device.mac)) {
                return true;
            }
        }
        return false;
    }

    public static void importTraceByELK(String message, String level, String method) {
        Class<MeshDataManager> cls = MeshDataManager.class;
        MeshLog.i(message);
        com.leedarson.log.elk.a.y(cls).s(method).x(MeshConstants.TRACE_ID_IMPORT).c(cls.getSimpleName()).o(level).t("LdsBleMesh").p(message).a().b();
    }

    public static void exportTraceByELK(String message, String level, String method) {
        Class<MeshDataManager> cls = MeshDataManager.class;
        com.leedarson.log.elk.a.y(cls).s(method).x(MeshConstants.TRACE_ID_EXPORT).c(cls.getSimpleName()).o(level).t("LdsBleMesh").p(message).a().b();
    }

    public void postMeshNetworkInfo(final String houseId) {
        new MeshEncryptInfoUtil(this.context, this).getNetworkEncryptInfo(houseId).b0(l.g).J(l.g).Y(new e<JSONObject>() {
            public void accept(JSONObject jsonObject) {
                JSONObject jSONObject = jsonObject;
                String currentHouseId = SharedPreferenceHelper.getHouseId(MeshDataManager.this.context);
                if (!MeshUtils.m(MeshDataManager.this.context, houseId)) {
                    k.a("updateMeshKey,requestHouseId:" + houseId + ",当前houseid:" + currentHouseId + ",不一致，忽略");
                    return;
                }
                String url = SharePreferenceUtils.getPrefString(MeshDataManager.this.context, "httpServer", "") + "/v15/houses/" + houseId;
                JSONObject paramJson = new JSONObject();
                try {
                    String appKey = jSONObject.optString("appKey");
                    String appKeyIndex = String.valueOf(jSONObject.get("appKeyIndex"));
                    String netKey = jSONObject.optString("netKey");
                    String netKeyIndex = String.valueOf(jSONObject.get("netKeyIndex"));
                    String ivIndex = String.valueOf(jSONObject.get("IVIndex"));
                    String meshUUID = jSONObject.optString("meshUUID");
                    paramJson.put("bleMeshAppKey", (Object) appKey);
                    paramJson.put("bleMeshAppKeyIndex", (Object) appKeyIndex);
                    paramJson.put("bleMeshNetKey", (Object) netKey);
                    paramJson.put("bleMeshNetKeyIndex", (Object) netKeyIndex);
                    paramJson.put("bleMeshIvIndex", (Object) ivIndex);
                    paramJson.put("bleMeshUuid", (Object) meshUUID);
                    io.reactivex.l<String> J = MeshDataManager.this.dbDataFetcher.getHouseInfo(houseId).J(l.g);
                    String str = houseId;
                    n nVar = r1;
                    String str2 = str;
                    io.reactivex.l<String> lVar = J;
                    String str3 = netKeyIndex;
                    String str4 = netKey;
                    String str5 = appKeyIndex;
                    JSONObject jSONObject2 = paramJson;
                    try {
                        n nVar2 = new n(this, str, appKey, netKey, meshUUID, ivIndex, url, paramJson, jsonObject);
                        lVar.Y(nVar, new m(str2));
                    } catch (JSONException e) {
                        e = e;
                    }
                } catch (JSONException e2) {
                    e = e2;
                    JSONObject jSONObject3 = paramJson;
                    e.printStackTrace();
                }
            }

            /* access modifiers changed from: private */
            /* JADX WARNING: Removed duplicated region for block: B:31:0x00f2  */
            /* JADX WARNING: Removed duplicated region for block: B:33:0x010a  */
            /* renamed from: lambda$accept$0 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public /* synthetic */ void a(java.lang.String r23, java.lang.String r24, java.lang.String r25, java.lang.String r26, java.lang.String r27, java.lang.String r28, org.json.JSONObject r29, org.json.JSONObject r30, java.lang.String r31) {
                /*
                    r22 = this;
                    r0 = r22
                    r1 = r23
                    r2 = r31
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder
                    r3.<init>()
                    java.lang.String r4 = "updateMeshKey getHouseInfo:"
                    r3.append(r4)
                    r3.append(r2)
                    java.lang.String r3 = r3.toString()
                    meshsdk.MeshLog.v(r3)
                    r3 = 0
                    org.json.JSONObject r4 = new org.json.JSONObject
                    r4.<init>((java.lang.String) r2)
                    java.lang.String r5 = "bleMeshUuid"
                    java.lang.String r5 = r4.optString(r5)
                    java.lang.String r6 = "bleMeshAppKey"
                    java.lang.String r6 = r4.optString(r6)
                    java.lang.String r7 = "bleMeshNetKey"
                    java.lang.String r7 = r4.optString(r7)
                    java.lang.String r8 = "bleMeshIvIndex"
                    java.lang.String r8 = r4.optString(r8)
                    java.lang.String r9 = "null"
                    boolean r10 = r9.equals(r5)
                    java.lang.String r11 = "updateMeshKey,houseId:"
                    if (r10 != 0) goto L_0x00d0
                    boolean r10 = android.text.TextUtils.isEmpty(r5)
                    if (r10 != 0) goto L_0x00d0
                    boolean r10 = r9.equals(r6)
                    if (r10 != 0) goto L_0x00c7
                    boolean r10 = android.text.TextUtils.isEmpty(r6)
                    if (r10 != 0) goto L_0x00c7
                    boolean r9 = r9.equals(r7)
                    if (r9 != 0) goto L_0x00be
                    boolean r9 = android.text.TextUtils.isEmpty(r7)
                    if (r9 == 0) goto L_0x006c
                    r9 = r24
                    r10 = r25
                    r12 = r26
                    r14 = r27
                    goto L_0x00d8
                L_0x006c:
                    r9 = r24
                    boolean r10 = r6.equals(r9)
                    if (r10 == 0) goto L_0x00b7
                    r10 = r25
                    boolean r12 = r7.equals(r10)
                    if (r12 == 0) goto L_0x00b9
                    r12 = r26
                    boolean r13 = r5.equals(r12)
                    if (r13 == 0) goto L_0x00bb
                    int r13 = java.lang.Integer.parseInt(r27)
                    int r14 = java.lang.Integer.parseInt(r8)
                    if (r13 <= r14) goto L_0x00b4
                    r3 = 1
                    java.lang.StringBuilder r13 = new java.lang.StringBuilder
                    r13.<init>()
                    r13.append(r11)
                    r13.append(r1)
                    java.lang.String r14 = ",云端key有值，且和本地一致，但ivIndex有变，需要提交更新mesh网络信息,cloudIvIndex:"
                    r13.append(r14)
                    r13.append(r8)
                    java.lang.String r14 = ",localIvIndex:"
                    r13.append(r14)
                    r14 = r27
                    r13.append(r14)
                    java.lang.String r13 = r13.toString()
                    com.leedarson.serviceimpl.reporters.k.a(r13)
                    goto L_0x00f0
                L_0x00b4:
                    r14 = r27
                    goto L_0x00f0
                L_0x00b7:
                    r10 = r25
                L_0x00b9:
                    r12 = r26
                L_0x00bb:
                    r14 = r27
                    goto L_0x00f0
                L_0x00be:
                    r9 = r24
                    r10 = r25
                    r12 = r26
                    r14 = r27
                    goto L_0x00d8
                L_0x00c7:
                    r9 = r24
                    r10 = r25
                    r12 = r26
                    r14 = r27
                    goto L_0x00d8
                L_0x00d0:
                    r9 = r24
                    r10 = r25
                    r12 = r26
                    r14 = r27
                L_0x00d8:
                    r3 = 1
                    java.lang.StringBuilder r13 = new java.lang.StringBuilder
                    r13.<init>()
                    r13.append(r11)
                    r13.append(r1)
                    java.lang.String r15 = ",云端key没值，需要提交更新mesh网络信息"
                    r13.append(r15)
                    java.lang.String r13 = r13.toString()
                    com.leedarson.serviceimpl.reporters.k.a(r13)
                L_0x00f0:
                    if (r3 != 0) goto L_0x010a
                    java.lang.StringBuilder r13 = new java.lang.StringBuilder
                    r13.<init>()
                    r13.append(r11)
                    r13.append(r1)
                    java.lang.String r11 = ",值没变，不需要提交更新mesh网络信息"
                    r13.append(r11)
                    java.lang.String r11 = r13.toString()
                    meshsdk.MeshLog.i(r11)
                    return
                L_0x010a:
                    com.leedarson.serviceimpl.http.manager.b0 r15 = com.leedarson.serviceimpl.http.manager.b0.b()
                    meshsdk.datamgr.MeshDataManager r11 = meshsdk.datamgr.MeshDataManager.this
                    android.content.Context r16 = r11.context
                    r17 = 0
                    r19 = 0
                    java.lang.String r20 = r29.toString()
                    meshsdk.datamgr.MeshDataManager$4$1 r11 = new meshsdk.datamgr.MeshDataManager$4$1
                    r13 = r30
                    r11.<init>(r1, r13)
                    r18 = r28
                    r21 = r11
                    r15.M(r16, r17, r18, r19, r20, r21)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: meshsdk.datamgr.MeshDataManager.AnonymousClass4.a(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.json.JSONObject, org.json.JSONObject, java.lang.String):void");
            }
        }, new e<Throwable>() {
            public void accept(Throwable throwable) {
                MeshLog.e("getNetworkEncryptInfo 出错:" + throwable.getMessage());
            }
        });
    }

    public io.reactivex.l<List<AddDevicesBean>> getMeshOObData(List<AddDevicesBean> param) {
        return this.dbDataFetcher.getMeshOObData(param);
    }

    public io.reactivex.l<AddDevicesBean> postDevice(AddDevicesBean addDevicesBean) {
        return this.dbDataFetcher.postDevice(addDevicesBean);
    }
}
