package com.didichuxing.doraemonkit.kit.webdoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;

public class WebDoorHistoryAdapter extends AbsRecyclerAdapter<AbsViewBinder<String>, String> {
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, String str);
    }

    public WebDoorHistoryAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<String> createViewHolder(View view, int viewType) {
        return new WebDoorHistoryViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_web_door_history, parent, false);
    }

    public class WebDoorHistoryViewHolder extends AbsViewBinder<String> {
        private TextView mContent;

        public WebDoorHistoryViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mContent = (TextView) getView(R.id.content);
        }

        public void bind(String s) {
            this.mContent.setText(s);
        }

        /* access modifiers changed from: protected */
        public void onViewClick(View view, String data) {
            super.onViewClick(view, data);
            if (WebDoorHistoryAdapter.this.mOnItemClickListener != null) {
                WebDoorHistoryAdapter.this.mOnItemClickListener.onItemClick(view, data);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
