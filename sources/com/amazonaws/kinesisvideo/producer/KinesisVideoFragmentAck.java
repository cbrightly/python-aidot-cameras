package com.amazonaws.kinesisvideo.producer;

import androidx.annotation.NonNull;

public class KinesisVideoFragmentAck {
    private static final int FRAGMENT_ACK_CURRENT_VERSION = 0;
    private final FragmentAckType mAckType;
    private final int mResult;
    private final String mSequenceNumber;
    private final long mTimestamp;

    public KinesisVideoFragmentAck(int ackType, long timestamp, @NonNull String sequenceNumber, int result) {
        this(new FragmentAckType(ackType), timestamp, sequenceNumber, result);
    }

    public KinesisVideoFragmentAck(@NonNull FragmentAckType ackType, long timestamp, @NonNull String sequenceNumber, int result) {
        this.mAckType = ackType;
        this.mTimestamp = timestamp;
        this.mSequenceNumber = sequenceNumber;
        this.mResult = result;
    }

    public int getVersion() {
        return 0;
    }

    @NonNull
    public FragmentAckType getAckType() {
        return this.mAckType;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    @NonNull
    public String getSequenceNumber() {
        return this.mSequenceNumber;
    }

    public int getResult() {
        return this.mResult;
    }
}
