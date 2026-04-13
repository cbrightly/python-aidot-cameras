package io.microshow.rxffmpeg;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import tv.danmaku.ijk.media.player.misc.IMediaFormat;

public class AudioVideoUtils {
    public static int getFitBitRate(int wxh) {
        if (wxh <= 230400) {
            return 1024000;
        }
        if (wxh <= 307200) {
            return 1536000;
        }
        if (wxh <= 384000) {
            return 1843200;
        }
        if (wxh <= 522240) {
            return 2097152;
        }
        if (wxh <= 921600) {
            return 2621440;
        }
        if (wxh <= 2088960) {
            return 3145728;
        }
        return 3584000;
    }

    public static int selectVideoTrack(MediaExtractor extractor) {
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            if (extractor.getTrackFormat(i).getString(IMediaFormat.KEY_MIME).startsWith("video/")) {
                return i;
            }
        }
        return -1;
    }

    public static int selectAudioTrack(MediaExtractor extractor) {
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            if (extractor.getTrackFormat(i).getString(IMediaFormat.KEY_MIME).startsWith("audio/")) {
                return i;
            }
        }
        return -1;
    }

    public static long getDuration(String url) {
        try {
            MediaExtractor mediaExtractor = new MediaExtractor();
            mediaExtractor.setDataSource(url);
            MediaFormat mediaFormat = mediaExtractor.getTrackFormat(selectVideoTrack(mediaExtractor));
            long res = mediaFormat.containsKey("durationUs") ? mediaFormat.getLong("durationUs") : 0;
            mediaExtractor.release();
            return res;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getVideoWidth(String videoPath) {
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        String width = retr.extractMetadata(18);
        String height = retr.extractMetadata(19);
        String rotation = retr.extractMetadata(24);
        if ("90".equals(rotation) || "270".equals(rotation)) {
            width = height;
        }
        retr.release();
        return Integer.parseInt(width);
    }

    public static int getVideoHeight(String videoPath) {
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        String width = retr.extractMetadata(18);
        String height = retr.extractMetadata(19);
        String rotation = retr.extractMetadata(24);
        if ("90".equals(rotation) || "270".equals(rotation)) {
            height = width;
        }
        retr.release();
        return Integer.parseInt(height);
    }

    public static int getVideoRotation(String videoPath) {
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        String rotation = retr.extractMetadata(24);
        retr.release();
        return Integer.parseInt(rotation);
    }

    public static int getVideoDuration(String videoPath) {
        MediaMetadataRetriever retr = new MediaMetadataRetriever();
        retr.setDataSource(videoPath);
        String rotation = retr.extractMetadata(9);
        retr.release();
        return Integer.parseInt(rotation) / 1000;
    }

    public static boolean isHorizontalVideo(String videoPath) {
        if (getVideoWidth(videoPath) >= getVideoHeight(videoPath)) {
            return true;
        }
        return false;
    }
}
