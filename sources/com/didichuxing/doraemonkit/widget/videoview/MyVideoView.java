package com.didichuxing.doraemonkit.widget.videoview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.didichuxing.doraemonkit.widget.videoview.CustomVideoView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import org.apache.http.l;

public class MyVideoView extends RelativeLayout {
    private static final int UPDATE_PROGRESS = 1;
    /* access modifiers changed from: private */
    public ImageView btnController;
    private ImageView btnScreen;
    /* access modifiers changed from: private */
    public FrameLayout flLight;
    /* access modifiers changed from: private */
    public FrameLayout flVolume;
    /* access modifiers changed from: private */
    public boolean isVerticalScreen;
    private ImageView ivVolume;
    private LinearLayout llyController;
    /* access modifiers changed from: private */
    public Activity mActivity;
    /* access modifiers changed from: private */
    public AudioManager mAudioManager;
    private Context mContext;
    /* access modifiers changed from: private */
    public Handler mHandler;
    private String mVideoPath;
    private RelativeLayout rlContainer;
    /* access modifiers changed from: private */
    public int screenHeight;
    private int screenWidth;
    /* access modifiers changed from: private */
    public SeekBar seekbarProgress;
    /* access modifiers changed from: private */
    public SeekBar seekbarVolume;
    /* access modifiers changed from: private */
    public int state;
    /* access modifiers changed from: private */
    public TextView tvCurrentProgress;
    /* access modifiers changed from: private */
    public TextView tvTotalProgress;
    private View videoLayout;
    private int videoPos;
    /* access modifiers changed from: private */
    public CustomVideoView videoView;

    public MyVideoView(Context context) {
        super(context, (AttributeSet) null);
        this.state = 0;
        this.isVerticalScreen = true;
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    int currentTime = MyVideoView.this.videoView.getCurrentPosition();
                    int totalTime = MyVideoView.this.videoView.getDuration() - 100;
                    if (currentTime >= totalTime) {
                        MyVideoView.this.videoView.pause();
                        MyVideoView.this.videoView.seekTo(0);
                        MyVideoView.this.seekbarProgress.setProgress(0);
                        MyVideoView.this.btnController.setImageResource(R.drawable.dk_btn_play_style);
                        MyVideoView myVideoView = MyVideoView.this;
                        myVideoView.updateTextViewFormat(myVideoView.tvCurrentProgress, 0);
                        MyVideoView.this.mHandler.removeMessages(1);
                        return;
                    }
                    MyVideoView.this.seekbarProgress.setMax(totalTime);
                    MyVideoView.this.seekbarProgress.setProgress(currentTime);
                    MyVideoView myVideoView2 = MyVideoView.this;
                    myVideoView2.updateTextViewFormat(myVideoView2.tvCurrentProgress, currentTime);
                    MyVideoView myVideoView3 = MyVideoView.this;
                    myVideoView3.updateTextViewFormat(myVideoView3.tvTotalProgress, totalTime);
                    MyVideoView.this.mHandler.sendEmptyMessageDelayed(1, 100);
                }
            }
        };
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.state = 0;
        this.isVerticalScreen = true;
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    int currentTime = MyVideoView.this.videoView.getCurrentPosition();
                    int totalTime = MyVideoView.this.videoView.getDuration() - 100;
                    if (currentTime >= totalTime) {
                        MyVideoView.this.videoView.pause();
                        MyVideoView.this.videoView.seekTo(0);
                        MyVideoView.this.seekbarProgress.setProgress(0);
                        MyVideoView.this.btnController.setImageResource(R.drawable.dk_btn_play_style);
                        MyVideoView myVideoView = MyVideoView.this;
                        myVideoView.updateTextViewFormat(myVideoView.tvCurrentProgress, 0);
                        MyVideoView.this.mHandler.removeMessages(1);
                        return;
                    }
                    MyVideoView.this.seekbarProgress.setMax(totalTime);
                    MyVideoView.this.seekbarProgress.setProgress(currentTime);
                    MyVideoView myVideoView2 = MyVideoView.this;
                    myVideoView2.updateTextViewFormat(myVideoView2.tvCurrentProgress, currentTime);
                    MyVideoView myVideoView3 = MyVideoView.this;
                    myVideoView3.updateTextViewFormat(myVideoView3.tvTotalProgress, totalTime);
                    MyVideoView.this.mHandler.sendEmptyMessageDelayed(1, 100);
                }
            }
        };
        this.mContext = context;
        init();
        initView();
        initData();
        initListener();
    }

    public void register(Activity activity) {
        this.mActivity = activity;
    }

    public void setVideoPath(String path) {
        this.mVideoPath = path;
        if (path.startsWith(l.DEFAULT_SCHEME_NAME) || path.startsWith("https")) {
            this.videoView.setVideoURI(Uri.parse(path));
        } else {
            this.videoView.setVideoPath(this.mVideoPath);
        }
    }

    private void init() {
        this.screenWidth = UIUtils.getWidthPixels();
        this.screenHeight = UIUtils.getRealHeightPixels();
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
    }

    private void initView() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.dk_video_layout, this, true);
        this.videoLayout = inflate;
        this.flVolume = (FrameLayout) inflate.findViewById(R.id.fl_volume);
        this.flLight = (FrameLayout) this.videoLayout.findViewById(R.id.fl_light);
        this.videoView = (CustomVideoView) this.videoLayout.findViewById(R.id.videoView);
        this.seekbarProgress = (SeekBar) this.videoLayout.findViewById(R.id.seekbar_progress);
        this.seekbarVolume = (SeekBar) this.videoLayout.findViewById(R.id.seekbar_volume);
        this.btnController = (ImageView) this.videoLayout.findViewById(R.id.btn_controller);
        this.btnScreen = (ImageView) this.videoLayout.findViewById(R.id.btn_screen);
        this.tvCurrentProgress = (TextView) this.videoLayout.findViewById(R.id.tv_currentProgress);
        this.tvTotalProgress = (TextView) this.videoLayout.findViewById(R.id.tv_totalProgress);
        this.ivVolume = (ImageView) this.videoLayout.findViewById(R.id.iv_volume);
        this.llyController = (LinearLayout) this.videoLayout.findViewById(R.id.lly_controller);
        this.rlContainer = (RelativeLayout) this.videoLayout.findViewById(R.id.rl_container);
    }

    private void initData() {
        this.seekbarVolume.setProgress(this.mAudioManager.getStreamVolume(3));
    }

    private void initListener() {
        this.btnScreen.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (MyVideoView.this.isVerticalScreen) {
                    MyVideoView.this.mActivity.setRequestedOrientation(0);
                } else {
                    MyVideoView.this.mActivity.setRequestedOrientation(1);
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.btnController.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                if (MyVideoView.this.videoView.isPlaying()) {
                    MyVideoView.this.btnController.setImageResource(R.drawable.dk_btn_play_style);
                    MyVideoView.this.videoView.pause();
                    MyVideoView.this.mHandler.removeMessages(1);
                } else {
                    MyVideoView.this.btnController.setImageResource(R.drawable.dk_btn_pause_style);
                    MyVideoView.this.videoView.start();
                    MyVideoView.this.mHandler.sendEmptyMessage(1);
                    if (MyVideoView.this.state == 0) {
                        int unused = MyVideoView.this.state = 1;
                    }
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        this.videoView.setStateListener(new CustomVideoView.StateListener() {
            public void changeVolumn(float detlaY) {
                if (MyVideoView.this.flVolume.getVisibility() == 8) {
                    MyVideoView.this.flVolume.setVisibility(0);
                }
                int volume = Math.max(0, MyVideoView.this.mAudioManager.getStreamVolume(3) - ((int) (((detlaY / ((float) MyVideoView.this.screenHeight)) * ((float) MyVideoView.this.mAudioManager.getStreamMaxVolume(3))) * 3.0f)));
                MyVideoView.this.mAudioManager.setStreamVolume(3, volume, 0);
                MyVideoView.this.seekbarVolume.setProgress(volume);
            }

            public void changeBrightness(float detlaY) {
                if (MyVideoView.this.flLight.getVisibility() == 8) {
                    MyVideoView.this.flLight.setVisibility(0);
                }
                WindowManager.LayoutParams wml = MyVideoView.this.mActivity.getWindow().getAttributes();
                float screenBrightness = wml.screenBrightness + (((-detlaY) / ((float) MyVideoView.this.screenHeight)) / 5.0f);
                if (screenBrightness > 1.0f) {
                    screenBrightness = 1.0f;
                } else if (screenBrightness < 0.01f) {
                    screenBrightness = 0.01f;
                }
                wml.screenBrightness = screenBrightness;
                MyVideoView.this.mActivity.getWindow().setAttributes(wml);
            }

            public void hideHint() {
                if (MyVideoView.this.flLight.getVisibility() == 0) {
                    MyVideoView.this.flLight.setVisibility(8);
                }
                if (MyVideoView.this.flVolume.getVisibility() == 0) {
                    MyVideoView.this.flVolume.setVisibility(8);
                }
            }
        });
        this.seekbarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MyVideoView.this.mAudioManager.setStreamVolume(3, progress, 0);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @SensorsDataInstrumented
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBar seekBar2 = seekBar;
                SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
            }
        });
        this.seekbarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MyVideoView myVideoView = MyVideoView.this;
                myVideoView.updateTextViewFormat(myVideoView.tvCurrentProgress, progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                MyVideoView.this.mHandler.removeMessages(1);
            }

            @SensorsDataInstrumented
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBar seekBar2 = seekBar;
                if (MyVideoView.this.state != 0) {
                    MyVideoView.this.mHandler.sendEmptyMessage(1);
                }
                MyVideoView.this.videoView.seekTo(seekBar2.getProgress());
                SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 1) {
            this.isVerticalScreen = true;
            this.ivVolume.setVisibility(8);
            this.seekbarVolume.setVisibility(8);
            setVideoViewScale(-1, UIUtils.dp2px(290.0f));
            this.mActivity.getWindow().clearFlags(1024);
            this.mActivity.getWindow().addFlags(2048);
            return;
        }
        this.isVerticalScreen = false;
        this.ivVolume.setVisibility(0);
        this.seekbarVolume.setVisibility(0);
        setVideoViewScale(-1, -1);
        this.mActivity.getWindow().clearFlags(2048);
        this.mActivity.getWindow().addFlags(1024);
    }

    public void setVideoViewScale(int width, int height) {
        ViewGroup.LayoutParams videoViewLayoutParams = this.videoView.getLayoutParams();
        videoViewLayoutParams.width = width;
        videoViewLayoutParams.height = height;
        this.videoView.setLayoutParams(videoViewLayoutParams);
        RelativeLayout.LayoutParams rlContainerLayoutParams = (RelativeLayout.LayoutParams) this.rlContainer.getLayoutParams();
        rlContainerLayoutParams.width = width;
        rlContainerLayoutParams.height = height;
        this.rlContainer.setLayoutParams(rlContainerLayoutParams);
    }

    /* access modifiers changed from: private */
    public void updateTextViewFormat(TextView tv2, int m) {
        String result;
        int second = m / 1000;
        int hour = second / 3600;
        int minute = (second % 3600) / 60;
        int ss = second % 60;
        if (hour != 0) {
            result = String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(ss)});
        } else {
            result = String.format("%02d:%02d", new Object[]{Integer.valueOf(minute), Integer.valueOf(ss)});
        }
        tv2.setText(result);
    }

    public void onPause() {
        this.videoPos = this.videoView.getCurrentPosition();
        this.videoView.stopPlayback();
        this.mHandler.removeMessages(1);
    }

    public void onResume() {
        this.videoView.seekTo(this.videoPos);
        this.videoView.resume();
    }

    public void setProgressBg(Drawable drawable) {
        this.seekbarProgress.setProgressDrawable(drawable);
    }

    public void setVolumeBg(Drawable drawable) {
        this.seekbarVolume.setProgressDrawable(drawable);
    }
}
