package org.webrtc;

import androidx.annotation.Nullable;
import java.util.List;
import java.util.Map;
import org.webrtc.MediaStreamTrack;

public class RtpParameters {
    public final List<Codec> codecs;
    @Nullable
    public DegradationPreference degradationPreference;
    public final List<Encoding> encodings;
    private final List<HeaderExtension> headerExtensions;
    private final Rtcp rtcp;
    public final String transactionId;

    public enum DegradationPreference {
        DISABLED,
        MAINTAIN_FRAMERATE,
        MAINTAIN_RESOLUTION,
        BALANCED;

        @CalledByNative("DegradationPreference")
        static DegradationPreference fromNativeIndex(int nativeIndex) {
            return values()[nativeIndex];
        }
    }

    public static class Encoding {
        public boolean active = true;
        public boolean adaptiveAudioPacketTime;
        public double bitratePriority = 1.0d;
        @Nullable
        public Integer maxBitrateBps;
        @Nullable
        public Integer maxFramerate;
        @Nullable
        public Integer minBitrateBps;
        public int networkPriority = 1;
        @Nullable
        public Integer numTemporalLayers;
        @Nullable
        public String rid;
        @Nullable
        public Double scaleResolutionDownBy;
        public Long ssrc;

        public Encoding(String rid2, boolean active2, Double scaleResolutionDownBy2) {
            this.rid = rid2;
            this.active = active2;
            this.scaleResolutionDownBy = scaleResolutionDownBy2;
        }

        @CalledByNative("Encoding")
        Encoding(String rid2, boolean active2, double bitratePriority2, int networkPriority2, Integer maxBitrateBps2, Integer minBitrateBps2, Integer maxFramerate2, Integer numTemporalLayers2, Double scaleResolutionDownBy2, Long ssrc2, boolean adaptiveAudioPacketTime2) {
            this.rid = rid2;
            this.active = active2;
            this.bitratePriority = bitratePriority2;
            this.networkPriority = networkPriority2;
            this.maxBitrateBps = maxBitrateBps2;
            this.minBitrateBps = minBitrateBps2;
            this.maxFramerate = maxFramerate2;
            this.numTemporalLayers = numTemporalLayers2;
            this.scaleResolutionDownBy = scaleResolutionDownBy2;
            this.ssrc = ssrc2;
            this.adaptiveAudioPacketTime = adaptiveAudioPacketTime2;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        @Nullable
        public String getRid() {
            return this.rid;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        public boolean getActive() {
            return this.active;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        public double getBitratePriority() {
            return this.bitratePriority;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        public int getNetworkPriority() {
            return this.networkPriority;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        @Nullable
        public Integer getMaxBitrateBps() {
            return this.maxBitrateBps;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        @Nullable
        public Integer getMinBitrateBps() {
            return this.minBitrateBps;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        @Nullable
        public Integer getMaxFramerate() {
            return this.maxFramerate;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        @Nullable
        public Integer getNumTemporalLayers() {
            return this.numTemporalLayers;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        @Nullable
        public Double getScaleResolutionDownBy() {
            return this.scaleResolutionDownBy;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        public Long getSsrc() {
            return this.ssrc;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Encoding")
        public boolean getAdaptivePTime() {
            return this.adaptiveAudioPacketTime;
        }
    }

    public static class Codec {
        public Integer clockRate;
        MediaStreamTrack.MediaType kind;
        public String name;
        public Integer numChannels;
        public Map<String, String> parameters;
        public int payloadType;

        @CalledByNative("Codec")
        Codec(int payloadType2, String name2, MediaStreamTrack.MediaType kind2, Integer clockRate2, Integer numChannels2, Map<String, String> parameters2) {
            this.payloadType = payloadType2;
            this.name = name2;
            this.kind = kind2;
            this.clockRate = clockRate2;
            this.numChannels = numChannels2;
            this.parameters = parameters2;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Codec")
        public int getPayloadType() {
            return this.payloadType;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Codec")
        public String getName() {
            return this.name;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Codec")
        public MediaStreamTrack.MediaType getKind() {
            return this.kind;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Codec")
        public Integer getClockRate() {
            return this.clockRate;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Codec")
        public Integer getNumChannels() {
            return this.numChannels;
        }

        /* access modifiers changed from: package-private */
        @CalledByNative("Codec")
        public Map getParameters() {
            return this.parameters;
        }
    }

    public static class Rtcp {
        private final String cname;
        private final boolean reducedSize;

        @CalledByNative("Rtcp")
        Rtcp(String cname2, boolean reducedSize2) {
            this.cname = cname2;
            this.reducedSize = reducedSize2;
        }

        @CalledByNative("Rtcp")
        public String getCname() {
            return this.cname;
        }

        @CalledByNative("Rtcp")
        public boolean getReducedSize() {
            return this.reducedSize;
        }
    }

    public static class HeaderExtension {
        private final boolean encrypted;
        private final int id;
        private final String uri;

        @CalledByNative("HeaderExtension")
        HeaderExtension(String uri2, int id2, boolean encrypted2) {
            this.uri = uri2;
            this.id = id2;
            this.encrypted = encrypted2;
        }

        @CalledByNative("HeaderExtension")
        public String getUri() {
            return this.uri;
        }

        @CalledByNative("HeaderExtension")
        public int getId() {
            return this.id;
        }

        @CalledByNative("HeaderExtension")
        public boolean getEncrypted() {
            return this.encrypted;
        }
    }

    @CalledByNative
    RtpParameters(String transactionId2, DegradationPreference degradationPreference2, Rtcp rtcp2, List<HeaderExtension> headerExtensions2, List<Encoding> encodings2, List<Codec> codecs2) {
        this.transactionId = transactionId2;
        this.degradationPreference = degradationPreference2;
        this.rtcp = rtcp2;
        this.headerExtensions = headerExtensions2;
        this.encodings = encodings2;
        this.codecs = codecs2;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public String getTransactionId() {
        return this.transactionId;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public DegradationPreference getDegradationPreference() {
        return this.degradationPreference;
    }

    @CalledByNative
    public Rtcp getRtcp() {
        return this.rtcp;
    }

    @CalledByNative
    public List<HeaderExtension> getHeaderExtensions() {
        return this.headerExtensions;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public List<Encoding> getEncodings() {
        return this.encodings;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public List<Codec> getCodecs() {
        return this.codecs;
    }
}
