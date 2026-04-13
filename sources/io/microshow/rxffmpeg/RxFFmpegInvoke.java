package io.microshow.rxffmpeg;

import io.reactivex.a;
import io.reactivex.e;
import io.reactivex.f;
import io.reactivex.g;

public class RxFFmpegInvoke {
    public static final String TAG = RxFFmpegInvoke.class.getSimpleName();
    private static volatile RxFFmpegInvoke instance;
    private IFFmpegListener ffmpegListener;

    public interface IFFmpegListener {
        void onCancel();

        void onError(String str);

        void onFinish();

        void onProgress(int i, long j);
    }

    public native void exit();

    public native String getMediaInfo(String str);

    public native int runFFmpegCmd(String[] strArr);

    public native void setDebug(boolean z);

    static {
        System.loadLibrary("rxffmpeg-core");
        System.loadLibrary("rxffmpeg-invoke");
    }

    private RxFFmpegInvoke() {
    }

    public static RxFFmpegInvoke getInstance() {
        if (instance == null) {
            synchronized (RxFFmpegInvoke.class) {
                if (instance == null) {
                    instance = new RxFFmpegInvoke();
                }
            }
        }
        return instance;
    }

    public void runCommandAsync(final String[] command, IFFmpegListener mffmpegListener) {
        setFFmpegListener(mffmpegListener);
        synchronized (RxFFmpegInvoke.class) {
            new Thread(new Runnable() {
                public void run() {
                    int runFFmpegCmd = RxFFmpegInvoke.this.runFFmpegCmd(command);
                    RxFFmpegInvoke.this.onClean();
                }
            }).start();
        }
    }

    public int runCommand(String[] command, IFFmpegListener mffmpegListener) {
        int ret;
        setFFmpegListener(mffmpegListener);
        synchronized (RxFFmpegInvoke.class) {
            ret = runFFmpegCmd(command);
            onClean();
        }
        return ret;
    }

    public e<RxFFmpegProgress> runCommandRxJava(final String[] command) {
        return e.d(new g<RxFFmpegProgress>() {
            public void subscribe(final f<RxFFmpegProgress> emitter) {
                RxFFmpegInvoke.this.setFFmpegListener(new IFFmpegListener() {
                    public void onFinish() {
                        emitter.onComplete();
                    }

                    public void onProgress(int progress, long progressTime) {
                        emitter.onNext(new RxFFmpegProgress(RxFFmpegSubscriber.STATE_PROGRESS, progress, progressTime));
                    }

                    public void onCancel() {
                        emitter.onNext(new RxFFmpegProgress(RxFFmpegSubscriber.STATE_CANCEL));
                    }

                    public void onError(String message) {
                        emitter.onError(new Throwable(message));
                    }
                });
                int runFFmpegCmd = RxFFmpegInvoke.this.runFFmpegCmd(command);
                RxFFmpegInvoke.this.onClean();
            }
        }, a.BUFFER).M(io.reactivex.schedulers.a.c()).A(io.reactivex.android.schedulers.a.a());
    }

    public void onProgress(int progress, long progressTime) {
        IFFmpegListener iFFmpegListener = this.ffmpegListener;
        if (iFFmpegListener != null) {
            iFFmpegListener.onProgress(progress, progressTime);
        }
    }

    public void onFinish() {
        IFFmpegListener iFFmpegListener = this.ffmpegListener;
        if (iFFmpegListener != null) {
            iFFmpegListener.onFinish();
        }
    }

    public void onCancel() {
        IFFmpegListener iFFmpegListener = this.ffmpegListener;
        if (iFFmpegListener != null) {
            iFFmpegListener.onCancel();
        }
    }

    public void onError(String message) {
        IFFmpegListener iFFmpegListener = this.ffmpegListener;
        if (iFFmpegListener != null) {
            iFFmpegListener.onError(message);
        }
    }

    public void onClean() {
        if (this.ffmpegListener != null) {
            this.ffmpegListener = null;
        }
    }

    public void onDestroy() {
        if (this.ffmpegListener != null) {
            this.ffmpegListener = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    public IFFmpegListener getFFmpegListener() {
        return this.ffmpegListener;
    }

    public void setFFmpegListener(IFFmpegListener ffmpegListener2) {
        this.ffmpegListener = ffmpegListener2;
    }
}
