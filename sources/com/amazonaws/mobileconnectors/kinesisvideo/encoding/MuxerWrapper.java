package com.amazonaws.mobileconnectors.kinesisvideo.encoding;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import tv.danmaku.ijk.media.player.misc.IMediaFormat;

public class MuxerWrapper {
    private static final DateFormat DATE_FORMAT_NOW = new SimpleDateFormat(FILE_TIMESTAMP_FORMAT);
    private static final String FILE_NAME_PREFIX = "muxer-output-";
    private static final String FILE_TIMESTAMP_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    private static final String MP4_EXTENSION = ".mp4";
    private static final String OUTPUT_PATH = "/sdcard/";
    private static final String TAG = MuxerWrapper.class.getSimpleName();
    private static final String WEBM_EXTENSION = ".webm";
    private MediaMuxer mMediaMuxer;
    private final String mMimeType;
    private int mTrackIndex;

    public MuxerWrapper(MediaCodec encoder) {
        this.mMimeType = encoder.getOutputFormat().getString(IMediaFormat.KEY_MIME);
    }

    public synchronized void start(MediaCodec encoder) {
        if (this.mMediaMuxer != null) {
            Log.w(TAG, "starting muxer before stopping previous one");
            stop();
        }
        MediaFormat outputFormat = encoder.getOutputFormat();
        MediaMuxer createMuxer = createMuxer();
        this.mMediaMuxer = createMuxer;
        this.mTrackIndex = createMuxer.addTrack(outputFormat);
        this.mMediaMuxer.start();
    }

    private MediaMuxer createMuxer() {
        try {
            return new MediaMuxer(getOutputPath(), getMediaFormat());
        } catch (IOException e) {
            throw new RuntimeException("unable to create muxer", e);
        }
    }

    private String getOutputPath() {
        String extension = getOutputFileExtension();
        String outputFullPath = OUTPUT_PATH + FILE_NAME_PREFIX + DATE_FORMAT_NOW.format(new Date()) + extension;
        Log.d(TAG, "Using '" + outputFullPath + "' for output");
        return outputFullPath;
    }

    private String getOutputFileExtension() {
        if ("video/x-vnd.on2.vp8".equals(this.mMimeType) || "video/x-vnd.on2.vp9".equals(this.mMimeType)) {
            return WEBM_EXTENSION;
        }
        if ("video/avc".equals(this.mMimeType)) {
            return ".mp4";
        }
        throw new RuntimeException("unsupported media format '" + this.mMimeType + "'. only AVC(h264) and vp8/vp9 are supported");
    }

    private int getMediaFormat() {
        if ("video/x-vnd.on2.vp8".equals(this.mMimeType) || "video/x-vnd.on2.vp9".equals(this.mMimeType)) {
            return 1;
        }
        if ("video/avc".equals(this.mMimeType)) {
            return 0;
        }
        throw new RuntimeException("unsupported media format '" + this.mMimeType + "'. only AVC(h264) and vp8/vp9 are supported");
    }

    public void writeData(MediaCodec.BufferInfo bufferInfo, ByteBuffer encodedData) {
        MediaMuxer mediaMuxer = this.mMediaMuxer;
        if (mediaMuxer != null) {
            mediaMuxer.writeSampleData(this.mTrackIndex, encodedData, bufferInfo);
            return;
        }
        throw new IllegalStateException("writing data before starting the muxer");
    }

    public synchronized void stop() {
        this.mMediaMuxer.stop();
        this.mMediaMuxer.release();
        this.mMediaMuxer = null;
    }
}
