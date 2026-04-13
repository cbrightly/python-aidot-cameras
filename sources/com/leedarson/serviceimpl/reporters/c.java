package com.leedarson.serviceimpl.reporters;

import com.leedarson.log.mgr.u;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;
import io.reactivex.l;
import io.reactivex.q;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.model.NodeInfo;
import meshsdk.sql.SqlManager;
import meshsdk.util.MeshConstants;
import meshsdk.util.ProcedureCollector;

/* compiled from: AutoConnectDevicesReporter */
public class c {
    public static List<AutoConnectDeviceStepBean> a = new ArrayList();
    public static boolean b = false;
    public static int c;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d;
    public static String e;
    public static final DateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    /* access modifiers changed from: private */
    public static b g;

    public static void e() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 8527, new Class[0], Void.TYPE).isSupported) {
            if (!b) {
                String strDate = f.format(new Date());
                com.leedarson.serviceimpl.elkstrays.b.a("autoConnect埋点: 初始化上线埋点,time:" + strDate);
                e = "_" + strDate;
                d tracker = new d(SIGMesh.getInstance().getContext());
                tracker.j(MeshConstants.TRACE_ID_AUTO_CONNECT + e, "BleMesh", MeshConstants.AUTO_CONNECT);
                u.d().b(tracker);
            }
            b = true;
        }
    }

    public static void c(AutoConnectDeviceStepBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{stepBean}, (Object) null, changeQuickRedirect, true, 8528, new Class[]{AutoConnectDeviceStepBean.class}, Void.TYPE).isSupported) {
            try {
                if (!b) {
                    com.leedarson.serviceimpl.elkstrays.b.a("autoConnect埋点：还未初始化，不添加自动连接上报步骤(" + stepBean.getStep() + ")");
                } else if (!d()) {
                    com.leedarson.serviceimpl.elkstrays.b.a("autoConnect埋点：当前没有mesh节点，不添加自动连接上报步骤(" + stepBean.getStep() + ")");
                    a.clear();
                } else {
                    if (c == e.CODE_SCAN_NOT_FOUND_DEVICE.getCode()) {
                        if (c == stepBean.code) {
                            com.leedarson.serviceimpl.elkstrays.b.a("autoConnect埋点：" + stepBean.step + "连续上报，不添加");
                            return;
                        }
                    }
                    c = stepBean.code;
                    com.leedarson.serviceimpl.elkstrays.b.a("autoConnect埋点：" + stepBean.step);
                    if (a.size() > 0) {
                        long j = stepBean._beginTraceTimeSpan;
                        List<AutoConnectDeviceStepBean> list = a;
                        stepBean.setDuration(j - list.get(list.size() - 1)._beginTraceTimeSpan);
                    }
                    a.add(stepBean);
                    u d2 = u.d();
                    d2.a(MeshConstants.TRACE_ID_AUTO_CONNECT + e, stepBean);
                }
            } catch (Exception e2) {
                MeshLog.e("addStep exception:" + e2.getMessage());
            }
        }
    }

    public static void g(boolean isConnectTimeoutReport, String str) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isConnectTimeoutReport ? (byte) 1 : 0), str}, (Object) null, changeQuickRedirect, true, 8529, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            try {
                if (a.size() > 0) {
                    ProcedureCollector.removeTimeoutMessage();
                    List<AutoConnectDeviceStepBean> list = a;
                    int reportCode = list.get(list.size() - 1).code;
                    int i = d;
                    if (i == reportCode && i == e.CODE_SCAN_NOT_FOUND_DEVICE.getCode()) {
                        d = reportCode;
                        com.leedarson.serviceimpl.elkstrays.b.a("autoConnect埋点：已上报扫描不到设备，不再上报");
                        a.clear();
                        return;
                    }
                    if (!isConnectTimeoutReport) {
                        com.leedarson.serviceimpl.elkstrays.b.c("mesh成功,等待20s统计mesh网络节点上线情况");
                        h();
                    } else {
                        List<AutoConnectDeviceStepBean> list2 = a;
                        d = list2.get(list2.size() - 1).code;
                        List<AutoConnectDeviceStepBean> list3 = a;
                        String str2 = list3.get(list3.size() - 1).step;
                        com.leedarson.serviceimpl.elkstrays.b.c("autoConnect埋点：120s超时未连接上, reportCode:" + d);
                        a.clear();
                        u d2 = u.d();
                        d2.e(MeshConstants.TRACE_ID_AUTO_CONNECT + e);
                        b = false;
                    }
                    return;
                }
                com.leedarson.serviceimpl.elkstrays.b.a("autoConnect埋点：没有步骤，不需要上报elk");
            } catch (Exception e2) {
                MeshLog.e("autoConnect report exception:" + e2.getMessage());
            }
        }
    }

    private static void h() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 8530, new Class[0], Void.TYPE).isSupported) {
            b bVar = g;
            if (bVar != null && !bVar.isDisposed()) {
                g.dispose();
            }
            l.D(0, 10, 0, 1000, TimeUnit.MILLISECONDS).J(io.reactivex.schedulers.a.d()).a(new a());
        }
    }

    /* compiled from: AutoConnectDevicesReporter */
    public class a implements q<Long> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void onNext(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8536, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Long) obj);
            }
        }

        public void onSubscribe(b d) {
            if (!PatchProxy.proxy(new Object[]{d}, this, changeQuickRedirect, false, 8533, new Class[]{b.class}, Void.TYPE).isSupported) {
                b unused = c.g = d;
            }
        }

        public void a(Long l) {
            if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 8534, new Class[]{Long.class}, Void.TYPE).isSupported) {
                u d = u.d();
                com.leedarson.log.tracker.a eventTracker = d.c(MeshConstants.TRACE_ID_AUTO_CONNECT + c.e);
                List<NodeInfo> nodeInfos = SIGMesh.getInstance().getMeshInfo().nodes;
                List<String> onlineDevices = new ArrayList<>();
                if (nodeInfos != null) {
                    for (NodeInfo nodeInfo : nodeInfos) {
                        if (nodeInfo.isOnline()) {
                            onlineDevices.add(nodeInfo.macAddress);
                        }
                    }
                }
                if (eventTracker != null) {
                    eventTracker.d("onlineDevice", onlineDevices);
                    if (nodeInfos.size() == onlineDevices.size()) {
                        eventTracker.d("desc", "连接成功，所有设备都上线了");
                    } else {
                        eventTracker.d("desc", "连接成功，部分设备未上线");
                    }
                    eventTracker.d("totalDuration", Long.valueOf(System.currentTimeMillis() - ((d) eventTracker).l));
                    eventTracker.m();
                }
                if (nodeInfos.size() == onlineDevices.size()) {
                    onComplete();
                    c.g.dispose();
                }
            }
        }

        public void onError(Throwable e) {
        }

        public void onComplete() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8535, new Class[0], Void.TYPE).isSupported) {
                List<AutoConnectDeviceStepBean> list = c.a;
                c.d = list.get(list.size() - 1).code;
                com.leedarson.serviceimpl.elkstrays.b.c("mesh连接成功,上报当前节点情况，traceid:" + MeshConstants.TRACE_ID_AUTO_CONNECT + c.e);
                c.a.clear();
                u d = u.d();
                d.e(MeshConstants.TRACE_ID_AUTO_CONNECT + c.e);
                c.b = false;
            }
        }
    }

    public static boolean d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 8531, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (SIGMesh.getInstance().getMeshInfo().nodes == null || SIGMesh.getInstance().getMeshInfo().nodes.size() == 0) {
            return false;
        }
        for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
            if (!SqlManager.delNodesContains(nodeInfo.macAddress)) {
                return true;
            }
        }
        return false;
    }

    public static void f(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8532, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.elkstrays.b.a(log);
        }
    }
}
