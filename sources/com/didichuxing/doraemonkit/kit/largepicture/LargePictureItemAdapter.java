package com.didichuxing.doraemonkit.kit.largepicture;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class LargePictureItemAdapter extends AbsRecyclerAdapter<AbsViewBinder<SettingItem>, SettingItem> {
    /* access modifiers changed from: private */
    public OnSettingItemClickListener mOnSettingItemClickListener;
    /* access modifiers changed from: private */
    public OnSettingItemSwitchListener mOnSettingItemSwitchListener;

    public interface OnSettingItemClickListener {
        void onSettingItemClick(View view, SettingItem settingItem);
    }

    public interface OnSettingItemSwitchListener {
        void onSettingItemSwitch(View view, SettingItem settingItem, boolean z);
    }

    public LargePictureItemAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<SettingItem> createViewHolder(View view, int viewType) {
        return new SettingItemViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_setting, parent, false);
    }

    public class SettingItemViewHolder extends AbsViewBinder<SettingItem> {
        private TextView mDesc;
        private ImageView mIcon;
        /* access modifiers changed from: private */
        public CheckBox mMenuSwitch;
        private TextView mRightDesc;

        public SettingItemViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mMenuSwitch = (CheckBox) getView(R.id.menu_switch);
            this.mDesc = (TextView) getView(R.id.desc);
            this.mIcon = (ImageView) getView(R.id.right_icon);
            this.mRightDesc = (TextView) getView(R.id.right_desc);
        }

        public void bind(final SettingItem settingItem) {
            this.mDesc.setText(settingItem.desc);
            if (settingItem.canCheck) {
                this.mMenuSwitch.setVisibility(0);
                this.mMenuSwitch.setChecked(settingItem.isChecked);
                this.mMenuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SensorsDataInstrumented
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        CompoundButton compoundButton2 = compoundButton;
                        settingItem.isChecked = isChecked;
                        LargePictureItemAdapter.this.mOnSettingItemSwitchListener.onSettingItemSwitch(SettingItemViewHolder.this.mMenuSwitch, settingItem, isChecked);
                        SensorsDataAutoTrackHelper.trackViewOnClick(compoundButton);
                    }
                });
            }
            if (settingItem.icon != 0) {
                this.mIcon.setVisibility(0);
                this.mIcon.setImageResource(settingItem.icon);
            }
            if (!TextUtils.isEmpty(settingItem.rightDesc)) {
                this.mRightDesc.setVisibility(0);
                this.mRightDesc.setText(settingItem.rightDesc);
            }
        }

        /* access modifiers changed from: protected */
        public void onViewClick(View view, SettingItem data) {
            super.onViewClick(view, data);
            if (LargePictureItemAdapter.this.mOnSettingItemClickListener != null) {
                LargePictureItemAdapter.this.mOnSettingItemClickListener.onSettingItemClick(view, data);
            }
        }
    }

    public void setOnSettingItemClickListener(OnSettingItemClickListener onSettingItemClickListener) {
        this.mOnSettingItemClickListener = onSettingItemClickListener;
    }

    public void setOnSettingItemSwitchListener(OnSettingItemSwitchListener onSettingItemSwitchListener) {
        this.mOnSettingItemSwitchListener = onSettingItemSwitchListener;
    }
}
