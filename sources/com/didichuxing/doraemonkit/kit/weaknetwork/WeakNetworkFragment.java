package com.didichuxing.doraemonkit.kit.weaknetwork;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class WeakNetworkFragment extends BaseFragment implements TextWatcher {
    /* access modifiers changed from: private */
    public AbsDokitView mNetWorkDokitView;
    private EditText mRequestSpeedView;
    private EditText mResponseSpeedView;
    private SettingItemAdapter mSettingItemAdapter;
    private RecyclerView mSettingList;
    private View mSpeedLimitView;
    private View mTimeoutOptionView;
    private EditText mTimeoutValueView;
    private View mWeakNetworkOptionView;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_weak_network;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((HomeTitleBar) findViewById(R.id.title_bar)).setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                WeakNetworkFragment.this.getActivity().finish();
            }
        });
        this.mWeakNetworkOptionView = findViewById(R.id.weak_network_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.setting_list);
        this.mSettingList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mSettingItemAdapter = settingItemAdapter;
        this.mSettingList.setAdapter(settingItemAdapter);
        this.mSettingItemAdapter.append(new SettingItem(R.string.dk_weak_network_switch, WeakNetworkManager.get().isActive()));
        this.mSettingItemAdapter.setOnSettingItemSwitchListener(new SettingItemAdapter.OnSettingItemSwitchListener() {
            public void onSettingItemSwitch(View view, SettingItem data, boolean on) {
                if (data.desc == R.string.dk_weak_network_switch) {
                    WeakNetworkFragment.this.setWeakNetworkEnabled(data.isChecked);
                }
            }
        });
        ((RadioGroup) findViewById(R.id.weak_network_option)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SensorsDataInstrumented
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedId = i;
                RadioGroup radioGroup2 = radioGroup;
                if (R.id.timeout == checkedId) {
                    WeakNetworkFragment.this.showTimeoutOptionView();
                } else if (R.id.speed_limit == checkedId) {
                    WeakNetworkFragment.this.showSpeedLimitOptionView();
                } else {
                    WeakNetworkFragment.this.showOffNetworkOptionView();
                }
                if (WeakNetworkFragment.this.mNetWorkDokitView == null) {
                    AbsDokitView unused = WeakNetworkFragment.this.mNetWorkDokitView = DokitViewManager.getInstance().getDokitView(WeakNetworkFragment.this.getActivity(), NetWokDokitView.class.getSimpleName());
                }
                if (WeakNetworkFragment.this.mNetWorkDokitView != null) {
                    WeakNetworkFragment.this.mNetWorkDokitView.onResume();
                }
                SensorsDataAutoTrackHelper.trackRadioGroup(radioGroup, i);
            }
        });
        this.mTimeoutOptionView = findViewById(R.id.layout_timeout_option);
        this.mSpeedLimitView = findViewById(R.id.layout_speed_limit);
        EditText editText = (EditText) findViewById(R.id.value_timeout);
        this.mTimeoutValueView = editText;
        editText.addTextChangedListener(this);
        EditText editText2 = (EditText) findViewById(R.id.request_speed);
        this.mRequestSpeedView = editText2;
        editText2.addTextChangedListener(this);
        EditText editText3 = (EditText) findViewById(R.id.response_speed);
        this.mResponseSpeedView = editText3;
        editText3.addTextChangedListener(this);
        updateUIState();
    }

    private void updateUIState() {
        int checkButtonId;
        boolean active = WeakNetworkManager.get().isActive();
        this.mWeakNetworkOptionView.setVisibility(active ? 0 : 8);
        if (active) {
            switch (WeakNetworkManager.get().getType()) {
                case 1:
                    checkButtonId = R.id.timeout;
                    break;
                case 2:
                    checkButtonId = R.id.speed_limit;
                    break;
                default:
                    checkButtonId = R.id.off_network;
                    break;
            }
            ((RadioButton) findViewById(checkButtonId)).setChecked(true);
            this.mTimeoutValueView.setHint(String.valueOf(WeakNetworkManager.get().getTimeOutMillis()));
            this.mRequestSpeedView.setHint(String.valueOf(WeakNetworkManager.get().getRequestSpeed()));
            this.mResponseSpeedView.setHint(String.valueOf(WeakNetworkManager.get().getResponseSpeed()));
        }
    }

    /* access modifiers changed from: private */
    public void setWeakNetworkEnabled(boolean enabled) {
        Class<NetWokDokitView> cls = NetWokDokitView.class;
        WeakNetworkManager.get().setActive(enabled);
        updateUIState();
        if (enabled) {
            DokitIntent dokitIntent = new DokitIntent(cls);
            dokitIntent.mode = 1;
            DokitViewManager.getInstance().attach(dokitIntent);
            return;
        }
        DokitViewManager.getInstance().detach((Class<? extends AbsDokitView>) cls);
    }

    /* access modifiers changed from: private */
    public void showTimeoutOptionView() {
        this.mTimeoutOptionView.setVisibility(0);
        this.mSpeedLimitView.setVisibility(8);
        WeakNetworkManager.get().setType(1);
    }

    /* access modifiers changed from: private */
    public void showSpeedLimitOptionView() {
        this.mSpeedLimitView.setVisibility(0);
        this.mTimeoutOptionView.setVisibility(8);
        WeakNetworkManager.get().setType(2);
    }

    /* access modifiers changed from: private */
    public void showOffNetworkOptionView() {
        this.mSpeedLimitView.setVisibility(8);
        this.mTimeoutOptionView.setVisibility(8);
        WeakNetworkManager.get().setType(0);
    }

    private long getLongValue(EditText editText) {
        CharSequence text = editText.getText();
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        return Long.parseLong(text.toString());
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        WeakNetworkManager.get().setParameter(getLongValue(this.mTimeoutValueView), getLongValue(this.mRequestSpeedView), getLongValue(this.mResponseSpeedView));
    }

    public void afterTextChanged(Editable s) {
    }
}
