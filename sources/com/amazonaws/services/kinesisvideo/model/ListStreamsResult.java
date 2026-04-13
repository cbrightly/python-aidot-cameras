package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListStreamsResult implements Serializable {
    private String nextToken;
    private List<StreamInfo> streamInfoList = new ArrayList();

    public List<StreamInfo> getStreamInfoList() {
        return this.streamInfoList;
    }

    public void setStreamInfoList(Collection<StreamInfo> streamInfoList2) {
        if (streamInfoList2 == null) {
            this.streamInfoList = null;
        } else {
            this.streamInfoList = new ArrayList(streamInfoList2);
        }
    }

    public ListStreamsResult withStreamInfoList(StreamInfo... streamInfoList2) {
        if (getStreamInfoList() == null) {
            this.streamInfoList = new ArrayList(streamInfoList2.length);
        }
        for (StreamInfo value : streamInfoList2) {
            this.streamInfoList.add(value);
        }
        return this;
    }

    public ListStreamsResult withStreamInfoList(Collection<StreamInfo> streamInfoList2) {
        setStreamInfoList(streamInfoList2);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListStreamsResult withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamInfoList() != null) {
            sb.append("StreamInfoList: " + getStreamInfoList() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getStreamInfoList() == null ? 0 : getStreamInfoList().hashCode())) * 31;
        if (getNextToken() != null) {
            i = getNextToken().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListStreamsResult)) {
            return false;
        }
        ListStreamsResult other = (ListStreamsResult) obj;
        if ((other.getStreamInfoList() == null) ^ (getStreamInfoList() == null)) {
            return false;
        }
        if (other.getStreamInfoList() != null && !other.getStreamInfoList().equals(getStreamInfoList())) {
            return false;
        }
        if ((other.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        if (other.getNextToken() == null || other.getNextToken().equals(getNextToken())) {
            return true;
        }
        return false;
    }
}
