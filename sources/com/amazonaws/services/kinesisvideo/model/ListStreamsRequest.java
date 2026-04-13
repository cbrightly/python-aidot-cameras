package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListStreamsRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer maxResults;
    private String nextToken;
    private StreamNameCondition streamNameCondition;

    public Integer getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
    }

    public ListStreamsRequest withMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListStreamsRequest withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public StreamNameCondition getStreamNameCondition() {
        return this.streamNameCondition;
    }

    public void setStreamNameCondition(StreamNameCondition streamNameCondition2) {
        this.streamNameCondition = streamNameCondition2;
    }

    public ListStreamsRequest withStreamNameCondition(StreamNameCondition streamNameCondition2) {
        this.streamNameCondition = streamNameCondition2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getMaxResults() != null) {
            sb.append("MaxResults: " + getMaxResults() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken() + ",");
        }
        if (getStreamNameCondition() != null) {
            sb.append("StreamNameCondition: " + getStreamNameCondition());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getMaxResults() == null ? 0 : getMaxResults().hashCode())) * 31) + (getNextToken() == null ? 0 : getNextToken().hashCode())) * 31;
        if (getStreamNameCondition() != null) {
            i = getStreamNameCondition().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListStreamsRequest)) {
            return false;
        }
        ListStreamsRequest other = (ListStreamsRequest) obj;
        if ((other.getMaxResults() == null) ^ (getMaxResults() == null)) {
            return false;
        }
        if (other.getMaxResults() != null && !other.getMaxResults().equals(getMaxResults())) {
            return false;
        }
        if ((other.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        if (other.getNextToken() != null && !other.getNextToken().equals(getNextToken())) {
            return false;
        }
        if ((other.getStreamNameCondition() == null) ^ (getStreamNameCondition() == null)) {
            return false;
        }
        if (other.getStreamNameCondition() == null || other.getStreamNameCondition().equals(getStreamNameCondition())) {
            return true;
        }
        return false;
    }
}
