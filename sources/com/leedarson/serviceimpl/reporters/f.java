package com.leedarson.serviceimpl.reporters;

import com.leedarson.serviceimpl.reporters.detectionmode.a;
import com.leedarson.serviceimpl.reporters.deviceControl.a;
import com.leedarson.serviceimpl.reporters.deviceControl.b;
import com.leedarson.serviceimpl.reporters.groupControl.a;
import com.leedarson.serviceimpl.reporters.preset.a;
import com.leedarson.serviceimpl.reporters.wallLamp.a;
import com.leedarson.serviceimpl.reporters.wallLamp.c;
import com.leedarson.serviceimpl.reporters.wallLamp.d;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.util.MeshConstants;

/* compiled from: GattCallbackReportDispatch */
public class f {
    public static f a = new f();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static f a() {
        return a;
    }

    public void c(String event, String macOrMeshAddr, long taskId) {
        c task;
        Class<String> cls = String.class;
        Object[] objArr = {event, macOrMeshAddr, new Long(taskId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8545, new Class[]{cls, cls, Long.TYPE}, Void.TYPE).isSupported) {
            if (MeshConstants.EVENT_DEVICE_CONTROL.equals(event)) {
                a task2 = b.b().c(taskId);
                if (task2 != null) {
                    task2.a = a.b.CODE_SEND_SUCCESS;
                }
            } else if (MeshConstants.EVENT_GROUP_CONTROL.equals(event)) {
                com.leedarson.serviceimpl.reporters.groupControl.a task3 = com.leedarson.serviceimpl.reporters.groupControl.b.b().c(taskId);
                if (task3 != null) {
                    task3.a = a.b.CODE_SEND_SUCCESS;
                }
            } else if (MeshConstants.EVENT_DEVICE_PRESET.equals(event) || MeshConstants.EVENT_GROUP_PRESET.equals(event)) {
                com.leedarson.serviceimpl.reporters.preset.b.b().e(a.b.CODE_SUCCESS, macOrMeshAddr);
            } else if (MeshConstants.EVENT_SET_DETECTION_MODE.equals(event) || MeshConstants.EVENT_SET_CURRENT_DETECTION_MODE.equals(event)) {
                com.leedarson.serviceimpl.reporters.detectionmode.a task4 = com.leedarson.serviceimpl.reporters.detectionmode.b.b().c(macOrMeshAddr);
                if (task4 != null) {
                    task4.a = a.b.CODE_SEND_SUCCESS;
                }
            } else if (MeshConstants.EVENT_SET_ENERGY_MODE.equals(event)) {
                com.leedarson.serviceimpl.reporters.wallLamp.a task5 = com.leedarson.serviceimpl.reporters.wallLamp.b.b().c(macOrMeshAddr);
                if (task5 != null) {
                    task5.a = a.b.CODE_SEND_SUCCESS;
                }
            } else if (MeshConstants.EVENT_SET_SECURITY_ALARM.equals(event) && (task = d.b().c(macOrMeshAddr)) != null) {
                task.a = c.b.CODE_SEND_SUCCESS;
            }
        }
    }

    public void b(String event, String mac, long taskId) {
        com.leedarson.serviceimpl.reporters.wallLamp.a task;
        Class<String> cls = String.class;
        Object[] objArr = {event, mac, new Long(taskId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8546, new Class[]{cls, cls, Long.TYPE}, Void.TYPE).isSupported) {
            if (MeshConstants.EVENT_DEVICE_CONTROL.equals(event)) {
                com.leedarson.serviceimpl.reporters.deviceControl.a task2 = b.b().c(taskId);
                if (task2 != null) {
                    task2.a = a.b.CODE_SEND_FAIL;
                }
            } else if (MeshConstants.EVENT_GROUP_CONTROL.equals(event)) {
                com.leedarson.serviceimpl.reporters.groupControl.a task3 = com.leedarson.serviceimpl.reporters.groupControl.b.b().c(taskId);
                if (task3 != null) {
                    com.leedarson.serviceimpl.reporters.groupControl.b.d("task:" + task3.d + "发送失败");
                    task3.a = a.b.CODE_SEND_FAIL;
                }
            } else if (MeshConstants.EVENT_DEVICE_PRESET.equals(event) || MeshConstants.EVENT_GROUP_PRESET.equals(mac)) {
                com.leedarson.serviceimpl.reporters.preset.b.b().e(a.b.CODE_FAIL, mac);
            } else if (MeshConstants.EVENT_SET_DETECTION_MODE.equals(event)) {
                com.leedarson.serviceimpl.reporters.detectionmode.b.b().e(a.b.CODE_SEND_FAIL, mac);
            } else if (MeshConstants.EVENT_SET_ENERGY_MODE.equals(event) && (task = com.leedarson.serviceimpl.reporters.wallLamp.b.b().c(mac)) != null) {
                task.a = a.b.CODE_FAIL;
            }
        }
    }
}
