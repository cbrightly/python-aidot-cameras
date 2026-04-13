package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class DeleteIdentityPoolRequest extends AmazonWebServiceRequest implements Serializable {
    private String identityPoolId;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public DeleteIdentityPoolRequest withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteIdentityPoolRequest)) {
            return false;
        }
        DeleteIdentityPoolRequest other = (DeleteIdentityPoolRequest) obj;
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() == null || other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return true;
        }
        return false;
    }
}
