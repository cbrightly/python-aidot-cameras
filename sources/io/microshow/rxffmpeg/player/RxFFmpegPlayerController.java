package io.microshow.rxffmpeg.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public abstract class RxFFmpegPlayerController extends FrameLayout {
    protected BaseMediaPlayer mPlayer;
    protected RxFFmpegPlayerView mPlayerView;

    /* access modifiers changed from: protected */
    public abstract int getLayoutId();

    /* access modifiers changed from: protected */
    public abstract void initListener();

    /* access modifiers changed from: protected */
    public abstract void initView();

    /* access modifiers changed from: protected */
    public abstract void onPause();

    /* access modifiers changed from: protected */
    public abstract void onResume();

    public RxFFmpegPlayerController(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
        initView();
    }

    public void setPlayerView(RxFFmpegPlayerView playerView) {
        if (playerView != null) {
            this.mPlayerView = playerView;
            this.mPlayer = playerView.mPlayer;
            initListener();
        }
    }
}
