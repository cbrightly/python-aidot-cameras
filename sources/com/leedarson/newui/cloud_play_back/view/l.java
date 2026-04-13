package com.leedarson.newui.cloud_play_back.view;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ IJKPlayBackPlayerView c;
    public final /* synthetic */ byte[] d;
    public final /* synthetic */ boolean f;
    public final /* synthetic */ int q;
    public final /* synthetic */ int x;

    public /* synthetic */ l(IJKPlayBackPlayerView iJKPlayBackPlayerView, byte[] bArr, boolean z, int i, int i2) {
        this.c = iJKPlayBackPlayerView;
        this.d = bArr;
        this.f = z;
        this.q = i;
        this.x = i2;
    }

    public final void run() {
        this.c.F0(this.d, this.f, this.q, this.x);
    }
}
