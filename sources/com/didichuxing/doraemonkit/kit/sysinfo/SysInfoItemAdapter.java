package com.didichuxing.doraemonkit.kit.sysinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.blankj.utilcode.util.s;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import com.didichuxing.doraemonkit.widget.textview.LabelTextView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class SysInfoItemAdapter extends AbsRecyclerAdapter<AbsViewBinder<SysInfoItem>, SysInfoItem> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_TITLE = 1;

    public SysInfoItemAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<SysInfoItem> createViewHolder(View view, int viewType) {
        if (viewType == 1) {
            return new TitleItemViewHolder(view);
        }
        return new SysInfoItemViewHolder(view);
    }

    public int getItemViewType(int position) {
        if (getData().get(position) instanceof TitleItem) {
            return 1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return inflater.inflate(R.layout.dk_item_sys_title, parent, false);
        }
        return inflater.inflate(R.layout.dk_item_sys_info, parent, false);
    }

    public class SysInfoItemViewHolder extends AbsViewBinder<SysInfoItem> {
        private LabelTextView mLabelText;

        public SysInfoItemViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mLabelText = (LabelTextView) getView(R.id.label_text);
        }

        public void bind(final SysInfoItem sysInfoItem) {
            this.mLabelText.setLabel(sysInfoItem.name);
            this.mLabelText.setText(sysInfoItem.value);
            this.mLabelText.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (sysInfoItem.isPermission) {
                        s.b();
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
    }

    public class TitleItemViewHolder extends AbsViewBinder<SysInfoItem> {
        private TextView mTextView;

        public TitleItemViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.mTextView = (TextView) getView(R.id.tv_title);
        }

        public void bind(SysInfoItem sysInfoItem) {
            this.mTextView.setText(sysInfoItem.name);
        }
    }
}
