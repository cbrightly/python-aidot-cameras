package androidx.camera.core.impl;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.UseCase;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.c;
import androidx.camera.core.internal.d;
import androidx.camera.core.internal.e;
import androidx.core.util.Consumer;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public final class VideoCaptureConfig implements UseCaseConfig<VideoCapture>, ImageOutputConfig, ThreadConfig {
    public static final Config.Option<Integer> OPTION_AUDIO_BIT_RATE;
    public static final Config.Option<Integer> OPTION_AUDIO_CHANNEL_COUNT;
    public static final Config.Option<Integer> OPTION_AUDIO_MIN_BUFFER_SIZE;
    public static final Config.Option<Integer> OPTION_AUDIO_RECORD_SOURCE;
    public static final Config.Option<Integer> OPTION_AUDIO_SAMPLE_RATE;
    public static final Config.Option<Integer> OPTION_BIT_RATE;
    public static final Config.Option<Integer> OPTION_INTRA_FRAME_INTERVAL;
    public static final Config.Option<Integer> OPTION_VIDEO_FRAME_RATE;
    private final OptionsBundle mConfig;

    public /* synthetic */ boolean containsOption(Config.Option option) {
        return a0.a(this, option);
    }

    public /* synthetic */ void findOptions(String str, Config.OptionMatcher optionMatcher) {
        a0.b(this, str, optionMatcher);
    }

    public /* synthetic */ Consumer getAttachedUseCasesUpdateListener() {
        return b0.a(this);
    }

    public /* synthetic */ Consumer getAttachedUseCasesUpdateListener(Consumer consumer) {
        return b0.b(this, consumer);
    }

    public /* synthetic */ Executor getBackgroundExecutor() {
        return d.a(this);
    }

    public /* synthetic */ Executor getBackgroundExecutor(Executor executor) {
        return d.b(this, executor);
    }

    public /* synthetic */ CameraSelector getCameraSelector() {
        return b0.c(this);
    }

    public /* synthetic */ CameraSelector getCameraSelector(CameraSelector cameraSelector) {
        return b0.d(this, cameraSelector);
    }

    public /* synthetic */ CaptureConfig.OptionUnpacker getCaptureOptionUnpacker() {
        return b0.e(this);
    }

    public /* synthetic */ CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker) {
        return b0.f(this, optionUnpacker);
    }

    public /* synthetic */ CaptureConfig getDefaultCaptureConfig() {
        return b0.g(this);
    }

    public /* synthetic */ CaptureConfig getDefaultCaptureConfig(CaptureConfig captureConfig) {
        return b0.h(this, captureConfig);
    }

    public /* synthetic */ Size getDefaultResolution() {
        return z.a(this);
    }

    public /* synthetic */ Size getDefaultResolution(Size size) {
        return z.b(this, size);
    }

    public /* synthetic */ SessionConfig getDefaultSessionConfig() {
        return b0.i(this);
    }

    public /* synthetic */ SessionConfig getDefaultSessionConfig(SessionConfig sessionConfig) {
        return b0.j(this, sessionConfig);
    }

    public /* synthetic */ Size getMaxResolution() {
        return z.c(this);
    }

    public /* synthetic */ Size getMaxResolution(Size size) {
        return z.d(this, size);
    }

    public /* synthetic */ Config.OptionPriority getOptionPriority(Config.Option option) {
        return a0.c(this, option);
    }

    public /* synthetic */ Set getPriorities(Config.Option option) {
        return a0.d(this, option);
    }

    public /* synthetic */ SessionConfig.OptionUnpacker getSessionOptionUnpacker() {
        return b0.k(this);
    }

    public /* synthetic */ SessionConfig.OptionUnpacker getSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker) {
        return b0.l(this, optionUnpacker);
    }

    public /* synthetic */ List getSupportedResolutions() {
        return z.e(this);
    }

    public /* synthetic */ List getSupportedResolutions(List list) {
        return z.f(this, list);
    }

    public /* synthetic */ int getSurfaceOccupancyPriority() {
        return b0.m(this);
    }

    public /* synthetic */ int getSurfaceOccupancyPriority(int i) {
        return b0.n(this, i);
    }

    public /* synthetic */ int getTargetAspectRatio() {
        return z.g(this);
    }

    public /* synthetic */ Class getTargetClass() {
        return c.a(this);
    }

    public /* synthetic */ Class getTargetClass(Class cls) {
        return c.b(this, cls);
    }

    public /* synthetic */ String getTargetName() {
        return c.c(this);
    }

    public /* synthetic */ String getTargetName(String str) {
        return c.d(this, str);
    }

    public /* synthetic */ Size getTargetResolution() {
        return z.h(this);
    }

    public /* synthetic */ Size getTargetResolution(Size size) {
        return z.i(this, size);
    }

    public /* synthetic */ int getTargetRotation() {
        return z.j(this);
    }

    public /* synthetic */ int getTargetRotation(int i) {
        return z.k(this, i);
    }

    public /* synthetic */ UseCase.EventCallback getUseCaseEventCallback() {
        return e.a(this);
    }

    public /* synthetic */ UseCase.EventCallback getUseCaseEventCallback(UseCase.EventCallback eventCallback) {
        return e.b(this, eventCallback);
    }

    public /* synthetic */ boolean hasTargetAspectRatio() {
        return z.l(this);
    }

    public /* synthetic */ Set listOptions() {
        return a0.e(this);
    }

    public /* synthetic */ Object retrieveOption(Config.Option option) {
        return a0.f(this, option);
    }

    public /* synthetic */ Object retrieveOption(Config.Option option, Object obj) {
        return a0.g(this, option, obj);
    }

    public /* synthetic */ Object retrieveOptionWithPriority(Config.Option option, Config.OptionPriority optionPriority) {
        return a0.h(this, option, optionPriority);
    }

    static {
        Class cls = Integer.TYPE;
        OPTION_VIDEO_FRAME_RATE = Config.Option.create("camerax.core.videoCapture.recordingFrameRate", cls);
        OPTION_BIT_RATE = Config.Option.create("camerax.core.videoCapture.bitRate", cls);
        OPTION_INTRA_FRAME_INTERVAL = Config.Option.create("camerax.core.videoCapture.intraFrameInterval", cls);
        OPTION_AUDIO_BIT_RATE = Config.Option.create("camerax.core.videoCapture.audioBitRate", cls);
        OPTION_AUDIO_SAMPLE_RATE = Config.Option.create("camerax.core.videoCapture.audioSampleRate", cls);
        OPTION_AUDIO_CHANNEL_COUNT = Config.Option.create("camerax.core.videoCapture.audioChannelCount", cls);
        OPTION_AUDIO_RECORD_SOURCE = Config.Option.create("camerax.core.videoCapture.audioRecordSource", cls);
        OPTION_AUDIO_MIN_BUFFER_SIZE = Config.Option.create("camerax.core.videoCapture.audioMinBufferSize", cls);
    }

    public VideoCaptureConfig(@NonNull OptionsBundle config) {
        this.mConfig = config;
    }

    public int getVideoFrameRate(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_VIDEO_FRAME_RATE, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getVideoFrameRate() {
        return ((Integer) retrieveOption(OPTION_VIDEO_FRAME_RATE)).intValue();
    }

    public int getBitRate(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_BIT_RATE, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getBitRate() {
        return ((Integer) retrieveOption(OPTION_BIT_RATE)).intValue();
    }

    public int getIFrameInterval(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_INTRA_FRAME_INTERVAL, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getIFrameInterval() {
        return ((Integer) retrieveOption(OPTION_INTRA_FRAME_INTERVAL)).intValue();
    }

    public int getAudioBitRate(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_AUDIO_BIT_RATE, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getAudioBitRate() {
        return ((Integer) retrieveOption(OPTION_AUDIO_BIT_RATE)).intValue();
    }

    public int getAudioSampleRate(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_AUDIO_SAMPLE_RATE, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getAudioSampleRate() {
        return ((Integer) retrieveOption(OPTION_AUDIO_SAMPLE_RATE)).intValue();
    }

    public int getAudioChannelCount(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_AUDIO_CHANNEL_COUNT, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getAudioChannelCount() {
        return ((Integer) retrieveOption(OPTION_AUDIO_CHANNEL_COUNT)).intValue();
    }

    public int getAudioRecordSource(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_AUDIO_RECORD_SOURCE, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getAudioRecordSource() {
        return ((Integer) retrieveOption(OPTION_AUDIO_RECORD_SOURCE)).intValue();
    }

    public int getAudioMinBufferSize(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_AUDIO_MIN_BUFFER_SIZE, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getAudioMinBufferSize() {
        return ((Integer) retrieveOption(OPTION_AUDIO_MIN_BUFFER_SIZE)).intValue();
    }

    public int getInputFormat() {
        return 34;
    }

    @NonNull
    public Config getConfig() {
        return this.mConfig;
    }
}
