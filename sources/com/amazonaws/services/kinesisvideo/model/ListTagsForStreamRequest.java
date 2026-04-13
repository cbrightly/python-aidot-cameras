package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListTagsForStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private String nextToken;
    private String streamARN;
    private String streamName;

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListTagsForStreamRequest withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public ListTagsForStreamRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public ListTagsForStreamRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken() + ",");
        }
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN() + ",");
        }
        if (getStreamName() != null) {
            sb.append("StreamName: " + getStreamName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getNextToken() == null ? 0 : getNextToken().hashCode())) * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31;
        if (getStreamName() != null) {
            i = getStreamName().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListTagsForStreamRequest)) {
            return false;
        }
        ListTagsForStreamRequest other = (ListTagsForStreamRequest) obj;
        if ((other.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        if (other.getNextToken() != null && !other.getNextToken().equals(getNextToken())) {
            return false;
        }
        if ((other.getStreamARN() == null) ^ (getStreamARN() == null)) {
            return false;
        }
        if (other.getStreamARN() != null && !other.getStreamARN().equals(getStreamARN())) {
            return false;
        }
        if ((other.getStreamName() == null) ^ (getStreamName() == null)) {
            return false;
        }
        if (other.getStreamName() == null || other.getStreamName().equals(getStreamName())) {
            return true;
        }
        return false;
    }
}
