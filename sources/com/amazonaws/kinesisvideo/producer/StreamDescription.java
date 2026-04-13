package com.amazonaws.kinesisvideo.producer;

public class StreamDescription {
    public static final int STREAM_DESCRIPTION_CURRENT_VERSION = 0;
    private final String mContentType;
    private final long mCreationTime;
    private final String mDeviceName;
    private final String mStreamArn;
    private final String mStreamName;
    private final StreamStatus mStreamStatus;
    private final String mUpdateVersion;
    private final int mVersion;

    public StreamDescription(int version, String deviceName, String streamName, String contentType, String updateVersion, String streamArn, StreamStatus streamStatus, long creationTime) {
        this.mVersion = version;
        this.mDeviceName = deviceName;
        this.mStreamName = streamName;
        this.mContentType = contentType;
        this.mUpdateVersion = updateVersion;
        this.mStreamArn = streamArn;
        this.mStreamStatus = streamStatus;
        this.mCreationTime = creationTime;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getStreamName() {
        return this.mStreamName;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public String getUpdateVersion() {
        return this.mUpdateVersion;
    }

    public String getStreamArn() {
        return this.mStreamArn;
    }

    public int getStreamStatus() {
        return this.mStreamStatus.intValue();
    }

    public long getCreationTime() {
        return this.mCreationTime;
    }
}
