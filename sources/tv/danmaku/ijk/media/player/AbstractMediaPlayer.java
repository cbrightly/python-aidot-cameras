package tv.danmaku.ijk.media.player;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.misc.IMediaDataSource;

public abstract class AbstractMediaPlayer implements IMediaPlayer {
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private IMediaPlayer.OnCompletionListener mOnCompletionListener;
    private IMediaPlayer.OnErrorListener mOnErrorListener;
    private IMediaPlayer.OnInfoListener mOnInfoListener;
    private IMediaPlayer.OnPreparedListener mOnPreparedListener;
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private IMediaPlayer.OnTimedTextListener mOnTimedTextListener;
    private IMediaPlayer.OnVideoFrameListener mOnVideoFrameListener;
    private IMediaPlayer.OnVideoRadarDataListener mOnVideoRadarDataListener;
    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;

    public final void setOnPreparedListener(IMediaPlayer.OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
    }

    public final void setOnCompletionListener(IMediaPlayer.OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    public final void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener listener) {
        this.mOnBufferingUpdateListener = listener;
    }

    public final void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
    }

    public final void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener listener) {
        this.mOnVideoSizeChangedListener = listener;
    }

    public final void setOnVideoFrameListener(IMediaPlayer.OnVideoFrameListener listener) {
        this.mOnVideoFrameListener = listener;
    }

    public final void setOnVideoRadarDataListener(IMediaPlayer.OnVideoRadarDataListener listener) {
        this.mOnVideoRadarDataListener = listener;
    }

    public final void setOnErrorListener(IMediaPlayer.OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    public final void setOnInfoListener(IMediaPlayer.OnInfoListener listener) {
        this.mOnInfoListener = listener;
    }

    public final void setOnTimedTextListener(IMediaPlayer.OnTimedTextListener listener) {
        this.mOnTimedTextListener = listener;
    }

    public void resetListeners() {
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnTimedTextListener = null;
        this.mOnVideoFrameListener = null;
        this.mOnVideoRadarDataListener = null;
    }

    /* access modifiers changed from: protected */
    public final void notifyOnPrepared() {
        IMediaPlayer.OnPreparedListener onPreparedListener = this.mOnPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared(this);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnCompletion() {
        IMediaPlayer.OnCompletionListener onCompletionListener = this.mOnCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(this);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnBufferingUpdate(int percent) {
        IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = this.mOnBufferingUpdateListener;
        if (onBufferingUpdateListener != null) {
            onBufferingUpdateListener.onBufferingUpdate(this, percent);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnSeekComplete() {
        IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener = this.mOnSeekCompleteListener;
        if (onSeekCompleteListener != null) {
            onSeekCompleteListener.onSeekComplete(this);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnVideoSizeChanged(int width, int height, int sarNum, int sarDen) {
        IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = this.mOnVideoSizeChangedListener;
        if (onVideoSizeChangedListener != null) {
            onVideoSizeChangedListener.onVideoSizeChanged(this, width, height, sarNum, sarDen);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnVideoFrameRefresh(IjkVideoFrame videoFrame) {
        IMediaPlayer.OnVideoFrameListener onVideoFrameListener = this.mOnVideoFrameListener;
        if (onVideoFrameListener != null) {
            onVideoFrameListener.onVideoFrameRefresh(this, videoFrame);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnVideRadarData(byte[] data) {
        IMediaPlayer.OnVideoRadarDataListener onVideoRadarDataListener = this.mOnVideoRadarDataListener;
        if (onVideoRadarDataListener != null) {
            onVideoRadarDataListener.onRadarDataCallback(this, data);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean notifyOnError(int what, int extra) {
        IMediaPlayer.OnErrorListener onErrorListener = this.mOnErrorListener;
        return onErrorListener != null && onErrorListener.onError(this, what, extra);
    }

    /* access modifiers changed from: protected */
    public final boolean notifyOnInfo(int what, int extra) {
        IMediaPlayer.OnInfoListener onInfoListener = this.mOnInfoListener;
        return onInfoListener != null && onInfoListener.onInfo(this, what, extra);
    }

    /* access modifiers changed from: protected */
    public final void notifyOnTimedText(IjkTimedText text) {
        IMediaPlayer.OnTimedTextListener onTimedTextListener = this.mOnTimedTextListener;
        if (onTimedTextListener != null) {
            onTimedTextListener.onTimedText(this, text);
        }
    }

    public void setDataSource(IMediaDataSource mediaDataSource) {
        throw new UnsupportedOperationException();
    }
}
