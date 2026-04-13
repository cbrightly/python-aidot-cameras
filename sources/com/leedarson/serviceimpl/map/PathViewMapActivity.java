package com.leedarson.serviceimpl.map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.serviceimpl.map.b;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.event.DeleteRadarMapEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.z;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;

public class PathViewMapActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A4 = 1;
    /* access modifiers changed from: private */
    public FusedLocationProviderClient B4;
    /* access modifiers changed from: private */
    public float C4;
    LocationRequest D4;
    Location E4;
    Geocoder F4;
    AutocompleteSupportFragment G4;
    private LinearLayout H4;
    private ImageView I4;
    private ImageView J4;
    /* access modifiers changed from: private */
    public TextView K4;
    /* access modifiers changed from: private */
    public TextView L4;
    private com.leedarson.base.views.common.dialogs.a M4;
    SupportMapFragment N4;
    View O4;
    ImageView P4;
    /* access modifiers changed from: private */
    public boolean Q4 = true;
    /* access modifiers changed from: private */
    public String R4;
    private com.leedarson.base.views.g S4;
    LocationCallback T4 = new i();
    private Application.ActivityLifecycleCallbacks U4 = new d();
    private RelativeLayout a1;
    private ImageView a2;
    private RelativeLayout p0;
    private TextView p1;
    /* access modifiers changed from: private */
    public LatLng p2;
    /* access modifiers changed from: private */
    public CameraPosition p3;
    /* access modifiers changed from: private */
    public String p4 = "";
    /* access modifiers changed from: private */
    public GoogleMap z4;

    static /* synthetic */ void X(PathViewMapActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8209, new Class[]{PathViewMapActivity.class}, Void.TYPE).isSupported) {
            x0.G0();
        }
    }

    static /* synthetic */ void c0(PathViewMapActivity x0, LatLng x1) {
        Class[] clsArr = {PathViewMapActivity.class, LatLng.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8212, clsArr, Void.TYPE).isSupported) {
            x0.B0(x1);
        }
    }

    static /* synthetic */ void e0(PathViewMapActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8213, new Class[]{PathViewMapActivity.class}, Void.TYPE).isSupported) {
            x0.y0();
        }
    }

    static /* synthetic */ void j0(PathViewMapActivity x0, LatLng x1) {
        Class[] clsArr = {PathViewMapActivity.class, LatLng.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8214, clsArr, Void.TYPE).isSupported) {
            x0.J0(x1);
        }
    }

    static /* synthetic */ void k0(PathViewMapActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8215, new Class[]{PathViewMapActivity.class}, Void.TYPE).isSupported) {
            x0.C0();
        }
    }

    static /* synthetic */ void v0(PathViewMapActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8210, new Class[]{PathViewMapActivity.class}, Void.TYPE).isSupported) {
            x0.E0();
        }
    }

    static /* synthetic */ void x0(PathViewMapActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8211, new Class[]{PathViewMapActivity.class}, Void.TYPE).isSupported) {
            x0.F0();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 8189, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            initView();
            H0();
            I0();
            com.leedarson.base.utils.c.h().a(this);
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8190, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (RelativeLayout) findViewById(R$id.ll_title);
            this.a1 = (RelativeLayout) findViewById(R$id.title_inside);
            this.p1 = (TextView) findViewById(R$id.tv_title);
            this.a2 = (ImageView) findViewById(R$id.btn_back);
            this.H4 = (LinearLayout) findViewById(R$id.ll_search);
            this.O4 = findViewById(R$id.map_layout);
            this.P4 = (ImageView) findViewById(R$id.center_img);
            ImageView imageView = (ImageView) findViewById(R$id.radar_icon_location);
            this.I4 = imageView;
            imageView.setOnClickListener(this);
            ImageView imageView2 = (ImageView) findViewById(R$id.radar_icon_maptype);
            this.J4 = imageView2;
            imageView2.setOnClickListener(this);
            TextView textView = (TextView) findViewById(R$id.tv_continue);
            this.K4 = textView;
            textView.setOnClickListener(this);
            TextView textView2 = (TextView) findViewById(R$id.tv_delete_location);
            this.L4 = textView2;
            textView2.setOnClickListener(this);
        }
    }

    public class e implements View.OnApplyWindowInsetsListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, windowInsets}, this, changeQuickRedirect2, false, 8216, new Class[]{View.class, WindowInsets.class}, WindowInsets.class);
            if (proxy.isSupported) {
                return (WindowInsets) proxy.result;
            }
            PathViewMapActivity.X(PathViewMapActivity.this);
            PathViewMapActivity.this.getWindow().getDecorView().setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) null);
            return view.onApplyWindowInsets(windowInsets);
        }
    }

    private void H0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_EVENT_REPORT, new Class[0], Void.TYPE).isSupported) {
            getWindow().getDecorView().setOnApplyWindowInsetsListener(new e());
            G0();
            int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            c.a("code::" + code);
            this.H4.setVisibility(0);
            Intent i2 = getIntent();
            if (i2 != null) {
                try {
                    this.R4 = i2.getStringExtra("deviceId");
                    Log.i("PathViewMapActivity11", "deviceId:" + this.R4);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.p1.setText(R$string.lds_radar_map_title);
            this.B4 = LocationServices.getFusedLocationProviderClient((Activity) this);
            this.F4 = new Geocoder(this);
            Context applicationContext = getApplicationContext();
            Resources resources = getResources();
            int i3 = R$string.maps_api_key;
            Places.initialize(applicationContext, resources.getString(i3));
            Log.i("zqr", "url:" + getResources().getString(i3));
            AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R$id.autocomplete_fragment);
            this.G4 = autocompleteSupportFragment;
            autocompleteSupportFragment.setHint("Search Here");
            this.G4.getView().findViewById(R$id.places_autocomplete_search_button).setVisibility(8);
            this.G4.setPlaceFields(Arrays.asList(new Place.Field[]{Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG}));
            this.G4.setOnPlaceSelectedListener(new f());
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R$id.map);
            this.N4 = supportMapFragment;
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) supportMapFragment.getView().getLayoutParams();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            params.width = screenWidth;
            params.height = screenWidth;
            this.N4.getMapAsync(this);
            new Intent();
            this.a2.setOnClickListener(new g());
            this.p1.getPaint().setFakeBoldText(true);
        }
    }

    public class f implements PlaceSelectionListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onPlaceSelected(Place place) {
            if (!PatchProxy.proxy(new Object[]{place}, this, changeQuickRedirect, false, 8224, new Class[]{Place.class}, Void.TYPE).isSupported) {
                if (place != null && place.getLatLng() != null) {
                    String unused = PathViewMapActivity.this.p4 = place.getName();
                    LatLng latLng = place.getLatLng();
                    LatLng unused2 = PathViewMapActivity.this.p2 = latLng;
                    c.a("333 moveCamera");
                    PathViewMapActivity.this.z4.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, PathViewMapActivity.this.z4.getCameraPosition().zoom));
                    c.a("点击搜索选择地址，不需要根据经纬度获取地址");
                    boolean unused3 = PathViewMapActivity.this.Q4 = false;
                }
            }
        }

        public void onError(Status status) {
        }
    }

    public class g implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8225, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            PathViewMapActivity.this.setResult(-1, (Intent) null);
            PathViewMapActivity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void I0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8192, new Class[0], Void.TYPE).isSupported) {
            getApplication().registerActivityLifecycleCallbacks(this.U4);
        }
    }

    public void K0(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8193, new Class[]{Context.class}, Void.TYPE).isSupported) {
            com.leedarson.base.views.g gVar = new com.leedarson.base.views.g(context);
            this.S4 = gVar;
            gVar.g();
        }
    }

    private void E0() {
        com.leedarson.base.views.g gVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8194, new Class[0], Void.TYPE).isSupported && (gVar = this.S4) != null) {
            gVar.e();
        }
    }

    private void C0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8195, new Class[0], Void.TYPE).isSupported) {
            try {
                Task<Location> locationResult = this.B4.getLastLocation();
                c.a("to getDeviceLocation");
                locationResult.addOnCompleteListener((Activity) this, (OnCompleteListener<Location>) new h());
            } catch (Exception e2) {
                c.a("getDeviceLocation exception:" + e2.getMessage());
            }
        }
    }

    public class h implements OnCompleteListener<Location> {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onComplete(@NonNull Task<Location> task) {
            if (!PatchProxy.proxy(new Object[]{task}, this, changeQuickRedirect, false, 8226, new Class[]{Task.class}, Void.TYPE).isSupported) {
                if (task.isSuccessful()) {
                    Location lastKnownLocation = task.getResult();
                    if (lastKnownLocation != null) {
                        LatLng unused = PathViewMapActivity.this.p2 = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        c.a("getDeviceLocation success:" + lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude() + ",setDefaultZoom:" + PathViewMapActivity.this.C4);
                        if (PathViewMapActivity.this.D0() != null) {
                            PathViewMapActivity.this.D0().animateCamera(CameraUpdateFactory.newLatLngZoom(PathViewMapActivity.this.p2, PathViewMapActivity.this.C4));
                        }
                        PathViewMapActivity.this.P4.setVisibility(0);
                        return;
                    }
                    c.a("getDeviceLocation lastKnownLocation is null to requestLocationUpdates");
                    FusedLocationProviderClient u0 = PathViewMapActivity.this.B4;
                    PathViewMapActivity pathViewMapActivity = PathViewMapActivity.this;
                    u0.requestLocationUpdates(pathViewMapActivity.D4, pathViewMapActivity.T4, Looper.myLooper());
                    return;
                }
                c.a("getDeviceLocation fail:");
            }
        }
    }

    private void G0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8196, new Class[0], Void.TYPE).isSupported) {
            int statusBarHeight = ((SystemService) com.alibaba.android.arouter.launcher.a.c().g(SystemService.class)).getStatusBarHeight(this);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.a1.getLayoutParams();
            lp.topMargin = statusBarHeight;
            this.a1.setLayoutParams(lp);
        }
    }

    public class i extends LocationCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void onLocationResult(LocationResult locationResult) {
            if (!PatchProxy.proxy(new Object[]{locationResult}, this, changeQuickRedirect, false, 8227, new Class[]{LocationResult.class}, Void.TYPE).isSupported) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    Location location = locationList.get(locationList.size() - 1);
                    PathViewMapActivity pathViewMapActivity = PathViewMapActivity.this;
                    pathViewMapActivity.E4 = location;
                    if (location != null && pathViewMapActivity.F4 != null) {
                        String addre = "";
                        if (Geocoder.isPresent()) {
                            try {
                                List<Address> addresses = PathViewMapActivity.this.F4.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses.size() > 0) {
                                    Address address = addresses.get(0);
                                    if (address.getAdminArea() == null || address.getLocality() == null || address.getFeatureName() == null) {
                                        addre = address.getAddressLine(0);
                                    } else {
                                        addre = address.getAdminArea() + address.getLocality() + address.getFeatureName();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        LatLng unused = PathViewMapActivity.this.p2 = latLng;
                        String unused2 = PathViewMapActivity.this.p4 = addre;
                        PathViewMapActivity.this.G4.setText(addre);
                        c.a("getDeviceLocation use requestLocationUpdates success:" + PathViewMapActivity.this.p2.latitude + "," + PathViewMapActivity.this.p2.longitude + ",setDefaultZoom:" + PathViewMapActivity.this.C4);
                        PathViewMapActivity.this.z4.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, PathViewMapActivity.this.C4));
                        boolean unused3 = PathViewMapActivity.this.Q4 = false;
                    }
                }
            }
        }
    }

    public GoogleMap D0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8197, new Class[0], GoogleMap.class);
        if (proxy.isSupported) {
            return (GoogleMap) proxy.result;
        }
        if (this.z4 == null) {
            Toast.makeText(this, "no google services available", 1).show();
            finish();
        }
        return this.z4;
    }

    public void onMapReady(GoogleMap googleMap) {
        if (!PatchProxy.proxy(new Object[]{googleMap}, this, changeQuickRedirect, false, 8198, new Class[]{GoogleMap.class}, Void.TYPE).isSupported) {
            this.z4 = googleMap;
            googleMap.setMapType(this.A4);
            LocationRequest locationRequest = new LocationRequest();
            this.D4 = locationRequest;
            locationRequest.setInterval(120000);
            this.D4.setFastestInterval(120000);
            this.D4.setPriority(102);
            this.C4 = this.z4.getMaxZoomLevel() * 0.75f;
            K0(this);
            c.a("defaultZoom:" + this.C4 + ",request to get Radar Map");
            new b().b(this.R4, new j());
        }
    }

    public class j implements b.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void a(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8228, new Class[]{Object.class}, Void.TYPE).isSupported) {
                try {
                    PathViewMapActivity.v0(PathViewMapActivity.this);
                    c.a("getRadarMap postSuccess:" + obj + ",deviceId:" + PathViewMapActivity.this.R4);
                    JSONObject jsonObject = new JSONObject((String) obj);
                    if (jsonObject.optInt("code") == 200) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        String longitude = data.optString("longitude");
                        String latitude = data.optString("latitude");
                        if (!TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude)) {
                            LatLng unused = PathViewMapActivity.this.p2 = new LatLng(Double.valueOf(latitude).doubleValue(), Double.valueOf(longitude).doubleValue());
                        }
                    }
                } catch (Exception e) {
                    c.a("getRadarMap postSuccess exception:" + e.getMessage());
                }
                PathViewMapActivity.x0(PathViewMapActivity.this);
            }
        }

        public void b(int code, String msg) {
            Object[] objArr = {new Integer(code), msg};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8229, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                PathViewMapActivity.v0(PathViewMapActivity.this);
                c.a("postFail:" + code + ",msg:" + msg);
                PathViewMapActivity.x0(PathViewMapActivity.this);
            }
        }
    }

    public class k implements GoogleMap.OnCameraIdleListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void onCameraIdle() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8230, new Class[0], Void.TYPE).isSupported) {
                PathViewMapActivity pathViewMapActivity = PathViewMapActivity.this;
                CameraPosition unused = pathViewMapActivity.p3 = pathViewMapActivity.z4.getCameraPosition();
                c.a("onCameraIdle needGetAddress:" + PathViewMapActivity.this.Q4 + ",currentPosition zoom:" + PathViewMapActivity.this.p3.zoom);
                LatLng latLng = PathViewMapActivity.this.z4.getCameraPosition().target;
                if (PathViewMapActivity.this.Q4) {
                    c.a("onCameraIdle toGetAddress by latlng:" + latLng.latitude + "," + latLng.longitude);
                    PathViewMapActivity.c0(PathViewMapActivity.this, latLng);
                }
                boolean unused2 = PathViewMapActivity.this.Q4 = true;
            }
        }
    }

    private void F0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8199, new Class[0], Void.TYPE).isSupported) {
            this.z4.setOnCameraIdleListener(new k());
            if (this.p2 != null) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.K4.getLayoutParams();
                params.removeRule(12);
                params.addRule(2, R$id.tv_delete_location);
                this.L4.setVisibility(0);
                this.P4.setVisibility(0);
                c.a("has set location moveCamera to defaultzoom:" + this.C4);
                this.z4.moveCamera(CameraUpdateFactory.newLatLngZoom(this.p2, this.C4));
                this.Q4 = true;
                return;
            }
            this.L4.setVisibility(8);
            if (Build.VERSION.SDK_INT < 23) {
                c.a("<6.0版本，requestLocationUpdates");
                this.B4.requestLocationUpdates(this.D4, this.T4, Looper.myLooper());
            } else if (EasyPermissions.a(this, "android.permission.ACCESS_FINE_LOCATION")) {
                c.a("当前获取到位置权限");
                C0();
            } else {
                L0();
            }
        }
    }

    private void L0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8200, new Class[0], Void.TYPE).isSupported) {
            c.a("未获取到权限");
            this.P4.setVisibility(8);
            this.O4.setBackgroundColor(Color.parseColor("#FFDBE1E6"));
            this.K4.setEnabled(false);
        }
    }

    private void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8201, new Class[0], Void.TYPE).isSupported) {
            if (EasyPermissions.a(this, "android.permission.ACCESS_FINE_LOCATION")) {
                C0();
            } else {
                N0();
            }
        }
    }

    private void N0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8202, new Class[0], Void.TYPE).isSupported) {
            c.a("点击显示弹窗");
            com.leedarson.base.views.common.dialogs.a ldsActionDialog = new com.leedarson.base.views.common.dialogs.a(this);
            ldsActionDialog.i(getString(R$string.lds_radar_map_location_permission_title));
            ldsActionDialog.h(getString(R$string.lds_radar_map_location_permission_content));
            ldsActionDialog.d(getString(R$string.cancel));
            ldsActionDialog.f(getString(R$string.per_guide_go_setting));
            ldsActionDialog.c(new l());
            ldsActionDialog.show();
        }
    }

    public class l implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8231, new Class[0], Void.TYPE).isSupported) {
                c.a("请求定位权限");
                PathViewMapActivity.e0(PathViewMapActivity.this);
            }
        }

        public void onCancel() {
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8203, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (v.getId() == R$id.radar_icon_location) {
            z0();
        } else if (v.getId() == R$id.radar_icon_maptype) {
            if (this.A4 == 1) {
                this.A4 = 4;
            } else {
                this.A4 = 1;
            }
            if (this.A4 == 1) {
                this.J4.setImageResource(R$drawable.radar_icon_map_2d_s);
            } else {
                this.J4.setImageResource(R$drawable.radar_icon_map_3d);
            }
            if (D0() != null) {
                D0().setMapType(this.A4);
            }
        } else if (v.getId() == R$id.tv_continue) {
            if (e.c()) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            } else if (D0() != null) {
                Intent intent = new Intent(this, PathViewMapStep2Activity.class);
                Log.i("PathViewMapActivity11", "put current:" + this.p2);
                intent.putExtra("currentCameraPosition", this.p3);
                intent.putExtra("deviceId", this.R4);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        } else if (v.getId() == R$id.tv_delete_location) {
            com.leedarson.base.views.common.dialogs.a aVar = this.M4;
            if (aVar == null || !aVar.isShowing()) {
                com.leedarson.base.views.common.dialogs.a aVar2 = new com.leedarson.base.views.common.dialogs.a(this);
                this.M4 = aVar2;
                aVar2.h(getString(R$string.lds_radar_map_delete_content));
                this.M4.d(getString(R$string.cancel));
                this.M4.f(getString(R$string.confirm));
                this.M4.c(new m());
                this.M4.show();
            } else {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class m implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public class a implements b.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void a(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8234, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    PathViewMapActivity.this.L4.setVisibility(8);
                    ((RelativeLayout.LayoutParams) PathViewMapActivity.this.K4.getLayoutParams()).addRule(12);
                    org.greenrobot.eventbus.c.c().l(new DeleteRadarMapEvent(PathViewMapActivity.this.R4));
                }
            }

            public void b(int code, String msg) {
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8232, new Class[0], Void.TYPE).isSupported) {
                c.a("confirm deleteRadarMap");
                b bVar = new b();
                PathViewMapActivity pathViewMapActivity = PathViewMapActivity.this;
                bVar.a(pathViewMapActivity, pathViewMapActivity.R4, new a());
            }
        }

        public void onCancel() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8233, new Class[0], Void.TYPE).isSupported) {
                c.a("onCancel");
            }
        }
    }

    private void B0(LatLng latLng) {
        if (!PatchProxy.proxy(new Object[]{latLng}, this, changeQuickRedirect, false, 8204, new Class[]{LatLng.class}, Void.TYPE).isSupported) {
            Geocoder geocoder = new Geocoder(this);
            if (Geocoder.isPresent()) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses.size() > 0) {
                        this.p4 = addresses.get(0).getAddressLine(0);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (!TextUtils.isEmpty(this.p4)) {
                    c.a("getAddressByLatng result success:" + this.p4);
                    J0(latLng);
                    return;
                }
                c.a("getAddressByLatng result fail");
                A0(latLng);
                return;
            }
            A0(latLng);
        }
    }

    private void A0(LatLng latLng) {
        if (!PatchProxy.proxy(new Object[]{latLng}, this, changeQuickRedirect, false, 8205, new Class[]{LatLng.class}, Void.TYPE).isSupported) {
            c.a("getAddressByHttpRequest");
            new z.a().c().a(new b0.a().e().p(String.format(Locale.US, "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s&language=en-US", new Object[]{Double.valueOf(latLng.latitude), Double.valueOf(latLng.longitude), getString(R$string.maps_api_key)})).b()).o0(new a(latLng));
        }
    }

    public class a implements okhttp3.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LatLng c;

        a(LatLng latLng) {
            this.c = latLng;
        }

        public void onFailure(okhttp3.e eVar, IOException e) {
            Class[] clsArr = {okhttp3.e.class, IOException.class};
            if (!PatchProxy.proxy(new Object[]{eVar, e}, this, changeQuickRedirect, false, 8217, clsArr, Void.TYPE).isSupported) {
                c.a("getAddressByHttpRequest fail:" + e.getMessage());
            }
        }

        public void onResponse(okhttp3.e eVar, d0 response) {
            if (!PatchProxy.proxy(new Object[]{eVar, response}, this, changeQuickRedirect, false, 8218, new Class[]{okhttp3.e.class, d0.class}, Void.TYPE).isSupported) {
                try {
                    String s = response.a().string();
                    PathViewMapActivity pathViewMapActivity = PathViewMapActivity.this;
                    String unused = pathViewMapActivity.p4 = ((LinkedTreeMap) ((ArrayList) ((Map) new Gson().fromJson(s, HashMap.class)).get("results")).get(0)).get("formatted_address") + "";
                    c.a("getAddressByHttpRequest success resultAddress:" + PathViewMapActivity.this.p4);
                    PathViewMapActivity.j0(PathViewMapActivity.this, this.c);
                } catch (Exception e) {
                    e.printStackTrace();
                    c.a("getAddressByHttpRequest exception:" + e.getMessage());
                }
            }
        }
    }

    private void J0(LatLng latLng) {
        if (!PatchProxy.proxy(new Object[]{latLng}, this, changeQuickRedirect, false, 8206, new Class[]{LatLng.class}, Void.TYPE).isSupported) {
            this.p2 = latLng;
            this.G4.setText(this.p4);
            if (!TextUtils.isEmpty(this.p4)) {
                this.K4.setEnabled(true);
            }
        }
    }

    private void y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8207, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.ACCESS_FINE_LOCATION"};
            boolean canRequest = true;
            if (SharePreferenceUtils.getPrefBoolean(this, "location_deny", false)) {
                if (EasyPermissions.h(this, perms[0])) {
                    canRequest = true;
                } else {
                    canRequest = false;
                }
            }
            if (canRequest) {
                new com.tbruyelle.rxpermissions2.b(this).l(perms[0]).Y(new b(), new c());
                return;
            }
            w.K(this);
        }
    }

    public class b implements io.reactivex.functions.e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8220, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 8219, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                if (permission.b) {
                    c.a("定位权限被授权");
                    PathViewMapActivity.k0(PathViewMapActivity.this);
                } else if (permission.c) {
                    c.a("直接拒绝、但是下一次还可以弹起原生的弹窗授权");
                    SharePreferenceUtils.setPrefBoolean(PathViewMapActivity.this, "location_deny", true);
                } else {
                    c.a("已拒绝且不在提示");
                    SharePreferenceUtils.setPrefBoolean(PathViewMapActivity.this, "location_deny", true);
                }
            }
        }
    }

    public class c implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8222, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 8221, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                throwable.printStackTrace();
            }
        }
    }

    public int O() {
        return R$layout.activity_pathview_map;
    }

    public void init() {
    }

    public void R() {
    }

    public class d implements Application.ActivityLifecycleCallbacks {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        public void onActivityStarted(@NonNull Activity activity) {
        }

        public void onActivityResumed(@NonNull Activity activity) {
            EditText googleMapSearchEdit;
            if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8223, new Class[]{Activity.class}, Void.TYPE).isSupported) {
                try {
                    if ("AutocompleteActivity".equals(activity.getClass().getSimpleName()) && (googleMapSearchEdit = (EditText) ((FragmentActivity) activity).findViewById(R.id.places_autocomplete_search_bar)) != null) {
                        ViewParent parent = googleMapSearchEdit.getParent();
                        if (parent instanceof LinearLayout) {
                            ((ViewGroup.MarginLayoutParams) ((LinearLayout) parent).getLayoutParams()).topMargin = com.leedarson.serviceimpl.system.notch.b.b(activity) + 100;
                        }
                    }
                } catch (Exception e) {
                    Log.i("zqr", "e:" + e.getMessage());
                }
            }
        }

        public void onActivityPaused(@NonNull Activity activity) {
        }

        public void onActivityStopped(@NonNull Activity activity) {
        }

        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }

        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8208, new Class[0], Void.TYPE).isSupported) {
            getApplication().unregisterActivityLifecycleCallbacks(this.U4);
            super.onDestroy();
        }
    }
}
