package com.leedarson.skiprope.player;

import android.content.Context;
import android.media.AudioTrack;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.leedarson.base.http.observer.l;
import com.leedarson.skiprope.player.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/* compiled from: PCMPlayer */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    /* access modifiers changed from: private */
    public c b;
    private Queue<String> c;
    private ExecutorService d;
    /* access modifiers changed from: private */
    public boolean e;
    private c.a f = new b();

    static /* synthetic */ void b(f x0, float x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Float(x1)}, (Object) null, changeQuickRedirect, true, 9624, new Class[]{f.class, Float.TYPE}, Void.TYPE).isSupported) {
            x0.i(x1);
        }
    }

    static /* synthetic */ void d(f x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 9625, new Class[]{f.class}, Void.TYPE).isSupported) {
            x0.g();
        }
    }

    public f(Context context) {
        this.a = context;
        this.c = new LinkedList();
    }

    @RequiresApi(api = 23)
    public int f(String... fileList) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{fileList}, this, changeQuickRedirect, false, 9618, new Class[]{String[].class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        ExecutorService executorService = this.d;
        if (executorService == null || executorService.isShutdown()) {
            this.d = l.i(1, "pcm-player-play", 2);
        }
        if (this.b == null) {
            this.b = new c(this.a);
        }
        this.b.g(this.f);
        this.c.clear();
        if (!e(fileList)) {
            this.c.clear();
            return 401;
        }
        g();
        return 200;
    }

    private void g() {
        ExecutorService executorService;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9619, new Class[0], Void.TYPE).isSupported) {
            String item = this.c.poll();
            if (item != null && (executorService = this.d) != null) {
                executorService.execute(new a(item));
            }
        }
    }

    /* compiled from: PCMPlayer */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        a(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9626, new Class[0], Void.TYPE).isSupported) {
                f fVar = f.this;
                f.b(fVar, fVar.e ? 0.0f : 1.0f);
                f.this.b.e(this.c);
            }
        }
    }

    /* compiled from: PCMPlayer */
    public class b implements c.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 9627, new Class[]{String.class}, Void.TYPE).isSupported) {
                f.d(f.this);
            }
        }
    }

    private boolean e(String... files) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{files}, this, changeQuickRedirect, false, 9620, new Class[]{String[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        for (String file : files) {
            if (TextUtils.isEmpty(file) || !new File(file).exists()) {
                return false;
            }
            this.c.add(file);
        }
        return true;
    }

    public void h(boolean mute) {
        if (!PatchProxy.proxy(new Object[]{new Byte(mute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9622, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.e = mute;
            i(mute ? 0.0f : 1.0f);
        }
    }

    private void i(float vol) {
        Object[] objArr = {new Float(vol)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9623, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            if (this.b != null) {
                float maxVolume = AudioTrack.getMaxVolume();
                if (vol != 0.0f) {
                    this.b.f(1.0f);
                } else {
                    this.b.f(0.0f);
                }
            }
        }
    }
}
