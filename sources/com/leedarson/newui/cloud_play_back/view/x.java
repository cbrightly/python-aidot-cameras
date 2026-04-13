package com.leedarson.newui.cloud_play_back.view;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: lambda */
public final /* synthetic */ class x implements Runnable {
    public final /* synthetic */ IjkMediaPlayer c;
    public final /* synthetic */ n0 d;

    public /* synthetic */ x(IjkMediaPlayer ijkMediaPlayer, n0 n0Var) {
        this.c = ijkMediaPlayer;
        this.d = n0Var;
    }

    public final void run() {
        IJKPlayBackPlayerView.q1(this.c, this.d);
    }
}
