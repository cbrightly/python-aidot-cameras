package com.didichuxing.doraemonkit.kit.performance.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.performance.datasource.IDataSource;

public class LineChart extends FrameLayout {
    private CardiogramView mLine;
    private TextView mTitle;
    private int performanceType;

    public int getPerformanceType() {
        return this.performanceType;
    }

    public void setPerformanceType(int performanceType2) {
        this.performanceType = performanceType2;
    }

    public LineChart(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LineChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        FrameLayout.inflate(context, R.layout.dk_view_line_chart, this);
        this.mTitle = (TextView) findViewById(R.id.tv_title);
        this.mLine = (CardiogramView) findViewById(R.id.line_chart_view);
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

    public void startMove() {
        this.mLine.startMove();
    }

    public void stopMove() {
        this.mLine.stopMove();
    }

    public void setInterval(int interval) {
        this.mLine.setInterval(interval);
    }

    public void setDataSource(@NonNull IDataSource dataSource) {
        this.mLine.setDataSource(dataSource);
    }
}
