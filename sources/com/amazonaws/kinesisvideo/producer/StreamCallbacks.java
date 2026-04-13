package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;

public interface StreamCallbacks {
    void droppedFragmentReport(long j);

    void droppedFrameReport(long j);

    void fragmentAckReceived(@NonNull KinesisVideoFragmentAck kinesisVideoFragmentAck);

    void streamClosed(long j);

    void streamConnectionStale(long j);

    void streamDataAvailable(long j, long j2, long j3);

    void streamErrorReport(long j, long j2);

    void streamLatencyPressure(long j);

    void streamReady();

    void streamUnderflowReport();
}
