package com.leedarson.serviceimpl.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.maps.android.SphericalUtil;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.map.CenterSeekBar;
import com.leedarson.serviceimpl.map.b;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.event.UploadFileResultEvent;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.luck.picture.lib.config.PictureMimeType;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.io.FileOutputStream;
import org.greenrobot.eventbus.ThreadMode;

public class PathViewMapStep3Activity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String A4;
    private com.leedarson.base.views.g B4;
    /* access modifiers changed from: private */
    public View C4;
    /* access modifiers changed from: private */
    public RadarArcView D4;
    /* access modifiers changed from: private */
    public float E4;
    /* access modifiers changed from: private */
    public CenterSeekBar.a F4 = new d();
    SupportMapFragment G4;
    private b H4;
    /* access modifiers changed from: private */
    public Handler I4 = new c(Looper.getMainLooper());
    private TextView a1;
    private ImageView a2;
    private RelativeLayout p0;
    private TextView p1;
    /* access modifiers changed from: private */
    public GoogleMap p2;
    /* access modifiers changed from: private */
    public CenterSeekBar p3;
    private int p4 = 4;
    /* access modifiers changed from: private */
    public CameraPosition z4;

    static /* synthetic */ void b0(PathViewMapStep3Activity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8262, new Class[]{PathViewMapStep3Activity.class}, Void.TYPE).isSupported) {
            x0.s0();
        }
    }

    static /* synthetic */ void e0(PathViewMapStep3Activity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8259, new Class[]{PathViewMapStep3Activity.class}, Void.TYPE).isSupported) {
            x0.t0();
        }
    }

    static /* synthetic */ void f0(PathViewMapStep3Activity x0, String x1) {
        Class[] clsArr = {PathViewMapStep3Activity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8260, clsArr, Void.TYPE).isSupported) {
            x0.v0(x1);
        }
    }

    static /* synthetic */ void l0(PathViewMapStep3Activity x0, LatLng x1, String x2) {
        Class[] clsArr = {PathViewMapStep3Activity.class, LatLng.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 8261, clsArr, Void.TYPE).isSupported) {
            x0.x0(x1, x2);
        }
    }

    public class d implements CenterSeekBar.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void b(CenterSeekBar centerSeekBar, int progress, boolean z) {
            if (!PatchProxy.proxy(new Object[]{centerSeekBar, new Integer(progress), new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8263, new Class[]{CenterSeekBar.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                PathViewMapStep3Activity.this.p2.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(PathViewMapStep3Activity.this.p2.getCameraPosition().target).zoom(PathViewMapStep3Activity.this.p2.getCameraPosition().zoom).bearing((PathViewMapStep3Activity.this.E4 + ((float) progress)) - 180.0f).build()));
            }
        }

        public void a(CenterSeekBar seekBar) {
        }

        public void c(CenterSeekBar seekBar) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 8246, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            org.greenrobot.eventbus.c.c().p(this);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            initView();
            u0();
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8247, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (RelativeLayout) findViewById(R$id.title_inside);
            this.a1 = (TextView) findViewById(R$id.tv_title);
            this.a2 = (ImageView) findViewById(R$id.btn_back);
            this.C4 = findViewById(R$id.line);
            CenterSeekBar centerSeekBar = (CenterSeekBar) findViewById(R$id.seekbar);
            this.p3 = centerSeekBar;
            centerSeekBar.setOnSeekBarChangeListener(this.F4);
            TextView textView = (TextView) findViewById(R$id.tv_confirm);
            this.p1 = textView;
            textView.setOnClickListener(this);
            this.D4 = (RadarArcView) findViewById(R$id.radar_arc_view);
        }
    }

    public class e implements View.OnApplyWindowInsetsListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, windowInsets}, this, changeQuickRedirect2, false, 8269, new Class[]{View.class, WindowInsets.class}, WindowInsets.class);
            if (proxy.isSupported) {
                return (WindowInsets) proxy.result;
            }
            PathViewMapStep3Activity.e0(PathViewMapStep3Activity.this);
            PathViewMapStep3Activity.this.getWindow().getDecorView().setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) null);
            return view.onApplyWindowInsets(windowInsets);
        }
    }

    private void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8248, new Class[0], Void.TYPE).isSupported) {
            getWindow().getDecorView().setOnApplyWindowInsetsListener(new e());
            t0();
            Intent i2 = getIntent();
            if (i2 != null) {
                try {
                    this.z4 = (CameraPosition) i2.getParcelableExtra("currentCameraPosition");
                    v0("currentCameraPosition bear:" + this.z4.bearing + ",zoom:" + this.z4.zoom);
                    this.A4 = i2.getStringExtra("deviceId");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.a1.setText(R$string.lds_radar_map_title);
            Places.initialize(getApplicationContext(), getResources().getString(R$string.maps_api_key));
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R$id.map);
            this.G4 = supportMapFragment;
            supportMapFragment.getMapAsync(this);
            new Intent();
            this.a2.setOnClickListener(new f());
            this.a1.getPaint().setFakeBoldText(true);
        }
    }

    public class f implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8270, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            PathViewMapStep3Activity.this.setResult(-1, (Intent) null);
            PathViewMapStep3Activity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void t0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8249, new Class[0], Void.TYPE).isSupported) {
            int statusBarHeight = ((SystemService) com.alibaba.android.arouter.launcher.a.c().g(SystemService.class)).getStatusBarHeight(this);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.p0.getLayoutParams();
            lp.topMargin = statusBarHeight;
            this.p0.setLayoutParams(lp);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        if (!PatchProxy.proxy(new Object[]{googleMap}, this, changeQuickRedirect, false, 8250, new Class[]{GoogleMap.class}, Void.TYPE).isSupported) {
            this.p2 = googleMap;
            googleMap.setIndoorEnabled(false);
            this.p2.setMapType(this.p4);
            this.p2.getUiSettings().setZoomGesturesEnabled(false);
            this.p2.getUiSettings().setScrollGesturesEnabled(false);
            this.p2.getUiSettings().setTiltGesturesEnabled(false);
            this.p2.getUiSettings().setRotateGesturesEnabled(true);
            this.p2.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(false);
            this.p2.getUiSettings().setCompassEnabled(true);
            this.p2.setOnCameraIdleListener(new g());
            this.p2.setOnCameraMoveListener(new h());
            CameraPosition cameraPosition = this.z4;
            if (cameraPosition != null) {
                this.p2.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                GoogleMap googleMap2 = this.p2;
                googleMap2.moveCamera(CameraUpdateFactory.zoomTo(googleMap2.getMaxZoomLevel()));
                c.a("放大到最大倍数:" + this.p2.getMaxZoomLevel());
                this.I4.post(new i(8.0d, googleMap, getResources().getDisplayMetrics().widthPixels));
            }
        }
    }

    public class g implements GoogleMap.OnCameraIdleListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onCameraIdle() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8271, new Class[0], Void.TYPE).isSupported) {
                PathViewMapStep3Activity pathViewMapStep3Activity = PathViewMapStep3Activity.this;
                PathViewMapStep3Activity.f0(pathViewMapStep3Activity, "onCameraIdlec bearing:" + PathViewMapStep3Activity.this.p2.getCameraPosition().bearing + ",currentLevel:" + PathViewMapStep3Activity.this.p2.getCameraPosition().zoom);
            }
        }
    }

    public class h implements GoogleMap.OnCameraMoveListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onCameraMove() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8272, new Class[0], Void.TYPE).isSupported) {
                int progress = (int) ((PathViewMapStep3Activity.this.p2.getCameraPosition().bearing - PathViewMapStep3Activity.this.E4) + 180.0f);
                PathViewMapStep3Activity.this.p3.setOnSeekBarChangeListener((CenterSeekBar.a) null);
                int showProgress = progress;
                if (progress < 0) {
                    showProgress = progress + 360;
                } else if (progress > 360) {
                    showProgress = progress - 360;
                }
                c.a("onCameraMove:" + PathViewMapStep3Activity.this.p2.getCameraPosition().bearing + ",progress:" + progress + ",showProgress:" + showProgress);
                PathViewMapStep3Activity.this.p3.setProgress(showProgress);
                PathViewMapStep3Activity.this.p3.setOnSeekBarChangeListener(PathViewMapStep3Activity.this.F4);
            }
        }
    }

    public class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ double c;
        final /* synthetic */ GoogleMap d;
        final /* synthetic */ int f;

        i(double d2, GoogleMap googleMap, int i) {
            this.c = d2;
            this.d = googleMap;
            this.f = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8273, new Class[0], Void.TYPE).isSupported) {
                PathViewMapStep3Activity pathViewMapStep3Activity = PathViewMapStep3Activity.this;
                PathViewMapStep3Activity.f0(pathViewMapStep3Activity, "bear:" + PathViewMapStep3Activity.this.z4.bearing + ",zoom:" + PathViewMapStep3Activity.this.z4.zoom);
                PathViewMapStep3Activity pathViewMapStep3Activity2 = PathViewMapStep3Activity.this;
                float unused = pathViewMapStep3Activity2.E4 = pathViewMapStep3Activity2.z4.bearing;
                double angle = (double) (PathViewMapStep3Activity.this.z4.bearing - 0.0f);
                LatLng latLng0 = SphericalUtil.computeOffset(PathViewMapStep3Activity.this.z4.target, this.c, angle);
                PathViewMapStep3Activity.l0(PathViewMapStep3Activity.this, latLng0, "正北0度方向");
                Projection projection = PathViewMapStep3Activity.this.p2.getProjection();
                Point pointCenter = projection.toScreenLocation(PathViewMapStep3Activity.this.z4.target);
                c.a("pointCenter x,y:" + pointCenter.x + "," + pointCenter.y);
                Point point0 = projection.toScreenLocation(latLng0);
                c.a("point0 x,y:" + point0.x + "," + point0.y);
                LatLng latLng1 = SphericalUtil.computeOffset(PathViewMapStep3Activity.this.z4.target, this.c, angle - 90.0d);
                PathViewMapStep3Activity.l0(PathViewMapStep3Activity.this, latLng1, "正西-90方向");
                Point point1 = projection.toScreenLocation(latLng1);
                c.a("point1 x,y:" + point1.x + "," + point1.y);
                Point startPoint = projection.toScreenLocation(this.d.getProjection().getVisibleRegion().nearLeft);
                c.a("startPoint x,y:" + startPoint.x + "," + startPoint.y + ",widthPixels:" + this.f);
                int height = (point1.y - point0.y) * 2;
                PathViewMapStep3Activity.this.D4.z = point1.x;
                PathViewMapStep3Activity.this.D4.p0 = point0.y;
                PathViewMapStep3Activity.this.D4.a1 = PathViewMapStep3Activity.this.D4.z + ((point0.x - point1.x) * 2);
                PathViewMapStep3Activity.this.D4.p1 = PathViewMapStep3Activity.this.D4.p0 + height;
                PathViewMapStep3Activity.this.D4.invalidate();
                c.a("radarArcView height/2:" + (height / 2));
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) PathViewMapStep3Activity.this.C4.getLayoutParams();
                params.height = height / 2;
                PathViewMapStep3Activity.this.C4.setLayoutParams(params);
                c.a("verticalLine height:" + params.height);
            }
        }
    }

    private void x0(LatLng latLng, String msg) {
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8251, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (e.c()) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        if (v.getId() == R$id.tv_confirm) {
            v0("点击保存地图图片");
            this.p2.snapshot(new j());
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class j implements GoogleMap.SnapshotReadyCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void onSnapshotReady(Bitmap snapshot) {
            if (!PatchProxy.proxy(new Object[]{snapshot}, this, changeQuickRedirect, false, 8274, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                PathViewMapStep3Activity.f0(PathViewMapStep3Activity.this, "onSnapshotReady saveBitmap");
                PathViewMapStep3Activity.this.w0(snapshot);
            }
        }
    }

    public void y0(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8252, new Class[]{Context.class}, Void.TYPE).isSupported) {
            com.leedarson.base.views.g gVar = new com.leedarson.base.views.g(context);
            this.B4 = gVar;
            gVar.g();
        }
    }

    public class a implements io.reactivex.functions.f<Bitmap, String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8265, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((Bitmap) obj);
        }

        public String a(Bitmap bitmap) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap}, this, changeQuickRedirect, false, 8264, new Class[]{Bitmap.class}, String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            int x = PathViewMapStep3Activity.this.D4.z - 10;
            int y = PathViewMapStep3Activity.this.D4.p0 - 150;
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, x, y, (PathViewMapStep3Activity.this.D4.a1 + 10) - x, (PathViewMapStep3Activity.this.D4.p1 + 150) - y);
            File f = new File(PathViewMapStep3Activity.this.getExternalCacheDir(), "map_" + System.currentTimeMillis() + PictureMimeType.PNG);
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                PathViewMapStep3Activity.f0(PathViewMapStep3Activity.this, "写入radarBitmap到图片文件成功");
                return f.getAbsolutePath();
            } catch (Exception e) {
                PathViewMapStep3Activity.f0(PathViewMapStep3Activity.this, "写入radarBitmap到图片文件成功 e:" + e.getMessage());
                return "";
            }
        }
    }

    public void w0(Bitmap bitmap) {
        if (!PatchProxy.proxy(new Object[]{bitmap}, this, changeQuickRedirect, false, 8253, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
            y0(this);
            io.reactivex.l.F(bitmap).G(new a()).G(new l()).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.android.schedulers.a.a()).X(new k());
        }
    }

    public class l implements io.reactivex.functions.f<String, Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8277, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String) obj);
        }

        public Boolean a(String filePath) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, this, changeQuickRedirect, false, 8276, new Class[]{String.class}, Boolean.class);
            if (proxy.isSupported) {
                return (Boolean) proxy.result;
            }
            b q0 = PathViewMapStep3Activity.this.q0();
            PathViewMapStep3Activity pathViewMapStep3Activity = PathViewMapStep3Activity.this;
            q0.d(pathViewMapStep3Activity, pathViewMapStep3Activity.A4, filePath, PathViewMapStep3Activity.this.z4.target);
            return true;
        }
    }

    public class k implements io.reactivex.functions.e<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8275, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Boolean) obj);
            }
        }

        public void a(Boolean aBoolean) {
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void uploadFileSuccess(UploadFileResultEvent uploadFileSuccessEvent) {
        if (!PatchProxy.proxy(new Object[]{uploadFileSuccessEvent}, this, changeQuickRedirect, false, 8254, new Class[]{UploadFileResultEvent.class}, Void.TYPE).isSupported) {
            c.a("uploadFileSuccess:" + Thread.currentThread().getName());
            if (uploadFileSuccessEvent.code == 200) {
                q0().c(uploadFileSuccessEvent.getFileId(), this.A4, this.z4.target, new b());
                return;
            }
            c.a("uploadFile fail");
            Toast.makeText(this, R$string.lds_radar_map_add_fail, 0).show();
        }
    }

    public class b implements b.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8266, new Class[]{Object.class}, Void.TYPE).isSupported) {
                PathViewMapStep3Activity.b0(PathViewMapStep3Activity.this);
                Toast.makeText(PathViewMapStep3Activity.this, R$string.lds_radar_map_add_success, 0).show();
                c.a("postRadarData success:" + Thread.currentThread().getName());
                PathViewMapStep3Activity.this.I4.sendEmptyMessageDelayed(1, 1000);
            }
        }

        public void b(int code, String str) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), str}, this, changeQuickRedirect, false, 8267, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                c.a("postRadarData fail:" + code);
                PathViewMapStep3Activity.b0(PathViewMapStep3Activity.this);
                Toast.makeText(PathViewMapStep3Activity.this, "上传图片失败", 0).show();
            }
        }
    }

    public class c extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        c(Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 8268, new Class[]{Message.class}, Void.TYPE).isSupported) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    com.leedarson.base.utils.c.h().e(PathViewMapActivity.class);
                    com.leedarson.base.utils.c.h().e(PathViewMapStep2Activity.class);
                    PathViewMapStep3Activity.this.finish();
                }
            }
        }
    }

    public b q0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8255, new Class[0], b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        if (this.H4 == null) {
            this.H4 = new b();
        }
        return this.H4;
    }

    private void s0() {
        com.leedarson.base.views.g gVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8256, new Class[0], Void.TYPE).isSupported && (gVar = this.B4) != null) {
            gVar.e();
        }
    }

    public int O() {
        return R$layout.activity_pathview_map_3;
    }

    public void init() {
    }

    public void R() {
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8257, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().r(this);
            super.onDestroy();
        }
    }

    private void v0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 8258, new Class[]{String.class}, Void.TYPE).isSupported) {
            c.a(msg);
        }
    }
}
