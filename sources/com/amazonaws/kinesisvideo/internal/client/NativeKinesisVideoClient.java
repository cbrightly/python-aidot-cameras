package com.amazonaws.kinesisvideo.internal.client;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.amazonaws.kinesisvideo.client.KinesisVideoClientConfiguration;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.logging.LogLevel;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSource;
import com.amazonaws.kinesisvideo.internal.client.mediasource.MediaSourceConfiguration;
import com.amazonaws.kinesisvideo.internal.mediasource.ProducerStreamSink;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.internal.producer.ServiceCallbacks;
import com.amazonaws.kinesisvideo.internal.producer.client.KinesisVideoServiceClient;
import com.amazonaws.kinesisvideo.internal.producer.jni.NativeKinesisVideoProducerJni;
import com.amazonaws.kinesisvideo.internal.service.DefaultServiceCallbacksImpl;
import com.amazonaws.kinesisvideo.producer.AuthCallbacks;
import com.amazonaws.kinesisvideo.producer.DeviceInfo;
import com.amazonaws.kinesisvideo.producer.StorageCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import com.amazonaws.kinesisvideo.streaming.DefaultStreamCallbacks;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class NativeKinesisVideoClient extends AbstractKinesisVideoClient {
    private static final String TAG = "NativeKinesisVideoClient";
    private KinesisVideoProducer kinesisVideoProducer;
    private final AuthCallbacks mAuthCallbacks;
    private final Map<MediaSource, KinesisVideoProducerStream> mMediaSourceToStreamMap;
    private final ServiceCallbacks mServiceCallbacks;
    private final StorageCallbacks mStorageCallbacks;
    private final StreamCallbacks mStreamCallbacks;

    public NativeKinesisVideoClient(@NonNull KinesisVideoClientConfiguration configuration, @NonNull KinesisVideoServiceClient serviceClient, @NonNull ScheduledExecutorService executor) {
        this(new Log(configuration.getLogChannel(), LogLevel.VERBOSE, TAG), configuration, serviceClient, executor);
    }

    public NativeKinesisVideoClient(@NonNull Log log, @NonNull KinesisVideoClientConfiguration configuration, @NonNull KinesisVideoServiceClient serviceClient, @NonNull ScheduledExecutorService executor) {
        this(log, new DefaultAuthCallbacks(configuration.getCredentialsProvider(), executor, log), configuration.getStorageCallbacks(), new DefaultServiceCallbacksImpl(log, executor, configuration, serviceClient), new DefaultStreamCallbacks());
    }

    public NativeKinesisVideoClient(@NonNull Log log, @NonNull AuthCallbacks authCallbacks, @NonNull StorageCallbacks storageCallbacks, @NonNull ServiceCallbacks serviceCallbacks, @NonNull StreamCallbacks streamCallbacks) {
        super(log);
        this.mAuthCallbacks = (AuthCallbacks) Preconditions.checkNotNull(authCallbacks);
        this.mStorageCallbacks = (StorageCallbacks) Preconditions.checkNotNull(storageCallbacks);
        this.mServiceCallbacks = (ServiceCallbacks) Preconditions.checkNotNull(serviceCallbacks);
        this.mStreamCallbacks = (StreamCallbacks) Preconditions.checkNotNull(streamCallbacks);
        this.mMediaSourceToStreamMap = new HashMap();
    }

    public void initialize(@NonNull DeviceInfo deviceInfo) {
        this.kinesisVideoProducer = initializeNewKinesisVideoProducer(deviceInfo);
        super.initialize(deviceInfo);
    }

    public void registerMediaSource(@NonNull MediaSource mediaSource) {
        Preconditions.checkNotNull(mediaSource);
        StreamCallbacks streamCallbacks = mediaSource.getStreamCallbacks();
        if (streamCallbacks == null) {
            streamCallbacks = this.mStreamCallbacks;
        }
        KinesisVideoProducerStream producerStream = this.kinesisVideoProducer.createStreamSync(mediaSource.getStreamInfo(), streamCallbacks);
        mediaSource.initialize(new ProducerStreamSink(producerStream));
        this.mServiceCallbacks.addStream(producerStream);
        this.mMediaSourceToStreamMap.put(mediaSource, producerStream);
        super.registerMediaSource(mediaSource);
    }

    public void unregisterMediaSource(@NonNull MediaSource mediaSource) {
        Preconditions.checkNotNull(mediaSource);
        super.unregisterMediaSource(mediaSource);
        KinesisVideoProducerStream producerStream = this.mMediaSourceToStreamMap.get(mediaSource);
        producerStream.stopStream();
        this.kinesisVideoProducer.freeStream(producerStream);
        this.mServiceCallbacks.removeStream(producerStream);
    }

    public void stopAllMediaSources() {
        super.stopAllMediaSources();
        for (MediaSource mediaSource : this.mMediaSources) {
            try {
                this.mMediaSourceToStreamMap.get(mediaSource).stopStreamSync();
            } catch (KinesisVideoException e) {
                this.mLog.exception(e, "Failed to stop media source %s due to Exception ", mediaSource);
            }
        }
    }

    public MediaSource createMediaSource(String streamName, MediaSourceConfiguration mediaSourceConfiguration) {
        throw new KinesisVideoException("creating media sources is not implemented yet");
    }

    public List<MediaSourceConfiguration.Builder<? extends MediaSourceConfiguration>> listSupportedConfigurations() {
        return Collections.emptyList();
    }

    public void free() {
        if (isInitialized()) {
            super.free();
            this.mServiceCallbacks.free();
            this.kinesisVideoProducer.stopStreams();
            this.kinesisVideoProducer.free();
            this.mIsInitialized = false;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public KinesisVideoProducer initializeNewKinesisVideoProducer(DeviceInfo deviceInfo) {
        KinesisVideoProducer kinesisVideoProducer2 = new NativeKinesisVideoProducerJni(this.mAuthCallbacks, this.mStorageCallbacks, this.mServiceCallbacks, this.mLog);
        kinesisVideoProducer2.createSync(deviceInfo);
        return kinesisVideoProducer2;
    }
}
