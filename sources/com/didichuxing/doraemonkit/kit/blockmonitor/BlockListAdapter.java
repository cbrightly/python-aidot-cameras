package com.didichuxing.doraemonkit.kit.blockmonitor;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.blockmonitor.bean.BlockInfo;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class BlockListAdapter extends AbsRecyclerAdapter<AbsViewBinder<BlockInfo>, BlockInfo> {
    /* access modifiers changed from: private */
    public OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClick(BlockInfo blockInfo);
    }

    public BlockListAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<BlockInfo> createViewHolder(View view, int viewType) {
        return new ItemViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_block_list, parent, false);
    }

    public class ItemViewHolder extends AbsViewBinder<BlockInfo> {
        private TextView tvTime;
        private TextView tvTitle;

        public ItemViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.tvTime = (TextView) getView(R.id.time);
            this.tvTitle = (TextView) getView(R.id.title);
        }

        public void bind(BlockInfo blockInfoEx) {
        }

        public void bind(final BlockInfo info, int position) {
            this.tvTitle.setText(((BlockListAdapter.this.getItemCount() - position) + ". ") + info.concernStackString + " " + getContext().getString(R.string.dk_block_class_has_blocked, new Object[]{String.valueOf(info.timeCost)}));
            this.tvTime.setText(DateUtils.formatDateTime(getContext(), info.time, 17));
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    if (BlockListAdapter.this.mListener != null) {
                        BlockListAdapter.this.mListener.onClick(info);
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
