package com.leedarson.newui.cloud_play_back.view;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: lambda */
public final /* synthetic */ class q implements IMediaPlayer.OnCompletionListener {
    public final /* synthetic */ IJKPlayBackPlayerView a;

    public /* synthetic */ q(IJKPlayBackPlayerView iJKPlayBackPlayerView) {
        this.a = iJKPlayBackPlayerView;
    }

    public final void onCompletion(IMediaPlayer iMediaPlayer) {
        this.a.J0(iMediaPlayer);
    }
}
