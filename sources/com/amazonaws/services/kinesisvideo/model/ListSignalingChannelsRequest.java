package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListSignalingChannelsRequest extends AmazonWebServiceRequest implements Serializable {
    private ChannelNameCondition channelNameCondition;
    private Integer maxResults;
    private String nextToken;

    public Integer getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
    }

    public ListSignalingChannelsRequest withMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListSignalingChannelsRequest withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public ChannelNameCondition getChannelNameCondition() {
        return this.channelNameCondition;
    }

    public void setChannelNameCondition(ChannelNameCondition channelNameCondition2) {
        this.channelNameCondition = channelNameCondition2;
    }

    public ListSignalingChannelsRequest withChannelNameCondition(ChannelNameCondition channelNameCondition2) {
        this.channelNameCondition = channelNameCondition2;
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
        if (getChannelNameCondition() != null) {
            sb.append("ChannelNameCondition: " + getChannelNameCondition());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getMaxResults() == null ? 0 : getMaxResults().hashCode())) * 31) + (getNextToken() == null ? 0 : getNextToken().hashCode())) * 31;
        if (getChannelNameCondition() != null) {
            i = getChannelNameCondition().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListSignalingChannelsRequest)) {
            return false;
        }
        ListSignalingChannelsRequest other = (ListSignalingChannelsRequest) obj;
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
        if ((other.getChannelNameCondition() == null) ^ (getChannelNameCondition() == null)) {
            return false;
        }
        if (other.getChannelNameCondition() == null || other.getChannelNameCondition().equals(getChannelNameCondition())) {
            return true;
        }
        return false;
    }
}
