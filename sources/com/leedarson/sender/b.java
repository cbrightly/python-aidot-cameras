package com.leedarson.sender;

import com.alibaba.android.arouter.launcher.a;
import com.leedarson.bean.RhyBLEMeshDevice;
import com.leedarson.serviceinterface.BleMeshService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.MeshLog;
import meshsdk.datamgr.MeshDataManager;
import org.json.JSONObject;

/* compiled from: BLEMeshSender */
public class b extends e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private BleMeshService a = ((BleMeshService) a.c().g(BleMeshService.class));
    private String b;
    private String c;
    private RhyBLEMeshDevice d;

    public b(String mac, String groupId, RhyBLEMeshDevice device) {
        this.d = device;
        this.b = mac;
        this.c = groupId;
    }

    public void a(String str, JSONObject jsonObject) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{str, jsonObject}, this, changeQuickRedirect, false, 5681, clsArr, Void.TYPE).isSupported) {
            if (MeshDataManager.isFlagAddGroupByHand) {
                MeshLog.logMusicRhythmWarn("当前正在建组，不发律动指令....");
            } else if (this.a == null) {
            } else {
                if (jsonObject.has("mac")) {
                    this.a.transferRhythm(jsonObject.optString("mac"), "", jsonObject);
                } else {
                    this.a.transferRhythm(this.b, this.c, jsonObject);
                }
            }
        }
    }

    public void b(String mac, String groupId, byte able, com.leedarson.base.http.listener.a listener) {
        Class<String> cls = String.class;
        Object[] objArr = {mac, groupId, new Byte(able), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5682, new Class[]{cls, cls, Byte.TYPE, com.leedarson.base.http.listener.a.class}, Void.TYPE).isSupported) {
            this.a.setRhythmEnable(mac, groupId, able, listener);
        }
    }

    public void c(String mac, String groupId, int[] colors) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, int[].class};
        if (!PatchProxy.proxy(new Object[]{mac, groupId, colors}, this, changeQuickRedirect, false, 5683, clsArr, Void.TYPE).isSupported) {
            this.a.setRhythmTheme(mac, groupId, colors);
        }
    }
}
