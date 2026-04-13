package com.leedarson.serviceimpl.reporters.groupControl;

import com.leedarson.serviceimpl.reporters.groupControl.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.model.NodeInfo;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.LDSModel;
import org.json.JSONObject;

/* compiled from: GroupControlReporterTaskManager */
public class b {
    private static b a = new b();
    public static ChangeQuickRedirect changeQuickRedirect;
    private List<a> b = new ArrayList();

    private b() {
    }

    public static b b() {
        return a;
    }

    public long a(int groupId, int groupAddr, int modelId, Object value) {
        int i = 0;
        Object[] objArr = {new Integer(groupId), new Integer(groupAddr), new Integer(modelId), value};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8588, new Class[]{cls, cls, cls, Object.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        List<NodeInfo> nodes = LDSMeshUtil.getDevicesInGroup(groupAddr);
        List<String> customGroupNodes = LDSMeshUtil.getDevicesInCustomGroup(groupId);
        if (nodes == null) {
            return 0;
        }
        a task = new a();
        task.d = System.currentTimeMillis();
        task.b = System.currentTimeMillis();
        task.f = nodes;
        StringBuilder sb = new StringBuilder();
        sb.append("标准组下有:");
        sb.append(nodes.size());
        sb.append("个设备，本地组下有:");
        if (customGroupNodes != null) {
            i = customGroupNodes.size();
        }
        sb.append(i);
        sb.append("个设备");
        task.g = sb.toString();
        task.i = modelId;
        task.h = value;
        task.j = groupId;
        task.k = groupAddr;
        task.c();
        this.b.add(task);
        d("添加task:" + task.d + ",value:" + value + ",modelname:" + LDSModel.LdsModelName.modelName(modelId) + ",groupId:" + groupId);
        return task.d;
    }

    public a c(long taskId) {
        Object[] objArr = {new Long(taskId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8589, new Class[]{Long.TYPE}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        List<a> list = this.b;
        if (list == null) {
            return null;
        }
        for (a task : list) {
            if (task.d == taskId) {
                return task;
            }
        }
        return null;
    }

    public void h(NodeInfo nodeInfo, int i, String str) {
        b bVar;
        boolean z;
        String str2;
        boolean z2 = true;
        Object[] objArr = {nodeInfo, new Integer(i), str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8590, new Class[]{NodeInfo.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            b bVar2 = this;
            int modelId = i;
            NodeInfo statusChangedNode = nodeInfo;
            String ref = str;
            try {
                for (a task : bVar2.b) {
                    try {
                        if (modelId == task.i) {
                            Iterator<NodeInfo> iterator = task.f.iterator();
                            while (iterator.hasNext()) {
                                if (iterator.next().macAddress.equals(statusChangedNode.macAddress)) {
                                    iterator.remove();
                                    if (modelId == 4096 || modelId == 4864) {
                                        bVar = bVar2;
                                        str2 = "响应,值不一致,来源:";
                                    } else if (modelId == 4867) {
                                        bVar = bVar2;
                                        str2 = "响应,值不一致,来源:";
                                    } else if (modelId == 4871) {
                                        try {
                                            JSONObject object = new JSONObject(String.valueOf(task.h));
                                            double hue = object.optDouble("HSLHue");
                                            double sat = object.optDouble("HSLSaturation");
                                            double light = object.optDouble("HSLLightness");
                                            String str3 = "响应,值不一致,来源:";
                                            bVar = bVar2;
                                            try {
                                                task.c = System.currentTimeMillis();
                                                JSONObject jSONObject = object;
                                                double d = hue;
                                                if (Math.abs(hue - ((double) statusChangedNode.hue)) > ((double) 3) || Math.abs(sat - ((double) statusChangedNode.sat)) > ((double) 3) || Math.abs(light - ((double) statusChangedNode.light)) > ((double) 3)) {
                                                    task.l = true;
                                                    d("task:" + task.d + ",收到设备:" + statusChangedNode.macAddress + LDSModel.LdsModelName.modelName(modelId) + str3 + ref);
                                                } else {
                                                    task.m = true;
                                                    d("task:" + task.d + ",收到设备:" + statusChangedNode.macAddress + LDSModel.LdsModelName.modelName(modelId) + "响应,值一致,来源:" + ref);
                                                }
                                                z = true;
                                            } catch (Exception e) {
                                                z = true;
                                                z2 = z;
                                                bVar2 = bVar;
                                            }
                                        } catch (Exception e2) {
                                            bVar = bVar2;
                                            z = true;
                                            z2 = z;
                                            bVar2 = bVar;
                                        }
                                    } else {
                                        bVar = bVar2;
                                        z = true;
                                    }
                                    int notifyValue = 0;
                                    if (modelId == 4096) {
                                        try {
                                            notifyValue = statusChangedNode.getOnOff();
                                        } catch (Exception e3) {
                                            e = e3;
                                            b bVar3 = bVar;
                                            e.printStackTrace();
                                            e("responseValue exception:" + e.getMessage() + ",etostring:" + e.toString());
                                        }
                                    } else if (modelId == 4864) {
                                        notifyValue = statusChangedNode.lum;
                                    } else if (modelId == 4867) {
                                        notifyValue = statusChangedNode.temp;
                                    }
                                    task.c = System.currentTimeMillis();
                                    if (notifyValue != ((Integer) task.h).intValue()) {
                                        task.l = true;
                                        d("task:" + task.d + ",收到设备:" + statusChangedNode.macAddress + LDSModel.LdsModelName.modelName(modelId) + str2 + ref);
                                        z = true;
                                    } else {
                                        z = true;
                                        task.m = true;
                                        d("task:" + task.d + ",收到设备:" + statusChangedNode.macAddress + LDSModel.LdsModelName.modelName(modelId) + "响应,值一致,来源:" + ref);
                                    }
                                } else {
                                    bVar = bVar2;
                                    z = z2;
                                }
                                z2 = z;
                                bVar2 = bVar;
                            }
                        }
                        z2 = z2;
                        bVar2 = bVar2;
                    } catch (Exception e4) {
                        e = e4;
                        e.printStackTrace();
                        e("responseValue exception:" + e.getMessage() + ",etostring:" + e.toString());
                    }
                }
                b bVar4 = bVar2;
                Iterator<a> it = bVar2.b.iterator();
                while (it.hasNext()) {
                    a task2 = it.next();
                    if (modelId == task2.i) {
                        if (task2.f.size() == 0) {
                            it.remove();
                            d("task:" + task2.d + "下所有设备处理完成");
                            task2.c = System.currentTimeMillis();
                            if (task2.l) {
                                task2.a = a.b.CODE_SUCCESS_NOT_MATCH;
                                bVar2.g(task2);
                            } else {
                                task2.a = a.b.CODE_SUCCESS;
                                bVar2.g(task2);
                            }
                        } else {
                            d("task:" + task2.d + "下还有:" + task2.f.size() + "个设备待处理, modelid:" + modelId);
                        }
                    }
                }
            } catch (Exception e5) {
                e = e5;
                e.printStackTrace();
                e("responseValue exception:" + e.getMessage() + ",etostring:" + e.toString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0029, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f(com.leedarson.serviceimpl.reporters.groupControl.a.b r9, long r10) {
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
            java.lang.Class<com.leedarson.serviceimpl.reporters.groupControl.a$b> r0 = com.leedarson.serviceimpl.reporters.groupControl.a.b.class
            r6[r2] = r0
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r4] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 8591(0x218f, float:1.2039E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0029
            return
        L_0x0029:
            r0 = r8
            com.leedarson.serviceimpl.reporters.groupControl.a r1 = r0.c(r10)
            if (r1 == 0) goto L_0x0035
            r1.a = r9
            r0.g(r1)
        L_0x0035:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.reporters.groupControl.b.f(com.leedarson.serviceimpl.reporters.groupControl.a$b, long):void");
    }

    public void g(a task) {
        if (!PatchProxy.proxy(new Object[]{task}, this, changeQuickRedirect, false, 8592, new Class[]{a.class}, Void.TYPE).isSupported) {
            if (task != null) {
                task.b();
                if (this.b.contains(task)) {
                    this.b.remove(task);
                }
            }
        }
    }

    public static void d(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8593, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i("GrControlReporterManager=>" + log);
        }
    }

    public static void e(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8594, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.e("GrControlReporterManager=>" + log);
        }
    }
}
