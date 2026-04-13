package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UntagResourceRequest extends AmazonWebServiceRequest implements Serializable {
    private String resourceARN;
    private List<String> tagKeyList = new ArrayList();

    public String getResourceARN() {
        return this.resourceARN;
    }

    public void setResourceARN(String resourceARN2) {
        this.resourceARN = resourceARN2;
    }

    public UntagResourceRequest withResourceARN(String resourceARN2) {
        this.resourceARN = resourceARN2;
        return this;
    }

    public List<String> getTagKeyList() {
        return this.tagKeyList;
    }

    public void setTagKeyList(Collection<String> tagKeyList2) {
        if (tagKeyList2 == null) {
            this.tagKeyList = null;
        } else {
            this.tagKeyList = new ArrayList(tagKeyList2);
        }
    }

    public UntagResourceRequest withTagKeyList(String... tagKeyList2) {
        if (getTagKeyList() == null) {
            this.tagKeyList = new ArrayList(tagKeyList2.length);
        }
        for (String value : tagKeyList2) {
            this.tagKeyList.add(value);
        }
        return this;
    }

    public UntagResourceRequest withTagKeyList(Collection<String> tagKeyList2) {
        setTagKeyList(tagKeyList2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getResourceARN() != null) {
            sb.append("ResourceARN: " + getResourceARN() + ",");
        }
        if (getTagKeyList() != null) {
            sb.append("TagKeyList: " + getTagKeyList());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getResourceARN() == null ? 0 : getResourceARN().hashCode())) * 31;
        if (getTagKeyList() != null) {
            i = getTagKeyList().hashCode();
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
        if ((other.getResourceARN() == null) ^ (getResourceARN() == null)) {
            return false;
        }
        if (other.getResourceARN() != null && !other.getResourceARN().equals(getResourceARN())) {
            return false;
        }
        if ((other.getTagKeyList() == null) ^ (getTagKeyList() == null)) {
            return false;
        }
        if (other.getTagKeyList() == null || other.getTagKeyList().equals(getTagKeyList())) {
            return true;
        }
        return false;
    }
}
