package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateSignalingChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String channelName;
    private String channelType;
    private SingleMasterConfiguration singleMasterConfiguration;
    private List<Tag> tags = new ArrayList();

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName2) {
        this.channelName = channelName2;
    }

    public CreateSignalingChannelRequest withChannelName(String channelName2) {
        this.channelName = channelName2;
        return this;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType2) {
        this.channelType = channelType2;
    }

    public CreateSignalingChannelRequest withChannelType(String channelType2) {
        this.channelType = channelType2;
        return this;
    }

    public void setChannelType(ChannelType channelType2) {
        this.channelType = channelType2.toString();
    }

    public CreateSignalingChannelRequest withChannelType(ChannelType channelType2) {
        this.channelType = channelType2.toString();
        return this;
    }

    public SingleMasterConfiguration getSingleMasterConfiguration() {
        return this.singleMasterConfiguration;
    }

    public void setSingleMasterConfiguration(SingleMasterConfiguration singleMasterConfiguration2) {
        this.singleMasterConfiguration = singleMasterConfiguration2;
    }

    public CreateSignalingChannelRequest withSingleMasterConfiguration(SingleMasterConfiguration singleMasterConfiguration2) {
        this.singleMasterConfiguration = singleMasterConfiguration2;
        return this;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Collection<Tag> tags2) {
        if (tags2 == null) {
            this.tags = null;
        } else {
            this.tags = new ArrayList(tags2);
        }
    }

    public CreateSignalingChannelRequest withTags(Tag... tags2) {
        if (getTags() == null) {
            this.tags = new ArrayList(tags2.length);
        }
        for (Tag value : tags2) {
            this.tags.add(value);
        }
        return this;
    }

    public CreateSignalingChannelRequest withTags(Collection<Tag> tags2) {
        setTags(tags2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelName() != null) {
            sb.append("ChannelName: " + getChannelName() + ",");
        }
        if (getChannelType() != null) {
            sb.append("ChannelType: " + getChannelType() + ",");
        }
        if (getSingleMasterConfiguration() != null) {
            sb.append("SingleMasterConfiguration: " + getSingleMasterConfiguration() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((((1 * 31) + (getChannelName() == null ? 0 : getChannelName().hashCode())) * 31) + (getChannelType() == null ? 0 : getChannelType().hashCode())) * 31;
        if (getSingleMasterConfiguration() == null) {
            i = 0;
        } else {
            i = getSingleMasterConfiguration().hashCode();
        }
        int hashCode2 = (hashCode + i) * 31;
        if (getTags() != null) {
            i2 = getTags().hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateSignalingChannelRequest)) {
            return false;
        }
        CreateSignalingChannelRequest other = (CreateSignalingChannelRequest) obj;
        if ((other.getChannelName() == null) ^ (getChannelName() == null)) {
            return false;
        }
        if (other.getChannelName() != null && !other.getChannelName().equals(getChannelName())) {
            return false;
        }
        if ((other.getChannelType() == null) ^ (getChannelType() == null)) {
            return false;
        }
        if (other.getChannelType() != null && !other.getChannelType().equals(getChannelType())) {
            return false;
        }
        if ((other.getSingleMasterConfiguration() == null) ^ (getSingleMasterConfiguration() == null)) {
            return false;
        }
        if (other.getSingleMasterConfiguration() != null && !other.getSingleMasterConfiguration().equals(getSingleMasterConfiguration())) {
            return false;
        }
        if ((other.getTags() == null) ^ (getTags() == null)) {
            return false;
        }
        if (other.getTags() == null || other.getTags().equals(getTags())) {
            return true;
        }
        return false;
    }
}
