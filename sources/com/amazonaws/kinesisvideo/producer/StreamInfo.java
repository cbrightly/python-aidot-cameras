package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.luck.picture.lib.config.PictureMimeType;

public class StreamInfo {
    public static final int STREAM_INFO_CURRENT_VERSION = 0;
    private final boolean mAbsoluteFragmentTimes;
    private final boolean mAdaptive;
    private final int mAvgBandwidthBps;
    private final long mBufferDuration;
    private final String mCodecId;
    private final byte[] mCodecPrivateData;
    private final long mConnectionStalenessDuration;
    private final String mContentType;
    private final boolean mFragmentAcks;
    private final long mFragmentDuration;
    private final int mFrameRate;
    private final boolean mFrameTimecodes;
    private final boolean mKeyFrameFragmentation;
    private final String mKmsKeyId;
    private final long mMaxLatency;
    private final NalAdaptationFlags mNalAdaptationFlags;
    private final String mName;
    private final boolean mRecalculateMetrics;
    private final boolean mRecoverOnError;
    private final long mReplayDuration;
    private final long mRetentionPeriod;
    private final StreamingType mStreamingType;
    private final Tag[] mTags;
    private final long mTimecodeScale;
    private final String mTrackName;
    private final int mVersion;

    public enum StreamingType {
        STREAMING_TYPE_REALTIME(0),
        STREAMING_TYPE_NEAR_REALTIME(1),
        STREAMING_TYPE_OFFLINE(2);
        
        private int value;

        private StreamingType(int i) {
            this.value = i;
        }

        public int getIntValue() {
            return this.value;
        }
    }

    public enum NalAdaptationFlags {
        NAL_ADAPTATION_FLAG_NONE(0),
        NAL_ADAPTATION_ANNEXB_NALS(8),
        NAL_ADAPTATION_AVCC_NALS(16),
        NAL_ADAPTATION_ANNEXB_CPD_NALS(32),
        NAL_ADAPTATION_ANNEXB_CPD_AND_FRAME_NALS(r1.getIntValue() | r5.getIntValue());
        
        private int value;

        private NalAdaptationFlags(int i) {
            this.value = i;
        }

        public static NalAdaptationFlags getFlag(int i) {
            for (NalAdaptationFlags eachValue : values()) {
                if (eachValue.getIntValue() == i) {
                    return eachValue;
                }
            }
            throw new IllegalArgumentException("Illegal value for NalAdaptationFlags");
        }

        public int getIntValue() {
            return this.value;
        }
    }

    @NonNull
    public static String createTrackName(@NonNull String contentType) {
        Preconditions.checkNotNull(contentType);
        return "Track_" + contentType;
    }

    @NonNull
    public static String codecIdFromContentType(@NonNull String contentType) {
        String lowerCaseContentType = ((String) Preconditions.checkNotNull(contentType)).toLowerCase();
        if (lowerCaseContentType.compareTo("video/x-vnd.on2.vp8") == 0) {
            return "V_VP8";
        }
        if (lowerCaseContentType.compareTo("video/x-vnd.on2.vp9") == 0) {
            return "V_VP9";
        }
        if (lowerCaseContentType.compareTo("video/avc") == 0 || lowerCaseContentType.compareTo("video/h264") == 0) {
            return "V_MPEG4/ISO/AVC";
        }
        if (lowerCaseContentType.compareTo("video/hevc") == 0) {
            return "V_MPEG4/ISO/AP";
        }
        if (lowerCaseContentType.compareTo("video/mp4v-es") == 0) {
            return "V_MPEG4/ISO/ASP";
        }
        if (lowerCaseContentType.compareTo(PictureMimeType.MIME_TYPE_AUDIO) == 0) {
            return "A_MPEG/L3";
        }
        if (lowerCaseContentType.compareTo("audio/mp4a-latm") == 0) {
            return "A_AAC/MPEG4/MAIN";
        }
        if (lowerCaseContentType.compareTo("audio/vorbis") == 0) {
            return "A_VORBIS";
        }
        throw new KinesisVideoException("Unknown content type to convert.");
    }

    public StreamInfo(int version, @Nullable String name, @NonNull StreamingType streamingType, @NonNull String contentType, @Nullable String kmsKeyId, long retentionPeriod, boolean adaptive, long maxLatency, long fragmentDuration, boolean keyFrameFragmentation, boolean frameTimecodes, boolean absoluteFragmentTimes, boolean fragmentAcks, boolean recoverOnError, @Nullable String codecId, @Nullable String trackName, int avgBandwidthBps, int frameRate, long bufferDuration, long replayDuration, long connectionStalenessDuration, long timecodeScale, boolean recalculateMetrics, @Nullable byte[] codecPrivateData, @Nullable Tag[] tags, @NonNull NalAdaptationFlags nalAdaptationFlags) {
        this.mVersion = version;
        this.mName = name;
        this.mStreamingType = streamingType;
        this.mContentType = contentType;
        this.mKmsKeyId = kmsKeyId;
        this.mRetentionPeriod = retentionPeriod;
        this.mAdaptive = adaptive;
        this.mMaxLatency = maxLatency;
        this.mFragmentDuration = fragmentDuration;
        this.mKeyFrameFragmentation = keyFrameFragmentation;
        this.mFrameTimecodes = frameTimecodes;
        this.mAbsoluteFragmentTimes = absoluteFragmentTimes;
        this.mFragmentAcks = fragmentAcks;
        this.mRecoverOnError = recoverOnError;
        this.mCodecId = codecId;
        this.mTrackName = trackName;
        this.mAvgBandwidthBps = avgBandwidthBps;
        this.mFrameRate = frameRate;
        this.mBufferDuration = bufferDuration;
        this.mReplayDuration = replayDuration;
        this.mConnectionStalenessDuration = connectionStalenessDuration;
        this.mTimecodeScale = timecodeScale;
        this.mRecalculateMetrics = recalculateMetrics;
        this.mCodecPrivateData = codecPrivateData;
        this.mTags = tags;
        this.mNalAdaptationFlags = nalAdaptationFlags;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Nullable
    public String getName() {
        return this.mName;
    }

    public int getStreamingType() {
        return this.mStreamingType.getIntValue();
    }

    @NonNull
    public String getContentType() {
        return this.mContentType;
    }

    @Nullable
    public String getKmsKeyId() {
        return this.mKmsKeyId;
    }

    public long getRetentionPeriod() {
        return this.mRetentionPeriod;
    }

    public boolean isAdaptive() {
        return this.mAdaptive;
    }

    public long getMaxLatency() {
        return this.mMaxLatency;
    }

    public long getFragmentDuration() {
        return this.mFragmentDuration;
    }

    public boolean isKeyFrameFragmentation() {
        return this.mKeyFrameFragmentation;
    }

    public boolean isFrameTimecodes() {
        return this.mFrameTimecodes;
    }

    public boolean isAbsoluteFragmentTimes() {
        return this.mAbsoluteFragmentTimes;
    }

    public boolean isFragmentAcks() {
        return this.mFragmentAcks;
    }

    public boolean isRecoverOnError() {
        return this.mRecoverOnError;
    }

    @Nullable
    public String getCodecId() {
        return this.mCodecId;
    }

    @Nullable
    public String getTrackName() {
        return this.mTrackName;
    }

    public int getAvgBandwidthBps() {
        return this.mAvgBandwidthBps;
    }

    public int getFrameRate() {
        return this.mFrameRate;
    }

    public long getBufferDuration() {
        return this.mBufferDuration;
    }

    public long getReplayDuration() {
        return this.mReplayDuration;
    }

    public long getConnectionStalenessDuration() {
        return this.mConnectionStalenessDuration;
    }

    public long getTimecodeScale() {
        return this.mTimecodeScale;
    }

    public boolean isRecalculateMetrics() {
        return this.mRecalculateMetrics;
    }

    @Nullable
    public byte[] getCodecPrivateData() {
        return this.mCodecPrivateData;
    }

    @Nullable
    public Tag[] getTags() {
        return this.mTags;
    }

    @NonNull
    public int getNalAdaptationFlags() {
        return this.mNalAdaptationFlags.getIntValue();
    }
}
