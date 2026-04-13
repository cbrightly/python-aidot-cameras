package smarthome.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.j;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.BitmapUtils;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import io.reactivex.f;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import meshsdk.ctrl.GroupCtrlAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import smarthome.bean.AdvertBean;
import smarthome.ui.AdvertPageDialog;
import timber.log.a;

/* compiled from: AdvertManager */
public class h {
    private String a = "AdvertManager";
    /* access modifiers changed from: private */
    public String b;
    private boolean c = false;
    private boolean d = false;
    /* access modifiers changed from: private */
    public RxAppCompatActivity e;
    /* access modifiers changed from: private */
    public ArrayList<AdvertBean> f;
    /* access modifiers changed from: private */
    public int g = 0;
    private e h;

    /* compiled from: AdvertManager */
    public interface e {
        void a(List<AdvertBean> list);
    }

    public h(RxAppCompatActivity activity) {
        this.e = activity;
        this.b = SharePreferenceUtils.getPrefString(activity, "httpServer", "");
    }

    public boolean q() {
        return this.c;
    }

    /* access modifiers changed from: private */
    public void a(String msg) {
        timber.log.a.g(this.a).m(msg, new Object[0]);
    }

    /* access modifiers changed from: private */
    public void b(String msg) {
        timber.log.a.g(this.a).c(msg, new Object[0]);
    }

    public void p() {
        this.c = true;
        JSONObject headerJson = new JSONObject();
        JSONObject paramsJson = new JSONObject();
        String accessToken = SharePreferenceUtils.getPrefString(this.e, "accessToken", "");
        RxAppCompatActivity rxAppCompatActivity = this.e;
        String t = w.t(SharePreferenceUtils.getPrefString(rxAppCompatActivity, "webVersion", SharePreferenceUtils.getPrefString(rxAppCompatActivity, "WEB_VERSION", "")), SharePreferenceUtils.getPrefString(this.e, "WEB_VERSION", ""));
        try {
            String currentVersion = w.H(this.e);
            headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(this.e, "APP_ID", ""));
            if (!TextUtils.isEmpty(accessToken)) {
                headerJson.put("token", (Object) accessToken);
            }
            headerJson.put("terminal", (Object) "app");
            paramsJson.put("os", (Object) ExifInterface.GPS_MEASUREMENT_3D);
            paramsJson.put("nativeVersion", (Object) currentVersion);
            paramsJson.put("pictureSize", (Object) BitmapUtils.getScreenHeight(this.e) + org.slf4j.e.ANY_MARKER + BitmapUtils.getScreenWidth(this.e));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        b0.b().K(this.e, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, this.b + "/v10/commons/splashScreens", headerJson.toString(), paramsJson.toString(), new a());
    }

    /* compiled from: AdvertManager */
    public class a extends i<String> {
        a() {
        }

        /* access modifiers changed from: protected */
        public void onStart(io.reactivex.disposables.b d) {
            timber.log.a.g("Advert").a("getAdvertInfo", new Object[0]);
        }

        /* access modifiers changed from: protected */
        public void onError(ApiException e) {
            h hVar = h.this;
            hVar.b("getAdvertInfo http onError:" + e.getCode() + "," + e.getMsg());
        }

        /* access modifiers changed from: protected */
        public void onSuccess(String response) {
            h hVar = h.this;
            hVar.a("getAdvertInfo http onSuccess:" + response);
            if (!TextUtils.isEmpty(response)) {
                try {
                    Type type = new C0495a().getType();
                    ArrayList unused = h.this.f = (ArrayList) new Gson().fromJson(response, type);
                    if (h.this.f != null) {
                        if (h.this.f.size() == 0) {
                            h.this.o();
                        }
                        if (h.this.f.size() == 1) {
                            if (TextUtils.isEmpty(SharePreferenceUtils.getPrefString(h.this.e, ((AdvertBean) h.this.f.get(0)).getFileId(), ""))) {
                                h hVar2 = h.this;
                                hVar2.E(hVar2.b, 0);
                            }
                        } else if (h.this.f.size() > 1) {
                            Random random = new Random();
                            h hVar3 = h.this;
                            int unused2 = hVar3.g = random.nextInt(hVar3.f.size());
                            h hVar4 = h.this;
                            hVar4.a("Advert:showIndex= " + h.this.g);
                            int i = 0;
                            while (i < h.this.f.size()) {
                                if (TextUtils.isEmpty(SharePreferenceUtils.getPrefString(h.this.e, ((AdvertBean) h.this.f.get(i)).getFileId(), ""))) {
                                    h hVar5 = h.this;
                                    hVar5.E(hVar5.b, i);
                                    i++;
                                } else {
                                    return;
                                }
                            }
                        }
                        h.this.F();
                        return;
                    }
                    h.this.o();
                } catch (Exception e) {
                    h hVar6 = h.this;
                    hVar6.b("转化异常：getAdvertInfo" + e.toString());
                }
            } else {
                h.this.o();
            }
        }

        /* renamed from: smarthome.utils.h$a$a  reason: collision with other inner class name */
        /* compiled from: AdvertManager */
        public class C0495a extends TypeToken<ArrayList<AdvertBean>> {
            C0495a() {
            }
        }
    }

    public void D() {
        io.reactivex.e.d(new g(this), io.reactivex.a.DROP).c(l.b()).I(new f(this), new c(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: r */
    public /* synthetic */ void s(f emitter) {
        try {
            ArrayList<AdvertBean> arrayList = (ArrayList) com.leedarson.base.utils.l.c(this.e, "com.leedarson.base.ADVERT");
            this.f = arrayList;
            if (arrayList == null) {
                this.f = new ArrayList<>();
            }
            a("local advertBeans size:" + this.f.size());
            emitter.onNext(this.f);
        } catch (Exception e2) {
            b("loadLocalAdVertInfo.exception  e=" + e2.toString());
            ArrayList<AdvertBean> arrayList2 = new ArrayList<>();
            this.f = arrayList2;
            emitter.onNext(arrayList2);
        }
        emitter.onComplete();
    }

    /* access modifiers changed from: private */
    /* renamed from: v */
    public /* synthetic */ void w(ArrayList advertBeans) {
        if (advertBeans.size() > 0) {
            new Handler(Looper.getMainLooper()).postDelayed(new e(this, advertBeans), 200);
            n();
        } else {
            e eVar = this.h;
            if (eVar != null) {
                eVar.a(advertBeans);
            }
        }
        p();
    }

    /* access modifiers changed from: private */
    /* renamed from: t */
    public /* synthetic */ void u(ArrayList advertBeans) {
        e eVar = this.h;
        if (eVar != null) {
            eVar.a(advertBeans);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: x */
    public /* synthetic */ void y(Throwable throwable) {
        b("loadLocalAdvertInfo:" + throwable.toString());
        p();
    }

    public void G(e onLoadCallback) {
        this.h = onLoadCallback;
    }

    /* access modifiers changed from: private */
    public void F() {
        io.reactivex.e.d(new a(this), io.reactivex.a.DROP).c(l.c()).I(b.c, d.c);
    }

    /* access modifiers changed from: private */
    /* renamed from: z */
    public /* synthetic */ void A(f emitter) {
        boolean flag_Result = false;
        try {
            flag_Result = com.leedarson.base.utils.l.f(this.e, "com.leedarson.base.ADVERT", this.f);
        } catch (Exception e2) {
            b("SaveLocalAdvertInfo: " + e2.toString());
        }
        emitter.onNext(Boolean.valueOf(flag_Result));
        emitter.onComplete();
    }

    static /* synthetic */ void B(Boolean aBoolean) {
    }

    static /* synthetic */ void C(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    public void E(String baseUrl, int index) {
        String url = baseUrl + "/commons/resources";
        JSONObject paramsJson1 = new JSONObject();
        try {
            paramsJson1.put("ids", (Object) this.f.get(index).getFileId());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        b0 b2 = b0.b();
        RxAppCompatActivity rxAppCompatActivity = this.e;
        b2.K(rxAppCompatActivity, rxAppCompatActivity, url, (String) null, paramsJson1.toString(), new b(index));
    }

    /* compiled from: AdvertManager */
    public class b extends i<String> {
        final /* synthetic */ int c;

        b(int i) {
            this.c = i;
        }

        /* access modifiers changed from: protected */
        public void onStart(io.reactivex.disposables.b d2) {
            timber.log.a.g("Advert").a("startGetUpgradeInfo", new Object[0]);
        }

        /* access modifiers changed from: protected */
        public void onError(ApiException e) {
            h hVar = h.this;
            hVar.b(" Advert: resources error=" + e.getMsg());
        }

        /* access modifiers changed from: protected */
        public void onSuccess(String response) {
            h hVar = h.this;
            hVar.a("preLoadAdvert resources response=" + response);
            if (!response.isEmpty()) {
                String fileUrl1 = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(((AdvertBean) h.this.f.get(this.c)).getFileId())) {
                        fileUrl1 = jsonObject.optString(((AdvertBean) h.this.f.get(this.c)).getFileId());
                    }
                    com.bumptech.glide.request.f options = (com.bumptech.glide.request.f) ((com.bumptech.glide.request.f) ((com.bumptech.glide.request.f) new com.bumptech.glide.request.f().m0(false)).n0(GroupCtrlAdapter.TIMEOUT_WAIT_MESH_ONLINE)).f(com.bumptech.glide.load.engine.i.a);
                    String finalFileUrl = fileUrl1;
                    h hVar2 = h.this;
                    hVar2.a("preLoadAdvert onSuccess:  imageurl" + finalFileUrl);
                    if (!TextUtils.isEmpty(finalFileUrl)) {
                        com.bumptech.glide.b.u(h.this.e).q(fileUrl1).a(options).J0(new a(finalFileUrl)).Q0();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* compiled from: AdvertManager */
        public class a implements com.bumptech.glide.request.e<Drawable> {
            final /* synthetic */ String a;

            a(String str) {
                this.a = str;
            }

            public boolean onLoadFailed(@Nullable GlideException e, Object model, j<Drawable> jVar, boolean isFirstResource) {
                return false;
            }

            /* renamed from: a */
            public boolean onResourceReady(Drawable resource, Object model, j<Drawable> jVar, com.bumptech.glide.load.a dataSource, boolean isFirstResource) {
                h hVar = h.this;
                hVar.a("preLoadAdvert preload Success:  imageurl: " + this.a);
                SharePreferenceUtils.setPrefString(h.this.e, ((AdvertBean) h.this.f.get(b.this.c)).getFileId(), this.a);
                return false;
            }
        }
    }

    public void n() {
        if (!this.d) {
            this.d = true;
            String dayTime = l.b();
            ArrayList<AdvertBean> arrayList = this.f;
            if (arrayList == null || arrayList.size() <= 1 || this.g >= this.f.size()) {
                ArrayList<AdvertBean> arrayList2 = this.f;
                if (arrayList2 != null && arrayList2.size() == 1) {
                    a.b g2 = timber.log.a.g("Advert");
                    g2.h("Advert: had showTimes=" + SharePreferenceUtils.getPrefInt(this.e, this.f.get(0).getId(), 0), new Object[0]);
                    a.b g3 = timber.log.a.g("Advert");
                    g3.h("Advert: need showTimes=" + this.f.get(0).getDisplayTimes(), new Object[0]);
                    if (!TextUtils.isEmpty(dayTime) && !dayTime.equals(SharePreferenceUtils.getPrefString(this.e, "advertTime", ""))) {
                        SharePreferenceUtils.setPrefInt(this.e, this.f.get(0).getId(), 0);
                    }
                    if ((this.f.get(this.g).getDisplayTimes() <= 0 || SharePreferenceUtils.getPrefInt(this.e, this.f.get(0).getId(), 0) <= this.f.get(0).getDisplayTimes()) && !TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this.e, this.f.get(0).getFileId(), ""))) {
                        this.e.runOnUiThread(new d());
                        return;
                    }
                    return;
                }
                return;
            }
            a.b g4 = timber.log.a.g("Advert");
            g4.h("Advert: had showTimes=" + SharePreferenceUtils.getPrefInt(this.e, this.f.get(this.g).getId(), 0), new Object[0]);
            a.b g5 = timber.log.a.g("Advert");
            g5.h("Advert: need showTimes=" + this.f.get(this.g).getDisplayTimes(), new Object[0]);
            if (!TextUtils.isEmpty(dayTime) && !dayTime.equals(SharePreferenceUtils.getPrefString(this.e, "advertTime", ""))) {
                SharePreferenceUtils.setPrefInt(this.e, this.f.get(this.g).getId(), 0);
            }
            if ((this.f.get(this.g).getDisplayTimes() <= 0 || SharePreferenceUtils.getPrefInt(this.e, this.f.get(this.g).getId(), this.g) <= this.f.get(this.g).getDisplayTimes()) && !TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this.e, this.f.get(this.g).getFileId(), ""))) {
                this.e.runOnUiThread(new c());
            }
        }
    }

    /* compiled from: AdvertManager */
    public class c implements Runnable {
        c() {
        }

        public void run() {
            try {
                new AdvertPageDialog(SharePreferenceUtils.getPrefString(h.this.e, ((AdvertBean) h.this.f.get(h.this.g)).getFileId(), ""), (AdvertBean) h.this.f.get(h.this.g)).show(h.this.e.getSupportFragmentManager(), "AdvertPageDialog");
            } catch (Exception e) {
            }
        }
    }

    /* compiled from: AdvertManager */
    public class d implements Runnable {
        d() {
        }

        public void run() {
            try {
                new AdvertPageDialog(SharePreferenceUtils.getPrefString(h.this.e, ((AdvertBean) h.this.f.get(h.this.g)).getFileId(), ""), (AdvertBean) h.this.f.get(h.this.g)).show(h.this.e.getSupportFragmentManager(), "AdvertPageDialog");
            } catch (Exception e) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void o() {
        timber.log.a.g("Advert").h("清除广告数据", new Object[0]);
        File file = new File(this.e.getFilesDir(), "com.leedarson.base.ADVERT");
        if (file.exists()) {
            file.delete();
        }
    }
}
