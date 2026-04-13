package com.didichuxing.doraemonkit.kit.parameter.cpu;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.DokitMemoryConfig;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.kit.parameter.AbsParameterFragment;
import com.didichuxing.doraemonkit.kit.performance.PerformanceDataManager;
import com.didichuxing.doraemonkit.kit.performance.PerformanceFragment;
import java.util.Collection;
import java.util.List;

public class CpuMainPageFragment extends AbsParameterFragment {
    private static final String TAG = "CpuMainPageFragment";

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerformanceDataManager.getInstance().init();
    }

    /* access modifiers changed from: protected */
    public int getTitle() {
        return R.string.dk_frameinfo_cpu;
    }

    /* access modifiers changed from: protected */
    public int getPerformanceType() {
        return 2;
    }

    /* access modifiers changed from: protected */
    public Collection<SettingItem> getSettingItems(List<SettingItem> list) {
        list.add(new SettingItem(R.string.dk_cpu_detection_switch, DokitMemoryConfig.CPU_STATUS));
        return list;
    }

    /* access modifiers changed from: protected */
    public SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener() {
        return new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (on) {
                    CpuMainPageFragment.this.startMonitor();
                } else {
                    CpuMainPageFragment.this.stopMonitor();
                }
                DokitMemoryConfig.CPU_STATUS = on;
            }
        };
    }

    /* access modifiers changed from: protected */
    public SettingItemAdapter.OnSettingItemClickListener getItemClickListener() {
        return new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_cache_log) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleKey.PERFORMANCE_TYPE, 0);
                    CpuMainPageFragment.this.showContent(PerformanceFragment.class, bundle);
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void startMonitor() {
        PerformanceDataManager.getInstance().startMonitorCPUInfo();
        openChartPage(R.string.dk_frameinfo_cpu, 2);
    }

    /* access modifiers changed from: private */
    public void stopMonitor() {
        PerformanceDataManager.getInstance().stopMonitorCPUInfo();
        closeChartPage();
    }
}
