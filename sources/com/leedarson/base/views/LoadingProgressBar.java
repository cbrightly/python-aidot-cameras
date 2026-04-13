package com.leedarson.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.leedarson.module_base.R$styleable;

public class LoadingProgressBar extends ProgressBar {
    int c = -1;

    public LoadingProgressBar(Context context) {
        super(context);
    }

    public LoadingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context.obtainStyledAttributes(attrs, R$styleable.LoadingProgressBar).getColor(R$styleable.LoadingProgressBar_lpb_color, -1);
        h threeCircle = new h();
        threeCircle.setColor(this.c);
        setIndeterminateDrawable(threeCircle);
    }

    public LoadingProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
