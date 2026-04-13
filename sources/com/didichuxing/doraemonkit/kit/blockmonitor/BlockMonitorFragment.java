package com.didichuxing.doraemonkit.kit.blockmonitor;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.blockmonitor.core.BlockMonitorManager;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import meshsdk.cache.CacheHandler;

public class BlockMonitorFragment extends BaseFragment {
    public static final String KEY_JUMP_TO_LIST = "KEY_JUMP_TO_LIST";
    private static final String TAG = "BlockMonitorIndexFragment";

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_block_monitor_index;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                BlockMonitorFragment.this.getActivity().finish();
            }
        });
        RecyclerView mSettingList = (RecyclerView) findViewById(R.id.setting_list);
        mSettingList.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        mSettingList.setAdapter(settingItemAdapter);
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_switch, BlockMonitorManager.getInstance().isRunning()));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_goto_list));
        settingItemAdapter.append(new SettingItem(R.string.dk_item_block_mock));
        settingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc != R.string.dk_item_block_switch) {
                    return;
                }
                if (on) {
                    BlockMonitorManager.getInstance().start();
                } else {
                    BlockMonitorManager.getInstance().stop();
                }
            }
        });
        settingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
                int i = data.desc;
                if (i == R.string.dk_item_block_goto_list) {
                    BlockMonitorFragment.this.showContent(BlockListFragment.class);
                } else if (i == R.string.dk_item_block_mock) {
                    BlockMonitorFragment.this.mockBlock();
                }
            }
        });
        if (getArguments() != null && getArguments().getBoolean(KEY_JUMP_TO_LIST, false)) {
            showContent(BlockListFragment.class);
        }
    }

    /* access modifiers changed from: private */
    public void mockBlock() {
        try {
            getView().postDelayed(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(CacheHandler.delayTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
