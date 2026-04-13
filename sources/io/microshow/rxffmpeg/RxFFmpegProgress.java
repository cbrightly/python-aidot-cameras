package io.microshow.rxffmpeg;

public class RxFFmpegProgress {
    public int progress;
    public long progressTime;
    public int state;

    public RxFFmpegProgress(int state2, int progress2, long progressTime2) {
        this.state = 0;
        this.state = state2;
        this.progress = progress2;
        this.progressTime = progressTime2;
    }

    public RxFFmpegProgress(int state2) {
        this(state2, 0, 0);
    }
}
