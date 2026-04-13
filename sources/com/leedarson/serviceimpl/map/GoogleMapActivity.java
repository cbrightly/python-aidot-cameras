package com.leedarson.serviceimpl.map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.event.OnLocationPickChangeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
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
import timber.log.a;

public class GoogleMapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public GoogleMap A4;
    private FusedLocationProviderClient B4;
    private final LatLng C4 = new LatLng(-33.8523341d, 151.2106085d);
    LocationRequest D4;
    Location E4;
    Marker F4;
    Geocoder G4;
    AutocompleteSupportFragment H4;
    private LinearLayout I4;
    /* access modifiers changed from: private */
    public LatLng J4;
    private ImageView K4;
    /* access modifiers changed from: private */
    public boolean L4 = true;
    LocationCallback M4 = new d();
    private RelativeLayout a1;
    private TextView a2;
    private RelativeLayout p0;
    private TextView p1;
    private ImageView p2;
    /* access modifiers changed from: private */
    public double p3 = 0.0d;
    /* access modifiers changed from: private */
    public double p4 = 0.0d;
    /* access modifiers changed from: private */
    public String z4 = "";

    static /* synthetic */ void X(GoogleMapActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8151, new Class[]{GoogleMapActivity.class}, Void.TYPE).isSupported) {
            x0.q0();
        }
    }

    static /* synthetic */ void j0(GoogleMapActivity x0, LatLng x1) {
        Class[] clsArr = {GoogleMapActivity.class, LatLng.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8152, clsArr, Void.TYPE).isSupported) {
            x0.n0(x1);
        }
    }

    static /* synthetic */ void k0(GoogleMapActivity x0, LatLng x1) {
        Class[] clsArr = {GoogleMapActivity.class, LatLng.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8153, clsArr, Void.TYPE).isSupported) {
            x0.v0(x1);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 8138, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            initView();
            s0();
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8139, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (RelativeLayout) findViewById(R$id.ll_title);
            this.a1 = (RelativeLayout) findViewById(R$id.title_inside);
            this.p1 = (TextView) findViewById(R$id.tv_title);
            this.a2 = (TextView) findViewById(R$id.btn_confirm);
            this.p2 = (ImageView) findViewById(R$id.btn_back);
            this.I4 = (LinearLayout) findViewById(R$id.ll_search);
            this.K4 = (ImageView) findViewById(R$id.center_img);
        }
    }

    public class a implements View.OnApplyWindowInsetsListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, windowInsets}, this, changeQuickRedirect2, false, 8154, new Class[]{View.class, WindowInsets.class}, WindowInsets.class);
            if (proxy.isSupported) {
                return (WindowInsets) proxy.result;
            }
            GoogleMapActivity.X(GoogleMapActivity.this);
            GoogleMapActivity.this.getWindow().getDecorView().setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) null);
            return view.onApplyWindowInsets(windowInsets);
        }
    }

    private void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8140, new Class[0], Void.TYPE).isSupported) {
            getWindow().getDecorView().setOnApplyWindowInsetsListener(new a());
            q0();
            int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            this.I4.setVisibility(8);
            Intent i2 = getIntent();
            if (i2 != null) {
                try {
                    JSONObject jsonObject = new JSONObject(i2.getStringExtra("data"));
                    JSONObject navObj = jsonObject.getJSONObject("navBar");
                    if (navObj.has("backgroundColor")) {
                        int color = Color.parseColor(navObj.getString("backgroundColor"));
                        this.p0.setBackgroundColor(color);
                        StatusBarUtil.setStatusBarStyleByColor(this, color);
                    }
                    if (navObj.has("title") != 0) {
                        this.p1.setText(navObj.getString("title"));
                    }
                    if (navObj.has("titleColor")) {
                        this.p1.setTextColor(Color.parseColor(navObj.getString("titleColor")));
                    }
                    if (navObj.has("backButtonColor")) {
                        this.p2.setColorFilter(Color.parseColor(navObj.getString("backButtonColor")));
                    }
                    if (navObj.has("rightButtonTitle")) {
                        this.a2.setText(navObj.getString("rightButtonTitle"));
                    } else if (jsonObject.has("confirmButtonText")) {
                        this.a2.setText(jsonObject.getString("confirmButtonText"));
                    }
                    if (navObj.has("rightButtonColor")) {
                        this.a2.setTextColor(Color.parseColor(navObj.getString("rightButtonColor")));
                    }
                    if (jsonObject.has(FirebaseAnalytics.Param.LOCATION)) {
                        this.p3 = jsonObject.getJSONObject(FirebaseAnalytics.Param.LOCATION).getDouble("latitude");
                        this.p4 = jsonObject.getJSONObject(FirebaseAnalytics.Param.LOCATION).getDouble("longitude");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.B4 = LocationServices.getFusedLocationProviderClient((Activity) this);
            this.G4 = new Geocoder(this);
            Context applicationContext = getApplicationContext();
            Resources resources = getResources();
            int i3 = R$string.maps_api_key;
            Places.initialize(applicationContext, resources.getString(i3));
            a.b g2 = timber.log.a.g("GoogleMapActivity");
            g2.m("map_apk_key:" + getResources().getString(i3), new Object[0]);
            AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R$id.autocomplete_fragment);
            this.H4 = autocompleteSupportFragment;
            autocompleteSupportFragment.setPlaceFields(Arrays.asList(new Place.Field[]{Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG}));
            this.H4.setOnPlaceSelectedListener(new b());
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R$id.map)).getMapAsync(this);
            Intent intent = new Intent();
            this.p2.setOnClickListener(new c());
            this.a2.setOnClickListener(new a(this, intent));
            if (SharePreferenceUtils.getPrefString(this, "repositoryName", "").equals("C610-Innr")) {
                this.p1.setTypeface(Typeface.createFromAsset(getAssets(), "NeurialGrotesk.ttf"));
            }
            this.p1.getPaint().setFakeBoldText(true);
        }
    }

    public class b implements PlaceSelectionListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onPlaceSelected(Place place) {
            if (!PatchProxy.proxy(new Object[]{place}, this, changeQuickRedirect, false, 8155, new Class[]{Place.class}, Void.TYPE).isSupported) {
                if (place != null && place.getLatLng() != null) {
                    Marker marker = GoogleMapActivity.this.F4;
                    if (marker != null) {
                        marker.remove();
                    }
                    String unused = GoogleMapActivity.this.z4 = place.getName();
                    LatLng latLng = place.getLatLng();
                    double unused2 = GoogleMapActivity.this.p3 = latLng.latitude;
                    double unused3 = GoogleMapActivity.this.p4 = latLng.longitude;
                    LatLng unused4 = GoogleMapActivity.this.J4 = latLng;
                    GoogleMapActivity.this.A4.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
                    boolean unused5 = GoogleMapActivity.this.L4 = false;
                }
            }
        }

        public void onError(Status status) {
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8156, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            GoogleMapActivity.this.setResult(-1, (Intent) null);
            GoogleMapActivity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: t0 */
    public /* synthetic */ void u0(Intent intent, View view) {
        Class[] clsArr = {Intent.class, View.class};
        if (PatchProxy.proxy(new Object[]{intent, view}, this, changeQuickRedirect, false, 8150, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        intent.putExtra("lat", this.p3);
        intent.putExtra("lon", this.p4);
        intent.putExtra(PlaceTypes.ADDRESS, this.z4);
        OnLocationPickChangeEvent event = new OnLocationPickChangeEvent();
        event.data = intent;
        org.greenrobot.eventbus.c.c().l(event);
        finish();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8141, new Class[0], Void.TYPE).isSupported) {
            int statusBarHeight = ((SystemService) com.alibaba.android.arouter.launcher.a.c().g(SystemService.class)).getStatusBarHeight(this);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.a1.getLayoutParams();
            lp.topMargin = statusBarHeight;
            this.a1.setLayoutParams(lp);
        }
    }

    public class d extends LocationCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onLocationResult(LocationResult locationResult) {
            if (!PatchProxy.proxy(new Object[]{locationResult}, this, changeQuickRedirect, false, 8157, new Class[]{LocationResult.class}, Void.TYPE).isSupported) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    Location location = locationList.get(locationList.size() - 1);
                    GoogleMapActivity googleMapActivity = GoogleMapActivity.this;
                    googleMapActivity.E4 = location;
                    Marker marker = googleMapActivity.F4;
                    if (marker != null) {
                        marker.remove();
                    }
                    if (location != null && GoogleMapActivity.this.G4 != null) {
                        String addre = "";
                        if (Geocoder.isPresent()) {
                            try {
                                List<Address> addresses = GoogleMapActivity.this.G4.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
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
                        LatLng unused = GoogleMapActivity.this.J4 = latLng;
                        String unused2 = GoogleMapActivity.this.z4 = addre;
                        double unused3 = GoogleMapActivity.this.p3 = latLng.latitude;
                        double unused4 = GoogleMapActivity.this.p4 = latLng.longitude;
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(addre);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R$drawable.trans_marker));
                        GoogleMapActivity googleMapActivity2 = GoogleMapActivity.this;
                        googleMapActivity2.F4 = googleMapActivity2.A4.addMarker(markerOptions);
                        GoogleMapActivity.this.F4.setDraggable(true);
                        GoogleMapActivity.this.F4.showInfoWindow();
                        GoogleMapActivity.this.H4.setText(addre);
                        GoogleMapActivity.this.A4.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
                        boolean unused5 = GoogleMapActivity.this.L4 = false;
                    }
                }
            }
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        if (!PatchProxy.proxy(new Object[]{googleMap}, this, changeQuickRedirect, false, 8142, new Class[]{GoogleMap.class}, Void.TYPE).isSupported) {
            this.A4 = googleMap;
            googleMap.setOnMarkerClickListener(this);
            this.A4.setOnMarkerDragListener(this);
            this.A4.setOnCameraMoveStartedListener(new e());
            this.A4.setOnCameraMoveListener(new f());
            this.A4.setOnCameraIdleListener(new g());
            LocationRequest locationRequest = new LocationRequest();
            this.D4 = locationRequest;
            locationRequest.setInterval(120000);
            this.D4.setFastestInterval(120000);
            this.D4.setPriority(102);
            if (this.p3 != 0.0d || this.p4 != 0.0d) {
                this.A4.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.p3, this.p4), 13.0f));
                this.L4 = true;
            } else if (Build.VERSION.SDK_INT < 23) {
                this.B4.requestLocationUpdates(this.D4, this.M4, Looper.myLooper());
            } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                this.B4.requestLocationUpdates(this.D4, this.M4, Looper.myLooper());
            } else {
                l0();
            }
        }
    }

    public class e implements GoogleMap.OnCameraMoveStartedListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onCameraMoveStarted(int i) {
            Marker marker;
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8158, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (marker = GoogleMapActivity.this.F4) != null) {
                marker.remove();
            }
        }
    }

    public class f implements GoogleMap.OnCameraMoveListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onCameraMove() {
        }
    }

    public class g implements GoogleMap.OnCameraIdleListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onCameraIdle() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8159, new Class[0], Void.TYPE).isSupported) {
                LatLng latLng = GoogleMapActivity.this.A4.getCameraPosition().target;
                if (GoogleMapActivity.this.L4) {
                    GoogleMapActivity.j0(GoogleMapActivity.this, latLng);
                } else {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(GoogleMapActivity.this.z4);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R$drawable.trans_marker));
                    GoogleMapActivity googleMapActivity = GoogleMapActivity.this;
                    googleMapActivity.F4 = googleMapActivity.A4.addMarker(markerOptions);
                    GoogleMapActivity.this.F4.setDraggable(true);
                    GoogleMapActivity.this.F4.showInfoWindow();
                }
                boolean unused = GoogleMapActivity.this.L4 = true;
            }
        }
    }

    private void n0(LatLng latLng) {
        if (!PatchProxy.proxy(new Object[]{latLng}, this, changeQuickRedirect, false, 8143, new Class[]{LatLng.class}, Void.TYPE).isSupported) {
            Geocoder geocoder = new Geocoder(this);
            if (Geocoder.isPresent()) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses.size() > 0) {
                        this.z4 = addresses.get(0).getAddressLine(0);
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (!TextUtils.isEmpty(this.z4)) {
                    v0(latLng);
                } else {
                    m0(latLng);
                }
            } else {
                m0(latLng);
            }
        }
    }

    private void m0(LatLng latLng) {
        if (!PatchProxy.proxy(new Object[]{latLng}, this, changeQuickRedirect, false, 8144, new Class[]{LatLng.class}, Void.TYPE).isSupported) {
            new z.a().c().a(new b0.a().e().p(String.format(Locale.US, "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s&language=en-US", new Object[]{Double.valueOf(latLng.latitude), Double.valueOf(latLng.longitude), getString(R$string.maps_api_key)})).b()).o0(new h(latLng));
        }
    }

    public class h implements okhttp3.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LatLng c;

        h(LatLng latLng) {
            this.c = latLng;
        }

        public void onFailure(okhttp3.e call, IOException e) {
        }

        public void onResponse(okhttp3.e eVar, d0 response) {
            if (!PatchProxy.proxy(new Object[]{eVar, response}, this, changeQuickRedirect, false, 8160, new Class[]{okhttp3.e.class, d0.class}, Void.TYPE).isSupported) {
                try {
                    String s = response.a().string();
                    GoogleMapActivity googleMapActivity = GoogleMapActivity.this;
                    String unused = googleMapActivity.z4 = ((LinkedTreeMap) ((ArrayList) ((Map) new Gson().fromJson(s, HashMap.class)).get("results")).get(0)).get("formatted_address") + "";
                    GoogleMapActivity.k0(GoogleMapActivity.this, this.c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void v0(LatLng latLng) {
        if (!PatchProxy.proxy(new Object[]{latLng}, this, changeQuickRedirect, false, 8145, new Class[]{LatLng.class}, Void.TYPE).isSupported) {
            this.J4 = latLng;
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(this.z4);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R$drawable.trans_marker));
            Marker addMarker = this.A4.addMarker(markerOptions);
            this.F4 = addMarker;
            addMarker.setDraggable(true);
            this.F4.showInfoWindow();
            this.p3 = latLng.latitude;
            this.p4 = latLng.longitude;
            this.H4.setText(this.z4);
        }
    }

    private void l0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8146, new Class[0], Void.TYPE).isSupported) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                return;
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
                new AlertDialog.Builder(this).setTitle((CharSequence) "Location Permission Needed").setMessage((CharSequence) "This app needs the Location permission, please accept to use location functionality").setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new i()).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
            }
        }
    }

    public class i implements DialogInterface.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        @SensorsDataInstrumented
        public void onClick(DialogInterface dialogInterface, int i) {
            if (PatchProxy.proxy(new Object[]{dialogInterface, new Integer(i)}, this, changeQuickRedirect, false, 8161, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
                return;
            }
            int i2 = i;
            DialogInterface dialogInterface2 = dialogInterface;
            ActivityCompat.requestPermissions(GoogleMapActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
            SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] strArr, int[] grantResults) {
        if (!PatchProxy.proxy(new Object[]{new Integer(requestCode), strArr, grantResults}, this, changeQuickRedirect, false, 8147, new Class[]{Integer.TYPE, String[].class, int[].class}, Void.TYPE).isSupported) {
            switch (requestCode) {
                case 99:
                    if (grantResults.length <= 0 || grantResults[0] != 0) {
                        Toast.makeText(this, "permission denied", 1).show();
                        return;
                    } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                        this.B4.requestLocationUpdates(this.D4, this.M4, Looper.myLooper());
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public int O() {
        return R$layout.activity_google_map;
    }

    public void init() {
    }

    public void R() {
    }

    public boolean onMarkerClick(Marker marker) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{marker}, this, changeQuickRedirect, false, 8148, new Class[]{Marker.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.F4.isInfoWindowShown()) {
            this.F4.showInfoWindow();
        }
        return true;
    }

    public void onMarkerDragStart(Marker marker) {
    }

    public void onMarkerDrag(Marker marker) {
    }

    public void onMarkerDragEnd(Marker marker) {
        if (!PatchProxy.proxy(new Object[]{marker}, this, changeQuickRedirect, false, 8149, new Class[]{Marker.class}, Void.TYPE).isSupported) {
            LatLng latLng = marker.getPosition();
            Log.e("GoogleMapActivity", "onMarkerDragEnd: " + latLng + "==" + this.G4);
            if (latLng != null && this.G4 != null) {
                String addre = "";
                if (Geocoder.isPresent()) {
                    try {
                        List<Address> addresses = this.G4.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            if (address.getAdminArea() == null || address.getLocality() == null || address.getFeatureName() == null) {
                                addre = address.getAddressLine(0);
                            } else {
                                addre = address.getAdminArea() + address.getLocality() + address.getFeatureName();
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                Marker marker2 = this.F4;
                if (marker2 != null) {
                    marker2.remove();
                }
                this.z4 = addre;
                this.p3 = latLng.latitude;
                this.p4 = latLng.longitude;
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addre);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R$drawable.trans_marker));
                Marker addMarker = this.A4.addMarker(markerOptions);
                this.F4 = addMarker;
                addMarker.setDraggable(true);
                this.F4.showInfoWindow();
                this.H4.setText(addre);
            }
        }
    }
}
