package com.leedarson.skiprope.datamgr;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.skiprope.bean.SourceBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.f;
import java.io.File;
import org.json.JSONArray;

/* compiled from: SkipRopeSourceManager */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Context a;
    private final String b = "/media/skipRope/config.json";
    /* access modifiers changed from: private */
    public String c = "https://test-static.arnoo.com";
    private String d;
    /* access modifiers changed from: private */
    public f e;
    /* access modifiers changed from: private */
    public f f;

    public e(Context context) {
        this.a = context;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        String str = File.separator;
        sb.append(str);
        sb.append("skiprope");
        sb.append(str);
        this.d = sb.toString();
        this.c = SharePreferenceUtils.getPrefString(context, "staticHttpServer", "https://test-static.arnoo.com");
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9569, new Class[0], Void.TYPE).isSupported) {
            ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).a(this.c + "/media/skipRope/config.json").J(l.a).b0(l.a).G(new c()).Y(new a(), new b());
        }
    }

    /* compiled from: SkipRopeSourceManager */
    public class c implements f<String, SourceBean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9578, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String) obj);
        }

        /* compiled from: SkipRopeSourceManager */
        public class a extends TypeToken<SourceBean> {
            a() {
            }
        }

        public SourceBean a(String s) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 9577, new Class[]{String.class}, SourceBean.class);
            if (proxy.isSupported) {
                return (SourceBean) proxy.result;
            }
            com.leedarson.skiprope.util.a.b("查询音频资源:" + s);
            SourceBean sourceBean = (SourceBean) new Gson().fromJson(s, new a().getType());
            if (sourceBean == null) {
                sourceBean = new SourceBean();
            }
            int localBgmVersion = b.b(e.this.a, "bgm");
            int localVoiceVersion = b.b(e.this.a, "voice");
            String laterestBgmName = SharePreferenceUtils.getPrefString(e.this.a, "skip_rope_bgm", "");
            String laterestVoiceName = SharePreferenceUtils.getPrefString(e.this.a, "skip_voice_bgm", "");
            if (TextUtils.isEmpty(laterestBgmName) || sourceBean.bgmVersion > localBgmVersion) {
                sourceBean.needDownloadBgm = true;
            } else {
                com.leedarson.skiprope.util.a.c("未发现新版本bgm资源,laterestBgmName=" + laterestBgmName + ",localBgmVersion=" + localBgmVersion);
            }
            if (TextUtils.isEmpty(laterestVoiceName) || sourceBean.voiceVersion > localVoiceVersion) {
                sourceBean.needDownloadVoice = true;
            } else {
                com.leedarson.skiprope.util.a.c("未发现新版本voice资源,laterestVoiceName=" + laterestVoiceName + ",localVoiceVersion=" + localVoiceVersion);
            }
            return sourceBean;
        }
    }

    /* compiled from: SkipRopeSourceManager */
    public class a implements io.reactivex.functions.e<SourceBean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9574, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((SourceBean) obj);
            }
        }

        public void a(SourceBean sourceBean) {
            if (!PatchProxy.proxy(new Object[]{sourceBean}, this, changeQuickRedirect, false, 9573, new Class[]{SourceBean.class}, Void.TYPE).isSupported) {
                if (sourceBean.needDownloadVoice) {
                    if (e.this.f == null) {
                        e eVar = e.this;
                        f unused = eVar.f = new f(eVar.a);
                    }
                    if (!e.this.f.h()) {
                        e.this.f.f(sourceBean.getVoiceDownloadUrl(e.this.c), "voice", sourceBean.voiceVersion, sourceBean.voiceSourceName);
                    } else {
                        com.leedarson.skiprope.util.a.a("voice 正在下载中，不重复下载");
                    }
                }
                if (sourceBean.needDownloadBgm) {
                    if (e.this.e == null) {
                        e eVar2 = e.this;
                        f unused2 = eVar2.e = new f(eVar2.a);
                    }
                    if (!e.this.e.h()) {
                        e.this.e.f(sourceBean.getBgmDownloadUrl(e.this.c), "bgm", sourceBean.bgmVersion, sourceBean.bgmSourceName);
                    } else {
                        com.leedarson.skiprope.util.a.a("bgm 正在下载中，不重复下载");
                    }
                }
            }
        }
    }

    /* compiled from: SkipRopeSourceManager */
    public class b implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9576, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 9575, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                com.leedarson.skiprope.util.a.b("查询资源出错:" + throwable.toString());
            }
        }
    }

    public JSONArray i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9570, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray array = new JSONArray();
        String latestBgmDir = SharePreferenceUtils.getPrefString(this.a, "skip_rope_bgm", "");
        if (TextUtils.isEmpty(latestBgmDir)) {
            return array;
        }
        File dir = new File(this.d + File.separator + "bgm", latestBgmDir);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                array.put((Object) com.leedarson.base.utils.l.b(file.getName()));
            }
        }
        return array;
    }

    public File h(String simpleName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{simpleName}, this, changeQuickRedirect, false, 9571, new Class[]{String.class}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        String latestBgmDir = SharePreferenceUtils.getPrefString(this.a, "skip_rope_bgm", "");
        if (TextUtils.isEmpty(latestBgmDir)) {
            return null;
        }
        File dir = new File(this.d + File.separator + "bgm", latestBgmDir);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.getName().contains(simpleName)) {
                    return file;
                }
            }
        }
        return null;
    }

    public File j(String fileName, String lan, String type) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{fileName, lan, type}, this, changeQuickRedirect2, false, 9572, new Class[]{cls, cls, cls}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        String latestBgmDir = SharePreferenceUtils.getPrefString(this.a, "skip_voice_bgm", "");
        if (TextUtils.isEmpty(latestBgmDir)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.d);
        String str = File.separator;
        sb.append(str);
        sb.append("voice");
        sb.append(str);
        sb.append(latestBgmDir);
        sb.append(str);
        sb.append(lan);
        sb.append(str);
        sb.append(type);
        File file = new File(sb.toString(), fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }
}
