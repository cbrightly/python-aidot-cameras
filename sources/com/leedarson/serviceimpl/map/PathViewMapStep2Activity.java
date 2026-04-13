package com.leedarson.serviceimpl.map;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.libraries.places.api.Places;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class PathViewMapStep2Activity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ImageView A4;
    private TextView B4;
    private String C4;
    private RelativeLayout a1;
    private ImageView a2;
    private RelativeLayout p0;
    private TextView p1;
    /* access modifiers changed from: private */
    public CameraPosition p2;
    /* access modifiers changed from: private */
    public GoogleMap p3;
    private int p4 = 4;
    Geocoder z4;

    static /* synthetic */ void X(PathViewMapStep2Activity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8241, new Class[]{PathViewMapStep2Activity.class}, Void.TYPE).isSupported) {
            x0.b0();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 8235, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            initView();
            c0();
            com.leedarson.base.utils.c.h().a(this);
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8236, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (RelativeLayout) findViewById(R$id.ll_title);
            this.a1 = (RelativeLayout) findViewById(R$id.title_inside);
            this.p1 = (TextView) findViewById(R$id.tv_title);
            this.a2 = (ImageView) findViewById(R$id.btn_back);
            ImageView imageView = (ImageView) findViewById(R$id.radar_icon_maptype);
            this.A4 = imageView;
            imageView.setOnClickListener(this);
            TextView textView = (TextView) findViewById(R$id.tv_continue);
            this.B4 = textView;
            textView.setOnClickListener(this);
        }
    }

    public class a implements View.OnApplyWindowInsetsListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, windowInsets}, this, changeQuickRedirect2, false, 8242, new Class[]{View.class, WindowInsets.class}, WindowInsets.class);
            if (proxy.isSupported) {
                return (WindowInsets) proxy.result;
            }
            PathViewMapStep2Activity.X(PathViewMapStep2Activity.this);
            PathViewMapStep2Activity.this.getWindow().getDecorView().setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) null);
            return view.onApplyWindowInsets(windowInsets);
        }
    }

    private void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8237, new Class[0], Void.TYPE).isSupported) {
            getWindow().getDecorView().setOnApplyWindowInsetsListener(new a());
            b0();
            Intent i = getIntent();
            if (i != null) {
                this.p2 = (CameraPosition) i.getParcelableExtra("currentCameraPosition");
                this.C4 = i.getStringExtra("deviceId");
            }
            Log.i("PathViewMapStep2Activity", "currentCameraPosition:" + this.p2);
            this.p1.setText(R$string.lds_radar_map_title);
            this.z4 = new Geocoder(this);
            Places.initialize(getApplicationContext(), getResources().getString(R$string.maps_api_key));
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R$id.map);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mapFragment.getView().getLayoutParams();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            params.width = screenWidth;
            params.height = screenWidth;
            mapFragment.getMapAsync(this);
            new Intent();
            this.a2.setOnClickListener(new b());
            this.p1.getPaint().setFakeBoldText(true);
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8243, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            PathViewMapStep2Activity.this.setResult(-1, (Intent) null);
            PathViewMapStep2Activity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8238, new Class[0], Void.TYPE).isSupported) {
            int statusBarHeight = ((SystemService) com.alibaba.android.arouter.launcher.a.c().g(SystemService.class)).getStatusBarHeight(this);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.a1.getLayoutParams();
            lp.topMargin = statusBarHeight;
            this.a1.setLayoutParams(lp);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        if (!PatchProxy.proxy(new Object[]{googleMap}, this, changeQuickRedirect, false, 8239, new Class[]{GoogleMap.class}, Void.TYPE).isSupported) {
            this.p3 = googleMap;
            googleMap.setMapType(this.p4);
            this.p3.setOnCameraMoveListener(new c());
            this.p3.setOnCameraIdleListener(new d());
            CameraPosition cameraPosition = this.p2;
            if (cameraPosition != null) {
                this.p3.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }

    public class c implements GoogleMap.OnCameraMoveListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onCameraMove() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8244, new Class[0], Void.TYPE).isSupported) {
                PathViewMapStep2Activity pathViewMapStep2Activity = PathViewMapStep2Activity.this;
                CameraPosition unused = pathViewMapStep2Activity.p2 = pathViewMapStep2Activity.p3.getCameraPosition();
            }
        }
    }

    public class d implements GoogleMap.OnCameraIdleListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onCameraIdle() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8245, new Class[0], Void.TYPE).isSupported) {
                PathViewMapStep2Activity pathViewMapStep2Activity = PathViewMapStep2Activity.this;
                CameraPosition unused = pathViewMapStep2Activity.p2 = pathViewMapStep2Activity.p3.getCameraPosition();
                c.a("onCameraIdle currentCameraPosition:" + PathViewMapStep2Activity.this.p2 + "," + PathViewMapStep2Activity.this.p2.target.latitude + "," + PathViewMapStep2Activity.this.p2.target.longitude);
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8240, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (v.getId() == R$id.radar_icon_maptype) {
            if (this.p4 == 1) {
                this.p4 = 4;
            } else {
                this.p4 = 1;
            }
            if (this.p4 == 1) {
                this.A4.setImageResource(R$drawable.radar_icon_map_2d_s);
            } else {
                this.A4.setImageResource(R$drawable.radar_icon_map_3d);
            }
            this.p3.setMapType(this.p4);
        } else if (v.getId() == R$id.tv_continue) {
            Intent intent = new Intent(this, PathViewMapStep3Activity.class);
            intent.putExtra("currentCameraPosition", this.p2);
            intent.putExtra("deviceId", this.C4);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public int O() {
        return R$layout.activity_pathview_map_2;
    }

    public void init() {
    }

    public void R() {
    }
}
