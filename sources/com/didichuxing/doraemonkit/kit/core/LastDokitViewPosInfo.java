package com.didichuxing.doraemonkit.kit.core;

import com.blankj.utilcode.util.x;

public class LastDokitViewPosInfo {
    private int dokitViewHeight;
    private int dokitViewWidth;
    private boolean isPortrait = true;
    private float leftMarginPercent;
    private float topMarginPercent;

    public int getDokitViewWidth() {
        return this.dokitViewWidth;
    }

    public void setDokitViewWidth(int dokitViewWidth2) {
        this.dokitViewWidth = dokitViewWidth2;
    }

    public int getDokitViewHeight() {
        return this.dokitViewHeight;
    }

    public void setDokitViewHeight(int dokitViewHeight2) {
        this.dokitViewHeight = dokitViewHeight2;
    }

    public void setPortrait() {
        this.isPortrait = x.e();
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMarginPercent = ((float) leftMargin) / ((float) x.b());
    }

    public void setTopMargin(int topMargin) {
        this.topMarginPercent = ((float) topMargin) / ((float) x.a());
    }

    public boolean isPortrait() {
        return this.isPortrait;
    }

    public float getLeftMarginPercent() {
        return this.leftMarginPercent;
    }

    public float getTopMarginPercent() {
        return this.topMarginPercent;
    }

    public String toString() {
        return "LastDokitViewPosInfo{isPortrait=" + this.isPortrait + ", leftMarginPercent=" + this.leftMarginPercent + ", topMarginPercent=" + this.topMarginPercent + '}';
    }
}
