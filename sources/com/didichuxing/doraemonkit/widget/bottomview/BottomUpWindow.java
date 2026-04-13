package com.didichuxing.doraemonkit.widget.bottomview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.bottomview.AssociationView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class BottomUpWindow extends PopupWindow {
    private final String TAG = "BottomUpSelectWindow";
    /* access modifiers changed from: private */
    public AssociationView associationView;
    private FrameLayout contentPanel;
    /* access modifiers changed from: private */
    public View ll_panel;
    /* access modifiers changed from: private */
    public OnSubmitListener mOnSubmitListener;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @SensorsDataInstrumented
        public void onClick(View v) {
            int vid = v.getId();
            if (vid == R.id.tv_submit) {
                Object submit = BottomUpWindow.this.associationView.submit();
                if (BottomUpWindow.this.mOnSubmitListener != null) {
                    BottomUpWindow.this.mOnSubmitListener.submit(submit);
                }
                BottomUpWindow.this.dismiss();
            } else if (vid == R.id.tv_cancel) {
                BottomUpWindow.this.cancel();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
        }
    };
    private View thisView;
    private final View titleViiew;
    /* access modifiers changed from: private */
    public View tv_submit;

    public interface OnSubmitListener {
        void cancel();

        void submit(Object obj);
    }

    public BottomUpWindow(Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dk_item_layout_bottom_up_select_window, (ViewGroup) null);
        this.thisView = inflate;
        this.ll_panel = inflate.findViewById(R.id.ll_panel);
        this.titleViiew = this.thisView.findViewById(R.id.tv_title);
        this.contentPanel = (FrameLayout) this.thisView.findViewById(R.id.content);
        setContentView(this.thisView);
        initView();
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Integer.MIN_VALUE));
    }

    private void initView() {
        View findViewById = this.thisView.findViewById(R.id.tv_submit);
        this.tv_submit = findViewById;
        findViewById.setOnClickListener(this.onClickListener);
        this.thisView.findViewById(R.id.tv_cancel).setOnClickListener(this.onClickListener);
        this.thisView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                BottomUpWindow.this.cancel();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public BottomUpWindow setContent(AssociationView view) {
        this.associationView = view;
        this.contentPanel.removeAllViews();
        this.contentPanel.addView(this.associationView.getView());
        this.associationView.setOnStateChangeListener(new AssociationView.OnStateChangeListener() {
            public void onStateChanged() {
                BottomUpWindow.this.tv_submit.setEnabled(BottomUpWindow.this.associationView.isCanSubmit());
            }
        });
        return this;
    }

    public void dismiss() {
        TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        animation.setDuration(200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                BottomUpWindow.this.ll_panel.setVisibility(8);
                BottomUpWindow.this.dismissWindow();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.ll_panel.startAnimation(animation);
        AssociationView associationView2 = this.associationView;
        if (associationView2 != null) {
            associationView2.onHide();
        }
    }

    /* access modifiers changed from: private */
    public void dismissWindow() {
        try {
            super.dismiss();
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public void cancel() {
        this.associationView.cancel();
        dismiss();
        OnSubmitListener onSubmitListener = this.mOnSubmitListener;
        if (onSubmitListener != null) {
            onSubmitListener.cancel();
        }
    }

    public BottomUpWindow show(View parent) {
        showAtLocation(parent, 81, 0, 0);
        this.ll_panel.setVisibility(0);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        translateAnimation.setDuration(200);
        this.ll_panel.startAnimation(translateAnimation);
        AssociationView associationView2 = this.associationView;
        if (associationView2 != null) {
            associationView2.onShow();
        }
        return this;
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.mOnSubmitListener = onSubmitListener;
    }
}
