package com.didichuxing.doraemonkit.kit.alignruler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.util.UIUtils;
import java.util.ArrayList;
import java.util.List;

public class AlignRulerMarkerDokitView extends AbsDokitView {
    private List<OnAlignRulerMarkerPositionChangeListener> mPositionChangeListeners = new ArrayList();

    public interface OnAlignRulerMarkerPositionChangeListener {
        void onPositionChanged(int i, int i2);
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_align_ruler_marker, (ViewGroup) null);
    }

    public void onViewCreated(FrameLayout view) {
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.height = i;
        params.width = i;
        params.x = UIUtils.getWidthPixels() / 2;
        params.y = UIUtils.getHeightPixels() / 2;
    }

    public void onCreate(Context context) {
    }

    public void onDestroy() {
        super.onDestroy();
        removePositionChangeListeners();
    }

    public void onMove(int x, int y, int dx, int dy) {
        super.onMove(x, y, dx, dy);
        for (OnAlignRulerMarkerPositionChangeListener listener : this.mPositionChangeListeners) {
            if (isNormalMode()) {
                listener.onPositionChanged(getNormalLayoutParams().leftMargin + (getRootView().getWidth() / 2), getNormalLayoutParams().topMargin + (getRootView().getHeight() / 2));
            } else {
                listener.onPositionChanged(getSystemLayoutParams().x + (getRootView().getWidth() / 2), getSystemLayoutParams().y + (getRootView().getHeight() / 2));
            }
        }
    }

    public void updateViewLayout(String tag, boolean isActivityResume) {
        super.updateViewLayout(tag, isActivityResume);
        for (OnAlignRulerMarkerPositionChangeListener listener : this.mPositionChangeListeners) {
            if (isNormalMode()) {
                listener.onPositionChanged(getNormalLayoutParams().leftMargin + (getRootView().getWidth() / 2), getNormalLayoutParams().topMargin + (getRootView().getHeight() / 2));
            } else {
                listener.onPositionChanged(getSystemLayoutParams().x + (getRootView().getWidth() / 2), getSystemLayoutParams().y + (getRootView().getHeight() / 2));
            }
        }
    }

    public void addPositionChangeListener(OnAlignRulerMarkerPositionChangeListener positionChangeListener) {
        this.mPositionChangeListeners.add(positionChangeListener);
        for (OnAlignRulerMarkerPositionChangeListener listener : this.mPositionChangeListeners) {
            if (isNormalMode()) {
                listener.onPositionChanged(getNormalLayoutParams().leftMargin + (getRootView().getWidth() / 2), getNormalLayoutParams().topMargin + (getRootView().getHeight() / 2));
            } else {
                listener.onPositionChanged(getSystemLayoutParams().x + (getRootView().getWidth() / 2), getSystemLayoutParams().y + (getRootView().getHeight() / 2));
            }
        }
    }

    public void removePositionChangeListener(OnAlignRulerMarkerPositionChangeListener positionChangeListener) {
        this.mPositionChangeListeners.remove(positionChangeListener);
    }

    private void removePositionChangeListeners() {
        this.mPositionChangeListeners.clear();
    }

    public boolean restrictBorderline() {
        return false;
    }
}
