package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class DescribeIdentityRequest extends AmazonWebServiceRequest implements Serializable {
    private String identityId;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public DescribeIdentityRequest withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeIdentityRequest)) {
            return false;
        }
        DescribeIdentityRequest other = (DescribeIdentityRequest) obj;
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() == null || other.getIdentityId().equals(getIdentityId())) {
            return true;
        }
        return false;
    }
}
