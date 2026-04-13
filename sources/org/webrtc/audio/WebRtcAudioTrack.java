package org.webrtc.audio;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Process;
import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import org.webrtc.CalledByNative;
import org.webrtc.Logging;
import org.webrtc.ThreadUtils;
import org.webrtc.audio.JavaAudioDeviceModule;

public class WebRtcAudioTrack {
    private static final int AUDIO_TRACK_START = 0;
    private static final int AUDIO_TRACK_STOP = 1;
    private static final long AUDIO_TRACK_THREAD_JOIN_TIMEOUT_MS = 2000;
    private static final int BITS_PER_SAMPLE = 16;
    private static final int BUFFERS_PER_SECOND = 100;
    private static final int CALLBACK_BUFFER_SIZE_MS = 10;
    private static final int DEFAULT_USAGE = getDefaultUsageAttribute();
    private static final String TAG = "WebRtcAudioTrackExternal";
    @Nullable
    private final AudioAttributes audioAttributes;
    private final AudioManager audioManager;
    @Nullable
    private AudioTrackThread audioThread;
    /* access modifiers changed from: private */
    @Nullable
    public AudioTrack audioTrack;
    /* access modifiers changed from: private */
    public ByteBuffer byteBuffer;
    private final Context context;
    /* access modifiers changed from: private */
    public byte[] emptyBytes;
    @Nullable
    private final JavaAudioDeviceModule.AudioTrackErrorCallback errorCallback;
    private int initialBufferSizeInFrames;
    /* access modifiers changed from: private */
    public long nativeAudioTrack;
    /* access modifiers changed from: private */
    public volatile boolean speakerMute;
    @Nullable
    private final JavaAudioDeviceModule.AudioTrackStateCallback stateCallback;
    private final ThreadUtils.ThreadChecker threadChecker;
    /* access modifiers changed from: private */
    public boolean useLowLatency;
    private final VolumeLogger volumeLogger;

    private static native void nativeCacheDirectBufferAddress(long j, ByteBuffer byteBuffer2);

    /* access modifiers changed from: private */
    public static native void nativeGetPlayoutData(long j, int i);

    private static int getDefaultUsageAttribute() {
        if (Build.VERSION.SDK_INT >= 21) {
            return 2;
        }
        return 0;
    }

    public class AudioTrackThread extends Thread {
        private LowLatencyAudioBufferManager bufferManager = new LowLatencyAudioBufferManager();
        private volatile boolean keepAlive = true;

        public AudioTrackThread(String name) {
            super(name);
        }

        public void run() {
            Process.setThreadPriority(-19);
            Logging.d(WebRtcAudioTrack.TAG, "AudioTrackThread" + WebRtcAudioUtils.getThreadInfo());
            WebRtcAudioTrack.assertTrue(WebRtcAudioTrack.this.audioTrack.getPlayState() == 3);
            WebRtcAudioTrack.this.doAudioTrackStateCallback(0);
            int sizeInBytes = WebRtcAudioTrack.this.byteBuffer.capacity();
            while (this.keepAlive) {
                WebRtcAudioTrack.nativeGetPlayoutData(WebRtcAudioTrack.this.nativeAudioTrack, sizeInBytes);
                WebRtcAudioTrack.assertTrue(sizeInBytes <= WebRtcAudioTrack.this.byteBuffer.remaining());
                if (WebRtcAudioTrack.this.speakerMute) {
                    WebRtcAudioTrack.this.byteBuffer.clear();
                    WebRtcAudioTrack.this.byteBuffer.put(WebRtcAudioTrack.this.emptyBytes);
                    WebRtcAudioTrack.this.byteBuffer.position(0);
                }
                int bytesWritten = writeBytes(WebRtcAudioTrack.this.audioTrack, WebRtcAudioTrack.this.byteBuffer, sizeInBytes);
                if (bytesWritten != sizeInBytes) {
                    Logging.e(WebRtcAudioTrack.TAG, "AudioTrack.write played invalid number of bytes: " + bytesWritten);
                    if (bytesWritten < 0) {
                        this.keepAlive = false;
                        WebRtcAudioTrack webRtcAudioTrack = WebRtcAudioTrack.this;
                        webRtcAudioTrack.reportWebRtcAudioTrackError("AudioTrack.write failed: " + bytesWritten);
                    }
                }
                if (WebRtcAudioTrack.this.useLowLatency) {
                    this.bufferManager.maybeAdjustBufferSize(WebRtcAudioTrack.this.audioTrack);
                }
                WebRtcAudioTrack.this.byteBuffer.rewind();
            }
        }

        private int writeBytes(AudioTrack audioTrack, ByteBuffer byteBuffer, int sizeInBytes) {
            if (Build.VERSION.SDK_INT >= 21) {
                return audioTrack.write(byteBuffer, sizeInBytes, 0);
            }
            return audioTrack.write(byteBuffer.array(), byteBuffer.arrayOffset(), sizeInBytes);
        }

        public void stopThread() {
            Logging.d(WebRtcAudioTrack.TAG, "stopThread");
            this.keepAlive = false;
        }
    }

    @CalledByNative
    WebRtcAudioTrack(Context context2, AudioManager audioManager2) {
        this(context2, audioManager2, (AudioAttributes) null, (JavaAudioDeviceModule.AudioTrackErrorCallback) null, (JavaAudioDeviceModule.AudioTrackStateCallback) null, false);
    }

    WebRtcAudioTrack(Context context2, AudioManager audioManager2, @Nullable AudioAttributes audioAttributes2, @Nullable JavaAudioDeviceModule.AudioTrackErrorCallback errorCallback2, @Nullable JavaAudioDeviceModule.AudioTrackStateCallback stateCallback2, boolean useLowLatency2) {
        ThreadUtils.ThreadChecker threadChecker2 = new ThreadUtils.ThreadChecker();
        this.threadChecker = threadChecker2;
        threadChecker2.detachThread();
        this.context = context2;
        this.audioManager = audioManager2;
        this.audioAttributes = audioAttributes2;
        this.errorCallback = errorCallback2;
        this.stateCallback = stateCallback2;
        this.volumeLogger = new VolumeLogger(audioManager2);
        this.useLowLatency = useLowLatency2;
        Logging.d(TAG, "ctor" + WebRtcAudioUtils.getThreadInfo());
    }

    @CalledByNative
    public void setNativeAudioTrack(long nativeAudioTrack2) {
        this.nativeAudioTrack = nativeAudioTrack2;
    }

    @CalledByNative
    private int initPlayout(int sampleRate, int channels, double bufferSizeFactor) {
        this.threadChecker.checkIsOnValidThread();
        Logging.d(TAG, "initPlayout(sampleRate=" + sampleRate + ", channels=" + channels + ", bufferSizeFactor=" + bufferSizeFactor + ")");
        this.byteBuffer = ByteBuffer.allocateDirect((sampleRate / 100) * channels * 2);
        StringBuilder sb = new StringBuilder();
        sb.append("byteBuffer.capacity: ");
        sb.append(this.byteBuffer.capacity());
        Logging.d(TAG, sb.toString());
        this.emptyBytes = new byte[this.byteBuffer.capacity()];
        nativeCacheDirectBufferAddress(this.nativeAudioTrack, this.byteBuffer);
        int channelConfig = channelCountToConfiguration(channels);
        int minBufferSizeInBytes = (int) (((double) AudioTrack.getMinBufferSize(sampleRate, channelConfig, 2)) * bufferSizeFactor);
        Logging.d(TAG, "minBufferSizeInBytes: " + minBufferSizeInBytes);
        if (minBufferSizeInBytes < this.byteBuffer.capacity()) {
            reportWebRtcAudioTrackInitError("AudioTrack.getMinBufferSize returns an invalid value.");
            return -1;
        }
        if (bufferSizeFactor > 1.0d) {
            this.useLowLatency = false;
        }
        if (this.audioTrack != null) {
            reportWebRtcAudioTrackInitError("Conflict with existing AudioTrack.");
            return -1;
        }
        try {
            if (this.useLowLatency && Build.VERSION.SDK_INT >= 26) {
                this.audioTrack = createAudioTrackOnOreoOrHigher(sampleRate, channelConfig, minBufferSizeInBytes, this.audioAttributes);
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.audioTrack = createAudioTrackOnLollipopOrHigher(sampleRate, channelConfig, minBufferSizeInBytes, this.audioAttributes);
            } else {
                this.audioTrack = createAudioTrackOnLowerThanLollipop(sampleRate, channelConfig, minBufferSizeInBytes);
            }
            AudioTrack audioTrack2 = this.audioTrack;
            if (audioTrack2 == null || audioTrack2.getState() != 1) {
                reportWebRtcAudioTrackInitError("Initialization of audio track failed.");
                releaseAudioResources();
                return -1;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                this.initialBufferSizeInFrames = this.audioTrack.getBufferSizeInFrames();
            } else {
                this.initialBufferSizeInFrames = -1;
            }
            logMainParameters();
            logMainParametersExtended();
            return minBufferSizeInBytes;
        } catch (IllegalArgumentException e) {
            reportWebRtcAudioTrackInitError(e.getMessage());
            releaseAudioResources();
            return -1;
        }
    }

    @CalledByNative
    private boolean startPlayout() {
        this.threadChecker.checkIsOnValidThread();
        this.volumeLogger.start();
        Logging.d(TAG, "startPlayout");
        assertTrue(this.audioTrack != null);
        assertTrue(this.audioThread == null);
        try {
            this.audioTrack.play();
            if (this.audioTrack.getPlayState() != 3) {
                JavaAudioDeviceModule.AudioTrackStartErrorCode audioTrackStartErrorCode = JavaAudioDeviceModule.AudioTrackStartErrorCode.AUDIO_TRACK_START_STATE_MISMATCH;
                reportWebRtcAudioTrackStartError(audioTrackStartErrorCode, "AudioTrack.play failed - incorrect state :" + this.audioTrack.getPlayState());
                releaseAudioResources();
                return false;
            }
            AudioTrackThread audioTrackThread = new AudioTrackThread("AudioTrackJavaThread");
            this.audioThread = audioTrackThread;
            audioTrackThread.start();
            return true;
        } catch (IllegalStateException e) {
            JavaAudioDeviceModule.AudioTrackStartErrorCode audioTrackStartErrorCode2 = JavaAudioDeviceModule.AudioTrackStartErrorCode.AUDIO_TRACK_START_EXCEPTION;
            reportWebRtcAudioTrackStartError(audioTrackStartErrorCode2, "AudioTrack.play failed: " + e.getMessage());
            releaseAudioResources();
            return false;
        }
    }

    @CalledByNative
    private boolean stopPlayout() {
        this.threadChecker.checkIsOnValidThread();
        this.volumeLogger.stop();
        Logging.d(TAG, "stopPlayout");
        assertTrue(this.audioThread != null);
        logUnderrunCount();
        this.audioThread.stopThread();
        Logging.d(TAG, "Stopping the AudioTrackThread...");
        this.audioThread.interrupt();
        if (!ThreadUtils.joinUninterruptibly(this.audioThread, 2000)) {
            Logging.e(TAG, "Join of AudioTrackThread timed out.");
            WebRtcAudioUtils.logAudioState(TAG, this.context, this.audioManager);
        }
        Logging.d(TAG, "AudioTrackThread has now been stopped.");
        this.audioThread = null;
        if (this.audioTrack != null) {
            Logging.d(TAG, "Calling AudioTrack.stop...");
            try {
                this.audioTrack.stop();
                Logging.d(TAG, "AudioTrack.stop is done.");
                doAudioTrackStateCallback(1);
            } catch (IllegalStateException e) {
                Logging.e(TAG, "AudioTrack.stop failed: " + e.getMessage());
            }
        }
        releaseAudioResources();
        return true;
    }

    @CalledByNative
    private int getStreamMaxVolume() {
        this.threadChecker.checkIsOnValidThread();
        Logging.d(TAG, "getStreamMaxVolume");
        return this.audioManager.getStreamMaxVolume(0);
    }

    @CalledByNative
    private boolean setStreamVolume(int volume) {
        this.threadChecker.checkIsOnValidThread();
        Logging.d(TAG, "setStreamVolume(" + volume + ")");
        if (isVolumeFixed()) {
            Logging.e(TAG, "The device implements a fixed volume policy.");
            return false;
        }
        this.audioManager.setStreamVolume(0, volume, 0);
        return true;
    }

    private boolean isVolumeFixed() {
        if (Build.VERSION.SDK_INT < 21) {
            return false;
        }
        return this.audioManager.isVolumeFixed();
    }

    @CalledByNative
    private int getStreamVolume() {
        this.threadChecker.checkIsOnValidThread();
        Logging.d(TAG, "getStreamVolume");
        return this.audioManager.getStreamVolume(0);
    }

    @CalledByNative
    private int GetPlayoutUnderrunCount() {
        if (Build.VERSION.SDK_INT < 24) {
            return -2;
        }
        AudioTrack audioTrack2 = this.audioTrack;
        if (audioTrack2 != null) {
            return audioTrack2.getUnderrunCount();
        }
        return -1;
    }

    private void logMainParameters() {
        Logging.d(TAG, "AudioTrack: session ID: " + this.audioTrack.getAudioSessionId() + ", channels: " + this.audioTrack.getChannelCount() + ", sample rate: " + this.audioTrack.getSampleRate() + ", max gain: " + AudioTrack.getMaxVolume());
    }

    private static void logNativeOutputSampleRate(int requestedSampleRateInHz) {
        int nativeOutputSampleRate = AudioTrack.getNativeOutputSampleRate(0);
        Logging.d(TAG, "nativeOutputSampleRate: " + nativeOutputSampleRate);
        if (requestedSampleRateInHz != nativeOutputSampleRate) {
            Logging.w(TAG, "Unable to use fast mode since requested sample rate is not native");
        }
    }

    private static AudioAttributes getAudioAttributes(@Nullable AudioAttributes overrideAttributes) {
        AudioAttributes.Builder attributesBuilder = new AudioAttributes.Builder().setUsage(DEFAULT_USAGE).setContentType(1);
        if (overrideAttributes != null) {
            if (overrideAttributes.getUsage() != 0) {
                attributesBuilder.setUsage(overrideAttributes.getUsage());
            }
            if (overrideAttributes.getContentType() != 0) {
                attributesBuilder.setContentType(overrideAttributes.getContentType());
            }
            attributesBuilder.setFlags(overrideAttributes.getFlags());
            if (Build.VERSION.SDK_INT >= 29) {
                attributesBuilder = applyAttributesOnQOrHigher(attributesBuilder, overrideAttributes);
            }
        }
        return attributesBuilder.build();
    }

    @TargetApi(21)
    private static AudioTrack createAudioTrackOnLollipopOrHigher(int sampleRateInHz, int channelConfig, int bufferSizeInBytes, @Nullable AudioAttributes overrideAttributes) {
        Logging.d(TAG, "createAudioTrackOnLollipopOrHigher");
        logNativeOutputSampleRate(sampleRateInHz);
        return new AudioTrack(getAudioAttributes(overrideAttributes), new AudioFormat.Builder().setEncoding(2).setSampleRate(sampleRateInHz).setChannelMask(channelConfig).build(), bufferSizeInBytes, 1, 0);
    }

    @TargetApi(26)
    private static AudioTrack createAudioTrackOnOreoOrHigher(int sampleRateInHz, int channelConfig, int bufferSizeInBytes, @Nullable AudioAttributes overrideAttributes) {
        Logging.d(TAG, "createAudioTrackOnOreoOrHigher");
        logNativeOutputSampleRate(sampleRateInHz);
        return new AudioTrack.Builder().setAudioAttributes(getAudioAttributes(overrideAttributes)).setAudioFormat(new AudioFormat.Builder().setEncoding(2).setSampleRate(sampleRateInHz).setChannelMask(channelConfig).build()).setBufferSizeInBytes(bufferSizeInBytes).setPerformanceMode(1).setTransferMode(1).setSessionId(0).build();
    }

    @TargetApi(29)
    private static AudioAttributes.Builder applyAttributesOnQOrHigher(AudioAttributes.Builder builder, AudioAttributes overrideAttributes) {
        return builder.setAllowedCapturePolicy(overrideAttributes.getAllowedCapturePolicy());
    }

    private static AudioTrack createAudioTrackOnLowerThanLollipop(int sampleRateInHz, int channelConfig, int bufferSizeInBytes) {
        return new AudioTrack(0, sampleRateInHz, channelConfig, 2, bufferSizeInBytes, 1);
    }

    private void logBufferSizeInFrames() {
        if (Build.VERSION.SDK_INT >= 23) {
            Logging.d(TAG, "AudioTrack: buffer size in frames: " + this.audioTrack.getBufferSizeInFrames());
        }
    }

    @CalledByNative
    private int getBufferSizeInFrames() {
        if (Build.VERSION.SDK_INT >= 23) {
            return this.audioTrack.getBufferSizeInFrames();
        }
        return -1;
    }

    @CalledByNative
    private int getInitialBufferSizeInFrames() {
        return this.initialBufferSizeInFrames;
    }

    private void logBufferCapacityInFrames() {
        if (Build.VERSION.SDK_INT >= 24) {
            Logging.d(TAG, "AudioTrack: buffer capacity in frames: " + this.audioTrack.getBufferCapacityInFrames());
        }
    }

    private void logMainParametersExtended() {
        logBufferSizeInFrames();
        logBufferCapacityInFrames();
    }

    private void logUnderrunCount() {
        if (Build.VERSION.SDK_INT >= 24) {
            Logging.d(TAG, "underrun count: " + this.audioTrack.getUnderrunCount());
        }
    }

    /* access modifiers changed from: private */
    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected condition to be true");
        }
    }

    private int channelCountToConfiguration(int channels) {
        return channels == 1 ? 4 : 12;
    }

    public void setSpeakerMute(boolean mute) {
        Logging.w(TAG, "setSpeakerMute(" + mute + ")");
        this.speakerMute = mute;
    }

    private void releaseAudioResources() {
        Logging.d(TAG, "releaseAudioResources");
        AudioTrack audioTrack2 = this.audioTrack;
        if (audioTrack2 != null) {
            audioTrack2.release();
            this.audioTrack = null;
        }
    }

    private void reportWebRtcAudioTrackInitError(String errorMessage) {
        Logging.e(TAG, "Init playout error: " + errorMessage);
        WebRtcAudioUtils.logAudioState(TAG, this.context, this.audioManager);
        JavaAudioDeviceModule.AudioTrackErrorCallback audioTrackErrorCallback = this.errorCallback;
        if (audioTrackErrorCallback != null) {
            audioTrackErrorCallback.onWebRtcAudioTrackInitError(errorMessage);
        }
    }

    private void reportWebRtcAudioTrackStartError(JavaAudioDeviceModule.AudioTrackStartErrorCode errorCode, String errorMessage) {
        Logging.e(TAG, "Start playout error: " + errorCode + ". " + errorMessage);
        WebRtcAudioUtils.logAudioState(TAG, this.context, this.audioManager);
        JavaAudioDeviceModule.AudioTrackErrorCallback audioTrackErrorCallback = this.errorCallback;
        if (audioTrackErrorCallback != null) {
            audioTrackErrorCallback.onWebRtcAudioTrackStartError(errorCode, errorMessage);
        }
    }

    /* access modifiers changed from: private */
    public void reportWebRtcAudioTrackError(String errorMessage) {
        Logging.e(TAG, "Run-time playback error: " + errorMessage);
        WebRtcAudioUtils.logAudioState(TAG, this.context, this.audioManager);
        JavaAudioDeviceModule.AudioTrackErrorCallback audioTrackErrorCallback = this.errorCallback;
        if (audioTrackErrorCallback != null) {
            audioTrackErrorCallback.onWebRtcAudioTrackError(errorMessage);
        }
    }

    /* access modifiers changed from: private */
    public void doAudioTrackStateCallback(int audioState) {
        Logging.d(TAG, "doAudioTrackStateCallback: " + audioState);
        JavaAudioDeviceModule.AudioTrackStateCallback audioTrackStateCallback = this.stateCallback;
        if (audioTrackStateCallback == null) {
            return;
        }
        if (audioState == 0) {
            audioTrackStateCallback.onWebRtcAudioTrackStart();
        } else if (audioState == 1) {
            audioTrackStateCallback.onWebRtcAudioTrackStop();
        } else {
            Logging.e(TAG, "Invalid audio state");
        }
    }
}
