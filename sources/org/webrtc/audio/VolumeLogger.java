package org.webrtc.audio;

import android.media.AudioManager;
import androidx.annotation.Nullable;
import androidx.work.WorkRequest;
import java.util.Timer;
import java.util.TimerTask;
import org.webrtc.Logging;

public class VolumeLogger {
    private static final String TAG = "VolumeLogger";
    private static final String THREAD_NAME = "WebRtcVolumeLevelLoggerThread";
    private static final int TIMER_PERIOD_IN_SECONDS = 30;
    /* access modifiers changed from: private */
    public final AudioManager audioManager;
    @Nullable
    private Timer timer;

    public VolumeLogger(AudioManager audioManager2) {
        this.audioManager = audioManager2;
    }

    public void start() {
        Logging.d(TAG, "start" + WebRtcAudioUtils.getThreadInfo());
        if (this.timer == null) {
            Logging.d(TAG, "audio mode is: " + WebRtcAudioUtils.modeToString(this.audioManager.getMode()));
            Timer timer2 = new Timer(THREAD_NAME);
            this.timer = timer2;
            timer2.schedule(new LogVolumeTask(this.audioManager.getStreamMaxVolume(2), this.audioManager.getStreamMaxVolume(0)), 0, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
        }
    }

    public class LogVolumeTask extends TimerTask {
        private final int maxRingVolume;
        private final int maxVoiceCallVolume;

        LogVolumeTask(int maxRingVolume2, int maxVoiceCallVolume2) {
            this.maxRingVolume = maxRingVolume2;
            this.maxVoiceCallVolume = maxVoiceCallVolume2;
        }

        public void run() {
            int mode = VolumeLogger.this.audioManager.getMode();
            if (mode == 1) {
                Logging.d(VolumeLogger.TAG, "STREAM_RING stream volume: " + VolumeLogger.this.audioManager.getStreamVolume(2) + " (max=" + this.maxRingVolume + ")");
            } else if (mode == 3) {
                Logging.d(VolumeLogger.TAG, "VOICE_CALL stream volume: " + VolumeLogger.this.audioManager.getStreamVolume(0) + " (max=" + this.maxVoiceCallVolume + ")");
            }
        }
    }

    public void stop() {
        Logging.d(TAG, "stop" + WebRtcAudioUtils.getThreadInfo());
        Timer timer2 = this.timer;
        if (timer2 != null) {
            timer2.cancel();
            this.timer = null;
        }
    }
}
