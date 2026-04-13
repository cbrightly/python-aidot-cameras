package com.zhy.view.flowlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class TagView extends FrameLayout implements Checkable {
    private static final int[] c = {16842912};
    private boolean d;

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            FrameLayout.mergeDrawableStates(states, c);
        }
        return states;
    }

    public void setChecked(boolean checked) {
        if (this.d != checked) {
            this.d = checked;
            refreshDrawableState();
        }
    }

    public boolean isChecked() {
        return this.d;
    }

    public void toggle() {
        setChecked(!this.d);
    }
}
