package com.didichuxing.doraemonkit.kit.crash;

import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.CrashCaptureConfig;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.kit.fileexplorer.FileExplorerFragment;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;

public class CrashCaptureMainFragment extends BaseFragment {
    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_crash_capture_main;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
    }

    private void initview() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                CrashCaptureMainFragment.this.finish();
            }
        });
        RecyclerView settingList = (RecyclerView) findViewById(R.id.setting_list);
        settingList.setLayoutManager(new LinearLayoutManager(getContext()));
        final SettingItemAdapter mSettingItemAdapter = new SettingItemAdapter(getContext());
        mSettingItemAdapter.append(new SettingItem(R.string.dk_crash_capture_switch, CrashCaptureConfig.isCrashCaptureOpen()));
        mSettingItemAdapter.append(new SettingItem(R.string.dk_crash_capture_look, R.mipmap.dk_more_icon));
        SettingItem item = new SettingItem(R.string.dk_crash_capture_clean_data);
        item.rightDesc = Formatter.formatFileSize(getContext(), FileUtil.getDirectorySize(CrashCaptureManager.getInstance().getCrashCacheDir()));
        mSettingItemAdapter.append(item);
        mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_crash_capture_switch) {
                    CrashCaptureConfig.setCrashCaptureOpen(on);
                    if (on) {
                        CrashCaptureManager.getInstance().start();
                    } else {
                        CrashCaptureManager.getInstance().stop();
                    }
                }
            }
        });
        mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
                int i = data.desc;
                if (i == R.string.dk_crash_capture_look) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BundleKey.DIR_KEY, CrashCaptureManager.getInstance().getCrashCacheDir());
                    CrashCaptureMainFragment.this.showContent(FileExplorerFragment.class, bundle);
                    return;
                }
                int i2 = R.string.dk_crash_capture_clean_data;
                if (i == i2) {
                    CrashCaptureManager.getInstance().clearCacheHistory();
                    data.rightDesc = Formatter.formatFileSize(CrashCaptureMainFragment.this.getContext(), FileUtil.getDirectorySize(CrashCaptureManager.getInstance().getCrashCacheDir()));
                    mSettingItemAdapter.notifyDataSetChanged();
                    CrashCaptureMainFragment.this.showToast(i2);
                }
            }
        });
        settingList.setAdapter(mSettingItemAdapter);
    }
}
