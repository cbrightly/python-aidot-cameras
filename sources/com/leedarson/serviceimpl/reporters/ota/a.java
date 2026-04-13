package com.leedarson.serviceimpl.reporters.ota;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.functions.e;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import org.json.JSONObject;

/* compiled from: OTAReportTask */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public io.reactivex.disposables.b a;
    public long b;
    public long c;
    public String d;
    public Object e;
    public String f;
    public String g;
    public b h;
    public String i;
    public String j;
    public String k;

    /* compiled from: OTAReportTask */
    public enum b {
        CODE_SUCCESS(200, "OTA成功"),
        CODE_FAIL_DOWNLOAD(BaseResp.ERR_MSG_SEND_FAIL, "OTA失败，文件下载失败"),
        CODE_FAIL_CONNECT(401, "OTA失败，连接设备失败"),
        CODE_FAIL_DISCONNECT(402, "OTA失败，OTA过程中断开"),
        CODE_FAIL_AUTH(403, "OTA失败，OTA完成后校验失败"),
        CODE_FAIL_TIMEOUT(404, "OTA失败, 超时");
        
        public static ChangeQuickRedirect changeQuickRedirect;
        int code;
        String desc;

        private b(int code2, String desc2) {
            this.code = code2;
            this.desc = desc2;
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8595, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.a;
            if (bVar != null && !bVar.isDisposed()) {
                this.a.dispose();
            }
            this.a = l.F(1).l(300, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.a()).J(io.reactivex.schedulers.a.a()).X(new C0156a());
        }
    }

    /* renamed from: com.leedarson.serviceimpl.reporters.ota.a$a  reason: collision with other inner class name */
    /* compiled from: OTAReportTask */
    public class C0156a implements e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0156a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8598, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Integer) obj);
            }
        }

        public void a(Integer num) {
            if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 8597, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                a aVar = a.this;
                if (aVar.h == null) {
                    aVar.h = b.CODE_FAIL_TIMEOUT;
                }
                b b = b.b();
                a aVar2 = a.this;
                b.e(aVar2.h, "", aVar2.d);
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8596, new Class[0], Void.TYPE).isSupported) {
            long currentTimeMillis = System.currentTimeMillis() - this.c;
            io.reactivex.disposables.b bVar = this.a;
            if (bVar != null && !bVar.isDisposed()) {
                this.a.dispose();
            }
            String message = "";
            b bVar2 = this.h;
            if (bVar2 != null) {
                if (bVar2 == b.CODE_SUCCESS) {
                    message = this.g + " 从" + this.j + " " + this.h.desc;
                } else {
                    message = this.g + " 从" + this.j + " OTA " + this.h.desc + "(具体原因:" + this.i + ")";
                }
            }
            JSONObject object = new JSONObject();
            try {
                object.put("value", this.e);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.log.elk.a p = com.leedarson.log.elk.a.y(a.class).t("BleMesh").e(this.f).o("info").b(8).p(message);
            b bVar3 = this.h;
            p.u("code", Integer.valueOf(bVar3 != null ? bVar3.code : -1)).u("mac", this.g).u("taskId", Long.valueOf(this.b)).u("payload", object.toString()).a().b();
            b.d("上报task:" + this.b + ",event:" + this.f + ",message:" + message);
        }
    }
}
