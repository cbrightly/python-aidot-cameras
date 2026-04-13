package io.microshow.rxffmpeg.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import io.microshow.rxffmpeg.R;
import io.microshow.rxffmpeg.player.IMediaPlayer;
import io.microshow.rxffmpeg.player.RxFFmpegPlayerView;
import java.lang.ref.WeakReference;

public class RxFFmpegPlayerControllerImpl extends RxFFmpegPlayerController {
    /* access modifiers changed from: private */
    public boolean isSeeking = false;
    /* access modifiers changed from: private */
    public View mBottomPanel;
    public int mPosition;
    /* access modifiers changed from: private */
    public ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public SeekBar mProgressView;
    /* access modifiers changed from: private */
    public TextView mTimeView;
    private ImageView muteImage;
    private ImageView playBtn;
    /* access modifiers changed from: private */
    public View repeatPlay;

    public RxFFmpegPlayerControllerImpl(Context context) {
        super(context);
    }

    public int getLayoutId() {
        return R.layout.rxffmpeg_player_controller;
    }

    public void initView() {
        this.mBottomPanel = findViewById(R.id.bottomPanel);
        this.mProgressView = (SeekBar) findViewById(R.id.progress_view);
        this.mTimeView = (TextView) findViewById(R.id.time_view);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.playBtn = (ImageView) findViewById(R.id.iv_play);
        View findViewById = findViewById(R.id.repeatPlay);
        this.repeatPlay = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                RxFFmpegPlayerControllerImpl.this.mPlayerView.repeatPlay();
                RxFFmpegPlayerControllerImpl.this.repeatPlay.setVisibility(8);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        findViewById(R.id.iv_fullscreen).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                RxFFmpegPlayerView rxFFmpegPlayerView = RxFFmpegPlayerControllerImpl.this.mPlayerView;
                if (rxFFmpegPlayerView != null) {
                    rxFFmpegPlayerView.switchScreen();
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.iv_mute);
        this.muteImage = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                RxFFmpegPlayerControllerImpl.this.switchMute();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.playBtn.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                RxFFmpegPlayerView rxFFmpegPlayerView = RxFFmpegPlayerControllerImpl.this.mPlayerView;
                if (rxFFmpegPlayerView != null) {
                    if (rxFFmpegPlayerView.isPlaying()) {
                        RxFFmpegPlayerControllerImpl.this.mPlayerView.pause();
                    } else {
                        RxFFmpegPlayerControllerImpl.this.mPlayerView.resume();
                    }
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public void switchMute() {
        RxFFmpegPlayerView rxFFmpegPlayerView = this.mPlayerView;
        if (rxFFmpegPlayerView == null) {
            return;
        }
        if (rxFFmpegPlayerView.getVolume() == 0) {
            this.mPlayerView.setVolume(100);
            this.muteImage.setImageResource(R.mipmap.rxffmpeg_player_unmute);
            return;
        }
        this.mPlayerView.setVolume(0);
        this.muteImage.setImageResource(R.mipmap.rxffmpeg_player_mute);
    }

    public void initListener() {
        PlayerListener mPlayerListener = new PlayerListener(this);
        this.mPlayer.setOnLoadingListener(mPlayerListener);
        this.mPlayer.setOnTimeUpdateListener(mPlayerListener);
        this.mPlayer.setOnErrorListener(mPlayerListener);
        this.mPlayer.setOnCompleteListener(mPlayerListener);
        this.mProgressView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RxFFmpegPlayerControllerImpl rxFFmpegPlayerControllerImpl = RxFFmpegPlayerControllerImpl.this;
                rxFFmpegPlayerControllerImpl.mPosition = (rxFFmpegPlayerControllerImpl.mPlayer.getDuration() * progress) / 100;
                if (RxFFmpegPlayerControllerImpl.this.isSeeking) {
                    RxFFmpegPlayerControllerImpl rxFFmpegPlayerControllerImpl2 = RxFFmpegPlayerControllerImpl.this;
                    RxFFmpegPlayerView.PlayerCoreType playerCoreType = rxFFmpegPlayerControllerImpl2.mPlayerView.mPlayerCoreType;
                    if (playerCoreType == RxFFmpegPlayerView.PlayerCoreType.PCT_RXFFMPEG_PLAYER) {
                        rxFFmpegPlayerControllerImpl2.onTimeUpdate((IMediaPlayer) null, rxFFmpegPlayerControllerImpl2.mPosition, rxFFmpegPlayerControllerImpl2.mPlayer.getDuration());
                    } else if (playerCoreType == RxFFmpegPlayerView.PlayerCoreType.PCT_SYSTEM_MEDIA_PLAYER) {
                        rxFFmpegPlayerControllerImpl2.onTimeUpdate((IMediaPlayer) null, rxFFmpegPlayerControllerImpl2.mPosition / 1000, rxFFmpegPlayerControllerImpl2.mPlayer.getDuration() / 1000);
                    }
                    RxFFmpegPlayerControllerImpl rxFFmpegPlayerControllerImpl3 = RxFFmpegPlayerControllerImpl.this;
                    rxFFmpegPlayerControllerImpl3.mPlayer.seekTo(rxFFmpegPlayerControllerImpl3.mPosition);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                boolean unused = RxFFmpegPlayerControllerImpl.this.isSeeking = true;
                RxFFmpegPlayerControllerImpl.this.mPlayer.pause();
            }

            @SensorsDataInstrumented
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBar seekBar2 = seekBar;
                RxFFmpegPlayerControllerImpl.this.mPlayer.resume();
                RxFFmpegPlayerControllerImpl rxFFmpegPlayerControllerImpl = RxFFmpegPlayerControllerImpl.this;
                rxFFmpegPlayerControllerImpl.mPlayer.seekTo(rxFFmpegPlayerControllerImpl.mPosition);
                boolean unused = RxFFmpegPlayerControllerImpl.this.isSeeking = false;
                SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
            }
        });
    }

    public void onCompletion(IMediaPlayer mediaPlayer) {
        post(new Runnable() {
            public void run() {
                RxFFmpegPlayerView rxFFmpegPlayerView = RxFFmpegPlayerControllerImpl.this.mPlayerView;
                if (rxFFmpegPlayerView == null || rxFFmpegPlayerView.isLooping()) {
                    RxFFmpegPlayerControllerImpl.this.repeatPlay.setVisibility(8);
                } else {
                    RxFFmpegPlayerControllerImpl.this.repeatPlay.setVisibility(0);
                }
            }
        });
    }

    public void onError(IMediaPlayer mediaPlayer, final int code, final String msg) {
        post(new Runnable() {
            public void run() {
                Context context = RxFFmpegPlayerControllerImpl.this.getContext();
                Toast.makeText(context, "出错了：code=" + code + ", msg=" + msg, 0).show();
            }
        });
    }

    public void onTimeUpdate(IMediaPlayer mediaPlayer, final int currentTime, final int totalTime) {
        post(new Runnable() {
            @SuppressLint({"SetTextI18n"})
            public void run() {
                if (totalTime <= 0) {
                    RxFFmpegPlayerControllerImpl.this.mBottomPanel.setVisibility(8);
                    return;
                }
                RxFFmpegPlayerControllerImpl.this.mBottomPanel.setVisibility(0);
                TextView access$300 = RxFFmpegPlayerControllerImpl.this.mTimeView;
                StringBuilder sb = new StringBuilder();
                sb.append(Helper.secdsToDateFormat(currentTime, totalTime));
                sb.append(" / ");
                int i = totalTime;
                sb.append(Helper.secdsToDateFormat(i, i));
                access$300.setText(sb.toString());
                if (!RxFFmpegPlayerControllerImpl.this.isSeeking && totalTime > 0) {
                    RxFFmpegPlayerControllerImpl.this.mProgressView.setProgress((currentTime * 100) / totalTime);
                }
            }
        });
    }

    public void onLoading(IMediaPlayer mediaPlayer, final boolean isLoading) {
        post(new Runnable() {
            public void run() {
                RxFFmpegPlayerControllerImpl.this.mProgressBar.setVisibility(isLoading ? 0 : 8);
            }
        });
    }

    public static class PlayerListener implements IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnLoadingListener, IMediaPlayer.OnTimeUpdateListener {
        private WeakReference<RxFFmpegPlayerControllerImpl> mWeakReference;

        PlayerListener(RxFFmpegPlayerControllerImpl playerControllerImpl) {
            this.mWeakReference = new WeakReference<>(playerControllerImpl);
        }

        public void onCompletion(IMediaPlayer mediaPlayer) {
            RxFFmpegPlayerControllerImpl playerControllerImpl = (RxFFmpegPlayerControllerImpl) this.mWeakReference.get();
            if (playerControllerImpl != null) {
                playerControllerImpl.onCompletion(mediaPlayer);
            }
        }

        public void onError(IMediaPlayer mediaPlayer, int err, String msg) {
            RxFFmpegPlayerControllerImpl playerControllerImpl = (RxFFmpegPlayerControllerImpl) this.mWeakReference.get();
            if (playerControllerImpl != null) {
                playerControllerImpl.onError(mediaPlayer, err, msg);
            }
        }

        public void onLoading(IMediaPlayer mediaPlayer, boolean isLoading) {
            RxFFmpegPlayerControllerImpl playerControllerImpl = (RxFFmpegPlayerControllerImpl) this.mWeakReference.get();
            if (playerControllerImpl != null) {
                playerControllerImpl.onLoading(mediaPlayer, isLoading);
            }
        }

        public void onTimeUpdate(IMediaPlayer mediaPlayer, int currentTime, int totalTime) {
            RxFFmpegPlayerControllerImpl playerControllerImpl = (RxFFmpegPlayerControllerImpl) this.mWeakReference.get();
            if (playerControllerImpl != null) {
                playerControllerImpl.onTimeUpdate(mediaPlayer, currentTime, totalTime);
            }
        }
    }

    public void onPause() {
        this.playBtn.setImageResource(R.mipmap.rxffmpeg_player_start);
        this.playBtn.animate().alpha(1.0f).start();
    }

    public void onResume() {
        this.playBtn.setImageResource(R.mipmap.rxffmpeg_player_pause);
        this.playBtn.animate().alpha(1.0f).start();
        RxFFmpegPlayerView rxFFmpegPlayerView = this.mPlayerView;
        if (rxFFmpegPlayerView != null) {
            this.muteImage.setImageResource(rxFFmpegPlayerView.getVolume() == 0 ? R.mipmap.rxffmpeg_player_mute : R.mipmap.rxffmpeg_player_unmute);
        }
    }
}
