package com.amazonaws.kinesisvideo.producer;

public enum StreamStatus {
    CREATING(0),
    ACTIVE(1),
    UPDATING(2),
    DELETING(3),
    UNKNOWN(-1);
    
    private final int mValue;

    public static final int getStatusCode(String status) {
        for (StreamStatus streamStatus : values()) {
            if (streamStatus.name().equals(status)) {
                return streamStatus.mValue;
            }
        }
        return UNKNOWN.intValue();
    }

    private StreamStatus(int value) {
        this.mValue = value;
    }

    public final int intValue() {
        return this.mValue;
    }
}
