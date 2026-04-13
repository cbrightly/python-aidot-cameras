package com.didichuxing.doraemonkit.kit.gpsmock;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.GpsMockConfig;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.model.LatLng;
import com.didichuxing.doraemonkit.util.WebUtil;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.didichuxing.doraemonkit.widget.webview.MyWebView;
import com.didichuxing.doraemonkit.widget.webview.MyWebViewClient;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;

public class GpsMockFragment extends BaseFragment implements SettingItemAdapter.OnSettingItemSwitchListener, MyWebViewClient.InvokeListener {
    private static final String TAG = "GpsMockFragment";
    private boolean isInit = true;
    /* access modifiers changed from: private */
    public EditText mEdLongLat;
    private ImageView mIvSearch;
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;
    private HomeTitleBar mTitleBar;
    /* access modifiers changed from: private */
    public MyWebView mWebView;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiSettingList();
        initTitleBar();
        initMockLocationArea();
        initWebView();
    }

    private void initWebView() {
        MyWebView myWebView = (MyWebView) findViewById(R.id.webview);
        this.mWebView = myWebView;
        WebUtil.webViewLoadLocalHtml(myWebView, "html/map.html");
        this.mWebView.addInvokeListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mWebView.removeInvokeListener(this);
    }

    private void initMockLocationArea() {
        this.mEdLongLat = (EditText) findViewById(R.id.ed_long_lat);
        ImageView imageView = (ImageView) findViewById(R.id.iv_search);
        this.mIvSearch = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (!GpsMockFragment.this.checkInput()) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                String[] longAndLat = GpsMockFragment.this.mEdLongLat.getText().toString().split(" ");
                try {
                    double longitude = Double.valueOf(longAndLat[0]).doubleValue();
                    double latitude = Double.valueOf(longAndLat[1]).doubleValue();
                    GpsMockManager.getInstance().mockLocation(latitude, longitude);
                    GpsMockConfig.saveMockLocation(new LatLng(latitude, longitude));
                    String url = String.format("javascript:updateLocation(%s,%s)", new Object[]{Double.valueOf(latitude), Double.valueOf(longitude)});
                    MyWebView access$200 = GpsMockFragment.this.mWebView;
                    access$200.loadUrl(url);
                    SensorsDataAutoTrackHelper.loadUrl2(access$200, url);
                    GpsMockFragment gpsMockFragment = GpsMockFragment.this;
                    int i = R.string.dk_gps_location_change_toast;
                    e0.n(gpsMockFragment.getString(i, "" + longitude, "" + latitude));
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                } catch (Exception e) {
                    e0.n("经纬度必须为数字");
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean checkInput() {
        String strLongLat = this.mEdLongLat.getText().toString();
        if (TextUtils.isEmpty(strLongLat)) {
            e0.n("请输入经纬度");
            return false;
        }
        String[] longAndLat = strLongLat.split(" ");
        if (longAndLat.length != 2) {
            e0.n("请输入符合规范的经纬度格式");
            return false;
        } else if (TextUtils.isEmpty(longAndLat[0]) || TextUtils.isEmpty(longAndLat[1])) {
            return false;
        } else {
            try {
                double longitude = Double.valueOf(longAndLat[0]).doubleValue();
                double latitude = Double.valueOf(longAndLat[1]).doubleValue();
                if (longitude > 180.0d || longitude < -180.0d || latitude > 90.0d || latitude < -90.0d) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                e0.n("经纬度必须为数字");
                return false;
            }
        }
    }

    private void initTitleBar() {
        HomeTitleBar homeTitleBar = (HomeTitleBar) findViewById(R.id.title_bar);
        this.mTitleBar = homeTitleBar;
        homeTitleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                GpsMockFragment.this.finish();
            }
        });
    }

    private void intiSettingList() {
        this.mSettingList = (RecyclerView) findViewById(R.id.setting_list);
        this.mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<SettingItem> settingItems = new ArrayList<>();
        settingItems.add(new SettingItem(R.string.dk_gpsmock_open, GpsMockConfig.isGPSMockOpen()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mSettingItemAdapter = settingItemAdapter;
        settingItemAdapter.setData(settingItems);
        this.mSettingItemAdapter.setOnSettingItemSwitchListener(this);
        this.mSettingList.setAdapter(this.mSettingItemAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        this.mSettingList.addItemDecoration(decoration);
    }

    public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
        if (data.desc == R.string.dk_gpsmock_open) {
            if (on) {
                GpsMockManager.getInstance().startMock();
            } else {
                GpsMockManager.getInstance().stopMock();
            }
            GpsMockConfig.setGPSMockOpen(on);
        }
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_gps_mock;
    }

    public void onNativeInvoke(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            if ("sendLocation".equals(uri.getLastPathSegment())) {
                String lat = uri.getQueryParameter("lat");
                String lnt = uri.getQueryParameter("lng");
                if (!TextUtils.isEmpty(lat) || !TextUtils.isEmpty(lnt)) {
                    this.mEdLongLat.setText(String.format("%s %s", new Object[]{lnt, lat}));
                    if (!this.isInit) {
                        try {
                            double longitude = Double.valueOf(lnt).doubleValue();
                            double latitude = Double.valueOf(lat).doubleValue();
                            GpsMockManager.getInstance().mockLocation(latitude, longitude);
                            GpsMockConfig.saveMockLocation(new LatLng(latitude, longitude));
                            int i = R.string.dk_gps_location_change_toast;
                            e0.n(getString(i, "" + longitude, "" + latitude));
                        } catch (Exception e) {
                            e0.n("经纬度必须为数字");
                            return;
                        }
                    }
                    this.isInit = false;
                }
            }
        }
    }
}
