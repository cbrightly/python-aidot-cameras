package com.leedarson.serviceimpl.reporters.groupControl;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import io.reactivex.l;
import java.util.List;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.SIGMesh;
import meshsdk.model.GroupInfo;
import meshsdk.model.NodeInfo;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.LDSModel;
import meshsdk.util.MeshConstants;
import org.json.JSONObject;

/* compiled from: GroupControlReporterTask */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public b a;
    public long b;
    public long c;
    public long d;
    public io.reactivex.disposables.b e;
    public List<NodeInfo> f;
    public String g;
    public Object h;
    public int i;
    public int j;
    public int k;
    public boolean l = false;
    public boolean m = false;

    /* compiled from: GroupControlReporterTask */
    public enum b {
        CODE_SUCCESS(200, "发送成功,组下所有设备响应成功"),
        CODE_SUCCESS_NOT_MATCH(201, "发送成功，组下所有设备响应成功，但存在值不匹配"),
        CODE_SEND_SUCCESS(KitWrapItem.TYPE_MODE, "发送成功，未收到组下任何设备的响应"),
        CODE_SUCCESS_SAME(KitWrapItem.TYPE_EXIT, "发送成功，收到部分设备响应成功"),
        CODE_SUCCESS_SAME_NOT_MATCH(KitWrapItem.TYPE_VERSION, "发送成功，收到部分响应成功,但值不匹配"),
        CODE_SEND_FAIL_NOT_CONNECTED(BaseResp.ERR_MSG_SEND_FAIL, "发送失败，设备未连接"),
        CODE_SEND_FAIL_BUSY(401, "发送失败，指令排队超时"),
        CODE_SEND_FAIL(402, "ble回调发送失败");
        
        public static ChangeQuickRedirect changeQuickRedirect;
        int code;
        String desc;

        private b(int code2, String desc2) {
            this.code = code2;
            this.desc = desc2;
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8581, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.e;
            if (bVar != null && !bVar.isDisposed()) {
                this.e.dispose();
            }
            this.e = l.F(1).l(10, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.a()).J(io.reactivex.schedulers.a.a()).X(new C0155a());
        }
    }

    /* renamed from: com.leedarson.serviceimpl.reporters.groupControl.a$a  reason: collision with other inner class name */
    /* compiled from: GroupControlReporterTask */
    public class C0155a implements e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0155a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8585, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Integer) obj);
            }
        }

        public void a(Integer num) {
            if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 8584, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                a aVar = a.this;
                if (aVar.l) {
                    aVar.a = b.CODE_SUCCESS_SAME_NOT_MATCH;
                } else if (aVar.m) {
                    aVar.a = b.CODE_SUCCESS_SAME;
                }
                b.b().g(a.this);
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8582, new Class[0], Void.TYPE).isSupported) {
            long duration = 0;
            long j2 = this.c;
            if (j2 > 0) {
                duration = j2 - this.b;
            }
            io.reactivex.disposables.b bVar = this.e;
            if (bVar != null && !bVar.isDisposed()) {
                this.e.dispose();
            }
            String message = "";
            if (this.a != null) {
                message = "groupId:" + this.j + " " + LDSModel.LdsModelName.modelName(this.i) + "=" + this.h + " " + this.a.desc + " 耗时" + duration + "ms";
            }
            JSONObject payload = new JSONObject();
            int groupAddress = 0;
            try {
                GroupInfo group = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, this.j);
                if (group != null) {
                    groupAddress = group.address;
                }
                payload.put("groupId", this.j);
                payload.put("groupAddress", (Object) String.format("0x%04X", new Object[]{Integer.valueOf(groupAddress)}));
                payload.put("desc", (Object) this.g + "," + this.f.size() + "个设备未响应结果");
            } catch (Exception e2) {
            }
            com.leedarson.log.elk.a p = com.leedarson.log.elk.a.y(a.class).t("BleMesh").e(MeshConstants.EVENT_GROUP_CONTROL).o("info").b(8).p(message);
            b bVar2 = this.a;
            p.u("code", Integer.valueOf(bVar2 != null ? bVar2.code : -1)).u("payload", payload.toString()).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration)).u("taskId", Long.valueOf(this.d)).u("inRhym", Boolean.valueOf(a())).a().b();
            b.d("上报task:" + this.d + ",message:" + message);
        }
    }

    public boolean a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8583, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        LightsRhythmService rhythmService = (LightsRhythmService) com.alibaba.android.arouter.launcher.a.c().g(LightsRhythmService.class);
        if (rhythmService != null) {
            return rhythmService.isMeshRhythm();
        }
        return false;
    }
}
