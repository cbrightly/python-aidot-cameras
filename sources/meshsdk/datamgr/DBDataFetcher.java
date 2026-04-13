package meshsdk.datamgr;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.BuildConfig;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceimpl.reporters.AddDeviceStepBean;
import com.leedarson.serviceimpl.reporters.b;
import com.leedarson.serviceimpl.reporters.k;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.Constants;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.util.FileSystem;
import com.trello.rxlifecycle3.android.a;
import io.reactivex.functions.f;
import io.reactivex.l;
import io.reactivex.m;
import io.reactivex.n;
import java.io.File;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import meshsdk.ConfigUtil;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.datamgr.DataFetcher;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.AddDevicesBean;
import meshsdk.model.json.CloudDevice;
import meshsdk.model.json.MeshJSON;
import meshsdk.model.json.MeshStorage;
import meshsdk.model.json.MeshStorageService;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.SharedPreferenceHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBDataFetcher extends DataFetcher {
    private String houseId;
    /* access modifiers changed from: private */
    public MeshDataManager meshDataManager;

    public interface PingCallback {
        void callback();
    }

    public DBDataFetcher(Context context, MeshDataManager meshDataManager2) {
        super(context);
        this.meshDataManager = meshDataManager2;
    }

    public l<DataFetcher.FetcherResp> fetchData(final String finalCurHouseId, String appId) {
        this.houseId = finalCurHouseId;
        return getJsonRequest(this.context, finalCurHouseId, appId).P(1).b0(com.leedarson.base.http.observer.l.g).J(com.leedarson.base.http.observer.l.g).u(new f() {
            private boolean checkMeshChange = false;

            public Object apply(Object o) {
                String resp = o.toString();
                if (TextUtils.isEmpty(resp)) {
                    k.a("获取云端meshjson 数据为空");
                    return l.F(new DataFetcher.FetcherResp(-1, "db mesh json is null"));
                }
                MeshJSON meshJSON = (MeshJSON) new Gson().fromJson(resp, new TypeToken<MeshJSON>() {
                }.getType());
                if (DBDataFetcher.this.isCanUpdate(Integer.parseInt(meshJSON.version), finalCurHouseId)) {
                    File file = FileSystem.e(new File(ConfigUtil.getConfigPath(DBDataFetcher.this.context, finalCurHouseId)), MeshStorageService.JSON_FILE, meshJSON.meshJson);
                    DBDataFetcher.this.meshDataManager.setMeshVersion(finalCurHouseId, meshJSON.version);
                    if (file == null) {
                        k.a("写入远程meshjson到本地失敗,版本:" + meshJSON.version);
                    } else {
                        k.a("写入远程meshjson到本地成功,更新本地版本为" + meshJSON.version + ",meshjson:" + meshJSON.meshJson);
                    }
                    ((MeshStorage) new Gson().fromJson(meshJSON.meshJson, new TypeToken<MeshStorage>() {
                    }.getType())).printNode();
                    MeshDataManager.importTraceByELK("更新mesh info finish 版本为:" + meshJSON.version, "debug", "DBDataFetcher#fetchData");
                    l obs = l.F(new DataFetcher.FetcherResp(file.getAbsolutePath()));
                    aysncCheckMeshInfo(meshJSON.meshJson);
                    return obs;
                }
                MeshLogNew.meshJsonLog("mesh json 远程版本不大于当前版本，跳过更新");
                return l.F(new DataFetcher.FetcherResp(201, "the same Mesh data version,ignore importing"));
            }

            private void aysncCheckMeshInfo(String serverMeshJson) {
                k.a("进入校验meshjson meshuuid, appkey, netkey信息(比对hourse接口信息)");
                checkHouseMeshInfo(serverMeshJson);
            }

            public void checkHouseMeshInfo(String serverMeshJson) {
                DBDataFetcher.this.getHouseInfo(finalCurHouseId).J(com.leedarson.base.http.observer.l.g).Y(new c(this, serverMeshJson), new a(this, serverMeshJson));
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$checkHouseMeshInfo$0 */
            public /* synthetic */ void b(String serverMeshJson, String houseResponse) {
                MeshStorage tempStorage = (MeshStorage) new GsonBuilder().setPrettyPrinting().create().fromJson(serverMeshJson, MeshStorage.class);
                if (tempStorage != null && !TextUtils.isEmpty(houseResponse)) {
                    JSONObject houseJson = new JSONObject(houseResponse);
                    String meshUuid = houseJson.optString("bleMeshUuid");
                    String meshAppKey = houseJson.optString("bleMeshAppKey");
                    String meshNetKey = houseJson.optString("bleMeshNetKey");
                    if (!meshUuid.equals(tempStorage.meshUUID)) {
                        this.checkMeshChange = true;
                        tempStorage.meshUUID = meshUuid;
                        k.a("替换meshjson中的meshuuid为house接口的meshuuid:" + meshUuid);
                    } else {
                        MeshLogNew.meshJsonLog("meshuuid一致:" + meshUuid);
                    }
                    List<MeshStorage.ApplicationKey> list = tempStorage.appKeys;
                    if (list != null && list.size() > 0) {
                        if (!meshAppKey.equals(tempStorage.appKeys.get(0).key)) {
                            this.checkMeshChange = true;
                            tempStorage.appKeys.get(0).key = meshAppKey;
                            k.a("替换meshjson中的 appkey 为house接口的appkey:" + meshAppKey);
                        } else {
                            MeshLogNew.meshJsonLog("appkey一致：" + meshAppKey);
                        }
                    }
                    List<MeshStorage.NetworkKey> list2 = tempStorage.netKeys;
                    if (list2 != null && list2.size() > 0) {
                        if (!meshNetKey.equals(tempStorage.netKeys.get(0).key)) {
                            this.checkMeshChange = true;
                            tempStorage.netKeys.get(0).key = meshNetKey;
                            k.a("替换云端meshjson中的 netkey 为house接口的netkey:" + meshNetKey);
                        } else {
                            MeshLogNew.meshJsonLog("netkey一致：" + meshNetKey);
                        }
                    }
                }
                if (!this.checkMeshChange) {
                    k.a("校验meshjson meshuuid, appkey, netkey ==> 不存在变更");
                }
                checkDeviceMeshInfo(tempStorage);
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$checkHouseMeshInfo$1 */
            public /* synthetic */ void c(String serverMeshJson, Throwable throwable) {
                checkDeviceMeshInfo((MeshStorage) new GsonBuilder().setPrettyPrinting().create().fromJson(serverMeshJson, MeshStorage.class));
            }

            public void checkDeviceMeshInfo(MeshStorage meshStorage) {
                k.a("进入校验meshjson 设备节点信息");
                DBDataFetcher.this.getDevices(finalCurHouseId).J(com.leedarson.base.http.observer.l.g).Y(new d(this, meshStorage, finalCurHouseId), b.c);
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$checkDeviceMeshInfo$2 */
            public /* synthetic */ void a(MeshStorage meshStorage, final String finalCurHouseId, List devices) {
                if (devices == null || devices.size() <= 0) {
                    MeshLogNew.meshJsonLog("云端 devices 为空");
                } else {
                    if (meshStorage.nodes == null) {
                        meshStorage.nodes = new ArrayList();
                    }
                    if (meshStorage.nodes != null) {
                        Iterator it = devices.iterator();
                        while (it.hasNext()) {
                            CloudDevice device = (CloudDevice) it.next();
                            if ((device.accessFlag & 4) == 0) {
                                k.a("云端存在设备mac:" + device.mac + ",非blemesh设备，跳过");
                            } else {
                                boolean exist = false;
                                for (MeshStorage.Node node : meshStorage.nodes) {
                                    if (device.mac.equals(node.macAddress)) {
                                        exist = true;
                                        if (!device.bleMeshDeviceKey.equals(node.deviceKey)) {
                                            this.checkMeshChange = true;
                                            k.a(Constants.ARRAY_TYPE + device.mac + "] 节点deviceKey不一致，替换为云端数据:" + device.bleMeshDeviceKey);
                                            node.deviceKey = device.bleMeshDeviceKey;
                                        } else {
                                            MeshLogNew.meshJsonLog("mesh node节点deviceKey 一致");
                                        }
                                        if (!device.bleMeshDeviceUuid.equals(node.UUID)) {
                                            this.checkMeshChange = true;
                                            k.a(Constants.ARRAY_TYPE + device.mac + "] 节点uuid不一致，替换为云端数据:" + device.bleMeshDeviceUuid);
                                            node.UUID = device.bleMeshDeviceUuid;
                                        } else {
                                            MeshLogNew.meshJsonLog("mesh node节点deviceuuid 一致");
                                        }
                                        if (device.bleMeshAddr != MeshUtils.k(node.unicastAddress, ByteOrder.BIG_ENDIAN)) {
                                            this.checkMeshChange = true;
                                            node.unicastAddress = String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(device.bleMeshAddr)});
                                            k.a(Constants.ARRAY_TYPE + device.mac + "] 节点meshAddr不一致，替换为云端数据:" + node.unicastAddress);
                                        } else {
                                            MeshLogNew.meshJsonLog("mesh node节点meshAddr 一致");
                                        }
                                    }
                                }
                                if (!exist) {
                                    this.checkMeshChange = true;
                                    k.a("云端存在设备mac:" + device.mac + ",本地丢了，补上");
                                    NodeInfo deviceInfo = new NodeInfo();
                                    deviceInfo.macAddress = device.mac;
                                    deviceInfo.meshAddress = device.bleMeshAddr;
                                    deviceInfo.deviceKey = e.g(device.bleMeshDeviceKey);
                                    deviceInfo.deviceUUID = e.g(device.bleMeshDeviceUuid);
                                    meshStorage.nodes.add(MeshStorageService.getInstance().convertDeviceInfoToNode(deviceInfo, 0));
                                    boolean hasLocalNode = false;
                                    Iterator<MeshStorage.Node> it2 = meshStorage.nodes.iterator();
                                    while (true) {
                                        if (!it2.hasNext()) {
                                            break;
                                        }
                                        MeshStorage.Node tempNode = it2.next();
                                        if (LDSMeshUtil.isProvisionerNode(meshStorage, tempNode) && tempNode.macAddress != null) {
                                            hasLocalNode = true;
                                            break;
                                        }
                                    }
                                    if (hasLocalNode) {
                                        k.a("nodes中包含手机节点,不需要补");
                                    } else if (MeshStorageService.getInstance().getLocalNode() != null) {
                                        k.a("nodes中不包含手机节点信息，补上手机节点");
                                        meshStorage.nodes.add(0, MeshStorageService.getInstance().getLocalNode());
                                    } else {
                                        k.a("本地手机节点为空，补【失败】");
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.checkMeshChange) {
                    k.a("校验meshjson结束，存在mesh信息变更，上传修改后的meshjson");
                    meshStorage.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    DBDataFetcher.this.meshDataManager.doUpload(new MeshDataManager.OnUploadCallback() {
                        public void onSuccess(String fillId) {
                            File file = new File(new File(ConfigUtil.getConfigPath(DBDataFetcher.this.context, finalCurHouseId)), MeshStorageService.JSON_FILE);
                            k.a("上传成功，准备导入json文件");
                            if (DBDataFetcher.this.meshDataManager.importMeshJson(file.getAbsolutePath())) {
                                k.a("导入json成功");
                            } else {
                                k.a("导入json【失败】");
                            }
                        }

                        public void onFail(String errMsg) {
                            MeshLogNew.meshJsonLog("校验meshjson有变更后，上传json【失败】");
                        }

                        public void onComplete() {
                        }
                    }, finalCurHouseId, meshStorage);
                    return;
                }
                k.a("校验meshjson 信息结束，不存在mesh信息变更");
            }
        });
    }

    public static l getJsonRequest(Context context, String houseId2, String appId) {
        MeshLogNew.meshJsonLog("请求云端获取meshjson信息,houseId:" + houseId2);
        String httpServer = SharePreferenceUtils.getPrefString(context, "httpServer", "");
        String prefString = SharePreferenceUtils.getPrefString(context, "accessToken", "");
        String url = httpServer + "/BLEMesh/meshJson?houseId=" + houseId2;
        new HashMap();
        JSONObject headerJson = new JSONObject();
        try {
            headerJson.put("appId", (Object) appId);
            headerJson.put("terminal", (Object) "app");
            headerJson.put("Content-Type", (Object) "application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l.k(new e(context, url, headerJson));
    }

    public l<List<AddDevicesBean>> getMeshOObData(List<AddDevicesBean> addDeviceBeans) {
        String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/v21/BLEMesh/oobBatch";
        JSONObject headerJson = new JSONObject();
        try {
            headerJson.put("token", (Object) SharePreferenceUtils.getPrefString(this.context, "accessToken", ""));
        } catch (Exception e) {
        }
        JSONArray jsonArray = new JSONArray();
        try {
            for (AddDevicesBean addDevicesBean : addDeviceBeans) {
                JSONObject paramJson = new JSONObject();
                paramJson.put("mac", (Object) addDevicesBean.getMac());
                paramJson.put("modelId", (Object) addDevicesBean.getModelId());
                jsonArray.put((Object) paramJson);
            }
        } catch (Exception e2) {
        }
        b.d("getMeshOobData param:" + jsonArray);
        final JSONArray jSONArray = jsonArray;
        final String str = url;
        final JSONObject jSONObject = headerJson;
        final List<AddDevicesBean> list = addDeviceBeans;
        return l.k(new n<List<AddDevicesBean>>() {
            /* access modifiers changed from: private */
            public AddDeviceStepBean _requestOObStep;

            public void subscribe(final m<List<AddDevicesBean>> emitter) {
                b.d("请求oob数据：" + Thread.currentThread().getName());
                DBDataFetcher.this.printCurrentHttpCore();
                AddDeviceStepBean addDeviceStepBean = new AddDeviceStepBean(AddDeviceStepBean.STEP_REQUEST_OOB);
                this._requestOObStep = addDeviceStepBean;
                addDeviceStepBean.setStepRequestOob(true);
                this._requestOObStep.putRequestParams("jsonData", jSONArray);
                long currentTimeMillis = System.currentTimeMillis();
                b0.b().P(DBDataFetcher.this.context.getApplicationContext(), (com.trello.rxlifecycle3.b<a>) null, str, jSONObject.toString(), jSONArray.toString(), new i<String>() {
                    /* access modifiers changed from: protected */
                    public void onStart(io.reactivex.disposables.b d) {
                        b.d("请求oob数据onStart");
                    }

                    /* access modifiers changed from: protected */
                    public void onError(ApiException e) {
                        b.e("获取mesh oobdata数据失败：" + e.getMessage() + ",thread:" + Thread.currentThread().getName());
                        AddDeviceStepBean access$100 = AnonymousClass3.this._requestOObStep;
                        com.leedarson.serviceimpl.reporters.e eVar = com.leedarson.serviceimpl.reporters.e.CODE_OOB_FAIL;
                        access$100.putResponseParams("code", Integer.valueOf(eVar.getCode()));
                        AddDeviceStepBean access$1002 = AnonymousClass3.this._requestOObStep;
                        access$1002.putResponseParams("desc", e.getMessage() + ":" + e.getCode());
                        AnonymousClass3.this._requestOObStep.endTrace(eVar.getDesc(), eVar.getCode());
                        AnonymousClass3.this._requestOObStep.setSdkErrorCode(e.getCode());
                        for (AddDevicesBean addDevicesBean : list) {
                            addDevicesBean.setFailMessage("接口返回失败->" + e.getMessage());
                            AnonymousClass3.this._requestOObStep.step = AddDeviceStepBean.STEP_REQUEST_OOB;
                            AnonymousClass3 r2 = AnonymousClass3.this;
                            DBDataFetcher.this.pingArnoo((AddDevicesBean) list.get(0), AnonymousClass3.this._requestOObStep, new f(emitter, e));
                        }
                    }

                    /* access modifiers changed from: protected */
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length() > 0) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    if (jsonObject.has("code") && !BuildConfig.TRAVIS.equals(jsonObject.optString("code"))) {
                                        b.e("获取mesh oobdata数据失败:" + response);
                                        AnonymousClass3.this._requestOObStep.putResponseParams("responseData", jsonArray);
                                        AddDeviceStepBean access$100 = AnonymousClass3.this._requestOObStep;
                                        com.leedarson.serviceimpl.reporters.e eVar = com.leedarson.serviceimpl.reporters.e.CODE_OOB_FAIL;
                                        access$100.endTrace(eVar.getDesc(), eVar.getCode());
                                        for (AddDevicesBean addDevicesBean : list) {
                                            AnonymousClass3.this._requestOObStep.step = AddDeviceStepBean.STEP_REQUEST_OOB;
                                            addDevicesBean.getTrackReport().a(AnonymousClass3.this._requestOObStep);
                                        }
                                        emitter.onError(new Exception("oob失败"));
                                        return;
                                    }
                                }
                                AnonymousClass3.this._requestOObStep.putResponseCodeSuccess();
                                AnonymousClass3.this._requestOObStep.putResponseParams("responseData", jsonArray);
                                AddDeviceStepBean access$1002 = AnonymousClass3.this._requestOObStep;
                                com.leedarson.serviceimpl.reporters.e eVar2 = com.leedarson.serviceimpl.reporters.e.CODE_SUCCESS;
                                access$1002.endTrace(eVar2.getDesc(), eVar2.getCode());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    AddDevicesBean.MeshOObResponseBean oObResponseBean = (AddDevicesBean.MeshOObResponseBean) new Gson().fromJson(((JSONObject) jsonArray.get(i)).toString(), AddDevicesBean.MeshOObResponseBean.class);
                                    if (oObResponseBean != null && !TextUtils.isEmpty(oObResponseBean.getAuthValue()) && !TextUtils.isEmpty(oObResponseBean.getMac())) {
                                        for (AddDevicesBean addDevicesBean2 : list) {
                                            if (oObResponseBean.getMac().equals(addDevicesBean2.getMac())) {
                                                addDevicesBean2.setMeshOObResponseBean(oObResponseBean);
                                            }
                                        }
                                    }
                                }
                                for (AddDevicesBean addDevicesBean3 : list) {
                                    AnonymousClass3.this._requestOObStep.step = AddDeviceStepBean.STEP_REQUEST_OOB;
                                    addDevicesBean3.getTrackReport().a(AnonymousClass3.this._requestOObStep);
                                }
                                if (jsonArray.length() == 0) {
                                    b.e("获取mesh oobdata 數據返回空");
                                    emitter.onError(new Throwable("数据返回空"));
                                    return;
                                }
                                b.d("获取mesh oobdata数据成功");
                                emitter.onNext(list);
                                emitter.onComplete();
                            }
                        } catch (Exception e) {
                            b.e("获取mesh oobdata数据异常：" + e.getMessage());
                            emitter.onError(e);
                        }
                    }
                }, com.leedarson.base.http.observer.l.g);
            }
        });
    }

    public void printCurrentHttpCore() {
    }

    public l<AddDevicesBean> postDevice(AddDevicesBean addDevicesBean) {
        String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/v21/devices";
        JSONObject headerJson = new JSONObject();
        try {
            headerJson.put("token", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", ""));
        } catch (Exception e) {
        }
        JSONObject paramObj = new JSONObject();
        try {
            paramObj.put("mac", (Object) addDevicesBean.getMac());
            paramObj.put("houseId", (Object) SharedPreferenceHelper.getHouseId(this.context));
            paramObj.put("id", (Object) addDevicesBean.getUuid());
            paramObj.put("modelId", (Object) addDevicesBean.getModelId());
            paramObj.put("accessFlag", addDevicesBean.getAccessFlag());
            paramObj.put("bleMeshAddr", addDevicesBean.getBleMeshAddr());
            paramObj.put("bleMeshDeviceKey", (Object) addDevicesBean.getBleMeshDeviceKey());
            paramObj.put("bleMeshDeviceUuid", (Object) addDevicesBean.getBleMeshDeviceUuid());
            paramObj.put("bleMeshProtocolVersion", addDevicesBean.getBleMeshProtocolVersion());
            MeshLog.e("配网成功，提交设备信息，设备协议版本:" + addDevicesBean.getBleMeshProtocolVersion());
        } catch (Exception e2) {
        }
        MeshLogNew.meshJsonLog("提交addMesh数据 param:" + paramObj.toString());
        final String str = url;
        final JSONObject jSONObject = headerJson;
        final JSONObject jSONObject2 = paramObj;
        final AddDevicesBean addDevicesBean2 = addDevicesBean;
        return l.k(new n<AddDevicesBean>() {
            /* access modifiers changed from: private */
            public AddDeviceStepBean postStep;

            public void subscribe(final m<AddDevicesBean> emitter) {
                DBDataFetcher.this.printCurrentHttpCore();
                AddDeviceStepBean addDeviceStepBean = new AddDeviceStepBean(AddDeviceStepBean.STEP_POST_DEVICE);
                this.postStep = addDeviceStepBean;
                addDeviceStepBean.setStepPostDevice(true);
                b0.b().P(DBDataFetcher.this.context.getApplicationContext(), (com.trello.rxlifecycle3.b<a>) null, str, jSONObject.toString(), jSONObject2.toString(), new i<String>() {
                    /* access modifiers changed from: protected */
                    public void onStart(io.reactivex.disposables.b d) {
                        b.d("start postDevices request");
                    }

                    /* access modifiers changed from: protected */
                    public void onError(ApiException e) {
                        b.e("提交postDevices数据失败:" + e.getMessage());
                        String desc = e.getMessage() + ":" + e.getCode();
                        AddDeviceStepBean access$200 = AnonymousClass4.this.postStep;
                        com.leedarson.serviceimpl.reporters.e eVar = com.leedarson.serviceimpl.reporters.e.CODE_POST_DEVICE_FAIL;
                        access$200.putResponseParams("code", Integer.valueOf(eVar.getCode()));
                        AnonymousClass4.this.postStep.putResponseParams("desc", desc);
                        AnonymousClass4.this.postStep.endTrace(String.format(eVar.getDesc(), new Object[]{desc}), eVar.getCode());
                        AnonymousClass4.this.postStep.setSdkErrorCode(e.getCode());
                        AnonymousClass4 r1 = AnonymousClass4.this;
                        DBDataFetcher.this.pingArnoo(addDevicesBean2, r1.postStep, new g(emitter, e));
                    }

                    /* access modifiers changed from: protected */
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                b.e("提交postDevices success");
                                JSONObject jsonObject = new JSONObject(response);
                                addDevicesBean2.setMeshSumitResponseBean((AddDevicesBean.MeshDeviceInfoResponseBean) new Gson().fromJson(jsonObject.toString(), AddDevicesBean.MeshDeviceInfoResponseBean.class));
                                AddDeviceStepBean access$200 = AnonymousClass4.this.postStep;
                                com.leedarson.serviceimpl.reporters.e eVar = com.leedarson.serviceimpl.reporters.e.CODE_SUCCESS;
                                access$200.putResponseParams("code", Integer.valueOf(eVar.getCode()));
                                AnonymousClass4.this.postStep.putResponseParams("desc", AddDeviceStepBean.STEP_POST_DEVICE_SUCCESS);
                                AnonymousClass4.this.postStep.endTrace(AddDeviceStepBean.STEP_POST_DEVICE_SUCCESS, eVar.getCode());
                                addDevicesBean2.getTrackReport().a(AnonymousClass4.this.postStep);
                                emitter.onNext(addDevicesBean2);
                                emitter.onComplete();
                                MeshLogNew.meshJsonLog("提交addMesh数据成功:" + Thread.currentThread().getName());
                                return;
                            }
                            b.e("提交postDevices 失敗 empty");
                        } catch (Exception e) {
                            emitter.onError(e);
                            b.e("提交addMesh解析返回数据异常：" + e.getMessage() + ",response:" + response);
                        }
                    }
                }, com.leedarson.base.http.observer.l.g);
            }
        });
    }

    public l<String> getHouseInfo(String houseId2) {
        final String url = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "") + "/v21/houses/" + houseId2;
        final JSONObject headerJson = new JSONObject();
        try {
            headerJson.put("token", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", ""));
        } catch (Exception e) {
        }
        return l.k(new n<String>() {
            public void subscribe(final m<String> emitter) {
                b0.b().K(DBDataFetcher.this.context.getApplicationContext(), (com.trello.rxlifecycle3.b<a>) null, url, headerJson.toString(), (String) null, new i<String>() {
                    /* access modifiers changed from: protected */
                    public void onStart(io.reactivex.disposables.b d) {
                    }

                    /* access modifiers changed from: protected */
                    public void onError(ApiException e) {
                        k.a("获取hourse详情(bleMeshAppKey等信息)【失败】：" + e.getMessage());
                        emitter.onError(e);
                    }

                    /* access modifiers changed from: protected */
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                k.a("获取hourse详情(bleMeshAppKey等信息)成功");
                                emitter.onNext(response);
                                emitter.onComplete();
                                return;
                            }
                            k.a("获取hourse详情(bleMeshAppKey等信息)【为空】。。。");
                        } catch (Exception e) {
                            k.a("获取hourse详情(bleMeshAppKey等信息)【异常】:" + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public l<List<CloudDevice>> getDevices(String houseId2) {
        final String url = SharePreferenceUtils.getPrefString(this.context, "httpServer", "") + "/v15/devices";
        final JSONObject headerJson = new JSONObject();
        try {
            headerJson.put("token", (Object) SharePreferenceUtils.getPrefString(this.context, "accessToken", ""));
        } catch (Exception e) {
        }
        final JSONObject paramJson = new JSONObject();
        try {
            paramJson.put("houseId", (Object) houseId2);
        } catch (Exception e2) {
        }
        return l.k(new n<List<CloudDevice>>() {
            public void subscribe(final m<List<CloudDevice>> emitter) {
                b0.b().K(DBDataFetcher.this.context.getApplicationContext(), (com.trello.rxlifecycle3.b<a>) null, url, headerJson.toString(), paramJson.toString(), new i<String>() {
                    /* access modifiers changed from: protected */
                    public void onStart(io.reactivex.disposables.b d) {
                    }

                    /* access modifiers changed from: protected */
                    public void onError(ApiException e) {
                        k.a("获取云端devices 数据失败：" + e.getMessage());
                        emitter.onError(e);
                    }

                    /* access modifiers changed from: protected */
                    public void onSuccess(String response) {
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                List<CloudDevice> devices = (List) new Gson().fromJson(response, new TypeToken<List<CloudDevice>>() {
                                }.getType());
                                StringBuilder sb = new StringBuilder();
                                sb.append("获取云端devices设备成功,设备数:");
                                sb.append(devices != null ? devices.size() : 0);
                                k.a(sb.toString());
                                emitter.onNext(devices);
                                emitter.onComplete();
                                return;
                            }
                            k.a("获取云端devices 数据【返回空】");
                        } catch (Exception e) {
                            k.a("获取云端devices 数据【异常】:" + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public void pingArnoo(final AddDevicesBean addDevicesBean, final AddDeviceStepBean stepBean, final PingCallback callback) {
        b0.b().L(BaseApplication.b(), (com.trello.rxlifecycle3.b<a>) null, "http://test-ping-us.arnoo.com", "", "", new i<String>() {
            /* access modifiers changed from: protected */
            public void onStart(io.reactivex.disposables.b d) {
            }

            /* access modifiers changed from: protected */
            public void onError(ApiException e) {
                MeshLog.i("pingArnoo fail:" + e.getMessage());
                stepBean.putResponseParams("ping_code", Integer.valueOf(e.getCode()));
                AddDeviceStepBean addDeviceStepBean = stepBean;
                StringBuilder sb = new StringBuilder();
                sb.append("ping http://test-ping-us.arnoo.com fail:");
                sb.append(e.getMessage() + ":" + e.getCode());
                addDeviceStepBean.putResponseParams("ping_desc", sb.toString());
                addDevicesBean.getTrackReport().a(stepBean);
                PingCallback pingCallback = callback;
                if (pingCallback != null) {
                    pingCallback.callback();
                }
            }

            /* access modifiers changed from: protected */
            public void onSuccess(String response) {
                MeshLog.i("pingArnoo success:" + response);
                stepBean.putResponseParams("ping_code", 200);
                AddDeviceStepBean addDeviceStepBean = stepBean;
                addDeviceStepBean.putResponseParams("ping_desc", "ping http://test-ping-us.arnoo.com success:" + response);
                addDevicesBean.getTrackReport().a(stepBean);
                PingCallback pingCallback = callback;
                if (pingCallback != null) {
                    pingCallback.callback();
                }
            }
        }, com.leedarson.base.http.observer.l.f);
    }
}
