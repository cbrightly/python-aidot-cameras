package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class DescribeStreamResult implements Serializable {
    private StreamInfo streamInfo;

    public StreamInfo getStreamInfo() {
        return this.streamInfo;
    }

    public void setStreamInfo(StreamInfo streamInfo2) {
        this.streamInfo = streamInfo2;
    }

    public DescribeStreamResult withStreamInfo(StreamInfo streamInfo2) {
        this.streamInfo = streamInfo2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamInfo() != null) {
            sb.append("StreamInfo: " + getStreamInfo());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getStreamInfo() == null ? 0 : getStreamInfo().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeStreamResult)) {
            return false;
        }
        DescribeStreamResult other = (DescribeStreamResult) obj;
        if ((other.getStreamInfo() == null) ^ (getStreamInfo() == null)) {
            return false;
        }
        if (other.getStreamInfo() == null || other.getStreamInfo().equals(getStreamInfo())) {
            return true;
        }
        return false;
    }
}
