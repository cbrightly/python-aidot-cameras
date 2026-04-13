package com.leedarson.newui.door_phone.repos;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceimpl.http.manager.b0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.b;
import io.reactivex.e;
import io.reactivex.f;
import io.reactivex.g;
import java.util.Locale;
import org.json.JSONObject;

/* compiled from: DoorBellWakeUpRepos */
public class d extends g {
    public static ChangeQuickRedirect changeQuickRedirect;

    /* compiled from: DoorBellWakeUpRepos */
    public class a implements g<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a;
        final /* synthetic */ String b;

        a(boolean z, String str) {
            this.a = z;
            this.b = str;
        }

        public void subscribe(f<Boolean> emitter) {
            if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 4122, new Class[]{f.class}, Void.TYPE).isSupported) {
                JSONObject bodyJson = new JSONObject();
                bodyJson.put("status", (Object) this.a ? "wakeup" : "sleep");
                b0.b().O(BaseApplication.b(), (b<com.trello.rxlifecycle3.android.a>) null, String.format(Locale.US, "/api/ipc/devices/%s/lowPowerActiveState", new Object[]{this.b}), new JSONObject().toString(), bodyJson.toString(), new C0110a(emitter));
            }
        }

        /* renamed from: com.leedarson.newui.door_phone.repos.d$a$a  reason: collision with other inner class name */
        /* compiled from: DoorBellWakeUpRepos */
        public class C0110a extends i<String> {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ f c;

            C0110a(f fVar) {
                this.c = fVar;
            }

            public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4125, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    onSuccess((String) obj);
                }
            }

            public void onStart(io.reactivex.disposables.b d2) {
            }

            public void onError(ApiException e) {
                if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 4123, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                    this.c.onError(e);
                }
            }

            public void onSuccess(String response) {
                if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4124, new Class[]{String.class}, Void.TYPE).isSupported) {
                    try {
                        JSONObject data = new JSONObject(response);
                        if (!data.has("code") || data.getInt("code") != 200) {
                            this.c.onError(new ApiException(-2100, "数据解析失败"));
                            return;
                        }
                        this.c.onNext(Boolean.valueOf(response.contains("wakeup")));
                        this.c.onComplete();
                    } catch (Exception ex) {
                        this.c.onError(new ApiException(-1000, ex.toString()));
                    }
                }
            }
        }
    }

    public e<Boolean> c(String deviceId, boolean flagWakeUp) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, new Byte(flagWakeUp ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4121, new Class[]{String.class, Boolean.TYPE}, e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new a(flagWakeUp, deviceId), io.reactivex.a.DROP).c(l.c());
    }
}
