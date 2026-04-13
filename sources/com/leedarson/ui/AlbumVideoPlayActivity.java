package com.leedarson.ui;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;

public class AlbumVideoPlayActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public SeekBar A4;
    /* access modifiers changed from: private */
    public TextView B4;
    private TextView C4;
    private String D4;
    /* access modifiers changed from: private */
    public MediaPlayer E4;
    private TextView F4;
    h G4 = new h(this, (a) null);
    /* access modifiers changed from: private */
    public int H4;
    /* access modifiers changed from: private */
    public int I4;
    AudioManager J4 = null;
    Timer K4;
    /* access modifiers changed from: private */
    public int L4 = 0;
    MediaPlayer.OnPreparedListener M4 = new f();
    private RelativeLayout a2;
    private LinearLayout p2;
    /* access modifiers changed from: private */
    public SurfaceView p3;
    private ImageView p4;
    private ImageView z4;

    static /* synthetic */ void c0(AlbumVideoPlayActivity x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 10837, new Class[]{AlbumVideoPlayActivity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.D0(x1);
        }
    }

    static /* synthetic */ void l0(AlbumVideoPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10835, new Class[]{AlbumVideoPlayActivity.class}, Void.TYPE).isSupported) {
            x0.B0();
        }
    }

    static /* synthetic */ void s0(AlbumVideoPlayActivity x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 10836, new Class[]{AlbumVideoPlayActivity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.E0(x1);
        }
    }

    public void T() {
    }

    public int S() {
        return R$layout.activity_album_video_play;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 10818, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setTitle("IPC Album Video Play");
            getWindow().addFlags(128);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            this.a2 = (RelativeLayout) findViewById(R$id.album_layout_title);
            TextView textView = (TextView) findViewById(R$id.tv_title);
            this.F4 = textView;
            textView.setText(PubUtils.getString(this, R$string.videos));
        }
    }

    public void y0(boolean mute) {
        boolean muteFlag = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(mute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 10819, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c("silentSwitch", "" + mute);
            try {
                if (this.J4 == null) {
                    this.J4 = (AudioManager) getApplicationContext().getSystemService("audio");
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    boolean muteFlag2 = this.J4.isStreamMute(3);
                    if (mute) {
                        if (!muteFlag2) {
                            this.J4.adjustStreamVolume(3, -100, 0);
                        }
                    } else if (muteFlag2) {
                        this.J4.adjustStreamVolume(3, 100, 0);
                    }
                } else {
                    if (this.J4.getRingerMode() == 2) {
                        muteFlag = false;
                    }
                    if (mute) {
                        if (!muteFlag) {
                            this.J4.setRingerMode(0);
                        }
                    } else if (muteFlag) {
                        this.J4.setRingerMode(2);
                    }
                    this.J4.getStreamVolume(2);
                }
            } catch (Exception e2) {
                com.leedarson.smartcamera.utils.e.c("AlbumVideoPlayActivity", "silentSwitch: " + e2);
            }
        }
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10820, new Class[0], Void.TYPE).isSupported) {
            String stringExtra = getIntent().getStringExtra("video_path");
            this.D4 = stringExtra;
            if (TextUtils.isEmpty(stringExtra)) {
                finish();
            }
            com.leedarson.smartcamera.utils.e.c("TAG", "init: " + this.D4);
            this.p3 = (SurfaceView) findViewById(R$id.srufaceview);
            this.p4 = (ImageView) findViewById(R$id.btn_back);
            this.z4 = (ImageView) findViewById(R$id.img_play);
            this.p4.setOnClickListener(this);
            this.z4.setOnClickListener(this);
            this.B4 = (TextView) findViewById(R$id.txt_currentTime);
            this.C4 = (TextView) findViewById(R$id.txt_totalTime);
            this.A4 = (SeekBar) findViewById(R$id.time_seekbar);
            this.p2 = (LinearLayout) findViewById(R$id.container_surface);
            this.A4.setThumbOffset(0);
            com.leedarson.utils.f.a(this.A4, Color.parseColor("#FC8E35"));
            this.A4.setOnSeekBarChangeListener(new a());
            this.p3.getHolder().addCallback(new b());
            y0(false);
        }
    }

    public class a implements SeekBar.OnSeekBarChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean z) {
            if (!PatchProxy.proxy(new Object[]{seekBar, new Integer(progress), new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 10838, new Class[]{SeekBar.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                AlbumVideoPlayActivity.this.B4.setText(com.leedarson.utils.e.c((seekBar.getMax() - progress) / 1000));
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @SensorsDataInstrumented
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 10839, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
                return;
            }
            SeekBar seekBar2 = seekBar;
            if (AlbumVideoPlayActivity.this.E4 != null) {
                if (AlbumVideoPlayActivity.this.E4.isPlaying()) {
                    AlbumVideoPlayActivity.this.E4.pause();
                }
                int progress = seekBar2.getProgress();
                if (progress > AlbumVideoPlayActivity.this.I4) {
                    progress = AlbumVideoPlayActivity.this.I4;
                }
                AlbumVideoPlayActivity.this.E4.seekTo(progress * 1000);
                int unused = AlbumVideoPlayActivity.this.H4 = progress;
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
        }
    }

    public class b implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void surfaceCreated(SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 10840, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                AlbumVideoPlayActivity.this.v0(holder.getSurface());
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 10841, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                if (AlbumVideoPlayActivity.this.E4 != null) {
                    AlbumVideoPlayActivity albumVideoPlayActivity = AlbumVideoPlayActivity.this;
                    int unused = albumVideoPlayActivity.H4 = albumVideoPlayActivity.E4.getCurrentPosition() / 1000;
                }
                AlbumVideoPlayActivity.this.u0();
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void u0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10821(0x2a45, float:1.5163E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.media.MediaPlayer r1 = r0.E4
            if (r1 == 0) goto L_0x0021
            r1.release()
            r1 = 0
            r0.E4 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.AlbumVideoPlayActivity.u0():void");
    }

    /* access modifiers changed from: package-private */
    public void v0(Surface surface) {
        if (!PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 10822, new Class[]{Surface.class}, Void.TYPE).isSupported) {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.E4 = mediaPlayer;
            mediaPlayer.setSurface(surface);
            A0();
        }
    }

    public void A0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10823, new Class[0], Void.TYPE).isSupported) {
            this.E4.reset();
            try {
                this.E4.setDataSource(this.D4);
                this.E4.setOnPreparedListener(this.M4);
                this.E4.setOnCompletionListener(new c());
                this.E4.setOnSeekCompleteListener(new d());
                this.E4.setOnErrorListener(new e());
                this.E4.prepareAsync();
            } catch (IOException e2) {
                com.leedarson.smartcamera.utils.e.c("AlbumVideoPlayActivity", "Failed to open video" + e2.getMessage());
            }
        }
    }

    public class c implements MediaPlayer.OnCompletionListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            if (!PatchProxy.proxy(new Object[]{mediaPlayer}, this, changeQuickRedirect, false, 10842, new Class[]{MediaPlayer.class}, Void.TYPE).isSupported) {
                AlbumVideoPlayActivity.this.t0();
                AlbumVideoPlayActivity.l0(AlbumVideoPlayActivity.this);
                int unused = AlbumVideoPlayActivity.this.L4 = 0;
                Message emsg = Message.obtain();
                emsg.what = 2;
                emsg.arg1 = AlbumVideoPlayActivity.this.A4.getMax();
                h hVar = AlbumVideoPlayActivity.this.G4;
                if (hVar != null) {
                    hVar.sendMessage(emsg);
                }
                Message msg = Message.obtain();
                msg.what = 2;
                msg.arg1 = 0;
                h hVar2 = AlbumVideoPlayActivity.this.G4;
                if (hVar2 != null) {
                    hVar2.sendMessageDelayed(msg, 100);
                }
            }
        }
    }

    public class d implements MediaPlayer.OnSeekCompleteListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onSeekComplete(MediaPlayer mediaPlayer) {
            if (!PatchProxy.proxy(new Object[]{mediaPlayer}, this, changeQuickRedirect, false, 10843, new Class[]{MediaPlayer.class}, Void.TYPE).isSupported) {
                AlbumVideoPlayActivity.this.x0();
            }
        }
    }

    public class e implements MediaPlayer.OnErrorListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void x0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10824(0x2a48, float:1.5168E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.media.MediaPlayer r1 = r0.E4
            if (r1 != 0) goto L_0x001c
            return
        L_0x001c:
            r1.start()
            r0.t0()
            r0.z0()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.AlbumVideoPlayActivity.x0():void");
    }

    public void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10825, new Class[0], Void.TYPE).isSupported) {
            MediaPlayer mediaPlayer = this.E4;
            if (mediaPlayer == null) {
                t0();
                return;
            }
            mediaPlayer.pause();
            t0();
            B0();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void C0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10826(0x2a4a, float:1.517E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.media.MediaPlayer r1 = r0.E4
            if (r1 != 0) goto L_0x001c
            return
        L_0x001c:
            boolean r1 = r1.isPlaying()
            if (r1 == 0) goto L_0x0026
            r0.w0()
            goto L_0x0029
        L_0x0026:
            r0.x0()
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.AlbumVideoPlayActivity.C0():void");
    }

    /* access modifiers changed from: package-private */
    public void t0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10827, new Class[0], Void.TYPE).isSupported) {
            MediaPlayer mediaPlayer = this.E4;
            if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
                this.z4.setSelected(true);
            } else {
                this.z4.setSelected(false);
            }
        }
    }

    public class g extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10845, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (AlbumVideoPlayActivity.this.E4 != null && AlbumVideoPlayActivity.this.E4.isPlaying()) {
                        int progress = (int) Math.ceil((((double) AlbumVideoPlayActivity.this.E4.getCurrentPosition()) * 1.0d) / 1000.0d);
                        if (progress > AlbumVideoPlayActivity.this.I4) {
                            progress = AlbumVideoPlayActivity.this.I4;
                        }
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.arg1 = progress;
                        AlbumVideoPlayActivity.this.G4.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void E0(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10828, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.L4 != 0 || position != this.A4.getMax()) {
                this.L4 = position;
                this.A4.setProgress(position);
                this.B4.setText(com.leedarson.utils.e.c(this.I4 - position));
            }
        }
    }

    private void D0(int duration) {
        Object[] objArr = {new Integer(duration)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10829, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.A4.setMax(duration);
            this.C4.setText(com.leedarson.utils.e.c(duration));
        }
    }

    private void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10830, new Class[0], Void.TYPE).isSupported) {
            Timer timer = this.K4;
            if (timer != null) {
                timer.cancel();
                this.K4.purge();
            }
            Timer timer2 = new Timer();
            this.K4 = timer2;
            timer2.schedule(new g(), 0, 500);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void B0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10831(0x2a4f, float:1.5177E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.K4
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.K4 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.AlbumVideoPlayActivity.B0():void");
    }

    public class f implements MediaPlayer.OnPreparedListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            if (!PatchProxy.proxy(new Object[]{mediaPlayer}, this, changeQuickRedirect, false, 10844, new Class[]{MediaPlayer.class}, Void.TYPE).isSupported) {
                int videoWidth = mediaPlayer.getVideoWidth();
                int videoHeight = mediaPlayer.getVideoHeight();
                int widthPixels = AlbumVideoPlayActivity.this.getResources().getDisplayMetrics().widthPixels;
                ViewGroup.LayoutParams layoutParams = AlbumVideoPlayActivity.this.p3.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = (int) (((float) widthPixels) / (((float) videoWidth) / ((float) videoHeight)));
                AlbumVideoPlayActivity.this.p3.requestLayout();
                com.leedarson.smartcamera.utils.e.c("AlbumVideoPlayActivity", widthPixels + " onPrepared: " + videoWidth + "==" + videoHeight + "==" + layoutParams.height);
                int duration = mediaPlayer.getDuration();
                if (duration > 0) {
                    AlbumVideoPlayActivity albumVideoPlayActivity = AlbumVideoPlayActivity.this;
                    if (albumVideoPlayActivity.G4 != null) {
                        int unused = albumVideoPlayActivity.I4 = duration / 1000;
                        Message msg = Message.obtain();
                        msg.what = 3;
                        msg.arg1 = AlbumVideoPlayActivity.this.I4;
                        AlbumVideoPlayActivity.this.G4.sendMessage(msg);
                    }
                }
                if (AlbumVideoPlayActivity.this.H4 > 0) {
                    mediaPlayer.seekTo(AlbumVideoPlayActivity.this.H4 * 1000);
                    int unused2 = AlbumVideoPlayActivity.this.H4 = 0;
                    return;
                }
                AlbumVideoPlayActivity.this.x0();
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10832, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            getWindow().clearFlags(128);
            h hVar = this.G4;
            if (hVar != null) {
                hVar.removeCallbacksAndMessages((Object) null);
                this.G4 = null;
            }
            this.M4 = null;
            Timer timer = this.K4;
            if (timer != null) {
                timer.cancel();
                this.K4 = null;
            }
            org.greenrobot.eventbus.c.c().r(this);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 10833, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.btn_back) {
            finish();
        } else if (viewId == R$id.img_play) {
            C0();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class h extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private h() {
        }

        /* synthetic */ h(AlbumVideoPlayActivity x0, a x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10846, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 2:
                        AlbumVideoPlayActivity.s0(AlbumVideoPlayActivity.this, msg.arg1);
                        return;
                    case 3:
                        AlbumVideoPlayActivity.c0(AlbumVideoPlayActivity.this, msg.arg1);
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 10834, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            finish();
        }
    }

    public boolean d0() {
        return false;
    }
}
