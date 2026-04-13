package com.didichuxing.doraemonkit.kit.sysinfo;

import android.annotation.TargetApi;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.d;
import com.blankj.utilcode.util.g;
import com.blankj.utilcode.util.t;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.DeviceUtils;
import com.didichuxing.doraemonkit.util.ExecutorUtil;
import com.didichuxing.doraemonkit.util.PermissionUtil;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.google.maps.android.BuildConfig;
import java.util.ArrayList;
import java.util.List;

public class SysInfoFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public SysInfoItemAdapter mInfoItemAdapter;
    private RecyclerView mInfoList;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_sys_info;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initView();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        this.mInfoList = (RecyclerView) findViewById(R.id.info_list);
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                SysInfoFragment.this.getActivity().finish();
            }
        });
        this.mInfoList.setLayoutManager(new LinearLayoutManager(getContext()));
        SysInfoItemAdapter sysInfoItemAdapter = new SysInfoItemAdapter(getContext());
        this.mInfoItemAdapter = sysInfoItemAdapter;
        this.mInfoList.setAdapter(sysInfoItemAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        this.mInfoList.addItemDecoration(decoration);
    }

    private void initData() {
        List<SysInfoItem> sysInfoItems = new ArrayList<>();
        addAppData(sysInfoItems);
        addDeviceData(sysInfoItems);
        if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
            addPermissionData(sysInfoItems);
        } else {
            addPermissionDataUnreliable();
        }
        this.mInfoItemAdapter.setData(sysInfoItems);
    }

    private void addAppData(List<SysInfoItem> sysInfoItems) {
        PackageInfo pi = DeviceUtils.getPackageInfo(getContext());
        sysInfoItems.add(new TitleItem(getString(R.string.dk_sysinfo_app_info)));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_name), pi.packageName));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_version_name), pi.versionName));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_version_code), String.valueOf(pi.versionCode)));
        if (Build.VERSION.SDK_INT >= 24) {
            sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_min_sdk), String.valueOf(getContext().getApplicationInfo().minSdkVersion)));
        }
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_package_target_sdk), String.valueOf(getContext().getApplicationInfo().targetSdkVersion)));
    }

    private void addDeviceData(List<SysInfoItem> sysInfoItems) {
        String str = BuildConfig.TRAVIS;
        sysInfoItems.add(new TitleItem(getString(R.string.dk_sysinfo_device_info)));
        String string = getString(R.string.dk_sysinfo_brand_and_model);
        sysInfoItems.add(new SysInfoItem(string, Build.MANUFACTURER + " " + Build.MODEL));
        String string2 = getString(R.string.dk_sysinfo_android_version);
        sysInfoItems.add(new SysInfoItem(string2, Build.VERSION.RELEASE + " (" + Build.VERSION.SDK_INT + ")"));
        try {
            sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_ext_storage_free), DeviceUtils.getSDCardSpace(getContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_rom_free), DeviceUtils.getRomSpace(getContext())));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            String string3 = getString(R.string.dk_sysinfo_display_size);
            sysInfoItems.add(new SysInfoItem(string3, UIUtils.getWidthPixels() + "x" + UIUtils.getRealHeightPixels()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            String string4 = getString(R.string.dk_sysinfo_display_inch);
            sysInfoItems.add(new SysInfoItem(string4, "" + UIUtils.getScreenInch(getActivity())));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem("ROOT", String.valueOf(g.r())));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem("DENSITY", String.valueOf(UIUtils.getDensity())));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem("IP", TextUtils.isEmpty(NetworkUtils.b(true)) ? str : NetworkUtils.b(true)));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem("Mac", TextUtils.isEmpty(g.c()) ? str : g.c()));
        } catch (Exception e8) {
            e8.printStackTrace();
        }
        try {
            if (!TextUtils.isEmpty(t.a())) {
                str = t.a();
            }
            sysInfoItems.add(new SysInfoItem("IMEI", str));
        } catch (Exception e9) {
            e9.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem("Sign MD5", d.f()));
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem("Sign SHA1", d.h()));
        } catch (Exception e11) {
            e11.printStackTrace();
        }
        try {
            sysInfoItems.add(new SysInfoItem("Sign SHA256", d.j()));
        } catch (Exception e12) {
            e12.printStackTrace();
        }
    }

    private void addPermissionDataUnreliable() {
        ExecutorUtil.execute(new Runnable() {
            public void run() {
                String str;
                String str2;
                String str3;
                String str4;
                final List<SysInfoItem> list = new ArrayList<>();
                list.add(new TitleItem(SysInfoFragment.this.getString(R.string.dk_sysinfo_permission_info_unreliable)));
                String str5 = "YES";
                list.add(new SysInfoItem(SysInfoFragment.this.getString(R.string.dk_sysinfo_permission_location), PermissionUtil.checkLocationUnreliable(SysInfoFragment.this.getContext()) ? str5 : "NO", true));
                String string = SysInfoFragment.this.getString(R.string.dk_sysinfo_permission_sdcard);
                if (PermissionUtil.checkStorageUnreliable()) {
                    str = str5;
                } else {
                    str = "NO";
                }
                list.add(new SysInfoItem(string, str, true));
                String string2 = SysInfoFragment.this.getString(R.string.dk_sysinfo_permission_camera);
                if (PermissionUtil.checkCameraUnreliable()) {
                    str2 = str5;
                } else {
                    str2 = "NO";
                }
                list.add(new SysInfoItem(string2, str2, true));
                String string3 = SysInfoFragment.this.getString(R.string.dk_sysinfo_permission_record);
                if (PermissionUtil.checkRecordUnreliable()) {
                    str3 = str5;
                } else {
                    str3 = "NO";
                }
                list.add(new SysInfoItem(string3, str3, true));
                String string4 = SysInfoFragment.this.getString(R.string.dk_sysinfo_permission_read_phone);
                if (PermissionUtil.checkReadPhoneUnreliable(SysInfoFragment.this.getContext())) {
                    str4 = str5;
                } else {
                    str4 = "NO";
                }
                list.add(new SysInfoItem(string4, str4, true));
                String string5 = SysInfoFragment.this.getString(R.string.dk_sysinfo_permission_contact);
                if (!PermissionUtil.checkReadContactUnreliable(SysInfoFragment.this.getContext())) {
                    str5 = "NO";
                }
                list.add(new SysInfoItem(string5, str5, true));
                SysInfoFragment.this.getView().post(new Runnable() {
                    public void run() {
                        if (!SysInfoFragment.this.isDetached()) {
                            SysInfoFragment.this.mInfoItemAdapter.append(list);
                        }
                    }
                });
            }
        });
    }

    @TargetApi(23)
    private void addPermissionData(List<SysInfoItem> sysInfoItems) {
        sysInfoItems.add(new TitleItem(getString(R.string.dk_sysinfo_permission_info)));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_location), checkPermission("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"), true));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_sdcard), checkPermission("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"), true));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_camera), checkPermission("android.permission.CAMERA"), true));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_record), checkPermission("android.permission.RECORD_AUDIO"), true));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_read_phone), checkPermission("android.permission.READ_PHONE_STATE"), true));
        sysInfoItems.add(new SysInfoItem(getString(R.string.dk_sysinfo_permission_contact), checkPermission("android.permission.READ_CONTACTS"), true));
    }

    private String checkPermission(String... perms) {
        try {
            return PermissionUtil.hasPermissions(getContext(), perms) ? "YES" : "NO";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "NO";
        }
    }
}
