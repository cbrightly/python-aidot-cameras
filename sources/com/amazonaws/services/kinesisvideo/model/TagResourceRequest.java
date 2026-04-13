package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TagResourceRequest extends AmazonWebServiceRequest implements Serializable {
    private String resourceARN;
    private List<Tag> tags = new ArrayList();

    public String getResourceARN() {
        return this.resourceARN;
    }

    public void setResourceARN(String resourceARN2) {
        this.resourceARN = resourceARN2;
    }

    public TagResourceRequest withResourceARN(String resourceARN2) {
        this.resourceARN = resourceARN2;
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

    public TagResourceRequest withTags(Tag... tags2) {
        if (getTags() == null) {
            this.tags = new ArrayList(tags2.length);
        }
        for (Tag value : tags2) {
            this.tags.add(value);
        }
        return this;
    }

    public TagResourceRequest withTags(Collection<Tag> tags2) {
        setTags(tags2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getResourceARN() != null) {
            sb.append("ResourceARN: " + getResourceARN() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getResourceARN() == null ? 0 : getResourceARN().hashCode())) * 31;
        if (getTags() != null) {
            i = getTags().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TagResourceRequest)) {
            return false;
        }
        TagResourceRequest other = (TagResourceRequest) obj;
        if ((other.getResourceARN() == null) ^ (getResourceARN() == null)) {
            return false;
        }
        if (other.getResourceARN() != null && !other.getResourceARN().equals(getResourceARN())) {
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
