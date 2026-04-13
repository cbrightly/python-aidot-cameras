package tv.danmaku.ijk.media.player.misc;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import com.google.maps.android.BuildConfig;

public class AndroidMediaFormat implements IMediaFormat {
    private final MediaFormat mMediaFormat;

    public AndroidMediaFormat(MediaFormat mediaFormat) {
        this.mMediaFormat = mediaFormat;
    }

    @TargetApi(16)
    public int getInteger(String name) {
        MediaFormat mediaFormat = this.mMediaFormat;
        if (mediaFormat == null) {
            return 0;
        }
        return mediaFormat.getInteger(name);
    }

    @TargetApi(16)
    public String getString(String name) {
        MediaFormat mediaFormat = this.mMediaFormat;
        if (mediaFormat == null) {
            return null;
        }
        return mediaFormat.getString(name);
    }

    @TargetApi(16)
    public String toString() {
        StringBuilder out = new StringBuilder(128);
        out.append(getClass().getName());
        out.append('{');
        MediaFormat mediaFormat = this.mMediaFormat;
        if (mediaFormat != null) {
            out.append(mediaFormat.toString());
        } else {
            out.append(BuildConfig.TRAVIS);
        }
        out.append('}');
        return out.toString();
    }
}
