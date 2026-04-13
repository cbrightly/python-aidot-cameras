package com.leedarson.serviceinterface.utils;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;

public class Mp4Muxer {
    public static final boolean VERBOSE = true;
    public static ChangeQuickRedirect changeQuickRedirect;
    private String TAG = getClass().getSimpleName();
    private int mAudioTrackIndex = -1;
    private MediaMuxer mMuxer;
    private int mVideoTrackIndex = -1;

    public Mp4Muxer(String outPath) {
        try {
            this.mMuxer = new MediaMuxer(outPath, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addVideoTrack(MediaFormat mediaFormat) {
        if (!PatchProxy.proxy(new Object[]{mediaFormat}, this, changeQuickRedirect, false, 9422, new Class[]{MediaFormat.class}, Void.TYPE).isSupported) {
            if (this.mVideoTrackIndex == -1) {
                this.mVideoTrackIndex = this.mMuxer.addTrack(mediaFormat);
                return;
            }
            throw new RuntimeException("already add video tracks");
        }
    }

    public void addAudioTrack(MediaFormat mediaFormat) {
        if (!PatchProxy.proxy(new Object[]{mediaFormat}, this, changeQuickRedirect, false, 9423, new Class[]{MediaFormat.class}, Void.TYPE).isSupported) {
            if (this.mAudioTrackIndex == -1) {
                this.mAudioTrackIndex = this.mMuxer.addTrack(mediaFormat);
                return;
            }
            throw new RuntimeException("already add audio tracks");
        }
    }

    public void start() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9424, new Class[0], Void.TYPE).isSupported) {
            this.mMuxer.start();
        }
    }

    public synchronized void writeVideoData(ByteBuffer outputBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (!PatchProxy.proxy(new Object[]{outputBuffer, bufferInfo}, this, changeQuickRedirect, false, 9425, new Class[]{ByteBuffer.class, MediaCodec.BufferInfo.class}, Void.TYPE).isSupported) {
            int i = this.mVideoTrackIndex;
            if (i == -1) {
                Log.i(this.TAG, String.format(Locale.US, "pumpStream [%s] but muxer is not start.ignore..", new Object[]{"video"}));
                return;
            }
            writeData(outputBuffer, bufferInfo, i);
        }
    }

    public synchronized void writeAudioData(ByteBuffer outputBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (!PatchProxy.proxy(new Object[]{outputBuffer, bufferInfo}, this, changeQuickRedirect, false, 9426, new Class[]{ByteBuffer.class, MediaCodec.BufferInfo.class}, Void.TYPE).isSupported) {
            int i = this.mAudioTrackIndex;
            if (i == -1) {
                Log.i(this.TAG, String.format(Locale.US, "pumpStream [%s] but muxer is not start.ignore..", new Object[]{"audio"}));
                return;
            }
            writeData(outputBuffer, bufferInfo, i);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeData(ByteBuffer outputBuffer, MediaCodec.BufferInfo bufferInfo, int track) {
        if (!PatchProxy.proxy(new Object[]{outputBuffer, bufferInfo, new Integer(track)}, this, changeQuickRedirect, false, 9427, new Class[]{ByteBuffer.class, MediaCodec.BufferInfo.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if ((bufferInfo.flags & 2) != 0) {
                bufferInfo.size = 0;
            } else if (bufferInfo.size != 0) {
                outputBuffer.position(bufferInfo.offset);
                outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                this.mMuxer.writeSampleData(track, outputBuffer, bufferInfo);
                String str = this.TAG;
                Locale locale = Locale.US;
                Log.d(str, String.format(locale, "send [%d] [" + bufferInfo.size + "] with timestamp:[%d] to muxer", new Object[]{Integer.valueOf(track), Long.valueOf(bufferInfo.presentationTimeUs)}));
                if ((bufferInfo.flags & 4) != 0) {
                    Log.i(this.TAG, "BUFFER_FLAG_END_OF_STREAM received");
                }
            }
        }
    }

    public synchronized void stop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9428, new Class[0], Void.TYPE).isSupported) {
            if (!(this.mMuxer == null || (this.mVideoTrackIndex == -1 && this.mAudioTrackIndex == -1))) {
                Log.i(this.TAG, String.format(Locale.US, "muxer is started. now it will be stoped.", new Object[0]));
                try {
                    this.mMuxer.stop();
                    this.mMuxer.release();
                } catch (IllegalStateException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            return;
        }
        return;
    }
}
