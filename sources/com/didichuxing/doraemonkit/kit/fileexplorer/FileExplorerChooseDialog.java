package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.kit.core.SettingItemAdapter;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import java.io.File;

public class FileExplorerChooseDialog extends DialogProvider<File> {
    private SettingItemAdapter mAdapter;
    private RecyclerView mChooseList;
    /* access modifiers changed from: private */
    public OnButtonClickListener onButtonClickListener;

    public interface OnButtonClickListener {
        void onDeleteClick(FileExplorerChooseDialog fileExplorerChooseDialog);

        void onShareClick(FileExplorerChooseDialog fileExplorerChooseDialog);
    }

    public FileExplorerChooseDialog(File data, DialogListener listener) {
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
    public void bindData(File file) {
        if (file.isFile()) {
            this.mAdapter.append(new SettingItem(R.string.dk_share));
        }
        this.mAdapter.append(new SettingItem(R.string.dk_delete));
        this.mAdapter.setOnSettingItemClickListener(new SettingItemAdapter.OnSettingItemClickListener() {
            public void onSettingItemClick(View view, SettingItem data) {
                int i = data.desc;
                if (i == R.string.dk_delete) {
                    if (FileExplorerChooseDialog.this.onButtonClickListener != null) {
                        FileExplorerChooseDialog.this.onButtonClickListener.onDeleteClick(FileExplorerChooseDialog.this);
                    }
                } else if (i == R.string.dk_share && FileExplorerChooseDialog.this.onButtonClickListener != null) {
                    FileExplorerChooseDialog.this.onButtonClickListener.onShareClick(FileExplorerChooseDialog.this);
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
