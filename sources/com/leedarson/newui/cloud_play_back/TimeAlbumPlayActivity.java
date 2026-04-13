package com.leedarson.newui.cloud_play_back;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.github.druk.dnssd.DNSSD;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.newui.cloud_play_back.repos.b0;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.cloud_play_back.repos.beans.TimeAlbumParamsBean;
import com.leedarson.newui.cloud_play_back.repos.c0;
import com.leedarson.newui.cloud_play_back.view.DownloadProgressView;
import com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView;
import com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean;
import com.leedarson.newui.cloud_play_back.view.p0;
import com.leedarson.newui.repos.beans.EventUrlResponseItemBean;
import com.leedarson.newui.repos.beans.VideoUrlItemBean;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.utils.IntentUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;

public class TimeAlbumPlayActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static String a2 = "TimeAlbumPlayActivity.KEY_PARAMS_DATA";
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView A4;
    private View B4;
    private View C4;
    private View D4;
    private View E4;
    private View F4;
    /* access modifiers changed from: private */
    public IJKPlayBackPlayerView G4;
    /* access modifiers changed from: private */
    public c0 H4;
    /* access modifiers changed from: private */
    public PlayBackSourceBean I4;
    /* access modifiers changed from: private */
    public j J4 = new j(this, (a) null);
    private com.leedarson.base.views.g K4;
    /* access modifiers changed from: private */
    public DownloadProgressView L4;
    /* access modifiers changed from: private */
    public Dialog M4 = null;
    private LDSTextView N4;
    private LDSTextView O4;
    private LDSTextView P4;
    private LDSTextView Q4;
    /* access modifiers changed from: private */
    public ArrayList<io.reactivex.disposables.b> R4 = new ArrayList<>();
    /* access modifiers changed from: private */
    public Gson S4;
    private LinearLayout T4;
    private View U4;
    private ImageView V4;
    private ImageView W4;
    private ImageView X4;
    private LDSTextView Y4;
    private LDSTextView Z4;
    private LDSTextView a5;
    private final int b5 = DNSSD.DNSSD_DEFAULT_TIMEOUT;
    /* access modifiers changed from: private */
    public Runnable c5 = new b();
    /* access modifiers changed from: private */
    public Runnable d5 = new c();
    private int e5 = 100;
    private Runnable f5 = new d();
    TimeAlbumParamsBean p2;
    private ImageView p3;
    private LDSTextView p4;
    private LDSTextView z4;

    static /* synthetic */ void c0(TimeAlbumPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3692, new Class[]{TimeAlbumPlayActivity.class}, Void.TYPE).isSupported) {
            x0.w0();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 3662, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
        }
    }

    public int S() {
        return R$layout.activity_time_album_play;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3663, new Class[0], Void.TYPE).isSupported) {
            ImageView imageView = (ImageView) findViewById(R$id.iv_back);
            this.p3 = imageView;
            imageView.setOnClickListener(this);
            this.B4 = findViewById(R$id.title_layout);
            this.p4 = (LDSTextView) findViewById(R$id.tv_title);
            this.z4 = (LDSTextView) findViewById(R$id.tv_type);
            this.A4 = (LDSTextView) findViewById(R$id.tv_video_size);
            IJKPlayBackPlayerView iJKPlayBackPlayerView = (IJKPlayBackPlayerView) findViewById(R$id.player);
            this.G4 = iJKPlayBackPlayerView;
            iJKPlayBackPlayerView.l5 = 1;
            iJKPlayBackPlayerView.f0();
            this.T4 = (LinearLayout) findViewById(R$id.layout_player);
            this.U4 = findViewById(R$id.layout_info);
            View findViewById = findViewById(R$id.layout_share);
            this.D4 = findViewById;
            findViewById.setOnClickListener(this);
            View findViewById2 = findViewById(R$id.layout_download);
            this.E4 = findViewById2;
            findViewById2.setOnClickListener(this);
            View findViewById3 = findViewById(R$id.layout_del);
            this.F4 = findViewById3;
            findViewById3.setOnClickListener(this);
            this.C4 = findViewById(R$id.layout_menu);
            this.V4 = (ImageView) findViewById(R$id.img_share);
            this.Y4 = (LDSTextView) findViewById(R$id.tv_share);
            this.W4 = (ImageView) findViewById(R$id.img_download);
            this.Z4 = (LDSTextView) findViewById(R$id.tv_download);
            this.X4 = (ImageView) findViewById(R$id.img_del);
            this.a5 = (LDSTextView) findViewById(R$id.tv_del);
            this.K4 = new com.leedarson.base.views.g(this);
            DownloadProgressView downloadProgressView = (DownloadProgressView) findViewById(R$id.view_download);
            this.L4 = downloadProgressView;
            downloadProgressView.setDownloadProgressListener(new s0(this));
            Dialog dialog = new Dialog(this, R$style.Theme_dialog);
            this.M4 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.M4.setCanceledOnTouchOutside(false);
            this.N4 = (LDSTextView) this.M4.findViewById(R$id.tip_title_tv);
            this.O4 = (LDSTextView) this.M4.findViewById(R$id.tip_content_tv);
            this.P4 = (LDSTextView) this.M4.findViewById(R$id.left_btn_tv);
            this.Q4 = (LDSTextView) this.M4.findViewById(R$id.right_btn_tv);
            L0(false);
            this.G4.set_mScreenChangeHandler(new a());
            A0();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: C0 */
    public /* synthetic */ void D0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3691, new Class[0], Void.TYPE).isSupported) {
            t0(true);
        }
    }

    public class a implements p0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean f0() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3693, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            try {
                if (TimeAlbumPlayActivity.this.getResources().getConfiguration().orientation != 2 && TimeAlbumPlayActivity.this.getResources().getConfiguration().orientation == 1) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void x0() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3694, new Class[0], Void.TYPE).isSupported) {
                TimeAlbumPlayActivity.this.u0();
            }
        }

        public void M() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3695, new Class[0], Void.TYPE).isSupported) {
                TimeAlbumPlayActivity.this.v0();
            }
        }
    }

    private void A0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3664, new Class[0], Void.TYPE).isSupported) {
            if (this.p2 == null) {
                finish();
            }
            try {
                String title = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date(this.p2.getDate()));
                this.p4.setText(title);
                this.G4.setUpPlayerTitle(title);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (!this.p2.getIsOwner()) {
                this.C4.setVisibility(8);
            }
            if (this.p2.getVideoType() == 1) {
                this.z4.setText(PubUtils.getString(this, R$string.lds_event_sunrise));
            } else if (this.p2.getVideoType() == 2) {
                this.z4.setText(PubUtils.getString(this, R$string.lds_event_sunset));
            } else {
                this.z4.setText("");
            }
            if (this.p2.getVideoSize() > 0) {
                this.A4.setText(String.format(Locale.US, PubUtils.getString(this, R$string.lds_event_video_size), new Object[]{Float.valueOf((((float) this.p2.getVideoSize()) / 1024.0f) / 1024.0f)}));
            }
            this.G4.M1(IpcServiceImpl.o(this.p2.getDeviceId()).getAspectRatio(), 1.7777778f);
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.G4;
            iJKPlayBackPlayerView.l5 = 1;
            iJKPlayBackPlayerView.f0();
            this.G4.setVideoCover(this.p2.getPoster());
            if (!com.alibaba.android.arouter.utils.e.b(this.p2.getVideoUrl())) {
                this.H4 = new c0();
                PlayBackSourceBean playBackSourceBean = new PlayBackSourceBean();
                this.I4 = playBackSourceBean;
                playBackSourceBean.url = this.p2.getVideoUrl();
                VideoUrlItemBean bean = new VideoUrlItemBean();
                if (this.p2.getEventType() == 1) {
                    bean.type = 1;
                } else if (this.p2.getEventType() == 2) {
                    bean.type = 2;
                }
                this.I4.eventPlayUrls = new EventUrlResponseItemBean();
                bean.url = this.p2.getVideoUrl();
                this.I4.eventPlayUrls.videoUrlList.add(bean);
                this.I4.coverUrl = this.p2.getPoster();
                this.G4.B4 = this.p2.getEventUuid();
                this.G4.A4 = this.p2.getDeviceId();
                this.G4.setDecodeThreadMaxCount(4);
                this.G4.F1(this.I4);
                this.G4.setExtroBzDuration(this.p2.getVideoDuration());
                M(this.G4.W4.I(new p0(this), q0.c));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: E0 */
    public /* synthetic */ void F0(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3690, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                L0(true);
            }
        }
    }

    static /* synthetic */ void G0(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 3689, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            Log.e("TimeAlbumPlayActivity", "起播失败" + throwable.toString());
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3665, new Class[0], Void.TYPE).isSupported) {
            if (getIntent().hasExtra(a2)) {
                this.p2 = (TimeAlbumParamsBean) new Gson().fromJson(getIntent().getStringExtra(a2), TimeAlbumParamsBean.class);
                return;
            }
            finish();
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3666, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            com.leedarson.log.f.a("onPause");
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.G4;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.w();
                this.G4.f();
                this.G4.O1();
            }
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3667, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            com.leedarson.log.f.a("onStop");
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.G4;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.w();
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3668, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.a("onDestroy");
            super.onDestroy();
            j jVar = this.J4;
            if (jVar != null) {
                jVar.removeCallbacksAndMessages((Object) null);
            }
            c0 c0Var = this.H4;
            if (c0Var != null) {
                c0Var.P();
            }
            com.leedarson.base.views.g gVar = this.K4;
            if (gVar != null) {
                gVar.dismiss();
            }
            Dialog dialog = this.M4;
            if (dialog != null) {
                dialog.dismiss();
            }
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.G4;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.H1();
            }
            ArrayList<io.reactivex.disposables.b> arrayList = this.R4;
            if (arrayList != null) {
                Iterator<io.reactivex.disposables.b> it = arrayList.iterator();
                while (it.hasNext()) {
                    io.reactivex.disposables.b disposable = it.next();
                    if (disposable != null && !disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3669(0xe55, float:5.141E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            com.leedarson.base.views.g r2 = r1.K4
            if (r2 == 0) goto L_0x0028
            r2.setCancelable(r0)
            com.leedarson.base.views.g r2 = r1.K4
            r2.setCanceledOnTouchOutside(r0)
            com.leedarson.base.views.g r0 = r1.K4
            r0.g()
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity.b():void");
    }

    public void a() {
        com.leedarson.base.views.g gVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3670, new Class[0], Void.TYPE).isSupported && (gVar = this.K4) != null) {
            gVar.e();
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3696, new Class[0], Void.TYPE).isSupported) {
                TimeAlbumPlayActivity.this.t0(false);
                Message msg = Message.obtain();
                msg.what = 2;
                TimeAlbumPlayActivity.this.J4.sendMessage(msg);
                TimeAlbumPlayActivity.this.a();
            }
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3697, new Class[0], Void.TYPE).isSupported) {
                TimeAlbumPlayActivity.this.t0(false);
                Message msg = Message.obtain();
                msg.what = 5;
                TimeAlbumPlayActivity.this.J4.sendMessage(msg);
                TimeAlbumPlayActivity.this.a();
            }
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3698, new Class[0], Void.TYPE).isSupported) {
                TimeAlbumPlayActivity.this.J4.removeCallbacks(TimeAlbumPlayActivity.this.c5);
                TimeAlbumPlayActivity.this.J4.postDelayed(TimeAlbumPlayActivity.this.c5, 60000);
                TimeAlbumPlayActivity.this.H4.w(TimeAlbumPlayActivity.this.G4.A4, TimeAlbumPlayActivity.this.G4.B4, TimeAlbumPlayActivity.this.I4.url, new a());
            }
        }

        public class a implements b0 {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onFinish(String path) {
                if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 3699, new Class[]{String.class}, Void.TYPE).isSupported) {
                    Log.d("TimeAlbumPlayActivity", "onFinish: " + path);
                    TimeAlbumPlayActivity.this.J4.removeCallbacks(TimeAlbumPlayActivity.this.c5);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = path;
                    TimeAlbumPlayActivity.this.J4.sendMessage(msg);
                    TimeAlbumPlayActivity.this.a();
                }
            }

            public void onProgress(int progress, long progressTime) {
            }

            public void onCancel() {
            }

            public void onError(String str) {
                if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 3700, new Class[]{String.class}, Void.TYPE).isSupported) {
                    TimeAlbumPlayActivity.this.J4.removeCallbacks(TimeAlbumPlayActivity.this.c5);
                    Message msg = Message.obtain();
                    msg.what = 2;
                    TimeAlbumPlayActivity.this.J4.sendMessage(msg);
                    TimeAlbumPlayActivity.this.a();
                }
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3671, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.iv_back) {
            y0();
        } else if (viewId == R$id.layout_share) {
            if (w.w(this) == 0) {
                showToast(R$string.player_error_41);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            b();
            if (this.L4.getVisibility() == 0) {
                t0(false);
                this.e5 = 3000;
            } else {
                this.e5 = 100;
            }
            this.J4.removeCallbacks(this.f5);
            this.J4.postDelayed(this.f5, (long) this.e5);
        } else if (viewId == R$id.layout_download) {
            if (w.w(this) == 0) {
                showToast(R$string.player_error_41);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            Q0();
        } else if (viewId == R$id.layout_del) {
            N0("delete");
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void Q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3672, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                this.L4.setVisibility(0);
                O0();
            } else if (EasyPermissions.a(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                this.L4.setVisibility(0);
                O0();
            } else {
                z0();
            }
        }
    }

    public class e implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3701, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                TimeAlbumPlayActivity.this.P0(fragment);
            }
        }
    }

    public void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3673, new Class[0], Void.TYPE).isSupported) {
            LDSPermissitonGuideFragment d2 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new e());
        }
    }

    public void P0(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3674, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new o0(this));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J0 */
    public /* synthetic */ void K0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3688, new Class[0], Void.TYPE).isSupported) {
            Q0();
        }
    }

    public void O0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3675, new Class[0], Void.TYPE).isSupported) {
            this.L4.setProgress(0);
            this.J4.removeCallbacks(this.d5);
            this.J4.postDelayed(this.d5, 60000);
            this.H4.v(this.G4.A4, this.p2.getDate(), this.I4.url, true, new f());
        }
    }

    public class f implements b0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onFinish(String path) {
            if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 3702, new Class[]{String.class}, Void.TYPE).isSupported) {
                TimeAlbumPlayActivity.this.J4.removeCallbacks(TimeAlbumPlayActivity.this.d5);
                Message msg = Message.obtain();
                msg.what = 3;
                msg.obj = path;
                TimeAlbumPlayActivity.this.J4.sendMessage(msg);
            }
        }

        public void onProgress(int progress, long j) {
            Object[] objArr = {new Integer(progress), new Long(j)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3703, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                Message msg = Message.obtain();
                msg.what = 4;
                msg.arg1 = progress;
                TimeAlbumPlayActivity.this.J4.sendMessage(msg);
            }
        }

        public void onCancel() {
        }

        public void onError(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 3704, new Class[]{String.class}, Void.TYPE).isSupported) {
                Message msg = Message.obtain();
                msg.what = 5;
                TimeAlbumPlayActivity.this.J4.sendMessage(msg);
            }
        }
    }

    public void t0(boolean needToast) {
        Object[] objArr = {new Byte(needToast ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3676, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.H4.o();
            this.J4.removeCallbacks(this.d5);
            this.J4.removeCallbacks(this.c5);
            this.L4.setVisibility(8);
            if (needToast) {
                showToast(R$string.lds_download_cancel);
            }
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3677, new Class[0], Void.TYPE).isSupported) {
            y0();
        }
    }

    private void y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3678, new Class[0], Void.TYPE).isSupported) {
            if (B0()) {
                v0();
            } else if (this.L4.getVisibility() == 0) {
                N0("leave");
            } else {
                finish();
                overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
            }
        }
    }

    public boolean B0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3679, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3680, new Class[0], Void.TYPE).isSupported) {
            setRequestedOrientation(1);
            getWindow().clearFlags(1024);
            this.B4.setVisibility(0);
            if (this.p2.getIsOwner()) {
                this.C4.setVisibility(0);
            }
            this.U4.setVisibility(0);
            this.T4.getLayoutParams().height = -2;
            this.G4.requestLayout();
            this.G4.getLayoutParams().height = -2;
            this.G4.requestLayout();
        }
    }

    public void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3681, new Class[0], Void.TYPE).isSupported) {
            setRequestedOrientation(0);
            getWindow().setFlags(1024, 1024);
            this.B4.setVisibility(8);
            this.L4.setVisibility(8);
            this.C4.setVisibility(8);
            this.U4.setVisibility(8);
            this.T4.getLayoutParams().height = -1;
            this.G4.requestLayout();
            this.G4.getLayoutParams().height = -1;
            this.G4.requestLayout();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: H0 */
    public /* synthetic */ void I0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3687, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.M4.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0041, code lost:
        if (r10.equals("stop") != false) goto L_0x004f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void N0(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3682(0xe62, float:5.16E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            com.leedarson.base.views.common.LDSTextView r2 = r1.P4
            com.leedarson.newui.cloud_play_back.r0 r3 = new com.leedarson.newui.cloud_play_back.r0
            r3.<init>(r1)
            r2.setOnClickListener(r3)
            r2 = -1
            int r3 = r10.hashCode()
            switch(r3) {
                case -1335458389: goto L_0x0044;
                case 3540994: goto L_0x003b;
                case 102846135: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x004e
        L_0x0031:
            java.lang.String r0 = "leave"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = r8
            goto L_0x004f
        L_0x003b:
            java.lang.String r3 = "stop"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0030
            goto L_0x004f
        L_0x0044:
            java.lang.String r0 = "delete"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = 2
            goto L_0x004f
        L_0x004e:
            r0 = r2
        L_0x004f:
            r2 = 8
            switch(r0) {
                case 0: goto L_0x00b8;
                case 1: goto L_0x0087;
                case 2: goto L_0x0056;
                default: goto L_0x0054;
            }
        L_0x0054:
            goto L_0x00e9
        L_0x0056:
            com.leedarson.base.views.common.LDSTextView r0 = r1.N4
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.O4
            int r2 = com.leedarson.R$string.delete_event_tip
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.P4
            int r2 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.Q4
            int r2 = com.leedarson.R$string.confirm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.Q4
            com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity$i r2 = new com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity$i
            r2.<init>()
            r0.setOnClickListener(r2)
            goto L_0x00e9
        L_0x0087:
            com.leedarson.base.views.common.LDSTextView r0 = r1.N4
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.O4
            int r2 = com.leedarson.R$string.lds_timealbum_stop_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.P4
            int r2 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.Q4
            int r2 = com.leedarson.R$string.confirm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.Q4
            com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity$h r2 = new com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity$h
            r2.<init>()
            r0.setOnClickListener(r2)
            goto L_0x00e9
        L_0x00b8:
            com.leedarson.base.views.common.LDSTextView r0 = r1.N4
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.O4
            int r2 = com.leedarson.R$string.lds_timealbum_leave_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.P4
            int r2 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.Q4
            int r2 = com.leedarson.R$string.confirm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r1, r2)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.Q4
            com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity$g r2 = new com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity$g
            r2.<init>()
            r0.setOnClickListener(r2)
        L_0x00e9:
            android.app.Dialog r0 = r1.M4
            r0.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity.N0(java.lang.String):void");
    }

    public class g implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3705, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            TimeAlbumPlayActivity.this.M4.dismiss();
            TimeAlbumPlayActivity.this.t0(false);
            TimeAlbumPlayActivity.this.showToast(R$string.lds_download_stopped);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class h implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3706, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            TimeAlbumPlayActivity.this.M4.dismiss();
            TimeAlbumPlayActivity.this.t0(false);
            TimeAlbumPlayActivity.this.showToast(R$string.lds_download_stopped);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class i implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3707, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            TimeAlbumPlayActivity.this.t0(false);
            TimeAlbumPlayActivity.this.M4.dismiss();
            if (TimeAlbumPlayActivity.this.H4 != null) {
                TimeAlbumPlayActivity timeAlbumPlayActivity = TimeAlbumPlayActivity.this;
                if (timeAlbumPlayActivity.p2 != null) {
                    timeAlbumPlayActivity.b();
                    TimeAlbumPlayActivity.this.H4.r(TimeAlbumPlayActivity.this.p2.getAlbumId(), TimeAlbumPlayActivity.this.p2.getVideoId(), new a());
                }
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        public class a extends com.leedarson.base.http.observer.i<String> {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3711, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    onSuccess((String) obj);
                }
            }

            public void onStart(io.reactivex.disposables.b d) {
                if (!PatchProxy.proxy(new Object[]{d}, this, changeQuickRedirect, false, 3708, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                    TimeAlbumPlayActivity.this.R4.add(d);
                }
            }

            public void onError(ApiException apiException) {
                if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 3709, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                    TimeAlbumPlayActivity.this.a();
                    TimeAlbumPlayActivity timeAlbumPlayActivity = TimeAlbumPlayActivity.this;
                    timeAlbumPlayActivity.a0(PubUtils.getString(timeAlbumPlayActivity, R$string.delete_failed));
                }
            }

            public void onSuccess(String response) {
                if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 3710, new Class[]{String.class}, Void.TYPE).isSupported) {
                    TimeAlbumPlayActivity.this.a();
                    if (TimeAlbumPlayActivity.this.S4 == null) {
                        Gson unused = TimeAlbumPlayActivity.this.S4 = new Gson();
                    }
                    try {
                        if (((LDSBaseBean) TimeAlbumPlayActivity.this.S4.fromJson(response, new C0105a().getType())).checkDataValid()) {
                            TimeAlbumPlayActivity.c0(TimeAlbumPlayActivity.this);
                            return;
                        }
                        TimeAlbumPlayActivity timeAlbumPlayActivity = TimeAlbumPlayActivity.this;
                        timeAlbumPlayActivity.a0(PubUtils.getString(timeAlbumPlayActivity, R$string.delete_failed));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            /* renamed from: com.leedarson.newui.cloud_play_back.TimeAlbumPlayActivity$i$a$a  reason: collision with other inner class name */
            public class C0105a extends TypeToken<LDSBaseBean> {
                C0105a() {
                }
            }
        }
    }

    private void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3683, new Class[0], Void.TYPE).isSupported) {
            a0(PubUtils.getString(this, R$string.delete_success));
            try {
                JSONObject extendsObj = new JSONObject();
                extendsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "removeVideo");
                extendsObj.put("videoId", (Object) this.p2.getVideoId());
                x0(this.p2.getDeviceId(), extendsObj);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            y0();
        }
    }

    private void x0(String deviceId, JSONObject extendsObj) {
        Class[] clsArr = {String.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{deviceId, extendsObj}, this, changeQuickRedirect, false, 3684, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) deviceId);
                jsonObject.put("extends", (Object) extendsObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Device", H5ActionName.ACTION_DEVICE_CONTROL, jsonObject.toString()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class j extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private j() {
        }

        /* synthetic */ j(TimeAlbumPlayActivity x0, a x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3712, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        TimeAlbumPlayActivity.this.a();
                        String path = (String) msg.obj;
                        if (!com.alibaba.android.arouter.utils.e.b(path)) {
                            IntentUtils.shareVideo(TimeAlbumPlayActivity.this, new File(path), "");
                            return;
                        }
                        return;
                    case 2:
                        TimeAlbumPlayActivity.this.a();
                        TimeAlbumPlayActivity.this.showToast(R$string.lds_share_fail);
                        return;
                    case 3:
                        String path2 = (String) msg.obj;
                        if (!com.alibaba.android.arouter.utils.e.b(path2)) {
                            TimeAlbumPlayActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(path2))));
                            TimeAlbumPlayActivity.this.L4.setProgress(100);
                            TimeAlbumPlayActivity.this.L4.setVisibility(8);
                            TimeAlbumPlayActivity.this.showToast(R$string.lds_download_suc);
                            if (TimeAlbumPlayActivity.this.M4 != null) {
                                TimeAlbumPlayActivity.this.M4.dismiss();
                                return;
                            }
                            return;
                        }
                        return;
                    case 4:
                        int progress = msg.arg1;
                        if (progress > 0 && progress <= 100) {
                            TimeAlbumPlayActivity.this.L4.setProgress(progress);
                            return;
                        }
                        return;
                    case 5:
                        TimeAlbumPlayActivity.this.L4.setVisibility(8);
                        TimeAlbumPlayActivity.this.showToast(R$string.lds_download_fail);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 3685, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            try {
                if (newConfig.orientation == 2) {
                    this.G4.L1(true);
                } else {
                    this.G4.L1(false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void L0(boolean enable) {
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3686, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.D4.setEnabled(enable);
            this.E4.setEnabled(enable);
            this.F4.setEnabled(enable);
            this.V4.setEnabled(enable);
            this.W4.setEnabled(enable);
            this.X4.setEnabled(enable);
            if (enable) {
                LDSTextView lDSTextView = this.Y4;
                Resources resources = getResources();
                int i2 = R$color.grid_default_color;
                lDSTextView.setTextColor(resources.getColor(i2));
                this.Z4.setTextColor(getResources().getColor(i2));
                this.a5.setTextColor(getResources().getColor(i2));
                return;
            }
            LDSTextView lDSTextView2 = this.Y4;
            Resources resources2 = getResources();
            int i3 = R$color.grid_disable_color;
            lDSTextView2.setTextColor(resources2.getColor(i3));
            this.Z4.setTextColor(getResources().getColor(i3));
            this.a5.setTextColor(getResources().getColor(i3));
        }
    }
}
