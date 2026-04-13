package com.didichuxing.doraemonkit.kit.uiperformance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.uiperformance.UIPerformanceManager;
import com.didichuxing.doraemonkit.kit.viewcheck.LayoutBorderView;
import com.didichuxing.doraemonkit.model.ViewInfo;
import java.util.List;

public class UIPerformanceDisplayDokitView extends AbsDokitView implements UIPerformanceManager.PerformanceDataListener {
    private LayoutBorderView mLayoutBorderView;

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(view.getContext()).inflate(R.layout.dk_float_ui_performance_display, view, false);
    }

    public void onViewCreated(FrameLayout view) {
        this.mLayoutBorderView = (LayoutBorderView) findViewById(R.id.rect_view);
        setDokitViewNotResponseTouchEvent(getRootView());
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = DokitViewLayoutParams.FLAG_NOT_FOCUSABLE_AND_NOT_TOUCHABLE;
        int i = DokitViewLayoutParams.MATCH_PARENT;
        params.width = i;
        params.height = i;
    }

    public void onCreate(Context context) {
        UIPerformanceManager.getInstance().addListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        UIPerformanceManager.getInstance().removeListener(this);
    }

    public void onRefresh(List<ViewInfo> infos) {
        this.mLayoutBorderView.showViewLayoutBorder(infos);
    }

    public boolean canDrag() {
        return false;
    }
}
