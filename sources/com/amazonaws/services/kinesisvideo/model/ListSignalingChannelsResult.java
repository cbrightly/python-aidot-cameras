package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListSignalingChannelsResult implements Serializable {
    private List<ChannelInfo> channelInfoList = new ArrayList();
    private String nextToken;

    public List<ChannelInfo> getChannelInfoList() {
        return this.channelInfoList;
    }

    public void setChannelInfoList(Collection<ChannelInfo> channelInfoList2) {
        if (channelInfoList2 == null) {
            this.channelInfoList = null;
        } else {
            this.channelInfoList = new ArrayList(channelInfoList2);
        }
    }

    public ListSignalingChannelsResult withChannelInfoList(ChannelInfo... channelInfoList2) {
        if (getChannelInfoList() == null) {
            this.channelInfoList = new ArrayList(channelInfoList2.length);
        }
        for (ChannelInfo value : channelInfoList2) {
            this.channelInfoList.add(value);
        }
        return this;
    }

    public ListSignalingChannelsResult withChannelInfoList(Collection<ChannelInfo> channelInfoList2) {
        setChannelInfoList(channelInfoList2);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListSignalingChannelsResult withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelInfoList() != null) {
            sb.append("ChannelInfoList: " + getChannelInfoList() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getChannelInfoList() == null ? 0 : getChannelInfoList().hashCode())) * 31;
        if (getNextToken() != null) {
            i = getNextToken().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListSignalingChannelsResult)) {
            return false;
        }
        ListSignalingChannelsResult other = (ListSignalingChannelsResult) obj;
        if ((other.getChannelInfoList() == null) ^ (getChannelInfoList() == null)) {
            return false;
        }
        if (other.getChannelInfoList() != null && !other.getChannelInfoList().equals(getChannelInfoList())) {
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
