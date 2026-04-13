package com.didichuxing.doraemonkit.kit.network.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.i;
import com.blankj.utilcode.util.j;
import com.blankj.utilcode.util.k;
import com.blankj.utilcode.util.r;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.DokitMemoryConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.bean.WhiteHostBean;
import com.didichuxing.doraemonkit.kit.parameter.AbsParameterFragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NetWorkMonitorFragment extends AbsParameterFragment {
    WhiteHostAdapter mHostAdapter;
    List<WhiteHostBean> mHostBeans = new ArrayList();
    RecyclerView mHostRv;
    private String whiteHostPath = (r.d() + File.separator + "white_host.json");

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_net_monitor;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCustomView();
    }

    /* access modifiers changed from: protected */
    public int getTitle() {
        return R.string.dk_kit_net_monitor;
    }

    /* access modifiers changed from: protected */
    public int getPerformanceType() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public Collection<SettingItem> getSettingItems(List<SettingItem> list) {
        list.add(new SettingItem(R.string.dk_net_monitor_detection_switch, NetworkManager.isActive()));
        return list;
    }

    /* access modifiers changed from: protected */
    public SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener() {
        return new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (on) {
                    NetWorkMonitorFragment.this.startMonitor();
                } else {
                    NetWorkMonitorFragment.this.stopMonitor();
                }
                DokitMemoryConfig.NETWORK_STATUS = on;
            }
        };
    }

    /* access modifiers changed from: protected */
    public SettingItemAdapter.OnSettingItemClickListener getItemClickListener() {
        return new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
            }
        };
    }

    private void initCustomView() {
        findViewById(R.id.btn_net_summary).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                NetWorkMonitorFragment.this.showContent(NetWorkMainPagerFragment.class);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.host_list);
        this.mHostRv = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (DokitConstant.WHITE_HOSTS.isEmpty()) {
            String whiteHostArray = i.d(this.whiteHostPath);
            if (TextUtils.isEmpty(whiteHostArray)) {
                this.mHostBeans.add(new WhiteHostBean("", true));
            } else {
                this.mHostBeans = (List) k.e(whiteHostArray, k.h(WhiteHostBean.class));
                DokitConstant.WHITE_HOSTS.clear();
                DokitConstant.WHITE_HOSTS.addAll(this.mHostBeans);
            }
        } else {
            this.mHostBeans.addAll(DokitConstant.WHITE_HOSTS);
        }
        WhiteHostAdapter whiteHostAdapter = new WhiteHostAdapter(R.layout.dk_item_white_host, this.mHostBeans);
        this.mHostAdapter = whiteHostAdapter;
        this.mHostRv.setAdapter(whiteHostAdapter);
    }

    /* access modifiers changed from: private */
    public void startMonitor() {
        NetworkManager.get().startMonitor();
        openChartPage(R.string.dk_kit_net_monitor, 1);
    }

    /* access modifiers changed from: private */
    public void stopMonitor() {
        NetworkManager.get().stopMonitor();
        closeChartPage();
    }

    public void onDestroy() {
        super.onDestroy();
        List<WhiteHostBean> hostBeans = this.mHostAdapter.getData();
        if (hostBeans.size() != 1 || !TextUtils.isEmpty(hostBeans.get(0).getHost())) {
            DokitConstant.WHITE_HOSTS.clear();
            DokitConstant.WHITE_HOSTS.addAll(hostBeans);
            String hostArray = k.j(hostBeans);
            j.e(this.whiteHostPath);
            i.f(this.whiteHostPath, hostArray);
            return;
        }
        DokitConstant.WHITE_HOSTS.clear();
        j.e(this.whiteHostPath);
    }
}
