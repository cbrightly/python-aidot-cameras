package com.leedarson.serviceimpl.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.leedarson.serviceimpl.camera.R$id;
import com.leedarson.serviceimpl.camera.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import timber.log.a;

public class LdsVideoView extends FrameLayout implements MediaPlayer.OnInfoListener, View.OnClickListener, MediaPlayer.OnVideoSizeChangedListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Bitmap a1;
    final String c;
    /* access modifiers changed from: private */
    public ImageView d;
    /* access modifiers changed from: private */
    public ImageView f;
    /* access modifiers changed from: private */
    public View p0;
    /* access modifiers changed from: private */
    public int p1;
    private SurfaceView q;
    /* access modifiers changed from: private */
    public MediaPlayer x;
    /* access modifiers changed from: private */
    public String y;
    private RelativeLayout z;

    static /* synthetic */ void b(LdsVideoView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7531, new Class[]{LdsVideoView.class}, Void.TYPE).isSupported) {
            x0.getVideoInfo();
        }
    }

    static /* synthetic */ void i(LdsVideoView x0, String x1) {
        Class[] clsArr = {LdsVideoView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7532, clsArr, Void.TYPE).isSupported) {
            x0.m(x1);
        }
    }

    static /* synthetic */ int[] j(LdsVideoView x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7533, new Class[]{LdsVideoView.class, cls, cls}, int[].class);
        return proxy.isSupported ? (int[]) proxy.result : x0.l(x1, x2);
    }

    public LdsVideoView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public LdsVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.c = "LdsVideoView";
        this.p1 = 0;
        n(context);
    }

    private void n(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 7519, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_ldsvideo_view, this, true);
            r();
            q();
            p();
            o();
        }
    }

    public void setSource(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 7520, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("LdsVideoView");
            g.a("setSource,path:" + url, new Object[0]);
            this.y = url;
            this.q.getHolder().addCallback(new a());
        }
    }

    public class a implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 7534, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                timber.log.a.g("LdsVideoView").a("surfaceCreated", new Object[0]);
                LdsVideoView.this.d.setVisibility(0);
                LdsVideoView.b(LdsVideoView.this);
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 7535, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                timber.log.a.g("LdsVideoView").a("surfaceDestroyed", new Object[0]);
                if (LdsVideoView.this.x != null) {
                    int unused = LdsVideoView.this.p1 = 0;
                    if (LdsVideoView.this.x.isPlaying()) {
                        LdsVideoView.this.x.stop();
                    }
                }
            }
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7521, new Class[0], Void.TYPE).isSupported) {
            this.d.setOnClickListener(this);
            this.z.setOnClickListener(this);
        }
    }

    private void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7522, new Class[0], Void.TYPE).isSupported) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R$id.surfaceView);
            this.q = surfaceView;
            surfaceView.setZOrderOnTop(false);
            this.q.getHolder().setType(3);
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7523, new Class[0], Void.TYPE).isSupported) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.x = mediaPlayer;
            mediaPlayer.setOnInfoListener(this);
            this.x.setOnVideoSizeChangedListener(this);
        }
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7524, new Class[0], Void.TYPE).isSupported) {
            this.d = (ImageView) findViewById(R$id.playOrPause);
            this.z = (RelativeLayout) findViewById(R$id.root_rl);
            this.f = (ImageView) findViewById(R$id.iv_frame);
            this.p0 = findViewById(R$id.video_fl);
        }
    }

    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void t() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 7525(0x1d65, float:1.0545E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            android.media.MediaPlayer r2 = r1.x
            if (r2 != 0) goto L_0x001c
            return
        L_0x001c:
            r2.reset()     // Catch:{ IOException -> 0x00a7 }
            java.lang.String r2 = r1.y     // Catch:{ IOException -> 0x00a7 }
            java.lang.String r3 = "http"
            boolean r2 = r2.startsWith(r3)     // Catch:{ IOException -> 0x00a7 }
            if (r2 == 0) goto L_0x0031
            android.media.MediaPlayer r0 = r1.x     // Catch:{ IOException -> 0x00a7 }
            java.lang.String r2 = r1.y     // Catch:{ IOException -> 0x00a7 }
            r0.setDataSource(r2)     // Catch:{ IOException -> 0x00a7 }
            goto L_0x006c
        L_0x0031:
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x00a7 }
            java.lang.String r3 = r1.y     // Catch:{ IOException -> 0x00a7 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x00a7 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0068 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0068 }
            java.io.FileDescriptor r4 = r3.getFD()     // Catch:{ Exception -> 0x0068 }
            java.lang.String r5 = "LdsVideoView"
            timber.log.a$b r5 = timber.log.a.g(r5)     // Catch:{ Exception -> 0x0068 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0068 }
            r6.<init>()     // Catch:{ Exception -> 0x0068 }
            java.lang.String r7 = "setDataSource fileDescriptor:"
            r6.append(r7)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r7 = r1.y     // Catch:{ Exception -> 0x0068 }
            r6.append(r7)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0068 }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0068 }
            r5.a(r6, r0)     // Catch:{ Exception -> 0x0068 }
            android.media.MediaPlayer r0 = r1.x     // Catch:{ Exception -> 0x0068 }
            r0.setDataSource(r4)     // Catch:{ Exception -> 0x0068 }
            r3.close()     // Catch:{ Exception -> 0x0068 }
            goto L_0x006c
        L_0x0068:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x00a7 }
        L_0x006c:
            android.media.MediaPlayer r0 = r1.x     // Catch:{ IOException -> 0x00a7 }
            android.view.SurfaceView r2 = r1.q     // Catch:{ IOException -> 0x00a7 }
            android.view.SurfaceHolder r2 = r2.getHolder()     // Catch:{ IOException -> 0x00a7 }
            r0.setDisplay(r2)     // Catch:{ IOException -> 0x00a7 }
            android.media.MediaPlayer r0 = r1.x     // Catch:{ IOException -> 0x00a7 }
            r0.prepare()     // Catch:{ IOException -> 0x00a7 }
            android.media.MediaPlayer r0 = r1.x     // Catch:{ IOException -> 0x00a7 }
            com.leedarson.serviceimpl.camera.view.LdsVideoView$b r2 = new com.leedarson.serviceimpl.camera.view.LdsVideoView$b     // Catch:{ IOException -> 0x00a7 }
            r2.<init>()     // Catch:{ IOException -> 0x00a7 }
            r0.setOnPreparedListener(r2)     // Catch:{ IOException -> 0x00a7 }
            android.media.MediaPlayer r0 = r1.x     // Catch:{ IOException -> 0x00a7 }
            com.leedarson.serviceimpl.camera.view.LdsVideoView$c r2 = new com.leedarson.serviceimpl.camera.view.LdsVideoView$c     // Catch:{ IOException -> 0x00a7 }
            r2.<init>()     // Catch:{ IOException -> 0x00a7 }
            r0.setOnErrorListener(r2)     // Catch:{ IOException -> 0x00a7 }
            android.media.MediaPlayer r0 = r1.x     // Catch:{ IOException -> 0x00a7 }
            com.leedarson.serviceimpl.camera.view.LdsVideoView$d r2 = new com.leedarson.serviceimpl.camera.view.LdsVideoView$d     // Catch:{ IOException -> 0x00a7 }
            r2.<init>()     // Catch:{ IOException -> 0x00a7 }
            r0.setOnCompletionListener(r2)     // Catch:{ IOException -> 0x00a7 }
            android.widget.ImageView r0 = r1.d     // Catch:{ IOException -> 0x00a7 }
            r2 = 8
            r0.setVisibility(r2)     // Catch:{ IOException -> 0x00a7 }
            android.widget.ImageView r0 = r1.f     // Catch:{ IOException -> 0x00a7 }
            r0.setVisibility(r2)     // Catch:{ IOException -> 0x00a7 }
            goto L_0x00ab
        L_0x00a7:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.camera.view.LdsVideoView.t():void");
    }

    public class b implements MediaPlayer.OnPreparedListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @RequiresApi(api = 26)
        public void onPrepared(MediaPlayer mediaPlayer) {
            if (!PatchProxy.proxy(new Object[]{mediaPlayer}, this, changeQuickRedirect, false, 7536, new Class[]{MediaPlayer.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("LdsVideoView");
                g.a("play:" + LdsVideoView.this.p1, new Object[0]);
                LdsVideoView.this.x.seekTo((long) LdsVideoView.this.p1, 3);
                LdsVideoView.this.x.start();
            }
        }
    }

    public class c implements MediaPlayer.OnErrorListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
            Object[] objArr = {mediaPlayer, new Integer(what), new Integer(extra)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 7537, new Class[]{MediaPlayer.class, cls, cls}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            a.b g = timber.log.a.g("LdsVideoView");
            g.c("media player onError what:" + what + ",extra:" + extra, new Object[0]);
            return false;
        }
    }

    public class d implements MediaPlayer.OnCompletionListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            if (!PatchProxy.proxy(new Object[]{mediaPlayer}, this, changeQuickRedirect, false, 7538, new Class[]{MediaPlayer.class}, Void.TYPE).isSupported) {
                LdsVideoView.this.d.setVisibility(0);
                if (LdsVideoView.this.a1 != null) {
                    LdsVideoView.this.f.setVisibility(0);
                    LdsVideoView.this.f.setImageBitmap(LdsVideoView.this.a1);
                }
            }
        }
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7526, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int id = v.getId();
        if (id == R$id.playOrPause) {
            t();
        } else if (id == R$id.root_rl) {
            s();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void s() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 7527(0x1d67, float:1.0548E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            android.media.MediaPlayer r2 = r1.x
            if (r2 != 0) goto L_0x001c
            return
        L_0x001c:
            boolean r2 = r2.isPlaying()
            if (r2 == 0) goto L_0x0052
            android.media.MediaPlayer r2 = r1.x
            int r2 = r2.getCurrentPosition()
            r1.p1 = r2
            java.lang.String r2 = "LdsVideoView"
            timber.log.a$b r2 = timber.log.a.g(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "pause:"
            r3.append(r4)
            int r4 = r1.p1
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r0]
            r2.a(r3, r4)
            android.media.MediaPlayer r2 = r1.x
            r2.pause()
            android.widget.ImageView r2 = r1.d
            r2.setVisibility(r0)
        L_0x0052:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.camera.view.LdsVideoView.s():void");
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7539, new Class[0], Void.TYPE).isSupported) {
                LdsVideoView ldsVideoView = LdsVideoView.this;
                LdsVideoView.i(ldsVideoView, ldsVideoView.y);
            }
        }
    }

    private void getVideoInfo() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7528, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("LdsVideoView").a("getVideoInfo", new Object[0]);
            new Thread(new e()).start();
        }
    }

    private void m(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 7529, new Class[]{String.class}, Void.TYPE).isSupported) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(url);
                this.a1 = retriever.getFrameAtTime();
                a.b g = timber.log.a.g("LdsVideoView");
                g.a("bitmap w:" + this.a1.getWidth() + ",h:" + this.a1.getHeight(), new Object[0]);
                if (this.a1 != null) {
                    this.f.post(new f());
                }
                try {
                    retriever.release();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
                retriever.release();
            } catch (Throwable th) {
                try {
                    retriever.release();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7540, new Class[0], Void.TYPE).isSupported) {
                LdsVideoView ldsVideoView = LdsVideoView.this;
                int[] ints = LdsVideoView.j(ldsVideoView, ldsVideoView.a1.getWidth(), LdsVideoView.this.a1.getHeight());
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) LdsVideoView.this.p0.getLayoutParams();
                params.width = ints[0];
                params.height = ints[1];
                LdsVideoView.this.p0.setLayoutParams(params);
                LdsVideoView.this.f.setVisibility(0);
                LdsVideoView.this.f.setImageBitmap(LdsVideoView.this.a1);
            }
        }
    }

    private int[] l(int w, int h) {
        Object[] objArr = {new Integer(w), new Integer(h)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7530, new Class[]{cls, cls}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int targetWidth = w;
        int targetHeight = h;
        int sw = com.leedarson.base.utils.d.e(getContext());
        int sh = com.leedarson.base.utils.d.d(getContext());
        if (w > h) {
            if (w > sw) {
                targetWidth = sw;
                targetHeight = (int) (((float) h) * ((((float) sw) * 1.0f) / ((float) w)));
            }
        } else if (h > sh) {
            targetHeight = sh;
            targetWidth = (int) (((float) w) * ((((float) sh) * 1.0f) / ((float) h)));
        }
        return new int[]{targetWidth, targetHeight};
    }
}
