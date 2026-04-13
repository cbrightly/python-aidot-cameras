package com.didichuxing.doraemonkit.kit.performance;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;

public class PolyLineAdapter extends RecyclerView.Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public List<PerformanceData> data;
    public boolean drawDiver;
    public int itemWidth;
    public int maxValue;
    public int minValue;
    /* access modifiers changed from: private */
    public OnViewClickListener onViewClickListener;
    public float pointSize;
    /* access modifiers changed from: private */
    public boolean showLatestLabel;
    public boolean touchable;

    public interface OnViewClickListener {
        void onViewClick(int i, PerformanceData performanceData);
    }

    private PolyLineAdapter() {
    }

    private PolyLineAdapter(List<PerformanceData> data2, int maxValue2, int minValue2, int itemWidth2) {
        this.data = data2;
        this.maxValue = maxValue2;
        this.minValue = minValue2;
        this.itemWidth = itemWidth2;
    }

    public void setData(List<PerformanceData> d) {
        List<PerformanceData> list = this.data;
        if (list != null) {
            list.clear();
            this.data.addAll(d);
            notifyDataSetChanged();
        }
    }

    public void addData(PerformanceData bean) {
        this.data.add(bean);
        notifyDataSetChanged();
    }

    public void addData(List<PerformanceData> beans) {
        this.data.addAll(beans);
        notifyDataSetChanged();
    }

    public static class Builder {
        private List<PerformanceData> data = new ArrayList();
        private boolean drawDiver = true;
        private final int itemWidth;
        private int maxValue = 100;
        private int minValue = 0;
        private float pointSize;
        private boolean showLatestLabel;
        private boolean touchable = true;

        public Builder(Context context, int itemNumber) {
            this.itemWidth = UIUtils.getWidthPixels() / itemNumber;
        }

        public Builder setMaxValue(int maxValue2) {
            this.maxValue = maxValue2;
            return this;
        }

        public Builder setMinValue(int minValue2) {
            this.minValue = minValue2;
            return this;
        }

        public Builder setData(List<PerformanceData> data2) {
            this.data = data2;
            return this;
        }

        public Builder setDrawDiver(boolean drawDiver2) {
            this.drawDiver = drawDiver2;
            return this;
        }

        public Builder setPointSize(float size) {
            this.pointSize = size;
            return this;
        }

        public PolyLineAdapter build() {
            PolyLineAdapter adapter = new PolyLineAdapter(this.data, this.maxValue, this.minValue, this.itemWidth);
            adapter.drawDiver = this.drawDiver;
            adapter.pointSize = this.pointSize;
            adapter.touchable = this.touchable;
            boolean unused = adapter.showLatestLabel = this.showLatestLabel;
            return adapter;
        }

        public Builder setTouchable(boolean touchable2) {
            this.touchable = touchable2;
            return this;
        }

        public Builder setShowLatestLabel(boolean showLatestLabel2) {
            this.showLatestLabel = showLatestLabel2;
            return this;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PolyLineItemView item = new PolyLineItemView(parent.getContext());
        item.setMinValue(this.minValue);
        item.setMaxValue(this.maxValue);
        item.setLayoutParams(new RecyclerView.LayoutParams(this.itemWidth, -1));
        return new ViewHolder(item);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    public int getItemCount() {
        return this.data.size();
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener2) {
        this.onViewClickListener = onViewClickListener2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PolyLineItemView item;

        public ViewHolder(View itemView) {
            super(itemView);
            PolyLineItemView polyLineItemView = (PolyLineItemView) itemView;
            this.item = polyLineItemView;
            polyLineItemView.setDrawDiver(PolyLineAdapter.this.drawDiver);
            this.item.setPointSize(PolyLineAdapter.this.pointSize);
            this.item.setTouchable(PolyLineAdapter.this.touchable);
        }

        public void bindData(final int position) {
            if (PolyLineAdapter.this.onViewClickListener != null) {
                this.item.setOnClickListener(new View.OnClickListener() {
                    @SensorsDataInstrumented
                    public void onClick(View view) {
                        View view2 = view;
                        PolyLineAdapter.this.onViewClickListener.onViewClick(position, (PerformanceData) PolyLineAdapter.this.data.get(position));
                        SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    }
                });
            }
            boolean z = false;
            if (position == 0) {
                this.item.setDrawLeftLine(false);
            } else {
                this.item.setDrawLeftLine(true);
                this.item.setlastValue(((PerformanceData) PolyLineAdapter.this.data.get(position - 1)).parameter);
            }
            this.item.setCurrentValue(((PerformanceData) PolyLineAdapter.this.data.get(position)).parameter);
            this.item.setLabel(((PerformanceData) PolyLineAdapter.this.data.get(position)).date);
            if (position == PolyLineAdapter.this.data.size() - 1) {
                this.item.setDrawRightLine(false);
            } else {
                this.item.setDrawRightLine(true);
                this.item.setNextValue(((PerformanceData) PolyLineAdapter.this.data.get(position + 1)).parameter);
            }
            PolyLineItemView polyLineItemView = this.item;
            if (PolyLineAdapter.this.showLatestLabel && position > PolyLineAdapter.this.data.size() - 3) {
                z = true;
            }
            polyLineItemView.showLabel(z);
        }
    }
}
