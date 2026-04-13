package com.didichuxing.doraemonkit.kit.layoutborder;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.LayoutBorderConfig;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;

public class LayoutBorderSettingFragment extends BaseFragment {
    private static final String TAG = "LayoutBorderSettingFragment";
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                LayoutBorderSettingFragment.this.finish();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.setting_list);
        this.mSettingList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mSettingItemAdapter = settingItemAdapter;
        settingItemAdapter.append(new SettingItem(R.string.dk_kit_layout_border, LayoutBorderConfig.isLayoutBorderOpen()));
        this.mSettingItemAdapter.append(new SettingItem(R.string.dk_layout_level, LayoutBorderConfig.isLayoutLevelOpen()));
        this.mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                Class<LayoutLevelDokitView> cls = LayoutLevelDokitView.class;
                int i = data.desc;
                if (i == R.string.dk_kit_layout_border) {
                    if (on) {
                        LayoutBorderManager.getInstance().start();
                    } else {
                        LayoutBorderManager.getInstance().stop();
                    }
                    LayoutBorderConfig.setLayoutBorderOpen(on);
                } else if (i == R.string.dk_layout_level) {
                    if (on) {
                        DokitIntent intent = new DokitIntent(cls);
                        intent.mode = 1;
                        DokitViewManager.getInstance().attach(intent);
                    } else {
                        DokitViewManager.getInstance().detach((Class<? extends AbsDokitView>) cls);
                    }
                    LayoutBorderConfig.setLayoutLevelOpen(on);
                }
            }
        });
        this.mSettingList.setAdapter(this.mSettingItemAdapter);
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_layout_border_setting;
    }
}
