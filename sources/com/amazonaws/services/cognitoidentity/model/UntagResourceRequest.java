package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UntagResourceRequest extends AmazonWebServiceRequest implements Serializable {
    private String resourceArn;
    private List<String> tagKeys;

    public String getResourceArn() {
        return this.resourceArn;
    }

    public void setResourceArn(String resourceArn2) {
        this.resourceArn = resourceArn2;
    }

    public UntagResourceRequest withResourceArn(String resourceArn2) {
        this.resourceArn = resourceArn2;
        return this;
    }

    public List<String> getTagKeys() {
        return this.tagKeys;
    }

    public void setTagKeys(Collection<String> tagKeys2) {
        if (tagKeys2 == null) {
            this.tagKeys = null;
        } else {
            this.tagKeys = new ArrayList(tagKeys2);
        }
    }

    public UntagResourceRequest withTagKeys(String... tagKeys2) {
        if (getTagKeys() == null) {
            this.tagKeys = new ArrayList(tagKeys2.length);
        }
        for (String value : tagKeys2) {
            this.tagKeys.add(value);
        }
        return this;
    }

    public UntagResourceRequest withTagKeys(Collection<String> tagKeys2) {
        setTagKeys(tagKeys2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getResourceArn() != null) {
            sb.append("ResourceArn: " + getResourceArn() + ",");
        }
        if (getTagKeys() != null) {
            sb.append("TagKeys: " + getTagKeys());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getResourceArn() == null ? 0 : getResourceArn().hashCode())) * 31;
        if (getTagKeys() != null) {
            i = getTagKeys().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UntagResourceRequest)) {
            return false;
        }
        UntagResourceRequest other = (UntagResourceRequest) obj;
        if ((other.getResourceArn() == null) ^ (getResourceArn() == null)) {
            return false;
        }
        if (other.getResourceArn() != null && !other.getResourceArn().equals(getResourceArn())) {
            return false;
        }
        if ((other.getTagKeys() == null) ^ (getTagKeys() == null)) {
            return false;
        }
        if (other.getTagKeys() == null || other.getTagKeys().equals(getTagKeys())) {
            return true;
        }
        return false;
    }
}
