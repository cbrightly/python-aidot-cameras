package com.scwang.smart.refresh.layout.constant;

/* compiled from: RefreshState */
public enum b {
    None(0, false, false, false, false, false),
    PullDownToRefresh(1, true, false, false, false, false),
    PullUpToLoad(2, true, false, false, false, false),
    PullDownCanceled(1, false, false, false, false, false),
    PullUpCanceled(2, false, false, false, false, false),
    ReleaseToRefresh(1, true, false, false, false, true),
    ReleaseToLoad(2, true, false, false, false, true),
    ReleaseToTwoLevel(1, true, false, false, true, true),
    TwoLevelReleased(1, false, false, false, true, false),
    RefreshReleased(1, false, false, false, false, false),
    LoadReleased(2, false, false, false, false, false),
    Refreshing(1, false, true, false, false, false),
    Loading(2, false, true, false, false, false),
    TwoLevel(1, false, true, false, true, false),
    RefreshFinish(1, false, false, true, false, false),
    LoadFinish(2, false, false, true, false, false),
    TwoLevelFinish(1, false, false, true, true, false);
    
    public final boolean isDragging;
    public final boolean isFinishing;
    public final boolean isFooter;
    public final boolean isHeader;
    public final boolean isOpening;
    public final boolean isReleaseToOpening;
    public final boolean isTwoLevel;

    private b(int role, boolean dragging, boolean opening, boolean finishing, boolean twoLevel, boolean releaseToOpening) {
        boolean z = false;
        this.isHeader = role == 1;
        this.isFooter = role == 2 ? true : z;
        this.isDragging = dragging;
        this.isOpening = opening;
        this.isFinishing = finishing;
        this.isTwoLevel = twoLevel;
        this.isReleaseToOpening = releaseToOpening;
    }

    public b toFooter() {
        if (!this.isHeader || this.isTwoLevel) {
            return this;
        }
        return values()[ordinal() + 1];
    }

    public b toHeader() {
        if (!this.isFooter || this.isTwoLevel) {
            return this;
        }
        return values()[ordinal() - 1];
    }
}
