package com.didichuxing.doraemonkit.kit.alignruler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.blankj.utilcode.util.a;
import com.blankj.utilcode.util.f;
import com.blankj.utilcode.util.x;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.alignruler.AlignRulerMarkerDokitView;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

public class AlignRulerLineDokitView extends AbsDokitView implements AlignRulerMarkerDokitView.OnAlignRulerMarkerPositionChangeListener {
    private AlignLineView mAlignInfoView;
    /* access modifiers changed from: private */
    public AlignRulerMarkerDokitView mMarker;

    public void onCreate(Context context) {
    }

    public void onDestroy() {
        super.onDestroy();
        this.mMarker.removePositionChangeListener(this);
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_align_ruler_line, view, false);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = 24;
        int i = DokitViewLayoutParams.MATCH_PARENT;
        params.height = i;
        params.width = i;
    }

    public void onViewCreated(FrameLayout view) {
        postDelayed(new Runnable() {
            public void run() {
                AlignRulerMarkerDokitView unused = AlignRulerLineDokitView.this.mMarker = (AlignRulerMarkerDokitView) DokitViewManager.getInstance().getDokitView(a.b(), AlignRulerMarkerDokitView.class.getSimpleName());
                if (AlignRulerLineDokitView.this.mMarker != null) {
                    AlignRulerLineDokitView.this.mMarker.addPositionChangeListener(AlignRulerLineDokitView.this);
                }
            }
        }, 100);
        setDokitViewNotResponseTouchEvent(getRootView());
        this.mAlignInfoView = (AlignLineView) findViewById(R.id.info_view);
    }

    public void onPositionChanged(int x, int y) {
        if (!isNormalMode()) {
            int iconSize = f.e(30.0f);
            if (y <= iconSize) {
                y = iconSize;
            }
            if (x.e()) {
                if (y >= getScreenLongSideLength() - iconSize) {
                    y = getScreenLongSideLength() - iconSize;
                }
            } else if (y >= getScreenShortSideLength() - iconSize) {
                y = getScreenShortSideLength() - iconSize;
            }
            if (x <= iconSize) {
                x = iconSize;
            }
            if (x.e()) {
                if (x >= getScreenShortSideLength() - iconSize) {
                    x = getScreenShortSideLength() - iconSize;
                }
            } else if (x >= getScreenLongSideLength() - iconSize) {
                x = getScreenLongSideLength() - iconSize;
            }
        }
        this.mAlignInfoView.showInfo(x, y);
    }

    public boolean canDrag() {
        return false;
    }

    public boolean restrictBorderline() {
        return true;
    }
}
