package io.microshow.rxffmpeg.player;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import io.microshow.rxffmpeg.player.IMediaPlayer;
import io.microshow.rxffmpeg.player.MeasureHelper;
import java.lang.ref.WeakReference;

public class RxFFmpegPlayerView extends FrameLayout {
    public static final int MODE_FULL_SCREEN = 1;
    public static final int MODE_NORMAL = 0;
    /* access modifiers changed from: private */
    public FrameLayout mContainer;
    private Context mContext;
    /* access modifiers changed from: private */
    public int mCurrentMode;
    /* access modifiers changed from: private */
    public MeasureHelper mMeasureHelper;
    public BaseMediaPlayer mPlayer;
    private RxFFmpegPlayerController mPlayerController;
    public PlayerCoreType mPlayerCoreType;
    /* access modifiers changed from: private */
    public TextureView mTextureView;

    public enum PlayerCoreType {
        PCT_RXFFMPEG_PLAYER,
        PCT_SYSTEM_MEDIA_PLAYER
    }

    public RxFFmpegPlayerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RxFFmpegPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPlayerCoreType = PlayerCoreType.PCT_RXFFMPEG_PLAYER;
        this.mCurrentMode = 0;
        this.mContext = context;
        this.mMeasureHelper = new MeasureHelper(this) {
            public boolean isFullScreen() {
                return RxFFmpegPlayerView.this.mCurrentMode == 1;
            }
        };
        initContainer();
        setKeepScreenOn(true);
    }

    public void switchPlayerCore(PlayerCoreType playerCoreType) {
        this.mPlayerCoreType = playerCoreType;
    }

    private void initPlayer() {
        if (this.mTextureView == null) {
            this.mTextureView = new ScaleTextureView(this.mContext);
        }
        if (this.mPlayerCoreType == PlayerCoreType.PCT_SYSTEM_MEDIA_PLAYER) {
            this.mPlayer = new SystemMediaPlayerImpl();
        } else {
            this.mPlayer = new RxFFmpegPlayerImpl();
        }
        this.mPlayer.setTextureView(this.mTextureView);
        this.mPlayer.setOnVideoSizeChangedListener(new VideoSizeChangedListener(this));
    }

    /* access modifiers changed from: private */
    public void updateVideoLayoutParams(final int width, final int height, final float dar) {
        post(new Runnable() {
            public void run() {
                RxFFmpegPlayerView.this.mMeasureHelper.setVideoSizeInfo(new MeasureHelper.VideoSizeInfo(width, height, dar));
                RxFFmpegPlayerView.this.mMeasureHelper.setVideoLayoutParams(RxFFmpegPlayerView.this.mTextureView, RxFFmpegPlayerView.this.mContainer);
            }
        });
    }

    public static class VideoSizeChangedListener implements IMediaPlayer.OnVideoSizeChangedListener {
        private WeakReference<RxFFmpegPlayerView> mWeakReference;

        VideoSizeChangedListener(RxFFmpegPlayerView mRxFFmpegPlayerView) {
            this.mWeakReference = new WeakReference<>(mRxFFmpegPlayerView);
        }

        public void onVideoSizeChanged(IMediaPlayer mediaPlayer, int width, int height, float dar) {
            RxFFmpegPlayerView mRxFFmpegPlayerView = (RxFFmpegPlayerView) this.mWeakReference.get();
            if (mRxFFmpegPlayerView != null) {
                mRxFFmpegPlayerView.updateVideoLayoutParams(width, height, dar);
            }
        }
    }

    private void initContainer() {
        FrameLayout frameLayout = new FrameLayout(this.mContext);
        this.mContainer = frameLayout;
        frameLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        addView(this.mContainer, new FrameLayout.LayoutParams(-1, -1));
    }

    public void setPlayerBackgroundColor(int color) {
        FrameLayout frameLayout = this.mContainer;
        if (frameLayout != null) {
            frameLayout.setBackgroundColor(color);
        }
    }

    public void setController(RxFFmpegPlayerController playerController, MeasureHelper.FitModel fitModel) {
        initPlayer();
        setFitModel(fitModel);
        this.mContainer.removeView(this.mPlayerController);
        this.mPlayerController = playerController;
        playerController.setPlayerView(this);
        this.mContainer.addView(this.mPlayerController, new FrameLayout.LayoutParams(-1, -1));
        addTextureView();
    }

    private void addTextureView() {
        this.mContainer.removeView(this.mTextureView);
        this.mContainer.addView(this.mTextureView, 0, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    public TextureView getTextureView() {
        return this.mTextureView;
    }

    public void setTextureViewEnabledTouch(boolean enabled) {
        TextureView textureView = this.mTextureView;
        if (textureView != null && (textureView instanceof ScaleTextureView)) {
            ((ScaleTextureView) textureView).setEnabledTouch(enabled);
        }
    }

    public void setOnCompleteListener(IMediaPlayer.OnCompletionListener listener) {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer != null) {
            baseMediaPlayer.setOnCompleteListener(listener);
        }
    }

    public FrameLayout getContainerView() {
        return this.mContainer;
    }

    public void setFitModel(MeasureHelper.FitModel fitModel) {
        MeasureHelper measureHelper = this.mMeasureHelper;
        if (measureHelper != null && fitModel != null) {
            measureHelper.setFitModel(fitModel);
            this.mMeasureHelper.setDefaultVideoLayoutParams();
        }
    }

    public void play(String videoPath, boolean isLooping) {
        if (this.mPlayer != null && !Helper.isFastClick()) {
            this.mPlayer.play(videoPath, isLooping);
            RxFFmpegPlayerController rxFFmpegPlayerController = this.mPlayerController;
            if (rxFFmpegPlayerController != null) {
                rxFFmpegPlayerController.onResume();
            }
        }
    }

    public void repeatPlay() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer != null) {
            baseMediaPlayer.repeatPlay();
        }
    }

    public void pause() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer != null) {
            baseMediaPlayer.pause();
            RxFFmpegPlayerController rxFFmpegPlayerController = this.mPlayerController;
            if (rxFFmpegPlayerController != null) {
                rxFFmpegPlayerController.onPause();
            }
        }
    }

    public void resume() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer != null) {
            baseMediaPlayer.resume();
            RxFFmpegPlayerController rxFFmpegPlayerController = this.mPlayerController;
            if (rxFFmpegPlayerController != null) {
                rxFFmpegPlayerController.onResume();
            }
        }
    }

    public boolean isPlaying() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        return baseMediaPlayer != null && baseMediaPlayer.isPlaying();
    }

    public boolean isLooping() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        return baseMediaPlayer != null && baseMediaPlayer.isLooping();
    }

    public void setVolume(int percent) {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer != null) {
            baseMediaPlayer.setVolume(percent);
        }
    }

    public int getVolume() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer == null || baseMediaPlayer.getVolume() == -1) {
            return 100;
        }
        return this.mPlayer.getVolume();
    }

    public void setMuteSolo(int mute) {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer != null) {
            baseMediaPlayer.setMuteSolo(mute);
        }
    }

    public int getMuteSolo() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer == null || baseMediaPlayer.getMuteSolo() == -1) {
            return 0;
        }
        return this.mPlayer.getMuteSolo();
    }

    public void release() {
        BaseMediaPlayer baseMediaPlayer = this.mPlayer;
        if (baseMediaPlayer != null) {
            baseMediaPlayer.release();
            this.mPlayer = null;
        }
        setKeepScreenOn(false);
    }

    public boolean isFullScreenModel() {
        return this.mCurrentMode == 1;
    }

    public boolean switchScreen() {
        if (isFullScreenModel()) {
            return exitFullScreen();
        }
        return enterFullScreen();
    }

    public boolean enterFullScreen() {
        ViewGroup decorView;
        if (this.mCurrentMode == 1 || (decorView = Helper.setFullScreen(this.mContext, true)) == null) {
            return false;
        }
        removeView(this.mContainer);
        decorView.addView(this.mContainer, new FrameLayout.LayoutParams(-1, -1));
        this.mCurrentMode = 1;
        return true;
    }

    public boolean exitFullScreen() {
        ViewGroup decorView;
        if (this.mCurrentMode != 1 || (decorView = Helper.setFullScreen(this.mContext, false)) == null) {
            return false;
        }
        decorView.removeView(this.mContainer);
        addView(this.mContainer, new FrameLayout.LayoutParams(-1, -1));
        this.mCurrentMode = 0;
        return false;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MeasureHelper measureHelper = this.mMeasureHelper;
        if (measureHelper != null) {
            measureHelper.setVideoLayoutParams(this.mTextureView, this.mContainer);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int[] size = this.mMeasureHelper.doMeasure(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size[0], size[1]);
    }
}
