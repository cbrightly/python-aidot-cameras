package com.amazonaws.kinesisvideo.internal.producer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFragmentAck;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import java.io.InputStream;

public interface KinesisVideoProducerStream extends StreamCallbacks {
    public static final long READY_TIMEOUT_IN_MILLISECONDS = 15000;
    public static final long STOPPED_TIMEOUT_IN_MILLISECONDS = 15000;

    void fragmentAck(long j, @NonNull KinesisVideoFragmentAck kinesisVideoFragmentAck);

    @NonNull
    InputStream getDataStream(long j);

    @NonNull
    KinesisVideoStreamMetrics getMetrics();

    void getStreamData(@NonNull byte[] bArr, int i, int i2, @NonNull ReadResult readResult);

    long getStreamHandle();

    @NonNull
    String getStreamName();

    void parseFragmentAck(long j, @NonNull String str);

    void putFragmentMetadata(@NonNull String str, @NonNull String str2, boolean z);

    void putFrame(@NonNull KinesisVideoFrame kinesisVideoFrame);

    void resetConnection();

    void stopStream();

    void stopStreamSync();

    void streamFormatChanged(@Nullable byte[] bArr);

    void streamTerminated(long j, int i);
}
