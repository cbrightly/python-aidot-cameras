package com.didichuxing.doraemonkit.kit.loginfo;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;

public class LogExportDialog extends DialogProvider<Object> {
    private SettingItemAdapter mAdapter;
    private RecyclerView mChooseList;
    /* access modifiers changed from: private */
    public OnButtonClickListener onButtonClickListener;

    public interface OnButtonClickListener {
        void onSaveClick(LogExportDialog logExportDialog);

        void onShareClick(LogExportDialog logExportDialog);
    }

    public LogExportDialog(Object data, DialogListener listener) {
        super(data, listener);
    }

    public int getLayoutId() {
        return R.layout.dk_dialog_file_explorer_choose;
    }

    /* access modifiers changed from: protected */
    public void findViews(View view) {
        this.mChooseList = (RecyclerView) view.findViewById(R.id.choose_list);
        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getContext());
        this.mAdapter = settingItemAdapter;
        this.mChooseList.setAdapter(settingItemAdapter);
        this.mChooseList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /* access modifiers changed from: protected */
    public void bindData(Object file) {
        this.mAdapter.append(new SettingItem(R.string.dk_save));
        this.mAdapter.append(new SettingItem(R.string.dk_share));
        this.mAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
                int i = data.desc;
                if (i == R.string.dk_save) {
                    if (LogExportDialog.this.onButtonClickListener != null) {
                        LogExportDialog.this.onButtonClickListener.onSaveClick(LogExportDialog.this);
                    }
                } else if (i == R.string.dk_share && LogExportDialog.this.onButtonClickListener != null) {
                    LogExportDialog.this.onButtonClickListener.onShareClick(LogExportDialog.this);
                }
            }
        });
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener2) {
        this.onButtonClickListener = onButtonClickListener2;
    }

    public boolean isCancellable() {
        return false;
    }
}
