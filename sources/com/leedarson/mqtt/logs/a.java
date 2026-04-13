package com.leedarson.mqtt.logs;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.Constants;
import com.leedarson.mqtt.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: MqttElkLogReporter */
public class a {
    private static ConcurrentHashMap<String, a> a = new ConcurrentHashMap<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    private String b = "2023.6.26.19.37.8";
    public ArrayList<MqttLogStepBean> c = new ArrayList<>();
    private String d = "";
    private String e = "";
    private String f = "";

    private a() {
    }

    public static a e(String taskId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{taskId}, (Object) null, changeQuickRedirect, true, 1698, new Class[]{String.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (a.containsKey(taskId)) {
            return a.get(taskId);
        }
        a _reporter = new a();
        a.put(taskId, _reporter);
        _reporter.k(taskId);
        return _reporter;
    }

    public void k(String taskId) {
        this.d = taskId;
    }

    public a i(String connectType) {
        this.f = connectType;
        return this;
    }

    public String d() {
        return this.f;
    }

    public a j(String eventName) {
        this.e = eventName;
        return this;
    }

    public a g(MqttLogStepBean step) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{step}, this, changeQuickRedirect, false, 1699, new Class[]{MqttLogStepBean.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        this.c.add(step);
        return this;
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1700, new Class[0], Void.TYPE).isSupported) {
            try {
                ArrayList<MqttLogStepBean> arrayList = this.c;
                if (arrayList == null) {
                    return;
                }
                if (arrayList.size() != 0) {
                    Gson gson = new Gson();
                    com.leedarson.log.elk.a u = com.leedarson.log.elk.a.y(this).c(a.class.getSimpleName()).t(Constants.SERVICE_MQTT).x(this.d).o("info").e(this.e).u("code", Integer.valueOf(l().code)).u("connectType", c()).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(a()));
                    u.u("message", l().desc + "").u("logVersion", this.b).u("payload", gson.toJson((Object) this.c)).u("nativeMqttStatue", Integer.valueOf(f())).u("AndroidAppState", BaseApplication.d ? "后台" : "前台").a().b();
                    this.c.clear();
                    a.remove(this.d);
                }
            } catch (Exception e2) {
                timber.log.a.c("MqttElkLogReporter 数据上报出现异常" + e2.toString(), new Object[0]);
            }
        }
    }

    private MqttLogStepBean l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1701, new Class[0], MqttLogStepBean.class);
        if (proxy.isSupported) {
            return (MqttLogStepBean) proxy.result;
        }
        ArrayList<MqttLogStepBean> arrayList = this.c;
        return arrayList.get(arrayList.size() - 1);
    }

    private int f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1702, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return l.C().D();
    }

    private String c() {
        return this.f;
    }

    private long a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1703, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        return b(this.c);
    }

    private long b(ArrayList<MqttLogStepBean> _tempConfig) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_tempConfig}, this, changeQuickRedirect, false, 1704, new Class[]{ArrayList.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (_tempConfig.size() == 0) {
            return 0;
        }
        if (_tempConfig.size() == 1) {
            return _tempConfig.get(0).durationMs;
        }
        long maxValue = _tempConfig.get(0).et;
        for (int i = 0; i < _tempConfig.size(); i++) {
            if (_tempConfig.get(i).et > maxValue) {
                maxValue = _tempConfig.get(i).et;
            }
        }
        return maxValue - _tempConfig.get(0).ct;
    }
}
