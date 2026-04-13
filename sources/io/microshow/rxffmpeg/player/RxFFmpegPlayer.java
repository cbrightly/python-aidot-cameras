package io.microshow.rxffmpeg.player;

import android.text.TextUtils;
import android.view.Surface;
import io.microshow.rxffmpeg.player.IMediaPlayer;
import io.reactivex.disposables.a;
import io.reactivex.disposables.b;
import io.reactivex.e;
import java.util.concurrent.TimeUnit;

public abstract class RxFFmpegPlayer extends BaseMediaPlayer {
    protected boolean looping;
    private a mCompositeDisposable = new a();
    protected int mDuration = 0;
    private b mTimeDisposable;
    protected String path;

    private native int nativeGetMuteSolo();

    private native int nativeGetVolume();

    private native boolean nativeIsPlaying();

    private native void nativePause();

    private native void nativePrepare(String str);

    private native void nativeRelease();

    private native void nativeResume();

    private native void nativeSeekTo(int i);

    private native void nativeSetMuteSolo(int i);

    private native void nativeSetSurface(Surface surface);

    private native void nativeSetVolume(int i);

    private native void nativeStart();

    private native void nativeStop();

    static {
        System.loadLibrary("rxffmpeg-core");
        System.loadLibrary("rxffmpeg-player");
    }

    public void setSurface(Surface surface) {
        if (surface != null) {
            nativeSetSurface(surface);
        }
    }

    public void setDataSource(String path2) {
        this.path = path2;
    }

    public void prepare() {
        if (!TextUtils.isEmpty(this.path)) {
            nativePrepare(this.path);
        }
    }

    public void pause() {
        nativePause();
    }

    public void resume() {
        nativeResume();
    }

    public void start() {
        if (!TextUtils.isEmpty(this.path)) {
            nativeStart();
        }
    }

    public void stop() {
        cancelTimeDisposable();
        nativeStop();
    }

    public void seekTo(int secds) {
        nativeSeekTo(secds);
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setLooping(boolean looping2) {
        this.looping = looping2;
    }

    public boolean isLooping() {
        return this.looping;
    }

    public boolean isPlaying() {
        return nativeIsPlaying();
    }

    public void setVolume(int percent) {
        nativeSetVolume(percent);
    }

    public int getVolume() {
        return nativeGetVolume();
    }

    public void setMuteSolo(int mute) {
        nativeSetMuteSolo(mute);
    }

    public int getMuteSolo() {
        return nativeGetMuteSolo();
    }

    public void release() {
        setOnPreparedListener((IMediaPlayer.OnPreparedListener) null);
        setOnVideoSizeChangedListener((IMediaPlayer.OnVideoSizeChangedListener) null);
        setOnLoadingListener((IMediaPlayer.OnLoadingListener) null);
        setOnTimeUpdateListener((IMediaPlayer.OnTimeUpdateListener) null);
        setOnErrorListener((IMediaPlayer.OnErrorListener) null);
        setOnCompleteListener((IMediaPlayer.OnCompletionListener) null);
        a aVar = this.mCompositeDisposable;
        if (aVar != null) {
            aVar.d();
            this.mCompositeDisposable = null;
        }
        nativeRelease();
    }

    public void repeatPlay() {
        play(this.path, this.looping);
    }

    private void cancelTimeDisposable() {
        b bVar = this.mTimeDisposable;
        if (bVar != null && !bVar.isDisposed()) {
            this.mTimeDisposable.dispose();
        }
    }

    public void onPreparedNative() {
        IMediaPlayer.OnPreparedListener onPreparedListener = this.mOnPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared(this);
        }
    }

    public void onVideoSizeChangedNative(int width, int height, float dar) {
        IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = this.mOnVideoSizeChangedListener;
        if (onVideoSizeChangedListener != null) {
            onVideoSizeChangedListener.onVideoSizeChanged(this, width, height, dar);
        }
    }

    public void onLoadingNative(boolean load) {
        IMediaPlayer.OnLoadingListener onLoadingListener = this.mOnLoadingListener;
        if (onLoadingListener != null) {
            onLoadingListener.onLoading(this, load);
        }
    }

    public void onTimeUpdateNative(int currentTime, int totalTime) {
        IMediaPlayer.OnTimeUpdateListener onTimeUpdateListener = this.mOnTimeUpdateListener;
        if (onTimeUpdateListener != null) {
            this.mDuration = totalTime;
            onTimeUpdateListener.onTimeUpdate(this, currentTime, totalTime);
        }
    }

    public void onErrorNative(int code, String msg) {
        IMediaPlayer.OnErrorListener onErrorListener = this.mOnErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onError(this, code, msg);
        }
    }

    public void onCompletionNative() {
        IMediaPlayer.OnCompletionListener onCompletionListener = this.mOnCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(this);
        }
        if (isLooping()) {
            b H = e.R(500, TimeUnit.MILLISECONDS).M(io.reactivex.schedulers.a.c()).A(io.reactivex.android.schedulers.a.a()).H(new io.reactivex.functions.e<Long>() {
                public void accept(Long aLong) {
                    RxFFmpegPlayer rxFFmpegPlayer = RxFFmpegPlayer.this;
                    rxFFmpegPlayer.play(rxFFmpegPlayer.path, rxFFmpegPlayer.looping);
                }
            });
            this.mTimeDisposable = H;
            this.mCompositeDisposable.b(H);
        }
    }
}
