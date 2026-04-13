package com.didichuxing.doraemonkit.kit.alignruler;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.AlignRulerConfig;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;

public class AlignRulerSettingFragment extends BaseFragment {
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;
    private HomeTitleBar mTitleBar;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleBar();
    }

    private void initTitleBar() {
        HomeTitleBar homeTitleBar = (HomeTitleBar) findViewById(R.id.title_bar);
        this.mTitleBar = homeTitleBar;
        homeTitleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                AlignRulerSettingFragment.this.finish();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.setting_list);
        this.mSettingList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mSettingItemAdapter = settingItemAdapter;
        settingItemAdapter.append(new SettingItem(R.string.dk_kit_align_ruler, AlignRulerConfig.isAlignRulerOpen()));
        this.mSettingList.setAdapter(this.mSettingItemAdapter);
        this.mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                Class<AlignRulerLineDokitView> cls = AlignRulerLineDokitView.class;
                Class<AlignRulerMarkerDokitView> cls2 = AlignRulerMarkerDokitView.class;
                if (data.desc == R.string.dk_kit_align_ruler) {
                    if (on) {
                        DokitViewManager.getInstance().attach(new DokitIntent(cls2));
                        DokitViewManager.getInstance().attach(new DokitIntent(cls));
                    } else {
                        DokitViewManager.getInstance().detach((Class<? extends AbsDokitView>) cls2);
                        DokitViewManager.getInstance().detach((Class<? extends AbsDokitView>) cls);
                    }
                    AlignRulerConfig.setAlignRulerOpen(on);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_align_ruler_setting;
    }
}
