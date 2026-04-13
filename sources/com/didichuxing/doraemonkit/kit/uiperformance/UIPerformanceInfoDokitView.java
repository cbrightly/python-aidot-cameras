package com.didichuxing.doraemonkit.kit.uiperformance;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.uiperformance.UIPerformanceManager;
import com.didichuxing.doraemonkit.model.ViewInfo;
import com.didichuxing.doraemonkit.widget.textview.LabelTextView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;

public class UIPerformanceInfoDokitView extends AbsDokitView implements UIPerformanceManager.PerformanceDataListener {
    private ImageView mClose;
    private LabelTextView mMaxLevelText;
    private LabelTextView mMaxLevelViewIdText;
    private LabelTextView mMaxTimeText;
    private LabelTextView mMaxTimeViewIdText;
    private LabelTextView mTotalTimeText;

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(view.getContext()).inflate(R.layout.dk_float_ui_performance_info, view, false);
    }

    public void onViewCreated(FrameLayout view) {
        ImageView imageView = (ImageView) findViewById(R.id.close);
        this.mClose = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                DokitViewManager.getInstance().detach(UIPerformanceDisplayDokitView.class.getSimpleName());
                DokitViewManager.getInstance().detach(UIPerformanceInfoDokitView.class.getSimpleName());
                UIPerformanceManager.getInstance().stop();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.mMaxLevelText = (LabelTextView) findViewById(R.id.max_level);
        this.mMaxLevelViewIdText = (LabelTextView) findViewById(R.id.max_level_view_id);
        this.mTotalTimeText = (LabelTextView) findViewById(R.id.total_time);
        this.mMaxTimeText = (LabelTextView) findViewById(R.id.max_time);
        this.mMaxTimeViewIdText = (LabelTextView) findViewById(R.id.max_time_view_id);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.y = 60;
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.height = i;
        params.width = i;
    }

    public void onCreate(Context context) {
        UIPerformanceManager.getInstance().addListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        UIPerformanceManager.getInstance().removeListener(this);
    }

    public void onRefresh(List<ViewInfo> viewInfos) {
        if (viewInfos != null) {
            int maxLevel = 0;
            float maxTime = 0.0f;
            float totalTime = 0.0f;
            ViewInfo maxLevelViewInfo = null;
            ViewInfo maxTimeViewInfo = null;
            for (ViewInfo viewInfo : viewInfos) {
                if (viewInfo.layerNum > maxLevel) {
                    maxLevel = viewInfo.layerNum;
                    maxLevelViewInfo = viewInfo;
                }
                float f = viewInfo.drawTime;
                if (f > maxTime) {
                    maxTime = viewInfo.drawTime;
                    maxTimeViewInfo = viewInfo;
                }
                totalTime += f;
            }
            this.mMaxLevelText.setText(String.valueOf(maxLevel));
            if (maxLevelViewInfo != null && !TextUtils.isEmpty(maxLevelViewInfo.id)) {
                this.mMaxLevelViewIdText.setText(maxLevelViewInfo.id);
            }
            LabelTextView labelTextView = this.mMaxTimeText;
            labelTextView.setText(maxTime + "ms");
            if (maxTimeViewInfo != null && !TextUtils.isEmpty(maxTimeViewInfo.id)) {
                this.mMaxTimeViewIdText.setText(maxTimeViewInfo.id);
            }
            LabelTextView labelTextView2 = this.mTotalTimeText;
            labelTextView2.setText(totalTime + "ms");
        }
    }
}
