package com.leedarson.serviceimpl.reporters.detectionmode;

import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.functions.e;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.model.json.DetectMode;
import org.json.JSONObject;

/* compiled from: DetectionModeReporterTask */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public b a;
    public long b;
    public long c;
    public io.reactivex.disposables.b d;
    public String e;
    public String f;
    public String g;
    public Object h;

    /* compiled from: DetectionModeReporterTask */
    public enum b {
        CODE_SUCCESS(200, "设备响应成功"),
        CODE_SEND_FAIL(201, "发送失败"),
        CODE_RESPONSE_FAIL(KitWrapItem.TYPE_MODE, "设备应答成功，响应设置失败"),
        CODE_SEND_SUCCESS(BaseResp.ERR_MSG_SEND_FAIL, "发送成功，未收到设备回复"),
        CODE_SEND_FAIL_NOT_CONNECTED(401, "发送失败，设备未连接"),
        CODE_SEND_FAIL_BUSY(402, "发送失败，指令排队超时");
        
        public static ChangeQuickRedirect changeQuickRedirect;
        int code;
        String desc;

        private b(int code2, String desc2) {
            this.code = code2;
            this.desc = desc2;
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8558, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.d;
            if (bVar != null && !bVar.isDisposed()) {
                this.d.dispose();
            }
            this.d = l.F(1).l(10, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.a()).J(io.reactivex.schedulers.a.a()).X(new C0153a());
        }
    }

    /* renamed from: com.leedarson.serviceimpl.reporters.detectionmode.a$a  reason: collision with other inner class name */
    /* compiled from: DetectionModeReporterTask */
    public class C0153a implements e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0153a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8561, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Integer) obj);
            }
        }

        public void a(Integer num) {
            if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 8560, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                a aVar = a.this;
                if (aVar.a == null) {
                    aVar.a = b.CODE_SEND_FAIL_BUSY;
                }
                b b = b.b();
                a aVar2 = a.this;
                b.e(aVar2.a, aVar2.g);
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8559, new Class[0], Void.TYPE).isSupported) {
            long currentTimeMillis = System.currentTimeMillis() - this.b;
            io.reactivex.disposables.b bVar = this.d;
            if (bVar != null && !bVar.isDisposed()) {
                this.d.dispose();
            }
            String message = "";
            if (this.a != null) {
                message = this.f + " " + this.e + " " + this.a.desc;
            }
            JSONObject object = new JSONObject();
            Object obj = this.h;
            if (obj instanceof DetectMode) {
                object = ((DetectMode) obj).toJson();
            } else {
                try {
                    object.put("value", obj);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            com.leedarson.log.elk.a p = com.leedarson.log.elk.a.y(a.class).t("BleMesh").e(this.e).o("info").b(8).p(message);
            b bVar2 = this.a;
            p.u("code", Integer.valueOf(bVar2 != null ? bVar2.code : -1)).u("mac", this.f).u("taskId", Long.valueOf(this.c)).u("payload", object.toString()).a().b();
            b.d("上报task:" + this.c + ",event:" + this.e + ",message:" + message);
        }
    }
}
