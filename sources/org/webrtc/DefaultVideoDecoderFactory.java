package org.webrtc;

import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import org.webrtc.AndroidVideoDecoder;
import org.webrtc.EglBase;

public class DefaultVideoDecoderFactory implements VideoDecoderFactory {
    private AndroidVideoDecoder.IOnAndroidVideoCodecError _onErrorCodecErrorHandler;
    private final VideoDecoderFactory hardwareVideoDecoderFactory;
    private boolean isSupportHevcHardware = true;
    @Nullable
    private final VideoDecoderFactory platformSoftwareVideoDecoderFactory;
    private final VideoDecoderFactory softwareVideoDecoderFactory = new SoftwareVideoDecoderFactory();

    public DefaultVideoDecoderFactory(@Nullable EglBase.Context eglContext) {
        this.hardwareVideoDecoderFactory = new HardwareVideoDecoderFactory(eglContext);
        this.platformSoftwareVideoDecoderFactory = new PlatformSoftwareVideoDecoderFactory(eglContext);
    }

    public DefaultVideoDecoderFactory(@Nullable EglBase.Context eglContext, boolean isSupportHevcHardware2) {
        this.isSupportHevcHardware = isSupportHevcHardware2;
        this.hardwareVideoDecoderFactory = new HardwareVideoDecoderFactory(eglContext);
        this.platformSoftwareVideoDecoderFactory = new PlatformSoftwareVideoDecoderFactory(eglContext);
    }

    public void closeDecoder() {
        ((SoftwareVideoDecoderFactory) this.softwareVideoDecoderFactory).closeDecoder();
    }

    public void openDecoder() {
        ((SoftwareVideoDecoderFactory) this.softwareVideoDecoderFactory).openDecoder();
    }

    DefaultVideoDecoderFactory(VideoDecoderFactory hardwareVideoDecoderFactory2) {
        this.hardwareVideoDecoderFactory = hardwareVideoDecoderFactory2;
        this.platformSoftwareVideoDecoderFactory = null;
    }

    @Nullable
    public VideoDecoder createDecoder(VideoCodecInfo codecType) {
        VideoDecoderFactory videoDecoderFactory;
        if (codecType.getName().equalsIgnoreCase(VideoCodecMimeType.H265.name()) && !this.isSupportHevcHardware) {
            return this.softwareVideoDecoderFactory.createDecoder(codecType);
        }
        VideoDecoder softwareDecoder = this.softwareVideoDecoderFactory.createDecoder(codecType);
        VideoDecoder hardwareDecoder = this.hardwareVideoDecoderFactory.createDecoder(codecType);
        VideoDecoderFactory videoDecoderFactory2 = this.hardwareVideoDecoderFactory;
        if (videoDecoderFactory2 instanceof HardwareVideoDecoderFactory) {
            ((HardwareVideoDecoderFactory) videoDecoderFactory2).setOnErrorCodecErrorHandler(new f(this));
        }
        if (softwareDecoder == null && (videoDecoderFactory = this.platformSoftwareVideoDecoderFactory) != null) {
            softwareDecoder = videoDecoderFactory.createDecoder(codecType);
        }
        if (hardwareDecoder == null || softwareDecoder == null) {
            return hardwareDecoder != null ? hardwareDecoder : softwareDecoder;
        }
        return new VideoDecoderFallback(softwareDecoder, hardwareDecoder);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createDecoder$0 */
    public /* synthetic */ void a(int code, String message) {
        AndroidVideoDecoder.IOnAndroidVideoCodecError iOnAndroidVideoCodecError = this._onErrorCodecErrorHandler;
        if (iOnAndroidVideoCodecError != null) {
            iOnAndroidVideoCodecError.onFirstFrameKeyDecodedError(code, message);
        }
    }

    public VideoCodecInfo[] getSupportedCodecs() {
        LinkedHashSet<VideoCodecInfo> supportedCodecInfos = new LinkedHashSet<>();
        supportedCodecInfos.addAll(Arrays.asList(this.hardwareVideoDecoderFactory.getSupportedCodecs()));
        return (VideoCodecInfo[]) supportedCodecInfos.toArray(new VideoCodecInfo[supportedCodecInfos.size()]);
    }

    public void setOnErrorCodecErrorHandler(AndroidVideoDecoder.IOnAndroidVideoCodecError errorHandler) {
        this._onErrorCodecErrorHandler = errorHandler;
    }
}
