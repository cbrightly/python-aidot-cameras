package com.didichuxing.doraemonkit.kit.timecounter;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;

public class TimeCounterFragment extends BaseFragment {
    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_time_counter_index;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                TimeCounterFragment.this.getActivity().finish();
            }
        });
        RecyclerView mSettingList = (RecyclerView) findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        mSettingList.setAdapter(settingItemAdapter);
        settingItemAdapter.append(new SettingItem(R.string.dk_item_time_counter_switch, TimeCounterManager.get().isRunning()));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_time_goto_list));
        settingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc != R.string.dk_item_time_counter_switch) {
                    return;
                }
                if (on) {
                    TimeCounterManager.get().start();
                } else {
                    TimeCounterManager.get().stop();
                }
            }
        });
        settingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
                if (data.desc == R.string.dk_item_time_goto_list) {
                    TimeCounterFragment.this.showContent(TimeCounterListFragment.class);
                }
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
