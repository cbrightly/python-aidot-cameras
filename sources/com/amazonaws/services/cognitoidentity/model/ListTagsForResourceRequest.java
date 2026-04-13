package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListTagsForResourceRequest extends AmazonWebServiceRequest implements Serializable {
    private String resourceArn;

    public String getResourceArn() {
        return this.resourceArn;
    }

    public void setResourceArn(String resourceArn2) {
        this.resourceArn = resourceArn2;
    }

    public ListTagsForResourceRequest withResourceArn(String resourceArn2) {
        this.resourceArn = resourceArn2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getResourceArn() != null) {
            sb.append("ResourceArn: " + getResourceArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getResourceArn() == null ? 0 : getResourceArn().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListTagsForResourceRequest)) {
            return false;
        }
        ListTagsForResourceRequest other = (ListTagsForResourceRequest) obj;
        if ((other.getResourceArn() == null) ^ (getResourceArn() == null)) {
            return false;
        }
        if (other.getResourceArn() == null || other.getResourceArn().equals(getResourceArn())) {
            return true;
        }
        return false;
    }
}
