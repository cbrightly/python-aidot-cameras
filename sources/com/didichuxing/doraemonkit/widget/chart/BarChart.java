package com.didichuxing.doraemonkit.widget.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;

public class BarChart extends LinearLayout {
    private View getValue;
    private View lineEnd;
    private TextView markFirst;
    private TextView markSecond;
    private TextView markThird;
    private View postValue;

    public BarChart(Context context) {
        super(context, (AttributeSet) null);
    }

    public BarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dk_item_bar_chart, this, true);
        this.markFirst = (TextView) inflate.findViewById(R.id.mark_first);
        this.markSecond = (TextView) inflate.findViewById(R.id.mark_second);
        this.markThird = (TextView) inflate.findViewById(R.id.mark_third);
        this.postValue = findViewById(R.id.post_value);
        this.getValue = findViewById(R.id.get_value);
        this.lineEnd = findViewById(R.id.solid_line_end);
    }

    public void setData(int postNumber, @ColorInt int postColor, int getNumber, @ColorInt int getColor) {
        int max = postNumber > getNumber ? postNumber : getNumber;
        int max2 = (max + 10) - (max % 10);
        float proportion = calculationProportion((float) max2);
        this.markFirst.setText("0");
        this.markThird.setText(String.valueOf(max2));
        this.markSecond.setText(String.valueOf(max2 / 2));
        this.postValue.setBackgroundColor(postColor);
        ViewGroup.LayoutParams layoutParams = this.postValue.getLayoutParams();
        layoutParams.width = (int) (((float) postNumber) * proportion);
        this.postValue.setLayoutParams(layoutParams);
        this.getValue.getLayoutParams().width = (int) (((float) getNumber) * proportion);
        this.getValue.setBackgroundColor(getColor);
    }

    private float calculationProportion(float max) {
        return ((float) ((RelativeLayout.LayoutParams) this.lineEnd.getLayoutParams()).leftMargin) / max;
    }
}
