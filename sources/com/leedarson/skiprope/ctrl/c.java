package com.leedarson.skiprope.ctrl;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RequiresApi;
import com.leedarson.skiprope.bean.StartConfigBean;
import com.leedarson.skiprope.bean.VoiceEnum;
import com.leedarson.skiprope.player.e;
import com.leedarson.skiprope.player.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.util.LinkedList;

/* compiled from: PlayerController */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private f b;
    /* access modifiers changed from: private */
    public e c;
    private com.leedarson.skiprope.datamgr.e d;
    /* access modifiers changed from: private */
    public Handler e;
    private String f = "en";
    private String g = "female";
    private boolean h;
    private StartConfigBean i;
    private b j = new a();
    private final AudioManager.OnAudioFocusChangeListener k = new a();
    int l = 3;
    private Runnable m = new b();
    C0168c n;

    /* renamed from: com.leedarson.skiprope.ctrl.c$c  reason: collision with other inner class name */
    /* compiled from: PlayerController */
    public interface C0168c {
        void onFinish();
    }

    static /* synthetic */ String b(c x0, String x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9528, new Class[]{c.class, String.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : x0.g(x1);
    }

    static /* synthetic */ void c(c x0, String[] x1) {
        Class[] clsArr = {c.class, String[].class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9529, clsArr, Void.TYPE).isSupported) {
            x0.l(x1);
        }
    }

    public c(Context context, com.leedarson.skiprope.datamgr.e dataManager) {
        this.a = context;
        this.d = dataManager;
        this.b = new f(context);
        this.c = new e();
    }

    public int j(String simpleName) {
        File bgmFile;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{simpleName}, this, changeQuickRedirect, false, 9515, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        StartConfigBean startConfigBean = this.i;
        if (startConfigBean == null || startConfigBean.broadcast == null || (bgmFile = this.d.h(simpleName)) == null) {
            return -1;
        }
        this.c.g(bgmFile.getAbsolutePath());
        boolean z = this.h;
        if (z) {
            this.c.i(z);
        }
        h(true);
        return 0;
    }

    public void p(StartConfigBean configBean, C0168c listener) {
        StartConfigBean.BroadcastMode broadcastMode;
        if (!PatchProxy.proxy(new Object[]{configBean, listener}, this, changeQuickRedirect, false, 9516, new Class[]{StartConfigBean.class, C0168c.class}, Void.TYPE).isSupported) {
            this.n = listener;
            this.i = configBean;
            if (configBean == null || (broadcastMode = configBean.broadcast) == null) {
                o(false);
            } else {
                this.g = broadcastMode.voiceType == 2 ? "female" : "male";
                o(broadcastMode.mute);
            }
            this.l = configBean.countdown;
            if (this.e == null) {
                this.e = new Handler(Looper.getMainLooper());
            }
            this.e.removeCallbacks(this.m);
            this.e.post(this.m);
        }
    }

    public void m(boolean test, int num) {
        Object[] objArr = {new Byte(test ? (byte) 1 : 0), new Integer(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9517, new Class[]{Boolean.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            LinkedList<VoiceEnum> list = new LinkedList<>();
            list.add(com.leedarson.skiprope.bean.b.YouJumped);
            i(num, list);
            list.add(com.leedarson.skiprope.bean.b.times);
            String[] voices = new String[list.size()];
            for (int i2 = 0; i2 < list.size(); i2++) {
                voices[i2] = g(((com.leedarson.skiprope.bean.b) list.get(i2)).fileName);
            }
            if (Build.VERSION.SDK_INT >= 23) {
                k(test, voices);
            }
        }
    }

    public void e(boolean test, int num) {
        Object[] objArr = {new Byte(test ? (byte) 1 : 0), new Integer(num)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9518, new Class[]{Boolean.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            LinkedList<VoiceEnum> list = new LinkedList<>();
            list.add(com.leedarson.skiprope.bean.b.FinishYouJumped);
            i(num, list);
            list.add(com.leedarson.skiprope.bean.b.times);
            String[] voices = new String[list.size()];
            for (int i2 = 0; i2 < list.size(); i2++) {
                voices[i2] = g(((com.leedarson.skiprope.bean.b) list.get(i2)).fileName);
            }
            if (Build.VERSION.SDK_INT >= 23) {
                k(test, voices);
            }
        }
    }

    private String[] i(int num, LinkedList<com.leedarson.skiprope.bean.b> list) {
        Object[] objArr = {new Integer(num), list};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9519, new Class[]{Integer.TYPE, LinkedList.class}, String[].class);
        if (proxy.isSupported) {
            return (String[]) proxy.result;
        }
        this.j.a(num, list);
        String[] arr = new String[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            arr[i2] = g(list.get(i2).fileName);
        }
        return arr;
    }

    public void n() {
        e eVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9521, new Class[0], Void.TYPE).isSupported && (eVar = this.c) != null) {
            eVar.h();
        }
    }

    public void o(boolean isMute) {
        Object[] objArr = {new Byte(isMute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9522, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.h = isMute;
            this.c.i(isMute);
            this.b.h(isMute);
        }
    }

    public boolean h(boolean bMute) {
        Object[] objArr = {new Byte(bMute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9523, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        AudioManager am = (AudioManager) this.a.getSystemService("audio");
        if (bMute) {
            if (am.requestAudioFocus(this.k, 3, 2) == 1) {
                return true;
            }
            return false;
        } else if (am.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /* compiled from: PlayerController */
    public class a implements AudioManager.OnAudioFocusChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onAudioFocusChange(int focusChange) {
            Object[] objArr = {new Integer(focusChange)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9530, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.skiprope.util.a.a("onAudioFocusChange focusChange = " + focusChange);
                c.this.c.f();
            }
        }
    }

    /* compiled from: PlayerController */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @RequiresApi(api = 23)
        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9531, new Class[0], Void.TYPE).isSupported) {
                c cVar = c.this;
                int i = cVar.l;
                if (i == 0) {
                    c.c(cVar, new String[]{c.b(cVar, com.leedarson.skiprope.bean.b.StartJumping.fileName)});
                    C0168c cVar2 = c.this.n;
                    if (cVar2 != null) {
                        cVar2.onFinish();
                        return;
                    }
                    return;
                }
                com.leedarson.skiprope.bean.b item = com.leedarson.skiprope.bean.b.findItem(String.valueOf(i));
                c cVar3 = c.this;
                c.c(cVar3, new String[]{c.b(cVar3, item.fileName)});
                c.this.e.postDelayed(this, 1000);
                c.this.l--;
            }
        }
    }

    private String g(String name) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{name}, this, changeQuickRedirect, false, 9524, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        File voiceFile = this.d.j(name, this.f, this.g);
        if (voiceFile != null) {
            return voiceFile.getAbsolutePath();
        }
        return "";
    }

    private void k(boolean test, String... fileList) {
        StartConfigBean startConfigBean;
        StartConfigBean.BroadcastMode broadcastMode;
        Object[] objArr = {new Byte(test ? (byte) 1 : 0), fileList};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9525, new Class[]{Boolean.TYPE, String[].class}, Void.TYPE).isSupported) {
            if ((test || !((startConfigBean = this.i) == null || (broadcastMode = startConfigBean.broadcast) == null || broadcastMode.mode == 0)) && Build.VERSION.SDK_INT >= 23) {
                this.b.f(fileList);
            }
        }
    }

    private void l(String... fileList) {
        if (!PatchProxy.proxy(new Object[]{fileList}, this, changeQuickRedirect, false, 9526, new Class[]{String[].class}, Void.TYPE).isSupported) {
            k(false, fileList);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9527(0x2537, float:1.335E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.os.Handler r1 = r0.e
            if (r1 == 0) goto L_0x0020
            java.lang.Runnable r2 = r0.m
            r1.removeCallbacks(r2)
        L_0x0020:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.skiprope.ctrl.c.f():void");
    }
}
