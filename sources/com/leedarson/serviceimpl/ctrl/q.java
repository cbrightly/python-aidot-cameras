package com.leedarson.serviceimpl.ctrl;

import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.leedarson.base.utils.p;
import com.leedarson.serviceimpl.MatterServiceImpl;
import com.leedarson.serviceimpl.bean.MtNode;
import com.leedarson.serviceimpl.i;
import com.leedarson.serviceimpl.manager.d;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.y;
import meshsdk.BaseResp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: RemoveDeviceCtrl.kt */
public final class q {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final String a;
    @NotNull
    private final JSONArray b;
    @NotNull
    private final MatterServiceImpl c;
    private int d;
    private boolean e;
    @NotNull
    private JSONArray f = new JSONArray();
    @Nullable
    private a g;

    /* compiled from: RemoveDeviceCtrl.kt */
    public interface a {
        void onFail(@NotNull String str);

        void onStart();

        void onSuccess(@NotNull String str);
    }

    public q(@NotNull JSONArray arr, @NotNull String callback, @NotNull MatterServiceImpl service) {
        k.e(arr, "arr");
        k.e(callback, "callback");
        k.e(service, NotificationCompat.CATEGORY_SERVICE);
        this.a = callback;
        this.b = arr;
        this.c = service;
    }

    public static final /* synthetic */ void a(q $this) {
        if (!PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 7815, new Class[]{q.class}, Void.TYPE).isSupported) {
            $this.d();
        }
    }

    public static final /* synthetic */ void b(q $this) {
        if (!PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 7814, new Class[]{q.class}, Void.TYPE).isSupported) {
            $this.f();
        }
    }

    public final void g(boolean z) {
        this.e = z;
    }

    @NotNull
    public final JSONArray c() {
        return this.f;
    }

    public final void h(@Nullable a aVar) {
        this.g = aVar;
    }

    public final void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7811, new Class[0], Void.TYPE).isSupported) {
            d.a.e();
            new Handler(Looper.getMainLooper()).postDelayed(new b(this), 200);
        }
    }

    /* compiled from: RemoveDeviceCtrl.kt */
    public static final class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ q c;

        b(q $receiver) {
            this.c = $receiver;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7816, new Class[0], Void.TYPE).isSupported) {
                q.b(this.c);
            }
        }
    }

    private final void f() {
        int i = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7812, new Class[0], Void.TYPE).isSupported) {
            a aVar = this.g;
            if (aVar != null) {
                aVar.onStart();
            }
            int length = this.b.length();
            if (length > 0) {
                do {
                    int i2 = i;
                    i++;
                    y addr = new y();
                    if (this.b.get(i2) instanceof Integer) {
                        Object obj = this.b.get(i2);
                        if (obj != null) {
                            addr.element = (long) ((Integer) obj).intValue();
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
                        }
                    } else if (this.b.get(i2) instanceof Long) {
                        Object obj2 = this.b.get(i2);
                        if (obj2 != null) {
                            addr.element = ((Long) obj2).longValue();
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Long");
                        }
                    } else {
                        addr.element = Long.parseLong(this.b.get(i2).toString());
                    }
                    i.a.D(addr.element, new c(addr, this));
                } while (i < length);
            }
        }
    }

    /* compiled from: RemoveDeviceCtrl.kt */
    public static final class c implements com.leedarson.serviceimpl.listener.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ y a;
        final /* synthetic */ q b;

        c(y $addr, q $receiver) {
            this.a = $addr;
            this.b = $receiver;
        }

        public void onSuccess(long j, @NotNull Object data) {
            Object[] objArr = {new Long(j), data};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7817, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
                k.e(data, "data");
                d dVar = d.a;
                MtNode b2 = dVar.b(this.a.element);
                if (b2 != null) {
                    b2.shutDown();
                }
                dVar.f(this.a.element);
                JSONObject item = new JSONObject();
                item.put("code", 200);
                item.put("matterAddr", this.a.element);
                this.b.c().put((Object) item);
                q.a(this.b);
            }
        }

        public void onFail(int code, @NotNull Exception ex) {
            Object[] objArr = {new Integer(code), ex};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7818, new Class[]{Integer.TYPE, Exception.class}, Void.TYPE).isSupported) {
                k.e(ex, "ex");
                d dVar = d.a;
                MtNode b2 = dVar.b(this.a.element);
                if (b2 != null) {
                    b2.shutDown();
                }
                dVar.f(this.a.element);
                this.b.g(true);
                JSONObject item = new JSONObject();
                item.put("code", code);
                item.put("matterAddr", this.a.element);
                item.put("ex", (Object) String.valueOf(ex.getMessage()));
                this.b.c().put((Object) item);
                q.a(this.b);
            }
        }
    }

    private final void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7813, new Class[0], Void.TYPE).isSupported) {
            int i = this.d + 1;
            this.d = i;
            if (i != this.b.length()) {
                return;
            }
            if (this.e) {
                JSONObject respJson = p.b(BaseResp.ERR_MSG_SEND_FAIL, this.f);
                a aVar = this.g;
                if (aVar != null) {
                    String jSONObject = respJson.toString();
                    k.d(jSONObject, "respJson.toString()");
                    aVar.onFail(jSONObject);
                }
                this.c.postJsBridgeCallback(this.a, respJson.toString(), "removeDevice");
                return;
            }
            a aVar2 = this.g;
            if (aVar2 != null) {
                String jSONObject2 = p.d(this.f).toString();
                k.d(jSONObject2, "generatorSuccessResp(resp).toString()");
                aVar2.onSuccess(jSONObject2);
            }
            this.c.postJsBridgeCallback(this.a, p.d(this.f).toString(), "removeDevice");
        }
    }
}
