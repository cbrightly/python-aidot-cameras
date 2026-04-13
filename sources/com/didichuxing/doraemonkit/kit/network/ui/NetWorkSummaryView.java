package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.utils.ByteUtil;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.didichuxing.doraemonkit.widget.chart.BarChart;
import com.didichuxing.doraemonkit.widget.chart.PieChart;
import java.util.ArrayList;
import java.util.List;

public class NetWorkSummaryView extends LinearLayout {
    public NetWorkSummaryView(Context context) {
        super(context);
        LinearLayout.inflate(context, R.layout.dk_fragment_network_summary_page, this);
        initView();
    }

    public NetWorkSummaryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LinearLayout.inflate(context, R.layout.dk_fragment_network_summary_page, this);
        initView();
    }

    private void initView() {
        TextView totalSec = (TextView) findViewById(R.id.total_sec);
        TextView totalNumber = (TextView) findViewById(R.id.total_number);
        TextView totalUpload = (TextView) findViewById(R.id.total_upload);
        int postCount = NetworkManager.get().getPostCount();
        int getCount = NetworkManager.get().getGetCount();
        int totalCount = NetworkManager.get().getTotalCount();
        totalNumber.setText(String.valueOf(totalCount));
        long time = NetworkManager.get().getRunningTime();
        totalSec.setText(CostTimeUtil.formatTime(getContext(), time));
        long requestSize = NetworkManager.get().getTotalRequestSize();
        long responseSize = NetworkManager.get().getTotalResponseSize();
        totalUpload.setText(ByteUtil.getPrintSizeForSpannable(requestSize));
        ((TextView) findViewById(R.id.total_down)).setText(ByteUtil.getPrintSizeForSpannable(responseSize));
        PieChart chart = (PieChart) findViewById(R.id.network_pier_chart);
        List<PieChart.PieData> data = new ArrayList<>();
        TextView textView = totalSec;
        Resources resource = getResources();
        if (postCount != 0) {
            TextView textView2 = totalNumber;
            TextView textView3 = totalUpload;
            int i = totalCount;
            long j = time;
            data.add(new PieChart.PieData(resource.getColor(R.color.dk_color_55A8FD), (long) postCount));
        } else {
            TextView textView4 = totalUpload;
            int i2 = totalCount;
            long j2 = time;
        }
        if (getCount != 0) {
            data.add(new PieChart.PieData(resource.getColor(R.color.dk_color_FAD337), (long) getCount));
        }
        chart.setData(data);
        ((BarChart) findViewById(R.id.network_bar_chart)).setData(postCount, getResources().getColor(R.color.dk_color_55A8FD), getCount, getResources().getColor(R.color.dk_color_FAD337));
    }
}
