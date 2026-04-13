package com.leedarson.serviceimpl;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.bean.Constants;
import com.leedarson.bean.IRhyDevice;
import com.leedarson.module_base.R$string;
import com.leedarson.serviceimpl.reporters.deviceControl.a;
import com.leedarson.serviceimpl.reporters.groupControl.a;
import com.leedarson.serviceimpl.reporters.ota.a;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.leedarson.serviceinterface.MatterService;
import com.leedarson.serviceinterface.event.BackAndFrontChangeEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.ScreenStatusReceiveEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.foundation.AutoConnectDevicesManager;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.parameter.AutoConnectParameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.cache.CacheHandler;
import meshsdk.cache.cachemodule.CurrentDetectionModeCacheInstance;
import meshsdk.cache.cachemodule.DetectionModeDetailCacheInstance;
import meshsdk.callback.MeshBindCallback;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGlobalCallback;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.callback.MeshGroupCallbackWrapper;
import meshsdk.callback.MeshOTACallback;
import meshsdk.callback.MeshScanCallback;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.callback.MeshSimpleCmdCallback;
import meshsdk.callback.MeshSimpleCmdSetCallback;
import meshsdk.callback.MeshUnbindCallback;
import meshsdk.callback.OnHttpCallback;
import meshsdk.callback.OnPermissionListener;
import meshsdk.ctrl.ControlDevIntercepter;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.datamgr.GroupFixHelper;
import meshsdk.datamgr.LDSGroupApi;
import meshsdk.datamgr.LDSSceneApi;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.GroupInfo;
import meshsdk.model.NetworkingDevice;
import meshsdk.model.NodeInfo;
import meshsdk.model.Scene;
import meshsdk.model.TestEvent;
import meshsdk.model.json.BatteryPropertyBean;
import meshsdk.model.json.CloudDevice;
import meshsdk.model.json.DetectMode;
import meshsdk.model.json.EffectModeConfig;
import meshsdk.model.json.OTAProgressBean;
import meshsdk.model.json.RoutineRule;
import meshsdk.sql.DBManager;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.LDSModel;
import meshsdk.util.MeshConstants;
import meshsdk.util.ProcedureCollector;
import meshsdk.util.SharedPreferenceHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public class BleMeshServiceImpl extends MeshGlobalCallback implements BleMeshService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A = false;
    private String B;
    final Runnable C = new w0();
    private OnPermissionListener D = new y0();
    /* access modifiers changed from: private */
    public final String a = BleMeshService.class.getName();
    /* access modifiers changed from: private */
    public OTAProgressBean b;
    /* access modifiers changed from: private */
    public MeshDataManager c;
    private Handler d;
    private BluetoothChangeReceiver e;
    JSONObject f = null;
    public Context g;
    /* access modifiers changed from: private */
    public Activity h;
    /* access modifiers changed from: private */
    public LDSGroupApi i;
    private LDSSceneApi j;
    private boolean k = false;
    private long l = 0;
    private long m = 0;
    /* access modifiers changed from: private */
    public Semaphore n = new Semaphore(0);
    private com.leedarson.serviceimpl.product.d o;
    private com.leedarson.serviceimpl.product.c p;
    private l q;
    private LightsRhythmService r;
    private boolean s = false;
    private Set<String> t = new HashSet();
    private Set<String> u = new HashSet();
    /* access modifiers changed from: private */
    public HashMap<String, io.reactivex.disposables.b> v = new HashMap<>();
    private HashMap<String, Boolean> w = new HashMap<>();
    public HashMap<String, NotifyWebHistoryBean> x = new HashMap<>();
    private HashMap<String, f1> y = new HashMap<>();
    private List<io.reactivex.disposables.b> z = new ArrayList();

    public static class f1 {
        public boolean a;
        public boolean b;
    }

    static /* synthetic */ void j(BleMeshServiceImpl x02, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x02, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 5772, new Class[]{BleMeshServiceImpl.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x02.F(x1, x2, x3);
        }
    }

    static /* synthetic */ void k(BleMeshServiceImpl x02, Activity x1, String x2, String x3, String x4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x02, x1, x2, x3, x4}, (Object) null, changeQuickRedirect, true, 5773, new Class[]{BleMeshServiceImpl.class, Activity.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x02.a(x1, x2, x3, x4);
        }
    }

    static /* synthetic */ void n(BleMeshServiceImpl x02, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {BleMeshServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x02, x1, x2}, (Object) null, changeQuickRedirect, true, 5774, clsArr, Void.TYPE).isSupported) {
            x02.v(x1, x2);
        }
    }

    @Deprecated
    private void v(String callbackKey, String action) {
        postJsBridgeCallback(callbackKey, BaseResp.generatorSuccessResp(this.c.getVersion()).toString(), action);
    }

    public void handleData(Activity activity, String str, String str2, String str3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{activity, str, str2, str3}, this, changeQuickRedirect, false, 5711, new Class[]{Activity.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String callbackKey = str;
            String data = str3;
            Activity activity2 = activity;
            String action = str2;
            a.b g2 = timber.log.a.g(Constants.SERVICE_SIG_MESH);
            g2.h("RX==>handleData action:%s,data:%s,thread:" + Thread.currentThread().getName() + "当前mesh连接状态?" + SIGMesh.getInstance().hasConnected(), action, data);
            if (!w(activity2, callbackKey, action, data)) {
                if (!this.c.isUpdateing) {
                    a(activity2, callbackKey, action, data);
                } else if (this.t.contains(action)) {
                    x(activity2, callbackKey, action, data);
                } else {
                    MeshLogNew.e("meshjson正在更新，还是继续执行:" + action);
                    a(activity2, callbackKey, action, data);
                }
            }
        }
    }

    private boolean w(Activity activity, String str, String str2, String str3) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity, str, str2, str3}, this, changeQuickRedirect2, false, 5712, new Class[]{Activity.class, cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String callbackKey = str;
        String data = str3;
        Activity activity2 = activity;
        String action = str2;
        if (SIGMesh.getInstance().hasConnected() && this.v.containsKey(callbackKey) && !this.v.get(callbackKey).isDisposed()) {
            MeshLogNew.e("waitingConnectedAction mesh网络连上了,接口:" + action + ",callbackKey:" + callbackKey + "data:" + data + ",轮询取消");
            this.v.get(callbackKey).dispose();
        }
        if (SIGMesh.getInstance().hasConnected() || !this.u.contains(action)) {
            return false;
        }
        if (!this.v.containsKey(callbackKey)) {
            F("", "action:" + action + "接口调用,mesh还未连接，等待5s轮询,params:" + data, "info");
            io.reactivex.l.D(0, 10, 0, 500, TimeUnit.MILLISECONDS).a(new k(action, data, callbackKey, activity2));
        }
        return true;
    }

    public class k implements io.reactivex.q<Long> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a1;
        private int c;
        private NodeInfo d;
        private int f;
        final /* synthetic */ String p0;
        final /* synthetic */ Activity p1;
        private int q;
        private Object x;
        private long y;
        final /* synthetic */ String z;

        k(String str, String str2, String str3, Activity activity) {
            this.z = str;
            this.p0 = str2;
            this.a1 = str3;
            this.p1 = activity;
        }

        public /* bridge */ /* synthetic */ void onNext(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5779, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Long) obj);
            }
        }

        public void onSubscribe(io.reactivex.disposables.b d2) {
            if (!PatchProxy.proxy(new Object[]{d2}, this, changeQuickRedirect, false, 5775, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                if ("controlDevice".equals(this.z)) {
                    try {
                        JSONObject paramObj = new JSONObject(this.p0);
                        String mac = "";
                        if (paramObj.has("mac")) {
                            mac = paramObj.getString("mac");
                        }
                        this.c = paramObj.getInt("modelId");
                        this.x = paramObj.get("value");
                        NodeInfo findMeshNode = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, mac);
                        this.d = findMeshNode;
                        this.y = com.leedarson.serviceimpl.reporters.deviceControl.b.b().a(new ControlDevIntercepter.CtrlDevWrap(findMeshNode, this.c, this.x, true, 0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (BleMeshService.ACTION_CONTROL_GROUP.equals(this.z)) {
                    try {
                        JSONObject paramObj2 = new JSONObject(this.p0);
                        this.f = paramObj2.getInt("groupId");
                        this.c = paramObj2.getInt("modelId");
                        this.x = paramObj2.get("value");
                        GroupInfo group = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, this.f);
                        if (group != null) {
                            this.q = group.address;
                            this.y = com.leedarson.serviceimpl.reporters.groupControl.b.b().a(this.f, this.q, this.c, this.x);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                MeshLogNew.e("waitingConnectedAction 还没连接上mesh，调用接口:" + this.z + ",callkey:" + this.a1 + "等待重试");
                BleMeshServiceImpl.this.v.put(this.a1, d2);
            }
        }

        public void a(Long l) {
            if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 5776, new Class[]{Long.class}, Void.TYPE).isSupported) {
                MeshLogNew.i("waitingConnectedAction 接口:" + this.z + ",callkey:" + this.a1 + " 收到轮询消息");
                BleMeshServiceImpl.this.handleData(this.p1, this.a1, this.z, this.p0);
            }
        }

        public void onError(Throwable th) {
            if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 5777, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                MeshLogNew.e("waitingConnectedAction onError:" + this.a1);
            }
        }

        public void onComplete() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5778, new Class[0], Void.TYPE).isSupported) {
                MeshLogNew.e("waitingConnectedAction onComplete:" + this.a1);
                ((io.reactivex.disposables.b) BleMeshServiceImpl.this.v.get(this.a1)).dispose();
                BleMeshServiceImpl.this.v.remove(this.a1);
                if (!SIGMesh.getInstance().hasConnected()) {
                    if ("controlDevice".equals(this.z)) {
                        com.leedarson.serviceimpl.reporters.deviceControl.b.b().e(a.b.CODE_SEND_FAIL_NOT_CONNECTED, this.y);
                    } else if (BleMeshService.ACTION_CONTROL_GROUP.equals(this.z)) {
                        com.leedarson.serviceimpl.reporters.groupControl.b.b().f(a.b.CODE_SEND_FAIL_NOT_CONNECTED, this.y);
                    }
                    MeshLogNew.e("waitingConnectedAction onComplete action:" + this.z + "接口等待5s，还未连接到mesh，调用失败");
                    BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                    BleMeshServiceImpl.j(bleMeshServiceImpl, "", "action:" + this.z + "接口调用,等待5smesh还未上线，调用失败,params:" + this.p0, "info");
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a1, BaseResp.generatorFailResp(-1, "mesh网络还未上线").toString(), this.z);
                }
            }
        }
    }

    private void x(Activity activity, String callbackKey, String str, String str2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{activity, callbackKey, str, str2}, this, changeQuickRedirect, false, 5713, new Class[]{Activity.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String data = str2;
            Activity activity2 = activity;
            String action = str;
            MeshLogNew.e("meshjson正在更新，action:" + action + "等待更新完毕在执行");
            if (this.v.containsKey(callbackKey) && !this.v.get(callbackKey).isDisposed()) {
                this.v.get(callbackKey).dispose();
            }
            io.reactivex.l.k(new g0(action)).e0(15, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.android.schedulers.a.a()).a(new v(callbackKey, activity2, action, data));
        }
    }

    public class g0 implements io.reactivex.n<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        g0(String str) {
            this.a = str;
        }

        public void subscribe(io.reactivex.m<Boolean> emitter) {
            if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 5824, new Class[]{io.reactivex.m.class}, Void.TYPE).isSupported) {
                do {
                    MeshLogNew.e("meshjson正在更新，继续轮训等待:" + this.a);
                } while (BleMeshServiceImpl.this.c.isUpdateing);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }
    }

    public class v implements io.reactivex.q<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ Activity d;
        final /* synthetic */ String f;
        final /* synthetic */ String q;

        v(String str, Activity activity, String str2, String str3) {
            this.c = str;
            this.d = activity;
            this.f = str2;
            this.q = str3;
        }

        public /* bridge */ /* synthetic */ void onNext(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5803, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Boolean) obj);
            }
        }

        public void onSubscribe(io.reactivex.disposables.b d2) {
            if (!PatchProxy.proxy(new Object[]{d2}, this, changeQuickRedirect, false, 5799, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.v.put(this.c, d2);
            }
        }

        public void a(Boolean aBoolean) {
            if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 5800, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
                if (aBoolean.booleanValue()) {
                    BleMeshServiceImpl.k(BleMeshServiceImpl.this, this.d, this.c, this.f, this.q);
                }
            }
        }

        public void onError(Throwable th) {
            if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 5801, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(Constants.SERVICE_SIG_MESH);
                g.c("RX==>handleData " + this.f + " callback is updating return!!!!==>%s", this.c);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = this.c;
                bleMeshServiceImpl.postJsBridgeCallback(str, BaseResp.generatorFailResp(-1, "mesh json is updating when " + this.f + ",ignore any operation").toString(), this.f);
            }
        }

        public void onComplete() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5802, new Class[0], Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.v.remove(this.c);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.app.Activity r35, java.lang.String r36, java.lang.String r37, java.lang.String r38) {
        /*
            r34 = this;
            java.lang.String r1 = "OOBData"
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r3 = 4
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r11 = 0
            r4[r11] = r35
            r12 = 1
            r4[r12] = r36
            r13 = 2
            r4[r13] = r37
            r14 = 3
            r4[r14] = r38
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            java.lang.Class<android.app.Activity> r5 = android.app.Activity.class
            r9[r11] = r5
            r9[r12] = r2
            r9[r13] = r2
            r9[r14] = r2
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 5714(0x1652, float:8.007E-42)
            r5 = r34
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0031
            return
        L_0x0031:
            r2 = r34
            r15 = r36
            r10 = r38
            r9 = r35
            r8 = r37
            r16 = 0
            r23 = 0
            int r4 = r8.hashCode()
            java.lang.String r5 = "disconnectMeshNetwork"
            java.lang.String r6 = "updateMeshData"
            java.lang.String r7 = "test_connect_state"
            java.lang.String r3 = "setEffectMode"
            switch(r4) {
                case -2129330689: goto L_0x03eb;
                case -2102969130: goto L_0x03e2;
                case -1932021452: goto L_0x03d7;
                case -1883970744: goto L_0x03cc;
                case -1849927877: goto L_0x03c1;
                case -1829537777: goto L_0x03b9;
                case -1799159335: goto L_0x03ae;
                case -1698305881: goto L_0x03a3;
                case -1696839979: goto L_0x0398;
                case -1645186270: goto L_0x038d;
                case -1633982922: goto L_0x0381;
                case -1500831146: goto L_0x0375;
                case -1463195118: goto L_0x0369;
                case -1448185193: goto L_0x035d;
                case -1299637476: goto L_0x0351;
                case -1296592902: goto L_0x0345;
                case -1258042786: goto L_0x0339;
                case -1247417237: goto L_0x032d;
                case -1211352924: goto L_0x0322;
                case -1180450975: goto L_0x0317;
                case -1107355429: goto L_0x030b;
                case -1019721403: goto L_0x02ff;
                case -1006527400: goto L_0x02f3;
                case -992865631: goto L_0x02e7;
                case -905815549: goto L_0x02db;
                case -826931972: goto L_0x02cf;
                case -756040043: goto L_0x02c4;
                case -637934591: goto L_0x02b9;
                case -572015507: goto L_0x02ad;
                case -454067836: goto L_0x02a1;
                case -442317225: goto L_0x0295;
                case -440038595: goto L_0x0289;
                case -420006011: goto L_0x027d;
                case -369855998: goto L_0x0271;
                case -315768741: goto L_0x0265;
                case -310017684: goto L_0x0259;
                case -305143192: goto L_0x024d;
                case -75121853: goto L_0x0241;
                case 78588: goto L_0x0235;
                case 67252664: goto L_0x0229;
                case 95274531: goto L_0x021d;
                case 159794707: goto L_0x0211;
                case 316835424: goto L_0x0205;
                case 352968256: goto L_0x01f9;
                case 397049632: goto L_0x01ef;
                case 422932870: goto L_0x01e3;
                case 441136659: goto L_0x01d7;
                case 458473726: goto L_0x01cb;
                case 458554570: goto L_0x01bf;
                case 613596591: goto L_0x01b3;
                case 633806903: goto L_0x01a8;
                case 667488321: goto L_0x019c;
                case 697947230: goto L_0x0190;
                case 705620439: goto L_0x0184;
                case 760248219: goto L_0x0178;
                case 778470238: goto L_0x016d;
                case 787946589: goto L_0x0161;
                case 910959880: goto L_0x0155;
                case 975677686: goto L_0x0149;
                case 1000329350: goto L_0x013d;
                case 1032550481: goto L_0x0131;
                case 1128986617: goto L_0x0125;
                case 1167070273: goto L_0x0119;
                case 1194689176: goto L_0x010d;
                case 1327783650: goto L_0x0101;
                case 1339340203: goto L_0x00f5;
                case 1388468386: goto L_0x00e9;
                case 1417866954: goto L_0x00dd;
                case 1591396871: goto L_0x00d1;
                case 1604778157: goto L_0x00c5;
                case 1699804437: goto L_0x00b9;
                case 1714778527: goto L_0x00ad;
                case 1875229516: goto L_0x00a1;
                case 1916269630: goto L_0x0095;
                case 1941201485: goto L_0x0089;
                case 1951215585: goto L_0x007d;
                case 1971119344: goto L_0x0071;
                case 1987173701: goto L_0x0068;
                case 2050060970: goto L_0x005c;
                case 2068146595: goto L_0x0050;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x03f6
        L_0x0050:
            java.lang.String r4 = "controlWallWasherLight"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 76
            goto L_0x03f7
        L_0x005c:
            java.lang.String r4 = "setSingleCappedAlarm"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 52
            goto L_0x03f7
        L_0x0068:
            boolean r4 = r8.equals(r5)
            if (r4 == 0) goto L_0x004e
            r4 = 6
            goto L_0x03f7
        L_0x0071:
            java.lang.String r4 = "getColorMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 39
            goto L_0x03f7
        L_0x007d:
            java.lang.String r4 = "setAlarmStatus"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 61
            goto L_0x03f7
        L_0x0089:
            java.lang.String r4 = "setEnergyMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 71
            goto L_0x03f7
        L_0x0095:
            java.lang.String r4 = "getEffectLinkage"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 47
            goto L_0x03f7
        L_0x00a1:
            java.lang.String r4 = "getIlluminationState"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 66
            goto L_0x03f7
        L_0x00ad:
            java.lang.String r4 = "stopScan"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 12
            goto L_0x03f7
        L_0x00b9:
            java.lang.String r4 = "performEffectMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 41
            goto L_0x03f7
        L_0x00c5:
            java.lang.String r4 = "getMeshData"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 8
            goto L_0x03f7
        L_0x00d1:
            java.lang.String r4 = "addSceneRule"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 28
            goto L_0x03f7
        L_0x00dd:
            java.lang.String r4 = "getEffectMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 43
            goto L_0x03f7
        L_0x00e9:
            java.lang.String r4 = "getVersion"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 36
            goto L_0x03f7
        L_0x00f5:
            java.lang.String r4 = "performScene"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 31
            goto L_0x03f7
        L_0x0101:
            java.lang.String r4 = "removeDeviceFocus "
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 65
            goto L_0x03f7
        L_0x010d:
            java.lang.String r4 = "addGroupMember"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 22
            goto L_0x03f7
        L_0x0119:
            java.lang.String r4 = "getEnergyMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 70
            goto L_0x03f7
        L_0x0125:
            java.lang.String r4 = "setPIRConfig"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 68
            goto L_0x03f7
        L_0x0131:
            java.lang.String r4 = "setCurrentDetectionMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 56
            goto L_0x03f7
        L_0x013d:
            java.lang.String r4 = "setDetectionMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 53
            goto L_0x03f7
        L_0x0149:
            java.lang.String r4 = "getTemporaryControlDuration"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 58
            goto L_0x03f7
        L_0x0155:
            java.lang.String r4 = "getDeviceAddress"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 78
            goto L_0x03f7
        L_0x0161:
            java.lang.String r4 = "getScenes"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 26
            goto L_0x03f7
        L_0x016d:
            java.lang.String r4 = "getCloudDevices"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = r11
            goto L_0x03f7
        L_0x0178:
            java.lang.String r4 = "getNetworkEncryptInfo"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 77
            goto L_0x03f7
        L_0x0184:
            java.lang.String r4 = "getBattery"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 49
            goto L_0x03f7
        L_0x0190:
            java.lang.String r4 = "getDeviceStatus"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 16
            goto L_0x03f7
        L_0x019c:
            java.lang.String r4 = "removeSmartRule"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 34
            goto L_0x03f7
        L_0x01a8:
            java.lang.String r4 = "connectMeshNetwork"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 5
            goto L_0x03f7
        L_0x01b3:
            java.lang.String r4 = "setSecurityAlarm"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 73
            goto L_0x03f7
        L_0x01bf:
            java.lang.String r4 = "getGroups"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 19
            goto L_0x03f7
        L_0x01cb:
            java.lang.String r4 = "removeDeviceFocus"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 64
            goto L_0x03f7
        L_0x01d7:
            java.lang.String r4 = "controlDevice"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 18
            goto L_0x03f7
        L_0x01e3:
            java.lang.String r4 = "setSmartRuleEnable"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 35
            goto L_0x03f7
        L_0x01ef:
            boolean r4 = r8.equals(r6)
            if (r4 == 0) goto L_0x004e
            r4 = 10
            goto L_0x03f7
        L_0x01f9:
            java.lang.String r4 = "getDeviceGroups"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 62
            goto L_0x03f7
        L_0x0205:
            java.lang.String r4 = "setDeviceFocus"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 63
            goto L_0x03f7
        L_0x0211:
            java.lang.String r4 = "performEffectLinkage"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 48
            goto L_0x03f7
        L_0x021d:
            java.lang.String r4 = "setSmartRule"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 33
            goto L_0x03f7
        L_0x0229:
            java.lang.String r4 = "removeDeviceFromGroups"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 24
            goto L_0x03f7
        L_0x0235:
            java.lang.String r4 = "OTA"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 32
            goto L_0x03f7
        L_0x0241:
            java.lang.String r4 = "getTime"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 38
            goto L_0x03f7
        L_0x024d:
            java.lang.String r4 = "removeScene"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 29
            goto L_0x03f7
        L_0x0259:
            java.lang.String r4 = "getCacheVersion"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 37
            goto L_0x03f7
        L_0x0265:
            java.lang.String r4 = "removeGroup"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 21
            goto L_0x03f7
        L_0x0271:
            java.lang.String r4 = "setTemporaryControlDuration"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 57
            goto L_0x03f7
        L_0x027d:
            java.lang.String r4 = "getPIRConfig"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 67
            goto L_0x03f7
        L_0x0289:
            java.lang.String r4 = "getEnergyConsumption"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 69
            goto L_0x03f7
        L_0x0295:
            java.lang.String r4 = "addDevice"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 13
            goto L_0x03f7
        L_0x02a1:
            java.lang.String r4 = "removeSceneRule"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 30
            goto L_0x03f7
        L_0x02ad:
            java.lang.String r4 = "getAlarmStatus"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 60
            goto L_0x03f7
        L_0x02b9:
            java.lang.String r4 = "test_export"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 2
            goto L_0x03f7
        L_0x02c4:
            java.lang.String r4 = "test_attack"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = r14
            goto L_0x03f7
        L_0x02cf:
            java.lang.String r4 = "addDevices"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 14
            goto L_0x03f7
        L_0x02db:
            java.lang.String r4 = "setDST"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 45
            goto L_0x03f7
        L_0x02e7:
            java.lang.String r4 = "getCacheBattery"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 50
            goto L_0x03f7
        L_0x02f3:
            java.lang.String r4 = "uploadMeshData"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 9
            goto L_0x03f7
        L_0x02ff:
            java.lang.String r4 = "getCurrentDetectionMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 55
            goto L_0x03f7
        L_0x030b:
            java.lang.String r4 = "getLightsRhythmStatus"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 46
            goto L_0x03f7
        L_0x0317:
            java.lang.String r4 = "test_idle"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 4
            goto L_0x03f7
        L_0x0322:
            java.lang.String r4 = "clearMeshData"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 7
            goto L_0x03f7
        L_0x032d:
            java.lang.String r4 = "addScene"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 27
            goto L_0x03f7
        L_0x0339:
            java.lang.String r4 = "addGroup"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 20
            goto L_0x03f7
        L_0x0345:
            java.lang.String r4 = "removeDevice"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 15
            goto L_0x03f7
        L_0x0351:
            java.lang.String r4 = "getWallWasherLightStatus"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 75
            goto L_0x03f7
        L_0x035d:
            java.lang.String r4 = "cancelSecurityAlarm"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 74
            goto L_0x03f7
        L_0x0369:
            java.lang.String r4 = "getDetectionMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 54
            goto L_0x03f7
        L_0x0375:
            java.lang.String r4 = "performCustomizeEffectMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 42
            goto L_0x03f7
        L_0x0381:
            java.lang.String r4 = "getSingleCappedAlarm"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 51
            goto L_0x03f7
        L_0x038d:
            java.lang.String r4 = "controlGroup"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 25
            goto L_0x03f7
        L_0x0398:
            java.lang.String r4 = "removeGroupMember"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 23
            goto L_0x03f7
        L_0x03a3:
            java.lang.String r4 = "getDevices"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 17
            goto L_0x03f7
        L_0x03ae:
            java.lang.String r4 = "setVoiceRhythmStopAttr"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 59
            goto L_0x03f7
        L_0x03b9:
            boolean r4 = r8.equals(r7)
            if (r4 == 0) goto L_0x004e
            r4 = r12
            goto L_0x03f7
        L_0x03c1:
            java.lang.String r4 = "getSecurityAlarm"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 72
            goto L_0x03f7
        L_0x03cc:
            java.lang.String r4 = "getProvisionerAddress"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 79
            goto L_0x03f7
        L_0x03d7:
            java.lang.String r4 = "getCurrentCustomizeEffectMode"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 44
            goto L_0x03f7
        L_0x03e2:
            boolean r4 = r8.equals(r3)
            if (r4 == 0) goto L_0x004e
            r4 = 40
            goto L_0x03f7
        L_0x03eb:
            java.lang.String r4 = "startScan"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x004e
            r4 = 11
            goto L_0x03f7
        L_0x03f6:
            r4 = -1
        L_0x03f7:
            java.lang.String r14 = ",sceneId:"
            java.lang.String r13 = "action"
            java.lang.String r12 = "status"
            java.lang.String r11 = ",params:"
            r36 = r7
            java.lang.String r7 = "sceneId"
            r37 = r5
            java.lang.String r5 = "macs"
            r38 = r6
            java.lang.String r6 = "info"
            r19 = r1
            java.lang.String r1 = "groupId"
            r20 = r14
            java.lang.String r14 = ""
            r21 = r7
            java.lang.String r7 = "mac"
            switch(r4) {
                case 0: goto L_0x1780;
                case 1: goto L_0x176b;
                case 2: goto L_0x1765;
                case 3: goto L_0x175c;
                case 4: goto L_0x174c;
                case 5: goto L_0x16ad;
                case 6: goto L_0x168d;
                case 7: goto L_0x167a;
                case 8: goto L_0x15fc;
                case 9: goto L_0x15f5;
                case 10: goto L_0x154d;
                case 11: goto L_0x1521;
                case 12: goto L_0x14be;
                case 13: goto L_0x1445;
                case 14: goto L_0x1403;
                case 15: goto L_0x13be;
                case 16: goto L_0x1367;
                case 17: goto L_0x1351;
                case 18: goto L_0x12d8;
                case 19: goto L_0x12be;
                case 20: goto L_0x1213;
                case 21: goto L_0x11d3;
                case 22: goto L_0x117e;
                case 23: goto L_0x1129;
                case 24: goto L_0x10ee;
                case 25: goto L_0x1070;
                case 26: goto L_0x1056;
                case 27: goto L_0x1005;
                case 28: goto L_0x0e97;
                case 29: goto L_0x0e5c;
                case 30: goto L_0x0dea;
                case 31: goto L_0x0da6;
                case 32: goto L_0x0cfd;
                case 33: goto L_0x0cb0;
                case 34: goto L_0x0c52;
                case 35: goto L_0x0c18;
                case 36: goto L_0x0bd0;
                case 37: goto L_0x0b8e;
                case 38: goto L_0x0b60;
                case 39: goto L_0x0b32;
                case 40: goto L_0x0ae3;
                case 41: goto L_0x0ae3;
                case 42: goto L_0x0ab8;
                case 43: goto L_0x0a84;
                case 44: goto L_0x0a56;
                case 45: goto L_0x0a35;
                case 46: goto L_0x0921;
                case 47: goto L_0x08eb;
                case 48: goto L_0x08a5;
                case 49: goto L_0x085d;
                case 50: goto L_0x081b;
                case 51: goto L_0x07ed;
                case 52: goto L_0x07b8;
                case 53: goto L_0x078a;
                case 54: goto L_0x0729;
                case 55: goto L_0x06e6;
                case 56: goto L_0x06a9;
                case 57: goto L_0x0662;
                case 58: goto L_0x062b;
                case 59: goto L_0x05ee;
                case 60: goto L_0x05bf;
                case 61: goto L_0x058b;
                case 62: goto L_0x055c;
                case 63: goto L_0x051c;
                case 64: goto L_0x0502;
                case 65: goto L_0x0502;
                case 66: goto L_0x04d8;
                case 67: goto L_0x04ae;
                case 68: goto L_0x0463;
                case 69: goto L_0x0439;
                case 70: goto L_0x042d;
                case 71: goto L_0x042d;
                case 72: goto L_0x042d;
                case 73: goto L_0x042d;
                case 74: goto L_0x042d;
                case 75: goto L_0x042d;
                case 76: goto L_0x042d;
                case 77: goto L_0x0421;
                case 78: goto L_0x0421;
                case 79: goto L_0x0421;
                default: goto L_0x041a;
            }
        L_0x041a:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0421:
            com.leedarson.serviceimpl.product.c r1 = r2.p
            r1.handleData(r9, r15, r8, r10)
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x042d:
            com.leedarson.serviceimpl.product.d r1 = r2.o
            r1.handleData(r9, r15, r8, r10)
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0439:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x045b }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x045b }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ Exception -> 0x0457 }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x0457 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$n0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$n0     // Catch:{ Exception -> 0x0457 }
            r5.<init>(r15, r8)     // Catch:{ Exception -> 0x0457 }
            r4.getEnergyConsumption(r3, r5)     // Catch:{ Exception -> 0x0457 }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0457:
            r0 = move-exception
            r16 = r1
            goto L_0x045c
        L_0x045b:
            r0 = move-exception
        L_0x045c:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0463:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x048d }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x048d }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ Exception -> 0x0488 }
            java.lang.String r4 = "level"
            r5 = 0
            int r4 = r1.optInt(r4, r5)     // Catch:{ Exception -> 0x0488 }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x0488 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$m0 r6 = new com.leedarson.serviceimpl.BleMeshServiceImpl$m0     // Catch:{ Exception -> 0x0488 }
            r6.<init>(r15, r8)     // Catch:{ Exception -> 0x0488 }
            r5.setPIRConfig(r3, r4, r6)     // Catch:{ Exception -> 0x0488 }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0488:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x048f
        L_0x048d:
            r0 = move-exception
            r1 = r0
        L_0x048f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "setPIRConfig error:"
            r3.append(r4)
            java.lang.String r4 = r1.getMessage()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            meshsdk.MeshLog.e(r3)
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x04ae:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x04d0 }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x04d0 }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ Exception -> 0x04cc }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x04cc }
            com.leedarson.serviceimpl.BleMeshServiceImpl$l0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$l0     // Catch:{ Exception -> 0x04cc }
            r5.<init>(r15, r8)     // Catch:{ Exception -> 0x04cc }
            r4.getPIRConfig(r3, r5)     // Catch:{ Exception -> 0x04cc }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x04cc:
            r0 = move-exception
            r16 = r1
            goto L_0x04d1
        L_0x04d0:
            r0 = move-exception
        L_0x04d1:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x04d8:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x04fa }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x04fa }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ Exception -> 0x04f6 }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x04f6 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$k0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$k0     // Catch:{ Exception -> 0x04f6 }
            r5.<init>(r15, r8)     // Catch:{ Exception -> 0x04f6 }
            r4.getIlluminationState(r3, r5)     // Catch:{ Exception -> 0x04f6 }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x04f6:
            r0 = move-exception
            r16 = r1
            goto L_0x04fb
        L_0x04fa:
            r0 = move-exception
        L_0x04fb:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0502:
            java.lang.String r1 = "web->removeDeviceFocus 取消设备焦点"
            meshsdk.MeshLog.i(r1)
            meshsdk.datamgr.MeshDataManager.setCurrentDeviceFocusMeshAddress(r14)
            org.json.JSONObject r1 = meshsdk.BaseResp.generatorSuccessResp(r14)
            java.lang.String r1 = r1.toString()
            r2.postJsBridgeCallback(r15, r1, r8)
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x051c:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0554 }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x0554 }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ Exception -> 0x0550 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0550 }
            r4.<init>()     // Catch:{ Exception -> 0x0550 }
            java.lang.String r5 = "web->setDeviceFocus 进入设备焦点:"
            r4.append(r5)     // Catch:{ Exception -> 0x0550 }
            r4.append(r3)     // Catch:{ Exception -> 0x0550 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0550 }
            meshsdk.MeshLog.i(r4)     // Catch:{ Exception -> 0x0550 }
            meshsdk.datamgr.MeshDataManager.setCurrentDeviceFocusMeshAddress(r3)     // Catch:{ Exception -> 0x0550 }
            org.json.JSONObject r4 = meshsdk.BaseResp.generatorSuccessResp(r14)     // Catch:{ Exception -> 0x0550 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0550 }
            r2.postJsBridgeCallback(r15, r4, r8)     // Catch:{ Exception -> 0x0550 }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0550:
            r0 = move-exception
            r16 = r1
            goto L_0x0555
        L_0x0554:
            r0 = move-exception
        L_0x0555:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x055c:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x057f }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x057f }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ JSONException -> 0x057a }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x057a }
            com.leedarson.serviceimpl.BleMeshServiceImpl$j0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$j0     // Catch:{ JSONException -> 0x057a }
            r5.<init>(r3, r15, r8)     // Catch:{ JSONException -> 0x057a }
            r4.getDeviceGroups(r3, r5)     // Catch:{ JSONException -> 0x057a }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x057a:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0581
        L_0x057f:
            r0 = move-exception
            r1 = r0
        L_0x0581:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x058b:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x05b3 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x05b3 }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ JSONException -> 0x05ae }
            r4 = 0
            int r4 = r1.optInt(r12, r4)     // Catch:{ JSONException -> 0x05ae }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x05ae }
            com.leedarson.serviceimpl.BleMeshServiceImpl$i0 r6 = new com.leedarson.serviceimpl.BleMeshServiceImpl$i0     // Catch:{ JSONException -> 0x05ae }
            r6.<init>(r15, r8)     // Catch:{ JSONException -> 0x05ae }
            r5.setAlarmStatus(r3, r4, r6)     // Catch:{ JSONException -> 0x05ae }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x05ae:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x05b5
        L_0x05b3:
            r0 = move-exception
            r1 = r0
        L_0x05b5:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x05bf:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x05e2 }
            r1.<init>((java.lang.String) r10)     // Catch:{ Exception -> 0x05e2 }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ Exception -> 0x05dd }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x05dd }
            com.leedarson.serviceimpl.BleMeshServiceImpl$h0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$h0     // Catch:{ Exception -> 0x05dd }
            r5.<init>(r15, r8)     // Catch:{ Exception -> 0x05dd }
            r4.getAlarmStatus(r3, r5)     // Catch:{ Exception -> 0x05dd }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x05dd:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x05e4
        L_0x05e2:
            r0 = move-exception
            r1 = r0
        L_0x05e4:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x05ee:
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x061f }
            r3.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x061f }
            java.lang.String r4 = r3.optString(r7, r14)     // Catch:{ JSONException -> 0x061a }
            java.lang.String r1 = r3.optString(r1, r14)     // Catch:{ JSONException -> 0x061a }
            java.lang.String r5 = "models"
            org.json.JSONArray r5 = r3.getJSONArray(r5)     // Catch:{ JSONException -> 0x061a }
            meshsdk.model.json.RhythmStopAttrBean r6 = meshsdk.model.json.RhythmStopAttrBean.create(r4, r1, r5)     // Catch:{ JSONException -> 0x061a }
            meshsdk.SIGMesh r7 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x061a }
            com.leedarson.serviceimpl.BleMeshServiceImpl$f0 r11 = new com.leedarson.serviceimpl.BleMeshServiceImpl$f0     // Catch:{ JSONException -> 0x061a }
            r11.<init>(r15, r8)     // Catch:{ JSONException -> 0x061a }
            r7.setVoiceRhythmStopAttr(r6, r11)     // Catch:{ JSONException -> 0x061a }
            r16 = r3
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x061a:
            r0 = move-exception
            r1 = r0
            r16 = r3
            goto L_0x0621
        L_0x061f:
            r0 = move-exception
            r1 = r0
        L_0x0621:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x062b:
            r1 = r14
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0656 }
            r3.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0656 }
            boolean r4 = r3.has(r7)     // Catch:{ JSONException -> 0x0651 }
            if (r4 == 0) goto L_0x063c
            java.lang.String r4 = r3.getString(r7)     // Catch:{ JSONException -> 0x0651 }
            r1 = r4
        L_0x063c:
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0651 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$e0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$e0     // Catch:{ JSONException -> 0x0651 }
            r5.<init>(r15, r8)     // Catch:{ JSONException -> 0x0651 }
            r4.getTemporaryControlDuration(r1, r5)     // Catch:{ JSONException -> 0x0651 }
            r16 = r3
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0651:
            r0 = move-exception
            r1 = r0
            r16 = r3
            goto L_0x0658
        L_0x0656:
            r0 = move-exception
            r1 = r0
        L_0x0658:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0662:
            r1 = r14
            r3 = 0
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x069d }
            r4.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x069d }
            boolean r5 = r4.has(r7)     // Catch:{ JSONException -> 0x0698 }
            if (r5 == 0) goto L_0x0674
            java.lang.String r5 = r4.getString(r7)     // Catch:{ JSONException -> 0x0698 }
            r1 = r5
        L_0x0674:
            java.lang.String r5 = "duration"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x0698 }
            if (r5 == 0) goto L_0x0683
            java.lang.String r5 = "duration"
            int r5 = r4.getInt(r5)     // Catch:{ JSONException -> 0x0698 }
            r3 = r5
        L_0x0683:
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0698 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$d0 r6 = new com.leedarson.serviceimpl.BleMeshServiceImpl$d0     // Catch:{ JSONException -> 0x0698 }
            r6.<init>(r15, r8)     // Catch:{ JSONException -> 0x0698 }
            r5.setTemporaryControlDuration(r1, r3, r6)     // Catch:{ JSONException -> 0x0698 }
            r16 = r4
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0698:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x069f
        L_0x069d:
            r0 = move-exception
            r1 = r0
        L_0x069f:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x06a9:
            r1 = r14
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x06da }
            r3.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x06da }
            boolean r4 = r3.has(r7)     // Catch:{ JSONException -> 0x06d5 }
            if (r4 == 0) goto L_0x06ba
            java.lang.String r4 = r3.getString(r7)     // Catch:{ JSONException -> 0x06d5 }
            r1 = r4
        L_0x06ba:
            java.lang.String r4 = "mode"
            int r4 = r3.optInt(r4)     // Catch:{ JSONException -> 0x06d5 }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x06d5 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$c0 r6 = new com.leedarson.serviceimpl.BleMeshServiceImpl$c0     // Catch:{ JSONException -> 0x06d5 }
            r6.<init>(r15, r8)     // Catch:{ JSONException -> 0x06d5 }
            r5.setCurrentDetectionMode(r1, r4, r6)     // Catch:{ JSONException -> 0x06d5 }
            r16 = r3
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x06d5:
            r0 = move-exception
            r1 = r0
            r16 = r3
            goto L_0x06dc
        L_0x06da:
            r0 = move-exception
            r1 = r0
        L_0x06dc:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x06e6:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x071d }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x071d }
            java.lang.String r3 = r1.optString(r7)     // Catch:{ JSONException -> 0x0718 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0718 }
            r4.<init>()     // Catch:{ JSONException -> 0x0718 }
            java.lang.String r5 = "web->getCurrentDetectionMode,mac:"
            r4.append(r5)     // Catch:{ JSONException -> 0x0718 }
            r4.append(r3)     // Catch:{ JSONException -> 0x0718 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0718 }
            meshsdk.MeshLogNew.i(r4)     // Catch:{ JSONException -> 0x0718 }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0718 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$b0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$b0     // Catch:{ JSONException -> 0x0718 }
            r5.<init>(r15, r8, r3)     // Catch:{ JSONException -> 0x0718 }
            r4.getCurrentDetectionMode(r3, r5)     // Catch:{ JSONException -> 0x0718 }
            r16 = r1
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0718:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x071f
        L_0x071d:
            r0 = move-exception
            r1 = r0
        L_0x071f:
            r1.printStackTrace()
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0729:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x077d }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x077d }
            java.lang.String r3 = r1.optString(r7)     // Catch:{ JSONException -> 0x0775 }
            java.lang.String r4 = "mode"
            int r4 = r1.optInt(r4)     // Catch:{ JSONException -> 0x0775 }
            r11 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0775 }
            r4.<init>()     // Catch:{ JSONException -> 0x0775 }
            java.lang.String r5 = "web->getDetectionMode, mac:"
            r4.append(r5)     // Catch:{ JSONException -> 0x0775 }
            r4.append(r3)     // Catch:{ JSONException -> 0x0775 }
            java.lang.String r5 = ",mode:"
            r4.append(r5)     // Catch:{ JSONException -> 0x0775 }
            r4.append(r11)     // Catch:{ JSONException -> 0x0775 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0775 }
            meshsdk.MeshLogNew.i(r4)     // Catch:{ JSONException -> 0x0775 }
            meshsdk.SIGMesh r12 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0775 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$a0 r13 = new com.leedarson.serviceimpl.BleMeshServiceImpl$a0     // Catch:{ JSONException -> 0x0775 }
            r4 = r13
            r5 = r2
            r6 = r15
            r7 = r11
            r14 = r8
            r24 = r9
            r9 = r3
            r4.<init>(r6, r7, r8, r9)     // Catch:{ JSONException -> 0x0770 }
            r12.getDetectionModeParams(r3, r11, r13)     // Catch:{ JSONException -> 0x0770 }
            r16 = r1
            r12 = r10
            r3 = r14
            r11 = r15
            goto L_0x179d
        L_0x0770:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0782
        L_0x0775:
            r0 = move-exception
            r14 = r8
            r24 = r9
            r16 = r1
            r1 = r0
            goto L_0x0782
        L_0x077d:
            r0 = move-exception
            r14 = r8
            r24 = r9
            r1 = r0
        L_0x0782:
            r1.printStackTrace()
            r12 = r10
            r3 = r14
            r11 = r15
            goto L_0x179d
        L_0x078a:
            r14 = r8
            r24 = r9
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            com.leedarson.serviceimpl.BleMeshServiceImpl$y r3 = new com.leedarson.serviceimpl.BleMeshServiceImpl$y
            r3.<init>()
            java.lang.reflect.Type r3 = r3.getType()
            java.lang.Object r1 = r1.fromJson((java.lang.String) r10, (java.lang.reflect.Type) r3)
            meshsdk.model.json.DetectMode r1 = (meshsdk.model.json.DetectMode) r1
            r1.convertTimespan()
            r1.convertEcoTimeSpan()
            meshsdk.SIGMesh r3 = meshsdk.SIGMesh.getInstance()
            com.leedarson.serviceimpl.BleMeshServiceImpl$z r4 = new com.leedarson.serviceimpl.BleMeshServiceImpl$z
            r4.<init>(r15, r14)
            r3.setDetectionMode(r1, r4)
            r12 = r10
            r3 = r14
            r11 = r15
            goto L_0x179d
        L_0x07b8:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x07e3 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x07e3 }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ JSONException -> 0x07de }
            java.lang.String r4 = "onOff"
            r5 = 0
            int r4 = r1.optInt(r4, r5)     // Catch:{ JSONException -> 0x07de }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x07de }
            com.leedarson.serviceimpl.BleMeshServiceImpl$x r6 = new com.leedarson.serviceimpl.BleMeshServiceImpl$x     // Catch:{ JSONException -> 0x07de }
            r6.<init>(r15, r11)     // Catch:{ JSONException -> 0x07de }
            r5.setSingleCappedAlarm(r3, r4, r6)     // Catch:{ JSONException -> 0x07de }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x07de:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x07e5
        L_0x07e3:
            r0 = move-exception
            r1 = r0
        L_0x07e5:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x07ed:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0811 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0811 }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ JSONException -> 0x080c }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x080c }
            com.leedarson.serviceimpl.BleMeshServiceImpl$w r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$w     // Catch:{ JSONException -> 0x080c }
            r5.<init>(r3, r15, r11)     // Catch:{ JSONException -> 0x080c }
            r4.getSingleCappedAlarm(r3, r5)     // Catch:{ JSONException -> 0x080c }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x080c:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0813
        L_0x0811:
            r0 = move-exception
            r1 = r0
        L_0x0813:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x081b:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0853 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0853 }
            org.json.JSONArray r3 = r1.getJSONArray(r5)     // Catch:{ JSONException -> 0x084e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x084e }
            r4.<init>()     // Catch:{ JSONException -> 0x084e }
            java.lang.String r5 = "web->getCacheBattery macs:"
            r4.append(r5)     // Catch:{ JSONException -> 0x084e }
            r4.append(r3)     // Catch:{ JSONException -> 0x084e }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x084e }
            meshsdk.MeshLog.i(r4)     // Catch:{ JSONException -> 0x084e }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x084e }
            com.leedarson.serviceimpl.BleMeshServiceImpl$u r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$u     // Catch:{ JSONException -> 0x084e }
            r5.<init>(r15, r11)     // Catch:{ JSONException -> 0x084e }
            r4.getCacheBatterys(r3, r5)     // Catch:{ JSONException -> 0x084e }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x084e:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0855
        L_0x0853:
            r0 = move-exception
            r1 = r0
        L_0x0855:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x085d:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x089b }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x089b }
            java.lang.String r3 = r1.optString(r7, r14)     // Catch:{ JSONException -> 0x0896 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0896 }
            r4.<init>()     // Catch:{ JSONException -> 0x0896 }
            java.lang.String r5 = "web->getBattery mac:"
            r4.append(r5)     // Catch:{ JSONException -> 0x0896 }
            r4.append(r3)     // Catch:{ JSONException -> 0x0896 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0896 }
            meshsdk.MeshLog.i(r4)     // Catch:{ JSONException -> 0x0896 }
            meshsdk.SIGMesh r12 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0896 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$t r13 = new com.leedarson.serviceimpl.BleMeshServiceImpl$t     // Catch:{ JSONException -> 0x0896 }
            r4 = r13
            r5 = r2
            r6 = r3
            r7 = r3
            r8 = r15
            r9 = r11
            r4.<init>(r6, r7, r8, r9)     // Catch:{ JSONException -> 0x0896 }
            r12.getBattery(r3, r13)     // Catch:{ JSONException -> 0x0896 }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0896:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x089d
        L_0x089b:
            r0 = move-exception
            r1 = r0
        L_0x089d:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x08a5:
            r11 = r8
            r24 = r9
            r3 = r14
            r4 = r14
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x08e1 }
            r5.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x08e1 }
            boolean r6 = r5.has(r7)     // Catch:{ JSONException -> 0x08dc }
            if (r6 == 0) goto L_0x08ba
            java.lang.String r6 = r5.getString(r7)     // Catch:{ JSONException -> 0x08dc }
            r3 = r6
        L_0x08ba:
            boolean r6 = r5.has(r1)     // Catch:{ JSONException -> 0x08dc }
            if (r6 == 0) goto L_0x08c5
            java.lang.String r1 = r5.getString(r1)     // Catch:{ JSONException -> 0x08dc }
            r4 = r1
        L_0x08c5:
            int r1 = r5.getInt(r13)     // Catch:{ JSONException -> 0x08dc }
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x08dc }
            com.leedarson.serviceimpl.BleMeshServiceImpl$s r7 = new com.leedarson.serviceimpl.BleMeshServiceImpl$s     // Catch:{ JSONException -> 0x08dc }
            r7.<init>(r15, r11)     // Catch:{ JSONException -> 0x08dc }
            r6.performEffectLinkage(r3, r1, r4, r7)     // Catch:{ JSONException -> 0x08dc }
            r16 = r5
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x08dc:
            r0 = move-exception
            r1 = r0
            r16 = r5
            goto L_0x08e3
        L_0x08e1:
            r0 = move-exception
            r1 = r0
        L_0x08e3:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x08eb:
            r11 = r8
            r24 = r9
            r1 = r14
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0917 }
            r3.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0917 }
            boolean r4 = r3.has(r7)     // Catch:{ JSONException -> 0x0912 }
            if (r4 == 0) goto L_0x08ff
            java.lang.String r4 = r3.getString(r7)     // Catch:{ JSONException -> 0x0912 }
            r1 = r4
        L_0x08ff:
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0912 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$r r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$r     // Catch:{ JSONException -> 0x0912 }
            r5.<init>(r15, r11)     // Catch:{ JSONException -> 0x0912 }
            r4.getEffectLinkage(r1, r5)     // Catch:{ JSONException -> 0x0912 }
            r16 = r3
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0912:
            r0 = move-exception
            r1 = r0
            r16 = r3
            goto L_0x0919
        L_0x0917:
            r0 = move-exception
            r1 = r0
        L_0x0919:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0921:
            r11 = r8
            r24 = r9
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0a2b }
            r3.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0a2b }
            r4 = 0
            java.lang.String r5 = r3.getString(r7)     // Catch:{ JSONException -> 0x0a18 }
            com.alibaba.android.arouter.launcher.a r6 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x0a18 }
            java.lang.Class<com.leedarson.serviceinterface.LightsRhythmService> r8 = com.leedarson.serviceinterface.LightsRhythmService.class
            java.lang.Object r6 = r6.g(r8)     // Catch:{ JSONException -> 0x0a18 }
            com.leedarson.serviceinterface.LightsRhythmService r6 = (com.leedarson.serviceinterface.LightsRhythmService) r6     // Catch:{ JSONException -> 0x0a18 }
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0a18 }
            r8.<init>()     // Catch:{ JSONException -> 0x0a18 }
            if (r6 == 0) goto L_0x09ff
            org.json.JSONArray r9 = r6.getRhythmDevices()     // Catch:{ JSONException -> 0x0a18 }
            boolean r13 = r6.isRhythm()     // Catch:{ JSONException -> 0x0a18 }
            if (r13 == 0) goto L_0x09e5
            int r13 = r9.length()     // Catch:{ JSONException -> 0x0a18 }
            if (r13 != 0) goto L_0x0957
            r35 = r3
            r37 = r6
            goto L_0x09e9
        L_0x0957:
            r13 = 0
            r14 = 0
        L_0x0959:
            r35 = r3
            int r3 = r9.length()     // Catch:{ JSONException -> 0x0a15 }
            if (r14 >= r3) goto L_0x09ca
            java.lang.Object r3 = r9.get(r14)     // Catch:{ JSONException -> 0x0a15 }
            org.json.JSONObject r3 = (org.json.JSONObject) r3     // Catch:{ JSONException -> 0x0a15 }
            r36 = r4
            java.lang.String r4 = "protocolType"
            java.lang.Object r4 = r3.get(r4)     // Catch:{ JSONException -> 0x0a15 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0a15 }
            r37 = r6
            java.lang.String r6 = "protocolData"
            java.lang.Object r6 = r3.get(r6)     // Catch:{ JSONException -> 0x0a15 }
            org.json.JSONObject r6 = (org.json.JSONObject) r6     // Catch:{ JSONException -> 0x0a15 }
            r38 = r3
            java.lang.String r3 = "BLEMesh"
            boolean r3 = r3.equals(r4)     // Catch:{ JSONException -> 0x0a15 }
            if (r3 == 0) goto L_0x09bf
            r3 = 0
            boolean r16 = r6.has(r7)     // Catch:{ JSONException -> 0x0a15 }
            if (r16 == 0) goto L_0x0994
            java.lang.String r16 = r6.getString(r7)     // Catch:{ JSONException -> 0x0a15 }
            r3 = r16
        L_0x0994:
            boolean r16 = r6.has(r1)     // Catch:{ JSONException -> 0x0a15 }
            if (r16 == 0) goto L_0x09a5
            java.lang.String r16 = r6.getString(r1)     // Catch:{ JSONException -> 0x0a15 }
            r33 = r16
            r16 = r4
            r4 = r33
            goto L_0x09a9
        L_0x09a5:
            r16 = r4
            r4 = r36
        L_0x09a9:
            if (r3 == 0) goto L_0x09b3
            boolean r18 = r3.equals(r5)     // Catch:{ JSONException -> 0x0a15 }
            if (r18 == 0) goto L_0x09b3
            r13 = 1
            goto L_0x09ce
        L_0x09b3:
            if (r4 == 0) goto L_0x09c3
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0a15 }
            int r1 = r1.getRhythmStatusForMac(r4, r5)     // Catch:{ JSONException -> 0x0a15 }
            r13 = r1
            goto L_0x09ce
        L_0x09bf:
            r16 = r4
            r4 = r36
        L_0x09c3:
            int r14 = r14 + 1
            r3 = r35
            r6 = r37
            goto L_0x0959
        L_0x09ca:
            r36 = r4
            r37 = r6
        L_0x09ce:
            if (r4 != 0) goto L_0x0a03
            java.lang.String r1 = "support"
            r3 = 1
            r8.put((java.lang.String) r1, (int) r3)     // Catch:{ JSONException -> 0x0a15 }
            r8.put((java.lang.String) r12, (int) r13)     // Catch:{ JSONException -> 0x0a15 }
            org.json.JSONObject r1 = meshsdk.BaseResp.generatorSuccessResp(r8)     // Catch:{ JSONException -> 0x0a15 }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x0a15 }
            r2.postJsBridgeCallback(r15, r1, r11)     // Catch:{ JSONException -> 0x0a15 }
            goto L_0x0a03
        L_0x09e5:
            r35 = r3
            r37 = r6
        L_0x09e9:
            java.lang.String r1 = "support"
            r3 = 1
            r8.put((java.lang.String) r1, (int) r3)     // Catch:{ JSONException -> 0x0a15 }
            r1 = 0
            r8.put((java.lang.String) r12, (int) r1)     // Catch:{ JSONException -> 0x0a15 }
            org.json.JSONObject r1 = meshsdk.BaseResp.generatorSuccessResp(r8)     // Catch:{ JSONException -> 0x0a15 }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x0a15 }
            r2.postJsBridgeCallback(r15, r1, r11)     // Catch:{ JSONException -> 0x0a15 }
            goto L_0x0a03
        L_0x09ff:
            r35 = r3
            r37 = r6
        L_0x0a03:
            java.lang.String r1 = "查下律动状态 web触发调用 getLightsRhythmStatus"
            meshsdk.MeshLogNew.v(r1)     // Catch:{ JSONException -> 0x0a15 }
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0a15 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$q r3 = new com.leedarson.serviceimpl.BleMeshServiceImpl$q     // Catch:{ JSONException -> 0x0a15 }
            r3.<init>(r8, r15, r11)     // Catch:{ JSONException -> 0x0a15 }
            r1.getLightsRhythmStatus(r5, r3)     // Catch:{ JSONException -> 0x0a15 }
            goto L_0x0a1f
        L_0x0a15:
            r0 = move-exception
            r1 = r0
            goto L_0x0a1c
        L_0x0a18:
            r0 = move-exception
            r35 = r3
            r1 = r0
        L_0x0a1c:
            r1.printStackTrace()     // Catch:{ JSONException -> 0x0a26 }
        L_0x0a1f:
            r16 = r35
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0a26:
            r0 = move-exception
            r16 = r35
            r1 = r0
            goto L_0x0a2d
        L_0x0a2b:
            r0 = move-exception
            r1 = r0
        L_0x0a2d:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0a35:
            r11 = r8
            r24 = r9
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.Class<meshsdk.model.json.DSTRule> r3 = meshsdk.model.json.DSTRule.class
            java.lang.Object r1 = r1.fromJson((java.lang.String) r10, r3)
            meshsdk.model.json.DSTRule r1 = (meshsdk.model.json.DSTRule) r1
            meshsdk.SIGMesh r3 = meshsdk.SIGMesh.getInstance()
            com.leedarson.serviceimpl.BleMeshServiceImpl$p r4 = new com.leedarson.serviceimpl.BleMeshServiceImpl$p
            r4.<init>(r15, r11)
            r3.setDST(r1, r4)
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0a56:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0a7a }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0a7a }
            java.lang.String r3 = r1.getString(r7)     // Catch:{ JSONException -> 0x0a75 }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0a75 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$o r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$o     // Catch:{ JSONException -> 0x0a75 }
            r5.<init>(r15, r11)     // Catch:{ JSONException -> 0x0a75 }
            r4.getCurrentCustomEffectMode(r3, r5)     // Catch:{ JSONException -> 0x0a75 }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0a75:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0a7c
        L_0x0a7a:
            r0 = move-exception
            r1 = r0
        L_0x0a7c:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0a84:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0aae }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0aae }
            java.lang.String r3 = r1.getString(r7)     // Catch:{ JSONException -> 0x0aa9 }
            java.lang.String r4 = "effectId"
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x0aa9 }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0aa9 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$n r6 = new com.leedarson.serviceimpl.BleMeshServiceImpl$n     // Catch:{ JSONException -> 0x0aa9 }
            r6.<init>(r15, r11)     // Catch:{ JSONException -> 0x0aa9 }
            r5.getEffectMode(r3, r4, r6)     // Catch:{ JSONException -> 0x0aa9 }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0aa9:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0ab0
        L_0x0aae:
            r0 = move-exception
            r1 = r0
        L_0x0ab0:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0ab8:
            r11 = r8
            r24 = r9
            com.google.gson.Gson r1 = new com.google.gson.Gson     // Catch:{ Exception -> 0x0ad9 }
            r1.<init>()     // Catch:{ Exception -> 0x0ad9 }
            java.lang.Class<meshsdk.model.json.CustomEffectMode> r3 = meshsdk.model.json.CustomEffectMode.class
            java.lang.Object r1 = r1.fromJson((java.lang.String) r10, r3)     // Catch:{ Exception -> 0x0ad9 }
            meshsdk.model.json.CustomEffectMode r1 = (meshsdk.model.json.CustomEffectMode) r1     // Catch:{ Exception -> 0x0ad9 }
            meshsdk.SIGMesh r3 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x0ad9 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$m r4 = new com.leedarson.serviceimpl.BleMeshServiceImpl$m     // Catch:{ Exception -> 0x0ad9 }
            r4.<init>(r15, r11)     // Catch:{ Exception -> 0x0ad9 }
            r3.setCustomEffectMode(r1, r4)     // Catch:{ Exception -> 0x0ad9 }
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0ad9:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0ae3:
            r11 = r8
            r24 = r9
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0b28 }
            r4.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0b28 }
            java.lang.String r27 = r4.optString(r7)     // Catch:{ JSONException -> 0x0b23 }
            java.lang.String r5 = "effectId"
            int r28 = r4.getInt(r5)     // Catch:{ JSONException -> 0x0b23 }
            r5 = -1
            r6 = 0
            boolean r3 = r11.equals(r3)     // Catch:{ JSONException -> 0x0b23 }
            if (r3 == 0) goto L_0x0b05
            r6 = 1
            java.lang.String r3 = "durationTime"
            int r3 = r4.getInt(r3)     // Catch:{ JSONException -> 0x0b23 }
            r5 = r3
        L_0x0b05:
            java.lang.String r26 = r4.optString(r1)     // Catch:{ JSONException -> 0x0b23 }
            meshsdk.SIGMesh r25 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0b23 }
            long r7 = (long) r5     // Catch:{ JSONException -> 0x0b23 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$l r1 = new com.leedarson.serviceimpl.BleMeshServiceImpl$l     // Catch:{ JSONException -> 0x0b23 }
            r1.<init>(r15, r11)     // Catch:{ JSONException -> 0x0b23 }
            r29 = r7
            r31 = r6
            r32 = r1
            r25.setEffectMode(r26, r27, r28, r29, r31, r32)     // Catch:{ JSONException -> 0x0b23 }
            r16 = r4
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0b23:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x0b2a
        L_0x0b28:
            r0 = move-exception
            r1 = r0
        L_0x0b2a:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0b32:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0b56 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0b56 }
            java.lang.String r3 = r1.getString(r7)     // Catch:{ JSONException -> 0x0b51 }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0b51 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$j r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$j     // Catch:{ JSONException -> 0x0b51 }
            r5.<init>(r15, r11)     // Catch:{ JSONException -> 0x0b51 }
            r4.getColorMode(r3, r5)     // Catch:{ JSONException -> 0x0b51 }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0b51:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0b58
        L_0x0b56:
            r0 = move-exception
            r1 = r0
        L_0x0b58:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0b60:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0b84 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0b84 }
            java.lang.String r3 = r1.getString(r7)     // Catch:{ JSONException -> 0x0b7f }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0b7f }
            com.leedarson.serviceimpl.BleMeshServiceImpl$i r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$i     // Catch:{ JSONException -> 0x0b7f }
            r5.<init>()     // Catch:{ JSONException -> 0x0b7f }
            r4.getDevTime(r3, r5)     // Catch:{ JSONException -> 0x0b7f }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0b7f:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0b86
        L_0x0b84:
            r0 = move-exception
            r1 = r0
        L_0x0b86:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0b8e:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0bc6 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0bc6 }
            org.json.JSONArray r3 = r1.getJSONArray(r5)     // Catch:{ JSONException -> 0x0bc1 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0bc1 }
            r4.<init>()     // Catch:{ JSONException -> 0x0bc1 }
            java.lang.String r5 = "web->getCacheVesion macs:"
            r4.append(r5)     // Catch:{ JSONException -> 0x0bc1 }
            r4.append(r3)     // Catch:{ JSONException -> 0x0bc1 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0bc1 }
            meshsdk.MeshLog.i(r4)     // Catch:{ JSONException -> 0x0bc1 }
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0bc1 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$h r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$h     // Catch:{ JSONException -> 0x0bc1 }
            r5.<init>(r15, r11)     // Catch:{ JSONException -> 0x0bc1 }
            r4.getCacheDevVersions(r3, r5)     // Catch:{ JSONException -> 0x0bc1 }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0bc1:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0bc8
        L_0x0bc6:
            r0 = move-exception
            r1 = r0
        L_0x0bc8:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0bd0:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0c0e }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0c0e }
            java.lang.String r3 = r1.getString(r7)     // Catch:{ JSONException -> 0x0c09 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0c09 }
            r4.<init>()     // Catch:{ JSONException -> 0x0c09 }
            java.lang.String r5 = "web->getVersion协议版本 mac:"
            r4.append(r5)     // Catch:{ JSONException -> 0x0c09 }
            r4.append(r3)     // Catch:{ JSONException -> 0x0c09 }
            java.lang.String r4 = r4.toString()     // Catch:{ JSONException -> 0x0c09 }
            meshsdk.MeshLogNew.v(r4)     // Catch:{ JSONException -> 0x0c09 }
            meshsdk.SIGMesh r12 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0c09 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$g r13 = new com.leedarson.serviceimpl.BleMeshServiceImpl$g     // Catch:{ JSONException -> 0x0c09 }
            r4 = r13
            r5 = r2
            r6 = r3
            r7 = r3
            r8 = r15
            r9 = r11
            r4.<init>(r6, r7, r8, r9)     // Catch:{ JSONException -> 0x0c09 }
            r12.getDevVersion(r3, r13)     // Catch:{ JSONException -> 0x0c09 }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0c09:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0c10
        L_0x0c0e:
            r0 = move-exception
            r1 = r0
        L_0x0c10:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0c18:
            r11 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0c48 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0c48 }
            java.lang.String r3 = r1.getString(r7)     // Catch:{ JSONException -> 0x0c43 }
            java.lang.String r4 = "smartId"
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x0c43 }
            java.lang.String r5 = "enable"
            int r5 = r1.getInt(r5)     // Catch:{ JSONException -> 0x0c43 }
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0c43 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$f r7 = new com.leedarson.serviceimpl.BleMeshServiceImpl$f     // Catch:{ JSONException -> 0x0c43 }
            r7.<init>(r15, r11)     // Catch:{ JSONException -> 0x0c43 }
            r6.setSmartRuleEnable(r4, r3, r5, r7)     // Catch:{ JSONException -> 0x0c43 }
            r16 = r1
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0c43:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0c4a
        L_0x0c48:
            r0 = move-exception
            r1 = r0
        L_0x0c4a:
            r1.printStackTrace()
            r12 = r10
            r3 = r11
            r11 = r15
            goto L_0x179d
        L_0x0c52:
            r3 = r8
            r24 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0ca7 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0ca7 }
            java.lang.String r4 = r1.getString(r7)     // Catch:{ JSONException -> 0x0ca2 }
            r12 = r4
            java.lang.String r4 = "smartId"
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x0ca2 }
            r13 = r4
            java.lang.String r4 = meshsdk.util.MeshConstants.TRACE_ID_SMART     // Catch:{ JSONException -> 0x0ca2 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0ca2 }
            r5.<init>()     // Catch:{ JSONException -> 0x0ca2 }
            java.lang.String r7 = "removeSmartRule mac:"
            r5.append(r7)     // Catch:{ JSONException -> 0x0ca2 }
            r5.append(r12)     // Catch:{ JSONException -> 0x0ca2 }
            java.lang.String r7 = ",smartId:"
            r5.append(r7)     // Catch:{ JSONException -> 0x0ca2 }
            r5.append(r13)     // Catch:{ JSONException -> 0x0ca2 }
            r5.append(r11)     // Catch:{ JSONException -> 0x0ca2 }
            r5.append(r10)     // Catch:{ JSONException -> 0x0ca2 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x0ca2 }
            r2.F(r4, r5, r6)     // Catch:{ JSONException -> 0x0ca2 }
            meshsdk.SIGMesh r11 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0ca2 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$e r14 = new com.leedarson.serviceimpl.BleMeshServiceImpl$e     // Catch:{ JSONException -> 0x0ca2 }
            r4 = r14
            r5 = r2
            r6 = r15
            r7 = r3
            r8 = r12
            r9 = r13
            r4.<init>(r6, r7, r8, r9)     // Catch:{ JSONException -> 0x0ca2 }
            r11.removeSmartRule(r13, r12, r14)     // Catch:{ JSONException -> 0x0ca2 }
            r16 = r1
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0ca2:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0ca9
        L_0x0ca7:
            r0 = move-exception
            r1 = r0
        L_0x0ca9:
            r1.printStackTrace()
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0cb0:
            r3 = r8
            r24 = r9
            java.lang.String r1 = "jsbridgeSetSmart"
            meshsdk.util.TimeRecorder.mark(r1)
            com.google.gson.Gson r1 = new com.google.gson.Gson
            r1.<init>()
            java.lang.Class<meshsdk.model.json.RoutineRule> r4 = meshsdk.model.json.RoutineRule.class
            java.lang.Object r1 = r1.fromJson((java.lang.String) r10, r4)
            meshsdk.model.json.RoutineRule r1 = (meshsdk.model.json.RoutineRule) r1
            java.lang.String r4 = meshsdk.util.MeshConstants.TRACE_ID_SMART
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "setSmartRule mac:"
            r5.append(r7)
            java.lang.String r7 = r1.mac
            r5.append(r7)
            java.lang.String r7 = ",smartId:"
            r5.append(r7)
            int r7 = r1.smartId
            r5.append(r7)
            r5.append(r11)
            r5.append(r10)
            java.lang.String r5 = r5.toString()
            r2.F(r4, r5, r6)
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()
            com.leedarson.serviceimpl.BleMeshServiceImpl$d r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$d
            r5.<init>(r15, r3, r1)
            r4.setSmartRule(r1, r5)
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x0cfd:
            r3 = r8
            r24 = r9
            java.lang.String r1 = meshsdk.util.ProcedureCollector.FUNC_Mesh_OTA     // Catch:{ JSONException -> 0x0d99 }
            r4 = 1
            meshsdk.util.ProcedureCollector.startCollect(r1, r4)     // Catch:{ JSONException -> 0x0d99 }
            meshsdk.model.json.OTAProgressBean r1 = r2.b     // Catch:{ JSONException -> 0x0d99 }
            if (r1 != 0) goto L_0x0d18
            meshsdk.model.json.OTAProgressBean r1 = new meshsdk.model.json.OTAProgressBean     // Catch:{ JSONException -> 0x0d12 }
            r1.<init>()     // Catch:{ JSONException -> 0x0d12 }
            r2.b = r1     // Catch:{ JSONException -> 0x0d12 }
            goto L_0x0d18
        L_0x0d12:
            r0 = move-exception
            r1 = r0
            r12 = r24
            goto L_0x0d9d
        L_0x0d18:
            r12 = r24
            r1 = 1
            G(r12, r1)     // Catch:{ JSONException -> 0x0d96 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0d96 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0d96 }
            java.lang.String r4 = r1.getString(r7)     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r5 = "url"
            java.lang.String r5 = r1.getString(r5)     // Catch:{ JSONException -> 0x0d91 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0d91 }
            r6.<init>()     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r7 = "web->ota_upgrade,mac:"
            r6.append(r7)     // Catch:{ JSONException -> 0x0d91 }
            r6.append(r4)     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r7 = ",url:"
            r6.append(r7)     // Catch:{ JSONException -> 0x0d91 }
            r6.append(r5)     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0d91 }
            meshsdk.MeshLogNew.ota(r6)     // Catch:{ JSONException -> 0x0d91 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x0d91 }
            meshsdk.datamgr.MeshDataManager.startOTATimespan = r6     // Catch:{ JSONException -> 0x0d91 }
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0d91 }
            meshsdk.model.MeshInfo r6 = r6.getMeshInfo()     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r6 = r6.meshUUID     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r19 = meshsdk.util.LDSMeshUtil.getMeshHWVersion(r6, r4)     // Catch:{ JSONException -> 0x0d91 }
            com.leedarson.serviceimpl.reporters.ota.b r16 = com.leedarson.serviceimpl.reporters.ota.b.b()     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r20 = ""
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0d91 }
            r6.<init>()     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r7 = "url:"
            r6.append(r7)     // Catch:{ JSONException -> 0x0d91 }
            r6.append(r5)     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r21 = r6.toString()     // Catch:{ JSONException -> 0x0d91 }
            java.lang.String r22 = meshsdk.util.MeshConstants.EVENT_OTA     // Catch:{ JSONException -> 0x0d91 }
            r17 = r4
            r18 = r4
            r16.a(r17, r18, r19, r20, r21, r22)     // Catch:{ JSONException -> 0x0d91 }
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0d91 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$c r7 = new com.leedarson.serviceimpl.BleMeshServiceImpl$c     // Catch:{ JSONException -> 0x0d91 }
            r7.<init>(r12, r15)     // Catch:{ JSONException -> 0x0d91 }
            r6.OTAUpgrade(r5, r4, r7)     // Catch:{ JSONException -> 0x0d91 }
            r16 = r1
            r24 = r12
            r11 = r15
            r12 = r10
            goto L_0x179d
        L_0x0d91:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0d9d
        L_0x0d96:
            r0 = move-exception
            r1 = r0
            goto L_0x0d9d
        L_0x0d99:
            r0 = move-exception
            r12 = r24
            r1 = r0
        L_0x0d9d:
            r1.printStackTrace()
            r24 = r12
            r11 = r15
            r12 = r10
            goto L_0x179d
        L_0x0da6:
            r3 = r8
            r12 = r9
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0ddf }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0ddf }
            r4 = r21
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x0dda }
            r5 = 0
            java.lang.String r6 = "fading"
            boolean r6 = r1.has(r6)     // Catch:{ JSONException -> 0x0dda }
            if (r6 == 0) goto L_0x0dc3
            java.lang.String r6 = "fading"
            int r6 = r1.getInt(r6)     // Catch:{ JSONException -> 0x0dda }
            r5 = r6
        L_0x0dc3:
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0dda }
            org.json.JSONObject r6 = r6.runScene(r4, r5)     // Catch:{ JSONException -> 0x0dda }
            java.lang.String r7 = r6.toString()     // Catch:{ JSONException -> 0x0dda }
            r2.postJsBridgeCallback(r15, r7, r3)     // Catch:{ JSONException -> 0x0dda }
            r16 = r1
            r24 = r12
            r11 = r15
            r12 = r10
            goto L_0x179d
        L_0x0dda:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0de1
        L_0x0ddf:
            r0 = move-exception
            r1 = r0
        L_0x0de1:
            r1.printStackTrace()
            r24 = r12
            r11 = r15
            r12 = r10
            goto L_0x179d
        L_0x0dea:
            r3 = r8
            r12 = r9
            r4 = r21
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0e51 }
            r1.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0e51 }
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x0e47 }
            r13 = r4
            java.lang.String r4 = r1.getString(r7)     // Catch:{ JSONException -> 0x0e47 }
            r14 = r4
            java.lang.String r4 = meshsdk.util.MeshConstants.TRACE_ID_SCENE     // Catch:{ JSONException -> 0x0e47 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0e47 }
            r5.<init>()     // Catch:{ JSONException -> 0x0e47 }
            java.lang.String r7 = "removeSceneAction mac:"
            r5.append(r7)     // Catch:{ JSONException -> 0x0e47 }
            r5.append(r14)     // Catch:{ JSONException -> 0x0e47 }
            r8 = r20
            r5.append(r8)     // Catch:{ JSONException -> 0x0e47 }
            r5.append(r13)     // Catch:{ JSONException -> 0x0e47 }
            r5.append(r11)     // Catch:{ JSONException -> 0x0e47 }
            r5.append(r10)     // Catch:{ JSONException -> 0x0e47 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x0e47 }
            r2.F(r4, r5, r6)     // Catch:{ JSONException -> 0x0e47 }
            meshsdk.SIGMesh r11 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0e47 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$b r9 = new com.leedarson.serviceimpl.BleMeshServiceImpl$b     // Catch:{ JSONException -> 0x0e47 }
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0e47 }
            r4 = r9
            r5 = r2
            r7 = r15
            r8 = r3
            r35 = r1
            r1 = r9
            r9 = r14
            r24 = r12
            r12 = r10
            r10 = r13
            r4.<init>(r6, r7, r8, r9, r10)     // Catch:{ JSONException -> 0x0e42 }
            r11.removeSceneAction(r13, r14, r1)     // Catch:{ JSONException -> 0x0e42 }
            r16 = r35
            r11 = r15
            goto L_0x179d
        L_0x0e42:
            r0 = move-exception
            r16 = r35
            r1 = r0
            goto L_0x0e56
        L_0x0e47:
            r0 = move-exception
            r35 = r1
            r24 = r12
            r12 = r10
            r16 = r35
            r1 = r0
            goto L_0x0e56
        L_0x0e51:
            r0 = move-exception
            r24 = r12
            r12 = r10
            r1 = r0
        L_0x0e56:
            r1.printStackTrace()
            r11 = r15
            goto L_0x179d
        L_0x0e5c:
            r3 = r8
            r24 = r9
            r12 = r10
            r4 = r21
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0e8f }
            r1.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x0e8f }
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x0e8a }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0e8a }
            org.json.JSONObject r5 = r5.removeScene(r4)     // Catch:{ JSONException -> 0x0e8a }
            java.lang.String r6 = "删除 mesh scene 成功，设置mesh needUpload为true"
            r2.reportMeshJsonReport(r6)     // Catch:{ JSONException -> 0x0e8a }
            meshsdk.datamgr.MeshDataManager r6 = r2.c     // Catch:{ JSONException -> 0x0e8a }
            r7 = 1
            r6.setNeedUpload(r7)     // Catch:{ JSONException -> 0x0e8a }
            java.lang.String r6 = r5.toString()     // Catch:{ JSONException -> 0x0e8a }
            r2.postJsBridgeCallback(r15, r6, r3)     // Catch:{ JSONException -> 0x0e8a }
            r16 = r1
            r11 = r15
            goto L_0x179d
        L_0x0e8a:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x0e91
        L_0x0e8f:
            r0 = move-exception
            r1 = r0
        L_0x0e91:
            r1.printStackTrace()
            r11 = r15
            goto L_0x179d
        L_0x0e97:
            r3 = r8
            r24 = r9
            r12 = r10
            r8 = r20
            r4 = r21
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0ffd }
            r1.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x0ffd }
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x0ff5 }
            java.lang.String r5 = r1.getString(r7)     // Catch:{ JSONException -> 0x0ff5 }
            r7 = 0
            boolean r9 = r1.has(r13)     // Catch:{ JSONException -> 0x0ff5 }
            if (r9 == 0) goto L_0x0f50
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0f47 }
            r9.<init>()     // Catch:{ JSONException -> 0x0f47 }
            r7 = r9
            org.json.JSONObject r9 = r1.getJSONObject(r13)     // Catch:{ JSONException -> 0x0f47 }
            java.lang.String r10 = "OnOff"
            boolean r10 = r9.has(r10)     // Catch:{ JSONException -> 0x0f47 }
            if (r10 == 0) goto L_0x0ede
            meshsdk.model.CustomScene$SceneRule r10 = new meshsdk.model.CustomScene$SceneRule     // Catch:{ JSONException -> 0x0f47 }
            r13 = 4096(0x1000, float:5.74E-42)
            java.lang.String r14 = "OnOff"
            int r14 = r9.getInt(r14)     // Catch:{ JSONException -> 0x0f47 }
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ JSONException -> 0x0f47 }
            r35 = r1
            r1 = 0
            r10.<init>(r13, r14, r1)     // Catch:{ JSONException -> 0x0f95 }
            r1 = r10
            r7.add(r1)     // Catch:{ JSONException -> 0x0f95 }
            goto L_0x0ee0
        L_0x0ede:
            r35 = r1
        L_0x0ee0:
            java.lang.String r1 = "Dimming"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0f95 }
            if (r1 == 0) goto L_0x0efd
            meshsdk.model.CustomScene$SceneRule r1 = new meshsdk.model.CustomScene$SceneRule     // Catch:{ JSONException -> 0x0f95 }
            r10 = 4864(0x1300, float:6.816E-42)
            java.lang.String r13 = "Dimming"
            int r13 = r9.getInt(r13)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x0f95 }
            r14 = 0
            r1.<init>(r10, r13, r14)     // Catch:{ JSONException -> 0x0f95 }
            r7.add(r1)     // Catch:{ JSONException -> 0x0f95 }
        L_0x0efd:
            java.lang.String r1 = "CCT"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0f95 }
            if (r1 == 0) goto L_0x0f1c
            meshsdk.model.CustomScene$SceneRule r1 = new meshsdk.model.CustomScene$SceneRule     // Catch:{ JSONException -> 0x0f95 }
            r10 = 4867(0x1303, float:6.82E-42)
            java.lang.String r13 = "CCT"
            int r13 = r9.getInt(r13)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ JSONException -> 0x0f95 }
            r14 = 0
            r1.<init>(r10, r13, r14)     // Catch:{ JSONException -> 0x0f95 }
            r7.add(r1)     // Catch:{ JSONException -> 0x0f95 }
            goto L_0x0f52
        L_0x0f1c:
            java.lang.String r1 = "HSLHue"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0f95 }
            if (r1 == 0) goto L_0x0f52
            meshsdk.model.json.HSL r1 = new meshsdk.model.json.HSL     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r10 = "HSLHue"
            int r10 = r9.getInt(r10)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r13 = "HSLSaturation"
            int r13 = r9.getInt(r13)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r14 = "HSLLightness"
            int r14 = r9.getInt(r14)     // Catch:{ JSONException -> 0x0f95 }
            r1.<init>(r10, r13, r14)     // Catch:{ JSONException -> 0x0f95 }
            meshsdk.model.CustomScene$SceneRule r10 = new meshsdk.model.CustomScene$SceneRule     // Catch:{ JSONException -> 0x0f95 }
            r13 = 4871(0x1307, float:6.826E-42)
            r14 = 1
            r10.<init>(r13, r1, r14)     // Catch:{ JSONException -> 0x0f95 }
            r7.add(r10)     // Catch:{ JSONException -> 0x0f95 }
            goto L_0x0f52
        L_0x0f47:
            r0 = move-exception
            r35 = r1
            r16 = r35
            r1 = r0
            r11 = r15
            goto L_0x1000
        L_0x0f50:
            r35 = r1
        L_0x0f52:
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0fef }
            meshsdk.model.MeshInfo r1 = r1.getMeshInfo()     // Catch:{ JSONException -> 0x0fef }
            java.util.List<meshsdk.model.NodeInfo> r1 = r1.nodes     // Catch:{ JSONException -> 0x0fef }
            meshsdk.model.NodeInfo r1 = meshsdk.util.LDSMeshUtil.findMeshNode((java.util.List<meshsdk.model.NodeInfo>) r1, (java.lang.String) r5)     // Catch:{ JSONException -> 0x0fef }
            if (r1 != 0) goto L_0x0f9c
            r6 = 418(0x1a2, float:5.86E-43)
            java.lang.String r9 = "node not exist"
            org.json.JSONObject r6 = meshsdk.BaseResp.generatorFailResp(r6, r9)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0f95 }
            r2.postJsBridgeCallback(r15, r6, r3)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r6 = meshsdk.util.MeshConstants.TRACE_ID_SCENE     // Catch:{ JSONException -> 0x0f95 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0f95 }
            r9.<init>()     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r10 = "addSceneAction fail mac:"
            r9.append(r10)     // Catch:{ JSONException -> 0x0f95 }
            r9.append(r5)     // Catch:{ JSONException -> 0x0f95 }
            r9.append(r8)     // Catch:{ JSONException -> 0x0f95 }
            r9.append(r4)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r8 = "node not exist"
            r9.append(r8)     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r8 = r9.toString()     // Catch:{ JSONException -> 0x0f95 }
            java.lang.String r9 = "failure"
            r2.F(r6, r8, r9)     // Catch:{ JSONException -> 0x0f95 }
            return
        L_0x0f95:
            r0 = move-exception
            r16 = r35
            r1 = r0
            r11 = r15
            goto L_0x1000
        L_0x0f9c:
            java.lang.String r9 = meshsdk.util.MeshConstants.TRACE_ID_SCENE     // Catch:{ JSONException -> 0x0fef }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0fef }
            r10.<init>()     // Catch:{ JSONException -> 0x0fef }
            java.lang.String r13 = "addSceneAction mac:"
            r10.append(r13)     // Catch:{ JSONException -> 0x0fef }
            r10.append(r5)     // Catch:{ JSONException -> 0x0fef }
            r10.append(r8)     // Catch:{ JSONException -> 0x0fef }
            r10.append(r4)     // Catch:{ JSONException -> 0x0fef }
            java.lang.String r8 = ",协议版本:"
            r10.append(r8)     // Catch:{ JSONException -> 0x0fef }
            int r8 = r1.protocolVersion     // Catch:{ JSONException -> 0x0fef }
            r10.append(r8)     // Catch:{ JSONException -> 0x0fef }
            r10.append(r11)     // Catch:{ JSONException -> 0x0fef }
            r10.append(r12)     // Catch:{ JSONException -> 0x0fef }
            java.lang.String r8 = r10.toString()     // Catch:{ JSONException -> 0x0fef }
            r2.F(r9, r8, r6)     // Catch:{ JSONException -> 0x0fef }
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0fef }
            com.leedarson.serviceimpl.BleMeshServiceImpl$a r8 = new com.leedarson.serviceimpl.BleMeshServiceImpl$a     // Catch:{ JSONException -> 0x0fef }
            meshsdk.SIGMesh r17 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x0fef }
            r11 = r15
            r15 = r8
            r16 = r2
            r18 = r11
            r19 = r3
            r20 = r5
            r21 = r4
            r22 = r12
            r15.<init>(r17, r18, r19, r20, r21, r22)     // Catch:{ JSONException -> 0x0fea }
            r6.addSceneAction(r4, r5, r7, r8)     // Catch:{ JSONException -> 0x0fea }
            r16 = r35
            goto L_0x179d
        L_0x0fea:
            r0 = move-exception
            r16 = r35
            r1 = r0
            goto L_0x1000
        L_0x0fef:
            r0 = move-exception
            r11 = r15
            r16 = r35
            r1 = r0
            goto L_0x1000
        L_0x0ff5:
            r0 = move-exception
            r35 = r1
            r11 = r15
            r16 = r35
            r1 = r0
            goto L_0x1000
        L_0x0ffd:
            r0 = move-exception
            r11 = r15
            r1 = r0
        L_0x1000:
            r1.printStackTrace()
            goto L_0x179d
        L_0x1005:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            r4 = r21
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x104f }
            r1.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x104f }
            int r4 = r1.getInt(r4)     // Catch:{ JSONException -> 0x104a }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x104a }
            int r5 = r5.addScene(r4)     // Catch:{ JSONException -> 0x104a }
            r6 = -1
            if (r5 == r6) goto L_0x1037
            java.lang.String r5 = "添加mesh scene成功，设置mesh needUpload为true"
            r2.reportMeshJsonReport(r5)     // Catch:{ JSONException -> 0x104a }
            meshsdk.datamgr.MeshDataManager r5 = r2.c     // Catch:{ JSONException -> 0x104a }
            r6 = 1
            r5.setNeedUpload(r6)     // Catch:{ JSONException -> 0x104a }
            org.json.JSONObject r5 = meshsdk.BaseResp.generatorSuccessResp()     // Catch:{ JSONException -> 0x104a }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x104a }
            r2.postJsBridgeCallback(r11, r5, r3)     // Catch:{ JSONException -> 0x104a }
            goto L_0x1046
        L_0x1037:
            r5 = 419(0x1a3, float:5.87E-43)
            java.lang.String r6 = "The scene address is full"
            org.json.JSONObject r5 = meshsdk.BaseResp.generatorFailResp(r5, r6)     // Catch:{ JSONException -> 0x104a }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x104a }
            r2.postJsBridgeCallback(r11, r5, r3)     // Catch:{ JSONException -> 0x104a }
        L_0x1046:
            r16 = r1
            goto L_0x179d
        L_0x104a:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x1051
        L_0x104f:
            r0 = move-exception
            r1 = r0
        L_0x1051:
            r1.printStackTrace()
            goto L_0x179d
        L_0x1056:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            org.json.JSONArray r1 = r1.scenes()
            org.json.JSONObject r4 = meshsdk.BaseResp.generatorSuccessResp(r1)
            java.lang.String r4 = r4.toString()
            r2.postJsBridgeCallback(r11, r4, r3)
            goto L_0x179d
        L_0x1070:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x10c6 }
            r4.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x10c6 }
            int r1 = r4.getInt(r1)     // Catch:{ JSONException -> 0x10c1 }
            java.lang.String r5 = "modelId"
            int r5 = r4.getInt(r5)     // Catch:{ JSONException -> 0x10c1 }
            java.lang.String r6 = "value"
            java.lang.Object r6 = r4.get(r6)     // Catch:{ JSONException -> 0x10c1 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x10c1 }
            r7.<init>()     // Catch:{ JSONException -> 0x10c1 }
            java.lang.String r8 = "web->组控,groupid:"
            r7.append(r8)     // Catch:{ JSONException -> 0x10c1 }
            r7.append(r1)     // Catch:{ JSONException -> 0x10c1 }
            java.lang.String r8 = ",model:"
            r7.append(r8)     // Catch:{ JSONException -> 0x10c1 }
            r7.append(r5)     // Catch:{ JSONException -> 0x10c1 }
            java.lang.String r8 = ",value:"
            r7.append(r8)     // Catch:{ JSONException -> 0x10c1 }
            r7.append(r6)     // Catch:{ JSONException -> 0x10c1 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x10c1 }
            meshsdk.MeshLogNew.v(r7)     // Catch:{ JSONException -> 0x10c1 }
            meshsdk.SIGMesh r7 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x10c1 }
            org.json.JSONObject r7 = r7.controlGroup(r1, r5, r6)     // Catch:{ JSONException -> 0x10c1 }
            java.lang.String r8 = r7.toString()     // Catch:{ JSONException -> 0x10c1 }
            r2.postJsBridgeCallback(r11, r8, r3)     // Catch:{ JSONException -> 0x10c1 }
            r16 = r4
            goto L_0x179d
        L_0x10c1:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x10c8
        L_0x10c6:
            r0 = move-exception
            r1 = r0
        L_0x10c8:
            r1.printStackTrace()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "error parameter :"
            r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r5 = -1
            org.json.JSONObject r4 = meshsdk.BaseResp.generatorFailResp(r5, r4)
            java.lang.String r4 = r4.toString()
            r2.postJsBridgeCallback(r11, r4, r3)
            goto L_0x179d
        L_0x10ee:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x1122 }
            r4.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x1122 }
            java.lang.String r5 = r4.getString(r7)     // Catch:{ JSONException -> 0x111d }
            org.json.JSONArray r1 = r4.getJSONArray(r1)     // Catch:{ JSONException -> 0x111d }
            java.lang.String r7 = meshsdk.util.MeshConstants.TRACE_ID_GROUP     // Catch:{ JSONException -> 0x111d }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x111d }
            r8.<init>()     // Catch:{ JSONException -> 0x111d }
            java.lang.String r9 = "removeDeviceFromGroups params:"
            r8.append(r9)     // Catch:{ JSONException -> 0x111d }
            r8.append(r12)     // Catch:{ JSONException -> 0x111d }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x111d }
            r2.F(r7, r8, r6)     // Catch:{ JSONException -> 0x111d }
            r2.D(r1, r5, r3, r11)     // Catch:{ JSONException -> 0x111d }
            r16 = r4
            goto L_0x179d
        L_0x111d:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x1124
        L_0x1122:
            r0 = move-exception
            r1 = r0
        L_0x1124:
            r1.printStackTrace()
            goto L_0x179d
        L_0x1129:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x1177 }
            r4.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x1177 }
            int r1 = r4.getInt(r1)     // Catch:{ JSONException -> 0x1172 }
            org.json.JSONArray r8 = new org.json.JSONArray     // Catch:{ JSONException -> 0x1172 }
            r8.<init>()     // Catch:{ JSONException -> 0x1172 }
            boolean r9 = r4.has(r7)     // Catch:{ JSONException -> 0x1172 }
            if (r9 == 0) goto L_0x114a
            java.lang.Object r5 = r4.get(r7)     // Catch:{ JSONException -> 0x1172 }
            r8.put((java.lang.Object) r5)     // Catch:{ JSONException -> 0x1172 }
            goto L_0x1155
        L_0x114a:
            boolean r7 = r4.has(r5)     // Catch:{ JSONException -> 0x1172 }
            if (r7 == 0) goto L_0x1155
            org.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x1172 }
            r8 = r5
        L_0x1155:
            java.lang.String r5 = meshsdk.util.MeshConstants.TRACE_ID_GROUP     // Catch:{ JSONException -> 0x1172 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x1172 }
            r7.<init>()     // Catch:{ JSONException -> 0x1172 }
            java.lang.String r9 = "removeGroupMember params:"
            r7.append(r9)     // Catch:{ JSONException -> 0x1172 }
            r7.append(r12)     // Catch:{ JSONException -> 0x1172 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x1172 }
            r2.F(r5, r7, r6)     // Catch:{ JSONException -> 0x1172 }
            r2.E(r8, r1, r3, r11)     // Catch:{ JSONException -> 0x1172 }
            r16 = r4
            goto L_0x179d
        L_0x1172:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x1179
        L_0x1177:
            r0 = move-exception
            r1 = r0
        L_0x1179:
            r1.printStackTrace()
            goto L_0x179d
        L_0x117e:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x11cc }
            r4.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x11cc }
            int r1 = r4.getInt(r1)     // Catch:{ JSONException -> 0x11c7 }
            org.json.JSONArray r8 = new org.json.JSONArray     // Catch:{ JSONException -> 0x11c7 }
            r8.<init>()     // Catch:{ JSONException -> 0x11c7 }
            boolean r9 = r4.has(r7)     // Catch:{ JSONException -> 0x11c7 }
            if (r9 == 0) goto L_0x119f
            java.lang.Object r5 = r4.get(r7)     // Catch:{ JSONException -> 0x11c7 }
            r8.put((java.lang.Object) r5)     // Catch:{ JSONException -> 0x11c7 }
            goto L_0x11aa
        L_0x119f:
            boolean r7 = r4.has(r5)     // Catch:{ JSONException -> 0x11c7 }
            if (r7 == 0) goto L_0x11aa
            org.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x11c7 }
            r8 = r5
        L_0x11aa:
            java.lang.String r5 = meshsdk.util.MeshConstants.TRACE_ID_GROUP     // Catch:{ JSONException -> 0x11c7 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x11c7 }
            r7.<init>()     // Catch:{ JSONException -> 0x11c7 }
            java.lang.String r9 = "addGroupMember params:"
            r7.append(r9)     // Catch:{ JSONException -> 0x11c7 }
            r7.append(r12)     // Catch:{ JSONException -> 0x11c7 }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x11c7 }
            r2.F(r5, r7, r6)     // Catch:{ JSONException -> 0x11c7 }
            r2.t(r8, r1, r3, r11)     // Catch:{ JSONException -> 0x11c7 }
            r16 = r4
            goto L_0x179d
        L_0x11c7:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x11ce
        L_0x11cc:
            r0 = move-exception
            r1 = r0
        L_0x11ce:
            r1.printStackTrace()
            goto L_0x179d
        L_0x11d3:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            java.lang.String r4 = "web->removeGroup"
            meshsdk.MeshLogNew.i(r4)     // Catch:{ JSONException -> 0x120c }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x120c }
            r4.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x120c }
            int r1 = r4.getInt(r1)     // Catch:{ JSONException -> 0x1207 }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x1207 }
            org.json.JSONObject r5 = r5.removeGroup(r1)     // Catch:{ JSONException -> 0x1207 }
            java.lang.String r6 = "删除mesh group成功,设置mesh needUpload为true"
            r2.reportMeshJsonReport(r6)     // Catch:{ JSONException -> 0x1207 }
            meshsdk.datamgr.MeshDataManager r6 = r2.c     // Catch:{ JSONException -> 0x1207 }
            r7 = 1
            r6.setNeedUpload(r7)     // Catch:{ JSONException -> 0x1207 }
            java.lang.String r6 = r5.toString()     // Catch:{ JSONException -> 0x1207 }
            r2.postJsBridgeCallback(r11, r6, r3)     // Catch:{ JSONException -> 0x1207 }
            r6 = 0
            meshsdk.datamgr.MeshDataManager.flagAddGroup = r6     // Catch:{ JSONException -> 0x1207 }
            r16 = r4
            goto L_0x179d
        L_0x1207:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x120e
        L_0x120c:
            r0 = move-exception
            r1 = r0
        L_0x120e:
            r1.printStackTrace()
            goto L_0x179d
        L_0x1213:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x129f }
            r4.<init>((java.lang.String) r12)     // Catch:{ Exception -> 0x129f }
            int r1 = r4.getInt(r1)     // Catch:{ Exception -> 0x129a }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x129a }
            r7.<init>()     // Catch:{ Exception -> 0x129a }
            java.lang.String r8 = "时序addGroup,groupId:"
            r7.append(r8)     // Catch:{ Exception -> 0x129a }
            r7.append(r1)     // Catch:{ Exception -> 0x129a }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x129a }
            meshsdk.MeshLogNew.v(r7)     // Catch:{ Exception -> 0x129a }
            java.lang.String r7 = "normal"
            java.lang.String r8 = "groupType"
            boolean r8 = r4.has(r8)     // Catch:{ Exception -> 0x129a }
            if (r8 == 0) goto L_0x1246
            java.lang.String r8 = "groupType"
            java.lang.String r8 = r4.getString(r8)     // Catch:{ Exception -> 0x129a }
            r7 = r8
        L_0x1246:
            meshsdk.SIGMesh r8 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x129a }
            int r8 = r8.addGroup(r1, r7)     // Catch:{ Exception -> 0x129a }
            r9 = -1
            if (r8 == r9) goto L_0x1284
            r8 = 0
            meshsdk.datamgr.MeshDataManager.flagAddGroup = r8     // Catch:{ Exception -> 0x129a }
            boolean r8 = r4.has(r5)     // Catch:{ Exception -> 0x129a }
            if (r8 == 0) goto L_0x1278
            org.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ Exception -> 0x129a }
            java.lang.String r8 = meshsdk.util.MeshConstants.TRACE_ID_GROUP     // Catch:{ Exception -> 0x129a }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x129a }
            r9.<init>()     // Catch:{ Exception -> 0x129a }
            java.lang.String r10 = "addGroupMember params:"
            r9.append(r10)     // Catch:{ Exception -> 0x129a }
            r9.append(r12)     // Catch:{ Exception -> 0x129a }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x129a }
            r2.F(r8, r9, r6)     // Catch:{ Exception -> 0x129a }
            r2.t(r5, r1, r3, r11)     // Catch:{ Exception -> 0x129a }
            goto L_0x1296
        L_0x1278:
            org.json.JSONObject r5 = meshsdk.BaseResp.generatorSuccessResp()     // Catch:{ Exception -> 0x129a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x129a }
            r2.postJsBridgeCallback(r11, r5, r3)     // Catch:{ Exception -> 0x129a }
            goto L_0x1296
        L_0x1284:
            r5 = 0
            meshsdk.datamgr.MeshDataManager.flagAddGroup = r5     // Catch:{ Exception -> 0x129a }
            r5 = 425(0x1a9, float:5.96E-43)
            java.lang.String r6 = "allocate group address fail or has been full"
            org.json.JSONObject r5 = meshsdk.BaseResp.generatorFailResp(r5, r6)     // Catch:{ Exception -> 0x129a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x129a }
            r2.postJsBridgeCallback(r11, r5, r3)     // Catch:{ Exception -> 0x129a }
        L_0x1296:
            r16 = r4
            goto L_0x179d
        L_0x129a:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x12a1
        L_0x129f:
            r0 = move-exception
            r1 = r0
        L_0x12a1:
            r1.printStackTrace()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "ACTION_ADD_GROUP exception:"
            r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            meshsdk.MeshLog.e(r4)
            goto L_0x179d
        L_0x12be:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            org.json.JSONArray r1 = r1.groups()
            org.json.JSONObject r4 = meshsdk.BaseResp.generatorSuccessResp(r1)
            java.lang.String r4 = r4.toString()
            r2.postJsBridgeCallback(r11, r4, r3)
            goto L_0x179d
        L_0x12d8:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x133a }
            r1.<init>((java.lang.String) r12)     // Catch:{ Exception -> 0x133a }
            r4 = r14
            boolean r5 = r1.has(r7)     // Catch:{ Exception -> 0x1335 }
            if (r5 == 0) goto L_0x12ee
            java.lang.String r5 = r1.getString(r7)     // Catch:{ Exception -> 0x1335 }
            r4 = r5
        L_0x12ee:
            java.lang.String r5 = "modelId"
            int r5 = r1.getInt(r5)     // Catch:{ Exception -> 0x1335 }
            java.lang.String r6 = "value"
            java.lang.Object r6 = r1.get(r6)     // Catch:{ Exception -> 0x1335 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x1335 }
            r7.<init>()     // Catch:{ Exception -> 0x1335 }
            java.lang.String r8 = "web->controlDevice mac:"
            r7.append(r8)     // Catch:{ Exception -> 0x1335 }
            r7.append(r4)     // Catch:{ Exception -> 0x1335 }
            java.lang.String r8 = ",modelId:"
            r7.append(r8)     // Catch:{ Exception -> 0x1335 }
            java.lang.String r8 = meshsdk.util.LDSModel.LdsModelName.modelName(r5)     // Catch:{ Exception -> 0x1335 }
            r7.append(r8)     // Catch:{ Exception -> 0x1335 }
            java.lang.String r8 = ",value:"
            r7.append(r8)     // Catch:{ Exception -> 0x1335 }
            r7.append(r6)     // Catch:{ Exception -> 0x1335 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x1335 }
            meshsdk.MeshLog.i(r7)     // Catch:{ Exception -> 0x1335 }
            meshsdk.SIGMesh r7 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x1335 }
            org.json.JSONObject r7 = r7.controlDevice((java.lang.String) r4, (int) r5, (java.lang.Object) r6)     // Catch:{ Exception -> 0x1335 }
            java.lang.String r8 = r7.toString()     // Catch:{ Exception -> 0x1335 }
            r2.postJsBridgeCallback(r11, r8, r3)     // Catch:{ Exception -> 0x1335 }
            r16 = r1
            goto L_0x179d
        L_0x1335:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x133c
        L_0x133a:
            r0 = move-exception
            r1 = r0
        L_0x133c:
            r1.printStackTrace()
            java.lang.String r4 = r1.getMessage()
            r5 = -1
            org.json.JSONObject r4 = meshsdk.BaseResp.generatorFailResp(r5, r4)
            java.lang.String r4 = r4.toString()
            r2.postJsBridgeCallback(r11, r4, r3)
            goto L_0x179d
        L_0x1351:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            org.json.JSONObject r1 = r1.devices()
            java.lang.String r4 = r1.toString()
            r2.postJsBridgeCallback(r11, r4, r3)
            goto L_0x179d
        L_0x1367:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            java.lang.String r1 = "web->getDeviceStatus"
            meshsdk.MeshLog.i(r1)     // Catch:{ JSONException -> 0x13b7 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ JSONException -> 0x13b7 }
            r1.<init>()     // Catch:{ JSONException -> 0x13b7 }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x13b7 }
            r4.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x13b7 }
            java.lang.String r5 = "devices"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x13b2 }
            if (r5 == 0) goto L_0x139d
            java.lang.String r5 = "devices"
            org.json.JSONArray r5 = r4.getJSONArray(r5)     // Catch:{ JSONException -> 0x13b2 }
            r6 = 0
        L_0x138a:
            int r7 = r5.length()     // Catch:{ JSONException -> 0x13b2 }
            if (r6 >= r7) goto L_0x139d
            java.lang.Object r7 = r5.get(r6)     // Catch:{ JSONException -> 0x13b2 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ JSONException -> 0x13b2 }
            r1.add(r7)     // Catch:{ JSONException -> 0x13b2 }
            int r6 = r6 + 1
            goto L_0x138a
        L_0x139d:
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x13b2 }
            org.json.JSONObject r5 = r5.getDeviceStatus(r1)     // Catch:{ JSONException -> 0x13b2 }
            r23 = r5
            java.lang.String r5 = r23.toString()     // Catch:{ JSONException -> 0x13b2 }
            r2.postJsBridgeCallback(r11, r5, r3)     // Catch:{ JSONException -> 0x13b2 }
            r16 = r4
            goto L_0x179d
        L_0x13b2:
            r0 = move-exception
            r1 = r0
            r16 = r4
            goto L_0x13b9
        L_0x13b7:
            r0 = move-exception
            r1 = r0
        L_0x13b9:
            r1.printStackTrace()
            goto L_0x179d
        L_0x13be:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x13fc }
            r1.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x13fc }
            boolean r4 = r1.has(r7)     // Catch:{ JSONException -> 0x13f7 }
            if (r4 == 0) goto L_0x13f3
            java.lang.String r4 = r1.getString(r7)     // Catch:{ JSONException -> 0x13f7 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x13f7 }
            r5.<init>()     // Catch:{ JSONException -> 0x13f7 }
            java.lang.String r6 = "js invoke 删除设备:"
            r5.append(r6)     // Catch:{ JSONException -> 0x13f7 }
            r5.append(r4)     // Catch:{ JSONException -> 0x13f7 }
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x13f7 }
            meshsdk.MeshLogNew.v(r5)     // Catch:{ JSONException -> 0x13f7 }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x13f7 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$e1 r6 = new com.leedarson.serviceimpl.BleMeshServiceImpl$e1     // Catch:{ JSONException -> 0x13f7 }
            r6.<init>(r11, r3, r4)     // Catch:{ JSONException -> 0x13f7 }
            r7 = 0
            r5.removeDevice(r4, r7, r6)     // Catch:{ JSONException -> 0x13f7 }
        L_0x13f3:
            r16 = r1
            goto L_0x179d
        L_0x13f7:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x13fe
        L_0x13fc:
            r0 = move-exception
            r1 = r0
        L_0x13fe:
            r1.printStackTrace()
            goto L_0x179d
        L_0x1403:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x1429 }
            meshsdk.util.ProcedureCollector.startAddDevicesTime = r4     // Catch:{ Exception -> 0x1429 }
            java.lang.String r1 = meshsdk.util.ProcedureCollector.FUNC_MESH_PROVISION     // Catch:{ Exception -> 0x1429 }
            meshsdk.util.ProcedureCollector.startCollect(r1)     // Catch:{ Exception -> 0x1429 }
            com.leedarson.serviceimpl.strategys.e r4 = new com.leedarson.serviceimpl.strategys.e     // Catch:{ Exception -> 0x1429 }
            r4.<init>()     // Catch:{ Exception -> 0x1429 }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x1429 }
            r5.<init>((java.lang.String) r12)     // Catch:{ Exception -> 0x1429 }
            meshsdk.datamgr.MeshDataManager r7 = r2.c     // Catch:{ Exception -> 0x1429 }
            android.content.Context r10 = r2.g     // Catch:{ Exception -> 0x1429 }
            r6 = r2
            r8 = r11
            r9 = r3
            r4.m(r5, r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x1429 }
            goto L_0x179d
        L_0x1429:
            r0 = move-exception
            r1 = r0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "BleMeshServiceImpl.Action_Add_devices.exception"
            r4.append(r5)
            java.lang.String r5 = r1.toString()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            meshsdk.MeshLog.debugInfo(r4)
            goto L_0x179d
        L_0x1445:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            r2.cancelTimer()     // Catch:{ JSONException -> 0x14b7 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x14b7 }
            r1.<init>((java.lang.String) r12)     // Catch:{ JSONException -> 0x14b7 }
            boolean r4 = r1.has(r7)     // Catch:{ JSONException -> 0x14b2 }
            if (r4 == 0) goto L_0x14ae
            java.lang.String r4 = r1.getString(r7)     // Catch:{ JSONException -> 0x14b2 }
            r5 = 1
            meshsdk.util.ProcedureCollector.setProvisionCollectEnable(r4, r5)     // Catch:{ JSONException -> 0x14b2 }
            r5 = 0
            r6 = r19
            boolean r7 = r1.has(r6)     // Catch:{ JSONException -> 0x14b2 }
            if (r7 == 0) goto L_0x14a2
            java.lang.String r7 = r1.getString(r6)     // Catch:{ JSONException -> 0x14b2 }
            byte[] r7 = com.telink.ble.mesh.core.MeshUtils.p(r7)     // Catch:{ JSONException -> 0x14b2 }
            r5 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x14b2 }
            r7.<init>()     // Catch:{ JSONException -> 0x14b2 }
            java.lang.String r8 = "OOBData:"
            r7.append(r8)     // Catch:{ JSONException -> 0x14b2 }
            java.lang.String r6 = r1.getString(r6)     // Catch:{ JSONException -> 0x14b2 }
            r7.append(r6)     // Catch:{ JSONException -> 0x14b2 }
            java.lang.String r6 = r7.toString()     // Catch:{ JSONException -> 0x14b2 }
            meshsdk.MeshLog.d(r6)     // Catch:{ JSONException -> 0x14b2 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x14b2 }
            r6.<init>()     // Catch:{ JSONException -> 0x14b2 }
            java.lang.String r7 = "OOBDataBytes:"
            r6.append(r7)     // Catch:{ JSONException -> 0x14b2 }
            java.lang.String r7 = org.spongycastle.pqc.math.linearalgebra.ByteUtils.e(r5)     // Catch:{ JSONException -> 0x14b2 }
            r6.append(r7)     // Catch:{ JSONException -> 0x14b2 }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x14b2 }
            meshsdk.MeshLog.d(r6)     // Catch:{ JSONException -> 0x14b2 }
        L_0x14a2:
            meshsdk.SIGMesh r6 = meshsdk.SIGMesh.getInstance()     // Catch:{ JSONException -> 0x14b2 }
            com.leedarson.serviceimpl.BleMeshServiceImpl$d1 r7 = new com.leedarson.serviceimpl.BleMeshServiceImpl$d1     // Catch:{ JSONException -> 0x14b2 }
            r7.<init>(r11, r3, r4)     // Catch:{ JSONException -> 0x14b2 }
            r6.addDevice(r4, r5, r7)     // Catch:{ JSONException -> 0x14b2 }
        L_0x14ae:
            r16 = r1
            goto L_0x179d
        L_0x14b2:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x14b9
        L_0x14b7:
            r0 = move-exception
            r1 = r0
        L_0x14b9:
            r1.printStackTrace()
            goto L_0x179d
        L_0x14be:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            com.telink.ble.mesh.foundation.MeshService r1 = com.telink.ble.mesh.foundation.MeshService.k()
            com.telink.ble.mesh.foundation.MeshController$Mode r1 = r1.f()
            com.telink.ble.mesh.foundation.MeshController$Mode r4 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_BIND
            if (r1 == r4) goto L_0x14fc
            com.telink.ble.mesh.foundation.MeshService r1 = com.telink.ble.mesh.foundation.MeshService.k()
            com.telink.ble.mesh.foundation.MeshController$Mode r1 = r1.f()
            com.telink.ble.mesh.foundation.MeshController$Mode r4 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_PROVISION
            if (r1 != r4) goto L_0x14dc
            goto L_0x14fc
        L_0x14dc:
            java.lang.String r1 = "web->blemesh stopscan"
            meshsdk.MeshScanLog.d(r1)
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            java.lang.String r4 = "web->blemesh stopscan"
            org.json.JSONObject r23 = r1.stopScan(r4)
            java.lang.String r1 = "web->blemesh stopScan autoConnect request"
            com.leedarson.serviceimpl.reporters.c.f(r1)
            r2.deviceConnect()
            java.lang.String r1 = r23.toString()
            r2.postJsBridgeCallback(r11, r1, r3)
            goto L_0x179d
        L_0x14fc:
            java.lang.String r1 = "web->blemesh stopscan 当前主controll正在bind 或者 provision,不能stopScan"
            meshsdk.MeshScanLog.d(r1)
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.String r4 = "code"
            r5 = -1
            r1.put((java.lang.String) r4, (int) r5)     // Catch:{ JSONException -> 0x1514 }
            java.lang.String r4 = "data"
            java.lang.String r5 = "current mode is binding or provisioning"
            r1.put((java.lang.String) r4, (java.lang.Object) r5)     // Catch:{ JSONException -> 0x1514 }
            goto L_0x1519
        L_0x1514:
            r0 = move-exception
            r4 = r0
            r4.printStackTrace()
        L_0x1519:
            java.lang.String r4 = r23.toString()
            r2.postJsBridgeCallback(r11, r4, r3)
            return
        L_0x1521:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            r2.cancelTimer()
            java.lang.String r1 = "BleMeshServiceImpl web -> start scan"
            meshsdk.MeshScanLog.d(r1)
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            com.leedarson.serviceimpl.BleMeshServiceImpl$c1 r4 = new com.leedarson.serviceimpl.BleMeshServiceImpl$c1
            r4.<init>()
            org.json.JSONObject r23 = r1.startScan(r4)
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r4 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r5 = r23.toString()
            r4.<init>(r11, r5)
            r1.l(r4)
            goto L_0x179d
        L_0x154d:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x15a7 }
            r1.<init>((java.lang.String) r12)     // Catch:{ Exception -> 0x15a7 }
            java.lang.String r4 = "dataVersion"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ Exception -> 0x15a2 }
            java.lang.String r5 = "url"
            java.lang.String r5 = r1.optString(r5)     // Catch:{ Exception -> 0x15a2 }
            java.lang.String r6 = "houseId"
            java.lang.String r6 = r1.getString(r6)     // Catch:{ Exception -> 0x15a2 }
            java.util.HashMap r7 = new java.util.HashMap     // Catch:{ Exception -> 0x15a2 }
            r7.<init>()     // Catch:{ Exception -> 0x15a2 }
            java.lang.String r8 = "cause"
            java.lang.String r9 = "ACTION_UPDATE_MESH_DATA_BY_MQTT"
            r7.put(r8, r9)     // Catch:{ Exception -> 0x15a2 }
            java.lang.String r8 = "configData"
            r7.put(r8, r1)     // Catch:{ Exception -> 0x15a2 }
            com.leedarson.serviceimpl.elkstrays.a.a(r7)     // Catch:{ Exception -> 0x15a2 }
            java.lang.String r8 = "收到web 更新meshjson的通知"
            r2.reportMeshJsonReport(r8)     // Catch:{ Exception -> 0x15a2 }
            meshsdk.datamgr.MeshDataManager r8 = r2.c     // Catch:{ Exception -> 0x15a2 }
            r9 = 0
            boolean r8 = r8.update(r11, r6, r9)     // Catch:{ Exception -> 0x15a2 }
            if (r8 == 0) goto L_0x159e
            boolean r9 = r2.k     // Catch:{ Exception -> 0x15a2 }
            if (r9 != 0) goto L_0x159e
            r9 = 1
            r2.k = r9     // Catch:{ Exception -> 0x15a2 }
            java.lang.String r9 = "web->updateMeshData autoConnect request"
            com.leedarson.serviceimpl.reporters.c.f(r9)     // Catch:{ Exception -> 0x15a2 }
            meshsdk.SIGMesh r9 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x15a2 }
            r9.autoConnect()     // Catch:{ Exception -> 0x15a2 }
        L_0x159e:
            r16 = r1
            goto L_0x179d
        L_0x15a2:
            r0 = move-exception
            r16 = r1
            r1 = r0
            goto L_0x15a9
        L_0x15a7:
            r0 = move-exception
            r1 = r0
        L_0x15a9:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "{\"err\":\"update mesh data exception:"
            r4.append(r5)
            java.lang.String r5 = r1.getMessage()
            r4.append(r5)
            java.lang.String r5 = "\"}"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "update mesh data exception:"
            r5.append(r6)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            r6 = r38
            r2.postJsBridgeCallback(r11, r5, r6)
            org.greenrobot.eventbus.c r5 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r6 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r7 = "mesh config download url is null "
            r8 = -1
            org.json.JSONObject r7 = meshsdk.BaseResp.generatorFailResp(r8, r7)
            java.lang.String r7 = r7.toString()
            r6.<init>(r11, r7)
            r5.l(r6)
            r1.printStackTrace()
            goto L_0x179d
        L_0x15f5:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x15fc:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            long r4 = java.lang.System.currentTimeMillis()
            long r6 = r2.l
            long r4 = r4 - r6
            r6 = 3000(0xbb8, double:1.482E-320)
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 >= 0) goto L_0x160f
            return
        L_0x160f:
            long r4 = java.lang.System.currentTimeMillis()
            r2.l = r4
            android.content.Context r1 = r2.g
            android.content.Context r1 = r1.getApplicationContext()
            java.lang.String r1 = meshsdk.util.SharedPreferenceHelper.getHouseId(r1)
            android.content.Context r4 = r2.g
            java.lang.String r4 = meshsdk.util.SharedPreferenceHelper.getLastHouseId(r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "getMeshData,houseId:"
            r5.append(r6)
            r5.append(r1)
            java.lang.String r6 = ",lastHouseId:"
            r5.append(r6)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            meshsdk.MeshLog.d(r5)
            android.content.Context r5 = r2.g
            boolean r5 = meshsdk.util.SharedPreferenceHelper.isNeedUpload(r5)
            if (r5 == 0) goto L_0x1676
            boolean r6 = android.text.TextUtils.isEmpty(r4)
            if (r6 != 0) goto L_0x1676
            boolean r6 = r1.equals(r4)
            if (r6 == 0) goto L_0x1656
            goto L_0x1676
        L_0x1656:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "切换家前上传最后的mesh配置:target houseId:"
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            meshsdk.MeshLog.d(r6)
            meshsdk.datamgr.MeshDataManager r6 = r2.c
            com.leedarson.serviceimpl.BleMeshServiceImpl$b1 r7 = new com.leedarson.serviceimpl.BleMeshServiceImpl$b1
            r7.<init>(r11, r3)
            r6.upload(r4, r7)
            goto L_0x179d
        L_0x1676:
            r2.v(r11, r3)
            return
        L_0x167a:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            com.leedarson.serviceimpl.BleMeshServiceImpl$a1 r4 = new com.leedarson.serviceimpl.BleMeshServiceImpl$a1
            r4.<init>(r11)
            r1.executorTask(r4)
            goto L_0x179d
        L_0x168d:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            meshsdk.datamgr.MeshDataManager r1 = r2.c
            r1.stopUpTimer()
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            r4 = r37
            r1.disconnectAndIdle(r4)
            org.json.JSONObject r1 = meshsdk.BaseResp.generatorSuccessResp()
            java.lang.String r1 = r1.toString()
            r2.postJsBridgeCallback(r11, r1, r3)
            goto L_0x179d
        L_0x16ad:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            boolean r1 = meshsdk.util.BleCompat.neededPermissionPermanentlyDenied(r24)
            r2.s = r1
            java.lang.String r1 = "收到js->invoke connectMeshNetwork，连接至mesh网络请求调用"
            com.leedarson.serviceimpl.elkstrays.b.a(r1)
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            java.lang.String r4 = "bleMeshConnect"
            boolean r1 = r1.checkPermission(r4)
            if (r1 != 0) goto L_0x16d9
            r1 = 401(0x191, float:5.62E-43)
            java.lang.String r4 = meshsdk.BaseResp.DESC_NO_PERMISSION
            org.json.JSONObject r1 = meshsdk.BaseResp.generatorFailResp(r1, r4)
            java.lang.String r1 = r1.toString()
            r2.postJsBridgeCallback(r11, r1, r3)
            return
        L_0x16d9:
            r1 = 1
            r2.k = r1
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            boolean r1 = r1.hasConnected()
            if (r1 == 0) goto L_0x171b
            java.lang.String r1 = "mesh network hasConnected,no need to execute autoConnect"
            meshsdk.MeshLog.d(r1)
            java.lang.String r1 = "mesh网络已成功, 发送0xffff唤醒其它所有节点上线"
            com.leedarson.serviceimpl.elkstrays.b.a(r1)
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            meshsdk.model.MeshInfo r1 = r1.getMeshInfo()
            int r1 = r1.getOnlineCountInAll()
            meshsdk.SIGMesh r4 = meshsdk.SIGMesh.getInstance()
            meshsdk.model.MeshInfo r4 = r4.getMeshInfo()
            int r4 = r4.getDefaultAppKeyIndex()
            r5 = 65535(0xffff, float:9.1834E-41)
            com.telink.ble.mesh.core.message.generic.OnOffGetMessage r5 = com.telink.ble.mesh.core.message.generic.OnOffGetMessage.I(r5, r4, r1)
            meshsdk.ctrl.MeshMessagePool r6 = meshsdk.ctrl.MeshMessagePool.getInstance()
            r6.addAndSend(r5)
            r6 = 1
            r2.onNetworkStatusChange(r6)
            goto L_0x173f
        L_0x171b:
            com.telink.ble.mesh.foundation.MeshService r1 = com.telink.ble.mesh.foundation.MeshService.k()
            com.telink.ble.mesh.foundation.MeshController$Mode r1 = r1.f()
            com.telink.ble.mesh.foundation.MeshController$Mode r4 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_AUTO_CONNECT
            if (r1 != r4) goto L_0x1738
            java.lang.String r1 = "当前已经在auto连接组网中...."
            com.leedarson.serviceimpl.elkstrays.b.a(r1)
            org.json.JSONObject r1 = meshsdk.BaseResp.generatorSuccessResp()
            java.lang.String r1 = r1.toString()
            r2.postJsBridgeCallback(r11, r1, r3)
            return
        L_0x1738:
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()
            r1.autoConnect()
        L_0x173f:
            org.json.JSONObject r1 = meshsdk.BaseResp.generatorSuccessResp()
            java.lang.String r1 = r1.toString()
            r2.postJsBridgeCallback(r11, r1, r3)
            goto L_0x179d
        L_0x174c:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            com.telink.ble.mesh.foundation.MeshService r1 = com.telink.ble.mesh.foundation.MeshService.k()
            java.lang.String r4 = "ACTION_TEST_IDLE"
            r5 = 0
            r1.n(r5, r4)
            goto L_0x179d
        L_0x175c:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            r2.test()
            goto L_0x179d
        L_0x1765:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            goto L_0x179d
        L_0x176b:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            meshsdk.datamgr.MeshDataManager r1 = r2.c
            org.json.JSONObject r1 = r1.getConnectState()
            java.lang.String r4 = r1.toString()
            r5 = r36
            r2.postJsCallH5ByNative(r5, r4)
            goto L_0x179d
        L_0x1780:
            r3 = r8
            r24 = r9
            r12 = r10
            r11 = r15
            android.content.Context r1 = r2.g
            android.content.Context r1 = r1.getApplicationContext()
            java.lang.String r1 = meshsdk.util.SharedPreferenceHelper.getHouseId(r1)
            meshsdk.datamgr.LDSDeviceApi r4 = new meshsdk.datamgr.LDSDeviceApi
            r4.<init>()
            com.leedarson.serviceimpl.BleMeshServiceImpl$r0 r5 = new com.leedarson.serviceimpl.BleMeshServiceImpl$r0
            r5.<init>()
            r4.getCloudDevices(r1, r5)
        L_0x179d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.BleMeshServiceImpl.a(android.app.Activity, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public class r0 extends OnHttpCallback<List<CloudDevice>> {
        public static ChangeQuickRedirect changeQuickRedirect;

        r0() {
        }

        public /* bridge */ /* synthetic */ void onResult(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5843, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onResult((List<CloudDevice>) (List) obj);
            }
        }

        public void onResult(List<CloudDevice> list) {
        }
    }

    public class a1 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        a1(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5865, new Class[0], Void.TYPE).isSupported) {
                if (SharedPreferenceHelper.isNeedUpload(BleMeshServiceImpl.this.g)) {
                    try {
                        MeshLog.d("wait for uploading mesh json file,then clear data ,semaphore.acquire");
                        BleMeshServiceImpl.this.n.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                BleMeshServiceImpl.this.c.clear(this.c);
            }
        }
    }

    public class b1 implements MeshDataManager.OnUploadCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        b1(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(String fillId) {
        }

        public void onFail(String errMsg) {
        }

        public void onComplete() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5877, new Class[0], Void.TYPE).isSupported) {
                SharedPreferenceHelper.setLastHouseId(BleMeshServiceImpl.this.g, "");
                BleMeshServiceImpl.n(BleMeshServiceImpl.this, this.a, this.b);
                if (BleMeshServiceImpl.this.n != null && BleMeshServiceImpl.this.n.hasQueuedThreads()) {
                    MeshLog.e("semaphore.release");
                    BleMeshServiceImpl.this.n.release();
                }
            }
        }
    }

    public class c1 extends MeshScanCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        c1() {
        }

        public void onDeviceFound(NetworkingDevice device, String meshAddress, String mac, String advertisingData) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{device, meshAddress, mac, advertisingData}, this, changeQuickRedirect, false, 5878, new Class[]{NetworkingDevice.class, cls, cls, cls}, Void.TYPE).isSupported) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("meshDeviceId", (Object) meshAddress);
                    if (device != null) {
                        jsonObject.put("protocolVer", device.nodeInfo.protocolVersion);
                        jsonObject.put("rssi", device.rssi);
                    }
                    jsonObject.put("mac", (Object) mac);
                    jsonObject.put("advertisingData", (Object) advertisingData);
                    jsonObject.put("veryLowBattery", device.veryLowBattery);
                    MeshLog.i("BleMesh scan report:" + device.veryLowBattery + ",mac:" + mac);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BleMeshServiceImpl.this.postJsCallH5ByNative(BleMeshService.ON_BROAD_CAST, jsonObject.toString());
            }
        }
    }

    public class d1 implements MeshBindCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        d1(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public void onBindSuccess(String mac, NetworkingDevice dev, String str) {
            Class<String> cls = String.class;
            Class[] clsArr = {cls, NetworkingDevice.class, cls};
            if (!PatchProxy.proxy(new Object[]{mac, dev, str}, this, changeQuickRedirect, false, 5879, clsArr, Void.TYPE).isSupported) {
                SIGMesh.getInstance().setLastDirectMac(mac);
                MeshLogNew.v("添加设备成功，将新加的设备:" + mac + "--> 设置为最近的直连mac设备");
                BleMeshServiceImpl.this.c.setNeedUpload(true);
                Map<String, Object> map = new HashMap<>();
                map.put("code", 200);
                map.put("nodeAddress", Integer.valueOf(dev.nodeInfo.meshAddress));
                map.put("macAddress", mac);
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.map2Json(map).toString(), this.b);
                BleMeshServiceImpl.this.onDeviceOnlineChange(mac, 1);
                com.leedarson.serviceimpl.reporters.c.f("single device addSuccess, autoConnect request");
                SIGMesh.getInstance().autoConnect();
                ProcedureCollector.endCollectAndClear(mac, "");
                ProcedureCollector.addAndReportELK(BaseResp.map2Json(map).toString(), "mesh", Thread.currentThread().getId(), ProcedureCollector.FUNC_MESH_PROVISION);
            }
        }

        public void onBindFail(int code, String msg) {
            Object[] objArr = {new Integer(code), msg};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5880, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
                ProcedureCollector.endCollectThenReport(this.c, "");
            }
        }
    }

    public class e1 implements MeshUnbindCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        e1(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public void onUnBindSuccess(String mac, int i, boolean realSuccess) {
            if (!PatchProxy.proxy(new Object[]{mac, new Integer(i), new Byte(realSuccess ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5881, new Class[]{String.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                if (mac.equals(SIGMesh.getInstance().getLastDirectMac())) {
                    MeshLogNew.v("删除 主 设备成功，重置最近主节点为null");
                    SIGMesh.getInstance().setLastDirectMac((String) null);
                }
                BleMeshServiceImpl.this.reportMeshJsonReport("删除设备成功，设置mesh needUpload为true");
                BleMeshServiceImpl.this.c.setNeedUpload(true);
                MeshLogNew.v("resposne js 删除设备成功:" + BaseResp.generatorSuccessResp(Boolean.valueOf(realSuccess)).toString());
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(Boolean.valueOf(realSuccess)).toString(), this.b);
            }
        }

        public void onUnBindFail(int i, String msg) {
            Object[] objArr = {new Integer(i), msg};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5882, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                if (this.c.equals(SIGMesh.getInstance().getLastDirectMac())) {
                    MeshLogNew.v("删除 主 节点失败【未在meshjson中找到】，重置最近主节点为null");
                    SIGMesh.getInstance().setLastDirectMac((String) null);
                }
                MeshLogNew.v("js invoke 删除设备失败【未在meshjson中找到】:" + BaseResp.generatorSuccessResp(msg).toString());
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(msg).toString(), this.b);
            }
        }
    }

    public class a extends MeshSceneCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;
        final /* synthetic */ String d;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(SIGMesh sigMesh, String callbackkey, String str, String str2, int i, String str3) {
            super(sigMesh, callbackkey);
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = str3;
        }

        public void onSuccess(int sceneId, Scene scene, int i) {
            Object[] objArr = {new Integer(sceneId), scene, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5780, new Class[]{cls, Scene.class, cls}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.reportMeshJsonReport("添加mesh scene rule 成功，设置mesh needUpload为true");
                BleMeshServiceImpl.this.c.setNeedUpload(true);
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackkey(), BaseResp.generatorSuccessResp().toString(), this.a);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SCENE;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "addSceneAction success mac:" + this.b + ",sceneId:" + sceneId, FirebaseAnalytics.Param.SUCCESS);
            }
        }

        public void onFail(int code, String msg, Scene scene, int i) {
            Object[] objArr = {new Integer(code), msg, scene, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5781, new Class[]{cls, String.class, Scene.class, cls}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackkey(), BaseResp.generatorFailResp(code, msg).toString(), this.a);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SCENE;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "addSceneAction fail mac:" + this.b + ",sceneId:" + this.c + this.d, "failure");
            }
        }
    }

    public class b extends MeshSceneCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(SIGMesh sigMesh, String callbackkey, String str, String str2, int i) {
            super(sigMesh, callbackkey);
            this.a = str;
            this.b = str2;
            this.c = i;
        }

        public void onSuccess(int sceneId, Scene scene, int i) {
            Object[] objArr = {new Integer(sceneId), scene, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5782, new Class[]{cls, Scene.class, cls}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.reportMeshJsonReport("删除 mesh scene rule 成功，设置mesh needUpload为true");
                BleMeshServiceImpl.this.c.setNeedUpload(true);
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackkey(), BaseResp.generatorSuccessResp().toString(), this.a);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SCENE;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "removeSceneAction success mac:" + this.b + ",sceneId:" + sceneId, FirebaseAnalytics.Param.SUCCESS);
            }
        }

        public void onFail(int code, String msg, Scene scene, int i) {
            Object[] objArr = {new Integer(code), msg, scene, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5783, new Class[]{cls, String.class, Scene.class, cls}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackkey(), BaseResp.generatorFailResp(code, msg).toString(), this.a);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SCENE;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "removeSceneAction fail mac:" + this.b + ",sceneId:" + this.c, "failure");
            }
        }
    }

    public class c implements MeshOTACallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Activity a;
        final /* synthetic */ String b;

        c(Activity activity, String str) {
            this.a = activity;
            this.b = str;
        }

        public void onSuccess(String mac) {
            if (!PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 5784, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.reporters.ota.b.b().e(a.b.CODE_SUCCESS, "", mac);
                MeshDataManager.otaSuccessList.add(mac);
                MeshLogNew.ota("native->web ota success mac:" + mac);
                MeshDataManager.startOTATimespan = 0;
                BleMeshServiceImpl.G(this.a, false);
                MeshLog.i("设备:" + mac + " ota成功，清除本地缓存的版本号");
                LDSMeshUtil.setMeshHWVersion(SIGMesh.getInstance().getMeshInfo().meshUUID, mac, "");
                BleMeshServiceImpl.this.b.progress = 100;
                BleMeshServiceImpl.this.b.desc = "";
                BleMeshServiceImpl.this.b.mac = mac;
                BleMeshServiceImpl.this.b.stage = 5;
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                bleMeshServiceImpl.postJsCallH5ByNative(BleMeshService.ON_OTA_PROGRESS_CHANGE, bleMeshServiceImpl.b.parseJsonStr());
                BleMeshServiceImpl.this.postJsCallH5ByNative(this.b, BaseResp.generatorSuccessResp().toString());
                String str = ProcedureCollector.FUNC_Mesh_OTA;
                ProcedureCollector.endCollectAndClear(str);
                ProcedureCollector.addAndReportELK(BleMeshServiceImpl.this.b.parseJsonStr(), "mesh", Thread.currentThread().getId(), str);
            }
        }

        public void onFail(int code, String msg, String mac) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), msg, mac};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5785, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                a.b cod = a.b.CODE_FAIL_DOWNLOAD;
                if (code == 418) {
                    cod = a.b.CODE_FAIL_DISCONNECT;
                } else if (code == -1) {
                    cod = a.b.CODE_FAIL_DOWNLOAD;
                } else if (code == 422) {
                    cod = a.b.CODE_FAIL_DOWNLOAD;
                } else if (code == 423) {
                    cod = a.b.CODE_FAIL_CONNECT;
                }
                com.leedarson.serviceimpl.reporters.ota.b.b().e(cod, msg, mac);
                MeshDataManager.startOTATimespan = 0;
                MeshLogNew.otaWarn("native->web ota fail mac:" + mac + ",msg:" + msg + ",code:" + code);
                BleMeshServiceImpl.G(this.a, false);
                BleMeshServiceImpl.this.b.progress = -1;
                BleMeshServiceImpl.this.b.desc = msg;
                BleMeshServiceImpl.this.b.mac = mac;
                BleMeshServiceImpl.this.b.stage = 6;
                BleMeshServiceImpl.this.b.countdown = 0;
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                bleMeshServiceImpl.postJsCallH5ByNative(BleMeshService.ON_OTA_PROGRESS_CHANGE, bleMeshServiceImpl.b.parseJsonStr());
                BleMeshServiceImpl.this.postJsCallH5ByNative(this.b, BaseResp.generatorFailResp(code, msg).toString());
                ProcedureCollector.endCollectThenReport(ProcedureCollector.FUNC_Mesh_OTA);
            }
        }

        public void onProgress(int progress, String mac, int countdown_s) {
            Object[] objArr = {new Integer(progress), mac, new Integer(countdown_s)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5786, new Class[]{cls, String.class, cls}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.b.progress = progress;
                BleMeshServiceImpl.this.b.desc = "";
                BleMeshServiceImpl.this.b.mac = mac;
                BleMeshServiceImpl.this.b.stage = 4;
                BleMeshServiceImpl.this.b.countdown = countdown_s;
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                bleMeshServiceImpl.postJsCallH5ByNative(BleMeshService.ON_OTA_PROGRESS_CHANGE, bleMeshServiceImpl.b.parseJsonStr());
            }
        }

        public void onStage(int state) {
        }
    }

    public class d extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ RoutineRule c;

        d(String str, String str2, RoutineRule routineRule) {
            this.a = str;
            this.b = str2;
            this.c = routineRule;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5787, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.reportMeshJsonReport("设置 mesh smart rule 成功，设置mesh needUpload为true");
                BleMeshServiceImpl.this.c.setNeedUpload(true);
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SMART;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "setSmartRule success mac:" + this.c.mac + ",smartId:" + this.c.smartId, FirebaseAnalytics.Param.SUCCESS);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5788, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SMART;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "setSmartRule fail mac:" + this.c.mac + ",smartId:" + this.c.smartId, "failure");
            }
        }
    }

    public class e extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;
        final /* synthetic */ int d;

        e(String str, String str2, String str3, int i) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = i;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5789, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.reportMeshJsonReport("删除 mesh smart rule 成功，设置mesh needUpload为true");
                BleMeshServiceImpl.this.c.setNeedUpload(true);
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SMART;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "removeSmartRule success mac:" + this.c + ",smartId:" + this.d, FirebaseAnalytics.Param.SUCCESS);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5790, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
                BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                String str = MeshConstants.TRACE_ID_SMART;
                BleMeshServiceImpl.j(bleMeshServiceImpl, str, "removeSmartRule success mac:" + this.c + ",smartId:" + this.d, "failure");
            }
        }
    }

    public class f extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        f(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5791, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.reportMeshJsonReport("设置 mesh smart rule enable 成功，设置mesh needUpload为true");
                BleMeshServiceImpl.this.c.setNeedUpload(true);
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5792, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class g extends MeshSimpleCmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(String mac, String str, String str2, String str3) {
            super(mac);
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5793, new Class[]{Object.class}, Void.TYPE).isSupported) {
                MeshLogNew.i("getVersion onSuccess mac:" + this.a + ",data:" + data);
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorSuccessResp((JSONObject) data).toString(), this.c);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5794, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorFailResp(code, msg).toString(), this.c);
            }
        }
    }

    public class h extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        h(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5795, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(data).toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5796, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class i extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void onSuccess(Object data) {
        }

        public void onFail(int code, String msg, Object data) {
        }
    }

    public class j extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        j(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5797, new Class[]{Object.class}, Void.TYPE).isSupported) {
                int colorMode = ((Integer) data).intValue();
                JSONObject object = new JSONObject();
                try {
                    object.put("colorMode", colorMode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(object).toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5798, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class l extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        l(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5804, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5805, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class m extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        m(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5806, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5807, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class n extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        n(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5808, new Class[]{Object.class}, Void.TYPE).isSupported) {
                MeshLogNew.e("getEffectMode onsuccess,data:" + data);
                try {
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(new JSONObject(new Gson().toJson((Object) (EffectModeConfig) data))).toString(), this.b);
                } catch (JSONException e) {
                    e.printStackTrace();
                    MeshLogNew.e("getEffectMode exception:" + e.getMessage() + ",data:" + data);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5809, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class o extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        o(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            int i = 1;
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5810, new Class[]{Object.class}, Void.TYPE).isSupported) {
                int value = ((Integer) data).intValue();
                JSONObject object = new JSONObject();
                if (value == 255) {
                    i = 0;
                }
                try {
                    object.put("status", i);
                    object.put("enable", value);
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(object).toString(), this.b);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5811, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class p extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        p(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5812, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5813, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class q extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ JSONObject a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        q(JSONObject jSONObject, String str, String str2) {
            this.a = jSONObject;
            this.b = str;
            this.c = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5814, new Class[]{Object.class}, Void.TYPE).isSupported) {
                if (data instanceof int[]) {
                    int[] arr = (int[]) data;
                    try {
                        this.a.put("support", arr[0]);
                        this.a.put("status", arr[1]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorSuccessResp(this.a).toString(), this.c);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5815, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorFailResp(code, msg).toString(), this.c);
            }
        }
    }

    public class r extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        r(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5816, new Class[]{Object.class}, Void.TYPE).isSupported) {
                if (data instanceof int[]) {
                    int[] arr = (int[]) data;
                    JSONObject object = new JSONObject();
                    try {
                        object.put("action", arr[0]);
                        object.put("groupAddr", arr[1]);
                        GroupInfo group = LDSMeshUtil.findGroupByAddress(SIGMesh.getInstance().getMeshInfo().groups, arr[1]);
                        object.put("groupId", (Object) group == null ? "" : String.valueOf(group.groupId));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(object).toString(), this.b);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5817, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class s extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        s(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5818, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5819, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class t extends MeshSimpleCmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        t(String mac, String str, String str2, String str3) {
            super(mac);
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5820, new Class[]{Object.class}, Void.TYPE).isSupported) {
                if (data instanceof BatteryPropertyBean) {
                    BatteryPropertyBean batteryPropertyBean = (BatteryPropertyBean) data;
                    int battery = batteryPropertyBean.getBattery();
                    int chargeState = batteryPropertyBean.getChargeState();
                    int acState = batteryPropertyBean.getAcState();
                    JSONObject object = new JSONObject();
                    try {
                        object.put("battery", battery);
                        if (chargeState != -1) {
                            object.put("chargeState", chargeState);
                        }
                        object.put("acState", acState);
                        object.put("mac", (Object) this.a);
                        object.put("desc", (Object) batteryPropertyBean.isCache() ? "从缓存中读取" : "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorSuccessResp(object).toString(), this.c);
                    MeshLog.i("getBattery onSuccess mac" + this.a + ",battery:" + battery + ",chargeState:" + chargeState + ",acState:" + acState);
                    com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(BleMeshServiceImpl.this).t("LdsBleMesh");
                    StringBuilder sb = new StringBuilder();
                    sb.append("设备电量查询:mac");
                    sb.append(this.a);
                    sb.append(",battery:");
                    sb.append(battery);
                    sb.append(",detail:");
                    sb.append(object.toString());
                    t.p(sb.toString()).o("info").x(MeshConstants.TRACE_ID_LOW_POWER).a().b();
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5821, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorFailResp(code, msg).toString(), this.c);
            }
        }
    }

    public class u extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        u(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5822, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(data).toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5823, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class w extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        w(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5825, new Class[]{Object.class}, Void.TYPE).isSupported) {
                int onOff = ((Integer) data).intValue();
                JSONObject object = new JSONObject();
                try {
                    object.put("onOff", onOff);
                    object.put("mac", (Object) this.a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorSuccessResp(object).toString(), this.c);
                MeshLog.e("单头灯报警设置查询:mac" + this.a + ",onOff:" + onOff);
                com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(BleMeshServiceImpl.this).t("LdsBleMesh");
                t.p("单头灯报警设置查询:mac" + this.a + ",onOff:" + onOff).o("info").x(MeshConstants.TRACE_ID_LOW_POWER).a().b();
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5826, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorFailResp(code, msg).toString(), this.c);
            }
        }
    }

    public class x extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        x(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5827, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5828, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class y extends TypeToken<DetectMode> {
        y() {
        }
    }

    public class z extends MeshSimpleCmdSetCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        z(String callbackKey, String str) {
            super(callbackKey);
            this.a = str;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5829, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorSuccessResp().toString(), this.a);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5830, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.a);
            }
        }
    }

    public class a0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a0(String callbackKey, int i, String str, String str2) {
            super(callbackKey);
            this.a = i;
            this.b = str;
            this.c = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5831, new Class[]{Object.class}, Void.TYPE).isSupported) {
                if (data instanceof DetectMode) {
                    DetectionModeDetailCacheInstance.getInstance().removeCacheHandlerMsg(CacheHandler.WHAT_GET_DETECTION_MDOE_DETAIL);
                    DetectMode detectMode = (DetectMode) data;
                    JSONObject object = detectMode.toJson();
                    if (object == null) {
                        a.b g = timber.log.a.g(BleMeshServiceImpl.this.a);
                        g.m("getDetectionMode mode:" + this.a + ",onSuccess fail", new Object[0]);
                        BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(-1, "detect mode gson to json err").toString(), this.b);
                        return;
                    }
                    a.b g2 = timber.log.a.g(BleMeshServiceImpl.this.a);
                    g2.m("getDetectionMode mode:" + this.a + ",onSuccess, duration:" + detectMode.duration + ",callback:" + this, new Object[0]);
                    BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorSuccessResp(object).toString(), this.b);
                    NodeInfo node = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, this.c);
                    if (node != null) {
                        DetectionModeDetailCacheInstance instance = DetectionModeDetailCacheInstance.getInstance();
                        instance.put(this.c + "_" + node.meshAddress + "_getDetectionMode", detectMode);
                    }
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5832, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(BleMeshServiceImpl.this.a);
                g.m("getDetectionMode onFail mode:" + this.a + ",callback:" + this, new Object[0]);
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class b0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b0(String callbackKey, String str, String str2) {
            super(callbackKey);
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5833, new Class[]{Object.class}, Void.TYPE).isSupported) {
                CurrentDetectionModeCacheInstance.getInstance().removeCacheHandlerMsg(CacheHandler.WHAT_GET_CURRENT_DETECTION_MODE);
                int mode = 0;
                JSONObject object = new JSONObject();
                try {
                    mode = ((Integer) data).intValue();
                    object.put("mode", mode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorSuccessResp(object).toString(), this.a);
                NodeInfo node = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, this.b);
                if (node != null) {
                    CurrentDetectionModeCacheInstance instance = CurrentDetectionModeCacheInstance.getInstance();
                    instance.put(this.b + "_" + node.meshAddress + "_getCurrentDetectionMode", Integer.valueOf(mode));
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5834, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.a);
            }
        }
    }

    public class c0 extends MeshSimpleCmdSetCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c0(String callbackKey, String str) {
            super(callbackKey);
            this.a = str;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5835, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorSuccessResp().toString(), this.a);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5836, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.a);
            }
        }
    }

    public class d0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        d0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5837, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5838, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class e0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        e0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5839, new Class[]{Object.class}, Void.TYPE).isSupported) {
                int duration = ((Integer) data).intValue();
                JSONObject object = new JSONObject();
                try {
                    object.put(TypedValues.TransitionType.S_DURATION, duration);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(object).toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5840, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class f0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        f0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5841, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5842, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class h0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        h0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5844, new Class[]{Object.class}, Void.TYPE).isSupported) {
                int status = ((Integer) data).intValue();
                JSONObject object = new JSONObject();
                try {
                    object.put("status", status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(object).toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5845, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class i0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        i0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5846, new Class[]{Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5847, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class j0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        j0(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5848, new Class[]{Object.class}, Void.TYPE).isSupported) {
                JSONArray array = new JSONArray();
                if (data != null && (data instanceof ArrayList)) {
                    Iterator<Integer> it = ((ArrayList) data).iterator();
                    while (it.hasNext()) {
                        Integer groupAddress = it.next();
                        JSONObject json = new JSONObject();
                        try {
                            GroupInfo group = LDSMeshUtil.findGroupByAddress(SIGMesh.getInstance().getMeshInfo().groups, groupAddress.intValue());
                            json.put("groupId", group != null ? group.groupId : 0);
                            json.put("groupAddress", (Object) groupAddress);
                            array.put((Object) json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (array.length() >= 8) {
                    MeshLog.i("getDeviceGroups success mac:" + this.a + " 超过8个, bindedgroups:" + array + ",尝试去移除无用组");
                    GroupFixHelper.getInstance().removeInvalidateGroupInDevice(this.a, array, new a(array));
                    return;
                }
                MeshLog.i("getDeviceGroups success mac:" + this.a + " bindedgroups:" + array);
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorSuccessResp(array).toString(), this.c);
            }
        }

        public class a extends MeshCustomcmdCallback {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ JSONArray a;

            a(JSONArray jSONArray) {
                this.a = jSONArray;
            }

            public void onSuccess(Object data) {
                if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5850, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    try {
                        if (data instanceof JSONArray) {
                            JSONArray completeRemoveMemberArray = (JSONArray) data;
                            boolean quit = false;
                            int i = 0;
                            while (true) {
                                if (i >= completeRemoveMemberArray.length()) {
                                    break;
                                }
                                JSONObject obj = completeRemoveMemberArray.getJSONObject(i);
                                if (obj.getInt("code") == 200) {
                                    int groupAddress = obj.getInt("groupAddress");
                                    int j = 0;
                                    while (true) {
                                        if (i >= this.a.length()) {
                                            break;
                                        } else if (groupAddress == this.a.getJSONObject(j).optInt("groupAddress")) {
                                            quit = true;
                                            MeshLog.i("getDeviceGroups成功，达到8个组了，检测存在无用组，删除成功");
                                            this.a.remove(j);
                                            break;
                                        } else {
                                            j++;
                                        }
                                    }
                                }
                                if (quit) {
                                    break;
                                }
                                i++;
                            }
                        }
                        j0 j0Var = j0.this;
                        BleMeshServiceImpl.this.postJsBridgeCallback(j0Var.b, BaseResp.generatorSuccessResp(this.a).toString(), j0.this.c);
                    } catch (Exception e) {
                        j0 j0Var2 = j0.this;
                        BleMeshServiceImpl.this.postJsBridgeCallback(j0Var2.b, BaseResp.generatorSuccessResp(this.a).toString(), j0.this.c);
                    }
                }
            }

            public void onFail(int i, String str, Object obj) {
                Object[] objArr = {new Integer(i), str, obj};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5851, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                    MeshLog.i("removeInvalidateGroupInDevice onFail...");
                    j0 j0Var = j0.this;
                    BleMeshServiceImpl.this.postJsBridgeCallback(j0Var.b, BaseResp.generatorSuccessResp(this.a).toString(), j0.this.c);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5849, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(this.b, BaseResp.generatorFailResp(code, msg).toString(), this.c);
            }
        }
    }

    public class k0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        k0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5852, new Class[]{Object.class}, Void.TYPE).isSupported) {
                JSONObject state = new JSONObject();
                try {
                    state.put(Constants.ACTION_STATE, data);
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(state).toString(), this.b);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5853, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class l0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        l0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5854, new Class[]{Object.class}, Void.TYPE).isSupported) {
                JSONObject state = new JSONObject();
                try {
                    state.put(FirebaseAnalytics.Param.LEVEL, data);
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(state).toString(), this.b);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5855, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class m0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        m0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5856, new Class[]{Object.class}, Void.TYPE).isSupported) {
                try {
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp().toString(), this.b);
                } catch (Exception e) {
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorFailResp(-1, e.getMessage()).toString(), this.b);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5857, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class n0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        n0(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5858, new Class[]{Object.class}, Void.TYPE).isSupported) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("energy", data);
                    BleMeshServiceImpl.this.postJsBridgeCallback(this.a, BaseResp.generatorSuccessResp(obj).toString(), this.b);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void onFail(int code, String msg, Object obj) {
            Object[] objArr = {new Integer(code), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5859, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.postJsBridgeCallback(getCallbackKey(), BaseResp.generatorFailResp(code, msg).toString(), this.b);
            }
        }
    }

    public class o0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        o0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5860, new Class[0], Void.TYPE).isSupported) {
                MeshDataManager.isFlagAddGroupByHand = false;
                MeshLog.e("建组超时，isFlagAddGroupByHand重置为false");
            }
        }
    }

    public void t(JSONArray jSONArray, int i2, String str, String str2) {
        Class<String> cls = String.class;
        Object[] objArr = {jSONArray, new Integer(i2), str, str2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5715, new Class[]{JSONArray.class, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            int groupId = i2;
            String callbackKey = str2;
            JSONArray array = jSONArray;
            String action = str;
            try {
                MeshDataManager.isFlagAddGroupByHand = true;
                io.reactivex.disposables.b disposable = u((long) (array.length() * WearableStatusCodes.TARGET_NODE_NOT_CONNECTED), new o0());
                int count = array.length();
                JSONArray completeArray = new JSONArray();
                long start = System.currentTimeMillis();
                GroupInfo group = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, groupId);
                int groupAddress = 0;
                String groupType = "";
                if (group != null) {
                    groupAddress = group.address;
                    groupType = group.groupType;
                }
                String fGroupType = groupType;
                int addr = groupAddress;
                for (int i3 = 0; i3 < array.length(); i3++) {
                    String mac = array.getString(i3);
                    s(groupId, mac, new p0(mac, completeArray, count, addr, groupId, callbackKey, action, fGroupType, start, disposable));
                }
            } catch (Exception e2) {
                MeshLog.e("addGroupMembers exception:" + e2.getMessage());
            }
        }
    }

    public class p0 implements MeshGroupCallbackWrapper {
        public static ChangeQuickRedirect changeQuickRedirect;
        private String a = "";
        final /* synthetic */ String b;
        final /* synthetic */ JSONArray c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;
        final /* synthetic */ int f;
        final /* synthetic */ String g;
        final /* synthetic */ String h;
        final /* synthetic */ String i;
        final /* synthetic */ long j;
        final /* synthetic */ io.reactivex.disposables.b k;

        p0(String str, JSONArray jSONArray, int i2, int i3, int i4, String str2, String str3, String str4, long j2, io.reactivex.disposables.b bVar) {
            this.b = str;
            this.c = jSONArray;
            this.d = i2;
            this.e = i3;
            this.f = i4;
            this.g = str2;
            this.h = str3;
            this.i = str4;
            this.j = j2;
            this.k = bVar;
        }

        public void onSuccess(int i2, int groupAddr, int i3) {
            Object[] objArr = {new Integer(i2), new Integer(groupAddr), new Integer(i3)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5861, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
                int i4 = i2;
                int i5 = i3;
                int code = groupAddr == 0 ? 201 : 200;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mac", (Object) this.b);
                    jsonObject.put("code", code);
                    jsonObject.put("desc", (Object) FirebaseAnalytics.Param.SUCCESS);
                    jsonObject.put("degradeToLocalGroupReason", (Object) this.a);
                    this.c.put((Object) jsonObject);
                    if (this.c.length() == this.d) {
                        long endTime = System.currentTimeMillis();
                        JSONObject result = BaseResp.generatorSuccessResp(this.c);
                        result.put("groupAddress", this.e);
                        result.put("groupId", (Object) String.valueOf(this.f));
                        BleMeshServiceImpl.this.postJsBridgeCallback(this.g, result.toString(), this.h);
                        BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                        bleMeshServiceImpl.reportMeshJsonReport("addGroupMembers success:" + result);
                        BleMeshServiceImpl.this.c.setNeedUpload(true);
                        com.leedarson.serviceimpl.reporters.g.a(this.c, this.f, this.e, this.i, endTime - this.j);
                        MeshDataManager.flagAddGroup = false;
                        io.reactivex.disposables.b bVar = this.k;
                        if (bVar != null) {
                            bVar.dispose();
                        }
                        MeshDataManager.isFlagAddGroupByHand = false;
                        BleMeshServiceImpl.this.H(this.f, this.c);
                        return;
                    }
                    MeshLogNew.i("addGroupMembers completeCount:" + this.c.length() + ",targetCount:" + this.d);
                } catch (Exception e2) {
                    MeshLog.e("addGroupMembers(onsuccess) exception:" + e2.getMessage());
                }
            }
        }

        public void onFail(int i2, String str, int i3, int i4, int i5) {
            Object[] objArr = {new Integer(i2), str, new Integer(i3), new Integer(i4), new Integer(i5)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {cls, String.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5862, clsArr, Void.TYPE).isSupported) {
                String msg = str;
                int i6 = i4;
                int i7 = i2;
                int i8 = i3;
                int i9 = i5;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mac", (Object) this.b);
                    jsonObject.put("code", (int) BaseResp.ERR_MSG_SEND_FAIL);
                    jsonObject.put("desc", (Object) msg);
                    jsonObject.put("degradeToLocalGroupReason", (Object) this.a);
                    this.c.put((Object) jsonObject);
                    if (this.c.length() == this.d) {
                        long endTime = System.currentTimeMillis();
                        JSONObject result = BaseResp.generatorSuccessResp(this.c);
                        BleMeshServiceImpl.this.postJsBridgeCallback(this.g, result.toString(), this.h);
                        BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                        bleMeshServiceImpl.reportMeshJsonReport("addGroupMember onFail:" + result.toString());
                        BleMeshServiceImpl.this.c.setNeedUpload(true);
                        com.leedarson.serviceimpl.reporters.g.a(this.c, this.f, this.e, this.i, endTime - this.j);
                        MeshDataManager.flagAddGroup = false;
                        io.reactivex.disposables.b bVar = this.k;
                        if (bVar != null) {
                            bVar.dispose();
                        }
                        MeshDataManager.isFlagAddGroupByHand = false;
                        BleMeshServiceImpl.this.H(this.f, this.c);
                        return;
                    }
                    MeshLogNew.i("addGroupMembers(onFailed) completeCount:" + this.c.length() + ",targetCount:" + this.d);
                } catch (Exception e2) {
                    MeshLog.e("addGroupMembers(onfail) exception:" + e2.getMessage());
                }
            }
        }

        public void onDegradeToLocalGroup(String reason) {
            this.a = reason;
        }
    }

    public void E(JSONArray jSONArray, int i2, String str, String str2) {
        Class<String> cls = String.class;
        Object[] objArr = {jSONArray, new Integer(i2), str, str2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5716, new Class[]{JSONArray.class, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            int groupId = i2;
            String callbackKey = str2;
            JSONArray array = jSONArray;
            String action = str;
            String groupType = "";
            try {
                GroupInfo groupInfo = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, groupId);
                if (groupInfo != null) {
                    groupType = groupInfo.groupType;
                }
                String fGroupType = groupType;
                long start = System.currentTimeMillis();
                int count = array.length();
                JSONArray completeArray = new JSONArray();
                for (int i3 = 0; i3 < array.length(); i3++) {
                    String mac = array.getString(i3);
                    SIGMesh.getInstance().removeGroupMember(groupId, mac, new q0(mac, completeArray, count, callbackKey, action, groupId, fGroupType, start));
                }
            } catch (Exception e2) {
                MeshLog.e("removeGroupMembers exception:" + e2.getMessage());
            }
        }
    }

    public class q0 implements MeshGroupCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ JSONArray b;
        final /* synthetic */ int c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;
        final /* synthetic */ int f;
        final /* synthetic */ String g;
        final /* synthetic */ long h;

        q0(String str, JSONArray jSONArray, int i2, String str2, String str3, int i3, String str4, long j) {
            this.a = str;
            this.b = jSONArray;
            this.c = i2;
            this.d = str2;
            this.e = str3;
            this.f = i3;
            this.g = str4;
            this.h = j;
        }

        public void onSuccess(int i2, int i3, int i4) {
            Object[] objArr = {new Integer(i2), new Integer(i3), new Integer(i4)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5863, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
                int groupAddr = i3;
                int i5 = i4;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mac", (Object) this.a);
                    jsonObject.put("code", 200);
                    jsonObject.put("desc", (Object) FirebaseAnalytics.Param.SUCCESS);
                    this.b.put((Object) jsonObject);
                    if (this.b.length() == this.c) {
                        long endTime = System.currentTimeMillis();
                        BleMeshServiceImpl.this.postJsBridgeCallback(this.d, BaseResp.generatorSuccessResp(this.b).toString(), this.e);
                        BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                        bleMeshServiceImpl.reportMeshJsonReport("removeGroupMembers success:" + this.b);
                        BleMeshServiceImpl.this.c.setNeedUpload(true);
                        MeshDataManager.flagAddGroup = false;
                        com.leedarson.serviceimpl.reporters.g.e(this.b, this.f, groupAddr, this.g, endTime - this.h);
                        BleMeshServiceImpl.this.i.editGroup(this.f, "removeGroupMember");
                        return;
                    }
                    MeshLogNew.i("removeGroupMember completeCount:" + this.b.length() + ",targetCount:" + this.c);
                } catch (Exception e2) {
                    MeshLog.e("removeGroupMember onfail:" + e2.getMessage());
                }
            }
        }

        public void onFail(int i2, String str, int i3, int i4, int i5) {
            Object[] objArr = {new Integer(i2), str, new Integer(i3), new Integer(i4), new Integer(i5)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {cls, String.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5864, clsArr, Void.TYPE).isSupported) {
                String msg = str;
                int groupAddr = i4;
                int i6 = i2;
                int i7 = i3;
                int i8 = i5;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mac", (Object) this.a);
                    jsonObject.put("code", (int) BaseResp.ERR_MSG_SEND_FAIL);
                    jsonObject.put("desc", (Object) msg + " fail");
                    this.b.put((Object) jsonObject);
                    if (this.b.length() == this.c) {
                        long endTime = System.currentTimeMillis();
                        JSONObject result = BaseResp.generatorSuccessResp(this.b);
                        BleMeshServiceImpl.this.postJsBridgeCallback(this.d, result.toString(), this.e);
                        BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                        bleMeshServiceImpl.reportMeshJsonReport("removeGroup onFail:" + result.toString());
                        BleMeshServiceImpl.this.c.setNeedUpload(true);
                        com.leedarson.serviceimpl.reporters.g.e(this.b, this.f, groupAddr, this.g, endTime - this.h);
                        MeshDataManager.flagAddGroup = false;
                        BleMeshServiceImpl.this.i.editGroup(this.f, "removeGroupMember");
                        return;
                    }
                    MeshLogNew.i("removeGroupMember(onFailed) completeCount:" + this.b.length() + ",targetCount:" + this.c + ",msg:" + msg);
                } catch (Exception e2) {
                }
            }
        }
    }

    public void D(JSONArray jSONArray, String str, String str2, String str3) {
        String str4;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{jSONArray, str, str2, str3}, this, changeQuickRedirect, false, 5717, new Class[]{JSONArray.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String mac = str;
            String callbackKey = str3;
            JSONArray array = jSONArray;
            String action = str2;
            try {
                long start = System.currentTimeMillis();
                int count = array.length();
                JSONArray completeArray = new JSONArray();
                int i2 = 0;
                while (i2 < array.length()) {
                    int groupId = array.getInt(i2);
                    GroupInfo groupInfo = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, groupId);
                    if (groupInfo != null) {
                        try {
                            str4 = groupInfo.groupType;
                        } catch (Exception e2) {
                            e = e2;
                            JSONArray jSONArray2 = array;
                        }
                    } else {
                        str4 = "";
                    }
                    String groupType = str4;
                    SIGMesh instance = SIGMesh.getInstance();
                    JSONArray array2 = array;
                    s0 s0Var = r6;
                    try {
                        s0 s0Var2 = new s0(mac, groupId, completeArray, count, callbackKey, action, groupType, start);
                        instance.removeGroupMember(groupId, mac, s0Var);
                        i2++;
                        array = array2;
                    } catch (Exception e3) {
                        e = e3;
                        MeshLog.e("removeDeviceFromGroups exception:" + e.getMessage());
                    }
                }
            } catch (Exception e4) {
                e = e4;
                JSONArray jSONArray3 = array;
                MeshLog.e("removeDeviceFromGroups exception:" + e.getMessage());
            }
        }
    }

    public class s0 implements MeshGroupCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ int b;
        final /* synthetic */ JSONArray c;
        final /* synthetic */ int d;
        final /* synthetic */ String e;
        final /* synthetic */ String f;
        final /* synthetic */ String g;
        final /* synthetic */ long h;

        s0(String str, int i2, JSONArray jSONArray, int i3, String str2, String str3, String str4, long j) {
            this.a = str;
            this.b = i2;
            this.c = jSONArray;
            this.d = i3;
            this.e = str2;
            this.f = str3;
            this.g = str4;
            this.h = j;
        }

        public void onSuccess(int i2, int i3, int i4) {
            Object[] objArr = {new Integer(i2), new Integer(i3), new Integer(i4)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5866, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mac", (Object) this.a);
                    jsonObject.put("code", 200);
                    jsonObject.put("desc", (Object) "设备迁移房间解绑组id:" + this.b + FirebaseAnalytics.Param.SUCCESS);
                    jsonObject.put("groupId", this.b);
                    this.c.put((Object) jsonObject);
                    if (this.c.length() == this.d) {
                        long endTime = System.currentTimeMillis();
                        BleMeshServiceImpl.this.postJsBridgeCallback(this.e, BaseResp.generatorSuccessResp(this.c).toString(), this.f);
                        BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                        bleMeshServiceImpl.reportMeshJsonReport("removeDeviceFromGroups success:" + this.c);
                        BleMeshServiceImpl.this.c.setNeedUpload(true);
                        MeshDataManager.flagAddGroup = false;
                        com.leedarson.serviceimpl.reporters.g.d(this.c, this.a, this.g, endTime - this.h);
                        BleMeshServiceImpl.this.i.editGroup(this.b, "removeGroupMember");
                        return;
                    }
                    MeshLogNew.i("removeGroupMember completeCount:" + this.c.length() + ",targetCount:" + this.d);
                } catch (Exception e2) {
                    MeshLog.e("removeGroupMember onfail:" + e2.getMessage());
                }
            }
        }

        public void onFail(int i2, String str, int i3, int i4, int i5) {
            Object[] objArr = {new Integer(i2), str, new Integer(i3), new Integer(i4), new Integer(i5)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {cls, String.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5867, clsArr, Void.TYPE).isSupported) {
                String msg = str;
                int i6 = i4;
                int i7 = i2;
                int i8 = i3;
                int i9 = i5;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mac", (Object) this.a);
                    jsonObject.put("code", (int) BaseResp.ERR_MSG_SEND_FAIL);
                    jsonObject.put("desc", (Object) msg + " 设备迁移房间解绑组id:" + this.b + "fail");
                    jsonObject.put("groupId", this.b);
                    this.c.put((Object) jsonObject);
                    if (this.c.length() == this.d) {
                        long endTime = System.currentTimeMillis();
                        JSONObject result = BaseResp.generatorSuccessResp(this.c);
                        BleMeshServiceImpl.this.postJsBridgeCallback(this.e, result.toString(), this.f);
                        BleMeshServiceImpl bleMeshServiceImpl = BleMeshServiceImpl.this;
                        bleMeshServiceImpl.reportMeshJsonReport("removeGroup onFail:" + result.toString());
                        BleMeshServiceImpl.this.c.setNeedUpload(true);
                        com.leedarson.serviceimpl.reporters.g.d(this.c, this.a, this.g, endTime - this.h);
                        MeshDataManager.flagAddGroup = false;
                        BleMeshServiceImpl.this.i.editGroup(this.b, "removeGroupMember");
                        return;
                    }
                    MeshLogNew.i("removeGroupMember(onFailed) completeCount:" + this.c.length() + ",targetCount:" + this.d + ",msg:" + msg);
                } catch (Exception e2) {
                    MeshLog.e("removeDeviceFromGroups inner exception:" + e2.getMessage());
                }
            }
        }
    }

    public void s(int groupId, String mac, MeshGroupCallbackWrapper callbackWrapper) {
        Object[] objArr = {new Integer(groupId), mac, callbackWrapper};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5718, new Class[]{Integer.TYPE, String.class, MeshGroupCallbackWrapper.class}, Void.TYPE).isSupported) {
            SIGMesh.getInstance().addGroupMember(groupId, mac, callbackWrapper);
        }
    }

    public void initBleMesh(Activity activity) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 5719, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            this.h = activity;
            this.i = new LDSGroupApi();
            this.j = new LDSSceneApi();
            this.q = new l(activity);
            this.t.add(BleMeshService.ACTION_ADD_GROUP);
            this.t.add("addGroupMember");
            this.t.add(BleMeshService.ACTION_ADD_DEVICES);
            this.u.add(BleMeshService.ACTION_GET_CURRENT_DETECT_MODE);
            this.u.add(BleMeshService.ACTION_CONTROL_GROUP);
            this.u.add("controlDevice");
            this.u.add(BleMeshService.ACTION_GET_VERSION);
            this.u.add(BleMeshService.ACTION_GET_DEVICE_GROUPS);
            MeshDataManager meshDataManager = new MeshDataManager(activity);
            this.c = meshDataManager;
            this.p = new com.leedarson.serviceimpl.product.c(this.g, meshDataManager);
            this.r = (LightsRhythmService) com.alibaba.android.arouter.launcher.a.c().g(LightsRhythmService.class);
            DBManager.getInstance(activity);
            try {
                String packServerEnv = SharePreferenceUtils.getPrefString(activity, "PACK_SERVER_ENV", "prod");
                SIGMesh instance = SIGMesh.getInstance();
                if ("prod".equals(packServerEnv)) {
                    z2 = false;
                }
                instance.enableLog(z2).initSDK(activity.getApplicationContext());
                this.c.cleanInvalidNode();
            } catch (Exception e2) {
                e2.printStackTrace();
                MeshLog.e("111222 出异常了initBleMesh:" + e2.getMessage());
            }
            this.e = new BluetoothChangeReceiver();
            activity.registerReceiver(this.e, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
            SIGMesh.getInstance().setGlobalCallback(this);
            SIGMesh.getInstance().setOnPermissionListener(this.D);
            com.leedarson.serviceimpl.reporters.c.f("app-> 启动(" + activity.getClass().getSimpleName() + ") initBleMesh autoConnect request");
            SIGMesh.getInstance().autoConnect();
        }
    }

    public void unInit() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5720, new Class[0], Void.TYPE).isSupported) {
            MeshLogNew.d("unInitBleMesh 退出，释放mesh资源");
            SIGMesh.getInstance().release();
            org.greenrobot.eventbus.c.c().r(this);
            I();
            MeshDataManager meshDataManager = this.c;
            if (meshDataManager != null) {
                meshDataManager.stopUpTimer();
            }
        }
    }

    private void I() {
        BluetoothChangeReceiver bluetoothChangeReceiver;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5721, new Class[0], Void.TYPE).isSupported) {
            Intent intent = new Intent();
            intent.setAction("android.bluetooth.adapter.action.STATE_CHANGED");
            if (y(intent) && (bluetoothChangeReceiver = this.e) != null) {
                try {
                    this.g.unregisterReceiver(bluetoothChangeReceiver);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean y(Intent intent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 5722, new Class[]{Intent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            List<ResolveInfo> resolveInfos = this.g.getPackageManager().queryBroadcastReceivers(intent, 0);
            if (resolveInfos != null && !resolveInfos.isEmpty()) {
                for (ResolveInfo info : resolveInfos) {
                    if (this.g.getPackageName().equals(info.resolvePackageName)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5723, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.g = context;
            this.o = new com.leedarson.serviceimpl.product.d();
            this.d = new Handler(Looper.getMainLooper());
            org.greenrobot.eventbus.c.c().p(this);
        }
    }

    public void onNetworkInfoUpdate(int sno, int ivIndex) {
        Object[] objArr = {new Integer(sno), new Integer(ivIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5724, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            MeshLog.e("### 更新mesh配置文件时 网络配置更新的回调 onNetworkInfoUpdate:sno" + sno + ",ivIndex" + ivIndex);
            this.d.postDelayed(new t0(), 200);
        }
    }

    public class t0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        t0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5868, new Class[0], Void.TYPE).isSupported) {
                if (MeshService.k().f() != MeshController.Mode.MODE_BIND) {
                    com.leedarson.serviceimpl.reporters.c.f("onNetworkInfoUpdate, autoConnect request");
                    SIGMesh.getInstance().autoConnectWithoutDialog();
                }
            }
        }
    }

    public void onNetworkStatusChange(int status) {
        if (!PatchProxy.proxy(new Object[]{new Integer(status)}, this, changeQuickRedirect, false, 5725, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append("#### 收到mesh网络状态改变通知onNetworkStatusChange:");
            sb.append(status == 1 ? "在线" : "离线");
            MeshLog.i(sb.toString());
            if (status == 1) {
                ProcedureCollector.endCollectAndClear(MeshConstants.TRACE_ID_AUTO_CONNECT);
                ProcedureCollector.addAndReportELK("mesh 上线 onNetworkStatusChange=1", "mesh", Thread.currentThread().getId(), MeshConstants.TRACE_ID_AUTO_CONNECT);
                com.leedarson.serviceimpl.reporters.c.g(false, "onNetworkStatusChange status=" + status);
            }
            JSONObject json = new JSONObject();
            try {
                json.put("status", status);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_NETWORK_STATUS_CHANGE, json.toString());
            List<NodeInfo> nodes = SIGMesh.getInstance().getMeshInfo().nodes;
            MeshLog.i("#### onNetworkStatusChange.status  getMeshInfo.nodes=" + nodes.size());
            for (NodeInfo nodeInfo : nodes) {
                if (status == 0) {
                    MeshLog.i("mesh网络离线:" + nodeInfo.macAddress + "通知离线");
                    nodeInfo.offlineReset();
                    onDeviceOnlineChange(nodeInfo.macAddress, 0);
                } else if (nodeInfo.isOnline() || nodeInfo.getOnOff() != -1) {
                    MeshLog.i("mesh网络在线:" + nodeInfo.macAddress + "通知上线,node isonline:" + nodeInfo.isOnline() + ",onoff:" + nodeInfo.getOnOff());
                    onDeviceOnlineChange(nodeInfo.macAddress, 1);
                } else {
                    MeshLog.i("mesh网络在线，但设备还未上报在线:" + nodeInfo.macAddress + "通知离线,等待设备上报任何消息上线");
                    nodeInfo.offlineReset();
                    onDeviceOnlineChange(nodeInfo.macAddress, 0);
                }
            }
            if (status != 1) {
                this.k = false;
            }
        }
    }

    public void onDeviceOnlineChange(String mac, int online) {
        if (!PatchProxy.proxy(new Object[]{mac, new Integer(online)}, this, changeQuickRedirect, false, 5726, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            C(mac, online, true);
        }
    }

    private void C(String mac, int online, boolean queryState) {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{mac, new Integer(online), new Byte(queryState ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5727, new Class[]{String.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            String key = mac + "_" + "onDeviceOnlineChange";
            if (this.x.containsKey(key)) {
                NotifyWebHistoryBean historyBean = this.x.get(key);
                boolean overHistoryTime = System.currentTimeMillis() - historyBean.time > 600;
                Object obj = historyBean.value;
                if (obj != null && ((Integer) obj).intValue() == online) {
                    z2 = true;
                }
                boolean isValueSame = z2;
                if (!overHistoryTime && isValueSame) {
                    MeshLog.e(mac + "短时间内重复上报onDeviceOnlineChange相同状态,不通知web，超过通知间隔?" + overHistoryTime + ",value same?" + isValueSame);
                    return;
                }
            }
            NotifyWebHistoryBean notifyWebHistoryBean = new NotifyWebHistoryBean();
            notifyWebHistoryBean.time = System.currentTimeMillis();
            notifyWebHistoryBean.value = Integer.valueOf(online);
            this.x.put(key, notifyWebHistoryBean);
            JSONObject json = new JSONObject();
            try {
                json.put("mac", (Object) mac);
                json.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, online);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (online == 1 && queryState) {
                SIGMesh.getInstance().getSyncCtrl().sendInitConfig(mac);
            }
            postJsCallH5ByNative("onDeviceOnlineChange", json.toString());
        }
    }

    public void onDeviceStatusChange(String mac, int modelId, Object value) {
        if (!PatchProxy.proxy(new Object[]{mac, new Integer(modelId), value}, this, changeQuickRedirect, false, 5728, new Class[]{String.class, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            if (!this.q.b()) {
                MeshLog.v("onDeviceStatusChange被拦截，手机锁屏中");
                return;
            }
            JSONObject json = new JSONObject();
            try {
                json.put("mac", (Object) mac);
                json.put("modelId", modelId);
                json.put("value", value);
                MeshLog.d("上报(" + mac + ")设备属性信息(model:" + LDSModel.LdsModelName.modelName(modelId) + ",value:" + value);
                C(mac, 1, false);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_DEVICES_STATUS_CHANGE, json.toString());
        }
    }

    public void onRetryReportDeviceOnLine() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5729, new Class[0], Void.TYPE).isSupported) {
            for (AutoConnectDevicesManager.AutoConnectDevice autoConnectDevice : AutoConnectDevicesManager.d().d) {
                if (autoConnectDevice.d() != -1) {
                    onPreReportDeviceStatusChange(autoConnectDevice.b().getAddress(), autoConnectDevice.d(), autoConnectDevice.c(), true);
                } else {
                    com.leedarson.serviceimpl.elkstrays.b.b("补发，先上报mesh设备-旧设备(" + autoConnectDevice.b().getAddress() + ")广播包中无onOff信息，不上报");
                }
            }
        }
    }

    public void onPreReportDeviceStatusChange(String macAddress, int onOff, int dimming, boolean didRenderTry) {
        Object[] objArr = {macAddress, new Integer(onOff), new Integer(dimming), new Byte(didRenderTry ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5730, new Class[]{String.class, cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            String scanMac = macAddress.replace(":", "");
            f1 reported = this.y.get(scanMac);
            if (reported == null) {
                reported = new f1();
            }
            this.y.put(scanMac, reported);
            if (!reported.a) {
                if (didRenderTry) {
                    h(scanMac, onOff, dimming);
                } else if (!reported.b) {
                    reported.b = true;
                    this.z.add(u(CacheHandler.delayTime, new u0(scanMac, onOff, dimming)));
                }
            }
        }
    }

    public class u0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ int d;
        final /* synthetic */ int f;

        u0(String str, int i, int i2) {
            this.c = str;
            this.d = i;
            this.f = i2;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5869, new Class[0], Void.TYPE).isSupported) {
                BleMeshServiceImpl.this.h(this.c, this.d, this.f);
            }
        }
    }

    public void onCanclePreReportDeviceStatusChanged() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5731, new Class[0], Void.TYPE).isSupported) {
            List<io.reactivex.disposables.b> list = this.z;
            if (list != null && list.size() > 0) {
                for (io.reactivex.disposables.b disposable : this.z) {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }
            this.z.clear();
            for (AutoConnectDevicesManager.AutoConnectDevice device : AutoConnectDevicesManager.d().d) {
                String scanMac = device.b().getAddress().replace(":", "");
                for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
                    if (scanMac.equals(nodeInfo.macAddress)) {
                        com.leedarson.serviceimpl.elkstrays.b.c("下线扫描到的已提前上线的mesh设备:" + scanMac);
                        JSONObject json = new JSONObject();
                        try {
                            json.put("mac", (Object) nodeInfo.macAddress);
                            json.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, 0);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        postJsCallH5ByNative("onDeviceOnlineChange", json.toString());
                    }
                }
            }
        }
    }

    public void h(String scanMac, int onOff, int dimming) {
        Object[] objArr = {scanMac, new Integer(onOff), new Integer(dimming)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5732, new Class[]{String.class, cls, cls}, Void.TYPE).isSupported) {
            if (!Constans.isDidRender) {
                com.leedarson.serviceimpl.elkstrays.b.c("先上报mesh设备上线:" + scanMac + " but webview还未挂载完成 ,isDidUnhandler:" + Constans.isDidUnhandler);
            } else if (!this.y.get(scanMac).a) {
                this.y.get(scanMac).a = true;
                for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
                    if (scanMac.equals(nodeInfo.macAddress)) {
                        com.leedarson.serviceimpl.elkstrays.b.c("先上报mesh设备:" + scanMac + "上线,同时Onoff赋值:" + onOff);
                        nodeInfo.setOnOff(onOff);
                        nodeInfo.lum = dimming;
                    }
                }
                onDeviceStatusChange(scanMac, 4096, Integer.valueOf(onOff));
                onDeviceStatusChange(scanMac, LDSModel.MODEL_BRIGHTNESS_CTRL, Integer.valueOf(dimming));
            }
        }
    }

    private io.reactivex.disposables.b u(long timeMillseconds, Runnable runnable) {
        Object[] objArr = {new Long(timeMillseconds), runnable};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5733, new Class[]{Long.TYPE, Runnable.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        return io.reactivex.l.F(1).l(timeMillseconds, TimeUnit.MILLISECONDS).b0(com.leedarson.base.http.observer.l.f).X(new b(runnable));
    }

    static /* synthetic */ void z(Runnable runnable, Integer num) {
        Class[] clsArr = {Runnable.class, Integer.class};
        if (!PatchProxy.proxy(new Object[]{runnable, num}, (Object) null, changeQuickRedirect, true, 5771, clsArr, Void.TYPE).isSupported) {
            runnable.run();
        }
    }

    @org.greenrobot.eventbus.l
    public void onReceived(TestEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5734, new Class[]{TestEvent.class}, Void.TYPE).isSupported) {
            JSONObject object = new JSONObject();
            try {
                object.put("info", (Object) event.msg);
                object.put(FirebaseAnalytics.Param.LEVEL, (Object) event.level);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(event.tag, object.toString());
        }
    }

    public void transferRhythm(String mac, String groupId, JSONObject jsonObject) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{mac, groupId, jsonObject}, this, changeQuickRedirect, false, 5735, clsArr, Void.TYPE).isSupported) {
            try {
                if (this.A) {
                    MeshLog.logMusicRhythmWarn("正在发送主题颜色，先不发律动指令");
                } else if (!TextUtils.isEmpty(this.B)) {
                    MeshLog.logMusicRhythmWarn(this.B + "，先不发律动指令");
                } else if (jsonObject.has("selectedMacsRhythmV3")) {
                    SIGMesh.getInstance().setRhythmV3(mac, groupId, jsonObject);
                } else {
                    String rhythmType = "";
                    if (jsonObject.has("rhythmType")) {
                        rhythmType = (String) jsonObject.get("rhythmType");
                    }
                    boolean supportAsyncRhythm = jsonObject.optBoolean("supportAsyncRhythm", false);
                    if (!IRhyDevice.RHYTHM_TYPE_ASYNC.equals(rhythmType)) {
                        if (!supportAsyncRhythm) {
                            SIGMesh.getInstance().setRhythmV1(mac, groupId, jsonObject);
                            return;
                        }
                    }
                    SIGMesh.getInstance().setAsyncRhythmV2(mac, "", jsonObject);
                }
            } catch (Exception e2) {
                MeshLog.logMusicRhythmWarn("transferRhythm error=>" + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }

    public void setRhythmEnable(String mac, String groupId, byte able, com.leedarson.base.http.listener.a aVar) {
        Class<String> cls = String.class;
        Object[] objArr = {mac, groupId, new Byte(able), aVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5736, new Class[]{cls, cls, Byte.TYPE, com.leedarson.base.http.listener.a.class}, Void.TYPE).isSupported) {
            com.leedarson.base.http.listener.a listener = aVar;
            if (able != 1 || !isDelayRhythmRef()) {
                MeshLog.logMusicRhythm("setRhythmEnable. mac:" + mac + ",groupId:" + groupId + ",enable:" + able);
                SIGMesh.getInstance().setRhythmEnable(mac, groupId, able, new v0(mac, groupId, able, listener));
                return;
            }
            MeshLog.logMusicRhythmWarn("正在控制，不发enable");
        }
    }

    public class v0 extends MeshCustomcmdCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ byte c;
        final /* synthetic */ com.leedarson.base.http.listener.a d;

        v0(String str, String str2, byte b2, com.leedarson.base.http.listener.a aVar) {
            this.a = str;
            this.b = str2;
            this.c = b2;
            this.d = aVar;
        }

        public void onSuccess(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5870, new Class[]{Object.class}, Void.TYPE).isSupported) {
                MeshLog.logMusicRhythm("setRhythmEnable success mac:" + this.a + ",groupId:" + this.b + ",enable:" + this.c);
                com.leedarson.base.http.listener.a aVar = this.d;
                if (aVar != null) {
                    aVar.onSuccess(data);
                }
            }
        }

        public void onFail(int i, String msg, Object obj) {
            Object[] objArr = {new Integer(i), msg, obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5871, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported) {
                MeshLog.logMusicRhythm("setRhythmEnable fail msg:" + msg + ",mac:" + this.a + ",groupId:" + this.b + ",enable:" + this.c);
                com.leedarson.base.http.listener.a aVar = this.d;
                if (aVar != null) {
                    aVar.onFail(msg);
                }
            }
        }
    }

    public void setRhythmTheme(String mac, String groupId, int[] colors) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{mac, groupId, colors}, this, changeQuickRedirect, false, 5737, new Class[]{cls, cls, int[].class}, Void.TYPE).isSupported) {
            this.A = true;
            StringBuilder sb = new StringBuilder();
            sb.append("标记当前正在发送律动主题,size:");
            sb.append(colors != null ? colors.length : 0);
            MeshLog.logMusicRhythm(sb.toString());
            for (int j2 = 0; j2 < 2; j2++) {
                int i2 = 0;
                while (i2 < colors.length) {
                    SIGMesh.getInstance().setRhythmTheme(mac, groupId, colors, i2);
                    try {
                        Thread.sleep(150);
                        i2++;
                    } catch (InterruptedException e2) {
                        throw new RuntimeException(e2);
                    }
                }
            }
            this.A = false;
            MeshLog.logMusicRhythm("发送律动主题完毕");
        }
    }

    public void setOfflineCheckEnable(String mac, boolean enable) {
        if (!PatchProxy.proxy(new Object[]{mac, new Byte(enable ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5738, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            MeshLog.e("setOfflineCheckEnable:" + enable);
            SIGMesh.getInstance().setOfflineCheckEnable(mac, enable);
        }
    }

    public void deviceConnect() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5739, new Class[0], Void.TYPE).isSupported) {
            MeshLog.e("deviceConnect()");
            Handler handler = this.d;
            if (handler != null) {
                handler.removeCallbacks(this.C);
                this.d.postDelayed(this.C, 200);
            }
        }
    }

    public void cancelTimer() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5740, new Class[0], Void.TYPE).isSupported) {
            MeshLog.e("cancelTimer()");
            Handler handler = this.d;
            if (handler != null) {
                handler.removeCallbacks(this.C);
            }
        }
    }

    public class w0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        w0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5872, new Class[0], Void.TYPE).isSupported) {
                SIGMesh.getInstance().autoConnect();
            }
        }
    }

    public boolean setDelayRhythmRef(String delayRhythmRef) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{delayRhythmRef}, this, changeQuickRedirect, false, 5741, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        LightsRhythmService lightsRhythmService = this.r;
        if (lightsRhythmService == null || !lightsRhythmService.isMeshRhythm()) {
            return false;
        }
        this.B = delayRhythmRef;
        setMsgQueueMode(true);
        MeshLog.logMusicRhythm(delayRhythmRef + " 设置延迟律动标志位,使用ble队列方式发送");
        return true;
    }

    public boolean isDelayRhythmRef() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5742, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : !TextUtils.isEmpty(this.B);
    }

    public void clearDelayRhythmRef() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5743, new Class[0], Void.TYPE).isSupported) {
            this.B = "";
            LightsRhythmService lightsRhythmService = this.r;
            if (lightsRhythmService != null && lightsRhythmService.isMeshRhythm()) {
                setMsgQueueMode(false);
                MeshLog.logMusicRhythm("清除延迟律动标志位, 不使用ble队列方式发送");
            }
        }
    }

    public class x0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean c;
        final /* synthetic */ Activity d;

        x0(boolean z, Activity activity) {
            this.c = z;
            this.d = activity;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5873, new Class[0], Void.TYPE).isSupported) {
                if (this.c) {
                    this.d.getWindow().addFlags(128);
                } else {
                    this.d.getWindow().clearFlags(128);
                }
            }
        }
    }

    public static void G(Activity activity, boolean keepOn) {
        if (!PatchProxy.proxy(new Object[]{activity, new Byte(keepOn ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 5744, new Class[]{Activity.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            MeshLog.d("setKeepScreenOn:" + keepOn);
            activity.runOnUiThread(new x0(keepOn, activity));
        }
    }

    public void forceUpdateConfig() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5745, new Class[0], Void.TYPE).isSupported) {
            this.c.queryMeshConfig(true);
        }
    }

    public void reportMeshJsonReport(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5746, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.reporters.k.a(msg);
        }
    }

    public void onPermissionRequestGranted(String targetAction, boolean granted) {
        if (!PatchProxy.proxy(new Object[]{targetAction, new Byte(granted ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5747, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (granted) {
                MeshLog.i("onPermissionRequestGranted:" + targetAction);
                if ("bleMeshConnect".equals(targetAction)) {
                    com.leedarson.serviceimpl.reporters.c.f("onPermissionRequestGranted autoConnect request");
                    SIGMesh.getInstance().autoConnect();
                } else if (!"bleStartScan".equals(targetAction)) {
                    boolean equals = "bleStopScan".equals(targetAction);
                }
            } else {
                this.s = true;
            }
        }
    }

    public void onHouseChange(String newHouseId) {
        if (!PatchProxy.proxy(new Object[]{newHouseId}, this, changeQuickRedirect, false, 5748, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (System.currentTimeMillis() - this.m < 1500) {
                MeshLog.e("1.5s内又调用，切换家,忽略");
                return;
            }
            this.c.startUpTimer();
            this.m = System.currentTimeMillis();
            String lastHouseId = SharedPreferenceHelper.getLastHouseId(this.g);
            String tempHouseId = SharedPreferenceHelper.getHouseId(this.g);
            if (!TextUtils.isEmpty(tempHouseId) && !newHouseId.equals(tempHouseId)) {
                SharedPreferenceHelper.setLastHouseId(this.g, tempHouseId);
                lastHouseId = tempHouseId;
            }
            SharedPreferenceHelper.setHouseId(this.g, newHouseId);
            MeshLog.e("onHouseChange####newHouseId:" + SharedPreferenceHelper.getHouseId(this.g) + ",lastHouseId:" + SharedPreferenceHelper.getLastHouseId(this.g));
            if (lastHouseId != null) {
                try {
                    MeshService.k().n(SIGMesh.getInstance().hasConnected(), "切换家");
                    reportMeshJsonReport("切换家重新加载mesh配置");
                    SIGMesh.getInstance().reloadMeshConfig();
                    this.c.cleanInvalidNode();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    com.leedarson.log.elk.a t2 = com.leedarson.log.elk.a.y(BleMeshServiceImpl.class).o("info").t("LdsBleMesh");
                    t2.p("切换家出错 newHouseId:" + SharedPreferenceHelper.getHouseId(this.g) + "err:" + e2.toString()).a().b();
                    MeshLog.e("切换家出错 newHouseId:" + SharedPreferenceHelper.getHouseId(this.g) + "err:" + e2.toString());
                }
            } else {
                MeshLogNew.meshJsonLog("切换家 lastHouseId =null");
            }
            MeshLogNew.meshJsonLog("准备上报当前家的 mesh network信息到云端");
            this.c.postMeshNetworkInfo(SharedPreferenceHelper.getHouseId(this.g));
            MatterService matterService = (MatterService) com.alibaba.android.arouter.launcher.a.c().g(MatterService.class);
            if (matterService != null) {
                matterService.onHouseChange(SharedPreferenceHelper.getHouseId(this.g), SharedPreferenceHelper.getLastHouseId(this.g));
            }
        }
    }

    public void setMsgQueueMode(boolean isQueue) {
        Object[] objArr = {new Byte(isQueue ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5749, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            MeshLog.w("setMsgQueueMode:" + isQueue);
            MeshService.k().u(isQueue);
        }
    }

    public void test() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5750, new Class[0], Void.TYPE).isSupported) {
            for (int i2 = 0; i2 < 1000; i2++) {
                JSONObject json = new JSONObject();
                try {
                    json.put("mac", (Object) "1CD6BDC851EE");
                    json.put("modelId", 4096);
                    json.put("value", 1);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_SIG_MESH, BleMeshService.ON_DEVICES_STATUS_CHANGE, json.toString()));
                JSONObject json2 = new JSONObject();
                try {
                    json2.put("mac", (Object) "1CD6BDC851EE");
                    json2.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, 1);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_SIG_MESH, "onDeviceOnlineChange", json2.toString()));
            }
        }
    }

    public void checkNeedToAutoConnectProcess() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5751, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.reporters.c.f("离开后台超过3分钟回来, autoConnect request");
            MeshService.k().a(new AutoConnectParameters());
        }
    }

    @org.greenrobot.eventbus.l
    public void onScreenStatusChange(ScreenStatusReceiveEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5752, new Class[]{ScreenStatusReceiveEvent.class}, Void.TYPE).isSupported) {
            if (event.screenOn) {
                String str = this.q.e();
                MeshLog.i(str + ",查询版本");
                if (this.c != null) {
                    reportMeshJsonReport("屏幕点亮，请求查询meshjson信息");
                    com.leedarson.base.http.observer.l.g.b(new a(this));
                    return;
                }
                return;
            }
            MeshLog.i("屏幕熄灭");
            this.q.d();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: A */
    public /* synthetic */ void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5770, new Class[0], Void.TYPE).isSupported) {
            this.c.queryMeshConfig(false);
        }
    }

    @org.greenrobot.eventbus.l
    public void onBackAndFrontEventChange(BackAndFrontChangeEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5753, new Class[]{BackAndFrontChangeEvent.class}, Void.TYPE).isSupported) {
            MeshLog.i("前后台切换 isFront:" + event.isFrontFlag);
            this.q.c(event.isFrontFlag);
        }
    }

    public void onSecurityTrigger(int meshAddr, int act) {
        Object[] objArr = {new Integer(meshAddr), new Integer(act)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5754, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.o.onSecurityTrigger(meshAddr, act);
        }
    }

    public void onAlarmStatusChangeEvent(int meshAddr, int status) {
        Object[] objArr = {new Integer(meshAddr), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5755, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, meshAddr);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mac", (Object) nodeInfo.macAddress);
                jsonObject.put("status", status);
                jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) BleMeshService.EVT_ALARM_STATUS_CHANGE_EVENT);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_EVENT_MESSAGE, jsonObject.toString());
        }
    }

    public void onGotoSleepEvent(int meshAddr, int status) {
        Object[] objArr = {new Integer(meshAddr), new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5756, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, meshAddr);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mac", (Object) nodeInfo.macAddress);
                jsonObject.put("status", status);
                jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) BleMeshService.EVT_GOTO_SLEEP_EVENT);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_EVENT_MESSAGE, jsonObject.toString());
        }
    }

    public void onIlluminationStateChange(int meshAddr, int state) {
        Object[] objArr = {new Integer(meshAddr), new Integer(state)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5757, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, meshAddr);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mac", (Object) nodeInfo.macAddress);
                jsonObject.put(Constants.ACTION_STATE, state);
                jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) BleMeshService.EVT_ILLUMINATION_STATE_CHANGE);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_EVENT_MESSAGE, jsonObject.toString());
        }
    }

    public void onLowBatteryEvent(int meshAddr, int battery, int chargeState, int acState, String params) {
        Object[] objArr = {new Integer(meshAddr), new Integer(battery), new Integer(chargeState), new Integer(acState), params};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5758, new Class[]{cls, cls, cls, cls, String.class}, Void.TYPE).isSupported) {
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, meshAddr);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mac", (Object) nodeInfo.macAddress);
                jsonObject.put("battery", battery);
                if (chargeState != -1) {
                    jsonObject.put("chargeState", chargeState);
                }
                jsonObject.put("acState", acState);
                jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) BleMeshService.EVT_LOW_BATTERY);
                jsonObject.put("params", (Object) params);
                MeshLog.i("设备电量上报 macAddress:" + nodeInfo.macAddress + ",battery:" + battery + ",chargeState:" + chargeState + ",acState:" + acState);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_EVENT_MESSAGE, jsonObject.toString());
            com.leedarson.log.elk.a y2 = com.leedarson.log.elk.a.y(this);
            y2.p(jsonObject + ",params:" + params).o("info").x(MeshConstants.TRACE_ID_LOW_POWER).a().b();
        }
    }

    public void onSingleCappedAlarmTrigger(int meshAddr, int onOff) {
        Object[] objArr = {new Integer(meshAddr), new Integer(onOff)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5759, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, meshAddr);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mac", (Object) nodeInfo.macAddress);
                jsonObject.put("onOff", onOff);
                jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) BleMeshService.EVT_SINGLE_CAPPED_ALARM_EVENT);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            postJsCallH5ByNative(BleMeshService.ON_EVENT_MESSAGE, jsonObject.toString());
        }
    }

    private void F(String traceId, String message, String level) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{traceId, message, level}, this, changeQuickRedirect, false, 5760, clsArr, Void.TYPE).isSupported) {
            MeshLogNew.i("traceId:" + traceId + ",message:" + message);
            com.leedarson.log.elk.a.y(this).t("LdsBleMesh").x(traceId).p(message).o(level).a().b();
        }
    }

    public List<String> getNodeAddressByGroupId(String groupId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{groupId}, this, changeQuickRedirect, false, 5761, new Class[]{String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        return SIGMesh.getInstance().getNodeAddressByGroupId(groupId);
    }

    public void onDidRender() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5762, new Class[0], Void.TYPE).isSupported) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                com.leedarson.serviceimpl.reporters.c.f("onDidRender 蓝牙未打开 autoConnect request");
                SIGMesh.getInstance().autoConnect();
            }
        }
    }

    public class y0 implements OnPermissionListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        y0() {
        }

        public void onBluetoothEnable() {
        }

        public void onBluetoothDisable() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5874, new Class[0], Void.TYPE).isSupported) {
                MeshLog.e("onBluetoothDisable");
                com.leedarson.base.views.common.dialogs.a actionDialog = new com.leedarson.base.views.common.dialogs.a(BleMeshServiceImpl.this.h);
                String contentTxt = PubUtils.getString(BleMeshServiceImpl.this.h, R$string.rationale_enable_bluetooth);
                actionDialog.i(PubUtils.getString(BleMeshServiceImpl.this.h, R$string.permission_title_setting));
                actionDialog.d(PubUtils.getString(BleMeshServiceImpl.this.h, R$string.cancel));
                actionDialog.f(PubUtils.getString(BleMeshServiceImpl.this.h, R$string.ok));
                actionDialog.h(contentTxt);
                actionDialog.c(new a());
                actionDialog.show();
            }
        }

        public class a implements a.c {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void a() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5875, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.reporters.c.f("蓝牙授权弹出 autoConnect request");
                    SIGMesh.getInstance().autoConnect();
                }
            }

            public void onCancel() {
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5763, new Class[0], Void.TYPE).isSupported) {
            MeshLog.i("onResume");
            if (this.s && EasyPermissions.a(this.h, "android.permission.ACCESS_COARSE_LOCATION")) {
                this.s = false;
                MeshLog.i("满足授权后返回APP重新连接mesh");
                SIGMesh.getInstance().autoConnect();
            }
        }
    }

    public JSONObject getELKMessageBody(Object obj, String msg, String method, String traceId, String level, String module) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, msg, method, traceId, level, module}, this, changeQuickRedirect, false, 5764, new Class[]{Object.class, cls, cls, cls, cls, cls}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        return com.leedarson.log.elk.a.y(obj).p(msg).s(method).x(traceId).o(level).t(module).f();
    }

    public void postDeviceStatusChange(String mac, int modelId, Object value) {
        Object[] objArr = {mac, new Integer(modelId), value};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5765, new Class[]{String.class, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            onDeviceStatusChange(mac, modelId, value);
        }
    }

    public void onHeartBeat(int srcAddr, int onoff, boolean goingToSleep) {
        Object[] objArr = {new Integer(srcAddr), new Integer(onoff), new Byte(goingToSleep ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5766, new Class[]{cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            NodeInfo node = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, srcAddr);
            if (node != null) {
                MeshLog.d("property_heart_beat 收到 protocol3 心跳包协议 ：" + node.macAddress + ",addr:" + srcAddr + ",onOff:" + onoff + ",goingToSleep:" + goingToSleep);
                if (goingToSleep) {
                    MeshLog.d(node.macAddress + "property_heart_beat 进入低电量休眠，3s后上报设备离线");
                    this.d.postDelayed(new z0(node), GroupCtrlAdapter.RETRY_TIMEOUT);
                    return;
                }
                if (!node.isOnline()) {
                    MeshLog.e("node 离线变在线：" + node.macAddress);
                    node.setOnline(true);
                }
                onDeviceStatusChange(node.macAddress, 4096, Integer.valueOf(onoff));
            }
        }
    }

    public class z0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ NodeInfo c;

        z0(NodeInfo nodeInfo) {
            this.c = nodeInfo;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5876, new Class[0], Void.TYPE).isSupported) {
                this.c.offlineReset();
                BleMeshServiceImpl.this.onDeviceOnlineChange(this.c.macAddress, 0);
            }
        }
    }

    public void stopScan(String fromBz) {
        if (!PatchProxy.proxy(new Object[]{fromBz}, this, changeQuickRedirect, false, 5767, new Class[]{String.class}, Void.TYPE).isSupported) {
            SIGMesh.getInstance().stopScan(fromBz);
        }
    }

    public boolean meshGroupExist(int groupId) {
        Object[] objArr = {new Integer(groupId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5768, new Class[]{Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return SIGMesh.getInstance().meshGroupExist(groupId);
    }

    public void H(int groupId, JSONArray completeArray) {
        Object[] objArr = {new Integer(groupId), completeArray};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5769, new Class[]{Integer.TYPE, JSONArray.class}, Void.TYPE).isSupported) {
            try {
                JSONArray meshMembers = new JSONArray();
                JSONArray localMembers = new JSONArray();
                for (int i2 = 0; i2 < completeArray.length(); i2++) {
                    JSONObject object = completeArray.getJSONObject(i2);
                    String mac = object.optString("mac");
                    if (object.optInt("code") == 200) {
                        meshMembers.put((Object) mac);
                    } else {
                        localMembers.put((Object) mac);
                    }
                }
                this.i.editGroup(groupId, "addGroupMember");
            } catch (Exception e2) {
            }
        }
    }
}
