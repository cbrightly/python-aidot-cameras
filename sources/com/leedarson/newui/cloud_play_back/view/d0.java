package com.leedarson.newui.cloud_play_back.view;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: lambda */
public final /* synthetic */ class d0 implements IMediaPlayer.OnInfoListener {
    public final /* synthetic */ IJKPlayBackPlayerView a;

    public /* synthetic */ d0(IJKPlayBackPlayerView iJKPlayBackPlayerView) {
        this.a = iJKPlayBackPlayerView;
    }

    public final boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
        return this.a.L0(iMediaPlayer, i, i2);
    }
}
