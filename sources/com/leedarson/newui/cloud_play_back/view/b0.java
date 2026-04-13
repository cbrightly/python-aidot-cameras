package com.leedarson.newui.cloud_play_back.view;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: lambda */
public final /* synthetic */ class b0 implements IMediaPlayer.OnSeekCompleteListener {
    public final /* synthetic */ IJKPlayBackPlayerView a;

    public /* synthetic */ b0(IJKPlayBackPlayerView iJKPlayBackPlayerView) {
        this.a = iJKPlayBackPlayerView;
    }

    public final void onSeekComplete(IMediaPlayer iMediaPlayer) {
        this.a.R0(iMediaPlayer);
    }
}
