package com.amazonaws.kinesisvideo.internal.client;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.client.KinesisVideoClient;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSource;
import com.amazonaws.kinesisvideo.producer.DeviceInfo;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractKinesisVideoClient implements KinesisVideoClient {
    protected boolean mIsInitialized = false;
    protected final Log mLog;
    protected final List<MediaSource> mMediaSources = new ArrayList();

    public AbstractKinesisVideoClient(@NonNull Log log) {
        this.mLog = (Log) Preconditions.checkNotNull(log);
    }

    public boolean isInitialized() {
        return this.mIsInitialized;
    }

    public void initialize(@NonNull DeviceInfo deviceInfo) {
        this.mLog.info("Initializing Kinesis Video client");
        Preconditions.checkState(!this.mIsInitialized, "Already initialized");
        this.mIsInitialized = true;
    }

    public void startAllMediaSources() {
        this.mLog.verbose("Resuming Kinesis Video client");
        Preconditions.checkState(isInitialized(), "Must initialize first.");
        for (MediaSource mediaSource : this.mMediaSources) {
            mediaSource.start();
        }
    }

    public void stopAllMediaSources() {
        this.mLog.verbose("Pausing Kinesis Video client");
        if (isInitialized()) {
            for (MediaSource mediaSource : this.mMediaSources) {
                mediaSource.stop();
            }
        }
    }

    public void free() {
        this.mLog.verbose("Releasing Kinesis Video client");
        if (isInitialized()) {
            for (MediaSource mediaSource : this.mMediaSources) {
                if (!mediaSource.isStopped()) {
                    mediaSource.stop();
                }
                mediaSource.free();
            }
            this.mMediaSources.clear();
        }
    }

    public void registerMediaSource(@NonNull MediaSource mediaSource) {
        this.mMediaSources.add(mediaSource);
    }

    public void unregisterMediaSource(@NonNull MediaSource mediaSource) {
        this.mMediaSources.remove(mediaSource);
    }
}
