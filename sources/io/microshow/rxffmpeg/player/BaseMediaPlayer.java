package io.microshow.rxffmpeg.player;

import android.view.TextureView;
import io.microshow.rxffmpeg.player.IMediaPlayer;

public abstract class BaseMediaPlayer implements IMediaPlayer {
    public IMediaPlayer.OnCompletionListener mOnCompletionListener;
    public IMediaPlayer.OnErrorListener mOnErrorListener;
    public IMediaPlayer.OnLoadingListener mOnLoadingListener;
    public IMediaPlayer.OnPreparedListener mOnPreparedListener;
    public IMediaPlayer.OnTimeUpdateListener mOnTimeUpdateListener;
    public IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;

    public abstract void play(String str, boolean z);

    public abstract void repeatPlay();

    public abstract void setTextureView(TextureView textureView);

    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
    }

    public void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener listener) {
        this.mOnVideoSizeChangedListener = listener;
    }

    public void setOnLoadingListener(IMediaPlayer.OnLoadingListener listener) {
        this.mOnLoadingListener = listener;
    }

    public void setOnTimeUpdateListener(IMediaPlayer.OnTimeUpdateListener listener) {
        this.mOnTimeUpdateListener = listener;
    }

    public void setOnErrorListener(IMediaPlayer.OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    public void setOnCompleteListener(IMediaPlayer.OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }
}
