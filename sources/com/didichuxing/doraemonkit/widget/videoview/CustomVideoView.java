package com.didichuxing.doraemonkit.widget.videoview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;
import com.didichuxing.doraemonkit.util.UIUtils;

public class CustomVideoView extends VideoView implements View.OnTouchListener {
    private float lastX;
    private float lastY;
    private Context mContext;
    private StateListener mStateListener;
    private int thresold;

    public interface StateListener {
        void changeBrightness(float f);

        void changeVolumn(float f);

        void hideHint();
    }

    public void setStateListener(StateListener stateListener) {
        this.mStateListener = stateListener;
    }

    public CustomVideoView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.thresold = 30;
        this.mContext = context;
        setOnTouchListener(this);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(VideoView.getDefaultSize(1920, widthMeasureSpec), VideoView.getDefaultSize(1080, heightMeasureSpec));
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.lastX = event.getX();
                this.lastY = event.getY();
                return true;
            case 1:
                this.mStateListener.hideHint();
                return true;
            case 2:
                float detlaX = event.getX() - this.lastX;
                float detlaY = event.getY() - this.lastY;
                if (Math.abs(detlaX) < ((float) this.thresold) && Math.abs(detlaY) > ((float) this.thresold)) {
                    if (event.getX() < ((float) (UIUtils.getWidthPixels() / 2))) {
                        this.mStateListener.changeBrightness(detlaY);
                    } else {
                        this.mStateListener.changeVolumn(detlaY);
                    }
                }
                this.lastX = event.getX();
                this.lastY = event.getY();
                return true;
            default:
                return true;
        }
    }
}
