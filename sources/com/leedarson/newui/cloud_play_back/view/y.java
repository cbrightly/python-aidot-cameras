package com.leedarson.newui.cloud_play_back.view;

/* compiled from: lambda */
public final /* synthetic */ class y implements Runnable {
    public final /* synthetic */ IJKPlayBackPlayerView c;
    public final /* synthetic */ int d;
    public final /* synthetic */ int f;
    public final /* synthetic */ int q;
    public final /* synthetic */ int x;
    public final /* synthetic */ int y;
    public final /* synthetic */ int z;

    public /* synthetic */ y(IJKPlayBackPlayerView iJKPlayBackPlayerView, int i, int i2, int i3, int i4, int i5, int i6) {
        this.c = iJKPlayBackPlayerView;
        this.d = i;
        this.f = i2;
        this.q = i3;
        this.x = i4;
        this.y = i5;
        this.z = i6;
    }

    public final void run() {
        this.c.B0(this.d, this.f, this.q, this.x, this.y, this.z);
    }
}
