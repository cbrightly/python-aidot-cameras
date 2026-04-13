package com.leedarson.serviceimpl.reporters.preset;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.functions.e;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PresetReporterTask */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public b a;
    public long b;
    public io.reactivex.disposables.b c;
    public String d;
    public String e;
    public Object f;

    /* compiled from: PresetReporterTask */
    public enum b {
        CODE_SUCCESS(200, "发送成功"),
        CODE_FAIL(201, "发送失败"),
        CODE_SEND_FAIL_NOT_CONNECTED(BaseResp.ERR_MSG_SEND_FAIL, "发送失败，设备未连接"),
        CODE_SEND_FAIL_BUSY(401, "发送失败，指令排队超时");
        
        public static ChangeQuickRedirect changeQuickRedirect;
        int code;
        String desc;

        private b(int code2, String desc2) {
            this.code = code2;
            this.desc = desc2;
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8605, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.c;
            if (bVar != null && !bVar.isDisposed()) {
                this.c.dispose();
            }
            this.c = l.F(1).l(10, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.a()).J(io.reactivex.schedulers.a.a()).X(new C0157a());
        }
    }

    /* renamed from: com.leedarson.serviceimpl.reporters.preset.a$a  reason: collision with other inner class name */
    /* compiled from: PresetReporterTask */
    public class C0157a implements e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0157a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8608, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Integer) obj);
            }
        }

        public void a(Integer num) {
            if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 8607, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                a.this.a = b.CODE_SEND_FAIL_BUSY;
                b b = b.b();
                a aVar = a.this;
                b.e(aVar.a, aVar.e);
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8606, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.c;
            if (bVar != null && !bVar.isDisposed()) {
                this.c.dispose();
            }
            String message = "";
            if (this.a != null) {
                message = this.e + " preset " + this.a.desc;
            }
            JSONObject payload = new JSONObject();
            try {
                payload.put("desc", this.f);
                com.leedarson.log.elk.a p = com.leedarson.log.elk.a.y(a.class).t("BleMesh").e(this.d).o("info").b(8).p(message);
                b bVar2 = this.a;
                p.u("code", Integer.valueOf(bVar2 != null ? bVar2.code : -1)).u("mac", this.e).u("taskId", Long.valueOf(this.b)).u("payload", payload.toString()).a().b();
                b.d("上报task:" + this.b + ",message:" + message);
            } catch (JSONException e2) {
                throw new RuntimeException(e2);
            }
        }
    }
}
