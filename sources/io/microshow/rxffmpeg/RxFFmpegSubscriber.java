package io.microshow.rxffmpeg;

import io.microshow.rxffmpeg.RxFFmpegInvoke;
import io.reactivex.subscribers.a;

public abstract class RxFFmpegSubscriber extends a<RxFFmpegProgress> implements RxFFmpegInvoke.IFFmpegListener {
    public static int STATE_CANCEL = -100;
    public static int STATE_PROGRESS = 100;

    public void onNext(RxFFmpegProgress mProgress) {
        if (mProgress.state == STATE_CANCEL) {
            onCancel();
        } else {
            onProgress(mProgress.progress, mProgress.progressTime);
        }
    }

    public void onError(Throwable t) {
        onError(t.getMessage());
    }

    public void onComplete() {
        onFinish();
    }
}
