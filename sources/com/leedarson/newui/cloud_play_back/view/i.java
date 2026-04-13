package com.leedarson.newui.cloud_play_back.view;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    public final /* synthetic */ IJKPlayBackPlayerView c;
    public final /* synthetic */ String d;
    public final /* synthetic */ int f;
    public final /* synthetic */ boolean q;

    public /* synthetic */ i(IJKPlayBackPlayerView iJKPlayBackPlayerView, String str, int i, boolean z) {
        this.c = iJKPlayBackPlayerView;
        this.d = str;
        this.f = i;
        this.q = z;
    }

    public final void run() {
        this.c.p1(this.d, this.f, this.q);
    }
}
