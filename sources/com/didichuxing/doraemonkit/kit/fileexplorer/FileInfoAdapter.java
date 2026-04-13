package com.didichuxing.doraemonkit.kit.fileexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;

public class FileInfoAdapter extends AbsRecyclerAdapter<AbsViewBinder<FileInfo>, FileInfo> {
    /* access modifiers changed from: private */
    public OnViewClickListener mOnViewClickListener;
    /* access modifiers changed from: private */
    public OnViewLongClickListener mOnViewLongClickListener;

    public interface OnViewClickListener {
        void onViewClick(View view, FileInfo fileInfo);
    }

    public interface OnViewLongClickListener {
        boolean onViewLongClick(View view, FileInfo fileInfo);
    }

    public FileInfoAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<FileInfo> createViewHolder(View view, int viewType) {
        return new FileInfoViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_file_info, parent, false);
    }

    public class FileInfoViewHolder extends AbsViewBinder<FileInfo> {
        private ImageView mIcon;
        private ImageView mMoreBtn;
        private TextView mName;

        public FileInfoViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mName = (TextView) getView(R.id.name);
            this.mIcon = (ImageView) getView(R.id.icon);
            this.mMoreBtn = (ImageView) getView(R.id.more);
        }

        public void bind(final FileInfo fileInfo) {
            getView().setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    return FileInfoAdapter.this.mOnViewLongClickListener != null && FileInfoAdapter.this.mOnViewLongClickListener.onViewLongClick(v, fileInfo);
                }
            });
            this.mName.setText(fileInfo.file.getName());
            if (fileInfo.file.isDirectory()) {
                this.mIcon.setImageResource(R.mipmap.dk_dir_icon);
                this.mMoreBtn.setVisibility(0);
                return;
            }
            if (FileUtil.getSuffix(fileInfo.file).equals(FileUtil.JPG)) {
                this.mIcon.setImageResource(R.mipmap.dk_jpg_icon);
            } else if (FileUtil.getSuffix(fileInfo.file).equals(FileUtil.TXT)) {
                this.mIcon.setImageResource(R.mipmap.dk_txt_icon);
            } else if (FileUtil.getSuffix(fileInfo.file).equals(FileUtil.DB)) {
                this.mIcon.setImageResource(R.mipmap.dk_file_db);
            } else {
                this.mIcon.setImageResource(R.mipmap.dk_file_icon);
            }
            this.mMoreBtn.setVisibility(8);
        }

        /* access modifiers changed from: protected */
        public void onViewClick(View view, FileInfo data) {
            super.onViewClick(view, data);
            if (FileInfoAdapter.this.mOnViewClickListener != null) {
                FileInfoAdapter.this.mOnViewClickListener.onViewClick(view, data);
            }
        }
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.mOnViewClickListener = onViewClickListener;
    }

    public void setOnViewLongClickListener(OnViewLongClickListener onViewLongClickListener) {
        this.mOnViewLongClickListener = onViewLongClickListener;
    }
}
