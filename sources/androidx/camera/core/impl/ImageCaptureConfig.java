package androidx.camera.core.impl;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageReaderProxyProvider;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.internal.IoConfig;
import androidx.camera.core.internal.c;
import androidx.camera.core.internal.e;
import androidx.core.util.Consumer;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public final class ImageCaptureConfig implements UseCaseConfig<ImageCapture>, ImageOutputConfig, IoConfig {
    public static final Config.Option<Integer> OPTION_BUFFER_FORMAT;
    public static final Config.Option<CaptureBundle> OPTION_CAPTURE_BUNDLE = Config.Option.create("camerax.core.imageCapture.captureBundle", CaptureBundle.class);
    public static final Config.Option<CaptureProcessor> OPTION_CAPTURE_PROCESSOR = Config.Option.create("camerax.core.imageCapture.captureProcessor", CaptureProcessor.class);
    public static final Config.Option<Integer> OPTION_FLASH_MODE;
    public static final Config.Option<Integer> OPTION_IMAGE_CAPTURE_MODE;
    public static final Config.Option<ImageReaderProxyProvider> OPTION_IMAGE_READER_PROXY_PROVIDER = Config.Option.create("camerax.core.imageCapture.imageReaderProxyProvider", ImageReaderProxyProvider.class);
    public static final Config.Option<Integer> OPTION_MAX_CAPTURE_STAGES;
    public static final Config.Option<Boolean> OPTION_USE_SOFTWARE_JPEG_ENCODER = Config.Option.create("camerax.core.imageCapture.useSoftwareJpegEncoder", Boolean.TYPE);
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
        Class<Integer> cls = Integer.class;
        Class cls2 = Integer.TYPE;
        OPTION_IMAGE_CAPTURE_MODE = Config.Option.create("camerax.core.imageCapture.captureMode", cls2);
        OPTION_FLASH_MODE = Config.Option.create("camerax.core.imageCapture.flashMode", cls2);
        OPTION_BUFFER_FORMAT = Config.Option.create("camerax.core.imageCapture.bufferFormat", cls);
        OPTION_MAX_CAPTURE_STAGES = Config.Option.create("camerax.core.imageCapture.maxCaptureStages", cls);
    }

    public ImageCaptureConfig(@NonNull OptionsBundle config) {
        this.mConfig = config;
    }

    @NonNull
    public Config getConfig() {
        return this.mConfig;
    }

    public boolean hasCaptureMode() {
        return containsOption(OPTION_IMAGE_CAPTURE_MODE);
    }

    public int getCaptureMode() {
        return ((Integer) retrieveOption(OPTION_IMAGE_CAPTURE_MODE)).intValue();
    }

    public int getFlashMode(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_FLASH_MODE, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getFlashMode() {
        return ((Integer) retrieveOption(OPTION_FLASH_MODE)).intValue();
    }

    @Nullable
    public CaptureBundle getCaptureBundle(@Nullable CaptureBundle valueIfMissing) {
        return (CaptureBundle) retrieveOption(OPTION_CAPTURE_BUNDLE, valueIfMissing);
    }

    @NonNull
    public CaptureBundle getCaptureBundle() {
        return (CaptureBundle) retrieveOption(OPTION_CAPTURE_BUNDLE);
    }

    @Nullable
    public CaptureProcessor getCaptureProcessor(@Nullable CaptureProcessor valueIfMissing) {
        return (CaptureProcessor) retrieveOption(OPTION_CAPTURE_PROCESSOR, valueIfMissing);
    }

    @NonNull
    public CaptureProcessor getCaptureProcessor() {
        return (CaptureProcessor) retrieveOption(OPTION_CAPTURE_PROCESSOR);
    }

    @Nullable
    public Integer getBufferFormat(@Nullable Integer valueIfMissing) {
        return (Integer) retrieveOption(OPTION_BUFFER_FORMAT, valueIfMissing);
    }

    @NonNull
    public Integer getBufferFormat() {
        return (Integer) retrieveOption(OPTION_BUFFER_FORMAT);
    }

    public int getInputFormat() {
        return ((Integer) retrieveOption(ImageInputConfig.OPTION_INPUT_FORMAT)).intValue();
    }

    public int getMaxCaptureStages(int valueIfMissing) {
        return ((Integer) retrieveOption(OPTION_MAX_CAPTURE_STAGES, Integer.valueOf(valueIfMissing))).intValue();
    }

    public int getMaxCaptureStages() {
        return ((Integer) retrieveOption(OPTION_MAX_CAPTURE_STAGES)).intValue();
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ImageReaderProxyProvider getImageReaderProxyProvider() {
        return (ImageReaderProxyProvider) retrieveOption(OPTION_IMAGE_READER_PROXY_PROVIDER, null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isSoftwareJpegEncoderRequested() {
        return ((Boolean) retrieveOption(OPTION_USE_SOFTWARE_JPEG_ENCODER, false)).booleanValue();
    }

    @Nullable
    public Executor getIoExecutor(@Nullable Executor valueIfMissing) {
        return (Executor) retrieveOption(IoConfig.OPTION_IO_EXECUTOR, valueIfMissing);
    }

    @NonNull
    public Executor getIoExecutor() {
        return (Executor) retrieveOption(IoConfig.OPTION_IO_EXECUTOR);
    }
}
