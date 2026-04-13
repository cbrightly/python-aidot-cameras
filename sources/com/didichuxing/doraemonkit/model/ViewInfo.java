package com.didichuxing.doraemonkit.model;

import android.graphics.Rect;
import android.view.View;
import com.didichuxing.doraemonkit.util.UIUtils;

public class ViewInfo {
    private static final int DRAW_TIME_LEVEL_GAP = 5;
    private static final int DRAW_TIME_LEVEL_NUM = 4;
    private static String TAG = "ViewInfo";
    public float drawTime;
    public final String id;
    public int layerNum;
    public final Rect viewRect;

    public ViewInfo(View view) {
        this.viewRect = UIUtils.getViewRect(view);
        this.id = UIUtils.getIdText(view);
    }

    public int getDrawTimeLevel() {
        return ((((int) this.drawTime) / 5) * 255) / 4;
    }
}
