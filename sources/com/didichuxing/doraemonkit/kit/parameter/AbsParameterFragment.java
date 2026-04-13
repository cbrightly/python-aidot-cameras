package com.didichuxing.doraemonkit.kit.parameter;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.kit.performance.PerformanceDokitViewManager;
import com.didichuxing.doraemonkit.kit.performance.PerformanceFragmentCloseListener;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbsParameterFragment extends BaseFragment implements PerformanceFragmentCloseListener {
    /* access modifiers changed from: private */
    public static final String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;

    /* access modifiers changed from: protected */
    public abstract SettingItemAdapter.OnSettingItemClickListener getItemClickListener();

    /* access modifiers changed from: protected */
    public abstract SettingItemAdapter.OnSettingItemSwitchListener getItemSwitchListener();

    /* access modifiers changed from: protected */
    public abstract int getPerformanceType();

    /* access modifiers changed from: protected */
    public abstract Collection<SettingItem> getSettingItems(List<SettingItem> list);

    /* access modifiers changed from: protected */
    @StringRes
    public abstract int getTitle();

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_parameter;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /* access modifiers changed from: protected */
    public void openChartPage(@StringRes int title, int type) {
        PerformanceDokitViewManager.open(type, getString(title), this);
    }

    /* access modifiers changed from: protected */
    public void closeChartPage() {
        PerformanceDokitViewManager.close(getPerformanceType(), getString(getTitle()));
    }

    private void initView() {
        HomeTitleBar titleBar = (HomeTitleBar) findViewById(R.id.title_bar);
        titleBar.setTitle(getTitle());
        titleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                AbsParameterFragment.this.getActivity().finish();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.setting_list);
        this.mSettingList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mSettingItemAdapter = settingItemAdapter;
        settingItemAdapter.append(getSettingItems(new ArrayList()));
        this.mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (!on || AbsParameterFragment.this.ownPermissionCheck()) {
                    SettingItemAdapter.OnSettingItemSwitchListener itemSwitchListener = AbsParameterFragment.this.getItemSwitchListener();
                    if (itemSwitchListener != null) {
                        itemSwitchListener.onSettingItemSwitch(view, data, on);
                        return;
                    }
                    return;
                }
                if (view instanceof CheckBox) {
                    ((CheckBox) view).setChecked(false);
                }
                AbsParameterFragment.this.requestPermissions(AbsParameterFragment.PERMISSIONS_STORAGE, 2);
            }
        });
        this.mSettingItemAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
                if (!AbsParameterFragment.this.ownPermissionCheck()) {
                    AbsParameterFragment.this.requestPermissions(AbsParameterFragment.PERMISSIONS_STORAGE, 2);
                    return;
                }
                SettingItemAdapter.OnSettingItemClickListener itemClickListener = AbsParameterFragment.this.getItemClickListener();
                if (itemClickListener != null) {
                    itemClickListener.onSettingItemClick(view, data);
                }
            }
        });
        this.mSettingList.setAdapter(this.mSettingItemAdapter);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    showToast(R.string.dk_error_tips_permissions_less);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* access modifiers changed from: private */
    public boolean ownPermissionCheck() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    public void onClose(int performanceType) {
        RecyclerView recyclerView;
        SettingItemAdapter settingItemAdapter;
        if (performanceType == getPerformanceType() && (recyclerView = this.mSettingList) != null && !recyclerView.isComputingLayout() && (settingItemAdapter = this.mSettingItemAdapter) != null && ((SettingItem) settingItemAdapter.getData().get(0)).isChecked) {
            ((SettingItem) this.mSettingItemAdapter.getData().get(0)).isChecked = false;
            this.mSettingItemAdapter.notifyItemChanged(0);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mSettingItemAdapter = null;
    }

    public void onDestroy() {
        super.onDestroy();
        PerformanceDokitViewManager.onPerformanceSettingFragmentDestroy(this);
    }
}
