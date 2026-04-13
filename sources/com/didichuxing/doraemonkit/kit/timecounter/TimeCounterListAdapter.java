package com.didichuxing.doraemonkit.kit.timecounter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.kit.timecounter.bean.CounterInfo;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsRecyclerAdapter;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import net.sqlcipher.database.SQLiteDatabase;

public class TimeCounterListAdapter extends AbsRecyclerAdapter<AbsViewBinder<CounterInfo>, CounterInfo> {
    public TimeCounterListAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AbsViewBinder<CounterInfo> createViewHolder(View view, int viewType) {
        return new ItemViewHolder(view);
    }

    /* access modifiers changed from: protected */
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.dk_item_time_counter_list, parent, false);
    }

    public class ItemViewHolder extends AbsViewBinder<CounterInfo> {
        private TextView tvLaunch;
        private TextView tvOther;
        private TextView tvPause;
        private TextView tvRender;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvTotal;

        public ItemViewHolder(View view) {
            super(view);
        }

        /* access modifiers changed from: protected */
        public void getViews() {
            this.tvTime = (TextView) getView(R.id.time);
            this.tvTitle = (TextView) getView(R.id.title);
            this.tvTotal = (TextView) getView(R.id.total_cost);
            this.tvPause = (TextView) getView(R.id.pause_cost);
            this.tvLaunch = (TextView) getView(R.id.launch_cost);
            this.tvRender = (TextView) getView(R.id.render_cost);
            this.tvOther = (TextView) getView(R.id.other_cost);
        }

        public void bind(CounterInfo counterInfo) {
        }

        public void bind(final CounterInfo info, int position) {
            this.tvTitle.setText(info.title);
            this.tvTime.setText(DateUtils.formatDateTime(getContext(), info.time, 1));
            setTotalCost(info.totalCost);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    CounterInfo counterInfo = info;
                    counterInfo.show = !counterInfo.show;
                    ItemViewHolder.this.showDetail(counterInfo);
                    if (info.type == 0 && TimeCounterListAdapter.this.mContext != null) {
                        Intent intent = new Intent(TimeCounterListAdapter.this.mContext, UniversalActivity.class);
                        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                        intent.putExtra(BundleKey.FRAGMENT_INDEX, 28);
                        TimeCounterListAdapter.this.mContext.startActivity(intent);
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            showDetail(info);
        }

        private void setTotalCost(long cost) {
            TextView textView = this.tvTotal;
            textView.setText("Total Cost: " + cost + "ms");
            if (cost <= 500) {
                this.tvTotal.setTextColor(getContext().getResources().getColor(R.color.dk_color_48BB31));
            } else if (cost <= 1000) {
                this.tvTotal.setTextColor(getContext().getResources().getColor(R.color.dk_color_FAD337));
            } else {
                this.tvTotal.setTextColor(getContext().getResources().getColor(R.color.dk_color_FF0006));
            }
        }

        /* access modifiers changed from: private */
        public void showDetail(CounterInfo info) {
            if (info.type == 0) {
                info.show = false;
            }
            if (info.show) {
                this.tvPause.setVisibility(0);
                this.tvLaunch.setVisibility(0);
                this.tvRender.setVisibility(0);
                this.tvOther.setVisibility(0);
                TextView textView = this.tvPause;
                textView.setText("Pause Cost: " + info.pauseCost + "ms");
                TextView textView2 = this.tvLaunch;
                textView2.setText("Launch Cost: " + info.launchCost + "ms");
                TextView textView3 = this.tvRender;
                textView3.setText("Render Cost: " + info.renderCost + "ms");
                TextView textView4 = this.tvOther;
                textView4.setText("Other Cost: " + info.otherCost + "ms");
                return;
            }
            this.tvPause.setVisibility(8);
            this.tvLaunch.setVisibility(8);
            this.tvRender.setVisibility(8);
            this.tvOther.setVisibility(8);
        }
    }
}
