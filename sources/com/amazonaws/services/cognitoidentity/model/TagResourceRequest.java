package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TagResourceRequest extends AmazonWebServiceRequest implements Serializable {
    private String resourceArn;
    private Map<String, String> tags;

    public String getResourceArn() {
        return this.resourceArn;
    }

    public void setResourceArn(String resourceArn2) {
        this.resourceArn = resourceArn2;
    }

    public TagResourceRequest withResourceArn(String resourceArn2) {
        this.resourceArn = resourceArn2;
        return this;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> tags2) {
        this.tags = tags2;
    }

    public TagResourceRequest withTags(Map<String, String> tags2) {
        this.tags = tags2;
        return this;
    }

    public TagResourceRequest addTagsEntry(String key, String value) {
        if (this.tags == null) {
            this.tags = new HashMap();
        }
        if (!this.tags.containsKey(key)) {
            this.tags.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public TagResourceRequest clearTagsEntries() {
        this.tags = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getResourceArn() != null) {
            sb.append("ResourceArn: " + getResourceArn() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getResourceArn() == null ? 0 : getResourceArn().hashCode())) * 31;
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
        if ((other.getResourceArn() == null) ^ (getResourceArn() == null)) {
            return false;
        }
        if (other.getResourceArn() != null && !other.getResourceArn().equals(getResourceArn())) {
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
