package org.webrtc;

import androidx.annotation.Nullable;
import org.webrtc.EncodedImage;

public interface VideoEncoder {

    public interface Callback {
        void onEncodedFrame(EncodedImage encodedImage, CodecSpecificInfo codecSpecificInfo);
    }

    public static class CodecSpecificInfo {
    }

    public static class CodecSpecificInfoAV1 extends CodecSpecificInfo {
    }

    public static class CodecSpecificInfoH264 extends CodecSpecificInfo {
    }

    public static class CodecSpecificInfoVP8 extends CodecSpecificInfo {
    }

    public static class CodecSpecificInfoVP9 extends CodecSpecificInfo {
    }

    @CalledByNative
    long createNativeVideoEncoder();

    @CalledByNative
    VideoCodecStatus encode(VideoFrame videoFrame, EncodeInfo encodeInfo);

    @CalledByNative
    EncoderInfo getEncoderInfo();

    @CalledByNative
    String getImplementationName();

    @CalledByNative
    ResolutionBitrateLimits[] getResolutionBitrateLimits();

    @CalledByNative
    ScalingSettings getScalingSettings();

    @CalledByNative
    VideoCodecStatus initEncode(Settings settings, Callback callback);

    @CalledByNative
    boolean isHardwareEncoder();

    @CalledByNative
    VideoCodecStatus release();

    VideoCodecStatus setRateAllocation(BitrateAllocation bitrateAllocation, int i);

    @CalledByNative
    VideoCodecStatus setRates(RateControlParameters rateControlParameters);

    public static class Settings {
        public final boolean automaticResizeOn;
        public final Capabilities capabilities;
        public final int height;
        public final int maxFramerate;
        public final int numberOfCores;
        public final int numberOfSimulcastStreams;
        public final int startBitrate;
        public final int width;

        @Deprecated
        public Settings(int numberOfCores2, int width2, int height2, int startBitrate2, int maxFramerate2, int numberOfSimulcastStreams2, boolean automaticResizeOn2) {
            this(numberOfCores2, width2, height2, startBitrate2, maxFramerate2, numberOfSimulcastStreams2, automaticResizeOn2, new Capabilities(false));
        }

        @CalledByNative("Settings")
        public Settings(int numberOfCores2, int width2, int height2, int startBitrate2, int maxFramerate2, int numberOfSimulcastStreams2, boolean automaticResizeOn2, Capabilities capabilities2) {
            this.numberOfCores = numberOfCores2;
            this.width = width2;
            this.height = height2;
            this.startBitrate = startBitrate2;
            this.maxFramerate = maxFramerate2;
            this.numberOfSimulcastStreams = numberOfSimulcastStreams2;
            this.automaticResizeOn = automaticResizeOn2;
            this.capabilities = capabilities2;
        }
    }

    public static class Capabilities {
        public final boolean lossNotification;

        @CalledByNative("Capabilities")
        public Capabilities(boolean lossNotification2) {
            this.lossNotification = lossNotification2;
        }
    }

    public static class EncodeInfo {
        public final EncodedImage.FrameType[] frameTypes;

        @CalledByNative("EncodeInfo")
        public EncodeInfo(EncodedImage.FrameType[] frameTypes2) {
            this.frameTypes = frameTypes2;
        }
    }

    public static class BitrateAllocation {
        public final int[][] bitratesBbs;

        @CalledByNative("BitrateAllocation")
        public BitrateAllocation(int[][] bitratesBbs2) {
            this.bitratesBbs = bitratesBbs2;
        }

        public int getSum() {
            int sum = 0;
            for (int[] spatialLayer : this.bitratesBbs) {
                for (int bitrate : r1[r4]) {
                    sum += bitrate;
                }
            }
            return sum;
        }
    }

    public static class ScalingSettings {
        public static final ScalingSettings OFF = new ScalingSettings();
        @Nullable
        public final Integer high;
        @Nullable
        public final Integer low;
        public final boolean on;

        public ScalingSettings(int low2, int high2) {
            this.on = true;
            this.low = Integer.valueOf(low2);
            this.high = Integer.valueOf(high2);
        }

        private ScalingSettings() {
            this.on = false;
            this.low = null;
            this.high = null;
        }

        @Deprecated
        public ScalingSettings(boolean on2) {
            this.on = on2;
            this.low = null;
            this.high = null;
        }

        @Deprecated
        public ScalingSettings(boolean on2, int low2, int high2) {
            this.on = on2;
            this.low = Integer.valueOf(low2);
            this.high = Integer.valueOf(high2);
        }

        public String toString() {
            if (!this.on) {
                return "OFF";
            }
            return "[ " + this.low + ", " + this.high + " ]";
        }
    }

    public static class ResolutionBitrateLimits {
        public final int frameSizePixels;
        public final int maxBitrateBps;
        public final int minBitrateBps;
        public final int minStartBitrateBps;

        public ResolutionBitrateLimits(int frameSizePixels2, int minStartBitrateBps2, int minBitrateBps2, int maxBitrateBps2) {
            this.frameSizePixels = frameSizePixels2;
            this.minStartBitrateBps = minStartBitrateBps2;
            this.minBitrateBps = minBitrateBps2;
            this.maxBitrateBps = maxBitrateBps2;
        }

        @CalledByNative("ResolutionBitrateLimits")
        public int getFrameSizePixels() {
            return this.frameSizePixels;
        }

        @CalledByNative("ResolutionBitrateLimits")
        public int getMinStartBitrateBps() {
            return this.minStartBitrateBps;
        }

        @CalledByNative("ResolutionBitrateLimits")
        public int getMinBitrateBps() {
            return this.minBitrateBps;
        }

        @CalledByNative("ResolutionBitrateLimits")
        public int getMaxBitrateBps() {
            return this.maxBitrateBps;
        }
    }

    public static class RateControlParameters {
        public final BitrateAllocation bitrate;
        public final double framerateFps;

        @CalledByNative("RateControlParameters")
        public RateControlParameters(BitrateAllocation bitrate2, double framerateFps2) {
            this.bitrate = bitrate2;
            this.framerateFps = framerateFps2;
        }
    }

    public static class EncoderInfo {
        public final boolean applyAlignmentToAllSimulcastLayers;
        public final int requestedResolutionAlignment;

        public EncoderInfo(int requestedResolutionAlignment2, boolean applyAlignmentToAllSimulcastLayers2) {
            this.requestedResolutionAlignment = requestedResolutionAlignment2;
            this.applyAlignmentToAllSimulcastLayers = applyAlignmentToAllSimulcastLayers2;
        }

        @CalledByNative("EncoderInfo")
        public int getRequestedResolutionAlignment() {
            return this.requestedResolutionAlignment;
        }

        @CalledByNative("EncoderInfo")
        public boolean getApplyAlignmentToAllSimulcastLayers() {
            return this.applyAlignmentToAllSimulcastLayers;
        }
    }
}
