package io.microshow.rxffmpeg.player;

import android.media.MediaPlayer;
import android.os.Build;
import android.view.Surface;
import io.microshow.rxffmpeg.player.IMediaPlayer;
import io.reactivex.disposables.a;
import io.reactivex.disposables.b;
import io.reactivex.e;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class SystemMediaPlayer extends BaseMediaPlayer {
    private a mCompositeDisposable = new a();
    public MediaPlayer mMediaPlayer = new MediaPlayer();
    private b mTimeUpdateDisposable;
    protected String path;
    public int volumePercent = -1;

    public void setSurface(Surface surface) {
        if (surface != null) {
            this.mMediaPlayer.setSurface(surface);
        }
    }

    public void setDataSource(String path2) {
        try {
            this.path = path2;
            this.mMediaPlayer.setDataSource(path2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepare() {
        this.mMediaPlayer.prepareAsync();
    }

    public void pause() {
        this.mMediaPlayer.pause();
        cancelTimeUpdateDisposable();
    }

    public void resume() {
        start();
    }

    public void start() {
        this.mMediaPlayer.start();
        startTimeUpdateDisposable();
    }

    public void stop() {
        this.mMediaPlayer.stop();
    }

    public void seekTo(int secds) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mMediaPlayer.seekTo((long) secds, 3);
        } else {
            this.mMediaPlayer.seekTo(secds);
        }
    }

    public int getDuration() {
        return this.mMediaPlayer.getDuration();
    }

    public void setLooping(boolean looping) {
        this.mMediaPlayer.setLooping(looping);
    }

    public boolean isLooping() {
        return this.mMediaPlayer.isLooping();
    }

    public boolean isPlaying() {
        return this.mMediaPlayer.isPlaying();
    }

    public void setVolume(int percent) {
        this.volumePercent = percent;
        this.mMediaPlayer.setVolume(((float) percent) / 100.0f, ((float) percent) / 100.0f);
    }

    public int getVolume() {
        return this.volumePercent;
    }

    public void setMuteSolo(int mute) {
    }

    public int getMuteSolo() {
        return 0;
    }

    public void release() {
        setOnPreparedListener((IMediaPlayer.OnPreparedListener) null);
        setOnVideoSizeChangedListener((IMediaPlayer.OnVideoSizeChangedListener) null);
        setOnLoadingListener((IMediaPlayer.OnLoadingListener) null);
        setOnTimeUpdateListener((IMediaPlayer.OnTimeUpdateListener) null);
        setOnErrorListener((IMediaPlayer.OnErrorListener) null);
        setOnCompleteListener((IMediaPlayer.OnCompletionListener) null);
        cancelTimeUpdateDisposable();
        a aVar = this.mCompositeDisposable;
        if (aVar != null) {
            aVar.d();
            this.mCompositeDisposable = null;
        }
        this.mMediaPlayer.release();
    }

    public void setOnPreparedListener(final IMediaPlayer.OnPreparedListener listener) {
        super.setOnPreparedListener(listener);
        this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                IMediaPlayer.OnPreparedListener onPreparedListener = listener;
                if (onPreparedListener != null) {
                    onPreparedListener.onPrepared(SystemMediaPlayer.this);
                }
            }
        });
    }

    public void setOnVideoSizeChangedListener(final IMediaPlayer.OnVideoSizeChangedListener listener) {
        super.setOnVideoSizeChangedListener(listener);
        this.mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
                IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = listener;
                if (onVideoSizeChangedListener != null) {
                    onVideoSizeChangedListener.onVideoSizeChanged(SystemMediaPlayer.this, width, height, ((float) width) / ((float) height));
                }
            }
        });
    }

    public void setOnLoadingListener(IMediaPlayer.OnLoadingListener listener) {
        super.setOnLoadingListener(listener);
    }

    public void setOnErrorListener(final IMediaPlayer.OnErrorListener listener) {
        super.setOnErrorListener(listener);
        this.mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
                IMediaPlayer.OnErrorListener onErrorListener = listener;
                if (onErrorListener == null || what == -38) {
                    return true;
                }
                SystemMediaPlayer systemMediaPlayer = SystemMediaPlayer.this;
                onErrorListener.onError(systemMediaPlayer, what, extra + "");
                return true;
            }
        });
    }

    public void setOnCompleteListener(final IMediaPlayer.OnCompletionListener listener) {
        super.setOnCompleteListener(listener);
        this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                IMediaPlayer.OnCompletionListener onCompletionListener = listener;
                if (onCompletionListener != null) {
                    onCompletionListener.onCompletion(SystemMediaPlayer.this);
                }
            }
        });
    }

    public void setOnTimeUpdateListener(IMediaPlayer.OnTimeUpdateListener listener) {
        super.setOnTimeUpdateListener(listener);
        this.mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                if (what == 3 || what == 700) {
                    SystemMediaPlayer systemMediaPlayer = SystemMediaPlayer.this;
                    IMediaPlayer.OnLoadingListener onLoadingListener = systemMediaPlayer.mOnLoadingListener;
                    if (onLoadingListener != null) {
                        onLoadingListener.onLoading(systemMediaPlayer, false);
                    }
                    return true;
                } else if (what != 701) {
                    return false;
                } else {
                    SystemMediaPlayer systemMediaPlayer2 = SystemMediaPlayer.this;
                    IMediaPlayer.OnLoadingListener onLoadingListener2 = systemMediaPlayer2.mOnLoadingListener;
                    if (onLoadingListener2 != null) {
                        onLoadingListener2.onLoading(systemMediaPlayer2, true);
                    }
                    return true;
                }
            }
        });
    }

    public void repeatPlay() {
        play(this.path, this.mMediaPlayer.isLooping());
    }

    private void cancelTimeUpdateDisposable() {
        b bVar = this.mTimeUpdateDisposable;
        if (bVar != null && !bVar.isDisposed()) {
            this.mTimeUpdateDisposable.dispose();
        }
    }

    public void startTimeUpdateDisposable() {
        cancelTimeUpdateDisposable();
        b H = e.u(200, TimeUnit.MILLISECONDS).M(io.reactivex.schedulers.a.c()).A(io.reactivex.android.schedulers.a.a()).H(new io.reactivex.functions.e<Long>() {
            public void accept(Long aLong) {
                SystemMediaPlayer systemMediaPlayer = SystemMediaPlayer.this;
                IMediaPlayer.OnTimeUpdateListener onTimeUpdateListener = systemMediaPlayer.mOnTimeUpdateListener;
                if (onTimeUpdateListener != null) {
                    onTimeUpdateListener.onTimeUpdate(systemMediaPlayer, systemMediaPlayer.mMediaPlayer.getCurrentPosition() / 1000, SystemMediaPlayer.this.getDuration() / 1000);
                }
            }
        });
        this.mTimeUpdateDisposable = H;
        this.mCompositeDisposable.b(H);
    }
}
