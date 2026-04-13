package androidx.camera.core.impl;

import android.media.CamcorderProfile;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CamcorderProfileProxy {
    public abstract int getAudioBitRate();

    public abstract int getAudioChannels();

    public abstract int getAudioCodec();

    public abstract int getAudioSampleRate();

    public abstract int getDuration();

    public abstract int getFileFormat();

    public abstract int getQuality();

    public abstract int getVideoBitRate();

    public abstract int getVideoCodec();

    public abstract int getVideoFrameHeight();

    public abstract int getVideoFrameRate();

    public abstract int getVideoFrameWidth();

    @NonNull
    public static CamcorderProfileProxy create(int duration, int quality, int fileFormat, int videoCodec, int videoBitRate, int videoFrameRate, int videoFrameWidth, int videoFrameHeight, int audioCodec, int audioBitRate, int audioSampleRate, int audioChannels) {
        return new AutoValue_CamcorderProfileProxy(duration, quality, fileFormat, videoCodec, videoBitRate, videoFrameRate, videoFrameWidth, videoFrameHeight, audioCodec, audioBitRate, audioSampleRate, audioChannels);
    }

    @NonNull
    public static CamcorderProfileProxy fromCamcorderProfile(@NonNull CamcorderProfile camcorderProfile) {
        return new AutoValue_CamcorderProfileProxy(camcorderProfile.duration, camcorderProfile.quality, camcorderProfile.fileFormat, camcorderProfile.videoCodec, camcorderProfile.videoBitRate, camcorderProfile.videoFrameRate, camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight, camcorderProfile.audioCodec, camcorderProfile.audioBitRate, camcorderProfile.audioSampleRate, camcorderProfile.audioChannels);
    }
}
