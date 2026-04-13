package com.didichuxing.doraemonkit.kit.crash;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.FormatUtil;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;

public class CrashHistoryAdapter extends AbsRecyclerAdapter<AbsViewBinder<CrashInfo>, CrashInfo> {
    public CrashHistoryAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<CrashInfo> createViewHolder(View view, int viewType) {
        return new CrashHistoryViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_crash_history, parent, false);
    }

    public static class CrashHistoryViewHolder extends AbsViewBinder<CrashInfo> {
        private TextView mContent;
        private TextView mTime;

        public CrashHistoryViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mContent = (TextView) getView(R.id.content);
            this.mTime = (TextView) getView(R.id.time);
        }

        public void bind(CrashInfo info) {
            this.mContent.setText(Log.getStackTraceString(info.tr));
            this.mTime.setText(FormatUtil.format(info.time));
        }
    }
}
