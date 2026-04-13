package com.amazonaws.kinesisvideo.producer;

public class FragmentAckType {
    public static final int FRAGMENT_ACK_TYPE_BUFFERING = 1;
    public static final int FRAGMENT_ACK_TYPE_ERROR = 4;
    public static final int FRAGMENT_ACK_TYPE_IDLE = 5;
    public static final int FRAGMENT_ACK_TYPE_PERSISTED = 3;
    public static final int FRAGMENT_ACK_TYPE_RECEIVED = 2;
    public static final int FRAGMENT_ACK_TYPE_UNDEFINED = 0;
    private final int mType;

    public FragmentAckType(int type) {
        this.mType = type;
    }

    public int getIntType() {
        return this.mType;
    }
}
