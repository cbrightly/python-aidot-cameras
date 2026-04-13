package com.leedarson.serviceimpl.reporters.deviceControl;

import com.leedarson.serviceimpl.reporters.deviceControl.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.ctrl.ControlDevIntercepter;
import meshsdk.model.NodeInfo;
import meshsdk.util.LDSModel;
import org.json.JSONObject;

/* compiled from: DeviceControlReporterTaskManager */
public class b {
    private static b a = new b();
    public static ChangeQuickRedirect changeQuickRedirect;
    private List<a> b = new ArrayList();

    private b() {
    }

    public static b b() {
        return a;
    }

    public long a(ControlDevIntercepter.CtrlDevWrap ctrlDevWrap) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ctrlDevWrap}, this, changeQuickRedirect, false, 8575, new Class[]{ControlDevIntercepter.CtrlDevWrap.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        a task = new a();
        task.e = System.currentTimeMillis();
        task.b = System.currentTimeMillis();
        task.h = ctrlDevWrap;
        task.f = ctrlDevWrap.node.macAddress;
        task.d = ctrlDevWrap.modelId;
        ctrlDevWrap.taskId = task.e;
        this.b.add(task);
        task.c();
        d("添加task:" + task.e + ",value:" + ctrlDevWrap.value + ",modelname:" + LDSModel.LdsModelName.modelName(ctrlDevWrap.modelId) + ",mac:" + ctrlDevWrap.node.macAddress);
        return task.e;
    }

    public a c(long taskId) {
        Object[] objArr = {new Long(taskId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8576, new Class[]{Long.TYPE}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        List<a> list = this.b;
        if (list == null) {
            return null;
        }
        for (a task : list) {
            if (task.e == taskId) {
                return task;
            }
        }
        return null;
    }

    public void g(NodeInfo nodeInfo, int i, String str) {
        int modelId;
        String str2;
        int modelId2;
        String str3;
        String str4;
        int modelId3;
        String str5;
        String str6;
        String str7 = ",";
        Object[] objArr = {nodeInfo, new Integer(i), str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8577, new Class[]{NodeInfo.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            int modelId4 = i;
            NodeInfo statusChangedNode = nodeInfo;
            String ref = str;
            try {
                Iterator<a> it = this.b.iterator();
                while (it.hasNext()) {
                    a task = it.next();
                    if (task.d != modelId4 || !task.f.equals(statusChangedNode.macAddress)) {
                        modelId = modelId4;
                        str2 = str7;
                    } else {
                        if (modelId4 == 4096 || modelId4 == 4864) {
                            modelId2 = modelId4;
                            str4 = str7;
                            str3 = ",value:";
                        } else if (modelId4 == 4867) {
                            modelId2 = modelId4;
                            str4 = str7;
                            str3 = ",value:";
                        } else if (modelId4 == 4871) {
                            try {
                                JSONObject object = new JSONObject(String.valueOf(task.h.value));
                                double hue = object.optDouble("HSLHue");
                                double sat = object.optDouble("HSLSaturation");
                                double light = object.optDouble("HSLLightness");
                                JSONObject jSONObject = object;
                                modelId3 = modelId4;
                                try {
                                    str5 = ",value:";
                                    str6 = str7;
                                } catch (Exception e) {
                                    str2 = str7;
                                    modelId = modelId3;
                                    str7 = str2;
                                    modelId4 = modelId;
                                }
                                try {
                                    double d = hue;
                                    if (Math.abs(hue - ((double) statusChangedNode.hue)) > ((double) 3) || Math.abs(sat - ((double) statusChangedNode.sat)) > ((double) 3) || Math.abs(light - ((double) statusChangedNode.light)) > ((double) 3)) {
                                        if (task.c == 0) {
                                            task.c = System.currentTimeMillis();
                                        }
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("设备响应,taskId:");
                                        sb.append(task.e);
                                        sb.append(str5);
                                        sb.append(statusChangedNode.hue);
                                        str2 = str6;
                                        try {
                                            sb.append(str2);
                                            sb.append(statusChangedNode.sat);
                                            sb.append(str2);
                                            sb.append(statusChangedNode.light);
                                            sb.append("不一致, 来源:");
                                            sb.append(ref);
                                            d(sb.toString());
                                            task.a = a.b.CODE_SUCCESS_NOT_MATCH;
                                        } catch (Exception e2) {
                                            modelId = modelId3;
                                            str7 = str2;
                                            modelId4 = modelId;
                                        }
                                    } else {
                                        task.c = System.currentTimeMillis();
                                        d("设备响应,taskId:" + task.e + "一致，上报,来源:" + ref);
                                        task.a = a.b.CODE_SUCCESS;
                                        it.remove();
                                        f(task);
                                        str2 = str6;
                                    }
                                    modelId = modelId3;
                                } catch (Exception e3) {
                                    str2 = str6;
                                    modelId = modelId3;
                                    str7 = str2;
                                    modelId4 = modelId;
                                }
                            } catch (Exception e4) {
                                modelId3 = modelId4;
                                str2 = str7;
                                modelId = modelId3;
                                str7 = str2;
                                modelId4 = modelId;
                            }
                        } else {
                            int modelId5 = modelId4;
                            str2 = str7;
                            modelId = modelId5;
                        }
                        int notifyValue = 0;
                        modelId = modelId2;
                        if (modelId == 4096) {
                            try {
                                notifyValue = statusChangedNode.getOnOff();
                            } catch (Exception e5) {
                                e = e5;
                                e.printStackTrace();
                                d("responseValue exception:" + e.getMessage());
                            }
                        } else if (modelId == 4864) {
                            notifyValue = statusChangedNode.lum;
                        } else if (modelId == 4867) {
                            notifyValue = statusChangedNode.temp;
                        }
                        if (notifyValue == ((Integer) task.h.value).intValue()) {
                            task.c = System.currentTimeMillis();
                            task.a = a.b.CODE_SUCCESS;
                            d("设备响应,taskId:" + task.e + " 一致,上报 来源:" + ref);
                            it.remove();
                            f(task);
                        } else {
                            if (task.c == 0) {
                                task.c = System.currentTimeMillis();
                            }
                            d("设备响应,taskId:" + task.e + str3 + notifyValue + " 不一致,来源:" + ref);
                            task.a = a.b.CODE_SUCCESS_NOT_MATCH;
                        }
                    }
                    str7 = str2;
                    modelId4 = modelId;
                }
            } catch (Exception e6) {
                e = e6;
                int i2 = modelId4;
                e.printStackTrace();
                d("responseValue exception:" + e.getMessage());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0029, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(com.leedarson.serviceimpl.reporters.deviceControl.a.b r9, long r10) {
        /*
            r8 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            java.lang.Long r3 = new java.lang.Long
            r3.<init>(r10)
            r4 = 1
            r1[r4] = r3
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceimpl.reporters.deviceControl.a$b> r0 = com.leedarson.serviceimpl.reporters.deviceControl.a.b.class
            r6[r2] = r0
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r4] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 8578(0x2182, float:1.202E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0029
            return
        L_0x0029:
            r0 = r8
            com.leedarson.serviceimpl.reporters.deviceControl.a r1 = r0.c(r10)
            if (r1 == 0) goto L_0x0035
            r1.a = r9
            r0.f(r1)
        L_0x0035:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.reporters.deviceControl.b.e(com.leedarson.serviceimpl.reporters.deviceControl.a$b, long):void");
    }

    public void f(a task) {
        if (!PatchProxy.proxy(new Object[]{task}, this, changeQuickRedirect, false, 8579, new Class[]{a.class}, Void.TYPE).isSupported) {
            if (task != null) {
                task.b();
                if (this.b.contains(task)) {
                    this.b.remove(task);
                }
            }
        }
    }

    public static void d(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8580, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i("DeControlReporterManager=>" + log);
        }
    }
}
