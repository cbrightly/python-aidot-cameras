package com.didichuxing.doraemonkit.kit.timecounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.timecounter.bean.CounterInfo;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class TimeCounterDokitView extends AbsDokitView {
    private ImageView mClose;
    private TextView tvLaunch;
    private TextView tvOther;
    private TextView tvPause;
    private TextView tvRender;
    private TextView tvTitle;
    private TextView tvTotal;

    public void onCreate(Context context) {
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_time_counter, (ViewGroup) null);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.width = i;
        params.height = i;
        params.x = UIUtils.dp2px(30.0f);
        params.y = UIUtils.dp2px(30.0f);
    }

    public void onViewCreated(FrameLayout view) {
        initView();
    }

    private void initView() {
        this.tvTitle = (TextView) findViewById(R.id.title);
        this.tvTotal = (TextView) findViewById(R.id.total_cost);
        this.tvPause = (TextView) findViewById(R.id.pause_cost);
        this.tvLaunch = (TextView) findViewById(R.id.launch_cost);
        this.tvRender = (TextView) findViewById(R.id.render_cost);
        this.tvOther = (TextView) findViewById(R.id.other_cost);
        showInfo(TimeCounterManager.get().getAppSetupInfo());
        ImageView imageView = (ImageView) findViewById(R.id.close);
        this.mClose = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                TimeCounterManager.get().stop();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public void showInfo(CounterInfo info) {
        this.tvTitle.setText(info.title);
        setTotalCost(info.totalCost);
        if (info.type == 1) {
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

    private void showDetail(CounterInfo info) {
        if (info.type == 0) {
            info.show = false;
        }
        if (info.show) {
            this.tvPause.setVisibility(0);
            this.tvLaunch.setVisibility(0);
            this.tvRender.setVisibility(0);
            this.tvOther.setVisibility(0);
            return;
        }
        this.tvPause.setVisibility(8);
        this.tvLaunch.setVisibility(8);
        this.tvRender.setVisibility(8);
        this.tvOther.setVisibility(8);
    }

    public void onEnterForeground() {
        super.onEnterForeground();
    }

    public void onEnterBackground() {
        super.onEnterBackground();
        TimeCounterManager.get().onEnterBackground();
    }
}
