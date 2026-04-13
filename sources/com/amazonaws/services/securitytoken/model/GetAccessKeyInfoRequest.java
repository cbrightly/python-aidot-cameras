package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class GetAccessKeyInfoRequest extends AmazonWebServiceRequest implements Serializable {
    private String accessKeyId;

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId2) {
        this.accessKeyId = accessKeyId2;
    }

    public GetAccessKeyInfoRequest withAccessKeyId(String accessKeyId2) {
        this.accessKeyId = accessKeyId2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAccessKeyId() != null) {
            sb.append("AccessKeyId: " + getAccessKeyId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getAccessKeyId() == null ? 0 : getAccessKeyId().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetAccessKeyInfoRequest)) {
            return false;
        }
        GetAccessKeyInfoRequest other = (GetAccessKeyInfoRequest) obj;
        if ((other.getAccessKeyId() == null) ^ (getAccessKeyId() == null)) {
            return false;
        }
        if (other.getAccessKeyId() == null || other.getAccessKeyId().equals(getAccessKeyId())) {
            return true;
        }
        return false;
    }
}
