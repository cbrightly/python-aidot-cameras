package com.amazonaws.kinesisvideo.streaming;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFragmentAck;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;

public class DefaultStreamCallbacks implements StreamCallbacks {
    public void streamUnderflowReport() {
    }

    public void streamLatencyPressure(long duration) {
    }

    public void streamConnectionStale(long lastAckDuration) {
    }

    public void fragmentAckReceived(@NonNull KinesisVideoFragmentAck fragmentAck) {
    }

    public void droppedFrameReport(long frameTimecode) {
    }

    public void streamErrorReport(long frameTimecode, long statusCode) {
    }

    public void droppedFragmentReport(long fragmentTimecode) {
    }

    public void streamDataAvailable(long uploadHandle, long duration, long availableSize) {
    }

    public void streamReady() {
    }

    public void streamClosed(long uploadHandle) {
    }
}
