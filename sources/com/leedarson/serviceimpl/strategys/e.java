package com.leedarson.serviceimpl.strategys;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.http.observer.k;
import com.leedarson.base.http.observer.l;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.BleMeshServiceImpl;
import com.leedarson.serviceimpl.g;
import com.leedarson.serviceimpl.reporters.AddDeviceStepBean;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.parameter.AutoConnectParameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import meshsdk.BaseResp;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshBindCallback;
import meshsdk.callback.MeshUnbindCallback;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.NetworkingDevice;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.AddDevicesBean;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.MeshConstants;
import meshsdk.util.ProcedureCollector;
import meshsdk.util.SharedPreferenceHelper;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AddDevicesStrategy */
public class e {
    public static String a = "";
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public int b = 0;
    /* access modifiers changed from: private */
    public int c = 0;
    /* access modifiers changed from: private */
    public int d = 0;
    /* access modifiers changed from: private */
    public BleMeshServiceImpl e = null;
    MeshDataManager f = null;
    private String g = "";
    ArrayList<b> h = new ArrayList<>();
    /* access modifiers changed from: private */
    public String i = "";
    private String j = "";
    /* access modifiers changed from: private */
    public HashMap<String, String> k = new HashMap<>();
    /* access modifiers changed from: private */
    public Handler l = new Handler(Looper.getMainLooper());

    static /* synthetic */ void g(e x0, AddDevicesBean x1, String x2, int x3, String x4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, new Integer(x3), x4}, (Object) null, changeQuickRedirect, true, 8776, new Class[]{e.class, AddDevicesBean.class, cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            x0.s(x1, x2, x3, x4);
        }
    }

    public void m(JSONObject jSONObject, BleMeshServiceImpl bleMeshService, MeshDataManager meshDataManager, String str, String str2, Context context) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{jSONObject, bleMeshService, meshDataManager, str, str2, context}, this, changeQuickRedirect, false, 8769, new Class[]{JSONObject.class, BleMeshServiceImpl.class, MeshDataManager.class, cls, cls, Context.class}, Void.TYPE).isSupported) {
            Context context2 = context;
            String callBackKey = str;
            JSONObject jsonObject = jSONObject;
            String action = str2;
            this.e = bleMeshService;
            this.f = meshDataManager;
            try {
                if (jsonObject.has("devices")) {
                    List<AddDevicesBean> addDevicesBeans = new ArrayList<>();
                    JSONArray devices = jsonObject.getJSONArray("devices");
                    for (int i2 = 0; i2 < devices.length(); i2++) {
                        JSONObject tempJsonObj = devices.getJSONObject(i2);
                        if (tempJsonObj.has("mac")) {
                            AddDevicesBean addDevicesBean = new AddDevicesBean();
                            addDevicesBean.setMac(tempJsonObj.getString("mac"));
                            addDevicesBean.setModelId(tempJsonObj.getString("modelId"));
                            addDevicesBean.setReportTraceId(System.currentTimeMillis());
                            addDevicesBeans.add(addDevicesBean);
                            com.leedarson.serviceimpl.reporters.b addDevicesReporter = new com.leedarson.serviceimpl.reporters.b();
                            addDevicesReporter.c(addDevicesBean);
                            addDevicesReporter.a(new AddDeviceStepBean(AddDeviceStepBean.STEP_PARSE_WEB_PARAM));
                            addDevicesBean.setTrackReport(addDevicesReporter);
                        }
                    }
                    this.f.getMeshOObData(addDevicesBeans).R(new k(4, 5000)).b0(l.g).J(io.reactivex.android.schedulers.a.a()).Y(new a(this, callBackKey, action, context2), new d(this, addDevicesBeans, callBackKey, action, context2));
                }
            } catch (Exception e2) {
                MeshLog.v("addDevices 解析数据异常:" + e2.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public /* synthetic */ void p(String callBackKey, String action, Context context, List deviceBeans) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callBackKey, action, context, deviceBeans}, this, changeQuickRedirect, false, 8775, new Class[]{cls, cls, Context.class, List.class}, Void.TYPE).isSupported) {
            if (deviceBeans != null && deviceBeans.size() > 0) {
                a(deviceBeans, callBackKey, action, context);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q */
    public /* synthetic */ void r(List addDevicesBeans, String callBackKey, String action, Context context, Throwable th) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{addDevicesBeans, callBackKey, action, context, th}, this, changeQuickRedirect, false, 8774, new Class[]{List.class, cls, cls, Context.class, Throwable.class}, Void.TYPE).isSupported) {
            a(addDevicesBeans, callBackKey, action, context);
        }
    }

    private void a(List<AddDevicesBean> devices, String callBackKey, String action, Context context) {
        Class<String> cls = String.class;
        boolean z = false;
        if (!PatchProxy.proxy(new Object[]{devices, callBackKey, action, context}, this, changeQuickRedirect, false, 8770, new Class[]{List.class, cls, cls, Context.class}, Void.TYPE).isSupported) {
            this.g = callBackKey;
            this.j = action;
            this.b = 0;
            this.c = 0;
            this.d = 0;
            try {
                this.b = devices.size();
                Iterator<AddDevicesBean> iterator = devices.iterator();
                while (iterator.hasNext()) {
                    AddDevicesBean device = iterator.next();
                    ProcedureCollector.setProvisionCollectEnable(device.getMac(), true);
                    b meshBindCallbackWrap = new b(this.b > 1, device);
                    if (!TextUtils.isEmpty(device.getMac())) {
                        if (!TextUtils.isEmpty(device.getOObData())) {
                            NetworkingDevice dev = SIGMesh.getInstance().getCache().findDevice(device.getMac());
                            if (dev == null) {
                                com.leedarson.serviceimpl.reporters.e eVar = com.leedarson.serviceimpl.reporters.e.CODE_PRE_ADD_ERROR_CODE_NO_FOUND_DEVICE;
                                device.getTrackReport().a(new AddDeviceStepBean(eVar.getDesc(), eVar.getCode()));
                                MeshLog.e("添加设备的时候，发现一个异常的mac地址无法在设备缓存中找到:" + device.getMac());
                                meshBindCallbackWrap.onBindFail(eVar.getCode(), "");
                                iterator.remove();
                            } else {
                                device.networkingDevice = dev;
                                this.h.add(meshBindCallbackWrap);
                            }
                        }
                    }
                    com.leedarson.serviceimpl.reporters.e eVar2 = com.leedarson.serviceimpl.reporters.e.CODE_FOUND_OOB_FAIL;
                    meshBindCallbackWrap.onBindFail(eVar2.getCode(), String.format(eVar2.getDesc(), new Object[]{device.getFailMessage()}));
                    iterator.remove();
                }
                if (TextUtils.isEmpty(SharedPreferenceHelper.getHouseId(context))) {
                    for (int i2 = 0; i2 < devices.size(); i2++) {
                        com.leedarson.serviceimpl.reporters.e eVar3 = com.leedarson.serviceimpl.reporters.e.CODE_EMPTY_HOURSE_ID;
                        devices.get(i2).getTrackReport().a(new AddDeviceStepBean(eVar3.getDesc(), eVar3.getCode()));
                        this.h.get(i2).onBindFail(BaseResp.CODE_NO_HOUSE_ID, "could not find houseId");
                    }
                } else if (devices.size() > 0) {
                    if (devices.size() > 0) {
                        z = true;
                    }
                    MeshDataManager.flagNetConfingAdddevices = z;
                    com.leedarson.log.elk.a.y(this).x(MeshConstants.TRACE_ID_ADD_DEVICES).c(e.class.getSimpleName()).o("info").t("LdsBleMesh").p("bleMesh 开始配网:" + devices.toString()).a().b();
                    SIGMesh.getInstance().addDevices(devices, this.h);
                } else {
                    MeshLogNew.meshJsonLog("devices 数据为空,配什么网..");
                }
            } catch (Exception e2) {
                MeshLog.i("SUFUN.mesh: AddDevicesStrategy.handMessage 网络配置失败" + e2.toString());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void n(String currentMac) {
        Class<e> cls = e.class;
        boolean z = true;
        if (!PatchProxy.proxy(new Object[]{currentMac}, this, changeQuickRedirect, false, 8771, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.e("SUFUN.AddDevicesStrategy.onBindStatueChange  successCount=" + this.c + "  FailCount=" + this.d);
            if (this.c + this.d >= this.b) {
                ProcedureCollector.startAddDevicesTime = 0;
                JSONObject root = new JSONObject();
                JSONArray responseDevices = new JSONArray();
                for (int i2 = 0; i2 < this.h.size(); i2++) {
                    b meshwrapItem = this.h.get(i2);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        NodeInfo node = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, meshwrapItem.a.getMac());
                        jsonObject.put("code", meshwrapItem.a.getResultCode());
                        jsonObject.put("mac", (Object) meshwrapItem.a.getMac());
                        jsonObject.put("bleMeshAddr", node == null ? 0 : node.meshAddress);
                        jsonObject.put("accessFlag", meshwrapItem.a.getAccessFlag());
                        jsonObject.put("oobData", (Object) meshwrapItem.a.getOObData());
                        jsonObject.put("desc", (Object) meshwrapItem.a.getFailMessage());
                        jsonObject.put("rssi", meshwrapItem.a.getRssi());
                        if (meshwrapItem.a.getMeshSumitResponseBean() != null) {
                            meshwrapItem.a.getMeshSumitResponseBean().generateJSONObject(jsonObject);
                        }
                        responseDevices.put((Object) jsonObject);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                try {
                    root.put("data", (Object) responseDevices);
                    root.put("code", this.c == this.b ? 200 : 403);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                MeshLog.e("SUFUN.AddDevicesStrategy.onBindStatueChange==>handleData " + this.j + ":" + responseDevices.toString());
                c.c().l(new JsBridgeCallbackEvent(this.g, root.toString()));
                MeshService.k().v(this.i);
                MeshService.k().E();
                AutoConnectParameters parameters = new AutoConnectParameters();
                a = this.i;
                com.leedarson.serviceimpl.reporters.c.f("配网结束,autoConnect request");
                parameters.b = false;
                MeshService.k().a(parameters);
                if (this.c > 0) {
                    MeshLogNew.meshJsonLog("匹配配网结束，成功：" + this.c + " 个设备,上传本地meshjson");
                    t();
                } else {
                    MeshDataManager.flagNetConfingAdddevices = false;
                    com.leedarson.serviceimpl.reporters.k.a("所有的设备都配网失败，不需要上传meshjson,同时尝试更新meshjson");
                    this.f.update("", SharedPreferenceHelper.getHouseId(this.e.g), false);
                }
                MeshLogNew.meshJsonLog("SUCCESS_COUNT:" + this.c + ",TOTAL_DEVICES_COUNT:" + this.b);
                if (this.c != this.b) {
                    MeshLogNew.meshJsonLog("匹配配网存在失败的设备，上报单组数据");
                    com.leedarson.log.elk.a x = com.leedarson.log.elk.a.y(this).x(MeshConstants.TRACE_ID_ADD_DEVICES);
                    if (this.b <= 1) {
                        z = false;
                    }
                    x.u("isBatch", Boolean.valueOf(z)).u(NotificationCompat.CATEGORY_EVENT, "addMeshDeviceByGroup").u(Constants.ACTION_STATE, "failure").c(cls.getSimpleName()).o("failure").t("LdsBleMesh").p("bleMesh 单组批量配网失败，配网: " + this.b + " 个设备,失败:" + this.d).a().b();
                    return;
                }
                MeshLogNew.meshJsonLog("匹配配网成功，上报单组数据数据");
                com.leedarson.log.elk.a x2 = com.leedarson.log.elk.a.y(this).x(MeshConstants.TRACE_ID_ADD_DEVICES);
                if (this.b <= 1) {
                    z = false;
                }
                x2.u("isBatch", Boolean.valueOf(z)).u(NotificationCompat.CATEGORY_EVENT, "addMeshDeviceByGroup").u(Constants.ACTION_STATE, FirebaseAnalytics.Param.SUCCESS).c(cls.getSimpleName()).o(FirebaseAnalytics.Param.SUCCESS).t("LdsBleMesh").p("bleMesh 单组批量配网成功").a().b();
                return;
            }
            MeshLogNew.v("mesh设备:" + currentMac + ",配网成功，断开此蓝牙连接");
            MeshService.k().e(currentMac);
        }
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8772, new Class[0], Void.TYPE).isSupported) {
            MeshDataManager meshDataManager = new MeshDataManager(this.e.g);
            String houseId = SharedPreferenceHelper.getHouseId(this.e.g);
            com.leedarson.serviceimpl.reporters.k.a("mesh 批量配网完成，上传meshjson,houseId" + houseId);
            meshDataManager.upload(houseId, new a());
        }
    }

    /* compiled from: AddDevicesStrategy */
    public class a implements MeshDataManager.OnUploadCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 8777, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.reporters.k.a("批量配网完成 上传本地meshinfo成功回调");
                MeshDataManager.flagNetConfingAdddevices = false;
            }
        }

        public void onFail(String errMsg) {
            if (!PatchProxy.proxy(new Object[]{errMsg}, this, changeQuickRedirect, false, 8778, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.reporters.k.a("批量配网完成 上传本地meshinfo【失败】:" + errMsg);
                MeshDataManager.flagNetConfingAdddevices = false;
            }
        }

        public void onComplete() {
            MeshDataManager.flagNetConfingAdddevices = false;
        }
    }

    private void s(AddDevicesBean addDevicesBean, String mac, int code, String desc) {
        Class<String> cls = String.class;
        Object[] objArr = {addDevicesBean, mac, new Integer(code), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8773, new Class[]{AddDevicesBean.class, cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            try {
                JSONObject object = new JSONObject();
                object.put("mac", (Object) mac);
                object.put("status", code);
                object.put("desc", (Object) desc);
                object.put("accessFlag", addDevicesBean.getAccessFlag());
                object.put("bleMeshAddr", addDevicesBean.getBleMeshAddr());
                if (addDevicesBean.getMeshSumitResponseBean() != null) {
                    addDevicesBean.getMeshSumitResponseBean().generateJSONObject(object);
                }
                MeshLogNew.meshJsonLog("mesh设备bind状态回调.notifyToWebNodeConnectStatueChange.onAddDeviceProgress" + object.toString());
                c.c().l(new JsCallH5ByNativeEvent(g.KEY_SIGMesh, "onAddDeviceProgress", object.toString()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /* compiled from: AddDevicesStrategy */
    public class b implements MeshBindCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public AddDevicesBean a;
        private boolean b = false;

        public b(boolean isBatch, AddDevicesBean addDevicesBean) {
            this.b = isBatch;
            this.a = addDevicesBean;
        }

        public void onBindSuccess(String mac, NetworkingDevice dev, String str) {
            Class<String> cls = String.class;
            Class[] clsArr = {cls, NetworkingDevice.class, cls};
            if (!PatchProxy.proxy(new Object[]{mac, dev, str}, this, changeQuickRedirect, false, 8779, clsArr, Void.TYPE).isSupported) {
                this.a.setNodeInfo(dev.nodeInfo);
                this.a.setRssi(dev.rssi);
                String unused = e.this.i = mac;
                if (e.this.b == 1) {
                    MeshLog.i("设备配网成功,设置mesh网络通，以及主节点controller，避免removeDevice调用解绑导致失败等");
                    SIGMesh.getInstance().setConnected(true);
                    MeshService.k().v(e.this.i);
                }
                e.this.f.postDevice(this.a).R(new k(4, 5000)).b0(l.g).J(io.reactivex.android.schedulers.a.a()).Y(new c(this, mac, dev), new b(this, mac));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: b */
        public /* synthetic */ void c(String mac, NetworkingDevice dev, AddDevicesBean addDevicesBean) {
            Class[] clsArr = {String.class, NetworkingDevice.class, AddDevicesBean.class};
            if (!PatchProxy.proxy(new Object[]{mac, dev, addDevicesBean}, this, changeQuickRedirect, false, 8782, clsArr, Void.TYPE).isSupported) {
                MeshLogNew.meshJsonLog("配网成功:" + mac);
                e eVar = e.this;
                int unused = eVar.c = eVar.c + 1;
                addDevicesBean.setResultCode(200);
                e.this.e.onDeviceOnlineChange(mac, 1);
                String unused2 = e.this.i = mac;
                e.this.n(mac);
                e.g(e.this, addDevicesBean, mac, 6, "ble node keybind success");
                com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(e.this).x(MeshConstants.TRACE_ID_ADD_DEVICES).u("rssi", Integer.valueOf(dev.rssi)).u("isBatch", Boolean.valueOf(this.b)).u(NotificationCompat.CATEGORY_EVENT, MeshConstants.EVENT_ADD_MESH_DEVICE).u(Constants.ACTION_STATE, FirebaseAnalytics.Param.SUCCESS).u("oobData", addDevicesBean.getOObData()).u("mac", mac).u("modelId", addDevicesBean.getModelId()).c(e.class.getSimpleName()).o(FirebaseAnalytics.Param.SUCCESS).t("LdsBleMesh");
                t.p("bleMesh 配网成功:" + mac + ",addr:" + dev.nodeInfo.meshAddress + ",rssi:" + dev.rssi).a().b();
                if (addDevicesBean.getTrackReport() != null) {
                    MeshLogNew.meshJsonLog("配网成功，上报新版elk统计");
                    com.leedarson.serviceimpl.reporters.b trackReport = addDevicesBean.getTrackReport();
                    trackReport.a(new AddDeviceStepBean("配网成功,addr:" + dev.nodeInfo.meshAddress));
                    addDevicesBean.getTrackReport().f();
                }
                ProcedureCollector.endCollectAndClear(mac, "");
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: d */
        public /* synthetic */ void e(String mac, Throwable throwable) {
            Class[] clsArr = {String.class, Throwable.class};
            if (!PatchProxy.proxy(new Object[]{mac, throwable}, this, changeQuickRedirect, false, 8781, clsArr, Void.TYPE).isSupported) {
                MeshLogNew.v("postDevice fail:" + Thread.currentThread().getName());
                String unused = e.this.i = mac;
                onBindFail(com.leedarson.serviceimpl.reporters.e.CODE_POST_DEVICE_FAIL.getCode(), "");
                e.this.l.postDelayed(new a(mac, throwable), GroupCtrlAdapter.RETRY_TIMEOUT);
            }
        }

        /* compiled from: AddDevicesStrategy */
        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ String c;
            final /* synthetic */ Throwable d;

            a(String str, Throwable th) {
                this.c = str;
                this.d = th;
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8783, new Class[0], Void.TYPE).isSupported) {
                    MeshLogNew.meshJsonLog("配网成功了，但是提交云端失败，执行解绑删除设备:" + this.c + ",throwable:" + this.d.getMessage());
                    if (b.this.a.getTrackReport() != null) {
                        b.this.a.getTrackReport().f();
                    }
                    SIGMesh.getInstance().removeDevice(this.c, false, new C0162a());
                }
            }

            /* renamed from: com.leedarson.serviceimpl.strategys.e$b$a$a  reason: collision with other inner class name */
            /* compiled from: AddDevicesStrategy */
            public class C0162a implements MeshUnbindCallback {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0162a() {
                }

                public void onUnBindSuccess(String mac, int i, boolean z) {
                    if (!PatchProxy.proxy(new Object[]{mac, new Integer(i), new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8784, new Class[]{String.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                        MeshLogNew.meshJsonLog("解绑删除设备成功");
                        if (mac.equals(SIGMesh.getInstance().getLastDirectMac())) {
                            MeshLogNew.v("删除 主 设备成功，重置最近主节点为null");
                            SIGMesh.getInstance().setLastDirectMac((String) null);
                        } else {
                            MeshLogNew.meshJsonLog("删除 非 设备成功，重置最近主节点为null");
                        }
                        e.this.f.setNeedUpload(true);
                    }
                }

                public void onUnBindFail(int i, String str) {
                    Object[] objArr = {new Integer(i), str};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8785, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                        MeshLogNew.meshJsonLog("解绑删除设备失败");
                        if (a.this.c.equals(SIGMesh.getInstance().getLastDirectMac())) {
                            MeshLogNew.v("删除 主 节点失败【未在meshjson中找到】，重置最近主节点为null");
                            SIGMesh.getInstance().setLastDirectMac((String) null);
                        } else {
                            MeshLogNew.v("删除 非主 节点失败【未在meshjson中找到】，重置最近主节点为null");
                        }
                        e.this.f.setNeedUpload(true);
                    }
                }
            }
        }

        public void onBindFail(int code, String msg) {
            Object[] objArr = {new Integer(code), msg};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8780, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                e eVar = e.this;
                int unused = eVar.d = eVar.d + 1;
                this.a.setResultCode(code);
                this.a.setFailMessage(msg);
                e.this.n(this.a.getMac());
                e eVar2 = e.this;
                AddDevicesBean addDevicesBean = this.a;
                String mac = addDevicesBean.getMac();
                e.g(eVar2, addDevicesBean, mac, 7, "SUFUN.KeyBind.Fail ble mesh node connect error msg:" + msg + "   mac:" + this.a.getMac());
                MeshLog.i("SUFUN.KeyBind.Fail  code=" + code + "   msg=" + msg + "  mac=" + this.a.getMac());
                e.this.k.put(this.a.getMac(), this.a.getOObData());
                NetworkingDevice device = SIGMesh.getInstance().getCache().findDevice(this.a.getMac());
                com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(e.this).x(MeshConstants.TRACE_ID_ADD_DEVICES).u("isBatch", Boolean.valueOf(this.b)).u(NotificationCompat.CATEGORY_EVENT, MeshConstants.EVENT_ADD_MESH_DEVICE).u(Constants.ACTION_STATE, "failure").u("oobData", this.a.getOObData()).u("mac", this.a.getMac()).u("modelId", this.a.getModelId()).c(e.class.getSimpleName()).o("failure").t("LdsBleMesh");
                com.leedarson.log.elk.a builder = t.p("bleMesh 配网失败:" + this.a.getMac() + ",msg:" + msg);
                if (device != null) {
                    builder.u("rssi", Integer.valueOf(device.rssi));
                }
                builder.a().b();
                if (this.a.getTrackReport() != null) {
                    MeshLogNew.meshJsonLog("配网失败，上报新版elk统计");
                    this.a.getTrackReport().f();
                }
                ProcedureCollector.endCollectThenReport(this.a.getMac(), "");
            }
        }
    }
}
