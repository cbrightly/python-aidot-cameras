package tv.danmaku.ijk.media.player.misc;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.os.Build;
import com.google.maps.android.BuildConfig;

public class AndroidTrackInfo implements ITrackInfo {
    private final MediaPlayer.TrackInfo mTrackInfo;

    public static AndroidTrackInfo[] fromMediaPlayer(MediaPlayer mp) {
        if (Build.VERSION.SDK_INT >= 16) {
            return fromTrackInfo(mp.getTrackInfo());
        }
        return null;
    }

    private static AndroidTrackInfo[] fromTrackInfo(MediaPlayer.TrackInfo[] trackInfos) {
        if (trackInfos == null) {
            return null;
        }
        AndroidTrackInfo[] androidTrackInfo = new AndroidTrackInfo[trackInfos.length];
        for (int i = 0; i < trackInfos.length; i++) {
            androidTrackInfo[i] = new AndroidTrackInfo(trackInfos[i]);
        }
        return androidTrackInfo;
    }

    private AndroidTrackInfo(MediaPlayer.TrackInfo trackInfo) {
        this.mTrackInfo = trackInfo;
    }

    @TargetApi(19)
    public IMediaFormat getFormat() {
        MediaFormat mediaFormat;
        MediaPlayer.TrackInfo trackInfo = this.mTrackInfo;
        if (trackInfo == null || Build.VERSION.SDK_INT < 19 || (mediaFormat = trackInfo.getFormat()) == null) {
            return null;
        }
        return new AndroidMediaFormat(mediaFormat);
    }

    @TargetApi(16)
    public String getLanguage() {
        MediaPlayer.TrackInfo trackInfo = this.mTrackInfo;
        if (trackInfo == null) {
            return "und";
        }
        return trackInfo.getLanguage();
    }

    @TargetApi(16)
    public int getTrackType() {
        MediaPlayer.TrackInfo trackInfo = this.mTrackInfo;
        if (trackInfo == null) {
            return 0;
        }
        return trackInfo.getTrackType();
    }

    @TargetApi(16)
    public String toString() {
        StringBuilder out = new StringBuilder(128);
        out.append(getClass().getSimpleName());
        out.append('{');
        MediaPlayer.TrackInfo trackInfo = this.mTrackInfo;
        if (trackInfo != null) {
            out.append(trackInfo.toString());
        } else {
            out.append(BuildConfig.TRAVIS);
        }
        out.append('}');
        return out.toString();
    }

    @TargetApi(16)
    public String getInfoInline() {
        MediaPlayer.TrackInfo trackInfo = this.mTrackInfo;
        if (trackInfo != null) {
            return trackInfo.toString();
        }
        return BuildConfig.TRAVIS;
    }
}
