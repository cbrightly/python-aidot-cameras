package com.leedarson.newui.cloud_play_back.view;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: lambda */
public final /* synthetic */ class j implements IMediaPlayer.OnPreparedListener {
    public final /* synthetic */ IJKPlayBackPlayerView a;

    public /* synthetic */ j(IJKPlayBackPlayerView iJKPlayBackPlayerView) {
        this.a = iJKPlayBackPlayerView;
    }

    public final void onPrepared(IMediaPlayer iMediaPlayer) {
        this.a.H0(iMediaPlayer);
    }
}
