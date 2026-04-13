package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListTagsForResourceRequest extends AmazonWebServiceRequest implements Serializable {
    private String nextToken;
    private String resourceARN;

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListTagsForResourceRequest withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String getResourceARN() {
        return this.resourceARN;
    }

    public void setResourceARN(String resourceARN2) {
        this.resourceARN = resourceARN2;
    }

    public ListTagsForResourceRequest withResourceARN(String resourceARN2) {
        this.resourceARN = resourceARN2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken() + ",");
        }
        if (getResourceARN() != null) {
            sb.append("ResourceARN: " + getResourceARN());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getNextToken() == null ? 0 : getNextToken().hashCode())) * 31;
        if (getResourceARN() != null) {
            i = getResourceARN().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListTagsForResourceRequest)) {
            return false;
        }
        ListTagsForResourceRequest other = (ListTagsForResourceRequest) obj;
        if ((other.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        if (other.getNextToken() != null && !other.getNextToken().equals(getNextToken())) {
            return false;
        }
        if ((other.getResourceARN() == null) ^ (getResourceARN() == null)) {
            return false;
        }
        if (other.getResourceARN() == null || other.getResourceARN().equals(getResourceARN())) {
            return true;
        }
        return false;
    }
}
