package com.leedarson.mqtt;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.maps.android.BuildConfig;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.H5ActionName;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.mqtt.beans.ApiMqttConfigBean;
import com.leedarson.mqtt.beans.LdsMqttConfigOptionBean;
import com.leedarson.mqtt.beans.MqttServiceRequestTaskBean;
import com.leedarson.mqtt.libservice.MqttAndroidClient;
import com.leedarson.mqtt.logs.MqttLogStepBean;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.event.NativeMqttMessageArrivedEvent;
import com.leedarson.serviceinterface.event.NativeMqttStatusChangeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import meshsdk.BaseResp;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LdsMqttManager */
public class l {
    private static l a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private MqttAndroidClient b;
    private org.eclipse.paho.client.mqttv3.j c;
    private org.eclipse.paho.client.mqttv3.a d;
    private LdsMqttConfigOptionBean e;
    private com.leedarson.mqtt.listerners.b f;
    private com.leedarson.mqtt.listerners.a g;
    io.reactivex.disposables.a h = new io.reactivex.disposables.a();
    com.leedarson.mqtt.repos.h i = new com.leedarson.mqtt.repos.h();
    Handler j = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public int k = 0;
    /* access modifiers changed from: private */
    public int l = 6;
    /* access modifiers changed from: private */
    public n m = n.MQTT_CONNECT_STATUE_IDLE;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Integer, MqttServiceRequestTaskBean> n = new ConcurrentHashMap<>();
    n o;
    /* access modifiers changed from: private */
    public String p = "";
    /* access modifiers changed from: private */
    public long q = 0;
    /* access modifiers changed from: private */
    public long r = 0;
    private String s = "";
    /* access modifiers changed from: private */
    public String t = "";
    private String u = "";
    private volatile String v = "";
    private int w = 0;

    /* compiled from: LdsMqttManager */
    public enum n {
        MQTT_CONNECT_STATUE_IDLE,
        MQTT_CONNECT_STATUE_CONNECTING,
        MQTT_CONNECT_STATUE_CONNECTED,
        MQTT_CONNECT_FAIL,
        MQTT_DISCONNECTED,
        MQTT_SHUT_DOWN;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void j(l x0, LDSBaseMqttService.MqttActionHandler x1, int x2, String x3) {
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 1470, new Class[]{l.class, LDSBaseMqttService.MqttActionHandler.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.Q(x1, x2, x3);
        }
    }

    static /* synthetic */ void k(l x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1471, new Class[]{l.class}, Void.TYPE).isSupported) {
            x0.u();
        }
    }

    static /* synthetic */ void l(l x0, Runnable x1) {
        Class[] clsArr = {l.class, Runnable.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1463, clsArr, Void.TYPE).isSupported) {
            x0.R(x1);
        }
    }

    static /* synthetic */ void m(l x0, n x1, String x2) {
        Class[] clsArr = {l.class, n.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 1464, clsArr, Void.TYPE).isSupported) {
            x0.Y(x1, x2);
        }
    }

    static /* synthetic */ MqttServiceRequestTaskBean o(l x0, String x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1465, new Class[]{l.class, String.class}, MqttServiceRequestTaskBean.class);
        return proxy.isSupported ? (MqttServiceRequestTaskBean) proxy.result : x0.y(x1);
    }

    static /* synthetic */ void p(l x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1466, new Class[]{l.class}, Void.TYPE).isSupported) {
            x0.U();
        }
    }

    static /* synthetic */ void q(l x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1467, new Class[]{l.class}, Void.TYPE).isSupported) {
            x0.P();
        }
    }

    static /* synthetic */ void r(l x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1468, new Class[]{l.class}, Void.TYPE).isSupported) {
            x0.V();
        }
    }

    static /* synthetic */ void s(l x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1469, new Class[]{l.class}, Void.TYPE).isSupported) {
            x0.T();
        }
    }

    private void R(Runnable runnable) {
        if (!PatchProxy.proxy(new Object[]{runnable}, this, changeQuickRedirect, false, 1435, new Class[]{Runnable.class}, Void.TYPE).isSupported) {
            this.j.post(runnable);
        }
    }

    private l() {
        n nVar = new n();
        this.o = nVar;
        nVar.f();
        this.o.a(new e());
        this.o.a(new f());
        this.o.a(new g());
    }

    /* compiled from: LdsMqttManager */
    public class e extends com.leedarson.mqtt.keepstragys.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void a(long currentTimespan) {
            Object[] objArr = {new Long(currentTimespan)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1472, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                super.a(currentTimespan);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1473, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.mqtt.logs.b.b(" Mqtt 服务连接状态活跃检测----> _currentMqttConnectStatue=" + l.this.m);
                if (l.this.m != n.MQTT_SHUT_DOWN && l.this.m != n.MQTT_CONNECT_STATUE_CONNECTED && l.this.m != n.MQTT_CONNECT_STATUE_CONNECTING) {
                    if (TextUtils.isEmpty(SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", ""))) {
                        com.leedarson.mqtt.logs.b.a("当前用户未登陆，不需要重连");
                    } else {
                        l.this.F(false, H5ActionName.ACTION_RECONNECT);
                    }
                }
            }
        }
    }

    /* compiled from: LdsMqttManager */
    public class f extends com.leedarson.mqtt.keepstragys.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void a(long currentTimespan) {
            f fVar;
            boolean z = true;
            if (!PatchProxy.proxy(new Object[]{new Long(currentTimespan)}, this, changeQuickRedirect, false, 1478, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                f fVar2 = this;
                super.a(currentTimespan);
                Set<Integer> _keySets = l.this.n.keySet();
                ArrayList<Integer> _targetKeysToDelete = new ArrayList<>();
                for (Integer key : _keySets) {
                    MqttServiceRequestTaskBean _item = (MqttServiceRequestTaskBean) l.this.n.get(key);
                    if (_item != null && _item.timeOutDeadline < System.currentTimeMillis()) {
                        Pair<Integer, String> _resultPair = _item.timeOutToAnylizeReason();
                        switch (d.a[_item.requestType.ordinal()]) {
                            case 1:
                            case 2:
                                if (_item.flagEnableDelete) {
                                    fVar = fVar2;
                                    break;
                                } else {
                                    _item.flagEnableDelete = true;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Mqtt自检 消息任务投递超时 topic=");
                                    sb.append(_item.topic);
                                    sb.append(" taskId=");
                                    sb.append(_item.taskId);
                                    sb.append(" timeOutTime=");
                                    f fVar3 = fVar2;
                                    sb.append(_item.timeOutDeadline);
                                    sb.append(" currentTime=");
                                    sb.append(System.currentTimeMillis());
                                    sb.append(", code=");
                                    sb.append(_resultPair.getFirst());
                                    sb.append(", desc=");
                                    sb.append(_resultPair.getSecond());
                                    sb.append(", topic=");
                                    sb.append(_item.topic);
                                    sb.append(", taskStatue=");
                                    sb.append(_item.taskDeliveryStatue);
                                    sb.append(", message=");
                                    sb.append(_item.requestParams);
                                    String _tip = sb.toString();
                                    fVar = fVar3;
                                    l.l(l.this, new b(_item, _resultPair, _tip));
                                    com.leedarson.mqtt.logs.a _reporter = com.leedarson.mqtt.logs.a.e(_item.taskId + "");
                                    MqttLogStepBean _timeOutStep = new MqttLogStepBean(_item.requestType == MqttServiceRequestTaskBean.MqttRequestType.MQTT_PUBLISH ? MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_ACK : MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_RESP);
                                    _timeOutStep.putRequest("topic", _item.topic);
                                    _timeOutStep.putRequest("message", new String(_item.mqttMessage.c()));
                                    _timeOutStep.putRequest("tip", _tip);
                                    _timeOutStep.endTrace(_resultPair.getFirst().intValue(), _resultPair.getSecond() + ", topic=" + _item.topic);
                                    _reporter.j("publish").g(_timeOutStep).h();
                                    com.leedarson.mqtt.logs.b.a(_tip);
                                    _targetKeysToDelete.add(Integer.valueOf(_item.taskId));
                                    break;
                                }
                            case 3:
                            case 4:
                                if (_item.flagEnableDelete) {
                                    fVar = fVar2;
                                    break;
                                } else {
                                    _item.flagEnableDelete = z;
                                    String topics = new Gson().toJson((Object) _item.topics);
                                    String _tip2 = "Mqtt自检 消息订阅超时 topics=" + topics + ", code=" + _resultPair.getFirst() + ", desc=" + _resultPair.getSecond() + ", taskStatue=" + _item.taskDeliveryStatue;
                                    com.leedarson.mqtt.logs.a _reporter2 = com.leedarson.mqtt.logs.a.e(_item.taskId + "");
                                    MqttServiceRequestTaskBean.MqttRequestType mqttRequestType = _item.requestType;
                                    MqttServiceRequestTaskBean.MqttRequestType mqttRequestType2 = MqttServiceRequestTaskBean.MqttRequestType.MQTT_SUBSCRIBES;
                                    MqttLogStepBean _subscribeTimeoutCheckStep = new MqttLogStepBean(mqttRequestType == mqttRequestType2 ? MqttLogStepBean.STEP_OF_MQTT_SUBSCRIBE_PROCESS : MqttLogStepBean.STEP_OF_MQTT_UN_SUBSCRIBE_PROCESS);
                                    _subscribeTimeoutCheckStep.putRequest("topics", topics);
                                    _subscribeTimeoutCheckStep.endTrace(4082, "(4082)超时10s,未收到unsuback " + topics);
                                    _reporter2.j(_item.requestType == mqttRequestType2 ? "subscribe" : "unsubscribe").g(_subscribeTimeoutCheckStep).h();
                                    l.l(l.this, new a(_item, _resultPair, _tip2));
                                    com.leedarson.mqtt.logs.b.a(_tip2);
                                    fVar = fVar2;
                                    break;
                                }
                            default:
                                fVar = fVar2;
                                break;
                        }
                    } else {
                        fVar = fVar2;
                    }
                    fVar2 = fVar;
                    z = true;
                }
                f fVar4 = fVar2;
                for (int i = 0; i < _targetKeysToDelete.size(); i++) {
                    l.this.n.remove(_targetKeysToDelete.get(i));
                }
            }
        }

        static /* synthetic */ void b(MqttServiceRequestTaskBean _item, kotlin.n _resultPair, String _tip) {
            if (!PatchProxy.proxy(new Object[]{_item, _resultPair, _tip}, (Object) null, changeQuickRedirect, true, 1480, new Class[]{MqttServiceRequestTaskBean.class, kotlin.n.class, String.class}, Void.TYPE).isSupported) {
                _item.callBackToBzHandler.onActionFail(((Integer) _resultPair.getFirst()).intValue(), _item.taskId + "", ((String) _resultPair.getSecond()) + _tip);
            }
        }

        static /* synthetic */ void c(MqttServiceRequestTaskBean _item, kotlin.n _resultPair, String _tip) {
            if (!PatchProxy.proxy(new Object[]{_item, _resultPair, _tip}, (Object) null, changeQuickRedirect, true, 1479, new Class[]{MqttServiceRequestTaskBean.class, kotlin.n.class, String.class}, Void.TYPE).isSupported) {
                _item.callBackToBzHandler.onActionFail(((Integer) _resultPair.getFirst()).intValue(), _item.taskId + "", ((String) _resultPair.getSecond()) + _tip);
            }
        }
    }

    /* compiled from: LdsMqttManager */
    public static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[MqttServiceRequestTaskBean.MqttRequestType.values().length];
            a = iArr;
            try {
                iArr[MqttServiceRequestTaskBean.MqttRequestType.MQTT_PUBLISH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[MqttServiceRequestTaskBean.MqttRequestType.MQTT_PUBLISH_WITH_RESP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[MqttServiceRequestTaskBean.MqttRequestType.MQTT_SUBSCRIBES.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[MqttServiceRequestTaskBean.MqttRequestType.MQTT_UN_SUBSCRIBES.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* compiled from: LdsMqttManager */
    public class g extends com.leedarson.mqtt.keepstragys.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void a(long currentTimespan) {
            Object[] objArr = {new Long(currentTimespan)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1481, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                super.a(currentTimespan);
            }
        }
    }

    public static l C() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1436, new Class[0], l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        if (a == null) {
            a = new l();
        }
        return a;
    }

    private void G(LdsMqttConfigOptionBean _options, LDSBaseMqttService.MqttActionHandler _handler) {
        if (!PatchProxy.proxy(new Object[]{_options, _handler}, this, changeQuickRedirect, false, 1437, new Class[]{LdsMqttConfigOptionBean.class, LDSBaseMqttService.MqttActionHandler.class}, Void.TYPE).isSupported) {
            this.c = new org.eclipse.paho.client.mqttv3.j();
            this.e = _options;
            try {
                if (!TextUtils.isEmpty(_options._userName) && !TextUtils.isEmpty(_options._password)) {
                    if (!TextUtils.isEmpty(_options._clientId)) {
                        this.c.z(_options._userName);
                        this.c.y(_options._password.toCharArray());
                        this.c.s(false);
                        this.c.t(true);
                        this.c.w(1000);
                        this.c.v(40);
                        this.c.u(this.l);
                        this.c.x(4);
                        if (TextUtils.isEmpty(_options._willLeaveTopic) && TextUtils.isEmpty(_options._willLeaveMsg)) {
                            this.c.B(_options._willLeaveTopic, _options._willLeaveMsg.getBytes(StandardCharsets.UTF_8), 1, false);
                        }
                        org.eclipse.paho.client.mqttv3.a aVar = new org.eclipse.paho.client.mqttv3.a();
                        this.d = aVar;
                        aVar.b(true);
                        this.d.c(100);
                        this.d.e(false);
                        this.d.d(false);
                        if (_handler != null) {
                            JSONObject _responseObj = new JSONObject();
                            _responseObj.put("desc", (Object) "初始化MqttOptions成功");
                            _handler.onActionSuccess("", _responseObj);
                        }
                        return;
                    }
                }
                com.leedarson.mqtt.logs.b.a("初始化mqttOptions失败（用户名|||密码为空||clientId）");
            } catch (Exception e2) {
                if (_handler != null) {
                    _handler.onActionFail(-4000001, "", "初始化mqttOption失败 " + e2.toString());
                }
                com.leedarson.mqtt.logs.b.a("初始化MqttOptions失败(" + e2.toString() + ")");
            }
        }
    }

    public JSONObject w(ApiMqttConfigBean apiMqttConfigBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{apiMqttConfigBean}, this, changeQuickRedirect, false, 1438, new Class[]{ApiMqttConfigBean.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject _willLeaveMsgObj = new JSONObject();
        try {
            _willLeaveMsgObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) "user");
            _willLeaveMsgObj.put(FirebaseAnalytics.Param.METHOD, (Object) "disconnect");
            _willLeaveMsgObj.put("seq", 0);
            _willLeaveMsgObj.put("srcAddr", (Object) apiMqttConfigBean.clientId);
            JSONObject _payloadObj = new JSONObject();
            _payloadObj.put("timespamp", System.currentTimeMillis());
            _payloadObj.put("create", (Object) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            _willLeaveMsgObj.put("payload", (Object) _payloadObj);
            return _willLeaveMsgObj;
        } catch (JSONException exception) {
            exception.printStackTrace();
            return _willLeaveMsgObj;
        }
    }

    public void F(boolean forceUpdateRemote, String ref) {
        if (!PatchProxy.proxy(new Object[]{new Byte(forceUpdateRemote ? (byte) 1 : 0), ref}, this, changeQuickRedirect, false, 1439, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("收到启动 mqtt服务请求  forceUpdateRemote=" + forceUpdateRemote + " , ref=" + ref);
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            n nVar = this.m;
            if (nVar == n.MQTT_CONNECT_STATUE_CONNECTED) {
                com.leedarson.mqtt.logs.b.b("initAndConnect  mqtt 已连接成功，不需要重新触发");
            } else if (nVar == n.MQTT_CONNECT_STATUE_CONNECTING) {
                com.leedarson.mqtt.logs.b.b("initAndConnect  mqtt 正在连接中，不需要重新触发");
            } else if (TextUtils.isEmpty(accessToken) || BuildConfig.TRAVIS.equals(accessToken)) {
                com.leedarson.mqtt.logs.b.b("initAndConnect  mqtt 用户未登陆，中断mqtt连接...");
            } else {
                n nVar2 = this.o;
                if (nVar2 != null) {
                    nVar2.f();
                }
                if ("loginConnect".equals(ref)) {
                    this.q = System.currentTimeMillis();
                } else if (H5ActionName.ACTION_RECONNECT.equals(ref)) {
                    this.r = System.currentTimeMillis();
                }
                String str = v() + "";
                this.p = str;
                this.s = ref;
                com.leedarson.mqtt.logs.a _reporter = com.leedarson.mqtt.logs.a.e(str);
                _reporter.j("connect").i(ref);
                MqttLogStepBean _connectFetchConfig = new MqttLogStepBean(MqttLogStepBean.STEP_OF_FETCH_MQTT_CONFIG);
                _connectFetchConfig.putRequest("forceUpdateRemote", forceUpdateRemote + "");
                _reporter.g(_connectFetchConfig);
                this.h.b(new com.leedarson.mqtt.repos.h().g(forceUpdateRemote).c(com.leedarson.base.http.observer.l.c()).I(new h(this, _connectFetchConfig, ref), new e(_connectFetchConfig, _reporter)));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J */
    public /* synthetic */ void K(MqttLogStepBean _connectFetchConfig, String ref, ApiMqttConfigBean apiMqttConfigBean) {
        Class[] clsArr = {MqttLogStepBean.class, String.class, ApiMqttConfigBean.class};
        if (!PatchProxy.proxy(new Object[]{_connectFetchConfig, ref, apiMqttConfigBean}, this, changeQuickRedirect, false, 1462, clsArr, Void.TYPE).isSupported) {
            LdsMqttConfigOptionBean _config = new LdsMqttConfigOptionBean();
            _config._clientId = apiMqttConfigBean.clientId;
            _config._password = apiMqttConfigBean.password;
            _config._useSsl = true;
            _config._serverUrl = "wss://" + apiMqttConfigBean.host + "/mqtt";
            _config._userName = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
            _config._willLeaveTopic = "/userId/user/disconnect".replace("userId", apiMqttConfigBean.clientId);
            _config._willLeaveMsg = w(apiMqttConfigBean).toString();
            _config._context = BaseApplication.b();
            _connectFetchConfig.putResponse("data", new Gson().toJson((Object) apiMqttConfigBean));
            _connectFetchConfig.endTrace(200, "获取mqttConfig成功");
            G(_config, new h(ref));
        }
    }

    /* compiled from: LdsMqttManager */
    public class h implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        h(String str) {
            this.a = str;
        }

        public void onActionSuccess(String str, JSONObject jSONObject) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, jSONObject}, this, changeQuickRedirect, false, 1482, clsArr, Void.TYPE).isSupported) {
                com.leedarson.mqtt.logs.b.b("Mqtt初始化配参（成功） ref=" + this.a);
                l.k(l.this);
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1483, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                com.leedarson.mqtt.logs.b.b("Mqtt初始化配参（失败） code=" + code + " , taskId=" + taskId + " , desc=" + desc);
            }
        }
    }

    static /* synthetic */ void L(MqttLogStepBean _connectFetchConfig, com.leedarson.mqtt.logs.a _reporter, Throwable throwable) {
        Class[] clsArr = {MqttLogStepBean.class, com.leedarson.mqtt.logs.a.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{_connectFetchConfig, _reporter, throwable}, (Object) null, changeQuickRedirect, true, 1461, clsArr, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.a("初始化mqtt配参失败  throwable=" + throwable.toString());
            _connectFetchConfig.putResponse("data", throwable.toString());
            _connectFetchConfig.endTrace(BaseResp.ERR_MSG_SEND_FAIL, "mqtt 配置文件获取失败");
            _reporter.h();
        }
    }

    private void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1440, new Class[0], Void.TYPE).isSupported) {
            n nVar = this.m;
            n nVar2 = n.MQTT_CONNECT_STATUE_CONNECTING;
            if (nVar == nVar2) {
                com.leedarson.mqtt.logs.b.b("Mqtt 正在连接中......，不需重复调用");
            } else if (nVar == n.MQTT_CONNECT_STATUE_CONNECTED) {
                com.leedarson.mqtt.logs.b.b("Mqtt 已连接成功,不需重复调用....");
            } else {
                int taskId = v();
                this.k++;
                Y(nVar2, "Mqtt 连接中...");
                if (this.f == null) {
                    this.f = new i(this.e._clientId);
                }
                MqttLogStepBean _connectStep = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_ENGINE_CONNECT);
                com.leedarson.mqtt.logs.a.e(this.p).g(_connectStep);
                if (this.g == null) {
                    this.g = new j(taskId, this.e._clientId, _connectStep);
                }
                try {
                    if (this.b == null) {
                        LdsMqttConfigOptionBean ldsMqttConfigOptionBean = this.e;
                        this.b = new MqttAndroidClient(ldsMqttConfigOptionBean._context, ldsMqttConfigOptionBean._serverUrl, ldsMqttConfigOptionBean._clientId);
                    }
                    this.b.P(this.f);
                    this.b.l(this.c, (Object) null, this.g);
                } catch (Exception e2) {
                    com.leedarson.mqtt.logs.b.a("Mqtt连接-失败 e:" + e2.toString());
                    _connectStep.endTrace(BaseResp.ERR_MSG_SEND_FAIL, "连接失败(" + e2.toString() + ")");
                    com.leedarson.mqtt.logs.a.e(this.p).g(_connectStep);
                    com.leedarson.mqtt.logs.a.e(this.p).h();
                    Y(n.MQTT_CONNECT_FAIL, "mqttClient连接失败-->" + e2.toString());
                }
            }
        }
    }

    /* compiled from: LdsMqttManager */
    public class i extends com.leedarson.mqtt.listerners.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        i(String clientId) {
            super(clientId);
        }

        public void connectComplete(boolean reconnect, String serverURI) {
            Object[] objArr = {new Byte(reconnect ? (byte) 1 : 0), serverURI};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1484, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
                super.connectComplete(reconnect, serverURI);
                com.leedarson.mqtt.logs.b.b("Mqtt 连接 - (连接成功/完成连接)");
                l.m(l.this, n.MQTT_CONNECT_STATUE_CONNECTED, "Mqtt 连接中 - (连接成功)");
                org.greenrobot.eventbus.c.c().l(new NativeMqttStatusChangeEvent(1));
                com.leedarson.base.http.observer.l.a.b(new d(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1488, new Class[0], Void.TYPE).isSupported) {
                l.p(l.this);
                l.q(l.this);
                l.r(l.this);
                l.s(l.this);
            }
        }

        public void connectionLost(Throwable cause) {
            if (!PatchProxy.proxy(new Object[]{cause}, this, changeQuickRedirect, false, 1485, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                super.connectionLost(cause);
                com.leedarson.mqtt.logs.b.b("Mqtt 连接中 - (连接断开)");
                if (l.this.m != n.MQTT_SHUT_DOWN) {
                    l.m(l.this, n.MQTT_DISCONNECTED, "Mqtt 连接断开");
                    com.leedarson.mqtt.logs.a _reporter = com.leedarson.mqtt.logs.a.e(l.this.p);
                    MqttLogStepBean _stepOfConnect = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_ENGINE_CONNECT);
                    if (cause != null) {
                        _stepOfConnect.endTrace(BaseResp.ERR_MSG_SEND_FAIL, "连接断开(连接失败) cause=" + cause.toString());
                    } else {
                        _stepOfConnect.endTrace(BaseResp.ERR_MSG_SEND_FAIL, "连接断开(连接失败) cause= null 无具体原因");
                    }
                    _reporter.j("connect").i("connectedThenLosted").g(_stepOfConnect).h();
                } else {
                    com.leedarson.mqtt.logs.a _reporter2 = com.leedarson.mqtt.logs.a.e(l.this.p);
                    MqttLogStepBean _stepOfConnect2 = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_ENGINE_CONNECT);
                    _stepOfConnect2.endTrace(200, "退出登录，断开连接 connectionLost");
                    _reporter2.j("disconnect").g(_stepOfConnect2).h();
                }
                org.greenrobot.eventbus.c.c().l(new NativeMqttStatusChangeEvent(0));
            }
        }

        public void messageArrived(String str, org.eclipse.paho.client.mqttv3.l lVar) {
            if (!PatchProxy.proxy(new Object[]{str, lVar}, this, changeQuickRedirect, false, 1486, new Class[]{String.class, org.eclipse.paho.client.mqttv3.l.class}, Void.TYPE).isSupported) {
                org.eclipse.paho.client.mqttv3.l message = lVar;
                String topic = str;
                super.messageArrived(topic, message);
                String _receiveMessageStr = new String(message.c());
                com.leedarson.mqtt.logs.b.b("Mqtt 消息通知 - (收到消息)  topic=" + topic + " , message=" + _receiveMessageStr + " , messageId=" + message.b());
                JSONObject _receiveMessageObj = new JSONObject(_receiveMessageStr);
                if (!topic.contains("setDevAttrResp")) {
                    org.greenrobot.eventbus.c.c().l(new NativeMqttMessageArrivedEvent(topic, _receiveMessageStr));
                }
                if (_receiveMessageObj.has("seq")) {
                    String _seq = _receiveMessageObj.getString("seq");
                    MqttServiceRequestTaskBean _taskItem = l.o(l.this, _seq);
                    if (_taskItem != null) {
                        int taskId = _taskItem.taskId;
                        JSONObject _responseDataObj = new JSONObject(_receiveMessageObj.toString());
                        _responseDataObj.put("topic", (Object) _taskItem.topic);
                        _taskItem.taskDeliveryStatue = MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.BROKER_ACK_WITH_RESP;
                        _taskItem.updateRespTime();
                        MqttMessageConfigBean mqttMessageConfigBean = _taskItem.config;
                        if (mqttMessageConfigBean != null && !mqttMessageConfigBean.onlyPubAck) {
                            com.leedarson.mqtt.logs.b.b("Mqtt 消息抄收 -->回执给业务层（resp）&& 移除掉缓存消息 --> 匹配到 seq=" + _seq + ", topic=" + _taskItem.topic + ", taskId=" + _taskItem.taskId);
                            LDSBaseMqttService.MqttActionHandler mqttActionHandler = _taskItem.callBackToBzHandler;
                            StringBuilder sb = new StringBuilder();
                            sb.append(_taskItem.taskId);
                            sb.append("");
                            mqttActionHandler.onActionSuccess(sb.toString(), _responseDataObj);
                            com.leedarson.mqtt.logs.a _reporter = com.leedarson.mqtt.logs.a.e(_taskItem.taskId + "");
                            MqttLogStepBean _step = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_RESP);
                            _reporter.j("publish");
                            _step.putRequest("topic", topic);
                            _step.putRequest("payload", new String(_taskItem.mqttMessage.c()));
                            _step.endTrace(200, "发布消息成功(resp) seq=" + _taskItem.seqNum + ", " + _taskItem.topic);
                            _reporter.g(_step);
                            _reporter.h();
                            l.this.n.remove(Integer.valueOf(taskId));
                            return;
                        }
                        return;
                    }
                    String tip = "收到消息通知(unmatch) seq=" + _seq + ", topic=" + topic;
                    com.leedarson.mqtt.logs.a _reporter2 = com.leedarson.mqtt.logs.a.e(l.this.v() + "");
                    MqttLogStepBean _step2 = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_RESP);
                    _reporter2.j("receiveMessage");
                    _step2.putRequest("topic", topic);
                    _step2.putRequest("payload", new String(message.c()));
                    _step2.endTrace(200, tip);
                    _reporter2.g(_step2);
                    _reporter2.h();
                    com.leedarson.mqtt.logs.b.b(tip);
                    com.leedarson.mqtt.utils.a.a(topic, message);
                    return;
                }
                String tip2 = "收到消息通知(unmatch) seq=(缺失), topic=" + topic;
                com.leedarson.mqtt.logs.b.b(tip2);
                com.leedarson.mqtt.logs.a _reporter3 = com.leedarson.mqtt.logs.a.e(l.this.v() + "");
                MqttLogStepBean _step3 = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_RESP);
                _reporter3.j("receiveMessage");
                _step3.putRequest("topic", topic);
                _step3.putRequest("payload", new String(message.c()));
                _step3.endTrace(200, tip2);
                _reporter3.g(_step3);
                _reporter3.h();
                com.leedarson.mqtt.utils.a.a(topic, message);
            }
        }

        public void deliveryComplete(org.eclipse.paho.client.mqttv3.d token) {
            if (!PatchProxy.proxy(new Object[]{token}, this, changeQuickRedirect, false, 1487, new Class[]{org.eclipse.paho.client.mqttv3.d.class}, Void.TYPE).isSupported) {
                super.deliveryComplete(token);
                try {
                    org.eclipse.paho.client.mqttv3.l message = token.a();
                    if (message != null) {
                        try {
                            JSONObject _messageObj = new JSONObject(new String(message.c()));
                            if (_messageObj.has("seq")) {
                                String _seq = _messageObj.getString("seq");
                                int taskId = message.b();
                                MqttServiceRequestTaskBean _taskItem = (MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(taskId));
                                if (_taskItem != null && _taskItem.isSeqMatch(_seq)) {
                                    JSONObject _responseDataObj = new JSONObject();
                                    _responseDataObj.put("topic", (Object) _taskItem.topic);
                                    _responseDataObj.put("message", (Object) _messageObj);
                                    _taskItem.updateAckTime();
                                    _taskItem.taskDeliveryStatue = MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.BROKER_ACK;
                                    MqttMessageConfigBean mqttMessageConfigBean = _taskItem.config;
                                    if (mqttMessageConfigBean != null && mqttMessageConfigBean.onlyPubAck) {
                                        com.leedarson.mqtt.logs.b.b("Mqtt 消息投递 -->回执给业务层 && 移除掉缓存消息 --> 匹配到 seq=" + _seq + ", topic=" + _taskItem.topic + ", taskId=" + taskId);
                                        LDSBaseMqttService.MqttActionHandler mqttActionHandler = _taskItem.callBackToBzHandler;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(_taskItem.taskId);
                                        sb.append("");
                                        mqttActionHandler.onActionSuccess(sb.toString(), _responseDataObj);
                                        com.leedarson.mqtt.logs.a _reporter = com.leedarson.mqtt.logs.a.e(_taskItem.taskId + "");
                                        MqttLogStepBean _step = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_ACK);
                                        _reporter.j("publish");
                                        _step.putRequest("topic", _taskItem.topic);
                                        _step.putRequest("payload", new String(message.c()));
                                        _step.endTrace(200, "发布消息成功(ack) seq=" + _taskItem.seqNum + ", " + _taskItem.topic);
                                        _reporter.g(_step);
                                        _reporter.h();
                                        l.this.n.remove(Integer.valueOf(taskId));
                                    }
                                }
                            }
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                            com.leedarson.mqtt.logs.b.a("Mqtt 消息投递 --> message转化失败 exception=" + exception.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            com.leedarson.mqtt.logs.b.a("Mqtt 消息投递 --> message转化失败 e=" + e.toString());
                        }
                        return;
                    }
                    com.leedarson.mqtt.logs.b.a("Mqtt 消息投递失败  messageId=" + token.a().b() + " ,topics=");
                } catch (MqttException e2) {
                    e2.printStackTrace();
                    com.leedarson.mqtt.logs.b.a("Mqtt 消息投递失败  e=" + e2.toString());
                }
            }
        }
    }

    /* compiled from: LdsMqttManager */
    public class j extends com.leedarson.mqtt.listerners.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MqttLogStepBean c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(int taskId, String clientId, MqttLogStepBean mqttLogStepBean) {
            super(taskId, clientId);
            this.c = mqttLogStepBean;
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0079, code lost:
            if (r3.equals("hotStartConnect") != false) goto L_0x007d;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onSuccess(org.eclipse.paho.client.mqttv3.f r10) {
            /*
                r9 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r8 = 0
                r1[r8] = r10
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<org.eclipse.paho.client.mqttv3.f> r2 = org.eclipse.paho.client.mqttv3.f.class
                r6[r8] = r2
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 1489(0x5d1, float:2.087E-42)
                r2 = r9
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r1 = r1.isSupported
                if (r1 == 0) goto L_0x001d
                return
            L_0x001d:
                r1 = r9
                super.onSuccess(r10)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "Mqtt 连接通道  （成功）"
                r2.append(r3)
                int r3 = r1.a()
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.leedarson.mqtt.logs.b.b(r2)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                com.leedarson.mqtt.l r3 = com.leedarson.mqtt.l.this
                java.lang.String r3 = r3.p
                com.leedarson.mqtt.logs.a r3 = com.leedarson.mqtt.logs.a.e(r3)
                java.lang.String r3 = r3.d()
                r4 = -1
                int r5 = r3.hashCode()
                switch(r5) {
                    case -1104619755: goto L_0x0073;
                    case -523778260: goto L_0x0069;
                    case 644327745: goto L_0x005f;
                    case 990157655: goto L_0x0055;
                    default: goto L_0x0054;
                }
            L_0x0054:
                goto L_0x007c
            L_0x0055:
                java.lang.String r0 = "reconnect"
                boolean r0 = r3.equals(r0)
                if (r0 == 0) goto L_0x0054
                r0 = r8
                goto L_0x007d
            L_0x005f:
                java.lang.String r0 = "loginConnect"
                boolean r0 = r3.equals(r0)
                if (r0 == 0) goto L_0x0054
                r0 = 2
                goto L_0x007d
            L_0x0069:
                java.lang.String r0 = "coldStartConnect"
                boolean r0 = r3.equals(r0)
                if (r0 == 0) goto L_0x0054
                r0 = 3
                goto L_0x007d
            L_0x0073:
                java.lang.String r5 = "hotStartConnect"
                boolean r3 = r3.equals(r5)
                if (r3 == 0) goto L_0x0054
                goto L_0x007d
            L_0x007c:
                r0 = r4
            L_0x007d:
                switch(r0) {
                    case 0: goto L_0x00bd;
                    case 1: goto L_0x00a9;
                    case 2: goto L_0x0095;
                    case 3: goto L_0x0081;
                    default: goto L_0x0080;
                }
            L_0x0080:
                goto L_0x00d1
            L_0x0081:
                java.lang.String r0 = "冷启动连接成功,耗时"
                r2.append(r0)
                long r3 = java.lang.System.currentTimeMillis()
                com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()
                long r5 = r0.a1
                long r3 = r3 - r5
                r2.append(r3)
                goto L_0x00d1
            L_0x0095:
                java.lang.String r0 = "重新登陆连接成功,耗时"
                r2.append(r0)
                long r3 = java.lang.System.currentTimeMillis()
                com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.this
                long r5 = r0.q
                long r3 = r3 - r5
                r2.append(r3)
                goto L_0x00d1
            L_0x00a9:
                java.lang.String r0 = "热启动连接成功,耗时"
                r2.append(r0)
                long r3 = java.lang.System.currentTimeMillis()
                com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()
                long r5 = r0.p2
                long r3 = r3 - r5
                r2.append(r3)
                goto L_0x00d1
            L_0x00bd:
                java.lang.String r0 = "重连连接成功,耗时"
                r2.append(r0)
                long r3 = java.lang.System.currentTimeMillis()
                com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.this
                long r5 = r0.r
                long r3 = r3 - r5
                r2.append(r3)
            L_0x00d1:
                java.lang.String r0 = " ms"
                r2.append(r0)
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r3 = "尝试次数 "
                r0.append(r3)
                com.leedarson.mqtt.l r3 = com.leedarson.mqtt.l.this
                int r3 = r3.k
                r0.append(r3)
                java.lang.String r0 = r0.toString()
                r2.append(r0)
                com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.this
                int unused = r0.k = r8
                com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.this
                java.lang.String r0 = r0.p
                com.leedarson.mqtt.logs.a r0 = com.leedarson.mqtt.logs.a.e(r0)
                java.util.ArrayList<com.leedarson.mqtt.logs.MqttLogStepBean> r0 = r0.c
                int r0 = r0.size()
                r3 = 200(0xc8, float:2.8E-43)
                if (r0 != 0) goto L_0x013d
                com.leedarson.mqtt.logs.MqttLogStepBean r0 = r1.c
                java.lang.String r4 = r2.toString()
                r0.endTrace(r3, r4)
                com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.this
                java.lang.String r0 = r0.p
                com.leedarson.mqtt.logs.a r0 = com.leedarson.mqtt.logs.a.e(r0)
                com.leedarson.mqtt.logs.MqttLogStepBean r4 = r1.c
                r0.g(r4)
                com.leedarson.mqtt.logs.MqttLogStepBean r0 = new com.leedarson.mqtt.logs.MqttLogStepBean
                java.lang.String r4 = "MqttEngineConnect"
                r0.<init>(r4)
                java.lang.String r4 = r2.toString()
                r0.endTrace(r3, r4)
                com.leedarson.mqtt.l r3 = com.leedarson.mqtt.l.this
                java.lang.String r3 = r3.p
                com.leedarson.mqtt.logs.a r3 = com.leedarson.mqtt.logs.a.e(r3)
                r3.g(r0)
                goto L_0x0155
            L_0x013d:
                com.leedarson.mqtt.logs.MqttLogStepBean r0 = r1.c
                java.lang.String r4 = r2.toString()
                r0.endTrace(r3, r4)
                com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.this
                java.lang.String r0 = r0.p
                com.leedarson.mqtt.logs.a r0 = com.leedarson.mqtt.logs.a.e(r0)
                com.leedarson.mqtt.logs.MqttLogStepBean r3 = r1.c
                r0.g(r3)
            L_0x0155:
                com.leedarson.mqtt.l r0 = com.leedarson.mqtt.l.this
                java.lang.String r0 = r0.p
                com.leedarson.mqtt.logs.a r0 = com.leedarson.mqtt.logs.a.e(r0)
                r0.h()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.mqtt.l.j.onSuccess(org.eclipse.paho.client.mqttv3.f):void");
        }

        public void onFailure(org.eclipse.paho.client.mqttv3.f asyncActionToken, Throwable exception) {
            if (!PatchProxy.proxy(new Object[]{asyncActionToken, exception}, this, changeQuickRedirect, false, 1490, new Class[]{org.eclipse.paho.client.mqttv3.f.class, Throwable.class}, Void.TYPE).isSupported) {
                super.onFailure(asyncActionToken, exception);
                com.leedarson.mqtt.logs.b.a("Mqtt 连接 （异常） exception=" + exception.toString() + " taskId=" + a());
                if (l.this.m != n.MQTT_SHUT_DOWN) {
                    l lVar = l.this;
                    int unused = lVar.l = lVar.l + 2;
                    if (l.this.l >= 15) {
                        int unused2 = l.this.l = 15;
                    }
                    Pair<Integer, String> _errorDetail = m.a(exception.toString());
                    MqttLogStepBean mqttLogStepBean = this.c;
                    mqttLogStepBean.endTrace(BaseResp.ERR_MSG_SEND_FAIL, "连接失败(" + _errorDetail.getSecond() + ")");
                    com.leedarson.mqtt.logs.a.e(l.this.p).g(this.c);
                    com.leedarson.mqtt.logs.a.e(l.this.p).h();
                    l lVar2 = l.this;
                    n nVar = n.MQTT_CONNECT_FAIL;
                    l.m(lVar2, nVar, "Mqtt 连接失败 onFailure=" + exception.toString() + " ,taskId=" + a());
                    if (_errorDetail.getFirst().intValue() == 4) {
                        l.this.F(true, "hotStartConnect");
                    } else {
                        com.leedarson.mqtt.logs.b.a("Mqtt 连接失败 code=" + _errorDetail.getFirst() + " , detail=" + _errorDetail.getSecond());
                    }
                } else {
                    int unused3 = l.this.l = 6;
                    com.leedarson.mqtt.logs.b.a("Mqtt 连接当前被强制中断.....exception=" + exception.toString());
                    MqttLogStepBean _disconnectStep = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_ENGINE_DISCONNECT);
                    _disconnectStep.endTrace(200, "退出登录，断开连接");
                    com.leedarson.mqtt.logs.a.e(l.this.t).g(_disconnectStep).h();
                }
                org.greenrobot.eventbus.c.c().l(new NativeMqttStatusChangeEvent(0));
            }
        }
    }

    private MqttServiceRequestTaskBean y(String seq) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{seq}, this, changeQuickRedirect, false, 1441, new Class[]{String.class}, MqttServiceRequestTaskBean.class);
        if (proxy.isSupported) {
            return (MqttServiceRequestTaskBean) proxy.result;
        }
        for (Integer key : this.n.keySet()) {
            MqttServiceRequestTaskBean _item = this.n.get(key);
            if (_item.isSeqMatch(seq)) {
                return _item;
            }
        }
        return null;
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1442, new Class[0], Void.TYPE).isSupported) {
            if (this.b != null) {
                try {
                    this.t = v() + "";
                    MqttLogStepBean _disconnectStep = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_REQUEST_DISCONNECT);
                    _disconnectStep.endTrace(200, "执行mqtt连接断开服务");
                    com.leedarson.mqtt.logs.a.e(this.t).g(_disconnectStep).j("disconnect");
                    this.m = n.MQTT_SHUT_DOWN;
                    this.h.b(new com.leedarson.mqtt.repos.h().e().c(com.leedarson.base.http.observer.l.b()).I(f.c, c.c));
                    this.o.g();
                    t("disconnect mqtt client channel");
                    this.b.r();
                    this.b = null;
                    this.f = null;
                    this.g = null;
                } catch (MqttException e2) {
                    e2.printStackTrace();
                    com.leedarson.mqtt.logs.b.a("断开mqtt(失败) e=" + e2.toString());
                } catch (Exception e3) {
                    com.leedarson.mqtt.logs.b.a("断开mqtt(失败) Exception e=" + e3.toString());
                }
            }
        }
    }

    static /* synthetic */ void H(kotlin.n nVar) {
        if (!PatchProxy.proxy(new Object[]{nVar}, (Object) null, changeQuickRedirect, true, 1460, new Class[]{kotlin.n.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("mqttConfig 缓存删除成功");
        }
    }

    static /* synthetic */ void I(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 1459, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.a("mqttConfig 缓存删除失败 throwable=" + throwable.toString());
        }
    }

    public void W(String[] strArr, LDSBaseMqttService.MqttActionHandler mqttActionHandler, boolean z) {
        MqttServiceRequestTaskBean _task;
        int taskId;
        MqttLogStepBean _stepOfRequest;
        if (!PatchProxy.proxy(new Object[]{strArr, mqttActionHandler, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1443, new Class[]{String[].class, LDSBaseMqttService.MqttActionHandler.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            LDSBaseMqttService.MqttActionHandler _handler = mqttActionHandler;
            String[] topics = strArr;
            boolean flagResubscribe = z;
            int[] _qos = new int[topics.length];
            for (int i2 = 0; i2 < _qos.length; i2++) {
                _qos[i2] = 1;
            }
            int taskId2 = v();
            MqttServiceRequestTaskBean _task2 = new MqttServiceRequestTaskBean();
            if (flagResubscribe) {
                MqttServiceRequestTaskBean _oldTask = E(topics);
                if (_oldTask != null) {
                    taskId2 = _oldTask.taskId;
                    _task2 = _oldTask;
                }
                taskId = taskId2;
                _task = _task2;
            } else {
                this.n.put(Integer.valueOf(taskId2), _task2);
                taskId = taskId2;
                _task = _task2;
            }
            _task.flagEnableDelete = false;
            _task.taskId = taskId;
            _task.requestType = MqttServiceRequestTaskBean.MqttRequestType.MQTT_SUBSCRIBES;
            _task.topics = topics;
            _task.callBackToBzHandler = _handler;
            _task.taskDeliveryStatue = MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.WAITING_TO_DELIVER;
            long currentTimeMillis = System.currentTimeMillis();
            _task.createTime = currentTimeMillis;
            _task.timeOutDeadline = currentTimeMillis + 10000;
            com.leedarson.mqtt.logs.a _reporterSubscribe = com.leedarson.mqtt.logs.a.e(taskId + "");
            _reporterSubscribe.j("subscribe");
            MqttLogStepBean _stepOfRequest2 = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_SUBSCRIBE_REQUEST);
            _stepOfRequest2.putRequest("topics", new Gson().toJson((Object) topics));
            _stepOfRequest2.putRequest("retrySubscribe", flagResubscribe + "");
            _stepOfRequest2.putRequest("qos", "1");
            _stepOfRequest2.putRequest("taskId", taskId + "");
            _reporterSubscribe.g(_stepOfRequest2);
            if (this.m != n.MQTT_CONNECT_STATUE_CONNECTED) {
                com.leedarson.mqtt.logs.b.a("订阅Topics 失败（mqtt通道未连接成功）- 待重连成功后会自动处理...  topic=" + new Gson().toJson((Object) topics));
                return;
            }
            try {
                com.leedarson.mqtt.logs.b.b("收到mqtt 消息订阅 topics=" + new Gson().toJson((Object) topics) + " , taskId=" + taskId + ", flagResubscribe=" + flagResubscribe);
                MqttAndroidClient mqttAndroidClient = this.b;
                Integer valueOf = Integer.valueOf(taskId);
                int i3 = taskId;
                int i4 = taskId;
                k kVar = r1;
                boolean z2 = flagResubscribe;
                Integer num = valueOf;
                MqttServiceRequestTaskBean mqttServiceRequestTaskBean = _task;
                _stepOfRequest = _stepOfRequest2;
                try {
                    k kVar2 = new k(i3, topics, _stepOfRequest2, _reporterSubscribe, _handler);
                    mqttAndroidClient.o0(topics, _qos, num, kVar);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                int i5 = taskId;
                boolean z3 = flagResubscribe;
                MqttServiceRequestTaskBean mqttServiceRequestTaskBean2 = _task;
                _stepOfRequest = _stepOfRequest2;
                String tip = "Topic订阅失败" + e.toString();
                _stepOfRequest.putResponse("tip", tip);
                _stepOfRequest.endTrace(4081, "(4081）订阅失败, " + new Gson().toJson((Object) topics));
                _reporterSubscribe.h();
                Q(_handler, 4081, tip);
            }
        }
    }

    /* compiled from: LdsMqttManager */
    public class k extends com.leedarson.mqtt.listerners.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] c;
        final /* synthetic */ MqttLogStepBean d;
        final /* synthetic */ com.leedarson.mqtt.logs.a e;
        final /* synthetic */ LDSBaseMqttService.MqttActionHandler f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        k(int taskId, String[] strArr, MqttLogStepBean mqttLogStepBean, com.leedarson.mqtt.logs.a aVar, LDSBaseMqttService.MqttActionHandler mqttActionHandler) {
            super(taskId);
            this.c = strArr;
            this.d = mqttLogStepBean;
            this.e = aVar;
            this.f = mqttActionHandler;
        }

        public void onSuccess(org.eclipse.paho.client.mqttv3.f asyncActionToken) {
            if (!PatchProxy.proxy(new Object[]{asyncActionToken}, this, changeQuickRedirect, false, 1491, new Class[]{org.eclipse.paho.client.mqttv3.f.class}, Void.TYPE).isSupported) {
                super.onSuccess(asyncActionToken);
                MqttServiceRequestTaskBean _item = (MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(a()));
                if (_item != null) {
                    _item.flagEnableDelete = true;
                    String _tip = "subscribeTopics mqtt底层引擎触发订阅" + asyncActionToken.e();
                    com.leedarson.mqtt.logs.b.b(_tip);
                    JSONObject _resData = new JSONObject();
                    try {
                        _resData.put("topics", (Object) new JSONArray((Object) this.c));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                    this.d.putResponse("tip", _tip);
                    this.d.endTrace(200, "订阅消息成功, " + new Gson().toJson((Object) this.c));
                    this.e.h();
                    ((MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(((Integer) asyncActionToken.e()).intValue()))).taskDeliveryStatue = MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.MQTT_CLIENT_DELIVERED;
                    _item.callBackToBzHandler.onActionSuccess(a() + "", _resData);
                    return;
                }
                String _tip2 = "subscribeTopics mqtt底层引擎触发订阅（未匹配到taskId）taskId=" + a();
                com.leedarson.mqtt.logs.b.a(_tip2);
                this.d.putResponse("tip", _tip2);
                this.d.endTrace(200, "订阅消息成功" + new Gson().toJson((Object) this.c) + _tip2);
                this.e.h();
            }
        }

        public void onFailure(org.eclipse.paho.client.mqttv3.f asyncActionToken, Throwable exception) {
            if (!PatchProxy.proxy(new Object[]{asyncActionToken, exception}, this, changeQuickRedirect, false, 1492, new Class[]{org.eclipse.paho.client.mqttv3.f.class, Throwable.class}, Void.TYPE).isSupported) {
                super.onFailure(asyncActionToken, exception);
                MqttServiceRequestTaskBean _item = (MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(a()));
                String tipException = BuildConfig.TRAVIS;
                if (exception != null) {
                    tipException = exception.toString();
                }
                String tip = "Topic订阅失败  messageId:" + asyncActionToken.c() + tipException;
                this.d.putResponse("tip", tip);
                this.d.endTrace(4081, "(4081）订阅失败, " + new Gson().toJson((Object) this.c));
                this.e.h();
                if (_item != null) {
                    com.leedarson.mqtt.logs.b.a(tip);
                    l.j(l.this, _item.callBackToBzHandler, 4081, tip);
                    return;
                }
                l.j(l.this, this.f, 4081, tip);
                com.leedarson.mqtt.logs.b.a(tip + ",未匹配到 taskId=" + a());
            }
        }
    }

    public void X(String[] strArr, LDSBaseMqttService.MqttActionHandler mqttActionHandler) {
        if (!PatchProxy.proxy(new Object[]{strArr, mqttActionHandler}, this, changeQuickRedirect, false, 1444, new Class[]{String[].class, LDSBaseMqttService.MqttActionHandler.class}, Void.TYPE).isSupported) {
            LDSBaseMqttService.MqttActionHandler _handler = mqttActionHandler;
            String[] topics = strArr;
            int[] _qos = new int[topics.length];
            for (int i2 = 0; i2 < _qos.length; i2++) {
                _qos[i2] = 1;
            }
            int _taskId = v();
            MqttServiceRequestTaskBean _task = new MqttServiceRequestTaskBean();
            _task.taskId = _taskId;
            _task.topics = topics;
            _task.requestType = MqttServiceRequestTaskBean.MqttRequestType.MQTT_UN_SUBSCRIBES;
            _task.callBackToBzHandler = _handler;
            this.n.put(Integer.valueOf(_taskId), _task);
            com.leedarson.mqtt.logs.a _reporter = com.leedarson.mqtt.logs.a.e(_taskId + "");
            MqttLogStepBean _unscribeStep = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_UN_SUBSCRIBE_REQUEST);
            _reporter.j("unsubscribe").g(_unscribeStep);
            String _topics = new Gson().toJson((Object) topics);
            _unscribeStep.putRequest("topics", _topics);
            try {
                if (this.m != n.MQTT_CONNECT_STATUE_CONNECTED) {
                    try {
                        com.leedarson.mqtt.logs.b.a("取消订阅Topics 失败（mqtt通道未连接成功）- 待重连成功后处理...");
                    } catch (Exception e2) {
                        e = e2;
                        int[] iArr = _qos;
                        int i3 = _taskId;
                        String str = _topics;
                        Q(_handler, 4081, "Topic取消订阅失败" + e.toString());
                    }
                } else {
                    MqttAndroidClient mqttAndroidClient = this.b;
                    int[] iArr2 = _qos;
                    C0097l lVar = r9;
                    int i4 = _taskId;
                    Integer valueOf = Integer.valueOf(_task.taskId);
                    String str2 = _topics;
                    try {
                        C0097l lVar2 = new C0097l(_task.taskId, _unscribeStep, _topics, _reporter, topics, _handler);
                        mqttAndroidClient.a1(topics, valueOf, lVar);
                    } catch (Exception e3) {
                        e = e3;
                    }
                }
            } catch (Exception e4) {
                e = e4;
                int[] iArr3 = _qos;
                int i5 = _taskId;
                String str3 = _topics;
                Q(_handler, 4081, "Topic取消订阅失败" + e.toString());
            }
        }
    }

    /* renamed from: com.leedarson.mqtt.l$l  reason: collision with other inner class name */
    /* compiled from: LdsMqttManager */
    public class C0097l extends com.leedarson.mqtt.listerners.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MqttLogStepBean c;
        final /* synthetic */ String d;
        final /* synthetic */ com.leedarson.mqtt.logs.a e;
        final /* synthetic */ String[] f;
        final /* synthetic */ LDSBaseMqttService.MqttActionHandler g;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0097l(int taskId, MqttLogStepBean mqttLogStepBean, String str, com.leedarson.mqtt.logs.a aVar, String[] strArr, LDSBaseMqttService.MqttActionHandler mqttActionHandler) {
            super(taskId);
            this.c = mqttLogStepBean;
            this.d = str;
            this.e = aVar;
            this.f = strArr;
            this.g = mqttActionHandler;
        }

        public void onSuccess(org.eclipse.paho.client.mqttv3.f asyncActionToken) {
            if (!PatchProxy.proxy(new Object[]{asyncActionToken}, this, changeQuickRedirect, false, 1493, new Class[]{org.eclipse.paho.client.mqttv3.f.class}, Void.TYPE).isSupported) {
                super.onSuccess(asyncActionToken);
                MqttServiceRequestTaskBean _item = (MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(a()));
                this.c.putResponse("topics", this.d);
                MqttLogStepBean mqttLogStepBean = this.c;
                mqttLogStepBean.endTrace(200, "取消订阅消息成功, topics:=" + this.d);
                this.e.h();
                if (_item != null) {
                    com.leedarson.mqtt.logs.b.b("unsubscribeTopics  mqtt底层引擎触发取消订阅（成功）" + asyncActionToken.toString() + " taskId=" + asyncActionToken.e().toString());
                    JSONObject _resData = new JSONObject();
                    try {
                        _resData.put("topics", (Object) _item.topics);
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                    LDSBaseMqttService.MqttActionHandler mqttActionHandler = _item.callBackToBzHandler;
                    mqttActionHandler.onActionSuccess(a() + "", _resData);
                    return;
                }
                com.leedarson.mqtt.logs.b.a("unsubscribeTopics  mqtt底层引擎触发取消订阅（成功-未匹配到消息TaskId）taskId=" + a());
            }
        }

        public void onFailure(org.eclipse.paho.client.mqttv3.f asyncActionToken, Throwable exception) {
            if (!PatchProxy.proxy(new Object[]{asyncActionToken, exception}, this, changeQuickRedirect, false, 1494, new Class[]{org.eclipse.paho.client.mqttv3.f.class, Throwable.class}, Void.TYPE).isSupported) {
                super.onFailure(asyncActionToken, exception);
                MqttServiceRequestTaskBean _item = (MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(a()));
                String tip = "Topic取消订阅失败 topics=" + new Gson().toJson((Object) this.f) + " messageId:" + asyncActionToken.c() + exception.toString() + " ,taskId=" + a();
                this.c.putResponse("topics", this.d);
                this.c.putResponse("tip", tip);
                this.c.endTrace(4082, "取消订阅消息失败, topics:=" + this.d);
                this.e.h();
                if (_item != null) {
                    l.j(l.this, _item.callBackToBzHandler, 4082, tip);
                } else {
                    l.j(l.this, this.g, 4082, tip);
                }
                com.leedarson.mqtt.logs.b.a("unsubscribeTopics  mqtt底层引擎触发取消订阅（失败）" + asyncActionToken.toString() + ", exception=" + exception.toString());
            }
        }
    }

    private void Q(LDSBaseMqttService.MqttActionHandler _handler, int code, String desc) {
        if (!PatchProxy.proxy(new Object[]{_handler, new Integer(code), desc}, this, changeQuickRedirect, false, 1445, new Class[]{LDSBaseMqttService.MqttActionHandler.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            String tip = "Topic订阅失败" + desc;
            if (_handler != null) {
                _handler.onActionFail(code, "", tip);
            }
            com.leedarson.mqtt.logs.b.a(tip);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x0261 A[SYNTHETIC, Splitter:B:103:0x0261] */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0278 A[SYNTHETIC, Splitter:B:106:0x0278] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0164 A[SYNTHETIC, Splitter:B:65:0x0164] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0167 A[SYNTHETIC, Splitter:B:67:0x0167] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x016f A[SYNTHETIC, Splitter:B:71:0x016f] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0175 A[Catch:{ Exception -> 0x0149 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0178 A[SYNTHETIC, Splitter:B:75:0x0178] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0180 A[SYNTHETIC, Splitter:B:79:0x0180] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0185 A[SYNTHETIC, Splitter:B:81:0x0185] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0196 A[SYNTHETIC, Splitter:B:88:0x0196] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01d0 A[Catch:{ Exception -> 0x02a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x01d2 A[Catch:{ Exception -> 0x02a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x023d  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0240  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void S(java.lang.String r21, java.lang.String r22, com.leedarson.combeans.MqttMessageConfigBean r23, com.leedarson.serviceinterface.LDSBaseMqttService.MqttActionHandler r24, com.leedarson.mqtt.beans.MqttServiceRequestTaskBean r25) {
        /*
            r20 = this;
            java.lang.String r1 = " , message="
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            java.lang.String r2 = ""
            r3 = 5
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r11 = 0
            r4[r11] = r21
            r12 = 1
            r4[r12] = r22
            r5 = 2
            r4[r5] = r23
            r6 = 3
            r4[r6] = r24
            r7 = 4
            r4[r7] = r25
            com.meituan.robust.ChangeQuickRedirect r8 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            r9[r11] = r0
            r9[r12] = r0
            java.lang.Class<com.leedarson.combeans.MqttMessageConfigBean> r0 = com.leedarson.combeans.MqttMessageConfigBean.class
            r9[r5] = r0
            java.lang.Class<com.leedarson.serviceinterface.LDSBaseMqttService$MqttActionHandler> r0 = com.leedarson.serviceinterface.LDSBaseMqttService.MqttActionHandler.class
            r9[r6] = r0
            java.lang.Class<com.leedarson.mqtt.beans.MqttServiceRequestTaskBean> r0 = com.leedarson.mqtt.beans.MqttServiceRequestTaskBean.class
            r9[r7] = r0
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r0 = 1446(0x5a6, float:2.026E-42)
            r5 = r20
            r6 = r8
            r8 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x003e
            return
        L_0x003e:
            r9 = r20
            r10 = r22
            r13 = r24
            r14 = r21
            r15 = r23
            r8 = r25
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 != 0) goto L_0x02ff
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 == 0) goto L_0x0060
            r19 = r8
            r17 = r9
            r24 = r13
            r16 = r15
            goto L_0x0307
        L_0x0060:
            if (r8 == 0) goto L_0x0065
            int r0 = r8.taskId
            goto L_0x0069
        L_0x0065:
            int r0 = r9.v()
        L_0x0069:
            r7 = r0
            if (r8 != 0) goto L_0x0072
            com.leedarson.mqtt.beans.MqttServiceRequestTaskBean r0 = new com.leedarson.mqtt.beans.MqttServiceRequestTaskBean
            r0.<init>()
            goto L_0x0073
        L_0x0072:
            r0 = r8
        L_0x0073:
            r6 = r0
            r3 = 0
            org.eclipse.paho.client.mqttv3.l r0 = new org.eclipse.paho.client.mqttv3.l     // Catch:{ Exception -> 0x02b1 }
            r0.<init>()     // Catch:{ Exception -> 0x02b1 }
            r5 = r0
            int r0 = r15.qos     // Catch:{ Exception -> 0x02b1 }
            r5.k(r0)     // Catch:{ Exception -> 0x02b1 }
            r5.l(r11)     // Catch:{ Exception -> 0x02b1 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            r0.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            r3 = r0
            java.lang.String r0 = "srcAddr"
            java.lang.String r4 = "seq"
            java.lang.String r16 = "tst"
            r21 = r16
            java.lang.String r16 = "tid"
            r22 = r16
            java.lang.String r12 = r15.seq     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            boolean r12 = android.text.TextUtils.isEmpty(r12)     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            if (r12 == 0) goto L_0x00b7
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            r12.<init>()     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            r12.append(r7)     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            r12.append(r2)     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            goto L_0x00c8
        L_0x00ad:
            r0 = move-exception
            r24 = r13
            goto L_0x0144
        L_0x00b2:
            r0 = move-exception
            r24 = r13
            goto L_0x0158
        L_0x00b7:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            r12.<init>()     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            java.lang.String r11 = r15.seq     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            r12.append(r11)     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            r12.append(r2)     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
        L_0x00c8:
            r6.seqNum = r12     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            boolean r11 = r3.has(r0)     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            if (r11 != 0) goto L_0x00fc
            com.leedarson.base.application.BaseApplication r11 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            java.lang.String r12 = "userId"
            java.lang.String r11 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r11, r12, r2)     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            r12.<init>()     // Catch:{ JSONException -> 0x0155, Exception -> 0x0141 }
            r24 = r13
            java.lang.String r13 = "0."
            r12.append(r13)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r12.append(r11)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r3.put((java.lang.String) r0, (java.lang.Object) r12)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            java.lang.String r12 = r3.toString()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            byte[] r12 = r12.getBytes()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r5.j(r12)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            goto L_0x00fe
        L_0x00fc:
            r24 = r13
        L_0x00fe:
            boolean r11 = r3.has(r4)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            if (r11 != 0) goto L_0x0113
            boolean r11 = r15.isSupportSimpleVersion     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            if (r11 == 0) goto L_0x010e
            java.lang.String r11 = com.leedarson.serviceinterface.prefs.SpExtendHelper.generateNextSeq()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r6.seqNum = r11     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
        L_0x010e:
            java.lang.String r11 = r6.seqNum     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r3.put((java.lang.String) r4, (java.lang.Object) r11)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
        L_0x0113:
            r11 = r21
            boolean r12 = r3.has(r11)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            if (r12 != 0) goto L_0x0122
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r3.put((java.lang.String) r11, (long) r12)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
        L_0x0122:
            r12 = r22
            boolean r13 = r3.has(r12)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            if (r13 != 0) goto L_0x0131
            java.lang.String r13 = com.leedarson.serviceinterface.prefs.SpExtendHelper.generateTid()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r3.put((java.lang.String) r12, (java.lang.Object) r13)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
        L_0x0131:
            java.lang.String r13 = r3.toString()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            byte[] r13 = r13.getBytes()     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            r5.j(r13)     // Catch:{ JSONException -> 0x013f, Exception -> 0x013d }
            goto L_0x015b
        L_0x013d:
            r0 = move-exception
            goto L_0x0144
        L_0x013f:
            r0 = move-exception
            goto L_0x0158
        L_0x0141:
            r0 = move-exception
            r24 = r13
        L_0x0144:
            r0.printStackTrace()     // Catch:{ Exception -> 0x0149 }
            r0 = r3
            goto L_0x015c
        L_0x0149:
            r0 = move-exception
            r18 = r7
            r19 = r8
            r17 = r9
            r16 = r15
            r9 = r6
            goto L_0x02bd
        L_0x0155:
            r0 = move-exception
            r24 = r13
        L_0x0158:
            r0.printStackTrace()     // Catch:{ Exception -> 0x02a6 }
        L_0x015b:
            r0 = r3
        L_0x015c:
            r6.taskId = r7     // Catch:{ Exception -> 0x02a6 }
            r6.messageId = r7     // Catch:{ Exception -> 0x02a6 }
            boolean r3 = r15.onlyPubAck     // Catch:{ Exception -> 0x02a6 }
            if (r3 == 0) goto L_0x0167
            com.leedarson.mqtt.beans.MqttServiceRequestTaskBean$MqttRequestType r3 = com.leedarson.mqtt.beans.MqttServiceRequestTaskBean.MqttRequestType.MQTT_PUBLISH     // Catch:{ Exception -> 0x0149 }
            goto L_0x0169
        L_0x0167:
            com.leedarson.mqtt.beans.MqttServiceRequestTaskBean$MqttRequestType r3 = com.leedarson.mqtt.beans.MqttServiceRequestTaskBean.MqttRequestType.MQTT_PUBLISH_WITH_RESP     // Catch:{ Exception -> 0x02a6 }
        L_0x0169:
            r6.requestType = r3     // Catch:{ Exception -> 0x02a6 }
            r6.requestParams = r10     // Catch:{ Exception -> 0x02a6 }
            if (r8 == 0) goto L_0x0173
            long r3 = r8.createTime     // Catch:{ Exception -> 0x0149 }
            r6.createTime = r3     // Catch:{ Exception -> 0x0149 }
        L_0x0173:
            if (r8 == 0) goto L_0x0178
            long r3 = r8.createTime     // Catch:{ Exception -> 0x0149 }
            goto L_0x017c
        L_0x0178:
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x02a6 }
        L_0x017c:
            r6.createTime = r3     // Catch:{ Exception -> 0x02a6 }
            if (r8 == 0) goto L_0x0185
            long r3 = r8.timeOutDeadline     // Catch:{ Exception -> 0x0149 }
            r6.timeOutDeadline = r3     // Catch:{ Exception -> 0x0149 }
            goto L_0x0192
        L_0x0185:
            long r11 = r15.timeOutLimitOfMs     // Catch:{ Exception -> 0x02a6 }
            r18 = 0
            int r13 = (r11 > r18 ? 1 : (r11 == r18 ? 0 : -1))
            if (r13 > 0) goto L_0x018f
            r11 = 10000(0x2710, double:4.9407E-320)
        L_0x018f:
            long r3 = r3 + r11
            r6.timeOutDeadline = r3     // Catch:{ Exception -> 0x02a6 }
        L_0x0192:
            r6.mqttMessage = r5     // Catch:{ Exception -> 0x02a6 }
            if (r8 == 0) goto L_0x0199
            com.leedarson.serviceinterface.LDSBaseMqttService$MqttActionHandler r3 = r8.callBackToBzHandler     // Catch:{ Exception -> 0x0149 }
            goto L_0x019b
        L_0x0199:
            r3 = r24
        L_0x019b:
            r6.callBackToBzHandler = r3     // Catch:{ Exception -> 0x02a6 }
            r6.topic = r14     // Catch:{ Exception -> 0x02a6 }
            r6.config = r15     // Catch:{ Exception -> 0x02a6 }
            com.leedarson.mqtt.beans.MqttServiceRequestTaskBean$LDS_MQTT_MESSAGE_DEVELIVERY_STATUE r3 = com.leedarson.mqtt.beans.MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.WAITING_TO_DELIVER     // Catch:{ Exception -> 0x02a6 }
            r6.taskDeliveryStatue = r3     // Catch:{ Exception -> 0x02a6 }
            r3 = 0
            r6.flagEnableDelete = r3     // Catch:{ Exception -> 0x02a6 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02a6 }
            r4.<init>()     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r11 = "收到消息发布请求 ,topic="
            r4.append(r11)     // Catch:{ Exception -> 0x02a6 }
            r4.append(r14)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r11 = " seq ="
            r4.append(r11)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r11 = r6.seqNum     // Catch:{ Exception -> 0x02a6 }
            r4.append(r11)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r11 = " taskId="
            r4.append(r11)     // Catch:{ Exception -> 0x02a6 }
            int r11 = r6.taskId     // Catch:{ Exception -> 0x02a6 }
            r4.append(r11)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r11 = " ,补发消息?="
            r4.append(r11)     // Catch:{ Exception -> 0x02a6 }
            if (r8 == 0) goto L_0x01d2
            r11 = 1
            goto L_0x01d3
        L_0x01d2:
            r11 = r3
        L_0x01d3:
            r4.append(r11)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r3 = ", isSupportSimpleVersion="
            r4.append(r3)     // Catch:{ Exception -> 0x02a6 }
            boolean r3 = r15.isSupportSimpleVersion     // Catch:{ Exception -> 0x02a6 }
            r4.append(r3)     // Catch:{ Exception -> 0x02a6 }
            r4.append(r1)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r3 = r0.toString()     // Catch:{ Exception -> 0x02a6 }
            r4.append(r3)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r3 = r4.toString()     // Catch:{ Exception -> 0x02a6 }
            com.leedarson.mqtt.logs.b.b(r3)     // Catch:{ Exception -> 0x02a6 }
            r5.h(r7)     // Catch:{ Exception -> 0x02a6 }
            java.util.concurrent.ConcurrentHashMap<java.lang.Integer, com.leedarson.mqtt.beans.MqttServiceRequestTaskBean> r3 = r9.n     // Catch:{ Exception -> 0x02a6 }
            int r4 = r6.taskId     // Catch:{ Exception -> 0x02a6 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x02a6 }
            r3.put(r4, r6)     // Catch:{ Exception -> 0x02a6 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02a6 }
            r3.<init>()     // Catch:{ Exception -> 0x02a6 }
            int r4 = r6.taskId     // Catch:{ Exception -> 0x02a6 }
            r3.append(r4)     // Catch:{ Exception -> 0x02a6 }
            r3.append(r2)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x02a6 }
            com.leedarson.mqtt.logs.a r3 = com.leedarson.mqtt.logs.a.e(r3)     // Catch:{ Exception -> 0x02a6 }
            r11 = r3
            com.leedarson.mqtt.logs.MqttLogStepBean r3 = new com.leedarson.mqtt.logs.MqttLogStepBean     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r4 = "MqttPublishRequest"
            r3.<init>(r4)     // Catch:{ Exception -> 0x02a6 }
            r12 = r3
            java.lang.String r3 = "topic"
            r12.putRequest(r3, r14)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r3 = "payload"
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x02a6 }
            byte[] r13 = r5.c()     // Catch:{ Exception -> 0x02a6 }
            r4.<init>(r13)     // Catch:{ Exception -> 0x02a6 }
            r12.putRequest(r3, r4)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r3 = "publish"
            com.leedarson.mqtt.logs.a r3 = r11.j(r3)     // Catch:{ Exception -> 0x02a6 }
            r3.g(r12)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r3 = "republish"
            if (r8 != 0) goto L_0x0240
            java.lang.String r4 = "true"
            goto L_0x0242
        L_0x0240:
            java.lang.String r4 = "false"
        L_0x0242:
            r12.putRequest(r3, r4)     // Catch:{ Exception -> 0x02a6 }
            r3 = 4082(0xff2, float:5.72E-42)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02a6 }
            r4.<init>()     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r13 = "超时10s,未收到SDK发出标识 topic="
            r4.append(r13)     // Catch:{ Exception -> 0x02a6 }
            r4.append(r14)     // Catch:{ Exception -> 0x02a6 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x02a6 }
            r12.endTrace(r3, r4)     // Catch:{ Exception -> 0x02a6 }
            com.leedarson.mqtt.l$n r3 = r9.m     // Catch:{ Exception -> 0x02a6 }
            com.leedarson.mqtt.l$n r4 = com.leedarson.mqtt.l.n.MQTT_CONNECT_STATUE_CONNECTED     // Catch:{ Exception -> 0x02a6 }
            if (r3 == r4) goto L_0x0278
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0149 }
            r3.<init>()     // Catch:{ Exception -> 0x0149 }
            java.lang.String r4 = "收到发布订阅消息（mqtt通道未连接）mqttConnectStatue="
            r3.append(r4)     // Catch:{ Exception -> 0x0149 }
            com.leedarson.mqtt.l$n r4 = r9.m     // Catch:{ Exception -> 0x0149 }
            r3.append(r4)     // Catch:{ Exception -> 0x0149 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0149 }
            com.leedarson.mqtt.logs.b.a(r3)     // Catch:{ Exception -> 0x0149 }
            return
        L_0x0278:
            com.leedarson.mqtt.libservice.MqttAndroidClient r13 = r9.b     // Catch:{ Exception -> 0x02a6 }
            int r3 = r6.taskId     // Catch:{ Exception -> 0x02a6 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x02a6 }
            com.leedarson.mqtt.l$m r3 = new com.leedarson.mqtt.l$m     // Catch:{ Exception -> 0x02a6 }
            r21 = r0
            int r0 = r6.taskId     // Catch:{ Exception -> 0x02a6 }
            r22 = r3
            r23 = r12
            r12 = r4
            r4 = r9
            r16 = r15
            r15 = r5
            r5 = r0
            r17 = r9
            r9 = r6
            r6 = r14
            r18 = r7
            r7 = r11
            r19 = r8
            r8 = r10
            r3.<init>(r5, r6, r7, r8)     // Catch:{ Exception -> 0x02a4 }
            r0 = r22
            r13.z(r14, r15, r12, r0)     // Catch:{ Exception -> 0x02a4 }
            goto L_0x02fe
        L_0x02a4:
            r0 = move-exception
            goto L_0x02bd
        L_0x02a6:
            r0 = move-exception
            r18 = r7
            r19 = r8
            r17 = r9
            r16 = r15
            r9 = r6
            goto L_0x02bd
        L_0x02b1:
            r0 = move-exception
            r18 = r7
            r19 = r8
            r17 = r9
            r24 = r13
            r16 = r15
            r9 = r6
        L_0x02bd:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "消息发布失败  e="
            r3.append(r4)
            java.lang.String r4 = r0.toString()
            r3.append(r4)
            java.lang.String r4 = ", topic="
            r3.append(r4)
            r3.append(r14)
            r3.append(r1)
            r3.append(r10)
            java.lang.String r1 = r3.toString()
            com.leedarson.serviceinterface.LDSBaseMqttService$MqttActionHandler r3 = r9.callBackToBzHandler
            if (r3 == 0) goto L_0x02fb
            r4 = -4000009(0xffffffffffc2f6f7, float:NaN)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            int r6 = r9.taskId
            r5.append(r6)
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            r3.onActionFail(r4, r2, r1)
        L_0x02fb:
            com.leedarson.mqtt.logs.b.a(r1)
        L_0x02fe:
            return
        L_0x02ff:
            r19 = r8
            r17 = r9
            r24 = r13
            r16 = r15
        L_0x0307:
            java.lang.String r0 = "发布消息参数缺失 (topic/msg 为空)"
            java.lang.String r1 = "收到发布消息请求 --> 但发布消息参数缺失 (topic/msg 为空)"
            com.leedarson.mqtt.logs.b.a(r1)
            r1 = -4000003(0xffffffffffc2f6fd, float:NaN)
            r3 = r24
            r2 = r17
            r2.Q(r3, r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.mqtt.l.S(java.lang.String, java.lang.String, com.leedarson.combeans.MqttMessageConfigBean, com.leedarson.serviceinterface.LDSBaseMqttService$MqttActionHandler, com.leedarson.mqtt.beans.MqttServiceRequestTaskBean):void");
    }

    /* compiled from: LdsMqttManager */
    public class m extends com.leedarson.mqtt.listerners.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ com.leedarson.mqtt.logs.a d;
        final /* synthetic */ String e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        m(int taskId, String str, com.leedarson.mqtt.logs.a aVar, String str2) {
            super(taskId);
            this.c = str;
            this.d = aVar;
            this.e = str2;
        }

        public void onSuccess(org.eclipse.paho.client.mqttv3.f asyncActionToken) {
            if (!PatchProxy.proxy(new Object[]{asyncActionToken}, this, changeQuickRedirect, false, 1495, new Class[]{org.eclipse.paho.client.mqttv3.f.class}, Void.TYPE).isSupported) {
                super.onSuccess(asyncActionToken);
                MqttServiceRequestTaskBean _item = (MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(a()));
                MqttLogStepBean _stepPublishRequestProcess = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_PROCESS);
                if (_item != null) {
                    _item.mqttEngineSendTimeSpan = System.currentTimeMillis();
                    _item.taskDeliveryStatue = MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.MQTT_CLIENT_DELIVERED;
                    com.leedarson.mqtt.logs.b.b("publish 消息已投递至mqtt缓存池成功  " + asyncActionToken.c() + ", context=" + asyncActionToken.e().toString());
                    _stepPublishRequestProcess.putRequest("match", "匹配到任务");
                } else {
                    com.leedarson.mqtt.logs.b.a("publish 消息投递至缓存池，但未找到匹配的任务id=" + a() + ",topic=" + this.c);
                    _stepPublishRequestProcess.putRequest("match", "未匹配到本地任务");
                }
                _stepPublishRequestProcess.endTrace(4083, "超时10s,未收到puback,topic=" + this.c);
                this.d.g(_stepPublishRequestProcess);
            }
        }

        public void onFailure(org.eclipse.paho.client.mqttv3.f asyncActionToken, Throwable exception) {
            String str;
            if (!PatchProxy.proxy(new Object[]{asyncActionToken, exception}, this, changeQuickRedirect, false, 1496, new Class[]{org.eclipse.paho.client.mqttv3.f.class, Throwable.class}, Void.TYPE).isSupported) {
                super.onFailure(asyncActionToken, exception);
                MqttServiceRequestTaskBean _item = (MqttServiceRequestTaskBean) l.this.n.get(Integer.valueOf(a()));
                MqttLogStepBean _stepPublishRequestProcess = new MqttLogStepBean(MqttLogStepBean.STEP_OF_MQTT_PUBLISH_REQUEST_PROCESS);
                if (_item != null) {
                    _item.mqttEngineSendTimeSpan = System.currentTimeMillis();
                    _item.taskDeliveryStatue = MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.MQTT_CLIENT_DELIVERED_FAIL;
                    StringBuilder sb = new StringBuilder();
                    sb.append("publish 消息投递至mqtt缓存池失败  topic=");
                    sb.append(this.c);
                    sb.append(" exception=");
                    sb.append(exception != null ? exception.toString() : BuildConfig.TRAVIS);
                    sb.append(" ,message=");
                    sb.append(this.e);
                    String tip = sb.toString();
                    _stepPublishRequestProcess.endTrace(4081, tip);
                    com.leedarson.mqtt.logs.b.a(tip);
                    _item.callBackToBzHandler.onActionFail(-4000009, a() + "", tip);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("publish 消息投递至mqtt缓存池失败(未匹配到对应taskId)  exception=");
                    if (exception != null) {
                        str = exception.toString() + " ";
                    } else {
                        str = "null  ";
                    }
                    sb2.append(str);
                    sb2.append(a());
                    sb2.append(", topic=");
                    sb2.append(this.c);
                    sb2.append(" ,message=");
                    sb2.append(this.e);
                    String _tip = sb2.toString();
                    com.leedarson.mqtt.logs.b.a(_tip);
                    _stepPublishRequestProcess.endTrace(4081, _tip);
                }
                this.d.g(_stepPublishRequestProcess);
                this.d.h();
            }
        }
    }

    private void Y(n statue, String ref) {
        this.m = statue;
        if (n.MQTT_CONNECT_STATUE_CONNECTED != statue) {
            this.u = ref;
        }
    }

    public int v() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1447, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String _seq = (System.currentTimeMillis() + "").substring(4);
        if (this.v.equals(_seq)) {
            synchronized (l.class) {
                do {
                    _seq = (System.currentTimeMillis() + "").substring(4);
                } while (this.v.equals(_seq));
                this.v = _seq;
            }
        } else {
            this.v = _seq;
        }
        return Integer.parseInt(_seq);
    }

    private void t(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 1448, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("close 清除所有的taskList任务 ref=" + ref);
            this.n.clear();
        }
    }

    public int D() {
        return this.m == n.MQTT_CONNECT_STATUE_CONNECTED ? 1 : 0;
    }

    public String A() {
        return this.u;
    }

    private void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1449, new Class[0], Void.TYPE).isSupported) {
            Set<Integer> _keySets = this.n.keySet();
            ArrayList<Integer> _targetKeys = new ArrayList<>();
            for (Integer key : _keySets) {
                MqttServiceRequestTaskBean _item = this.n.get(key);
                if (_item != null && _item.requestType == MqttServiceRequestTaskBean.MqttRequestType.MQTT_SUBSCRIBES) {
                    _targetKeys.add(key);
                }
            }
            com.leedarson.mqtt.logs.b.b("reSubscribeAfterConnected 当前线程池........" + Thread.currentThread().getName() + " , targetKeys=" + _targetKeys.size());
            for (int i2 = 0; i2 < _targetKeys.size(); i2++) {
                MqttServiceRequestTaskBean _item2 = this.n.get(_targetKeys.get(i2));
                W(_item2.topics, new a(_item2), true);
            }
            IpcService _ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            if (_ipcService != null) {
                this.h.b(this.i.f().c(com.leedarson.base.http.observer.l.c()).I(new g(this, _ipcService), i.c));
            }
        }
    }

    /* compiled from: LdsMqttManager */
    public class a implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MqttServiceRequestTaskBean a;

        a(MqttServiceRequestTaskBean mqttServiceRequestTaskBean) {
            this.a = mqttServiceRequestTaskBean;
        }

        public void onActionSuccess(String str, JSONObject jSONObject) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, jSONObject}, this, changeQuickRedirect, false, 1474, clsArr, Void.TYPE).isSupported) {
                com.leedarson.mqtt.logs.b.b("mqtt 订阅成功（重新订阅）  topics=" + new Gson().toJson((Object) this.a.topics));
            }
        }

        public void onActionFail(int i, String str, String str2) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, str2};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1475, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                com.leedarson.mqtt.logs.b.b("mqtt 订阅失败（重新订阅）  topics=" + new Gson().toJson((Object) this.a.topics));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: M */
    public /* synthetic */ void N(IpcService _ipcService, JSONObject jsonObject) {
        if (!PatchProxy.proxy(new Object[]{_ipcService, jsonObject}, this, changeQuickRedirect, false, 1458, new Class[]{IpcService.class, JSONObject.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("mqttlog 连接成功--》触发缓存全量更新  deviceList Info=  ref=" + this.s);
            if ("coldStartConnect".equals(this.s)) {
                _ipcService.actualFullUpdateByNativeMqtt(jsonObject, false, false);
            }
        }
    }

    static /* synthetic */ void O(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 1457, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.a("fetchDeviceList throwable=" + throwable.toString());
        }
    }

    private void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1450, new Class[0], Void.TYPE).isSupported) {
            for (Integer key : this.n.keySet()) {
                MqttServiceRequestTaskBean _item = this.n.get(key);
                StringBuilder sb = new StringBuilder();
                sb.append("rePublishAfterConnected 待发送消息包..... item.topic=");
                sb.append(_item.topic);
                sb.append(", statue=");
                sb.append(_item.taskDeliveryStatue);
                sb.append(" , flagEnableDelete=");
                sb.append(_item.flagEnableDelete);
                sb.append(" _item.timeOutDeadline > (System.currentTimeMillis() - 1500)=");
                sb.append(_item.timeOutDeadline > System.currentTimeMillis() - 1500);
                sb.append(" timeOutDeadline=");
                sb.append(_item.timeOutDeadline);
                sb.append(" current=");
                sb.append(System.currentTimeMillis());
                sb.append(" currentBuffer=");
                sb.append(System.currentTimeMillis() - 1500);
                sb.append(" taskId=");
                sb.append(_item.taskId);
                com.leedarson.mqtt.logs.b.b(sb.toString());
                MqttServiceRequestTaskBean.MqttRequestType mqttRequestType = _item.requestType;
                if ((mqttRequestType == MqttServiceRequestTaskBean.MqttRequestType.MQTT_PUBLISH || mqttRequestType == MqttServiceRequestTaskBean.MqttRequestType.MQTT_PUBLISH_WITH_RESP) && !_item.flagEnableDelete && _item.taskDeliveryStatue == MqttServiceRequestTaskBean.LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.WAITING_TO_DELIVER) {
                    if (_item.timeOutDeadline > System.currentTimeMillis() - 1500) {
                        com.leedarson.mqtt.logs.b.b("mqtt 连接成功 - 尝试重新投递消息 topic=" + _item.topic);
                        S(_item.topic, _item.requestParams, _item.config, _item.callBackToBzHandler, _item);
                    } else {
                        _item.flagEnableDelete = false;
                        com.leedarson.mqtt.logs.b.a("mqtt 连接成功 - 尝试重新投递消息 - 时间已超 topic=" + _item.topic + " , _item.timeOutDeadline=" + _item.timeOutDeadline + " current=" + (System.currentTimeMillis() - 1500));
                    }
                }
            }
        }
    }

    private void V() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1451, new Class[0], Void.TYPE).isSupported) {
            String topic = "iot/v1/c/userId/#".replace("userId", SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
            com.leedarson.mqtt.logs.b.b("开始订阅当前用户根目录");
            W(new String[]{topic}, new b(), false);
        }
    }

    /* compiled from: LdsMqttManager */
    public class b implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
        }

        public void onActionFail(int code, String taskId, String desc) {
        }
    }

    private void P() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1452, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.mqtt.logs.b.b("开始通知当前用户上线");
            String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
            String topic = "iot/v1/cb/userId/user/connect".replace("userId", userId);
            MqttMessageConfigBean _config = new MqttMessageConfigBean();
            _config.onlyPubAck = true;
            JSONObject _messageObj = new JSONObject();
            JSONObject _payloadObj = new JSONObject();
            try {
                _messageObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) "user");
                _messageObj.put(FirebaseAnalytics.Param.METHOD, (Object) "connect");
                _messageObj.put("srcAddr", (Object) "0." + userId);
                _payloadObj.put("timestamp", (Object) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
                _messageObj.put("payload", (Object) _payloadObj);
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            S(topic, _messageObj.toString(), _config, new c(), (MqttServiceRequestTaskBean) null);
        }
    }

    /* compiled from: LdsMqttManager */
    public class c implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onActionFail(int i, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1476, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                com.leedarson.mqtt.logs.b.a("通知当前用户上线 （失败）taskId=" + taskId + " , desc=" + desc);
            }
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData}, this, changeQuickRedirect, false, 1477, clsArr, Void.TYPE).isSupported) {
                com.leedarson.mqtt.logs.b.b("通知当前用户上线 （成功）taskId=" + taskId + " , data=" + callbackData);
            }
        }
    }

    private MqttServiceRequestTaskBean E(String[] topics) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{topics}, this, changeQuickRedirect, false, 1453, new Class[]{String[].class}, MqttServiceRequestTaskBean.class);
        if (proxy.isSupported) {
            return (MqttServiceRequestTaskBean) proxy.result;
        }
        for (Integer key : this.n.keySet()) {
            MqttServiceRequestTaskBean _item = this.n.get(key);
            if (_item != null && _item.requestType == MqttServiceRequestTaskBean.MqttRequestType.MQTT_SUBSCRIBES && new Gson().toJson((Object) _item.topics).equals(new Gson().toJson((Object) topics))) {
                return _item;
            }
        }
        return null;
    }

    public io.reactivex.e<Boolean> z(JSONObject _data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_data}, this, changeQuickRedirect, false, 1455, new Class[]{JSONObject.class}, io.reactivex.e.class);
        return proxy.isSupported ? (io.reactivex.e) proxy.result : this.i.x(_data);
    }

    public io.reactivex.e<JSONObject> B() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1456, new Class[0], io.reactivex.e.class);
        return proxy.isSupported ? (io.reactivex.e) proxy.result : this.i.f();
    }
}
