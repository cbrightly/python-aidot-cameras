package com.leedarson.newui.cloud_play_back.view;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: lambda */
public final /* synthetic */ class m implements IMediaPlayer.OnErrorListener {
    public final /* synthetic */ IJKPlayBackPlayerView a;

    public /* synthetic */ m(IJKPlayBackPlayerView iJKPlayBackPlayerView) {
        this.a = iJKPlayBackPlayerView;
    }

    public final boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
        return this.a.P0(iMediaPlayer, i, i2);
    }
}
