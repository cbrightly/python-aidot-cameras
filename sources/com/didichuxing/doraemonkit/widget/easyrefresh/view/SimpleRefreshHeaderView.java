package com.didichuxing.doraemonkit.widget.easyrefresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.easyrefresh.IRefreshHeader;
import com.didichuxing.doraemonkit.widget.easyrefresh.State;

public class SimpleRefreshHeaderView extends FrameLayout implements IRefreshHeader {
    private View arrowIcon;
    private View loadingIcon;
    private Animation rotate_down;
    private Animation rotate_infinite;
    private Animation rotate_up;
    private View successIcon;
    private TextView textView;

    public SimpleRefreshHeaderView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SimpleRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rotate_up = AnimationUtils.loadAnimation(context, R.anim.dk_easy_refresh_rotate_up);
        this.rotate_down = AnimationUtils.loadAnimation(context, R.anim.dk_easy_refresh_rotate_down);
        this.rotate_infinite = AnimationUtils.loadAnimation(context, R.anim.dk_easy_refresh_rotate_infinite);
        FrameLayout.inflate(context, R.layout.dk_refresh_default_refresh_header, this);
        this.textView = (TextView) findViewById(R.id.text);
        this.arrowIcon = findViewById(R.id.arrowIcon);
        this.successIcon = findViewById(R.id.successIcon);
        this.loadingIcon = findViewById(R.id.loadingIcon);
    }

    public void reset() {
        this.textView.setText(getResources().getText(R.string.dk_header_reset));
        this.successIcon.setVisibility(4);
        this.arrowIcon.setVisibility(0);
        this.arrowIcon.clearAnimation();
        this.loadingIcon.setVisibility(4);
        this.loadingIcon.clearAnimation();
    }

    public void pull() {
    }

    public void refreshing() {
        this.arrowIcon.setVisibility(4);
        this.loadingIcon.setVisibility(0);
        this.textView.setText(getResources().getText(R.string.dk_header_refreshing));
        this.arrowIcon.clearAnimation();
        this.loadingIcon.startAnimation(this.rotate_infinite);
    }

    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
        if (currentPos < refreshPos && lastPos >= refreshPos) {
            Log.i("", ">>>>up");
            if (isTouch && state == State.PULL) {
                this.textView.setText(getResources().getText(R.string.dk_header_pull));
                this.arrowIcon.clearAnimation();
                this.arrowIcon.startAnimation(this.rotate_down);
            }
        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
            Log.i("", ">>>>down");
            if (isTouch && state == State.PULL) {
                this.textView.setText(getResources().getText(R.string.dk_header_pull_over));
                this.arrowIcon.clearAnimation();
                this.arrowIcon.startAnimation(this.rotate_up);
            }
        }
    }

    public void complete() {
        this.loadingIcon.setVisibility(4);
        this.loadingIcon.clearAnimation();
        this.successIcon.setVisibility(0);
        this.textView.setText(getResources().getText(R.string.dk_header_completed));
    }
}
