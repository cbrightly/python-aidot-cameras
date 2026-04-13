package com.didichuxing.doraemonkit.kit.performance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class PerformanceCloseDokitView extends AbsDokitView {
    ImageView mIvClose0;
    ImageView mIvClose1;
    ImageView mIvClose2;
    ImageView mIvClose3;
    LinearLayout mLlCloseWrap;
    PerformanceCloseListener mPerformanceCloseListener;
    FrameLayout mWrap0;
    FrameLayout mWrap1;
    FrameLayout mWrap2;
    FrameLayout mWrap3;

    public void onCreate(Context context) {
    }

    public View onCreateView(Context context, FrameLayout rootView) {
        return LayoutInflater.from(context).inflate(R.layout.dk_performance_close_wrap, rootView, false);
    }

    public void addItem(int index, int performanceType) {
        LinearLayout linearLayout = this.mLlCloseWrap;
        if (linearLayout != null) {
            FrameLayout closeViewWrap = (FrameLayout) linearLayout.getChildAt(index);
            closeViewWrap.setVisibility(0);
            closeViewWrap.setTag(Integer.valueOf(performanceType));
        }
    }

    public void removeItem(int index) {
        FrameLayout closeViewWrap = (FrameLayout) this.mLlCloseWrap.getChildAt(index);
        closeViewWrap.setVisibility(8);
        closeViewWrap.setTag(-1);
    }

    public void onViewCreated(FrameLayout rootView) {
        this.mLlCloseWrap = (LinearLayout) findViewById(R.id.ll_close_wrap);
        this.mWrap0 = (FrameLayout) findViewById(R.id.fl_wrap0);
        this.mIvClose0 = (ImageView) findViewById(R.id.iv_close0);
        this.mWrap0.setVisibility(8);
        this.mWrap1 = (FrameLayout) findViewById(R.id.fl_wrap1);
        this.mIvClose1 = (ImageView) findViewById(R.id.iv_close1);
        this.mWrap1.setVisibility(8);
        this.mWrap2 = (FrameLayout) findViewById(R.id.fl_wrap2);
        this.mIvClose2 = (ImageView) findViewById(R.id.iv_close2);
        this.mWrap2.setVisibility(8);
        this.mWrap3 = (FrameLayout) findViewById(R.id.fl_wrap3);
        this.mIvClose3 = (ImageView) findViewById(R.id.iv_close3);
        this.mWrap3.setVisibility(8);
        this.mWrap0.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View v = view;
                v.setVisibility(8);
                PerformanceCloseListener performanceCloseListener = PerformanceCloseDokitView.this.mPerformanceCloseListener;
                if (performanceCloseListener != null) {
                    performanceCloseListener.onClose(((Integer) v.getTag()).intValue());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.mWrap1.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View v = view;
                v.setVisibility(8);
                PerformanceCloseListener performanceCloseListener = PerformanceCloseDokitView.this.mPerformanceCloseListener;
                if (performanceCloseListener != null) {
                    performanceCloseListener.onClose(((Integer) v.getTag()).intValue());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.mWrap2.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View v = view;
                v.setVisibility(8);
                PerformanceCloseListener performanceCloseListener = PerformanceCloseDokitView.this.mPerformanceCloseListener;
                if (performanceCloseListener != null) {
                    performanceCloseListener.onClose(((Integer) v.getTag()).intValue());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.mWrap3.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View v = view;
                v.setVisibility(8);
                PerformanceCloseListener performanceCloseListener = PerformanceCloseDokitView.this.mPerformanceCloseListener;
                if (performanceCloseListener != null) {
                    performanceCloseListener.onClose(((Integer) v.getTag()).intValue());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.gravity = 53;
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.width = i;
        params.height = i;
    }

    /* access modifiers changed from: protected */
    public void setPerformanceCloseListener(PerformanceCloseListener listener) {
        this.mPerformanceCloseListener = listener;
    }

    public boolean canDrag() {
        return false;
    }
}
