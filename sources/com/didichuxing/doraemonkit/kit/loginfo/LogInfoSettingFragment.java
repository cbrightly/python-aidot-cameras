package com.didichuxing.doraemonkit.kit.loginfo;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.LogInfoConfig;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;

public class LogInfoSettingFragment extends BaseFragment {
    private static final String TAG = "LogInfoSettingFragment";
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                LogInfoSettingFragment.this.finish();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.setting_list);
        this.mSettingList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mSettingItemAdapter = settingItemAdapter;
        settingItemAdapter.append(new SettingItem(R.string.dk_kit_log_info, LogInfoConfig.isLogInfoOpen()));
        this.mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                Class<LogInfoDokitView> cls = LogInfoDokitView.class;
                if (data.desc == R.string.dk_kit_log_info) {
                    if (on) {
                        DokitIntent intent = new DokitIntent(cls);
                        intent.mode = 1;
                        DokitViewManager.getInstance().attach(intent);
                        LogInfoManager.getInstance().start();
                    } else {
                        DokitViewManager.getInstance().detach((Class<? extends AbsDokitView>) cls);
                        LogInfoManager.getInstance().stop();
                        LogInfoManager.getInstance().removeListener();
                    }
                    LogInfoConfig.setLogInfoOpen(on);
                }
            }
        });
        this.mSettingList.setAdapter(this.mSettingItemAdapter);
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_log_info_setting;
    }
}
