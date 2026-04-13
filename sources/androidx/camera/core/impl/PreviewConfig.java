package androidx.camera.core.impl;

import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
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

public final class PreviewConfig implements UseCaseConfig<Preview>, ImageOutputConfig, ThreadConfig {
    public static final Config.Option<ImageInfoProcessor> IMAGE_INFO_PROCESSOR = Config.Option.create("camerax.core.preview.imageInfoProcessor", ImageInfoProcessor.class);
    public static final Config.Option<CaptureProcessor> OPTION_PREVIEW_CAPTURE_PROCESSOR = Config.Option.create("camerax.core.preview.captureProcessor", CaptureProcessor.class);
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

    public PreviewConfig(@NonNull OptionsBundle config) {
        this.mConfig = config;
    }

    @NonNull
    public Config getConfig() {
        return this.mConfig;
    }

    @Nullable
    public ImageInfoProcessor getImageInfoProcessor(@Nullable ImageInfoProcessor valueIfMissing) {
        return (ImageInfoProcessor) retrieveOption(IMAGE_INFO_PROCESSOR, valueIfMissing);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public ImageInfoProcessor getImageInfoProcessor() {
        return (ImageInfoProcessor) retrieveOption(IMAGE_INFO_PROCESSOR);
    }

    @Nullable
    public CaptureProcessor getCaptureProcessor(@Nullable CaptureProcessor valueIfMissing) {
        return (CaptureProcessor) retrieveOption(OPTION_PREVIEW_CAPTURE_PROCESSOR, valueIfMissing);
    }

    @NonNull
    public CaptureProcessor getCaptureProcessor() {
        return (CaptureProcessor) retrieveOption(OPTION_PREVIEW_CAPTURE_PROCESSOR);
    }

    public int getInputFormat() {
        return ((Integer) retrieveOption(ImageInputConfig.OPTION_INPUT_FORMAT)).intValue();
    }
}
