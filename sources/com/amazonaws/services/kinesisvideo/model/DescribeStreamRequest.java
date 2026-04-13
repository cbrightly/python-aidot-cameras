package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class DescribeStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private String streamARN;
    private String streamName;

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public DescribeStreamRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public DescribeStreamRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamName() != null) {
            sb.append("StreamName: " + getStreamName() + ",");
        }
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31;
        if (getStreamARN() != null) {
            i = getStreamARN().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeStreamRequest)) {
            return false;
        }
        DescribeStreamRequest other = (DescribeStreamRequest) obj;
        if ((other.getStreamName() == null) ^ (getStreamName() == null)) {
            return false;
        }
        if (other.getStreamName() != null && !other.getStreamName().equals(getStreamName())) {
            return false;
        }
        if ((other.getStreamARN() == null) ^ (getStreamARN() == null)) {
            return false;
        }
        if (other.getStreamARN() == null || other.getStreamARN().equals(getStreamARN())) {
            return true;
        }
        return false;
    }
}
