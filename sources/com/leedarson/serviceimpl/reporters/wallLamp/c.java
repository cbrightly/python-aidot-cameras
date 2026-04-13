package com.leedarson.serviceimpl.reporters.wallLamp;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.functions.e;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.model.json.SecuAlarmRule;
import org.json.JSONObject;

/* compiled from: SecurityAlarmReporterTask */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    public b a;
    public long b;
    public long c;
    public io.reactivex.disposables.b d;
    public String e;
    public String f;
    public String g;
    public Object h;

    /* compiled from: SecurityAlarmReporterTask */
    public enum b {
        CODE_SUCCESS(200, "设备响应成功"),
        CODE_FAIL(201, "发送失败"),
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
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8625, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.d;
            if (bVar != null && !bVar.isDisposed()) {
                this.d.dispose();
            }
            d.d("startTimeout");
            this.d = l.F(1).l(10, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.a()).J(io.reactivex.schedulers.a.a()).X(new a());
        }
    }

    /* compiled from: SecurityAlarmReporterTask */
    public class a implements e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8628, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Integer) obj);
            }
        }

        public void a(Integer num) {
            if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 8627, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                c cVar = c.this;
                if (cVar.a == null) {
                    cVar.a = b.CODE_SEND_FAIL_BUSY;
                }
                d.d("startTimeout report");
                d b = d.b();
                c cVar2 = c.this;
                b.e(cVar2.a, cVar2.g);
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8626, new Class[0], Void.TYPE).isSupported) {
            long currentTimeMillis = System.currentTimeMillis() - this.b;
            io.reactivex.disposables.b bVar = this.d;
            if (bVar != null && !bVar.isDisposed()) {
                this.d.dispose();
            }
            String message = "";
            if (this.a != null) {
                message = this.f + " 报警模式设置 " + this.a.desc;
            }
            JSONObject object = new JSONObject();
            try {
                Object obj = this.h;
                if (obj instanceof SecuAlarmRule) {
                    object.put("value", (Object) ((SecuAlarmRule) obj).toJson());
                }
            } catch (Exception e2) {
            }
            com.leedarson.log.elk.a p = com.leedarson.log.elk.a.y(c.class).t("BleMesh").e(this.e).o("info").b(8).p(message);
            b bVar2 = this.a;
            p.u("code", Integer.valueOf(bVar2 != null ? bVar2.code : -1)).u("mac", this.f).u("taskId", Long.valueOf(this.c)).u("payload", object.toString()).a().b();
            d.d("上报task:" + this.c + ",event:" + this.e + ",message:" + message);
        }
    }
}
