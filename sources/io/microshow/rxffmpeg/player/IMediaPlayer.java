package io.microshow.rxffmpeg.player;

import android.view.Surface;

public interface IMediaPlayer {

    public interface OnCompletionListener {
        void onCompletion(IMediaPlayer iMediaPlayer);
    }

    public interface OnErrorListener {
        void onError(IMediaPlayer iMediaPlayer, int i, String str);
    }

    public interface OnLoadingListener {
        void onLoading(IMediaPlayer iMediaPlayer, boolean z);
    }

    public interface OnPreparedListener {
        void onPrepared(IMediaPlayer iMediaPlayer);
    }

    public interface OnTimeUpdateListener {
        void onTimeUpdate(IMediaPlayer iMediaPlayer, int i, int i2);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, float f);
    }

    int getDuration();

    int getMuteSolo();

    int getVolume();

    boolean isLooping();

    boolean isPlaying();

    void pause();

    void prepare();

    void release();

    void resume();

    void seekTo(int i);

    void setDataSource(String str);

    void setLooping(boolean z);

    void setMuteSolo(int i);

    void setSurface(Surface surface);

    void setVolume(int i);

    void start();

    void stop();
}
