package androidx.camera.core.impl;

public final class AutoValue_CamcorderProfileProxy extends CamcorderProfileProxy {
    private final int audioBitRate;
    private final int audioChannels;
    private final int audioCodec;
    private final int audioSampleRate;
    private final int duration;
    private final int fileFormat;
    private final int quality;
    private final int videoBitRate;
    private final int videoCodec;
    private final int videoFrameHeight;
    private final int videoFrameRate;
    private final int videoFrameWidth;

    AutoValue_CamcorderProfileProxy(int duration2, int quality2, int fileFormat2, int videoCodec2, int videoBitRate2, int videoFrameRate2, int videoFrameWidth2, int videoFrameHeight2, int audioCodec2, int audioBitRate2, int audioSampleRate2, int audioChannels2) {
        this.duration = duration2;
        this.quality = quality2;
        this.fileFormat = fileFormat2;
        this.videoCodec = videoCodec2;
        this.videoBitRate = videoBitRate2;
        this.videoFrameRate = videoFrameRate2;
        this.videoFrameWidth = videoFrameWidth2;
        this.videoFrameHeight = videoFrameHeight2;
        this.audioCodec = audioCodec2;
        this.audioBitRate = audioBitRate2;
        this.audioSampleRate = audioSampleRate2;
        this.audioChannels = audioChannels2;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getQuality() {
        return this.quality;
    }

    public int getFileFormat() {
        return this.fileFormat;
    }

    public int getVideoCodec() {
        return this.videoCodec;
    }

    public int getVideoBitRate() {
        return this.videoBitRate;
    }

    public int getVideoFrameRate() {
        return this.videoFrameRate;
    }

    public int getVideoFrameWidth() {
        return this.videoFrameWidth;
    }

    public int getVideoFrameHeight() {
        return this.videoFrameHeight;
    }

    public int getAudioCodec() {
        return this.audioCodec;
    }

    public int getAudioBitRate() {
        return this.audioBitRate;
    }

    public int getAudioSampleRate() {
        return this.audioSampleRate;
    }

    public int getAudioChannels() {
        return this.audioChannels;
    }

    public String toString() {
        return "CamcorderProfileProxy{duration=" + this.duration + ", quality=" + this.quality + ", fileFormat=" + this.fileFormat + ", videoCodec=" + this.videoCodec + ", videoBitRate=" + this.videoBitRate + ", videoFrameRate=" + this.videoFrameRate + ", videoFrameWidth=" + this.videoFrameWidth + ", videoFrameHeight=" + this.videoFrameHeight + ", audioCodec=" + this.audioCodec + ", audioBitRate=" + this.audioBitRate + ", audioSampleRate=" + this.audioSampleRate + ", audioChannels=" + this.audioChannels + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CamcorderProfileProxy)) {
            return false;
        }
        CamcorderProfileProxy that = (CamcorderProfileProxy) o;
        if (this.duration == that.getDuration() && this.quality == that.getQuality() && this.fileFormat == that.getFileFormat() && this.videoCodec == that.getVideoCodec() && this.videoBitRate == that.getVideoBitRate() && this.videoFrameRate == that.getVideoFrameRate() && this.videoFrameWidth == that.getVideoFrameWidth() && this.videoFrameHeight == that.getVideoFrameHeight() && this.audioCodec == that.getAudioCodec() && this.audioBitRate == that.getAudioBitRate() && this.audioSampleRate == that.getAudioSampleRate() && this.audioChannels == that.getAudioChannels()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((((((((((((1 * 1000003) ^ this.duration) * 1000003) ^ this.quality) * 1000003) ^ this.fileFormat) * 1000003) ^ this.videoCodec) * 1000003) ^ this.videoBitRate) * 1000003) ^ this.videoFrameRate) * 1000003) ^ this.videoFrameWidth) * 1000003) ^ this.videoFrameHeight) * 1000003) ^ this.audioCodec) * 1000003) ^ this.audioBitRate) * 1000003) ^ this.audioSampleRate) * 1000003) ^ this.audioChannels;
    }
}
