package com.leedarson.newui.cloud_play_back.view;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: lambda */
public final /* synthetic */ class b implements IMediaPlayer.OnBufferingUpdateListener {
    public final /* synthetic */ IJKPlayBackPlayerView a;

    public /* synthetic */ b(IJKPlayBackPlayerView iJKPlayBackPlayerView) {
        this.a = iJKPlayBackPlayerView;
    }

    public final void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
        this.a.N0(iMediaPlayer, i);
    }
}
