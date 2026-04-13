package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class FullScreenEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int angle;
    float height;
    float width;

    public FullScreenEvent(int angle2, float width2, float height2) {
        this.angle = angle2;
        this.width = width2;
        this.height = height2;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width2) {
        this.width = width2;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }
}
